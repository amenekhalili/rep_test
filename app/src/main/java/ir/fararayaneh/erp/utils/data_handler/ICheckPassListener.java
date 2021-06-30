package ir.fararayaneh.erp.utils.data_handler;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface ICheckPassListener extends IListeners {

    void checkPassword(boolean correctPass,String userId);
    void error();
}
