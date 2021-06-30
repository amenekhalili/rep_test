package ir.fararayaneh.erp.activities.sync_act;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface ISyncActivity extends IListeners {
    void cancelResultFromOtherActivity();
    void resultOkFromUpdateActivity();
}
