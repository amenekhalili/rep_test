package ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris;

import com.annimon.stream.Stream;

import java.util.ArrayList;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;


/**
 * get all value from PlaceShelfRow + Commons.NULL at first (for all synced and synced error)
 */
public class GetAllPlaceShelfRowWarehouseQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        ArrayList<String> nameValue = new ArrayList<>();
        nameValue.add(Commons.NULL_WAREHOUSE_FILTER_VALUE);

        GlobalModel globalModel = new GlobalModel();

        return Single.create(emitter -> realm.executeTransactionAsync(

                realm -> {
                    RealmResults<WarehouseHandlingTable> results = realm.where(WarehouseHandlingTable.class)
                            .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNCED)
                            .or()
                            .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                            .distinct(CommonColumnName.PLACE_SHELF_ROW)
                            .findAll();

                    Stream.of(results).forEach(warehouseHandlingTable -> nameValue.add(warehouseHandlingTable.getPlaceShelfRow()));
                    globalModel.setStringArrayList(nameValue);
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
                    ThrowableLoggerHelper.logMyThrowable("GetAllPlaceShelfRowWarehouseQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));

    }

}