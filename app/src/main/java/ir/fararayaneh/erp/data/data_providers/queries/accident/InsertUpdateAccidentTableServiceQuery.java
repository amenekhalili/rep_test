package ir.fararayaneh.erp.data.data_providers.queries.accident;

import android.util.Log;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AccidentTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;

//todo eslahe hame queri haye class service va hazfe anha be samte queri haye asli
public class InsertUpdateAccidentTableServiceQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        AccidentTable accidentTable = (AccidentTable) iModels;

        return Single.create(emitter -> {
            realm.beginTransaction();
            realm.insertOrUpdate(accidentTable);
            realm.commitTransaction();
            Log.i("arash", "InsertUpdateAccidentTableServiceQuery: " + realm.where(AccidentTable.class).findAll().size());
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}
