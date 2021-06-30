package ir.fararayaneh.erp.data.models.online.request.socket;


import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TaskTable;

public class TaskSocketModel extends RootModel implements ISocketModel {



    private TaskTable body;
    public TaskSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber,String receivers,TaskTable body) {
        super(kind,  organization, userId, appName,  hasFile, lang, pageNo, pageItem, chatRoomId,  nodeReceivedDate, errorNumber,receivers);
        this.body=body;
    }

    public TaskTable getBody() {
        return body;
    }

    public void setBody(TaskTable body) {
        this.body = body;
    }
}
