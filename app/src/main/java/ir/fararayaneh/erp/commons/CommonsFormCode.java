package ir.fararayaneh.erp.commons;


public class CommonsFormCode {

    public static final String TIME_SHEET_FORM_CODE = "215"; //user can not set end date after now
    public static final String SHEET_PLAN_FORM_CODE = "520";
    public static final String DEVICE_TIME_SHEET_FORM_CODE ="483" ; //یوزر یک تایم میبیند و انتخاب میکند که تاریخ شروع است یا پایان
    public static final String TASK_FORM_CODE = "28";
    public static final String WAREHOUSE_HANDLING_FORM_CODE ="316" ;
    public static final String WEIGHTING_FORM_CODE ="60" ;
    public static final String REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE ="276" ;//در خواست کالا از انبار
    public static final String PERMANENT_RECEIPT_GOODS_FORM_CODE ="275" ;//رسید موقت کالا(no main)
    public static final String WAREHOUSE_TRANSFERENCE_FORM_CODE ="278" ;//حواله انبار(no main)
    public static final String RECEIPT_GOODS_FORM_CODE ="224" ;//(no main)رسید کالا
    public static final String BUY_SERVICES_INVOICE_FORM_CODE ="153" ;//(no main)فاکتور خرید خدمات
    public static final String BUYER_REQUEST_FORM_CODE ="284" ;//سفارش خریدار
    public static final String GOODS_DELIVERY_PROCEEDINGS_FORM_CODE ="274" ;//صورت مجلس تحویل کالا
    public static final String PRODUCTION_FORM_CODE ="62" ;//تولید محصولات
    public static final String FUEL_DETAIL_FORM_CODE = "548";//(no main)سوخت رسانی مستر
    //*****************************************
    //این فرم کد فقط برای انتقال به اکتیویتی GoodsForGoodTrans است و در خود اکتیویتی GoodsForGoodTrans ما
    // از فرم کد مستر(مثلا 276) استفاده میکنیم
    //بنا بر این فرم کد اصلی را هم همراه خود به اکتیویتی GoodForGoodTrans میبریم
    public static final String ADD_EDIT_ONE_GOOD_TO_GOOD_TRANS ="448" ;
    //*****************************************
    //--------------------------form code for reports-----------------------------------------------
    public static final String CARTABLE_REPORT_FORM_CODE ="1204" ;
    public static final String GOODS_REPORT_FORM_CODE ="53" ;//C5GoodsReport
    public static final String TASK_REPORT_FORM_CODE ="301" ;//T5TaskReport
    public static final String TIME_REPORT_FORM_CODE ="1250" ;//T5TimeSheetReport
    public static final String WEIGHTING_REPORT_FORM_CODE ="1117" ;
    public static final String REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE ="1315" ;//ریپورت در خواست کالا از انبار
    public static final String PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE ="1316" ;//رسید موقت کالا ریپورت
    public static final String WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE ="1313" ;//حواله انبار ریپورت
    public static final String RECEIPT_GOODS_REPORT_FORM_CODE ="1318" ;//رسید کالا ریپورت
    public static final String BUY_SERVICES_INVOICE_REPORT_FORM_CODE ="1322" ;//فاکتور خرید خدمات ریپورت
    public static final String BUYER_REQUEST_REPORT_FORM_CODE ="1305" ;//سفارش خریدار ریپورت
    public static final String GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE ="1317" ;//صورت مجلس تحویل کالا ریپورت
    public static final String PRODUCTION_REPORT_FORM_CODE ="1324" ;//تولید محصولات ریپورت
    public static final String FUEL_MASTER_REPORT_FORM_CODE = "1903";//(اولین صفحه سوخت رسانی که از صفحه اصلی باز میشود)مستر سوخت رسانی ریپورت
    public static final String FUEL_DETAIL_REPORT_FORM_CODE ="869"; //دیتیل سوخت رسانی ریپورت(no main)

    //*****************************************
    //این فرم کد فقط برای انتقال به اکتیویتی SELECTION activity است و در خود اکتیویتی SELECTION ما
    // از فرم کد مستر(مثلا 276) استفاده میکنیم
    //بنا بر این فرم کد اصلی را هم همراه خود به اکتیویتی SELECTION میبریم
    //برای شرایط دیگری که میخواهیم از اکتیویتی دیگری برای سلکت کردن بیاییم باید فکر دیگری نمود
    public static final String SELECT_GOODS_FOR_ORDER_FORM_CODE ="95" ;//use for select goods from GoodsTable,in selection activity for result (fake)
    //--------------------------form code for reports-----------------------------------------------

}