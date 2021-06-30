package ir.fararayaneh.erp.activities.chat_message;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseViewWithRecycle;
import ir.fararayaneh.erp.IBase.common_base.IGoNextPage;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonChatRoomToolbarState;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.custom_views.CustomCircleImageView;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomLayoutManager;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;
import ir.fararayaneh.erp.utils.PicassoHandler;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;

public class MessageView extends BaseViewWithRecycle {

    @BindView(R.id.global_bottom_sheet_message)
    BottomSheetLayout global_bottom_sheet_message;
    @BindView(R.id.edt_txt_msg_msg)
    AppCompatEditText edt_txt_msg_msg;
    @BindView(R.id.img_send_msg)
    AppCompatImageView img_send_msg;
    @BindView(R.id.global_toolbar)
    Toolbar global_toolbar;
    @BindView(R.id.img_attach_msg)
    AppCompatImageView img_attach_msg;
    @BindView(R.id.txt_profile_toolbar_chatroom)
    AppCompatTextView txt_profile_toolbar_chatroom;
    @BindView(R.id.txt_online_toolbar_chatroom)
    AppCompatTextView txt_online_toolbar_chatroom;
    @BindView(R.id.txt_offline_toolbar_chatroom)
    AppCompatTextView txt_offline_toolbar_chatroom;
    @BindView(R.id.txt_istyping_toolbar_chatroom)
    AppCompatTextView txt_istyping_toolbar_chatroom;
    @BindView(R.id.img_profile_toolbar_chatroom)
    CustomCircleImageView img_profile_toolbar_chatroom;
    @BindView(R.id.rec_chat_msg)
    CustomRecycleView rec_chat_msg;
    @BindView(R.id.conslay_prog_msg_act)
    ConstraintLayout conslay_prog_msg_act;
    @BindView(R.id.global_float_btn_badger)
    com.andremion.counterfab.CounterFab global_float_btn_badger;

    @BindView(R.id.load_more_progress_chat)
    ProgressBar load_more_progress_chat;



    private LinearLayoutManager linearLayoutManager;
    private IChatMessageProvider iChatMessageProvider;

    public void setiChatMessageProvider(IChatMessageProvider iChatMessageProvider) {
        this.iChatMessageProvider = iChatMessageProvider;
    }

    @OnClick({R.id.global_float_btn_badger,R.id.img_attach_msg,R.id.img_send_msg,R.id.img_profile_toolbar_chatroom})
    public void clickChatRoom(View view) {
        switch (view.getId()) {
            case R.id.global_float_btn_badger:
                clickFloatBtnBadge();
                break;
            case R.id.img_attach_msg:
                clickImageAttachMessage();
                break;
            case R.id.img_send_msg:
                clickImageSendMessage();
                break;
            case R.id.img_profile_toolbar_chatroom:
                clickImageProfileToolbar();
                break;
        }
    }

    @OnTextChanged(R.id.edt_txt_msg_msg)
    protected void onTextChanged(CharSequence text) {
       if(text.toString().length()==0){
           showHideAttachAndSendImage(false);
       } else {
           showHideAttachAndSendImage(true);
       }
    }


    private void showHideAttachAndSendImage(boolean showSendImage){
        img_send_msg.setVisibility(showSendImage?VISIBLE:GONE);
        img_attach_msg.setVisibility(showSendImage?GONE:VISIBLE);
    }


    public MessageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected MessageView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void setupRecyclers() {
        setDynamicRecyclersSpan();
    }

    private void setDynamicRecyclersSpan() {
        linearLayoutManager = new CustomLayoutManager(activity, RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        rec_chat_msg.setLayoutManager(linearLayoutManager);
        rec_chat_msg.setHasFixedSize(true);//you told RecyclerView to don’t calculate items size every time they added and removed from RecyclerView
        //recycleScrollHandler(previousPosition);
    }

    @Override
    public void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        rec_chat_msg.setAdapter(recycleAdaptor[0]);
        setRecycleScrollListener();
    }

    private void clickFloatBtnBadge() {
        //کاهش تعداد نخوانده ها در زمان اسکرول انجام میشود
        scroolRecyceleToPosition(linearLayoutManager.getItemCount() - 1 - global_float_btn_badger.getCount());
    }

    private void clickImageAttachMessage() {

        showAttachmentBottomSheet(global_bottom_sheet_message,true,true,true,true,false,true);
    }

    private void clickImageSendMessage() {
        if(iChatMessageProvider!=null){
            iChatMessageProvider.sendMessage(Objects.requireNonNull(edt_txt_msg_msg.getText()).toString());
            edt_txt_msg_msg.setText(Commons.SPACE);
        }
    }

    private void clickImageProfileToolbar() {
        if(iChatMessageProvider!=null){
            iChatMessageProvider.toolbarClick();
        }
    }


    @Override
    public void loadAtEndList(IGoNextPage IGoNextPage) {
        //no need
        //custom_recycle_reports_act.setListenEndScroll(() -> loadAtEndListReport(IGoNextPage));
    }

