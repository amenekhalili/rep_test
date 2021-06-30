package ir.fararayaneh.erp.activities.select_activity;

import android.content.Context;
import android.content.Intent;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.realm.Realm;
import ir.fararayaneh.erp.IBase.common_base.BasePresenterWithRecycle;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.adaptors.selection_adaptor.IClickSelectionRecAdaptor;
import ir.fararayaneh.erp.adaptors.selection_adaptor.SelectionDBRecAdaptor;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsFormCode;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.GetGoodIdFromWareHouseIdQuery;
import ir.fararayaneh.erp.data.data_providers.queries.recycle_list_view_queries.report_quries.ReportQueries;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.BaseGetTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.PurchasableGoodsTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.SalableGoodsTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.GoodTransDetailsModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.utils.CalculationHelper;
import ir.fararayaneh.erp.utils.data_handler.GoodSUOMJsonHelper;
import ir.fararayaneh.erp.utils.data_handler.GoodTransDetailJsonHelper;
import ir.fararayaneh.erp.utils.data_handler.IRefineTempAmountListener;
import ir.fararayaneh.erp.utils.data_handler.RefinePurchasableSalableTempAmountHandler;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.intent_handler.IntentReceiverHandler;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;
import ir.fararayaneh.erp.utils.waiting.IWaitingHandler;

import static ir.fararayaneh.erp.commons.Commons.DELAY_TIME_FOR_PRIMITIVE_DATA;
import static ir.fararayaneh.erp.commons.Commons.DELAY_TIME_FOR_SEARCH_DATA;
import static ir.fararayaneh.erp.commons.CommonsFormCode.BUYER_REQUEST_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.PRODUCTION_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE;


/**
 * -------------------------------------------------------------------------
 * <p>
 * * user come here with some form code (maybe some fake form cod) for result
 * * to show a list of data and select one or more rows(maybe change number of them)
 * * and return list of string as json data model(like GoodTransDetailsModel and so on...)
 * <p>
 * <p>
 * 1-get all rows of a table and show to user
 * ( user can not edit (only maybe change value of row) and only can  see attach(@see ###) those rows or can not)
 * <p>
 * 2-in changeListener of tables :
 * in insert mode ==>> check rows that those rows are accessDenied or other edited(show in two category : NEW and ACCESSDENIED)
 * in edit mode ==>> check rows that  are accessDenied or other edit state(show in two category : EDIT and ACCESSDENIED)
 * in delete mode ==>> we do not check any things , because no row were be deleted physically
 *
 * <p>
 * ###
 * when row can see and click on attachment that have column attach
 * and column guid(no need to guid after  adaptor were be changed to realm adaptor, because we do not need to query
 * on guid to find attach, all data are here)
 * and only can go to see attach not to edit (add-delete) them
 * (we need guid because all record have not id when shows their report)
 * ###
 * in this activity no need to listen to result of album activity
 * because we can not add and delete attachment from here
 */

public class SelectionPresenter extends BasePresenterWithRecycle<SelectionView> {

    private SelectionDBRecAdaptor selectionDBRecAdaptor;
    private boolean moltiSelectMode;
    private ISelectionActivityClickListener iSelectionActivityClickListener;
    private IRefineTempAmountListener iRefineTempAmountListener;
    private IClickSelectionRecAdaptor iClickSelectionRecAdaptor;
    private String myFormCode = Commons.NULL_INTEGER;
    private Realm realm;
    private ReportQueries reportQueries;
    private IWaitingHandler iWaitingHandler;
    private String searchText = Commons.SPACE;
    private Date startDate, endDate;//for filter result
    private ArrayList<String> filteredIdGUID;//for filter result for time
    private String warehouseNumberId;//for filter goods
    private ArrayList<String> filteredGoodsByWarehouse;//for use in select goods in new mode or edit mode of 276,62



    public SelectionPresenter(WeakReference<SelectionView> weekView, Context context, boolean shouldHaveIntent) {
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
        iClickSelectionRecAdaptor = null;
        iSelectionActivityClickListener = null;
        iRefineTempAmountListener = null;
        iWaitingHandler = null;
        //should be last
        closeRealm();
    }

