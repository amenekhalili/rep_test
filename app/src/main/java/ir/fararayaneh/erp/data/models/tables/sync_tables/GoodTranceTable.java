package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * all details in: finalGoodTrance.xlx
 *
 * in GET mode : all syncState is Synced;
 */

public class GoodTranceTable extends RealmObject implements IModels {

    public GoodTranceTable() {

    }

    @PrimaryKey
    private String guid;

    private String
            id,
            B5FormRefId,
            B5IdRefId1,
            B5IdRefId2,
            B5IdRefId3,
            B5IdRefId4,
            B5IdRefId5,
            B5IdRefId6,
            B5IdRefId14,
            B5HCCurrencyId,
            B5HCSellMethodId,
            B5HCAccountSideId,
            B5IdRefIdRecall,
            B5HCStatusId,
            deliveryName,
            formNumber,
            formDate,
            expireDate,
            description,
            syncState,
            activityState,
            oldValue,
            customerGroupId,
            addressId,
            goodTransDetail;

    private int duration;

    public GoodTranceTable(String id, String b5FormRefId, String b5IdRefId1, String b5IdRefId2, String b5IdRefId3, String b5IdRefId4, String b5IdRefId5, String b5IdRefId6, String b5IdRefId14, String b5HCCurrencyId, String b5HCSellMethodId, String b5HCAccountSideId, String b5IdRefIdRecall, String b5HCStatusId, String deliveryName, String formNumber,  String formDate, String expireDate, String description, String guid, String syncState, String activityState, String oldValue, String goodTransDetail, int duration,String customerGroupId,
                           String addressId) {
        this.id = id;
        B5FormRefId = b5FormRefId;
        B5IdRefId1 = b5IdRefId1;
        B5IdRefId2 = b5IdRefId2;
        B5IdRefId3 = b5IdRefId3;
        B5IdRefId4 = b5IdRefId4;
        B5IdRefId5 = b5IdRefId5;
        B5IdRefId6 = b5IdRefId6;
        B5IdRefId14 = b5IdRefId14;
        B5HCCurrencyId = b5HCCurrencyId;
        B5HCSellMethodId = b5HCSellMethodId;
        B5HCAccountSideId = b5HCAccountSideId;
        B5IdRefIdRecall = b5IdRefIdRecall;
        B5HCStatusId = b5HCStatusId;
        this.deliveryName = deliveryName;
        this.formNumber = formNumber;
        this.formDate = formDate;
        this.expireDate = expireDate;
        this.description = description;
        this.guid = guid;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
        this.goodTransDetail = goodTransDetail;
        this.duration = duration;
        this.customerGroupId = customerGroupId;
        this.addressId = addressId;

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setB5FormRefId(String b5FormRefId) {
        B5FormRefId = b5FormRefId;
    }

    public void setB5IdRefId1(String b5IdRefId1) {
        B5IdRefId1 = b5IdRefId1;
    }

    public void setB5IdRefId2(String b5IdRefId2) {
        B5IdRefId2 = b5IdRefId2;
    }

    public void setB5IdRefId3(String b5IdRefId3) {
        B5IdRefId3 = b5IdRefId3;
    }

    public void setB5IdRefId4(String b5IdRefId4) {
        B5IdRefId4 = b5IdRefId4;
    }

    public void setB5IdRefId5(String b5IdRefId5) {
        B5IdRefId5 = b5IdRefId5;
    }

    public void setB5IdRefId6(String b5IdRefId6) {
        B5IdRefId6 = b5IdRefId6;
    }

    public void setB5IdRefId14(String b5IdRefId14) {
        B5IdRefId14 = b5IdRefId14;
    }

    public void setB5HCCurrencyId(String b5HCCurrencyId) {
        B5HCCurrencyId = b5HCCurrencyId;
    }

    public void setB5HCSellMethodId(String b5HCSellMethodId) {
        B5HCSellMethodId = b5HCSellMethodId;
    }

    public void setB5HCAccountSideId(String b5HCAccountSideId) {
        B5HCAccountSideId = b5HCAccountSideId;
    }

    public void setB5IdRefIdRecall(String b5IdRefIdRecall) {
        B5IdRefIdRecall = b5IdRefIdRecall;
    }

    public void setB5HCStatusId(String b5HCStatusId) {
        B5HCStatusId = b5HCStatusId;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
    }

    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setSecondaryDate(String secondaryDate) {
        secondaryDate = secondaryDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public void setGoodTransDetail(String goodTransDetail) {
        this.goodTransDetail = goodTransDetail;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    //-------------------------------------------------------------------------

    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getB5FormRefId() {
        return B5FormRefId == null ? Commons.NULL_INTEGER : B5FormRefId;
    }

    public String getB5IdRefId1() {
        return B5IdRefId1 == null ? Commons.NULL_INTEGER : B5IdRefId1;
    }

    public String getB5IdRefId2() {
        return B5IdRefId2 == null ? Commons.NULL_INTEGER : B5IdRefId2;
    }

    public String getB5IdRefId3() {
        return B5IdRefId3 == null ? Commons.NULL_INTEGER : B5IdRefId3;
    }

    public String getB5IdRefId4() {
        return B5IdRefId4 == null ? Commons.NULL_INTEGER : B5IdRefId4;
    }

    public String getB5IdRefId5() {
        return B5IdRefId5 == null ? Commons.NULL_INTEGER : B5IdRefId5;
    }

    public String getB5IdRefId6() {
        return B5IdRefId6 == null ? Commons.NULL_INTEGER : B5IdRefId6;
    }

    public String getB5IdRefId14() {
        return B5IdRefId14 == null ? Commons.NULL_INTEGER : B5IdRefId14;
    }

    public String getB5HCCurrencyId() {
        return B5HCCurrencyId == null ? Commons.NULL_INTEGER : B5HCCurrencyId;
    }

    public String getB5HCSellMethodId() {
        return B5HCSellMethodId == null ? Commons.NULL_INTEGER : B5HCSellMethodId;
    }

    public String getB5HCAccountSideId() {
        return B5HCAccountSideId == null ? Commons.NULL_INTEGER : B5HCAccountSideId;
    }

    public String getB5IdRefIdRecall() {
        return B5IdRefIdRecall == null ? Commons.NULL_INTEGER : B5IdRefIdRecall;
    }

    public String getB5HCStatusId() {
        return B5HCStatusId == null ? Commons.NULL_INTEGER : B5HCStatusId;
    }

    public String getDeliveryName() {
        return deliveryName == null ? Commons.NULL : deliveryName;
    }

    public String getFormNumber() {
        return formNumber == null ? Commons.NULL_INTEGER : formNumber;
    }

    public String getFormDate() {
        return formDate == null ? Commons.NULL : formDate ;
    }

    public String getExpireDate() {
        return expireDate == null ? Commons.NULL : expireDate;
    }

    public String getDescription() {
        return description == null ? Commons.NULL : description;
    }

    public String getGuid() {
        return guid == null ? Commons.NULL : guid;
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

    public String getGoodTransDetail() {
        return goodTransDetail == null ? Commons.NULL_ARRAY : goodTransDetail;
    }

    public int getDuration() {
        return duration;
    }

    public String getCustomerGroupId() {
        return customerGroupId == null ? Commons.NULL_INTEGER :customerGroupId;
    }

    public String getAddressId() {
        return addressId == null ? Commons.NULL_INTEGER :addressId;
    }
}
