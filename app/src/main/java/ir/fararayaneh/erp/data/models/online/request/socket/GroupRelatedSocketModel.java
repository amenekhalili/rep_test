package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.GroupRelatedTable;

public class GroupRelatedSocketModel extends RootModel implements ISocketModel {

    private GroupRelatedTable body;

    public GroupRelatedSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, GroupRelatedTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public GroupRelatedTable getBody() {
        return body;
    }

    public void setBody(GroupRelatedTable body) {
        this.body = body;
    }
}
