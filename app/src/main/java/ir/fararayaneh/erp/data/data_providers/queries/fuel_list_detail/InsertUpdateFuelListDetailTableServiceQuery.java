package ir.fararayaneh.erp.data.data_providers.queries.fuel_list_detail;

import android.util.Log;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListDetailTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;

public class InsertUpdateFuelListDetailTableServiceQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        FuelListDetailTable fuelListDetailTable = (FuelListDetailTable) iModels;

        return Single.create(emitter -> {
            realm.beginTransaction();
            realm.insertOrUpdate(fuelListDetailTable);
            realm.commitTransaction();
            Log.i("arash", "InsertUpdateFuelListDetailTableServiceQuery: size" +
                    realm.where(FuelListDetailTable.class).findAll().size());
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}