package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * formCode : 305
 * هیچ دیتایی از بیس کدینگ و یوتیل کد بیرون نمیاید و همه دیتا ها ازهمین جدول حاظر استخراج میشود
 */

/**
 * این جدول با رست پر میشود
 * KIND=WAREHOUSEHANDLING
 * * و همه سینک استیت های اولیه باید
 * synced
 * باشد
 * و یوزر فقط میتواند کالاهایی را ببیند که
 * سینک استیت آنها سینک یا سینک اررور است
 * و پس از ویرایش توسط کاربر حالت ان به
 * beforeSync
 * تغییر یابد و ریسپانس از سرور نود که
 * state=accessDenied
 * باشد
 * * و کاربر اجازه ویرایش مجدد را نخواهد داشت
 * ویا اگر به اررور بخورد
 * state=syncError
 * است و هنوز امکان نمایش به یوزر را دارد
 * ولی استیت
 * beforeSync
 * به یوزر نمایش داده نمیشود چون به معنی این است که یک بار کالا را شمارش نموده است
 * <p>
 * ------------------------------------------------------
 * id
 * goodCode, شماره یکتایی که به هر کالا اختصاص میابد
 * goodName ,
 * serial ,یک شماره سریالی برای هر کالا
 * barCode,
 * mainUnitName , واحد شمارش اصلی
 * SubUnitName ,واحد شمارش فرعی
 * PlaceShelfRow,شماره ردیفی که قفسه در آن قرار دارد
 * PlaceShelfSubRow,ردیف فرعی - عمود بر ردیف اصلی- که قفسه در ان قرار دارد
 * PlaceShelfLayer,طبقه هر قفسه
 * CountingNumber , نوبت شمارش (1-2-3
 * batchNumber , شماره ای شبیه مک آدرس
 * goodColumnNumber ,  عدد تنوع کالایی
 * amount ,
 * subAmount,
 * syncState : from CommonSyncState class
 * oldValue
 * searchValue: مقدار کانکت شده از
 * goodCode
 * goodName
 * است و برای سرچ کردن از ان استفاده میشود
 * guid : for use in report activity(id=guid)(dar rest vojod nadarad)
 */

public class WarehouseHandlingTable extends RealmObject implements IModels {

    @PrimaryKey
    private String id;
    private int countingNumber,goodColumnNumber;
    private String goodCode, serial,goodName, barCode, mainUnitName, subUnitName,amount, subAmount,
            placeShelfRow, placeShelfSubRow, placeShelfLayer, batchNumber,
            syncState, oldValue, searchValue, guid;

    public WarehouseHandlingTable(String id, String goodCode, String serial, int countingNumber, int goodColumnNumber, String amount, String subAmount, String goodName, String barCode, String mainUnitName, String subUnitName, String placeShelfRow, String placeShelfSubRow, String placeShelfLayer, String batchNumber, String syncState, String oldValue, String searchValue) {
        this.id = id;
        this.goodCode = goodCode;
        this.serial = serial;
        this.countingNumber = countingNumber;
        this.goodColumnNumber = goodColumnNumber;
        this.amount = amount;
        this.subAmount = subAmount;
        this.goodName = goodName;
        this.barCode = barCode;
        this.mainUnitName = mainUnitName;
        this.subUnitName = subUnitName;
        this.placeShelfRow = placeShelfRow;
        this.placeShelfSubRow = placeShelfSubRow;
        this.placeShelfLayer = placeShelfLayer;
        this.batchNumber = batchNumber;
        this.syncState = syncState;
        this.oldValue = oldValue;
        this.searchValue = searchValue;
        this.guid = String.valueOf(id);
    }

    public WarehouseHandlingTable() {
    }



    public String getGoodCode() {
        return goodCode == null ? Commons.NULL : goodCode;
    }

    public String getSerial() {
        return serial == null ? Commons.NULL : serial;
    }

    public int getCountingNumber() {
        return countingNumber;
    }

    public int getGoodColumnNumber() {
        return goodColumnNumber;
    }

    //amount and sub defult valu is -1 = Commons.NULL_INTEGER
    public String getAmount() {
        return amount== null ? Commons.NULL_INTEGER : amount;
    }

    //amount and sub defult valu is 0
    public String getSubAmount() {
        return subAmount== null ? Commons.NULL_INTEGER : subAmount;
    }

    public String getGoodName() {
        return goodName == null ? Commons.NULL : goodName;
    }

    public String getBarCode() {
        return barCode == null ? Commons.NULL : barCode;
    }

    public String getMainUnitName() {
        return mainUnitName == null ? Commons.NULL : mainUnitName;
    }

    public String getSubUnitName() {
        return subUnitName == null ? Commons.NULL : subUnitName;
    }

    public String getPlaceShelfRow() {
        return placeShelfRow == null ? Commons.NULL : placeShelfRow;
    }

    public String getPlaceShelfSubRow() {
        return placeShelfSubRow == null ? Commons.NULL : placeShelfSubRow;
    }

    public String getPlaceShelfLayer() {
        return placeShelfLayer == null ? Commons.NULL : placeShelfLayer;
    }

    public String getBatchNumber() {
        return batchNumber == null ? Commons.NULL : batchNumber;
    }

    /**
     * @return : SYNCED because default from server is SYNCED
     */
    public String getSyncState() {
        return syncState == null ? CommonSyncState.SYNCED : syncState;
    }

    public String getOldValue() {
        return oldValue == null ? Commons.NULL : oldValue;
    }

    public String getSearchValue() {
        return searchValue == null ? Commons.NULL : searchValue;
    }

    public String getGuid() {
        return String.valueOf(id);
    }

    public String getId() {
        return id== null ? Commons.NULL_INTEGER : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setCountingNumber(int countingNumber) {
        this.countingNumber = countingNumber;
    }

    public void setGoodColumnNumber(int goodColumnNumber) {
        this.goodColumnNumber = goodColumnNumber;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setSubAmount(String subAmount) {
        this.subAmount = subAmount;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setMainUnitName(String mainUnitName) {
        this.mainUnitName = mainUnitName;
    }

    public void setSubUnitName(String subUnitName) {
        this.subUnitName = subUnitName;
    }

    public void setPlaceShelfRow(String placeShelfRow) {
        this.placeShelfRow = placeShelfRow;
    }

    public void setPlaceShelfSubRow(String placeShelfSubRow) {
        this.placeShelfSubRow = placeShelfSubRow;
    }

    public void setPlaceShelfLayer(String placeShelfLayer) {
        this.placeShelfLayer = placeShelfLayer;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
