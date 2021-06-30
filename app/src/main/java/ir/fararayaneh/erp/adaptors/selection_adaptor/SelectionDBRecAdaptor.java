package ir.fararayaneh.erp.adaptors.selection_adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;
import ir.fararayaneh.erp.IBase.common_base.BaseDBRealmRecycleAdaptor;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.CommonViewTypeReport;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsFormCode;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.ChangeValueModel;
import ir.fararayaneh.erp.data.models.middle.GoodTransDetailsModel;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.SalableGoodsTable;
import ir.fararayaneh.erp.utils.CalculationHelper;
import ir.fararayaneh.erp.utils.EditTextCheckHelper;
import ir.fararayaneh.erp.utils.UUIDHelper;
import ir.fararayaneh.erp.utils.data_handler.AttachJsonCreator;
import ir.fararayaneh.erp.utils.data_handler.CheckRowEditSelectValidation;
import ir.fararayaneh.erp.utils.data_handler.GoodTransDetailJsonHelper;


/**
 * در اینجا داده های هر ردیف ممکن است با توجه
 * به جند جدول ایجاد شود اما در هر صورت
 * یکی از آن جداول، اصلی است و آی دی آنرا در زمان انتخاب شدن نگه میداریم
 * و همچنین مقدارها در کلاس مدل نگه میداریم
 * در نهایت در زمان برگشت دادن پاسخ مناسب، با توجه به هر فرم کد ورودی، دیتای مورد نیاز را ایجاد مینماییم
 * و در قالب یک جیسون لیست استرینگ برگشت میدهیم
 * <p>
 * only need to complete todo
 */
public class SelectionDBRecAdaptor extends BaseDBRealmRecycleAdaptor implements IClickRowSelectionRecHolderListener {

    private Context context;
    private String formCode;
    private boolean multiSelection, selectAll;
    private HashSet<String> listOfSelectedItemId = new HashSet<>();
    private HashMap<String, ChangeValueModel> listOfChangedValueItemID = new HashMap<>();//here we collect amount-2
    private IClickSelectionRecAdaptor iClickSelectionRecAdaptor;

    public void setiClickSelectionRecAdaptor(IClickSelectionRecAdaptor iClickSelectionRecAdaptor) {
        this.iClickSelectionRecAdaptor = iClickSelectionRecAdaptor;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
        if (multiSelection || !selectAll) {
            handleSelectAll();
        }
    }

    private void handleSelectAll() {
        //todo
        switch (formCode) {
            /* @deprecated
            case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
            case CommonsFormCode.PRODUCTION_FORM_CODE:
                handleSelectAllGoods();
                break;*/
            case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                handleSelectAllPurchasableGoods();
                break;
            case CommonsFormCode.BUYER_REQUEST_FORM_CODE:
                handleSelectAllSalableGoods();
                break;
        }
        notifyDataSetChanged();
    }

    public String getSelectionAdaptorResult() {

        HashSet<String> selectedItem = new HashSet<>();
        HashMap<String, ChangeValueModel> changedValueItem = new HashMap<>();

        //because maybe we select some item but , do not set proper value to them
        //if user did not filled amount (amount 2) do not select them as result
        Stream.of(listOfSelectedItemId).forEach(id -> {
            if (listOfChangedValueItemID.containsKey(id)) {
                if (!listOfChangedValueItemID.get(id).getAmount().equals(Commons.NULL_INTEGER)) {
                    changedValueItem.put(id, listOfChangedValueItemID.get(id));
                    selectedItem.add(id);
                }
            }
        });

        return properSelectResult(selectedItem, changedValueItem);
    }

    //todo:
    private String properSelectResult(HashSet<String> selectedItemId, HashMap<String, ChangeValueModel> changedValueItem) {

        if (selectedItemId.isEmpty()) {
            return Commons.NULL;//for Assured that we return cancel result
        } else {
            switch (formCode) {
               /* @deprecated
                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                case CommonsFormCode.PRODUCTION_FORM_CODE:
                    return properResultGoodsForOrder(selectedItemId, changedValueItem);*/
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                    return properResultPurchasableGoodsForOrder(selectedItemId, changedValueItem);
                case CommonsFormCode.BUYER_REQUEST_FORM_CODE:
                    return properResultSalableGoodsForOrder(selectedItemId, changedValueItem);
                default:
                    return Commons.NULL;//for Assured that we return cancel result
            }
        }
    }


