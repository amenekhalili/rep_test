package ir.fararayaneh.erp.data.models.tables.sync_tables;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;




/**
 *  ردیف هایی از جدول سینک شود که :
 *  * syncState = beforeSync
   *****************************************************************
 * deprecated : ساختار جدول اتچمنت تغییر کرده است
 *
 * بعد از انجام سینک اگر امکان درج اتچمنت وجود دارد حتما ستون
 * b5IdRefId
 *  * جدول اتچمنت هم آپدیت شود
 //--------------------------------------------------------------------------
 * این جدول توسط سه فرم کد زیر پر میشود
 * 215 و 483و 520
 *
 * به عبارت دیگر برای محل ذخیره فرم های تایم شیت، شیت پلن و دیوایس تایم شیت است
 //--------------------------------------------------------------------------
 * id --->> در ابتدا صفر است و پس از انجام سینک مقدار آی دی از سرور
 * guid --->  جی یو آی دی ولی تاانتها بدون تغییر باقی میماند
 *  * مقدار جی یو آی دی بدون خط فاصله درج شود
 * geoLocation --->  lat:long,lat:long,lat:long,lat:long
 *
 * isHappeningAfterAtTheSameTime --->
 * 0 :نداشتن همزمانی در انجام کار مورد نظر
 * 1 :داشتن همزمانی در انجام کار مورد نظر
 *
 *B5formRefId --->> برای ذخیره مقدار ستون
 *                      formId
 *                            از جدول
 *                      formCode
 *
 *   B5IdRefId,B5IdRefId2,B5IdRefId3 --->> برای ذخیره مقدار کانفیگ ها که به ستون آی دی در جدول بیس کدینگ منتهی شده است
 *
 *   B5HCDailyScheduleId --->> مقدار آی دی در جدول یوتیل کد برای مقدار DAS
 *   B5HCWageTitleId --->> مقدار آی دی در جدول یوتیل کد برای مقدار WTT
 *
 *   syncState --->> CommonSyncState یک از حالت های سه گانه کلاس
 *
 *   activityState --->> CommonActivityState یک از حالت های سه گانه کلاس
 *
 * B5HCStatusId :  (draft,during,trash,archive,....) (name can GET from utilCode) (VCS)
 *
 *
 * ------------------------------------------------------
 * when get from server :
 * syncState = synced
 * guid = sting of id
 *
 *
 */


public class TimeTable extends RealmObject implements IModels {

    public TimeTable(String id,String guid, String B5formRefId, String B5IdRefId, String B5IdRefId2, String B5IdRefId3, String B5HCDailyScheduleId, String B5HCWageTitleId, int isHappeningAtTheSameTime, String startDate, String endDate, String description, String geoLocation, String syncState, String activityState, String oldValue,String B5HCStatusId) {
        this.id = id;
        this.guid = guid;
        this.B5formRefId = B5formRefId;
        this.B5IdRefId = B5IdRefId;
        this.B5IdRefId2 = B5IdRefId2;
        this.B5IdRefId3 = B5IdRefId3;
        this.B5HCDailyScheduleId = B5HCDailyScheduleId;
        this.B5HCWageTitleId = B5HCWageTitleId;
        this.isHappeningAtTheSameTime = isHappeningAtTheSameTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.geoLocation = geoLocation;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
        this.B5HCStatusId = B5HCStatusId;
    }

    public TimeTable() {
    }

    private int isHappeningAtTheSameTime;
    private String id,B5formRefId,B5IdRefId,B5IdRefId2,B5IdRefId3, B5HCDailyScheduleId,B5HCWageTitleId,B5HCStatusId;
    @PrimaryKey
    private String guid;
    private String startDate,endDate;
    private String description,geoLocation,syncState,activityState,oldValue;




    public int getIsHappeningAtTheSameTime() {
        return isHappeningAtTheSameTime;
    }



    //if we have not guid, return id instead
    public String getGuid() {
        return guid==null?String.valueOf(id):guid;
    }

    public String getDescription() {
        return description==null?Commons.NULL:description;
    }

    public String getGeoLocation() {
        return geoLocation==null?Commons.NULL:geoLocation;
    }

    public String getSyncState() {
        return  syncState==null?CommonSyncState.BEFORE_SYNC:syncState;
    }

    public String getActivityState() {
        return  activityState==null?CommonActivityState.ADD:activityState;
    }

    public String getOldValue() {
        return oldValue==null?Commons.NULL:oldValue;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setIsHappeningAtTheSameTime(int isHappeningAtTheSameTime) {
        this.isHappeningAtTheSameTime = isHappeningAtTheSameTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
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

    public String getStartDate() {
        return startDate==null?Commons.NULL:startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate==null?Commons.NULL:endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setB5formRefId(String b5formRefId) {
        B5formRefId = b5formRefId;
    }

    public void setB5IdRefId(String b5IdRefId) {
        B5IdRefId = b5IdRefId;
    }

    public void setB5IdRefId2(String b5IdRefId2) {
        B5IdRefId2 = b5IdRefId2;
    }

    public void setB5IdRefId3(String b5IdRefId3) {
        B5IdRefId3 = b5IdRefId3;
    }

    public void setB5HCDailyScheduleId(String b5HCDailyScheduleId) {
        B5HCDailyScheduleId = b5HCDailyScheduleId;
    }

    public void setB5HCWageTitleId(String b5HCWageTitleId) {
        B5HCWageTitleId = b5HCWageTitleId;
    }

    public void setB5HCStatusId(String b5HCStatusId) {
        B5HCStatusId = b5HCStatusId;
    }

    public String getId() {
        return id== null ? Commons.NULL_INTEGER : id;
    }

    public String getB5formRefId() {
        return B5formRefId== null ? Commons.NULL_INTEGER : B5formRefId;
    }

    public String getB5IdRefId() {
        return B5IdRefId== null ? Commons.NULL_INTEGER : B5IdRefId;
    }

    public String getB5IdRefId2() {
        return B5IdRefId2== null ? Commons.NULL_INTEGER : B5IdRefId2;
    }

    public String getB5IdRefId3() {
        return B5IdRefId3== null ? Commons.NULL_INTEGER : B5IdRefId3;
    }

    public String getB5HCDailyScheduleId() {
        return B5HCDailyScheduleId== null ? Commons.NULL_INTEGER : B5HCDailyScheduleId;
    }

    public String getB5HCWageTitleId() {
        return B5HCWageTitleId== null ? Commons.NULL_INTEGER : B5HCWageTitleId;
    }

    public String getB5HCStatusId() {
        return B5HCStatusId == null ? Commons.NULL_INTEGER : B5HCStatusId;
    }
}
