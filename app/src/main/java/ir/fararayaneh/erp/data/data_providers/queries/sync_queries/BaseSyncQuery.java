package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;


import io.reactivex.Single;
import io.realm.Realm;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public abstract class BaseSyncQuery extends BaseQueries {

    protected boolean haveUnSyncData;
    @Override
    public Single<IModels> data(final IModels iModels) {
        super.data(iModels);
        return Single.create(emitter -> realm.executeTransactionAsync(this::getAllUnSyncRowAndSendOrDelete,
                () -> {
                    if (!emitter.isDisposed()) {
                        GlobalModel globalModel=new GlobalModel();
                        globalModel.setMyBoolean(haveUnSyncData);
                        emitter.onSuccess(globalModel);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("BaseSyncQuery***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }

    protected abstract void getAllUnSyncRowAndSendOrDelete(Realm realm);
}