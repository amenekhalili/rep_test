package ir.fararayaneh.erp.data.data_providers.queries.chatroom_member;

import android.util.Log;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.ChatroomMemberAddModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;

//todo eslahe hame queri haye class service va hazfe anha be samte queri haye asli


public class InsertUpdateChatroomMemberAddTableServiceQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        ChatroomMemberAddModel chatroomMemberAddModel = (ChatroomMemberAddModel) iModels;

        return Single.create(emitter -> {

            ChatroomMemberTable chatroomMemberTable = new ChatroomMemberTable(chatroomMemberAddModel.getId(),chatroomMemberAddModel.getUserId(),chatroomMemberAddModel.getName(),chatroomMemberAddModel.getB5HCUserTypeId(),chatroomMemberAddModel.getChatroomId(),chatroomMemberAddModel.getChatroomGUID(),chatroomMemberAddModel.getActivityKind(),chatroomMemberAddModel.getSyncState(),chatroomMemberAddModel.getActivityState(),chatroomMemberAddModel.getAttach(),chatroomMemberAddModel.getGuid(),chatroomMemberAddModel.getIsMute(),chatroomMemberAddModel.getIsOnline(),chatroomMemberAddModel.getIsTyping(),chatroomMemberAddModel.getUnSeenCount());

            realm.beginTransaction();
            realm.insertOrUpdate(chatroomMemberTable);

            RealmResults<ChatroomTable> chatroomTables =
                    realm.where(ChatroomTable.class)
                    .equalTo(CommonColumnName.ID,chatroomMemberAddModel.getChatroomTableList().get(0).getId())
                    .findAll();
            //only if we have not that chatRoom, add it;
            if(chatroomTables.size()==0){
                realm.insertOrUpdate(chatroomMemberAddModel.getChatroomTableList().get(0));
            }

            realm.commitTransaction();
            Log.i("arash", "InsertUpdateChatroomMemberAddTableServiceQuery: size" + realm.where(ChatroomMemberTable.class).findAll().size());
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}
