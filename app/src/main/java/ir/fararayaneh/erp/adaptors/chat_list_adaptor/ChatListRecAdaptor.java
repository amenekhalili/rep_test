package ir.fararayaneh.erp.adaptors.chat_list_adaptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Locale;
import java.util.Objects;
import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.IBase.common_base.BaseDBRealmRecycleAdaptor;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonChatroomTypeCode;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonTimeZone;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.tables.LastMessageTable;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.utils.data_handler.AttachJsonCreator;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;

public class ChatListRecAdaptor extends BaseDBRealmRecycleAdaptor implements IClickRowChatListRecHolderListener {

    private Context context;
    private IClickChatListRecAdaptor clickChatListRecAdaptor;
    private OrderedRealmCollection<ChatroomTable> chatroomTables;
    private OrderedRealmCollection<LastMessageTable> lastMessageTables;
    private OrderedRealmCollection<UtilCodeTable> utilCodeTables;
    private OrderedRealmCollection<ChatroomMemberTable> chatroomMemberTables;//only have row for private chatroom

    public void setClickChatListRecAdaptor(IClickChatListRecAdaptor clickChatListRecAdaptor) {
        this.clickChatListRecAdaptor = clickChatListRecAdaptor;
    }

    public ChatListRecAdaptor(boolean autoUpdate, Context context, @Nullable OrderedRealmCollection[] data) {
        super(autoUpdate, data);
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ChatListRecHolder(LayoutInflater.from(context).inflate(R.layout.row_chatlist, viewGroup, false), this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
       // if (getItem(0, viewHolder.getAdapterPosition()) != null) { // no need to check, in this case , itemCount is zero , and onbind not be called
            //if first table is not all, all table is not null
            ChatListRecHolder chatListRecHolder = (ChatListRecHolder) viewHolder;
            setupBinding(chatListRecHolder, viewHolder.getAdapterPosition());
        //}
    }

    private void setupBinding(ChatListRecHolder chatListRecHolder, int adaptorPosition) {
        chatListRecHolder.setRowData(
                chatroomTables.get(adaptorPosition).getName(),
                handleLastMessage(adaptorPosition),
                handleMessageDate(adaptorPosition),
                handleUnreadCount(adaptorPosition),
                handleSyncState(adaptorPosition),
                handleShowingOnlineUser(adaptorPosition),
                handleOnlineUser(adaptorPosition),
                AttachJsonCreator.getFirstAttachName(chatroomTables.get(adaptorPosition).getAttach())
        );
    }

    private String handleUnreadCount(int adaptorPosition) {
        RealmResults<ChatroomMemberTable> chatroomMemberTableRealmResults =
                chatroomMemberTables.where()
                        .beginGroup()
                        .equalTo(CommonColumnName.CHATROOM_GUID, chatroomTables.get(adaptorPosition).getGuid())
                        .endGroup()
                        .beginGroup()
                        .equalTo(CommonColumnName.USER_ID, SharedPreferenceProvider.getUserId(context))
                        .endGroup()
                        .findAll();

        if (chatroomMemberTableRealmResults.size() != 0) {
            return Objects.requireNonNull(chatroomMemberTableRealmResults.get(0)).getUnSeenCount();
        } else {
            return Commons.NULL_INTEGER;
        }
    }

    //find online or offline for my friend user in private chatRoom
    // (فقط اعضای چت روم پرایوت به اضافه خودم در همه چت روم ها در اینجا هستند)
    private boolean handleOnlineUser(int adaptorPosition) {
        RealmResults<ChatroomMemberTable> chatroomMemberTableRealmResults =
                chatroomMemberTables.where()
                        .equalTo(CommonColumnName.CHATROOM_GUID, chatroomTables.get(adaptorPosition)
                                .getGuid())
                        .notEqualTo(CommonColumnName.USER_ID, SharedPreferenceProvider.getUserId(App.getAppContext()))
                        .findAll();
        if (chatroomMemberTableRealmResults.size() != 0) {
            return Objects.requireNonNull(chatroomMemberTableRealmResults.get(0)).getIsOnline().equals(Commons.IS_USER_ONLINE);
        } else {
            return false;
        }

    }

    private String handleSyncState(int adaptorPosition) {
        return chatroomTables.get(adaptorPosition).getSyncState();
    }

    private String handleMessageDate(int adaptorPosition) {
        //در صورتی که چت روم فاقد آی دی باشد رفتار دیفالت انجام میشود
        String date = Commons.MINIMUM_TIME;
        RealmResults<LastMessageTable> lastMessageTable = lastMessageTables.where()
                .equalTo(CommonColumnName.CHATROOM_ID, chatroomTables.get(adaptorPosition)
                        .getId()).findAll();
        if (lastMessageTable.size() != 0) {
            if(!Objects.requireNonNull(lastMessageTable.get(0)).getMessageDate().equals(date)){
                return CustomTimeHelper.getPresentableChatDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(lastMessageTable.get(0).getMessageDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), context);
            }
            return date;
        } else {
            return date;
        }
    }

