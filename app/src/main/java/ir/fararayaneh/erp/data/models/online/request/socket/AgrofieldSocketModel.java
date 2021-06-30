package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.AgroFieldTable;

public class AgrofieldSocketModel extends RootModel implements ISocketModel {

    private AgroFieldTable body;

    public AgrofieldSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, AgroFieldTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public AgroFieldTable getBody() {
        return body;
    }

    public void setBody(AgroFieldTable body) {
        this.body = body;
    }
}
