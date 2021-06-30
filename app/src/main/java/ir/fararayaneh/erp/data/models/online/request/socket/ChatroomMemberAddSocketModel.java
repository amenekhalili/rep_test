package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.middle.ChatroomMemberAddModel;
import ir.fararayaneh.erp.data.models.online.request.RootModel;


public class ChatroomMemberAddSocketModel extends RootModel implements ISocketModel {

    private ChatroomMemberAddModel body;

    public ChatroomMemberAddSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers,ChatroomMemberAddModel body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public ChatroomMemberAddModel getBody() {
        return body;
    }

    public void setBody(ChatroomMemberAddModel body) {
        this.body = body;
    }
}
