package ir.fararayaneh.erp.data.data_providers.online;

import ir.fararayaneh.erp.IBase.common_base.IListeners;
import ir.fararayaneh.erp.data.models.online.response.RestResponseModel;

public interface INetDataProviderListener extends IListeners {
    void uploadProgressListener (int upLoad);
    void downloadProgressListen (int downLoad);
    void responce(RestResponseModel response);
    void error(String error);
}
