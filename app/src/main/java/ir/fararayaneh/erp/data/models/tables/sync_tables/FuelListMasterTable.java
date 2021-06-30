package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 *         branchName  نام شعبه
 *         unitName    نام واحد متقاضی
 *
 *         دیتا ها به صورت استیت سینک به دست یوزر میرسد
 *       و یا به صورت اکسس دیناید میرسد(پس از مقدار دهی به فیلد مقدار)
 *   که اکتیویت استیت آنها ادد یا آپدیت یا دیلیت است
 *   و   در قالب ریپورتی به یوزر نمایش داده میشود
 *       و اگر اکتیویتی استیت برابر دیلیت نباشد یوزر میتواند میتواند دیتیل را ببیند
 *       و اگر یوزر ای دی راننده باشد میتواند مقدار دهی هم نماید
 *و پس از مقدار دهی اکتیویتی استیت تبدیل به آپدیت میشود و برای سرور ارسال میشود
 * و سرور آنرا اکسس دیناید میکند و برمیگرداند
 * و پس از ارشیو شدن در سرور، اکتیویتی استیت را هم دیلیت میکند تا یوزر آن رکورد را نبیند(هم مستر هم دیتیل)
 * ضمنا حتی یوزر راننده نمیتواند رکورد اکسس دیناید را ویرایش نماید
 *
 * در کلاس سینک رکورد های سینک نشده مستر چک نمیشود چون دیتایی برای سرور ارسال نمیشود
 *
 */


public class FuelListMasterTable extends RealmObject implements IModels {


    @PrimaryKey
    private String id;

    private String branchName,unitName,driverId,driverName,wareHouseName,departmentName,
                    fuelName,formNumber,formDate,description,syncState,
            activityState,oldValue;

    public FuelListMasterTable() {

    }

    public FuelListMasterTable(String id, String branchName, String unitName, String driverId, String driverName, String wareHouseName,String departmentName, String fuelName, String formNumber, String formDate, String description, String syncState, String activityState, String oldValue) {
        this.id = id;
        this.branchName = branchName;
        this.unitName = unitName;
        this.driverId = driverId;
        this.driverName = driverName;
        this.wareHouseName = wareHouseName;
        this.departmentName = departmentName;
        this.fuelName = fuelName;
        this.formNumber = formNumber;
        this.formDate = formDate;
        this.description = description;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
    }

    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getBranchName() {
        return branchName == null ? Commons.NULL : branchName;
    }

    public String getUnitName() {
        return unitName== null ? Commons.NULL : unitName;
    }

    public String getDriverId() {
        return driverId == null ? Commons.NULL_INTEGER :driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getWareHouseName() {
        return wareHouseName== null ? Commons.NULL : wareHouseName;
    }

    public String getDepartmentName() {
        return departmentName == null ? Commons.NULL : departmentName;
    }

    public String getFuelName() {
        return fuelName== null ? Commons.NULL : fuelName;
    }

    public String getFormNumber() {
        return formNumber == null ? Commons.NULL_INTEGER : formNumber;
    }

    public String getFormDate() {
        return formDate == null ? Commons.MINIMUM_TIME : formDate;
    }

    public String getDescription() {
        return description == null ? Commons.NULL : description;
    }

    public String getSyncState() {
        return syncState == null ? CommonSyncState.SYNCED : syncState;
    }

    public String getActivityState() {
        return activityState == null ? Commons.NULL : activityState;
    }

    public String getOldValue() {
        return oldValue == null ? Commons.NULL : oldValue;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
    }

    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
    }

    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
