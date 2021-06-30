package ir.fararayaneh.erp.data.data_providers.queries.evacuate;


import android.util.Log;

import com.google.android.gms.common.internal.service.Common;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.EvacuatePacketTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonParser;


public class InsertUpdateEvacuateTableInSendProcessQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        GlobalModel globalModel = (GlobalModel)iModels;
        String json =globalModel.getMyString();
        String guid = SocketJsonParser.getGUIDForEvacuateHandler(json);

        return Single.create(emitter -> {
            if(!guid.equals(Commons.SPACE)){
                //اینجا ما مطمعن نیستیم که جی یو ای دی داشته باشیم
                realm.beginTransaction();
                realm.insertOrUpdate(new EvacuatePacketTable(guid,json));
                realm.commitTransaction();
            }
            Log.i("arash", "add data to EvacuatePacketTable " + realm.where(EvacuatePacketTable.class).findAll().size());
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}
