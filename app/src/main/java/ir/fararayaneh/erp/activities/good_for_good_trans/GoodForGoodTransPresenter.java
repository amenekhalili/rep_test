package ir.fararayaneh.erp.activities.good_for_good_trans;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.realm.RealmModel;
import ir.fararayaneh.erp.IBase.common_base.form_base.BasePresenterCollectionDataForm;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonComboClickType;
import ir.fararayaneh.erp.commons.CommonComboFormType;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.GetGoodIdFromWareHouseIdQuery;
import ir.fararayaneh.erp.data.data_providers.queries.purchasable_goods.GetPurchasableGoodsByRecallId;
import ir.fararayaneh.erp.data.data_providers.queries.salable_goods.GetSalableByRecallId;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.ComboFormModel;
import ir.fararayaneh.erp.data.models.middle.GoodSUOMModel;
import ir.fararayaneh.erp.data.models.middle.GoodTransDetailsModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.tables.BaseCodingTable;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.SalableGoodsTable;
import ir.fararayaneh.erp.utils.CalculationHelper;
import ir.fararayaneh.erp.utils.UUIDHelper;
import ir.fararayaneh.erp.utils.data_handler.GetOneRowByGUIDHandler;
import ir.fararayaneh.erp.utils.data_handler.GoodSUOMJsonHelper;
import ir.fararayaneh.erp.utils.data_handler.IGetRowByGUIDQueryListener;
import ir.fararayaneh.erp.utils.data_handler.IRefineTempAmountListener;
import ir.fararayaneh.erp.utils.data_handler.RefinePurchasableSalableTempAmountHandler;
import ir.fararayaneh.erp.utils.data_handler.WarehouseUnitListHandler;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.search_spinner_dialog.SearchAbleSpinnerDialogHandler;

import static ir.fararayaneh.erp.commons.CommonsFormCode.BUYER_REQUEST_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.BUY_SERVICES_INVOICE_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.PERMANENT_RECEIPT_GOODS_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.PRODUCTION_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.RECEIPT_GOODS_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.WAREHOUSE_TRANSFERENCE_FORM_CODE;

/**
 * این اکتیویتی فور ریزالت
 * و با استفاده از فرم کد هایی نظیر
 * REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE
 * و ...
 * نیز شماره انباری که یوزر میخواهد از ان کالا انتخاب نماید
 * که همگی فرم کد های تشکیل دهنده
 * اکتیویتی
 * goodTrans
 * هستند ایجاد میشود
 * کانفیکگ هایی که از این فرم کدها در فرم حاظر مد نظر است
 * فرم کدهای
 * 8و9و10و15
 * میباشد
 * در واقع فرم حاظر یکی از روش های ایجاد دیتیل برای اکتیویتی
 * GoodTrans
 * میبشاد و یک جیسون استرنگ از جنس
 * GoodTransDetailsModel
 * با استفاده از
 * GoodTransDetailJsonHelper
 * به عنوان ریزالت برمیگرداند
 */

/**
 * اینجا ما دو
 * guid
 * داریم یکی همان که در بیس مقدار میگیرد و مقدار ان آی دی کالا است ور در اینتنت به اینجا ارسال میشود و نشان میدهد که ما در ادیت مود هستیم یا خیر
 * اما مورد دوم
 * goodTransModelGUID
 * است که به درد اکتیویتی مبدا و تجمیع دیتیل کالا برای زمانی میخورد که که ما یک کالا را ویرایش میکنیم
 */
public class GoodForGoodTransPresenter extends BasePresenterCollectionDataForm<GoodForGoodTransView> {


    private static final String PRIMITIVE_GOOD_COLUMN_NUMBER = "1";

    //value for create json --------------------------->>>
    private int goodColumnNumber;
    private String id, idRecallDetail, goodId, C5UnitId, length, width, height, alloy,
            requestAmount1, requestAmount2, amount, amount2, currencyUnitPrice2,
            currencyUnitPrice1, currencyTotalValue, discountPercentage, currencyDiscount1,
            currencyDiscount2, taxPercentage, currencyTaxValue, currencyNetValue, currencyNetUnitPrice2,
            B5IdRefId8, B5IdRefId9, B5IdRefId10, B5IdRefId15, batchNumber, I5BOMId,
            goodTransModelGUID;//for set that my good detail is new or not
    //value for create json --------------------------->>>

    //--------------------------->>>
    private String currencyDiscountTotal = Commons.NULL_INTEGER;//only for show
    //--------------------------->>>

    private String stringGoodTransDetailsModel;
    private boolean showGoodUnit, canChangeReqAmount;
    private GoodTransDetailsModel goodTransDetailsModel;
    private IGetRowByGUIDQueryListener iGetRowByGUIDQueryListener;
    private GoodsTable goodsTableEditMode;
    private SalableGoodsTable salableGoodsTableEditMode;
    private PurchasableGoodsTable purchasableGoodsTableEditMode;
    private ArrayList<GoodSUOMModel> goodSUOMEditModeList;
    private GoodSUOMModel goodSUOMModelEditMode;
    private String numerator = Commons.PLUS_STRING, deNumerator = Commons.PLUS_STRING;
    private String primitiveAmount2 = Commons.ZERO_STRING;////use in 274,284 for change in tempAmount2, no need in new mode(274,284 have not new mode), and other form code
    private IRefineTempAmountListener iRefineTempAmountListener;
    private String warehouseNumberId;//for filter goods
    private ArrayList<String> filteredGoodsByWarehouse;//for use in select goods in new mode or edit mode of 276,62

    public GoodForGoodTransPresenter(WeakReference<GoodForGoodTransView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    @Override
    public void onCreate(IntentModel intentModel) {
        //before super should set wareHouseNumber
        stringGoodTransDetailsModel = intentModel.getSomeString4();
        warehouseNumberId = intentModel.getSomeString3();
        showGoodUnit = WarehouseUnitListHandler.showGoodUnit(context, warehouseNumberId);
        canChangeReqAmount = intentModel.isCanChangeReqAmount();
        super.onCreate(intentModel);
    }

    /**
     * only for set values that no need to database because get from db call after this method
     */
    @Override
    protected void setDefaultValue() {
        /*
           here no need to -->
         * guid,id,myFormId,
         * syncState,
         * activityState,
         * oldValue
         */
        id = Commons.NULL_INTEGER;
        idRecallDetail = Commons.NULL_INTEGER;
        goodId = Commons.NULL_INTEGER;
        goodColumnNumber = Commons.PRIMITIVE_GOOD_COLUMN_NUMBER_VALUE;
        C5UnitId = Commons.NULL_INTEGER;
        length = Commons.NULL_INTEGER;
        width = Commons.NULL_INTEGER;
        height = Commons.NULL_INTEGER;
        alloy = Commons.NULL;
        requestAmount1 = Commons.NULL_INTEGER;
        requestAmount2 = Commons.NULL_INTEGER;
        amount = Commons.NULL_INTEGER;
        amount2 = Commons.NULL_INTEGER;
        currencyUnitPrice2 = Commons.NULL_INTEGER;
        currencyUnitPrice1 = Commons.NULL_INTEGER;
        currencyTotalValue = Commons.NULL_INTEGER;
        discountPercentage = Commons.ZERO_STRING;//default is zero
        currencyDiscount1 = Commons.NULL_INTEGER;
        currencyDiscount2 = Commons.ZERO_STRING;//default is zero
        currencyDiscountTotal = Commons.ZERO_STRING;//only for show(default is zero)
        taxPercentage = String.valueOf(SharedPreferenceProvider.getTaxPercent(context));
        currencyTaxValue = Commons.NULL_INTEGER;
        currencyNetValue = Commons.NULL_INTEGER;
        currencyNetUnitPrice2 = Commons.NULL_INTEGER;
        B5IdRefId8 = Commons.NULL_INTEGER;
        B5IdRefId9 = Commons.NULL_INTEGER;
        B5IdRefId10 = Commons.NULL_INTEGER;
        B5IdRefId15 = Commons.NULL_INTEGER;
        batchNumber = Commons.NULL;
        I5BOMId = Commons.NULL_INTEGER;
        goodTransModelGUID = UUIDHelper.getProperUUID();
    }

    private void setDefaultInEditMode() {
        /*
           here no need to -->
         * id (id of row of table record),myFormId,
         * syncState,
         * activityState,
         * oldValue
         *
         * guid were set in base
         */

        if (!stringGoodTransDetailsModel.equals(Commons.NULL)) {


            goodTransDetailsModel = gson.fromJson(stringGoodTransDetailsModel, GoodTransDetailsModel.class);

            primitiveAmount2 = goodTransDetailsModel.getAmount2(); //use in 274,284 for change in tempAmount2


            id = goodTransDetailsModel.getId();
            idRecallDetail = goodTransDetailsModel.getIdRecallDetail();
            goodId = goodTransDetailsModel.getGoodId();
            goodColumnNumber = goodTransDetailsModel.getGoodColumnNumber();
            C5UnitId = goodTransDetailsModel.getC5UnitId();
            length = goodTransDetailsModel.getLength();
            width = goodTransDetailsModel.getWidth();
            height = goodTransDetailsModel.getHeight();
            alloy = goodTransDetailsModel.getAlloy();
            requestAmount1 = goodTransDetailsModel.getRequestAmount1();
            requestAmount2 = goodTransDetailsModel.getRequestAmount2();
            amount = goodTransDetailsModel.getAmount();
            amount2 = goodTransDetailsModel.getAmount2();
            currencyUnitPrice2 = goodTransDetailsModel.getCurrencyUnitPrice2();
            currencyUnitPrice1 = goodTransDetailsModel.getCurrencyUnitPrice1();
            currencyTotalValue = goodTransDetailsModel.getCurrencyTotalValue();
            discountPercentage = goodTransDetailsModel.getDiscountPercentage();
            currencyDiscount1 = goodTransDetailsModel.getCurrencyDiscount1();
            currencyDiscount2 = goodTransDetailsModel.getCurrencyDiscount2();
            currencyDiscountTotal = currencyTotalValue.equals(Commons.NULL_INTEGER) ? Commons.ZERO_STRING : CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(currencyTotalValue, discountPercentage, Commons.HUNDRED_STRING, context);//only for show
            taxPercentage = goodTransDetailsModel.getTaxPercentage();
            currencyTaxValue = goodTransDetailsModel.getCurrencyTaxValue();
            currencyNetValue = goodTransDetailsModel.getCurrencyNetValue();
            currencyNetUnitPrice2 = goodTransDetailsModel.getCurrencyNetUnitPrice2();
            B5IdRefId8 = goodTransDetailsModel.getB5IdRefId8();
            B5IdRefId9 = goodTransDetailsModel.getB5IdRefId9();
            B5IdRefId10 = goodTransDetailsModel.getB5IdRefId10();
            B5IdRefId15 = goodTransDetailsModel.getB5IdRefId15();
            batchNumber = goodTransDetailsModel.getBatchNumber();
            I5BOMId = goodTransDetailsModel.getI5BOMId();
            goodTransModelGUID = goodTransDetailsModel.getGuid();
            loadProperTable();
        } else {
            if (checkNull()) {
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_94);
            }
        }

    }

