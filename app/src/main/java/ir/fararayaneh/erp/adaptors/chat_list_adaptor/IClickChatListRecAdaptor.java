package ir.fararayaneh.erp.adaptors.chat_list_adaptor;


import ir.fararayaneh.erp.IBase.common_base.IListeners;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;


public interface IClickChatListRecAdaptor  extends IListeners {

    void clickAttachRow(ChatroomTable chatroomTable);

    void clickGoChatRoomRow(ChatroomTable chatroomTable,String chatTypeCode);
}
