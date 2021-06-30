package ir.fararayaneh.erp.data.data_providers.queries;


import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.UserTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class HaveUserQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        GlobalModel globalModel = new GlobalModel();
        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    RealmResults<UserTable> results = realm.where(UserTable.class).findAll();

                    globalModel.setMyBoolean(results.size() == 1);

                    if (results.size() != 0) {
                        //no need to Objects.requireNonNull
                        globalModel.setUserId(Objects.requireNonNull(results.get(0)).getUserId());
                        globalModel.setUserName(Objects.requireNonNull(results.get(0)).getUserName());
                        globalModel.setOrganization(Objects.requireNonNull(results.get(0)).getOrganization());
                        globalModel.setUserImageUrl(Objects.requireNonNull(results.get(0)).getUserImageUrl());
                        globalModel.setPassWord(Objects.requireNonNull(results.get(0)).getPassWord());
                    }
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
                    ThrowableLoggerHelper.logMyThrowable("HaveUserQuery***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
