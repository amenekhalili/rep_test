package ir.fararayaneh.erp.utils.data_handler;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface IFormIdListener extends IListeners {
    void onGetData(String formId);
    void onError();
}
