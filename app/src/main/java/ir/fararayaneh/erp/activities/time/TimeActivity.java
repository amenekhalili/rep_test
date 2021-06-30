package ir.fararayaneh.erp.activities.time;


import ir.fararayaneh.erp.IBase.common_base.BaseActivity;

public class TimeActivity extends BaseActivity<TimePresenter, TimeView> {


    @Override
    protected void initView() {
        view=new TimeView(this);
    }

    @Override
    protected void initPresenter() {
        presenter=new TimePresenter(weekView,this,true);
    }
}
