package ir.fararayaneh.erp.data.models.online.request;




import java.io.Serializable;

/**
 * this model is used for :
 *
 *          1- in Item.class (response package) (Item.class  not inherited from here,and all change should be done manually)
 *          todo : تغییرات به کلاس Item هم اعمال شود
 *          2- RestRequestModel (request package/rest package)
 *          3- all socket model (request package/socket package)
 *          4- FileUploadJsonModel
 *
 *
 * all values should be capsLock,,,all key are camelCase
 * kind : معمولا نام جدولی است که میخواهیم با متد گت پر کنیم
 * organization,userId : از جدول یوزر
 * appName : ERP
 * language : FA,EN,... از سمت سرور
 * errorNumber : {CommonErrorNumber.class} به کلاس روبرو مراجعه شود
 */

public class RootModel implements Serializable {



    protected String kind;

    protected String organization;

    protected String userId;

    protected String appName;

    /**
     * 0: have not file  ---> agar tabl attach darad az soton attach estefade mikonad banabar in, be in field negah nemikonim
     * 1: have file
     */
    protected String hasFile;

    protected String lang;

    protected String pageNo;

    protected String pageItem;

    protected String chatRoomId;

    protected String nodeReceivedDate;

    /**
     * errorNumber :
     * مقداری که در اینجا نشسته است نشان دهنده ارور از سمت دیتا بیس یا ارور از سمت سرور نود یا کانفریم از سمت سرور نود است
     * کلاینت به محض دریافت ابتدا باید چک کند که این پراپرتی مقدار دارد یا خیر و اگر مقدار دارد و نال نیست با توجه به مقدار موجود
     * باید مقدار ستون
     * syncState
     * را اصلاح کند
     */
    protected String errorNumber;

    protected String receivers; //"1234:14524:8457" or null



    public RootModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers) {
        this.kind = kind;
        this.organization = organization;
        this.userId = userId;
        this.appName = appName;
        this.hasFile = hasFile;
        this.lang = lang;
        this.pageNo = pageNo;
        this.pageItem = pageItem;
        this.chatRoomId = chatRoomId;
        this.nodeReceivedDate = nodeReceivedDate;
        this.errorNumber = errorNumber;
        this.receivers = receivers; //---> id1:id2:id3  or null

    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }



    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }



    public String getHasFile() {
        return hasFile;
    }

    public void setHasFile(String hasFile) {
        this.hasFile = hasFile;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageItem() {
        return pageItem;
    }

    public void setPageItem(String pageItem) {
        this.pageItem = pageItem;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }


    public String getNodeReceivedDate() {
        return nodeReceivedDate;
    }

    public void setNodeReceivedDate(String nodeReceivedDate) {
        this.nodeReceivedDate = nodeReceivedDate;
    }

    public String getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(String errorNumber) {
        this.errorNumber = errorNumber;
    }

}
