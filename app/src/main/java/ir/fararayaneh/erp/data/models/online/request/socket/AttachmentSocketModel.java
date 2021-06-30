package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.attachment.AttachmentTable;

public class AttachmentSocketModel extends RootModel implements ISocketModel {

    private AttachmentTable body;

    public AttachmentSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, AttachmentTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public AttachmentTable getBody() {
        return body;
    }

    public void setBody(AttachmentTable body) {
        this.body = body;
    }
}
