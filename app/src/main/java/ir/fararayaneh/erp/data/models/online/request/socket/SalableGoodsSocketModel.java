package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.SalableGoodsTable;

public class SalableGoodsSocketModel extends RootModel implements ISocketModel {

    private SalableGoodsTable body;

    public SalableGoodsSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, SalableGoodsTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body = body;
    }

    public SalableGoodsTable getBody() {
        return body;
    }

    public void setBody(SalableGoodsTable body) {
        this.body = body;
    }
}
