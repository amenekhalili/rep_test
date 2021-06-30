package ir.fararayaneh.erp.activities.chat_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseViewWithRecycle;
import ir.fararayaneh.erp.IBase.common_base.IGoNextPage;
import ir.fararayaneh.erp.R;

import ir.fararayaneh.erp.commons.CommonRequestCodes;

import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomLayoutManager;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;


public class ChatListView extends BaseViewWithRecycle {

    @BindView(R.id.chatlist)
    CustomRecycleView chatlist;
    @BindView(R.id.global_toolbar)
    Toolbar global_toolbar;
    @BindView(R.id.txt_global_toolbar)
    AppCompatTextView txt_global_toolbar;
    @BindView(R.id.conslay_prog_chatlist_act)
    ConstraintLayout conslay_prog_chatlist_act;
    @BindView(R.id.global_float_btn_2)
    FloatingActionButton global_float_btn;

    @BindView(R.id.conslay)
    ConstraintLayout conslay_delete_global_search_result_layout;



    private IChatListActivityListener iChatListActivityListener;



    @OnClick({R.id.chip_cancel_search})
    public void clickReportsView(View view) {
        switch (view.getId()) {

            case R.id.chip_cancel_search:
                if(iChatListActivityListener!=null){
                    iChatListActivityListener.doSearch(null);
                    showHideSearchBanner(false);
                }
                break;
        }
    }


    public void setiChatListActivityListener(IChatListActivityListener iChatListActivityListener) {
        this.iChatListActivityListener = iChatListActivityListener;
    }

    public ChatListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected ChatListView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void setupRecyclers() {
        setDynamicRecyclersSpan();
    }

    private void setDynamicRecyclersSpan() {
        chatlist.setLayoutManager(new CustomLayoutManager(activity, RecyclerView.VERTICAL, false));
        chatlist.setHasFixedSize(true);//you told RecyclerView to don’t calculate items size every time they added and removed from RecyclerView
        //recycleScrollHandler(previousPosition);
    }

    @SafeVarargs
    @Override
    public final void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        chatlist.setAdapter(recycleAdaptor[0]);
        setRecycleScrollListener();
    }

    private void setRecycleScrollListener() {
        chatlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    showHideFabButton(true);
                } else {
                    showHideFabButton(false);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void showHideFabButton(boolean showHide) {
        if (showHide) {
            global_float_btn.show();
        } else {
            global_float_btn.hide();
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
        LayoutInflater.from(activity).inflate(R.layout.activity_chat_list, this, true);
        ButterKnife.bind(this);
        setupToolbar();
    }

    @Override
    protected void setupToolbar() {
        activity.setSupportActionBar(global_toolbar);
        setupTextToolbar(getResources().getString(R.string.chatlist));
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupTextToolbar(String txt) {
        txt_global_toolbar.setText(txt);
    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_chatlist_act.setVisibility(showHide ? VISIBLE : GONE);

    }

    @Override
    protected void workforOnBackPressed() {
        finish();
    }

    @Override
    protected void workforOnCreateOptionsMenu(Menu menu) {
        activity.getMenuInflater().inflate(R.menu.chat_list_menu, menu);
    }

    @Override
    protected boolean workforOnOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                workforOnBackPressed();
                return true;
            /*case R.id.sync_data_menu:
                if (iChatListActivityListener != null) {
                    iChatListActivityListener.goSyncAct();
                }
                return true;*/
            case R.id.search_chat_list:
                ActivityIntents.goSearchActForResult(activity);
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
        ضمنا در صورتی که امکان ایجاد چت روم جدید نیز وجود داشته باشد باید به نتایج آن نیز گوش کرد
        چون باید در یک اکتیویتی جداگانه که شامل یک استپر باشد و به لیست دوستان و اتچمنت دسترسی دارد
        چت روم را ایجاد نمود
         */
        if (resultCode == Activity.RESULT_CANCELED) {
            showMessageNothingsSelected();
        } else {
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case CommonRequestCodes.Do_SYNC:
                        reCreate();
                        break;
                    case CommonRequestCodes.GET_SEARCH:
                        doSearch(data);
                        break;
                }
            }

        }
    }

    @Override
    protected void clearAllViewClickListeners() {

    }

    private void doSearch(Intent data) {
        if(data!=null){
            if(data.getExtras()!=null && iChatListActivityListener!=null){
                iChatListActivityListener.doSearch(data);
                showHideSearchBanner(true);
            } else {
                showMessageSomeProblems(CommonsLogErrorNumber.error_122);
            }
        } else {
            showMessageSomeProblems(CommonsLogErrorNumber.error_123);
        }
    }

    private void showHideSearchBanner(boolean showHide){
        conslay_delete_global_search_result_layout.setVisibility(showHide?VISIBLE:GONE);
    }

}
