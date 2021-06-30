package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AddressBookTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanAddressBookQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<AddressBookTable> realmResults =
                realm.where(AddressBookTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if (realmResults.size() != 0) {
            Stream.of(realmResults).forEach(addressBookTable -> {
                haveUnSyncData = true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .addressBookSocket(
                                SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),
                                new AddressBookTable(
                                        addressBookTable.getIsMain(),
                                        addressBookTable.getGuid(),
                                        addressBookTable.getId(),
                                        addressBookTable.getB5IdRefId(),
                                        addressBookTable.getB5CountryDivisionId(),
                                        addressBookTable.getAddress(),
                                        addressBookTable.getPostalCode(),
                                        addressBookTable.getB5HCOwnershipId(),
                                        addressBookTable.getB5HCAddressTypeId(),
                                        addressBookTable.getStartOwnedDate(),
                                        addressBookTable.getStopOwnedDate(),
                                        addressBookTable.getRegion(),
                                        addressBookTable.getLocation(),
                                        addressBookTable.getSyncState(),
                                        addressBookTable.getActivityState(),
                                        addressBookTable.getOldValue()
                                )
                        ), true);
            });

        }
    }
}
