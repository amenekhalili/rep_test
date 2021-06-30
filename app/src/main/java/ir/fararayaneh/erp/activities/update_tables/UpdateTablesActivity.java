package ir.fararayaneh.erp.activities.update_tables;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;

/**
 * for intent we need :
 * List<RestRequestModel> requestModelList;
 * ArrayList<Class> classArrayList;
 */
public class UpdateTablesActivity extends BaseActivity<UpdateTablesPresenter, UpdateTablesView> {

    @Override
    protected void initView() {
        view=new UpdateTablesView(this);
    }

    @Override
    protected void initPresenter() {
        presenter=new UpdateTablesPresenter(weekView,this,true);
    }
}
