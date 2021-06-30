package ir.fararayaneh.erp.data.data_providers.queries.fuel_list_detail;

import android.util.Log;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListDetailTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TaskTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class InsertUpdateFuelListTableQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);


        FuelListDetailTable fuelListDetailTable = (FuelListDetailTable) iModels;

        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    realm.insertOrUpdate(fuelListDetailTable);
                    Log.i("arash", "add data to fueldetial table: " + realm.where(FuelListDetailTable.class).findAll().size());
                }
                ,
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(App.getNullRXModel());
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("InsertUpdateFuelListTableQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
