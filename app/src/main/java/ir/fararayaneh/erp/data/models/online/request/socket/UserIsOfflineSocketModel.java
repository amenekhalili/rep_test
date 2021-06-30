package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.middle.UserIsOfflineModel;
import ir.fararayaneh.erp.data.models.online.request.RootModel;

public class UserIsOfflineSocketModel extends RootModel implements ISocketModel {

    private UserIsOfflineModel body; //we set null in send socket, because here no need to body data

    public UserIsOfflineSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, UserIsOfflineModel body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public UserIsOfflineModel getBody() {
        return body;
    }

    public void setBody(UserIsOfflineModel body) {
        this.body = body;
    }
}
