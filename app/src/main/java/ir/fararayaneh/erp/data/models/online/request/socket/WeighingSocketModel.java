package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WeighingTable;

public class WeighingSocketModel extends RootModel implements ISocketModel {

    private WeighingTable body;
    public WeighingSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers,WeighingTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public WeighingTable getBody() {
        return body;
    }

    public void setBody(WeighingTable body) {
        this.body = body;
    }
}
