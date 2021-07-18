package ir.fararayaneh.erp.utils.intent_handler;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.activities.attach_albom.AttachAlbumActivity;
import ir.fararayaneh.erp.activities.attach_provider.AttachProviderActivity;
import ir.fararayaneh.erp.activities.barcode_scanner.BarCodeActivity;
import ir.fararayaneh.erp.activities.chat_list.ChatListActivity;
import ir.fararayaneh.erp.activities.chat_message.MessageActivity;
import ir.fararayaneh.erp.activities.configs.ConfigsActivity;
import ir.fararayaneh.erp.activities.fuel_process.FuelActivity;
import ir.fararayaneh.erp.activities.good_for_good_trans.GoodForGoodTrans;
import ir.fararayaneh.erp.activities.good_trance.GoodTranceActivity;
import ir.fararayaneh.erp.activities.itruducer_act.IntroHelp;
import ir.fararayaneh.erp.activities.login.LoginActivity;
import ir.fararayaneh.erp.activities.main.MainActivity;
import ir.fararayaneh.erp.activities.map.MapsActivity;
import ir.fararayaneh.erp.activities.report.ReportsActivity;
import ir.fararayaneh.erp.activities.search.SearchActivity;
import ir.fararayaneh.erp.activities.select_activity.SelectionActivity;
import ir.fararayaneh.erp.activities.splash.SplashActivity;
import ir.fararayaneh.erp.activities.sync_act.SyncActivity;
import ir.fararayaneh.erp.activities.task.TaskActivity;
import ir.fararayaneh.erp.activities.time.TimeActivity;
import ir.fararayaneh.erp.activities.time_filter.TimeFilterActivity;
import ir.fararayaneh.erp.activities.update_tables.UpdateTablesActivity;
import ir.fararayaneh.erp.activities.warehouse_handling.WarehouseHandlingActivity;
import ir.fararayaneh.erp.activities.web_activity.WebActivity;
import ir.fararayaneh.erp.activities.weighing.WeighingActivity;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.data.models.middle.GoodTransDetailsModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.online.request.rest.RestRequestModel;
import ir.fararayaneh.erp.services.SocketService;

/**
 * these methods should be called only from presenter layer
 * <p>
 * for those intents that no need to form code
 */
public class ActivityIntents {


    public static void goMainActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    //-------------------------------------------------------------------------------------------->>>
    public static void goChatListActivity(Context context) {
        context.startActivity(new Intent(context, ChatListActivity.class));
    }
    //-------------------------------------------------------------------------------------------->>>

    /**
     * @param chatTypeCode from CommonChatroomTypeCode
     */
    public static void goMessageActivity(Context context,String chatRoomId,String chatTypeCode,String chatRoomGUID) {
        Intent intent = new Intent(context, MessageActivity.class);
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(chatRoomId);
        intentModel.setSomeString2(chatTypeCode);
        intentModel.setSomeString3(chatRoomGUID);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        context.startActivity(intent);
    }
    //-------------------------------------------------------------------------------------------->>>
    public static void goConfigsActivity(Context context, boolean configActFromSplash) {
        Intent intent = new Intent(context, ConfigsActivity.class);
        IntentModel intentModel = new IntentModel();
        intentModel.setGoConfigActFromSplash(configActFromSplash);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        context.startActivity(intent);
    }

