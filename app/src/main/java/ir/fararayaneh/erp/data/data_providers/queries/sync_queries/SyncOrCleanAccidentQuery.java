package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AccidentTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;


/**
 * send all beforeSync and onSync and sync error or delete all table and return true
 * or
 * delete all  and return false
 */
public class SyncOrCleanAccidentQuery extends BaseSyncQuery {
    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<AccidentTable> realmResults =
                realm.where(AccidentTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if(realmResults.size()!=0){
            Stream.of(realmResults).forEach(accidentTable -> {
                haveUnSyncData=true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .accidentSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),new AccidentTable(accidentTable.getGuid(),accidentTable.getId(),
                                        accidentTable.getSerial(),accidentTable.getInsertDate(),accidentTable.getAccidentDate(),
                                        accidentTable.getInsuranceName(),accidentTable.getDESCRIPTION(),accidentTable.getB5FinancialYearID(),
                                        accidentTable.getAddress(),accidentTable.getParentId(),accidentTable.getRegistrant(),accidentTable.getDamageName(),
                                        accidentTable.getFormId(),accidentTable.getInspectionId(),accidentTable.getSyncState(),accidentTable.getActivityState(),
                                        accidentTable.getOldValue(),accidentTable.getAttach())
                        ), true);
            });

        }
    }
}
