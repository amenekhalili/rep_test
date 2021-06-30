package ir.fararayaneh.erp.data.data_providers.queries;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.GroupRelatedTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/*
we send groupId to here as myString
we send groupName FROM HERE as myString2
 */
public class GetCustomerGroupNameFromIdQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        GlobalModel globalModel = (GlobalModel) iModels;
        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    RealmResults<GroupRelatedTable> results =
                            realm.where(GroupRelatedTable.class).equalTo(CommonColumnName.GROUP_ID, globalModel.getMyString()).findAll();
                    if (results.size() != 0) {//because maybe groupId=-1
                        globalModel.setMyString2(Objects.requireNonNull(results.get(0)).getParentName());
                    } else {
                        globalModel.setMyString2(Commons.NULL);
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
                    ThrowableLoggerHelper.logMyThrowable("GetCustomerGroupNameFromIdQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}

