package ir.fararayaneh.erp.data.data_providers.queries;

import com.annimon.stream.Stream;

import java.util.ArrayList;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.KartableRecieverTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class GetComboIdNameFromKartableRecieverQuery extends BaseQueries {

   private GlobalModel globalModel= new GlobalModel();
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {

                    //create for result
                    ArrayList<ArrayList<String>> comboIds = new ArrayList<>(1);
                    ArrayList<ArrayList<String>> comboNames = new ArrayList<>(1);

                        RealmResults<KartableRecieverTable> results = realm.where(KartableRecieverTable.class).findAll();

                        ArrayList<String> idList = new ArrayList<>(results.size());
                        ArrayList<String> nameList = new ArrayList<>(results.size());
                        Stream.of(results).forEach(kartableRecieverTable -> {
                            idList.add(kartableRecieverTable.getId());
                            nameList.add(kartableRecieverTable.getName());
                        });

                        comboIds.add(idList);
                        comboNames.add(nameList);

                        globalModel.setListStringArrayList(comboNames);
                        globalModel.setListStringArrayList2(comboIds);
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
                    ThrowableLoggerHelper.logMyThrowable("GetComboIdNameFromKartableRecieverQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
