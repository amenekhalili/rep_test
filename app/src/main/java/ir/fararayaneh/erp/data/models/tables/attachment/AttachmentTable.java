package ir.fararayaneh.erp.data.models.tables.attachment;


import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonComboClickType;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.data_handler.DownloadFileDataHandler;


/*
  به طور کلی مسول ارسال فایل
  ir.fararayaneh.erp.services.UploadFileForeGroundService
  است که کل جدول attachment را اسکن کرده و تمام فایل های ارسال نشده یا به خطا خورده را ارسال میکند
  این سرویس از هرکجای برنامه و از جمله از سینک اکتیویتی
  میتواند صدا زده شود
  برای اتچمنت مفهومی به اسم سوکت و گت کردن وجود ندارد
  ---------------------------------------------------------------------------------
  :روند ایجاد فایل
  به این ترتیب است که به هر فایل یک جی یو آی دی اختصاص داده میشود
  و با اطلاعات زیر در دیتابیس و اس دی کارت ذخیره میشود
  و دیگر نیازی به آی دی موجودی که قرار است اتچمنت برای او خورده شود نیست
  بلکه در برگشت از
  اکتیویتی اتچمنت باید لیستی از
  {attachmentGUID+suffix(fileName),columnNumber,B5HCTypeId} (AttachmentJsonModel.class)
  columnNumber : مقدار پیش فرض 1
  و همچنین Uri
  را به اکتیویتی مبدا برگردانیم تا در جدول مربوط به موجودی که اتچمنت ها مربوط به او است
  و در ستون اتچمنت آن جدول به صورت جیسون ارای ذخیره شود
  "[{"attachmentGUID+suffix":"123","columnNumber":"123","B5HCTypeId":"123"},...]"
  <p>
  -----------------------
  سه یوز کیس زیر وجود دارد
  1-
  در زمان دانلود فایل مثلا در چت روم یا البوم اتچمنت
  اگر فایل را در اس دی کارت نداریم و میخواهیم آنرا دانلود کنیم لازم نیست که پس از دانلود آنرا در جدول درج کنیم
  2-
  در زمان آپلود همه جدول پیمایش شود و اگر هر ردیف دارای استیت
  beforeSync,onSync,syncError
  <p>
  است و انرا در sdcart
  داریم اقدام به آپلود ان کرده و سپس
  اگر عملیات موفق بود
  در پاسخ استیت انرا سینک شده در جدول درج کرده و برویم سراغ ردیف بعدی
  و اگر به مشکل خوردیم کاری انجام ندهیم
  و اگر فایل موجود  نبود
  آنرا سینک شده بکنیم و برویم سراغ ردیف بعدی
  و باز اولین ردیف سینک نشده را پیدا کردیم
  3- Deprecated (مفهومی برای گت کردن اتچمنت ها وجود ندارد)
  در زمان GET ما از دیتابیس همه دیتاهایی را که در دیتابیس وجود دارد ولی اتچمنت آن موجود نیست را به صورت
  beforeSync
  و اگر اتچمنت آن در دیتابیس وجود دارد چیزی نگیریم
  و هیچ ردیفی را پاک نکنیم و فقط اینسرت یا اپدیت کنیم
  تا در زمان آپلود اگر آن فایل روی گوشی ما موجود بود آنرا
  آپلود کنیم
  -----------------------
  <p>
  در زمان آپلود فایل باید از یک رست استفاده شود و هر فایل
  که سینک استیت آن
  beforeSync,onSync,syncError
  است
  با مشخصات اضافه ای مانند
  (*
  attachmentGUID,
  mimeType,
  FILE_BLOB,     --file
  tag
  description
  {dar ghalebe   FileUploadJsonModel.class}
  <p>
  )
  که سرور نیاز دارد ارسال شود(ب همین پست موجود اما در جای جداگانه ای صدا زده شود)(kind=ATTACH) و در صورت موفقیت
  استیت را تبدیل به
  synced
  و در صورت خطا استیت را تبدیل به
  syncError
  کنیم
  <p>
  -----------------------
  <p>
  attachmentGUID  --->> آی دی اتچمنت که توسط تولید کننده فایل ایجاد میشود چه دیتابیس باشد چه کلاینت و همه جا با این آی دی کار خواهد شد)
  syncState       --->> CommonSyncState از کلاس
  tag             --->> از یوزر
  description     --->> از یوزر
  suffix          --->> پسوند فایل(.mp3 or ....)
  <p>
  در زمان نمایش فایل ابتدا چک میکنیم که فایلی با نام
  attachmentGUID+suffix
  داریم یا خیر و اگر نداریم آنرا دانلود نماییم
  و لازم نیست چیزی در جدول اعمال شود، چه  فایل خود ما است
  چه فایل دیتابیس است
 */

