package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * B5CountryDivisionId,//id  in tableCountryDivisionTable.class
 * B5HCOwnershipId,    //from utilCode by 'AOT'
 * B5HCAddressTypeId,  //from utilCode by 'AUT'
 * startOwnedDate,     //date of start rental
 * stopOwnedDate,      //date of end rental
 * location,           //lat:lang
 */
public class AddressBookTable extends RealmObject implements IModels {

    private int isMain;
    @PrimaryKey
    private String guid;
    private String id, B5IdRefId, B5CountryDivisionId,
            address, postalCode, B5HCOwnershipId,
            B5HCAddressTypeId, startOwnedDate,
            stopOwnedDate, region, location,
            syncState, activityState, oldValue;

    public AddressBookTable(int isMain, String guid, String id, String b5IdRefId, String b5CountryDivisionId, String address, String postalCode, String b5HCOwnershipId, String b5HCAddressTypeId, String startOwnedDate, String stopOwnedDate, String region, String location, String syncState, String activityState, String oldValue) {
        this.isMain = isMain;
        this.guid = guid;
        this.id = id;
        B5IdRefId = b5IdRefId;
        B5CountryDivisionId = b5CountryDivisionId;
        this.address = address;
        this.postalCode = postalCode;
        B5HCOwnershipId = b5HCOwnershipId;
        B5HCAddressTypeId = b5HCAddressTypeId;
        this.startOwnedDate = startOwnedDate;
        this.stopOwnedDate = stopOwnedDate;
        this.region = region;
        this.location = location;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
    }

    public AddressBookTable() {
    }

    public void setIsMain(int isMain) {
        this.isMain = isMain;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setId(String id) {
        id = id;
    }

    public void setB5IdRefId(String b5IdRefId) {
        B5IdRefId = b5IdRefId;
    }

    public void setB5CountryDivisionId(String b5CountryDivisionId) {
        B5CountryDivisionId = b5CountryDivisionId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setB5HCOwnershipId(String b5HCOwnershipId) {
        B5HCOwnershipId = b5HCOwnershipId;
    }

    public void setB5HCAddressTypeId(String b5HCAddressTypeId) {
        B5HCAddressTypeId = b5HCAddressTypeId;
    }

    public void setStartOwnedDate(String startOwnedDate) {
        this.startOwnedDate = startOwnedDate;
    }

    public void setStopOwnedDate(String stopOwnedDate) {
        this.stopOwnedDate = stopOwnedDate;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setLocation(String location) {
        this.location = location;
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

    //--------------------------------------------------------

    /**
     * by default return 0 that is not main [(Commons.IS_NOT_MAIN)]
     */
    public int getIsMain() {
        return isMain;
    }

    public String getGuid() {
        return guid == null ? Commons.NULL : guid;
    }

    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getB5IdRefId() {
        return B5IdRefId == null ? Commons.NULL_INTEGER : B5IdRefId;
    }

    public String getB5CountryDivisionId() {
        return B5CountryDivisionId == null ? Commons.NULL_INTEGER : B5CountryDivisionId;
    }

    public String getAddress() {
        return address == null ? Commons.NULL : address;
    }

    public String getPostalCode() {
        return postalCode == null ? Commons.NULL : postalCode;
    }

    public String getB5HCOwnershipId() {
        return B5HCOwnershipId == null ? Commons.NULL_INTEGER :B5HCOwnershipId;
    }

    public String getB5HCAddressTypeId() {
        return B5HCAddressTypeId == null ? Commons.NULL_INTEGER :B5HCAddressTypeId;
    }

    public String getStartOwnedDate() {
        return startOwnedDate == null ? Commons.NULL :startOwnedDate;
    }

    public String getStopOwnedDate() {
        return stopOwnedDate == null ? Commons.NULL :stopOwnedDate;
    }

    public String getRegion() {
        return region == null ? Commons.NULL_INTEGER :region;
    }

    public String getLocation() {
        return location == null ? Commons.NULL :location;
    }

    public String getSyncState() {
        return syncState == null ? CommonSyncState.SYNCED :syncState;
    }

    public String getActivityState() {
        return activityState == null ? CommonActivityState.ADD :activityState;
    }

    public String getOldValue() {
        return oldValue == null ? Commons.NULL :oldValue;
    }


}
