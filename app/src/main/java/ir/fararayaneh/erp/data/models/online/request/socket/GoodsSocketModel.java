package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.AgroFieldTable;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;

public class GoodsSocketModel extends RootModel implements ISocketModel {

    private GoodsTable body;

    public GoodsSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, GoodsTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public GoodsTable getBody() {
        return body;
    }

    public void setBody(GoodsTable body) {
        this.body = body;
    }
}

