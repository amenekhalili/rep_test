package ir.fararayaneh.erp.data.data_providers.queries;

import android.util.Log;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.BaseCodingTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;


/**
 * globalModel.getStringArrayList() : list of IDs
 * globalModel.getStringArrayList2() : list of Names
 */
public class BaseCodingNameListFromBaseCodingListId extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        GlobalModel globalModel = (GlobalModel) iModels;
        ArrayList<String> nameArrayList = new ArrayList<>(globalModel.getStringArrayList().size());
        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    Stream.of(globalModel.getStringArrayList()).forEach(new Consumer<String>() {
                        @Override
                        public void accept(String id) {
                            RealmResults<BaseCodingTable> baseCoding = realm.where(BaseCodingTable.class)
                                    .equalTo(CommonColumnName.ID, id)
                                    .findAll();
                            //we not sure we have those ids for this user
                            nameArrayList.add(baseCoding.size()!=0? Objects.requireNonNull(baseCoding.get(0)).getName(): Commons.NULL);
                        }
                    });
                    globalModel.setStringArrayList2(nameArrayList);
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
                    ThrowableLoggerHelper.logMyThrowable("BaseCodingNameListFromBaseCodingListId***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}