    private String handleLastMessage(int adaptorPosition) {
        //در صورتی که چت روم فاقد آی دی باشد رفتار دیفالت انجام میشود

        RealmResults<LastMessageTable> lastMessageTable = lastMessageTables.where()
                .equalTo(CommonColumnName.CHATROOM_ID, chatroomTables.get(adaptorPosition)
                        .getId()).findAll();
        if (lastMessageTable.size() != 0) {
            if ((Objects.requireNonNull(lastMessageTable.get(0)).getMessage().equals(Commons.SPACE) ||
                    Objects.requireNonNull(lastMessageTable.get(0)).getMessage().toLowerCase().equals(Commons.NULL)) &&
                    Objects.requireNonNull(lastMessageTable.get(0)).getHaveFile().equals(CommonsDownloadFile.HAVE_FILE)) {
                return context.getString(R.string.file);
            } else {
                return Objects.requireNonNull(lastMessageTable.get(0)).getMessage();
            }
        } else {
            return Commons.SPACE;
        }
    }

    /**
     * only if my chatRoom is private, should show online img
     */
    private boolean handleShowingOnlineUser(int adaptorPosition) {

        RealmResults<UtilCodeTable> utilCodeTableRealmResults = utilCodeTables.where()
                .equalTo(CommonColumnName.ID, chatroomTables.get(adaptorPosition).getB5HCChatTypeId())
                .findAll();
        if(utilCodeTableRealmResults.size()!=0){
            return Objects.requireNonNull(utilCodeTableRealmResults.get(0)).getCode().equals(CommonChatroomTypeCode.PRV);
        }else {
            showPermanentError("error in util code-1");
        }

        return false;
    }

    @Override
    public void clickAttachRow(int position) {
        if (clickChatListRecAdaptor != null) {
            clickChatListRecAdaptor.clickAttachRow(chatroomTables.get(position));
        }
    }

    @Override
    public void clickGoChatRoomRow(int position) {
        RealmResults<UtilCodeTable> utilCodeTableRealmResults =
                utilCodeTables.where().equalTo(CommonColumnName.ID, chatroomTables.get(position).getB5HCChatTypeId()).findAll();
        if(utilCodeTableRealmResults.size()!=0 && clickChatListRecAdaptor != null){
                clickChatListRecAdaptor.clickGoChatRoomRow(chatroomTables.get(position), Objects.requireNonNull(utilCodeTableRealmResults.get(0)).getCode());
        } else {
            showPermanentError("error in util code-2");
        }
    }


    private void showPermanentError(String error){
       ToastMessage.show(context, error);
    }

//------------------------------------------------------------------------------------------------------

    /**
     * به این دلیل که ویرایش چت لیست نیاز به تغییر UI ویو ندارد و با تغییر نخوانده ها یا آخرین پیام به روز میشود و ... هندل میشود لذا در اینجا از
     * insertedRowIds
     * editedRowIds
     * و در نتیجه
     * setupRowReportViewType()
     * استفاده نمیشود
     * ضمنا تغییرات مورد نیاز در بیس انجام شده است و برای تغییر در جدول اصلی نیازی به انجام کاری نیست
     */
    @Override
    protected void customNotifyItemRangeEdited(int startPosition, int length) {
        //no need any work for main table,notify were done in base
    }

    @Override
    protected void customNotifyItemRangeInserted(int startPosition, int length) {
        //no need any work for main table,notify were done in base
    }

    @Override
    protected void customNotifyUserForNewRow(int position) {
        //no need any work for main table,notify were done in base
    }

    @Override
    protected void customNotifyUserForChangeRow(int position) {
        //no need any work for main table,notify were done in base
    }


