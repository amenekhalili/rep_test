package ir.fararayaneh.erp.adaptors.reports_adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.IBase.common_base.BaseDBRealmRecycleAdaptor;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.CommonTimeZone;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsFormCode;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.data.models.middle.GoodSUOMModel;
import ir.fararayaneh.erp.data.models.tables.BaseCodingTable;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CartableTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListDetailTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListMasterTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.GoodTranceTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TaskTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TimeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WeighingTable;
import ir.fararayaneh.erp.utils.EditTextCheckHelper;
import ir.fararayaneh.erp.utils.data_handler.AttachJsonCreator;
import ir.fararayaneh.erp.utils.data_handler.GoodSUOMJsonHelper;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;


/**
 * only need to complete todo
 */
public class ReportsDBRecAdaptor extends BaseDBRealmRecycleAdaptor implements IClickRowReportsRecHolderListener {

    private Context context;
    private String formCode;
    private IClickReportsRecAdaptor clickReportsRecAdaptor;
    private GoodTranceTable goodTranceTable;//for prevent many object create in onBinding
    private OrderedRealmCollection<BaseCodingTable> baseCodingTables;//for prevent many object create in onBinding
    private TaskTable taskTable;//for prevent many object create in onBinding

    public void setClickReportsRecAdaptor(IClickReportsRecAdaptor clickReportsRecAdaptor) {
        this.clickReportsRecAdaptor = clickReportsRecAdaptor;
    }

    public ReportsDBRecAdaptor(boolean autoUpdate, String formCode, Context context, boolean startProcess, @Nullable OrderedRealmCollection[] data) {
        super(startProcess, autoUpdate, data);
        this.formCode = formCode;
        this.context = context;
    }

    @NonNull
    @Override
    public BaseRecHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //todo: add other form code (report code)
        switch (formCode) {
            case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
            case CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
            case CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
            case CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE:
            case CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
            case CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE:
            case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
            case CommonsFormCode.PRODUCTION_REPORT_FORM_CODE:
                return new ReportsRecHolderType3(LayoutInflater.from(context).inflate(R.layout.row_report_type_3, viewGroup, false), this);

            case CommonsFormCode.FUEL_MASTER_REPORT_FORM_CODE:
                return new ReportsRecHolderType4(LayoutInflater.from(context).inflate(R.layout.row_report_type_4, viewGroup, false), this);
            case CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE:
                return new ReportsRecHolderType5(LayoutInflater.from(context).inflate(R.layout.row_report_type_5, viewGroup, false), this);
            default:
                //by default
                return new ReportsRecHolderType2(LayoutInflater.from(context).inflate(R.layout.row_report_type_2, viewGroup, false), this);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        setRowData(viewHolder);
    }


    private void setRowData(RecyclerView.ViewHolder myViewHolder) {
        //todo:
        switch (formCode) {
            case CommonsFormCode.CARTABLE_REPORT_FORM_CODE:
                ReportsRecHolderType2 cartableHolder = (ReportsRecHolderType2) myViewHolder;
                setupRowCartable(cartableHolder);
                break;
            case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
            case CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
            case CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
            case CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE:
            case CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
            case CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE:
            case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
            case CommonsFormCode.PRODUCTION_REPORT_FORM_CODE:
                ReportsRecHolderType3 reportsRecHolderType3 = (ReportsRecHolderType3) myViewHolder;
                setupRowGoodTrans(reportsRecHolderType3);
                break;
            case CommonsFormCode.GOODS_REPORT_FORM_CODE:
                ReportsRecHolderType2 goodHolder = (ReportsRecHolderType2) myViewHolder;
                setupRowGoods(goodHolder);
                break;
            case CommonsFormCode.TASK_REPORT_FORM_CODE:
                ReportsRecHolderType2 taskHolder = (ReportsRecHolderType2) myViewHolder;
                setupRowTask(taskHolder);
                break;
            case CommonsFormCode.TIME_REPORT_FORM_CODE:
                ReportsRecHolderType2 timeHolder = (ReportsRecHolderType2) myViewHolder;
                setupRowTime(timeHolder);
                break;
            case CommonsFormCode.WEIGHTING_REPORT_FORM_CODE:
                ReportsRecHolderType2 weightingTimeHolder = (ReportsRecHolderType2) myViewHolder;
                setupRowWeighing(weightingTimeHolder);
                break;
            case CommonsFormCode.FUEL_MASTER_REPORT_FORM_CODE:
                ReportsRecHolderType4 fuelMasterHolder = (ReportsRecHolderType4) myViewHolder;
                setupRowFuelMaster(fuelMasterHolder);
                break;
            case CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE:
                ReportsRecHolderType5 fuelDetailHolder = (ReportsRecHolderType5) myViewHolder;
                setupRowFuelDetail(fuelDetailHolder);
                break;
        }
    }




