package ir.fararayaneh.erp.data.data_providers.queries.form_ref;

import android.util.Log;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.FormRefTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * get formLocation from formCode
 */
public class GetFormLocationQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        GlobalModel globalModel = (GlobalModel) iModels;

        return Single.create(emitter -> realm.executeTransactionAsync(

                realm -> {
                    RealmResults<FormRefTable> formRefTables = realm.where(FormRefTable.class)
                            .equalTo(CommonColumnName.FORM_CODE, globalModel.getMyString()).findAll();
                    if(formRefTables.size()!=0){
                        globalModel.setMyInt(Objects.requireNonNull(formRefTables.get(0)).getFormLocation());
                    } else {
                        globalModel.setMyInt(Commons.NO_NEED_FORCE_LOCATION);
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
                    ThrowableLoggerHelper.logMyThrowable("GetFormLocationQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));

    }

}
