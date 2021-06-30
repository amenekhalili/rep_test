package ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris;

import io.reactivex.Single;
import io.realm.RealmResults;
import io.realm.Sort;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class GetFirstEditableWarehouseRowQuery  extends BaseQueries {

    private WarehouseHandlingTable warehouseHandlingTable = new WarehouseHandlingTable();
    @Override
    public Single<IModels> data(IModels iModels) {

        super.data(iModels);

        GlobalModel globalModel =  (GlobalModel)iModels;
        String filterValue = globalModel.getMyString();

        return Single.create(emitter -> realm.executeTransactionAsync(

                realm -> {
                    RealmResults<WarehouseHandlingTable> results;
                    if(filterValue.equals(Commons.NULL_WAREHOUSE_FILTER_VALUE)){
                        results = realm.where(WarehouseHandlingTable.class)
                                .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNCED)
                                .or()
                                .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                                .sort(CommonColumnName.PLACE_SHELF_ROW, Sort.ASCENDING,CommonColumnName.GOOD_CODE, Sort.ASCENDING)

                                .findAll();
                    } else {
                        results = realm.where(WarehouseHandlingTable.class)
                                .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNCED)
                                .equalTo(CommonColumnName.PLACE_SHELF_ROW,filterValue)
                                .or()
                                .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                                .equalTo(CommonColumnName.PLACE_SHELF_ROW,filterValue)
                                .sort(CommonColumnName.PLACE_SHELF_ROW, Sort.ASCENDING,CommonColumnName.GOOD_CODE, Sort.ASCENDING)

                                .findAll();
                    }

                    if(results.size()!=0){
                        warehouseHandlingTable = new WarehouseHandlingTable(results.get(0).getId(),results.get(0).getGoodCode(),results.get(0).getSerial(),results.get(0).getCountingNumber(),results.get(0).getGoodColumnNumber(),results.get(0).getAmount(),results.get(0).getSubAmount(),results.get(0).getGoodName(),results.get(0).getBarCode(),results.get(0).getMainUnitName(),results.get(0).getSubUnitName(),results.get(0).getPlaceShelfRow(),results.get(0).getPlaceShelfSubRow(),results.get(0).getPlaceShelfLayer(),results.get(0).getBatchNumber(),results.get(0).getSyncState(),results.get(0).getOldValue(),results.get(0).getSearchValue());
                    }

                }
                ,
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(warehouseHandlingTable);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("GetFirstEditableWarehouseRowQuery***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));

    }

}

