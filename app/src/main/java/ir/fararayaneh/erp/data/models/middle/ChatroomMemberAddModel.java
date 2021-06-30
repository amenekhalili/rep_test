package ir.fararayaneh.erp.data.models.middle;

import java.util.ArrayList;

import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;

/**
 * use in chatroomMemberAddSocketModel
 * ---------------------------------
 * todo todo todo todo todo
 * هر تغییری در جدول چت روم ممبر به اینجا هم اعمال شود
 * چون فیلد های این کلاس در واقع همان فیلد های چت روم ممبر است
 * به اضافه لیستی از چت روم مربوطه که در واقع یک عضو دارد
 (چت رومی که به ان عضو اضافه شده است)
 */

public class ChatroomMemberAddModel implements IModels {

    private int activityKind;

    private String id,guid, userId, name, B5HCUserTypeId, chatroomId,chatroomGUID, syncState, activityState, attach, isMute, isOnline, isTyping, unSeenCount;

    private ArrayList<ChatroomTable> chatroomTableList;//only have one record (چت رومی که به ان عضو اضافه شده است)

    public ChatroomMemberAddModel(int activityKind, String id, String guid, String userId, String name, String b5HCUserTypeId, String chatroomId, String chatroomGUID, String syncState, String activityState, String attach, String isMute, String isOnline, String isTyping, String unSeenCount, ArrayList<ChatroomTable> chatroomTableList) {
        this.activityKind = activityKind;
        this.id = id;
        this.guid = guid;
        this.userId = userId;
        this.name = name;
        B5HCUserTypeId = b5HCUserTypeId;
        this.chatroomId = chatroomId;
        this.chatroomGUID = chatroomGUID;
        this.syncState = syncState;
        this.activityState = activityState;
        this.attach = attach;
        this.isMute = isMute;
        this.isOnline = isOnline;
        this.isTyping = isTyping;
        this.unSeenCount = unSeenCount;
        this.chatroomTableList = chatroomTableList;
    }

    public int getActivityKind() {
        return activityKind;
    }

    public void setActivityKind(int activityKind) {
        this.activityKind = activityKind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getB5HCUserTypeId() {
        return B5HCUserTypeId;
    }

    public void setB5HCUserTypeId(String b5HCUserTypeId) {
        B5HCUserTypeId = b5HCUserTypeId;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getChatroomGUID() {
        return chatroomGUID;
    }

    public void setChatroomGUID(String chatroomGUID) {
        this.chatroomGUID = chatroomGUID;
    }

    public String getSyncState() {
        return syncState;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public String getActivityState() {
        return activityState;
    }

    public void setActivityState(String activityState) {
        this.activityState = activityState;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getIsMute() {
        return isMute;
    }

    public void setIsMute(String isMute) {
        this.isMute = isMute;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getIsTyping() {
        return isTyping;
    }

    public void setIsTyping(String isTyping) {
        this.isTyping = isTyping;
    }

    public String getUnSeenCount() {
        return unSeenCount;
    }

    public void setUnSeenCount(String unSeenCount) {
        this.unSeenCount = unSeenCount;
    }

    public ArrayList<ChatroomTable> getChatroomTableList() {
        return chatroomTableList;
    }

    public void setChatroomTableList(ArrayList<ChatroomTable> chatroomTableList) {
        this.chatroomTableList = chatroomTableList;
    }
}
