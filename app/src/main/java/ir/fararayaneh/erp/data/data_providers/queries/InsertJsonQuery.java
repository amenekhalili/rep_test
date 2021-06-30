package ir.fararayaneh.erp.data.data_providers.queries;

import android.util.Log;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class InsertJsonQuery extends BaseQueries {
    private GlobalModel globalModel;
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        return Single.create(emitter -> realm.executeTransactionAsync(realm -> {
                    globalModel = (GlobalModel) iModels;
                    realm.createOrUpdateAllFromJson(globalModel.getClassArrayList().get(0), App.getmGson().toJson(globalModel.getRestResponseModel().getItems()));
                    globalModel.setCount(realm.where(globalModel.getClassArrayList().get(0)).findAll().size());
                    Log.i("arash", "dataaaaaa: "+realm.where(globalModel.getClassArrayList().get(0)).findAll().toString());
                },
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
                    ThrowableLoggerHelper.logMyThrowable("InsertJsonQuery***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}