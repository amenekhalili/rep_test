package ir.fararayaneh.erp.data.data_providers.queries.message;

import android.util.Log;

import java.util.Date;
import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.MessageEditModel;
import ir.fararayaneh.erp.data.models.tables.LastMessageTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.MessageTable;
import ir.fararayaneh.erp.utils.CalculationHelper;
import ir.fararayaneh.erp.utils.data_handler.AttachJsonCreator;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;

//todo eslahe hame queri haye class service va hazfe anha be samte queri haye asli

/**
 * هر رکوردی که به جدول مسیج اضافه میشود
 * مطابق بیزینس نوشته شده در جدول چت روم
 * جدول لست مسیج نیز اصلاح میشود
 * <p>
 * ضمنا اگر مسیج از طرف خود ما نیست و مربوط به چت روم آنلاین هم نیست
 * به تعداد نخوانده های ما از ان چت روم یکی اضافه میشود و بج هم یکی اضافه میشود
 *
 *

 * تغییرات لست مسیج و چت ممبر در ترنزکشن های جدا گانه انجام میشود تا در تغییرات UI  یکی بعد از دیگری ناشی از نمایش آخرین پیام و نمایش نخوانده ها
 * ضمنا در
 دریافت مسیج اولد
 فقط در چت روم تیبل مسیج ریخته میشود و کار دیگری انجام نمیشود

 ضمنا اگر مسیج اولد نیست و یوزر مسیج خودمان نیستیم آن یوزر در همه چت روم ها آنلاین شود
 */

public class InsertUpdateMessageTableServiceQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        MessageTable messageTable = (MessageTable) iModels;

        return Single.create(emitter -> {

            String myUserId = SharedPreferenceProvider.getUserId(App.getAppContext());
            //--------------------------------------------------------------------------------------------------------------
            //check we have that chatRoom and is active
            RealmResults<ChatroomTable> chatroomTableRealmResults =
                    realm.where(ChatroomTable.class).equalTo(CommonColumnName.ID, messageTable.getChatroomId()).findAll();
            if (chatroomTableRealmResults.size() != 0) {
                if (!Objects.requireNonNull(chatroomTableRealmResults.get(0)).getSyncState().equals(CommonSyncState.ACCESS_DENIED) &&
                        !Objects.requireNonNull(chatroomTableRealmResults.get(0)).getActivityState().equals(CommonActivityState.DELETE)) {
            //--------------------------------------------------------------------------------------------------------------
                    realm.beginTransaction();
                    //add to msg tbl
                    realm.insertOrUpdate(messageTable);
            //--------------------------------------------------------------------------------------------------------------
                    if(messageTable.getIsOld().equals(Commons.IS_NOT_OLD_MESSAGE)){
                    if (!messageTable.getActivityState().equals(CommonActivityState.DELETE)
                            && (
                            !messageTable.getSyncState().equals(CommonSyncState.ACCESS_DENIED)
                                    &&
                                    !messageTable.getSyncState().equals(CommonSyncState.SYNC_ERROR))) {

            //--------------------------------------------------------------------------------------------------------------
                        //update last msg if message is correct, other wise remove last
                        // msg for that device (because message may be were deleted and so on )
                        LastMessageTable lastMessageTable = new LastMessageTable(
                                Objects.requireNonNull(messageTable).getChatroomId(),
                                messageTable.getCreateDate(),
                                messageTable.getMessage(),
                                AttachJsonCreator.getAttachmentNameList(messageTable.getAttach()).size() == 0 ?
                                        CommonsDownloadFile.HAVE_NO_FILE :
                                        CommonsDownloadFile.HAVE_FILE);
                        realm.insertOrUpdate(lastMessageTable);
            //--------------------------------------------------------------------------------------------------------------
                        //update unReed count and badges if sender is not me and message is not old
                        if (!messageTable.getUserId().equals(myUserId) &&
                                !messageTable.getChatroomId().equals(SharedPreferenceProvider.getOnlineChatroom(App.getAppContext()))) {

                            RealmResults<ChatroomMemberTable> memberTables = realm.where(ChatroomMemberTable.class)
                                    .equalTo(CommonColumnName.CHATROOM_ID, messageTable.getChatroomId())
                                    .equalTo(CommonColumnName.USER_ID, myUserId)//should find my user id
                                    .findAll();
                            if (memberTables.size() != 0) {
                                Objects.requireNonNull(memberTables.get(0)).setUnSeenCount(CalculationHelper.numberValidation(Objects.requireNonNull(memberTables.get(0)).getUnSeenCount())?String.valueOf(Integer.valueOf(Objects.requireNonNull(memberTables.get(0)).getUnSeenCount())+1):Commons.ZERO_STRING);
                                SharedPreferenceProvider.setBadgeNumber(App.getAppContext(), 1, 0);
                            }

                        }
            //--------------------------------------------------------------------------------------------------------------
                        //send packet editMessage for correction in oracle
                        if(messageTable.getChatroomId().equals(SharedPreferenceProvider.getOnlineChatroom(App.getAppContext())) && !messageTable.getUserId().equals(myUserId) && messageTable.getIsOld().equals(Commons.IS_NOT_OLD_MESSAGE)){
                            SendPacket.sendEncryptionMessage(App.getAppContext(), SocketJsonMaker.messageEditSocket(SharedPreferenceProvider.getOrganization(App.getAppContext()), SharedPreferenceProvider.getUserId(App.getAppContext()), messageTable.getChatroomId(), new MessageEditModel(chatroomTableRealmResults.get(0).getGuid())),true );
                        }
            //--------------------------------------------------------------------------------------------------------------
                       //make user online if is not me
                        if(!messageTable.getUserId().equals(myUserId)){
                            RealmResults<ChatroomMemberTable> chatroomMemberTables = realm.where(ChatroomMemberTable.class)
                                    .equalTo(CommonColumnName.USER_ID, myUserId).findAll();
                            for (ChatroomMemberTable chatroomMemberTable : chatroomMemberTables) {
                                chatroomMemberTable.setIsOnline(Commons.IS_USER_ONLINE);
                            }
                        }
            //--------------------------------------------------------------------------------------------------------------
                        //change chatRoom date for listener in chatList (should be last step, of course we are in transaction and no need to ... but ...)
                        Objects.requireNonNull(chatroomTableRealmResults.get(0)).setSortDate(new Date().getTime());

                    } else {
                        realm.where(LastMessageTable.class)
                                .equalTo(CommonColumnName.CHATROOM_ID, messageTable.getChatroomId())
                                .findAll().deleteAllFromRealm();
                    }}

            //--------------------------------------------------------------------------------------------------------------
                    realm.commitTransaction();
            //--------------------------------------------------------------------------------------------------------------
                }
            }

            Log.i("arash", "InsertUpdateMessageTableServiceQuery: size" + realm.where(MessageTable.class).findAll().size());
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }

}