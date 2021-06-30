package ir.fararayaneh.erp.activities.sync_act;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.ListIterator;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.IBase.common_base.BasePresenter;
import ir.fararayaneh.erp.commons.CommonKind;
import ir.fararayaneh.erp.commons.CommonUrls;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanAccidentQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanAddressBookQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanAgroFieldQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanBaseCodingQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanCartableQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanChatroomMemberQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanChatroomQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanContactBookQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanCountryDivisionQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanFormRefQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanFuelListDetailQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanGoodTrancQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanGoodsQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanGroupRelatedQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanKartableReceiverQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanMessageQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanPurchasableGoodsQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanSalableGoodsQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanTaskQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanTimeQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanUtilCodeQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanWareHouseQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanWarehouseOutQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanWeighingQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.online.request.rest.RestRequestModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.tables.AgroFieldTable;
import ir.fararayaneh.erp.data.models.tables.BaseCodingTable;
import ir.fararayaneh.erp.data.models.tables.EvacuatePacketTable;
import ir.fararayaneh.erp.data.models.tables.FormRefTable;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.data.models.tables.GroupRelatedTable;
import ir.fararayaneh.erp.data.models.tables.KartableRecieverTable;
import ir.fararayaneh.erp.data.models.tables.LastMessageTable;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AccidentTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AddressBookTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CartableTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ContactBookTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CountryDivisionTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListDetailTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListMasterTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.GoodTranceTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.MessageTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.SalableGoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TaskTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TimeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingListOutTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WeighingTable;
import ir.fararayaneh.erp.utils.PingHandler;
import ir.fararayaneh.erp.utils.data_handler.AllTBLListHelper;
import ir.fararayaneh.erp.utils.data_handler.BackUpRealmHelper;
import ir.fararayaneh.erp.utils.data_handler.CheckPermissionHandler;
import ir.fararayaneh.erp.utils.data_handler.CheckUnSyncData;
import ir.fararayaneh.erp.utils.data_handler.ClearAllTablesHandler;
import ir.fararayaneh.erp.utils.data_handler.IAllDataBaseBackupQuery;
import ir.fararayaneh.erp.utils.data_handler.ICheckUnSyncTableListener;
import ir.fararayaneh.erp.utils.data_handler.IClearAllTablesListener;
import ir.fararayaneh.erp.utils.data_handler.RestReqDataHandler;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;
import ir.fararayaneh.erp.utils.waiting.IWaitingHandler;


/**
 * caution :
 * intentModel.getStringList().size must be have greater than 0
 * <p>
 * only for sync tables not for files :
 * tables that send to here should have syncState column
 * if tables have beforeSync and onSync or syncError data, socket send for that
 * 1-ارسال همه مقادیر که ستون سینک استیت آنها
 * beforeSync and onSync or syncError
 * است و سپس هر جدول با منطق خاصی پاک میشود و اگر لازم است
 * دیتا جدید دریافت میشود
 * مثلا جدول اتچ چون در سرویس جداگانه سینک میشود در اینحا فقط ردیف های سینک نشده ارسال میشود
 * ومیرود برای گرفتن دیتا از سرور
 * و چیزی را پاک نمیکند
 * <p>
 * برای هر کایند جدید
 * todo
 * تکمیل شود
 */
public class SyncPresenter extends BasePresenter<SyncView> {

    private ArrayList<String> kindStringList;
    private ListIterator<String> kindStringListIterator;
    private ArrayList<Class> classArrayListForGet = new ArrayList<>();//for get data without duplicate
    private ArrayList<Class> classArrayListForDelete = new ArrayList<>();//for delete data without duplicate
    private ArrayList<RestRequestModel> requestArrayListForGet = new ArrayList<>();//for get data
    private int kindSize, kindSizeForRepeatSync, callNumber;//size of table that we should sync
    private ISyncActivity iSyncActivity;
    private IWaitingHandler iWaitingHandler;
    private IIOSDialogeListener iiosDialogeListener;
    private IClearAllTablesListener iClearAllTablesListener;
    private ICheckUnSyncTableListener iCheckUnSyncTableListener;
    private IAllDataBaseBackupQuery iAllDataBaseBackupQuery;
    private boolean getNewData = true;
    private boolean waitForDataBaseResponse = true;//should not be reset this value,because we only need one time to wait for database

