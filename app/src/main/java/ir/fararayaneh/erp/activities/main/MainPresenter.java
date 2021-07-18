package ir.fararayaneh.erp.activities.main;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.IBase.common_base.BasePresenter;
import ir.fararayaneh.erp.commons.CommonClickMainBottomBar;
import ir.fararayaneh.erp.commons.CommonUrls;
import ir.fararayaneh.erp.commons.CommonWebApplicationNumber;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.offline.FakeFacilitiesListDataProvider;
import ir.fararayaneh.erp.data.data_providers.online.INetDataProviderListener;
import ir.fararayaneh.erp.data.data_providers.queries.GetAllAppNameQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.DeviceDeleteModel;
import ir.fararayaneh.erp.data.models.middle.FacilitiesModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.online.response.RestResponseModel;
import ir.fararayaneh.erp.utils.PingHandler;
import ir.fararayaneh.erp.utils.data_handler.AllTBLListHelper;
import ir.fararayaneh.erp.utils.data_handler.BackUpRealmHelper;
import ir.fararayaneh.erp.utils.data_handler.CheckPassWordHandler;
import ir.fararayaneh.erp.utils.data_handler.CheckPermissionHandler;
import ir.fararayaneh.erp.utils.data_handler.CheckUnSyncData;
import ir.fararayaneh.erp.utils.data_handler.HavingUserHandler;
import ir.fararayaneh.erp.utils.data_handler.IAllDataBaseBackupQuery;
import ir.fararayaneh.erp.utils.data_handler.ICheckUnSyncTableListener;
import ir.fararayaneh.erp.utils.data_handler.IHaveUserListener;
import ir.fararayaneh.erp.utils.data_handler.KindListForSyncActHelper;
import ir.fararayaneh.erp.utils.encription.HashHelper;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.intent_handler.BrowserIntentHandler;
import ir.fararayaneh.erp.utils.intent_handler.CallIntentHandler;
import ir.fararayaneh.erp.utils.intent_handler.IntentSenderHelper;
import ir.fararayaneh.erp.utils.location_handler.ILiveLocationListener;
import ir.fararayaneh.erp.utils.logger.CrashlyticsLogger;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;
import ir.fararayaneh.erp.utils.waiting.IWaitingHandler;

import static ir.fararayaneh.erp.commons.CommonIntroHelperKt.SHOW_GLOBAL_INTRO;

public class MainPresenter extends BasePresenter<MainView> {

    private IBackClick iBackClick;
    private IMainClickListener iMainClickListener;
    private IWaitingHandler iWaitingHandler;
    private IHaveUserListener iHaveUserListener;
    private ICheckUnSyncTableListener iCheckUnSyncTableListener;
    private IIOSDialogeListener iiosDialogeListener;
    private IAllDataBaseBackupQuery iAllDataBaseBackupQuery;
    private INetDataProviderListener iNetDataProviderListener;

