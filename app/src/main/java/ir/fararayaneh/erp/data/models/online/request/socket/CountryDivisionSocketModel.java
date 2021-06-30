package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CountryDivisionTable;

public class CountryDivisionSocketModel extends RootModel implements ISocketModel {

    private CountryDivisionTable body;

    public CountryDivisionSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, CountryDivisionTable body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body = body;
    }

    public CountryDivisionTable getBody() {
        return body;
    }

    public void setBody(CountryDivisionTable body) {
        this.body = body;
    }
}
