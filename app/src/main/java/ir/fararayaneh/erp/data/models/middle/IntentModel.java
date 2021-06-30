package ir.fararayaneh.erp.data.models.middle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ir.fararayaneh.erp.data.models.online.request.rest.RestRequestModel;

public class IntentModel implements Serializable {

    private boolean goConfigActFromSplash,comeHereForResult,getNewData,canChangeReqAmount,canUserAddAttach,canUserRemoveAttach;
    private List<FacilitiesModel> facilitiesModelList;
    private List<RestRequestModel> requestModelList;
    private List<GoodTransDetailsModel> goodTransDetailsModelList;
    private ArrayList<Class> classArrayListForUpdate;
    private ArrayList<String> stringList;
    private ArrayList<String> stringList2;
    private ArrayList<String> stringList3;
    private ArrayList<String> stringList4;
    private String someString,someString2,someString3,someString4;
    private int someInt,someInt2;
    private Date startDate,endDate;


    public boolean isGoConfigActFromSplash() {
        return goConfigActFromSplash;
    }

    public void setGoConfigActFromSplash(boolean goConfigActFromSplash) {
        this.goConfigActFromSplash = goConfigActFromSplash;
    }

    public List<FacilitiesModel> getFacilitiesModelList() {
        return facilitiesModelList;
    }

    public void setFacilitiesModelList(List<FacilitiesModel> facilitiesModelList) {
        this.facilitiesModelList = facilitiesModelList;
    }

    public List<RestRequestModel> getRequestModelList() {
        return requestModelList;
    }

    public void setRequestModelList(List<RestRequestModel> requestModelList) {
        this.requestModelList = requestModelList;
    }

    public ArrayList<Class> getClassArrayListForUpdate() {
        return classArrayListForUpdate;
    }

    public void setClassArrayListForUpdate(ArrayList<Class> classArrayListForUpdate) {
        this.classArrayListForUpdate = classArrayListForUpdate;
    }

    public ArrayList<String> getStringList() {
        return stringList;
    }

    public void setStringList(ArrayList<String> stringList) {
        this.stringList = stringList;
    }

    public String getSomeString() {
        return someString;
    }

    public void setSomeString(String someString) {
        this.someString = someString;
    }

    public ArrayList<String> getStringList2() {
        return stringList2;
    }

    public void setStringList2(ArrayList<String> stringList2) {
        this.stringList2 = stringList2;
    }

    public String getSomeString2() {
        return someString2;
    }

    public void setSomeString2(String someString2) {
        this.someString2 = someString2;
    }

    public int getSomeInt() {
        return someInt;
    }

    public void setSomeInt(int someInt) {
        this.someInt = someInt;
    }

    public String getSomeString3() {
        return someString3;
    }

    public void setSomeString3(String someString3) {
        this.someString3 = someString3;
    }

    public ArrayList<String> getStringList3() {
        return stringList3;
    }

    public void setStringList3(ArrayList<String> stringList3) {
        this.stringList3 = stringList3;
    }

    public ArrayList<String> getStringList4() {
        return stringList4;
    }

    public void setStringList4(ArrayList<String> stringList4) {
        this.stringList4 = stringList4;
    }

    public int getSomeInt2() {
        return someInt2;
    }

    public void setSomeInt2(int someInt2) {
        this.someInt2 = someInt2;
    }

    public boolean isComeHereForResult() {
        return comeHereForResult;
    }

    public void setComeHereForResult(boolean comeHereForResult) {
        this.comeHereForResult = comeHereForResult;
    }

    public boolean isGetNewData() {
        return getNewData;
    }

    public void setGetNewData(boolean getNewData) {
        this.getNewData = getNewData;
    }

    public String getSomeString4() {
        return someString4;
    }

    public void setSomeString4(String someString4) {
        this.someString4 = someString4;
    }

    public boolean isCanChangeReqAmount() {
        return canChangeReqAmount;
    }

    public void setCanChangeReqAmount(boolean canChangeReqAmount) {
        this.canChangeReqAmount = canChangeReqAmount;
    }

    public List<GoodTransDetailsModel> getGoodTransDetailsModelList() {
        return goodTransDetailsModelList;
    }

    public void setGoodTransDetailsModelList(List<GoodTransDetailsModel> goodTransDetailsModelList) {
        this.goodTransDetailsModelList = goodTransDetailsModelList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isCanUserAddAttach() {
        return canUserAddAttach;
    }

    public void setCanUserAddAttach(boolean canUserAddAttach) {
        this.canUserAddAttach = canUserAddAttach;
    }

    public boolean isCanUserRemoveAttach() {
        return canUserRemoveAttach;
    }

    public void setCanUserRemoveAttach(boolean canUserRemoveAttach) {
        this.canUserRemoveAttach = canUserRemoveAttach;
    }
}
