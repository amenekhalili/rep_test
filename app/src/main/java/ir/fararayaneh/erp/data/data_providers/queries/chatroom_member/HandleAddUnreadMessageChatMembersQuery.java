package ir.fararayaneh.erp.data.data_providers.queries.chatroom_member;


import java.util.Objects;
import java.util.stream.Stream;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.utils.CalculationHelper;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * we get userId and chatRoomId and unreadCount to be added (userId,chatroomId,myString from globalModel)
 *
 * if calculation value is negative, make it zero
 *
 * هم برای اضافه کردن و هم کم کردن سراغ این کویری می آییم
 *
 * در کویری
 * InsertUpdateMessageTableServiceQuery
 * InsertUpdateMessageEditTableServiceQuery
 * هم مقدار نخوانده ها تغییر میدهیم
 */
public class HandleAddUnreadMessageChatMembersQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);


        GlobalModel globalModel = (GlobalModel) iModels;


        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    RealmResults<ChatroomMemberTable> realmResults = realm.where(ChatroomMemberTable.class)
                            .equalTo(CommonColumnName.USER_ID,globalModel.getUserId())
                            .equalTo(CommonColumnName.CHATROOM_ID,globalModel.getChatroomId())
                            .findAll();
                    if(realmResults.size()!=0){

                        int calclation =Integer.valueOf(Objects.requireNonNull(realmResults.get(0)).getUnSeenCount())+Integer.valueOf(globalModel.getMyString());
                        Objects.requireNonNull(realmResults.get(0)).setUnSeenCount(String.valueOf(calclation<0?Commons.ZERO_INT:calclation));

                        //be in dalil ke ba taghire nakhnde ha, chatlist ham dar UI eslah shavad,
                        //ama nabayad mahale radif taghir konad, chatlist ra ba sortDate mojod por mikonim
                        //dar vaghe faghat ui ra notif mikonim
                        RealmResults<ChatroomTable> chatroomTables=realm.where(ChatroomTable.class)
                                .equalTo(CommonColumnName.ID, Objects.requireNonNull(realmResults.get(0)).getChatroomId())
                                .findAll();
                        if(chatroomTables.size()!=0){
                            Objects.requireNonNull(chatroomTables.get(0)).setSortDate(Objects.requireNonNull(chatroomTables.get(0)).getSortDate());
                        }
                    }
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
                    ThrowableLoggerHelper.logMyThrowable("HandleAddUnreadMessageChatMembersQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}