package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.GoodTranceTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanGoodTrancQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<GoodTranceTable> realmResults =
                realm.where(GoodTranceTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if (realmResults.size() != 0) {
            Stream.of(realmResults).forEach(goodTranceTable -> {
                haveUnSyncData = true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .goodTransSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),
                                new GoodTranceTable(goodTranceTable.getId(),
                                        goodTranceTable.getB5FormRefId(),
                                        goodTranceTable.getB5IdRefId1(),
                                        goodTranceTable.getB5IdRefId2(),
                                        goodTranceTable.getB5IdRefId3(),
                                        goodTranceTable.getB5IdRefId4(),
                                        goodTranceTable.getB5IdRefId5(),
                                        goodTranceTable.getB5IdRefId6(),
                                        goodTranceTable.getB5IdRefId14(),
                                        goodTranceTable.getB5HCCurrencyId(),
                                        goodTranceTable.getB5HCSellMethodId(),
                                        goodTranceTable.getB5HCAccountSideId(),
                                        goodTranceTable.getB5IdRefIdRecall(),
                                        goodTranceTable.getB5HCStatusId(),
                                        goodTranceTable.getDeliveryName(),
                                        goodTranceTable.getFormNumber(),
                                        goodTranceTable.getFormDate(),
                                        goodTranceTable.getExpireDate(),
                                        goodTranceTable.getDescription(),
                                        goodTranceTable.getGuid(),
                                        goodTranceTable.getSyncState(),
                                        goodTranceTable.getActivityState(),
                                        goodTranceTable.getOldValue(),
                                        goodTranceTable.getGoodTransDetail(),
                                        goodTranceTable.getDuration(),
                                        goodTranceTable.getCustomerGroupId(),
                                        goodTranceTable.getAddressId()
                                )
                        ), true);
            });

        }
    }
}
