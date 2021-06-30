package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.middle.UserIsOnlineModel;
import ir.fararayaneh.erp.data.models.online.request.RootModel;

public class UserIsOnlineSocketModel extends RootModel implements ISocketModel {

    private UserIsOnlineModel body; //we set null in send socket, because here no need to body data

    public UserIsOnlineSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, UserIsOnlineModel body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public UserIsOnlineModel getBody() {
        return body;
    }

    public void setBody(UserIsOnlineModel body) {
        this.body = body;
    }
}

