package ir.fararayaneh.erp.activities.report;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.realm.Realm;
import ir.fararayaneh.erp.IBase.common_base.BasePresenterWithRecycle;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.adaptors.reports_adaptor.CommonClickRowReport;
import ir.fararayaneh.erp.adaptors.reports_adaptor.IClickReportsRecAdaptor;
import ir.fararayaneh.erp.adaptors.reports_adaptor.ReportsDBRecAdaptor;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.CommonUtilCode;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsFormCode;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.offline.SetFormCodeToIntentModel;
import ir.fararayaneh.erp.data.data_providers.queries.fuel_list_detail.FindMasterDriverIdFuelListDetailQuery;
import ir.fararayaneh.erp.data.data_providers.queries.recycle_list_view_queries.report_quries.ReportQueries;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.BaseGetTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.CartableTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.FuelDetailTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.FuelMasterTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.GoodTransTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.TaskTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.TimeTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.WeighingTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.GoodSUOMModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CartableTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListDetailTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListMasterTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.GoodTranceTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TaskTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TimeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WeighingTable;
import ir.fararayaneh.erp.utils.EditTextCheckHelper;
import ir.fararayaneh.erp.utils.data_handler.CheckRowEditSelectValidation;
import ir.fararayaneh.erp.utils.data_handler.ComboDataListHandler;
import ir.fararayaneh.erp.utils.data_handler.FormIdFromFormCodeHandler;
import ir.fararayaneh.erp.utils.data_handler.GoodSUOMJsonHelper;
import ir.fararayaneh.erp.utils.data_handler.IComboListener;
import ir.fararayaneh.erp.utils.data_handler.IFormCodeListener;
import ir.fararayaneh.erp.utils.data_handler.IFormIdListener;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntentFactoryHandler;
import ir.fararayaneh.erp.utils.intent_handler.IntentReceiverHandler;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;
import ir.fararayaneh.erp.utils.waiting.IWaitingHandler;

import static ir.fararayaneh.erp.commons.Commons.DELAY_TIME_FOR_PRIMITIVE_DATA;
import static ir.fararayaneh.erp.commons.Commons.DELAY_TIME_FOR_SEARCH_DATA;


/**
 * -------------------------------------------------------------------------
 * alave bar takmil karadan todo dar inclass, todo dar adaptor ham takmil shavad
 * <p>
 * 1-get all rows of a table and show to user (check user can edit(@see ##) and see attach(@see ###) those rows or can not)
 * 2-in changeListener of tables :
 * in insert mode ==>> check rows that those rows are accessDenied or other edited(show in two category : NEW and ACCESSDENIED)
 * in edit mode ==>> check rows that  are accessDenied or other edit state(show in two category : EDIT and ACCESSDENIED)
 * in delete mode ==>> we do not check any things , because no row were be deleted physically
 * <p>
 * ##
 * when row is editable that have 4 condition:
 * 1- statusId != trash,finalized,archive (see -->>  checkEditValidation() function)
 * 2- should have form (see -->> needValidationFormCode = true in ActivityIntentFactoryHandler)
 * 3- should have guid
 * 4- syncState != accessDenied (see -->>  checkEditValidation() function)
 * <p>
 * ###
 * and column guid(no need to guid after adaptor were be changed to realm adaptor, because we do not need to query
 * on guid to find attach, all data are here,but need to guid for show edit and new rows)
 * and only can go to see attach not to edit (add-delete) them
 * (we need guid because all record have not id when shows their report)
 * ###
 * in this activity no need to listen to result of album activity
 * because we can not add and delete attachment from here
 */
public class ReportsPresenter extends BasePresenterWithRecycle<ReportsView> {


    private ReportsDBRecAdaptor reportsDBRecAdaptor;
    private IClickReportsRecAdaptor clickReportsRecAdaptor;
    private IComboListener iComboListener;
    private IWaitingHandler iWaitingHandler;
    private IReportClickListener iReportClickListener;
    private IFormCodeListener iFormCodeListener;
    private IFormIdListener iFormIdListener;
    private String myFormCode = Commons.NULL_INTEGER,
            myFormId = Commons.NULL_INTEGER;//other report no need to formId(only goodTrans family !)
    private Realm realm;
    private ReportQueries reportQueries;
    private ArrayList<String> b5hcStatusNameList = new ArrayList<>();
    private ArrayList<String> b5hcStatusIdList = new ArrayList<>();
    private String filterText = Commons.SPACE;
    private Date startDate, endDate;//for filter result
    private ArrayList<String> filteredIdGUID;//for filter result
    private IntentModel intentModel;


