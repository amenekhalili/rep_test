package ir.fararayaneh.erp.activities.login;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.lang.ref.WeakReference;
import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.IBase.common_base.BasePresenter;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.online.INetDataProviderListener;
import ir.fararayaneh.erp.data.data_providers.queries.InsertUserQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.online.response.RestResponseModel;
import ir.fararayaneh.erp.utils.data_handler.CheckUnSyncData;
import ir.fararayaneh.erp.utils.data_handler.ICheckUnSyncTableListener;
import ir.fararayaneh.erp.utils.data_handler.KindListForSyncActHelper;
import ir.fararayaneh.erp.utils.data_handler.AllTBLListHelper;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.data_handler.CheckPassWordHandler;
import ir.fararayaneh.erp.utils.data_handler.ClearAllTablesHandler;
import ir.fararayaneh.erp.utils.data_handler.HavingUserHandler;
import ir.fararayaneh.erp.utils.data_handler.ICheckPassListener;
import ir.fararayaneh.erp.utils.data_handler.IClearAllTablesListener;
import ir.fararayaneh.erp.utils.data_handler.IHaveUserListener;
import ir.fararayaneh.erp.utils.encription.HashHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.package_handler.VersionAppHelper;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;


public class LoginPresenter extends BasePresenter<LoginView> {

    private boolean haveSavedUser;
    private String mUserID, mUserName, mPassWord, mOrganization, mUserImageUrl,mCompanyName;
    private IClickLoginListener iClickLoginListener;
    private ISaveUserDetails iSaveUserDetails;
    private IHaveUserListener iHaveUserListener;
    private ICheckPassListener iCheckPassListener;
    private INetDataProviderListener iNetDataProviderListener;
    private IClearAllTablesListener iClearAllTablesListener;
    private IIOSDialogeListener iiosDialogeListener;
    private ICheckUnSyncTableListener iCheckUnSyncTableListener;

