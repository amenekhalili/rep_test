package ir.fararayaneh.erp.data.data_providers.queries;

import android.util.Log;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.FacilitiesModel;
import ir.fararayaneh.erp.data.models.tables.FormRefTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * make data for show in main activity
 */

public class GetAllAppNameQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        GlobalModel globalModel = new GlobalModel();
        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {

                    RealmResults<FormRefTable> results =
                            realm.where(FormRefTable.class)
                                    .equalTo(CommonColumnName.MAIN_MENU_ACTIVE, Commons.IS_MAIN)
                                    .distinct(CommonColumnName.APP_NAME)
                                    .findAll();
                    Log.i("arash", "getdata: ic_start qury");
                    ArrayList<String> appName = new ArrayList<>();
                    ArrayList<FacilitiesModel> facilitiesModels = new ArrayList<>();
                    ArrayList<ArrayList<FacilitiesModel>> listArrayList = new ArrayList<>();

                    Stream.of(results).forEach(formRefTable -> {

                        appName.add(Objects.requireNonNull(formRefTable).getAppName());
                        RealmResults<FormRefTable> innerResults = realm.where(FormRefTable.class)
                                .equalTo(CommonColumnName.APP_NAME, Objects.requireNonNull(formRefTable).getAppName())
                                .equalTo(CommonColumnName.MAIN_MENU_ACTIVE, Commons.IS_MAIN)
                                .findAll();
                        ArrayList<FacilitiesModel> innerFacilitiesModels = new ArrayList<>();
                        Stream.of(innerResults).forEach(formRefTable1 -> {
                            FacilitiesModel model = new FacilitiesModel(Objects.requireNonNull(formRefTable1).getFormTitle(), Objects.requireNonNull(formRefTable1).getFormCode(), Objects.requireNonNull(formRefTable1).getIconUrl(), false);
                            facilitiesModels.add(model);
                            innerFacilitiesModels.add(model);
                        });
                        listArrayList.add(innerFacilitiesModels);

                    });

                    globalModel.setStringArrayList(appName);
                    globalModel.setFacilitiesArrayList(facilitiesModels);
                    globalModel.setListFacilitiesArrayList(listArrayList);
                }
                ,
                () -> {
                    if (!emitter.isDisposed()) {
                        Log.i("arash", "getdata: end qury");

                        emitter.onSuccess(globalModel);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("GetAllAppNameQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
