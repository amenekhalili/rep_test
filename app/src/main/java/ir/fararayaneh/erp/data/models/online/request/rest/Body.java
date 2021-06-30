package ir.fararayaneh.erp.data.models.online.request.rest;

import java.io.Serializable;

import ir.fararayaneh.erp.data.models.IModels;

public class Body  implements IModels, Serializable {


    private String userName, passWord,deviceId;

    public Body(String userName, String passWord, String deviceId) {
        this.userName = userName;
        this.passWord = passWord;
        this.deviceId = deviceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
