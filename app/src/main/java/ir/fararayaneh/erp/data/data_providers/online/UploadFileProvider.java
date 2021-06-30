package ir.fararayaneh.erp.data.data_providers.online;


import android.net.Uri;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonUrls;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.data_providers.IDataProvider;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.online.request.rest.upload.FileUploadJsonModel;
import ir.fararayaneh.erp.data.models.online.response.UploadResponseModel;
import ir.fararayaneh.erp.utils.IPHandler;
import ir.fararayaneh.erp.utils.file_helper.FileToUriHelper;
import okhttp3.Response;


public class UploadFileProvider implements IDataProvider {



    public void data(FileUploadJsonModel fileUploadJsonModel, INetUploadListener iNetUploadListener, Uri uri) {
        AndroidNetworking
                .upload(IPHandler.creatorForRest(CommonUrls.HTTP_BASE_URL, SharedPreferenceProvider.getServerConfigs(App.getAppContext()),CommonUrls.MIDDLE_BASE_URL)+CommonUrls.LAST_BASE_URL)
                .addMultipartFile(CommonUrls.UPLOAD_FILE_KEY_NAME, FileToUriHelper.getFile(App.getAppContext(), CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME,CommonsDownloadFile.TEMP_FILE_NAME,uri))
                .addMultipartParameter(CommonUrls.REQUEST_JSON_FIELD_NAME,App.getmGson().toJson(fileUploadJsonModel))
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener((bytesUploaded, totalBytes) -> {
                    //do nothing
                })
                .setDownloadProgressListener((bytesDownloaded, totalBytes) -> {
                    //do nothing
                })
                .getAsObject(UploadResponseModel.class, new ParsedRequestListener<UploadResponseModel>() {
                            @Override
                            public void onResponse(UploadResponseModel response) {
                                iNetUploadListener.responce(response);
                            }

                            @Override
                            public void onError(ANError anError) {
                                iNetUploadListener.error(anError.getMessage());
                            }});
    }


 /*
 AndroidNetworking.upload("http://138.201.40.213:3001/upload")
                        .addMultipartFile("uploadToNodeServer",FileToUriHelper.getFile(context,CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME,CommonsDownloadFile.TEMP_FILE_NAME,StringUriConvertor.stringToUri(listURIForResult.get(0))))
                        //.addMultipartParameter(CommonUrls.REQUEST_JSON_FIELD_NAME,App.getmGson().toJson(restRequestModel))
                        .setPriority(Priority.HIGH)
                        .build()
                        .setUploadProgressListener(new UploadProgressListener() {
                            @Override
                            public void onProgress(long bytesUploaded, long totalBytes) {
                                Log.i("arash", "accept:bytesUploaded " + bytesUploaded);
                            }
                        })
                        .setDownloadProgressListener(new DownloadProgressListener() {
                            @Override
                            public void onProgress(long bytesDownloaded, long totalBytes) {
                                Log.i("arash", "accept:bytesUploaded " + bytesDownloaded);

                            }
                        })
                        .getAsObject(RestResponseModel.class, new ParsedRequestListener<RestResponseModel>() {
                            @Override
                            public void onResponse(RestResponseModel response) {
                                Log.i("arash", "onResponse: "+response.getItems().get(0).getKind());
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.i("arash", "onError: "+anError.getMessage());
                            }});
*/





    @Override
    public Object data(IModels iModels) {
        return null;
    }
}
