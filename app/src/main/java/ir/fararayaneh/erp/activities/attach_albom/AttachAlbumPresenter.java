package ir.fararayaneh.erp.activities.attach_albom;

import android.content.Context;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.IBase.common_base.BasePresenterWithRecycle;
import ir.fararayaneh.erp.adaptors.attach_album_adaptor.AttachAlbumRecAdaptor;
import ir.fararayaneh.erp.adaptors.attach_album_adaptor.IClickAttachAlbumRecAdaptor;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.offline.AttachAlbumListDataProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.middle.AttachmentDownloadModel;
import ir.fararayaneh.erp.data.models.middle.DownloadState;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.utils.data_handler.AttachJsonCreator;
import ir.fararayaneh.erp.utils.data_handler.DownloadFileDataHandler;
import ir.fararayaneh.erp.utils.file_helper.CreateFolderFileHandler;
import ir.fararayaneh.erp.utils.file_helper.FileToUriHelper;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;
import ir.fararayaneh.erp.utils.intent_handler.FileIntentHandler;

/**
 * --------------------------------------------------------------------------
 * intent model for come to this activity :
 * canUserAddAttach
 * canUserDeleteAttach
 * attach :  {[attachmentGUID+suffix(fileName),columnNumber,B5HCTypeId],...} (AttachmentJsonModel.class)
 * --------------------------------------------------------------------------
 * and intent model from this activity is:
 * <p>
 * attachNameList;
 * attachColumnIdList
 * attachB5HCTypeIdList
 * <p>
 * <p>
 * in origin activity ,if we listen to result
 * (example : in report activity, we can not edit, there fore we do not listen
 * to result from this activity),
 * we remove all old value of above lists, and paste above values
 * (in basePresenter  : resetAttachDataLists() )
 * <p>
 * tag for download is file name here
 */

public class AttachAlbumPresenter extends BasePresenterWithRecycle<AttachAlbumView> {

    private AttachAlbumRecAdaptor attachAlbumRecAdaptor = new AttachAlbumRecAdaptor(context);
    private IAttachAlbumListener iAttachAlbumListener;
    private IClickAttachAlbumRecAdaptor iClickAttachAlbumRecAdaptor;
    private boolean canUserAddAttach, canUserDeleteAttach;

