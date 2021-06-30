package ir.fararayaneh.erp.data.data_providers.queries.time_queris;

import android.util.Log;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TimeTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class InsertUpdateTimeTableQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        TimeTable timeTable = (TimeTable) iModels;

        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    /*TimeTable table = new TimeTable();
                    TimeTable table = realm.createObject(TimeTable.class,timeTable.getGuid());
                    table.setActivityState(timeTable.getActivityState());
                    table.setB5formRefId(timeTable.getB5formRefId());
                    table.setB5HCDailyScheduleId(timeTable.getB5HCDailyScheduleId());
                    table.setB5HCWageTitleId(timeTable.getB5HCWageTitleId());
                    table.setB5IdRefId(timeTable.getB5IdRefId());
                    table.setB5IdRefId2(timeTable.getB5IdRefId2());
                    table.setB5IdRefId3(timeTable.getB5IdRefId3());
                    table.setDescription(timeTable.getDescription());
                    table.setEndDate(timeTable.getEndDate());
                    table.setGeoLocation(timeTable.getGeoLocation());
                    table.setId(timeTable.getId());
                    table.setIsHappeningAtTheSameTime(timeTable.getIsHappeningAtTheSameTime());
                    table.setOldValue(timeTable.getOldValue());
                    table.setStartDate(timeTable.getStartDate());
                    table.setSyncState(timeTable.getSyncState());
                    table.setB5HCStatusId(timeTable.getB5HCStatusId());*/
                    realm.insertOrUpdate(timeTable);
                    Log.i("arash", "add data to time table: "+realm.where(TimeTable.class).findAll().size());

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
                    ThrowableLoggerHelper.logMyThrowable("InsertUpdateTimeTableQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}

