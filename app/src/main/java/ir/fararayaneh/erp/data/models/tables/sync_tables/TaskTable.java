package ir.fararayaneh.erp.data.models.tables.sync_tables;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;


/**
 * ردیف هایی از جدول سینک شود که :
 * syncState = beforeSync
 *******************************************************************
 * formCode : TASK_FORM_CODE = 28 (CommonsFormCode class)
 ******************************************************************
 * user only can set task for current financial year
 ******************************************************************
 * * بعد از انجام سینک اگر امکان درج اتچمنت وجود دارد حتما ستون
 * b5IdRefId
 * جدول اتچمنت هم آپدیت شود
 ******************************************************************
 * id --->> در ابتدا صفر است و پس از انجام سینک مقدار آی دی از سرور
 * guid --->  جی یو آی دی ولی تاانتها بدون تغییر باقی میماند
 *  * مقدار جی یو آی دی بدون خط فاصله درج شود
 * subject ----> from user input
 * comments ----> from user input
 * geoLocation ----> lat:long,lat:long,lat:long,lat:long
 * syncState ----> from CommonSyncState class
 * activityState ----> from CommonActivityState class
 * oldValue ----> from server
 * B5IdRefId ---->  id of whom that task created for him/her (from KartableRecieverTable table)
 * B5IdRefId2 ---->  (userId in here)
 * B5IdRefId3 ---->  (task ref in here)برای ذخیره مقدار کانفیگ ها که به ستون آی دی در جدول بیس کدینگ منتهی شده است
 * T5SCTypeId ---->  from utilCode table by value : 'TAT'
 * B5HCPriorityId ---->  from utilCode table by value : 'PRY'
 * B5HCStatusId :  (draft,during,trash,archive,....) (name can GET from utilCode) (VCS)
 * <p>
 * *****************************************************************
 *  attach :  {attachmentGUID+suffix(fileName),columnNumber,B5HCTypeId} (AttachmentJsonModel.class)
 */

public class TaskTable extends RealmObject implements IModels {

    private String  subject, comments, geoLocation, syncState, activityState, oldValue,attach;
    @PrimaryKey
    private String guid;
    private String taskDate, deadLine;
    private String id,B5IdRefId, B5IdRefId2, B5IdRefId3, T5SCTypeId, B5HCPriorityId,B5HCStatusId;
    public TaskTable(String id,String guid ,String subject, String comments, String geoLocation, String syncState, String activityState, String oldValue, String taskDate, String deadLine,String attach, String B5IdRefId, String B5IdRefId2, String B5IdRefId3, String T5SCTypeId, String B5HCPriorityId,String B5HCStatusId) {
        this.id = id;
        this.guid = guid;
        this.subject = subject;
        this.comments = comments;
        this.geoLocation = geoLocation;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
        this.attach = attach;
        this.taskDate = taskDate;
        this.deadLine = deadLine;
        this.B5IdRefId = B5IdRefId;
        this.B5IdRefId2 = B5IdRefId2;
        this.B5IdRefId3 = B5IdRefId3;
        this.T5SCTypeId = T5SCTypeId;
        this.B5HCPriorityId = B5HCPriorityId;
        this.B5HCStatusId = B5HCStatusId;
    }

    public TaskTable() {

    }

    public String getGuid() {
        return guid == null ? Commons.NULL : guid;
    }

    public String getSubject() {
        return subject == null ? Commons.NULL : subject;
    }

    public String getComments() {
        return comments == null ? Commons.NULL : comments;
    }

    public String getGeoLocation() {
        return geoLocation == null ? Commons.NULL : geoLocation;
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

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
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





    public String getAttach() {
        return attach==null?Commons.NULL_ARRAY:attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTaskDate() {
        return taskDate == null ? Commons.NULL : taskDate;
    }

    public void setTaskDate(String taskDate) {

        this.taskDate = taskDate;
    }

    public String getDeadLine() {
        return deadLine == null ? Commons.NULL : deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setB5IdRefId(String b5IdRefId) {
        B5IdRefId = b5IdRefId;
    }

    public void setB5IdRefId2(String b5IdRefId2) {
        B5IdRefId2 = b5IdRefId2;
    }

    public void setB5IdRefId3(String b5IdRefId3) {
        B5IdRefId3 = b5IdRefId3;
    }

    public void setT5SCTypeId(String t5SCTypeId) {
        T5SCTypeId = t5SCTypeId;
    }

    public void setB5HCPriorityId(String b5HCPriorityId) {
        B5HCPriorityId = b5HCPriorityId;
    }

    public void setB5HCStatusId(String b5HCStatusId) {
        B5HCStatusId = b5HCStatusId;
    }

    public String getId() {
        return id== null ? Commons.NULL_INTEGER : id;
    }

    public String getB5IdRefId() {
        return B5IdRefId== null ? Commons.NULL_INTEGER : B5IdRefId;
    }

    public String getB5IdRefId2() {
        return B5IdRefId2== null ? Commons.NULL_INTEGER : B5IdRefId2;
    }

    public String getB5IdRefId3() {
        return B5IdRefId3== null ? Commons.NULL_INTEGER : B5IdRefId3;
    }

    public String getT5SCTypeId() {
        return T5SCTypeId== null ? Commons.NULL_INTEGER : T5SCTypeId;
    }

    public String getB5HCPriorityId() {
        return B5HCPriorityId== null ? Commons.NULL_INTEGER : B5HCPriorityId;
    }

    public String getB5HCStatusId() {
        return B5HCStatusId== null ? Commons.NULL_INTEGER : B5HCStatusId;
    }
}