    public ReportsPresenter(WeakReference<ReportsView> weekView, Context context, boolean shouldHaveIntent) {
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
        clickReportsRecAdaptor = null;
        iReportClickListener = null;
        iFormCodeListener = null;
        iFormIdListener = null;
        iComboListener = null;
        iWaitingHandler = null;
        //should be last
        closeRealm();
    }

    @Override
    public void click() {

        iReportClickListener = new IReportClickListener() {
            @Override
            public void filterText(Intent data) {
                if (data != null) {
                    filterText = IntentReceiverHandler.
                            getIntentData(Objects.requireNonNull(data.getExtras())).getSomeString();
                    createDataFromSearch();
                } else {
                    filterText = Commons.SPACE;
                    createDataFromSearch();
                }
            }

            @Override
            public void filterTime(Intent data) {
                if (data != null) {
                    makeAllDateNull();//for cancel before filter
                    startDate = IntentReceiverHandler.
                            getIntentData(Objects.requireNonNull(data.getExtras())).getStartDate();
                    endDate = IntentReceiverHandler.
                            getIntentData(Objects.requireNonNull(data.getExtras())).getEndDate();
                    createDataFromSearch();
                } else {
                    makeAllDateNull();
                    createDataFromSearch();
                }
            }
        };

        clickReportsRecAdaptor = new IClickReportsRecAdaptor() {
            @Override
            public void clickConfigs(IModels iModels, int witchClick, boolean clickOnNormalViewType) {
                refineBadjeCartable(clickOnNormalViewType);
                switch (witchClick) {
                    case CommonClickRowReport.IMAGE_ATTACH_CLICKED:
                        rowAttachClicked(iModels);
                        break;
                    case CommonClickRowReport.EDIT_CLICKED:
                        rowEditClicked(iModels);
                        break;
                    case CommonClickRowReport.FORBIDDEN_CLICKED:
                        rowForbiddenClicked();
                        break;
                }
            }

            @Override
            public void notifyNewRow(int position) {
                if (checkNull()) {
                    ToastMessage.showGreenSnackWithAction(getView().getActivity(), context.getString(R.string.new_row_record), context.getString(R.string.see), view -> {
                        if (checkNull()) {
                            getView().recycleScrollHandler(position);
                        }
                    });
                }
            }

            @Override
            public void notifyEditedRow(int position) {
                if (checkNull()) {
                    ToastMessage.showGreenSnackWithAction(getView().getActivity(), context.getString(R.string.edit_row_record), context.getString(R.string.see), view -> {
                        if (checkNull()) {
                            getView().recycleScrollHandler(position);
                        }
                    });
                }
            }
        };


        reportsDBRecAdaptor.setClickReportsRecAdaptor(clickReportsRecAdaptor);
        if (checkNull()) {
            getView().setiReportClickListener(iReportClickListener);
        }
    }

    private void makeAllDateNull() {
        startDate = null;
        endDate = null;
        filteredIdGUID = null;
    }

