package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingListOutTable;

public class WarehouseOutSocketModel extends RootModel implements ISocketModel {

    private WarehouseHandlingListOutTable body;

    public WarehouseOutSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, WarehouseHandlingListOutTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public WarehouseHandlingListOutTable getBody() {
        return body;
    }

    public void setBody(WarehouseHandlingListOutTable body) {
        this.body = body;
    }
}
