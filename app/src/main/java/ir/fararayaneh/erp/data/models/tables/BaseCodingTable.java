package ir.fararayaneh.erp.data.models.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 *  این جدول معادل گروه ها و کدینگ های موجود در دیتابیس است
 * -----------------------------------------------
 * اسامی ثابت درون دیتا بیس و مواد اولیه و دارایی های ثابت و اسامی شرکت ها و...
 *
 * امکان اینسرت و آپدیت بالایی دارد بر خلاف جدول
 * utilCode
 * که ثابت های برنامه نویسی سیستم است و معمولا بدون تغییر است
 *
 * ستون
 * B5FormRefId
 * در اوراکل نشان میدهد که این موجودیت مربوط به کدام فرم است ولی در موبایل استفاده ای ندارد
 */

public class BaseCodingTable extends RealmObject implements IModels {
    public BaseCodingTable(String id, String code, String b5FormRefId, String nationalityId, int isActive, String name, String activitySubject, String description, String techCode, String syncState) {
        this.id = id;
        this.code = code;
        B5FormRefId = b5FormRefId;
        this.nationalityId = nationalityId;
        this.isActive = isActive;
        this.name = name;
        this.activitySubject = activitySubject;
        this.description = description;
        this.techCode = techCode;
        this.syncState = syncState;
    }

    public BaseCodingTable() {
    }
    //---------------------------------------------------------------------->>

    @PrimaryKey
    private String id;
    private String B5FormRefId,nationalityId,code;
    private int isActive;
    private String name,activitySubject,description,techCode;
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

    public String getB5FormRefId() {
        return B5FormRefId == null ? Commons.NULL_INTEGER : B5FormRefId;
    }

    public String getNationalityId() {
        return nationalityId == null ? Commons.NULL_INTEGER : nationalityId;
    }

    public String getCode() {
        return code == null ? Commons.NULL_INTEGER : code;
    }

    //---------------------------------------------------------------------->>


    public void setId(String id) {
        this.id = id;
    }

    public void setB5FormRefId(String b5FormRefId) {
        B5FormRefId = b5FormRefId;
    }

    public void setNationalityId(String nationalityId) {
        this.nationalityId = nationalityId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIsActive() {
        return isActive;
    }

    public String getName() {
        return name == null ? Commons.NULL : name;
    }

    public String getActivitySubject() {
        return activitySubject == null ? Commons.NULL : activitySubject;
    }

    public String getDescription() {
        return description == null ? Commons.NULL : description;
    }

    public String getTechCode() {
        return techCode == null ? Commons.NULL : techCode;
    }

    //---------------------------------------------------------------------->>


    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActivitySubject(String activitySubject) {
        this.activitySubject = activitySubject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTechCode(String techCode) {
        this.techCode = techCode;
    }
}
