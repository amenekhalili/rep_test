package ir.fararayaneh.erp.activities.configs;

import android.content.Context;
import android.util.Log;
import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.fararayaneh.erp.IBase.common_base.BasePresenterWithRecycle;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.adaptors.configs_adaptor.ConfigsRecAdaptor;
import ir.fararayaneh.erp.adaptors.configs_adaptor.IClickConfigsRecAdaptor;
import ir.fararayaneh.erp.commons.CommonDialogeClick;
import ir.fararayaneh.erp.commons.CommonDialogeType;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.offline.ConfigsListDataProvider;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.utils.data_handler.AllTBLListHelper;
import ir.fararayaneh.erp.utils.data_handler.ICheckUnSyncTableListener;
import ir.fararayaneh.erp.utils.PingHandler;
import ir.fararayaneh.erp.utils.data_handler.CheckUnSyncData;
import ir.fararayaneh.erp.utils.data_handler.ClearAllTablesHandler;
import ir.fararayaneh.erp.utils.data_handler.IClearAllTablesListener;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;

public class ConfigsPresenter extends BasePresenterWithRecycle<ConfigsView> {

    private ConfigsRecAdaptor configsRecAdaptor = new ConfigsRecAdaptor(context);
    private IConfigsClickListener iConfigsClickListener;
    private IClickConfigsRecAdaptor clickConfigsRecAdaptor;
    private IClearAllTablesListener iClearAllTablesListener;
    private ICheckUnSyncTableListener iCheckUnSyncTableListener;
    private IIOSDialogeListener iiosDialogeListener;
    private String oldIPValue = SharedPreferenceProvider.getServerConfigs(context);
    private boolean comeFromSplash;


