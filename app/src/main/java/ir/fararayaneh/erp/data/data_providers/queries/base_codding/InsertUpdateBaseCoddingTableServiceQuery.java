package ir.fararayaneh.erp.data.data_providers.queries.base_codding;

import android.util.Log;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.BaseCodingTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;


//todo eslahe hame queri haye class service va hazfe anha be samte queri haye asli

public class InsertUpdateBaseCoddingTableServiceQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        BaseCodingTable baseCodingTable = (BaseCodingTable) iModels;

        return Single.create(emitter -> {
            realm.beginTransaction();
            realm.insertOrUpdate(baseCodingTable);
            realm.commitTransaction();
            Log.i("arash", "InsertUpdateBaseCoddingTableServiceQuery: size" + realm.where(BaseCodingTable.class).findAll().size());
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}
