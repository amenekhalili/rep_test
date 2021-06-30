package ir.fararayaneh.erp.utils.data_handler;

import ir.fararayaneh.erp.IBase.common_base.IListeners;
import ir.fararayaneh.erp.data.models.GlobalModel;

public interface IHaveUserListener extends IListeners {
    void haveUser(boolean user, GlobalModel globalModel);
    void error();
}
