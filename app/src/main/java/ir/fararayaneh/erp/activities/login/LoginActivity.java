package ir.fararayaneh.erp.activities.login;



import ir.fararayaneh.erp.IBase.common_base.BaseActivity;




public class LoginActivity extends BaseActivity<LoginPresenter, LoginView> {


    @Override
    protected void initView() {
        view=new LoginView(this);
    }

    @Override
    protected void initPresenter() {
        presenter=new LoginPresenter(weekView,this,false);
    }
}



