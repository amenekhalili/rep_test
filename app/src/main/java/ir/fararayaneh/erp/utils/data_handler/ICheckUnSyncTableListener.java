package ir.fararayaneh.erp.utils.data_handler;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface ICheckUnSyncTableListener extends IListeners {
    void haveUnsyncData();
    void haveNotUnsyncData();
    void uploadProcess();
    void error();
}
