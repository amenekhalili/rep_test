package ir.fararayaneh.erp.data.data_providers.queries.task_queris;


import android.util.Log;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TaskTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;

//todo eslahe hame queri haye class service va hazfe anha be samte queri haye asli

public class InsertUpdateTaskTableServiceQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        TaskTable taskTable = (TaskTable) iModels;

        return Single.create(emitter -> {

            realm.beginTransaction();
            realm.insertOrUpdate(taskTable);
            realm.commitTransaction();
            Log.i("arash", "add data to task table2: " + realm.where(TaskTable.class).findAll().size());
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}


