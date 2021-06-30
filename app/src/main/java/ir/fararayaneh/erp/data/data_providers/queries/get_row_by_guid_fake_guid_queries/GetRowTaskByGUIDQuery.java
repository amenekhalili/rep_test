package ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries;

import io.realm.RealmResults;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TaskTable;

public class GetRowTaskByGUIDQuery extends BaseRowByGuidQuery {

    private TaskTable taskTable = new TaskTable();
    @Override
    protected IModels getProperModel() {
        return taskTable;
    }

    @Override
    protected void getRowByGuid(RealmResults realmResults) {
        if(realmResults.size()!=0){//==> if have not that row, we send a new table by default values
            TaskTable taskTableProxy =(TaskTable)realmResults.get(0);
            taskTable=new TaskTable(taskTableProxy.getId(),taskTableProxy.getGuid(),taskTableProxy.getSubject(),taskTableProxy.getComments(),taskTableProxy.getGeoLocation(),taskTableProxy.getSyncState(),taskTableProxy.getActivityState(),taskTableProxy.getOldValue(),taskTableProxy.getTaskDate(),taskTableProxy.getDeadLine(),taskTableProxy.getAttach(),taskTableProxy.getB5IdRefId(),taskTableProxy.getB5IdRefId2(),taskTableProxy.getB5IdRefId3(),taskTableProxy.getT5SCTypeId(),taskTableProxy.getB5HCPriorityId(),taskTableProxy.getB5HCStatusId());
        }
    }
}
