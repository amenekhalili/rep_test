package ir.fararayaneh.erp.data.data_providers.queries.attach_query;

import android.util.Log;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.attachment.AttachmentTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;


public class FindFirstUnSyncAttachmentRowQuery extends BaseQueries {

    private AttachmentTable attachmentTable;

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        return Single.create(emitter -> realm.executeTransactionAsync(realm -> {

                    Log.i("arash", "data: "+realm.where(AttachmentTable.class).findAll());

                    RealmResults<AttachmentTable> realmResults = realm.where(AttachmentTable.class)
                            .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                            .or()
                            .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                            .or()
                            .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                            .findAll();
                    if (realmResults.size() != 0) {
                        attachmentTable = new AttachmentTable(Objects.requireNonNull(realmResults.get(0)).getAttachmentGUID(), Objects.requireNonNull(realmResults.get(0)).getSyncState(), Objects.requireNonNull(realmResults.get(0)).getTag(), Objects.requireNonNull(realmResults.get(0)).getDescription(), Objects.requireNonNull(realmResults.get(0)).getSuffix(), Objects.requireNonNull(realmResults.get(0)).getLongitude(), Objects.requireNonNull(realmResults.get(0)).getLatitude());
                    } else {
                        attachmentTable = new AttachmentTable();
                    }
                },
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(attachmentTable);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("FindFirstUnSyncAttachmentRowQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
