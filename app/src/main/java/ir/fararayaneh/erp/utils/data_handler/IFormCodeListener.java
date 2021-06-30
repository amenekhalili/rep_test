package ir.fararayaneh.erp.utils.data_handler;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface IFormCodeListener extends IListeners {
    void onGetData(String formCode);
    void onError();
}

