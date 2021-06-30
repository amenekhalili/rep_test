package ir.fararayaneh.erp.data.data_providers.queries.attach_query;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.attachment.AttachmentTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class ChangeStateToSyncedByGUIDQuery extends BaseQueries {


    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        return Single.create(emitter -> realm.executeTransactionAsync(realm -> {

                    GlobalModel globalModel=(GlobalModel)iModels;
                    RealmResults<AttachmentTable> realmResults = realm.where(AttachmentTable.class)
                            .equalTo(CommonColumnName.ATTACHMENT_GUID, globalModel.getMyString())
                            .findAll();
                    if (realmResults.size() != 0) {
                        Objects.requireNonNull(realmResults.get(0)).setSyncState(CommonSyncState.SYNCED);
                    }
                },
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
                    ThrowableLoggerHelper.logMyThrowable("ChangeStateToSyncedByGUIDQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
