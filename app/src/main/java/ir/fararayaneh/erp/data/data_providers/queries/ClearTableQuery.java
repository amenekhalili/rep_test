package ir.fararayaneh.erp.data.data_providers.queries;

import java.util.ArrayList;
import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * this query delete data for one or more tabales that come with iModels
 */
public class ClearTableQuery extends BaseQueries {
    @Override
    public Single<IModels> data(final IModels iModels) {
        super.data(iModels);
        return Single.create(emitter -> realm.executeTransactionAsync(realm -> {
            GlobalModel globalModel = (GlobalModel) iModels;
            ArrayList<Class> classArrayList = globalModel.getClassArrayList();
            for (Class myClass : classArrayList) {
                realm.where(myClass).findAll().deleteAllFromRealm();
            }
        },
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
                    ThrowableLoggerHelper.logMyThrowable("ClearTableQuery***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}