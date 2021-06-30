package ir.fararayaneh.erp.activities.good_trance;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;

public class GoodTranceActivity extends BaseActivity<GoodTrancePresenter, GoodTranceView> {


    @Override
    protected void initView() {
        view = new GoodTranceView(this);
    }

    @Override
    protected void initPresenter() {
        presenter = new GoodTrancePresenter(weekView, this, true);
    }
}

