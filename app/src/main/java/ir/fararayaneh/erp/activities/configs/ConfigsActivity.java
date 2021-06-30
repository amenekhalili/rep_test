package ir.fararayaneh.erp.activities.configs;


import ir.fararayaneh.erp.IBase.common_base.BaseActivity;

/**
 * in this activity, server configs is necessary,but other configs have default values
 */
public class ConfigsActivity extends BaseActivity<ConfigsPresenter,ConfigsView> {

    @Override
    protected void initView() {
        view=new ConfigsView(this);
    }

    @Override
    protected void initPresenter() {
        presenter=new ConfigsPresenter(weekView,this,true);
    }
}