    @Override
    public void clickRow(int position, int witchClick, boolean clickOnNormalViewType) {
        if (clickReportsRecAdaptor != null) {
            //todo
            switch (formCode) {
                case CommonsFormCode.CARTABLE_REPORT_FORM_CODE:
                    clickReportsRecAdaptor.clickConfigs(((CartableTable) Objects.requireNonNull(getItem(0, position))), witchClick, clickOnNormalViewType);
                    break;
                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
                case CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
                case CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
                case CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE:
                case CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
                case CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE:
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
                case CommonsFormCode.PRODUCTION_REPORT_FORM_CODE:
                    clickReportsRecAdaptor.clickConfigs(((GoodTranceTable) Objects.requireNonNull(getItem(0, position))), witchClick, clickOnNormalViewType);
                    break;
                case CommonsFormCode.GOODS_REPORT_FORM_CODE:
                    clickReportsRecAdaptor.clickConfigs(((GoodsTable) Objects.requireNonNull(getItem(0, position))), witchClick, clickOnNormalViewType);
                    break;
                case CommonsFormCode.TASK_REPORT_FORM_CODE:
                    clickReportsRecAdaptor.clickConfigs(((TaskTable) Objects.requireNonNull(getItem(0, position))), witchClick, clickOnNormalViewType);
                    break;
                case CommonsFormCode.TIME_REPORT_FORM_CODE:
                    clickReportsRecAdaptor.clickConfigs(((TimeTable) Objects.requireNonNull(getItem(0, position))), witchClick, clickOnNormalViewType);
                    break;
                case CommonsFormCode.WEIGHTING_REPORT_FORM_CODE:
                    clickReportsRecAdaptor.clickConfigs(((WeighingTable) Objects.requireNonNull(getItem(0, position))), witchClick, clickOnNormalViewType);
                    break;
                case CommonsFormCode.FUEL_MASTER_REPORT_FORM_CODE:
                    clickReportsRecAdaptor.clickConfigs(((FuelListMasterTable) Objects.requireNonNull(getItem(0, position))), witchClick, clickOnNormalViewType);
                    break;
                case CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE:
                    clickReportsRecAdaptor.clickConfigs(((FuelListDetailTable) Objects.requireNonNull(getItem(0, position))), witchClick, clickOnNormalViewType);
                    break;
            }
        }
    }

    /**
     * because new values maybe edited after insert, and be in two list(editedRowIds,insertedRowIds)
     * we check edited rows before inserted rows
     */

    @Override
    protected void customNotifyItemRangeEdited(int startPosition, int length) {
        for (int i = startPosition; i < length; i++) {
            //todo
            switch (formCode) {
                case CommonsFormCode.CARTABLE_REPORT_FORM_CODE:
                    editedRowIds.add(((CartableTable) Objects.requireNonNull(getItem(0, i))).getGuid());
                    break;
                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
                case CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
                case CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
                case CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE:
                case CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
                case CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE:
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
                case CommonsFormCode.PRODUCTION_REPORT_FORM_CODE:
                    editedRowIds.add(((GoodTranceTable) Objects.requireNonNull(getItem(0, i))).getGuid());
                    break;
                case CommonsFormCode.GOODS_REPORT_FORM_CODE:
                    editedRowIds.add(((GoodsTable) Objects.requireNonNull(getItem(0, i))).getGuid());
                    break;
                case CommonsFormCode.TASK_REPORT_FORM_CODE:
                    editedRowIds.add(((TaskTable) Objects.requireNonNull(getItem(0, i))).getGuid());
                    break;
                case CommonsFormCode.TIME_REPORT_FORM_CODE:
                    editedRowIds.add(((TimeTable) Objects.requireNonNull(getItem(0, i))).getGuid());
                    break;
                case CommonsFormCode.WEIGHTING_REPORT_FORM_CODE:
                    editedRowIds.add(((WeighingTable) Objects.requireNonNull(getItem(0, i))).getGuid());
                    break;
                case CommonsFormCode.FUEL_MASTER_REPORT_FORM_CODE:
                    editedRowIds.add(((FuelListMasterTable) Objects.requireNonNull(getItem(0, i))).getId());
                    break;
                case CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE:
                    editedRowIds.add(((FuelListDetailTable) Objects.requireNonNull(getItem(0, i))).getId());
                    break;
            }
        }
    }

