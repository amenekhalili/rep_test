package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CartableTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanCartableQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<CartableTable> realmResults =
                realm.where(CartableTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if(realmResults.size()!=0){
            Stream.of(realmResults).forEach(cartableTable -> {
                haveUnSyncData=true;
                //no need to send socket, because we have not uncync data in cartable...but ...!!
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .cartableSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),new CartableTable(cartableTable.getId(),cartableTable.getInsertDate(),cartableTable.getSubject(),cartableTable.getB5HCPriorityName(),cartableTable.getDefinitionName(),cartableTable.getSenderName(),cartableTable.getB5FormRefTitle(),cartableTable.getSyncState(),cartableTable.getAttach())
                        ), true);
            });

        }
    }
}
