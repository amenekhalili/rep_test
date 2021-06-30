package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AddressBookTable;

public class AddressBookSocketModel extends RootModel implements ISocketModel {

    private AddressBookTable body;

    public AddressBookSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, AddressBookTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body = body;
    }

    public AddressBookTable getBody() {
        return body;
    }

    public void setBody(AddressBookTable body) {
        this.body = body;
    }
}
