package ir.fararayaneh.erp.activities.good_trance;


import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.annimon.stream.Stream;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.realm.RealmModel;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.IBase.common_base.form_base.BasePresenterCollectionDataForm;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.adaptors.good_detail_adaptor.GoodDetailsRecAdaptor;
import ir.fararayaneh.erp.adaptors.good_detail_adaptor.IGoodsDetailRecAdaptorListener;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonComboClickType;
import ir.fararayaneh.erp.commons.CommonComboFormType;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.CommonTimeZone;
import ir.fararayaneh.erp.commons.CommonUtilCode;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsFormCode;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.GetCustomerGroupNameFromIdQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetDefaultCostumerGroupQuery;
import ir.fararayaneh.erp.data.data_providers.queries.address_book.GetAddressNameFromIdQuery;
import ir.fararayaneh.erp.data.data_providers.queries.address_book.GetDefaultAddressQuery;
import ir.fararayaneh.erp.data.data_providers.queries.good_trance.InsertUpdateGoodTransTableChangePurchasableAmountQuery;
import ir.fararayaneh.erp.data.data_providers.queries.good_trance.InsertUpdateGoodTransTableChangeSalableAmountQuery;
import ir.fararayaneh.erp.data.data_providers.queries.good_trance.InsertUpdateGoodTransTableQuery;
import ir.fararayaneh.erp.data.data_providers.queries.good_trance.InsertUpdateGoodTransTableRefineAndChangePurchasableAmountQuery;
import ir.fararayaneh.erp.data.data_providers.queries.good_trance.InsertUpdateGoodTransTableRefineAndChangeSalableAmountQuery;
import ir.fararayaneh.erp.data.data_providers.queries.goods.GetGoodsListByIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.purchasable_goods.SetAllRealPurchasableToTempAmount;
import ir.fararayaneh.erp.data.data_providers.queries.salable_goods.SetAllRealSalableToTempAmount;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.online.request.socket.GoodTrancSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.middle.ComboFormModel;
import ir.fararayaneh.erp.data.models.middle.GoodTransDetailsModel;
import ir.fararayaneh.erp.data.models.middle.GoodsDetailModel;
import ir.fararayaneh.erp.data.models.tables.BaseCodingTable;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.data.models.tables.GroupRelatedTable;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AddressBookTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.GoodTranceTable;
import ir.fararayaneh.erp.utils.CalculationHelper;
import ir.fararayaneh.erp.utils.EditTextCheckHelper;
import ir.fararayaneh.erp.utils.data_handler.GetOneRowByGUIDHandler;
import ir.fararayaneh.erp.utils.data_handler.GoodTransDetailJsonHelper;
import ir.fararayaneh.erp.utils.data_handler.IGetRowByGUIDQueryListener;
import ir.fararayaneh.erp.utils.data_handler.IRefineTempAmountListener;
import ir.fararayaneh.erp.utils.data_handler.RefinePurchasableSalableTempAmountHandler;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntentFactoryHandler;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.intent_handler.IntentReceiverHandler;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.search_spinner_dialog.SearchAbleSpinnerDialogHandler;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;
import ir.fararayaneh.erp.utils.time_helper.CustomDatePicker;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;
import ir.fararayaneh.erp.utils.time_helper.ISelectDateListener;

import static ir.fararayaneh.erp.commons.CommonsFormCode.BUYER_REQUEST_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.BUY_SERVICES_INVOICE_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.PERMANENT_RECEIPT_GOODS_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.PRODUCTION_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.RECEIPT_GOODS_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.WAREHOUSE_TRANSFERENCE_FORM_CODE;

/**
 * این اکتیویتی از جنس for result است
 * و در صورتی که جواب آن اوکی بود لیستی که از ان به اینجا آمده ایم رفرش میشود
 * در واقع چیزی به مبدا برگردانده نمیشود و فقط تایید صحت عملیات برگردانده میشود
 * ---------------------------------------
 * این اکتیویتی برای نمایش فرم کد های زیر به کار میرود :
 * todo
 * REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE ="276" ;//در خواست کالا از انبار -امکان اضافه کردن تکی(از جدول گودز) و گروهی(از جدول گودز) که اضافه کردن گروهی از روی جدول کالا است
 * PERMANENT_RECEIPT_GOODS_FORM_CODE ="275" ;//رسید موقت کالا -فقط مشاهده مستر دیتیل بدون هیچ گونه امکان کلیک
 * WAREHOUSE_TRANSFERENCE_FORM_CODE ="278" ;//حواله انبار-فقط مشاهده مستر دیتیل بدون هیچ گونه امکان کلیک
 * RECEIPT_GOODS_FORM_CODE ="224" ;//رسید کالا-فقط مشاهده مستر دیتیل بدون هیچ گونه امکان کلیک
 * BUY_SERVICES_INVOICE_FORM_CODE ="153" ;//فاکتور خرید خدمات-فقط مشاهده مستر دیتیل بدون هیچ گونه امکان کلیک
 * BUYER_REQUEST_FORM_CODE ="284" ;//سفارش خریدارامکان اضافه کردن گروهی که گروهی از جدول کالای قابل سفارش است و اضافه کردن تکی فعلا نداریم--امکان اضافه کردن تکی و نیز ویرایش دیتیل وجود ندارد
 * GOODS_DELIVERY_PROCEEDINGS_FORM_CODE ="274" ;//صورت مجلس تحویل کالا - امکان اضافه کردن گروهی از روی جدول کالای قابل خرید--امکان اضافه کردن تکی و نیز ویرایش دیتیل وجود ندارد
 * PRODUCTION_FORM_CODE ="62" ;//تولید محصولات --دقیقا شبیه سفارش کالا از انبار
 * ---------------------------------------
 * هر کدام از LOV
 * ممکن است روشن و یا خاموش باشد و نیز ممکن است قابل کلیک شدن باشد یا فقط مقدار پیشفرض را نشان بدهد
 * که بسته به نوع فرم کد متغیر است
 * ---------------------------------------
 * 10-06-1398 by eng-Rezaii
 * در این اکتیویتی و نیز اضافه کردن تکی و گروهی نیازی به گوش کردن به سوکت نیست
 * هر جند در اضافه کردن گروهی در واقع اگر سطر جدیدی
 * در واقع داریم به سطرهای جدید گوش میدهیم
 * در واقع هدف این است که تغییرات ناشی از جداول قابل خرید یا قابل فروش یا فاکتور را را در زمان کار با فرم بینیم
 * ---------------------------------------
 */

public class GoodTrancePresenter extends BasePresenterCollectionDataForm<GoodTranceView> {

    private static final int MAXIMUM_DURATION_LIMIT = 364;
    private GoodDetailsRecAdaptor goodDetailsRecAdaptor = new GoodDetailsRecAdaptor(context);
    private IGoodsDetailRecAdaptorListener iGoodsDetailRecAdaptorListener;
    private IGoodTranceListener iGoodTranceListener;
    private ISelectDateListener iSelectDateListener;
    private IRefineTempAmountListener iRefineTempAmountListener;

    private String primitiveAddressName = Commons.NULL, primitiveCostumerGroupName = Commons.NULL;//only for use in first step of edit mode,


    //value for save in table ()--------------------------->>>
    private String B5IdRefId1, B5IdRefId2, B5IdRefId3, B5IdRefId4, B5IdRefId5, B5IdRefId6,
            B5IdRefId14, B5HCCurrencyId, B5HCSellMethodId, B5HCAccountSideId, B5IdRefIdRecall, B5HCStatusId,
            deliveryName, formNumber, formDate, expireDate, description,
            goodTransDetail, customerGroupId, addressId;
    private int duration;
    //value for save in table--------------------------->>>
    private ArrayList<GoodTransDetailsModel> goodTransDetailsModelArrayList = new ArrayList<>();


    @Override
    public void click() {
        super.click();

        iGoodsDetailRecAdaptorListener = new IGoodsDetailRecAdaptorListener() {
            @Override
            public void clickDeleteRow(int position) {
                deleteDetailRow(position);
            }

            @Override
            public void clickEditRow(int position) {
                goSelectOneGoods(true, position);
            }
        };

        if (goodDetailsRecAdaptor != null) {
            goodDetailsRecAdaptor.setiGoodsDetailRecAdaptorListener(iGoodsDetailRecAdaptorListener);
        }

        iGoodTranceListener = new IGoodTranceListener() {
            @Override
            public void dataFromSelectionAct(Intent data) {
                prepareNewDetailDataFromSelectionAct(IntentReceiverHandler.
                        getIntentData(Objects.requireNonNull(data.getExtras())).getSomeString());
            }

            @Override
            public void dataFromGoodForGoodTransAct(Intent data) {
                prepareNewDetailDataFromGoodsForGoodTransAct(IntentReceiverHandler.
                        getIntentData(Objects.requireNonNull(data.getExtras())).getGoodTransDetailsModelList().get(0));
            }
        };

        if (checkNull()) {
            getView().setiGoodTranceListener(iGoodTranceListener);
        }

    }

