package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.SalableGoodsTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanSalableGoodsQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<SalableGoodsTable> realmResults =
                realm.where(SalableGoodsTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if (realmResults.size() != 0) {
            Stream.of(realmResults).forEach(salableGoodsTable -> {
                haveUnSyncData = true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .salableGoodsSocket(
                                SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),
                                new SalableGoodsTable(
                                        salableGoodsTable.getGoodColumnNumber(),
                                        salableGoodsTable.getGuid(),
                                        salableGoodsTable.getId(),
                                        salableGoodsTable.getCustomerGroupId(),
                                        salableGoodsTable.getGoodId(),
                                        salableGoodsTable.getCode(),
                                        salableGoodsTable.getGoodCode(),
                                        salableGoodsTable.getTechInfo(),
                                        salableGoodsTable.getGoodName(),
                                        salableGoodsTable.getUnitName(),
                                        salableGoodsTable.getUnitId2(),
                                        salableGoodsTable.getUnitName2(),
                                        salableGoodsTable.getDescription(),
                                        salableGoodsTable.getUnitPrice(),
                                        salableGoodsTable.getLength(),
                                        salableGoodsTable.getWidth(),
                                        salableGoodsTable.getHeight(),
                                        salableGoodsTable.getAlloy(),
                                        salableGoodsTable.getGoodBrandId(),
                                        salableGoodsTable.getDiscountPercentage(),
                                        salableGoodsTable.getTaxPercentage(),
                                        salableGoodsTable.getAvailableAmount2(),
                                        salableGoodsTable.getTempAvailableAmount2(),
                                        salableGoodsTable.getNumerator(),
                                        salableGoodsTable.getDenominator(),
                                        salableGoodsTable.getBaseAmountForGift(),
                                        salableGoodsTable.getGoodsIdGift(),
                                        salableGoodsTable.getGiftAmount(),
                                        salableGoodsTable.getExpireDate(),
                                        salableGoodsTable.getSyncState(),
                                        salableGoodsTable.getActivityState(),
                                        salableGoodsTable.getOldValue()
                                )
                        ), true);
            });

        }
    }
}