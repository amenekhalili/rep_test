package ir.fararayaneh.erp.activities.attach_provider;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.annimon.stream.Stream;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.fararayaneh.erp.IBase.common_base.BasePresenter;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.commons.CommonsAttachmentType;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.CompressImageProvider;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.GetB5HCTypeIdQuery;
import ir.fararayaneh.erp.data.data_providers.queries.attach_query.InsertAttachmentsFromClientQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.utils.ContactHelper;
import ir.fararayaneh.erp.utils.VoiceRecorderHelper;
import ir.fararayaneh.erp.utils.data_handler.CheckPermissionHandler;
import ir.fararayaneh.erp.utils.file_helper.FileToUriHelper;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;
import ir.fararayaneh.erp.utils.file_helper.StringUriConvertor;
import ir.fararayaneh.erp.utils.file_helper.WriteToTextFileHelper;
import ir.fararayaneh.erp.utils.intent_handler.CameraIntent;
import ir.fararayaneh.erp.utils.intent_handler.ContactIntentHandler;
import ir.fararayaneh.erp.utils.intent_handler.FileIntentHandler;
import ir.fararayaneh.erp.utils.intent_handler.GalleryIntentHandler;
import ir.fararayaneh.erp.utils.location_handler.ILiveLocationListener;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

import static ir.fararayaneh.erp.commons.Commons.ZERO_STRING;

/**
 * دراین اکتیویتی به صورت پیش فرض یک لوکیشن از یوزر گرفته میشود و برای هر تعداد اتچمنت که در اینجا گرفته شود، همان یک لوکیشن را در جدول اتچمنت ذخیره میشود
 * all data of attachment from this activity saved in
 *              attachNameList
 *              attachUriList >>> deprecated
 *              attachB5HCTypeIdLis
 *              attachColumnIdList
 *
 *  in base activity and no need to any work !
 *
 * here no need to origin Activity row id
 * (example : agar baraye task attachment mizanim, niazi be id ya guid khode task
 * nadarim ama be har hal meghdar an dar mIntentModel.getSomeString2() be in activity miresad) >>> deprecated
 * this activity prepare camera,gallery,record voice,file and contact and return lists of name,
 * uri(stringfy)>>> deprecated
 * ,culomNumber,b5HcTypeId of files
 *
 *
 * name and phone of a contact, will be saved in a txt file and return it
 *
 * this activity get all needed permissions
 * <p>
 * uriRowNumber : show row number of uri from receivedUriArrayList that are in the process
 * <p>
 * if origin activity needs only one attachment , and here prepare more attachment, in before activity use first attachment from list
 */

public class AttachProviderPresenter extends BasePresenter<AttachProviderView> {

    private int uriRowNumber;
    private IAttachProviderListener attachProviderListener;
    private ILiveLocationListener iLiveLocationListener;
    private ArrayList<Uri> receivedUriArrayList;
    //private ArrayList<String> listURIForResult = new ArrayList<>(); deprecated (from file name we can find file uri or url)
    //private ArrayList<String> listFileNameForResult = new ArrayList<>();//filname+suffix = >(14554dfg.mp3)
    //private ArrayList<String> listFileB5HcTypeId = new ArrayList<>();   //---> for all files are equaprivate ArrayList<String> listFileColumnNumber = new ArrayList<>(); //---> for all files are equal
    private IntentModel mIntentModel;
    private String latitude ,longitude;
    

