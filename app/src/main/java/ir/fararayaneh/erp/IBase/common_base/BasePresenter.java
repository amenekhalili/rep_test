package ir.fararayaneh.erp.IBase.common_base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.Log;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsAttachmentType;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.commons.CommonsFormCode;
import ir.fararayaneh.erp.data.data_providers.offline.SetFormCodeToIntentModel;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.online.request.rest.RestRequestModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.services.ISocketListener;
import ir.fararayaneh.erp.services.SocketService;
import ir.fararayaneh.erp.services.UploadFileForeGroundService;
import ir.fararayaneh.erp.utils.PingHandler;
import ir.fararayaneh.erp.utils.data_handler.AttachJsonCreator;
import ir.fararayaneh.erp.utils.data_handler.CheckPermissionHandler;
import ir.fararayaneh.erp.utils.file_helper.CreateFolderFileHandler;
import ir.fararayaneh.erp.utils.file_helper.FileToUriHelper;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntentFactoryHandler;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.intent_handler.IntentReceiverHandler;
import ir.fararayaneh.erp.utils.location_handler.ILiveLocationListener;
import ir.fararayaneh.erp.utils.location_handler.MockLocationHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.package_handler.HaveSomeAppsHandler;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.MessageListener;
import ir.fararayaneh.erp.utils.waiting.IWaitingHandler;
import mumayank.com.airlocationlibrary.AirLocation;


public abstract class BasePresenter<V extends BaseView> extends InjectorToBasePresenter implements IWeekViews<V>, IPresenter, IDisposable, IClick, IPermission {


    protected Context context;
    private final CompositeDisposable compositeDisposable;
    private WeakReference<V> weekView;
    public boolean shouldHaveIntent;
    protected Uri cameraVoiceUri;
    protected AirLocation airLocation;
    private AirLocation.Callback airCallbacks;
    private IShowMessageDialogueListener iShowMessageDialogueListener;
    private ISendAttachmentToPersenter iSendAttachmentToPersenter;
    private IWaitingHandler iWaitingHandler;
    private IBottomSheetClickListener iBottomSheetClickListener;
    private ISendBarCodeToPersenter iSendBarCodeToPersenter;
    private MessageListener messageListener;

    //-------------------------------------------------------------------------------
    /**
     * if we have any attachment , should save here, after we come back from attach provider activity
     * in attach provider and attach album, use this parameter too
     **/
    //protected ArrayList<String> attachUriList = new ArrayList<>(); deprecated
    protected ArrayList<String> attachNameList;
    protected ArrayList<String> attachB5HCTypeIdList = new ArrayList<>();
    protected ArrayList<String> attachColumnIdList = new ArrayList<>();
    //-------------------------------------------------------------------------------
    protected String barCode = Commons.NULL;
    //-------------------------------------------------------------------------------

    public BasePresenter(WeakReference<V> weekView, Context context, boolean shouldHaveIntent) {
        this.weekView = weekView;
        this.context = context;
        this.shouldHaveIntent = shouldHaveIntent;
        compositeDisposable = new CompositeDisposable();
        attachNameList = new ArrayList<>();
    }

    protected abstract UserMessageModel makeProperShowDialogueData(int dialogueType, int commonComboClickType, String someValue);

    protected abstract void workForListenToMSGOrDialoge(int whichDialog, int whichClick, IModels iModels);


    @Override
    public V getView() {
        if (weekView == null)
            return null;
        else
            return weekView.get();
    }

