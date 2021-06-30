package ir.fararayaneh.erp.data.models.tables.sync_tables;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * in jadval ham az daste sync ast ham az daste jadval haii
 * ke dar shoroo barname data paie migirad
 * <p>
 * id --->> در ابتدا صفر است و پس از انجام سینک مقدار آی دی از سرور
 * guid --->  جی یو آی دی ولی تاانتها بدون تغییر باقی میماند
 * * مقدار جی یو آی دی بدون خط فاصله درج شود
 * <p>
 * syncState ----> from CommonSyncState class
 * <p>
 * activityState ----> from CommonActivityState class
 * <p>
 * oldValue ----> from server
 * <p>
 * B5HCMessageTypeId : payam system (create chatroom,...) ya payam user
 * <p>
 * U5UserId : ferestande message
 * <p>
 * G5MessageId : parent message (if we have note parent , we have -0)
 * <p>
 * attach :  {attachmentGUID+suffix(fileName),columnNumber,B5HCTypeId} (AttachmentJsonModel.class)
 *
 createDate  [az local device ferestande zade mishavad,,baraye namayesh icon loding]
 insertDate  [az oracle  zade mishavad,,baraye namayesh icon 2ticks]
 sendDate    [az node  zade mishavad,,baraye namayesh icon 1ticks] @Deprecated(az local device ferestande zade mishavad,,baraye namayesh icon loding)
 seenDate    [az local device girande zade mishavad,,baraye namayesh icon 2ticks abi]
 deleteForMe  : araye az userhaii ke baraye khodeshan message ra delete kardand dar gheire insorate []
 seenCount : tedad user haii ke payam ra didand
 receivedCount : tedad user haii ke payam be anha reside ast

 isOld ---> 1 ---> is old and we get it in load more message

 sortDate :bayad hamishe bar hasbe zamane greenwich

 */
public class MessageTable extends RealmObject implements IModels {


    public MessageTable() {}

    public MessageTable(String id, String g5MessageId, String chatroomId,
                        String b5HCMessageTypeId, String userId, String createDate,
                        String insertDate, String sendDate, String seenDate, String syncState,
                        String activityState,  String message, String attach,
                        String guid,
                        String deleteForMe,
                        String seenCount,
                        String receivedCount,
                        String isOld,
                        long sortDate) {
        this.id = id;
        this.isOld = isOld;
        G5MessageId = g5MessageId;
        this.chatroomId = chatroomId;
        B5HCMessageTypeId = b5HCMessageTypeId;
        this.userId = userId;
        this.createDate = createDate;
        this.insertDate = insertDate;
        this.sendDate = sendDate;
        this.seenDate = seenDate;
        this.syncState = syncState;
        this.activityState = activityState;
        this.message = message;
        this.attach = attach;
        this.guid = guid;
        this.deleteForMe = deleteForMe;
        this.seenCount = seenCount;
        this.receivedCount = receivedCount;
        this.sortDate = sortDate;

    }


    private String id, G5MessageId, chatroomId, B5HCMessageTypeId,
            userId,deleteForMe,seenCount,receivedCount,isOld;

    private String createDate, insertDate, sendDate, seenDate;

    private String syncState, activityState, message, attach;

    @PrimaryKey
    private String guid;

    private long sortDate;//for sort


    public long getSortDate() {
        return sortDate;
    }

    public void setSortDate(long sortDate) {
        this.sortDate = sortDate;
    }


    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getG5MessageId() {
        return G5MessageId == null ? Commons.NULL_INTEGER : G5MessageId;
    }

    public String getChatroomId() {
        return chatroomId == null ? Commons.NULL_INTEGER : chatroomId;
    }

    public String getB5HCMessageTypeId() {
        return B5HCMessageTypeId == null ? Commons.NULL_INTEGER : B5HCMessageTypeId;
    }

    public String getDeleteForMe() {
        return deleteForMe == null ? Commons.NULL_ARRAY : deleteForMe;
    }

    public void setDeleteForMe(String deleteForMe) {
        this.deleteForMe = deleteForMe;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setG5MessageId(String g5MessageId) {
        G5MessageId = g5MessageId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public void setB5HCMessageTypeId(String b5HCMessageTypeId) {
        B5HCMessageTypeId = b5HCMessageTypeId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message == null ? Commons.NULL : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSyncState() {
        return syncState == null ? CommonSyncState.BEFORE_SYNC : syncState;
    }

    public String getSeenCount() {
        return seenCount== null ? Commons.NULL_INTEGER :seenCount;
    }

    public String getReceivedCount() {
        return receivedCount== null ? Commons.NULL_INTEGER :receivedCount;
    }

    public void setReceivedCount(String receivedCount) {
        this.receivedCount = receivedCount;
    }

    public void setSeenCount(String seenCount) {
        this.seenCount = seenCount;
    }

    public String getActivityState() {
        return activityState == null ? CommonActivityState.ADD : activityState;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public void setActivityState(String activityState) {
        this.activityState = activityState;
    }

    public String getGuid() {
        return guid == null ? Commons.NULL : guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUserId() {
        return userId == null ? Commons.NULL_INTEGER : userId;
    }

    public String getAttach() {
        return attach == null ? Commons.NULL_ARRAY : attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getCreateDate() {
        return createDate == null ? Commons.MINIMUM_TIME : createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getInsertDate() {
        return insertDate == null ? Commons.MINIMUM_TIME : insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getSendDate() {
        return sendDate == null ? Commons.MINIMUM_TIME : sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSeenDate() {
        return seenDate == null ? Commons.MINIMUM_TIME : seenDate;
    }

    public void setSeenDate(String seenDate) {
        this.seenDate = seenDate;
    }

    public String getIsOld() {
        return isOld == null ? Commons.IS_NOT_OLD_MESSAGE : isOld;
    }

    public void setIsOld(String isOld) {
        this.isOld = isOld;
    }
}