    //todo
    /*
    لازم نیست با توجه به شماره انبار و آی دی گروه
    فیلتر شود چون برای ادیت امده ایم و حتما کالای مجازی انتخاب شده است
     */
    private void loadProperTable() {
        switch (myFormCode) {
            case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
            case PRODUCTION_FORM_CODE:
            case PERMANENT_RECEIPT_GOODS_FORM_CODE:
            case WAREHOUSE_TRANSFERENCE_FORM_CODE:
            case RECEIPT_GOODS_FORM_CODE:
            case BUY_SERVICES_INVOICE_FORM_CODE:
                getGoodTable();
                break;
            case BUYER_REQUEST_FORM_CODE:
                addDisposable(getSalableTable());
                break;
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                addDisposable(getPurchasableTable());
                break;
        }
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
        //no need here
    }

    @Override
    protected void ClearAllPresenterListener() {
        super.ClearAllPresenterListener();
        iGetRowByGUIDQueryListener = null;
        iRefineTempAmountListener = null;
    }


//----------------------------------------------------------------------------------------------
    //region Common work for all form code

    //todo تکمیل باقی مانده فرم کد ها
    @Override
    protected void setupCustomLayoutManagerFactory() {
        switch (myFormCode) {
            case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                setupCustomLayoutManagerReqGoodFromWareHouse();
                break;
            case PRODUCTION_FORM_CODE:
                setupCustomLayoutManagerProduction();
                break;
            case BUYER_REQUEST_FORM_CODE:
                setupCustomLayoutManagerBuyerReq();
                break;
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                setupCustomLayoutManagerDeliveryProceedings();
                break;
            case PERMANENT_RECEIPT_GOODS_FORM_CODE:
            case WAREHOUSE_TRANSFERENCE_FORM_CODE:
            case RECEIPT_GOODS_FORM_CODE:
            case BUY_SERVICES_INVOICE_FORM_CODE:
                setupCustomLayoutManagerNonEditable();
        }
    }

