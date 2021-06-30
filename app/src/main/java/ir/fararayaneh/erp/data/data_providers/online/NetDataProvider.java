package ir.fararayaneh.erp.data.data_providers.online;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonUrls;
import ir.fararayaneh.erp.data.data_providers.IDataProvider;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.online.request.rest.RestRequestModel;
import ir.fararayaneh.erp.data.models.online.response.RestResponseModel;
import ir.fararayaneh.erp.utils.IPHandler;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;


public class NetDataProvider implements IDataProvider {

    public void data(RestRequestModel restRequestModel,INetDataProviderListener iNetDataProviderListener) {

        Log.i("arash", "RestRequestModel ip :: "+IPHandler.creatorForRest(CommonUrls.HTTP_BASE_URL,SharedPreferenceProvider.getServerConfigs(App.getAppContext()),CommonUrls.MIDDLE_BASE_URL)+CommonUrls.LAST_BASE_URL);
        Log.i("arash", "RestRequestModel ip :: "+CommonUrls.REQUEST_JSON_FIELD_NAME);
        Log.i("arash", "RestRequestModel ip :: "+App.getmGson().toJson(restRequestModel));

        AndroidNetworking.upload(IPHandler.creatorForRest(CommonUrls.HTTP_BASE_URL,SharedPreferenceProvider.getServerConfigs(App.getAppContext()),CommonUrls.MIDDLE_BASE_URL)+CommonUrls.LAST_BASE_URL)
                .addMultipartParameter(CommonUrls.REQUEST_JSON_FIELD_NAME,App.getmGson().toJson(restRequestModel))
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener((bytesUploaded, totalBytes) -> {
                    if(iNetDataProviderListener!=null){
                        iNetDataProviderListener.uploadProgressListener(createPercentage(bytesUploaded,totalBytes));
                    }
                })
                .setDownloadProgressListener((bytesDownloaded, totalBytes) -> {
                    if(iNetDataProviderListener!=null){
                        iNetDataProviderListener.downloadProgressListen(createPercentage(bytesDownloaded,totalBytes));
                    }
                })
                .getAsObject(RestResponseModel.class, new ParsedRequestListener<RestResponseModel>() {
                    @Override
                    public void onResponse(RestResponseModel response) {
                        if(iNetDataProviderListener!=null){
                            iNetDataProviderListener.responce(response);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if(iNetDataProviderListener!=null){
                            iNetDataProviderListener.error(anError.getMessage());
                        }
                        ThrowableLoggerHelper.logMyThrowable("NetDataProvider***data/"+anError.getMessage());
                        ThrowableLoggerHelper.logMyThrowable("NetDataProvider***data/"+anError.getErrorBody());
                        ThrowableLoggerHelper.logMyThrowable("NetDataProvider***data/"+anError.getErrorCode());
                    }});
    }

    private int createPercentage(long detail, long all){
        return (int) (detail*100/all);
    }

    @Override
    public Object data(IModels iModels) {
        return null;
    }
}
