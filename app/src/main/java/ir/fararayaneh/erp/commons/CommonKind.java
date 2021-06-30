package ir.fararayaneh.erp.commons;



/**
 * ویژگی کلی پکت ها:
 * روت همه پکت ها هم باید شبیه هم باشد
 * -------------------
 * به هیچ وجه نباید درون بادی یک آرایه بدون اسم وجود داشته باشد در بحث
 * evacuatePacket
 * در موبایل دچار مشکل میشویم
 * -----------------
 * syncState
 * هم روی سرور نود چک میشود و پکت هایی که توسط نود چک میشود نیاز به سینک استیت دارد
 */
public class CommonKind {


    /**
     *  روی دکمه لاگ اوت در هر کجای اپ است که اگر دیتای سینک نشده داریم نباید اجازه لاگ اوت داده شود
     *  * و اگر نداریم هم
     *  * ابتدا باید به سوکت یک پکت بفرستیم
     *  (DEVICEDELETE)
     *  که این دیوایس را ازلیست دیوایس های آن یوزر حذف کند و به دیتابیس هم بگوید
     *  اگر توانستیم این پکت را بفرستیم
     *  * سپس همه تیبل های یوزر را پاک کنیم
     *  * سپس به اسپلش اینتنت بزنیم
     *  اگر از سوکت این پکت را گرفتیم هم باید همه این کارها را انجام دهیم ینی انگار دستور لاگ اوت از دیتابیس آمده است
     */


    public static final String DEVICEDELETE = "DEVICEDELETE";//no need to rest
    public static final String LOGIN = "LOGIN";//no need to socket
    public static final String FORMREF = "FORMREF";
    public static final String SYNCFORMREF = "SYNCFORMREF";
    public static final String AGROFIELD = "AGROFIELD";
    public static final String SYNCAGROFIELD = "SYNCAGROFIELD";
    public static final String UTILCODE = "UTILCODE";
    public static final String SYNCUTILCODE = "SYNCUTILCODE";
    public static final String KARTABLERECIEVER = "KARTABLERECIEVER";
    public static final String SYNCKARTABLERECIEVER = "SYNCKARTABLERECIEVER";
    public static final String BASECODING = "BASECODING";
    public static final String SYNCBASECODING = "SYNCBASECODING";
    public static final String GOODS = "GOODS";
    public static final String SYNCGOODS = "SYNCGOODS";
    public static final String GOODTRANCE = "GOODTRANCE";
    public static final String SYNCGOODTRANCE = "SYNCGOODTRANCE";
    public static final String GROUPRELATED = "GROUPRELATED";
    public static final String SYNCGROUPRELATED = "SYNCGROUPRELATED";
    public static final String SYNCTASK = "SYNCTASK";//no need to GET by rest (task from other for me , comming by cartable)
    public static final String TIME = "TIME";
    public static final String SYNCTIME = "SYNCTIME";
    public static final String ACCIDENT = "ACCIDENT";
    public static final String SYNCACCIDENT = "SYNCACCIDENT";
    //----------------------------------------------------------------------------------------------
    public static final String INITWEBSOCKETCLIENT = "INITWEBSOCKETCLIENT"; //for send socket url from web tabs to shared worker(empty body)
    public static final String WEBSOCKETCLIENTSTATE = "WEBSOCKETCLIENTSTATE"; //for check web socket connection state(empty body)
    public static final String TABCLOSED = "TABCLOSED"; //for send close tab event to sharedWorker from tabs (empty body)
    public static final String TABLOCK = "TABLOCK"; //for lock tab in browser when needed (empty body)
    public static final String NODEBASEDATA = "NODEBASEDATA"; //use in node server for get primitive data
    public static final String EDITGPSTRACKER = "EDITGPSTRACKER"; //for change  GPSUserList from oracle to node server
    public static final String SYNCGPSTRACKER = "SYNCGPSTRACKER"; //for send gps data from node to oracle by rest and to mobile client by socket
    //----------------------------------------------------------------------------------------------
    /*
    برای توضیحات به جدول چت روم مراجعه شود
     */
    public static final String SYNCONLINE = "SYNCONLINE";//فقط از طرف سرور نود برای یوزر ها ارسال میشود بدون کچ شدن
    public static final String SYNCOFFLINE = "SYNCOFFLINE";//فقط از طرف سرور نود برای یوزر ها ارسال میشود بدون کچ شدن
    public static final String MESSAGE = "MESSAGE";
    public static final String SYNCMESSAGE = "SYNCMESSAGE";
    public static final String SYNCMESSAGEMORE = "SYNCMESSAGEMORE";
    public static final String SYNCMESSAGEEDIT = "SYNCMESSAGEEDIT";
    public static final String CHATROOMMEMBER = "CHATROOMMEMBER";
    public static final String SYNCCHATROOMMEMBERADD = "SYNCCHATROOMMEMBERADD";
    public static final String SYNCCHATROOMMEMBER = "SYNCCHATROOMMEMBER";
    public static final String CHATROOM = "CHATROOM";
    public static final String SYNCCHATROOM = "SYNCCHATROOM";
    public static final String SYNCCHATROOMADD = "SYNCCHATROOMADD";
    // ----------------------------------------------------------------------------------------------
    public static final String CARTABLE = "CARTABLE";//get from rest
    public static final String SYNCCARTABLE = "SYNCCARTABLE";//get from socket (no need for send socket but ...)
    public static final String ATTACH = "ATTACH";//baraye vorod be act sync jahate ersal file ha, in kind niazi be get nadarad, har data attach marboot be khod ra darad
    public static final String WAREHOUSEHANDLING = "WAREHOUSEHANDLING"; //for get
    public static final String SYNCWAREHOUSEHANDLING = "SYNCWAREHOUSEHANDLING"; //for socket
    public static final String SYNCWEIGHING = "SYNCWEIGHING"; //for socket
    public static final String WEIGHING = "WEIGHING"; //for rest
    public static final String SYNCWAREHOUSEHANDLINGLISTOUT = "SYNCWAREHOUSEHANDLINGLISTOUT"; //no have GET kind
    public static final String PURCHASABLEGOODS = "PURCHASABLEGOODS"; //for rest
    public static final String SYNCPURCHASABLEGOODS = "SYNCPURCHASABLEGOODS"; //for socket
    public static final String SALABLEGOODS = "SALABLEGOODS"; //for rest
    public static final String SYNCSALABLEGOODS = "SYNCSALABLEGOODS"; //for socket
    public static final String SYNCCONTACTBOOK = "SYNCCONTACTBOOK"; //for socket
    public static final String CONTACTBOOK = "CONTACTBOOK"; //for rest
    public static final String ADDRESSBOOK = "ADDRESSBOOK"; //for rest
    public static final String SYNCADDRESSBOOK = "SYNCADDRESSBOOK"; //for socket
    public static final String SYNCCOUNTRYDIVISION = "SYNCCOUNTRYDIVISION"; //for socket
    public static final String COUNTRYDIVISION = "COUNTRYDIVISION"; //for rest
    public static final String FUELLISTMASTER = "FUELLISTMASTER"; //for rest
    public static final String SYNCFUELLISTMASTER = "SYNCFUELLISTMASTER"; //for socket
    public static final String FUELLISTDETAIL = "FUELLISTDETAIL"; //for rest
    public static final String SYNCFUELLISTDETAIL = "SYNCFUELLISTDETAIL"; //for socket

}
