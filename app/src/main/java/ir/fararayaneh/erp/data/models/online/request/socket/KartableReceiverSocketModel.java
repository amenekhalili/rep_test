package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.KartableRecieverTable;

public class KartableReceiverSocketModel extends RootModel implements ISocketModel {

    private KartableRecieverTable body;

    public KartableReceiverSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, KartableRecieverTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public KartableRecieverTable getBody() {
        return body;
    }

    public void setBody(KartableRecieverTable body) {
        this.body = body;
    }
}

