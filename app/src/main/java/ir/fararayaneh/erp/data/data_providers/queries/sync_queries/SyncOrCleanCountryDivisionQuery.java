package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CountryDivisionTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanCountryDivisionQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<CountryDivisionTable> realmResults =
                realm.where(CountryDivisionTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if (realmResults.size() != 0) {
            Stream.of(realmResults).forEach(countryDivisionTable -> {
                haveUnSyncData = true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .countryDivisionSocket(
                                SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),
                                new CountryDivisionTable(
                                        countryDivisionTable.getId(),
                                        countryDivisionTable.getName(),
                                        countryDivisionTable.getB5CountryDivisionId(),
                                        countryDivisionTable.getCountryDivisionTypeCode(),
                                        countryDivisionTable.getSyncState(),
                                        countryDivisionTable.getActivityState(),
                                        countryDivisionTable.getOldValue(),
                                        countryDivisionTable.getGuid()
                                )
                        ), true);
            });

        }
    }
}