    private void createDataFromSearch() {

        if (checkNull()) {
            getView().showHideProgress(true);
        }
        iWaitingHandler = () -> {
            //todo add other form code ((report code) and add in class ActivityIntentFactoryHandler and add to adaptor)
            if ((startDate == null || endDate == null) && filteredIdGUID != null) {
                makeAllDateNull();
                createDataFromSearch();
            } else if (startDate != null && endDate != null && filteredIdGUID == null) {
                switch (myFormCode) {
                    case CommonsFormCode.CARTABLE_REPORT_FORM_CODE:
                        CartableTimeFilteredIdListQuery cartableTimeFilteredIdListQuery =
                                (CartableTimeFilteredIdListQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.CARTABLE_TIME_FILTERED_ID_LIST_QUERY);
                        assert cartableTimeFilteredIdListQuery != null;
                        addDisposable(getTimeFilterIdGuidList(cartableTimeFilteredIdListQuery));
                        break;
                    case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
                    case CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
                    case CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
                    case CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE:
                    case CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
                    case CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE:
                    case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
                    case CommonsFormCode.PRODUCTION_REPORT_FORM_CODE:
                        GoodTransTimeFilteredIdListQuery goodTransTimeFilteredIdListQuery =
                                (GoodTransTimeFilteredIdListQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GOOD_TRANS_TIME_FILTERED_ID_LIST_QUERY);
                        assert goodTransTimeFilteredIdListQuery != null;
                        addDisposable(getTimeFilterIdGuidList(goodTransTimeFilteredIdListQuery));
                        break;
                    case CommonsFormCode.GOODS_REPORT_FORM_CODE:
                        //no need to time filter here
                        makeAllDateNull();
                        createDataFromSearch();
                        break;
                    case CommonsFormCode.TASK_REPORT_FORM_CODE:
                        TaskTimeFilteredIdListQuery taskTimeFilteredIdListQuery =
                                (TaskTimeFilteredIdListQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.TASK_TIME_FILTERED_ID_LIST_QUERY);
                        assert taskTimeFilteredIdListQuery != null;
                        addDisposable(getTimeFilterIdGuidList(taskTimeFilteredIdListQuery));
                        break;
                    case CommonsFormCode.TIME_REPORT_FORM_CODE:
                        TimeTimeFilteredIdListQuery timeTimeFilteredIdListQuery =
                                (TimeTimeFilteredIdListQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.TIME_TIME_FILTERED_ID_LIST_QUERY);
                        assert timeTimeFilteredIdListQuery != null;
                        addDisposable(getTimeFilterIdGuidList(timeTimeFilteredIdListQuery));
                        break;
                    case CommonsFormCode.WEIGHTING_REPORT_FORM_CODE:
                        WeighingTimeFilteredIdListQuery weighingTimeFilteredIdListQuery =
                                (WeighingTimeFilteredIdListQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.WEIGHING_TIME_FILTERED_ID_LIST_QUERY);
                        assert weighingTimeFilteredIdListQuery != null;
                        addDisposable(getTimeFilterIdGuidList(weighingTimeFilteredIdListQuery));
                        break;
                    case CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE:
                        FuelDetailTimeFilteredIdListQuery fuelDetailTimeFilteredIdListQuery = (FuelDetailTimeFilteredIdListQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.FUEL_DETAIL_TIME_FILTERED_ID_LIST_QUERY);
                        assert fuelDetailTimeFilteredIdListQuery != null;
                        addDisposable(getTimeFilterIdGuidList(fuelDetailTimeFilteredIdListQuery));
                        break;
                    case CommonsFormCode.FUEL_MASTER_REPORT_FORM_CODE:
                        FuelMasterTimeFilteredIdListQuery fuelMasterTimeFilteredIdListQuery =
                                (FuelMasterTimeFilteredIdListQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.FUEL_MASTER_TIME_FILTERED_ID_LIST_QUERY);
                        assert fuelMasterTimeFilteredIdListQuery != null;
                        addDisposable(getTimeFilterIdGuidList(fuelMasterTimeFilteredIdListQuery));
                        break;

                }
            } else {
                //todo add other form code ((report code) and add in class ActivityIntentFactoryHandler and add to adaptor)
                switch (myFormCode) {
                    case CommonsFormCode.CARTABLE_REPORT_FORM_CODE:
                        addDisposable(getSearchCartableResult());
                        break;
                    case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
                    case CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
                    case CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
                    case CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE:
                    case CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
                    case CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE:
                    case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
                    case CommonsFormCode.PRODUCTION_REPORT_FORM_CODE:
                        addDisposable(getSearchGoodTransResult());
                        break;
                    case CommonsFormCode.GOODS_REPORT_FORM_CODE:
                        addDisposable(getSearchGoodsResult());
                        break;
                    case CommonsFormCode.TASK_REPORT_FORM_CODE:
                        addDisposable(getSearchTaskResult());
                        break;
                    case CommonsFormCode.TIME_REPORT_FORM_CODE:
                        addDisposable(getSearchTimeResult());
                        break;
                    case CommonsFormCode.WEIGHTING_REPORT_FORM_CODE:
                        addDisposable(getSearchWeightingResult());
                        break;
                    case CommonsFormCode.FUEL_MASTER_REPORT_FORM_CODE:
                        addDisposable(getSearchFuelMasterResult());
                        break;
                    case CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE:
                        addDisposable(getSearchFuelDetailResult());
                        break;
                }
            }
        };

        waitingHandler.setiWaitingHandler(iWaitingHandler);
        new android.os.Handler().postDelayed(waitingHandler, DELAY_TIME_FOR_SEARCH_DATA);
    }

