package ir.fararayaneh.erp.activities.report;

import android.content.Intent;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface IReportClickListener extends IListeners {
    void filterText(Intent data);
    void filterTime(Intent data);
}