    @Override
    protected void customNotifyItemRangeInserted(int startPosition, int length) {
        for (int i = startPosition; i < length; i++) {
            //todo
            switch (formCode) {
                case CommonsFormCode.CARTABLE_REPORT_FORM_CODE:
                    insertedRowIds.add(((CartableTable) Objects.requireNonNull(getItem(0, i))).getGuid());
                    break;
                case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
                case CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
                case CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
                case CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE:
                case CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
                case CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE:
                case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
                case CommonsFormCode.PRODUCTION_REPORT_FORM_CODE:
                    insertedRowIds.add(((GoodTranceTable) Objects.requireNonNull(getItem(0, i))).getGuid());
                    break;
                case CommonsFormCode.GOODS_REPORT_FORM_CODE:
                    insertedRowIds.add(((GoodsTable) Objects.requireNonNull(getItem(0, i))).getGuid());
                    break;
                case CommonsFormCode.TASK_REPORT_FORM_CODE:
                    insertedRowIds.add(((TaskTable) Objects.requireNonNull(getItem(0, i))).getGuid());
                    break;
                case CommonsFormCode.TIME_REPORT_FORM_CODE:
                    insertedRowIds.add(((TimeTable) Objects.requireNonNull(getItem(0, i))).getGuid());
                    break;
                case CommonsFormCode.WEIGHTING_REPORT_FORM_CODE:
                    insertedRowIds.add(((WeighingTable) Objects.requireNonNull(getItem(0, i))).getGuid());
                    break;
                case CommonsFormCode.FUEL_MASTER_REPORT_FORM_CODE:
                    insertedRowIds.add(((FuelListMasterTable) Objects.requireNonNull(getItem(0, i))).getId());
                    break;
                case CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE:
                    insertedRowIds.add(((FuelListDetailTable) Objects.requireNonNull(getItem(0, i))).getId());
                    break;
            }
        }

    }

    @Override
    protected void customNotifyUserForNewRow(int position) {
        if (clickReportsRecAdaptor != null) {
            clickReportsRecAdaptor.notifyNewRow(position);
        }
    }

    @Override
    protected void customNotifyUserForChangeRow(int position) {
        if (clickReportsRecAdaptor != null) {
            clickReportsRecAdaptor.notifyEditedRow(position);
        }
    }

    @Override
    protected void injectNewDataCollectionWereDone() {
        // TODO: 21/10/19
        // چون در onbind تیبل ها ساخته میشود بنابراین نیازی به این فانکشن در اینجا نیست مگر به دلیل کاهش پرفورمنس از آن استفاده کنیم
    }

    @Override
    protected void notifyNonMainItemAnyChanged(Object collection, int positionOfChangedTable, int startPosition, int length) {
        //todo  آیا لازم است که رفتار جداگانه ای به ازای جداول غیر اصلی نشان داد ؟
        notifyDataSetChanged();
    }



//----------------------------------------------------------------------------------------------------------

