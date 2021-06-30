package ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * در صورتی که جدول ما دارای guid فیک است
 * به این معنی است که ما حتما دارای ای دی هستیم
 */
public abstract class BaseRowByGuidQuery extends BaseQueries {

    @Override
    public Single<IModels> data(final IModels iModels) {
        super.data(iModels);
        GlobalModel globalModel = (GlobalModel) iModels;
        ArrayList<Class> classArrayList = globalModel.getClassArrayList();
        String myGUIDOrID = globalModel.getMyString();
        boolean haveFakeGUID = globalModel.isMyBoolean();

        return Single.create(emitter -> realm.executeTransactionAsync(realm -> {
            if(haveFakeGUID){
                BaseRowByGuidQuery.this.getRowByGuid(realm.where(classArrayList.get(0)).equalTo(CommonColumnName.ID, myGUIDOrID).findAll());
            }else {
                BaseRowByGuidQuery.this.getRowByGuid(realm.where(classArrayList.get(0)).equalTo(CommonColumnName.GUID, myGUIDOrID).findAll());
            }
                },
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(BaseRowByGuidQuery.this.getProperModel());
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("BaseRowByGuidQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }

    protected abstract void getRowByGuid(RealmResults realmResults);

    protected abstract IModels getProperModel();//this method call after that IModels have values

}
