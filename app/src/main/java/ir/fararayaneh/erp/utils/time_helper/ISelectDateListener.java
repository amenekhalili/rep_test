package ir.fararayaneh.erp.utils.time_helper;

import java.util.Date;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface ISelectDateListener extends IListeners {
    void onDateSelection(Date date);
    void onDateNotSelection();
}
