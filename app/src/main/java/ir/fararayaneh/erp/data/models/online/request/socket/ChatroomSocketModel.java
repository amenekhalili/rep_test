package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;

public class ChatroomSocketModel extends RootModel implements ISocketModel {

    private ChatroomTable body;

    public ChatroomSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, ChatroomTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public ChatroomTable getBody() {
        return body;
    }

    public void setBody(ChatroomTable body) {
        this.body = body;
    }
}
