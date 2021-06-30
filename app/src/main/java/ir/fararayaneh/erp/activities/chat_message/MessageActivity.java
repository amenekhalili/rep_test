package ir.fararayaneh.erp.activities.chat_message;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;

public class MessageActivity extends BaseActivity<MessagePresenter, MessageView> {

    @Override
    protected void initView() {
        view = new MessageView(this);
    }

    @Override
    protected void initPresenter() {
        presenter = new MessagePresenter(weekView, this, true);
    }
}