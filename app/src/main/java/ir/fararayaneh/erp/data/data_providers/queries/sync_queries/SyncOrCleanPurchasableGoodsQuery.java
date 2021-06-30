package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanPurchasableGoodsQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<PurchasableGoodsTable> realmResults =
                realm.where(PurchasableGoodsTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if (realmResults.size() != 0) {
            Stream.of(realmResults).forEach(purchasableGoodsTable -> {
                haveUnSyncData = true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .purchasableGoodsSocket(
                                SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),
                                new PurchasableGoodsTable(
                                        purchasableGoodsTable.getGuid(),
                                        purchasableGoodsTable.getId(),
                                        purchasableGoodsTable.getCode(),
                                        purchasableGoodsTable.getGoodCode(),
                                        purchasableGoodsTable.getTechInfo(),
                                        purchasableGoodsTable.getGoodName(),
                                        purchasableGoodsTable.getUnitName1(),
                                        purchasableGoodsTable.getGoodColumnNumber(),
                                        purchasableGoodsTable.getUnitId(),
                                        purchasableGoodsTable.getUnitName2(),
                                        purchasableGoodsTable.getNumerator(),
                                        purchasableGoodsTable.getDenominator(),
                                        purchasableGoodsTable.getAmount2(),
                                        purchasableGoodsTable.getTempAmount2(),
                                        purchasableGoodsTable.getCode6(),
                                        purchasableGoodsTable.getName6(),
                                        purchasableGoodsTable.getFormNumber(),
                                        purchasableGoodsTable.getFormDate(),
                                        purchasableGoodsTable.getDescription(),
                                        purchasableGoodsTable.getLength(),
                                        purchasableGoodsTable.getWidth(),
                                        purchasableGoodsTable.getHeight(),
                                        purchasableGoodsTable.getAlloy(),
                                        purchasableGoodsTable.getExpireDate(),
                                        purchasableGoodsTable.getB5IdRefId15(),
                                        purchasableGoodsTable.getB5IdRefId8(),
                                        purchasableGoodsTable.getB5IdRefId9(),
                                        purchasableGoodsTable.getB5IdRefId10(),
                                        purchasableGoodsTable.getStatusName(),
                                        purchasableGoodsTable.getFormName(),
                                        purchasableGoodsTable.getYearCode(),
                                        purchasableGoodsTable.getGoodId(),
                                        purchasableGoodsTable.getSyncState(),
                                        purchasableGoodsTable.getActivityState(),
                                        purchasableGoodsTable.getOldValue(),
                                        purchasableGoodsTable.getAttach()
                                )
                        ), true);
            });

        }
    }
}