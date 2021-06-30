package ir.fararayaneh.erp.data.models.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * attach : "[{"attachmentGUID":"1a2b3","columnNumber":"123","B5HCTypeId":"123"},...]"
 * <p>
 * * این جدول با رست پر میشود
 * * KIND=Goods
 * * * و همه سینک استیت های اولیه باید
 * * synced
 * * باشد
 * * و یوزر فقط میتواند کالاهایی را ببیند که
 * * سینک استیت آنها سینک یا سینک اررور یا بی فور سینک است
 * * و پس از ویرایش توسط کاربر حالت ان به
 * * beforeSync
 * * تغغیر یابد وریسپانس از سرور نود که
 * * state=Synced
 * * باشد
 * * ویا اگر به اررور بخورد
 * * state=syncError
 * * است و هنوز امکان نمایش به یوزر را دارد
 * نکته اینجا است که یوزر فقط میتواند ستون اتچمنت این جدول را ویرایش کند
 * و بنابراین حتی اگر در وضعیت
 * سینک ارور یا بی فور سینک هم باشد میتواند کالا را ببیند و یا استفاده های دیگر کند
 * <p>
 *
 *goodSUOMList : یک آرایه جیسون برای تبدیل واحدهای فرعی کالا به یکدیگر(واحد اصلی همیشه در سطر اول جیسون قرار میگیرد
 *goodTypeList : یک آرایه جیسون برای دیدن واریته های مختلف کالا به یکدیگر
 *attach :  {attachmentGUID+suffix(fileName),columnNumber,B5HCTypeId} (AttachmentJsonModel.class)
 */


public class GoodsTable extends RealmObject implements IModels {

    public GoodsTable(String id, String c5BrandId, String brandName, String b5HCStatusId, String nationalityCode, String code, String name, String latinName, String description, String googleKeyWord, String techInfo, String statusName, String attach, String guid, String serial, String goodSUOMList, String goodTypeList, String oldValue, String syncState) {
        this.id = id;
        C5BrandId = c5BrandId;
        this.brandName = brandName;
        B5HCStatusId = b5HCStatusId;
        this.nationalityCode = nationalityCode;
        this.code = code;
        this.name = name;
        this.latinName = latinName;
        this.description = description;
        this.googleKeyWord = googleKeyWord;
        this.techInfo = techInfo;
        this.statusName = statusName;
        this.attach = attach;
        this.guid = guid;
        this.serial = serial;
        this.goodSUOMList = goodSUOMList;
        this.goodTypeList = goodTypeList;
        this.oldValue = oldValue;
        this.syncState = syncState;
    }

    public GoodsTable() {

    }

    @PrimaryKey
    private String id;
    private String  C5BrandId,brandName, B5HCStatusId,
            nationalityCode, code, name, latinName, description, googleKeyWord, techInfo, statusName, attach,
            guid,//for use in report activity
            serial,
            goodSUOMList,
            goodTypeList,
            oldValue;

    private String syncState;//for SyncActivity


    public String getSyncState() {
        return syncState == null ? CommonSyncState.SYNCED : syncState;//chon hame data az database be  ma reside ast
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    //---------------------------------------------------------------------->>
    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getC5BrandId() {
        return C5BrandId == null ? Commons.NULL_INTEGER : C5BrandId;
    }

    public String getB5HCStatusId() {
        return B5HCStatusId == null ? Commons.NULL_INTEGER : B5HCStatusId;
    }

    public String getNationalityCode() {
        return nationalityCode == null ? Commons.NULL : nationalityCode;
    }

    public String getCode() {
        return code == null ? Commons.NULL : code;

    }

    public String getName() {
        return name == null ? Commons.NULL : name;

    }

    public String getLatinName() {
        return latinName == null ? Commons.NULL : latinName;
    }

    public String getDescription() {
        return description == null ? Commons.NULL : description;

    }

    public String getGoogleKeyWord() {
        return googleKeyWord == null ? Commons.NULL : googleKeyWord;
    }

    public String getTechInfo() {
        return techInfo == null ? Commons.NULL : techInfo;
    }

    public String getBrandName() {
        return brandName == null ? Commons.NULL : brandName;
    }

    public String getStatusName() {
        return statusName == null ? Commons.NULL : statusName;
    }

    public String getGoodSUOMList() {
        return goodSUOMList == null ? Commons.NULL_ARRAY : goodSUOMList;
    }

    public String getGoodTypeList() {
        return goodTypeList == null ? Commons.NULL_ARRAY : goodTypeList;
    }

    //----------------------------------------------------------------------->>

    public void setId(String id) {
        this.id = id;
    }

    public void setC5BrandId(String c5BrandId) {
        C5BrandId = c5BrandId;
    }

    public void setB5HCStatusId(String b5HCStatusId) {
        B5HCStatusId = b5HCStatusId;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGoogleKeyWord(String googleKeyWord) {
        this.googleKeyWord = googleKeyWord;
    }

    public void setTechInfo(String techInfo) {
        this.techInfo = techInfo;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getAttach() {
        return attach == null ? Commons.NULL_ARRAY : attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getGuid() {
        return String.valueOf(id);
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSerial() {
        return serial == null ? Commons.NULL : serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getOldValue() {
        return oldValue == null ? Commons.NULL : oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

}
