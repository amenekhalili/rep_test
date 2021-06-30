package ir.fararayaneh.erp.data.models.online.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import ir.fararayaneh.erp.data.models.IModels;


/**
 * dar in class field haye date be
 * sorate string daryaft mishavad
 * va be sorate string dar database zakhire mishavad
 */


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item implements IModels {



    //---------all field in RootModel.class
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("organization")
    private String organization;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("appName")
    private String appName;
    @JsonProperty("hasFile")
    private Integer hasFile;
    @JsonProperty("lang")
    private String lang;
    @JsonProperty("pageNo")
    private Integer pageNo;
    @JsonProperty("pageItem")
    private Integer pageItem;
    @JsonProperty("chatroomId")
    private String chatroomId;
    @JsonProperty("nodeReceivedDate")
    private String nodeReceivedDate;
    @JsonProperty("errorNumber")
    private String errorNumber;
    //---------all field in RootModel.class

    @JsonProperty("guid")
    private String guid;
    @JsonProperty("formId")
    private String formId;
    @JsonProperty("formName")
    private String formName;
    @JsonProperty("formTitle")
    private String formTitle;
    @JsonProperty("formCode")
    private String formCode;
    @JsonProperty("totalCount")
    private Integer totalCount;
    @JsonProperty("file")
    private String file;
    @JsonProperty("configIdRef1")
    private String configIdRef1;
    @JsonProperty("configIdRef2")
    private String configIdRef2;
    @JsonProperty("configIdRef3")
    private String configIdRef3;
    @JsonProperty("configIdRef4")
    private String configIdRef4;
    @JsonProperty("configIdRef5")
    private String configIdRef5;
    @JsonProperty("configIdRef6")
    private String configIdRef6;
    @JsonProperty("configIdRef7")
    private String configIdRef7;
    @JsonProperty("configIdRef8")
    private String configIdRef8;
    @JsonProperty("configIdRef9")
    private String configIdRef9;
    @JsonProperty("configIdRef10")
    private String configIdRef10;
    @JsonProperty("configIdRef11")
    private String configIdRef11;
    @JsonProperty("configIdRef12")
    private String configIdRef12;
    @JsonProperty("configIdRef13")
    private String configIdRef13;
    @JsonProperty("configIdRef14")
    private String configIdRef14;
    @JsonProperty("configIdRef15")
    private String configIdRef15;
    @JsonProperty("iconUrl")
    private String iconUrl;
    @JsonProperty("id")
    private String id;
    @JsonProperty("J5OrgChartId")
    private String J5OrgChartId;
    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
    @JsonProperty("field")
    private String field;
    @JsonProperty("part")
    private String part;
    @JsonProperty("surface")
    private String surface;
    @JsonProperty("rowCount")
    private String rowCount;
    @JsonProperty("segmentCount")
    private String segmentCount;
    @JsonProperty("description")
    private String description;
    @JsonProperty("geoLocation")
    private String geoLocation;
    @JsonProperty("parentId")
    private String parentId;
    @JsonProperty("parentName")
    private String parentName;
    @JsonProperty("parentCode")
    private String parentCode;
    @JsonProperty("B5IdRefIds")
    private String B5IdRefIds;

    @JsonProperty("B5IdRefId")
    private String B5IdRefId;
    @JsonProperty("groupId")
    private String groupId;
    @JsonProperty("isMain")
    private Integer isMain;
    @JsonProperty("parentTypeId")
    private String parentTypeId;

    @JsonProperty("B5FormRefId")
    private String B5FormRefId;
    @JsonProperty("nationalityId")
    private String nationalityId;
    @JsonProperty("isActive")
    private Integer isActive;
    @JsonProperty("activitySubject")
    private String activitySubject;
    @JsonProperty("techCode")
    private String techCode;

    @JsonProperty("latinName")
    private String latinName;
    @JsonProperty("nationalityCode")
    private String nationalityCode;
    @JsonProperty("googleKeyWord")
    private String googleKeyWord;
    @JsonProperty("techInfo")
    private String techInfo;
    @JsonProperty("C5UnitId")
    private String C5UnitId;
    @JsonProperty("unitName")
    private String unitName;
    @JsonProperty("C5BrandId")
    private String C5BrandId;
    @JsonProperty("C5GoodsId")
    private String C5GoodsId;
    @JsonProperty("brandName")
    private String brandName;
    @JsonProperty("width")
    private String width;
    @JsonProperty("height")
    private String height;
    @JsonProperty("length")
    private String length;
    @JsonProperty("weight")
    private String weight;
    @JsonProperty("barCode")
    private String barCode;
    @JsonProperty("B5HCStatusId")
    private String B5HCStatusId;
    @JsonProperty("statusName")
    private String statusName;
    @JsonProperty("body")
    private List<Body> body = null;

    @JsonProperty("U5UserId")
    private String U5UserId;
    @JsonProperty("B5HCUserTypeId")
    private String B5HCUserTypeId;
    @JsonProperty("G5ChatroomId")
    private String G5ChatroomId;
    @JsonProperty("activityKind")
    private Integer activityKind;
    @JsonProperty("B5HCChatTypeId")
    private String B5HCChatTypeId;
    @JsonProperty("G5MessageId")
    private String G5MessageId;
    @JsonProperty("A5AttachmentId")
    private Integer A5AttachmentId;
    @JsonProperty("B5HCMessageTypeId")
    private String B5HCMessageTypeId;
    @JsonProperty("B5HCReceiverStatusId")
    private String B5HCReceiverStatusId;

    @JsonProperty("insertDate")
    private String insertDate;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("sendDate")
    private String sendDate;
    @JsonProperty("receivedDate")
    private String receivedDate;
    @JsonProperty("seenDate")
    private String seenDate;
    @JsonProperty("typeId")
    private String typeId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("attach")
    private String attach;

    @JsonProperty("receivers")
    private String receivers;

    @JsonProperty("B5IdRefId1")
    private String B5IdRefId1;
    @JsonProperty("B5IdRefId2")
    private String B5IdRefId2;
    @JsonProperty("definitionId")
    private int definitionId;

    @JsonProperty("B5HCPriorityName")
    private int B5HCPriorityName;
    @JsonProperty("definitionName")
    private String definitionName;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("senderName")
    private String senderName;
    @JsonProperty("B5FormRefTitle")
    private String B5FormRefTitle;


    @JsonProperty("goodCode")
    private String goodCode;
    @JsonProperty("serial")
    private String serial;
    @JsonProperty("countingNumber")
    private int countingNumber;
    @JsonProperty("goodColumnNumber")
    private int goodColumnNumber;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("subAmount")
    private String subAmount;

    @JsonProperty("goodName")
    private String goodName;
    @JsonProperty("mainUnitName")
    private String mainUnitName;
    @JsonProperty("subUnitName")
    private String subUnitName;
    @JsonProperty("placeShelfRow")
    private String placeShelfRow;
    @JsonProperty("placeShelfSubRow")
    private String placeShelfSubRow;
    @JsonProperty("placeShelfLayer")
    private String placeShelfLayer;
    @JsonProperty("batchNumber")
    private String batchNumber;
    @JsonProperty("syncState")
    private String syncState;


    @JsonProperty("carPlaqueNumber")
    private String carPlaqueNumber;
    @JsonProperty("buyerName")
    private String buyerName;
    @JsonProperty("goodTranseDate")
    private String goodTranseDate;
    @JsonProperty("driverName")
    private String driverName;
    @JsonProperty("emptyDate")
    private String emptyDate;
    @JsonProperty("goodTranseNumber")
    private String goodTranseNumber;
    @JsonProperty("emptyWeight")
    private String emptyWeight;
    @JsonProperty("goodTranseAmount")
    private String goodTranseAmount;
    @JsonProperty("goodTranseWeight")
    private String goodTranseWeight;
    @JsonProperty("sumWeight")
    private String sumWeight;
    @JsonProperty("balance")
    private String balance;
    @JsonProperty("appVersion")
    private int appVersion;
    @JsonProperty("oldValue")
    private String oldValue;
    @JsonProperty("searchValue")
    private String searchValue;


    @JsonProperty("B5formRefId")
    private String B5formRefId;
    @JsonProperty("B5IdRefId3")
    private String B5IdRefId3;
    @JsonProperty("B5HCDailyScheduleId")
    private String B5HCDailyScheduleId;
    @JsonProperty("B5HCWageTitleId")
    private String B5HCWageTitleId;
    @JsonProperty("isHappeningAtTheSameTime")
    private int isHappeningAtTheSameTime;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("activityState")
    private String activityState;
    @JsonProperty("GTCode")
    private String GTCode;
    @JsonProperty("alloy")
    private String alloy;


    @JsonProperty("B5IdRefId4")
    private String B5IdRefId4;
    @JsonProperty("B5IdRefId5")
    private String B5IdRefId5;
    @JsonProperty("B5IdRefId6")
    private String B5IdRefId6;
    @JsonProperty("B5IdRefId14")
    private String B5IdRefId14;
    @JsonProperty("B5HCCurrencyId")
    private String B5HCCurrencyId;
    @JsonProperty("B5HCSellMethodId")
    private String B5HCSellMethodId;
    @JsonProperty("B5HCAccountSideId")
    private String B5HCAccountSideId;
    @JsonProperty("B5IdRefIdRecall")
    private String B5IdRefIdRecall;
    @JsonProperty("deliveryName")
    private String deliveryName;
    @JsonProperty("formNumber")
    private String formNumber;
    @JsonProperty("formDate")
    private String formDate;
    @JsonProperty("expireDate")
    private String expireDate;
    @JsonProperty("secondaryDate")
    private String secondaryDate;
    @JsonProperty("goodTransDetail")
    private String goodTransDetail;
    @JsonProperty("duration")
    private int duration;


    @JsonProperty("unitName1")
    private String unitName1;
    @JsonProperty("unitId")
    private String unitId;
    @JsonProperty("unitName2")
    private String unitName2;
    @JsonProperty("amount2")
    private String amount2;
    @JsonProperty("code6")
    private String code6;
    @JsonProperty("name6")
    private String name6;
    @JsonProperty("warehouseId3")
    private String warehouseId3;
    @JsonProperty("B5IdRefId8")
    private String B5IdRefId8;
    @JsonProperty("B5IdRefId9")
    private String B5IdRefId9;
    @JsonProperty("B5IdRefId10")
    private String B5IdRefId10;
    @JsonProperty("yearCode")
    private String yearCode;
    @JsonProperty("branchId")
    private String branchId;
    @JsonProperty("warehouseId")
    private String warehouseId;
    @JsonProperty("goodId")
    private String goodId;
    @JsonProperty("customerGroupId")
    private String customerGroupId;
    @JsonProperty("unitId2")
    private String unitId2;
    @JsonProperty("unitPrice")
    private String unitPrice;
    @JsonProperty("goodBrandId")
    private String goodBrandId;
    @JsonProperty("discountPercentage")
    private String discountPercentage;
    @JsonProperty("taxPercentage")
    private String taxPercentage;
    @JsonProperty("availableAmount2")
    private String availableAmount2;
    @JsonProperty("numerator")
    private String numerator;
    @JsonProperty("denominator")
    private String denominator;
    @JsonProperty("baseAmountForGift")
    private String baseAmountForGift;
    @JsonProperty("goodsIdGift")
    private String goodsIdGift;
    @JsonProperty("giftAmount")
    private String giftAmount;
    @JsonProperty("addressId")
    private String addressId;
    @JsonProperty("B5HCTypeId")
    private String B5HCTypeId;
    @JsonProperty("value")
    private String value;
    @JsonProperty("B5CountryDivisionId")
    private String B5CountryDivisionId;
    @JsonProperty("address")
    private String address;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("B5HCOwnershipId")
    private String B5HCOwnershipId;
    @JsonProperty("B5HCAddressTypeId")
    private String B5HCAddressTypeId;
    @JsonProperty("startOwnedDate")
    private String startOwnedDate;
    @JsonProperty("stopOwnedDate")
    private String stopOwnedDate;
    @JsonProperty("region")
    private String region;
    @JsonProperty("location")
    private String location;
    @JsonProperty("goodSUOMList")
    private String goodSUOMList;
    @JsonProperty("goodTypeList")
    private String goodTypeList;
    @JsonProperty("mainMenuActive")
    private String mainMenuActive;
    @JsonProperty("rootId")
    private String rootId;
    @JsonProperty("isMute")
    private String isMute;
    @JsonProperty("isOnline")
    private String isOnline;
    @JsonProperty("isTyping")
    private String isTyping;
    @JsonProperty("unSeenCount")
    private String unSeenCount;


    @JsonProperty("deleteForMe")
    private String deleteForMe;
    @JsonProperty("seenCount")
    private String seenCount;
    @JsonProperty("receivedCount")
    private String receivedCount;
    @JsonProperty("chatroomGUID")
    private String chatroomGUID;




    @JsonProperty("branchName")
    private String branchName;
    @JsonProperty("driverId")
    private String driverId;
    @JsonProperty("wareHouseName")
    private String wareHouseName;
    @JsonProperty("fuelName")
    private String fuelName;
    @JsonProperty("placeName")
    private String placeName;
    @JsonProperty("deviceName")
    private String deviceName;
    @JsonProperty("B5HCDistributionId")
    private String B5HCDistributionI;
    @JsonProperty("distributionDate")
    private String distributionDate;
    @JsonProperty("masterId")
    private String masterId;
    @JsonProperty("departmentName")
    private String departmentName;

    @JsonProperty("formLocation")
    private int formLocation;

    @JsonProperty("departmentName")
    public String getDepartmentName() {
        return departmentName;
    }

    @JsonProperty("departmentName")
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @JsonProperty("B5HCDistributionI")
    public String getB5HCDistributionI() {
        return B5HCDistributionI;
    }

    @JsonProperty("B5HCDistributionI")
    public void setB5HCDistributionI(String B5HCDistributionI) {
        B5HCDistributionI = B5HCDistributionI;
    }

    @JsonProperty("distributionDate")
    public String getDistributionDate() {
        return distributionDate;
    }

    @JsonProperty("distributionDate")
    public void setDistributionDate(String distributionDate) {
        this.distributionDate = distributionDate;
    }

    @JsonProperty("masterId")
    public String getMasterId() {
        return masterId;
    }

    @JsonProperty("masterId")
    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    @JsonProperty("placeName")
    public String getPlaceName() {
        return placeName;
    }

    @JsonProperty("placeName")
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @JsonProperty("deviceName")
    public String getDeviceName() {
        return deviceName;
    }

    @JsonProperty("deviceName")
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @JsonProperty("formLocation")
    public int getFormLocation() {
        return formLocation;
    }

    @JsonProperty("formLocation")
    public void setFormLocation(int formLocation) {
        this.formLocation = formLocation;
    }

    @JsonProperty("branchName")
    public String getBranchName() {
        return branchName;
    }

    @JsonProperty("branchName")
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @JsonProperty("driverId")
    public String getDriverId() {
        return driverId;
    }

    @JsonProperty("driverId")
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    @JsonProperty("wareHouseName")
    public String getWareHouseName() {
        return wareHouseName;
    }

    @JsonProperty("wareHouseName")
    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    @JsonProperty("fuelName")
    public String getFuelName() {
        return fuelName;
    }

    @JsonProperty("fuelName")
    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
    }

    @JsonProperty("sortDate")
    private long sortDate;

    @JsonProperty("sortDate")
    public long getSortDate() {
        return sortDate;
    }

    @JsonProperty("sortDate")
    public void setSortDate(long sortDate) {
        this.sortDate = sortDate;
    }

    @JsonProperty("chatroomGUID")
    public String getChatroomGUID() {
        return chatroomGUID;
    }

    @JsonProperty("chatroomGUID")
    public void setChatroomGUID(String chatroomGUID) {
        this.chatroomGUID = chatroomGUID;
    }

    @JsonProperty("deleteForMe")
    public String getDeleteForMe() {
        return deleteForMe;
    }

    @JsonProperty("deleteForMe")
    public void setDeleteForMe(String deleteForMe) {
        this.deleteForMe = deleteForMe;
    }

    @JsonProperty("seenCount")
    public String getSeenCount() {
        return seenCount;
    }

    @JsonProperty("seenCount")
    public void setSeenCount(String seenCount) {
        this.seenCount = seenCount;
    }

    @JsonProperty("receivedCount")
    public String getReceivedCount() {
        return receivedCount;
    }

    @JsonProperty("receivedCount")
    public void setReceivedCount(String receivedCount) {
        this.receivedCount = receivedCount;
    }

    @JsonProperty("isMute")
    public String getIsMute() {
        return isMute;
    }

    @JsonProperty("isMute")
    public void setIsMute(String isMute) {
        this.isMute = isMute;
    }

    @JsonProperty("isOnline")
    public String getIsOnline() {
        return isOnline;
    }

    @JsonProperty("isOnline")
    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    @JsonProperty("isTyping")
    public String getIsTyping() {
        return isTyping;
    }

    @JsonProperty("isTyping")
    public void setIsTyping(String isTyping) {
        this.isTyping = isTyping;
    }

    @JsonProperty("unSeenCount")
    public String getUnSeenCount() {
        return unSeenCount;
    }

    @JsonProperty("unSeenCount")
    public void setUnSeenCount(String unSeenCount) {
        this.unSeenCount = unSeenCount;
    }

    @JsonProperty("mainMenuActive")
    public String getMainMenuActive() {
        return mainMenuActive;
    }

    @JsonProperty("rootId")
    public String getRootId() {
        return rootId;
    }

    @JsonProperty("mainMenuActive")
    public void setMainMenuActive(String mainMenuActive) {
        this.mainMenuActive = mainMenuActive;
    }

    @JsonProperty("rootId")
    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    @JsonProperty("goodSUOMList")
    public String getGoodSUOMList() {
        return goodSUOMList;
    }

    @JsonProperty("goodSUOMList")
    public void setGoodSUOMList(String goodSUOMList) {
        this.goodSUOMList = goodSUOMList;
    }

    @JsonProperty("goodTypeList")
    public String getGoodTypeList() {
        return goodTypeList;
    }

    @JsonProperty("goodTypeList")
    public void setGoodTypeList(String goodTypeList) {
        this.goodTypeList = goodTypeList;
    }

    @JsonProperty("B5CountryDivisionId")
    public String getB5CountryDivisionId() {
        return B5CountryDivisionId;
    }

    @JsonProperty("B5CountryDivisionId")
    public void setB5CountryDivisionId(String b5CountryDivisionId) {
        B5CountryDivisionId = b5CountryDivisionId;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("B5HCOwnershipId")
    public String getB5HCOwnershipId() {
        return B5HCOwnershipId;
    }

    @JsonProperty("B5HCOwnershipId")
    public void setB5HCOwnershipId(String b5HCOwnershipId) {
        B5HCOwnershipId = b5HCOwnershipId;
    }

    @JsonProperty("B5HCAddressTypeId")
    public String getB5HCAddressTypeId() {
        return B5HCAddressTypeId;
    }

    @JsonProperty("B5HCAddressTypeId")
    public void setB5HCAddressTypeId(String b5HCAddressTypeId) {
        B5HCAddressTypeId = b5HCAddressTypeId;
    }

    @JsonProperty("startOwnedDate")
    public String getStartOwnedDate() {
        return startOwnedDate;
    }

    @JsonProperty("startOwnedDate")
    public void setStartOwnedDate(String startOwnedDate) {
        this.startOwnedDate = startOwnedDate;
    }

    @JsonProperty("stopOwnedDate")
    public String getStopOwnedDate() {
        return stopOwnedDate;
    }

    @JsonProperty("stopOwnedDate")
    public void setStopOwnedDate(String stopOwnedDate) {
        this.stopOwnedDate = stopOwnedDate;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }


    @JsonProperty("B5HCTypeId")
    public String getB5HCTypeId() {
        return B5HCTypeId;
    }

    @JsonProperty("B5HCTypeId")
    public void setB5HCTypeId(String b5HCTypeId) {
        B5HCTypeId = b5HCTypeId;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("addressId")
    public String getAddressId() {
        return addressId;
    }

    @JsonProperty("addressId")
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    @JsonProperty("customerGroupId")
    public String getCustomerGroupId() {
        return customerGroupId;
    }

    @JsonProperty("customerGroupId")
    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    @JsonProperty("unitId2")
    public String getUnitId2() {
        return unitId2;
    }

    @JsonProperty("unitId2")
    public void setUnitId2(String unitId2) {
        this.unitId2 = unitId2;
    }

    @JsonProperty("unitPrice")
    public String getUnitPrice() {
        return unitPrice;
    }

    @JsonProperty("unitPrice")
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    @JsonProperty("goodBrandId")
    public String getGoodBrandId() {
        return goodBrandId;
    }

    @JsonProperty("goodBrandId")
    public void setGoodBrandId(String goodBrandId) {
        this.goodBrandId = goodBrandId;
    }

    @JsonProperty("discountPercentage")
    public String getDiscountPercentage() {
        return discountPercentage;
    }

    @JsonProperty("discountPercentage")
    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @JsonProperty("taxPercentage")
    public String getTaxPercentage() {
        return taxPercentage;
    }

    @JsonProperty("taxPercentage")
    public void setTaxPercentage(String taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    @JsonProperty("availableAmount2")
    public String getAvailableAmount2() {
        return availableAmount2;
    }

    @JsonProperty("availableAmount2")
    public void setAvailableAmount2(String availableAmount2) {
        this.availableAmount2 = availableAmount2;
    }

    @JsonProperty("numerator")
    public String getNumerator() {
        return numerator;
    }

    @JsonProperty("numerator")
    public void setNumerator(String numerator) {
        this.numerator = numerator;
    }

    @JsonProperty("denominator")
    public String getDenominator() {
        return denominator;
    }

    @JsonProperty("denominator")
    public void setDenominator(String denominator) {
        this.denominator = denominator;
    }

    @JsonProperty("baseAmountForGift")
    public String getBaseAmountForGift() {
        return baseAmountForGift;
    }

    @JsonProperty("baseAmountForGift")
    public void setBaseAmountForGift(String baseAmountForGift) {
        this.baseAmountForGift = baseAmountForGift;
    }

    @JsonProperty("goodsIdGift")
    public String getGoodsIdGift() {
        return goodsIdGift;
    }

    @JsonProperty("goodsIdGift")
    public void setGoodsIdGift(String goodsIdGift) {
        this.goodsIdGift = goodsIdGift;
    }

    @JsonProperty("giftAmount")
    public String getGiftAmount() {
        return giftAmount;
    }

    @JsonProperty("giftAmount")
    public void setGiftAmount(String giftAmount) {
        this.giftAmount = giftAmount;
    }


    @JsonProperty("unitName1")
    public String getUnitName1() {
        return unitName1;
    }

    @JsonProperty("unitName1")
    public void setUnitName1(String unitName1) {
        this.unitName1 = unitName1;
    }

    @JsonProperty("unitId")
    public String getUnitId() {
        return unitId;
    }

    @JsonProperty("unitId")
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @JsonProperty("unitName2")
    public String getUnitName2() {
        return unitName2;
    }

    @JsonProperty("unitName2")
    public void setUnitName2(String unitName2) {
        this.unitName2 = unitName2;
    }

    @JsonProperty("amount2")
    public String getAmount2() {
        return amount2;
    }

    @JsonProperty("amount2")
    public void setAmount2(String amount2) {
        this.amount2 = amount2;
    }

    @JsonProperty("code6")
    public String getCode6() {
        return code6;
    }

    @JsonProperty("code6")
    public void setCode6(String code6) {
        this.code6 = code6;
    }

    @JsonProperty("name6")
    public String getName6() {
        return name6;
    }

    @JsonProperty("name6")
    public void setName6(String name6) {
        this.name6 = name6;
    }

    @JsonProperty("warehouseId3")
    public String getWarehouseId3() {
        return warehouseId3;
    }

    @JsonProperty("warehouseId3")
    public void setWarehouseId3(String warehouseId3) {
        this.warehouseId3 = warehouseId3;
    }

    @JsonProperty("B5IdRefId8")
    public String getB5IdRefId8() {
        return B5IdRefId8;
    }

    @JsonProperty("B5IdRefId8")
    public void setB5IdRefId8(String b5IdRefId8) {
        B5IdRefId8 = b5IdRefId8;
    }

    @JsonProperty("B5IdRefId9")
    public String getB5IdRefId9() {
        return B5IdRefId9;
    }

    @JsonProperty("B5IdRefId9")
    public void setB5IdRefId9(String b5IdRefId9) {
        B5IdRefId9 = b5IdRefId9;
    }

    @JsonProperty("B5IdRefId10")
    public String getB5IdRefId10() {
        return B5IdRefId10;
    }

    @JsonProperty("B5IdRefId10")
    public void setB5IdRefId10(String b5IdRefId10) {
        B5IdRefId10 = b5IdRefId10;
    }

    @JsonProperty("yearCode")
    public String getYearCode() {
        return yearCode;
    }

    @JsonProperty("yearCode")
    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    @JsonProperty("branchId")
    public String getBranchId() {
        return branchId;
    }

    @JsonProperty("branchId")
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    @JsonProperty("warehouseId")
    public String getWarehouseId() {
        return warehouseId;
    }

    @JsonProperty("warehouseId")
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    @JsonProperty("goodId")
    public String getGoodId() {
        return goodId;
    }

    @JsonProperty("goodId")
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    @JsonProperty("duration")
    public int getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @JsonProperty("B5IdRefId4")
    public String getB5IdRefId4() {
        return B5IdRefId4;
    }

    @JsonProperty("B5IdRefId4")
    public void setB5IdRefId4(String b5IdRefId4) {
        B5IdRefId4 = b5IdRefId4;
    }

    @JsonProperty("B5IdRefId5")
    public String getB5IdRefId5() {
        return B5IdRefId5;
    }

    @JsonProperty("B5IdRefId5")
    public void setB5IdRefId5(String b5IdRefId5) {
        B5IdRefId5 = b5IdRefId5;
    }

    @JsonProperty("B5IdRefId6")
    public String getB5IdRefId6() {
        return B5IdRefId6;
    }

    @JsonProperty("B5IdRefId6")
    public void setB5IdRefId6(String b5IdRefId6) {
        B5IdRefId6 = b5IdRefId6;
    }

    @JsonProperty("B5IdRefId14")
    public String getB5IdRefId14() {
        return B5IdRefId14;
    }

    @JsonProperty("B5IdRefId14")
    public void setB5IdRefId14(String b5IdRefId14) {
        B5IdRefId14 = b5IdRefId14;
    }

    @JsonProperty("B5HCCurrencyId")
    public String getB5HCCurrencyId() {
        return B5HCCurrencyId;
    }

    @JsonProperty("B5HCCurrencyId")
    public void setB5HCCurrencyId(String b5HCCurrencyId) {
        B5HCCurrencyId = b5HCCurrencyId;
    }

    @JsonProperty("B5HCSellMethodId")
    public String getB5HCSellMethodId() {
        return B5HCSellMethodId;
    }

    @JsonProperty("B5HCSellMethodId")
    public void setB5HCSellMethodId(String b5HCSellMethodId) {
        B5HCSellMethodId = b5HCSellMethodId;
    }

    @JsonProperty("B5HCAccountSideId")
    public String getB5HCAccountSideId() {
        return B5HCAccountSideId;
    }

    @JsonProperty("B5HCAccountSideId")
    public void setB5HCAccountSideId(String b5HCAccountSideId) {
        B5HCAccountSideId = b5HCAccountSideId;
    }

    @JsonProperty("B5IdRefIdRecall")
    public String getB5IdRefIdRecall() {
        return B5IdRefIdRecall;
    }

    @JsonProperty("B5IdRefIdRecall")
    public void setB5IdRefIdRecall(String b5IdRefIdRecall) {
        B5IdRefIdRecall = b5IdRefIdRecall;
    }

    @JsonProperty("deliveryName")
    public String getDeliveryName() {
        return deliveryName;
    }

    @JsonProperty("deliveryName")
    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    @JsonProperty("formNumber")
    public String getFormNumber() {
        return formNumber;
    }

    @JsonProperty("formNumber")
    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
    }

    @JsonProperty("formDate")
    public String getFormDate() {
        return formDate;
    }

    @JsonProperty("formDate")
    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    @JsonProperty("expireDate")
    public String getExpireDate() {
        return expireDate;
    }

    @JsonProperty("expireDate")
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    @JsonProperty("secondaryDate")
    public String getSecondaryDate() {
        return secondaryDate;
    }

    @JsonProperty("secondaryDate")
    public void setSecondaryDate(String secondaryDate) {
        secondaryDate = secondaryDate;
    }

    @JsonProperty("goodTransDetail")
    public String getGoodTransDetail() {
        return goodTransDetail;
    }

    @JsonProperty("goodTransDetail")
    public void setGoodTransDetail(String goodTransDetail) {
        this.goodTransDetail = goodTransDetail;
    }

    @JsonProperty("alloy")
    public String getAlloy() {
        return alloy;
    }

    @JsonProperty("alloy")
    public void setAlloy(String alloy) {
        this.alloy = alloy;
    }

    @JsonProperty("B5HCReceiverStatusId")
    public String getB5HCReceiverStatusId() {
        return B5HCReceiverStatusId;
    }

    @JsonProperty("B5HCReceiverStatusId")
    public void setB5HCReceiverStatusId(String b5HCReceiverStatusId) {
        B5HCReceiverStatusId = b5HCReceiverStatusId;
    }

    @JsonProperty("C5GoodsId")
    public String getC5GoodsId() {
        return C5GoodsId;
    }

    @JsonProperty("typeId")
    public String getTypeId() {
        return typeId;
    }

    @JsonProperty("typeId")
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @JsonProperty("C5GoodsId")
    public void setC5GoodsId(String c5GoodsId) {
        C5GoodsId = c5GoodsId;
    }

    @JsonProperty("GTCode")
    public String getGTCode() {
        return GTCode;
    }

    @JsonProperty("GTCode")
    public void setGTCode(String GTCode) {
        this.GTCode = GTCode;
    }

    @JsonProperty("B5formRefId")
    public String getB5formRefId() {
        return B5formRefId;
    }

    @JsonProperty("B5formRefId")
    public void setB5formRefId(String b5formRefId) {
        B5formRefId = b5formRefId;
    }

    @JsonProperty("getB5IdRefId3")
    public String getB5IdRefId3() {
        return B5IdRefId3;
    }

    @JsonProperty("B5IdRefId3")
    public void setB5IdRefId3(String b5IdRefId3) {
        B5IdRefId3 = b5IdRefId3;
    }

    @JsonProperty("B5HCDailyScheduleId")
    public String getB5HCDailyScheduleId() {
        return B5HCDailyScheduleId;
    }

    @JsonProperty("B5HCDailyScheduleId")
    public void setB5HCDailyScheduleId(String b5HCDailyScheduleId) {
        B5HCDailyScheduleId = b5HCDailyScheduleId;
    }

    @JsonProperty("B5HCWageTitleId")
    public String getB5HCWageTitleId() {
        return B5HCWageTitleId;
    }

    @JsonProperty("B5HCWageTitleId")
    public void setB5HCWageTitleId(String b5HCWageTitleId) {
        B5HCWageTitleId = b5HCWageTitleId;
    }

    @JsonProperty("isHappeningAtTheSameTime")
    public int getIsHappeningAtTheSameTime() {
        return isHappeningAtTheSameTime;
    }

    @JsonProperty("isHappeningAtTheSameTime")
    public void setIsHappeningAtTheSameTime(int isHappeningAtTheSameTime) {
        this.isHappeningAtTheSameTime = isHappeningAtTheSameTime;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("activityState")
    public String getActivityState() {
        return activityState;
    }

    @JsonProperty("activityState")
    public void setActivityState(String activityState) {
        this.activityState = activityState;
    }


    @JsonProperty("oldValue")
    public String getOldValue() {
        return oldValue;
    }

    @JsonProperty("oldValue")
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    @JsonProperty("searchValue")
    public String getSearchValue() {
        return searchValue;
    }

    @JsonProperty("searchValue")
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    @JsonProperty("appVersion")
    public int getAppVersion() {
        return appVersion;
    }

    @JsonProperty("appVersion")
    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    @JsonProperty("carPlaqueNumber")
    public String getCarPlaqueNumber() {
        return carPlaqueNumber;
    }

    @JsonProperty("carPlaqueNumber")
    public void setCarPlaqueNumber(String carPlaqueNumber) {
        this.carPlaqueNumber = carPlaqueNumber;
    }

    @JsonProperty("buyerName")
    public String getBuyerName() {
        return buyerName;
    }

    @JsonProperty("buyerName")
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    @JsonProperty("goodTranseDate")
    public String getGoodTranseDate() {
        return goodTranseDate;
    }

    @JsonProperty("goodTranseDate")
    public void setGoodTranseDate(String goodTranseDate) {
        this.goodTranseDate = goodTranseDate;
    }

    @JsonProperty("driverName")
    public String getDriverName() {
        return driverName;
    }

    @JsonProperty("driverName")
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    @JsonProperty("emptyDate")
    public String getEmptyDate() {
        return emptyDate;
    }

    @JsonProperty("emptyDate")
    public void setEmptyDate(String emptyDate) {
        this.emptyDate = emptyDate;
    }

    @JsonProperty("goodTranseNumber")
    public String getGoodTranseNumber() {
        return goodTranseNumber;
    }

    @JsonProperty("goodTranseNumber")
    public void setGoodTranseNumber(String goodTranseNumber) {
        this.goodTranseNumber = goodTranseNumber;
    }

    @JsonProperty("emptyWeight")
    public String getEmptyWeight() {
        return emptyWeight;
    }

    @JsonProperty("emptyWeight")
    public void setEmptyWeight(String emptyWeight) {
        this.emptyWeight = emptyWeight;
    }

    @JsonProperty("goodTranseAmount")
    public String getGoodTranseAmount() {
        return goodTranseAmount;
    }

    @JsonProperty("goodTranseAmount")
    public void setGoodTranseAmount(String goodTranseAmount) {
        this.goodTranseAmount = goodTranseAmount;
    }

    @JsonProperty("goodTranseWeight")
    public String getGoodTranseWeight() {
        return goodTranseWeight;
    }

    @JsonProperty("goodTranseWeight")
    public void setGoodTranseWeight(String goodTranseWeight) {
        this.goodTranseWeight = goodTranseWeight;
    }

    @JsonProperty("sumWeight")
    public String getSumWeight() {
        return sumWeight;
    }

    @JsonProperty("sumWeight")
    public void setSumWeight(String sumWeight) {
        this.sumWeight = sumWeight;
    }

    @JsonProperty("balance")
    public String getBalance() {
        return balance;
    }

    @JsonProperty("balance")
    public void setBalance(String balance) {
        this.balance = balance;
    }


    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("formId")
    public String getFormId() {
        return formId;
    }

    @JsonProperty("formId")
    public void setFormId(String formId) {
        this.formId = formId;
    }

    @JsonProperty("formName")
    public String getFormName() {
        return formName;
    }

    @JsonProperty("formName")
    public void setFormName(String formName) {
        this.formName = formName;
    }

    @JsonProperty("formTitle")
    public String getFormTitle() {
        return formTitle;
    }

    @JsonProperty("formTitle")
    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    @JsonProperty("formCode")
    public String getFormCode() {
        return formCode;
    }

    @JsonProperty("formCode")
    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    @JsonProperty("appName")
    public String getAppName() {
        return appName;
    }

    @JsonProperty("appName")
    public void setAppName(String appName) {
        this.appName = appName;
    }


    @JsonProperty("chatroomId")
    public String getChatroomId() {
        return chatroomId;
    }

    @JsonProperty("chatroomId")
    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }


    @JsonProperty("nodeReceivedDate")
    public String getNodeReceivedDate() {
        return nodeReceivedDate;
    }

    @JsonProperty("nodeReceivedDate")
    public void setNodeReceivedDate(String nodeReceivedDate) {
        this.nodeReceivedDate = nodeReceivedDate;
    }

    @JsonProperty("errorNumber")
    public String getErrorNumber() {
        return errorNumber;
    }

    @JsonProperty("errorNumber")
    public void setErrorNumber(String errorNumber) {
        this.errorNumber = errorNumber;
    }


    @JsonProperty("configIdRef1")
    public String getConfigIdRef1() {
        return configIdRef1;
    }

    @JsonProperty("configIdRef1")
    public void setConfigIdRef1(String configIdRef1) {
        this.configIdRef1 = configIdRef1;
    }

    @JsonProperty("configIdRef2")
    public String getConfigIdRef2() {
        return configIdRef2;
    }

    @JsonProperty("configIdRef2")
    public void setConfigIdRef2(String configIdRef2) {
        this.configIdRef2 = configIdRef2;
    }

    @JsonProperty("configIdRef3")
    public String getConfigIdRef3() {
        return configIdRef3;
    }

    @JsonProperty("configIdRef3")
    public void setConfigIdRef3(String configIdRef3) {
        this.configIdRef3 = configIdRef3;
    }

    @JsonProperty("configIdRef4")
    public String getConfigIdRef4() {
        return configIdRef4;
    }

    @JsonProperty("configIdRef4")
    public void setConfigIdRef4(String configIdRef4) {
        this.configIdRef4 = configIdRef4;
    }

    @JsonProperty("configIdRef5")
    public String getConfigIdRef5() {
        return configIdRef5;
    }

    @JsonProperty("configIdRef5")
    public void setConfigIdRef5(String configIdRef5) {
        this.configIdRef5 = configIdRef5;
    }

    @JsonProperty("configIdRef6")
    public String getConfigIdRef6() {
        return configIdRef6;
    }

    @JsonProperty("configIdRef6")
    public void setConfigIdRef6(String configIdRef6) {
        this.configIdRef6 = configIdRef6;
    }

    @JsonProperty("configIdRef7")
    public String getConfigIdRef7() {
        return configIdRef7;
    }

    @JsonProperty("configIdRef7")
    public void setConfigIdRef7(String configIdRef7) {
        this.configIdRef7 = configIdRef7;
    }

    @JsonProperty("configIdRef8")
    public String getConfigIdRef8() {
        return configIdRef8;
    }

    @JsonProperty("configIdRef8")
    public void setConfigIdRef8(String configIdRef8) {
        this.configIdRef8 = configIdRef8;
    }

    @JsonProperty("configIdRef9")
    public String getConfigIdRef9() {
        return configIdRef9;
    }

    @JsonProperty("configIdRef9")
    public void setConfigIdRef9(String configIdRef9) {
        this.configIdRef9 = configIdRef9;
    }

    @JsonProperty("configIdRef10")
    public String getConfigIdRef10() {
        return configIdRef10;
    }

    @JsonProperty("configIdRef10")
    public void setConfigIdRef10(String configIdRef10) {
        this.configIdRef10 = configIdRef10;
    }

    @JsonProperty("configIdRef11")
    public String getConfigIdRef11() {
        return configIdRef11;
    }

    @JsonProperty("configIdRef11")
    public void setConfigIdRef11(String configIdRef11) {
        this.configIdRef11 = configIdRef11;
    }

    @JsonProperty("configIdRef12")
    public String getConfigIdRef12() {
        return configIdRef12;
    }

    @JsonProperty("configIdRef12")
    public void setConfigIdRef12(String configIdRef12) {
        this.configIdRef12 = configIdRef12;
    }

    @JsonProperty("configIdRef13")
    public String getConfigIdRef13() {
        return configIdRef13;
    }

    @JsonProperty("configIdRef13")
    public void setConfigIdRef13(String configIdRef13) {
        this.configIdRef13 = configIdRef13;
    }

    @JsonProperty("configIdRef14")
    public String getConfigIdRef14() {
        return configIdRef14;
    }

    @JsonProperty("configIdRef14")
    public void setConfigIdRef14(String configIdRef14) {
        this.configIdRef14 = configIdRef14;
    }

    @JsonProperty("configIdRef15")
    public String getConfigIdRef15() {
        return configIdRef15;
    }

    @JsonProperty("configIdRef15")
    public void setConfigIdRef15(String configIdRef15) {
        this.configIdRef15 = configIdRef15;
    }

    @JsonProperty("iconUrl")
    public String getIconUrl() {
        return iconUrl;
    }

    @JsonProperty("iconUrl")
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @JsonProperty("totalCount")
    public Integer getTotalCount() {
        return totalCount;
    }

    @JsonProperty("totalCount")
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("J5OrgChartId")
    public String getJ5OrgChartId() {
        return J5OrgChartId;
    }

    @JsonProperty("J5OrgChartId")
    public void setJ5OrgChartId(String J5OrgChartId) {
        this.J5OrgChartId = J5OrgChartId;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("field")
    public String getField() {
        return field;
    }

    @JsonProperty("field")
    public void setField(String field) {
        this.field = field;
    }

    @JsonProperty("part")
    public String getPart() {
        return part;
    }

    @JsonProperty("part")
    public void setPart(String part) {
        this.part = part;
    }

    @JsonProperty("surface")
    public String getSurface() {
        return surface;
    }

    @JsonProperty("surface")
    public void setSurface(String surface) {
        this.surface = surface;
    }

    @JsonProperty("rowCount")
    public String getRowCount() {
        return rowCount;
    }

    @JsonProperty("rowCount")
    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    @JsonProperty("segmentCount")
    public String getSegmentCount() {
        return segmentCount;
    }

    @JsonProperty("segmentCount")
    public void setSegmentCount(String segmentCount) {
        this.segmentCount = segmentCount;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("geoLocation")
    public String getGeoLocation() {
        return geoLocation;
    }

    @JsonProperty("geoLocation")
    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    @JsonProperty("parentId")
    public String getParentId() {
        return parentId;
    }

    @JsonProperty("parentId")
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @JsonProperty("parentName")
    public String getParentName() {
        return parentName;
    }

    @JsonProperty("parentName")
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @JsonProperty("parentCode")
    public String getParentCode() {
        return parentCode;
    }

    @JsonProperty("parentCode")
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @JsonProperty("B5IdRefIds")
    public String getB5IdRefIds() {
        return B5IdRefIds;
    }

    @JsonProperty("b5IdRefIds")
    public void setB5IdRefIds(String B5IdRefIds) {
        this.B5IdRefIds = B5IdRefIds;
    }

    @JsonProperty("B5IdRefId")
    public String getB5IdRefId() {
        return B5IdRefId;
    }

    @JsonProperty("b5IdRefId")
    public void setB5IdRefId(String B5IdRefId) {
        this.B5IdRefId = B5IdRefId;
    }

    @JsonProperty("isMain")
    public Integer getIsMain() {
        return isMain;
    }

    @JsonProperty("isMain")
    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }


    @JsonProperty("parentTypeId")
    public String getParentTypeId() {
        return parentTypeId;
    }

    @JsonProperty("parentTypeId")
    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    @JsonProperty("groupId")
    public String getGroupId() {
        return groupId;
    }

    @JsonProperty("groupId")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }


    @JsonProperty("organization")
    public String getOrganization() {
        return organization;
    }

    @JsonProperty("organization")
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }


    @JsonProperty("guid")
    public String getGuid() {
        return guid;
    }

    @JsonProperty("guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }

    @JsonProperty("hasFile")
    public Integer getHasFile() {
        return hasFile;
    }

    @JsonProperty("hasFile")
    public void setHasFile(Integer hasFile) {
        this.hasFile = hasFile;
    }

    @JsonProperty("lang")
    public String getLang() {
        return lang;
    }

    @JsonProperty("lang")
    public void setLang(String lang) {
        this.lang = lang;
    }

    @JsonProperty("pageNo")
    public Integer getPageNo() {
        return pageNo;
    }

    @JsonProperty("pageNo")
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    @JsonProperty("pageItem")
    public Integer getPageItem() {
        return pageItem;
    }

    @JsonProperty("pageItem")
    public void setPageItem(Integer pageItem) {
        this.pageItem = pageItem;
    }

    @JsonProperty("file")
    public String getFile() {
        return file;
    }

    @JsonProperty("file")
    public void setFile(String file) {
        this.file = file;
    }

    @JsonProperty("body")
    public List<Body> getBody() {
        return body;
    }


    @JsonProperty("body")
    public void setBody(List<Body> body) {
        this.body = body;
    }

    @JsonProperty("B5FormRefId")
    public String getB5FormRefId() {
        return B5FormRefId;
    }

    @JsonProperty("B5FormRefId")
    public void setB5FormRefId(String B5FormRefId) {
        this.B5FormRefId = B5FormRefId;
    }

    @JsonProperty("nationalityId")
    public String getNationalityId() {
        return nationalityId;
    }

    @JsonProperty("nationalityId")
    public void setNationalityId(String nationalityId) {
        this.nationalityId = nationalityId;
    }

    @JsonProperty("isActive")
    public Integer getIsActive() {
        return isActive;
    }

    @JsonProperty("isActive")
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @JsonProperty("activitySubject")
    public String getActivitySubject() {
        return activitySubject;
    }

    @JsonProperty("activitySubject")
    public void setActivitySubject(String activitySubject) {
        this.activitySubject = activitySubject;
    }

    @JsonProperty("techCode")
    public String getTechCode() {
        return techCode;
    }

    @JsonProperty("techCode")
    public void setTechCode(String techCode) {
        this.techCode = techCode;
    }

    @JsonProperty("latinName")
    public String getLatinName() {
        return latinName;
    }

    @JsonProperty("latinName")
    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    @JsonProperty("nationalityCode")
    public String getNationalityCode() {
        return nationalityCode;
    }

    @JsonProperty("nationalityCode")
    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }


    @JsonProperty("googleKeyWord")
    public String getGoogleKeyWord() {
        return googleKeyWord;
    }

    @JsonProperty("googleKeyWord")
    public void setGoogleKeyWord(String googleKeyWord) {
        this.googleKeyWord = googleKeyWord;
    }

    @JsonProperty("techInfo")
    public String getTechInfo() {
        return techInfo;
    }

    @JsonProperty("techInfo")
    public void setTechInfo(String techInfo) {
        this.techInfo = techInfo;
    }

    @JsonProperty("C5UnitId")
    public String getC5UnitId() {
        return C5UnitId;
    }

    @JsonProperty("C5UnitId")
    public void setC5UnitId(String C5UnitId) {
        this.C5UnitId = C5UnitId;
    }

    @JsonProperty("unitName")
    public String getUnitName() {
        return unitName;
    }

    @JsonProperty("unitName")
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @JsonProperty("C5BrandId")
    public String getC5BrandId() {
        return C5BrandId;
    }

    @JsonProperty("C5BrandId")
    public void setC5BrandId(String C5BrandId) {
        this.C5BrandId = C5BrandId;
    }

    @JsonProperty("brandName")
    public String getBrandName() {
        return brandName;
    }

    @JsonProperty("brandName")
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @JsonProperty("width")
    public String getWidth() {
        return width;
    }

    @JsonProperty("width")
    public void setWidth(String width) {
        this.width = width;
    }

    @JsonProperty("height")
    public String getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(String height) {
        this.height = height;
    }

    @JsonProperty("length")
    public String getLength() {
        return length;
    }

    @JsonProperty("length")
    public void setLength(String length) {
        this.length = length;
    }

    @JsonProperty("weight")
    public String getWeight() {
        return weight;
    }

    @JsonProperty("weight")
    public void setWeight(String weight) {
        this.weight = weight;
    }


    @JsonProperty("B5HCStatusId")
    public String getB5HCStatusId() {
        return B5HCStatusId;
    }

    @JsonProperty("B5HCStatusId")
    public void setB5HCStatusId(String B5HCStatusId) {
        this.B5HCStatusId = B5HCStatusId;
    }

    @JsonProperty("statusName")
    public String getStatusName() {
        return statusName;
    }

    @JsonProperty("statusName")
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("U5UserId")
    public String getU5UserId() {
        return U5UserId;
    }

    @JsonProperty("U5UserId")
    public void setU5UserId(String u5UserId) {
        U5UserId = u5UserId;
    }

    @JsonProperty("B5HCUserTypeId")
    public String getB5HCUserTypeId() {
        return B5HCUserTypeId;
    }

    @JsonProperty("B5HCUserTypeId")
    public void setB5HCUserTypeId(String b5HCUserTypeId) {
        B5HCUserTypeId = b5HCUserTypeId;
    }

    @JsonProperty("G5ChatroomId")
    public String getG5ChatroomId() {
        return G5ChatroomId;
    }

    @JsonProperty("G5ChatroomId")
    public void setG5ChatroomId(String g5ChatroomId) {
        G5ChatroomId = g5ChatroomId;
    }

    @JsonProperty("activityKind")
    public Integer getActivityKind() {
        return activityKind;
    }

    @JsonProperty("activityKind")
    public void setActivityKind(Integer activityKind) {
        this.activityKind = activityKind;
    }

    @JsonProperty("B5HCChatTypeId")
    public String getB5HCChatTypeId() {
        return B5HCChatTypeId;
    }

    @JsonProperty("B5HCChatTypeId")
    public void setB5HCChatTypeId(String b5HCChatTypeId) {
        B5HCChatTypeId = b5HCChatTypeId;
    }

    @JsonProperty("G5MessageId")
    public String getG5MessageId() {
        return G5MessageId;
    }

    @JsonProperty("G5MessageId")
    public void setG5MessageId(String g5MessageId) {
        G5MessageId = g5MessageId;
    }

    @JsonProperty("A5AttachmentId")
    public Integer getA5AttachmentId() {
        return A5AttachmentId;
    }

    @JsonProperty("A5AttachmentId")
    public void setA5AttachmentId(Integer a5AttachmentId) {
        A5AttachmentId = a5AttachmentId;
    }

    @JsonProperty("B5HCMessageTypeId")
    public String getB5HCMessageTypeId() {
        return B5HCMessageTypeId;
    }

    @JsonProperty("B5HCMessageTypeId")
    public void setB5HCMessageTypeId(String b5HCMessageTypeId) {
        B5HCMessageTypeId = b5HCMessageTypeId;
    }

    @JsonProperty("insertDate")
    public String getInsertDate() {
        return insertDate;
    }

    @JsonProperty("insertDate")
    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    @JsonProperty("createDate")
    public String getCreateDate() {
        return createDate;
    }

    @JsonProperty("createDate")
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @JsonProperty("sendDate")
    public String getSendDate() {
        return sendDate;
    }

    @JsonProperty("sendDate")
    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    @JsonProperty("receivedDate")
    public String getReceivedDate() {
        return receivedDate;
    }

    @JsonProperty("receivedDate")
    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    @JsonProperty("attach")
    public String getAttach() {
        return attach;
    }

    @JsonProperty("attach")
    public void setAttach(String attach) {
        this.attach = attach;
    }

    @JsonProperty("receivers")
    public String getReceivers() {
        return receivers;
    }

    @JsonProperty("receivers")
    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    @JsonProperty("seenDate")
    public String getSeenDate() {
        return seenDate;
    }

    @JsonProperty("seenDate")
    public void setSeenDate(String seenDate) {
        this.seenDate = seenDate;
    }

    @JsonProperty("B5IdRefId1")
    public String getB5IdRefId1() {
        return B5IdRefId1;
    }


    @JsonProperty("B5IdRefId1")
    public void setB5IdRefId1(String b5IdRefId1) {
        B5IdRefId1 = b5IdRefId1;
    }

    @JsonProperty("B5IdRefId2")
    public String getB5IdRefId2() {
        return B5IdRefId2;
    }

    @JsonProperty("B5IdRefId2")
    public void setB5IdRefId2(String b5IdRefId2) {
        B5IdRefId2 = b5IdRefId2;
    }

    @JsonProperty("definitionId")
    public int getDefinitionId() {
        return definitionId;
    }

    @JsonProperty("definitionId")
    public void setDefinitionId(int definitionId) {
        this.definitionId = definitionId;
    }

    @JsonProperty("B5HCPriorityName")
    public int getB5HCPriorityName() {
        return B5HCPriorityName;
    }

    @JsonProperty("B5HCPriorityName")
    public void setB5HCPriorityName(int b5HCPriorityName) {
        B5HCPriorityName = b5HCPriorityName;
    }

    @JsonProperty("definitionName")
    public String getDefinitionName() {
        return definitionName;
    }

    @JsonProperty("definitionName")
    public void setDefinitionName(String definitionName) {
        this.definitionName = definitionName;
    }

    @JsonProperty("senderName")
    public String getSenderName() {
        return senderName;
    }

    @JsonProperty("senderName")
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @JsonProperty("B5FormRefTitle")
    public String getB5FormRefTitle() {
        return B5FormRefTitle;
    }

    @JsonProperty("B5FormRefTitle")
    public void setB5FormRefTitle(String b5FormRefTitle) {
        B5FormRefTitle = b5FormRefTitle;
    }


    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @JsonProperty("goodCode")
    public String getGoodCode() {
        return goodCode;
    }

    @JsonProperty("goodCode")
    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }


    @JsonProperty("serial")
    public String getSerial() {
        return serial;
    }

    @JsonProperty("serial")
    public void setSerial(String serial) {
        this.serial = serial;
    }


    @JsonProperty("countingNumber")
    public int getCountingNumber() {
        return countingNumber;
    }

    @JsonProperty("countingNumber")
    public void setCountingNumber(int countingNumber) {
        this.countingNumber = countingNumber;
    }

    @JsonProperty("goodColumnNumber")
    public int getGoodColumnNumber() {
        return goodColumnNumber;
    }

    @JsonProperty("goodColumnNumber")
    public void setGoodColumnNumber(int goodColumnNumber) {
        this.goodColumnNumber = goodColumnNumber;
    }


    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }


    @JsonProperty("subAmount")
    public String getSubAmount() {
        return subAmount;
    }

    @JsonProperty("subAmount")
    public void setSubAmount(String subAmount) {
        this.subAmount = subAmount;
    }

    @JsonProperty("goodName")
    public String getGoodName() {
        return goodName;
    }

    @JsonProperty("goodName")
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }


    @JsonProperty("mainUnitName")
    public String getMainUnitName() {
        return mainUnitName;
    }

    @JsonProperty("mainUnitName")
    public void setMainUnitName(String mainUnitName) {
        this.mainUnitName = mainUnitName;
    }


    @JsonProperty("subUnitName")
    public String getSubUnitName() {
        return subUnitName;
    }

    @JsonProperty("subUnitName")
    public void setSubUnitName(String subUnitName) {
        this.subUnitName = subUnitName;
    }


    @JsonProperty("placeShelfRow")
    public String getPlaceShelfRow() {
        return placeShelfRow;
    }

    @JsonProperty("placeShelfRow")
    public void setPlaceShelfRow(String placeShelfRow) {
        this.placeShelfRow = placeShelfRow;
    }


    @JsonProperty("placeShelfSubRow")
    public String getPlaceShelfSubRow() {
        return placeShelfSubRow;
    }

    @JsonProperty("placeShelfSubRow")
    public void setPlaceShelfSubRow(String placeShelfSubRow) {
        this.placeShelfSubRow = placeShelfSubRow;
    }


    @JsonProperty("placeShelfLayer")
    public String getPlaceShelfLayer() {
        return placeShelfLayer;
    }

    @JsonProperty("placeShelfLayer")
    public void setPlaceShelfLayer(String placeShelfLayer) {
        this.placeShelfLayer = placeShelfLayer;
    }

    @JsonProperty("batchNumber")
    public String getBatchNumber() {
        return batchNumber;
    }

    @JsonProperty("batchNumber")
    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    @JsonProperty("syncState")
    public String getSyncState() {
        return syncState;
    }

    @JsonProperty("syncState")
    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    @JsonProperty("barCode")
    public String getBarCode() {
        return barCode;
    }

    @JsonProperty("barCode")
    public void setBarCode(String barCode) {
        this.barCode = barCode;
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