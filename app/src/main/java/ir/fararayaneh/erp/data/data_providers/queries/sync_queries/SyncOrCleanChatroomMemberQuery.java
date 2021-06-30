package ir.fararayaneh.erp.data.data_providers.queries.sync_queries;

import com.annimon.stream.Stream;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

public class SyncOrCleanChatroomMemberQuery extends BaseSyncQuery {

    @Override
    protected void getAllUnSyncRowAndSendOrDelete(Realm realm) {
        RealmResults<ChatroomMemberTable> realmResults =
                realm.where(ChatroomMemberTable.class)
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                        .or()
                        .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.ON_SYNC)
                        .findAll();
        //-------------------------------------------------------------------
        if(realmResults.size()!=0){
            Stream.of(realmResults).forEach(chatroomMemberTable -> {
                /*
                اگر رکورد ما فاقد چت روم آی دی است
                مربوط به شرایط ایجاد چت روم است
                و با کایند
                 SYNCCHATROOMADD
                 ارسال میگردد و نباید از اینجا ارسال شود
                 */
                if(!chatroomMemberTable.getChatroomId().equals(Commons.NULL_INTEGER)){
                    haveUnSyncData=true;

                    if(chatroomMemberTable.getActivityState().equals(CommonActivityState.ADD)){
                        //send SYNCCHATROOMMEMBERADD (add member if chatroom is synced)

                        RealmResults<ChatroomTable> chatroomTables = realm.where(ChatroomTable.class)
                                .equalTo(CommonColumnName.CHATROOM_ID,chatroomMemberTable.getChatroomId())
                                .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.SYNCED)
                                .findAll();
                        if(chatroomTables.size()!=0){
                            ArrayList<ChatroomTable> chatroomTableList =new ArrayList<>(1);
                            chatroomTableList.add(chatroomTables.get(0));
                            SendPacket.sendEncryptionMessage(App.getAppContext(),
                                    SocketJsonMaker.chatroomMemberAddSocket(
                                            SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                            SharedPreferenceProvider.getUserId(App.getAppContext())
                                            ,new ChatroomMemberTable(
                                                    chatroomMemberTable.getId(),
                                                    chatroomMemberTable.getUserId(),
                                                    chatroomMemberTable.getName(),
                                                    chatroomMemberTable.getB5HCUserTypeId(),
                                                    chatroomMemberTable.getChatroomId(),
                                                    chatroomMemberTable.getChatroomGUID(),
                                                    chatroomMemberTable.getActivityKind(),
                                                    chatroomMemberTable.getSyncState(),
                                                    chatroomMemberTable.getActivityState(),
                                                    chatroomMemberTable.getAttach(),
                                                    chatroomMemberTable.getGuid(),
                                                    chatroomMemberTable.getIsMute(),
                                                    chatroomMemberTable.getIsOnline(),
                                                    chatroomMemberTable.getIsTyping(),
                                                    chatroomMemberTable.getUnSeenCount())
                                    , chatroomTableList), true);
                        }
                    } else {
                        //send SYNCCHATROOMMEMBER (edit member or delete member)
                        SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker
                                .chatroomMemberSocket(
                                        SharedPreferenceProvider.getOrganization(App.getAppContext()),
                                        SharedPreferenceProvider.getUserId(App.getAppContext()),
                                        new ChatroomMemberTable(
                                                chatroomMemberTable.getId(),
                                                chatroomMemberTable.getUserId(),
                                                chatroomMemberTable.getName(),
                                                chatroomMemberTable.getB5HCUserTypeId(),
                                                chatroomMemberTable.getChatroomId(),
                                                chatroomMemberTable.getChatroomGUID(),
                                                chatroomMemberTable.getActivityKind(),
                                                chatroomMemberTable.getSyncState(),
                                                chatroomMemberTable.getActivityState(),
                                                chatroomMemberTable.getAttach(),
                                                chatroomMemberTable.getGuid(),
                                                chatroomMemberTable.getIsMute(),
                                                chatroomMemberTable.getIsOnline(),
                                                chatroomMemberTable.getIsTyping(),
                                                chatroomMemberTable.getUnSeenCount())
                                ), true);
                    }

                }
            });

        }
    }
}
