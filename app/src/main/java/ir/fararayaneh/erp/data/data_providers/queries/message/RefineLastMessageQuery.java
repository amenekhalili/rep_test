package ir.fararayaneh.erp.data.data_providers.queries.message;


import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import io.realm.Sort;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.LastMessageTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.MessageTable;
import ir.fararayaneh.erp.utils.data_handler.AttachJsonCreator;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * when we come to chatList, update all last message and date
 */
public class RefineLastMessageQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);


        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    realm.where(LastMessageTable.class).findAll().deleteAllFromRealm();

                    RealmResults<ChatroomTable> chatroomTables = realm.where(ChatroomTable.class).findAll();
                    Stream.of(chatroomTables).forEach(chatroomTable -> {

                        RealmResults<MessageTable> messageTables = realm.where(MessageTable.class)
                                .beginGroup()
                                .equalTo(CommonColumnName.CHATROOM_ID, chatroomTable.getId())
                                .endGroup()
                                .beginGroup()
                                .notEqualTo(CommonColumnName.SYNCSTATE, CommonSyncState.ACCESS_DENIED)
                                .endGroup()
                                .beginGroup()
                                .notEqualTo(CommonColumnName.ACTIVITY_STATE, CommonActivityState.DELETE)
                                .endGroup()
                                .sort(CommonColumnName.SORT_DATE, Sort.DESCENDING)
                                .findAll();

                        if (messageTables.size() != 0) {
                            LastMessageTable lastMessageTable = realm.createObject(LastMessageTable.class, chatroomTable.getId());
                            lastMessageTable.setMessageDate(Objects.requireNonNull(messageTables.get(0)).getCreateDate());
                            lastMessageTable.setMessage(Objects.requireNonNull(messageTables.get(0)).getMessage());
                            lastMessageTable.setHaveFile(AttachJsonCreator.getAttachmentNameList(
                                    Objects.requireNonNull(messageTables.get(0)).getAttach()).size() == 0 ?
                                    CommonsDownloadFile.HAVE_NO_FILE :
                                    CommonsDownloadFile.HAVE_FILE);
                        } else {
                            LastMessageTable lastMessageTable = realm.createObject(LastMessageTable.class, chatroomTable.getId());
                            lastMessageTable.setMessageDate(Commons.MINIMUM_TIME);
                            lastMessageTable.setMessage(Commons.DASH);
                            lastMessageTable.setHaveFile(CommonsDownloadFile.HAVE_NO_FILE);
                        }
                    });
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
                    ThrowableLoggerHelper.logMyThrowable("RefineLastMessageQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}