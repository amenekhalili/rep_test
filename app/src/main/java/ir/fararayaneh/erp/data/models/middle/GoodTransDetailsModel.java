package ir.fararayaneh.erp.data.models.middle;

import java.io.Serializable;

import ir.fararayaneh.erp.data.models.IModels;

/**
 * in relation with GoodTrans activity
 */
public class GoodTransDetailsModel implements IModels , Serializable {

    private int goodColumnNumber;
    private String id,idRecallDetail,guid
    , goodId,C5UnitId,length,width,height,alloy
    ,requestAmount1,requestAmount2,amount,amount2
    ,currencyUnitPrice2,currencyUnitPrice1,currencyTotalValue
    ,discountPercentage,currencyDiscount1,currencyDiscount2
    ,taxPercentage,currencyTaxValue,currencyNetValue
    ,currencyNetUnitPrice2,B5IdRefId8,B5IdRefId9,B5IdRefId10,B5IdRefId15
    ,batchNumber,I5BOMId;

    public GoodTransDetailsModel(String id,String idRecallDetail,int goodColumnNumber,String guid, String goodId, String c5UnitId, String length, String width, String height, String alloy, String requestAmount1, String requestAmount2, String amount, String amount2, String currencyUnitPrice2, String currencyUnitPrice1, String currencyTotalValue, String discountPercentage, String currencyDiscount1, String currencyDiscount2, String taxPercentage, String currencyTaxValue, String currencyNetValue, String currencyNetUnitPrice2, String b5IdRefId8, String b5IdRefId9, String b5IdRefId10, String b5IdRefId15, String batchNumber, String i5BOMId) {
        this.id = id;
        this.idRecallDetail = idRecallDetail;
        this.goodColumnNumber = goodColumnNumber;
        this.guid = guid;
        this.goodId = goodId;
        C5UnitId = c5UnitId;
        this.length = length;
        this.width = width;
        this.height = height;
        this.alloy = alloy;
        this.requestAmount1 = requestAmount1;
        this.requestAmount2 = requestAmount2;
        this.amount = amount;
        this.amount2 = amount2;
        this.currencyUnitPrice2 = currencyUnitPrice2;
        this.currencyUnitPrice1 = currencyUnitPrice1;
        this.currencyTotalValue = currencyTotalValue;
        this.discountPercentage = discountPercentage;
        this.currencyDiscount1 = currencyDiscount1;
        this.currencyDiscount2 = currencyDiscount2;
        this.taxPercentage = taxPercentage;
        this.currencyTaxValue = currencyTaxValue;
        this.currencyNetValue = currencyNetValue;
        this.currencyNetUnitPrice2 = currencyNetUnitPrice2;
        B5IdRefId8 = b5IdRefId8;
        B5IdRefId9 = b5IdRefId9;
        B5IdRefId10 = b5IdRefId10;
        B5IdRefId15 = b5IdRefId15;
        this.batchNumber = batchNumber;
        I5BOMId = i5BOMId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdRecallDetail() {
        return idRecallDetail;
    }

    public void setIdRecallDetail(String idRecallDetail) {
        this.idRecallDetail = idRecallDetail;
    }

    public int getGoodColumnNumber() {
        return goodColumnNumber;
    }

    public void setGoodColumnNumber(int goodColumnNumber) {
        this.goodColumnNumber = goodColumnNumber;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getC5UnitId() {
        return C5UnitId;
    }

    public void setC5UnitId(String c5UnitId) {
        C5UnitId = c5UnitId;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAlloy() {
        return alloy;
    }

    public void setAlloy(String alloy) {
        this.alloy = alloy;
    }

    public String getRequestAmount1() {
        return requestAmount1;
    }

    public void setRequestAmount1(String requestAmount1) {
        this.requestAmount1 = requestAmount1;
    }

    public String getRequestAmount2() {
        return requestAmount2;
    }

    public void setRequestAmount2(String requestAmount2) {
        this.requestAmount2 = requestAmount2;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount2() {
        return amount2;
    }

    public void setAmount2(String amount2) {
        this.amount2 = amount2;
    }

    public String getCurrencyUnitPrice2() {
        return currencyUnitPrice2;
    }

    public void setCurrencyUnitPrice2(String currencyUnitPrice2) {
        this.currencyUnitPrice2 = currencyUnitPrice2;
    }

    public String getCurrencyUnitPrice1() {
        return currencyUnitPrice1;
    }

    public void setCurrencyUnitPrice1(String currencyUnitPrice1) {
        this.currencyUnitPrice1 = currencyUnitPrice1;
    }

    public String getCurrencyTotalValue() {
        return currencyTotalValue;
    }

    public void setCurrencyTotalValue(String currencyTotalValue) {
        this.currencyTotalValue = currencyTotalValue;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getCurrencyDiscount1() {
        return currencyDiscount1;
    }

    public void setCurrencyDiscount1(String currencyDiscount1) {
        this.currencyDiscount1 = currencyDiscount1;
    }

    public String getCurrencyDiscount2() {
        return currencyDiscount2;
    }

    public void setCurrencyDiscount2(String currencyDiscount2) {
        this.currencyDiscount2 = currencyDiscount2;
    }

    public String getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(String taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public String getCurrencyTaxValue() {
        return currencyTaxValue;
    }

    public void setCurrencyTaxValue(String currencyTaxValue) {
        this.currencyTaxValue = currencyTaxValue;
    }

    public String getCurrencyNetValue() {
        return currencyNetValue;
    }

    public void setCurrencyNetValue(String currencyNetValue) {
        this.currencyNetValue = currencyNetValue;
    }

    public String getCurrencyNetUnitPrice2() {
        return currencyNetUnitPrice2;
    }

    public void setCurrencyNetUnitPrice2(String currencyNetUnitPrice2) {
        this.currencyNetUnitPrice2 = currencyNetUnitPrice2;
    }

    public String getB5IdRefId8() {
        return B5IdRefId8;
    }

    public void setB5IdRefId8(String b5IdRefId8) {
        B5IdRefId8 = b5IdRefId8;
    }

    public String getB5IdRefId9() {
        return B5IdRefId9;
    }

    public void setB5IdRefId9(String b5IdRefId9) {
        B5IdRefId9 = b5IdRefId9;
    }

    public String getB5IdRefId10() {
        return B5IdRefId10;
    }

    public void setB5IdRefId10(String b5IdRefId10) {
        B5IdRefId10 = b5IdRefId10;
    }

    public String getB5IdRefId15() {
        return B5IdRefId15;
    }

    public void setB5IdRefId15(String b5IdRefId15) {
        B5IdRefId15 = b5IdRefId15;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getI5BOMId() {
        return I5BOMId;
    }

    public void setI5BOMId(String i5BOMId) {
        I5BOMId = i5BOMId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
