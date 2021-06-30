package ir.fararayaneh.erp.utils.socket_helper;




import org.json.JSONException;
import org.json.JSONObject;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsSocket;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;


public class SocketJsonParser {


    public static String getKind(String json)  {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.get(CommonsSocket.KIND).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            ThrowableLoggerHelper.logMyThrowable("SocketJsonParser/getKind/" + e.getMessage());
            return Commons.SPACE;
        }
    }

    /**
     * caution : body in json should be like  :
     * <p>
     * {"sdfsd":"ssdfsd","body":{"syncState":"sfsd",...}}
     * {"sdfsd":"ssdfsd","body":{"fdssd":[],syncState":"sfsd"}}
     * <p>
     *
     *     به هیچ وجه درون بادی نباید آرایه بدون اسم وجود داشته باشد
     *
     * فقط جیسون های جی پی اس دارای بادی آرایه هستند
     * و سینک استیت ندارند که رفتار آنها برای هدف
     * evacuate
     * اهمیت ندارد
     * چون نیاز به سینک ندارد
     */

    private static final String SYNC_STATE = "syncState";
    private static final String GUID = "guid";
    private static final String BODY = "body";

    public static boolean checkJsonForEvacuateHandler(String json) {
        try {
            JSONObject jsonObjectBody = new JSONObject(new JSONObject(json).get(BODY).toString());
            if (jsonObjectBody.has(SYNC_STATE) && jsonObjectBody.has(GUID)) {
                String syncState = jsonObjectBody.getString(SYNC_STATE);
                return syncState.equals(CommonSyncState.ACCESS_DENIED) || syncState.equals(CommonSyncState.SYNCED);
            } else {
                return false;
            }
        } catch (JSONException e) {
            ThrowableLoggerHelper.logMyThrowable("SocketJsonParser/checkJsonForEvacuateHandler/" + e.getMessage());
            return false;
        }
    }


    public static String getGUIDForEvacuateHandler(String json){
        try {
            JSONObject jsonObjectBody = new JSONObject(new JSONObject(json).get(BODY).toString());
            if ( jsonObjectBody.has(GUID)) {
                return jsonObjectBody.getString(GUID);
            } else {
                return Commons.SPACE;
            }
        } catch (JSONException e) {
            ThrowableLoggerHelper.logMyThrowable("SocketJsonParser/getGUIDForEvacuateHandler/" + e.getMessage());
            return Commons.SPACE;
        }
    }


}
