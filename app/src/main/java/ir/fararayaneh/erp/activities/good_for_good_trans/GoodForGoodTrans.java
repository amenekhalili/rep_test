package ir.fararayaneh.erp.activities.good_for_good_trans;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;

public class GoodForGoodTrans extends BaseActivity<GoodForGoodTransPresenter, GoodForGoodTransView> {

    @Override
    protected void initView() {
        view = new GoodForGoodTransView(this);
    }

    @Override
    protected void initPresenter() {
        presenter = new GoodForGoodTransPresenter(weekView, this, true);
    }
}
