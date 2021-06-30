package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListDetailTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanFuelListDetailQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<FuelListDetailTable> realmResults =
                realm.where(FuelListDetailTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if(realmResults.size()!=0){
            Stream.of(realmResults).forEach(fuelListDetail -> {
                haveUnSyncData=true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .fuelListSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider
                                        .getUserId(App.getAppContext())
                                ,new FuelListDetailTable(fuelListDetail.getId()
                                        ,fuelListDetail.getB5HCDistributionId()
                                        ,fuelListDetail.getPlaceName()
                                        ,fuelListDetail.getDeviceName()
                                        ,fuelListDetail.getDistributionDate()
                                        ,fuelListDetail.getAmount()
                                        ,fuelListDetail.getDescription()
                                        ,fuelListDetail.getMasterId()
                                        ,fuelListDetail.getLocation(),
                                        fuelListDetail.getSyncState(),
                                        fuelListDetail.getActivityState(),
                                        fuelListDetail.getOldValue())), true);
            });

        }
    }
}
