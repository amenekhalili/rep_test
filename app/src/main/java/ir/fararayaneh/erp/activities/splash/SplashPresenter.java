package ir.fararayaneh.erp.activities.splash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.CountDownTimer;
import android.util.Log;
import java.lang.ref.WeakReference;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.IBase.common_base.BasePresenter;
import ir.fararayaneh.erp.commons.CommonUrls;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.online.GetNodeServerDataProvider;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.services.DownLoadAPKForGroundService;
import ir.fararayaneh.erp.utils.PingHandler;
import ir.fararayaneh.erp.utils.data_handler.AllTBLListHelper;
import ir.fararayaneh.erp.utils.data_handler.CheckUnSyncData;
import ir.fararayaneh.erp.utils.data_handler.ICheckUnSyncTableListener;
import ir.fararayaneh.erp.utils.data_handler.KindListForSyncActHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.intent_handler.CallIntentHandler;
import ir.fararayaneh.erp.utils.package_handler.VersionAppHelper;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;
import ir.fararayaneh.erp.utils.waiting.IWaitingHandler;

import static ir.fararayaneh.erp.commons.CommonIntroHelperKt.SHOW_GLOBAL_INTRO;

public class SplashPresenter extends BasePresenter<SplashView> {

    private IWaitingHandler iWaitingHandler;
    private ISplshListener iSplshListener;
    private IIOSDialogeListener iiosDialogeListener;
    private ICheckUnSyncTableListener iCheckUnSyncTableListener;

    SplashPresenter(WeakReference<SplashView> weekView, Context context, boolean shouldHaveIntent) {
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
    protected void ClearAllPresenterListener() {
        iWaitingHandler = null;
        iSplshListener = null;
        iiosDialogeListener = null;
        iCheckUnSyncTableListener = null;
    }


    @Override
    public void click() {
        if (checkNull()) {
            iSplshListener = () -> {
                if (SharedPreferenceProvider.haveUploadProcess(context)) {
                    if (checkNull()) {
                        getView().showMessageUnSyncUploadData(context);
                    }
                } else {
                    DownLoadAPKForGroundService.intentToDownLoadAPKForGroundService(context);
                }
            };
            getView().setiSplshListener(iSplshListener);
        }

    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(IntentModel intentModel) {
        makeDelay();
    }

    private void makeDelay() {
        iWaitingHandler = this::showCompanyIcon;
        waitingHandler.setiWaitingHandler(iWaitingHandler);
        new android.os.Handler().postDelayed(waitingHandler, Commons.SPLASH_WAIT_TIME);
        //unRegisterListener.add(iWaitingHandler);
    }

    private void showCompanyIcon(){
        if (checkNull()) {
            getView().animateCompanyIcon();
        }
        callTimer();
        
    }

    private void callTimer() {
        new CountDownTimer(Commons.SPLASH_WAIT_TIME_2, 100) {
            public void onTick(long millisUntilFinished) {
                Log.i("arash", "onTick: ");
            }

            public void onFinish() {
                Log.i("arash", "onFinish: ");
                workAfterDelay();
            }

        }.start();
    }

    private void workAfterDelay() {
        if (SharedPreferenceProvider.getServerConfigs(context)
                .equals(Commons.NO_HAVE_CONFIGS_FOR_SERVER)) {
            internetConnectionChecking();
        } else {
            checkNodeConnection();
        }
        /*else if(!SharedPreferenceProvider.getNodeIp(context).equals(Commons.NULL)) {
            checkNodeConnection();
        }
        else {
            goToLogin();
        }*/
    }

    private void checkNodeConnection() {
        if (PingHandler.checkPingNode()) {
            addDisposable(haveNewAppVersion());
        } else {
            if (checkNull()) {
                //getView().showMessageNodeServerDisconnecting(context);
                goToLogin();
            }
        }
    }

    private Disposable haveNewAppVersion() {


        GetNodeServerDataProvider getNodeServerDataProvider =
                (GetNodeServerDataProvider) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_NODE_SERVER_DATA_PROVIDER);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setDownloadURL(SharedPreferenceProvider.getNodeIp(context) + CommonUrls.APK_VERSION_URL_END_POINT);
        assert getNodeServerDataProvider != null;
        return getNodeServerDataProvider.data(globalModel)
                .subscribe(restResponseModel -> {
                    if (restResponseModel.getItems().size() > 0) {
                        if (restResponseModel.getItems().get(0).getAppVersion() > VersionAppHelper.getMyVersionCode(context)) {
                            showDownloadDialoge();
                        } else {
                            goToLogin();
                        }
                    } else {
                        goToLogin();
                    }
                }, throwable -> {
                    ThrowableLoggerHelper.logMyThrowable(throwable.getMessage() + "/SplashPresenter/haveNewAppVersion");
                    goToLogin();
                });

    }

    private void goToLogin() {
        ActivityIntents.goLoginActivity(context);
        if (checkNull()) {
            getView().finish();
        }
    }

    private void showDownloadDialoge() {
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
            getView().showUpdateLayout(iiosDialogeListener);
        }

    }

    private void checkUnSyncData() {
        iCheckUnSyncTableListener = new ICheckUnSyncTableListener() {
            @Override
            public void haveUnsyncData() {
                goSyncAct(KindListForSyncActHelper.getAllKindsList());
            }

            @Override
            public void haveNotUnsyncData() {
                if (checkNull()) {
                    getView().showMessageDownloading();
                }
                //no need to get permission
                DownLoadAPKForGroundService.intentToDownLoadAPKForGroundService(context);
            }

            @Override
            public void uploadProcess() {
                if (checkNull()) {
                    getView().showMessageUnSyncUploadData(context);
                }
            }

            @Override
            public void error() {
                ThrowableLoggerHelper.logMyThrowable("/SplashPresenter/checkUnSyncData");
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_39);
                }
            }
        };

        addDisposable(CheckUnSyncData.haveUnSync(iCheckUnSyncTableListener, AllTBLListHelper.getListOfAllTables()));
    }

    private void internetConnectionChecking() {
        addDisposable(PingHandler
                .getPing(CommonUrls.PING_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        if (checkNull()) {
                            ActivityIntents.goConfigsActivity(context, true);
                        }
                    } else {
                        showDisconnection();
                    }
                }, throwable -> showDisconnection()));
    }

    private void showDisconnection() {
        if (checkNull()) {
            getView().showHideProgress(false);
            iiosDialogeListener = new IIOSDialogeListener() {
                @Override
                public void confirm() {
                    if (checkNull()) {
                        getView().reCreate();
                    }
                }

                @Override
                public void cancle() {
                    CallIntentHandler.handler(getView().getActivity(), Commons.SUPPORT_PHONE_CALLS);
                    if (checkNull()) {
                        getView().finish();
                    }
                }
            };
            getView().showDisconnectionLayout(iiosDialogeListener);
            //unRegisterListener.add(fancyDialogeListener);
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onBackPress() {
        if (checkNull()) {
            getView().workforOnBackPressed();
        }
    }
}
