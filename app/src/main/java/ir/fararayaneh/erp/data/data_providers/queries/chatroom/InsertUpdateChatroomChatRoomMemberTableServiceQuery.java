package ir.fararayaneh.erp.data.data_providers.queries.chatroom;

//todo eslahe hame queri haye class service va hazfe anha be samte queri haye asli

import android.util.Log;

import com.annimon.stream.Stream;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.ChatroomAddModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;


/**
 * اضافه کردن چت روم
 * SYNCCHATROOMADD:
 */
public class InsertUpdateChatroomChatRoomMemberTableServiceQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        ChatroomAddModel chatroomAddModel = (ChatroomAddModel) iModels;

        return Single.create(emitter -> {
            realm.beginTransaction();
            ChatroomTable chatroomTable = new ChatroomTable(chatroomAddModel.getId(),chatroomAddModel.getB5HCChatTypeId(),chatroomAddModel.getName(),chatroomAddModel.getDescription(),chatroomAddModel.getSyncState(),chatroomAddModel.getActivityState(),chatroomAddModel.getOldValue(),chatroomAddModel.getAttach(),chatroomAddModel.getGuid(),chatroomAddModel.getInsertDate());
            realm.insertOrUpdate(chatroomTable);
            for (int i = 0; i <chatroomAddModel.getChatroomMemberTableList().size() ; i++) {
                realm.insertOrUpdate(chatroomAddModel.getChatroomMemberTableList().get(i));
            }
            realm.commitTransaction();
            Log.i("arash", "InsertUpdateChatroomChatRoomMemberTableServiceQuery: size" + realm.where(ChatroomTable.class).findAll().size());
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}
