package ir.fararayaneh.erp.activities.fuel_process;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.activities.configs.ConfigsPresenter;
import ir.fararayaneh.erp.activities.configs.ConfigsView;

public class FuelActivity extends BaseActivity<FuelPresenter, FuelView> {

    @Override
    protected void initView() {
        view = new FuelView(this);
    }

    @Override
    protected void initPresenter() {
        presenter = new FuelPresenter(weekView, this, true);
    }
}