    @Override
    public void click() {
        iSelectionActivityClickListener = new ISelectionActivityClickListener() {
            @Override
            public void searchText(Intent data) {
                if (data != null) {
                    searchText = IntentReceiverHandler.
                            getIntentData(Objects.requireNonNull(data.getExtras())).getSomeString();
                    createDataFromSearch();
                } else {
                    searchText = Commons.SPACE;
                    createDataFromSearch();
                }

            }

            @Override
            public void clickSelectAll() {
                //if user can see this menu , we are in multiSelect mode,  no need to check them
                selectionDBRecAdaptor.setSelectAll(true);
            }

            @Override
            public void clickDeSelectAll() {
                //if user can see this menu , we are in multiSelect mode, no need to check them
                selectionDBRecAdaptor.setSelectAll(false);
            }

            @Override
            public void clickBack() {
                onBackPress();
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

        iClickSelectionRecAdaptor = new IClickSelectionRecAdaptor() {
            @Override
            public void clickAttachment(IModels rowOfTable) {
                rowAttachClicked(rowOfTable);
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

            @Override
            public void showDialog(ArrayList<String> stringArrayList) {
                if (checkNull()) {
                    ToastMessage.showSimpleScrollAbleDialog(getView().getActivity(), stringArrayList.get(0));
                }
            }
        };

        selectionDBRecAdaptor.setiClickSelectionRecAdaptor(iClickSelectionRecAdaptor);
        if (checkNull()) {
            getView().setiSelectionActivityClickListener(iSelectionActivityClickListener);
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
            if ((startDate == null || endDate == null) && filteredIdGUID != null) {
                makeAllDateNull();
                createDataFromSearch();
            } else if (startDate != null && endDate != null && filteredIdGUID == null) {
                switch (myFormCode) {
                    //todo add other form code ((report code) and add to adaptor)
                    /*
                    deprecated
                    case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                    case CommonsFormCode.PRODUCTION_FORM_CODE:
                        //no need to time filter here, goods have not time
                        makeAllDateNull();
                        createDataFromSearch();
                        break;*/
                    case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                        PurchasableGoodsTimeFilteredIdListQuery purchasableGoodsTimeFilteredIdListQuery =
                                (PurchasableGoodsTimeFilteredIdListQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SALABLE_GOODS_TIME_FILTERED_ID_LIST_QUERY);
                        addDisposable(getTimeFilterIdGuidList(purchasableGoodsTimeFilteredIdListQuery));
                        break;
                    case CommonsFormCode.BUYER_REQUEST_FORM_CODE:
                        SalableGoodsTimeFilteredIdListQuery salableGoodsTimeFilteredIdListQuery
                                =(SalableGoodsTimeFilteredIdListQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SALABLE_GOODS_TIME_FILTERED_ID_LIST_QUERY);
                        addDisposable(getTimeFilterIdGuidList(salableGoodsTimeFilteredIdListQuery));
                        break;
                }
            } else {
                switch (myFormCode) {
                    //todo add other form code ((report code) and add to adaptor)
                    /*
                    deprecated
                    case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                    case CommonsFormCode.PRODUCTION_FORM_CODE:
                        addDisposable(getSearchSelectGoodsResult());
                        break;*/
                    case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                        addDisposable(getSearchSelectPurchasableGoodsResult());
                        break;
                    case CommonsFormCode.BUYER_REQUEST_FORM_CODE:
                        addDisposable(getSearchSelectSalableGoodsResult());
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
        myFormCode = intentModel.getSomeString2();//-->> we need myFormCode before all things
        setMultiSelectionMode();
        selectionDBRecAdaptor = new SelectionDBRecAdaptor(true, true, null, context, myFormCode, moltiSelectMode);
        warehouseNumberId = intentModel.getSomeString3();
        super.onCreate(intentModel);
        setupToolbar();
    }

    //todo add other form code ((report code) and add to todo in  "selectionDBRecAdaptor")
    private void setMultiSelectionMode() {
        switch (myFormCode) {
            /*
            deprecated
            case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
            case CommonsFormCode.PRODUCTION_FORM_CODE:*/
            case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
            case CommonsFormCode.BUYER_REQUEST_FORM_CODE:
                moltiSelectMode = true;
                break;
        }
    }

    @Override
    protected void setRecyclAdaptor() {
        if (checkNull()) {
            getView().setRecycleAdaptor(selectionDBRecAdaptor);
        }
    }

    @Override
    protected void workForLoadPage() {
        addDisposable(getAllowedGoodIds());
    }

    /**
     * find goodIds that are in my warehouse
     */
    private Disposable getAllowedGoodIds() {
        GetGoodIdFromWareHouseIdQuery getGoodIdFromWareHouseIdQuery = (GetGoodIdFromWareHouseIdQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_GOOD_ID_FROM_WAREHOUSE_ID_QUERY);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(warehouseNumberId);
        assert getGoodIdFromWareHouseIdQuery != null;
        return getGoodIdFromWareHouseIdQuery
                .data(globalModel)
                .subscribe(iModels -> {
                    filteredGoodsByWarehouse = globalModel.getStringArrayList();
                    loadMyPage();
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_111);
                    }
                });

    }

    //todo add other form code ((report code) and add to todo in  "selectionDBRecAdaptor")
    private void loadMyPage() {
        iWaitingHandler = () -> {
            //todo add other form code ((report code)  and add to adaptor)
            switch (myFormCode) {
                /*
                deprecated

                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                case CommonsFormCode.PRODUCTION_FORM_CODE:
                    addDisposable(getPrimitiveSelectGoodsResult());
                    break;
                    */
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                    addDisposable(getPrimitiveSelectPurchasableGoodsResult());
                    break;
                case CommonsFormCode.BUYER_REQUEST_FORM_CODE:
                    addDisposable(getPrimitiveSelectSalableGoodsResult());
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
            getView().showHideProgress(true);
        }
        String result = selectionDBRecAdaptor.getSelectionAdaptorResult();

        if (result.equals(Commons.NULL)) {
            cancelResultIntent();
        } else if (result.equals(Commons.NULL_INTEGER) && checkNull()){
            getView().showMessageOverFlowAmount();
            getView().showHideProgress(false);
        }
        else if (checkNull()) {
            setupSalablePurchasableTempAmount(result);
        }
    }


    private void setupSalablePurchasableTempAmount(String goodTransStringJson) {
        iRefineTempAmountListener = new IRefineTempAmountListener() {
            @Override
            public void done() {
                ActivityIntents.resultOkFromSelectionAct(getView().getActivity(), goodTransStringJson);
            }

            @Override
            public void error() {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_110);
                }
            }
        };
        //todo add other
        switch (myFormCode){
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                addDisposable(RefinePurchasableSalableTempAmountHandler.doRefinePurchasable(getProperModelForChangeTempValue(goodTransStringJson),iRefineTempAmountListener));
                break;
            case BUYER_REQUEST_FORM_CODE:
                addDisposable(RefinePurchasableSalableTempAmountHandler.doRefineSalable(getProperModelForChangeTempValue(goodTransStringJson),iRefineTempAmountListener));
                break;
        }
    }

    private GlobalModel getProperModelForChangeTempValue(String goodTransStringJson){

        ArrayList<GoodTransDetailsModel> arrayList = GoodTransDetailJsonHelper.getGoodTransDetailArray(goodTransStringJson);
        ArrayList<String> idList =new ArrayList<>();
        ArrayList<String> tempAmountList=new ArrayList<>();

        Stream.of(arrayList).forEach(goodTransDetailsModel -> {
            for (int i = 0; i < arrayList.size() ; i++) {
                idList.add(goodTransDetailsModel.getIdRecallDetail());
                tempAmountList.add(CalculationHelper.minusAnyAndRoundNonMoneyValue(Commons.ZERO_STRING,goodTransDetailsModel.getAmount2(),context));
            }
        });

        GlobalModel globalModel = new GlobalModel();
        globalModel.setStringArrayList(idList);
        globalModel.setStringArrayList2(tempAmountList);
        return globalModel;
    }

    private void setupToolbar() {
        if (checkNull()) {
            //todo add other form code ((report code) and add to adaptor)
            switch (myFormCode) {
                /*
                deprecated
                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                case CommonsFormCode.PRODUCTION_FORM_CODE:*/
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                case CommonsFormCode.BUYER_REQUEST_FORM_CODE:
                    getView().setupTextToolbar(context.getResources().getString(R.string.please_select));
                    break;
            }
        }
    }

    /**
     * if we can not see attach or have not attach column,we do not come here
     */
    //todo add other form code ((report code) and add to adaptor)
    private void rowAttachClicked(IModels iModels) {
        if (checkNull()) {
            switch (myFormCode) {
                /*
                deprecated
                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                case CommonsFormCode.PRODUCTION_FORM_CODE:
                    GoodsTable goodsTable = (GoodsTable) iModels;
                    goAttachAlbumActivity(false, false, goodsTable.getAttach());
                    break;*/
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                    PurchasableGoodsTable purchasableGoodsTable = (PurchasableGoodsTable) iModels;
                    goAttachAlbumActivity(false, false, purchasableGoodsTable.getAttach());
                    break;
            }
        }
    }

//----------------------------------------------------------------------------------------------

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
            if(!realm.isClosed()){
                realm.removeAllChangeListeners();
                realm.close();
            }
        }
    }

    //----------------------------------------------------------------------------------------------

    /**
        if want to use, should filter goods by warehouseId
     */
    /*private Disposable getPrimitiveSelectGoodsResult() {
        return getReportQueriesInstance()
                .primitiveGoodsQuery(getRealm())
                .subscribe(orderedRealmCollections -> {
                    if (selectionDBRecAdaptor != null) {
                        selectionDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }

                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_37);
                    }
                });
    }

    private Disposable getSearchSelectGoodsResult() {

        return getReportQueriesInstance().searchGoodsQuery(getRealm(), searchText)
                .subscribe(orderedRealmCollections -> {
                    if (selectionDBRecAdaptor != null) {
                        selectionDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_38);
                    }
                });
    }*/

    //---------------------------------------------------------------------------------------------------------
    private Disposable getPrimitiveSelectPurchasableGoodsResult() {

        return getReportQueriesInstance().primitivePurchasableGoodsQuery(getRealm(), filteredIdGUID,filteredGoodsByWarehouse)
                .subscribe(orderedRealmCollections -> {
                    if (selectionDBRecAdaptor != null) {
                        selectionDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_33);
                    }
                });
    }