    //-------------------------------------------------------------------------------------------->>>
    public static void goSplashActivity(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
//-------------------------------------------------------------------------------------------->>>

    public static void goLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    //-------------------------------------------------------------------------------------------->>>
    public static void cancelResultIntent(Activity activity) {
        Intent returnIntent = new Intent();
        activity.setResult(Activity.RESULT_CANCELED, returnIntent);
    }

    //-------------------------------------------------------------------------------------------->>>
    public static void goUpdateTableActForResult(BaseActivity activity, List<RestRequestModel> requestModelList, ArrayList<Class> classArrayListForUpdate) {

        IntentModel intentModel = new IntentModel();
        intentModel.setRequestModelList(requestModelList);
        intentModel.setClassArrayListForUpdate(classArrayListForUpdate);
        Intent intent = new Intent(activity, UpdateTablesActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.GET_DATA_INSERT_IN_TABLE);
    }

    public static void resultOkFromUpdateTableAct(BaseActivity activity) {
        Intent returnIntent = new Intent();
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }


//-------------------------------------------------------------------------------------------->>>

    /**
     * @param witchAttachment -->> from CommonsAttachmentType class
     *                        b5IdRefGUID  -->>  guid of the object that need have attachments (deprecated and no need , but ....)(>>> deprecated)
     * @param b5HcName        -->>  from CommonB5HCTypeName class
     */
    public static void goAttachProviderActForResult(BaseActivity activity, String witchAttachment
                                                    ///,String b5IdRefGUID >>> deprecated
            , String b5HcName) {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(witchAttachment);
        ////intentModel.setSomeString2(b5IdRefGUID); >>> deprecated
        intentModel.setSomeString3(b5HcName);
        Intent intent = new Intent(activity, AttachProviderActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.GET_URI_LIST_FROM_ATTACHMENT);
    }


    /**
     * @param attach {[attachmentGUID+suffix(fileName),columnNumber,B5HCTypeId],...} (AttachmentJsonModel.class)
     */
    public static void goAttachAlbumActForResult(BaseActivity activity, String attach, boolean canUserAddAttach, boolean canUserDeleteAttach) {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(attach);
        intentModel.setCanUserAddAttach(canUserAddAttach);
        intentModel.setCanUserRemoveAttach(canUserDeleteAttach);
        Intent intent = new Intent(activity, AttachAlbumActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.GET_URI_LIST_FROM_ALBUM_ATTACHMENT);
    }


    /**
     * taghir dar vorodi in method, bayad dar metode resultOkFromAttachAlbumProviderAct() emal shavad
     */
    public static void resultOkFromAttachOrAlbumAttachProviderAct(BaseActivity activity,
                                                                  //ArrayList<String> uriStringList, deprecated
                                                                  ArrayList<String> fileNameList,
                                                                  ArrayList<String> B5HTypeIdList,
                                                                  ArrayList<String> columnNumberList) {
        IntentModel intentModel = new IntentModel();
        //intentModel.setStringList(uriStringList); deprecated
        intentModel.setStringList2(fileNameList);
        intentModel.setStringList3(B5HTypeIdList);
        intentModel.setStringList4(columnNumberList);
        Intent returnIntent = new Intent();
        returnIntent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

//-------------------------------------------------------------------------------------------->>>

    public static void goBarCodeActForResult(Activity activity) {
        Intent intent = new Intent(activity, BarCodeActivity.class);
        activity.startActivityForResult(intent, CommonRequestCodes.GET_BARCODE);
    }


    public static void resultOkFromBarCodeAct(Activity activity, String result) {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(result);
        Intent returnIntent = new Intent();
        returnIntent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

    //-------------------------------------------------------------------------------------------->>>
    public static void goSearchActForResult(Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivityForResult(intent, CommonRequestCodes.GET_SEARCH);
    }


    public static void resultOkFromSearchAct(Activity activity, String result) {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(result);
        Intent returnIntent = new Intent();
        returnIntent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }
//-------------------------------------------------------------------------------------------->>>

    public static void goMapActGetOneLocationForResult(BaseActivity activity) {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(String.valueOf(CommonRequestCodes.MAP_GET_ONE_LOCATION));
        Intent intent = new Intent(activity, MapsActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.MAP_GET_ONE_LOCATION);
    }

    public static void goMapActGetMoreLocationForResult(BaseActivity activity) {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(String.valueOf(CommonRequestCodes.MAP_GET_MORE_LOCATION));
        Intent intent = new Intent(activity, MapsActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.MAP_GET_MORE_LOCATION);
    }

    public static void goMapActGetDestinationForResult(BaseActivity activity) {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(String.valueOf(CommonRequestCodes.MAP_GET_DESTINATION));
        Intent intent = new Intent(activity, MapsActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.MAP_GET_DESTINATION);
    }

    public static void goMapActGetSurfaceForResult(BaseActivity activity) {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(String.valueOf(CommonRequestCodes.MAP_GET_SURFACE));
        Intent intent = new Intent(activity, MapsActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.MAP_GET_SURFACE);
    }

    public static void goMapActShowGeoJssonForResult(BaseActivity activity, String GeoJson) {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(String.valueOf(CommonRequestCodes.MAP_SHOW_GEOJSON));
        intentModel.setSomeString2(GeoJson);
        Intent intent = new Intent(activity, MapsActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.MAP_SHOW_GEOJSON);
    }


    public static void resultOkFromMapAct(Activity activity, ArrayList<String> latitude, ArrayList<String> longitude) {
        IntentModel intentModel = new IntentModel();
        intentModel.setStringList(latitude);
        intentModel.setStringList2(longitude);
        Intent returnIntent = new Intent();
        returnIntent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

    //-------------------------------------------------------------------------------------------->>>
    static void goTimeActivityForResult(Activity activity, IntentModel intentModel) {
        Intent intent = new Intent(activity, TimeActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.TIME_FORMS);
    }

    public static void resultOkFromTimeAct(Activity activity) {
        Intent returnIntent = new Intent();
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

    //-------------------------------------------------------------------------------------------->>>
    static void goTaskActivityForResult(Activity activity, IntentModel intentModel) {

        Intent intent = new Intent(activity, TaskActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.TASK_FORMS);
    }

    public static void resultOkFromTaskAct(Activity activity) {
        Intent returnIntent = new Intent();
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

    //-------------------------------------------------------------------------------------------->>>
    static void goGoodTrancActivityForResult(Activity activity, IntentModel intentModel) {

        Intent intent = new Intent(activity, GoodTranceActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.GOOD_TRANS);
    }

    public static void resultOkFromGoodTranceAct(Activity activity) {
        Intent returnIntent = new Intent();
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

    //-------------------------------------------------------------------------------------------->>>
    public static void goTimeFilterActivityForResult(Activity activity) {
        Intent intent = new Intent(activity, TimeFilterActivity.class);
        activity.startActivityForResult(intent, CommonRequestCodes.GET_TIME_FILTER);
    }

    public static void resultOkFromTimeFilterAct(Activity activity, Date startDate, Date endDate) {
        Intent returnIntent = new Intent();

        IntentModel intentModel = new IntentModel();
        intentModel.setStartDate(startDate);
        intentModel.setEndDate(endDate);
        returnIntent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

    //-------------------------------------------------------------------------------------------->>>
    static void goGoodForGoodTransActivityForResult(Activity activity, IntentModel intentModel) {
        Intent intent = new Intent(activity, GoodForGoodTrans.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.ONE_GOOD_FOR_GOOD_TRANS);
    }

    public static void resultOkFromGoodForGoodTranceAct(Activity activity, ArrayList<GoodTransDetailsModel> goodTransDetailsModelArrayList) {
        Intent returnIntent = new Intent();
        IntentModel intentModel = new IntentModel();
        intentModel.setGoodTransDetailsModelList(goodTransDetailsModelArrayList);
        returnIntent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }
//-------------------------------------------------------------------------------------------->>>

    static void goWarehouseActivityForResult(Activity activity, IntentModel intentModel) {

        Intent intent = new Intent(activity, WarehouseHandlingActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.WAREHOUSE_HANDLING_FORMS);
    }


    public static void resultOkFromWarehouseAct(Activity activity) {
        Intent returnIntent = new Intent();
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }


    //-------------------------------------------------------------------------------------------->>>
    static void goWeighingActivityForResult(Activity activity, IntentModel intentModel) {
        Intent intent = new Intent(activity, WeighingActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.WEIGHING_FORMS);
    }


    public static void resultOkFromWeigingAct(Activity activity) {
        Intent returnIntent = new Intent();
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

    //-------------------------------------------------------------------------------------------->>>
    //report not have result...but
    static void goReportActForResult(Activity activity, IntentModel intentModel) {
        Intent intent = new Intent(activity, ReportsActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.REPORT_FORMS);
    }

    //no need ...but
    public static void resultOkFromReportAct(Activity activity) {
        Intent returnIntent = new Intent();
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

    //-------------------------------------------------------------------------------------------->>>
    static void goSelectionActForResult(Activity activity, IntentModel intentModel) {
        Intent intent = new Intent(activity, SelectionActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.SELECTION_REPORT);
    }

    public static void resultOkFromSelectionAct(Activity activity, String resultJsonList) {
        Intent returnIntent = new Intent();
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(resultJsonList);
        returnIntent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }
//-------------------------------------------------------------------------------------------->>>

    /**
     * sync process were done and clear tables and get new data
     */
    public static void goSyncActForResult(Activity activity, ArrayList<String> kindsForSync) {
        SocketService.intentToSocketService(activity);
        IntentModel intentModel = new IntentModel();
        intentModel.setStringList(kindsForSync);
        intentModel.setGetNewData(true);
        Intent intent = new Intent(activity, SyncActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.Do_SYNC);
    }

    /**
     * do not get new data, only sync process were done and clear tables
     */
    public static void goSyncActExpiredFinancialDateForResult(Activity activity, ArrayList<String> kindsForSync) {
        IntentModel intentModel = new IntentModel();
        intentModel.setStringList(kindsForSync);
        intentModel.setGetNewData(false);
        Intent intent = new Intent(activity, SyncActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.Do_SYNC_EXPIRED_FINANCIAL_DATE);
    }

    public static void resultOkFromSyncAct(Activity activity) {
        Intent returnIntent = new Intent();
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

//-------------------------------------------------------------------------------------------->>>
    public static void goWebViewActivity(Activity activity,String applicationId,String pageNumber,String formCode) {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeString(applicationId);
        intentModel.setSomeString2(pageNumber);
        intentModel.setSomeString3(formCode);
        Intent intent =new Intent(activity, WebActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivity(intent);
    }

    //-------------------------------------------------------------------------------------------->>>

    /**
     * @param witchIntro : from CommonIntroHelper.kt
     */
    public static void goIntroHelperActivityForResualt(Activity activity, int witchIntro) {
        IntentModel intentModel = new IntentModel();
        intentModel.setSomeInt(witchIntro);
        Intent intent =new Intent(activity, IntroHelp.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent,CommonRequestCodes.INTRODUCE_FORMS);
    }

    public static void resultOkFromIntroduceHelper(Activity activity) {
        Intent returnIntent = new Intent();
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }


//-------------------------------------------------------------------------------------------->>>
    public static void resultOkFromFuel(BaseActivity activity) {
    Intent returnIntent = new Intent();
    activity.setResult(Activity.RESULT_OK, returnIntent);
    activity.finish();
}

    static void goFuelListMasterActivity(Activity activity, IntentModel intentModel) {
        Intent intent = new Intent(activity, FuelActivity.class);
        intent.putExtras(IntentSenderHelper.setIntentData(intentModel));
        activity.startActivityForResult(intent, CommonRequestCodes.FUEL_FORMS);
    }

}
