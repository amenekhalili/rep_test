package ir.fararayaneh.erp.data.data_providers.queries.time_filter_query;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Single;
import io.realm.Realm;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * در کویری های زیر مجموعه این کویری
 * لازم نیست هیچ شرطی چک شود فقط کافی است رکورد هایی که از
 * نظر تایم مناسب است اماده شود تا در کویری های مناسب بعدی استفاده گردد
 */

public abstract class BaseGetTimeFilteredIdListQuery extends BaseQueries {

    protected ArrayList<String> finalIdGuidList = new ArrayList<>();
    protected Date startDate,endDate;
    @Override
    public Single<IModels> data(final IModels iModels) {
        super.data(iModels);

        GlobalModel globalModel = (GlobalModel) iModels;
         startDate = globalModel.getStartDate();
         endDate = globalModel.getEnddate();

        return Single.create(emitter -> realm.executeTransactionAsync(this::calculateFilteredIdGuid,
                () -> {
                    if (!emitter.isDisposed()) {
                        globalModel.setStringArrayList(finalIdGuidList);
                        emitter.onSuccess(globalModel);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("BaseGetTimeFilteredIdListQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }

    protected abstract void calculateFilteredIdGuid(Realm realm);



}