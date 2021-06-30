package ir.fararayaneh.erp.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonUrls;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.online.DownloadFileProvider;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.middle.AttachmentDownloadModel;
import ir.fararayaneh.erp.utils.NotificationHelper;
import ir.fararayaneh.erp.utils.data_handler.CheckPermissionHandler;
import ir.fararayaneh.erp.utils.file_helper.CreateFolderFileHandler;
import ir.fararayaneh.erp.utils.file_helper.DeleteFileHelper;
import ir.fararayaneh.erp.utils.file_helper.FileToUriHelper;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;
import ir.fararayaneh.erp.utils.intent_handler.FileIntentHandler;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.package_handler.CheckAndroidOVersion;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;

import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_DOWNLOAD_APK_NOTIFICATIONS_ID;

/**
 *  for foreground service :
 *  1-add permission and change call intentToSocketService
 *  only use for download APK
 *  or
 *  uploadFile
 *  no need to check unSync data, because realm migration
 */
public class DownLoadAPKForGroundService extends Service {

    private CompositeDisposable compositeDisposable;

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
     * FOREGROUND_DOWNLOAD_APK_NOTIFICATIONS_ID != 0
     * notify priority should be greater than low
     */
    private void setupForegroundNotify() {
        /* after maximum 5 second, this code should be ran*/
        startForeground(FOREGROUND_DOWNLOAD_APK_NOTIFICATIONS_ID, NotificationHelper.setForeGroundDownloadAPKNotification(this));
        if(compositeDisposable==null){
            startProcess();
        }
    }

    private void startProcess() {
        createDisposable();
        if(DeleteFileHelper.delete(MimeTypeHandler.getFilePathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME,CommonsDownloadFile.APK_FILE_NAME))){
        addDisposable(getFilePermission());
        } else {
            ToastMessage.nonUiToast(this,getResources().getString(R.string.delete_apk));
            stopDownLoadAPKForGroundService();
        }
    }

    private Disposable getFilePermission() {

        return CheckPermissionHandler.getFilePermissions(this)
                .subscribe(tedPermissionResult -> {
                    if (tedPermissionResult.isGranted()) {
                        addDisposable(downloadApk());
                    } else {
                        ToastMessage.nonUiToast(this,this.getResources().getString(R.string.not_application_have_access));
                        stopDownLoadAPKForGroundService();
                    }
                }, throwable -> {
                    ThrowableLoggerHelper.logMyThrowable(throwable.getMessage()+"/DownloadApkForeGroundService/getFilePermission");
                    ToastMessage.nonUiToast(this, CommonsLogErrorNumber.error_84 +this.getResources().getString(R.string.some_problem_error));
                    stopDownLoadAPKForGroundService();
                });
    }

    private Disposable downloadApk() {

        DownloadFileProvider downloadFileProvider=(DownloadFileProvider) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.DOWNLOAD_FILE_PROVIDER);
        assert downloadFileProvider != null;
        return downloadFileProvider.data(
                new AttachmentDownloadModel(SharedPreferenceProvider.getNodeIp(App.getAppContext())+CommonUrls.APK_DOWNLOAD_URL_END_POINT
                        ,MimeTypeHandler.getFolderPathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME)
                        ,CommonsDownloadFile.APK_FILE_NAME
                        ,CommonsDownloadFile.APK_FILE_NAME
                ))
                .subscribe(() -> {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> {
                        FileIntentHandler.openExistFile(App.getAppContext(), FileToUriHelper.getUri(CreateFolderFileHandler.createFile(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, CommonsDownloadFile.APK_FILE_NAME), App.getAppContext()));
                        stopDownLoadAPKForGroundService();
                        Log.i("arash", "downloadApk: done");
                    });
                }, throwable -> {
                    ThrowableLoggerHelper.logMyThrowable(throwable.getMessage()+"/DownLoadAPKForGroundService/downloadApk");
                    ToastMessage.nonUiToast(this,getResources().getString(R.string.some_problem_error));
                    stopDownLoadAPKForGroundService();
                });
    }

    private void createDisposable() {
        if(compositeDisposable==null){
            compositeDisposable = new CompositeDisposable();
        }
    }

    private void addDisposable( Disposable disposable) {
        if(compositeDisposable!=null)
            compositeDisposable.add(disposable);
    }

    private void clearDisposable() {
        if(compositeDisposable!=null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void onDestroy() {
        stopDownLoadAPKForGroundService();
        super.onDestroy();
    }

    public  void stopDownLoadAPKForGroundService() {
        clearDisposable();
        stopForeground(true);
        stopSelf();
    }

    // we no need to check that service is ran
    public static void intentToDownLoadAPKForGroundService(Context context) {
        if (CheckAndroidOVersion.SDKVersionAboveO()) {
            context.startForegroundService(new Intent(context, DownLoadAPKForGroundService.class));
        } else {
            context.startService(new Intent(context, DownLoadAPKForGroundService.class));
        }
    }

}