    //todo تکمیل باقی مانده فرم کد ها
    @Override
    protected void witchWorkForShowForm() {
        if (!primitiveGUID.toLowerCase().equals(Commons.NULL)) {
            switch (myFormCode) {
                case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                case PRODUCTION_FORM_CODE:
                    addDisposable(getAllowedGoodIds());
                    break;
                case BUYER_REQUEST_FORM_CODE:
                case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                    setDefaultInEditMode();
                    break;
            }
        } else {
            switch (myFormCode) {
                case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                case PRODUCTION_FORM_CODE:
                    addDisposable(getAllowedGoodIds());
                    break;
                case BUYER_REQUEST_FORM_CODE:
                    showFormBuyerReq();
                    break;
                case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                    showFormDeliveryProceedings();
                    break;
            }
        }

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
                    switch (myFormCode) {
                        case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                            if (!primitiveGUID.toLowerCase().equals(Commons.NULL)) {
                                setDefaultInEditMode();
                            } else {
                                showFormReqGoodFromWareHouse();
                            }
                            break;
                        case PRODUCTION_FORM_CODE:
                            if (!primitiveGUID.toLowerCase().equals(Commons.NULL)) {
                                setDefaultInEditMode();
                            } else {
                                showFormProduction();
                            }
                            break;
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_111);
                    }
                });

    }

    private void getGoodTable() {
        iGetRowByGUIDQueryListener = new IGetRowByGUIDQueryListener() {
            @Override
            public void onGetRow(IModels iModels) {
                if (setupGoodTableInEditMode((GoodsTable) iModels)) {
                    witchWorkForShowFormEditMode();
                } else {
                    if (checkNull()) {
                        getView().showHideProgress(true);
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_59);
                    }
                }
            }

            @Override
            public void onError() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_60);
                }
            }
        };
        addDisposable(GetOneRowByGUIDHandler.getRowGoods(primitiveGUID, GoodsTable.class, true, iGetRowByGUIDQueryListener));//primitiveGuid=goodId
    }

    private Disposable getSalableTable() {
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(idRecallDetail);
        GetSalableByRecallId getSalableByRecallId = (GetSalableByRecallId) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_SALABLE_BY_RECALL_ID);
        assert getSalableByRecallId != null;
        return getSalableByRecallId
                .data(globalModel).subscribe(iModels -> {
                    if (setupSalableTableInEditMode((SalableGoodsTable) iModels)) {
                        witchWorkForShowFormEditMode();
                    } else {
                        if (checkNull()) {
                            getView().showHideProgress(true);
                            getView().showMessageSomeProblems(CommonsLogErrorNumber.error_97);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_96);
                    }
                });
    }

    private Disposable getPurchasableTable() {
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(idRecallDetail);
        GetPurchasableGoodsByRecallId getPurchasableGoodsByRecallId = (GetPurchasableGoodsByRecallId) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_PURCHASABLE_GOODS_BY_RECALL_ID);
        assert getPurchasableGoodsByRecallId != null;
        return getPurchasableGoodsByRecallId
                .data(globalModel).subscribe(iModels -> {
                    if (setupPurchasableTableInEditMode((PurchasableGoodsTable) iModels)) {
                        witchWorkForShowFormEditMode();
                    } else {
                        if (checkNull()) {
                            getView().showHideProgress(true);
                            getView().showMessageSomeProblems(CommonsLogErrorNumber.error_98);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_99);
                    }
                });
    }

    //todo تکمیل باقی مانده فرم کد ها
    private void witchWorkForShowFormEditMode() {
        switch (myFormCode) {
            case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                showFormReqGoodFromWareHouseEditMode();
                break;
            case BUYER_REQUEST_FORM_CODE:
                showFormBuyerReqEditMode();
                break;
            case PRODUCTION_FORM_CODE:
                showFormProductionEditMode();
                break;
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                showFormDeliveryProceedingsEditMode();
                break;
        }
    }


    //todo add other clickType
    @Override
    protected void clickRowRecycleFactoryMethod(int comboFormClickType, String someValue) {
        switch (comboFormClickType) {
            case CommonComboClickType.SELECT_GOODS_CLICK_TYPE:
                if (checkNull()) {
                    isearchDialogeListener = (realmModel, item, position) -> setDataFromSearchAbleSpinner(realmModel, item, position, CommonComboClickType.SELECT_GOODS_CLICK_TYPE);
                    SearchAbleSpinnerDialogHandler.showGoodsNameDBDialog(getView().getActivity(), filteredGoodsByWarehouse, isearchDialogeListener);
                }
                break;
            case CommonComboClickType.SELECT_UNIT_CLICK_TYPE:
                //if we set my goods
                if (checkNull() && !goodId.equals(Commons.NULL_INTEGER) && goodSUOMEditModeList != null) {
                    isearchDialogeListener = (realmModel, item, position) -> setDataFromSearchAbleSpinner(realmModel, item, position, CommonComboClickType.SELECT_UNIT_CLICK_TYPE);
                    SearchAbleSpinnerDialogHandler.show(getView().getActivity(), GoodSUOMJsonHelper.getGoodSuomUnitNameList(goodSUOMEditModeList), isearchDialogeListener);
                } else {
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_GOODS_CLICK_TYPE, new ComboFormModel(context.getString(R.string.good_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                }
                break;
            case CommonComboClickType.SELECT_CONF_8_CLICK_TYPE:
                showSearchAbleSpinnerOfFormRefConfig(8, CommonComboClickType.SELECT_CONF_8_CLICK_TYPE);
                break;
            case CommonComboClickType.SELECT_CONF_9_CLICK_TYPE:
                showSearchAbleSpinnerOfFormRefConfig(9, CommonComboClickType.SELECT_CONF_9_CLICK_TYPE);
                break;
            case CommonComboClickType.SELECT_CONF_10_CLICK_TYPE:
                showSearchAbleSpinnerOfFormRefConfig(10, CommonComboClickType.SELECT_CONF_10_CLICK_TYPE);
                break;
            case CommonComboClickType.SELECT_CONF_15_CLICK_TYPE:
                showSearchAbleSpinnerOfFormRefConfig(15, CommonComboClickType.SELECT_CONF_15_CLICK_TYPE);
                break;
            case CommonComboClickType.SELECT_LENGTH_CLICK_TYPE:
                //no need to update,  dataList were updated in holder
                length = someValue;
                break;
            case CommonComboClickType.SELECT_WIDTH_CLICK_TYPE:
                //no need to update,  dataList were updated in holder
                width = someValue;
                break;
            case CommonComboClickType.SELECT_HEIGHT_CLICK_TYPE:
                //no need to update,  dataList were updated in holder
                height = someValue;
                break;
            case CommonComboClickType.SELECT_ALLOY_CLICK_TYPE:
                //no need to update,  dataList were updated in holder
                alloy = someValue;
                break;
            case CommonComboClickType.SELECT_COLUMN_NUMBER_CLICK_TYPE:
                showSearchAbleSpinner(makeProperArrayForColumnNumber(), CommonComboClickType.SELECT_COLUMN_NUMBER_CLICK_TYPE);
                break;
            case CommonComboClickType.SUB_NUMBER_CLICK_TYPE:
                if (!someValue.equals(amount2)) {
                    amount2 = someValue.equals(Commons.SPACE) ? Commons.NULL_INTEGER : someValue;
                    setupAmount2(someValue);
                }
                break;
            case CommonComboClickType.SELECT_CURRENCY_UNIT_PRICE_2_CLICK_TYPE:
                if (!someValue.equals(currencyUnitPrice2)) {
                    currencyUnitPrice2 = someValue.equals(Commons.SPACE) ? Commons.NULL_INTEGER : someValue;
                    setupCurrencyUnitPrice2(someValue);
                }
                break;
            case CommonComboClickType.SELECT_DISCOUNT_PERCENTAGE_CLICK_TYPE:
                if (!someValue.equals(discountPercentage)) {
                    discountPercentage = someValue.equals(Commons.SPACE) ? Commons.NULL_INTEGER : someValue;
                    setupDiscountPercentage(someValue);
                }
                break;
            case CommonComboClickType.SELECT_TAX_PERCENTAGE_CLICK_TYPE:
                if (!someValue.equals(taxPercentage)) {
                    taxPercentage = someValue.equals(Commons.SPACE) ? Commons.NULL_INTEGER : someValue;
                    setupTaxPercent(someValue);
                }
                break;
            case CommonComboClickType.SELECT_DISCOUNT_2_CLICK_TYPE:
                if (!someValue.equals(currencyDiscount2)) {
                    currencyDiscount2 = someValue.equals(Commons.SPACE) ? Commons.NULL_INTEGER : someValue;
                    setupDiscount2(someValue);
                }
                break;
            case CommonComboClickType.BATCH_NUMBER_CLICK_TYPE:
                batchNumber = someValue;
                break;
            case CommonComboClickType.BOM_CLICK_TYPE:
                Toast.makeText(context, "مقدار مناسب برای BOM", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    private ArrayList<String> makeProperArrayForColumnNumber() {
        ArrayList<String> arrayList = new ArrayList<>(20);
        for (int i = Commons.PRIMITIVE_GOOD_COLUMN_NUMBER_VALUE; i < 21; i++) {
            arrayList.add(String.valueOf(i));
        }
        return arrayList;
    }

    //todo add other clickType
    @Override
    protected void setDataFromSearchAbleSpinner(@Nullable RealmModel realmModel, @Nullable String item, int position, int commonComboClickType) {
        if (checkNull() && item != null) {
            comboFormRecAdaptor.updateRowValue(commonComboClickType, new ComboFormModel(Commons.NULL, item, Commons.NULL, false), false);
            switch (commonComboClickType) {
                case CommonComboClickType.SELECT_GOODS_CLICK_TYPE:
                    if (realmModel != null) {
                        if (setupGoodTableInEditMode((GoodsTable) realmModel)) {
                            calculateAfterGoodId();
                        } else {
                            if (checkNull()) {
                                getView().showHideProgress(true);
                                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_95);
                            }
                        }
                    }
                    break;
                case CommonComboClickType.SELECT_UNIT_CLICK_TYPE:
                    //here realm is null, because we do not show list from realm
                    if (goodSUOMEditModeList != null) {
                        calculateAfterUnitId(GoodSUOMJsonHelper.getGoodSuomPosition(goodSUOMEditModeList, item));
                    }
                    break;
                case CommonComboClickType.SELECT_CONF_8_CLICK_TYPE:
                    if (realmModel != null) {
                        BaseCodingTable baseCodingTable1 = (BaseCodingTable) realmModel;
                        B5IdRefId8 = baseCodingTable1.getId();
                    }
                    break;
                case CommonComboClickType.SELECT_CONF_9_CLICK_TYPE:
                    if (realmModel != null) {
                        BaseCodingTable baseCodingTable2 = (BaseCodingTable) realmModel;
                        B5IdRefId9 = baseCodingTable2.getId();
                    }
                    break;
                case CommonComboClickType.SELECT_CONF_10_CLICK_TYPE:
                    if (realmModel != null) {
                        BaseCodingTable baseCodingTable3 = (BaseCodingTable) realmModel;
                        B5IdRefId10 = baseCodingTable3.getId();
                    }
                    break;
                case CommonComboClickType.SELECT_CONF_15_CLICK_TYPE:
                    if (realmModel != null) {
                        BaseCodingTable baseCodingTable4 = (BaseCodingTable) realmModel;
                        B5IdRefId15 = baseCodingTable4.getId();
                    }
                    break;
                case CommonComboClickType.SELECT_COLUMN_NUMBER_CLICK_TYPE:
                    goodColumnNumber = Integer.valueOf(item.trim());
                    break;
            }

        }
    }

    private boolean setupGoodTableInEditMode(GoodsTable goodsTable) {
        if (!goodsTable.getId().equals(Commons.NULL_INTEGER)) {
            goodsTableEditMode = goodsTable;
            //we sure we have goodsTable correctly
            goodSUOMEditModeList = GoodSUOMJsonHelper.getGoodSUOMArray(goodsTable.getGoodSUOMList());
            return true;
        } else {
            goodsTableEditMode = null;
            goodSUOMEditModeList = null;
            return false;
        }
    }

    private boolean setupSalableTableInEditMode(SalableGoodsTable salableGoodsTable) {
        if (!salableGoodsTable.getId().equals(Commons.NULL_INTEGER)) {
            salableGoodsTableEditMode = salableGoodsTable;
            setupNumeratorValues(salableGoodsTable.getNumerator(), salableGoodsTable.getDenominator());
            return true;
        } else {
            salableGoodsTableEditMode = null;
            return false;
        }
    }

    private boolean setupPurchasableTableInEditMode(PurchasableGoodsTable purchasableGoodsTable) {
        if (!purchasableGoodsTable.getId().equals(Commons.NULL_INTEGER)) {
            purchasableGoodsTableEditMode = purchasableGoodsTable;
            setupNumeratorValues(purchasableGoodsTableEditMode.getNumerator(), purchasableGoodsTableEditMode.getDenominator());
            return true;
        } else {
            purchasableGoodsTableEditMode = null;
            return false;
        }
    }

    private void setupNumeratorValues(String numerator, String deNumerator) {
        this.numerator = numerator;
        this.deNumerator = deNumerator;
    }

    @Override
    protected void checkData() {

        if (goodId.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_GOODS_CLICK_TYPE, new ComboFormModel(context.getString(R.string.good_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showHideProgress(false);
                getView().showMessageFillProperValue();
            }
            return;
        }
        if (C5UnitId.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_UNIT_CLICK_TYPE, new ComboFormModel(context.getString(R.string.good_sub_unit_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showHideProgress(false);
                getView().showMessageFillProperValue();
            }
            return;
        }
        if (amount2.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageSubValueNotSelected();
            }
            return;
        }
        if (checkCurrencyUnitPrice2()) {
            if (currencyUnitPrice2.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageCurrencyUnitPrice();
                }
                return;
            }

        }

        if (checkDiscountPercentage()) {
            if (discountPercentage.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageDiscountPercantage();
                }
                return;
            }
        }

        if (checkTaxPercentage()) {
            if (taxPercentage.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageTaxPercantage();
                }
                return;
            }
        }

        if (checkCurrencyDisCount2()) {
            if (currencyDiscount2.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageCurrencyDisCount2();
                }
                return;
            }
        }

        if (checkHaveConfig(8)) {
            if (B5IdRefId8.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_CONF_8_CLICK_TYPE, new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    getView().showHideProgress(false);
                    getView().showMessageFillProperValue();
                }
                return;
            }

        }

        if (checkHaveConfig(9)) {
            if (B5IdRefId9.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_CONF_9_CLICK_TYPE, new ComboFormModel(context.getString(R.string.consumable), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    getView().showHideProgress(false);
                    getView().showMessageFillProperValue();
                }
                return;
            }
        }

        if (checkHaveConfig(10)) {
            if (B5IdRefId10.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_CONF_10_CLICK_TYPE, new ComboFormModel(context.getString(R.string.production_process), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    getView().showHideProgress(false);
                    getView().showMessageFillProperValue();
                }
                return;
            }
        }

        if (checkHaveConfig(15)) {
            if (B5IdRefId15.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_CONF_15_CLICK_TYPE, new ComboFormModel(context.getString(R.string.warehouse), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    getView().showHideProgress(false);
                    getView().showMessageFillProperValue();
                }
                return;
            }
        }

        setupNeededGlobalCalculation(false);

        //should be after calculation
        if(CalculationHelper.isNegativeValue(currencyNetValue)){
            if(myFormCode.equals(GOODS_DELIVERY_PROCEEDINGS_FORM_CODE) || myFormCode.equals(BUYER_REQUEST_FORM_CODE)){
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageNegativeTotalUnitPrice();
                }
                return;
            }

        }

        if (myFormCode.equals(BUYER_REQUEST_FORM_CODE)) {
            setupTempAmountSalableBeforeExit();
        } else if (myFormCode.equals(GOODS_DELIVERY_PROCEEDINGS_FORM_CODE)) {
            setupTempAmountPurchasableBeforeExit();
        } else {
            createResultJsonAndSendBack();
        }
    }

    private void setupTempAmountSalableBeforeExit() {
        iRefineTempAmountListener = new IRefineTempAmountListener() {
            @Override
            public void done() {
                createResultJsonAndSendBack();
            }

            @Override
            public void error() {
                if (checkNull()) {
                    getView().showHideProgress(true);
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_105);
                }
            }
        };
        addDisposable(RefinePurchasableSalableTempAmountHandler.doRefineSalable(getProperModelForChangeTempValue(salableGoodsTableEditMode.getId()), iRefineTempAmountListener));
    }

    private void setupTempAmountPurchasableBeforeExit() {
        iRefineTempAmountListener = new IRefineTempAmountListener() {
            @Override
            public void done() {
                createResultJsonAndSendBack();
            }

            @Override
            public void error() {
                if (checkNull()) {
                    getView().showHideProgress(true);
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_104);
                }
            }
        };
        addDisposable(RefinePurchasableSalableTempAmountHandler.doRefinePurchasable(getProperModelForChangeTempValue(purchasableGoodsTableEditMode.getId()), iRefineTempAmountListener));

    }

    private GlobalModel getProperModelForChangeTempValue(String id) {
        ArrayList<String> idArrayList = new ArrayList<>();
        ArrayList<String> tempArrayList = new ArrayList<>();
        idArrayList.add(id);
        tempArrayList.add(CalculationHelper.minusAnyAndRoundNonMoneyValue(primitiveAmount2, amount2, context));
        GlobalModel globalModel = new GlobalModel();
        globalModel.setStringArrayList(idArrayList);
        globalModel.setStringArrayList2(tempArrayList);
        return globalModel;
    }

    private void createResultJsonAndSendBack() {

        ArrayList<GoodTransDetailsModel> goodTransDetailsModelArrayList = new ArrayList<>(1);
        goodTransDetailsModelArrayList.add(new GoodTransDetailsModel(id, idRecallDetail, goodColumnNumber, goodTransModelGUID, goodId, C5UnitId, length, width, height, alloy, requestAmount1, requestAmount2, amount, amount2, currencyUnitPrice2, currencyUnitPrice1, currencyTotalValue, discountPercentage, currencyDiscount1, currencyDiscount2, taxPercentage, currencyTaxValue, currencyNetValue, currencyNetUnitPrice2, B5IdRefId8, B5IdRefId9, B5IdRefId10, B5IdRefId15, batchNumber, I5BOMId));
        if (checkNull()) {
            ActivityIntents.resultOkFromGoodForGoodTranceAct(getView().getActivity(), goodTransDetailsModelArrayList);
        }
    }

    //todo تکمیل باقی مانده فرم کد ها
    private boolean checkCurrencyUnitPrice2() {
        switch (myFormCode) {
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
            case BUYER_REQUEST_FORM_CODE:
                return true;
            default:
                return false;

        }
    }

    //todo تکمیل باقی مانده فرم کد ها
    private boolean checkDiscountPercentage() {
        switch (myFormCode) {
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
            case BUYER_REQUEST_FORM_CODE:
                return true;
            default:
                return false;

        }
    }

    //todo تکمیل باقی مانده فرم کد ها
    private boolean checkTaxPercentage() {
        switch (myFormCode) {
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
            case BUYER_REQUEST_FORM_CODE:
                return true;
            default:
                return false;

        }
    }

    //todo تکمیل باقی مانده فرم کد ها
    private boolean checkCurrencyDisCount2() {
        switch (myFormCode) {
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
            case BUYER_REQUEST_FORM_CODE:
                return true;
            default:
                return false;

        }
    }

    @Override
    protected void workNextClick() {
        //we have not next click here
    }

    @Override
    protected void workDeleteClick() {
        //we have not delete click here, user can delete from master form(GoodTransActivity)
    }

    private void calculateAfterGoodId() {
        if (goodsTableEditMode != null) {
            goodId = goodsTableEditMode.getId();
            //set default unitId, we sure we have minimum one row
            calculateAfterUnitId(0);
        }
    }

    private void calculateAfterUnitId(int positionOfSUOMRow) {
        if (checkNull()) {
            getView().showHideProgress(true);
        }
        //حتی اگر یوزر مقداری برای این فیلد انتخاب کرده است نیز باید تغییر کند چون از جایی صدا میشود که تغییر حتمی است
        if (goodSUOMEditModeList != null) {
            goodSUOMModelEditMode = goodSUOMEditModeList.get(positionOfSUOMRow);
            setupNumeratorValues(String.valueOf(goodSUOMModelEditMode.getNumerator()), String.valueOf(goodSUOMModelEditMode.getDeNominator()));
            C5UnitId = goodSUOMModelEditMode.getC5UnitId();
            length = goodSUOMModelEditMode.getLength();
            width = goodSUOMModelEditMode.getWidth();
            height = goodSUOMModelEditMode.getHeight();
            alloy = goodSUOMModelEditMode.getAlloy();

            comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_UNIT_CLICK_TYPE, new ComboFormModel(context.getString(R.string.good_sub_unit_name), goodSUOMModelEditMode.getUnitName(), Commons.NULL, false), false);
            if (showGoodUnit && checkNull()) {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_LENGTH_CLICK_TYPE, new ComboFormModel(context.getString(R.string.length), length, Commons.NULL, false), false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_WIDTH_CLICK_TYPE, new ComboFormModel(context.getString(R.string.width), width, Commons.NULL, false), false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_HEIGHT_CLICK_TYPE, new ComboFormModel(context.getString(R.string.height), height, Commons.NULL, false), false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_ALLOY_CLICK_TYPE, new ComboFormModel(context.getString(R.string.alloy), alloy, Commons.NULL, false), false);
            }

        }
        if (checkNull()) {
            getView().showHideProgress(false);
        }
    }

    private void setupAmount2(String someValue) {
        if (C5UnitId.equals(Commons.NULL_INTEGER) && !someValue.equals(Commons.SPACE)) {
            if (checkNull()) {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_GOODS_CLICK_TYPE, new ComboFormModel(context.getString(R.string.good_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SUB_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sub_number), Commons.SPACE, Commons.NULL, false), false);
            }
            calculateAfterAmount2(Commons.SPACE);
        } else {
            calculateAfterAmount2(someValue);
        }
    }

    private void calculateAfterAmount2(String mAmount2) {
        if (!CalculationHelper.numberValidation(mAmount2) && !mAmount2.equals(Commons.SPACE)) {
            if (checkNull()) {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SUB_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sub_number), Commons.SPACE, Commons.NULL, false), false);
            }
            //todo add other form code
        } else if (myFormCode.equals(BUYER_REQUEST_FORM_CODE) && !mAmount2.equals(Commons.SPACE)) {
            if (checkNull() && CalculationHelper.isNegativeValue(CalculationHelper.minusAnyAndRoundNonMoneyValue(CalculationHelper.plusAnyAndRoundNonMoneyValue(salableGoodsTableEditMode.getTempAvailableAmount2(), primitiveAmount2, context), mAmount2, context))) {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SUB_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sub_number), Commons.SPACE, Commons.NULL, false), false);
                getView().showHideProgress(false);
                getView().showMessageOverFlowAmount();
            } else {
                setupNeededGlobalCalculation(true);
            }
        } else if (myFormCode.equals(GOODS_DELIVERY_PROCEEDINGS_FORM_CODE) && !mAmount2.equals(Commons.SPACE)) {
            if (checkNull() && CalculationHelper.isNegativeValue(CalculationHelper.minusAnyAndRoundNonMoneyValue(CalculationHelper.plusAnyAndRoundNonMoneyValue(purchasableGoodsTableEditMode.getTempAmount2(), primitiveAmount2, context), mAmount2, context))) {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SUB_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sub_number), Commons.SPACE, Commons.NULL, false), false);
                getView().showHideProgress(false);
                getView().showMessageOverFlowAmount();
            } else {
                setupNeededGlobalCalculation(true);
            }
        }
    }

    private void setupCurrencyUnitPrice2(String someValue) {
        if (C5UnitId.equals(Commons.NULL_INTEGER) && !someValue.equals(Commons.SPACE)) {
            if (checkNull()) {
                //چون اگر کالا انتخاب میشد حتما unitId داشتیم
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_GOODS_CLICK_TYPE, new ComboFormModel(context.getString(R.string.good_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_CURRENCY_UNIT_PRICE_2_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sub_unit_price), Commons.SPACE, Commons.NULL, false), false);
            }
            calculateAfterCurrencyUnitPrice2(Commons.SPACE);
        } else {
            calculateAfterCurrencyUnitPrice2(someValue);
        }

    }

    private void calculateAfterCurrencyUnitPrice2(String someValue) {
        if (!CalculationHelper.numberValidation(someValue) && !someValue.equals(Commons.SPACE)) {
            //no need update any things, all will be update in second condition
            if (checkNull()) {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_CURRENCY_UNIT_PRICE_2_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sub_unit_price), Commons.SPACE, Commons.NULL, false), false);
            }
        } else if (!someValue.equals(Commons.SPACE)) {
            setupNeededGlobalCalculation(true);
        }

    }

    private void setupDiscountPercentage(String someValue) {
        if (C5UnitId.equals(Commons.NULL_INTEGER) && !someValue.equals(Commons.SPACE)) {
            if (checkNull()) {
                //چون اگر کالا انتخاب میشد حتما unitId داشتیم
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_GOODS_CLICK_TYPE, new ComboFormModel(context.getString(R.string.good_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_DISCOUNT_PERCENTAGE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.discount_percentage), Commons.SPACE, Commons.NULL, false), false);
            }
            calculateAfterDiscountPercentage(Commons.SPACE);
        } else {
            calculateAfterDiscountPercentage(someValue);
        }


    }

    private void calculateAfterDiscountPercentage(String someValue) {
        if (!CalculationHelper.numberValidation(someValue) && !someValue.equals(Commons.SPACE)) {
            //no need update any things, all will be update in second condition
            if (checkNull()) {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_DISCOUNT_PERCENTAGE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.discount_percentage), Commons.SPACE, Commons.NULL, false), false);
            }
        } else if (!someValue.equals(Commons.SPACE)) {

            if(CalculationHelper.isNegativeValue(CalculationHelper.minusAnyAndRoundNonMoneyValue(Commons.HUNDRED_STRING,someValue,context))) {
                if (checkNull()) {
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_DISCOUNT_PERCENTAGE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.discount_percentage), Commons.SPACE, Commons.NULL, false), false);
                }

            }else {
            setupNeededGlobalCalculation(true);
            }
        }


    }

    private void setupTaxPercent(String someValue) {
        if (C5UnitId.equals(Commons.NULL_INTEGER) && !someValue.equals(Commons.SPACE)) {
            if (checkNull()) {
                //چون اگر کالا انتخاب میشد حتما unitId داشتیم
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_GOODS_CLICK_TYPE, new ComboFormModel(context.getString(R.string.good_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_TAX_PERCENTAGE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.tax_percentag), Commons.SPACE, Commons.NULL, false), false);
            }
            calculateAfterTaxPercent(Commons.SPACE);
        } else {
            calculateAfterTaxPercent(someValue);
        }

    }

    private void calculateAfterTaxPercent(String someValue) {
        if (!CalculationHelper.numberValidation(someValue) && !someValue.equals(Commons.SPACE)) {
            //no need update any things, all will be update in second condition
            if (checkNull()) {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_TAX_PERCENTAGE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.tax_percentag), Commons.SPACE, Commons.NULL, false), false);
            }
        } else if (!someValue.equals(Commons.SPACE)) {
            if(CalculationHelper.isNegativeValue(CalculationHelper.minusAnyAndRoundNonMoneyValue(Commons.HUNDRED_STRING,someValue,context))) {
                if (checkNull()) {
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_TAX_PERCENTAGE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.tax_percentag), Commons.SPACE, Commons.NULL, false), false);
                }

            }else {

            setupNeededGlobalCalculation(true);
            }
        }

    }

    private void setupDiscount2(String someValue) {
        if (C5UnitId.equals(Commons.NULL_INTEGER) && !someValue.equals(Commons.SPACE)) {
            if (checkNull()) {
                //چون اگر کالا انتخاب میشد حتما unitId داشتیم
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_GOODS_CLICK_TYPE, new ComboFormModel(context.getString(R.string.good_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_DISCOUNT_2_CLICK_TYPE, new ComboFormModel(context.getString(R.string.discount_2), Commons.SPACE, Commons.NULL, false), false);
            }
            calculateAfterDiscount2(Commons.SPACE);
        } else {
            calculateAfterDiscount2(someValue);
        }

    }

    private void calculateAfterDiscount2(String someValue) {
        if (!CalculationHelper.numberValidation(someValue) && !someValue.equals(Commons.SPACE)) {
            //no need update any things, all will be update in second condition
            if (checkNull()) {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_DISCOUNT_2_CLICK_TYPE, new ComboFormModel(context.getString(R.string.discount_2), Commons.SPACE, Commons.NULL, false), false);
            }
        } else if (!someValue.equals(Commons.SPACE)) {
            setupNeededGlobalCalculation(true);
        }
    }

    /**
     * should call in last step of a method
     * manner of method should not be changed
     * no need to check discount values
     */
    private void setupNeededGlobalCalculation(boolean needUpdateUI) {
        if (amount2.equals(Commons.NULL_INTEGER)) {
            amount = Commons.NULL_INTEGER;
            currencyUnitPrice1 = Commons.NULL_INTEGER;
            currencyDiscount1 = Commons.NULL_INTEGER;//
            currencyDiscountTotal = Commons.ZERO_STRING;//only for show
            currencyTotalValue = Commons.NULL_INTEGER;
            currencyTaxValue = Commons.NULL_INTEGER;
            currencyNetValue = Commons.NULL_INTEGER;
            currencyNetUnitPrice2 = Commons.NULL_INTEGER;

            if (checkNull() && needUpdateUI) {
                //todo add other form code
                if (myFormCode.equals(GOODS_DELIVERY_PROCEEDINGS_FORM_CODE) || myFormCode.equals(BUYER_REQUEST_FORM_CODE)) {

                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_18, new ComboFormModel(Commons.NULL, Commons.DASH, Commons.NULL, false), false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_20, new ComboFormModel(Commons.NULL, Commons.DASH, Commons.NULL, false), false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_22, new ComboFormModel(Commons.NULL, Commons.DASH, Commons.NULL, false), false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_24, new ComboFormModel(Commons.NULL, Commons.DASH, Commons.NULL, false), false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_26, new ComboFormModel(Commons.NULL, Commons.DASH, Commons.NULL, false), false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_28, new ComboFormModel(Commons.NULL, Commons.DASH, Commons.NULL, false), false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_16, new ComboFormModel(Commons.NULL, Commons.DASH, Commons.NULL, false), false);
                }
            }

        } else {
            amount = CalculationHelper.multipleAnyDividedAnyAndRoundNonMoneyValue(amount2, goodSUOMModelEditMode != null ? numerator : Commons.PLUS_STRING, goodSUOMModelEditMode != null ? deNumerator : Commons.PLUS_STRING, context);
            currencyUnitPrice1 = CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(currencyUnitPrice2, goodSUOMModelEditMode != null ? numerator : Commons.PLUS_STRING, goodSUOMModelEditMode != null ? deNumerator : Commons.PLUS_STRING, context);
            currencyTotalValue = currencyUnitPrice2.equals(Commons.NULL_INTEGER) ? Commons.NULL_INTEGER : CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(amount2, currencyUnitPrice2, Commons.PLUS_STRING, context);
            currencyDiscount1 = CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(currencyUnitPrice1, discountPercentage, Commons.HUNDRED_STRING, context);
            currencyDiscountTotal = currencyTotalValue.equals(Commons.NULL_INTEGER) ? Commons.ZERO_STRING : CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(currencyTotalValue, discountPercentage, Commons.HUNDRED_STRING, context);//only for show
            currencyTaxValue = currencyTotalValue.equals(Commons.NULL_INTEGER) ? Commons.NULL_INTEGER : CalculationHelper.currencyTaxValueCalculation(discountPercentage.equals(Commons.NULL_INTEGER) ? Commons.ZERO_STRING : discountPercentage, currencyTotalValue, taxPercentage.equals(Commons.NULL_INTEGER) ? String.valueOf(SharedPreferenceProvider.getTaxPercent(context)) : taxPercentage, currencyDiscount2.equals(Commons.NULL_INTEGER) ? Commons.ZERO_STRING : currencyDiscount2, context);
            currencyNetValue = currencyTotalValue.equals(Commons.NULL_INTEGER) ? Commons.NULL_INTEGER : CalculationHelper.currencyNetValueCalculation(discountPercentage.equals(Commons.NULL_INTEGER) ? Commons.ZERO_STRING : discountPercentage, currencyTotalValue, taxPercentage.equals(Commons.NULL_INTEGER) ? String.valueOf(SharedPreferenceProvider.getTaxPercent(context)) : taxPercentage, currencyDiscount2.equals(Commons.NULL_INTEGER) ? Commons.ZERO_STRING : currencyDiscount2, context);
            currencyNetUnitPrice2 = currencyTotalValue.equals(Commons.NULL_INTEGER) ? Commons.NULL_INTEGER : CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(currencyNetValue, Commons.PLUS_STRING, amount2, context);
            if (checkNull() && needUpdateUI) {
                //todo add other form code
                if (myFormCode.equals(GOODS_DELIVERY_PROCEEDINGS_FORM_CODE) || myFormCode.equals(BUYER_REQUEST_FORM_CODE)) {

                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_18, new ComboFormModel(Commons.NULL, amount, Commons.NULL, false), false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_20, new ComboFormModel(Commons.NULL, currencyUnitPrice1, Commons.NULL, false), false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_22, new ComboFormModel(Commons.NULL, currencyDiscountTotal, Commons.NULL, false), false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_24, new ComboFormModel(Commons.NULL, currencyTaxValue, Commons.NULL, false), false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_26, new ComboFormModel(Commons.NULL, currencyTotalValue, Commons.NULL, false), false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_28, new ComboFormModel(Commons.NULL, currencyNetValue, Commons.NULL, false), false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_16, new ComboFormModel(Commons.NULL, currencyNetUnitPrice2, Commons.NULL, false), false);
                }
            }
        }
        if (canChangeReqAmount) {
            requestAmount1 = amount;
            requestAmount2 = amount2;
        }
    }

    //endregion
