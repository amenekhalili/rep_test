package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.GroupRelatedTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanGroupRelatedQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<GroupRelatedTable> realmResults =
                realm.where(GroupRelatedTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if(realmResults.size()!=0){
            Stream.of(realmResults).forEach(groupRelatedTable -> {
                haveUnSyncData=true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .groupRelatedSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),new GroupRelatedTable(groupRelatedTable.getId(),groupRelatedTable.getB5IdRefId(),groupRelatedTable.getGroupId(),groupRelatedTable.getIsMain(),groupRelatedTable.getParentTypeId(),groupRelatedTable.getParentCode(),groupRelatedTable.getParentName(),groupRelatedTable.getSyncState(),groupRelatedTable.getRootId())
                        ), true);
            });

        }
    }
}
