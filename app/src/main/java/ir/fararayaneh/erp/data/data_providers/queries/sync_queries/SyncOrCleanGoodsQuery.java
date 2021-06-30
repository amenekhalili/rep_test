package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanGoodsQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<GoodsTable> realmResults =
                realm.where(GoodsTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if(realmResults.size()!=0){
            Stream.of(realmResults).forEach(goodsTable -> {
                haveUnSyncData=true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .goodsSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),new GoodsTable(goodsTable.getId(),goodsTable.getC5BrandId(),goodsTable.getBrandName(),goodsTable.getB5HCStatusId(),goodsTable.getNationalityCode(),goodsTable.getCode(),goodsTable.getName(),goodsTable.getLatinName(),goodsTable.getDescription(),goodsTable.getGoogleKeyWord(),goodsTable.getTechInfo(),goodsTable.getStatusName(),goodsTable.getAttach(),goodsTable.getGuid(),goodsTable.getSerial(),goodsTable.getGoodSUOMList(),goodsTable.getGoodTypeList(),goodsTable.getOldValue(),goodsTable.getSyncState())
                        ), true);
            });

        }
    }
}
