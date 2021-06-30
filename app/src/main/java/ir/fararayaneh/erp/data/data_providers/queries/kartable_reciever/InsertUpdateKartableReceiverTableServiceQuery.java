package ir.fararayaneh.erp.data.data_providers.queries.kartable_reciever;

import android.util.Log;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.KartableRecieverTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;

public class InsertUpdateKartableReceiverTableServiceQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        KartableRecieverTable kartableRecieverTable = (KartableRecieverTable) iModels;

        return Single.create(emitter -> {
            realm.beginTransaction();
            realm.insertOrUpdate(kartableRecieverTable);
            realm.commitTransaction();
            Log.i("arash", "InsertUpdateKartableReceiverTableServiceQuery: size" + realm.where(KartableRecieverTable.class).findAll().size());
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}