    private Disposable getSearchSelectPurchasableGoodsResult() {

        return getReportQueriesInstance().searchPurchasableGoodsQuery(getRealm(), searchText, filteredIdGUID,filteredGoodsByWarehouse)
                .subscribe(orderedRealmCollections -> {
                    if (selectionDBRecAdaptor != null) {
                        selectionDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_34);
                    }
                });
    }

    //---------------------------------------------------------------------------------------------------------
    private Disposable getPrimitiveSelectSalableGoodsResult() {

        return getReportQueriesInstance().primitiveSalableGoodsQuery(getRealm(), filteredIdGUID,filteredGoodsByWarehouse)
                .subscribe(orderedRealmCollections -> {
                    if (selectionDBRecAdaptor != null) {
                        selectionDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_109);
                    }
                });
    }

    private Disposable getSearchSelectSalableGoodsResult() {

        return getReportQueriesInstance().searchSalableGoodsQuery(getRealm(), searchText, filteredIdGUID,filteredGoodsByWarehouse)
                .subscribe(orderedRealmCollections -> {
                    if (selectionDBRecAdaptor != null) {
                        selectionDBRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_108);
                    }
                });
    }
    //---------------------------------------------------------------------------------------------------------
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
                            getView().showMessageSomeProblems(CommonsLogErrorNumber.error_65);
                        }
                    });
        }

    }

}
