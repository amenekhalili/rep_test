package ir.fararayaneh.erp.data.data_providers.queries.weighing;


import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.util.ArrayList;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WeighingTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * با توجه به اسم ستونی که به اینجا میاید،
 * globalModel.getMyString()
 * همه ان ستون برمیگردد
 * و همه آی دی آنها هم برمیگردد
 *  به شرطی که
 *  accessDenied
 *  نباشد
 */
public class GetAllDataFromOneColumnWeighingQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {

        super.data(iModels);

        GlobalModel globalModel =  (GlobalModel)iModels;
        String columnName = globalModel.getMyString();

        ArrayList<String> nameValue =new ArrayList<>();
        ArrayList<String> idValue =new ArrayList<>();

        return Single.create(emitter -> realm.executeTransactionAsync(

                realm -> {
                    RealmResults<WeighingTable> results = realm.where(WeighingTable.class)
                            .notEqualTo(CommonColumnName.SYNCSTATE, CommonSyncState.ACCESS_DENIED)
                            .findAll();

                    switch (columnName){
                        case CommonColumnName.GOODTRANSENUMBER:
                            //------------------------------
                            Stream.of(results).forEach(new Consumer<WeighingTable>() {
                                @Override
                                public void accept(WeighingTable weighingTable) {
                                    idValue.add(weighingTable.getId());
                                    nameValue.add(weighingTable.getGoodTranseNumber());
                                }
                            });
                            //------------------------------
                            break;
                        case CommonColumnName.CARPLAQUENUMBER:
                            //------------------------------
                            Stream.of(results).forEach(new Consumer<WeighingTable>() {
                                @Override
                                public void accept(WeighingTable weighingTable) {
                                    idValue.add(weighingTable.getId());
                                    nameValue.add(weighingTable.getCarPlaqueNumber());
                                }
                            });
                            //------------------------------
                            break;
                    }


                    globalModel.setStringArrayList(nameValue);
                    globalModel.setStringArrayList2(idValue);
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
                    ThrowableLoggerHelper.logMyThrowable("GetAllDataFromOneColumnWeighingQuery***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));

    }

}