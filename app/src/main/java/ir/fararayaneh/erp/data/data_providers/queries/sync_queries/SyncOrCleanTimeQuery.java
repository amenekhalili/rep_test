package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;
import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TimeTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;


/**
 * send all beforeSync and onSync and sync error or delete all table and return true
 * or
 * delete all  and return false
 */
public class SyncOrCleanTimeQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<TimeTable> realmResults =
                realm.where(TimeTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if(realmResults.size()!=0){
            Stream.of(realmResults).forEach(timeTable -> {
                haveUnSyncData=true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .timeSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),new TimeTable(timeTable.getId(),timeTable.getGuid(),timeTable.getB5formRefId(),timeTable.getB5IdRefId(),timeTable.getB5IdRefId2(),timeTable.getB5IdRefId3(),timeTable.getB5HCDailyScheduleId(),timeTable.getB5HCWageTitleId(),timeTable.getIsHappeningAtTheSameTime(),timeTable.getStartDate(),timeTable.getEndDate(),timeTable.getDescription(),timeTable.getGeoLocation(),timeTable.getSyncState(),timeTable.getActivityState(),timeTable.getOldValue(),timeTable.getB5HCStatusId())
                                ), true);
            });

        }
    }
}