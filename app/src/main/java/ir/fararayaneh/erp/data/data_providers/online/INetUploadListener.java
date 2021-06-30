package ir.fararayaneh.erp.data.data_providers.online;

import ir.fararayaneh.erp.IBase.common_base.IListeners;
import ir.fararayaneh.erp.data.models.online.response.UploadResponseModel;

public interface INetUploadListener extends IListeners {
    void responce(UploadResponseModel response);
    void error(String error);
}
