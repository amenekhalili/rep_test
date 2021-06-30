package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 *
B5HCDistributionId	    String			نحوه توزیع سوخت --توسط یوزر
deviceName              String         (کسی که مقصد سوخت است)نام ماشین تحویل گیرنده سوخت
placeName	            String			نام مزرعه و ساختمانها -- دارایی ثابت
distributionDate	    String			 - توسط دیوایس-- تاریخ توزیع سوخت
amount	                String			--توسط یوزر--مقدار سوخت تحویلی
description	            String			توضیحات
masterId	            String			شناسه مستر
location(lat:long)      String          توسط دیوایس پر شود//lat:lang

 بیزینس در مستر
 * ------------------------------------------------------
 */


public class FuelListDetailTable extends RealmObject implements IModels {

    public FuelListDetailTable() {
    }

    public FuelListDetailTable(String id, String b5HCDistributionId, String placeName, String deviceName, String distributionDate, String amount, String description, String masterId, String location,String syncState,String activityState,String oldValue) {
        this.id = id;
        B5HCDistributionId = b5HCDistributionId;
        this.placeName = placeName;
        this.deviceName = deviceName;
        this.distributionDate = distributionDate;
        this.amount = amount;
        this.description = description;
        this.masterId = masterId;
        this.location = location;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
    }

    @PrimaryKey
    private String id;

    private String B5HCDistributionId, placeName, deviceName, distributionDate, amount, description, masterId, location,syncState,activityState,oldValue;

    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getB5HCDistributionId() {
        return B5HCDistributionId == null ? Commons.NULL_INTEGER : B5HCDistributionId;
    }


    public String getPlaceName() {
        return placeName== null ? Commons.NULL : placeName;
    }

    public String getDeviceName() {
        return deviceName== null ? Commons.NULL : deviceName;
    }

    public String getDistributionDate() {
        return distributionDate == null ? Commons.MINIMUM_TIME : distributionDate;
    }

    public String getAmount() {
        return amount == null ? Commons.NULL_INTEGER : amount;
    }

    public String getDescription() {
        return description == null ? Commons.NULL : description;
    }

    public String getMasterId() {
        return masterId == null ? Commons.NULL_INTEGER : masterId;
    }

    public String getLocation() {
        return location == null ? Commons.NULL : location;
    }

    public String getSyncState() {
        return syncState== null ? CommonSyncState.SYNCED : syncState;
    }

    public String getActivityState() {
        return activityState== null ? CommonActivityState.ADD : activityState;
    }

    public String getOldValue() {
        return oldValue== null ? Commons.NULL : oldValue;
    }



    public void setId(String id) {
        this.id = id;
    }

    public void setB5HCDistributionId(String b5HCDistributionId) {
        B5HCDistributionId = b5HCDistributionId;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDistributionDate(String distributionDate) {
        this.distributionDate = distributionDate;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
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
}