    LoginPresenter(WeakReference<LoginView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    @Override
    protected UserMessageModel makeProperShowDialogueData(int dialogueType, int CommonComboClickType,String someValue) {
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
        iClickLoginListener=null;
        iSaveUserDetails=null;
        iHaveUserListener=null;
        iCheckPassListener=null;
        iNetDataProviderListener=null;
        iClearAllTablesListener=null;
        iiosDialogeListener=null;
        iCheckUnSyncTableListener=null;
    }

    @Override
    public void click() {
        iClickLoginListener=new IClickLoginListener() {
            @Override
            public void loginClick(String userName, String passWord, String organization) {
                if (haveSavedUser) {
                    checkOfflinePassword(userName, passWord);
                } else {
                    setPermanentUserAccount(userName, passWord, organization);
                    if(checkNull()){
                        getView().showConfigIMG(false);
                    }
                    checkOnlinePassword(userName, passWord, organization);
                }
            }
            @Override
            public void SettingClick() {
                ActivityIntents.goConfigsActivity(context,false);
            }
        };


        iSaveUserDetails = new ISaveUserDetails() {
            @Override
            public void saveUserDetails() {
                addDisposable(addUserToTable());
            }

            @Override
            public void workForExpireFinancialDate() {
                //here we are after sync process
                    clearForExpireFinancialDate();
            }
        };


        if (checkNull()) {
            getView().setiClickLoginListener(iClickLoginListener);
            getView().setiSaveUserDetails(iSaveUserDetails);
        }

        //unRegisterListener.add(iClickLoginListener);
        //unRegisterListener.add(iSaveUserDetails);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(IntentModel intentModel) {
        witchWorkAfterCreate();
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
    //--------------------------------------------------------------------------------------->>

    private void witchWorkAfterCreate() {
        iHaveUserListener = new IHaveUserListener() {
            @Override
            public void haveUser(boolean user, GlobalModel globalModel) {
                if (user) {
                    haveSavedUser = true;
                    SharedPreferenceProvider.setUserId(context,globalModel.getUserId());
                    SharedPreferenceProvider.setOrganization(context,globalModel.getOrganization());
                    if (checkNull()) {
                        getView().setUserNameOrganization(globalModel.getUserName(), globalModel.getOrganization());
                    }
                } else {
                    if (checkNull()) {
                        getView().showConfigIMG(true);
                    }
                }
            }

            @Override
            public void error() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_23);
                }
            }
        };
        addDisposable(HavingUserHandler.haveUser(iHaveUserListener));
        setAppVersion();
        //unRegisterListener.add(iHaveUserListener);
    }

    private void setAppVersion(){
        if(checkNull()){
            getView().setVersionName(VersionAppHelper.getMyVersionName(context));
        }
    }

    private void checkOfflinePassword(String userName, String passWord) {
        iCheckPassListener = new ICheckPassListener() {
            @Override
            public void checkPassword(boolean correctPass, String userId) {
                if (correctPass) {
                    if(CustomTimeHelper.isDateBetweenFinancialDate(context,CustomTimeHelper.getCurrentDate().getTime())){
                        goMainActivityAndFinish();
                    } else {
                        if (checkNull()) {
                            getView().showHideProgress(false);
                            showInvalidFinancialDialoge();
                        }
                    }
                } else {
                    if (checkNull()) {
                        getView().showMessageWrongPass();
                        getView().showHideProgress(false);
                    }
                }
            }

            @Override
            public void error() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_24);
                    getView().showHideProgress(false);
                }
            }
        };
        //unRegisterListener.add(iCheckPassListener);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setUserName(userName);
        globalModel.setPassWord(passWord);
        addDisposable(CheckPassWordHandler.offLineChecking(globalModel, iCheckPassListener));
    }

    private void checkOnlinePassword(String userName, String passWord, String organization) {

        iNetDataProviderListener = new INetDataProviderListener() {
            @Override
            public void uploadProgressListener(int upLoad) {
                //no work
            }

            @Override
            public void downloadProgressListen(int downLoad) {
                //no work
            }

            @Override
            public void responce(RestResponseModel response) {

                if (!response.getItems().get(0).getUserId().equals(Commons.NULL_INTEGER) ) {
                    setUserId(response.getItems().get(0).getUserId(), response.getItems().get(0).getBody().get(0).getUserImageUrl(), response.getItems().get(0).getBody().get(0).getCompanyName());
                    CheckPassWordHandler.saveApplicationConfig(context,response);
                    App.setupMySocket();
                    clearAllTables(false);
                } else {
                    if (checkNull()) {
                        getView().showMessageWrongInput();
                        getView().showHideProgress(false);
                        getView().showConfigIMG(true);
                    }
                }
            }

            @Override
            public void error(String error) {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_25);
                    getView().showHideProgress(false);
                    getView().showConfigIMG(true);
                }
            }
        };
        //unRegisterListener.add(iNetDataProviderListener);
        CheckPassWordHandler.onLineChecking(userName, HashHelper.hashProcess(passWord, Commons.HASH_PROCESS), organization, iNetDataProviderListener);
    }

    private void setPermanentUserAccount(String userName, String passWord, String organization) {
        mUserName = userName;
        mPassWord = passWord;
        mOrganization = organization;
    }

    private void setUserId(String userId, String userImageUrl,String companyName) {
        mUserID = userId;
        mUserImageUrl = userImageUrl;
        mCompanyName = companyName;
    }

    /**
     * baraye ehtiait, chon dar activity sync ham, hameie data pak mishavad(be joz attachment)
     */
    private void clearAllTables(boolean fromExpireFinancialDate) {
         iClearAllTablesListener = new IClearAllTablesListener() {
            @Override
            public void done() {
                if(fromExpireFinancialDate){
                   SharedPreferenceProvider.clearAllPreferences(context);
                    ActivityIntents.goConfigsActivity(context,false);
                } else {
                    //caution : here do not clear shared preference,because we should connect to the server
                    goSyncAct(KindListForSyncActHelper.getAllKindsList());
                    if(checkNull()){
                        getView().showHideProgress(false);
                    }
                }
            }

            @Override
            public void error() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_26);
                    getView().showHideProgress(false);
                }
            }
        };
        //unRegisterListener.add(iClearAllTablesListener);
        addDisposable(ClearAllTablesHandler.clear(iClearAllTablesListener, AllTBLListHelper.getListOfAllTables()));

    }

    private Disposable addUserToTable() {

        InsertUserQuery insertUserQuery = (InsertUserQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_USER_QUERY);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setUserName(mUserName);
        globalModel.setUserId(mUserID);
        globalModel.setOrganization(mOrganization);
        globalModel.setCompanyName(mCompanyName);
        globalModel.setPassWord(mPassWord);
        globalModel.setUserImageUrl(mUserImageUrl);
        globalModel.setDeviceId(SharedPreferenceProvider.getDeviceId(context));
        assert insertUserQuery != null;
        return insertUserQuery.data(globalModel).subscribe(iModels -> {
            App.setupMySocket();
            goMainActivityAndFinish();
        }, throwable -> {
            if (checkNull()) {
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_27);
            }
        });
    }

    private void showInvalidFinancialDialoge() {
        iiosDialogeListener = new IIOSDialogeListener() {
            @Override
            public void confirm() {
                checkUnSyncData();
            }
            @Override
            public void cancle() {
                //no need here
            }
        };

        if(checkNull()){
            getView().showInvalidFinancialLayout(iiosDialogeListener);
        }

    }

    private void checkUnSyncData() {
        iCheckUnSyncTableListener = new ICheckUnSyncTableListener() {
            @Override
            public void haveUnsyncData() {
             goSyncActForExpireFinancialDate(KindListForSyncActHelper.getAllKindsList());
            }

            @Override
            public void haveNotUnsyncData() {
                clearForExpireFinancialDate();
            }

            @Override
            public void uploadProcess() {
                if (checkNull()) {
                    getView().showMessageUnSyncUploadData(context);
                }
            }
            @Override
            public void error() {
                ThrowableLoggerHelper.logMyThrowable("/LoginPresenter/checkUnSyncData");
                if(checkNull()){
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_28);
                }
            }
        };

        addDisposable(CheckUnSyncData.haveUnSync(iCheckUnSyncTableListener, AllTBLListHelper.getListOfAllTables()));
    }

    private void clearForExpireFinancialDate() {
        clearAllTables(true);
    }

}
