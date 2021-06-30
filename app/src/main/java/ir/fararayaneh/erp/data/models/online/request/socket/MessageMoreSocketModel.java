package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.middle.LoadMoreMessageModel;
import ir.fararayaneh.erp.data.models.online.request.RootModel;

public class MessageMoreSocketModel extends RootModel implements ISocketModel {

    private LoadMoreMessageModel body;

    public MessageMoreSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers,LoadMoreMessageModel body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public LoadMoreMessageModel getBody() {
        return body;
    }

    public void setBody(LoadMoreMessageModel body) {
        this.body = body;
    }
}