//----------------------------------------------------------------------------------------------
    //region ReqGoodFromWareHouse
    private void setupCustomLayoutManagerReqGoodFromWareHouse() {

        ArrayList<Integer> listOfQuarterPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfThirdPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfHalfPosition = new ArrayList<>();//empty

        if (checkNull()) {
            getView().setupStairCustomLayoutManager(listOfQuarterPosition, listOfThirdPosition, listOfHalfPosition);
        }
    }

    private void showFormReqGoodFromWareHouse() {
        if (checkNull()) {
            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToReqGoodFromWareHouse(), setProperClickRowTypeToReqGoodFromWareHouse(), setProperDataToReqGoodFromWareHouse());
            getView().showHideProgress(false);
            getView().showHideConslayGoodForGoodTrance(true);
        }
    }

    //in goodTable guid is same id
    private void showFormReqGoodFromWareHouseEditMode() {
        comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToReqGoodFromWareHouse(), setProperClickRowTypeToReqGoodFromWareHouse(), setProperDataToReqGoodFromWareHouseEditMode());
        getView().showHideProgress(false);
        getView().showHideConslayGoodForGoodTrance(true);

    }

    private ArrayList<Integer> setProperRowTypeToReqGoodFromWareHouse() {

        ArrayList<Integer> comboFormType = new ArrayList<>();


        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//select goods
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//select C5UnitId
        if (showGoodUnit) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//length
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//width
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//height
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_18);//alloy
        }
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//amount2

        if (checkHaveConfig(8)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId8
        }
        if (checkHaveConfig(9)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId9
        }
        if (checkHaveConfig(10)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId10
        }
        if (checkHaveConfig(15)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId15
        }

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//goodColumnNumber

        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToReqGoodFromWareHouse() {

        ArrayList<Integer> comboFormClickType = new ArrayList<>();


        comboFormClickType.add(CommonComboClickType.SELECT_GOODS_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.SELECT_UNIT_CLICK_TYPE);
        if (showGoodUnit) {
            comboFormClickType.add(CommonComboClickType.SELECT_LENGTH_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_WIDTH_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_HEIGHT_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_ALLOY_CLICK_TYPE);
        }
        comboFormClickType.add(CommonComboClickType.SUB_NUMBER_CLICK_TYPE);

        if (checkHaveConfig(8)) {
            comboFormClickType.add(CommonComboClickType.SELECT_CONF_8_CLICK_TYPE);
        }
        if (checkHaveConfig(9)) {
            comboFormClickType.add(CommonComboClickType.SELECT_CONF_9_CLICK_TYPE);
        }
        if (checkHaveConfig(10)) {
            comboFormClickType.add(CommonComboClickType.SELECT_CONF_10_CLICK_TYPE);
        }
        if (checkHaveConfig(15)) {
            comboFormClickType.add(CommonComboClickType.SELECT_CONF_15_CLICK_TYPE);
        }


        comboFormClickType.add(CommonComboClickType.SELECT_COLUMN_NUMBER_CLICK_TYPE);


        return comboFormClickType;
    }

    private List<ComboFormModel> setProperDataToReqGoodFromWareHouse() {
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();


        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_name), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_sub_unit_name), Commons.NULL, Commons.NULL, false));
        if (showGoodUnit) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.length), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.width), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.height), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.alloy), Commons.NULL, Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.sub_number), Commons.NULL, Commons.NULL, false));


        if (checkHaveConfig(8)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(9)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.production_process), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(10)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.consumable), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(15)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse), Commons.NULL, Commons.NULL, false));
        }


        comboFormModels.add(new ComboFormModel(context.getString(R.string.variety), PRIMITIVE_GOOD_COLUMN_NUMBER, Commons.NULL, false));
        //comboFormModels.add(new ComboFormModel(context.getString(R.string.formula), Commons.NULL, Commons.NULL, false));
        //comboFormModels.add(new ComboFormModel(context.getString(R.string.batch_number), Commons.NULL, Commons.NULL, false));


        return comboFormModels;
    }

    private List<ComboFormModel> setProperDataToReqGoodFromWareHouseEditMode() {

        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_name), goodsTableEditMode.getName(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_sub_unit_name), GoodSUOMJsonHelper.getGoodSuomUnitName(goodSUOMEditModeList, C5UnitId), Commons.NULL, false));
        if (showGoodUnit) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.length), goodTransDetailsModel.getLength(), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.width), goodTransDetailsModel.getWidth(), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.height), goodTransDetailsModel.getHeight(), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.alloy), goodTransDetailsModel.getAlloy(), Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.sub_number), goodTransDetailsModel.getAmount2(), Commons.NULL, false));


        if (checkHaveConfig(8)) {
            if (myListBaseIdList.get(7).contains(B5IdRefId8)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), myListBaseNameList.get(7).get(myListBaseIdList.get(7).indexOf(B5IdRefId8) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(7).indexOf(B5IdRefId8)), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, Commons.NULL, false));
            }
        }
        if (checkHaveConfig(9)) {
            if (myListBaseIdList.get(8).contains(B5IdRefId9)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.production_process), myListBaseNameList.get(8).get(myListBaseIdList.get(8).indexOf(B5IdRefId9) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(8).indexOf(B5IdRefId9)), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.production_process), Commons.NULL, Commons.NULL, false));
            }
        }
        if (checkHaveConfig(10)) {
            if (myListBaseIdList.get(9).contains(B5IdRefId10)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.consumable), myListBaseNameList.get(9).get(myListBaseIdList.get(9).indexOf(B5IdRefId10) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(9).indexOf(B5IdRefId10)), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.consumable), Commons.NULL, Commons.NULL, false));
            }
        }
        if (checkHaveConfig(15)) {
            if (myListBaseIdList.get(14).contains(B5IdRefId15)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse), myListBaseNameList.get(14).get(myListBaseIdList.get(14).indexOf(B5IdRefId15) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(14).indexOf(B5IdRefId15)), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse), Commons.NULL, Commons.NULL, false));
            }
        }


        comboFormModels.add(new ComboFormModel(context.getString(R.string.variety), String.valueOf(goodTransDetailsModel.getGoodColumnNumber()), Commons.NULL, false));
        //comboFormModels.add(new ComboFormModel(context.getString(R.string.formula), Commons.NULL, goodTransDetailsModel.getI5BOMId(), false));
        //comboFormModels.add(new ComboFormModel(context.getString(R.string.batch_number), goodTransDetailsModel.getBatchNumber(), Commons.NULL, false));

        return comboFormModels;
    }

    //endregion
