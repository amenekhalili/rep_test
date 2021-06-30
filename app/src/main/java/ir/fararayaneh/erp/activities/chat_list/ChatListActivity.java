package ir.fararayaneh.erp.activities.chat_list;


import ir.fararayaneh.erp.IBase.common_base.BaseActivity;

public class ChatListActivity extends BaseActivity<ChatListPresenter, ChatListView> {

    @Override
    protected void initView() {
        view=new ChatListView(this);
    }

    @Override
    protected void initPresenter() {
        presenter = new ChatListPresenter(weekView, this, false);
    }
}