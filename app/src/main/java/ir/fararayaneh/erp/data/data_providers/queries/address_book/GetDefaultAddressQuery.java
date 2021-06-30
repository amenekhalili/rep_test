package ir.fararayaneh.erp.data.data_providers.queries.address_book;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.GroupRelatedTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AddressBookTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/*
we send b5IdRefId to here as myString
we send address & addressId FROM HERE as myString2 & myString3
 */
public class GetDefaultAddressQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        GlobalModel globalModel = (GlobalModel) iModels;
        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    RealmResults<AddressBookTable> results =
                            realm.where(AddressBookTable.class)
                                    .equalTo(CommonColumnName.B5IDREFID, globalModel.getMyString())
                                    .equalTo(CommonColumnName.IS_MAIN, Commons.IS_MAIN)
                                    .findAll();
                    if (results.size() != 0) {
                        globalModel.setMyString2(Objects.requireNonNull(results.get(0)).getAddress());
                        globalModel.setMyString3(Objects.requireNonNull(results.get(0)).getId());
                    } else {
                        globalModel.setMyString2(Commons.NULL);
                        globalModel.setMyString3(Commons.NULL_INTEGER);
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
                    ThrowableLoggerHelper.logMyThrowable("GetDefaultAddressQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
