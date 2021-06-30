package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListMasterTable;

public class FuelListMasterSocketModel extends RootModel implements ISocketModel {

    private FuelListMasterTable body;

    public FuelListMasterSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, FuelListMasterTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public FuelListMasterTable getBody() {
        return body;
    }

    public void setBody(FuelListMasterTable body) {
        this.body = body;
    }
}
