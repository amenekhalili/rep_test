package ir.fararayaneh.erp.data.data_providers.queries.attach_query;

import android.util.Log;

import com.annimon.stream.Stream;

import java.util.ArrayList;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.attachment.AttachmentTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * for when we create list of attachments in my client before sync
 */
public class InsertAttachmentsFromClientQuery extends BaseQueries {
    private GlobalModel globalModel;
    private ArrayList<String> listFileNameForResult;
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        return Single.create(emitter -> realm.executeTransactionAsync(realm -> {
                    globalModel = (GlobalModel) iModels;
                    listFileNameForResult = globalModel.getStringArrayList();
                    AttachmentTable attachmentTable = new AttachmentTable();
                    Stream.of(listFileNameForResult).forEach(name -> {
                        //todo new nakardane model moshkeli ijad nemikonad ?
                        attachmentTable.setLatitude(globalModel.getMyString4());
                        attachmentTable.setLongitude(globalModel.getMyString5());
                        attachmentTable.setAttachmentGUID(MimeTypeHandler.getGUIDFromName(name));
                        attachmentTable.setSuffix(MimeTypeHandler.getFileSuffixFromName(name));
                        attachmentTable.setSyncState(CommonSyncState.BEFORE_SYNC);   //--> for all attachments in list is equal
                        attachmentTable.setTag(globalModel.getMyString2());          //--> for all attachments in list is equal
                        attachmentTable.setDescription(globalModel.getMyString3());  //--> for all attachments in list is equal
                        realm.insertOrUpdate(attachmentTable);
                    });
                    Log.i("arash", "datawwwwwwwwww: "+realm.where(AttachmentTable.class).findAll());
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
                    ThrowableLoggerHelper.logMyThrowable("InsertAttachmentsFromClientQuery***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}