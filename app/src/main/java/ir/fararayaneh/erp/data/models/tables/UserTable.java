package ir.fararayaneh.erp.data.models.tables;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * این جدول با مقادیر وارد شده از سوی یوزر و یوزر آی دی سرور تکمیل میشود
 * چون این جدول ارسال و دریافت نمیشود نیاز به سینک استیت نداریم
 */


public class UserTable extends RealmObject implements IModels {


    public UserTable() {

    }

    public UserTable(String userId, String userName, String organization, String passWord, String userImageUrl, String deviceId, String companyName, String syncState) {
        this.userId = userId;
        this.userName = userName;
        this.organization = organization;
        this.passWord = passWord;
        this.userImageUrl = userImageUrl;
        this.deviceId = deviceId;
        this.companyName = companyName;
        this.syncState = syncState;
    }

    @PrimaryKey
    private String userId;
    private String userName, organization, passWord, userImageUrl, deviceId, companyName;

    //---------------------------------------------------------------------->>
    private String syncState;//for check unSync data


    public String getSyncState() {
        return syncState == null ? CommonSyncState.SYNCED : syncState;//chon hame data az database be  ma reside ast
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }
    //---------------------------------------------------------------------->>

    public String getUserId() {
        return userId == null ? Commons.NULL : userId;
    }

    public String getUserName() {
        return userName == null ? Commons.NULL : userName;
    }

    public String getOrganization() {
        return organization == null ? Commons.NULL : organization;
    }

    public String getPassWord() {
        return passWord == null ? Commons.NULL : passWord;
    }

    public String getUserImageUrl() {
        return userImageUrl == null ? Commons.NULL : userImageUrl;
    }

    public String getDeviceId() {
        return deviceId == null ? Commons.NULL : deviceId;
    }

    public String getCompanyName() {
        return companyName == null ? Commons.NULL : companyName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