    public SyncPresenter(WeakReference<SyncView> weekView, Context context, boolean shouldHaveIntent) {
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

    }

    @Override
    protected void ClearAllPresenterListener() {
        iSyncActivity = null;
        iWaitingHandler = null;
        iiosDialogeListener = null;
        iClearAllTablesListener = null;
        iCheckUnSyncTableListener = null;
        iAllDataBaseBackupQuery = null;
    }

    @Override
    public void click() {
        iSyncActivity = new ISyncActivity() {
            @Override
            public void cancelResultFromOtherActivity() {
                cancelResultIntent();
            }

            @Override
            public void resultOkFromUpdateActivity() {
                if (checkNull()) {
                    ActivityIntents.resultOkFromSyncAct(getView().getActivity());
                }
            }
        };
        //unRegisterListener.add(iSyncActivity);
        if (checkNull()) {
            getView().setiSyncActivity(iSyncActivity);
        }
    }

    @Override
    public void onCreate(IntentModel intentModel) {
        kindStringList = intentModel.getStringList();
        kindSize = intentModel.getStringList().size();
        getNewData = intentModel.isGetNewData();
        internetConnectionChecking();
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
                            addDisposable(getFileAndLocationPermission());
                        }
                    } else {
                        if (checkNull()) {
                            getView().showMessageNoPing();
                        }
                        cancelResultIntent();
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageNoPing();
                    }
                    cancelResultIntent();
                }));
    }

    private Disposable getFileAndLocationPermission() {

        return CheckPermissionHandler
                .getFileAndLocationPermissions(context)
                .subscribe(tedPermissionResult -> {
                    if (tedPermissionResult.isGranted()) {
                        createBackupFromDataBase();
                    } else {
                        cancelResultIntent();
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_40);
                    }
                    cancelResultIntent();
                });
    }

    private void createBackupFromDataBase() {
        Log.i("arash", "doWork: SocketReceiverWorker ");
        startSyncProcess();
/*
        iAllDataBaseBackupQuery = new IAllDataBaseBackupQuery() {
            @Override
            public void success() {
                startSyncProcess();
            }

            @Override
            public void error() {
                //if we get error... not problem ... go to sync
                startSyncProcess();
            }
        };
        addDisposable(BackUpRealmHelper.makeBackUp(iAllDataBaseBackupQuery));
*/

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onBackPress() {
        //do not back if get error in add to table or in go to other activity, make cancel result
        //cancelResultIntent();
    }

//--------------------------------------------------------------------------------------

    private void startSyncProcess() {
        resetValues();
        if (kindStringListIterator.hasNext()) {
            //setupWaiting(false,Commons.SYNC_WAIT_TIME);
            handleCheckOneTable(kindStringListIterator.next());
        } else {
            if (checkNull()) {
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_41);
            }
            cancelResultIntent();
            commonWorkForThrowable("list is empty !! / startSyncProcess");

        }
    }

    private void resetValues() {
        kindSizeForRepeatSync = kindSize;
        kindStringListIterator = kindStringList.listIterator();
        callNumber = 0;
    }

    private void setupWaiting(long delayTime) {
        if (checkNull()) {
            getView().setAnimation();
        }
        iWaitingHandler = this::checkHaveUnSyncedData;
        waitingHandler.setiWaitingHandler(iWaitingHandler);
        new android.os.Handler().postDelayed(waitingHandler, delayTime);
        // unRegisterListener.add(iWaitingHandler);
    }

    //todo add other kind and create proper query for them
    private void handleCheckOneTable(String requestKind) {
        switch (requestKind) {
            case CommonKind.SYNCACCIDENT:
                addDisposable(SyncAccidentTable());
                break;
            case CommonKind.SYNCWAREHOUSEHANDLING:
                addDisposable(SyncWareHouseTable());
                break;
            case CommonKind.SYNCTIME:
                addDisposable(SyncTimeTable());
                break;
            case CommonKind.SYNCTASK:
                addDisposable(SyncTaskTable());
                break;
            case CommonKind.SYNCCARTABLE:
                addDisposable(SyncCartableTable());
                break;
            case CommonKind.SYNCCHATROOMMEMBER:
                addDisposable(SyncChatroomMemberTable());
                break;
            case CommonKind.SYNCCHATROOM:
                addDisposable(SyncChatroomTable());
                break;
            case CommonKind.SYNCMESSAGE:
                addDisposable(SyncMessageTable());
                break;
            case CommonKind.SYNCAGROFIELD:
                addDisposable(SyncAgroFiledTable());
                break;
            case CommonKind.SYNCBASECODING:
                addDisposable(SyncBaseCodingTable());
                break;
            case CommonKind.SYNCFORMREF:
                addDisposable(SyncFormRefTable());
                break;
            case CommonKind.SYNCGOODS:
                addDisposable(SyncGoodsTable());
                break;
            case CommonKind.SYNCGROUPRELATED:
                addDisposable(SyncGroupRelatedTable());
                break;
            case CommonKind.SYNCKARTABLERECIEVER:
                addDisposable(SyncKartableReceiverTable());
                break;
            case CommonKind.SYNCUTILCODE:
                addDisposable(SyncUtilCodeTable());
                break;
            case CommonKind.SYNCWEIGHING:
                addDisposable(SyncWeighing());
                break;
            case CommonKind.SYNCWAREHOUSEHANDLINGLISTOUT:
                addDisposable(SyncWarehouseOut());
                break;
            case CommonKind.ATTACH:
                SyncAttachTable();
                break;
            case CommonKind.SYNCGOODTRANCE:
                addDisposable(SyncGoodTrancTable());
                break;
            case CommonKind.SYNCPURCHASABLEGOODS:
                addDisposable(SyncPurchaseGoodsTable());
                break;
            case CommonKind.SYNCSALABLEGOODS:
                addDisposable(SyncSalableGoodsTable());
                break;
            case CommonKind.SYNCCONTACTBOOK:
                addDisposable(SyncContactBookTable());
                break;
            case CommonKind.SYNCADDRESSBOOK:
                addDisposable(SyncAddressBookTable());
                break;
            case CommonKind.SYNCCOUNTRYDIVISION:
                addDisposable(SyncCountryDivisionTable());
                break;
            case CommonKind.SYNCFUELLISTMASTER:
                SyncFuelListMasterTable();
                break;
            case CommonKind.SYNCFUELLISTDETAIL:
                addDisposable(SyncFuelListDetailTable());
                break;
            default: {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_43);
                }
                cancelResultIntent();
            }


        }
    }

