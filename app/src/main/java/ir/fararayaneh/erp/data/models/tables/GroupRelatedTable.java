package ir.fararayaneh.erp.data.models.tables;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;


/**
 * parentTypeId :
 * یکی از چند آی دی  موجود در هر کدام از ستون های کانفیگ  جدول
 * formRef
 *
 * B5IdRefId :
 * همان ستون ای دی در جدول
 * BaseCoding
 *
 */
public class GroupRelatedTable extends RealmObject implements IModels {

    public GroupRelatedTable() {
    }

    public GroupRelatedTable(String id, String b5IdRefId, String groupId, int isMain, String parentTypeId, String parentCode, String parentName, String syncState,String rootId) {
        this.id = id;
        B5IdRefId = b5IdRefId;
        this.groupId = groupId;
        this.isMain = isMain;
        this.parentTypeId = parentTypeId;
        this.parentCode = parentCode;
        this.parentName = parentName;
        this.syncState = syncState;
        this.rootId = rootId;
    }

    @PrimaryKey
    private String id;
    private int isMain;
    private String parentCode, parentName,B5IdRefId,groupId,parentTypeId,rootId;

    //---------------------------------------------------------------------->>
    private String syncState;//for SyncActivity


    public String getSyncState() {
        return syncState==null? CommonSyncState.SYNCED :syncState;//chon hame data az database be  ma reside ast
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }
    //---------------------------------------------------------------------->>


    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getB5IdRefId() {
        return B5IdRefId == null ? Commons.NULL_INTEGER : B5IdRefId;
    }

    public String getGroupId() {
        return groupId == null ? Commons.NULL_INTEGER : groupId;
    }

    public String getParentTypeId() {
        return parentTypeId == null ? Commons.NULL_INTEGER : parentTypeId;
    }

    public int getIsMain() {
        return isMain;
    }


    public String getParentCode() {
        return parentCode == null ? Commons.NULL : parentCode;
    }

    public String getParentName() {
        return parentName == null ? Commons.NULL : parentName;
    }

    public String getRootId() {
        return rootId == null ? Commons.NULL_INTEGER : rootId;
    }

    //------------------------------------------------------------------>>


    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setB5IdRefId(String b5IdRefId) {
        B5IdRefId = b5IdRefId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public void setIsMain(int isMain) {
        this.isMain = isMain;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
