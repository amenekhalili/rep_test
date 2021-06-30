package ir.fararayaneh.erp.data.models.tables;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * * این جدول معادل HCBASE و b5HC در دیتابیس اوراکل است و
 * parentCode
 * همان کد موجود در جدول
 * hcbase
 * است و
 * code
 * همان کد های موجود در
 * b5hc
 * است.
 * -----------------------------------------------
 * مجموعه ای از
 * 1-هارد کد های سیستم مانند کلمه "ساعت ورود" و یا "ساعت خروج" که دارای id جداگانه هستند و parentId آنها آی دی گروه حسابداری است
 * 2-دیتای پایه سیستم مانند واحد های پولی و یا انواع دبیرخانه در حسابداری و...
 *
 * B5IdRefIds : مجموعه کانکت شده آز آی دی های نشان دهنده انواع کارکرد های زمین های کشاورزی
 */


public class UtilCodeTable extends RealmObject implements IModels {


    public UtilCodeTable() {
    }

    public UtilCodeTable(String id, String parentId, String name, String code, String parentName, String parentCode, String b5IdRefIds, String syncState) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.code = code;
        this.parentName = parentName;
        this.parentCode = parentCode;
        B5IdRefIds = b5IdRefIds;
        this.syncState = syncState;
    }

    @PrimaryKey
    private String id;
    private String parentId;
    private String name;
    private String code;
    private String parentName;
    private String parentCode;
    private String B5IdRefIds;
    //---------------------------------------------------------------------->>
    private String syncState;//for SyncActivity

    public String getSyncState() {
        return syncState==null? CommonSyncState.SYNCED :syncState;//chon hame data az database be  ma reside ast
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }
    //---------------------------------------------------------------------->>


    public void setId(String id) {
        this.id = id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public void setB5IdRefIds(String B5IdRefIds) {
        this.B5IdRefIds = B5IdRefIds;
    }

    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getParentId() {
        return parentId == null ? Commons.NULL_INTEGER : parentId;
    }

    public String getName() {
        return name==null?Commons.NULL:name;
    }

    public String getCode() {
        return code==null?Commons.NULL:code;
    }

    public String getParentName() {
        return parentName==null?Commons.NULL:parentName;
    }

    public String getParentCode() {
        return parentCode==null?Commons.NULL:parentCode;
    }

    public String getB5IdRefIds() {
        return B5IdRefIds==null?Commons.NULL:B5IdRefIds;
    }
}