//----------------------------------------------------------------------------------------------
    //region Production
    private void setupCustomLayoutManagerProduction() {
        ArrayList<Integer> listOfQuarterPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfThirdPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfHalfPosition = new ArrayList<>();//empty
        if (checkNull()) {
            getView().setupStairCustomLayoutManager(listOfQuarterPosition, listOfThirdPosition, listOfHalfPosition);
        }
    }

    private void showFormProduction() {
        if (checkNull()) {
            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToProduction(), setProperClickRowTypeToProduction(), setProperDataToProduction());
            getView().showHideProgress(false);
            getView().showHideConslayGoodForGoodTrance(true);
        }
    }

    //in goodTable guid is same id
    private void showFormProductionEditMode() {
        comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToProduction(), setProperClickRowTypeToProduction(), setProperDataToProductionEditMode());
        getView().showHideProgress(false);
        getView().showHideConslayGoodForGoodTrance(true);

    }

    private ArrayList<Integer> setProperRowTypeToProduction() {

        ArrayList<Integer> comboFormType = new ArrayList<>();


        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//select goods
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//select C5UnitId
        if (showGoodUnit) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//length
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//width
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//height
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_18);//alloy
        }
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//amount2

        if (checkHaveConfig(8)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId8
        }
        if (checkHaveConfig(9)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId9
        }
        if (checkHaveConfig(10)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId10
        }
        if (checkHaveConfig(15)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId15
        }

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//goodColumnNumber
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//I5BOMId
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_18);//batchNumber
        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToProduction() {

        ArrayList<Integer> comboFormClickType = new ArrayList<>();


        comboFormClickType.add(CommonComboClickType.SELECT_GOODS_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.SELECT_UNIT_CLICK_TYPE);
        if (showGoodUnit) {
            comboFormClickType.add(CommonComboClickType.SELECT_LENGTH_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_WIDTH_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_HEIGHT_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_ALLOY_CLICK_TYPE);
        }
        comboFormClickType.add(CommonComboClickType.SUB_NUMBER_CLICK_TYPE);

        if (checkHaveConfig(8)) {
            comboFormClickType.add(CommonComboClickType.SELECT_CONF_8_CLICK_TYPE);
        }
        if (checkHaveConfig(9)) {
            comboFormClickType.add(CommonComboClickType.SELECT_CONF_9_CLICK_TYPE);
        }
        if (checkHaveConfig(10)) {
            comboFormClickType.add(CommonComboClickType.SELECT_CONF_10_CLICK_TYPE);
        }
        if (checkHaveConfig(15)) {
            comboFormClickType.add(CommonComboClickType.SELECT_CONF_15_CLICK_TYPE);
        }


        comboFormClickType.add(CommonComboClickType.SELECT_COLUMN_NUMBER_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.BOM_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.BATCH_NUMBER_CLICK_TYPE);

        return comboFormClickType;
    }

    private List<ComboFormModel> setProperDataToProduction() {
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();


        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_name), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_sub_unit_name), Commons.NULL, Commons.NULL, false));
        if (showGoodUnit) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.length), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.width), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.height), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.alloy), Commons.NULL, Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.sub_number), Commons.NULL, Commons.NULL, false));


        if (checkHaveConfig(8)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(9)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.production_process), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(10)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.consumable), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(15)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse), Commons.NULL, Commons.NULL, false));
        }


        comboFormModels.add(new ComboFormModel(context.getString(R.string.variety), PRIMITIVE_GOOD_COLUMN_NUMBER, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.formula), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.batch_number), Commons.NULL, Commons.NULL, false));

        return comboFormModels;
    }

    private List<ComboFormModel> setProperDataToProductionEditMode() {

        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_name), goodsTableEditMode.getName(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_sub_unit_name), GoodSUOMJsonHelper.getGoodSuomUnitName(goodSUOMEditModeList, C5UnitId), Commons.NULL, false));
        if (showGoodUnit) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.length), goodTransDetailsModel.getLength(), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.width), goodTransDetailsModel.getWidth(), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.height), goodTransDetailsModel.getHeight(), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.alloy), goodTransDetailsModel.getAlloy(), Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.sub_number), goodTransDetailsModel.getAmount2(), Commons.NULL, false));

        if (checkHaveConfig(8)) {
            if (myListBaseIdList.get(7).contains(B5IdRefId8)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), myListBaseNameList.get(7).get(myListBaseIdList.get(7).indexOf(B5IdRefId8) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(7).indexOf(B5IdRefId8)), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, Commons.NULL, false));
            }
        }
        if (checkHaveConfig(9)) {
            if (myListBaseIdList.get(8).contains(B5IdRefId9)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.production_process), myListBaseNameList.get(8).get(myListBaseIdList.get(8).indexOf(B5IdRefId9) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(8).indexOf(B5IdRefId9)), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.production_process), Commons.NULL, Commons.NULL, false));
            }
        }
        if (checkHaveConfig(10)) {
            if (myListBaseIdList.get(9).contains(B5IdRefId10)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.consumable), myListBaseNameList.get(9).get(myListBaseIdList.get(9).indexOf(B5IdRefId10) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(9).indexOf(B5IdRefId10)), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.consumable), Commons.NULL, Commons.NULL, false));
            }
        }
        if (checkHaveConfig(15)) {
            if (myListBaseIdList.get(14).contains(B5IdRefId15)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse), myListBaseNameList.get(14).get(myListBaseIdList.get(14).indexOf(B5IdRefId15) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(14).indexOf(B5IdRefId15)), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse), Commons.NULL, Commons.NULL, false));
            }
        }

        comboFormModels.add(new ComboFormModel(context.getString(R.string.variety), String.valueOf(goodTransDetailsModel.getGoodColumnNumber()), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.formula), Commons.NULL, goodTransDetailsModel.getI5BOMId(), false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.batch_number), goodTransDetailsModel.getBatchNumber(), Commons.NULL, false));

        return comboFormModels;
    }

    //endregion
