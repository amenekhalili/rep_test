package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.KartableRecieverTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanKartableReceiverQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<KartableRecieverTable> realmResults =
                realm.where(KartableRecieverTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if(realmResults.size()!=0){
            Stream.of(realmResults).forEach(kartableRecieverTable -> {
                haveUnSyncData=true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .kartableReceiverSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),new KartableRecieverTable(kartableRecieverTable.getId(),kartableRecieverTable.getName(),kartableRecieverTable.getAttach(),kartableRecieverTable.getSyncState())
                        ), true);
            });

        }
    }
}

