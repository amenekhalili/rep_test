package ir.fararayaneh.erp.data.models.middle;

import java.util.ArrayList;

import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;

/**
 * use in chatroomAddSocketModel
 * <p>
 *----------------------------------
 * // todo todo todo todo todo
 *  در صورت تغییر دیکته فیلد
 * chatroomMemberTableList
 * حتما در فانکشن
 * addChatroomAndUsersToNodeDb
 *سرور نود هم اعمال شود
 * ---------------------------------
 * todo todo todo todo todo
 * هر تغییری در جدول چت روم به اینجا هم اعمال شود
 * چون فیلد های این کلاس در واقع همان فیلد های چت روم است به اضافه لیستی از ممبرها
 */

public class ChatroomAddModel implements IModels {


    public ChatroomAddModel(String id, String b5HCChatTypeId, String name, String description, String syncState, String activityState, String oldValue, String attach, String guid, String insertDate, ArrayList<ChatroomMemberTable> chatroomMemberTableList) {
        this.id = id;
        B5HCChatTypeId = b5HCChatTypeId;
        this.name = name;
        this.description = description;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
        this.attach = attach;
        this.guid = guid;
        this.insertDate = insertDate;
        this.chatroomMemberTableList = chatroomMemberTableList;
    }

    private ArrayList<ChatroomMemberTable> chatroomMemberTableList;

    private String name, description, syncState, activityState, oldValue, attach, id, B5HCChatTypeId, guid, insertDate;


    public ArrayList<ChatroomMemberTable> getChatroomMemberTableList() {
        return chatroomMemberTableList;
    }

    public void setChatroomMemberTableList(ArrayList<ChatroomMemberTable> chatroomMemberTableList) {
        this.chatroomMemberTableList = chatroomMemberTableList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getB5HCChatTypeId() {
        return B5HCChatTypeId;
    }

    public void setB5HCChatTypeId(String b5HCChatTypeId) {
        B5HCChatTypeId = b5HCChatTypeId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }


}