    public MainPresenter(WeakReference<MainView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    @Override
    protected UserMessageModel makeProperShowDialogueData(int dialogueType, int CommonComboClickType, String someValue) {
        return null;
    }

    @Override
    protected void workForListenToMSGOrDialoge(int whichDialog, int whichClick, IModels iModels) {

    }

    /**
     * به هر دلیل این لیستنر کال شود
     * مقدار بج دوباری کویری میشود
     * چه تغیییر کرده باشد چه نکرده باشد
     */
    @Override
    protected void workForSocketListener(ISocketModel iSocketModel) {
        if (checkNull()) {
            getView().setBadgeNumber(SharedPreferenceProvider.getBadgeChat(context),
                    SharedPreferenceProvider.getBadgeCartable(context));
        }
    }

    @Override
    public void click() {
        iBackClick = this::falseExitFromAppDelay;
        //unRegisterListener.add(iBackClick);
        if (checkNull()) {
            getView().setiBackClick(iBackClick);
        }
        iMainClickListener = new IMainClickListener() {
            @Override
            public void clickBottomBarTab(int commonClickMainBottomBar) {
                switch (commonClickMainBottomBar) {
                    case CommonClickMainBottomBar.DASHBOARD_CLICK:
                        internetConnectionChecking(CommonClickMainBottomBar.DASHBOARD_CLICK);
                        break;
                    case CommonClickMainBottomBar.CARTABLE_CLICK:
                        goToCartableActivity();
                        SharedPreferenceProvider.setBadgeNumber(context, 0, -SharedPreferenceProvider.getBadgeCartable(context));
                        break;
                    case CommonClickMainBottomBar.CHAT_CLICK:
                        ActivityIntents.goChatListActivity(context);
                        //do not change badge of chats here,chang on click to go to chatRoom
                        break;
                    case CommonClickMainBottomBar.ONLINE_CLICK:
                        internetConnectionChecking(CommonClickMainBottomBar.ONLINE_CLICK);
                        break;

                }
            }

            @Override
            public void clickSync() {
                goSyncAct(KindListForSyncActHelper.getAllKindsList());
            }

            @Override
            public void clickLogOut() {
                if (!SharedPreferenceProvider.getUserId(context).equals(Commons.NULL_INTEGER)) {
                    workForLogOut();
                }
            }

            @Override
            public void clickAbout() {
                BrowserIntentHandler.handler(getView().getActivity(), CommonUrls.FARA_WEB_SITE_URL);
            }

            @Override
            public void clickSupport() {
                CallIntentHandler.handler(getView().getActivity(), Commons.SUPPORT_PHONE_CALLS);
            }

            @Override
            public void clickgetConfig() {
                if (checkNull()) {
                    /*
                    در صورتی که کانفیگ موجود با کانفیگ دریافتی متفاوت باشد
                    ما امکان سینک دیتا و آپلود فایل نداریم
                    لذا ضرورتی به چک کردن داشتن این موارد نیست
                     */
                    getOnlineConfigs();
                }
            }

            @Override
            public void clickBackUp() {
                if (checkNull()) {
                    getView().showHideProgress(true);
                    addDisposable(getFilePermissionForBackUp());
                }
            }

            @Override
            public void clickNightMode() {
                if (checkNull()) {
                 SharedPreferenceProvider.
                         changeNightMode(context,SharedPreferenceProvider.getNightMode(context)== AppCompatDelegate.MODE_NIGHT_YES?AppCompatDelegate.MODE_NIGHT_NO:AppCompatDelegate.MODE_NIGHT_YES);
                            getView().reCreate();
                }
            }

            @Override
            public void clickHelp() {
                if(checkNull()){
                    ActivityIntents.goIntroHelperActivityForResualt(getView().getActivity(),SHOW_GLOBAL_INTRO);
                }

            }
        };

        if (checkNull()) {
            getView().setiMainClickListener(iMainClickListener);
        }
    }

    private void internetConnectionChecking(int wichWebView) {

        if(checkNull()){
            getView().showHideProgress(true);
        }

        addDisposable(PingHandler
                .getPing(CommonUrls.PING_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        switch (wichWebView){
                            case CommonClickMainBottomBar.ONLINE_CLICK:
                                if (checkNull()) {
                                    ActivityIntents.goWebViewActivity(getView().getActivity(), CommonWebApplicationNumber.GRL_APP_NUMBER,
                                            CommonWebApplicationNumber.GRL_PAGE_NUMBER,
                                            Commons.SPACE);
                                }
                                break;
                            case CommonClickMainBottomBar.DASHBOARD_CLICK:
                                if (checkNull()) {
                                    ActivityIntents.goWebViewActivity(getView().getActivity(), CommonWebApplicationNumber.DASHBOARD_APP_NUMBER,
                                            CommonWebApplicationNumber.DASHBOARD_PAGE_NUMBER,
                                            Commons.SPACE);
                                }
                                break;
                        }

                    } else {
                       if(checkNull()){
                           getView().showHideProgress(false);
                           getView().showMessageNoPing();
                       }
                    }
                }, throwable -> {
                    getView().showHideProgress(false);
                    getView().showMessageNoPing();
                }));
    }

    private void getOnlineConfigs() {
        if(checkNull()){
            getView().showHideProgress(true);
        }

        iNetDataProviderListener = new INetDataProviderListener() {
            @Override
            public void uploadProgressListener(int upLoad) {

            }

            @Override
            public void downloadProgressListen(int downLoad) {

            }

            @Override
            public void responce(RestResponseModel response) {
                if(checkNull()){
                    getView().showHideProgress(false);
                    CheckPassWordHandler.saveApplicationConfig(context,response);
                        getView().showMessageSaveDataSuccess();
                }
            }

            @Override
            public void error(String error) {
                if(checkNull()){
                    getView().showHideProgress(false);
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_92);
                }
            }
        };

        iHaveUserListener = new IHaveUserListener() {
            @Override
            public void haveUser(boolean user, GlobalModel globalModel) {
                //we sure we have user and pass and organization
                CheckPassWordHandler.onLineChecking(globalModel.getUserName(), HashHelper.hashProcess(globalModel.getPassWord(), Commons.HASH_PROCESS), globalModel.getOrganization(), iNetDataProviderListener);
            }
            @Override
            public void error() {
                if(checkNull()){
                    getView().showHideProgress(false);
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_91);
                }
            }
        };
        HavingUserHandler.haveUser(iHaveUserListener);
    }

    private Disposable getFilePermissionForBackUp() {

        return CheckPermissionHandler
                .getFilePermissions(context)
                .subscribe(tedPermissionResult -> {
                    //else do nothing
                    if (tedPermissionResult.isGranted()) {
                        createBackupDataBase();
                    } else {
                        if (checkNull()) {
                            getView().showMessageNoPermissionGranted();
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_29);
                    }
                });
    }

    private void createBackupDataBase() {

        iAllDataBaseBackupQuery = new IAllDataBaseBackupQuery() {
            @Override
            public void success() {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().shoMessageSuccessBackUp();
                }
            }

            @Override
            public void error() {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_30);
                }
            }
        };

        addDisposable(BackUpRealmHelper.makeBackUp(iAllDataBaseBackupQuery));
    }

    private void falseExitFromAppDelay() {
        iWaitingHandler = this::workAfterDelayExitApp;
        waitingHandler.setiWaitingHandler(iWaitingHandler);
        new android.os.Handler().postDelayed(waitingHandler, Commons.EXIT_APP_WAIT_TIME);
        //unRegisterListener.add(iWaitingHandler);
    }

    private void workAfterDelayExitApp() {
        if (checkNull()) {
            getView().exitFromApp = false;
        }
    }

    @Override
    public void onCreate(IntentModel intentModel) {

        if (checkNull()) {
            getView().initMainActivity();
        }
        makeDelay();
        setupFakeData();
        addDisposable(getProperData());
        CrashlyticsLogger.logger(this.getClass().getName(),SharedPreferenceProvider.getUserId(context));
        showIntroduce();

    }

    private void showIntroduce() {
        if(SharedPreferenceProvider.canShowIntroduc(context) && checkNull()){
            SharedPreferenceProvider.setShowIntroduc(context,false);
            ActivityIntents.goIntroHelperActivityForResualt(getView().getActivity(), SHOW_GLOBAL_INTRO);
        }
    }


    private void makeDelay() {
        iWaitingHandler = this::hideCommercial;
        waitingHandler.setiWaitingHandler(iWaitingHandler);
        new android.os.Handler().postDelayed(waitingHandler, Commons.MAIN_WAIT_TIME);
        //unRegisterListener.add(iWaitingHandler);
    }

    private void hideCommercial() {
        if (checkNull()) {
            getView().showHideCommercial(false);
        }
    }

    private void setupFakeData() {
        FakeFacilitiesListDataProvider fakeFacilitiesListDataProvider = (FakeFacilitiesListDataProvider) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.FACILITIES_LIST_DATAPROVIDER);
        IntentModel intentModel = new IntentModel();
        assert fakeFacilitiesListDataProvider != null;
        if (checkNull()) {
            intentModel.setFacilitiesModelList(fakeFacilitiesListDataProvider.data(null));
            getView().setFakeViewPagerData(IntentSenderHelper.setIntentData(intentModel));
        }
    }

    private Disposable getProperData() {
        GetAllAppNameQuery getAllAppNameQuery = (GetAllAppNameQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ALL_APP_NAME_QUERY);
        assert getAllAppNameQuery != null;
        return getAllAppNameQuery.data(App.getNullRXModel()).subscribe(iModels -> {
            GlobalModel globalModel = (GlobalModel) iModels;
            Log.i("arash", "getdata: ic_start show data");
            setStartPage(globalModel.getFacilitiesArrayList());
            setOtherPage(globalModel.getStringArrayList(), globalModel.getListFacilitiesArrayList());

        }, throwable -> {
            if (checkNull()) {
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_31);
            }
        });

    }

    private void setStartPage(ArrayList<FacilitiesModel> facilitiesArrayList) {
        if (checkNull()) {
            IntentModel intentModel = new IntentModel();
            intentModel.setFacilitiesModelList(facilitiesArrayList);
            getView().setCorrectStartViewPagerData(IntentSenderHelper.setIntentData(intentModel));
        }
    }

    private void setOtherPage(ArrayList<String> appNames, ArrayList<ArrayList<FacilitiesModel>> listFacilitiesArrayList) {
        if (checkNull()) {
            for (int i = 0; i < appNames.size(); i++) {
                IntentModel intentModel = new IntentModel();
                intentModel.setFacilitiesModelList(listFacilitiesArrayList.get(i));
                getView().setCorrectOtherViewPagerData(IntentSenderHelper.setIntentData(intentModel), appNames.get(i));
            }
            getView().setCorrectOffsetScreen(appNames.size());
            getView().setCorrectOffsetScreen(0);
            addDisposable(setUpDrawerHeader());

        }
    }

    private Disposable setUpDrawerHeader() {
        iHaveUserListener = new IHaveUserListener() {
            @Override
            public void haveUser(boolean user, GlobalModel globalModel) {
                if (user && checkNull()) {
                    getView().setHeaderDrawerData(globalModel.getUserName(), globalModel.getUserImageUrl(), SharedPreferenceProvider.getCompanyName(context));
                }
            }

            @Override
            public void error() {
                //do nothings
            }
        };
        //unRegisterListener.add(iHaveUserListener);
        return HavingUserHandler.haveUser(iHaveUserListener);
    }

    @Override
    public void onResume() {
        super.onResume();

        //we set offline chatRoom, in onResume chatList and mainActivity
        SharedPreferenceProvider.setOnlineChatroom(context, Commons.NULL_INTEGER,false);

        if (checkNull()) {
            getView().setBadgeNumber(SharedPreferenceProvider.getBadgeChat(context),
                    SharedPreferenceProvider.getBadgeCartable(context));
            getView().showHideProgress(false); //when goes online , we show progress, here we hide it
        }


    }

    @Override
    protected void updateUIWhenReceivedAttachment() {

    }

    @Override
    public void onPause() {
        //donothing
    }

    @Override
    public void onBackPress() {
        if (checkNull()) {
            getView().workforOnBackPressed();
        }
    }

    @Override
    protected void ClearAllPresenterListener() {
        iBackClick = null;
        iWaitingHandler = null;
        iHaveUserListener = null;
        iMainClickListener = null;
        iCheckUnSyncTableListener = null;
        iiosDialogeListener = null;
        iAllDataBaseBackupQuery = null;
        iNetDataProviderListener = null;
    }

    private void workForLogOut() {
        iiosDialogeListener = new IIOSDialogeListener() {
            @Override
            public void confirm() {
                checkUnSyncData();
            }

            @Override
            public void cancle() {

            }
        };

        if (checkNull()) {
            getView().showConfirmLogoutMessage(iiosDialogeListener);
        }
    }

    private void checkUnSyncData() {
        if (checkNull()) {
            getView().showHideProgress(true);
        }
        iCheckUnSyncTableListener = new ICheckUnSyncTableListener() {
            @Override
            public void haveUnsyncData() {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageUnSyncData(context);
                }
            }

            @Override
            public void haveNotUnsyncData() {
                if (checkNull()) {
                    getView().showHideProgress(false);
                }
                showConfirmSendLogoutMessage();
            }

            @Override
            public void uploadProcess() {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageUnSyncUploadData(context);

                }
            }

            @Override
            public void error() {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_32);
                }
            }
        };
        addDisposable(CheckUnSyncData.haveUnSync(iCheckUnSyncTableListener, AllTBLListHelper.getListOfAllTables()));

    }

    private void showConfirmSendLogoutMessage() {
        iiosDialogeListener = new IIOSDialogeListener() {
            @Override
            public void confirm() {
                if (PingHandler.checkPingNode()) {
                    if (checkNull()) {
                        //getView().showHideProgress(true);
                        //BlurHandler.add(context, getView());
                        getView().showMessagePleaseWaite();
                        SendPacket.sendEncryptionMessage(context, SocketJsonMaker.deviceDeleteSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()), new DeviceDeleteModel(SharedPreferenceProvider.getDeviceId(context),SharedPreferenceProvider.getUserId(context))), true);
                    }
                } else {
                    if (checkNull()) {
                        getView().showMessageNodeServerDisconnecting(context);
                    }
                }
            }

            @Override
            public void cancle() {

            }
        };
        if (checkNull()) {
            getView().showConfirmSendLogoutMessage(context, iiosDialogeListener);
        }
    }


}
