package ir.fararayaneh.erp.activities.splash;





import ir.fararayaneh.erp.IBase.common_base.BaseActivity;

public class SplashActivity extends BaseActivity<SplashPresenter, SplashView> {



    @Override
    protected void initView() {
        view=new SplashView(this);
    }

    @Override
    protected void initPresenter() {
        presenter=new SplashPresenter(weekView,this,false);
    }

}
