package ir.fararayaneh.erp.data.data_providers.queries;

import java.util.Objects;

import io.reactivex.Single;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.UserTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;


public class CheckPassWordQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        GlobalModel globalModel = (GlobalModel) iModels;
        return Single.create(emitter -> realm.executeTransactionAsync(realm -> globalModel.setMyBoolean(Objects.requireNonNull(realm.where(UserTable.class).
                        equalTo(CommonColumnName.USER_NAME, globalModel.getUserName()).findAll().get(0)).getPassWord().equals(globalModel.getPassWord())),
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
                    ThrowableLoggerHelper.logMyThrowable("CheckPassWordQuery***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
