package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.MessageTable;

public class MessageSocketModel extends RootModel implements ISocketModel {

    private MessageTable body;
    public MessageSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber,String receivers,MessageTable body) {
        super(kind,organization, userId, appName,  hasFile, lang, pageNo, pageItem, chatRoomId,  nodeReceivedDate, errorNumber,receivers);
        this.body=body;
    }

    public MessageTable getBody() {
        return body;
    }

    public void setBody(MessageTable body) {
        this.body = body;
    }
}

