package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TaskTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

/**
 * send all beforeSync and onSync and sync error or delete all table and return true
 * or
 * delete all  and return false
 */
public class SyncOrCleanTaskQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<TaskTable> realmResults =
                realm.where(TaskTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if (realmResults.size() != 0) {
            Stream.of(realmResults).forEach(taskTable -> {
                haveUnSyncData = true;
            SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker.taskSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()), SharedPreferenceProvider.getUserId(App.getAppContext()), new TaskTable(taskTable.getId(), taskTable.getGuid(), taskTable.getSubject(), taskTable.getComments(), taskTable.getGeoLocation(), taskTable.getSyncState(), taskTable.getActivityState(), taskTable.getOldValue(), taskTable.getTaskDate(), taskTable.getDeadLine(), taskTable.getAttach(), taskTable.getB5IdRefId(), taskTable.getB5IdRefId2(), taskTable.getB5IdRefId3(), taskTable.getT5SCTypeId(), taskTable.getB5HCPriorityId(), taskTable.getB5HCStatusId())), true);
            });
        }
    }
}
