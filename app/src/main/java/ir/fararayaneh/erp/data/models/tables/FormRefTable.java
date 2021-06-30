package ir.fararayaneh.erp.data.models.tables;


import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * برای نمایش اطلاعات موجود در combobox های مختلف درون فرم ها
 * از طرف دیگر انواع مختلف AppName نشان دهنده سر تیتر های ما در صفحه اصلی و با گرفتن distinct
 * روی آن، title های مختلف، نشان دهنده زیر سیستم های موجود در آن appName است
 * <p>
 * formCode : نشان دهنده آی دی یونیک هر فرم است که باید در اکتیویتی خاصی نمایش داده و پر شده و در
 * جدول یا جداولی ذخیره شود
 *
 *
 * formLocation (commons.NEED_FORCE_LOCATION and so on ....)
 *
 * 0 =no need location (لوکیشن را نگیریم)
 * 2 = maybe location (اگر توانستیم لوکیشن را در این فرم کد بگیریم)
 * 1= should location (حتما لوکیشن را بگیریم)
 *
 *
 * آی دی های موجود در
 * configIdRef
 * ها در واقع آی دی گروه های موجود در اوراکل است که با استفاده از جدول گروپ ریلیتد
 * و بیس کدینگ به موجودیت ها یا کدینگ ها میرسیم
 *
 * در اوراکل کانفیگ های قابل نمایش در هر فرم در جدول های
 * b5attributevalue
 * و
 * b5attributeRelated
 * قرار داده شده است
 */


public class FormRefTable extends RealmObject implements IModels {

    public FormRefTable(String formId, String formCode, String formName, String formTitle, String appName, String configIdRef1, String configIdRef2, String configIdRef3, String configIdRef4, String configIdRef5, String configIdRef6, String configIdRef7, String configIdRef8, String configIdRef9, String configIdRef10, String configIdRef11, String configIdRef12, String configIdRef13, String configIdRef14, String configIdRef15, String iconUrl, String syncState,int mainMenuActive,int formLocation) {
        this.formId = formId;
        this.formCode = formCode;
        this.formName = formName;
        this.formTitle = formTitle;
        this.appName = appName;
        this.configIdRef1 = configIdRef1;
        this.configIdRef2 = configIdRef2;
        this.configIdRef3 = configIdRef3;
        this.configIdRef4 = configIdRef4;
        this.configIdRef5 = configIdRef5;
        this.configIdRef6 = configIdRef6;
        this.configIdRef7 = configIdRef7;
        this.configIdRef8 = configIdRef8;
        this.configIdRef9 = configIdRef9;
        this.configIdRef10 = configIdRef10;
        this.configIdRef11 = configIdRef11;
        this.configIdRef12 = configIdRef12;
        this.configIdRef13 = configIdRef13;
        this.configIdRef14 = configIdRef14;
        this.configIdRef15 = configIdRef15;
        this.iconUrl = iconUrl;
        this.syncState = syncState;
        this.mainMenuActive = mainMenuActive;
        this.formLocation = formLocation;
    }

    public FormRefTable() {
    }

    @PrimaryKey
    private String formId;

    private String formCode;

    private String formName;

    private String formTitle;
    @Index
    private String appName;

    private String configIdRef1;

    private String configIdRef2;

    private String configIdRef3;

    private String configIdRef4;

    private String configIdRef5;

    private String configIdRef6;

    private String configIdRef7;

    private String configIdRef8;

    private String configIdRef9;

    private String configIdRef10;

    private String configIdRef11;

    private String configIdRef12;

    private String configIdRef13;

    private String configIdRef14;

    private String configIdRef15;

    private String iconUrl;

    private int formLocation;

    private int mainMenuActive;

    //---------------------------------------------------------------------->>
    private String syncState;//for SyncActivity


    public String getSyncState() {
        return syncState == null ? CommonSyncState.SYNCED : syncState;//chon hame data az database be  ma reside ast
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }
    //---------------------------------------------------------------------->>


