package ir.fararayaneh.erp.data.data_providers.queries;

import java.util.ArrayList;

import io.reactivex.Single;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class HaveUnSyncQuery extends BaseQueries {
    private boolean haveUnSyncData;
    private GlobalModel globalModel;

    @Override
    public Single<IModels> data(final IModels iModels) {
        super.data(iModels);
        return Single.create(emitter -> realm.executeTransactionAsync(realm -> {
                     globalModel = (GlobalModel) iModels;
                    ArrayList<Class> classArrayList = globalModel.getClassArrayList();
                    for (Class myClass : classArrayList) {
                        if (realm.where(myClass)
                                .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                                .or()
                                .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                                .or()
                                .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                                .findAll().size() != 0) {
                            haveUnSyncData = true;
                        }
                    }
                },
                () -> {
                    if (!emitter.isDisposed()) {
                        globalModel.setMyBoolean(haveUnSyncData);
                        emitter.onSuccess(globalModel);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("HaveUnSyncQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
