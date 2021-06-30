package ir.fararayaneh.erp.activities.warehouse_handling;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import android.annotation.SuppressLint;

public class WarehouseHandlingActivity extends BaseActivity<WarehouseHandlingPresenter, WarehouseHandlingView> {

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        view=new WarehouseHandlingView(this);
    }

    @Override
    protected void initPresenter() {
        presenter=new WarehouseHandlingPresenter(weekView,this,true);
    }
}
