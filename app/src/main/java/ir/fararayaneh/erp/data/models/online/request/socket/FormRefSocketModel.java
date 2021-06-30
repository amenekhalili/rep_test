package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.FormRefTable;

public class FormRefSocketModel extends RootModel implements ISocketModel {

    private FormRefTable body;

    public FormRefSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, FormRefTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public FormRefTable getBody() {
        return body;
    }

    public void setBody(FormRefTable body) {
        this.body = body;
    }
}
