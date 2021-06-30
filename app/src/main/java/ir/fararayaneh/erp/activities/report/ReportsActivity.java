package ir.fararayaneh.erp.activities.report;

import androidx.appcompat.app.AppCompatActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.activities.login.LoginPresenter;
import ir.fararayaneh.erp.activities.login.LoginView;

import android.os.Bundle;

public class ReportsActivity extends BaseActivity<ReportsPresenter, ReportsView> {

    @Override
    protected void initView() {
        view = new ReportsView(this);
    }

    @Override
    protected void initPresenter() {
        presenter = new ReportsPresenter(weekView, this, true);
    }

}
