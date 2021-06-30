package ir.fararayaneh.erp.data.models;


import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

import ir.fararayaneh.erp.data.models.middle.FacilitiesModel;
import ir.fararayaneh.erp.data.models.online.response.RestResponseModel;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;


public class GlobalModel implements IModels {

    private boolean myBoolean;
    private String myIP,userName,organization,passWord,userId,userImageUrl,deviceId,companyName,myString,myString2,myString3,myString4,myString5,downloadURL,downloadPath,fileName,chatroomId;
    private ArrayList<Class> classArrayList;
    private ArrayList<Integer> intArrayList;
    private ArrayList<GoodsTable> goodsTableArrayList;
    private RestResponseModel restResponseModel;
    private int count,myInt,myInt2;
    private ArrayList<String> stringArrayList;
    private ArrayList<ArrayList<String>> listStringArrayList,listStringArrayList2;
    private ArrayList<ArrayList<Integer>> listIntArrayList;
    private ArrayList<FacilitiesModel> facilitiesArrayList;
    private ArrayList<ArrayList<FacilitiesModel>> listFacilitiesArrayList;
    private Context context;
    private Uri uri;
    private ArrayList<String> stringArrayList2;
    private Date startDate,Enddate;

    public String getMyIP() {
        return myIP;
    }

    public void setMyIP(String myIP) {
        this.myIP = myIP;
    }

    public boolean isMyBoolean() {
        return myBoolean;
    }

    public void setMyBoolean(boolean myBoolean) {
        this.myBoolean = myBoolean;
    }

    public ArrayList<Class> getClassArrayList() {
        return classArrayList;
    }

    public void setClassArrayList(ArrayList<Class> classArrayList) {
        this.classArrayList = classArrayList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RestResponseModel getRestResponseModel() {
        return restResponseModel;
    }

    public void setRestResponseModel(RestResponseModel restResponseModel) {
        this.restResponseModel = restResponseModel;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<String> getStringArrayList() {
        return stringArrayList;
    }

    public void setStringArrayList(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    public ArrayList<FacilitiesModel> getFacilitiesArrayList() {
        return facilitiesArrayList;
    }

    public void setFacilitiesArrayList(ArrayList<FacilitiesModel> facilitiesArrayList) {
        this.facilitiesArrayList = facilitiesArrayList;
    }

    public ArrayList<ArrayList<FacilitiesModel>> getListFacilitiesArrayList() {
        return listFacilitiesArrayList;
    }

    public void setListFacilitiesArrayList(ArrayList<ArrayList<FacilitiesModel>> listFacilitiesArrayList) {
        this.listFacilitiesArrayList = listFacilitiesArrayList;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getMyInt() {
        return myInt;
    }

    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }

    public ArrayList<ArrayList<String>> getListStringArrayList() {
        return listStringArrayList;
    }

    public void setListStringArrayList(ArrayList<ArrayList<String>> listStringArrayList) {
        this.listStringArrayList = listStringArrayList;
    }

    public ArrayList<ArrayList<Integer>> getListIntArrayList() {
        return listIntArrayList;
    }

    public void setListIntArrayList(ArrayList<ArrayList<Integer>> listIntArrayList) {
        this.listIntArrayList = listIntArrayList;
    }

    public String getMyString() {
        return myString;
    }

    public void setMyString(String myString) {
        this.myString = myString;
    }

    public String getMyString2() {
        return myString2;
    }

    public void setMyString2(String myString2) {
        this.myString2 = myString2;
    }

    public String getMyString3() {
        return myString3;
    }

    public void setMyString3(String myString3) {
        this.myString3 = myString3;
    }

    public int getMyInt2() {
        return myInt2;
    }

    public void setMyInt2(int myInt2) {
        this.myInt2 = myInt2;
    }

    public ArrayList<Integer> getIntArrayList() {
        return intArrayList;
    }

    public void setIntArrayList(ArrayList<Integer> intArrayList) {
        this.intArrayList = intArrayList;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }



    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public String getMyString4() {
        return myString4;
    }

    public void setMyString4(String myString4) {
        this.myString4 = myString4;
    }


    public void setStringArrayList2(ArrayList<String> stringArrayList2) {
        this.stringArrayList2 = stringArrayList2;
    }

    public ArrayList<String> getStringArrayList2() {
        return stringArrayList2;
    }

    public ArrayList<ArrayList<String>> getListStringArrayList2() {
        return listStringArrayList2;
    }

    public void setListStringArrayList2(ArrayList<ArrayList<String>> listStringArrayList2) {
        this.listStringArrayList2 = listStringArrayList2;
    }

    public ArrayList<GoodsTable> getGoodsTableArrayList() {
        return goodsTableArrayList;
    }

    public void setGoodsTableArrayList(ArrayList<GoodsTable> goodsTableArrayList) {
        this.goodsTableArrayList = goodsTableArrayList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEnddate() {
        return Enddate;
    }

    public void setEnddate(Date enddate) {
        Enddate = enddate;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getMyString5() {
        return myString5;
    }

    public void setMyString5(String myString5) {
        this.myString5 = myString5;
    }
}
