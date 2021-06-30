package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;

public class PurchasableGoodsSocketModel extends RootModel implements ISocketModel {

    private PurchasableGoodsTable body;

    public PurchasableGoodsSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, PurchasableGoodsTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body = body;
    }

    public PurchasableGoodsTable getBody() {
        return body;
    }

    public void setBody(PurchasableGoodsTable body) {
        this.body = body;
    }
}
