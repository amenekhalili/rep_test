package ir.fararayaneh.erp.utils.data_handler;



import androidx.annotation.Nullable;
import com.androidnetworking.AndroidNetworking;
import com.gun0912.tedpermission.TedPermissionResult;
import java.util.Objects;
import io.reactivex.functions.Consumer;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.online.DownloadFileProvider;
import ir.fararayaneh.erp.data.models.middle.AttachmentDownloadModel;
import ir.fararayaneh.erp.utils.file_helper.CheckHaveFile;
import ir.fararayaneh.erp.utils.file_helper.CreateFolderFileHandler;
import ir.fararayaneh.erp.utils.file_helper.DeleteFileHelper;
import ir.fararayaneh.erp.utils.file_helper.FileSizeHelper;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;


/**
 * all download were be done here in one by one manner
 */
public class DownloadFileDataHandler {

    public static void addToDownloadList(AttachmentDownloadModel attachmentDownloadModel, boolean addToMyList) {

        CheckPermissionHandler.getFilePermissions(App.getAppContext()).subscribe(new Consumer<TedPermissionResult>() {
            @Override
            public void accept(TedPermissionResult tedPermissionResult) throws Exception {
                if(tedPermissionResult.isGranted()){
                    if(addToMyList){
                        addToList(attachmentDownloadModel);
                    }

                    if(App.getCancelDownloadTagList().contains(attachmentDownloadModel.getDownloadTag())){
                        App.removeFromCancellDownloadTagList(attachmentDownloadModel.getDownloadTag());
                    }

                    if (!App.oneDownloadIsInProgress) {
                        App.oneDownloadIsInProgress = true;


                        DownloadFileProvider downloadFileProvider = (DownloadFileProvider) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.DOWNLOAD_FILE_PROVIDER);
                        assert downloadFileProvider != null;
                        downloadFileProvider.data(attachmentDownloadModel)
                                .subscribe(() -> goForNextDownload(attachmentDownloadModel), throwable -> {
                                    ThrowableLoggerHelper.logMyThrowable(throwable.getMessage() + "/DownloadFileDataHandler/addToDownloadList");
                                    goForNextDownload(null);
                                });
                    }
                } else {
                    ToastMessage.nonUiToast(App.getAppContext(),App.getAppContext().getResources().getString(R.string.not_application_have_access));
                }
            }
        }, throwable -> {
            ThrowableLoggerHelper.logMyThrowable(throwable.getMessage()+"/DownloadFileDataHandler/getFilePermission");
            ToastMessage.nonUiToast(App.getAppContext(), CommonsLogErrorNumber.error_85 +App.getAppContext().getString(R.string.some_problem_error));
        });

    }

    private static void goForNextDownload(@Nullable AttachmentDownloadModel attachmentDownloadModel) {
        if (App.getFinishDownloadEmitter() != null && attachmentDownloadModel!=null ) {
            App.getFinishDownloadEmitter().onNext(Objects.requireNonNull(attachmentDownloadModel).getDownloadTag());
        }
            App.oneDownloadIsInProgress = false;

        if (App.downloadListIterator.hasNext()) {
                AttachmentDownloadModel nowAttachmentDownloadModel = App.downloadListIterator.next();
                if(App.getCancelDownloadTagList().contains(nowAttachmentDownloadModel.getDownloadTag())){
                    App.removeFromCancellDownloadTagList(nowAttachmentDownloadModel.getDownloadTag());
                    goForNextDownload(null);
                } else {
                    DownloadFileDataHandler.addToDownloadList(nowAttachmentDownloadModel,false);
                }
            }
    }

    private static void addToList(AttachmentDownloadModel attachmentDownloadModel) {
            App.downloadListIterator.add(attachmentDownloadModel);
    }

    /**
     * no need to call next, in throwable go next automatically
     */
    public static void cancelDownloadWithTag(String tag){
        AndroidNetworking.forceCancel(tag);
        App.addToCancellDownloadTagList(tag);
    }
}
