package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;
/*
      from d7accident of oracle table:
      guid;    همان آی دی در هنگام گت کردن
      id;
      serial;
      insertDate; //(D7accident.INSERTDATETIME)
      accidentDate;
      insuranceName;//(C5COMPANY_ID+C5COMPANYBRANCH_ID) (as name)
      DESCRIPTION;
      B5FinancialYearID;
      address;//(B5COUNTRYDIVISION_ID+CARPOSITION) (as name)
      parentId;//(D7ACCIDENT_ID)
      registrant;//(U5USER_ID) (as name) (کسی که حادثه را ثبت میکند)
      damageName;//(D7SC_DAMAGEKIND_ID) (as name)
      formId; //(B5FORMREF_ID)
      inspectionId;
      syncState;
      activityState;
      oldValue;//( C5PERSON_ID+C5COMPANY_ID+C5COMPANYBRANCH_ID+D7SC_DAMAGEKIND_ID+B5FinancialYearID)
      attach;

بیزینس :
یوزر در شروع نصب برنامه و یا با زدن دکمه سینک مربوط به اکسیدنت
رکوردها را با کایند
ACCIDENT
و استیت ADD
دریافت میکند (صرفا رکوردهای تولید شده در 2 ماه گذشته و آنهایی که عکس ندارند)
یوزر در ریپورت خود رکوردهایی با شرایط زیر را ببیند
(add-synced,update-brfore,update-synced,update-sysncerror)
یوزر در زمان مشاهده ریپورت در موبایل میتواند با زدن دکمه حذف
آن رکورد را تبدیل به
accessDenied
بکند چون ممکن است رکوردهایی را دارد میبیند که
update-synced
است (میتوان رکورد های update-synced را به یوزر نشان نداد که نیازی به اکسس دیناید نباشد که باید به طور عملی چک شود که ایا مشکلی ندارد یوزر با این موضوع)
از طرف دیگر با کلیک بر روی یک رکورد در ریپورت موبایل
یوزر صرفا عکس های متصل به آن رکورد را میبیند
که اگر آنها را حذف کند ، صرفا در ارایه موبایل حذف میشود و ریلیشنی در دیتابیس حذف نمیشود که باید چک شود که این موضوع مشکلی ایجاد نمیکند ؟
اگر یوزر بخواهد اتچمنت اضافه کند باید تایپ مورد نیاز خسارت نیز ارسال شود و همچنین به طور عمومی لوکیشن هم برای اتچمنت ثبت میشود
فعلا ویرایش دیگری در سمت موبایل برای رکورد به جز اضافه کردن ویا کم کردن اتچمنت وجود ندارد

 */
public class AccidentTable extends RealmObject implements IModels {

    @PrimaryKey
    private String guid;
    private String id;
    private String serial;
    private String insertDate; //(D7accident.INSERTDATETIME)
    private String accidentDate;
    private String insuranceName;//(C5COMPANY_ID+C5COMPANYBRANCH_ID)
    private String DESCRIPTION;
    private String B5FinancialYearID;
    private String address;//(B5COUNTRYDIVISION_ID+CARPOSITION)
    private String parentId;//(D7ACCIDENT_ID)
    private String registrant;//(U5USER_ID)
    private String damageName;//(D7SC_DAMAGEKIND_ID)
    private String formId; //(B5FORMREF_ID)
    private String inspectionId;
    private String syncState;
    private String activityState;
    private String oldValue;//( id+C5PERSON_ID+C5COMPANY_ID+C5COMPANYBRANCH_ID+D7SC_DAMAGEKIND_ID+B5FinancialYearID)
    private String attach;

    public AccidentTable() {
    }

    public AccidentTable(String guid, String id, String serial, String insertDate, String accidentDate,
                         String insuranceName, String DESCRIPTION, String b5FinancialYearID, String address,
                         String parentId, String registrant, String damageName, String formId, String inspectionId,
                         String syncState, String activityState, String oldValue, String attach) {
        this.guid = guid;
        this.id = id;
        this.serial = serial;
        this.insertDate = insertDate;
        this.accidentDate = accidentDate;
        this.insuranceName = insuranceName;
        this.DESCRIPTION = DESCRIPTION;
        B5FinancialYearID = b5FinancialYearID;
        this.address = address;
        this.parentId = parentId;
        this.registrant = registrant;
        this.damageName = damageName;
        this.formId = formId;
        this.inspectionId = inspectionId;
        this.syncState = syncState;
        this.activityState = activityState;
        this.oldValue = oldValue;
        this.attach = attach;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getAccidentDate() {
        return accidentDate;
    }

    public void setAccidentDate(String accidentDate) {
        this.accidentDate = accidentDate;
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getB5FinancialYearID() {
        return B5FinancialYearID;
    }

    public void setB5FinancialYearID(String b5FinancialYearID) {
        B5FinancialYearID = b5FinancialYearID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public void setDamageName(String damageName) {
        this.damageName = damageName;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
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

    public void setAttach(String attach) {
        this.attach = attach;
    }

//--------------------------------------------------------------------------------------------------
    public String getAddress() {
        return address == null ? Commons.NULL : address;
    }

    public String getParentId() {
        return parentId == null ? Commons.NULL_INTEGER : parentId;
    }

    public String getRegistrant() {
        return registrant== null ? Commons.NULL : registrant;
    }

    public String getDamageName() {
        return damageName== null ? Commons.NULL : damageName;
    }

    public String getFormId() {
        return formId== null? Commons.NULL_INTEGER : formId;
    }

    public String getInspectionId() {
        return inspectionId== null? Commons.NULL_INTEGER : inspectionId;
    }

    public String getSyncState() {
        return syncState== null ? CommonSyncState.BEFORE_SYNC : syncState;
    }

    public String getActivityState() {
        return activityState== null ? CommonActivityState.ADD : activityState;
    }

    public String getOldValue() {
        return oldValue == null ? Commons.NULL : oldValue;
    }

    public String getAttach() {
        return attach ==null?Commons.NULL_ARRAY:attach;
    }
}