    @Override
    public void onCreate(IntentModel intentModel) {
        if (checkNull()) {
            getView().showHideProgress(true);
        }
        this.intentModel = intentModel;
        myFormCode = intentModel.getSomeString2();//-->> we need myFormCode before super
        reportsDBRecAdaptor = new ReportsDBRecAdaptor(true, myFormCode, context, true, null);
        super.onCreate(intentModel);
        setupToolbar();
        addDisposable(getAllStatusCode());
    }

    private Disposable getAllStatusCode() {

        iComboListener = new IComboListener() {
            @Override
            public void onData(ArrayList<ArrayList<String>> listNameList, ArrayList<ArrayList<String>> listIdList) {
                //here we have only have one list
                b5hcStatusNameList = listNameList.get(0);
                b5hcStatusIdList = listIdList.get(0);
            }

            @Override
            public void onError() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_67);
                }
            }
        };
        return ComboDataListHandler.getOneUtilCoddingData(CommonUtilCode.VCS, iComboListener);
    }

    @Override
    protected void setRecyclAdaptor() {
        if (checkNull()) {
            getView().setRecycleAdaptor(reportsDBRecAdaptor);
        }
    }

    @Override
    protected void workForLoadPage() {

        switch (myFormCode) {
            case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
                workForLoadFormId(CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE);
                break;

            case CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
                workForLoadFormId(CommonsFormCode.PERMANENT_RECEIPT_GOODS_FORM_CODE);
                break;

            case CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
                workForLoadFormId(CommonsFormCode.WAREHOUSE_TRANSFERENCE_FORM_CODE);
                break;

            case CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE:
                workForLoadFormId(CommonsFormCode.RECEIPT_GOODS_FORM_CODE);
                break;

            case CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
                workForLoadFormId(CommonsFormCode.BUY_SERVICES_INVOICE_FORM_CODE);
                break;

            case CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE:
                workForLoadFormId(CommonsFormCode.BUYER_REQUEST_FORM_CODE);
                break;

            case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
                workForLoadFormId(CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE);
                break;

            case CommonsFormCode.PRODUCTION_REPORT_FORM_CODE:
                workForLoadFormId(CommonsFormCode.PRODUCTION_FORM_CODE);
                break;
            default:
                workForLoadFormId(myFormCode);//other report no need to formId

        }

    }

    private void loadPrimitiveData() {
        iWaitingHandler = () -> {
            //todo add other form code ((report code) and add in class ActivityIntentFactoryHandler and add to adaptor)
            switch (myFormCode) {
                case CommonsFormCode.CARTABLE_REPORT_FORM_CODE:
                    addDisposable(getPrimitiveCartableResult());
                    break;
                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
                case CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
                case CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
                case CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE:
                case CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
                case CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE:
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
                case CommonsFormCode.PRODUCTION_REPORT_FORM_CODE:
                    addDisposable(getPrimitiveGoodTransResult());
                    break;
                case CommonsFormCode.GOODS_REPORT_FORM_CODE:
                    addDisposable(getPrimitiveGoodsResult());
                    break;
                case CommonsFormCode.TASK_REPORT_FORM_CODE:
                    addDisposable(getPrimitiveTaskResult());
                    break;
                case CommonsFormCode.TIME_REPORT_FORM_CODE:
                    addDisposable(getPrimitiveTimeResult());
                    break;
                case CommonsFormCode.WEIGHTING_REPORT_FORM_CODE:
                    addDisposable(getPrimitiveWeightingResult());
                    break;
                case CommonsFormCode.FUEL_MASTER_REPORT_FORM_CODE:
                    addDisposable(getPrimitiveFuelMasterResult());
                    break;
                case CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE:
                    addDisposable(getPrimitiveFuelDetailResult());
                    break;
            }
        };
        waitingHandler.setiWaitingHandler(iWaitingHandler);
        new android.os.Handler().postDelayed(waitingHandler, DELAY_TIME_FOR_PRIMITIVE_DATA);
    }

    @Override
    protected void setLazyLoad() {
        if (checkNull()) {
            getView().loadAtEndList(() -> {
                //no need to any work
            });
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

    private void setupToolbar() {
        if (checkNull()) {
            //todo add other form code ((report code) and add in class ActivityIntentFactoryHandler and add to adaptor)
            switch (myFormCode) {
                case CommonsFormCode.CARTABLE_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.cartable_report));
                    break;
                case CommonsFormCode.TASK_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.task_report));
                    break;
                case CommonsFormCode.TIME_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.time_report));
                    break;
                case CommonsFormCode.WEIGHTING_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.weiging_report));
                    break;
                case CommonsFormCode.GOODS_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.goods_report));
                    break;
                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.request_goods_from_warehouse_report));
                    break;
                case CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.permanent_receipt_goods_report));
                    break;
                case CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.warehouse_transfer_report));
                    break;
                case CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.receipt_goods_report));
                    break;
                case CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.buy_service_invoice_report));
                    break;
                case CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.buyer_request_report));
                    break;
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.delivery_proceeding_report));
                    break;
                case CommonsFormCode.PRODUCTION_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.product_report));
                    break;
                case CommonsFormCode.FUEL_MASTER_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.Fuel_report));
                    break;
                case CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.Fuel_consumer_report));
                    break;
            }
        }
    }

    /**
     * if we can not see attach or have not attach column,we do not come here
     */
    //todo add other form code ((report code) and add in class ActivityIntentFactoryHandler and add to adaptor)
    private void rowAttachClicked(IModels iModels) {

        if (checkNull()) {
            switch (myFormCode) {
                case CommonsFormCode.CARTABLE_REPORT_FORM_CODE:
                    CartableTable cartableTable = (CartableTable) iModels;
                    goAttachAlbumActivity(false, false, cartableTable.getAttach());
                    break;
                case CommonsFormCode.TASK_REPORT_FORM_CODE:
                    TaskTable taskTable = (TaskTable) iModels;
                    goAttachAlbumActivity(false, false, taskTable.getAttach());
                    break;
                case CommonsFormCode.GOODS_REPORT_FORM_CODE:
                    GoodsTable goodsTable = (GoodsTable) iModels;
                    goAttachAlbumActivity(false, false, goodsTable.getAttach());
                    break;
                case CommonsFormCode.TIME_REPORT_FORM_CODE:
                case CommonsFormCode.WEIGHTING_REPORT_FORM_CODE:
                case CommonsFormCode.FUEL_MASTER_REPORT_FORM_CODE:
                case CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE:
                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
                case CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
                case CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
                case CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE:
                case CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
                case CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE:
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
                case CommonsFormCode.PRODUCTION_REPORT_FORM_CODE:
                    //no need to any work, user can not come here
                    break;
            }
        }
    }

    //todo add other form code ((report code) and add in class ActivityIntentFactoryHandler and add to adaptor)

    /**
     * if user can click on edit, here we check HIS/HER validation
     */
    private void rowEditClicked(IModels iModels) {
        if (checkNull()) {
            getView().showHideProgress(true);
            switch (myFormCode) {
                case CommonsFormCode.CARTABLE_REPORT_FORM_CODE:
                    //only show full data,do not go to edit
                    CartableTable cartableTable = (CartableTable) iModels;
                    if (checkNull()) {
                        ToastMessage.showSimpleScrollAbleDialog(getView().getActivity(), cartableTextMaker(cartableTable.getSenderName(), cartableTable.getSubject()));
                    }
                    break;
                case CommonsFormCode.TASK_REPORT_FORM_CODE:
                    TaskTable taskTable = (TaskTable) iModels;
                    if (CheckRowEditSelectValidation.checkEditValidation(b5hcStatusIdList, b5hcStatusNameList, taskTable.getB5HCStatusId(), taskTable.getSyncState(), taskTable.getActivityState())) {
                        ActivityIntentFactoryHandler.goToProperActivity(getView().getActivity(), CommonsFormCode.TASK_FORM_CODE, SetFormCodeToIntentModel.setFormCode(CommonsFormCode.TASK_FORM_CODE, taskTable.getGuid()), true, true);
                    } else {
                        if (checkNull()) {
                            getView().showMessageNoEditPermission();
                        }
                    }
                    break;
                case CommonsFormCode.TIME_REPORT_FORM_CODE:
                    TimeTable timeTable = (TimeTable) iModels;
                    if (CheckRowEditSelectValidation.checkEditValidation(b5hcStatusIdList, b5hcStatusNameList, timeTable.getB5HCStatusId(), timeTable.getSyncState(), timeTable.getActivityState())) {
                        workForLoadFormCodeAndGoToEditAct(timeTable.getB5formRefId(), timeTable.getGuid());
                    } else {
                        if (checkNull()) {
                            getView().showMessageNoEditPermission();
                        }
                    }
                    break;
                case CommonsFormCode.WEIGHTING_REPORT_FORM_CODE:
                    WeighingTable weighingTable = (WeighingTable) iModels;
                    if (CheckRowEditSelectValidation.checkEditValidation(b5hcStatusIdList, b5hcStatusNameList, weighingTable.getB5HCStatusId(), weighingTable.getSyncState(), weighingTable.getActivityState())) {
                        ActivityIntentFactoryHandler.goToProperActivity(getView().getActivity(), CommonsFormCode.WEIGHTING_FORM_CODE, SetFormCodeToIntentModel.setFormCode(CommonsFormCode.WEIGHTING_FORM_CODE, weighingTable.getGuid()), true, true);
                    } else {
                        if (checkNull()) {
                            getView().showMessageNoEditPermission();
                        }
                    }
                    break;
                case CommonsFormCode.GOODS_REPORT_FORM_CODE:
                    //only show full data,do not go to edit
                    GoodsTable goodsTable = (GoodsTable) iModels;
                    ArrayList<GoodSUOMModel> goodSUOMModelArrayList = GoodSUOMJsonHelper.getGoodSUOMArray(goodsTable.getGoodSUOMList());
                    if (checkNull()) {
                        ToastMessage.showSimpleScrollAbleDialog(getView().getActivity(), goodsTextMaker(Objects.requireNonNull(goodsTable).getName(), goodsTable.getCode(), goodSUOMModelArrayList.get(0).getUnitName(), goodsTable.getBrandName(), goodsTable.getLatinName(), goodsTable.getTechInfo(), goodsTable.getNationalityCode(), goodSUOMModelArrayList.get(0).getWidth(), goodSUOMModelArrayList.get(0).getHeight(), goodSUOMModelArrayList.get(0).getLength(), goodSUOMModelArrayList.get(0).getWeight(), goodsTable.getSerial()));
                    }
                    break;
                case CommonsFormCode.FUEL_MASTER_REPORT_FORM_CODE:
                    FuelListMasterTable fuelListMasterTable = (FuelListMasterTable) iModels;
                    ActivityIntentFactoryHandler.goToProperActivity(getView().getActivity(), CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE, SetFormCodeToIntentModel.setFormCode(CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE, fuelListMasterTable.getId()), true, false);
                    break;
                case CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE:
                    FuelListDetailTable fuelListDetailTable = (FuelListDetailTable) iModels;
                    addDisposable(checkValidationFuelDetail(fuelListDetailTable.getMasterId(), fuelListDetailTable.getId(),fuelListDetailTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED)));
                    break;
                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
                case CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
                case CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
                case CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE:
                case CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
                case CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE:
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
                case CommonsFormCode.PRODUCTION_REPORT_FORM_CODE:
                    GoodTranceTable goodTranceTable = (GoodTranceTable) iModels;
                    if (CheckRowEditSelectValidation.checkEditValidation(b5hcStatusIdList, b5hcStatusNameList, goodTranceTable.getB5HCStatusId(), goodTranceTable.getSyncState(), goodTranceTable.getActivityState())) {
                        workForLoadFormCodeAndGoToEditAct(goodTranceTable.getB5FormRefId(), goodTranceTable.getGuid());
                    } else {
                        if (checkNull()) {
                            getView().showMessageNoEditPermission();
                        }
                    }
                    break;
            }
            getView().showHideProgress(false);
        }
    }

    private Disposable checkValidationFuelDetail(String masterId, String id, boolean isAccessDenied) {
        if(isAccessDenied){
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageNoEditPermission();
            }
            return  null;
        } else {
            if (checkNull()) {
                getView().showHideProgress(true);
            }
            GlobalModel globalModel = new GlobalModel();
            globalModel.setMyString(masterId);
            FindMasterDriverIdFuelListDetailQuery findMasterDriverIdFuelListDetailQuery = (FindMasterDriverIdFuelListDetailQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.FIND_MASTER_DRIVER_ID_FUEL_LIST_DETAIL_QUERY);
            assert findMasterDriverIdFuelListDetailQuery != null;
            return findMasterDriverIdFuelListDetailQuery.data(globalModel)
                    .subscribe(iModels -> {
                        Log.i("arash", "checkValidationFuelDetail: +" + globalModel.getUserId());
                        if (globalModel.getUserId().equals(SharedPreferenceProvider.getUserId(context))) {
                            ActivityIntentFactoryHandler.goToProperActivity(getView().getActivity(), CommonsFormCode.FUEL_DETAIL_FORM_CODE, SetFormCodeToIntentModel.setFormCode(CommonsFormCode.FUEL_DETAIL_FORM_CODE, id), true, false);
                        } else {
                            if (checkNull()) {
                                getView().showHideProgress(false);
                                getView().showMessageNoEditPermission();
                            }
                        }
                    }, throwable -> {
                        if (checkNull()) {
                            getView().showHideProgress(false);
                            getView().showMessageSomeProblems(CommonsLogErrorNumber.error_132);
                        }

                    });
        }

    }

    private String cartableTextMaker(String senderName, String subject) {
        return EditTextCheckHelper.concatHandler(
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.sender), senderName),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.subject), subject)
        );
    }

    private String goodsTextMaker(String goodName, String code, String unitName,
                                  String brandName, String latinName, String techInfo,
                                  String nationalityCode, String width, String height,
                                  String length, String weight, String serial) {
        return EditTextCheckHelper.concatHandler(
                goodName,
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_code), code),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_main_unit_name), unitName),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.brand), brandName),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.latinName), latinName),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.tech_info), techInfo),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.nationality_code), nationalityCode),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.width), width),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.length), length),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.height), height),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.weight), weight),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.serial), serial)
        );
    }

    private void rowForbiddenClicked() {
        if (checkNull()) {
            getView().showMessageNoPermission();
        }
    }

    //----------------------------------------------------------------------------------------------
    private void refineBadjeCartable(boolean clickOnNormalViewType) {
        if (myFormCode.equals(CommonsFormCode.CARTABLE_REPORT_FORM_CODE) && !clickOnNormalViewType) {
            SharedPreferenceProvider.setBadgeNumber(context, 0, -1);
        }
    }

    private Realm getRealm() {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        return realm;
    }

    private ReportQueries getReportQueriesInstance() {
        if (reportQueries == null) {
            reportQueries = (ReportQueries) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.REPORT_QUERIES);
        }
        return reportQueries;
    }

    private void closeRealm() {
        if (realm != null) {
            if (!realm.isClosed()) {
                realm.removeAllChangeListeners();
                RealmCloseHelper.closeRealm(realm);
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    private Disposable getPrimitiveCartableResult() {
        return getReportQueriesInstance()
                .primitiveCartableQuery(getRealm(), filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }

                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_74);
                    }
                });
    }

    private Disposable getSearchCartableResult() {
        return getReportQueriesInstance().searchCartableQuery(getRealm(), filterText, filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_75);
                    }
                });
    }

    //----------------------------------------------------------------------------------------------
    private Disposable getPrimitiveGoodsResult() {
        return getReportQueriesInstance()
                .primitiveGoodsQuery(getRealm())
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }

                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_73);
                    }
                });
    }

    private Disposable getSearchGoodsResult() {
        return getReportQueriesInstance()
                .searchGoodsQuery(getRealm(), filterText)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_72);
                    }
                });
    }

    //----------------------------------------------------------------------------------------------
    private Disposable getPrimitiveTaskResult() {
        return getReportQueriesInstance()
                .primitiveTaskQuery(getRealm(), filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }

                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_76);
                    }
                });
    }

    private Disposable getSearchTaskResult() {
        return getReportQueriesInstance()
                .searchTaskQuery(getRealm(), filterText, filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_77);
                    }
                });
    }

    //----------------------------------------------------------------------------------------------
    private Disposable getPrimitiveWeightingResult() {
        return getReportQueriesInstance()
                .primitiveWeightingQuery(getRealm(), filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }

                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_78);
                    }
                });
    }

    private Disposable getSearchWeightingResult() {
        return getReportQueriesInstance()
                .searchWeightingQuery(getRealm(), filterText, filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_79);
                    }
                });
    }

    //----------------------------------------------------------------------------------------------
    private Disposable getPrimitiveFuelMasterResult() {
        return getReportQueriesInstance()
                .primitiveFuelMasterQuery(getRealm(), filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_127);
                    }
                });
    }



    private Disposable getSearchFuelMasterResult() {
        return getReportQueriesInstance()
                .searchFuelMasterQuery(getRealm(), filterText, filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_126);
                    }
                });
    }

    //----------------------------------------------------------------------------------------------

    private Disposable getPrimitiveFuelDetailResult() {
        return getReportQueriesInstance()
                .primitiveFuelDetailQuery(getRealm(), intentModel.getSomeString(), filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_129);
                    }
                });
    }
    private Disposable getSearchFuelDetailResult() {
        return getReportQueriesInstance()
                .searchFuelDetailQuery(getRealm(), intentModel.getSomeString(), filterText, filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_128);
                    }
                });
    }

    //----------------------------------------------------------------------------------------------
    private Disposable getPrimitiveTimeResult() {
        return getReportQueriesInstance()
                .primitiveTimeQuery(getRealm(), filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }

                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_80);
                    }
                });
    }

    private Disposable getSearchTimeResult() {
        return getReportQueriesInstance()
                .searchTimeQuery(getRealm(), filterText, filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_81);
                    }
                });
    }

    //----------------------------------------------------------------------------------------------
    private Disposable getPrimitiveGoodTransResult() {
        //we sure we have valid formId, otherwise we can not received here
        return getReportQueriesInstance()
                .primitiveGoodTransQuery(getRealm(), myFormId, filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_82);
                    }
                });
    }

    private Disposable getSearchGoodTransResult() {
        //we sure we have valid formId, otherwise we can not received here
        return getReportQueriesInstance().searchGoodTransQuery(getRealm(), filterText, myFormId, filteredIdGUID)
                .subscribe(orderedRealmCollections -> {
                    if (reportsDBRecAdaptor != null) {
                        reportsDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_83);
                    }
                });
    }

    //----------------------------------------------------------------------------------------------
    private void workForLoadFormCodeAndGoToEditAct(String formId, String guid) {
        if (checkNull()) {
            getView().showHideProgress(true);
            iFormCodeListener = new IFormCodeListener() {
                @Override
                public void onGetData(String formCode) {
                    if (checkNull() && !formCode.equals(Commons.NULL_INTEGER)) {
                        ActivityIntentFactoryHandler.goToProperActivity(getView().getActivity(), formCode, SetFormCodeToIntentModel.setFormCode(formCode, guid), true, true);
                    } else {
                        if (checkNull()) {
                            getView().showHideProgress(false);
                            getView().showMessageSomeProblems(CommonsLogErrorNumber.error_35);
                        }
                    }
                }

                @Override
                public void onError() {
                    if (checkNull()) {
                        getView().showHideProgress(false);
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_36);
                    }
                }
            };
            addDisposable(FormIdFromFormCodeHandler.getFormCode(formId, iFormCodeListener));
        }
    }
    //----------------------------------------------------------------------------------------------

    /**
     * @param formFormCode formCode of form not report
     */
    private void workForLoadFormId(String formFormCode) {
        if (checkNull()) {
            getView().showHideProgress(true);
            iFormIdListener = new IFormIdListener() {
                @Override
                public void onGetData(String formId) {
                    if (checkNull() && !formId.equals(Commons.NULL_INTEGER)) {
                        myFormId = formId;
                        loadPrimitiveData();
                    } else {
                        if (checkNull()) {
                            //we no need to see search and filter
                            //getView().showHideProgress(false);
                            getView().showMessageSomeProblems(CommonsLogErrorNumber.error_89);
                        }
                    }
                }

                @Override
                public void onError() {
                    if (checkNull()) {
                        //we no need to see search and filter
                        //getView().showHideProgress(false);
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_88);
                    }
                }
            };
            addDisposable(FormIdFromFormCodeHandler.getFormId(formFormCode, iFormIdListener));
        }
    }

    //---------------------------------------------------------------------------------
    //time filtering
    private Disposable getTimeFilterIdGuidList(BaseGetTimeFilteredIdListQuery baseGetTimeFilteredIdListQuery) {
        if (startDate == null || endDate == null) {
            makeAllDateNull();
            createDataFromSearch();
            return null;
        } else {
            GlobalModel globalModel = new GlobalModel();
            globalModel.setStartDate(startDate);
            globalModel.setEnddate(endDate);
            assert baseGetTimeFilteredIdListQuery != null;
            return baseGetTimeFilteredIdListQuery.data(globalModel)
                    .subscribe(iModels -> {
                        filteredIdGUID = globalModel.getStringArrayList();
                        createDataFromSearch();
                    }, throwable -> {
                        if (checkNull()) {
                            //we no need to see search and filter
                            //getView().showHideProgress(false);
                            getView().showMessageSomeProblems(CommonsLogErrorNumber.error_90);
                        }
                    });
        }

    }



}