/**
 * نکته در زمان آپلود فایل و یا ارسال و دریافت سوکت
 * فیلدattachmentGUID
 * به عنوان
 * guid+suffix
 * استفاده میشود و فقط در جدول زیر به عنوان guid
 * تنها استفاده میشود
 */

/*
در سمت اوراکل در زمان دریافت پکت های مختلف در
b5clientsync.createAttach
آبجکت اتچ همه پکت ها را لوپ می اندازد و اگر به ازای
اکسترنال ای دی موجود در اتچ، اتچمنتی وجود نداشته باشد
ویا به ازای نداشتن اکسترنال آیدی همان فایل نیم را نداشته باشد
(چون ممکن است فایل از دیتابیس به موبایل فرستاده شده و دوباره از موبایل به دیتابیس برگردانده شود)
یک اتچ خالی زده میشود با اکسترنال آیدی مناسب و اگر شروط بالا برقرار باشد اتچمنت وجود دارد
و اتچ دوباره زده نمیشود
و چک میشود که آیا ریلیتد داریم یا خیر
و اگر ریلیتد نداریم، ریلیتد را میزنیم
و از طرف دیگر در فانکشن
b5clientsync.setAttach
اگر خود اتچمنت با مقدار اکسترنال آیدی
اگر وجود دارد،با مقدار فایل آپدیت میشود(چون اینجا صرفا از موبایل صدا زده میشود و هیچ گاه نمیشود فایل یک اکسترنال ایدی را در موبایل ویرایش نمود
بنابراین اگر فایل را آپدیت میکنیم یا در 99 درصد مواقع فایل فیک را به فایل اصلی تبدیل میکنیم یا در 1 درصد موارد فایل قبلی را به فایل قبلی اپدیت میکنیم)
و اگر کلا وجود ندارد اینسرت میشود
نکته اینجاست که تنها زمانی این فانکشن صدا زده میشود که فایلی که درون موبایل ساخته شده است و میخواهد آپلود شود
و فایلهایی که از قبل فقط در دیتابیس بوده اند در جدول اتچمنت وجود ندارند که بخواهند آپلود شوند
بنابراین اهمیتی ندارد که اول فایل به دیتابیس برسد یا ریلیشن آن

از طرف دیگر یک فانکشن
getAttach
هم داریم که به عنوان
attachmentGUID
مقدار فایل نیم دیتابیس را پر میکند
و به موبایل میدهد که دلیلش این است که برخی اتچمنت ها ممکن است اکسترنال ایدی
نداشته باشد و چون هدف ما آپدیت کردن محتوای اتچمنت در سمت موبایل نیست مشکلی به وجود نمیاید
 و صرفا یا اتچمنتی اضافه میشود
 و یا اتچمنتی از لیست اتچمنت های یک رکورد حذف میشود که اگر این اتچمنت توسط موبایل اضافه شده باشد و هنوز برای دیتابیس ارسال نشده است آن رکورد
 خود فایل برای دیتابیس ارسال میشود اما ریلیشنی به رکوردی نمیخورد چون از لیست اتچمنت های آن رکورد حذف شده است
 و اگر  قبلا برای دیتابیس هم ارسال شده است، و یا توسط کلاینت دیگری و یا خود دیتابیس ان ریلیشن ایجاد شده
 است ، ان ریلیشن حذف نمیشود و باید در وب انرا حذف نمود
 */


public class AttachmentTable extends RealmObject implements IModels {

    public AttachmentTable(String attachmentGUID, String syncState, String tag, String description, String suffix,String longitude,String latitude) {
        this.attachmentGUID = attachmentGUID;
        this.syncState = syncState;
        this.tag = tag;
        this.description = description;
        this.suffix = suffix;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public AttachmentTable() {

    }

    @PrimaryKey
    private String attachmentGUID;
    private String syncState, tag, description, suffix,longitude,latitude;

    public void setAttachmentGUID(String attachmentGUID) {
        this.attachmentGUID = attachmentGUID;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getAttachmentGUID() {
        return attachmentGUID == null ? Commons.NULL : attachmentGUID;
    }

    public String getSyncState() {
        return syncState == null ? CommonSyncState.BEFORE_SYNC : syncState;
    }

    public String getTag() {
        return tag == null ? Commons.NULL : tag;
    }

    public String getDescription() {
        return description == null ? Commons.NULL : description;
    }

    /**
     * send .jpeg by default
     */
    public String getSuffix() {
        return suffix == null ? CommonsDownloadFile.IMAGE_SUFFIX_NAME : suffix;
    }

    public String getLongitude() {
        return longitude== null ? Commons.NULL :latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude== null ? Commons.NULL :latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
