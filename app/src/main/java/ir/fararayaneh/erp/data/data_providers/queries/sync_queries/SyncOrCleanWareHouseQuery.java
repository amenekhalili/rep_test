package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;
import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

/**
 * send all beforeSync and onSync and sync error table and return true
 * or
 * delete all  and return false
 */
public class SyncOrCleanWareHouseQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<WarehouseHandlingTable> realmResults =
                realm.where(WarehouseHandlingTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if(realmResults.size()!=0){
            Stream.of(realmResults).forEach(warehouseHandlingTable -> {
                haveUnSyncData=true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .wareHouseSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),
                                new WarehouseHandlingTable(warehouseHandlingTable.getId(),warehouseHandlingTable.getGoodCode(),warehouseHandlingTable.getSerial(),warehouseHandlingTable.getCountingNumber(),warehouseHandlingTable.getGoodColumnNumber(),warehouseHandlingTable.getAmount(),warehouseHandlingTable.getSubAmount(),warehouseHandlingTable.getGoodName(),warehouseHandlingTable.getBarCode(),warehouseHandlingTable.getMainUnitName(),warehouseHandlingTable.getSubUnitName(),warehouseHandlingTable.getPlaceShelfRow(),warehouseHandlingTable.getPlaceShelfSubRow(),warehouseHandlingTable.getPlaceShelfLayer(),warehouseHandlingTable.getBatchNumber(),warehouseHandlingTable.getSyncState(),warehouseHandlingTable.getOldValue(),warehouseHandlingTable.getSearchValue())), true);
                /*try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Log.i("arash", "getAllUnSyncRowAndSendOrDelete: "+e.getMessage());
                }*/
            });
        }
    }
}