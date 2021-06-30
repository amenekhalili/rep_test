package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.BaseCodingTable;

public class BaseCodingSocketModel extends RootModel implements ISocketModel {

    private BaseCodingTable body;

    public BaseCodingSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, BaseCodingTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public BaseCodingTable getBody() {
        return body;
    }

    public void setBody(BaseCodingTable body) {
        this.body = body;
    }
}