    public AttachProviderPresenter(WeakReference<AttachProviderView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    @Override
    protected UserMessageModel makeProperShowDialogueData(int dialogueType, int CommonComboClickType, String someValue) {
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
    public void click() {

         attachProviderListener = new IAttachProviderListener() {
            @Override
            public void cameraListener() {
                showImageForCrop(cameraVoiceUri, null);
            }

            @Override
            public void goToCompress(SingleEmitter<Uri> emitter, Uri uri) {
                addDisposable(getCompressUri(uri, emitter));
            }


            @Override
            public void URIListener(ArrayList<Uri> uriArrayList, int witchRequestFile) {
                receivedUriArrayList = uriArrayList;
                witchWorkWithUriList(witchRequestFile);
            }

            @Override
            public void voiceListener() {
                handleVoiceUri();
            }

            @Override
            public void confirmListener(String tag, String description) {
                if (checkNull()) {
                    getView().showHideProgress(true);
                    getView().showHideDescriptionLayput(false);
                }
                addDisposable(getB5HCTypeId(tag, description));
            }

            @Override
            public void noResultListener() {
                cancelResultIntent();
            }
        };
        if (checkNull()) {
            getView().setIAttachProviderListener(attachProviderListener);
        }
    }

    @Override
    public void onCreate(IntentModel intentModel) {
        mIntentModel = intentModel;
        addDisposable(getAttachPermissions());
    }



    @Override
    public void onPause() {

    }




    @Override
    protected void ClearAllPresenterListener() {
        attachProviderListener=null;
        iLiveLocationListener=null;
    }

    @Override
    public void onBackPress() {
        cancelResultIntent();
    }

    //----------------------------------------------------------------------->>

    private void witchAttachment(IntentModel intentModel) {
        switch (intentModel.getSomeString()) {
            case CommonsAttachmentType.CAMERA:
                gotoCamera();
                break;
            case CommonsAttachmentType.GALLERY:
                goToGallery();
                break;
            case CommonsAttachmentType.FILE:
                goToFile();
                break;
            case CommonsAttachmentType.VOICE:
                gotoVoice();
                break;
            case CommonsAttachmentType.CONTACT:
                goToContact();
                break;

        }
    }


    private void gotoCamera() {
        if (checkNull()) {
            SharedPreferenceProvider.setCurrentAttachmentName(context, CommonsDownloadFile.IMAGE_SUFFIX_NAME);
            setupCameraVoiceUri();
            CameraIntent.openCamera(getView().getActivity(), cameraVoiceUri);
        }

    }

    private void goToGallery() {
        if (checkNull()) {
            GalleryIntentHandler.intent(getView().getActivity());
        }
    }

    private void goToFile() {
        if (checkNull()) {
            FileIntentHandler.goToFile(getView().getActivity());
        }
    }

    private void gotoVoice() {
        if (checkNull()) {
            SharedPreferenceProvider.setCurrentAttachmentName(context, CommonsDownloadFile.VOICE_SUFFIX_NAME);
            setupCameraVoiceUri();
            Log.i("arash", "gotoVoice: " +MimeTypeHandler.getFilePathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, SharedPreferenceProvider.getCurrentAttachmentName(context)) );
            VoiceRecorderHelper.record(getView().getActivity(), MimeTypeHandler.getFilePathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, SharedPreferenceProvider.getCurrentAttachmentName(context)));
        }
    }

    private void goToContact() {
        if (checkNull()) {
            ContactIntentHandler.intent(getView().getActivity());
        }
    }

    private void witchWorkWithUriList(int witchRequestFile) {
        switch (witchRequestFile) {
            case CommonRequestCodes.FILE:
                addDisposable(handleCopyInFolderUris());
                break;
            case CommonRequestCodes.GALLERY:
                addDisposable(handleCropCompressUris());
                break;
            case CommonRequestCodes.CONTACT:
                handleContacts();
                break;
        }
    }


    //------------------------------------------------------------------------------------------->>>
    private void showImageForCrop(Uri uri, final SingleEmitter<Uri> emitter) {
        if (checkNull()) {
            getView().showHideCropImageView(emitter, uri, true);
        }
    }

