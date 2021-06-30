package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ContactBookTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanContactBookQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<ContactBookTable> realmResults =
                realm.where(ContactBookTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if (realmResults.size() != 0) {
            Stream.of(realmResults).forEach(contactBookTable -> {
                haveUnSyncData = true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .contactBookSocket(
                                SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),
                                new ContactBookTable(
                                        contactBookTable.getIsMain(),
                                        contactBookTable.getId(),
                                        contactBookTable.getB5HCTypeId(),
                                        contactBookTable.getB5IdRefId(),
                                        contactBookTable.getValue(),
                                        contactBookTable.getDescription(),
                                        contactBookTable.getSyncState(),
                                        contactBookTable.getActivityState(),
                                        contactBookTable.getOldValue(),
                                        contactBookTable.getGuid()

                                )
                        ), true);
            });

        }
    }
}
