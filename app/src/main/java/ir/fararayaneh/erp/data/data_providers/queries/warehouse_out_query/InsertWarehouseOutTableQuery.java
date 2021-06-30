package ir.fararayaneh.erp.data.data_providers.queries.warehouse_out_query;


import io.reactivex.Single;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingListOutTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.UUIDHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * only for insert from UI
 */
public class InsertWarehouseOutTableQuery extends BaseQueries {

    private WarehouseHandlingListOutTable warehouseHandlingListOutTable = new WarehouseHandlingListOutTable();

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        GlobalModel globalModel = (GlobalModel) iModels;

         warehouseHandlingListOutTable = new WarehouseHandlingListOutTable(UUIDHelper.getProperUUID(),globalModel.getMyString(),globalModel.getMyString2(),globalModel.getMyString3(),CommonSyncState.BEFORE_SYNC,globalModel.getMyString4());

        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> realm.insertOrUpdate(warehouseHandlingListOutTable)
                ,
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(warehouseHandlingListOutTable);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("InsertWarehouseOutTableQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));

    }

}
