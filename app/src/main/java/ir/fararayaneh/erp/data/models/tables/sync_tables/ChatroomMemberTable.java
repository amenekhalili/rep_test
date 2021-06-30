package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

import static ir.fararayaneh.erp.commons.Commons.IS_CHATROOM_UN_MUTE;
import static ir.fararayaneh.erp.commons.Commons.IS_USER_NOT_TYPING;
import static ir.fararayaneh.erp.commons.Commons.IS_USER_OFFLINE;
import static ir.fararayaneh.erp.commons.Commons.IS_USER_ONLINE;

/**
 * in jadval ham az daste sync ast ham az daste jadval haii ke dar shoroo barname data paie migirad
 * <p>
 * id --->> در ابتدا صفر است و پس از انجام سینک مقدار آی دی از سرور
 * guid --->  جی یو آی دی ولی تاانتها بدون تغییر باقی میماند
 * * مقدار جی یو آی دی بدون خط فاصله درج شود
 * * syncState ----> from CommonSyncState class
 * * activityState ----> from CommonActivityState class


 * userId: id farde ezafe shode
 * B5HCUserTypeId: type user ke az util code kharej mishavad (admin--power--public)
 * chatroomId:
 * activityKind: (commonActivityKind.class) for user
 * attach :  {attachmentGUID+suffix(fileName),columnNumber,B5HCTypeId} (AttachmentJsonModel.class)


 * unSeenCount :  tedade payam nakhande
 */


/**
 * ---------------------------------
 * todo todo todo todo todo
 * هر تغییری در جدول چت روم ممبر به ChatroomMemberAddModel هم اعمال شود
 * چون فیلد های ChatroomMemberAddModel در واقع همان
 * فیلد های چت روم ممبر است به اضافه لیستی از چت روم مربوطه که در واقع یک عضو دارد
 *  * و نیز به آرایه مدل چت روم ممبر وب هم منتقل شود
 */
public class ChatroomMemberTable extends RealmObject implements IModels {

    public ChatroomMemberTable(String id, String userId, String name, String b5HCUserTypeId, String chatroomId,String chatroomGUID, int activityKind, String syncState, String activityState,  String attach, String guid
            , String isMute, String isOnline, String isTyping, String unSeenCount) {

        this.id = id;
        this.userId = userId;
        this.name = name;
        B5HCUserTypeId = b5HCUserTypeId;
        this.chatroomId = chatroomId;
        this.chatroomGUID = chatroomGUID;
        this.activityKind = activityKind;
        this.syncState = syncState;
        this.activityState = activityState;
        this.attach = attach;
        this.guid = guid;
        this.isMute = isMute;
        this.isOnline = isOnline;
        this.isTyping = isTyping;
        this.unSeenCount = unSeenCount;
    }

    public ChatroomMemberTable() {}

    private int activityKind;

    private String id, userId, name, B5HCUserTypeId, chatroomId,chatroomGUID, syncState, activityState, attach, isMute, isOnline, isTyping, unSeenCount;

    @PrimaryKey
    private String guid;


    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getUserId() {
        return userId == null ? Commons.NULL_INTEGER : userId;
    }

    public String getName() {
        return name == null ? Commons.NULL : name;
    }

    public String getB5HCUserTypeId() {
        return B5HCUserTypeId == null ? Commons.NULL_INTEGER : B5HCUserTypeId;
    }

    public String getChatroomGUID() {
        return chatroomGUID == null ? Commons.NULL : chatroomGUID;
    }

    public String getChatroomId() {
        return chatroomId == null ? Commons.NULL_INTEGER : chatroomId;
    }

    public String getIsMute() {
        return isMute== null ? Commons.NULL_INTEGER :isMute;
    }

    public String getIsOnline() {
        return isOnline== null ? Commons.NULL_INTEGER :isOnline;
    }

    public String getIsTyping() {
        return isTyping== null ? Commons.NULL_INTEGER :isTyping;
    }

    public String getUnSeenCount() {
        return unSeenCount== null ? Commons.NULL_INTEGER :unSeenCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChatroomGUID(String chatroomGUID) {
        this.chatroomGUID = chatroomGUID;
    }

    public void setB5HCUserTypeId(String b5HCUserTypeId) {
        B5HCUserTypeId = b5HCUserTypeId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public int getActivityKind() {
        return activityKind;
    }

    public void setActivityKind(int activityKind) {
        this.activityKind = activityKind;
    }

    public String getSyncState() {
        return syncState == null ? CommonSyncState.BEFORE_SYNC : syncState;
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

    public String getAttach() {
        return attach == null ? Commons.NULL_ARRAY : attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public void setIsMute(String isMute) {
        this.isMute = isMute;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public void setIsTyping(String isTyping) {
        this.isTyping = isTyping;
    }

    public void setUnSeenCount(String unSeenCount) {
        this.unSeenCount = unSeenCount;
    }
}
