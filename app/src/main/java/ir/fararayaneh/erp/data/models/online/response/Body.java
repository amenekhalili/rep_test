package ir.fararayaneh.erp.data.models.online.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * financialStartDate
 * financialEndDate
 * این زمان ها در قالب
 * date
 * دریافت شده (در واقع همان استرینگ که شکل زمان دارد) و به صورت
 * long
 * در
 * sharedPreference
 * ذخیره میشود
 * ------------------------------------------------------------------------
 * forceLocation -->> در بعضی فرم ها که دریافت لوکیشن کاربر ضروری نیست مانند فرم تایم شیت
 * و از روی مقدار پارامتر
 * forceLocation
 * تعیین میشود که لوکیشن کاربر را بگیریم یا خیر
 * 1---> دریافت لوکیشن
 * 0---> عدم دریافت لوکیشن
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Body {
    @JsonProperty("userImageUrl")
    private String userImageUrl;
    @JsonProperty("forceLocation")
    private Integer forceLocation;
    @JsonProperty("financialStartDate")
    private String financialStartDate;
    @JsonProperty("financialEndDate")
    private String financialEndDate;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("nodeServerIP")
    private String nodeServerIP;
    @JsonProperty("nodeServerPort")
    private String nodeServerPort;
    @JsonProperty("nodeRestServerPort")
    private String nodeRestServerPort;
    @JsonProperty("onlineServerIP")
    private String onlineServerIP;
    @JsonProperty("attachBaseURL")
    private String attachBaseURL;
    @JsonProperty("sellEmporiumId")
    private String sellEmporiumId;
    @JsonProperty("sellEmporiumName")
    private String sellEmporiumName;
    @JsonProperty("monetaryUnitId")
    private String monetaryUnitId;
    @JsonProperty("monetaryUnitName")
    private String monetaryUnitName;
    @JsonProperty("displayableWarehouseList")//list of warehouse that can show width, height and so on
    private String displayableWarehouseList;
    @JsonProperty("countryDivisionId")
    private String countryDivisionId;
    @JsonProperty("statusIdDefault")
    private String statusIdDefault;
    @JsonProperty("taxPercent")
    private int taxPercent;
    @JsonProperty("precisionAmountLength")
    private int precisionAmountLength;
    @JsonProperty("precisionPriceLength")
    private int precisionPriceLength;
    @JsonProperty("isOld")
    private String isOld;


    @JsonProperty("isOld")
    public String getIsOld() {
        return isOld;
    }

    @JsonProperty("isOld")
    public void setIsOld(String isOld) {
        this.isOld = isOld;
    }

    @JsonProperty("displayableWarehouseList")
    public String getDisplayableWarehouseList() {
        return displayableWarehouseList;
    }

    @JsonProperty("displayableWarehouseList")
    public void setDisplayableWarehouseList(String displayableWarehouseList) {
        this.displayableWarehouseList = displayableWarehouseList;
    }

    @JsonProperty("precisionAmountLength")
    public int getPrecisionAmountLength() {
        return precisionAmountLength;
    }

    @JsonProperty("precisionAmountLength")
    public void setPrecisionAmountLength(int precisionAmountLength) {
        this.precisionAmountLength = precisionAmountLength;
    }

    @JsonProperty("precisionPriceLength")
    public int getPrecisionPriceLength() {
        return precisionPriceLength;
    }

    @JsonProperty("precisionPriceLength")
    public void setPrecisionPriceLength(int precisionPriceLength) {
        this.precisionPriceLength = precisionPriceLength;
    }

    @JsonProperty("taxPercent")
    public int getTaxPercent() {
        return taxPercent;
    }

    @JsonProperty("taxPercent")
    public void setTaxPercent(int taxPercent) {
        this.taxPercent = taxPercent;
    }
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("userImageUrl")
    public String getUserImageUrl() {
        return userImageUrl;
    }

    @JsonProperty("userImageUrl")
    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    @JsonProperty("forceLocation")
    public Integer getForceLocation() {
        return forceLocation;
    }

    @JsonProperty("forceLocation")
    public void setForceLocation(Integer forceLocation) {
        this.forceLocation = forceLocation;
    }

    @JsonProperty("financialStartDate")
    public String getFinancialStartDate() {
        return financialStartDate;
    }

    @JsonProperty("financialStartDate")
    public void setFinancialStartDate(String financialStartDate) {
        this.financialStartDate = financialStartDate;
    }

    @JsonProperty("financialEndDate")
    public String getFinancialEndDate() {
        return financialEndDate;
    }

    @JsonProperty("financialEndDate")
    public void setFinancialEndDate(String financialEndDate) {
        this.financialEndDate = financialEndDate;
    }

    @JsonProperty("companyName")
    public String getCompanyName() {
        return companyName;
    }

    @JsonProperty("companyName")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @JsonProperty("nodeServerIP")
    public String getNodeServerIP() {
        return nodeServerIP;
    }

    @JsonProperty("nodeServerIP")
    public void setNodeServerIP(String nodeServerIP) {
        this.nodeServerIP = nodeServerIP;
    }



    @JsonProperty("nodeServerPort")
    public String getNodeServerPort() {
        return nodeServerPort;
    }

    @JsonProperty("nodeServerPort")
    public void setNodeServerPort(String nodeServerPort) {
        this.nodeServerPort = nodeServerPort;
    }

    @JsonProperty("nodeRestServerPort")
    public String getNodeRestServerPort() {
        return nodeRestServerPort;
    }

    @JsonProperty("nodeRestServerPort")
    public void setNodeRestServerPort(String nodeRestServerPort) {
        this.nodeRestServerPort = nodeRestServerPort;
    }

    @JsonProperty("onlineServerIP")
    public String getOnlineServerIP() {
        return onlineServerIP;
    }

    @JsonProperty("onlineServerIP")
    public void setOnlineServerIP(String onlineServerIP) {
        this.onlineServerIP = onlineServerIP;
    }

    @JsonProperty("attachBaseURL")
    public String getAttachBaseURL() {
        return attachBaseURL;
    }

    @JsonProperty("attachBaseURL")
    public void setAttachBaseURL(String attachBaseURL) {
        this.attachBaseURL = attachBaseURL;
    }

    @JsonProperty("sellEmporiumId")
    public String getSellEmporiumId() {
        return sellEmporiumId;
    }

    @JsonProperty("sellEmporiumId")
    public void setSellEmporiumId(String sellEmporiumId) {
        this.sellEmporiumId = sellEmporiumId;
    }

    @JsonProperty("monetaryUnitId")
    public String getMonetaryUnitId() {
        return monetaryUnitId;
    }

    @JsonProperty("monetaryUnitId")
    public void setMonetaryUnitId(String monetaryUnitId) {
        this.monetaryUnitId = monetaryUnitId;
    }



    @JsonProperty("countryDivisionId")
    public String getCountryDivisionId() {
        return countryDivisionId;
    }

    @JsonProperty("countryDivisionId")
    public void setCountryDivisionId(String countryDivisionId) {
        this.countryDivisionId = countryDivisionId;
    }

    @JsonProperty("statusIdDefault")
    public String getStatusIdDefault() {
        return statusIdDefault;
    }

    @JsonProperty("statusIdDefault")
    public void setStatusIdDefault(String statusIdDefault) {
        this.statusIdDefault = statusIdDefault;
    }

    @JsonProperty("sellEmporiumName")
    public String getSellEmporiumName() {
        return sellEmporiumName;
    }

    @JsonProperty("sellEmporiumName")
    public void setSellEmporiumName(String sellEmporiumName) {
        this.sellEmporiumName = sellEmporiumName;
    }

    @JsonProperty("monetaryUnitName")
    public String getMonetaryUnitName() {
        return monetaryUnitName;
    }

    @JsonProperty("monetaryUnitName")
    public void setMonetaryUnitName(String monetaryUnitName) {
        this.monetaryUnitName = monetaryUnitName;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}




