package ir.fararayaneh.erp.activities.good_trance;

import android.content.Intent;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface IGoodTranceListener extends IListeners {
    void dataFromSelectionAct(Intent data);
    void dataFromGoodForGoodTransAct(Intent data);
}