    public SelectionDBRecAdaptor(boolean autoUpdate, boolean updateOnModification, @Nullable OrderedRealmCollection[] data, Context context, String formCode, boolean multiSelection) {
        super(autoUpdate, updateOnModification, data);
        this.context = context;
        this.formCode = formCode;
        this.multiSelection = multiSelection;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //todo add other form code (report code)
        switch (formCode) {
            /* @deprecated
            case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                return new SelectionRecHolder(LayoutInflater.from(context).inflate(R.layout.row_report_select_type_1, viewGroup, false), this);*/
            case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                return new SelectionRecHolder(LayoutInflater.from(context).inflate(R.layout.row_report_select_type_1, viewGroup, false), this);
            case CommonsFormCode.BUYER_REQUEST_FORM_CODE:
                return new SelectionRecHolder(LayoutInflater.from(context).inflate(R.layout.row_report_select_type_1, viewGroup, false), this);
            default:
                //by default
                return new SelectionRecHolder(LayoutInflater.from(context).inflate(R.layout.row_report_select_type_1, viewGroup, false), this);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        setRowData(viewHolder);
    }

    private void setRowData(RecyclerView.ViewHolder myViewHolder) {
        //todo:
        switch (formCode) {
            /* @deprecated
            case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                SelectionRecHolder viewHolder = (SelectionRecHolder) myViewHolder;
                setupRowGoodsSelection(viewHolder);
                break;*/
            case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                SelectionRecHolder viewHolder = (SelectionRecHolder) myViewHolder;
                setupRowGoodsDeliverySelection(viewHolder);
                break;
            case CommonsFormCode.BUYER_REQUEST_FORM_CODE:
                SelectionRecHolder viewHolder2 = (SelectionRecHolder) myViewHolder;
                setupRowBuyerRequestSelection(viewHolder2);
                break;
        }
    }

    private void handleRemoveSelectedAccessDeniedObject(boolean isAccessDenied, String id) {
        if (isAccessDenied) {
            listOfSelectedItemId.remove(id);
            listOfChangedValueItemID.remove(id);
        }
    }

    @Override
    protected void customNotifyItemRangeEdited(int startPosition, int length) {
        for (int i = startPosition; i < length; i++) {
            //todo
            switch (formCode) {
                /*
                @deprecated
                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                    GoodsTable goodsTable = ((GoodsTable) Objects.requireNonNull(getItem(0, i)));
                    handleRemoveSelectedAccessDeniedObject(goodsTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), goodsTable.getId());
                    insertedRowIds.add(goodsTable.getId());
                    break;*/
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                    PurchasableGoodsTable purchasableGoodsTable = ((PurchasableGoodsTable) Objects.requireNonNull(getItem(0, i)));
                    handleRemoveSelectedAccessDeniedObject(purchasableGoodsTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), purchasableGoodsTable.getId());
                    editedRowIds.add(purchasableGoodsTable.getId());
                    break;
                case CommonsFormCode.BUYER_REQUEST_FORM_CODE:
                    SalableGoodsTable salableGoodsTable = ((SalableGoodsTable) Objects.requireNonNull(getItem(0, i)));
                    handleRemoveSelectedAccessDeniedObject(salableGoodsTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), salableGoodsTable.getId());
                    editedRowIds.add(salableGoodsTable.getId());
                    break;
            }
        }
    }

    @Override
    protected void customNotifyItemRangeInserted(int startPosition, int length) {
        for (int i = startPosition; i < length; i++) {
            //todo
            switch (formCode) {
                /*
                @deprecated
                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                    GoodsTable goodsTable = ((GoodsTable) Objects.requireNonNull(getItem(0, i)));
                    handleRemoveSelectedAccessDeniedObject(goodsTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), goodsTable.getId());
                    insertedRowIds.add(goodsTable.getId());
                    break;*/
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                    PurchasableGoodsTable purchasableGoodsTable = ((PurchasableGoodsTable) Objects.requireNonNull(getItem(0, i)));
                    handleRemoveSelectedAccessDeniedObject(purchasableGoodsTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), purchasableGoodsTable.getId());
                    insertedRowIds.add(purchasableGoodsTable.getId());
                    break;
                case CommonsFormCode.BUYER_REQUEST_FORM_CODE:
                    SalableGoodsTable salableGoodsTable = ((SalableGoodsTable) Objects.requireNonNull(getItem(0, i)));
                    handleRemoveSelectedAccessDeniedObject(salableGoodsTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), salableGoodsTable.getId());
                    insertedRowIds.add(salableGoodsTable.getId());
                    break;
            }
        }
    }

    @Override
    protected void customNotifyUserForNewRow(int position) {
        if (iClickSelectionRecAdaptor != null) {
            iClickSelectionRecAdaptor.notifyNewRow(position);
        }
    }

    @Override
    protected void customNotifyUserForChangeRow(int position) {
        if (iClickSelectionRecAdaptor != null) {
            iClickSelectionRecAdaptor.notifyEditedRow(position);
        }
    }

    @Override
    public void clickCheckBox(String selectedId, boolean isChecked) {
        manageSelectionList(selectedId, isChecked);
    }

    @Override
    public void PlusMinusValue(String selectedId, String finalValue) {
        ChangeValueModel changeValueModel;

        if (listOfChangedValueItemID.containsKey(selectedId)) {
            changeValueModel = listOfChangedValueItemID.get(selectedId);
        } else {
            changeValueModel = new ChangeValueModel(context, selectedId);
        }
        changeValueModel.setAmount(finalValue);
        listOfChangedValueItemID.put(selectedId, changeValueModel);
    }

    @Override
    public void PlusMinusCurrency(String selectedId, String finalValue) {
        ChangeValueModel changeValueModel;
        if (listOfChangedValueItemID.containsKey(selectedId)) {
            changeValueModel = listOfChangedValueItemID.get(selectedId);
        } else {
            changeValueModel = new ChangeValueModel(context, selectedId);
        }
        changeValueModel.setCurrency(finalValue);
        listOfChangedValueItemID.put(selectedId, changeValueModel);
    }

    @Override
    public void PlusMinusTaxValue(String selectedId, String finalValue) {
        ChangeValueModel changeValueModel;
        if (listOfChangedValueItemID.containsKey(selectedId)) {
            changeValueModel = listOfChangedValueItemID.get(selectedId);
        } else {
            changeValueModel = new ChangeValueModel(context, selectedId);
        }
        changeValueModel.setTaxValue(finalValue);
        listOfChangedValueItemID.put(selectedId, changeValueModel);
    }

    @Override
    public void PlusMinusDiscountValue(String selectedId, String finalValue) {
        ChangeValueModel changeValueModel;
        if (listOfChangedValueItemID.containsKey(selectedId)) {
            changeValueModel = listOfChangedValueItemID.get(selectedId);
        } else {
            changeValueModel = new ChangeValueModel(context, selectedId);
        }
        changeValueModel.setDiscountValue(finalValue);
        listOfChangedValueItemID.put(selectedId, changeValueModel);
    }

    @Override
    public void seeAttach(int position) {
        if (iClickSelectionRecAdaptor != null) {
            iClickSelectionRecAdaptor.clickAttachment( (IModels) Objects.requireNonNull(getItem(0, position)));
        }
    }

    @Override
    public void showDialog(ArrayList<String> stringArrayList) {
        if (iClickSelectionRecAdaptor != null) {
            iClickSelectionRecAdaptor.showDialog(stringArrayList);
        }
    }

    /**
     * if we have id ==> row should be deselected else should be selected
     */
    private void manageSelectionList(String id, boolean isChecked) {
        if (isChecked) {
            listOfSelectedItemId.add(id);
            manageOnlyOneItemBeSelected(id);
        } else {
            listOfSelectedItemId.remove(id);
        }
    }

    private void manageOnlyOneItemBeSelected(String id) {
        if (!multiSelection) {
            Stream.of(listOfSelectedItemId).forEach(ids -> {
                if (!id.equals(ids)) {
                    listOfSelectedItemId.remove(id);
                }
            });
            notifyDataSetChanged();
        }
    }

    private String amountManager(String id) {
        return listOfChangedValueItemID.containsKey(id) ? listOfChangedValueItemID.get(id).getAmount() : Commons.ZERO_STRING;
    }

    private String currencyManager(String id) {
        return listOfChangedValueItemID.containsKey(id) ? listOfChangedValueItemID.get(id).getCurrency() : Commons.ZERO_STRING;
    }

    private String taxManager(String id) {
        return listOfChangedValueItemID.containsKey(id) ? listOfChangedValueItemID.get(id).getTaxValue() : Commons.ZERO_STRING;
    }

    private String discountManager(String id) {
        return listOfChangedValueItemID.containsKey(id) ? listOfChangedValueItemID.get(id).getDiscountValue() : Commons.ZERO_STRING;
    }

