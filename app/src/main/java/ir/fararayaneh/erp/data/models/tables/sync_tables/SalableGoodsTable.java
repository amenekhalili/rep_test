package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;


/**
 * form code BUYER_REQUEST_FORM_CODE
 * <p>
 * baseAmountForGift,مقداری که اگر یوزر خریداری کند مستحق دریافت اشنانتیون میشود مثلا به ازای هر 10 پفک  دو آدامس بگیرد
 * giftAmount,مقدار اشنانتیونی که یوزر به ازای 10 پفک میگیرد در اینجا میشود 2 تا ادامس
 * expireDate,تاریخی که در آن اعتبار این رکورد تمام میشود
 * tempAvailableAmount2 : به توضیحات tempAmount2 در جدول PurchasableGoodsTable مراجعه شود
 * <p>
 * unitPrice ==> نقش قیمت واحد دو را دارد
 */
public class SalableGoodsTable extends RealmObject implements IModels {

    private int goodColumnNumber;

    @PrimaryKey
    private String guid;

    private String id, customerGroupId, goodId, code,
            goodCode, techInfo, goodName,
            unitName, unitId2, unitName2,
            description, unitPrice, length,
            width, height, alloy, goodBrandId,
            discountPercentage, taxPercentage,
            availableAmount2, tempAvailableAmount2, numerator,
            denominator, baseAmountForGift,
            goodsIdGift, giftAmount, expireDate,
            syncState, activityState, oldValue;

    public SalableGoodsTable(int goodColumnNumber, String guid, String id, String customerGroupId, String goodId, String code, String goodCode, String techInfo, String goodName, String unitName, String unitId2, String unitName2, String description, String unitPrice, String length, String width, String height, String alloy, String goodBrandId, String discountPercentage, String taxPercentage, String availableAmount2, String tempAvailableAmount2, String numerator, String denominator, String baseAmountForGift, String goodsIdGift, String giftAmount, String expireDate, String syncState, String activityState, String oldValue) {
        this.goodColumnNumber = goodColumnNumber;
        this.guid = guid;
        this.id = id;
        this.customerGroupId = customerGroupId;
        this.goodId = goodId;
        this.code = code;
        this.goodCode = goodCode;
        this.techInfo = techInfo;
        this.goodName = goodName;
        this.unitName = unitName;
        this.unitId2 = unitId2;
        this.unitName2 = unitName2;
        this.description = description;
        this.unitPrice = unitPrice;
        this.length = length;
        this.width = width;
        this.height = height;
        this.alloy = alloy;
        this.goodBrandId = goodBrandId;
        this.discountPercentage = discountPercentage;
        this.taxPercentage = taxPercentage;
        this.availableAmount2 = availableAmount2;
        this.tempAvailableAmount2 = tempAvailableAmount2;
        this.numerator = numerator;
        this.denominator = denominator;
        this.baseAmountForGift = baseAmountForGift;
        this.goodsIdGift = goodsIdGift;
        this.giftAmount = giftAmount;
        this.expireDate = expireDate;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
    }

    public SalableGoodsTable() {
    }

    public void setGoodColumnNumber(int goodColumnNumber) {
        this.goodColumnNumber = goodColumnNumber;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
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

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setUnitId2(String unitId2) {
        this.unitId2 = unitId2;
    }

    public void setUnitName2(String unitName2) {
        this.unitName2 = unitName2;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
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

    public void setGoodBrandId(String goodBrandId) {
        this.goodBrandId = goodBrandId;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setTaxPercentage(String taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public void setAvailableAmount2(String availableAmount2) {
        this.availableAmount2 = availableAmount2;
    }

    public void setTempAvailableAmount2(String tempAvailableAmount2) {
        this.tempAvailableAmount2 = tempAvailableAmount2;
    }

    public void setNumerator(String numerator) {
        this.numerator = numerator;
    }

    public void setDenominator(String denominator) {
        this.denominator = denominator;
    }

    public void setBaseAmountForGift(String baseAmountForGift) {
        this.baseAmountForGift = baseAmountForGift;
    }

    public void setGoodsIdGift(String goodsIdGift) {
        this.goodsIdGift = goodsIdGift;
    }

    public void setGiftAmount(String giftAmount) {
        this.giftAmount = giftAmount;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
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
    //---------------------------------------------------------------------

    public int getGoodColumnNumber() {
        return goodColumnNumber;
    }

    public String getGuid() {
        return guid == null ? Commons.NULL : guid;
    }

    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getCustomerGroupId() {
        return customerGroupId == null ? Commons.NULL_INTEGER : customerGroupId;
    }

    public String getGoodId() {
        return goodId == null ? Commons.NULL_INTEGER : goodId;
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

    public String getUnitName() {
        return unitName == null ? Commons.NULL : unitName;
    }

    public String getUnitId2() {
        return unitId2 == null ? Commons.NULL_INTEGER : unitId2;
    }

    public String getUnitName2() {
        return unitName2 == null ? Commons.NULL : unitName2;
    }

    public String getDescription() {
        return description == null ? Commons.NULL : description;
    }

    public String getUnitPrice() {
        return unitPrice == null ? Commons.NULL_INTEGER : unitPrice;
    }

    public String getLength() {
        return length == null ? Commons.NULL_INTEGER : length;
    }

    public String getWidth() {
        return width == null ? Commons.NULL_INTEGER : width;
    }

    public String getHeight() {
        return height == null ? Commons.NULL : height;
    }

    public String getAlloy() {
        return alloy == null ? Commons.NULL : alloy;
    }

    public String getGoodBrandId() {
        return goodBrandId == null ? Commons.NULL_INTEGER : goodBrandId;
    }

    public String getDiscountPercentage() {
        return discountPercentage == null ? Commons.NULL_INTEGER : discountPercentage;
    }

    public String getTaxPercentage() {
        return taxPercentage == null ? Commons.NULL_INTEGER : taxPercentage;
    }

    public String getAvailableAmount2() {
        return availableAmount2 == null ? Commons.NULL_INTEGER : availableAmount2;
    }

    public String getTempAvailableAmount2() {
        return tempAvailableAmount2 == null ? Commons.NULL_INTEGER : tempAvailableAmount2;
    }

    public String getNumerator() {
        return numerator == null ? Commons.NULL_INTEGER : numerator;
    }

    public String getDenominator() {
        return denominator == null ? Commons.NULL_INTEGER : denominator;
    }

    public String getBaseAmountForGift() {
        return baseAmountForGift == null ? Commons.NULL_INTEGER : baseAmountForGift;
    }

    public String getGoodsIdGift() {
        return goodsIdGift == null ? Commons.NULL_INTEGER : goodsIdGift;
    }

    public String getGiftAmount() {
        return giftAmount == null ? Commons.NULL_INTEGER : giftAmount;
    }

    public String getExpireDate() {
        return expireDate == null ? Commons.NULL : expireDate;
    }

    //we get data from server a synced data
    public String getSyncState() {
        return syncState == null ? CommonSyncState.SYNCED : syncState;
    }

    //we get data from server a added data
    public String getActivityState() {
        return activityState == null ? CommonActivityState.ADD : activityState;
    }

    public String getOldValue() {
        return oldValue == null ? Commons.NULL : oldValue;
    }
}
