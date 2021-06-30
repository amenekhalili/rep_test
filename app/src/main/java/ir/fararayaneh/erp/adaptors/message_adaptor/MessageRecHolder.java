package ir.fararayaneh.erp.adaptors.message_adaptor;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonMessageSendType;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.custom_views.CustomCircleImageView;
import ir.fararayaneh.erp.utils.PicassoHandler;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;

public class MessageRecHolder extends BaseRecHolder {

    private IClickRowMessageRecHolderListener holderListener;



    @BindView(R.id.img_profile_row_chat_msg)
    CustomCircleImageView img_profile_row_chat_msg;
    @BindView(R.id.img_offline_row_chat_msg)
    CustomCircleImageView img_offline_row_chat_msg;
    @BindView(R.id.img_online_row_chat_msg)
    CustomCircleImageView img_online_row_chat_msg;
    @BindView(R.id.img_attach_row_chat_msg)
    AppCompatImageView img_attach_row_chat_msg;
    @BindView(R.id.txt_msg_row_chat_msg)
    AppCompatTextView txt_msg_row_chat_msg;
    @BindView(R.id.txt_date_row_chat_msg)
    AppCompatTextView txt_date_row_chat_msg;
    @BindView(R.id.img_error_row_chat_msg)
    AppCompatImageView img_error_row_chat_msg;
    @BindView(R.id.img_wait_row_chat_msg)
    AppCompatImageView img_wait_row_chat_msg;
    @BindView(R.id.img_send_row_chat_msg)
    AppCompatImageView img_send_row_chat_msg;
    @BindView(R.id.img_receive_row_chat_msg)
    AppCompatImageView img_receive_row_chat_msg;
    @BindView(R.id.img_seen_row_chat_msg)
    AppCompatImageView img_seen_row_chat_msg;

    @OnClick({R.id.img_profile_row_chat_msg, R.id.img_attach_row_chat_msg})
    public void clickMessageRecHolder(View view) {
        switch (view.getId()) {
            case R.id.img_profile_row_chat_msg:
                clickImageAvatar();
                break;
            case R.id.img_attach_row_chat_msg:
                clickImageAttach();
                break;
            case R.id.txt_msg_row_chat_msg:
                clickTextMessage();
                break;
        }
    }

    private void clickTextMessage() {
        if (holderListener != null) {
            holderListener.clickMessage(getAdapterPosition());
        }
    }


    private void clickImageAttach() {
        if (holderListener != null) {
            holderListener.clickAttachRow(getAdapterPosition());
        }
    }

    private void clickImageAvatar() {
        if (holderListener != null) {
            holderListener.clickAvatarRow(getAdapterPosition());
        }
    }

    public MessageRecHolder(View itemView, IClickRowMessageRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }


    /**
     * @param commonMessageReceivedType : from CommonMessageSendType
     */
    void setRowDataSend(String message,  String fileName, String messageDate,byte commonMessageReceivedType) {
        txt_msg_row_chat_msg.setText(message);
        showHideAttachImage(fileName);
        txt_date_row_chat_msg.setText(messageDate);
        handleSendMessageType(commonMessageReceivedType);
    }



    void setRowDataGroupReceived(String message, String fileName, String messageDate,boolean isOnline,String profileImage,boolean isPrivate,String userNameFirstCharacter) {
        txt_msg_row_chat_msg.setText(message);
        showHideAttachImage(fileName);
        txt_date_row_chat_msg.setText(messageDate);
        handleOnlineUser(isOnline,isPrivate);
        handleProfilePicture(profileImage,userNameFirstCharacter);
    }


    /**
     * @param fileName: GUID.Suffix
     */
    void setRowDataPrivateChannelReceived(String message,  String fileName, String messageDate) {
        txt_msg_row_chat_msg.setText(message);
        showHideAttachImage( fileName);
        txt_date_row_chat_msg.setText(messageDate);

    }

    void setRowDataGlobal(String message) {
        txt_msg_row_chat_msg.setText(message);
    }


    //----------------------------------------------------------------------------------------------
    //show erp icon if first attach is not image
    private void showHideAttachImage( String fileName) {
        if (fileName.equals(Commons.NULL)) {
            img_attach_row_chat_msg.setVisibility(View.GONE);
        } else {
            img_attach_row_chat_msg.setVisibility(View.VISIBLE);
            PicassoHandler.setByPicasso(picasso, MimeTypeHandler.getFilePathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, fileName), MimeTypeHandler.getURLFromName(fileName), img_attach_row_chat_msg, false, Commons.SPACE, false, true);
        }
    }

    private void handleOnlineUser(boolean isOnline,boolean isPrivate) {
        img_online_row_chat_msg.setVisibility(isOnline && isPrivate?View.VISIBLE:View.GONE);
        img_offline_row_chat_msg.setVisibility(!isOnline && isPrivate?View.VISIBLE:View.GONE);
    }

    private void handleProfilePicture(String fileName, String userNameFirstCharacter) {
        PicassoHandler.setByPicasso(picasso, MimeTypeHandler.getFilePathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, fileName), MimeTypeHandler.getURLFromName(fileName), img_profile_row_chat_msg, true, userNameFirstCharacter, true,false);
    }

    private void handleSendMessageType(byte commonMessageSendType) {

        switch (commonMessageSendType){
            case CommonMessageSendType.WAIT_MSG:
                handleReceivedMessageImage(true,false,false,false);
                break;
            case CommonMessageSendType.RECEIVED_MSG:
                handleReceivedMessageImage(false,true,false,false);
                break;
            case CommonMessageSendType.SEEN_MSG:
                handleReceivedMessageImage(false,false,true,false);
                break;
            case CommonMessageSendType.ERROR_MSG:
                handleReceivedMessageImage(false,false,false,true);
                break;
        }
    }

    private void handleReceivedMessageImage(boolean showWait, boolean showReceived, boolean showSeen, boolean showError) {
        img_wait_row_chat_msg.setVisibility(showWait?View.VISIBLE:View.GONE);
        img_receive_row_chat_msg.setVisibility(showReceived?View.VISIBLE:View.GONE);
        img_seen_row_chat_msg.setVisibility(showSeen?View.VISIBLE:View.GONE);
        img_error_row_chat_msg.setVisibility(showError?View.VISIBLE:View.GONE);
    }

}
