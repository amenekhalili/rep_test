package ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * for set main value and sub value (myInt and myInt2) and sync state from user
 * if have that row and syncState=synce or syncError
 */
public class SetValueWarehouseQuery extends BaseQueries {

    private WarehouseHandlingTable warehouseHandlingTable;

    @Override
    public Single<IModels> data(IModels iModels) {

        super.data(iModels);

        GlobalModel globalModel = (GlobalModel) iModels;
        String id = globalModel.getMyString3();
        String mainNumber = globalModel.getMyString();
        String subNumber = globalModel.getMyString2();


        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    RealmResults<WarehouseHandlingTable> results =
                            realm.where(WarehouseHandlingTable.class).
                                    equalTo(CommonColumnName.ID, id).findAll();

                    //agar data be daste user reside ast ama dar zamane taghir
                    //in record , responce accessDenied be ma reside bashad
                    //ejaze insert nadarim
                    if (results.size() != 0) {
                        if (Objects.requireNonNull(results.get(0)).getSyncState().equals(CommonSyncState.SYNCED)
                                ||
                                Objects.requireNonNull(results.get(0)).getSyncState().equals(CommonSyncState.SYNC_ERROR)) {
                            Objects.requireNonNull(results.get(0)).setAmount(mainNumber);
                            Objects.requireNonNull(results.get(0)).setSubAmount(subNumber);
                            Objects.requireNonNull(results.get(0)).setSyncState(CommonSyncState.BEFORE_SYNC);
                            //realm.insertOrUpdate(Objects.requireNonNull(results.get(0))); realm result is view, no need to insert
                            warehouseHandlingTable = new WarehouseHandlingTable(results.get(0).getId(), results.get(0).getGoodCode(), results.get(0).getSerial(), results.get(0).getCountingNumber(), results.get(0).getGoodColumnNumber(), results.get(0).getAmount(), results.get(0).getSubAmount(), results.get(0).getGoodName(), results.get(0).getBarCode(), results.get(0).getMainUnitName(), results.get(0).getSubUnitName(), results.get(0).getPlaceShelfRow(), results.get(0).getPlaceShelfSubRow(), results.get(0).getPlaceShelfLayer(), results.get(0).getBatchNumber(), results.get(0).getSyncState(),results.get(0).getOldValue(), results.get(0).getSearchValue());
                        } else {
                            warehouseHandlingTable = new WarehouseHandlingTable();
                        }
                    } else {
                        warehouseHandlingTable = new WarehouseHandlingTable();
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
                    ThrowableLoggerHelper.logMyThrowable("SetValueWarehouseQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));

    }

}