//----------------------------------------------------------------------------------------------
    //region BuyerReq
    private void setupCustomLayoutManagerBuyerReq() {
        ArrayList<Integer> listOfQuarterPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfThirdPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfHalfPosition = new ArrayList<>();//empty
        if (showGoodUnit) {
            listOfHalfPosition.add(7);
            listOfHalfPosition.add(8);
            listOfHalfPosition.add(10);
            listOfHalfPosition.add(11);
            listOfHalfPosition.add(13);
            listOfHalfPosition.add(14);
            listOfHalfPosition.add(16);
            listOfHalfPosition.add(17);
            listOfHalfPosition.add(19);
            listOfHalfPosition.add(20);
            listOfHalfPosition.add(21);
            listOfHalfPosition.add(22);
            listOfHalfPosition.add(23);
            listOfHalfPosition.add(24);

        } else {
            listOfHalfPosition.add(3);
            listOfHalfPosition.add(4);
            listOfHalfPosition.add(6);
            listOfHalfPosition.add(7);
            listOfHalfPosition.add(9);
            listOfHalfPosition.add(10);
            listOfHalfPosition.add(12);
            listOfHalfPosition.add(13);
            listOfHalfPosition.add(15);
            listOfHalfPosition.add(16);
            listOfHalfPosition.add(17);
            listOfHalfPosition.add(18);
            listOfHalfPosition.add(19);
            listOfHalfPosition.add(20);
        }
        if (checkNull()) {
            getView().setupStairCustomLayoutManager(listOfQuarterPosition, listOfThirdPosition, listOfHalfPosition);
        }
    }

    private void showFormBuyerReq() {
        //no need to any work , user can not received here
    }

    //in goodTable guid is same id
    private void showFormBuyerReqEditMode() {
        comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToBuyerReq(), setProperClickRowTypeToBuyerReq(), setProperDataToBuyerReqEditMode());
        getView().showHideProgress(false);
        getView().showHideConslayGoodForGoodTrance(true);

    }

    private List<Integer> setProperRowTypeToBuyerReq() {
        ArrayList<Integer> comboFormType = new ArrayList<>();


        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//select goods
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//select C5UnitId
        if (showGoodUnit) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//length
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//width
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//height
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//alloy
        }

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//amount2
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//amount
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//amount value

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//currency unit price 2
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CUP1
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CUP1 value


        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//discount percentage
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CD1
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CD1 value

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//tax percentage
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CTaxV
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CTaxV value

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//discount 2

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CTV
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CTV value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//netV
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//netV value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//NUP2
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//NUP2 value

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//goodColumnNumber


        if (checkHaveConfig(8)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//b5IdRefId8
        }
        if (checkHaveConfig(9)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//b5IdRefId9
        }
        if (checkHaveConfig(10)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//b5IdRefId10
        }
        if (checkHaveConfig(15)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//b5IdRefId15
        }


        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToBuyerReq() {

        ArrayList<Integer> comboFormClickType = new ArrayList<>();

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_2);
        if (showGoodUnit) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_5);
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_6);
        }


        comboFormClickType.add(CommonComboClickType.SUB_NUMBER_CLICK_TYPE); //amount2
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_17);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_18);


        comboFormClickType.add(CommonComboClickType.SELECT_CURRENCY_UNIT_PRICE_2_CLICK_TYPE);//currency unit price 2
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_19);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_20);


        comboFormClickType.add(CommonComboClickType.SELECT_DISCOUNT_PERCENTAGE_CLICK_TYPE);//discount percentage
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_21);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_22);

        comboFormClickType.add(CommonComboClickType.SELECT_TAX_PERCENTAGE_CLICK_TYPE);//tax percentage
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_23);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_24);

        comboFormClickType.add(CommonComboClickType.SELECT_DISCOUNT_2_CLICK_TYPE);//discount 2

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_25);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_26);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_27);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_28);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_15);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_16);


        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_14);

        if (checkHaveConfig(8)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_10);
        }
        if (checkHaveConfig(9)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_11);
        }
        if (checkHaveConfig(10)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_12);
        }
        if (checkHaveConfig(15)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_13);
        }

        return comboFormClickType;
    }

    private List<ComboFormModel> setProperDataToBuyerReq() {
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();
         /*
         user can not receive to here in buyer req
         */
        return comboFormModels;
    }

    private List<ComboFormModel> setProperDataToBuyerReqEditMode() {

        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();


        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_name), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_name), salableGoodsTableEditMode.getGoodName()), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_sub_unit_name), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_sub_unit_name), salableGoodsTableEditMode.getUnitName2()), Commons.NULL, false));
        if (showGoodUnit) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.length), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.length), goodTransDetailsModel.getLength()), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.width), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.width), goodTransDetailsModel.getWidth()), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.height), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.height), goodTransDetailsModel.getHeight()), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.alloy), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.alloy), goodTransDetailsModel.getAlloy()), Commons.NULL, false));
        }


        comboFormModels.add(new ComboFormModel(context.getString(R.string.sub_number), goodTransDetailsModel.getAmount2(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.amount), context.getString(R.string.amount), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(amount, amount, Commons.NULL, false));

        comboFormModels.add(new ComboFormModel(context.getString(R.string.sub_unit_price), goodTransDetailsModel.getCurrencyUnitPrice2(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.currency_unit_price_1), context.getString(R.string.currency_unit_price_1), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(currencyUnitPrice1, currencyUnitPrice1, Commons.NULL, false));

        comboFormModels.add(new ComboFormModel(context.getString(R.string.discount_percentage), goodTransDetailsModel.getDiscountPercentage(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.discount_currency), context.getString(R.string.discount_currency), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(currencyDiscountTotal, currencyDiscountTotal, Commons.NULL, false));

        comboFormModels.add(new ComboFormModel(context.getString(R.string.tax_percentag), goodTransDetailsModel.getTaxPercentage(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.tax_curreny), context.getString(R.string.tax_curreny), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(currencyTaxValue, currencyTaxValue, Commons.NULL, false));

        comboFormModels.add(new ComboFormModel(context.getString(R.string.discount_2), goodTransDetailsModel.getCurrencyDiscount2(), Commons.NULL, false));

        comboFormModels.add(new ComboFormModel(context.getString(R.string.currency_total_value), context.getString(R.string.currency_total_value), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(currencyTotalValue, currencyTotalValue, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.net_price), context.getString(R.string.net_price), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(currencyNetValue, currencyNetValue, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.net_price_sub), context.getString(R.string.net_price_sub), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(currencyNetUnitPrice2, currencyNetUnitPrice2, Commons.NULL, false));

        comboFormModels.add(new ComboFormModel(context.getString(R.string.variety), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.variety), String.valueOf(goodTransDetailsModel.getGoodColumnNumber())), Commons.NULL, false));


        if (checkHaveConfig(8)) {
            if (myListBaseIdList.get(7).contains(B5IdRefId8)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.center_cost), myListBaseNameList.get(7).get(myListBaseIdList.get(7).indexOf(B5IdRefId8) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(7).indexOf(B5IdRefId8))), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.center_cost), Commons.NULL), Commons.NULL, false));
            }
        }
        if (checkHaveConfig(9)) {
            if (myListBaseIdList.get(8).contains(B5IdRefId9)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.production_process), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.production_process), myListBaseNameList.get(8).get(myListBaseIdList.get(8).indexOf(B5IdRefId9) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(8).indexOf(B5IdRefId9))), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.production_process), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.production_process), Commons.NULL), Commons.NULL, false));
            }
        }
        if (checkHaveConfig(10)) {
            if (myListBaseIdList.get(9).contains(B5IdRefId10)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.consumable), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.consumable), myListBaseNameList.get(9).get(myListBaseIdList.get(9).indexOf(B5IdRefId10) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(9).indexOf(B5IdRefId10))), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.consumable), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.consumable), Commons.NULL), Commons.NULL, false));
            }
        }
        if (checkHaveConfig(15)) {
            if (myListBaseIdList.get(14).contains(B5IdRefId15)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.warehouse), myListBaseNameList.get(14).get(myListBaseIdList.get(14).indexOf(B5IdRefId15) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(14).indexOf(B5IdRefId15))), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.warehouse), Commons.NULL), Commons.NULL, false));
            }
        }

        return comboFormModels;
    }

    //endregion
