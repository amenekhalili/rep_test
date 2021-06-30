package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AccidentTable;

public class AccidentSocketModel extends RootModel implements ISocketModel {

    private AccidentTable body;
    public AccidentSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers,AccidentTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public AccidentTable getBody() {
        return body;
    }

    public void setBody(AccidentTable body) {
        this.body = body;
    }
}
