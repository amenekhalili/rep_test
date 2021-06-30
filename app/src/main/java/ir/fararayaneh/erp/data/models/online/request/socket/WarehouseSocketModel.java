package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingTable;

public class WarehouseSocketModel extends RootModel implements ISocketModel {

    private WarehouseHandlingTable body;
    public WarehouseSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, WarehouseHandlingTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public WarehouseHandlingTable getBody() {
        return body;
    }

    public void setBody(WarehouseHandlingTable body) {
        this.body = body;
    }
}
