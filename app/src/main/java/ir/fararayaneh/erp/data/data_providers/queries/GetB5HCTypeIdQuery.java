package ir.fararayaneh.erp.data.data_providers.queries;


import android.util.Log;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * GlobalModel should have b5hcTypeName in its myString property
 */
public class GetB5HCTypeIdQuery extends BaseQueries {


    private static final String ATACH_PARENT_CODE = "ATCH";

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        GlobalModel globalModel = (GlobalModel) iModels;


        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {

                    RealmResults<UtilCodeTable> results = realm.where(UtilCodeTable.class)
                            .equalTo(CommonColumnName.PARENT_CODE, ATACH_PARENT_CODE)
                            .equalTo(CommonColumnName.NAME, globalModel.getMyString())
                            .findAll();
                    Log.i("arash", "data: aaaaaaaaaaaaaaaaa"+globalModel.getMyString());

                    globalModel.setMyString(Objects.requireNonNull(results.get(0)).getId());
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
                    ThrowableLoggerHelper.logMyThrowable("GetB5HCTypeIdQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
