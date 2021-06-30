package ir.fararayaneh.erp.activities.select_activity;


import ir.fararayaneh.erp.IBase.common_base.BaseActivity;

public class SelectionActivity extends BaseActivity<SelectionPresenter, SelectionView> {

    @Override
    protected void initView() {
        view = new SelectionView(this);
    }

    @Override
    protected void initPresenter() {
        presenter = new SelectionPresenter(weekView, this, true);
    }

}

