package ir.fararayaneh.erp.data.data_providers.queries.weighing;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WeighingTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * agar radifi peida nashavad, ya state==accessDenied  bashad 1 satre khali barmigaedanad
 * dar inja be dalil shart haye khas query , nemishavad az GetRowByGUIDQuery estefade kard
 */
public class GetOneRowByIdWeighingQuery extends BaseQueries {

    private WeighingTable weighingTable;

    @Override
    public Single<IModels> data(IModels iModels) {

        super.data(iModels);

        GlobalModel globalModel = (GlobalModel) iModels;
        String id = globalModel.getMyString();

        return Single.create(emitter -> realm.executeTransactionAsync(

                realm -> {
                    RealmResults<WeighingTable> results = realm.where(WeighingTable.class)
                            .equalTo(CommonColumnName.ID, id)
                            .notEqualTo(CommonColumnName.SYNCSTATE, CommonSyncState.ACCESS_DENIED)
                            .findAll();
                    if (results.size() != 0) {
                        weighingTable = new WeighingTable(results.get(0).getId(),results.get(0).getCarPlaqueNumber(),results.get(0).getBuyerName(),results.get(0).getGoodTranseDate(),results.get(0).getDriverName(),results.get(0).getEmptyDate(),results.get(0).getGoodTranseNumber(),results.get(0).getEmptyWeight(),results.get(0).getGoodTranseAmount(),results.get(0).getGoodTranseWeight(),results.get(0).getSumWeight(),results.get(0).getBalance(),results.get(0).getSyncState(),results.get(0).getActivityState(),results.get(0).getOldValue(),results.get(0).getB5HCStatusId());
                    } else {
                        weighingTable = new WeighingTable();
                    }
                }
                ,
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(weighingTable);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("GetOneRowByIdWeighingQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));

    }

}