//-----------------------------------------------------------------------------------------------------

    /**
     * only first data Collection have the same order of  recycler view position
     * tables are : Purchasable
     */
    private void setupRowGoodsDeliverySelection(SelectionRecHolder viewHolder) {

        if (getItem(0, viewHolder.getAdapterPosition()) != null) {
            PurchasableGoodsTable purchasableGoodsTable = (PurchasableGoodsTable) getItem(0, viewHolder.getAdapterPosition());
            int viewType = setupRowReportViewType(purchasableGoodsTable.getGuid(), purchasableGoodsTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), false);
            viewHolder.setRowData(
                    Objects.requireNonNull(purchasableGoodsTable).getId(),
                    salablePurchasableTextMaker(purchasableGoodsTable.getGoodName(), purchasableGoodsTable.getTempAmount2(), purchasableGoodsTable.getCode(), purchasableGoodsTable.getUnitName2(), purchasableGoodsTable.getTechInfo()),
                    true,
                    AttachJsonCreator.getFirstAttachName(purchasableGoodsTable.getAttach()),
                    true,
                    amountManager(purchasableGoodsTable.getId()),
                    viewType,
                    !CheckRowEditSelectValidation.isRowSelectAble(viewType),
                    listOfSelectedItemId.contains(purchasableGoodsTable.getId()),
                    true,
                    currencyManager(purchasableGoodsTable.getId()),
                    true,
                    discountManager(purchasableGoodsTable.getId()),
                    true,
                    taxManager(purchasableGoodsTable.getId()),
                    false,
                    Commons.SPACE

            );
        }
    }

    private void setupRowBuyerRequestSelection(SelectionRecHolder viewHolder) {

        if (getItem(0, viewHolder.getAdapterPosition()) != null) {
            SalableGoodsTable salableGoodsTable = (SalableGoodsTable) getItem(0, viewHolder.getAdapterPosition());

            int viewType = setupRowReportViewType(salableGoodsTable.getGuid(), salableGoodsTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), false);
            viewHolder.setRowData(
                    Objects.requireNonNull(salableGoodsTable).getId(),
                    salablePurchasableTextMaker(salableGoodsTable.getGoodName(), salableGoodsTable.getTempAvailableAmount2(), salableGoodsTable.getGoodCode(), salableGoodsTable.getUnitName2(), salableGoodsTable.getTechInfo()),
                    false,
                    Commons.NULL,
                    true,
                    amountManager(salableGoodsTable.getId()),
                    viewType,
                    !CheckRowEditSelectValidation.isRowSelectAble(viewType),
                    listOfSelectedItemId.contains(salableGoodsTable.getId()),
                    false,
                    Commons.SPACE,
                    false,
                    Commons.SPACE,
                    false,
                    Commons.SPACE,
                    true,
                    discountManager(salableGoodsTable.getId())
            );
        }
    }

    /**
     * only first data Collection have the same order of  recycler view position
     * tables are : GoodsTable
     */
    /*
    /*
    deprecated if need again , check all code !

    private void setupRowGoodsSelection(SelectionRecHolder viewHolder) {

       if (getItem(0, viewHolder.getAdapterPosition()) != null) {
            GoodsTable goodsTable = (GoodsTable) getItem(0, viewHolder.getAdapterPosition());
            int viewType = setupRowReportViewType(goodsTable.getGuid(), goodsTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), false);
            Log.i("arash", "setupRowGoodsSelection: " + viewHolder.getAdapterPosition());
            Log.i("arash", "setupRowGoodsSelection***: " + goodsTable.getId());
            viewHolder.setRowData(
                    Objects.requireNonNull(goodsTable).getId(),
                    goodsTextMaker(Objects.requireNonNull(goodsTable).getName(), goodsTable.getCode(), goodsTable.getUnitName(), goodsTable.getBrandName(), goodsTable.getLatinName(), goodsTable.getTechInfo(), goodsTable.getNationalityCode(), goodsTable.getWidth(), goodsTable.getHeight(), goodsTable.getLength(), goodsTable.getWeight(), goodsTable.getSerial()),
                    true,
                    AttachJsonCreator.getFirstAttachName(goodsTable.getAttach()),
                    true,
                    amountManager(goodsTable.getId()),
                    viewType,
                    !CheckRowEditSelectValidation.isRowSelectAble(viewType),
                    listOfSelectedItemId.contains(goodsTable.getId()),
                    false,
                    Commons.SPACE,
                    false,
                    Commons.SPACE,
                    false,
                    Commons.SPACE
            );
        }
    }
*/
/*
    deprecated if need again , check all code !
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
    */
    private String salablePurchasableTextMaker(String goodName, String availableAmount, String code, String unitName,
                                               String techInfo
    ) {
        return EditTextCheckHelper.concatHandler(
                goodName,
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.max_amount), availableAmount),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_code), code),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.good_main_unit_name), unitName),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.tech_info), techInfo)
        );
    }


    /*
    deprecated if need again , check all code !
    private void handleSelectAllGoods() {
        if (selectAll) {
            if (getData(0) != null) {
                int tableSize = Objects.requireNonNull(getData(0)).size();
                for (int i = 0; i < tableSize; i++) {
                    GoodsTable goodsTable = (GoodsTable) getItem(0, i);
                    //in goods we have not deleted activity kind, only check access denied
                    if (CheckRowEditSelectValidation.isRowSelectAble(Objects.requireNonNull(goodsTable).getSyncState().equals(CommonSyncState.ACCESS_DENIED) ? CommonViewTypeReport.ACCESS_DENIED_VIEW_TYPE : CommonViewTypeReport.NORMAL_VIEW_TYPE)) {
                        listOfSelectedItemId.add(Objects.requireNonNull(goodsTable).getId());
                    }
                }
            }
        } else {
            listOfSelectedItemId.clear();
        }
    }*/
    private void handleSelectAllPurchasableGoods() {
        if (selectAll) {
            if (getData(0) != null) {
                int tableSize = Objects.requireNonNull(getData(0)).size();
                for (int i = 0; i < tableSize; i++) {
                    PurchasableGoodsTable purchasableGoodsTable = (PurchasableGoodsTable) getItem(0, i);
                    //in goods we have not deleted activity kind, only check access denied
                    if (CheckRowEditSelectValidation.isRowSelectAble(Objects.requireNonNull(purchasableGoodsTable).getSyncState().equals(CommonSyncState.ACCESS_DENIED) ? CommonViewTypeReport.ACCESS_DENIED_VIEW_TYPE : CommonViewTypeReport.NORMAL_VIEW_TYPE)) {
                        listOfSelectedItemId.add(Objects.requireNonNull(purchasableGoodsTable).getId());
                    }
                }
            }
        } else {
            listOfSelectedItemId.clear();
        }
    }

    private void handleSelectAllSalableGoods() {
        if (selectAll) {
            if (getData(0) != null) {
                int tableSize = Objects.requireNonNull(getData(0)).size();
                for (int i = 0; i < tableSize; i++) {
                    SalableGoodsTable salableGoodsTable = (SalableGoodsTable) getItem(0, i);
                    //in goods we have not deleted activity kind, only check access denied
                    if (CheckRowEditSelectValidation.isRowSelectAble(Objects.requireNonNull(salableGoodsTable).getSyncState().equals(CommonSyncState.ACCESS_DENIED) ? CommonViewTypeReport.ACCESS_DENIED_VIEW_TYPE : CommonViewTypeReport.NORMAL_VIEW_TYPE)) {
                        listOfSelectedItemId.add(Objects.requireNonNull(salableGoodsTable).getId());
                    }
                }
            }
        } else {
            listOfSelectedItemId.clear();
        }
    }



    /*
    //deprecated if need to use, check manner again

    private GoodsTable finalGoodsTable;
    private ArrayList<GoodSUOMModel> finalGoodSUOMModelArrayList;
    private String properResultGoodsForOrder(HashSet<String> selectedItemId, HashMap<String, ChangeValueModel> changedValueItem) {

        ArrayList<GoodTransDetailsModel> goodTransDetailsModelArrayList = new ArrayList<>();
        RealmResults<GoodsTable> goodsTableRealmResults = (RealmResults<GoodsTable>) getData(0);

        if (goodsTableRealmResults != null) {
            Stream.of(selectedItemId).forEach(id -> {
                finalGoodsTable = goodsTableRealmResults.where().equalTo(CommonColumnName.ID, id).findFirst();
                finalGoodSUOMModelArrayList = GoodSUOMJsonHelper.getGoodSUOMArray(finalGoodsTable.getGoodSUOMList());
                goodTransDetailsModelArrayList.add(new GoodTransDetailsModel(
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.PRIMITIVE_GOOD_COLUMN_NUMBER_VALUE,
                        UUIDHelper.getProperUUID(),
                        finalGoodsTable.getId(),
                        finalGoodSUOMModelArrayList.get(0).getC5UnitId(),//get default unitId
                        finalGoodSUOMModelArrayList.get(0).getLength(),//get default unitId
                        finalGoodSUOMModelArrayList.get(0).getWidth(),//get default unitId
                        finalGoodSUOMModelArrayList.get(0).getHeight(),//get default unitId
                        finalGoodSUOMModelArrayList.get(0).getAlloy(),//get default unitId
                        changedValueItem.get(finalGoodsTable.getId()).getAmount(),//all amount is amount2(get default unitId)
                        changedValueItem.get(finalGoodsTable.getId()).getAmount(),//all amount is amount2(get default unitId)
                        changedValueItem.get(finalGoodsTable.getId()).getAmount(),//all amount is amount2(get default unitId)
                        changedValueItem.get(finalGoodsTable.getId()).getAmount(),//all amount is amount2
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL_INTEGER,
                        Commons.NULL,
                        Commons.NULL_INTEGER
                ));
            });
            return GoodTransDetailJsonHelper.goodTransDetailsJson(goodTransDetailsModelArrayList);
        } else {
            return Commons.NULL;
        }
    }*/

