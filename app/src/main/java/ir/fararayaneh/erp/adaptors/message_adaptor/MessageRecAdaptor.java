package ir.fararayaneh.erp.adaptors.message_adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;
import java.util.Objects;

import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;
import ir.fararayaneh.erp.IBase.common_base.BaseDBRealmRecycleAdaptor;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonChatroomTypeCode;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonMessageLayoutType;
import ir.fararayaneh.erp.commons.CommonMessageSendType;
import ir.fararayaneh.erp.commons.CommonMessageTypeCode;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.CommonTimeZone;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.MessageTable;
import ir.fararayaneh.erp.utils.data_handler.AttachJsonCreator;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;

public class MessageRecAdaptor extends BaseDBRealmRecycleAdaptor implements IClickRowMessageRecHolderListener {

    private Context context;
    private IClickMessageRecAdaptor clickMessageRecAdaptor;
    private String chatRoomId,chatRoomType,myUserId;
    private OrderedRealmCollection<ChatroomTable> chatroomTables;
    private OrderedRealmCollection<MessageTable> messageTables;
    private OrderedRealmCollection<UtilCodeTable> utilCodeTables;
    private OrderedRealmCollection<ChatroomMemberTable> chatroomMemberTables;

    public void setClickMessageRecAdaptor(IClickMessageRecAdaptor clickMessageRecAdaptor) {
        this.clickMessageRecAdaptor = clickMessageRecAdaptor;
    }

