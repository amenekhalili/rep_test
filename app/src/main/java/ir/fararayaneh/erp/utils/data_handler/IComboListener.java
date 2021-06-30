package ir.fararayaneh.erp.utils.data_handler;

import java.util.ArrayList;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

/**
 * for get data of comboboxs
 */
public interface IComboListener extends IListeners {
    void onData(ArrayList<ArrayList<String>> listNameList,ArrayList<ArrayList<String>> listIdList);
    void onError();
}
