package ir.fararayaneh.erp.activities.select_activity;

import android.content.Intent;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface ISelectionActivityClickListener extends IListeners {

    void searchText(Intent data);

    void clickSelectAll();

    void clickDeSelectAll();

    void clickBack();

    void filterTime(Intent data);
}
