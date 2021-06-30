package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * GOODS_DELIVERY_PROCEEDINGS_FORM_CODE
 *
 *
 * این جدول برای انتخاب کالای قابل خرید
 * یا در واقع دیتیل مربوط به مستر فرم 274
 * در زمان تهیه صورتمجلس تحویل کالا است
 * و یوزر باید مقادیر
 * مقدار
 * و فی
 * و تخفیف
 * و ارزش افزوده
 * را تکمیل نموده و به مستر ارسال نماید


 * tempAmount2 : برای کنترل مقدار قابل تغییر به صورت زیر
 * به محض ورود به مستر از فیلد اصلی به فیلد تمپ کپی شود
 * و در زمان دکمه تایید
 * مقدار فیلد تمپ به صورت زیر تغییر کند
 * در زمان ترک اضافه کردن تکی یا گروهی:
 * temp = temp-final+old(مثلدر انتخاب گروهی ممکن است که مقدار اولد صفر باشد)()
 * : با کلیک روی دکمه دیلیت موجود در دیتیل های موجود در مستر
 * temp = temp + final
 * با کلیک روی دکمه دیلیت اصلی فرم مستر:
 * temp = temp + final
 * و سپس فیلدهای تمپ به فیلدهای اصلی کپی شود
 */


public class PurchasableGoodsTable extends RealmObject implements IModels {


    @PrimaryKey
    private String guid;

    private int goodColumnNumber;

    private String
            id, code, goodCode, techInfo, goodName,
            unitName1, unitId, unitName2, numerator,denominator,
            amount2, tempAmount2, code6, name6, formNumber,
            formDate, description, length, width,
            height, alloy, expireDate, B5IdRefId15,
            B5IdRefId8, B5IdRefId9, B5IdRefId10,
            statusName, formName, yearCode,
            goodId, syncState, activityState,
            oldValue, attach;

    public PurchasableGoodsTable(String guid, String id, String code, String goodCode, String techInfo, String goodName, String unitName1, int goodColumnNumber, String unitId, String unitName2, String numerator,String denominator, String amount2, String tempAmount2, String code6, String name6, String formNumber, String formDate, String description, String length, String width, String height, String alloy, String expireDate, String b5IdRefId15, String b5IdRefId8, String b5IdRefId9, String b5IdRefId10, String statusName, String formName, String yearCode, String goodId, String syncState, String activityState, String oldValue, String attach) {
        this.guid = guid;
        this.id = id;
        this.code = code;
        this.goodCode = goodCode;
        this.techInfo = techInfo;
        this.goodName = goodName;
        this.unitName1 = unitName1;
        this.goodColumnNumber = goodColumnNumber;
        this.unitId = unitId;
        this.unitName2 = unitName2;
        this.amount2 = amount2;
        this.numerator = numerator;
        this.denominator = denominator;
        this.tempAmount2 = tempAmount2;
        this.code6 = code6;
        this.name6 = name6;
        this.formNumber = formNumber;
        this.formDate = formDate;
        this.description = description;
        this.length = length;
        this.width = width;
        this.height = height;
        this.alloy = alloy;
        this.expireDate = expireDate;
        this.B5IdRefId15 = b5IdRefId15;
        B5IdRefId8 = b5IdRefId8;
        B5IdRefId9 = b5IdRefId9;
        B5IdRefId10 = b5IdRefId10;
        this.statusName = statusName;
        this.formName = formName;
        this.yearCode = yearCode;
        this.goodId = goodId;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
        this.attach = attach;
    }

    public PurchasableGoodsTable() {
    }

    public String getGuid() {
        return guid == null ? Commons.NULL : guid;
    }

    public int getGoodColumnNumber() {
        return goodColumnNumber;
    }

    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getCode() {
        return code == null ? Commons.NULL : code;
    }

    public String getGoodCode() {
        return goodCode == null ? Commons.NULL : goodCode;
    }

    public String getTechInfo() {
        return techInfo == null ? Commons.NULL : techInfo;
    }

    public String getGoodName() {
        return goodName == null ? Commons.NULL : goodName;
    }

    public String getUnitName1() {
        return unitName1 == null ? Commons.NULL : unitName1;
    }

    public String getUnitId() {
        return unitId == null ? Commons.NULL_INTEGER : unitId;
    }