    //todo add other form code
    private void deleteDetailRow(int position) {
        switch (myFormCode) {
            case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
            case PRODUCTION_FORM_CODE:
                deleteOneDetail(position);
                break;
            case BUYER_REQUEST_FORM_CODE:
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                setupSalablePurchasableTempAmountBeforeDelete(position);
                break;
            case PERMANENT_RECEIPT_GOODS_FORM_CODE:
            case WAREHOUSE_TRANSFERENCE_FORM_CODE:
            case RECEIPT_GOODS_FORM_CODE:
            case BUY_SERVICES_INVOICE_FORM_CODE:
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageNoEditPermission();
                }
                break;
        }
    }

    /*
    تغییر مقادیر موقت(Temp) در زمان کلیک دیلیت روی هر کدام از دیتیل ها
     */
    private void setupSalablePurchasableTempAmountBeforeDelete(int position) {
        iRefineTempAmountListener = new IRefineTempAmountListener() {
            @Override
            public void done() {
                deleteOneDetail(position);
            }

            @Override
            public void error() {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_107);
                }
            }
        };
        //todo add other
        switch (myFormCode) {
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                addDisposable(RefinePurchasableSalableTempAmountHandler.doRefinePurchasable(getProperModelForChangeTempValue(position, false), iRefineTempAmountListener));
                break;
            case BUYER_REQUEST_FORM_CODE:
                addDisposable(RefinePurchasableSalableTempAmountHandler.doRefineSalable(getProperModelForChangeTempValue(position, false), iRefineTempAmountListener));
                break;
        }
    }

    private GlobalModel getProperModelForChangeTempValue(int position, boolean forAllRow) {
        ArrayList<String> idList = new ArrayList<>();
        ArrayList<String> tempAmountList = new ArrayList<>();

        if (forAllRow) {

            for (int i = 0; i < goodTransDetailsModelArrayList.size(); i++) {
                idList.add(goodTransDetailsModelArrayList.get(i).getIdRecallDetail());
                tempAmountList.add(goodTransDetailsModelArrayList.get(i).getAmount2());
            }
        } else {
            idList.add(goodTransDetailsModelArrayList.get(position).getIdRecallDetail());
            tempAmountList.add(goodTransDetailsModelArrayList.get(position).getAmount2());
        }

        GlobalModel globalModel = new GlobalModel();
        globalModel.setStringArrayList(idList);
        globalModel.setStringArrayList2(tempAmountList);
        return globalModel;
    }

    private void deleteOneDetail(int position) {
        if (position < goodTransDetailsModelArrayList.size() && goodDetailsRecAdaptor != null) {
            goodDetailsRecAdaptor.removeFromList(position);
            goodTransDetailsModelArrayList.remove(position);
        }
    }

    /**
     * @param goodTranceDetailJson we sure we have at list one row,
     *                             on the other hand,
     *                             we received cancelResult intent.
     *                             <p>
     *                             here we can not received duplicated guid,
     *                             because we do not go to selection activity for edit
     */
    private void prepareNewDetailDataFromSelectionAct(String goodTranceDetailJson) {
        goodTransDetailsModelArrayList.addAll(GoodTransDetailJsonHelper.getGoodTransDetailArray(goodTranceDetailJson));
        addDisposable(showTableInDetailsRec());
    }

    private void prepareNewDetailDataFromGoodsForGoodTransAct(GoodTransDetailsModel goodTransDetailsModel) {
        String newGUID = goodTransDetailsModel.getGuid();
        for (int i = 0; i < goodTransDetailsModelArrayList.size(); i++) {
            if (goodTransDetailsModelArrayList.get(i).getGuid().equals(newGUID)) {
                goodTransDetailsModelArrayList.remove(i);
                break;//only one row can have duplicated guid
            }
        }
        goodTransDetailsModelArrayList.add(goodTransDetailsModel);
        addDisposable(showTableInDetailsRec());
    }

    public GoodTrancePresenter(WeakReference<GoodTranceView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    /**
     * only for set values that no need to database because get from db call after this method
     */
    @Override
    protected void setDefaultValue() {
        /*
         * guid;
         * id,
         * B5FormRefId,(به عنوان myformId)
         * syncState,
         * activityState,
         * oldValue,
         *
         * are in BasePresenterCollectionDataForm
         *
         *  duration is 0 by default
         */

        B5IdRefId1 = SharedPreferenceProvider.getSellEmporiumId(context);
        B5IdRefId2 = Commons.NULL_INTEGER;
        B5IdRefId3 = Commons.NULL_INTEGER;
        B5IdRefId4 = Commons.NULL_INTEGER;//اگر کانفیگ 4 مقدار داشت این فیلد مقدار یوزر آی دی را میگیرد
        B5IdRefId5 = Commons.NULL_INTEGER;
        B5IdRefId6 = Commons.NULL_INTEGER;
        B5IdRefId14 = Commons.NULL_INTEGER;
        B5HCCurrencyId = SharedPreferenceProvider.getMonetaryUnitId(context);
        B5HCSellMethodId = Commons.NULL_INTEGER;
        B5HCAccountSideId = Commons.NULL_INTEGER;
        B5IdRefIdRecall = Commons.NULL_INTEGER;
        B5HCStatusId = SharedPreferenceProvider.getStatusIdDefualt(context);
        deliveryName = Commons.NULL;
        formNumber = Commons.NULL_INTEGER;

        formDate = CustomTimeHelper.longLocalDateToOtherLocalConverter(CommonTimeZone.SERVER_TIME_ZONE, CustomTimeHelper.getCurrentDate().getTime(), CommonsFormat.DATE_SHARE_FORMAT_FROM_STRING);
        expireDate = Commons.MINIMUM_TIME;//set from time picker in some form code

        description = Commons.NULL;
        goodTransDetail = Commons.NULL_ARRAY;
        customerGroupId = Commons.NULL_INTEGER;
        addressId = Commons.NULL_INTEGER;
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
        if (iSocketModel instanceof GoodTrancSocketModel) {
            GoodTrancSocketModel goodTrancSocketModel = (GoodTrancSocketModel) iSocketModel;
            //if we are in edit mode and my id is equal to received in service socket :
            //چون آی دی دارای مقدار است بنابر این بقیه مقادیر هم مقدار دارد و چیزی نال نیست
            if (id.equals(goodTrancSocketModel.getBody().getId()) && checkNull()) {
                getView().showHideProgress(true);
                getView().showMessageGoodTrancIsChanged();
                witchWorkForShowForm();
            }
        }

    }

    @Override
    protected void ClearAllPresenterListener() {
        super.ClearAllPresenterListener();
        goodDetailsRecAdaptor = null;
        iGoodsDetailRecAdaptorListener = null;
        iGoodTranceListener = null;
        iSelectDateListener = null;
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
                setupCustomLayoutManagerProductionFormCode();
                break;
            case PERMANENT_RECEIPT_GOODS_FORM_CODE:
            case RECEIPT_GOODS_FORM_CODE:
            case BUY_SERVICES_INVOICE_FORM_CODE:
                setupCustomLayoutManagerPermanentReceiptGoods();
                break;
            case WAREHOUSE_TRANSFERENCE_FORM_CODE:
                setupCustomLayoutManagerWarehouseTransference();
                break;
            case BUYER_REQUEST_FORM_CODE:
                setupCustomLayoutManagerBuyerReq();
                break;
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                setupCustomLayoutManagerGoodsDeliveryProceedings();
                break;
        }
    }

    //todo تکمیل باقی مانده فرم کد ها
    @Override
    protected void witchWorkForShowForm() {
        setDetailsRecAdaptor();
        switch (myFormCode) {
            case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                showFormReqGoodFromWareHouse();
                break;
            case PRODUCTION_FORM_CODE:
                showFormProductionFormCode();
                break;
            case PERMANENT_RECEIPT_GOODS_FORM_CODE:
            case RECEIPT_GOODS_FORM_CODE:
            case BUY_SERVICES_INVOICE_FORM_CODE:
                showFormPermanentReceiptGoods();
                break;
            case WAREHOUSE_TRANSFERENCE_FORM_CODE:
                showFormWarehouseTransference();
                break;
            case BUYER_REQUEST_FORM_CODE:
                addDisposable(showFormBuyerReq());
                break;
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                addDisposable(showFormGoodsDeliveryProceedings());
                break;
        }

    }

    private void setDetailsRecAdaptor() {
        if (checkNull() && goodDetailsRecAdaptor != null) {
            getView().setRecycleDetailAdaptor(goodDetailsRecAdaptor);
        }
    }

    //todo تکمیل برای باقی مانده   کلیک تایپ ها
    @Override
    protected void clickRowRecycleFactoryMethod(int comboFormClickType, String someValue) {
        switch (comboFormClickType) {
            case CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE:
                goToMultipleSelect();
                break;
            case CommonComboClickType.ADD_NEW_CLICK_TYPE:
                goSelectOneGoods(false, -1);
                break;
            case CommonComboClickType.END_DATE_CLICK_TYPE:
                setExpireDateClickType();
                break;
            case CommonComboClickType.SELL_EMPORIUM_CLICK_TYPE:
                showSearchAbleSpinnerOfFormRefConfig(1, CommonComboClickType.SELL_EMPORIUM_CLICK_TYPE);
                break;
            case CommonComboClickType.WAREHOUSE_NUMBER_CLICK_TYPE:
                showSearchAbleSpinnerOfFormRefConfig(2, CommonComboClickType.WAREHOUSE_NUMBER_CLICK_TYPE);
                break;
            case CommonComboClickType.SELECT_BUYER_CLICK_TYPE:
                showSearchAbleSpinnerOfFormRefConfig(3, CommonComboClickType.SELECT_BUYER_CLICK_TYPE);
                break;
            case CommonComboClickType.DURATION_CLICK_TYPE:
                showSearchAbleSpinner(makeProperArrayListForDuration(), CommonComboClickType.DURATION_CLICK_TYPE);
                break;
            case CommonComboClickType.CENTER_COST_CLICK_TYPE:
                showSearchAbleSpinnerOfFormRefConfig(6, CommonComboClickType.CENTER_COST_CLICK_TYPE);
                break;
            case CommonComboClickType.CURRENCY_UNIT_CLICK_TYPE:
                showSearchAbleSpinnerOfUtilCodeConfig(CommonUtilCode.CUR, CommonComboClickType.CURRENCY_UNIT_CLICK_TYPE);
                break;
            case CommonComboClickType.SELL_METHOD_CLICK_TYPE:
                showSearchAbleSpinnerOfUtilCodeConfig(CommonUtilCode.SEM, CommonComboClickType.SELL_METHOD_CLICK_TYPE);
                break;
            case CommonComboClickType.ACCOUNT_SIDE_CLICK_TYPE:
                showSearchAbleSpinnerOfUtilCodeConfig(CommonUtilCode.ACS, CommonComboClickType.ACCOUNT_SIDE_CLICK_TYPE);
                break;
            case CommonComboClickType.SELECT_CUSTOMER_CLICK_TYPE:
                showCostumerListByConfig3();
                break;
            case CommonComboClickType.SELECT_ADDRESS_CLICK_TYPE:
                showAddressListByConfig3();
                break;
            case CommonComboClickType.DESCRIPTION_CLICK_TYPE:
                description = someValue;
                //no need to update,  dataList were updated in holder
                break;
        }
    }

    private void showCostumerListByConfig3() {
        //we can not come here with out have config 3, but ...
        if (checkHaveConfig(3)) {
            if (B5IdRefId3.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_BUYER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.buyer_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    getView().showHideProgress(false);
                    getView().showMessageFillProperValue();
                }
            } else {
                isearchDialogeListener = (realmModel, item, position) -> setDataFromSearchAbleSpinner(realmModel, item, position, CommonComboClickType.SELECT_CUSTOMER_CLICK_TYPE);
                SearchAbleSpinnerDialogHandler.showCustomerGroupNameDBDialog(getView().getActivity(), B5IdRefId3, isearchDialogeListener);
            }
        }
    }

    private void showAddressListByConfig3() {
        //we can not come here with out have config 3, but ...
        if (checkHaveConfig(3)) {
            if (B5IdRefId3.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_BUYER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.buyer_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                }
            } else {
                isearchDialogeListener = (realmModel, item, position) -> setDataFromSearchAbleSpinner(realmModel, item, position, CommonComboClickType.SELECT_ADDRESS_CLICK_TYPE);
                SearchAbleSpinnerDialogHandler.showAddressNameDBDialog(getView().getActivity(), B5IdRefId3, isearchDialogeListener);
            }
        }
    }

    private void setExpireDateClickType() {

        iSelectDateListener = new ISelectDateListener() {
            @Override
            public void onDateSelection(Date date) {

                if (CustomTimeHelper.isDateBetweenFinancialDate(context, date.getTime())) {
                    if (CustomTimeHelper.getDiffDate(date, CustomTimeHelper.getCurrentDate()) < 0) {
                        expireDate = CustomTimeHelper.longLocalDateToOtherLocalConverter(CommonTimeZone.SERVER_TIME_ZONE, date.getTime(), CommonsFormat.DATE_SHARE_FORMAT_FROM_STRING);
                        String mExpireDate = CustomTimeHelper.checkMyTimeNotNull(CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(expireDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context)) ? CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(expireDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context) : Commons.DASH;
                        comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.expire_date), mExpireDate), Commons.NULL, false), true);
                    } else {
                        if (checkNull()) {
                            comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.date_before_now_error), false), true);
                            getView().showHideProgress(false);
                            getView().showMessageDateBeforeNowError();
                        }
                    }
                } else {
                    if (checkNull()) {
                        comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.date_out_financial_date), false), true);
                        getView().showHideProgress(false);
                        getView().showMessageDateOutFinancialDate();
                    }
                }
            }

            @Override
            public void onDateNotSelection() {
                //do nothings
            }
        };
        CustomDatePicker.showProperDateTimePicker(getView().getActivity(), iSelectDateListener);

    }


    //todo    دیگر تکمیل باقی مانده کلیک های کمبوباکس
    @Override
    protected void setDataFromSearchAbleSpinner(@Nullable RealmModel realmModel, @Nullable String item, int position, int commonComboClickType) {
        if (checkNull() && item != null) {
            //we sure item not null
            comboFormRecAdaptor.updateRowValue(commonComboClickType, new ComboFormModel(Commons.NULL, item, Commons.NULL, false), true);
            switch (commonComboClickType) {
                case CommonComboClickType.SELL_EMPORIUM_CLICK_TYPE:
                    if (realmModel != null) {
                        BaseCodingTable baseCodingTable = (BaseCodingTable) realmModel;
                        B5IdRefId1 = baseCodingTable.getId();
                    }
                    break;
                case CommonComboClickType.WAREHOUSE_NUMBER_CLICK_TYPE:
                    if (realmModel != null) {
                        BaseCodingTable baseCodingTable = (BaseCodingTable) realmModel;
                        B5IdRefId2 = baseCodingTable.getId();
                    }
                    break;
                case CommonComboClickType.SELECT_BUYER_CLICK_TYPE:
                    if (realmModel != null) {
                        BaseCodingTable baseCodingTable = (BaseCodingTable) realmModel;
                        B5IdRefId3 = baseCodingTable.getId();
                        addDisposable(defaultCustomerAfterSelectBuyer());
                    }
                    break;
                case CommonComboClickType.DURATION_CLICK_TYPE:
                    duration = position;
                    break;
                case CommonComboClickType.CENTER_COST_CLICK_TYPE:
                    if (realmModel != null) {
                        BaseCodingTable baseCodingTable = (BaseCodingTable) realmModel;
                        B5IdRefId6 = baseCodingTable.getId();
                    }
                    break;

                case CommonComboClickType.CURRENCY_UNIT_CLICK_TYPE:
                    if (realmModel != null) {
                        UtilCodeTable utilCodeTable = (UtilCodeTable) realmModel;
                        B5HCCurrencyId = utilCodeTable.getId();
                    }
                    break;
                case CommonComboClickType.SELL_METHOD_CLICK_TYPE:
                    if (realmModel != null) {
                        UtilCodeTable utilCodeTable = (UtilCodeTable) realmModel;
                        B5HCSellMethodId = utilCodeTable.getId();
                    }
                    break;
                case CommonComboClickType.ACCOUNT_SIDE_CLICK_TYPE:
                    if (realmModel != null) {
                        UtilCodeTable utilCodeTable = (UtilCodeTable) realmModel;
                        B5HCAccountSideId = utilCodeTable.getId();
                    }
                    break;
                case CommonComboClickType.SELECT_CUSTOMER_CLICK_TYPE:
                    if (realmModel != null) {
                        GroupRelatedTable groupRelatedTable = (GroupRelatedTable) realmModel;
                        customerGroupId = groupRelatedTable.getGroupId();
                    }
                    break;
                case CommonComboClickType.SELECT_ADDRESS_CLICK_TYPE:
                    if (realmModel != null) {
                        AddressBookTable addressBookTable = (AddressBookTable) realmModel;
                        addressId = addressBookTable.getId();
                    }
                    break;
            }
        }
    }

    /*
    after that user select buyer, should be set default value for costumer group and address
    if have not values,
    here we sure we have B5IdRefId3
    attention : we have an other query for show address and costumer group
                  for show in spinner,and have an other query for get values from id
    */
    private Disposable defaultCustomerAfterSelectBuyer() {
        if (checkNull()) {
            getView().showHideProgress(true);
        }
        // if (customerGroupId.equals(Commons.NULL_INTEGER)) {
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(B5IdRefId3);
        GetDefaultCostumerGroupQuery getDefaultCostumerGroupQuery = (GetDefaultCostumerGroupQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_DEFAULT_COSTUMER_GROUP_QUERY);
        assert getDefaultCostumerGroupQuery != null;
        return getDefaultCostumerGroupQuery.data(globalModel)
                .subscribe(iModels -> {
                    customerGroupId = globalModel.getMyString3();
                    if (checkNull()) {
                        comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_CUSTOMER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.customer_group), globalModel.getMyString2(), Commons.NULL, false), true);
                    }
                    addDisposable(defaultAddressAfterSelectBuyer());
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_18);
                    }
                });
        // } else {
        //   addDisposable(defaultAddressAfterSelectBuyer());
        //  return null;
        // }

    }


    /*
    after that user select buyer, should be set default value for costumer group and address
    if have not values,
    here we sure we have B5IdRefId3
    attention : we have an other query for show address and costumer group
                  for show in spinner,and have an other query for get values from id
    */
    private Disposable defaultAddressAfterSelectBuyer() {
        //  if (addressId.equals(Commons.NULL_INTEGER)) {
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(B5IdRefId3);
        GetDefaultAddressQuery getDefaultAddressQuery = (GetDefaultAddressQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_DEFAULT_ADDRESS_QUERY);
        assert getDefaultAddressQuery != null;
        return getDefaultAddressQuery.data(globalModel)
                .subscribe(iModels -> {
                    addressId = globalModel.getMyString3();
                    if (checkNull()) {
                        comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_ADDRESS_CLICK_TYPE, new ComboFormModel(context.getString(R.string.address), globalModel.getMyString2(), Commons.NULL, false), true);
                        getView().showHideProgress(false);
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_93);
                    }
                });
        // } else {
        //   if (checkNull()) {
        //      getView().showHideProgress(false);
        //  }
        //   return null;
        //}
    }

    //todo تکمیل باقی مانده فرم کد ها
    @Override
    protected void checkData() {
        switch (myFormCode) {
            case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                checkDataReqGoodFromWareHouse();
                break;
            case PRODUCTION_FORM_CODE:
                checkDataProductionFormCode();
                break;
            case PERMANENT_RECEIPT_GOODS_FORM_CODE:
            case RECEIPT_GOODS_FORM_CODE:
            case BUY_SERVICES_INVOICE_FORM_CODE:
                checkDataPermanentReceiptGoods();
                break;
            case WAREHOUSE_TRANSFERENCE_FORM_CODE:
                checkDataWarehouseTransference();
                break;
            case BUYER_REQUEST_FORM_CODE:
                checkDataBuyerReq();
                break;
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                checkDataGoodsDeliveryProceedings();
                break;

        }
    }

    @Override
    protected void workNextClick() {
        //we have not next click here
    }

    /**
     * if primitiveGUID = null, this method never call
     */
    //todo other form code
    @Override
    protected void workDeleteClick() {
        activityState = CommonActivityState.DELETE;
        switch (myFormCode) {
            case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
            case PRODUCTION_FORM_CODE:
                addDisposable(saveDataGoodTrance());
                break;
            case BUYER_REQUEST_FORM_CODE:
                addDisposable(saveDataGoodTransAndChangeRefineSalableTempAmount());
                break;
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                addDisposable(saveDataGoodTransAndChangeRefinePurchasableTempAmount());
                break;
            case PERMANENT_RECEIPT_GOODS_FORM_CODE:
            case WAREHOUSE_TRANSFERENCE_FORM_CODE:
            case RECEIPT_GOODS_FORM_CODE:
            case BUY_SERVICES_INVOICE_FORM_CODE:
                //user can not see this btn ...but...
                if (checkNull()) {
                    getView().showMessageNoEditPermission();
                }
                break;
        }

    }

    private void getGoodTranceByGuid() {
        iGetRowByGUIDQueryListener = new IGetRowByGUIDQueryListener() {
            @Override
            public void onGetRow(IModels iModels) {
                /*
                 * formId were be valued in base (but here be valued again)
                 */
                GoodTranceTable goodTranceTable = (GoodTranceTable) iModels;
                guid = goodTranceTable.getGuid();//needed because in base , set defualt value and in save we use guid instead of primitiveGUID
                id = goodTranceTable.getId();
                myFormId = goodTranceTable.getB5FormRefId();//no need but ...(we come here with correct formId and formCode)(here use as B5FormRefId)
                B5IdRefId1 = goodTranceTable.getB5IdRefId1();
                B5IdRefId2 = goodTranceTable.getB5IdRefId2();
                B5IdRefId3 = goodTranceTable.getB5IdRefId3();
                B5IdRefId4 = goodTranceTable.getB5IdRefId4();
                B5IdRefId5 = goodTranceTable.getB5IdRefId5();
                B5IdRefId6 = goodTranceTable.getB5IdRefId6();
                B5IdRefId14 = goodTranceTable.getB5IdRefId14();
                B5HCCurrencyId = goodTranceTable.getB5HCCurrencyId();
                B5HCSellMethodId = goodTranceTable.getB5HCSellMethodId();
                B5HCAccountSideId = goodTranceTable.getB5HCAccountSideId();
                B5IdRefIdRecall = goodTranceTable.getB5IdRefIdRecall();
                B5HCStatusId = goodTranceTable.getB5HCStatusId();
                deliveryName = goodTranceTable.getDeliveryName();
                formNumber = goodTranceTable.getFormNumber();
                formDate = goodTranceTable.getFormDate();
                expireDate = goodTranceTable.getExpireDate();
                description = goodTranceTable.getDescription();
                syncState = CommonSyncState.BEFORE_SYNC;//we are in edit mode
                activityState = CommonActivityState.UPDATE;//we are in edit mode
                oldValue = goodTranceTable.getOldValue();
                goodTransDetail = goodTranceTable.getGoodTransDetail();
                duration = goodTranceTable.getDuration();
                customerGroupId = goodTranceTable.getCustomerGroupId();
                addressId = goodTranceTable.getAddressId();
                goodTransDetailsModelArrayList = GoodTransDetailJsonHelper.getGoodTransDetailArray(goodTransDetail);
                addDisposable(setCustomerGroupNameFromIdEditMode());
            }

            @Override
            public void onError() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_16);
                }
            }
        };
        addDisposable(GetOneRowByGUIDHandler.getRowGoodTrance(primitiveGUID, GoodTranceTable.class, false, iGetRowByGUIDQueryListener));
    }

    /*
    after that user select buyer, should be set default value for costumer group and address
    if have not values,
    here we sure we have B5IdRefId3
    attention : we have an other query for show address and costumer group
                  for show in spinner,and have an other query for get values from id
    */
    private Disposable setCustomerGroupNameFromIdEditMode() {
        GetCustomerGroupNameFromIdQuery getCustomerGroupNameFromIdQuery = (GetCustomerGroupNameFromIdQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_CUSTOMER_GROUP_NAME_FROM_ID_QUERY);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(customerGroupId);
        assert getCustomerGroupNameFromIdQuery != null;
        return getCustomerGroupNameFromIdQuery.data(globalModel)
                .subscribe(iModels -> {
                    primitiveCostumerGroupName = globalModel.getMyString2();
                    addDisposable(setAddressNameFromIdEditMode());
                }, throwable -> {
                    if (checkNull()) {
                        getView().showHideProgress(false);
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_61);
                    }
                });
    }

    /*
    after that user select buyer, should be set default value for costumer group and address
    if have not values,
    here we sure we have B5IdRefId3
    attention : we have an other query for show address and costumer group
                  for show in spinner,and have an other query for get values from id
    */
    private Disposable setAddressNameFromIdEditMode() {
        GetAddressNameFromIdQuery getAddressNameFromIdQuery = (GetAddressNameFromIdQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ADDRESS_NAME_FROM_ID_QUERY);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(addressId);
        assert getAddressNameFromIdQuery != null;
        return getAddressNameFromIdQuery.data(globalModel)
                .subscribe(iModels -> {
                    primitiveAddressName = globalModel.getMyString2();
                    showFormAfterPrimitiveData();
                }, throwable -> {
                    if (checkNull()) {
                        getView().showHideProgress(false);
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_62);
                    }
                });
    }

    //todo تکمیل باقی مانده فرم کد ها
    private void showFormAfterPrimitiveData() {
        switch (myFormCode) {
            case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                showFormReqGoodFromWareHouseEditMode();
                break;
            case PRODUCTION_FORM_CODE:
                showFormProductionFormCodeEditMode();
                break;
            case PERMANENT_RECEIPT_GOODS_FORM_CODE:
            case RECEIPT_GOODS_FORM_CODE:
            case BUY_SERVICES_INVOICE_FORM_CODE:
                showFormPermanentReceiptGoodsEditMode();
                break;
            case WAREHOUSE_TRANSFERENCE_FORM_CODE:
                showFormWarehouseTransferenceEditMode();
                break;
            case BUYER_REQUEST_FORM_CODE:
                showFormBuyerReqEditMode();
                break;
            case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                showFormGoodsDeliveryProceedingsEditMode();
                break;
        }
    }

    /**
     * do not worry about empty goodTransDetailsModelArrayList
     * every where that goodTransDetailsModelArrayList is changed(socket or click by user),
     * only call this method to update detail recycler
     */
    private Disposable showTableInDetailsRec() {

        if (checkNull()) {
            getView().showHideProgress(true);
        }
        ArrayList<String> goodIdList = new ArrayList<>();
        Stream.of(goodTransDetailsModelArrayList).forEach(goodTransDetailsModel -> goodIdList.add(goodTransDetailsModel.getGoodId()));
        GlobalModel globalModel = new GlobalModel();
        globalModel.setStringArrayList(goodIdList);

        /*
        all data in goodTransDetailsModelArrayList
        were be makes from salable or purchasable or goods table
        and we sure have those ids in goods table
         */
        GetGoodsListByIdListQuery getGoodsListByIdListQuery = (GetGoodsListByIdListQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_GOODS_LIST_BY_ID_LIST_QUERY);
        assert getGoodsListByIdListQuery != null;
        return getGoodsListByIdListQuery.data(globalModel)
                .subscribe(iModels -> {
                    setDataToDetailsAdaptor(getProperContentForDetailRec(globalModel.getGoodsTableArrayList()));
                    if (checkNull()) {
                        getView().showHideProgress(false);
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showHideProgress(false);
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_19);
                    }
                });
    }

    private ArrayList<GoodsDetailModel> getProperContentForDetailRec(ArrayList<GoodsTable> goodsTableArrayList) {
        //goodTransDetailsModelArrayList and goodsTableArrayList have same size
        ArrayList<GoodsDetailModel> goodsDetailModelArrayList = new ArrayList<>(goodTransDetailsModelArrayList.size());

        for (int i = 0; i < goodTransDetailsModelArrayList.size(); i++) {
            goodsDetailModelArrayList.add(
                    new GoodsDetailModel(
                            goodsContentTextMaker(goodsTableArrayList.get(i).getName()
                                    , goodTransDetailsModelArrayList.get(i).getAmount2(),
                                    goodTransDetailsModelArrayList.get(i).getCurrencyNetValue(),
                                    String.valueOf(Double.parseDouble(goodTransDetailsModelArrayList.get(i).getCurrencyDiscount1()) + Double.parseDouble(goodTransDetailsModelArrayList.get(i).getCurrencyDiscount2())),
                                    goodTransDetailsModelArrayList.get(i).getCurrencyTaxValue()),
                            goodsTitleTextMaker(goodsTableArrayList.get(i).getCode())
                    )
            );
        }
        return goodsDetailModelArrayList;
    }

    private String goodsContentTextMaker(String name, String goodSubAmount, String goodSubCost
            , String discount1, String tax) {
        return EditTextCheckHelper.concatHandler(
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_name), name),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.sub_number), goodSubAmount.equals(Commons.NULL_INTEGER) ? Commons.DASH : goodSubAmount),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.net_price), goodSubCost.equals(Commons.NULL_INTEGER) ? Commons.DASH : goodSubCost),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.discount_1), discount1.equals(Commons.NULL_INTEGER) ? Commons.DASH : discount1),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.tax), tax.equals(Commons.NULL_INTEGER) ? Commons.DASH : tax)
        );
    }

    private String goodsTitleTextMaker(String code) {
        return String.format(CommonsFormat.FORMAT_3,context.getString(R.string.good_code), code);
        /*return EditTextCheckHelper.concatHandler(
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.code), code)
        );*/
    }

    private void setDataToDetailsAdaptor(ArrayList<GoodsDetailModel> goodsDetailModelsList) {
        if (goodDetailsRecAdaptor != null) {
            goodDetailsRecAdaptor.setDataModelList(goodsDetailModelsList);
        }
    }

    private ArrayList<String> makeProperArrayListForDuration() {
        ArrayList<String> durationArray = new ArrayList<>();
        durationArray.add(context.getString(R.string.same_day));
        for (int i = 1; i < MAXIMUM_DURATION_LIMIT; i++) {
            durationArray.add(i + Commons.SPACE + Commons.SPACE + context.getString(R.string.days_later));
        }
        return durationArray;
    }

    private Disposable saveDataGoodTrance() {
        if (HaveNotZeroAmountDetailGoods()) {
            GoodTranceTable goodTranceTable = new GoodTranceTable(id, myFormId, B5IdRefId1, B5IdRefId2, B5IdRefId3,
                    B5IdRefId4, B5IdRefId5, B5IdRefId6, B5IdRefId14, B5HCCurrencyId, B5HCSellMethodId, B5HCAccountSideId, B5IdRefIdRecall, B5HCStatusId, deliveryName, formNumber, formDate, expireDate, description, guid, syncState, activityState, oldValue, GoodTransDetailJsonHelper.goodTransDetailsJson(goodTransDetailsModelArrayList), duration, customerGroupId, addressId);
            InsertUpdateGoodTransTableQuery insertUpdateGoodTransTableQuery = (InsertUpdateGoodTransTableQuery)
                    FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_GOOD_TRANS_TABLE_QUERY);

            assert insertUpdateGoodTransTableQuery != null;
            return insertUpdateGoodTransTableQuery.data(goodTranceTable).subscribe(iModels -> {
                SendPacket.sendEncryptionMessage(context, SocketJsonMaker.goodTransSocket(SharedPreferenceProvider.getOrganization(context), SharedPreferenceProvider.getUserId(context), goodTranceTable), false);
                if (checkNull()) {
                    ActivityIntents.resultOkFromGoodTranceAct(getView().getActivity());
                }
            }, throwable -> {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_20);
                }
            });
        } else {
            if(checkNull()){
                getView().showHideProgress(false);
                getView().showMessageZeroValue();
            }
            return null;
        }

    }

    private Disposable saveDataGoodTransAndChangeSalableTempAmount() {
        if (HaveNotZeroAmountDetailGoods()) {
            GoodTranceTable goodTranceTable = new GoodTranceTable(id, myFormId, B5IdRefId1, B5IdRefId2, B5IdRefId3,
                    B5IdRefId4, B5IdRefId5, B5IdRefId6, B5IdRefId14, B5HCCurrencyId, B5HCSellMethodId, B5HCAccountSideId, B5IdRefIdRecall, B5HCStatusId, deliveryName, formNumber, formDate, expireDate, description, guid, syncState, activityState, oldValue, GoodTransDetailJsonHelper.goodTransDetailsJson(goodTransDetailsModelArrayList), duration, customerGroupId, addressId);
            InsertUpdateGoodTransTableChangeSalableAmountQuery insertUpdateGoodTransTableChangeSalableAmountQuery = (InsertUpdateGoodTransTableChangeSalableAmountQuery)
                    FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_GOOD_TRANS_TABLE_CHANGE_SALABLE_AMOUNT_QUERY);

            assert insertUpdateGoodTransTableChangeSalableAmountQuery != null;
            return insertUpdateGoodTransTableChangeSalableAmountQuery.data(goodTranceTable).subscribe(iModels -> {
                SendPacket.sendEncryptionMessage(context, SocketJsonMaker.goodTransSocket(SharedPreferenceProvider.getOrganization(context), SharedPreferenceProvider.getUserId(context), goodTranceTable), false);
                if (checkNull()) {
                    ActivityIntents.resultOkFromGoodTranceAct(getView().getActivity());
                }
            }, throwable -> {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_102);
                }
            });
        } else {
            if(checkNull()){
                getView().showHideProgress(false);
                getView().showMessageZeroValue();
            }
            return null;
        }
    }

    private Disposable saveDataGoodTransAndChangePurchasableTempAmount() {
        if (HaveNotZeroAmountDetailGoods()) {
            GoodTranceTable goodTranceTable = new GoodTranceTable(id, myFormId, B5IdRefId1, B5IdRefId2, B5IdRefId3,
                    B5IdRefId4, B5IdRefId5, B5IdRefId6, B5IdRefId14, B5HCCurrencyId, B5HCSellMethodId, B5HCAccountSideId, B5IdRefIdRecall, B5HCStatusId, deliveryName, formNumber, formDate, expireDate, description, guid, syncState, activityState, oldValue, GoodTransDetailJsonHelper.goodTransDetailsJson(goodTransDetailsModelArrayList), duration, customerGroupId, addressId);
            InsertUpdateGoodTransTableChangePurchasableAmountQuery insertUpdateGoodTransTableChangePurchasableAmountQuery = (InsertUpdateGoodTransTableChangePurchasableAmountQuery)
                    FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_GOOD_TRANS_TABLE_CHANGE_PURCHASABLE_AMOUNT_QUERY);

            assert insertUpdateGoodTransTableChangePurchasableAmountQuery != null;
            return insertUpdateGoodTransTableChangePurchasableAmountQuery.data(goodTranceTable).subscribe(iModels -> {
                SendPacket.sendEncryptionMessage(context, SocketJsonMaker.goodTransSocket(SharedPreferenceProvider.getOrganization(context), SharedPreferenceProvider.getUserId(context), goodTranceTable), false);
                if (checkNull()) {
                    ActivityIntents.resultOkFromGoodTranceAct(getView().getActivity());
                }
            }, throwable -> {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_103);
                }
            });
        } else {
            if(checkNull()){
                getView().showHideProgress(false);
                getView().showMessageZeroValue();
            }
            return null;
        }
    }

    /*
    check if we have zero amount in details or not
     */
    private boolean HaveNotZeroAmountDetailGoods() {
        final boolean[] haveNotZeroAmount = {true};
        Stream.of(goodTransDetailsModelArrayList).forEach(goodTransDetailsModel -> {
            String amount = goodTransDetailsModel.getAmount();
            if(CalculationHelper.isNumberZero(amount)||amount.equals(Commons.NULL_INTEGER)){
                haveNotZeroAmount[0] = false;
            }
        });
        return haveNotZeroAmount[0];
    }

    private Disposable saveDataGoodTransAndChangeRefineSalableTempAmount() {

        GoodTranceTable goodTranceTable = new GoodTranceTable(id, myFormId, B5IdRefId1, B5IdRefId2, B5IdRefId3,
                B5IdRefId4, B5IdRefId5, B5IdRefId6, B5IdRefId14, B5HCCurrencyId, B5HCSellMethodId, B5HCAccountSideId, B5IdRefIdRecall, B5HCStatusId, deliveryName, formNumber, formDate, expireDate, description, guid, syncState, activityState, oldValue, GoodTransDetailJsonHelper.goodTransDetailsJson(goodTransDetailsModelArrayList), duration, customerGroupId, addressId);
        InsertUpdateGoodTransTableRefineAndChangeSalableAmountQuery insertUpdateGoodTransTableRefineAndChangeSalableAmountQuery = (InsertUpdateGoodTransTableRefineAndChangeSalableAmountQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_GOOD_TRANS_TABLE_REFINE_AND_CHANGE_SALABLE_AMOUNT_QUERY);

        assert insertUpdateGoodTransTableRefineAndChangeSalableAmountQuery != null;
        return insertUpdateGoodTransTableRefineAndChangeSalableAmountQuery.data(goodTranceTable).subscribe(iModels -> {
            SendPacket.sendEncryptionMessage(context, SocketJsonMaker.goodTransSocket(SharedPreferenceProvider.getOrganization(context), SharedPreferenceProvider.getUserId(context), goodTranceTable), false);
            if (checkNull()) {
                ActivityIntents.resultOkFromGoodTranceAct(getView().getActivity());
            }
        }, throwable -> {
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_106);
            }
        });
    }

    private Disposable saveDataGoodTransAndChangeRefinePurchasableTempAmount() {

        GoodTranceTable goodTranceTable = new GoodTranceTable(id, myFormId, B5IdRefId1, B5IdRefId2, B5IdRefId3,
                B5IdRefId4, B5IdRefId5, B5IdRefId6, B5IdRefId14, B5HCCurrencyId, B5HCSellMethodId, B5HCAccountSideId, B5IdRefIdRecall, B5HCStatusId, deliveryName, formNumber, formDate, expireDate, description, guid, syncState, activityState, oldValue, GoodTransDetailJsonHelper.goodTransDetailsJson(goodTransDetailsModelArrayList), duration, customerGroupId, addressId);
        InsertUpdateGoodTransTableRefineAndChangePurchasableAmountQuery insertUpdateGoodTransTableRefineAndChangePurchasableAmountQuery = (InsertUpdateGoodTransTableRefineAndChangePurchasableAmountQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_GOOD_TRANS_TABLE_REFINE_AND_CHANGE_PURCHASABLE_AMOUNT_QUERY);

        assert insertUpdateGoodTransTableRefineAndChangePurchasableAmountQuery != null;
        return insertUpdateGoodTransTableRefineAndChangePurchasableAmountQuery.data(goodTranceTable).subscribe(iModels -> {
            SendPacket.sendEncryptionMessage(context, SocketJsonMaker.goodTransSocket(SharedPreferenceProvider.getOrganization(context), SharedPreferenceProvider.getUserId(context), goodTranceTable), false);
            if (checkNull()) {
                ActivityIntents.resultOkFromGoodTranceAct(getView().getActivity());
            }
        }, throwable -> {
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_106);
            }
        });
    }

    //todo تکمیل بقیه فرم کد ها
    private void goToMultipleSelect() {
        if (checkNull()) {
            getView().showHideProgress(true);
        }
        if (canUserAddNewDetails()) {
            switch (myFormCode) {
                //case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE: @deprecated
                //case PRODUCTION_FORM_CODE:                   @deprecated
                case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                case BUYER_REQUEST_FORM_CODE:
                    if (checkNull()) {
                        //send parent form code for use in Destination
                        ActivityIntentFactoryHandler.goToProperActivity(getView().getActivity(), CommonsFormCode.SELECT_GOODS_FOR_ORDER_FORM_CODE, setProperMoultySelectionIntentModel(), false, false);
                    }
                    break;
                case PERMANENT_RECEIPT_GOODS_FORM_CODE:
                case WAREHOUSE_TRANSFERENCE_FORM_CODE:
                case RECEIPT_GOODS_FORM_CODE:
                case BUY_SERVICES_INVOICE_FORM_CODE:
                    //do nothings user can not see this button
                    break;
            }
        }
        if (checkNull()) {
            getView().showHideProgress(false);
        }
    }

    private IntentModel setProperMoultySelectionIntentModel() {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString2(myFormCode);
        intentModel.setSomeString(Commons.NULL);
        intentModel.setSomeString3(B5IdRefId2);//warehouse id
        return intentModel;
    }

    //todo
    //for go to add new detail or edit one detail
    private void goSelectOneGoods(boolean goForEdit, int position) {
        if (checkNull()) {
            getView().showHideProgress(true);
        }
        if (canUserAddNewDetails()) {
            switch (myFormCode) {
                case PERMANENT_RECEIPT_GOODS_FORM_CODE:
                case WAREHOUSE_TRANSFERENCE_FORM_CODE:
                case RECEIPT_GOODS_FORM_CODE:
                case BUY_SERVICES_INVOICE_FORM_CODE:
                case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                case PRODUCTION_FORM_CODE:
                case BUYER_REQUEST_FORM_CODE://for this form code user can not see add new btn and come here for edit only
                case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE://for this form code user can not see add new btn and come here for edit only
                    if (checkNull()) {
                        ActivityIntentFactoryHandler.goToProperActivity(
                                getView().getActivity(),
                                CommonsFormCode.ADD_EDIT_ONE_GOOD_TO_GOOD_TRANS,
                                setupIntentModelFoRGoGoodsForGoodTrans(goForEdit, position),
                                false,
                                false
                        );
                    }
                    break;

            }
        }
        if (checkNull()) {
            getView().showHideProgress(false);
        }
    }

    private IntentModel setupIntentModelFoRGoGoodsForGoodTrans(boolean goForEdit, int position) {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(goForEdit ? goodTransDetailsModelArrayList.get(position).getGoodId() : Commons.NULL);//we send id of goods for set that we go for edit in base presenter
        intentModel.setSomeString2(myFormCode);
        intentModel.setSomeString3(B5IdRefId2);//we sure we have that as -1 or some value
        intentModel.setSomeString4(goForEdit ? gson.toJson(goodTransDetailsModelArrayList.get(position), GoodTransDetailsModel.class) : Commons.NULL);
        intentModel.setCanChangeReqAmount(canChangeReqAmountInGoodsForGoodTrans(goForEdit));

        return intentModel;

    }

    private boolean canChangeReqAmountInGoodsForGoodTrans(boolean goForEdit) {
        if (goForEdit) {
            if (B5IdRefId4 != null) {
                //no need to check , because data were be filtered from database, but ....
                return SharedPreferenceProvider.getUserId(context).equals(B5IdRefId4);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean canUserAddNewDetails() {

        boolean conf1 = true, conf2 = true, conf3 = true;

        if (checkHaveConfig(1) && B5IdRefId1.equals(Commons.NULL_INTEGER)) {
            conf1 = false;
            comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELL_EMPORIUM_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sell_emporium), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
        }
        if (checkHaveConfig(2) && B5IdRefId2.equals(Commons.NULL_INTEGER)) {
            conf2 = false;
            comboFormRecAdaptor.updateRowValue(CommonComboClickType.WAREHOUSE_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.warehouse_code), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
        }
        if (checkHaveConfig(3) && B5IdRefId3.equals(Commons.NULL_INTEGER)) {
            conf3 = false;
            comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_BUYER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.buyer_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
        }
        if (!conf1 || !conf2 || !conf3) {
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageFillProperValue();
            }
        }
        return conf1 && conf2 && conf3;

    }

    //endregion
    //----------------------------------------------------------------------------------------------
    //region ReqGoodFromWareHouse
    private void setupCustomLayoutManagerReqGoodFromWareHouse() {

        ArrayList<Integer> listOfQuarterPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfThirdPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfHalfPosition = new ArrayList<>();

        listOfHalfPosition.add(0);//form date value
        listOfHalfPosition.add(1);//form number value

        if (checkNull()) {
            getView().setupStairCustomLayoutManager(listOfQuarterPosition, listOfThirdPosition, listOfHalfPosition);
        }
    }

    private void showFormReqGoodFromWareHouse() {
        if (checkNull()) {
            if (primitiveGUID.equals(Commons.NULL)) {
                comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToReqGoodFromWareHouse(), setProperClickRowTypeToReqGoodFromWareHouse(), setProperDataToReqGoodFromWareHouse());
                getView().showHideProgress(false);
                getView().showHideConslayGoodTrance(true);
            } else {
                getGoodTranceByGuid();
            }
        }
    }

    private void showFormReqGoodFromWareHouseEditMode() {
        if (checkNull()) {
            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToReqGoodFromWareHouse(), setProperClickRowTypeToReqGoodFromWareHouseEditMode(), setPrimitiveProperDataToReqGoodFromWareHouse());
            addDisposable(showTableInDetailsRec());
            getView().showHideProgress(false);
            getView().showHideConslayGoodTrance(true);
        }
    }

    private ArrayList<Integer> setProperRowTypeToReqGoodFromWareHouse() {

        ArrayList<Integer> comboFormType = new ArrayList<>();

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_16);//form date value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_16);//form number value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_3);//expire date
        //comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_11);//search goods @deprecated
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_17);//add goods


        if (checkHaveConfig(1)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId1
        }
        if (checkHaveConfig(2)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId2
        }
        if (checkHaveConfig(3)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId3
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//customerGroupId
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//addressId
        }
        if (checkHaveConfig(4)) {
            //no need to show any things
            B5IdRefId4 = SharedPreferenceProvider.getUserId(context);
        }
        if (checkHaveConfig(6)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId6
        }

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//duration
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_18);//description

        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToReqGoodFromWareHouse() {
        ArrayList<Integer> comboFormClickType = new ArrayList<>();

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
        comboFormClickType.add(CommonComboClickType.END_DATE_CLICK_TYPE);
        //comboFormClickType.add(CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE); @deprecated
        comboFormClickType.add(CommonComboClickType.ADD_NEW_CLICK_TYPE);

        if (checkHaveConfig(1)) {
            comboFormClickType.add(CommonComboClickType.SELL_EMPORIUM_CLICK_TYPE);
        }
        if (checkHaveConfig(2)) {
            comboFormClickType.add(CommonComboClickType.WAREHOUSE_NUMBER_CLICK_TYPE);
        }
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.SELECT_BUYER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_CUSTOMER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_ADDRESS_CLICK_TYPE);
        }
        if (checkHaveConfig(6)) {
            comboFormClickType.add(CommonComboClickType.CENTER_COST_CLICK_TYPE);
        }
        comboFormClickType.add(CommonComboClickType.DURATION_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.DESCRIPTION_CLICK_TYPE);

        return comboFormClickType;
    }

    private ArrayList<Integer> setProperClickRowTypeToReqGoodFromWareHouseEditMode() {
        ArrayList<Integer> comboFormClickType = new ArrayList<>();

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
        comboFormClickType.add(CommonComboClickType.END_DATE_CLICK_TYPE);
        //comboFormClickType.add(CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE); @deprecated
        comboFormClickType.add(CommonComboClickType.ADD_NEW_CLICK_TYPE);

        if (checkHaveConfig(1)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_29);
        }
        if (checkHaveConfig(2)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_30);
        }
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.SELECT_BUYER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_CUSTOMER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_ADDRESS_CLICK_TYPE);
        }
        if (checkHaveConfig(6)) {
            comboFormClickType.add(CommonComboClickType.CENTER_COST_CLICK_TYPE);
        }
        comboFormClickType.add(CommonComboClickType.DURATION_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.DESCRIPTION_CLICK_TYPE);

        return comboFormClickType;
    }

    private List<ComboFormModel> setProperDataToReqGoodFromWareHouse() {
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(formDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_trans_number), Commons.DASH), Commons.NULL, false));// we are  in edit mode//when we are not in edit mode, we have not formNumber
        String mExpireDate = CustomTimeHelper.checkMyTimeNotNull(CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(expireDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context)) ? CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(expireDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context) : Commons.DASH;
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.expire_date), mExpireDate), Commons.NULL, false));
        //comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false)); @deprecated
        comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));

        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.sell_emporium), SharedPreferenceProvider.getSellEmporiumName(context), Commons.NULL, false));//get default sells Emporium
        }
        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse_code), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.buyer_name), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.customer_group), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.address), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(6)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.duration), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), Commons.NULL, Commons.NULL, false));

        return comboFormModels;
    }

    private List<ComboFormModel> setPrimitiveProperDataToReqGoodFromWareHouse() {


        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();


        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(formDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_trans_number), formNumber), Commons.NULL, false));// we are  in edit mode
        String mExpireDate = CustomTimeHelper.checkMyTimeNotNull(CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(formDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context)) ? CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(expireDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context) : Commons.DASH;
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.expire_date), mExpireDate), Commons.NULL, false));
        //comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false)); @deprecated
        comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));

        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.sell_emporium), myListBaseNameList.get(0).get(myListBaseIdList.get(0).indexOf(B5IdRefId1) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(0).indexOf(B5IdRefId1)), Commons.NULL, false));
        }

        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse_code), myListBaseNameList.get(1).get(myListBaseIdList.get(1).indexOf(B5IdRefId2) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(1).indexOf(B5IdRefId2)), Commons.NULL, false));
        }

        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.buyer_name), myListBaseNameList.get(2).get(myListBaseIdList.get(2).indexOf(B5IdRefId3) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(2).indexOf(B5IdRefId3)), Commons.NULL, false));
            //we sure we calculate primitiveCostumerGroupName & primitiveAddressName
            comboFormModels.add(new ComboFormModel(context.getString(R.string.customer_group), primitiveCostumerGroupName, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.address), primitiveAddressName, Commons.NULL, false));
        }

        if (checkHaveConfig(6)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), myListBaseNameList.get(5).get(myListBaseIdList.get(5).indexOf(B5IdRefId6) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(5).indexOf(B5IdRefId6)), Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.duration), makeProperArrayListForDuration().get(duration < MAXIMUM_DURATION_LIMIT && duration != Commons.MINUS_INT ? duration : 0), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), description, Commons.NULL, false));

        return comboFormModels;
    }

    private void checkDataReqGoodFromWareHouse() {

        /*
        no need to check expireDate this value may be null_date
        no need to check duration this value may be 0
         */

        if (checkHaveConfig(1) && B5IdRefId1.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELL_EMPORIUM_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sell_emporium), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();
                return;
            }
        }

        if (checkHaveConfig(2) && B5IdRefId2.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.WAREHOUSE_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.warehouse_code), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();

                return;
            }
        }

        if (checkHaveConfig(3) && B5IdRefId3.equals(Commons.NULL_INTEGER)) {
            //after set config 3, address and group were be set automaticly

            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_BUYER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.buyer_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();

                return;
            }
        }

        if (checkHaveConfig(6) && B5IdRefId6.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.CENTER_COST_CLICK_TYPE, new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();

                return;
            }
        }

        if (goodTransDetailsModelArrayList.size() == 0) {
            if (checkNull()) {
                getView().showHideProgress(false);
                //getView().showMessageNoDetailInGoodTrance();
                getView().showMessageFillProperValue();

                return;
            }
        }
        addDisposable(saveDataGoodTrance());
    }

    //endregion
    //----------------------------------------------------------------------------------------------
    //region ProductionFormCode
    private void setupCustomLayoutManagerProductionFormCode() {

        ArrayList<Integer> listOfQuarterPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfThirdPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfHalfPosition = new ArrayList<>();


        listOfHalfPosition.add(0);//form date value
        listOfHalfPosition.add(1);//form number value


        if (checkNull()) {
            getView().setupStairCustomLayoutManager(listOfQuarterPosition, listOfThirdPosition, listOfHalfPosition);
        }
    }

    private void showFormProductionFormCode() {
        if (checkNull()) {
            if (primitiveGUID.equals(Commons.NULL)) {
                comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToProductionFormCode(), setProperClickRowTypeToProductionFormCode(), setProperDataToProductionFormCode());
                getView().showHideProgress(false);
                getView().showHideConslayGoodTrance(true);
            } else {
                getGoodTranceByGuid();
            }
        }
    }

    private void showFormProductionFormCodeEditMode() {
        if (checkNull()) {
            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToProductionFormCode(), setProperClickRowTypeToProductionFormCodeEditMode(), setPrimitiveProperDataToProductionFormCode());
            addDisposable(showTableInDetailsRec());
            getView().showHideProgress(false);
            getView().showHideConslayGoodTrance(true);
        }
    }

    private ArrayList<Integer> setProperRowTypeToProductionFormCode() {
        ArrayList<Integer> comboFormType = new ArrayList<>();

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_16);//form date value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_16);//form number value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_3);//expire date
        //comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_11);//search goods  @deprecated
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_17);//add goods


        if (checkHaveConfig(1)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId1
        }
        if (checkHaveConfig(2)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId2
        }
        if (checkHaveConfig(3)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId3
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//customerGroupId
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//addressId
        }
        if (checkHaveConfig(4)) {
            //no need to show any things
            B5IdRefId4 = SharedPreferenceProvider.getUserId(context);
        }
        if (checkHaveConfig(6)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId6
        }

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_18);//description

        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToProductionFormCode() {
        ArrayList<Integer> comboFormClickType = new ArrayList<>();

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
        comboFormClickType.add(CommonComboClickType.END_DATE_CLICK_TYPE);
        //comboFormClickType.add(CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE); @deprecated
        comboFormClickType.add(CommonComboClickType.ADD_NEW_CLICK_TYPE);

        if (checkHaveConfig(1)) {
            comboFormClickType.add(CommonComboClickType.SELL_EMPORIUM_CLICK_TYPE);
        }
        if (checkHaveConfig(2)) {
            comboFormClickType.add(CommonComboClickType.WAREHOUSE_NUMBER_CLICK_TYPE);
        }
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.SELECT_BUYER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_CUSTOMER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_ADDRESS_CLICK_TYPE);
        }
        if (checkHaveConfig(6)) {
            comboFormClickType.add(CommonComboClickType.CENTER_COST_CLICK_TYPE);
        }
        comboFormClickType.add(CommonComboClickType.DESCRIPTION_CLICK_TYPE);

        return comboFormClickType;
    }

    private ArrayList<Integer> setProperClickRowTypeToProductionFormCodeEditMode() {
        ArrayList<Integer> comboFormClickType = new ArrayList<>();

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
        comboFormClickType.add(CommonComboClickType.END_DATE_CLICK_TYPE);
        //comboFormClickType.add(CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE); @deprecated
        comboFormClickType.add(CommonComboClickType.ADD_NEW_CLICK_TYPE);

        if (checkHaveConfig(1)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_29);
        }
        if (checkHaveConfig(2)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_30);
        }
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.SELECT_BUYER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_CUSTOMER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_ADDRESS_CLICK_TYPE);
        }
        if (checkHaveConfig(6)) {
            comboFormClickType.add(CommonComboClickType.CENTER_COST_CLICK_TYPE);
        }
        comboFormClickType.add(CommonComboClickType.DESCRIPTION_CLICK_TYPE);

        return comboFormClickType;
    }

    private List<ComboFormModel> setProperDataToProductionFormCode() {
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();


        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(formDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_trans_number), Commons.DASH), Commons.NULL, false));

        String mExpireDate = CustomTimeHelper.checkMyTimeNotNull(CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(expireDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context)) ? CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(expireDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context) : Commons.DASH;
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.expire_date), mExpireDate), Commons.NULL, false));
        //comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false)); @deprecated
        comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));

        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.sell_emporium), SharedPreferenceProvider.getSellEmporiumName(context), Commons.NULL, false));//get default sells Emporium
        }
        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse_code), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.buyer_name), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.customer_group), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.address), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(6)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), Commons.NULL, Commons.NULL, false));

        return comboFormModels;
    }

    private List<ComboFormModel> setPrimitiveProperDataToProductionFormCode() {


        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(formDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_trans_number), formNumber), Commons.NULL, false));// we are  in edit mode

        String mExpireDate = CustomTimeHelper.checkMyTimeNotNull(CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(formDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context)) ? CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(expireDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context) : Commons.DASH;
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.expire_date), mExpireDate), Commons.NULL, false));

        //comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));  @deprecated
        comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));


        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.sell_emporium), myListBaseNameList.get(0).get(myListBaseIdList.get(0).indexOf(B5IdRefId1) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(0).indexOf(B5IdRefId1)), Commons.NULL, false));
        }

        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse_code), myListBaseNameList.get(1).get(myListBaseIdList.get(1).indexOf(B5IdRefId2) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(1).indexOf(B5IdRefId2)), Commons.NULL, false));
        }

        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.buyer_name), myListBaseNameList.get(2).get(myListBaseIdList.get(2).indexOf(B5IdRefId3) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(2).indexOf(B5IdRefId3)), Commons.NULL, false));
            //we sure we calculate primitiveCostumerGroupName & primitiveAddressName
            comboFormModels.add(new ComboFormModel(context.getString(R.string.customer_group), primitiveCostumerGroupName, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.address), primitiveAddressName, Commons.NULL, false));
        }

        if (checkHaveConfig(6)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), myListBaseNameList.get(5).get(myListBaseIdList.get(5).indexOf(B5IdRefId6) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(5).indexOf(B5IdRefId6)), Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), description, Commons.NULL, false));

        return comboFormModels;
    }

    private void checkDataProductionFormCode() {

        /*
        no need to check expireDate this value may be null_date
         */

        if (checkHaveConfig(1) && B5IdRefId1.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELL_EMPORIUM_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sell_emporium), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();

                return;
            }
        }

        if (checkHaveConfig(2) && B5IdRefId2.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.WAREHOUSE_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.warehouse_code), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();

                return;
            }
        }

        if (checkHaveConfig(3) && B5IdRefId3.equals(Commons.NULL_INTEGER)) {
            //after set config 3, address and group were be set automaticly

            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_BUYER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.buyer_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();

                return;
            }
        }

        if (checkHaveConfig(6) && B5IdRefId6.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.CENTER_COST_CLICK_TYPE, new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();

                return;
            }
        }

        if (goodTransDetailsModelArrayList.size() == 0) {
            if (checkNull()) {
                getView().showHideProgress(false);
                //getView().showMessageNoDetailInGoodTrance();
                getView().showMessageFillProperValue();

                return;
            }
        }

        addDisposable(saveDataGoodTrance());
    }

    //endregion
    //----------------------------------------------------------------------------------------------
    //region PermanentReceiptGoods & RECEIPT_GOODS_FORM_CODE & BUY_SERVICES_INVOICE_FORM_CODE

    private void setupCustomLayoutManagerPermanentReceiptGoods() {
        ArrayList<Integer> listOfQuarterPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfThirdPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfHalfPosition = new ArrayList<>();

        listOfHalfPosition.add(0);//form date value
        listOfHalfPosition.add(1);//form number value

        if (checkNull()) {
            getView().setupStairCustomLayoutManager(listOfQuarterPosition, listOfThirdPosition, listOfHalfPosition);
        }
    }

    private void showFormPermanentReceiptGoods() {
        //only we can be here in edit mode
        if (checkNull()) {
            if (!primitiveGUID.equals(Commons.NULL)) {
                getView().showHideBTNDelete(false);
                getGoodTranceByGuid();
            } else {
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_6);
            }
        }
    }

    private void showFormPermanentReceiptGoodsEditMode() {
        if (checkNull()) {
            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToPermanentReceiptGoods(), setProperClickRowTypeToPermanentReceiptGoods(), setPrimitiveProperDataToPermanentReceiptGoods());
            addDisposable(showTableInDetailsRec());
            getView().showHideProgress(false);
            getView().showHideConslayGoodTrance(true);
        }
    }

    private ArrayList<Integer> setProperRowTypeToPermanentReceiptGoods() {
        ArrayList<Integer> comboFormType = new ArrayList<>();

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_16);//form date value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_16);//form number value

        if (checkHaveConfig(1)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId1
        }
        if (checkHaveConfig(2)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId2
        }
        if (checkHaveConfig(3)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId3
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//customerGroupId
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//addressId
        }
        if (checkHaveConfig(4)) {
            //do not set b5IdRefId4 to user id, wa can not change any data
        }
        if (checkHaveConfig(6)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId6
        }

        if (checkHaveUtilConfig(1)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//B5HCCurrencyId
        }

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//duration
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//description (reed only)

        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToPermanentReceiptGoods() {
        ArrayList<Integer> comboFormClickType = new ArrayList<>();

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_2);
        if (checkHaveConfig(1)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
        }

        if (checkHaveConfig(2)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
        }
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_5);
        }

        if (checkHaveConfig(6)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_6);
        }

        if (checkHaveUtilConfig(1)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_6);
        }

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_7);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_8);

        return comboFormClickType;
    }

    private List<ComboFormModel> setPrimitiveProperDataToPermanentReceiptGoods() {
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(formDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_trans_number), Commons.DASH), Commons.NULL, false));// we are  in edit mode//when we are not in edit mode, we have not formNumber
        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.sell_emporium), myListBaseNameList.get(0).get(myListBaseIdList.get(0).indexOf(B5IdRefId1) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(0).indexOf(B5IdRefId1)), Commons.NULL, false));
        }
        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse_code), myListBaseNameList.get(1).get(myListBaseIdList.get(1).indexOf(B5IdRefId2) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(1).indexOf(B5IdRefId2)), Commons.NULL, false));
        }
        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.buyer_name), myListBaseNameList.get(2).get(myListBaseIdList.get(2).indexOf(B5IdRefId3) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(2).indexOf(B5IdRefId3)), Commons.NULL, false));
            //we sure we calculate primitiveCostumerGroupName & primitiveAddressName
            comboFormModels.add(new ComboFormModel(context.getString(R.string.customer_group), primitiveCostumerGroupName, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.address), primitiveAddressName, Commons.NULL, false));
        }
        if (checkHaveConfig(6)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), myListBaseNameList.get(5).get(myListBaseIdList.get(5).indexOf(B5IdRefId6) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(5).indexOf(B5IdRefId6)), Commons.NULL, false));
        }
        if (checkHaveUtilConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.currency_unit), myListUtilNameList.get(0).get(myListUtilIdList.get(0).indexOf(B5HCCurrencyId) == -1 ? Commons.ZERO_INT : myListUtilIdList.get(0).indexOf(B5HCCurrencyId)), Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.duration), makeProperArrayListForDuration().get(duration < MAXIMUM_DURATION_LIMIT && duration != Commons.MINUS_INT ? duration : 0), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), description, Commons.NULL, false));

        return comboFormModels;
    }

    private void checkDataPermanentReceiptGoods() {
        //because we can not edit no need to save or send data
        cancelResultIntent();
    }

    //endregion
    //----------------------------------------------------------------------------------------------
    //region WarehouseTransference
    private void setupCustomLayoutManagerWarehouseTransference() {
        ArrayList<Integer> listOfQuarterPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfThirdPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfHalfPosition = new ArrayList<>();

        listOfHalfPosition.add(0);//form date value
        listOfHalfPosition.add(1);//form number value

        if (checkNull()) {
            getView().setupStairCustomLayoutManager(listOfQuarterPosition, listOfThirdPosition, listOfHalfPosition);
        }
    }

    private void showFormWarehouseTransference() {
        //only we can be here in edit mode
        if (checkNull()) {
            if (!primitiveGUID.equals(Commons.NULL)) {
                getView().showHideBTNDelete(false);
                getGoodTranceByGuid();
            } else {
                //here we have not new mode
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_17);
            }
        }
    }

    private void showFormWarehouseTransferenceEditMode() {
        if (checkNull()) {
            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToWarehouseTransference(), setProperClickRowTypeToWarehouseTransference(), setPrimitiveProperDataToWarehouseTransference());
            addDisposable(showTableInDetailsRec());
            getView().showHideProgress(false);
            getView().showHideConslayGoodTrance(true);
        }
    }

    private ArrayList<Integer> setProperRowTypeToWarehouseTransference() {
        ArrayList<Integer> comboFormType = new ArrayList<>();

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_16);//form date value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_16);//form number value

        if (checkHaveConfig(1)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId1
        }
        if (checkHaveConfig(2)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId2
        }
        if (checkHaveConfig(3)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId3
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//customerGroupId
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//addressId
        }
        if (checkHaveConfig(4)) {
            //do not set b5IdRefId4 to user id, wa can not change any data
        }
        if (checkHaveConfig(6)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId6
        }

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//description (reed only)

        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToWarehouseTransference() {
        ArrayList<Integer> comboFormClickType = new ArrayList<>();

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_2);
        if (checkHaveConfig(1)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
        }

        if (checkHaveConfig(2)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
        }
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_5);
        }

        if (checkHaveConfig(6)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_6);
        }

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_8);

        return comboFormClickType;
    }

    private List<ComboFormModel> setPrimitiveProperDataToWarehouseTransference() {
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(formDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_trans_number), Commons.DASH), Commons.NULL, false));// we are  in edit mode//when we are not in edit mode, we have not formNumber
        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.sell_emporium), myListBaseNameList.get(0).get(myListBaseIdList.get(0).indexOf(B5IdRefId1) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(0).indexOf(B5IdRefId1)), Commons.NULL, false));
        }
        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse_code), myListBaseNameList.get(1).get(myListBaseIdList.get(1).indexOf(B5IdRefId2) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(1).indexOf(B5IdRefId2)), Commons.NULL, false));
        }
        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.buyer_name), myListBaseNameList.get(2).get(myListBaseIdList.get(2).indexOf(B5IdRefId3) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(2).indexOf(B5IdRefId3)), Commons.NULL, false));
            //we sure we calculate primitiveCostumerGroupName & primitiveAddressName
            comboFormModels.add(new ComboFormModel(context.getString(R.string.customer_group), primitiveCostumerGroupName, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.address), primitiveAddressName, Commons.NULL, false));
        }
        if (checkHaveConfig(6)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), myListBaseNameList.get(5).get(myListBaseIdList.get(5).indexOf(B5IdRefId6) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(5).indexOf(B5IdRefId6)), Commons.NULL, false));
        }

        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), description, Commons.NULL, false));

        return comboFormModels;
    }

    private void checkDataWarehouseTransference() {
        //because we can not edit no need to save or send data
        cancelResultIntent();
    }

    //endregion
    //----------------------------------------------------------------------------------------------
    //region BuyerRequest
    private void setupCustomLayoutManagerBuyerReq() {

        ArrayList<Integer> listOfQuarterPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfThirdPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfHalfPosition = new ArrayList<>();

        listOfHalfPosition.add(0);//form date value
        listOfHalfPosition.add(1);//form number value
        //listOfThirdPosition.add(0);//add group Goods

        if (checkNull()) {
            getView().setupStairCustomLayoutManager(listOfQuarterPosition, listOfThirdPosition, listOfHalfPosition);
        }
    }

    private Disposable showFormBuyerReq() {
        SetAllRealSalableToTempAmount setAllRealSalableToTempAmount = (SetAllRealSalableToTempAmount) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SET_ALL_REAL_SALABLE_TO_TEMP_AMOUNT);
        assert setAllRealSalableToTempAmount != null;
        return setAllRealSalableToTempAmount
                .data(App.getNullRXModel())
                .subscribe(iModels -> {
                    if (checkNull()) {
                        if (!primitiveGUID.equals(Commons.NULL)) {
                            getGoodTranceByGuid();
                        } else {
                            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToBuyerReq(), setProperClickRowTypeToBuyerReq(), setProperDataToBuyerReq());
                            getView().showHideProgress(false);
                            getView().showHideConslayGoodTrance(true);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showHideProgress(false);
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_101);
                    }
                });

    }

    private void showFormBuyerReqEditMode() {
        if (checkNull()) {
            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToBuyerReq(), setProperClickRowTypeToBuyerReqEditMode(), setPrimitiveProperDataToBuyerReq());
            addDisposable(showTableInDetailsRec());
            getView().showHideProgress(false);
            getView().showHideConslayGoodTrance(true);
        }
    }

    private ArrayList<Integer> setProperRowTypeToBuyerReq() {
        ArrayList<Integer> comboFormType = new ArrayList<>();

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_16);//form date value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_16);//form number value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_11);//search goods

        if (checkHaveConfig(5)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//deliveryName
        }

        if (checkHaveConfig(1)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId1
        }
        if (checkHaveConfig(2)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId2
        }
        if (checkHaveConfig(3)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId3
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//customerGroupId
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//addressId
        }
        if (checkHaveConfig(4)) {
            B5IdRefId4 = SharedPreferenceProvider.getUserId(context);
        }
        if (checkHaveConfig(6)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId6
        }
        /*
         no need in this form code
        if (checkHaveUtilConfig(1)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5HcCurrencyId
        }*/
        if (checkHaveUtilConfig(3)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//sellsMethod
        }
        if (checkHaveUtilConfig(2)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//accountSide
        }
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//duration
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_18);//description

        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToBuyerReq() {
        ArrayList<Integer> comboFormClickType = new ArrayList<>();

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
        comboFormClickType.add(CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE);
        if (checkHaveConfig(5)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        }

        if (checkHaveConfig(1)) {
            comboFormClickType.add(CommonComboClickType.SELL_EMPORIUM_CLICK_TYPE);
        }
        if (checkHaveConfig(2)) {
            comboFormClickType.add(CommonComboClickType.WAREHOUSE_NUMBER_CLICK_TYPE);
        }
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.SELECT_BUYER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_CUSTOMER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_ADDRESS_CLICK_TYPE);
        }
        if (checkHaveConfig(6)) {
            comboFormClickType.add(CommonComboClickType.CENTER_COST_CLICK_TYPE);
        }
        /*
        no need in this form code

        if (checkHaveUtilConfig(1)) {
            comboFormClickType.add(CommonComboClickType.CURRENCY_UNIT_CLICK_TYPE);//b5HcCurrencyId
        }
        */
        if (checkHaveUtilConfig(3)) {
            comboFormClickType.add(CommonComboClickType.SELL_METHOD_CLICK_TYPE);//sellsMethod
        }
        if (checkHaveUtilConfig(2)) {
            comboFormClickType.add(CommonComboClickType.ACCOUNT_SIDE_CLICK_TYPE);//accountSide
        }
        comboFormClickType.add(CommonComboClickType.DURATION_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.DESCRIPTION_CLICK_TYPE);

        return comboFormClickType;
    }

    private ArrayList<Integer> setProperClickRowTypeToBuyerReqEditMode() {
        ArrayList<Integer> comboFormClickType = new ArrayList<>();

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
        comboFormClickType.add(CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE);
        if (checkHaveConfig(5)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        }

        if (checkHaveConfig(1)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_29);
        }
        if (checkHaveConfig(2)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_30);
        }
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.SELECT_BUYER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_CUSTOMER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_ADDRESS_CLICK_TYPE);
        }
        if (checkHaveConfig(6)) {
            comboFormClickType.add(CommonComboClickType.CENTER_COST_CLICK_TYPE);
        }
        /*
        no need in this form code

        if (checkHaveUtilConfig(1)) {
            comboFormClickType.add(CommonComboClickType.CURRENCY_UNIT_CLICK_TYPE);//b5HcCurrencyId
        }
        */
        if (checkHaveUtilConfig(3)) {
            comboFormClickType.add(CommonComboClickType.SELL_METHOD_CLICK_TYPE);//sellsMethod
        }
        if (checkHaveUtilConfig(2)) {
            comboFormClickType.add(CommonComboClickType.ACCOUNT_SIDE_CLICK_TYPE);//accountSide
        }
        comboFormClickType.add(CommonComboClickType.DURATION_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.DESCRIPTION_CLICK_TYPE);

        return comboFormClickType;
    }

    private List<ComboFormModel> setProperDataToBuyerReq() {
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(formDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_trans_number), Commons.DASH), Commons.NULL, false));// we are  in edit mode//when we are not in edit mode, we have not formNumber
        comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));
        if (checkHaveConfig(5)) {
            comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.delivery), deliveryName), Commons.NULL, false));
        }
        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.sell_emporium), SharedPreferenceProvider.getSellEmporiumName(context), Commons.NULL, false));//get default sells Emporium
        }
        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse_code), Commons.NULL, Commons.NULL, false));
        }

        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.buyer_name), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.customer_group), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.address), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(6)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, Commons.NULL, false));
        }
        /*
        no need in this form code
        if (checkHaveUtilConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.currency_unit), SharedPreferenceProvider.getMonetaryUnitName(context), Commons.NULL, false));
        }
        */
        if (checkHaveUtilConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.sell_method), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveUtilConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.account_side), Commons.NULL, Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.duration), Commons.NULL, Commons.NULL, false));

        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), Commons.NULL, Commons.NULL, false));

        return comboFormModels;
    }

    private List<ComboFormModel> setPrimitiveProperDataToBuyerReq() {


        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(formDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_trans_number), formNumber), Commons.NULL, false));// we are  in edit mode
        comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));
        if (checkHaveConfig(5)) {
            comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.delivery), deliveryName), Commons.NULL, false));
        }
        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.sell_emporium), myListBaseNameList.get(0).get(myListBaseIdList.get(0).indexOf(B5IdRefId1) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(0).indexOf(B5IdRefId1)), Commons.NULL, false));
        }

        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse_code), myListBaseNameList.get(1).get(myListBaseIdList.get(1).indexOf(B5IdRefId2) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(1).indexOf(B5IdRefId2)), Commons.NULL, false));
        }

        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.buyer_name), myListBaseNameList.get(2).get(myListBaseIdList.get(2).indexOf(B5IdRefId3) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(2).indexOf(B5IdRefId3)), Commons.NULL, false));
            //we sure we calculate primitiveCostumerGroupName & primitiveAddressName
            comboFormModels.add(new ComboFormModel(context.getString(R.string.customer_group), primitiveCostumerGroupName, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.address), primitiveAddressName, Commons.NULL, false));
        }

        if (checkHaveConfig(6)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), myListBaseNameList.get(5).get(myListBaseIdList.get(5).indexOf(B5IdRefId6) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(5).indexOf(B5IdRefId6)), Commons.NULL, false));
        }

        /*
        no need in this form code

        if (checkHaveUtilConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.currency_unit), myListUtilNameList.get(0).get(myListUtilIdList.get(0).indexOf(B5HCCurrencyId) == -1 ? Commons.ZERO_INT : myListUtilIdList.get(0).indexOf(B5HCCurrencyId)), Commons.NULL, false));
        }
        */
        if (checkHaveUtilConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.sell_method), myListUtilNameList.get(2).get(myListUtilIdList.get(2).indexOf(B5HCSellMethodId) == -1 ? Commons.ZERO_INT : myListUtilIdList.get(2).indexOf(B5HCSellMethodId)), Commons.NULL, false));
        }
        if (checkHaveUtilConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.account_side), myListUtilNameList.get(1).get(myListUtilIdList.get(1).indexOf(B5HCAccountSideId) == -1 ? Commons.ZERO_INT : myListUtilIdList.get(1).indexOf(B5HCAccountSideId)), Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.duration), makeProperArrayListForDuration().get(duration < MAXIMUM_DURATION_LIMIT && duration != Commons.MINUS_INT ? duration : 0), Commons.NULL, false));

        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), description, Commons.NULL, false));

        return comboFormModels;
    }

    private void checkDataBuyerReq() {

        if (checkHaveConfig(1) && B5IdRefId1.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELL_EMPORIUM_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sell_emporium), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();
                return;
            }
        }
        if (checkHaveConfig(2) && B5IdRefId2.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.WAREHOUSE_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.warehouse_code), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();
                return;
            }
        }

        if (checkHaveConfig(3) && B5IdRefId3.equals(Commons.NULL_INTEGER)) {
            //after set config 3, address and group were be set automaticly

            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_BUYER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.buyer_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();
                return;
            }
        }

        if (checkHaveConfig(6) && B5IdRefId6.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.CENTER_COST_CLICK_TYPE, new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();
                return;
            }
        }

        /*

        no need in this form code

        if (checkHaveUtilConfig(1) && B5HCCurrencyId.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.CURRENCY_UNIT_CLICK_TYPE, new ComboFormModel(context.getString(R.string.currency_unit), Commons.NULL, context.getString(R.string.fill_proper_value), false));
                return;
            }
        }*/

        if (checkHaveUtilConfig(3) && B5HCAccountSideId.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.ACCOUNT_SIDE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.account_side), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();
                return;
            }
        }

        if (checkHaveUtilConfig(2) && B5HCSellMethodId.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELL_METHOD_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sell_method), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();
                return;
            }
        }

        if (goodTransDetailsModelArrayList.size() == 0) {
            if (checkNull()) {
                getView().showHideProgress(false);
                //getView().showMessageNoDetailInGoodTrance();
                getView().showMessageFillProperValue();
                return;
            }
        }
        addDisposable(saveDataGoodTransAndChangeSalableTempAmount());
    }

    //endregion
    //----------------------------------------------------------------------------------------------
    //region GoodsDeliveryProceedings
    private void setupCustomLayoutManagerGoodsDeliveryProceedings() {

        ArrayList<Integer> listOfQuarterPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfThirdPosition = new ArrayList<>();//empty
        ArrayList<Integer> listOfHalfPosition = new ArrayList<>();

        listOfHalfPosition.add(0);//form date value
        listOfHalfPosition.add(1);//form number value
        //listOfThirdPosition.add(2);//add group Goods

        if (checkNull()) {
            getView().setupStairCustomLayoutManager(listOfQuarterPosition, listOfThirdPosition, listOfHalfPosition);
        }
    }

    private Disposable showFormGoodsDeliveryProceedings() {

        SetAllRealPurchasableToTempAmount setAllRealPurchasableToTempAmount = (SetAllRealPurchasableToTempAmount) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SET_ALL_REAL_PURCHASABLE_TO_TEMP_AMOUNT);
        assert setAllRealPurchasableToTempAmount != null;
        return setAllRealPurchasableToTempAmount
                .data(App.getNullRXModel())
                .subscribe(iModels -> {
                    if (checkNull()) {
                        if (!primitiveGUID.equals(Commons.NULL)) {
                            getGoodTranceByGuid();
                        } else {
                            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToGoodsDeliveryProceedings(), setProperClickRowTypeToGoodsDeliveryProceedings(), setProperDataToGoodsDeliveryProceedings());
                            getView().showHideProgress(false);
                            getView().showHideConslayGoodTrance(true);
                        }
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showHideProgress(false);
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_100);
                    }
                });

    }

    private void showFormGoodsDeliveryProceedingsEditMode() {
        if (checkNull()) {
            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToGoodsDeliveryProceedings(), setProperClickRowTypeToGoodsDeliveryProceedingsEditMode(), setPrimitiveProperDataToGoodsDeliveryProceedings());
            addDisposable(showTableInDetailsRec());
            getView().showHideProgress(false);
            getView().showHideConslayGoodTrance(true);
        }
    }

    private ArrayList<Integer> setProperRowTypeToGoodsDeliveryProceedings() {
        ArrayList<Integer> comboFormType = new ArrayList<>();

        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_16);//form date value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_16);//form number value
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_11);//search goods

        if (checkHaveConfig(1)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId1
        }
        if (checkHaveConfig(2)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId2
        }
        if (checkHaveConfig(3)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId3
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//customerGroupId
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//addressId
        }
        if (checkHaveConfig(4)) {
            B5IdRefId4 = SharedPreferenceProvider.getUserId(context);
        }
        if (checkHaveConfig(6)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5IdRefId6
        }
        if (checkHaveUtilConfig(1)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//b5HcCurrencyId
        }
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//duration
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_18);//description

        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToGoodsDeliveryProceedings() {
        ArrayList<Integer> comboFormClickType = new ArrayList<>();

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
        comboFormClickType.add(CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE);


        if (checkHaveConfig(1)) {
            comboFormClickType.add(CommonComboClickType.SELL_EMPORIUM_CLICK_TYPE);
        }
        if (checkHaveConfig(2)) {
            comboFormClickType.add(CommonComboClickType.WAREHOUSE_NUMBER_CLICK_TYPE);
        }
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.SELECT_BUYER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_CUSTOMER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_ADDRESS_CLICK_TYPE);
        }
        if (checkHaveConfig(6)) {
            comboFormClickType.add(CommonComboClickType.CENTER_COST_CLICK_TYPE);
        }
        if (checkHaveUtilConfig(1)) {
            comboFormClickType.add(CommonComboClickType.CURRENCY_UNIT_CLICK_TYPE);//b5HcCurrencyId
        }

        comboFormClickType.add(CommonComboClickType.DURATION_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.DESCRIPTION_CLICK_TYPE);

        return comboFormClickType;
    }

    /*
      in this form code
      we need other click type in edit mode
   */
    private ArrayList<Integer> setProperClickRowTypeToGoodsDeliveryProceedingsEditMode() {
        ArrayList<Integer> comboFormClickType = new ArrayList<>();

        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
        comboFormClickType.add(CommonComboClickType.SEARCH_COMPOSITE_CLICK_TYPE);


        if (checkHaveConfig(1)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_29);
        }
        if (checkHaveConfig(2)) {
            comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_30);
        }
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.SELECT_BUYER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_CUSTOMER_CLICK_TYPE);
            comboFormClickType.add(CommonComboClickType.SELECT_ADDRESS_CLICK_TYPE);
        }
        if (checkHaveConfig(6)) {
            comboFormClickType.add(CommonComboClickType.CENTER_COST_CLICK_TYPE);
        }
        if (checkHaveUtilConfig(1)) {
            comboFormClickType.add(CommonComboClickType.CURRENCY_UNIT_CLICK_TYPE);//b5HcCurrencyId
        }

        comboFormClickType.add(CommonComboClickType.DURATION_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.DESCRIPTION_CLICK_TYPE);

        return comboFormClickType;
    }

    private List<ComboFormModel> setProperDataToGoodsDeliveryProceedings() {
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(formDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_trans_number), Commons.DASH), Commons.NULL, false));// we are  in edit mode//when we are not in edit mode, we have not formNumber
        comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));

        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.sell_emporium), SharedPreferenceProvider.getSellEmporiumName(context), Commons.NULL, false));//get default sells Emporium
        }
        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse_code), Commons.NULL, Commons.NULL, false));
        }

        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.buyer_name), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.customer_group), Commons.NULL, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.address), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(6)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveUtilConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.currency_unit), SharedPreferenceProvider.getMonetaryUnitName(context), Commons.NULL, false));
        }

        comboFormModels.add(new ComboFormModel(context.getString(R.string.duration), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), Commons.NULL, Commons.NULL, false));

        return comboFormModels;
    }

    private List<ComboFormModel> setPrimitiveProperDataToGoodsDeliveryProceedings() {


        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(formDate, CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_trans_number), formNumber), Commons.NULL, false));// we are  in edit mode
        comboFormModels.add(new ComboFormModel(Commons.NULL, Commons.NULL, Commons.NULL, false));

        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.sell_emporium), myListBaseNameList.get(0).get(myListBaseIdList.get(0).indexOf(B5IdRefId1) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(0).indexOf(B5IdRefId1)), Commons.NULL, false));
        }

        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.warehouse_code), myListBaseNameList.get(1).get(myListBaseIdList.get(1).indexOf(B5IdRefId2) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(1).indexOf(B5IdRefId2)), Commons.NULL, false));
        }

        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.buyer_name), myListBaseNameList.get(2).get(myListBaseIdList.get(2).indexOf(B5IdRefId3) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(2).indexOf(B5IdRefId3)), Commons.NULL, false));
            //we sure we calculate primitiveCostumerGroupName & primitiveAddressName
            comboFormModels.add(new ComboFormModel(context.getString(R.string.customer_group), primitiveCostumerGroupName, Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.address), primitiveAddressName, Commons.NULL, false));
        }

        if (checkHaveConfig(6)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.center_cost), myListBaseNameList.get(5).get(myListBaseIdList.get(5).indexOf(B5IdRefId6) == -1 ? Commons.ZERO_INT : myListBaseIdList.get(5).indexOf(B5IdRefId6)), Commons.NULL, false));
        }

        if (checkHaveUtilConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.currency_unit), myListUtilNameList.get(0).get(myListUtilIdList.get(0).indexOf(B5HCCurrencyId) == -1 ? Commons.ZERO_INT : myListUtilIdList.get(0).indexOf(B5HCCurrencyId)), Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.duration), makeProperArrayListForDuration().get(duration < MAXIMUM_DURATION_LIMIT && duration != Commons.MINUS_INT ? duration : 0), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), description, Commons.NULL, false));

        return comboFormModels;
    }

    private void checkDataGoodsDeliveryProceedings() {

        if (checkHaveConfig(1) && B5IdRefId1.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELL_EMPORIUM_CLICK_TYPE, new ComboFormModel(context.getString(R.string.sell_emporium), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();
                return;
            }
        }

        if (checkHaveConfig(2) && B5IdRefId2.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.WAREHOUSE_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.warehouse_code), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();
                return;
            }
        }

        if (checkHaveConfig(3) && B5IdRefId3.equals(Commons.NULL_INTEGER)) {
            //after set config 3, address and group were be set automaticly
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_BUYER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.buyer_name), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();
                return;
            }
        }

        if (checkHaveConfig(6) && B5IdRefId6.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.CENTER_COST_CLICK_TYPE, new ComboFormModel(context.getString(R.string.center_cost), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();
                return;
            }
        }

        if (checkHaveUtilConfig(1) && B5HCCurrencyId.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.CURRENCY_UNIT_CLICK_TYPE, new ComboFormModel(context.getString(R.string.currency_unit), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageFillProperValue();
                return;
            }
        }

        if (goodTransDetailsModelArrayList.size() == 0) {
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageNoDetailInGoodTrance();
                return;
            }
        }

        addDisposable(saveDataGoodTransAndChangePurchasableTempAmount());
    }
    //endregion
    //----------------------------------------------------------------------------------------------
}
