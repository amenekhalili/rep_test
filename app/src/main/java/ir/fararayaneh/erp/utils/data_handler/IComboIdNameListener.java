package ir.fararayaneh.erp.utils.data_handler;

import java.util.ArrayList;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface IComboIdNameListener extends IListeners {
    void onData(ArrayList<String> listName);
    void onError();
}
