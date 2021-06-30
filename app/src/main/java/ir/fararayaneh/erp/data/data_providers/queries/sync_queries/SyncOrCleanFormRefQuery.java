package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.FormRefTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanFormRefQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<FormRefTable> realmResults =
                realm.where(FormRefTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if(realmResults.size()!=0){
            Stream.of(realmResults).forEach(formRefTable -> {
                haveUnSyncData=true;
                SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                        .formRefSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                SharedPreferenceProvider.getUserId(App.getAppContext()),new FormRefTable(formRefTable.getFormId(),formRefTable.getFormCode(),formRefTable.getFormName(),formRefTable.getFormTitle(),formRefTable.getAppName(),formRefTable.getConfigIdRef1(),formRefTable.getConfigIdRef2(),formRefTable.getConfigIdRef3(),formRefTable.getConfigIdRef4(),formRefTable.getConfigIdRef5(),formRefTable.getConfigIdRef6(),formRefTable.getConfigIdRef7(),formRefTable.getConfigIdRef8(),formRefTable.getConfigIdRef9(),formRefTable.getConfigIdRef10(),formRefTable.getConfigIdRef11(),formRefTable.getConfigIdRef12(),formRefTable.getConfigIdRef13(),formRefTable.getConfigIdRef14(),formRefTable.getConfigIdRef15(),formRefTable.getIconUrl(),formRefTable.getSyncState(),formRefTable.getMainMenuActive(),formRefTable.getFormLocation())
                        ), true);
            });

        }
    }
}