    public void setFormId(String formId) {
        this.formId = formId;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setConfigIdRef1(String configIdRef1) {
        this.configIdRef1 = configIdRef1;
    }

    public void setConfigIdRef2(String configIdRef2) {
        this.configIdRef2 = configIdRef2;
    }

    public void setConfigIdRef3(String configIdRef3) {
        this.configIdRef3 = configIdRef3;
    }

    public void setConfigIdRef4(String configIdRef4) {
        this.configIdRef4 = configIdRef4;
    }

    public void setConfigIdRef5(String configIdRef5) {
        this.configIdRef5 = configIdRef5;
    }

    public void setConfigIdRef6(String configIdRef6) {
        this.configIdRef6 = configIdRef6;
    }

    public void setConfigIdRef7(String configIdRef7) {
        this.configIdRef7 = configIdRef7;
    }

    public void setConfigIdRef8(String configIdRef8) {
        this.configIdRef8 = configIdRef8;
    }

    public void setConfigIdRef9(String configIdRef9) {
        this.configIdRef9 = configIdRef9;
    }

    public void setConfigIdRef10(String configIdRef10) {
        this.configIdRef10 = configIdRef10;
    }

    public void setConfigIdRef11(String configIdRef11) {
        this.configIdRef11 = configIdRef11;
    }

    public void setConfigIdRef12(String configIdRef12) {
        this.configIdRef12 = configIdRef12;
    }

    public void setConfigIdRef13(String configIdRef13) {
        this.configIdRef13 = configIdRef13;
    }

    public void setConfigIdRef14(String configIdRef14) {
        this.configIdRef14 = configIdRef14;
    }

    public void setConfigIdRef15(String configIdRef15) {
        this.configIdRef15 = configIdRef15;
    }

    public void setMainMenuActive(int mainMenuActive) {
        this.mainMenuActive = mainMenuActive;
    }

    public String getFormId() {
        return formId == null ? Commons.NULL_INTEGER : formId;
    }

    public String getFormCode() {
        return formCode == null ? Commons.NULL_INTEGER : formCode;
    }

    public String getFormName() {
        return formName == null ? Commons.NULL : formName;
    }

    public String getFormTitle() {
        return formTitle == null ? Commons.NULL : formTitle;
    }

    public String getAppName() {
        return appName == null ? Commons.NULL : appName;
    }

    public String getConfigIdRef1() {
        return configIdRef1 == null ? Commons.NULL : configIdRef1;
    }

    public String getConfigIdRef2() {
        return configIdRef2 == null ? Commons.NULL : configIdRef2;
    }

    public String getConfigIdRef3() {
        return configIdRef3 == null ? Commons.NULL : configIdRef3;
    }

    public String getConfigIdRef4() {
        return configIdRef4 == null ? Commons.NULL : configIdRef4;
    }

    public String getConfigIdRef5() {
        return configIdRef5 == null ? Commons.NULL : configIdRef5;
    }

    public String getConfigIdRef6() {
        return configIdRef6 == null ? Commons.NULL : configIdRef6;
    }

    public String getConfigIdRef7() {
        return configIdRef7 == null ? Commons.NULL : configIdRef7;
    }

    public String getConfigIdRef8() {
        return configIdRef8 == null ? Commons.NULL : configIdRef8;
    }

    public String getConfigIdRef9() {
        return configIdRef9 == null ? Commons.NULL : configIdRef9;
    }

    public String getConfigIdRef10() {
        return configIdRef10 == null ? Commons.NULL : configIdRef10;
    }

    public String getConfigIdRef11() {
        return configIdRef11 == null ? Commons.NULL : configIdRef11;
    }

    public String getConfigIdRef12() {
        return configIdRef12 == null ? Commons.NULL : configIdRef12;
    }

    public String getConfigIdRef13() {
        return configIdRef13 == null ? Commons.NULL : configIdRef13;
    }

    public String getConfigIdRef14() {
        return configIdRef14 == null ? Commons.NULL : configIdRef14;
    }

    public String getConfigIdRef15() {
        return configIdRef15 == null ? Commons.NULL : configIdRef15;
    }

    public String getIconUrl() {
        return iconUrl == null ? Commons.NULL : iconUrl;
    }

    public int getMainMenuActive() {
        return mainMenuActive;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getFormLocation() {
        return formLocation;
    }//defualt value is 0 (Commons.NO_NEED_FORCE_LOCATION)

    public void setFormLocation(int formLocation) {
        this.formLocation = formLocation;
    }
}
