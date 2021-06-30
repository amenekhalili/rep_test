package ir.fararayaneh.erp.data.data_providers.queries;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.FormRefTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;


/**
 * global model should have formId, if we get 0, we have not those formId
 */
public class GetFormCodeQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        GlobalModel globalModel =  (GlobalModel)iModels;
        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    RealmResults<FormRefTable> results = realm.where(FormRefTable.class).
                            equalTo(CommonColumnName.FORM_ID,globalModel.getMyString()).findAll();
                    if(results.size()!=0){
                        globalModel.setMyString(Objects.requireNonNull(results.get(0)).getFormCode());
                    } else {
                        globalModel.setMyString(Commons.NULL_INTEGER);
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
                    ThrowableLoggerHelper.logMyThrowable("GetFormCodeQuery***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }

}