//--------------------------------------------------------------------------------------------------
    //here we sure we have minimum one selected row with amount

    private PurchasableGoodsTable finalPurchasableGoodsTable;
    private SalableGoodsTable finalSalableGoodsTable;
    private ChangeValueModel finalChangeValueModel;
    private boolean stackOverFlowAmount = false;
    private String properResultPurchasableGoodsForOrder(HashSet<String> selectedItemId, HashMap<String, ChangeValueModel> changedValueItem) {
        //here we sure we have minimum one selected row with amount
        ArrayList<GoodTransDetailsModel> goodTransDetailsModelArrayList = new ArrayList<>();
        RealmResults<PurchasableGoodsTable> purchasableGoodsTableRealmResults = (RealmResults<PurchasableGoodsTable>) getData(0);
        if (purchasableGoodsTableRealmResults != null) {
            Stream.of(selectedItemId).forEach(id -> {
                finalPurchasableGoodsTable = purchasableGoodsTableRealmResults.where().equalTo(CommonColumnName.ID, id).findFirst();
                finalChangeValueModel = changedValueItem.get(finalPurchasableGoodsTable.getId());

                //check stack over flow
                if(CalculationHelper.isNegativeValue(CalculationHelper.minusAnyAndRoundNonMoneyValue(finalPurchasableGoodsTable.getTempAmount2(),finalChangeValueModel.getAmount(),context))){
                    stackOverFlowAmount =true;
                    return ;
                }


                goodTransDetailsModelArrayList.add(
                        new GoodTransDetailsModel(
                                Commons.NULL_INTEGER,
                                finalPurchasableGoodsTable.getId(),
                                finalPurchasableGoodsTable.getGoodColumnNumber(),
                                UUIDHelper.getProperUUID(),
                                finalPurchasableGoodsTable.getGoodId(),
                                finalPurchasableGoodsTable.getUnitId(),
                                finalPurchasableGoodsTable.getLength(),
                                finalPurchasableGoodsTable.getWidth(),
                                finalPurchasableGoodsTable.getHeight(),
                                finalPurchasableGoodsTable.getAlloy(),
                                Commons.NULL_INTEGER,//no need in this form code
                                Commons.NULL_INTEGER,//no need in this form code
                                CalculationHelper.multipleAnyDividedAnyAndRoundNonMoneyValue(finalChangeValueModel.getAmount(), finalPurchasableGoodsTable.getNumerator(), finalPurchasableGoodsTable.getDenominator(), context),
                                finalChangeValueModel.getAmount(),
                                finalChangeValueModel.getCurrency(),
                                CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(finalChangeValueModel.getCurrency(), finalPurchasableGoodsTable.getNumerator(), finalPurchasableGoodsTable.getDenominator(), context),
                                CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(finalChangeValueModel.getAmount(), finalChangeValueModel.getCurrency(), Commons.PLUS_STRING, context),
                                finalChangeValueModel.getDiscountValue(),
                                CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(
                                        CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(finalChangeValueModel.getCurrency(), finalPurchasableGoodsTable.getNumerator(), finalPurchasableGoodsTable.getDenominator(), context),
                                        finalChangeValueModel.getDiscountValue(),
                                        Commons.HUNDRED_STRING, context),
                                Commons.ZERO_STRING,//by default
                                finalChangeValueModel.getTaxValue(),
                                CalculationHelper.currencyTaxValueCalculation(
                                        finalChangeValueModel.getDiscountValue(),
                                        CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(finalChangeValueModel.getAmount(), finalChangeValueModel.getCurrency(), Commons.PLUS_STRING, context),
                                        finalChangeValueModel.getTaxValue(),
                                        Commons.ZERO_STRING
                                        , context),
                                CalculationHelper.currencyNetValueCalculation(finalChangeValueModel.getDiscountValue(),
                                        CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(finalChangeValueModel.getAmount(), finalChangeValueModel.getCurrency(), Commons.PLUS_STRING, context),
                                        finalChangeValueModel.getTaxValue(),
                                        Commons.ZERO_STRING,
                                        context),
                                CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(
                                        CalculationHelper.currencyNetValueCalculation(finalChangeValueModel.getDiscountValue(),
                                                CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(finalChangeValueModel.getAmount(), finalChangeValueModel.getCurrency(), Commons.PLUS_STRING, context),
                                                finalChangeValueModel.getTaxValue(),
                                                Commons.ZERO_STRING,
                                                context),
                                        Commons.PLUS_STRING,
                                        finalChangeValueModel.getAmount(),
                                        context),
                                finalPurchasableGoodsTable.getB5IdRefId8(),
                                finalPurchasableGoodsTable.getB5IdRefId9(),
                                finalPurchasableGoodsTable.getB5IdRefId10(),
                                finalPurchasableGoodsTable.getB5IdRefId15(),
                                Commons.NULL,
                                Commons.NULL_INTEGER
                        ));
            });
            if(stackOverFlowAmount){
                stackOverFlowAmount=false;
                return Commons.NULL_INTEGER;
            }
            return GoodTransDetailJsonHelper.goodTransDetailsJson(goodTransDetailsModelArrayList);
        } else {
            return Commons.NULL;
        }

    }

    private String properResultSalableGoodsForOrder(HashSet<String> selectedItemId, HashMap<String, ChangeValueModel> changedValueItem) {
        ArrayList<GoodTransDetailsModel> goodTransDetailsModelArrayList = new ArrayList<>();
        RealmResults<SalableGoodsTable> salableGoodsTableRealmResults = (RealmResults<SalableGoodsTable>) getData(0);
        if (salableGoodsTableRealmResults != null) {
            Stream.of(selectedItemId).forEach(id -> {
                finalSalableGoodsTable = salableGoodsTableRealmResults.where().equalTo(CommonColumnName.ID, id).findFirst();
                finalChangeValueModel = changedValueItem.get(finalSalableGoodsTable.getId());
                //check stack over flow
                if(CalculationHelper.isNegativeValue(CalculationHelper.minusAnyAndRoundNonMoneyValue(finalSalableGoodsTable.getTempAvailableAmount2(),finalChangeValueModel.getAmount(),context))){
                    stackOverFlowAmount =true;
                    return ;
                }
                goodTransDetailsModelArrayList.add(
                        new GoodTransDetailsModel(
                                Commons.NULL_INTEGER,
                                finalSalableGoodsTable.getId(),
                                finalSalableGoodsTable.getGoodColumnNumber(),
                                UUIDHelper.getProperUUID(),
                                finalSalableGoodsTable.getGoodId(),
                                finalSalableGoodsTable.getUnitId2(),
                                finalSalableGoodsTable.getLength(),
                                finalSalableGoodsTable.getWidth(),
                                finalSalableGoodsTable.getHeight(),
                                finalSalableGoodsTable.getAlloy(),
                                CalculationHelper.multipleAnyDividedAnyAndRoundNonMoneyValue(finalChangeValueModel.getAmount(), finalSalableGoodsTable.getNumerator(), finalSalableGoodsTable.getDenominator(), context),
                                finalChangeValueModel.getAmount(),
                                CalculationHelper.multipleAnyDividedAnyAndRoundNonMoneyValue(finalChangeValueModel.getAmount(), finalSalableGoodsTable.getNumerator(), finalSalableGoodsTable.getDenominator(), context),
                                finalChangeValueModel.getAmount(),
                                finalSalableGoodsTable.getUnitPrice(),
                                CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(finalSalableGoodsTable.getUnitPrice(), finalSalableGoodsTable.getNumerator(), finalSalableGoodsTable.getDenominator(), context),
                                CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(finalChangeValueModel.getAmount(), finalSalableGoodsTable.getUnitPrice(), Commons.PLUS_STRING, context),
                                finalSalableGoodsTable.getDiscountPercentage(),
                                CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(
                                        CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(finalSalableGoodsTable.getUnitPrice(), finalSalableGoodsTable.getNumerator(), finalSalableGoodsTable.getDenominator(), context),
                                        finalSalableGoodsTable.getDiscountPercentage(),
                                        Commons.HUNDRED_STRING, context),
                                finalChangeValueModel.getDiscountValue(),//by default, discount in this form code is discount2
                                finalSalableGoodsTable.getTaxPercentage(),
                                CalculationHelper.currencyTaxValueCalculation(
                                        finalSalableGoodsTable.getDiscountPercentage(),
                                        CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(
                                                finalChangeValueModel.getAmount(),
                                                finalSalableGoodsTable.getUnitPrice(),
                                                Commons.PLUS_STRING, context),
                                        finalSalableGoodsTable.getTaxPercentage(),
                                        finalChangeValueModel.getDiscountValue(),//by default, discount in this form code is discount2
                                        context),
                                CalculationHelper.currencyNetValueCalculation(
                                        finalSalableGoodsTable.getDiscountPercentage(),
                                        CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(
                                                finalChangeValueModel.getAmount(),
                                                finalSalableGoodsTable.getUnitPrice(),
                                                Commons.PLUS_STRING, context),
                                        finalSalableGoodsTable.getTaxPercentage(),
                                        finalChangeValueModel.getDiscountValue(),//by default, discount in this form code is discount2
                                        context),
                                CalculationHelper.multipleAnyDividedAnyAndRoundNonMoneyValue(
                                        CalculationHelper.currencyNetValueCalculation(
                                                finalSalableGoodsTable.getDiscountPercentage(),
                                                CalculationHelper.multipleAnyDividedAnyAndRoundMoneyValue(
                                                        finalChangeValueModel.getAmount(),
                                                        finalSalableGoodsTable.getUnitPrice(),
                                                        Commons.PLUS_STRING, context),
                                                finalChangeValueModel.getTaxValue(),
                                                finalChangeValueModel.getDiscountValue(),//by default, discount in this form code is discount2
                                                context),
                                        Commons.PLUS_STRING,
                                        finalChangeValueModel.getAmount(),
                                        context),
                                Commons.NULL_INTEGER,
                                Commons.NULL_INTEGER,
                                Commons.NULL_INTEGER,
                                Commons.NULL_INTEGER,
                                Commons.NULL,
                                Commons.NULL_INTEGER
                        ));
            });
            if(stackOverFlowAmount){
                stackOverFlowAmount=false;
                return Commons.NULL_INTEGER;
            }
            return GoodTransDetailJsonHelper.goodTransDetailsJson(goodTransDetailsModelArrayList);
        } else {
            return Commons.NULL;
        }
    }
//--------------------------------------------------------------------------------------------------
    @Override
    protected void injectNewDataCollectionWereDone() {
        // TODO: 21/10/19
        //چون در onbind تیبل ها ساخته میشود بنابراین نیازی به این فانکشن در اینجا نیست مگر به دلیل کاهش پرفورمنس (به رفتار چت لیست نگاه شود)
    }

    @Override
    protected void notifyNonMainItemAnyChanged(Object collection, int positionOfChangedTable, int startPosition, int length) {
        //todo  آیا لازم است که رفتار جداگانه ای به ازای جداول غیر اصلی نشان داد ؟
        notifyDataSetChanged();
    }





}
