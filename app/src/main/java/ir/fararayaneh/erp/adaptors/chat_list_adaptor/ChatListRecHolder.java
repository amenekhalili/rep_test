package ir.fararayaneh.erp.adaptors.chat_list_adaptor;

import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.custom_views.CustomCircleImageView;
import ir.fararayaneh.erp.utils.PicassoHandler;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;

public class ChatListRecHolder extends BaseRecHolder {

    private IClickRowChatListRecHolderListener holderListener;


    @BindView(R.id.txt_name_chat_list)
    AppCompatTextView txt_name_chat_list;
    @BindView(R.id.img_forbiden_chatroom)
    AppCompatImageView img_forbiden_chatroom;
    @BindView(R.id.img_waiting_chatroom)
    AppCompatImageView img_waiting_chatroom;
    @BindView(R.id.txt_last_msg_chat_list)
    AppCompatTextView txt_last_msg_chat_list;
    @BindView(R.id.txt_date_chat_list)
    AppCompatTextView txt_date_chat_list;
    @BindView(R.id.txt_un_reed_chat_list)
    AppCompatTextView txt_un_reed_chat_list;
    @BindView(R.id.img_profile_chat_list)
    CustomCircleImageView img_profile_chat_list;
    @BindView(R.id.img_offline_chat_list)
    CustomCircleImageView img_offline_chat_list;
    @BindView(R.id.img_online_chat_list)
    CustomCircleImageView img_online_chat_list;
    @BindView(R.id.linlayChatlist)
    LinearLayout linlayChatlist;
    @BindView(R.id.img_back_offline_chat_list)
    CustomCircleImageView img_back_offline_chat_list;





    @OnClick({R.id.img_profile_chat_list,R.id.txt_name_chat_list,R.id.txt_last_msg_chat_list,R.id.linlayChatlist})
    public void clickChatListRecHolder(View view) {
        if (view.getId() == R.id.img_profile_chat_list) {
            clickImageAttach();
        } else {
            clickGoChatRoom();
        }
    }

    private void clickImageAttach() {
        if (holderListener != null) {
            holderListener.clickAttachRow(getAdapterPosition());
        }
    }

    private void clickGoChatRoom() {
        if (holderListener != null) {
            holderListener.clickGoChatRoomRow(getAdapterPosition());
        }
    }


    ChatListRecHolder(View itemView, IClickRowChatListRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    /**
     * @param fileName: GUID.Suffix
     * showOnlineStatus : آیا لازم است که آنلاین بودن را نمایش داد (فقط برای چت روم های دو نفره باید نمایش داده شود)
     */
    void setRowData(String chatName,String lastMessage, String date,String unReadCount,String syncState, boolean showOnlineStatus,boolean isOnline, String fileName) {
        txt_name_chat_list.setText(chatName);
        handleDateMessage(date);
        txt_last_msg_chat_list.setText(lastMessage);
        handelUnreadCount(unReadCount);
        handelSyncState(syncState);
        handelOnlineStatus(showOnlineStatus,isOnline);
        showHideAttachImage(fileName,chatName.substring(0,1));
    }

    private void handleDateMessage(String date) {

        txt_date_chat_list.setText(date);
        txt_date_chat_list.setVisibility(date.equals(Commons.MINIMUM_TIME)?View.GONE:View.VISIBLE);
    }


    private void handelUnreadCount(String unReadCount) {
        txt_un_reed_chat_list.setText(unReadCount);
        txt_un_reed_chat_list.setVisibility(Integer.valueOf(unReadCount)<1?View.INVISIBLE:View.VISIBLE);
    }

    private void handelSyncState(String syncState) {
        switch (syncState){
            case CommonSyncState.ACCESS_DENIED:
            case CommonSyncState.SYNC_ERROR:
                img_forbiden_chatroom.setVisibility(View.VISIBLE);
                img_waiting_chatroom.setVisibility(View.GONE);
                break;
            case CommonSyncState.BEFORE_SYNC:
            case CommonSyncState.ON_SYNC:
                img_forbiden_chatroom.setVisibility(View.GONE);
                img_waiting_chatroom.setVisibility(View.VISIBLE);
                break;
            default:
                img_forbiden_chatroom.setVisibility(View.GONE);
                img_waiting_chatroom.setVisibility(View.GONE);
        }
    }

    private void handelOnlineStatus(boolean showOnlineStatus, boolean isOnline) {
            img_back_offline_chat_list.setVisibility(showOnlineStatus?View.VISIBLE:View.GONE);
            img_offline_chat_list.setVisibility(showOnlineStatus&&!isOnline?View.VISIBLE:View.GONE);
            img_online_chat_list.setVisibility(showOnlineStatus&&isOnline?View.VISIBLE:View.GONE);
    }


    private void showHideAttachImage(String fileName,String chatRoomFirstCharacter) {
            PicassoHandler.setByPicasso(picasso, MimeTypeHandler.getFilePathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, fileName), MimeTypeHandler.getURLFromName(fileName), img_profile_chat_list, true, chatRoomFirstCharacter, true, false);
    }

}