    @Override
    public boolean checkNull() {
        return getView() != null;
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (compositeDisposable != null && disposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void onStart() {
        setShowMessageListener();
        setBottomSheetListener();
        setAttachmentListener();
        setBarCodeListener();
        click();
        App.addToAppPowerOnList();
        statusBarNotifManager(true);
    }

    @Override
    public void onResume() {
        addDisposable(subscribeSocketListener());
    }

    private void setShowMessageListener() {
        if (checkNull()) {
            iShowMessageDialogueListener = new IShowMessageDialogueListener() {
                @Override
                public void sendShowMsgToPresenter(String msg) {
                    workShowMessage(msg);
                }

                @Override
                public void sendShowDialogToPresenter(int dialogueType, int commonComboClickType, String someValue) {
                    workShowDialogue(dialogueType, commonComboClickType, someValue);
                }
            };
            getView().setiShowMessageDialogueListener(iShowMessageDialogueListener);
            //unRegisterListener.add(iShowMessageDialogueListener);
        }
    }

    private void setAttachmentListener() {
        if (checkNull()) {
            iSendAttachmentToPersenter = new ISendAttachmentToPersenter() {
                @Override
                public void getAttachmentFromViewAttachProvider(Intent data) {
                    if (data.getExtras() != null) {
                        addAttachDataToListsAttachProvider(data);
                        updateUIWhenReceivedAttachment();
                    }
                }

                @Override
                public void getAttachmentFromViewAttachAlbum(Intent data) {
                    if (data.getExtras() != null) {
                        resetAttachDataListsAttachAlbum(data);
                        updateUIWhenReceivedAttachment();
                    }
                }
            };

            getView().setiSendAttachmentToPersenter(iSendAttachmentToPersenter);
            //unRegisterListener.add(iSendAttachmentToPersenter);
        }
    }

    private void setBarCodeListener() {

        if (checkNull()) {
            iSendBarCodeToPersenter = data -> {
                if (data != null) {
                    if (data.getExtras() != null) {
                        setReceivedBarcode(data);
                    }
                }
            };
            getView().setiSendBarCodeToPersenter(iSendBarCodeToPersenter);
            //unRegisterListener.add(iSendBarCodeToPersenter);
        }
    }

    /**
     * this method is come from attachProvider and update all lists
     */
    private void addAttachDataToListsAttachProvider(Intent data) {

        IntentModel intentModel = IntentReceiverHandler.
                getIntentData(Objects.requireNonNull(data.getExtras()));
        for (int i = 0; i < intentModel.getStringList2().size(); i++) {
            //attachUriList.add(intentModel.getStringList().get(i)); deprecated
            attachNameList.add(intentModel.getStringList2().get(i));
            attachB5HCTypeIdList.add(intentModel.getStringList3().get(i));
            attachColumnIdList.add(intentModel.getStringList4().get(i));
        }


    }

    protected abstract void updateUIWhenReceivedAttachment();

    /**
     * this method is come from attachAlbum and reset all lists
     */
    private void resetAttachDataListsAttachAlbum(Intent data) {

        attachNameList.clear();
        attachB5HCTypeIdList.clear();
        attachColumnIdList.clear();

        IntentModel intentModel = IntentReceiverHandler.
                getIntentData(Objects.requireNonNull(data.getExtras()));
        for (int i = 0; i < intentModel.getStringList2().size(); i++) {
            //attachUriList.add(intentModel.getStringList().get(i)); deprecated
            attachNameList.add(intentModel.getStringList2().get(i));
            attachB5HCTypeIdList.add(intentModel.getStringList3().get(i));
            attachColumnIdList.add(intentModel.getStringList4().get(i));
        }
    }

    /**
     * in method protected ast chon dar presenter haye estefade konande
     * bayad betavanad befahmad che zamani data daryaft shode ast
     */
    protected void setReceivedBarcode(Intent data) {

        IntentModel intentModel = IntentReceiverHandler.
                getIntentData(Objects.requireNonNull(data.getExtras()));
        barCode = intentModel.getSomeString();
    }


    private void workShowMessage(String msg) {
        userMessageModel.setMessage(msg);
        messageListener = this::workForListenToMSGOrDialoge;
        msgStrategy.MsgStrategy(this, snakMsg, userMessageModel, messageListener);
        //unRegisterListener.add(messageListener);
    }

    private void workShowDialogue(int dialogueType, int commonComboClickType, String someValue) {
        dialogeMessage.setDialogeType(dialogueType);
        messageListener = this::workForListenToMSGOrDialoge;
        msgStrategy.MsgStrategy(this, dialogeMessage, makeProperShowDialogueData(dialogueType, commonComboClickType, someValue), messageListener);
        //unRegisterListener.add(messageListener);
    }

    protected UserMessageModel makeProperUserMessageModel(String message, String title, String otherData) {
        userMessageModel.setMessage(message);
        userMessageModel.setTitle(title);
        userMessageModel.setOtherData(otherData);
        return userMessageModel;
    }

    private Disposable subscribeSocketListener() {
        return bufferSockets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::workForSocketListener,
                        throwable -> ThrowableLoggerHelper.logMyThrowable(throwable.getMessage() + "Base presenter/**subscribeSocketListener"));
    }

    private Flowable<ISocketModel> bufferSockets() {
        return Flowable.create(this::setSocketListener, BackpressureStrategy.BUFFER);
    }

    private void setSocketListener(final FlowableEmitter<ISocketModel> emitter) {
        SocketService.setSocketlistener(new ISocketListener() {
            @Override
            public void listener(ISocketModel value) {
                emitter.onNext(value);
            }

            @Override
            public void SocketISConnect() {
                statusBarNotifManager(false);
            }

            @Override
            public void SocketIsDisConnect() {
                statusBarNotifManager(true);
            }
        });

    }

    protected abstract void workForSocketListener(ISocketModel iSocketModel);

    private void statusBarNotifManager(boolean showHide){
        if(checkNull()){
            if(!showHide || PingHandler.checkPingNode()) {
                getView().hideStatusBarMessage();
            }else if(!SharedPreferenceProvider.getUserId(context).equals(Commons.NULL_INTEGER)) {
                //in first installation we do not show this message
                getView().showStatusBarMessage();
            }
        }

    }

    //no need to null listener in on stop 25/02/98
    @Override
    public void onStop() {
        //clearDisposable();
        App.removeFromAppPowerOnList();
        statusBarNotifManager(false);
    }

    @Override
    public void onDestroy() {
        clearDisposable();
        if (checkNull()) {
            getView().clearAllClicksListener();
        }
        airCallbacks = null;

    }

    // TODO: 2/3/2019 تمام لیستنر هایی که در پرزنتر خارج از متد کلیک ، ست شوند با رفتن به استپ پاک میشوند و دوباره ست نمیشوند
    @Override
    public void clearDisposable() {
        //todo agar be stop raft vali be start raft digar az rest data nemigirad
        // ,chon data dar on creat migirad,
        // aya moshkel ijad mikonad ?
        // ama socket va listener haye click dar onstart dobare meghdar dehi mishavad,
        // TODO: 12/11/2018 make socket listener null
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        //chon destroy act aval bad az onstart act
        // dovom rokh midahad, meghdar dehi be socket hatman bayad dar
        // onresume bashad, va niaz nist inja null shavad, chon hatman ba meghdar dehi dar act dovom,
        //act aval khali shode ast

        //SocketService.setSocketlistener(null);

        iShowMessageDialogueListener = null;
        iSendAttachmentToPersenter = null;
        iWaitingHandler = null;
        iBottomSheetClickListener = null;
        iSendBarCodeToPersenter = null;
        messageListener = null;
        ClearAllPresenterListener();

        /*if (unRegisterListener != null) {
            unRegisterListener.unRegistersAll();
        }*/
    }

    protected abstract void ClearAllPresenterListener();

    @Override
    public void workForPermissionResults(int requestCode, @NonNull int[] grantResults) {
        if (checkNull()) {
            getView().workForPermissionResults(requestCode, grantResults);
        }
        workForLiveLocationPermission(requestCode, grantResults);
    }


    void workForActivityResult(int requestCode, int resultCode, Intent data) {
        if (checkNull()) {
            getView().workForActivityResult(requestCode, resultCode, data);
        }
        workForLiveLocationResult(requestCode, resultCode, data);
    }


    @Override
    public void onSaveInstantstate(Bundle outState) {
        outState.putParcelable(Commons.SAVE_INSTANCE_BUNDLE, cameraVoiceUri);
    }

    @Override
    public void onRestoreInstantstate(Bundle savedInstanceState) {
        cameraVoiceUri = savedInstanceState.getParcelable(Commons.SAVE_INSTANCE_BUNDLE);
    }

    //----------------------------------------------------------------->
    //----------------------------------------------------------------->
    //----------------------------------------------------------------->
    protected void goMainActivityAndFinish() {
        ActivityIntents.goMainActivity(context);
        if (checkNull()) {
            getView().finish();
        }
    }

    protected void resultFromUpdateTableAct() {
        if (checkNull()) {
            ActivityIntents.resultOkFromUpdateTableAct(getView().getActivity());
        }
    }

    protected void cancelResultIntent() {
        if (checkNull()) {
            ActivityIntents.cancelResultIntent(getView().getActivity());
            getView().getActivity().finish();
        }
    }

    protected void goUpdateTableAct(List<RestRequestModel> requestModelList, ArrayList<Class> classArrayListForUpdate) {
        if (checkNull()) {
            ActivityIntents.goUpdateTableActForResult(getView().getActivity(), requestModelList, classArrayListForUpdate);
        }
    }

    protected void goSyncAct(ArrayList<String> kindForSync) {
        if (checkNull()) {
            ActivityIntents.goSyncActForResult(getView().getActivity(), kindForSync);
        }
    }

    protected void goSyncActForExpireFinancialDate(ArrayList<String> kindForSync) {
        if (checkNull()) {
            ActivityIntents.goSyncActExpiredFinancialDateForResult(getView().getActivity(), kindForSync);
        }
    }

    protected void goToCartableActivity() {
        if (checkNull()) {
            ActivityIntentFactoryHandler.goToProperActivity(getView().getActivity(), CommonsFormCode.CARTABLE_REPORT_FORM_CODE, SetFormCodeToIntentModel.setFormCode(CommonsFormCode.CARTABLE_REPORT_FORM_CODE, Commons.NULL), false, false);
        }
    }

    private void goAttachmentActivity(String whichAttachment
                                      ///, String b5IdRefId >>> deprecated
            , String b5HcName) {
        if (checkNull()) {
            ActivityIntents.goAttachProviderActForResult(getView().getActivity(), whichAttachment
                    ///, b5IdRefId >>> deprecated
                    , b5HcName);
        }
    }

    /**
     * @param attachJson [{{attachmentGUID+suffix(fileName),columnNumber,B5HCTypeId} }]  (AttachmentJsonModel.class)
     *
     *                   if canUserAddAttach = false ==>> we no need to listen to result of attachAlbum
     *                   but if canUserDeleteAttach = false ==>> we may be need to listen to result of attachAlbum
     */
    protected void goAttachAlbumActivity(boolean canUserAddAttach,boolean canUserDeleteAttach,@Nullable String attachJson) {
        if (checkNull()) {
            if (attachJson == null) {
                //in this case, we are in new mode or edit mode and all attach data are in ram
                ActivityIntents.goAttachAlbumActForResult(getView().getActivity(), AttachJsonCreator.buildJson(attachNameList, attachColumnIdList, attachB5HCTypeIdList), canUserAddAttach,canUserDeleteAttach);
            } else {
                ActivityIntents.goAttachAlbumActForResult(getView().getActivity(), attachJson, canUserAddAttach,canUserDeleteAttach);
            }
        }
    }

    private void goMapForOneLocation() {
        if (checkNull()) {
            ActivityIntents.goMapActGetOneLocationForResult(getView().getActivity());
        }
    }


    private void setBottomSheetListener() {
        if (checkNull()) {
            iBottomSheetClickListener = (clickName, b5HcName) -> {
                iWaitingHandler = () -> BasePresenter.this.waitForCloseBottomSheet(clickName, b5HcName);
                waitingHandler.setiWaitingHandler(iWaitingHandler);
                new android.os.Handler().postDelayed(waitingHandler, Commons.CLOSE_BOTTOM_SHEET_WAIT_TIME);
                //unRegisterListener.add(iWaitingHandler);
            };
            getView().setiBottomSheetClickListener(iBottomSheetClickListener);
            //unRegisterListener.add(iBottomSheetClickListener);
        }
    }

    private void waitForCloseBottomSheet(String clickName
                                         ///, String b5IdRefGUID >>> deprecated
            , String b5HcName) {
        workBottomSheetClick(clickName
                ///, b5IdRefGUID >>> deprecated
                , b5HcName);
    }


    private void workBottomSheetClick(String clickName
                                      ///, String b5IdRefGUID >>> deprecated
            , String b5HcName) {
        switch (clickName) {
            case CommonsAttachmentType.CAMERA:
                goAttachmentActivity(CommonsAttachmentType.CAMERA
                        ///,b5IdRefGUID >>> deprecated
                        , b5HcName);
                break;
            case CommonsAttachmentType.GALLERY:
                goAttachmentActivity(CommonsAttachmentType.GALLERY
                        ///, b5IdRefGUID >>> deprecated
                        , b5HcName);
                break;
            case CommonsAttachmentType.CONTACT:
                goAttachmentActivity(CommonsAttachmentType.CONTACT
                        ///, b5IdRefGUID >>> deprecated
                        , b5HcName);
                break;
            case CommonsAttachmentType.FILE:
                goAttachmentActivity(CommonsAttachmentType.FILE
                        ///, b5IdRefGUID >>> deprecated
                        , b5HcName);
                break;
            case CommonsAttachmentType.LOCATION:
                goMapForOneLocation();
                break;
            case CommonsAttachmentType.VOICE:
                goAttachmentActivity(CommonsAttachmentType.VOICE
                        ///,b5IdRefGUID >>> deprecated
                        , b5HcName);
                break;

        }
    }


    protected void setupCameraVoiceUri() {
        cameraVoiceUri = FileToUriHelper.getUri(CreateFolderFileHandler.createFile(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, SharedPreferenceProvider.getCurrentAttachmentName(context)), context);
    }

    //---------------------------------------------------------------------------------------------->>
    //todo get forbidden apps from rest and set in table
    protected void getUserLiveLocation(ArrayList<String> forbiddenAppList, ILiveLocationListener locationListener) {

        if (MockLocationHelper.check(context)) {
            if (locationListener != null) {
                locationListener.mockLocation();
            }
        } else if (forbiddenAppList != null) {
            if (HaveSomeAppsHandler.havePackage(context, forbiddenAppList)) {
                if (locationListener != null) {
                    locationListener.forbiddenApp();
                }
            }
        } else {
            airCallbacks = new AirLocation.Callback() {
                @Override
                public void onSuccess(@NotNull ArrayList<Location> arrayList) {
                    if (locationListener != null) {
                        locationListener.getLocation(arrayList.get(0));
                    }
                    Log.i("arash", "onSuccess getUserLocation: air " + arrayList.get(0).getLatitude() + "***" + arrayList.get(0).getLongitude());
                }

                @Override
                public void onFailure(@NotNull AirLocation.LocationFailedEnum locationFailedEnum) {
                    if (locationListener != null) {
                        locationListener.getError(locationFailedEnum.name());
                    }
                    Log.i("arash", "onFailed: air" + locationFailedEnum.name());
                }
            };
            if (checkNull()) {
                airLocation = new AirLocation(getView().getActivity(), airCallbacks,false,5000,context.getResources().getString(R.string.location_alert));
                airLocation.start();

            }
        }
    }

    private void workForLiveLocationPermission(int requestCode, int[] grantResults) {
        if (airLocation != null) {
            String[] permission = new String[2];
            permission[0] = Manifest.permission.ACCESS_COARSE_LOCATION;
            permission[1] = Manifest.permission.ACCESS_FINE_LOCATION;
            airLocation.onRequestPermissionsResult(requestCode, permission, grantResults);
        }
    }

    private void workForLiveLocationResult(int requestCode, int resultCode, Intent data) {
        if (airLocation != null) {
            airLocation.onActivityResult(requestCode, resultCode, data);
        }
    }
    //---------------------------------------------------------------------------------------------->>

    /**
     * be tozihate table attach khande shavad
     * hatman permission file agar lazem ast gerefte shavad
     */
    protected Disposable startUploadAttachmentProcess() {


            return CheckPermissionHandler.getFilePermissions(context).subscribe(tedPermissionResult -> {
                if(tedPermissionResult.isGranted()){
                    UploadFileForeGroundService.intentToUploadFileForeGroundService(context);
                } else {
                    if(checkNull()){
                        getView().showMessageNoPermissionGranted();
                    }
                }
            }, throwable -> ThrowableLoggerHelper.logMyThrowable("basePresenter/startUploadAttachmentProcess:"+throwable.getMessage()));


    }

    //------------------------------------------------------------------------------------------

    /**
     * result from attachProvider and attachAlbum activities
     * Due to the request code, we will done proper work in origin activity
     */
    protected void goSendBackResultForAttach() {
        if (checkNull()) {
            for (int i = 0; i < attachNameList.size(); i++) {
                //Log.i("arash", "fileUri: " + listURIForResult.get(i)); deprecated
                Log.i("arash", "fileName: " + attachNameList.get(i));
            }
            ActivityIntents.resultOkFromAttachOrAlbumAttachProviderAct(getView().getActivity(),
                    // listURIForResult, deprecated
                    attachNameList, attachB5HCTypeIdList, attachColumnIdList);
        }
    }

}