    /**
     * cropped uri are the same compressed uri
     * cropped file name are the same compressed fileName
     */
    private Disposable getCompressUri(Uri croppedUri, SingleEmitter<Uri> emitter) {
        Log.i("arash", "getCompressUri: ");
        CompressImageProvider compressImageProvider = (CompressImageProvider) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.COMPRESS_IMAGE_PROVIDER);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setUri(croppedUri);
        globalModel.setContext(context);
        assert compressImageProvider != null;
        return compressImageProvider.data(globalModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    addToResultList(StringUriConvertor.uriToString(croppedUri), SharedPreferenceProvider.getCurrentAttachmentName(context));
                    if (emitter != null && !emitter.isDisposed()) {
                        //the uri in onSuccess , not used in  anywhere
                        emitter.onSuccess(croppedUri);
                    } else {
                        //we sure that when emitter is null, we have only one image for process
                        getTagDescription();
                    }
                }, throwable -> {
                    if (emitter != null && !emitter.isDisposed()) {
                        emitter.onError(throwable);
                    }
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_1);
                    }
                    ThrowableLoggerHelper.logMyThrowable("AttachProviderPresenter/getCompressUri**" + throwable);
                    cancelResultIntent();
                });
    }

    //------------------------------------------------------------------------------------------->>>
    private Disposable handleCopyInFolderUris() {

        return Single.create((SingleOnSubscribe<Uri>) emitter -> {
            SharedPreferenceProvider.setCurrentAttachmentName(context, MimeTypeHandler.getFileSuffixFromURI(context,receivedUriArrayList.get(uriRowNumber)));
            copyUriInMyFolder(receivedUriArrayList.get(uriRowNumber), emitter);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    if (uriRowNumber != receivedUriArrayList.size() - 1) {
                        uriRowNumber++;
                        addDisposable(handleCopyInFolderUris());
                    } else {
                        getTagDescription();
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_2);
                    }
                    cancelResultIntent();
                    ThrowableLoggerHelper.logMyThrowable("AttachProviderPresenter**handleCopyInFolderUris/" + throwable.getMessage() + "***" + throwable.toString());
                });
    }

    /**
     * should subscribeOn(AndroidSchedulers.mainThread())
     * because the image view can be accessed
     * cropping and compressing image were done in another tread
     */
    private Disposable handleCropCompressUris() {

        return Single.create((SingleOnSubscribe<Uri>) emitter -> {
            //SharedPreferenceProvider.setCurrentAttachmentName(context, MimeTypeHandler.getFileSuffixFromUri(context,StringUriConvertor.uriToString(receivedUriArrayList.get(uriRowNumber))));
            SharedPreferenceProvider.setCurrentAttachmentName(context, MimeTypeHandler.getFileSuffixFromURI(context,receivedUriArrayList.get(uriRowNumber)));
            showImageForCrop(receivedUriArrayList.get(uriRowNumber), emitter);
        }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    if (uriRowNumber != receivedUriArrayList.size() - 1) {
                        uriRowNumber++;
                        addDisposable(handleCropCompressUris());
                    } else {
                        getTagDescription();
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_3);
                    }
                    cancelResultIntent();
                    ThrowableLoggerHelper.logMyThrowable("AttachProviderPresenter**handleCropCompressUris/" + throwable.getMessage() + "***" + throwable.toString());
                });
    }

    /**
     * we have only one contact
     * write name and phone number to txt file, and return text file
     */
    private void handleContacts() {
        SharedPreferenceProvider.setCurrentAttachmentName(context,CommonsDownloadFile.TEXT_SUFFIX_NAME);
        addToResultList(StringUriConvertor.uriToString(FileToUriHelper.getUri(WriteToTextFileHelper.CreateAndWrite(SharedPreferenceProvider.getCurrentAttachmentName(context),String.format(CommonsFormat.CONTACT_FORMAT,ContactHelper.getContactName(context,receivedUriArrayList.get(0)),ContactHelper.getContactPhoneNumber(context,receivedUriArrayList.get(0)))),context)),SharedPreferenceProvider.getCurrentAttachmentName(context));
        getTagDescription();
    }

    /**
     * we have only one record audio
     */
    private void handleVoiceUri() {
        addToResultList(StringUriConvertor.uriToString(cameraVoiceUri), SharedPreferenceProvider.getCurrentAttachmentName(context));
        getTagDescription();
    }
    //------------------------------------------------------------------------------------------->>>

    private Disposable getAttachPermissions() {
        return CheckPermissionHandler.getAttachPermissions(context)
                .subscribe(tedPermissionResult -> {
                    if (!tedPermissionResult.isGranted()) {
                        cancelResultIntent();
                    } else {
                        getUserLocation();
                    }
                }, throwable -> {
                    ThrowableLoggerHelper.logMyThrowable("AttachProviderPermission/getAttachPermissions***" + throwable.getMessage());
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_4);
                    }
                });
    }

    private void getUserLocation() {
        iLiveLocationListener = new ILiveLocationListener() {
            @Override
            public void getLocation(Location location) {
                setProperLocation(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
            }

            @Override
            public void getError(String error) {
                setProperLocation(ZERO_STRING,ZERO_STRING);
            }

            @Override
            public void forbiddenApp() {
                setProperLocation(ZERO_STRING,ZERO_STRING);
            }

            @Override
            public void mockLocation() {
                setProperLocation(ZERO_STRING,ZERO_STRING);
            }
        };
        getUserLiveLocation(null,iLiveLocationListener);

    }

    private void setProperLocation(String lat, String longi) {
        latitude = lat;
        longitude = longi;
        witchAttachment(mIntentModel);
    }

    private void copyUriInMyFolder(Uri uri, SingleEmitter<Uri> emitter) {
        //because we call getFile , no need to create folder
        addToResultList(StringUriConvertor.uriToString(FileToUriHelper.getUri(FileToUriHelper.getFile(context, CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, SharedPreferenceProvider.getCurrentAttachmentName(context), uri), context)), SharedPreferenceProvider.getCurrentAttachmentName(context));
        if (!emitter.isDisposed()) {
            //the uri in onSuccess , not used in  anywhere
            emitter.onSuccess(uri);
        }
    }

    private void addToResultList(String fileUri, String fileName) {
        //listURIForResult.add(fileUri); deprecated
        //listFileNameForResult.add(fileName);
        attachNameList.add(fileName);
    }

    private void getTagDescription() {
        if (checkNull()) {
            getView().showHideProgress(false);
            getView().showHideCropImageView(null, null, false);
            getView().showHideDescriptionLayput(true);
        }
    }


    private Disposable getB5HCTypeId(String tag, String description) {

        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(mIntentModel.getSomeString3());
        GetB5HCTypeIdQuery getB5HCTypeIdQuery = (GetB5HCTypeIdQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_B5HCTYPEID_QUERY);
        assert getB5HCTypeIdQuery != null;
        return getB5HCTypeIdQuery.data(globalModel)
                .subscribe(iModels -> addDisposable(saveResult(tag, description, globalModel.getMyString())), throwable -> {
                    if (checkNull()) {
                        getView().showHideProgress(false);
                        getView().showHideDescriptionLayput(true);
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_5);
                    }
                });

    }

    private Disposable saveResult(String tag, String description, String b5HcTypeId) {

        
        GlobalModel globalModel = new GlobalModel();

        globalModel.setMyString2(tag);
        globalModel.setMyString3(description);
        globalModel.setMyString4(latitude);
        globalModel.setMyString5(longitude);
        globalModel.setStringArrayList(attachNameList);

        //globalModel.setMyString(mIntentModel.getSomeString2());
        //globalModel.setMyInt(Commons.DEFAULT_COLUMN_NUMBER);
        //globalModel.setMyInt2(b5HcTypeId);

        setListFileB5hcTypeId(attachNameList,b5HcTypeId);
        setListFileColumnNumber(attachNameList);

        InsertAttachmentsFromClientQuery insertAttachmentsFromClientQuery =
                (InsertAttachmentsFromClientQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_ATTACHMENTS_FROM_CLIENT_QUERY);
        assert insertAttachmentsFromClientQuery != null;
        return insertAttachmentsFromClientQuery.data(globalModel).subscribe(iModels -> goSendBackResultForAttach(), throwable -> {
            if(checkNull()){
                getView().showHideProgress(false);
                getView().showHideDescriptionLayput(true);
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_7);
            }

        });
    }

    private void setListFileB5hcTypeId(ArrayList<String> listFileNameForResult,String b5HcTypeId) {
        Stream.of(listFileNameForResult).forEach(s -> attachB5HCTypeIdList.add(b5HcTypeId));
    }

    private void setListFileColumnNumber(ArrayList<String> listFileNameForResult) {
        Stream.of(listFileNameForResult).forEach(s -> attachColumnIdList.add(CommonsDownloadFile.DEFAULT_COLUMN_NUMBER_IN_STRING));
    }


}
