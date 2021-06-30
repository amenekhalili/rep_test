package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.middle.MessageEditModel;
import ir.fararayaneh.erp.data.models.online.request.RootModel;

public class MessageEditSocketModel extends RootModel implements ISocketModel {


    private MessageEditModel body;

    public MessageEditSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers,  MessageEditModel body) {
        super(kind,organization, userId, appName,  hasFile, lang, pageNo, pageItem, chatRoomId,  nodeReceivedDate, errorNumber,receivers);
        this.body=body;
    }

    public MessageEditModel getBody() {
        return body;
    }

    public void setBody(MessageEditModel body) {
        this.body = body;
    }
}
