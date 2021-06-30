package ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries;

import io.realm.RealmResults;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TimeTable;

public class GetRowTimeByGUIDQuery extends BaseRowByGuidQuery {

    private TimeTable timeTable = new TimeTable();
    @Override
    protected IModels getProperModel() {
        return timeTable;
    }

    @Override
    protected void getRowByGuid(RealmResults realmResults) {
        if(realmResults.size()!=0){//==> if have not that row, we send a new table by default values
            TimeTable timeTableProxy =(TimeTable)realmResults.get(0);
            timeTable=new TimeTable(timeTableProxy.getId(),timeTableProxy.getGuid(),timeTableProxy.getB5formRefId(),timeTableProxy.getB5IdRefId(),timeTableProxy.getB5IdRefId2(),timeTableProxy.getB5IdRefId3(),timeTableProxy.getB5HCDailyScheduleId(),timeTableProxy.getB5HCWageTitleId(),timeTableProxy.getIsHappeningAtTheSameTime(),timeTableProxy.getStartDate(),timeTableProxy.getEndDate(),timeTableProxy.getDescription(),timeTableProxy.getGeoLocation(),timeTableProxy.getSyncState(),timeTableProxy.getActivityState(),timeTableProxy.getOldValue(),timeTableProxy.getB5HCStatusId());
        }
    }
}
