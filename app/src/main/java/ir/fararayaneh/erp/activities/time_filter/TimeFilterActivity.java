package ir.fararayaneh.erp.activities.time_filter;


import android.annotation.SuppressLint;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.activities.main.MainPresenter;
import ir.fararayaneh.erp.activities.main.MainView;

public class TimeFilterActivity extends BaseActivity<TimeFilterPresenter, TimeFilterView> {


    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        view=new TimeFilterView(this);
    }

    @Override
    protected void initPresenter() {
        presenter=new TimeFilterPresenter(weekView,this,false);
    }
}