//-------------------------------------------------------------------------------------

    private Disposable SyncWareHouseTable() {
        SyncOrCleanWareHouseQuery syncOrCleanWareHouseQuery = (SyncOrCleanWareHouseQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_WAREHOUSE_QUERY);
        assert syncOrCleanWareHouseQuery != null;
        return syncOrCleanWareHouseQuery.data(App.getNullRXModel()).subscribe(iModels -> {
            GlobalModel globalModel = (GlobalModel) iModels;
            if (globalModel.isMyBoolean()) {
                addToClassArrayListForGet(WarehouseHandlingTable.class);
                addToClassArrayListForDelete(WarehouseHandlingTable.class);
                addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.WAREHOUSEHANDLING, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                goForCheckData();
            } else {
                addToClassArrayListForGet(WarehouseHandlingTable.class);
                addToClassArrayListForDelete(WarehouseHandlingTable.class);
                addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.WAREHOUSEHANDLING, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                setRepeatSync();
                goForCheckData();
            }
        }, throwable -> commonWorkForThrowable("SyncWareHouseTable"));
    }

    private Disposable SyncTimeTable() {
        SyncOrCleanTimeQuery syncOrCleanTimeQuery = (SyncOrCleanTimeQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_TIME_QUERY);
        assert syncOrCleanTimeQuery != null;
        return syncOrCleanTimeQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(TimeTable.class);
                        addToClassArrayListForDelete(TimeTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.TIME, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(TimeTable.class);
                        addToClassArrayListForDelete(TimeTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.TIME, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncTimeTable"));
    }

    private Disposable SyncTaskTable() {
        SyncOrCleanTaskQuery syncOrCleanTaskQuery = (SyncOrCleanTaskQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_TASK_QUERY);
        assert syncOrCleanTaskQuery != null;
        return syncOrCleanTaskQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        //addDisposable(SyncTaskTable());
                        addToClassArrayListForDelete(TaskTable.class);
                        goForCheckData();
                    } else {
                        //no need get task data
                        addToClassArrayListForDelete(TaskTable.class);
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncTaskTable"));
    }

    private Disposable SyncCartableTable() {

        SyncOrCleanCartableQuery syncOrCleanCartableQuery = (SyncOrCleanCartableQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_CARTABLE_QUERY);
        assert syncOrCleanCartableQuery != null;
        return syncOrCleanCartableQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        //addDisposable(SyncCartableTable());
                        addToClassArrayListForGet(CartableTable.class);
                        addToClassArrayListForDelete(CartableTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.CARTABLE, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(CartableTable.class);
                        addToClassArrayListForDelete(CartableTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.CARTABLE, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncCartableTable"));
    }

    private Disposable SyncChatroomMemberTable() {

        SyncOrCleanChatroomMemberQuery syncOrCleanChatroomMemberQuery = (SyncOrCleanChatroomMemberQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_CHATROOM_MEMBER_QUERY);
        assert syncOrCleanChatroomMemberQuery != null;
        return syncOrCleanChatroomMemberQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(ChatroomMemberTable.class);
                        addToClassArrayListForDelete(ChatroomMemberTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.CHATROOMMEMBER, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(ChatroomMemberTable.class);
                        addToClassArrayListForDelete(ChatroomMemberTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.CHATROOMMEMBER, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncChatroomMemberTable"));
    }

    private Disposable SyncChatroomTable() {

        SyncOrCleanChatroomQuery syncOrCleanChatroomQuery = (SyncOrCleanChatroomQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_CHATROOM_QUERY);
        assert syncOrCleanChatroomQuery != null;
        return syncOrCleanChatroomQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(ChatroomTable.class);
                        addToClassArrayListForDelete(ChatroomTable.class);
                        addToClassArrayListForDelete(LastMessageTable.class);//pak kardane last message
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.CHATROOM, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(ChatroomTable.class);
                        addToClassArrayListForDelete(ChatroomTable.class);
                        addToClassArrayListForDelete(LastMessageTable.class);//pak kardane last message
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.CHATROOM, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncChatroomTable"));
    }

    private Disposable SyncMessageTable() {

        SyncOrCleanMessageQuery syncOrCleanMessageQuery = (SyncOrCleanMessageQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_MESSAGE_QUERY);
        assert syncOrCleanMessageQuery != null;
        return syncOrCleanMessageQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        //addDisposable(SyncMessageTable());
                        addToClassArrayListForGet(MessageTable.class);
                        addToClassArrayListForDelete(MessageTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.MESSAGE, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(MessageTable.class);
                        addToClassArrayListForDelete(MessageTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.MESSAGE, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncMessageTable"));
    }

    private Disposable SyncAgroFiledTable() {

        SyncOrCleanAgroFieldQuery syncOrCleanAgroFieldQuery = (SyncOrCleanAgroFieldQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_AGROFIELD_QUERY);
        assert syncOrCleanAgroFieldQuery != null;
        return syncOrCleanAgroFieldQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(AgroFieldTable.class);
                        addToClassArrayListForDelete(AgroFieldTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.AGROFIELD, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(AgroFieldTable.class);
                        addToClassArrayListForDelete(AgroFieldTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.AGROFIELD, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncAgroFiledTable"));
    }

    private Disposable SyncBaseCodingTable() {

        SyncOrCleanBaseCodingQuery syncOrCleanBaseCodingQuery = (SyncOrCleanBaseCodingQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_BASECODING_QUERY);
        assert syncOrCleanBaseCodingQuery != null;
        return syncOrCleanBaseCodingQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(BaseCodingTable.class);
                        addToClassArrayListForDelete(BaseCodingTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.BASECODING, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(BaseCodingTable.class);
                        addToClassArrayListForDelete(BaseCodingTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.BASECODING, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncBaseCodingTable"));
    }

    private Disposable SyncFormRefTable() {

        SyncOrCleanFormRefQuery syncOrCleanFormRefQuery = (SyncOrCleanFormRefQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_FORM_REF_QUERY);
        assert syncOrCleanFormRefQuery != null;
        return syncOrCleanFormRefQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(FormRefTable.class);
                        addToClassArrayListForDelete(FormRefTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.FORMREF, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(FormRefTable.class);
                        addToClassArrayListForDelete(FormRefTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.FORMREF, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncFormRefTable"));
    }

    private Disposable SyncGoodsTable() {

        SyncOrCleanGoodsQuery syncOrCleanGoodsQuery = (SyncOrCleanGoodsQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_GoodS_QUERY);
        assert syncOrCleanGoodsQuery != null;
        return syncOrCleanGoodsQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(GoodsTable.class);
                        addToClassArrayListForDelete(GoodsTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.GOODS, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(GoodsTable.class);
                        addToClassArrayListForDelete(GoodsTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.GOODS, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncGoodsTable"));
    }

    private Disposable SyncGroupRelatedTable() {

        SyncOrCleanGroupRelatedQuery syncOrCleanGroupRelatedQuery = (SyncOrCleanGroupRelatedQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_GROUPRELATED_QUERY);
        assert syncOrCleanGroupRelatedQuery != null;
        return syncOrCleanGroupRelatedQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(GroupRelatedTable.class);
                        addToClassArrayListForDelete(GroupRelatedTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.GROUPRELATED, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(GroupRelatedTable.class);
                        addToClassArrayListForDelete(GroupRelatedTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.GROUPRELATED, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncGroupRelatedTable"));
    }

    private Disposable SyncKartableReceiverTable() {

        SyncOrCleanKartableReceiverQuery syncOrCleanKartableReceiverQuery = (SyncOrCleanKartableReceiverQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_KARTABLERECEIVER_QUERY);
        assert syncOrCleanKartableReceiverQuery != null;
        return syncOrCleanKartableReceiverQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(KartableRecieverTable.class);
                        addToClassArrayListForDelete(KartableRecieverTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.KARTABLERECIEVER, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(KartableRecieverTable.class);
                        addToClassArrayListForDelete(KartableRecieverTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.KARTABLERECIEVER, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncKartableReceiverTable"));
    }

    private Disposable SyncUtilCodeTable() {

        SyncOrCleanUtilCodeQuery syncOrCleanUtilCodeQuery = (SyncOrCleanUtilCodeQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_UTILCODE_QUERY);
        assert syncOrCleanUtilCodeQuery != null;
        return syncOrCleanUtilCodeQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(UtilCodeTable.class);
                        addToClassArrayListForDelete(UtilCodeTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.UTILCODE, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(UtilCodeTable.class);
                        addToClassArrayListForDelete(UtilCodeTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.UTILCODE, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncUtilCodeTable"));
    }

    private Disposable SyncWeighing() {

        SyncOrCleanWeighingQuery syncOrCleanWeighingQuery = (SyncOrCleanWeighingQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_WEIGHING_QUERY);
        assert syncOrCleanWeighingQuery != null;
        return syncOrCleanWeighingQuery.data(App.getNullRXModel()).subscribe(iModels -> {
            GlobalModel globalModel = (GlobalModel) iModels;
            if (globalModel.isMyBoolean()) {
                addToClassArrayListForGet(WeighingTable.class);
                addToClassArrayListForDelete(WeighingTable.class);
                addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.WEIGHING, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                goForCheckData();
            } else {
                addToClassArrayListForGet(WeighingTable.class);
                addToClassArrayListForDelete(WeighingTable.class);
                addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.WEIGHING, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                setRepeatSync();
                goForCheckData();
            }

        }, throwable -> commonWorkForThrowable("SyncWeighing"));
    }

    private Disposable SyncWarehouseOut() {
        SyncOrCleanWarehouseOutQuery syncOrCleanWarehouseOutQuery = (SyncOrCleanWarehouseOutQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_WAREHOUSE_OUT_QUERY);
        assert syncOrCleanWarehouseOutQuery != null;
        return syncOrCleanWarehouseOutQuery.data(App.getNullRXModel()).subscribe(iModels -> {
            GlobalModel globalModel = (GlobalModel) iModels;
            if (globalModel.isMyBoolean()) {
                addToClassArrayListForDelete(WarehouseHandlingListOutTable.class);
                goForCheckData();
            } else {
                //no need to add for get
                addToClassArrayListForDelete(WarehouseHandlingListOutTable.class);
                setRepeatSync();
                goForCheckData();
            }

        }, throwable -> commonWorkForThrowable("SyncWarehouseOut"));
    }

    /**
     * no need to check unSync data, because we do not send data
     * to server(all data will be received from server)
     *
     * all data are synced or accessDenied
     */
    private void SyncFuelListMasterTable() {
        addToClassArrayListForGet(FuelListMasterTable.class);
        addToClassArrayListForDelete(FuelListMasterTable.class);
        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.FUELLISTMASTER, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
        goForCheckData();
    }



    /**
     * vazife ersal hame data haye sync nashode be
     * ohdeye startUploadAttachmentProcess ast
     * va niazi be ezafe kardan be list baraye get nist,
     * chon in table niazi be get kardan nadarad
     */
    private void SyncAttachTable() {
        addDisposable(startUploadAttachmentProcess());
        //addToClassArrayListForGet(AttachmentTable.class);
        //addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.ATTACH, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
        setRepeatSync();
        goForCheckData();
    }

    private Disposable SyncGoodTrancTable() {

        SyncOrCleanGoodTrancQuery syncOrCleanGoodTrancQuery = (SyncOrCleanGoodTrancQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_GOOD_TRANC_QUERY);
        assert syncOrCleanGoodTrancQuery != null;
        return syncOrCleanGoodTrancQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(GoodTranceTable.class);
                        addToClassArrayListForDelete(GoodTranceTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.GOODTRANCE, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(GoodTranceTable.class);
                        addToClassArrayListForDelete(GoodTranceTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.GOODTRANCE, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncGoodTrancTable"));
    }

    private Disposable SyncPurchaseGoodsTable() {
        SyncOrCleanPurchasableGoodsQuery syncOrCleanPurchasableGoodsQuery = (SyncOrCleanPurchasableGoodsQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_PURCHASABLE_GOODS_QUERY);
        assert syncOrCleanPurchasableGoodsQuery != null;
        return syncOrCleanPurchasableGoodsQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(PurchasableGoodsTable.class);
                        addToClassArrayListForDelete(PurchasableGoodsTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.PURCHASABLEGOODS, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(PurchasableGoodsTable.class);
                        addToClassArrayListForDelete(PurchasableGoodsTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.PURCHASABLEGOODS, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncPurchaseGoodsTable"));
    }

    //-------------------------------------------------------------------------
    private Disposable SyncSalableGoodsTable() {
        SyncOrCleanSalableGoodsQuery syncOrCleanSalableGoodsQuery = (SyncOrCleanSalableGoodsQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_SALABLE_GOODS_QUERY);
        assert syncOrCleanSalableGoodsQuery != null;
        return syncOrCleanSalableGoodsQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(SalableGoodsTable.class);
                        addToClassArrayListForDelete(SalableGoodsTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.SALABLEGOODS, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(SalableGoodsTable.class);
                        addToClassArrayListForDelete(SalableGoodsTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.SALABLEGOODS, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncSalableGoodsTable"));
    }

    //-------------------------------------------------------------------------
    private Disposable SyncContactBookTable() {
        SyncOrCleanContactBookQuery syncOrCleanContactBookQuery = (SyncOrCleanContactBookQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_CONTACT_BOOK_QUERY);
        assert syncOrCleanContactBookQuery != null;
        return syncOrCleanContactBookQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(ContactBookTable.class);
                        addToClassArrayListForDelete(ContactBookTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.CONTACTBOOK, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(ContactBookTable.class);
                        addToClassArrayListForDelete(ContactBookTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.CONTACTBOOK, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncContactBookTable"));
    }

    //-------------------------------------------------------------------------
    private Disposable SyncAddressBookTable() {
        SyncOrCleanAddressBookQuery syncOrCleanAddressBookQuery = (SyncOrCleanAddressBookQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_ADDRESS_BOOK_QUERY);
        assert syncOrCleanAddressBookQuery != null;
        return syncOrCleanAddressBookQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(AddressBookTable.class);
                        addToClassArrayListForDelete(AddressBookTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.ADDRESSBOOK, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(AddressBookTable.class);
                        addToClassArrayListForDelete(AddressBookTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.ADDRESSBOOK, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncAddressBookTable"));
    }

    //-------------------------------------------------------------------------
    private Disposable SyncCountryDivisionTable() {
        SyncOrCleanCountryDivisionQuery syncOrCleanCountryDivisionQuery = (SyncOrCleanCountryDivisionQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_COUNTRY_DIVISION_QUERY);
        assert syncOrCleanCountryDivisionQuery != null;
        return syncOrCleanCountryDivisionQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(CountryDivisionTable.class);
                        addToClassArrayListForDelete(CountryDivisionTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.COUNTRYDIVISION, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(CountryDivisionTable.class);
                        addToClassArrayListForDelete(CountryDivisionTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.COUNTRYDIVISION, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncCountryDivisionTable"));

    }


    //-------------------------------------------------------------------------
    private Disposable SyncFuelListDetailTable() {
        SyncOrCleanFuelListDetailQuery syncOrCleanFuelListDetailQuery = (SyncOrCleanFuelListDetailQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_FUEL_LIST_DETAIL_QUERY);
        assert syncOrCleanFuelListDetailQuery != null;
        return syncOrCleanFuelListDetailQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(FuelListDetailTable.class);
                        addToClassArrayListForDelete(FuelListDetailTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.FUELLISTDETAIL, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(FuelListDetailTable.class);
                        addToClassArrayListForDelete(FuelListDetailTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.FUELLISTDETAIL, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncFuelListDetailTable"));
    }
    //-------------------------------------------------------------------------

    private Disposable SyncAccidentTable() {
        SyncOrCleanAccidentQuery syncOrCleanAccidentQuery = (SyncOrCleanAccidentQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SYNC_OR_CLEAN_ACCIDENT_QUERY);
        assert syncOrCleanAccidentQuery != null;
        return syncOrCleanAccidentQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    GlobalModel globalModel = (GlobalModel) iModels;
                    if (globalModel.isMyBoolean()) {
                        addToClassArrayListForGet(AccidentTable.class);
                        addToClassArrayListForDelete(AccidentTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.ACCIDENT, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        goForCheckData();
                    } else {
                        addToClassArrayListForGet(TimeTable.class);
                        addToClassArrayListForDelete(TimeTable.class);
                        addToRequestArrayListForGet(RestReqDataHandler.allKindRequest(CommonKind.ACCIDENT, SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getOrganization(context)));
                        setRepeatSync();
                        goForCheckData();
                    }
                }, throwable -> commonWorkForThrowable("SyncAccidentTable"));
    }
    //-------------------------------------------------------------------------
    private void addToClassArrayListForGet(Class clazz) {
        classArrayListForGet.remove(clazz);//==> for prevent duplicate
        classArrayListForGet.add(clazz);
    }

    private void addToRequestArrayListForGet(RestRequestModel restRequestModel) {

        for (int i = 0; i < requestArrayListForGet.size(); i++) {
            if (restRequestModel.getKind().equals(requestArrayListForGet.get(i).getKind())) {
                requestArrayListForGet.remove(i);//==> for prevent duplicate
                break;
            }
        }
        requestArrayListForGet.add(restRequestModel);
    }

    private void addToClassArrayListForDelete(Class clazz) {
        classArrayListForDelete.remove(clazz);//==> for prevent duplicate
        classArrayListForDelete.add(clazz);
    }

    //-------------------------------------------------------------------------
    private void commonWorkForThrowable(String throwableMessage) {
        if (checkNull()) {
            getView().showMessageSomeProblems(CommonsLogErrorNumber.error_42);
        }
        ThrowableLoggerHelper.logMyThrowable("SyncPresenter/" + throwableMessage);
        cancelResultIntent();
    }

    //-------------------------------------------------------------------------
    private void setRepeatSync() {
        kindSizeForRepeatSync--;
    }

    private void changeCallNumber() {
        callNumber++;
    }

    private void goForCheckData() {
        /*changeCallNumber();
        if(kindStringListIterator.hasNext()){
            handleCheckOneTable(kindStringListIterator.next());
        } else if (kindSizeForRepeatSync <= 0) {
            goForGetData();
        } else if (callNumber == kindSize) {
            showConfirmMessage();
        }*/

        changeCallNumber();
        if (kindStringListIterator.hasNext()) {
            handleCheckOneTable(kindStringListIterator.next());
        } else if (kindSizeForRepeatSync <= 0) {
            goForGetData();
        } else if (callNumber == kindSize) {
            setupWaiting(kindStringList.size() * 1000);
        }
    }

    private void checkHaveUnSyncedData() {
        Log.i("arash", "checkHaveUnSyncedData: start unsync");
        iCheckUnSyncTableListener = new ICheckUnSyncTableListener() {
            @Override
            public void haveUnsyncData() {
                Log.i("arash", "checkHaveUnSyncedData: start haveUnsyncData");
                if(waitForDataBaseResponse){
                    makeFalseWaitingForDatabase();
                    waitForDataBaseResponse();
                } else {
                    showConfirmMessage();
                }
            }

            @Override
            public void haveNotUnsyncData() {
                Log.i("arash", "checkHaveUnSyncedData: start haveNotUnsyncData");
                goForGetData();
            }

            @Override
            public void uploadProcess() {
                if (checkNull()) {
                    Log.i("arash", "checkHaveUnSyncedData: start uploadProcess");
                    getView().showMessageUnSyncUploadData(context);
                }
            }

            @Override
            public void error() {
                //if we get error, we should check again
                Log.i("arash", "checkHaveUnSyncedData: start error");
                if(checkNull()){
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_133);
                }
                showConfirmMessage();
            }
        };

        //niazi be check kardane connection be node server nist, agar un sync data dashte bashim, user nemitavanad jolotar beravad
        addDisposable(CheckUnSyncData.haveUnSync(iCheckUnSyncTableListener, AllTBLListHelper.getListOfAllTables()));

        if (!PingHandler.checkPingNode()) {
            if (checkNull()) {
                getView().showMessageNodeServerDisconnecting(context);
            }
        }
    }

   private void makeFalseWaitingForDatabase(){
        waitForDataBaseResponse = false;
   }

    private void waitForDataBaseResponse() {
        new CountDownTimer(Commons.SYNC_WAIT_TIME_FOR_DATABASE, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                startSyncProcess();
            }

        }.start();
    }

    private void showConfirmMessage() {

        iiosDialogeListener = new IIOSDialogeListener() {
            @Override
            public void confirm() {
                startSyncProcess();
            }

            @Override
            public void cancle() {
                cancelResultIntent();
            }
        };
        if (checkNull()) {
            getView().showConfirmMessage(iiosDialogeListener);
        }


    }

    private void goForGetData() {
        if (getNewData) {
            iClearAllTablesListener = new IClearAllTablesListener() {
                @Override
                public void done() {
                    if (checkNull()) {
                        goUpdateTableAct(requestArrayListForGet, classArrayListForGet);
                        Log.i("arash", "goForGetData: " + requestArrayListForGet.size() + "");
                    }
                }

                @Override
                public void error() {
                    commonWorkForThrowable("error in clear table");
                }
            };
            GlobalModel globalModel = new GlobalModel();
            //todo add better condition
            if(classArrayListForDelete.size()>20){
                //for clear evacuate in global sync, not in chatroom or warehouse sync or ...
                classArrayListForDelete.add(EvacuatePacketTable.class);
            }
            globalModel.setClassArrayList(classArrayListForDelete);
            addDisposable(ClearAllTablesHandler.clear(iClearAllTablesListener, globalModel));

        } else {
            if (checkNull()) {
                ActivityIntents.resultOkFromSyncAct(getView().getActivity());
            }
        }

    }

}