    public AttachAlbumPresenter(WeakReference<AttachAlbumView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    @Override
    protected UserMessageModel makeProperShowDialogueData(int dialogueType, int commonComboClickType, String someValue) {
        return null;
    }

    @Override
    protected void workForListenToMSGOrDialoge(int whichDialog, int whichClick, IModels iModels) {

    }

    @Override
    protected void updateUIWhenReceivedAttachment() {
    }

    @Override
    protected void workForSocketListener(ISocketModel iSocketModel) {
        //todo
    }

    @Override
    protected void ClearAllPresenterListener() {
        iAttachAlbumListener = null;
        iClickAttachAlbumRecAdaptor = null;
    }

    @Override
    public void click() {
        iAttachAlbumListener = new IAttachAlbumListener() {
            @Override
            public void clickBackToolbar() {
                cancelResultIntent();
            }

            @Override
            public void clickImageAddToolbar() {
                if (canUserAddAttach) {
                    if (checkNull()) {
                        getView().showBottomSheet(true, true, true, true, false, true);
                    }
                } else {
                    if (checkNull()) {
                        getView().showMessageNoPermission();
                    }
                }
            }

            @Override
            public void clickImageSaveToolbar() {
                //if user can edit or not , can save result
                goSendBackResultForAttach();
            }

            @Override
            public void receivedNewAttachFromAttachProvider() {
                //here all attachment list were be updated and we need to call adaptor from first
                setDataAttachAlbumsList();
            }
        };
        if (checkNull()) {
            getView().setiAttachAlbumListener(iAttachAlbumListener);
        }

        iClickAttachAlbumRecAdaptor = new IClickAttachAlbumRecAdaptor() {
            @Override
            public void clickRowToShowByIntent(int position) {
                if (attachNameList.size() >= position) {
                    FileIntentHandler.openExistFile(context, FileToUriHelper.getUri(CreateFolderFileHandler.createFile(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, attachNameList.get(position)), context));
                }
            }

            @Override
            public void clickRowToStartDownload(int position) {
                if (attachNameList.size() >= position) {

                    addDisposable(downloadFile(attachNameList.get(position), attachNameList.get(position)));
                }
            }

            @Override
            public void clickRowToCancelDownload(int position) {
                if (attachNameList.size() >= position) {
                    DownloadFileDataHandler.cancelDownloadWithTag(attachNameList.get(position));
                }
            }

            @Override
            public void clickRowToDeleteAttach(int position) {
                //if user
                if (attachNameList.size() >= position) {
                    DownloadFileDataHandler.cancelDownloadWithTag(attachNameList.get(position));
                    //should be after cancel download
                    clearAttachList(position);
                }
            }
        };
        attachAlbumRecAdaptor.setiClickAttachAlbumRecAdaptor(iClickAttachAlbumRecAdaptor);

    }


    @Override
    protected void setRecyclAdaptor() {
        getView().setRecycleAdaptor(attachAlbumRecAdaptor);
    }

    @Override
    public void onCreate(IntentModel intentModel) {
        setupPrimitiveData(intentModel);//should be before super
        super.onCreate(intentModel);
    }

    private void setupPrimitiveData(IntentModel intentModel) {
        canUserAddAttach = intentModel.isCanUserAddAttach();
        canUserDeleteAttach = intentModel.isCanUserRemoveAttach();
        String attach = intentModel.getSomeString();
        attachNameList = AttachJsonCreator.getAttachmentNameList(attach);
        attachColumnIdList = AttachJsonCreator.getAttachColumnIdList(attach);
        attachB5HCTypeIdList = AttachJsonCreator.getAttachB5HCTypeIdList(attach);
    }

    @Override
    protected void workForLoadPage() {
        setDataAttachAlbumsList();
    }

    private void setDataAttachAlbumsList() {
        AttachAlbumListDataProvider albumListDataProvider = (AttachAlbumListDataProvider) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.ATTACH_ALBUM_LIST_DATA_PROVIDER);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyBoolean(canUserDeleteAttach);
        globalModel.setStringArrayList(attachNameList);
        assert albumListDataProvider != null;
        attachAlbumRecAdaptor.setDataModelList(albumListDataProvider.data(globalModel));
        if (checkNull()) {
            getView().showHideProgress(false);
        }
    }

    @Override
    protected void setLazyLoad() {
        if (checkNull()) {
            getView().loadAtEndList(() -> {
                //no need to scrolling
            });
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onBackPress() {

        cancelResultIntent();
    }

    //----------------------------------------------------------------------------------------------

    /**
     * when user delete a row , no need to clear from attachment table,
     * because may be other object have this attachment
     * <p>
     * no need to update recycle, this work were be done before here in adaptor
     */
    private void clearAttachList(int position) {

        if (attachNameList.size() >= position) {
            attachNameList.remove(position);
            attachB5HCTypeIdList.remove(position);
            attachColumnIdList.remove(position);
        }
    }


    /**
     * @param downloadTag : is fileName in here
     *                    no need to check permission
     */
    private Disposable downloadFile(String fileName, String downloadTag) {

        DownloadFileDataHandler.addToDownloadList(new AttachmentDownloadModel(MimeTypeHandler.getURLFromName(fileName)
                , MimeTypeHandler.getFolderPathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME)
                , fileName
                , downloadTag), true);

        return App.getFinishDownloadFlowAble().subscribe(fileName1 -> {
            //if we have this tag, update my recycler
            if (attachNameList.contains(fileName1)) {
                attachAlbumRecAdaptor.changeDownloadStateList(attachNameList.indexOf(fileName1), DownloadState.DOWNLOAD_COMPLETE);
            }
        });

    }
}