    //-------------------------------------------------------------------------------------------------

    @Override
    protected void injectNewDataCollectionWereDone() {
        //این فانکشن فقط یکبار صدا زده میشود و آپدیت های بعدی از طریق لیستنرهای دیتابیس انجام میشود
        chatroomTables = (OrderedRealmCollection<ChatroomTable>) getData(0);
        lastMessageTables = (OrderedRealmCollection<LastMessageTable>) getData(1);
        utilCodeTables = (OrderedRealmCollection<UtilCodeTable>) getData(2);
        chatroomMemberTables = (OrderedRealmCollection<ChatroomMemberTable>) getData(3);//only my friend id and me in all chatRoom
    }

    @Override
    protected void notifyNonMainItemAnyChanged(Object collection, int positionOfChangedTable, int startPosition, int length) {
        switch (positionOfChangedTable) {
            case 1:
                notifyByLastMsg(collection, startPosition, length);
                break;
            case 2:
                notifyByUtilCode(collection,startPosition,length);
                break;
            case 3:
                notifyByChatMember(collection,startPosition,length);
                break;
        }
    }


    private void notifyByLastMsg(Object collection, int startPosition, int length) {

        //no need any work, all work were done by change sort date off chatRoom in InsertUpdateMessageTableServiceQuery

        /*if (lastMessageTables != null) {
            for (int i = 0; i < length; i++) {
                if (lastMessageTables.size() >= startPosition + i && lastMessageTables.size()!=0) {//اگر به هر دلیل آن سطر را نداشتیم کاری انجام نمیدهیم
                    RealmResults<ChatroomTable> chatTableResult =
                            chatroomTables.where()
                                    .equalTo(CommonColumnName.ID, lastMessageTables.get(startPosition + i)
                                            .getChatRoomId())
                                    .findAll();

                    if (chatTableResult.size() != 0) {
                        notifyItemChanged(chatroomTables.indexOf(chatTableResult.first()));
                }

            }

        }
    }*/
}


    private void notifyByUtilCode(Object collection, int startPosition, int length) {
                    //no need any work, my util code changed in a slow manner
    }


    /**
     * جدول چت روم ممبری که به اینجا امده است شامل یوزر های روبه روی
     * من در چت روم های پرایوت و خود یوزر من در همه چت روم ها است
     *
     *
     * لذا اگر رکوردی تغییر کرد و یوزر خودم بود یعنی تعداد نخوانده های آن تغییر کرده است
     * از طرف دیگر اگر یوزر من نیست یعنی آفلاین و آنلاین تغییر کرده است به شرح زیر :
     *
     * تغییر آنلاین و آفلاین شدن یوزر  مقابل من در چت روم پرایوت
     *
     * تغییر تعداد نخوانده های خودم در همه چت روم ها که منجر به تغییر ردیف چت روم هم میشود که توسط تغییر چت لیست هندل میشود و نیاز به کاری نیست
     *
     * موضوع اینجاست که فقط صفر شدن نخوانده ها و آنلاین افلاین شدن چون موجب تغییر سطر نمیشود بنابراین باید در این جا هندل شود
     */
    private void notifyByChatMember(Object collection, int startPosition, int length) {
            notifyDataSetChanged();
        //no need any work, all work were done by change sort date off chatRoom in InsertUpdateMessageTableServiceQuery

     /*   if (chatroomMemberTables != null) {
            for (int i = 0; i < length; i++) {
                if (chatroomMemberTables.size() >= startPosition + i && chatroomMemberTables.size()!=0) {//اگر به هر دلیل آن سطر را نداشتیم کاری انجام نمیدهیم

                    ChatroomMemberTable memberTable =chatroomMemberTables.get(startPosition + i);
                    RealmResults<ChatroomTable> chatTableResult =
                            chatroomTables.where()
                                    .equalTo(CommonColumnName.ID, chatroomMemberTables.get(startPosition + i)
                                     .getChatroomId())
                                    .findAll();
                    if (chatTableResult.size() != 0) {
                        if(memberTable.getUserId().equals(SharedPreferenceProvider.getUserId(context))){
                            //unSeenCount were be changed no need any work
                        } else {
                            //private user were be hcanged
                            notifyItemChanged(chatroomTables.indexOf(chatTableResult.first()));
                        }
                    }



}}

        }*/
    }

}
