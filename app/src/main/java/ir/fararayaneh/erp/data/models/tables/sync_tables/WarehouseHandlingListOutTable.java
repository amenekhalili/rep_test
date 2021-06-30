package ir.fararayaneh.erp.data.models.tables.sync_tables;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * for temp use in Ahvaz !
 */

public class WarehouseHandlingListOutTable extends RealmObject implements IModels {

    //use for insert or update
    @PrimaryKey
    private String guid;
    private String warehouseCode, goodCode, description, syncState,amount;

    public WarehouseHandlingListOutTable() {
    }

    public WarehouseHandlingListOutTable(String guid, String warehouseCode, String goodCode, String description, String syncState, String amount) {
        this.guid = guid;
        this.warehouseCode = warehouseCode;
        this.goodCode = goodCode;
        this.description = description;
        this.syncState = syncState;
        this.amount = amount;
    }

    public String getGuid() {
        return guid == null ? Commons.NULL : guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getWarehouseCode() {
        return warehouseCode == null ? Commons.NULL : warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getGoodCode() {
        return goodCode == null ? Commons.NULL : goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getDescription() {
        return description == null ? Commons.NULL : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSyncState() {
        return syncState == null ? Commons.NULL : CommonSyncState.BEFORE_SYNC;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public String getAmount() {
        return amount == null ? Commons.NULL :amount;
    }

    public void setAmount(String value) {
        this.amount = value;
    }
}
