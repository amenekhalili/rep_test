package ir.fararayaneh.erp.adaptors.chat_list_adaptor;


import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface IClickRowChatListRecHolderListener extends IListeners {

    void clickAttachRow(int position);

    void clickGoChatRoomRow(int position);

}
