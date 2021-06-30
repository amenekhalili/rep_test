package ir.fararayaneh.erp.utils.data_handler;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonKind;
import ir.fararayaneh.erp.commons.CommonNetRequest;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.online.request.rest.Body;
import ir.fararayaneh.erp.data.models.online.request.rest.RestRequestModel;


public class RestReqDataHandler {

    public static RestRequestModel loginRequest(String userName, String passWord, String organization) {
        return new RestRequestModel(CommonKind.LOGIN,  organization, Commons.NULL_INTEGER, CommonNetRequest.APP_NAME,  CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, CommonNetRequest.START_PAGE_NUMBER, CommonNetRequest.DEFAULT_PAGE_ITEM, Commons.NULL_CAP, Commons.MINIMUM_TIME,  Commons.NULL_INTEGER,Commons.NULL_CAP, new Body(userName, passWord, SharedPreferenceProvider.getDeviceId(App.getAppContext())));
    }

    public static RestRequestModel allKindRequest(String kind, String userId, String organization) {
        return new RestRequestModel(kind, organization, userId, CommonNetRequest.APP_NAME,  CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, CommonNetRequest.START_PAGE_NUMBER, CommonNetRequest.DEFAULT_PAGE_ITEM, Commons.NULL_CAP, Commons.MINIMUM_TIME, Commons.NULL_INTEGER,Commons.NULL_CAP, new Body(Commons.NULL_CAP, Commons.NULL_CAP, Commons.NULL_CAP));
    }
}
