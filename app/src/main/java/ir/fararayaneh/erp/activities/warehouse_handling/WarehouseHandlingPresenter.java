package ir.fararayaneh.erp.activities.warehouse_handling;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.realm.RealmModel;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.IBase.common_base.form_base.BasePresenterCollectionDataForm;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonComboClickType;
import ir.fararayaneh.erp.commons.CommonComboFormType;
import ir.fararayaneh.erp.commons.CommonWarehouseSearchType;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsFormCode;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.warehouse_out_query.InsertWarehouseOutTableQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.GetAllDataFromOneColumnWarehouseQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.GetAllPlaceShelfRowWarehouseQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.GetFirstEditableWarehouseRowQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.GetOneRowByBarCodeWarehouseQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.GetOneRowByIdWarehouseQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.SetValueWarehouseQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.WarehouseSocketModel;
import ir.fararayaneh.erp.data.models.middle.ComboFormModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingListOutTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingTable;
import ir.fararayaneh.erp.utils.data_handler.KindListForSyncActHelper;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;


/**
 * formCode WERHOUSEHANDLING_FORM_CODE = 316 :
 * در جدول
 * formRef
 * این فرم دارای هیچ کانفیگی نیست
 * و همه دیتا هارا از جدول
 * WarehouseHandlingTable
 * خوانده میشود
 * <p>
 * این پرزنتر حاوی سه اسپینر
 * compositeSearch
 * serialSearch
 * filterSearch
 * است
 */
public class WarehouseHandlingPresenter extends BasePresenterCollectionDataForm<WarehouseHandlingView> {


    //-------------------------------------------------------------------------------
    private String shelfFilteredValue; //taiin mikonad ke query ma roye hame shelf ha bashad ya shelf khas(placeShelfRow)
    private ArrayList<String> compositeSearchId = new ArrayList<>(), serialSearchId = new ArrayList<>();
    private String mainNumber, subNumber;
    private boolean shouldFillSubAmount = true;
    //-------------------------------------------------------------------------------


    //--------------------------for delete soon--------------------------------------------------------------------
    //--------------------------for delete soon--------------------------------------------------------------------
    private IClickWareHoseListener iClickWareHoseListener;

    @Override
    public void onBackPress() {
        if (checkNull()) {
            if (getView().conslay_add_Goods_warehouse.getVisibility() == View.VISIBLE) {
                getView().showHideConslayAddGoods(false);
            } else {
                super.onBackPress();
            }
        } else {
            super.onBackPress();
        }
    }

    @Override
    public void click() {
        super.click();
        iClickWareHoseListener = (warehouseCode, goodCode, amount, description) -> addDisposable(addToWarehouseOut(warehouseCode, goodCode, amount, description));
        if (checkNull()) {
            getView().setiClickWareHoseListener(iClickWareHoseListener);
        }
    }

