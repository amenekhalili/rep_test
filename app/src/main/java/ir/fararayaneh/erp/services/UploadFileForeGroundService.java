package ir.fararayaneh.erp.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonKind;
import ir.fararayaneh.erp.commons.CommonNetRequest;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.online.INetUploadListener;
import ir.fararayaneh.erp.data.data_providers.online.UploadFileProvider;
import ir.fararayaneh.erp.data.data_providers.queries.attach_query.ChangeStateToSyncedByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.attach_query.FindFirstUnSyncAttachmentRowQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.online.request.rest.upload.Body;
import ir.fararayaneh.erp.data.models.online.request.rest.upload.FileUploadJsonModel;
import ir.fararayaneh.erp.data.models.online.response.UploadResponseModel;
import ir.fararayaneh.erp.data.models.tables.attachment.AttachmentTable;
import ir.fararayaneh.erp.utils.NotificationHelper;
import ir.fararayaneh.erp.utils.PingHandler;
import ir.fararayaneh.erp.utils.file_helper.CheckHaveFile;
import ir.fararayaneh.erp.utils.file_helper.CreateFolderFileHandler;
import ir.fararayaneh.erp.utils.file_helper.FileToUriHelper;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;
import ir.fararayaneh.erp.utils.package_handler.CheckAndroidOVersion;

import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_UPLOAD_ID;

public class UploadFileForeGroundService extends Service {

    private CompositeDisposable compositeDisposable;
    private UploadFileProvider uploadFileProvider;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setupForegroundNotify();
        return START_STICKY;
    }

    /**
     * FOREGROUND_NOTIFICATIONS_UPLOAD_ID != 0
     * notify priority should be greater than low
     */
    private void setupForegroundNotify() {
        /* after maximum 5 second, this code should be ran*/
        startForeground(FOREGROUND_NOTIFICATIONS_UPLOAD_ID, NotificationHelper.setForeGroundUploadFileNotification(this));
        if (compositeDisposable == null) {
            startProcess();
        }
    }

    private void startProcess() {
        createDisposable();
        SharedPreferenceProvider.setUploadProcess(this, true);
        addDisposable(findFirstUnUploadedData());
    }

    private Disposable findFirstUnUploadedData() {
        FindFirstUnSyncAttachmentRowQuery findFirstUnSyncAttachmentRowQuery = (FindFirstUnSyncAttachmentRowQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.FIND_FIRST_UN_SYNC_ATTACHMENT_ROW_QUERY);
        assert findFirstUnSyncAttachmentRowQuery != null;
        return findFirstUnSyncAttachmentRowQuery.data(App.getNullRXModel()).subscribe(iModels -> {
            AttachmentTable attachmentTable = (AttachmentTable) iModels;
            if (!attachmentTable.getAttachmentGUID().equals(Commons.NULL)) {
                if (CheckHaveFile.haveFile(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, attachmentTable.getAttachmentGUID() + attachmentTable.getSuffix())) {
                    uploadFile(attachmentTable);
                } else {
                    addDisposable(ChangeStateToSync(attachmentTable));
                }
            } else {
                //if attachmentTable is empty, we close service
                stopUploadFileForGroundService();
            }
        }, throwable -> addDisposable(findFirstUnUploadedData()));
    }

    //agar ok shod dar jadval darj(ChangeStateToSync) kon
    //agar khata khord kari nakon va findFirstUnUploadedData re seda bezan
    //nokte:  agr be har dalil masaln ghat shodan net, natavanestim file
    // ersal konim dochare loop nashavim (check kardan ping node)
    private void uploadFile(AttachmentTable attachmentTable) {
        if (PingHandler.checkPingNode()) {
            getUploadFileProvider().data(
                    new FileUploadJsonModel(CommonKind.ATTACH, SharedPreferenceProvider.getOrganization(App.getAppContext()),
                            SharedPreferenceProvider.getUserId(App.getAppContext()), CommonNetRequest.APP_NAME,
                            CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_CAP,
                            Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, new Body(attachmentTable.getAttachmentGUID() + attachmentTable.getSuffix(),
                            MimeTypeHandler.suffixToMimeType(attachmentTable.getSuffix()), attachmentTable.getTag(), attachmentTable.getDescription()))
                    , new INetUploadListener() {
                        @Override
                        public void responce(UploadResponseModel response) {
                            if (response.getResult().equals(Commons.OK)) {
                                addDisposable(ChangeStateToSync(attachmentTable));
                            } else {
                                addDisposable(findFirstUnUploadedData());
                            }
                        }

                        @Override
                        public void error(String error) {
                            addDisposable(findFirstUnUploadedData());
                        }
                    }, FileToUriHelper.getUri(CreateFolderFileHandler.createFile(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME,
                            attachmentTable.getAttachmentGUID() + attachmentTable.getSuffix()), App.getAppContext())
            );
        } else {
            stopUploadFileForGroundService();
        }
    }

    private Disposable ChangeStateToSync(AttachmentTable attachmentTable) {
        ChangeStateToSyncedByGUIDQuery changeStateToSyncedByGUIDQuery = (ChangeStateToSyncedByGUIDQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.CHANGE_STATE_TO_SYNCED_BY_GUID_QUERY);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(attachmentTable.getAttachmentGUID());
        assert changeStateToSyncedByGUIDQuery != null;
        return changeStateToSyncedByGUIDQuery.data(globalModel).subscribe(iModels -> addDisposable(findFirstUnUploadedData()), throwable -> addDisposable(findFirstUnUploadedData()));
    }

    private void createDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    private UploadFileProvider getUploadFileProvider() {
        if (uploadFileProvider == null) {
            uploadFileProvider = (UploadFileProvider) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.UPLOAD_FILE_PROVIDER);
        }
        return uploadFileProvider;
    }

    private void addDisposable(Disposable disposable) {
        if (compositeDisposable != null)
            compositeDisposable.add(disposable);
    }

    private void clearDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void onDestroy() {
        Log.i("arash", "intentToUploadFileForeGroundService: bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        stopUploadFileForGroundService();
        super.onDestroy();
    }

    public void stopUploadFileForGroundService() {
        SharedPreferenceProvider.setUploadProcess(this, false);
        clearDisposable();
        stopForeground(true);
        stopSelf();
    }

    public static void intentToUploadFileForeGroundService(Context context) {
        if (!SharedPreferenceProvider.haveUploadProcess(context)) {
            if (CheckAndroidOVersion.SDKVersionAboveO()) {
                context.startForegroundService(new Intent(context, UploadFileForeGroundService.class));
            } else {
                context.startService(new Intent(context, UploadFileForeGroundService.class));
            }
        }
    }

}

