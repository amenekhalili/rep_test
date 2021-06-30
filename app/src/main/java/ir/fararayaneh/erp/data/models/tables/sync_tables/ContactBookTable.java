package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * isMain = 1 if my record is main  (Commons.IS_MAIN)
 * B5HCTypeId = type address (util code by 'COT')
 */
public class ContactBookTable extends RealmObject implements IModels {

    private int isMain;
    private String id,B5HCTypeId, B5IdRefId,
            value, description,
            syncState, activityState,
            oldValue;

    @PrimaryKey
    private String guid;

    public ContactBookTable() {}

    public ContactBookTable(int isMain,String id, String b5HCTypeId, String b5IdRefId, String value, String description, String syncState, String activityState, String oldValue, String guid) {
        this.isMain = isMain;
        B5HCTypeId = b5HCTypeId;
        B5IdRefId = b5IdRefId;
        this.id = id;
        this.value = value;
        this.description = description;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
        this.guid = guid;
    }

    public void setIsMain(int isMain) {
        this.isMain = isMain;
    }

    public void setB5HCTypeId(String b5HCTypeId) {
        B5HCTypeId = b5HCTypeId;
    }

    public void setB5IdRefId(String b5IdRefId) {
        B5IdRefId = b5IdRefId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public void setActivityState(String activityState) {
        this.activityState = activityState;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setId(String id) {
        this.id = id;
    }
    //---------------------------------------------------------

    /**
     * by default return 0 that is not main [(Commons.IS_NOT_MAIN)]
     */
    public int getIsMain() {
        return isMain;
    }

    public String getB5HCTypeId() {
        return B5HCTypeId == null ? Commons.NULL_INTEGER : B5HCTypeId;
    }

    public String getB5IdRefId() {
        return B5IdRefId == null ? Commons.NULL_INTEGER : B5IdRefId;
    }

    public String getValue() {
        return value == null ? Commons.NULL : value;
    }

    public String getDescription() {
        return description == null ? Commons.NULL : description;
    }

    public String getSyncState() {//we get from server by default
        return syncState == null ? CommonSyncState.SYNCED : syncState;
    }

    public String getActivityState() {
        return activityState == null ? CommonActivityState.ADD : activityState;
    }

    public String getOldValue() {
        return oldValue == null ? Commons.NULL : oldValue;
    }

    public String getGuid() {
        return guid == null ? Commons.NULL : guid;
    }

    public String getId() {
        return id == null ? Commons.NULL_INTEGER :id;
    }
}
