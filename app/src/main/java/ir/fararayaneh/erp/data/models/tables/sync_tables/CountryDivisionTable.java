package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;
/**
 * countryDivisionTypeCode --> CommonCountryDivisionTypeCodeHandler.class
 */
public class CountryDivisionTable extends RealmObject implements IModels {


    private String id,
            name,
            B5CountryDivisionId,
            syncState, activityState, oldValue;

    private int countryDivisionTypeCode;

    @PrimaryKey
    private String guid;

    public CountryDivisionTable(String id, String name, String b5CountryDivisionId, int countryDivisionTypeCode, String syncState, String activityState, String oldValue, String guid) {
        this.id = id;
        this.name = name;
        B5CountryDivisionId = b5CountryDivisionId;
        this.countryDivisionTypeCode = countryDivisionTypeCode;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
        this.guid = guid;
    }

    public CountryDivisionTable() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setB5CountryDivisionId(String b5CountryDivisionId) {
        B5CountryDivisionId = b5CountryDivisionId;
    }

    public void setCountryDivisionTypeCode(int countryDivisionTypeCode) {
        this.countryDivisionTypeCode = countryDivisionTypeCode;
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

    //-------------------------------------------------------

    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getName() {
        return name == null ? Commons.NULL : name;
    }

    public String getB5CountryDivisionId() {
        return B5CountryDivisionId == null ? Commons.NULL_INTEGER : B5CountryDivisionId;
    }

    //if countryDivisionTypeCode=0 in CommonCountryDivisionTypeCodeHandler.class we return city
    public int getCountryDivisionTypeCode() {
        return countryDivisionTypeCode;
    }

    public String getSyncState() {
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


}
