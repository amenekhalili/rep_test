package ir.fararayaneh.erp.commons;


import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;

public class CommonUrls {
    public static final String PING_URL = "8.8.8.8";
    public static final String FARA_WEB_SITE_URL = "http://www.fararayanehco.com/";
    public static final String ERP_ONLINE_URL = "http://misdata.ir/";
    public static final String NODE_SERVER_PORT = "3002";
    public static final String NODE_SERVER_REST_PORT = "3001";
    public static final String KIND_AC = "{kind}";
    public static final String KIND = "kind";
    public static final String PAGE = "page";
    public static final String getReqModel = "getReqModel";
    public static final String MIDDLE_BASE_URL = "/ords/fara/Erp/";
    public static final String HTTP_BASE_URL = "http://";
    public static final String REQUEST_JSON_FIELD_NAME = "jsoninputparameters";
    public static final String UPLOAD_FILE_KEY_NAME = "file";
    public static final String LAST_BASE_URL = "MainService";
    public static final String BASE_ATTACH_URL = "http://apex.fararayanehco.com/ords/fara/image/showimage/";
    public static final String APK_DOWNLOAD_URL_END_POINT = ":" + SharedPreferenceProvider.getNodeRestServerPort(App.getAppContext()) + "/downloadAPK";
    public static final String APK_VERSION_URL_END_POINT = ":" + SharedPreferenceProvider.getNodeRestServerPort(App.getAppContext()) + "/apkVersion";
}