//----------------------------------------------------------------------------------------------
    //region DeliveryProceedings

    private void setupCustomLayoutManagerDeliveryProceedings() {
        ArrayList<Integer> listOfQuarterPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfThirdPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfHalfPosition = new ArrayList<>();//empty

        if (showGoodUnit) {
            listOfHalfPosition.add(7);
            listOfHalfPosition.add(8);
            listOfHalfPosition.add(10);
            listOfHalfPosition.add(11);
            listOfHalfPosition.add(13);
            listOfHalfPosition.add(14);
            listOfHalfPosition.add(16);
            listOfHalfPosition.add(17);
            listOfHalfPosition.add(19);
            listOfHalfPosition.add(20);
            listOfHalfPosition.add(21);
            listOfHalfPosition.add(22);
            listOfHalfPosition.add(23);
            listOfHalfPosition.add(24);

        } else {
            listOfHalfPosition.add(3);
            listOfHalfPosition.add(4);
            listOfHalfPosition.add(6);
            listOfHalfPosition.add(7);
            listOfHalfPosition.add(9);
            listOfHalfPosition.add(10);
            listOfHalfPosition.add(12);
            listOfHalfPosition.add(13);
            listOfHalfPosition.add(15);
            listOfHalfPosition.add(16);
            listOfHalfPosition.add(17);
            listOfHalfPosition.add(18);
            listOfHalfPosition.add(19);
            listOfHalfPosition.add(20);
        }

        if (checkNull()) {
            getView().setupStairCustomLayoutManager(listOfQuarterPosition, listOfThirdPosition, listOfHalfPosition);
        }
    }

    private void showFormDeliveryProceedings() {
        //no need to any work , user can not received here
    }

    private void showFormDeliveryProceedingsEditMode() {
        comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToDeliveryProceedings(), setProperClickRowTypeToDeliveryProceedings(), setProperDataToDeliveryProceedingsEditMode());
        getView().showHideProgress(false);
        getView().showHideConslayGoodForGoodTrance(true);
    }

    private List<Integer> setProperRowTypeToDeliveryProceedings() {
        ArrayList<Integer> comboFormType = new ArrayList<>();


        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//select goods
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//select C5UnitId
        if (showGoodUnit) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//length
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//width
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//height
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//alloy
        }

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//amount2
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//amount
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//amount value

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//currency unit price 2
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CUP1
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CUP1 value


        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//discount percentage
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CD1
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CD1 value

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//tax percentage
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CTaxV
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CTaxV value

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_19);//discount 2

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CTV
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//CTV value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//netV
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//netV value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//NUP2
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//NUP2 value

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//goodColumnNumber


        if (checkHaveConfig(8)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//b5IdRefId8
        }
        if (checkHaveConfig(9)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//b5IdRefId9
        }
        if (checkHaveConfig(10)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//b5IdRefId10
        }
        if (checkHaveConfig(15)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//b5IdRefId15
        }

        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToDeliveryProceedings() {

        ArrayList<Integer> comboFormClickType = new ArrayList<>();


        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_2);
        if (showGoodUnit) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_5);
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_6);
        }


        comboFormClickType.add(CommonComboClickType.SUB_NUMBER_CLICK_TYPE);                  //amount2
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_17);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_18);


        comboFormClickType.add(CommonComboClickType.SELECT_CURRENCY_UNIT_PRICE_2_CLICK_TYPE);//currency unit price 2
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_19);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_20);


        comboFormClickType.add(CommonComboClickType.SELECT_DISCOUNT_PERCENTAGE_CLICK_TYPE);  //discount percentage
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_21);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_22);

        comboFormClickType.add(CommonComboClickType.SELECT_TAX_PERCENTAGE_CLICK_TYPE);        //tax percentage
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_23);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_24);

        comboFormClickType.add(CommonComboClickType.SELECT_DISCOUNT_2_CLICK_TYPE);            //discount 2

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_25);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_26);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_27);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_28);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_15);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_16);


        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_14);

        if (checkHaveConfig(8)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_10);
        }
        if (checkHaveConfig(9)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_11);
        }
        if (checkHaveConfig(10)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_12);
        }
        if (checkHaveConfig(15)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_13);
        }


        return comboFormClickType;
    }

    private List<ComboFormModel> setProperDataToDeliveryProceedings() {
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();
         /*
         user can not receive to here in buyer req
         */
        return comboFormModels;
    }

    private List<ComboFormModel> setProperDataToDeliveryProceedingsEditMode() {

        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_name), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_name), purchasableGoodsTableEditMode.getGoodName()), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_sub_unit_name), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_sub_unit_name), purchasableGoodsTableEditMode.getUnitName2()), Commons.NULL, false));
        if (showGoodUnit) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.length), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.length), goodTransDetailsModel.getLength()), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.width), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.width), goodTransDetailsModel.getWidth()), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.height), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.height), goodTransDetailsModel.getHeight()), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.alloy), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.alloy), goodTransDetailsModel.getAlloy()), Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.sub_number), goodTransDetailsModel.getAmount2(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.amount), context.getString(R.string.amount), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(amount, amount, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.sub_unit_price), goodTransDetailsModel.getCurrencyUnitPrice2(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.currency_unit_price_1), context.getString(R.string.currency_unit_price_1), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(currencyUnitPrice1, currencyUnitPrice1, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.discount_percentage), goodTransDetailsModel.getDiscountPercentage(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.discount_currency), context.getString(R.string.discount_currency), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(currencyDiscountTotal, currencyDiscountTotal, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.tax_percentag), goodTransDetailsModel.getTaxPercentage(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.tax_curreny), context.getString(R.string.tax_curreny), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(currencyTaxValue, currencyTaxValue, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.discount_2), goodTransDetailsModel.getCurrencyDiscount2(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.currency_total_value), context.getString(R.string.currency_total_value), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(currencyTotalValue, currencyTotalValue, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.net_price), context.getString(R.string.net_price), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(currencyNetValue, currencyNetValue, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.net_price_sub), context.getString(R.string.net_price_sub), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(currencyNetUnitPrice2, currencyNetUnitPrice2, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.variety), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.variety), String.valueOf(goodTransDetailsModel.getGoodColumnNumber())), Commons.NULL, false));

        if (checkHaveConfig(8)) {
            if (myListBaseIdList.get(7).contains(B5IdRefId8)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.center_cost), myListBaseNameList.get(7).get(myListBaseIdList.get(7).indexOf(B5IdRefId8) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(7).indexOf(B5IdRefId8))), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.center_cost), Commons.NULL), Commons.NULL, false));
            }
        }
        if (checkHaveConfig(9)) {
            if (myListBaseIdList.get(8).contains(B5IdRefId9)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.production_process), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.production_process), myListBaseNameList.get(8).get(myListBaseIdList.get(8).indexOf(B5IdRefId9) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(8).indexOf(B5IdRefId9))), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.production_process), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.production_process), Commons.NULL), Commons.NULL, false));
            }
        }
        if (checkHaveConfig(10)) {
            if (myListBaseIdList.get(9).contains(B5IdRefId10)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.consumable), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.consumable), myListBaseNameList.get(9).get(myListBaseIdList.get(9).indexOf(B5IdRefId10) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(9).indexOf(B5IdRefId10))), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.consumable), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.consumable), Commons.NULL), Commons.NULL, false));
            }
        }
        if (checkHaveConfig(15)) {
            if (myListBaseIdList.get(14).contains(B5IdRefId15)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.warehouse), myListBaseNameList.get(14).get(myListBaseIdList.get(14).indexOf(B5IdRefId15) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(14).indexOf(B5IdRefId15))), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.warehouse), Commons.NULL), Commons.NULL, false));
            }
        }

        return comboFormModels;
    }

    //endregion
