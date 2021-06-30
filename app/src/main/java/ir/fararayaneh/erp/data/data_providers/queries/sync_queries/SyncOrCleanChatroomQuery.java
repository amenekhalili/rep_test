package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanChatroomQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<ChatroomTable> realmResults =
                realm.where(ChatroomTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if (realmResults.size() != 0) {
            haveUnSyncData = true;

            ArrayList<ChatroomMemberTable> userArray = new ArrayList<>();//for SYNCCHATROOMADD if needed

            Stream.of(realmResults).forEach(chatroomTable -> {

                if (chatroomTable.getActivityState().equals(CommonActivityState.ADD)) {

                    //we have new chatRoom
                    //(لازم نیست وضعیت سینک ممبر ها بررسی شود چون وقتی چت روم سینک نشده است حتما ممبر های انها هم سینک نشده است )
                    RealmResults<ChatroomMemberTable> memberTableRealmResults =
                            realm.where(ChatroomMemberTable.class)
                                    .equalTo(CommonColumnName.CHATROOM_GUID, chatroomTable.getGuid()).findAll();

                    Stream.of(memberTableRealmResults).forEach(userArray::add);

                    SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                            .chatroomAddSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                    SharedPreferenceProvider.getUserId(App.getAppContext()),
                                    new ChatroomTable(chatroomTable.getId(), chatroomTable.getB5HCChatTypeId(),
                                            chatroomTable.getName(), chatroomTable.getDescription(),
                                            chatroomTable.getSyncState(), chatroomTable.getActivityState(),
                                            chatroomTable.getOldValue(), chatroomTable.getAttach(),
                                            chatroomTable.getGuid(), chatroomTable.getInsertDate()), userArray
                            ), true);
                } else {
                    //for chatroom edit or delete
                    SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                            .chatroomSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                    SharedPreferenceProvider.getUserId(App.getAppContext()),
                                    new ChatroomTable(chatroomTable.getId(), chatroomTable.getB5HCChatTypeId(),
                                            chatroomTable.getName(), chatroomTable.getDescription(),
                                            chatroomTable.getSyncState(), chatroomTable.getActivityState(),
                                            chatroomTable.getOldValue(), chatroomTable.getAttach(), chatroomTable.getGuid(),
                                            chatroomTable.getInsertDate())
                            ), true);
                }

            });

        }
    }
}