    /**
     * only first data Collection have the same order of  recycler view position
     * tables are : CartableTable
     */
    private void setupRowCartable(ReportsRecHolderType2 viewHolder) {
        CartableTable cartableTable = (CartableTable) getItem(0, viewHolder.getAdapterPosition());

        if (cartableTable != null) {
            viewHolder.setRowData(cartableTextMaker(
                    Objects.requireNonNull(cartableTable).getSenderName(),
                    cartableTable.getSubject(),
                    CustomTimeHelper.getPresentableNumberDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(cartableTable.getInsertDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                    cartableTable.getB5HCPriorityName())
                   ,cartableTable.getSubject(),
                    cartableTable.getSenderName(),
                    CustomTimeHelper.getPresentableNumberDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(cartableTable.getInsertDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                    AttachJsonCreator.getFirstAttachName(cartableTable.getAttach()),
                    true,
                    setupRowReportViewType(cartableTable.getGuid(), cartableTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), false)
                    );
        }

    }

    /**
     * only first data Collection have the same order of  recycler view position
     * tables are goods table
     */
    private void setupRowGoods(ReportsRecHolderType2 viewHolder) {
        if (getItem(0, viewHolder.getAdapterPosition()) != null) {
            GoodsTable goodsTable = (GoodsTable) getItem(0, viewHolder.getAdapterPosition());
            ArrayList<GoodSUOMModel> goodSUOMModelArrayList = GoodSUOMJsonHelper.getGoodSUOMArray(Objects.requireNonNull(goodsTable).getGoodSUOMList());
            if (goodSUOMModelArrayList.size() != 0) {

                viewHolder.setRowData(
                        goodsTextMaker(Objects.requireNonNull(goodsTable).getName(), goodsTable.getCode(), goodSUOMModelArrayList.get(0).getUnitName(), goodsTable.getBrandName(), goodsTable.getLatinName(), goodsTable.getTechInfo(), goodsTable.getNationalityCode(), goodSUOMModelArrayList.get(0).getWidth(), goodSUOMModelArrayList.get(0).getHeight(), goodSUOMModelArrayList.get(0).getLength(), goodSUOMModelArrayList.get(0).getWeight(), goodsTable.getSerial()),
                        goodsTable.getName(),
                        goodsTable.getCode(),
                        Commons.DASH,
                        AttachJsonCreator.getFirstAttachName(goodsTable.getAttach()),
                        true,
                        setupRowReportViewType(goodsTable.getGuid(), goodsTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), false)
                        );
            }
        }
    }

