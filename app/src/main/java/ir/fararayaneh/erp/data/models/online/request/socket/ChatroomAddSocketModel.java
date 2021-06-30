package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.middle.ChatroomAddModel;
import ir.fararayaneh.erp.data.models.online.request.RootModel;


/**
 * use for SYNCCHATROOMADD kind
 */
public class ChatroomAddSocketModel extends RootModel implements ISocketModel {

    private ChatroomAddModel body;

    public ChatroomAddSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, ChatroomAddModel body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public ChatroomAddModel getBody() {
        return body;
    }

    public void setBody(ChatroomAddModel body) {
        this.body = body;
    }
}
