package ir.fararayaneh.erp.utils.show_information_message.message_strategy;


import ir.fararayaneh.erp.IBase.common_base.BasePresenter;
import ir.fararayaneh.erp.IBase.common_base.BaseView;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.MessageListener;

public class ChooseMsgStrategy <V extends BaseView> {
    public void MsgStrategy(BasePresenter<V> basePresenter, IMessage<V> iMessage, UserMessageModel userMessageModel, MessageListener messageListener) {
        iMessage.showMessageToUser(basePresenter,userMessageModel, messageListener);
    }
}