    /**
     * first table is GoodTrans
     * second table is BaseCoding (all rows come here)
     * <p>
     * second table not in manner of adaptor position but not null
     */
    private void setupRowGoodTrans(ReportsRecHolderType3 viewHolder) {
        if (viewHolder.getAdapterPosition() == 0) {
            //for all rows base codding is same, fro better performance only get one
            if (getData(1) != null) {
                baseCodingTables = (OrderedRealmCollection<BaseCodingTable>) getData(1);
            }
        }
        goodTranceTable = (GoodTranceTable) getItem(0, viewHolder.getAdapterPosition());

        if (goodTranceTable != null && baseCodingTables != null) {
            viewHolder.setRowData(
                    context.getString(R.string.sell_emporium), baseCodingTables.where().equalTo(CommonColumnName.ID, goodTranceTable.getB5IdRefId1()).findAll().size() != 0 ? baseCodingTables.where().equalTo(CommonColumnName.ID, goodTranceTable.getB5IdRefId1()).findAll().get(0).getName() : Commons.DASH,
                    context.getString(R.string.warehouse), baseCodingTables.where().equalTo(CommonColumnName.ID, goodTranceTable.getB5IdRefId2()).findAll().size() != 0 ? baseCodingTables.where().equalTo(CommonColumnName.ID, goodTranceTable.getB5IdRefId2()).findAll().get(0).getName() : Commons.DASH,
                    context.getString(R.string.buyer_name), baseCodingTables.where().equalTo(CommonColumnName.ID, goodTranceTable.getB5IdRefId3()).findAll().size() != 0 ? baseCodingTables.where().equalTo(CommonColumnName.ID, goodTranceTable.getB5IdRefId3()).findAll().get(0).getName() : Commons.DASH,
                    context.getString(R.string.visitor_name), baseCodingTables.where().equalTo(CommonColumnName.ID, goodTranceTable.getB5IdRefId4()).findAll().size() != 0 ? baseCodingTables.where().equalTo(CommonColumnName.ID, goodTranceTable.getB5IdRefId4()).findAll().get(0).getName() : Commons.DASH,
                    context.getString(R.string.delivery), baseCodingTables.where().equalTo(CommonColumnName.ID, goodTranceTable.getB5IdRefId5()).findAll().size() != 0 ? baseCodingTables.where().equalTo(CommonColumnName.ID, goodTranceTable.getB5IdRefId5()).findAll().get(0).getName() : Commons.DASH,
                    context.getString(R.string.description), goodTranceTable.getDescription(),
                    CustomTimeHelper.getPresentableNumberDateWithOutHour(CustomTimeHelper.stringDateToMyLocalDateConvertor(goodTranceTable.getFormDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                    String.format(CommonsFormat.FORMAT_3, context.getString(R.string.number), goodTranceTable.getFormNumber()),
                    setupRowReportViewType(goodTranceTable.getGuid(), goodTranceTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), goodTranceTable.getActivityState().equals(CommonActivityState.DELETE))
            );
        }
    }

    /**
     * only first data Collection have the same order of  recycler view position
     * * tables are task and baseCoding
     */
    private void setupRowTask(ReportsRecHolderType2 viewHolder) {

        if (viewHolder.getAdapterPosition() == 0) {
            //for all rows base codding is same, fro better performance only get one
            if (getData(1) != null) {
                baseCodingTables = (OrderedRealmCollection<BaseCodingTable>) getData(1);
            }
        }

        taskTable = (TaskTable) getItem(0, viewHolder.getAdapterPosition());

        if (taskTable != null && baseCodingTables != null) {

            viewHolder.setRowData(taskTextMaker(
                    baseCodingTables.where().equalTo(CommonColumnName.ID, taskTable.getB5IdRefId()).findAll().size() != 0 ? baseCodingTables.where().equalTo(CommonColumnName.ID, taskTable.getB5IdRefId()).findAll().get(0).getName() : Commons.DASH,
                    taskTable.getSubject(),
                    CustomTimeHelper.getPresentableNumberDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(taskTable.getTaskDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                    taskTable.getComments(),
                    baseCodingTables.where().equalTo(CommonColumnName.ID, taskTable.getB5IdRefId3()).findAll().size() != 0 ? baseCodingTables.where().equalTo(CommonColumnName.ID, taskTable.getB5IdRefId3()).findAll().get(0).getName() : Commons.DASH
            ),
                    taskTable.getSubject(),
                    taskTable.getComments(),
                    CustomTimeHelper.getPresentableNumberDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(taskTable.getTaskDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                    AttachJsonCreator.getFirstAttachName(taskTable.getAttach()),
                    true,
                    setupRowReportViewType(taskTable.getGuid(), taskTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), taskTable.getActivityState().equals(CommonActivityState.DELETE))
                    );
        }
    }

    /**
     * only first data Collection have the same order of  recycler view position
     * *      * tables are time only
     */
    private void setupRowTime(ReportsRecHolderType2 viewHolder) {
        if (getItem(0, viewHolder.getAdapterPosition()) != null) {
            TimeTable timeTable = (TimeTable) getItem(0, viewHolder.getAdapterPosition());

            viewHolder.setRowData(
                    timeTextMaker(
                            CustomTimeHelper.getPresentableNumberDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(timeTable.getStartDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                            CustomTimeHelper.getPresentableNumberDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(timeTable.getEndDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                            timeTable.getDescription()
                    ),
                    CustomTimeHelper.getPresentableNumberDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(timeTable.getStartDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                    CustomTimeHelper.getPresentableNumberDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(timeTable.getEndDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                    CustomTimeHelper.getPresentableNumberDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(timeTable.getStartDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                    Commons.NULL,
                    false,
                    setupRowReportViewType(timeTable.getGuid(), timeTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), timeTable.getActivityState().equals(CommonActivityState.DELETE))
            );
        }
    }

    /**
     * only first data Collection have the same order of  recycler view position
     * *      * tables are weighing
     */
    private void setupRowWeighing(ReportsRecHolderType2 viewHolder) {
        if (getItem(0, viewHolder.getAdapterPosition()) != null) {
            WeighingTable weighingTable = (WeighingTable) getItem(0, viewHolder.getAdapterPosition());
            viewHolder.setRowData(
                    weighingTextMaker(
                            Objects.requireNonNull(weighingTable).getCarPlaqueNumber(),
                            weighingTable.getDriverName(),
                            weighingTable.getGoodTranseAmount()),
                    weighingTable.getCarPlaqueNumber(),
                    weighingTable.getDriverName(),
                    CustomTimeHelper.getPresentableNumberDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(weighingTable.getGoodTranseDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                    Commons.NULL,
                    false,
                    setupRowReportViewType(weighingTable.getGuid(), weighingTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED), weighingTable.getActivityState().equals(CommonActivityState.DELETE))
            );

        }
    }

    private void setupRowFuelMaster(ReportsRecHolderType4 viewHolder) {
        if (getItem(0, viewHolder.getAdapterPosition()) != null) {
            FuelListMasterTable fuelListMasterTable = (FuelListMasterTable) getItem(0, viewHolder.getAdapterPosition());
            viewHolder.setRowData(
                    Objects.requireNonNull(fuelListMasterTable).getBranchName(),
                    fuelListMasterTable.getDepartmentName(),
                    String.format(CommonsFormat.FORMAT_3, context.getString(R.string.warehouse), fuelListMasterTable.getWareHouseName()),
                    String.format(CommonsFormat.FORMAT_3, context.getString(R.string.number), fuelListMasterTable.getFormNumber()),
                    CustomTimeHelper.getPresentableNumberDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(fuelListMasterTable.getFormDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                    fuelListMasterTable.getDriverName(),
                    fuelListMasterTable.getFuelName(),
                    fuelListMasterTable.getDescription(),
                    String.format(CommonsFormat.FORMAT_3, context.getString(R.string.unit), fuelListMasterTable.getUnitName()),
                    fuelListMasterTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED)
                    );

        }
    }

    private void setupRowFuelDetail(ReportsRecHolderType5 viewHolder) {
        if (getItem(0, viewHolder.getAdapterPosition()) != null) {

            FuelListDetailTable fuelListDetailTable = (FuelListDetailTable) getItem(0, viewHolder.getAdapterPosition());
            RealmResults<UtilCodeTable> utilCodeTableRealmResults = (RealmResults<UtilCodeTable>) getData(1);
            //ممکن است نحوه تخلیه سوخت مشخص شده باشد یا خیر (اگر انتخاب شده است بنابراین لیست اکسس دیناید است و هیچ یوزری نمیتواند آنرا ویرایش نماید )
                viewHolder.setRowData(Objects.requireNonNull(fuelListDetailTable).getPlaceName(),
                        fuelListDetailTable.getDistributionDate().equals(Commons.MINIMUM_TIME) || fuelListDetailTable.getDistributionDate().equals(Commons.MINIMUM_TIME_2)?String.format(CommonsFormat.FORMAT_3, context.getString(R.string.date), Commons.DASH):CustomTimeHelper.getPresentableNumberDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(fuelListDetailTable.getDistributionDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context),
                        fuelListDetailTable.getB5HCDistributionId().equals(Commons.NULL_INTEGER)?String.format(CommonsFormat.FORMAT_3, context.getString(R.string.distribut_type), Commons.DASH) :String.format(CommonsFormat.FORMAT_3, context.getString(R.string.distribut_type), Objects.requireNonNull(Objects.requireNonNull(utilCodeTableRealmResults).where().equalTo(CommonColumnName.ID, fuelListDetailTable.getB5HCDistributionId()).findAll().get(0)).getName()),
                        fuelListDetailTable.getAmount(),
                        fuelListDetailTable.getDeviceName(),
                        String.format(CommonsFormat.FORMAT_3, context.getString(R.string.description), fuelListDetailTable.getDescription()),
                        fuelListDetailTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED)
                );

        }
    }
    //------------------------------------------------------------------------------------------------------------------
    private String cartableTextMaker(String senderName, String subject, String insertDate, String priority) {
        return EditTextCheckHelper.concatHandler(
                CustomTimeHelper.checkMyTimeNotNull(insertDate) ? insertDate : Commons.DASH,
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.sender), senderName),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.subject), subject),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.priority), priority)
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

    private String weighingTextMaker(String carPlaqueNumber, String driverName, String amount) {

        return EditTextCheckHelper.concatHandler(
                carPlaqueNumber,
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.driver_name), driverName),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.main_number), amount));
    }

    //here date my be null (1348 or 1970) and should be checked
    private String timeTextMaker(String startDate, String endDate, String description) {
        return EditTextCheckHelper.concatHandler(
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.start_date), CustomTimeHelper.checkMyTimeNotNull(startDate) ? startDate : Commons.DASH),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.end_date), CustomTimeHelper.checkMyTimeNotNull(endDate) ? endDate : Commons.DASH),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.description), Commons.SPACE),
                description
        );
    }

    private String taskTextMaker(String receiver, String subject, String date, String comment, String taskRef) {
        return EditTextCheckHelper.concatHandler(
                CustomTimeHelper.checkMyTimeNotNull(date) ? date : Commons.DASH,
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.receiver), receiver),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.reference), taskRef),
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.subject), Commons.SPACE),
                subject,
                String.format(CommonsFormat.FORMAT_3, context.getString(R.string.description), Commons.SPACE),
                comment
        );
    }

//--------------------------------------------------------------------------------------------------

}