    public MessageRecAdaptor(boolean autoUpdate, Context context, @Nullable OrderedRealmCollection[] data, String chatRoomId, String chatRoomType) {
        super(autoUpdate, data);
        this.context = context;
        this.chatRoomId = chatRoomId;
        this.chatRoomType = chatRoomType;
        myUserId= SharedPreferenceProvider.getUserId(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case CommonMessageLayoutType.SENDED_MSG :
                return new MessageRecHolder(LayoutInflater.from(context).inflate(R.layout.row_chat_send, parent, false), this);
            case CommonMessageLayoutType.RECEIVED_GROUP_MSG :
                return new MessageRecHolder(LayoutInflater.from(context).inflate(R.layout.row_chat_received, parent, false), this);
            case CommonMessageLayoutType.RECEIVED_PRIVATE_CHANNEL_MSG :
                return new MessageRecHolder(LayoutInflater.from(context).inflate(R.layout.row_chat_received_private_channel, parent, false), this);
            default:
                return new MessageRecHolder(LayoutInflater.from(context).inflate(R.layout.row_chat_global, parent, false), this);

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (Objects.requireNonNull(utilCodeTables.where().equalTo(CommonColumnName.ID,
                messageTables.get(position).getB5HCMessageTypeId()).findAll().get(0)).getCode().equals(CommonMessageTypeCode.SMSG)){
                return CommonMessageLayoutType.RECEIVED_CLOBAL_MSG;
        } else {
            if(messageTables.get(position).getUserId().equals(myUserId)){
                return CommonMessageLayoutType.SENDED_MSG;
            } else {
                if(chatRoomType.equals(CommonChatroomTypeCode.CHN) || chatRoomType.equals(CommonChatroomTypeCode.PRV)){
                    return CommonMessageLayoutType.RECEIVED_PRIVATE_CHANNEL_MSG;
                } else {
                    return CommonMessageLayoutType.RECEIVED_GROUP_MSG;
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // if (getItem(0, viewHolder.getAdapterPosition()) != null) { // no need to check, in this case , itemCount is zero , and onbind not be called
            MessageRecHolder messageRecHolder = (MessageRecHolder) holder;
            MessageTable messageTable = messageTables.get(messageRecHolder.getAdapterPosition());
            RealmResults<ChatroomMemberTable> chatroomMemberTablesResulet =
                    chatroomMemberTables.where()
                            .equalTo(CommonColumnName.USER_ID,messageTable.getUserId())//no need to check chatroom id, all are for this chatroom
                            .findAll();

            if(chatroomMemberTablesResulet.size()!=0){
                if (Objects.requireNonNull(utilCodeTables.where().equalTo(CommonColumnName.ID,
                        messageTable.getB5HCMessageTypeId()).findAll().get(0)).getCode().equals(CommonMessageTypeCode.SMSG)){
                    bindGlobalMsg(messageRecHolder,messageTable);
                } else {
                    if(messageTables.get(messageRecHolder.getAdapterPosition()).getUserId().equals(myUserId)){
                        bindSendMsg(messageRecHolder,messageTable);
                    } else {
                        if(chatRoomType.equals(CommonChatroomTypeCode.CHN) || chatRoomType.equals(CommonChatroomTypeCode.PRV)){
                            bindChannelPrivateReceivedMsg(messageRecHolder,messageTable);
                        } else {
                            bindGroupReceivedMsg(messageRecHolder,messageTable, Objects.requireNonNull(chatroomMemberTablesResulet.get(0)));
                        }
                    }
                }

            }




        //}
    }

    private void bindGlobalMsg(MessageRecHolder messageRecHolder, MessageTable messageTable) {
        messageRecHolder.setRowDataGlobal(messageTable.getMessage());
    }

    private void bindSendMsg(MessageRecHolder messageRecHolder, MessageTable messageTable) {
        messageRecHolder.setRowDataSend(messageTable.getMessage(),
                AttachJsonCreator.getFirstAttachName(messageTable.getAttach()),
                CustomTimeHelper.getPresentableMessageDate(
                        CustomTimeHelper.stringDateToMyLocalDateConvertor(messageTable.getCreateDate(),
                                CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH,
                                CommonTimeZone.SERVER_TIME_ZONE),context)
                ,messageSendTypeHandler(messageTable));
    }

    private void bindChannelPrivateReceivedMsg(MessageRecHolder messageRecHolder,MessageTable messageTable) {
        messageRecHolder.setRowDataPrivateChannelReceived(messageTable.getMessage(),
                AttachJsonCreator.getFirstAttachName(messageTable.getAttach()),
                CustomTimeHelper.getPresentableMessageDate(
                        CustomTimeHelper.stringDateToMyLocalDateConvertor(messageTable.getCreateDate(),
                                CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH,
                                CommonTimeZone.SERVER_TIME_ZONE),context));
    }

    private void bindGroupReceivedMsg(MessageRecHolder messageRecHolder,MessageTable messageTable,ChatroomMemberTable chatroomMemberTable) {
        messageRecHolder.setRowDataGroupReceived(messageTable.getMessage(),
                AttachJsonCreator.getFirstAttachName(messageTable.getAttach()),
                CustomTimeHelper.getPresentableMessageDate(
                        CustomTimeHelper.stringDateToMyLocalDateConvertor(messageTable.getCreateDate(),
                                CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH,
                                CommonTimeZone.SERVER_TIME_ZONE),context),
                chatroomMemberTable.getIsOnline().equals(Commons.IS_USER_ONLINE),
                AttachJsonCreator.getFirstAttachName(chatroomMemberTable.getAttach()),
                chatRoomType.equals(CommonChatroomTypeCode.PRV),
                chatroomMemberTable.getName().substring(0,1));
    }

    @Override
    protected void customNotifyItemRangeEdited(int startPosition, int length) {
        //no need any work
    }

    @Override
    protected void customNotifyItemRangeInserted(int startPosition, int length) {
        //no need any work
    }

    @Override
    protected void customNotifyUserForNewRow(int position) {
        if(clickMessageRecAdaptor!=null){
            clickMessageRecAdaptor.receivedNewMessage(messageTables.get(position));
        }
    }

    @Override
    protected void customNotifyUserForChangeRow(int position) {
        //no need any work for edit or delete
    }

    @Override
    protected void injectNewDataCollectionWereDone() {
        //این فانکشن فقط یکبار صدا زده میشود و آپدیت های بعدی از طریق لیستنرهای دیتابیس انجام میشود
        messageTables = (OrderedRealmCollection<MessageTable>) getData(0);//all message except deleted message
        chatroomTables = (OrderedRealmCollection<ChatroomTable>) getData(1);//one record
        chatroomMemberTables = (OrderedRealmCollection<ChatroomMemberTable>) getData(2);//all user (all active and deActive)
        utilCodeTables = (OrderedRealmCollection<UtilCodeTable>) getData(3);
    }


    @Override
    public void clickAttachRow(int position) {
        if (clickMessageRecAdaptor != null) {
            clickMessageRecAdaptor.clickAttachRow(messageTables.get(position));
        }
    }

    @Override
    public void clickAvatarRow(int position) {
        if (clickMessageRecAdaptor != null) {
            //no need to check chatRoom, all row is for current chatRoom
            clickMessageRecAdaptor.clickAvatarRow(chatroomMemberTables.where().equalTo(CommonColumnName.USER_ID,messageTables.get(position).getUserId()).findFirst());
        }
    }

    @Override
    public void clickMessage(int position) {
            //scroll to reply if we have one
        if(!messageTables.get(position).getG5MessageId().equals(Commons.NULL_INTEGER)){
            RealmResults<MessageTable> realmResults = messageTables
                    .where()
                    .equalTo(CommonColumnName.ID,messageTables.get(position).getG5MessageId()).findAll();
            if(realmResults.size()!=0 && clickMessageRecAdaptor != null){
                clickMessageRecAdaptor.workForScroolToPosition(messageTables.indexOf(realmResults.get(0)));
            }
        }
    }

    @Override
    protected void notifyNonMainItemAnyChanged(Object collection, int positionOfChangedTable, int startPosition, int length) {
        switch (positionOfChangedTable) {
            case 1:
                notifyByChatRoom(collection, startPosition, length);
                break;
            case 2:
                notifyByChatMember(collection,startPosition,length);
                break;
            case 3:
                notifyByUtilCode(collection,startPosition,length);
                break;
        }
    }

//---------------------------------------------------------------------------------------------

    /**
     * برای تعیین تیک آبی یا تیک یکی یا غیره
     */
    private byte messageSendTypeHandler(MessageTable messageTable) {
        if(messageTable.getSyncState().equals(CommonSyncState.SYNC_ERROR)){
            return CommonMessageSendType.ERROR_MSG;
       // } else if(!messageTable.getSeenDate().equals(Commons.MINIMUM_TIME)){ old manner for seen
        } else if(!messageTable.getSeenCount().equals(Commons.ZERO_STRING)){ //new manner for seen
            return CommonMessageSendType.SEEN_MSG;
        } else if(messageTable.getSyncState().equals(CommonSyncState.SYNCED)){
            return CommonMessageSendType.RECEIVED_MSG;
        } else {
            return CommonMessageSendType.WAIT_MSG;
        }
    }



    /**
     * chatRoom edit   :set image and name of chatRoom again (در پرایوت امکان رخ دادن ندارد)
     * chatRoom delete :exit from chatRoom
     */
    private void notifyByChatRoom(Object collection, int startPosition, int length) {
        //delete
        if(chatroomTables.get(0).getActivityState().equals(CommonActivityState.DELETE)){
            workForDeleteChatRoom();

        } else if (chatroomTables.get(0).getActivityState().equals(CommonActivityState.UPDATE)){
            workForEditChatRoomNameAndImage(chatroomTables.get(0).getName(),AttachJsonCreator.getFirstAttachName(chatroomTables.get(0).getAttach()));
        }
    }

    /**
     * member add : no need any work (get proper message)
     * member delete : no need any work(if i be deleted,works were be done in chatRoom delete)(به بیزینس چت روم توجه شود)
     * member edit : if chatRoom is private only change toolbar, else do not any work (online offline user in group were not be changed until scroll ... do not worry about it)
     */
    private void notifyByChatMember(Object collection, int startPosition, int length) {
        if(chatRoomType.equals(CommonChatroomTypeCode.PRV)){
            //we have only 2 member in chatRoom private
            workForChangIsIsOnlineIsTypingToolbar(chatroomMemberTables.where()
                    .notEqualTo(CommonColumnName.USER_ID,myUserId).findFirst().getIsOnline().equals(Commons.IS_USER_ONLINE),chatroomMemberTables.where()
                    .notEqualTo(CommonColumnName.USER_ID,myUserId).findFirst().getIsTyping().equals(Commons.IS_USER_TYPING));
        }
    }

    private void notifyByUtilCode(Object collection, int startPosition, int length) {
        //no need any work, my util code changed in a slow manner
    }

    private void workForDeleteChatRoom() {
        if(clickMessageRecAdaptor!=null){
            clickMessageRecAdaptor.workForchatroomDelete();
        }
    }

    private void workForEditChatRoomNameAndImage(String name ,String fileName) {
        if(clickMessageRecAdaptor!=null){
            clickMessageRecAdaptor.workForEditChatRoomNameAndImage(name,fileName);
        }
    }

    private void workForChangIsIsOnlineIsTypingToolbar(boolean isOnline,boolean isTyping) {
        if(clickMessageRecAdaptor!=null){
            clickMessageRecAdaptor.workForChangesIsOnlineIsTypingToolbar(isOnline,isTyping);
        }
    }

    public void sendUserIsTypingPacket() {
        //no need to check chatRoom id, all are for this chatRoom id
        ChatroomMemberTable chatroomMemberTable =chatroomMemberTables.where()
                .equalTo(CommonColumnName.USER_ID,myUserId).findFirst();
        SendPacket.sendEncryptionMessage(context,
                SocketJsonMaker.chatroomMemberSocket(
                        SharedPreferenceProvider.getOrganization(context),myUserId,
                        new ChatroomMemberTable(Objects.requireNonNull(chatroomMemberTable).getId()
                                ,chatroomMemberTable.getUserId(),chatroomMemberTable.getName(),
                                chatroomMemberTable.getB5HCUserTypeId(),chatroomMemberTable.getChatroomId(),
                                chatroomMemberTable.getChatroomGUID(),chatroomMemberTable.getActivityKind(),
                                chatroomMemberTable.getSyncState(),chatroomMemberTable.getActivityState(),//اکتیویتی استیت برای تایپ کردن اهمیت ندارد
                                chatroomMemberTable.getAttach(),chatroomMemberTable.getGuid(),
                                chatroomMemberTable.getIsMute(),chatroomMemberTable.getIsOnline(),
                                Commons.IS_USER_TYPING,chatroomMemberTable.getUnSeenCount())), false);

    }

    /**
     * وقتی یوزر کیبورد را میبندد باید انلاین بودن وی نمایش داده شود
     */
    public void sendUserIsOnlinePacket() {


        //no need to check chatRoom id, all are for this chatRoom id
        ChatroomMemberTable chatroomMemberTable =chatroomMemberTables.where()
                .equalTo(CommonColumnName.USER_ID,myUserId).findFirst();
//todo با توجه به تغییر بیزینس آنلاین بودن .... این موضوع ارسال پگت زیر مجدد با توجه به بیزنس بازنگری شود
/*
        SendPacket.sendEncryptionMessage(context, SocketJsonMaker.chatroomMemberSocket(SharedPreferenceProvider.getOrganization(context),myUserId,
                new ChatroomMemberTable(Objects.requireNonNull(chatroomMemberTable).getId()
                        ,chatroomMemberTable.getUserId(),chatroomMemberTable.getName(),
                        chatroomMemberTable.getB5HCUserTypeId(),chatroomMemberTable.getChatroomId(),
                        chatroomMemberTable.getChatroomGUID(),chatroomMemberTable.getActivityKind(),
                        chatroomMemberTable.getSyncState(),chatroomMemberTable.getActivityState(),
                        chatroomMemberTable.getAttach(),chatroomMemberTable.getGuid(),
                        chatroomMemberTable.getIsMute(),Commons.IS_USER_ONLINE,
                        chatroomMemberTable.getIsTyping(),chatroomMemberTable.getUnSeenCount())
        ), false);
        */
    }

    public String getFirstMessageId(){
           return messageTables!=null? messageTables.get(0).getId():Commons.NULL_INTEGER;
    }


}
