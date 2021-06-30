package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.EditTextCheckHelper;


/*
 * goodTranseCode : 60
 * هیچ دیتایی از بیس کدینگ و یوتیل کد بیرون نمیاید و همه دیتا ها ازهمین جدول حاظر استخراج میشود
 */

/**
 * این جدول با رست پر میشود
 * KIND=WEIGHING
 * * و همه سینک استیت های اولیه باید
 * SYNCED
 * باشد
 * و مقدار
 * activityState=َِADD
 * و پس از ویرایش توسط کاربر
 * BEFORESYNCED
 * است
 * روند معمول را ادامه میابد
 * و با کایند
 * SYNCWEIGHING
 * ارسال میشود که باید به سه گیرنده ارسال شود
 * 1-فرستنده
 * 2-سرپرست انبار
 * 3-کارگر انبار
 * فقط یوزر اول اجازه ویرایش دارد و یوزر دوم و سوم فقط ریپورت را میبینند
 * یوزر اول تمام استیت های سینک به جز
 * accessDenied
 * را میتواند ویرایش نماید
 * ولی یوزر دوم و سوم امکان ویرایش نداشته و وارد فرم مربوطه نمیشوند
 * ------------------------------------------------------
 * id
 * carPlaqueNumber
 * buyerName
 * goodTranseNumber  //>> شماره حواله
 * goodTranseDate    //>> تاریخ حواله
 * driverName
 * emptyDate   //>> تاریخ ورود به باسکول
 * emptyWeight //>> وزن خالی وسیله نقلیه
 * amount      //>> مقدار بارگیری شده
 * goodTranseWeight  //>> وزن مندرج در حواله
 * sumWeight   //>> وزنی که تا کنون بارگیری و خارج شده است
 * balance     //>> goodTranseWeight - sumWeight   وزن باقی مانده
 * guid : for use in report activity(id=guid)(dar rest vojod nadarad)
 * syncState ----> from CommonSyncState class
 * activityState ----> from CommonActivityState class
 * oldValue ----> from server
 */
public class WeighingTable extends RealmObject implements IModels {

    @PrimaryKey
    private String id;
    private String guid, carPlaqueNumber, buyerName,
            goodTranseDate, driverName, emptyDate, goodTranseNumber,
            emptyWeight, goodTranseAmount, goodTranseWeight, sumWeight,
            balance, syncState, activityState, oldValue, B5HCStatusId;


    public WeighingTable() {

    }

    public WeighingTable(String id, String carPlaqueNumber, String buyerName, String goodTranseDate, String driverName, String emptyDate, String goodTranseNumber, String emptyWeight, String goodTranseAmount, String goodTranseWeight, String sumWeight, String balance, String syncState, String activityState, String oldValue, String B5HCStatusId) {
        this.id = id;
        this.carPlaqueNumber = carPlaqueNumber;
        this.buyerName = buyerName;
        this.goodTranseDate = goodTranseDate;
        this.driverName = driverName;
        this.emptyDate = emptyDate;
        this.goodTranseNumber = goodTranseNumber;
        this.emptyWeight = emptyWeight;
        this.goodTranseAmount = goodTranseAmount;
        this.goodTranseWeight = goodTranseWeight;
        this.sumWeight = sumWeight;
        this.balance = balance;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
        this.guid = String.valueOf(id);
        this.B5HCStatusId = B5HCStatusId;
    }

    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return String.valueOf(id);
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCarPlaqueNumber() {
        return carPlaqueNumber == null ? Commons.NULL : carPlaqueNumber.contains("-") ? carPlaqueNumber : EditTextCheckHelper.fullCarPlaqueHelper(carPlaqueNumber);
    }

    public void setCarPlaqueNumber(String carPlaqueNumber) {
        this.carPlaqueNumber = carPlaqueNumber;
    }

    public String getBuyerName() {
        return buyerName == null ? Commons.NULL : buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getGoodTranseDate() {
        return goodTranseDate == null ? Commons.NULL : goodTranseDate;

    }

    public void setGoodTranseDate(String goodTranseDate) {
        this.goodTranseDate = goodTranseDate;
    }

    public String getDriverName() {
        return driverName == null ? Commons.NULL : driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getEmptyDate() {
        return emptyDate == null ? Commons.NULL : emptyDate;
    }

    public void setEmptyDate(String emptyDate) {
        this.emptyDate = emptyDate;
    }

    public String getGoodTranseNumber() {
        return goodTranseNumber == null ? Commons.NULL : goodTranseNumber;
    }

    public void setGoodTranseNumber(String goodTranseNumber) {
        this.goodTranseNumber = goodTranseNumber;
    }

    public String getEmptyWeight() {
        return emptyWeight == null ? Commons.NULL : emptyWeight;
    }

    public void setEmptyWeight(String emptyWeight) {
        this.emptyWeight = emptyWeight;
    }

    //amount and sub default value is -1 = Commons.NULL_INTEGER
    public String getGoodTranseAmount() {
        return goodTranseAmount == null ? Commons.NULL_INTEGER : goodTranseAmount;
    }

    public void setGoodTranseAmount(String goodTranseAmount) {
        this.goodTranseAmount = goodTranseAmount;
    }

    public String getGoodTranseWeight() {
        return goodTranseWeight == null ? Commons.NULL : goodTranseWeight;
    }

    public void setGoodTranseWeight(String goodTranseWeight) {
        this.goodTranseWeight = goodTranseWeight;
    }

    public String getSumWeight() {
        return sumWeight == null ? Commons.NULL : sumWeight;
    }

    public void setSumWeight(String sumWeight) {
        this.sumWeight = sumWeight;
    }

    public String getBalance() {
        return balance == null ? Commons.NULL : balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    /**
     * @return : SYNCED because get from Db and not be send from client to server without  amount value
     * do not worry if in sync process , all values were be cleaned
     */
    public String getSyncState() {
        return syncState == null ? CommonSyncState.SYNCED : syncState;

    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public String getActivityState() {
        return activityState == null ? CommonActivityState.ADD : activityState;
    }

    public void setActivityState(String activityState) {
        this.activityState = activityState;
    }

    public String getOldValue() {
        return oldValue == null ? Commons.NULL : oldValue;

    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getB5HCStatusId() {
        return B5HCStatusId == null ? Commons.NULL_INTEGER : B5HCStatusId;
    }

    public void setB5HCStatusId(String b5HCStatusId) {
        B5HCStatusId = b5HCStatusId;
    }
}
