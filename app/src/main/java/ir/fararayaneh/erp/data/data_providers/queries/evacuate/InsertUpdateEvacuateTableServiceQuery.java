package ir.fararayaneh.erp.data.data_providers.queries.evacuate;


import android.util.Log;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.EvacuatePacketTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonParser;


//todo eslahe hame queri haye class service va hazfe anha be samte queri haye asli



public class InsertUpdateEvacuateTableServiceQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        GlobalModel globalModel = (GlobalModel) iModels;
        String JsonPacket = globalModel.getMyString();

        return Single.create(emitter -> {

            if(SocketJsonParser.checkJsonForEvacuateHandler(JsonPacket)){
                //اینجا حتما جی یو آی دی  داریم
                realm.beginTransaction();
                realm.where(EvacuatePacketTable.class)
                        .equalTo(CommonColumnName.GUID,SocketJsonParser.getGUIDForEvacuateHandler(JsonPacket))
                        .findAll()
                        .deleteAllFromRealm();
                realm.commitTransaction();
            }
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
            Log.i("arash", "evacuate process 3");
        });
    }
}
