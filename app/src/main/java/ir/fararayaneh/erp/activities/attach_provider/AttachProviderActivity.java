package ir.fararayaneh.erp.activities.attach_provider;


import ir.fararayaneh.erp.IBase.common_base.BaseActivity;

public class AttachProviderActivity extends BaseActivity<AttachProviderPresenter, AttachProviderView> {

    @Override
    protected void initView() {
        view=new AttachProviderView(this);
    }

    @Override
    protected void initPresenter() {
        presenter=new AttachProviderPresenter(weekView,this,true);
    }
}
