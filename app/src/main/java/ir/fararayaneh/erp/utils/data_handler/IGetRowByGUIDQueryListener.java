package ir.fararayaneh.erp.utils.data_handler;

import ir.fararayaneh.erp.IBase.common_base.IListeners;
import ir.fararayaneh.erp.data.models.IModels;

public interface IGetRowByGUIDQueryListener extends IListeners {
    void onGetRow(IModels iModels);
    void onError();
}