    public String getUnitName2() {
        return unitName2 == null ? Commons.NULL : unitName2;
    }

    public String getAmount2() {
        return amount2 == null ? Commons.NULL_INTEGER : amount2;
    }

    public String getTempAmount2() {
        return tempAmount2 == null ? Commons.NULL_INTEGER : tempAmount2;
    }

    public String getCode6() {
        return code6 == null ? Commons.NULL : code6;
    }

    public String getName6() {
        return name6 == null ? Commons.NULL : name6;
    }

    public String getFormNumber() {
        return formNumber == null ? Commons.NULL_INTEGER : formNumber;
    }

    public String getFormDate() {
        return formDate == null ? Commons.NULL : formDate;
    }

    public String getDescription() {
        return description == null ? Commons.NULL : description;
    }

    public String getLength() {
        return length == null ? Commons.NULL_INTEGER : length;
    }

    public String getWidth() {
        return width == null ? Commons.NULL_INTEGER : width;
    }

    public String getHeight() {
        return height == null ? Commons.NULL_INTEGER : height;
    }

    public String getAlloy() {
        return alloy == null ? Commons.NULL : alloy;
    }

    public String getExpireDate() {
        return expireDate == null ? Commons.NULL : expireDate;
    }

    public String getB5IdRefId15() {
        return B5IdRefId15 == null ? Commons.NULL_INTEGER : B5IdRefId15;
    }

    public String getB5IdRefId8() {
        return B5IdRefId8 == null ? Commons.NULL_INTEGER : B5IdRefId8;
    }

    public String getB5IdRefId9() {
        return B5IdRefId9 == null ? Commons.NULL_INTEGER : B5IdRefId9;
    }

    public String getB5IdRefId10() {
        return B5IdRefId10 == null ? Commons.NULL_INTEGER : B5IdRefId10;
    }

    public String getStatusName() {
        return statusName == null ? Commons.NULL : statusName;
    }

    public String getFormName() {
        return formName == null ? Commons.NULL : formName;
    }

    public String getYearCode() {
        return yearCode == null ? Commons.NULL_INTEGER : yearCode;
    }

    public String getGoodId() {
        return goodId == null ? Commons.NULL_INTEGER : goodId;
    }

    public String getSyncState() {
        return syncState == null ? CommonSyncState.BEFORE_SYNC : syncState;
    }

    public String getActivityState() {
        return activityState == null ? CommonActivityState.ADD : activityState;
    }

    public String getOldValue() {
        return oldValue == null ? Commons.NULL : oldValue;
    }

    public String getAttach() {
        return attach == null ? Commons.NULL_ARRAY : attach;
    }

    public String getNumerator() {
        return numerator == null ? Commons.NULL_INTEGER : numerator;
    }

    public String getDenominator() {
        return denominator == null ? Commons.NULL_INTEGER : denominator;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setGoodColumnNumber(int goodColumnNumber) {
        this.goodColumnNumber = goodColumnNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public void setTechInfo(String techInfo) {
        this.techInfo = techInfo;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public void setUnitName1(String unitName1) {
        this.unitName1 = unitName1;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setUnitName2(String unitName2) {
        this.unitName2 = unitName2;
    }

    public void setAmount2(String amount2) {
        this.amount2 = amount2;
    }

    public void setCode6(String code6) {
        this.code6 = code6;
    }

    public void setName6(String name6) {
        this.name6 = name6;
    }

    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
    }

    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setAlloy(String alloy) {
        this.alloy = alloy;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setB5IdRefId15(String b5IdRefId15) {
        B5IdRefId15 = b5IdRefId15;
    }

    public void setB5IdRefId8(String b5IdRefId8) {
        B5IdRefId8 = b5IdRefId8;
    }

    public void setB5IdRefId9(String b5IdRefId9) {
        B5IdRefId9 = b5IdRefId9;
    }

    public void setB5IdRefId10(String b5IdRefId10) {
        B5IdRefId10 = b5IdRefId10;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }


    public void setGoodId(String goodId) {
        this.goodId = goodId;
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

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public void setTempAmount2(String tempAmount2) {
        this.tempAmount2 = tempAmount2;
    }

    public void setNumerator(String numerator) {
        this.numerator = numerator;
    }

    public void setDenominator(String denominator) {
        this.denominator = denominator;
    }
}