//----------------------------------------------------------------------------------------------
    //region NonEditable

    private void setupCustomLayoutManagerNonEditable() {
        ArrayList<Integer> listOfQuarterPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfThirdPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfHalfPosition = new ArrayList<>();//empty

        if (checkNull()) {
            getView().setupStairCustomLayoutManager(listOfQuarterPosition, listOfThirdPosition, listOfHalfPosition);
        }
    }

    private void showFormNonEditable() {
        //no need to any work , user can not received here
    }

    private void showFormNonEditableEditMode() {
        comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToNonEditable(), setProperClickRowTypeToNonEditable(), setProperDataToNonEditableEditMode());
        getView().showHideProgress(false);
        getView().showHideConslayGoodForGoodTrance(true);

    }

    private List<Integer> setProperRowTypeToNonEditable() {
        ArrayList<Integer> comboFormType = new ArrayList<>();


        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//select goods
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//select C5UnitId
        if (showGoodUnit) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//length
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//width
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//height
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//alloy
        }

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//amount2
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//currency unit price 2
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//discount percentage
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//tax percentage
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//discount 2


        if (checkHaveConfig(8)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//b5IdRefId8
        }
        if (checkHaveConfig(9)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//b5IdRefId9
        }
        if (checkHaveConfig(10)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//b5IdRefId10
        }
        if (checkHaveConfig(15)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//b5IdRefId15
        }

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//goodColumnNumber


        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToNonEditable() {

        ArrayList<Integer> comboFormClickType = new ArrayList<>();

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_2);
        if (showGoodUnit) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_5);
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_6);
        }

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_15);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_16);

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_7);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_8);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_9);


        if (checkHaveConfig(8)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_10);
        }
        if (checkHaveConfig(9)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_11);
        }
        if (checkHaveConfig(10)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_12);
        }
        if (checkHaveConfig(15)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_13);
        }

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_14);

        return comboFormClickType;
    }

    private List<ComboFormModel> setProperDataToNonEditable() {
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();
         /*
         user can not receive to here in buyer req
         */
        return comboFormModels;
    }

    private List<ComboFormModel> setProperDataToNonEditableEditMode() {

        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_name), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_name), salableGoodsTableEditMode.getGoodName()), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_sub_unit_name), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_sub_unit_name), salableGoodsTableEditMode.getUnitName2()), Commons.NULL, false));
        if (showGoodUnit) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.length), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.length), goodTransDetailsModel.getLength()), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.width), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.width), goodTransDetailsModel.getWidth()), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.height), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.height), goodTransDetailsModel.getHeight()), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.alloy), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.alloy), goodTransDetailsModel.getAlloy()), Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.sub_number), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.sub_number), goodTransDetailsModel.getAmount2()), Commons.NULL, false));

        comboFormModels.add(new ComboFormModel(context.getString(R.string.sub_unit_price), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.sub_unit_price), goodTransDetailsModel.getCurrencyUnitPrice2()), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.discount_percentage), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.discount_percentage), goodTransDetailsModel.getDiscountPercentage()), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.tax_percentag), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.tax_percentag), goodTransDetailsModel.getTaxPercentage()), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.discount_2), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.discount_2), goodTransDetailsModel.getCurrencyDiscount2()), Commons.NULL, false));


        if (checkHaveConfig(8)) {
            if (myListBaseIdList.get(7).contains(B5IdRefId8)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.center_cost), myListBaseNameList.get(7).get(myListBaseIdList.get(7).indexOf(B5IdRefId8) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(7).indexOf(B5IdRefId8))), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.center_cost), Commons.NULL), Commons.NULL, false));
            }
        }
        if (checkHaveConfig(9)) {
            if (myListBaseIdList.get(8).contains(B5IdRefId9)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.production_process), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.production_process), myListBaseNameList.get(8).get(myListBaseIdList.get(8).indexOf(B5IdRefId9) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(8).indexOf(B5IdRefId9))), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.production_process), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.production_process), Commons.NULL), Commons.NULL, false));
            }
        }
        if (checkHaveConfig(10)) {
            if (myListBaseIdList.get(9).contains(B5IdRefId10)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.consumable), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.consumable), myListBaseNameList.get(9).get(myListBaseIdList.get(9).indexOf(B5IdRefId10) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(9).indexOf(B5IdRefId10))), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.consumable), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.consumable), Commons.NULL), Commons.NULL, false));
            }
        }
        if (checkHaveConfig(15)) {
            if (myListBaseIdList.get(14).contains(B5IdRefId15)) {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.warehouse), myListBaseNameList.get(14).get(myListBaseIdList.get(14).indexOf(B5IdRefId15) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(14).indexOf(B5IdRefId15))), Commons.NULL, false));
            } else {
                comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.warehouse), Commons.NULL), Commons.NULL, false));
            }
        }


        comboFormModels.add(new ComboFormModel(context.getString(R.string.variety), String.format(CommonsFormat.FORMAT_3, context.getString(R.string.variety), String.valueOf(goodTransDetailsModel.getGoodColumnNumber())), Commons.NULL, false));

        return comboFormModels;
    }

    //endregion

}
