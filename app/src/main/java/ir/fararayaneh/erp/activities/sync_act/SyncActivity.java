package ir.fararayaneh.erp.activities.sync_act;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import android.annotation.SuppressLint;

public class SyncActivity extends BaseActivity<SyncPresenter, SyncView> {

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        view=new SyncView(this);
    }

    @Override
    protected void initPresenter() {
        presenter=new SyncPresenter(weekView,this,true);
    }

}
