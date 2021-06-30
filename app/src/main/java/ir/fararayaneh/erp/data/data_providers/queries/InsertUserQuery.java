package ir.fararayaneh.erp.data.data_providers.queries;


import android.util.Log;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.UserTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * in jadval faghat bayad 1 radif dashte bashad;
 * bana bar in ghabl az insert har record, hame table pak mishavad
 */

public class InsertUserQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        GlobalModel globalModel = (GlobalModel) iModels;
        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {

                    //----------------------------------------------------------
                    realm.where(UserTable.class).findAll().deleteAllFromRealm();
                    //----------------------------------------------------------
                    UserTable userTable = realm.createObject(UserTable.class, globalModel.getUserId());
                    userTable.setUserName(globalModel.getUserName());
                    userTable.setPassWord(globalModel.getPassWord());
                    //userTable.setUserId(globalModel.getUserId()); chon primary key ast dar zamane ijad radif meghdar dehi mishavad
                    userTable.setOrganization(globalModel.getOrganization());
                    userTable.setCompanyName(globalModel.getCompanyName());
                    userTable.setUserImageUrl(globalModel.getUserImageUrl());
                    userTable.setDeviceId(globalModel.getDeviceId());
                    Log.i("arash", "data InsertUserQuery: " + realm.where(UserTable.class).findAll().size());
                }
                ,
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(App.getNullRXModel());
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("InsertUserQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