    private Disposable addToWarehouseOut(String warehouseCode, String goodCode, String amount, String description) {
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(warehouseCode);
        globalModel.setMyString2(goodCode);
        globalModel.setMyString3(description);
        globalModel.setMyString4(amount);
        InsertWarehouseOutTableQuery insertWarehouseOutTableQuery = (InsertWarehouseOutTableQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_WAREHOUSE_OUT_TABLE_QUERY);
        assert insertWarehouseOutTableQuery != null;
        return insertWarehouseOutTableQuery.data(globalModel).subscribe(iModels -> {
            WarehouseHandlingListOutTable warehouseHandlingListOutTable = (WarehouseHandlingListOutTable) iModels;
            SendPacket.sendEncryptionMessage(context, SocketJsonMaker.warehouseOutSocket(SharedPreferenceProvider.getOrganization(context), SharedPreferenceProvider.getUserId(context), warehouseHandlingListOutTable), false);
        }, throwable -> {
            if (checkNull()) {
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_49);
            }
        });
    }

    //--------------------------for delete soon--------------------------------------------------------------------
    //--------------------------for delete soon--------------------------------------------------------------------


    public WarehouseHandlingPresenter(WeakReference<WarehouseHandlingView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    /**
     * only for set values that no need to database because get from db call after this method
     */
    @Override
    protected void setDefaultValue() {
        callFilterManager(Commons.NULL_WAREHOUSE_FILTER_VALUE,false);//false because , UI not created
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
        if (iSocketModel instanceof WarehouseSocketModel && checkNull()) {
            if (((WarehouseSocketModel) iSocketModel).getErrorNumber().toLowerCase().equals(Commons.NULL)) {
                ToastMessage.showGreenSnack(getView().getActivity(), String.format(CommonsFormat.FORMAT_6, ((WarehouseSocketModel) iSocketModel).getBody().getGoodName(), context.getString(R.string.save_data_success)));
            } else {
                ToastMessage.showRedSnack(getView().getActivity(), String.format(CommonsFormat.FORMAT_7, ((WarehouseSocketModel) iSocketModel).getBody().getGoodName(), context.getString(R.string.save_data_error), ((WarehouseSocketModel) iSocketModel).getErrorNumber()));
            }
        }
    }

    @Override
    protected void ClearAllPresenterListener() {
        super.ClearAllPresenterListener();
    }

    //---------------------------------------------------------------------------------------------

    @Override
    protected void setupCustomLayoutManagerFactory() {
        if (checkNull()) {
            ArrayList<Integer> listOfPosition = new ArrayList<>();
            switch (myFormCode) {
                case CommonsFormCode.WAREHOUSE_HANDLING_FORM_CODE:
                    listOfPosition.add(0);
                    listOfPosition.add(1);
                    listOfPosition.add(2);
                    listOfPosition.add(3);
                    listOfPosition.add(5);
                    listOfPosition.add(6);
                    getView().setupCustomLayoutManager(2, 1, listOfPosition);
                    break;
            }
        }

    }


    @Override
    protected void witchWorkForShowForm() {
        switch (myFormCode) {
            case CommonsFormCode.WAREHOUSE_HANDLING_FORM_CODE:
                showFormWarehouseHandling();
                break;
        }
    }


    @Override
    protected void clickRowRecycleFactoryMethod(int comboFormClickType, String someValue) {
        switch (myFormCode) {
            case CommonsFormCode.WAREHOUSE_HANDLING_FORM_CODE:
                clickRowWarHouseForm(comboFormClickType, someValue);
                break;
        }
    }

    /*
     * dar inja bar khalaf baghie form ha data spinner ,
     * bayad az database queri shavad
     */
    @Override
    protected void setDataFromSearchAbleSpinner(RealmModel realmModel, String item, int position, int commonComboClickType) {
        if (checkNull()) {
            //here,we no need to set data to spinner,because spinner have not place to show selected value(only need to filter value)
            //comboFormRecAdaptor.updateRowValue(commonComboClickType, new ComboFormModel(Commons.NULL, item, Commons.NULL, false));

            switch (commonComboClickType) {
                case CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE:
                    //addDisposable(getOneRowById(compositeSearchId.get(compositeSearchName.indexOf(item))));
                    addDisposable(getOneRowById(compositeSearchId.get(position)));
                    break;
                case CommonComboClickType.SEARCH_SERIAL_CLICK_TYPE:
                    //addDisposable(getOneRowById(serialSearchId.get(serialSearchName.indexOf(item))));
                    addDisposable(getOneRowById(serialSearchId.get(position)));
                    break;
                case CommonComboClickType.FILTER_CLICK_TYPE:
                    setReadOnlyValues(null);
                    callFilterManager(item,true);//in setReadOnlyValues, filter will be removed , and should set here again and after that
                    break;
            }

        }
    }

    @Override
    protected void checkData() {
        switch (myFormCode) {
            case CommonsFormCode.WAREHOUSE_HANDLING_FORM_CODE:
                workCheckDataWareHouseActivity();
                break;
        }

    }

    private void workCheckDataWareHouseActivity() {
        setReadOnlyValues(null);
        goSyncAct(KindListForSyncActHelper.getWarehouseKindsList());
    }


    private void callFilterManager(String filterValue,boolean shouldSetupUi) {
        shelfFilteredValue = filterValue;
        if(comboFormRecAdaptor!=null && shouldSetupUi){
        comboFormRecAdaptor.updateRowValue(CommonComboClickType.FILTER_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, !filterValue.equals(Commons.NULL_WAREHOUSE_FILTER_VALUE)), true);
        }

    }

    @Override
    protected void workNextClick() {
        switch (myFormCode) {
            case CommonsFormCode.WAREHOUSE_HANDLING_FORM_CODE:
                nextClickWarehouseHandling();
                break;
        }

    }

    @Override
    protected void workDeleteClick() {
        //do nothings we do not come here fore delete
    }

    //----------------------------------------------------------------------------------------------
    //region WarHouse formCode
    private void showFormWarehouseHandling() {
        if (checkNull()) {
            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToWarehouseRec(), setProperClickRowTypeToWarehouseRec(), setProperDataToWarehouseRec());
            getView().showHideProgress(false);
            getView().showHideConslayWareHouse(true);
            //for show edit mode (with out delete)
            if (!primitiveGUID.equals(Commons.NULL)) {
                addDisposable(getOneRowById(primitiveGUID));
            }
        }
    }

    private List<Integer> setProperRowTypeToWarehouseRec() {
        ArrayList<Integer> comboFormType = new ArrayList<>();
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_13);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_11);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_12);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_10);

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_14);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_14);

        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToWarehouseRec() {

        ArrayList<Integer> comboFormClickType = new ArrayList<>();
        comboFormClickType.add(CommonComboClickType.BARCODE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.SEARCH_SERIAL_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.FILTER_CLICK_TYPE);

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.MAIN_NUMBER_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.SUB_NUMBER_CLICK_TYPE);

        return comboFormClickType;

    }

    private List<ComboFormModel> setProperDataToWarehouseRec() {

        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, context.getString(R.string.no_goods_selected), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.main_number), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.sub_number), Commons.NULL, Commons.NULL, false));
        return comboFormModels;
    }

    private void clickRowWarHouseForm(int comboFormClickType, String someValue) {

        switch (comboFormClickType) {
            case CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE:
                addDisposable(showOneColumnWarHouseTable(CommonWarehouseSearchType.COMPOSITE_SEARCH_TYPE));
                break;
            case CommonComboClickType.SEARCH_SERIAL_CLICK_TYPE:
                addDisposable(showOneColumnWarHouseTable(CommonWarehouseSearchType.SERIAL_SEARCH_TYPE));
                break;
            case CommonComboClickType.BARCODE_CLICK_TYPE:
                if (checkNull()) {
                    ActivityIntents.goBarCodeActForResult(getView().getActivity());
                }
                break;
            case CommonComboClickType.FILTER_CLICK_TYPE:
                addDisposable(showFilterList());
                break;
            case CommonComboClickType.MAIN_NUMBER_CLICK_TYPE:
                //no need to update,  dataList were updated in holder
                mainNumber = someValue;
                /*if (EditTextCheckHelper.isOnlyDigit(someValue)) {
                } else {
                    mainNumber = -1;
                }*/
                break;
            case CommonComboClickType.SUB_NUMBER_CLICK_TYPE:
                subNumber = someValue;
                /*if (EditTextCheckHelper.isOnlyDigit(someValue)) {
                    subNumber = Integer.valueOf(someValue);
                } else {
                    subNumber = -1 ;
                }*/
                break;
        }
    }

    @Override
    protected void setReceivedBarcode(Intent data) {
        super.setReceivedBarcode(data);
        Log.i("arash", "setReceivedBarcode: //////////////////////////" + barCode);
        if (!barCode.equals(Commons.NULL)) {
            addDisposable(getOneRowByBarCode());
        }
    }

    /**
     * @param searchType //-->> set name of column for show spinner
     */
    private Disposable showOneColumnWarHouseTable(int searchType) {
        GetAllDataFromOneColumnWarehouseQuery getAllDataFromOneColumnWarehouseQuery = (GetAllDataFromOneColumnWarehouseQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ALL_DATA_FROM_ONE_COLUMN_WAREHOUSE_QUERY);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyInt(searchType);
        globalModel.setMyString(shelfFilteredValue);
        assert getAllDataFromOneColumnWarehouseQuery != null;
        return getAllDataFromOneColumnWarehouseQuery.data(globalModel)
                .subscribe(iModels -> {
                    if (checkNull()) {
                        setProperDataToArrayListsForSearchSpinners(searchType, globalModel.getStringArrayList(), globalModel.getStringArrayList2());
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_50);
                    }
                });

    }

    private Disposable showFilterList() {
        GetAllPlaceShelfRowWarehouseQuery getAllPlaceShelfRowWarehouseQuery = (GetAllPlaceShelfRowWarehouseQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ALL_PLACE_SHELF_ROW_WAREHOUSE_QUERY);
        assert getAllPlaceShelfRowWarehouseQuery != null;
        return getAllPlaceShelfRowWarehouseQuery.data(App.getNullRXModel())
                .subscribe(iModels -> {
                    if (checkNull()) {
                        GlobalModel globalModel = (GlobalModel) iModels;
                        setProperDataToArrayListsForFilterSpinners(globalModel.getStringArrayList());
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_51);
                    }
                });
    }

    private Disposable getOneRowById(String id) {
        if (checkNull()) {
            getView().showHideProgress(true);
        }

        GetOneRowByIdWarehouseQuery getOneRowByIdWarehouseQuery = (GetOneRowByIdWarehouseQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ONE_ROW_BY_ID_WAREHOUSE_QUERY);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(id);
        assert getOneRowByIdWarehouseQuery != null;
        return getOneRowByIdWarehouseQuery.data(globalModel).subscribe(iModels -> {
            if (checkNull()) {
                getView().showHideProgress(false);
            }
            WarehouseHandlingTable warehouseHandlingTable = (WarehouseHandlingTable) iModels;
            if (warehouseHandlingTable.getId().equals(Commons.NULL_INTEGER)) {
                setReadOnlyValues(null);
            } else {
                setReadOnlyValues(warehouseHandlingTable);
            }
        }, throwable -> {
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_52);
                setReadOnlyValues(null);
            }
        });

    }

    private Disposable getOneRowByBarCode() {
        if (checkNull()) {
            getView().showHideProgress(true);
        }

        GetOneRowByBarCodeWarehouseQuery getOneRowByBarCodeWarehouseQuery = (GetOneRowByBarCodeWarehouseQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ONE_ROW_BY_BARCODE_WAREHOUSE_QUERY);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(barCode);
        assert getOneRowByBarCodeWarehouseQuery != null;
        return getOneRowByBarCodeWarehouseQuery.data(globalModel).subscribe(iModels -> {
            if (checkNull()) {
                getView().showHideProgress(true);
            }
            WarehouseHandlingTable warehouseHandlingTable = (WarehouseHandlingTable) iModels;
            if (warehouseHandlingTable.getId() .equals(Commons.NULL_INTEGER)) {
                setReadOnlyValues(null);
            } else {
                setReadOnlyValues(warehouseHandlingTable);
            }
        }, throwable -> {
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_53);
                setReadOnlyValues(null);
            }
        });
    }

    private void setReadOnlyValues(WarehouseHandlingTable warehouseHandlingTable) {
        if (warehouseHandlingTable != null) {
            if (checkNull()) {
                //---------------------------------------------------------
                //this section should be first because set shouldFillSubAmount (no need to reset filter)
                id = warehouseHandlingTable.getId();
                mainNumber = warehouseHandlingTable.getAmount();
                subNumber = warehouseHandlingTable.getSubAmount();
                shouldFillSubAmount = !warehouseHandlingTable.getSubUnitName().equals(warehouseHandlingTable.getMainUnitName());
                //---------------------------------------------------------

                comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE,
                        new ComboFormModel(Commons.NULL,
                                String.format(CommonsFormat.FORMAT_5,
                                        context.getString(R.string.counting_number), warehouseHandlingTable.getCountingNumber(),
                                        context.getString(R.string.good_name), warehouseHandlingTable.getGoodName(),
                                        context.getString(R.string.good_code), warehouseHandlingTable.getGoodCode(),
                                        context.getString(R.string.serial), warehouseHandlingTable.getSerial(),
                                        context.getString(R.string.good_main_unit_name), warehouseHandlingTable.getMainUnitName(),
                                        context.getString(R.string.good_main_shelf_row), warehouseHandlingTable.getPlaceShelfRow(),
                                        context.getString(R.string.good_main_shelf_sub_row), warehouseHandlingTable.getPlaceShelfSubRow(),
                                        context.getString(R.string.good_main_palce_layer), warehouseHandlingTable.getPlaceShelfLayer()
                                ), Commons.NULL, false), true);

                comboFormRecAdaptor.updateRowValue(CommonComboClickType.MAIN_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.main_number), String.valueOf(warehouseHandlingTable.getAmount()), Commons.NULL, false), true);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SUB_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sub_number), shouldFillSubAmount ? String.valueOf(warehouseHandlingTable.getSubAmount()) : Commons.HIDE_VIEW_PLEASE, Commons.NULL, false), true);
            }
        } else {
            if (checkNull()) {

                //-----------------------------------------
                //this section should be first because set shouldFillSubAmount
                id = Commons.NULL_INTEGER;
                mainNumber = Commons.NULL_INTEGER;
                subNumber = Commons.NULL_INTEGER;
                shouldFillSubAmount = true;
                callFilterManager(Commons.NULL_WAREHOUSE_FILTER_VALUE,true);
                //-----------------------------------------
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE, new ComboFormModel(Commons.NULL, context.getString(R.string.no_goods_selected), Commons.NULL, false), true);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.MAIN_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.main_number), Commons.NULL, Commons.NULL, false), true);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SUB_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sub_number), Commons.NULL, Commons.NULL, false), true);


            }
        }

        if (checkNull()) {
            getView().showHideProgress(false);
        }
    }

    private void setProperDataToArrayListsForSearchSpinners(int searchType, ArrayList<String> stringArrayList, ArrayList<String> intArrayList) {
        switch (searchType) {
            case CommonWarehouseSearchType.COMPOSITE_SEARCH_TYPE:
                compositeSearchId = intArrayList;
                showSearchAbleSpinner(stringArrayList, CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE);
                break;
            case CommonWarehouseSearchType.SERIAL_SEARCH_TYPE:
                serialSearchId = intArrayList;
                showSearchAbleSpinner(stringArrayList, CommonComboClickType.SEARCH_SERIAL_CLICK_TYPE);
                break;
        }
    }

    private void setProperDataToArrayListsForFilterSpinners(ArrayList<String> stringArrayList) {
        showSearchAbleSpinner(stringArrayList, CommonComboClickType.FILTER_CLICK_TYPE);
    }

    private void nextClickWarehouseHandling() {
        if (id .equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showMessageNothingsSelected();
            }
        } else if (mainNumber.equals(Commons.SPACE)) {
            getView().showMessageMainValueNotSelected();
        } else if (shouldFillSubAmount && subNumber.equals(Commons.SPACE)) {
            getView().showMessageSubValueNotSelected();
        } else {
            addDisposable(setValueWarehouse());
        }
    }

    /**
     * set id main and sub number
     * BEFORE_SYNC is default value in query and set to syncState
     * and send socket
     */
    private Disposable setValueWarehouse() {
        if (checkNull()) {
            getView().showHideProgress(true);
        }

        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(mainNumber);
        globalModel.setMyString2(subNumber);
        globalModel.setMyString3(id);

        SetValueWarehouseQuery valueWarehouseQuery =
                (SetValueWarehouseQuery) FactoryDataProvider
                        .getDataProvider(CommonsDataProviderFactory.SET_VALUE_WAREHOUSE_QUERY);

        assert valueWarehouseQuery != null;
        return valueWarehouseQuery.data(globalModel).subscribe(iModels -> {
            WarehouseHandlingTable warehouseHandlingTable = (WarehouseHandlingTable) iModels;
            //if we get id = 0 ==>> user do not worry about  edited previous record because 'findNextEditableItem()' ==> do not show error to user
            if (!warehouseHandlingTable.getId() .equals(Commons.NULL_INTEGER)) {
                SendPacket.sendEncryptionMessage(context, SocketJsonMaker.wareHouseSocket(SharedPreferenceProvider.getOrganization(context), SharedPreferenceProvider.getUserId(context), warehouseHandlingTable), false);
            }
            //if my id < 0 or not, first editable item can get from below method
            addDisposable(findNextEditableItem());

        }, throwable -> {
            //if we get error, do not remove current data in UI
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_54);
            }
        });
    }

    private Disposable findNextEditableItem() {
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(shelfFilteredValue);
        GetFirstEditableWarehouseRowQuery getFirstEditableWarehouseRowQuery = (GetFirstEditableWarehouseRowQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_FIRST_EDITABLE_WAREHOUSE_ROW_QUERY);
        assert getFirstEditableWarehouseRowQuery != null;
        return getFirstEditableWarehouseRowQuery.data(globalModel).subscribe(iModels -> {
            WarehouseHandlingTable warehouseHandlingTable = (WarehouseHandlingTable) iModels;
            if (warehouseHandlingTable.getId() .equals(Commons.NULL_INTEGER)) {
                setReadOnlyValues(null);
                getView().showMessageNoMoreGoods();
            } else {
                setReadOnlyValues(warehouseHandlingTable);
            }
        }, throwable -> setReadOnlyValues(null));
    }
}
