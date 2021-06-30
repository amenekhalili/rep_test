package ir.fararayaneh.erp.data.data_providers.queries.fuel_list_detail;

import android.util.Log;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListMasterTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class FindMasterDriverIdFuelListDetailQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        GlobalModel globalModel = (GlobalModel) iModels;

        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    RealmResults<FuelListMasterTable> fuelListMasterTables=
                    realm.where(FuelListMasterTable.class)
                            .equalTo(CommonColumnName.ID,globalModel.getMyString()).findAll();
                    if(fuelListMasterTables.size()!=0){
                        globalModel.setUserId(Objects.requireNonNull(fuelListMasterTables.get(0)).getDriverId());
                    } else {
                        globalModel.setUserId(Commons.NULL_INTEGER);
                    }
                }
                ,
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(globalModel);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("FindMasterDriverIdFuelListDetailQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
