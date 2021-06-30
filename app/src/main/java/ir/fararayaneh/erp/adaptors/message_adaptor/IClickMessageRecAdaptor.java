package ir.fararayaneh.erp.adaptors.message_adaptor;

import ir.fararayaneh.erp.IBase.common_base.IListeners;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.MessageTable;

public interface IClickMessageRecAdaptor  extends IListeners {

    void clickAttachRow(MessageTable messageTable);

    void clickAvatarRow(ChatroomMemberTable memberTable);

    void receivedNewMessage(MessageTable messageTable);

    void workForchatroomDelete();

    void workForScroolToPosition(int position);

    void workForEditChatRoomNameAndImage(String name ,String fileName);

    void workForChangesIsOnlineIsTypingToolbar(boolean isOnline, boolean isTyping);
}

