package ir.fararayaneh.erp.activities.task;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;

public class TaskActivity extends BaseActivity<TaskPresenter, TaskView> {


    @Override
    protected void initView() {
        view=new TaskView(this);
    }

    @Override
    protected void initPresenter() {
        presenter=new TaskPresenter(weekView,this,true);
    }
}