    private void loadAtEndListReport(IGoNextPage iGoNextPage) {
        //no need
        /*custom_recycle_reports_act.setListenEndScroll(() -> {
            custom_recycle_reports_act.setLoading(false);
            iGoNextPage.goNext();
        });*/
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_message, this, true);
        ButterKnife.bind(this);
        setupToolbar();
    }

    @Override
    protected void setupToolbar() {
        activity.setSupportActionBar(global_toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

     void setupTextImageToolbar(String txt, String chatroomAvatarFileName) {
        txt_profile_toolbar_chatroom.setText(txt);
        PicassoHandler.setByPicasso(picasso, MimeTypeHandler.getFilePathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, chatroomAvatarFileName), MimeTypeHandler.getURLFromName(chatroomAvatarFileName), img_profile_toolbar_chatroom, true, txt.substring(0, 1), true, false);
    }

     void handleOnlineOfflineTypingToolbar(byte chatRoomToolbarState) {
        switch (chatRoomToolbarState) {
            case CommonChatRoomToolbarState.ONLINE:
                showHideToolbarState(true,false,false);
                break;
            case CommonChatRoomToolbarState.OFFLINE:
                showHideToolbarState(false,true,false);
                break;
            case CommonChatRoomToolbarState.TYPING:
                showHideToolbarState(false,false,true);
                break;
        }
    }

    private void showHideToolbarState(boolean showOnline, boolean showOffline, boolean showTyping) {
        txt_online_toolbar_chatroom.setVisibility(showOnline ? VISIBLE : GONE);

        txt_offline_toolbar_chatroom.setVisibility(showOffline ? VISIBLE : GONE);

        txt_istyping_toolbar_chatroom.setVisibility(showTyping ? VISIBLE : GONE);
    }


    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_msg_act.setVisibility(showHide ? VISIBLE : GONE);
    }

    public void showHideLoadMoreProgress(boolean showHide) {
        load_more_progress_chat.setVisibility(showHide ? VISIBLE : GONE);
    }


    @Override
    protected void workforOnBackPressed() {
        finish();
    }

    @Override
    protected void workforOnCreateOptionsMenu(Menu menu) {

    }

    @Override
    protected boolean workforOnOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                workforOnBackPressed();
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void workForPermissionResults(int requestCode, @NonNull int[] grantResults) {

    }

    @Override
    public void workForActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: 10/5/2019
        /*
        در اینجا به نتایج سرچ و نتایج فیلتر تایم باید گوش کرد
        ضمنا در صورتی که امکان ایجاد یا حذف ممبر  یا ویرایش چت روم
        یا ترک و اضافه چت روم
        نیز وجود داشته باشد باید به نتایج آن نیز گوش کرد
        چون باید در یک اکتیویتی جداگانه که شامل یک استپر باشد
        و به لیست دوستان و اتچمنت دسترسی دارد
        چت روم را ایجاد نمود
         */
        if (resultCode == Activity.RESULT_CANCELED) {
            showMessageNothingsSelected();
        } else {
            switch (requestCode) {
                case CommonRequestCodes.GET_URI_LIST_FROM_ATTACHMENT:
                    sendAttachmentToPresenter(data,true);
                    break;
            }
        }
    }

    @Override
    protected void clearAllViewClickListeners() {

    }

    public void scroolRecyceleToPosition(int position) {
        if (linearLayoutManager != null) {
            if (position <= linearLayoutManager.getItemCount()) {
                rec_chat_msg.scrollToPosition(position);
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    private void setRecycleScrollListener() {
        rec_chat_msg.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(Objects.requireNonNull(linearLayoutManager).findFirstVisibleItemPosition()==0){
                    if(iChatMessageProvider!=null){
                        iChatMessageProvider.loadMoreMessage();
                    }
                }
                if (dy < 0) {
                    showHideFabButton(true);
                } else {
                    setFabBadgeCount(0);
                    handleScroollDownForBadge();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void showHideFabButton(boolean showHide) {
        if (showHide) {
            global_float_btn_badger.show();
        } else {
            global_float_btn_badger.hide();
        }
    }

    private void handleScroollDownForBadge() {
        /*if(global_float_btn_badger.getCount()==0){
            showHideFabButton(false);
        }
        */
        //position is zero base
        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.getItemCount() - 1) {
            showHideFabButton(false);
        }
    }

    public void handleNewMSGForBadge(boolean isMessageFromMe) {

        if (isMessageFromMe) {
            scroolRecyceleToPosition(linearLayoutManager.getItemCount() - 1);
        } else {
            //2 because position is zero base and we have new message in table
            if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.getItemCount() - 2) {
                scroolRecyceleToPosition(linearLayoutManager.getItemCount() - 1);
            } else {
                fabBadgeCountIncrease(1);
                showHideFabButton(true);
            }
        }
    }

    public void setFabBadgeCount(int count) {
        global_float_btn_badger.setCount(count);
    }

    public void fabBadgeCountIncrease(int count) {
        global_float_btn_badger.setCount(global_float_btn_badger.getCount() + count);
    }

    public void fabBadgeCountDecreaseMinZero() {
        //minimum decrease is 0
        global_float_btn_badger.decrease();

    }
    //----------------------------------------------------------------------------------------------
}
