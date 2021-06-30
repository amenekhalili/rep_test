package ir.fararayaneh.erp.activities.weighing;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import android.annotation.SuppressLint;

public class WeighingActivity extends BaseActivity<WeighingPresenter, WeighingView> {

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        view = new WeighingView(this);
    }

    @Override
    protected void initPresenter() {
        presenter = new WeighingPresenter(weekView, this, true);
    }
}