    public ConfigsPresenter(WeakReference<ConfigsView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    @Override
    protected UserMessageModel makeProperShowDialogueData(int dialogueType, int CommonComboClickType, String someValue) {
        return null;
    }

    @Override
    protected void workForListenToMSGOrDialoge(int whichDialog, int whichClick, IModels iModels) {

        switch (whichDialog) {
            case CommonDialogeType.SETUP_SERVER_DIALOG:
                newIPProcess(iModels);
                break;
            case CommonDialogeType.SETUP_LANGUAGE_DIALOG:
                newLangProcess(whichClick);
                break;
            case CommonDialogeType.SETUP_COLOR_DIALOG:
                newThemeProcess(whichClick);
                break;
        }

    }


    @Override
    protected void workForSocketListener(ISocketModel iSocketModel) {
        //todo
    }

    @Override
    protected void ClearAllPresenterListener() {
        iConfigsClickListener = null;
        iClearAllTablesListener = null;
        clickConfigsRecAdaptor = null;
        iCheckUnSyncTableListener = null;
        iiosDialogeListener = null;
    }


    @Override
    public void click() {
        iConfigsClickListener = this::onBackPress;
        if(checkNull()){
            getView().setiConfigsClickListener(iConfigsClickListener);
        }
        clickConfigsRecAdaptor = position -> {
            Log.i("arash", "click: " + position);
            switch (position) {
                case 0:
                    createServerConfigDialog();
                    break;
                case 1:
                    createThemeConfigDialog();
                    break;
                case 2:
                    createCalendarConfigDialog();
                    break;

            }

        };
        //unRegisterListener.add(clickConfigsRecAdaptor);
        configsRecAdaptor.setClickRecycle(clickConfigsRecAdaptor);

    }


    /**
     * this method is deprecated because user have not permission to set language (mr.Rezaii)
     */
    private void createLangConfigDialog() {
        if (checkNull()) {
            getView().showDialogue(CommonDialogeType.SETUP_LANGUAGE_DIALOG, -1, "");
        }
    }

    private void createServerConfigDialog() {
        if (checkNull()) {
            getView().showDialogue(CommonDialogeType.SETUP_SERVER_DIALOG, -1, "");
        }
    }

    private void createThemeConfigDialog() {
        if (checkNull()) {
            getView().showDialogue(CommonDialogeType.SETUP_COLOR_DIALOG, -1, "");
        }
    }

    private void createCalendarConfigDialog() {
        if (checkNull()) {
            getView().showDialogue(CommonDialogeType.SETUP_CALENDAR_DIALOG, 0, "");
        }
    }

    @Override
    public void onCreate(IntentModel intentModel) {
        super.onCreate(intentModel);
        setupOriginActivity(intentModel);

    }

    private void setupOriginActivity(IntentModel intentModel) {
        comeFromSplash = intentModel.isGoConfigActFromSplash();
    }

    @Override
    protected void setRecyclAdaptor() {
        if (checkNull()) {
            getView().setRecycleAdaptor(configsRecAdaptor);
        }
    }

    @Override
    protected void workForLoadPage() {
        setDataConfigsList();
    }

    private void setDataConfigsList() {
        ConfigsListDataProvider configsListDataProvider = (ConfigsListDataProvider)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.CONFIGS_LIST_DATAPROVIDER);
        assert configsListDataProvider != null;
        configsRecAdaptor.setDataModelList(configsListDataProvider.data(null));

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
    public void onStart() {
        super.onStart();
        if(comeFromSplash && checkNull()){
            getView().showMessagePleaseSetServerSetting();
        }
    }

    @Override
    protected void updateUIWhenReceivedAttachment() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onBackPress() {

        if (checkNull()) {
            if (comeFromSplash) {
                ActivityIntents.goSplashActivity(context);
                //no need to finish,splash clear all top activities but ...
            }
            getView().workforOnBackPressed();
        }
    }

    private void newIPProcess(IModels iModels) {
        GlobalModel globalModel = (GlobalModel) iModels;
        manageOldIp(globalModel.getMyIP());
    }

    private void manageOldIp(String newIP) {
        if (checkNull()) {
            if (!oldIPValue.equals(newIP)) {
                getView().showHideProgress(true);
                haveUnSyncData(newIP);
            }
        }
    }

    private void haveUnSyncData(String newIP) {

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
                addDisposable(pingServer(newIP));
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
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_14);
                }
            }
        };
        //unRegisterListener.add(iCheckUnSyncTableListener);
        addDisposable(CheckUnSyncData.haveUnSync(iCheckUnSyncTableListener, AllTBLListHelper.getListOfAllTables()));
    }

    private Disposable pingServer(String newIP) {
        return PingHandler.getPing(newIP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(iModels -> {
            GlobalModel globalModel = (GlobalModel) iModels;
            if (globalModel.isMyBoolean()) {
                manageNewIP(newIP);
            } else {
                //todo: پینگ سرور های ما باز شود و بخش کامنت شده اجرا شود
                manageNewIP(newIP);
                /*if (checkNull()) {
                    getView().showMessageNoPing();
                    getView().showHideProgress(false);
                }*/
            }

        }, throwable -> {
          //todo: پینگ سرور های ما باز شود و بخش کامنت شده اجرا شود
          manageNewIP(newIP);
          /*if (checkNull()) {
                    getView().showMessageNoPing();
                    getView().showHideProgress(false);
                }*/


        });
    }

    private void manageNewIP(String newIP) {
        iiosDialogeListener = new IIOSDialogeListener() {
            @Override
            public void confirm() {
                saveNewIPClearData(newIP);
            }

            @Override
            public void cancle() {
                if (checkNull()) {
                    getView().showHideProgress(false);
                }
            }
        };
        //unRegisterListener.add(iiosDialogeListener);
        if (checkNull()) {
            getView().showSaveIPAlert(iiosDialogeListener);
        }
    }

    private void saveNewIPClearData(String newIP) {
         iClearAllTablesListener = new IClearAllTablesListener() {
            @Override
            public void done() {
                SharedPreferenceProvider.setServerConfigs(context, newIP);
                oldIPValue = newIP;
                if (checkNull()) {
                    getView().showHideProgress(false);
                }
            }

            @Override
            public void error() {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_15);
                }
            }
        };
        addDisposable(ClearAllTablesHandler.clear(iClearAllTablesListener, AllTBLListHelper.getListOfAllTables()));
        //unRegisterListener.add(iClearAllTablesListener);
    }

    /**
     * this method is deprecated because user have not permission to set language (mr.Rezaii)
     * @deprecated
     */
    private void newLangProcess(int whichClick) {
        switch (whichClick) {
            case CommonDialogeClick.CLICK_PERSIAN_LANG:
                SharedPreferenceProvider.setLocalLanguage(context, Commons.PERSIAN_LOCAL);
                break;
            case CommonDialogeClick.CLICK_ENGLISH_LANG:
                SharedPreferenceProvider.setLocalLanguage(context, Commons.ENGLISH_LOCAL);
                break;
            case CommonDialogeClick.CLICK_ARABIC_LANG:
                SharedPreferenceProvider.setLocalLanguage(context, Commons.ARABIC_LOCAL);
                break;
        }
        ActivityIntents.goConfigsActivity(context, comeFromSplash);
    }

    private void newThemeProcess(int whichClick) {
        int style = R.style.DefaultAppTheme;
        switch (whichClick) {
            case CommonDialogeClick.CLICK_COLOR_1:
                style = R.style.DefaultAppTheme;
                break;
            case CommonDialogeClick.CLICK_COLOR_2:
                style = R.style.SecondAppTheme;
                break;
            case CommonDialogeClick.CLICK_COLOR_3:
                style = R.style.ThirdAppTheme;
                break;
            case CommonDialogeClick.CLICK_COLOR_4:
                style = R.style.ForthAppTheme;
                break;
            case CommonDialogeClick.CLICK_COLOR_5:
                style = R.style.FifthAppTheme;
                break;
            case CommonDialogeClick.CLICK_COLOR_6:
                style = R.style.SixAppTheme;
                break;
            case CommonDialogeClick.CLICK_COLOR_7:
                style = R.style.SevenAppTheme;
                break;
            case CommonDialogeClick.CLICK_COLOR_8:
                style = R.style.EightAppTheme;
                break;
        }
        SharedPreferenceProvider.setAppTheme(context, style);
        if (checkNull()) {
            getView().reCreate();
        }
    }

    //------------------------------------------


}
