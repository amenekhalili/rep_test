package ir.fararayaneh.erp.activities.select_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseViewWithRecycle;
import ir.fararayaneh.erp.IBase.common_base.IGoNextPage;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonErrorNumber;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;
import ir.fararayaneh.erp.utils.SoftKeyHelper;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;

public class SelectionView extends BaseViewWithRecycle {

    private ISelectionActivityClickListener iSelectionActivityClickListener;

    private int spanCount = 1;

    public void setiSelectionActivityClickListener(ISelectionActivityClickListener iSelectionActivityClickListener) {
        this.iSelectionActivityClickListener = iSelectionActivityClickListener;
    }

    @BindView(R.id.global_toolbar_2)
    Toolbar global_toolbar_2;
    @BindView(R.id.img_global_toolbar_2)
    AppCompatImageView img_global_toolbar_2;
    @BindView(R.id.img_filter_toolbar_2)
    AppCompatImageView img_filter_toolbar_2;
    @BindView(R.id.txt_global_toolbar_2)
    AppCompatTextView txt_global_toolbar_2;
    @BindView(R.id.custom_recycle_selection_act)
    CustomRecycleView custom_recycle_selection_act;
    @BindView(R.id.conslay_prog_selection_act)
    ConstraintLayout conslay_prog_selection_act;
    @BindView(R.id.chip_cancel_search)
    com.robertlevonyan.views.chip.Chip chip_cancel_search;
    @BindView(R.id.chip_cancel_filter)
    com.robertlevonyan.views.chip.Chip chip_cancel_filter;
    @BindView(R.id.include9)
    ConstraintLayout conslay_delete_global_search_result_layout;
    @BindView(R.id.include10)
    ConstraintLayout conslay_delete_global_filter_result_layout;

    @OnClick({R.id.chip_cancel_search,R.id.img_filter_toolbar_2,R.id.chip_cancel_filter,R.id.img_global_toolbar_2})
    public void clickSelectionView(View view) {
        switch (view.getId()) {
            case R.id.chip_cancel_search:
                if(iSelectionActivityClickListener!=null){
                    iSelectionActivityClickListener.searchText(null);
                    showHideSearchBanner(false);
                }
                break;
            case R.id.chip_cancel_filter:
                if(iSelectionActivityClickListener!=null){
                    iSelectionActivityClickListener.filterTime(null);
                    showHideTimeFilterBanner(false);
                }
                break;
            case R.id.img_filter_toolbar_2:
                ActivityIntents.goTimeFilterActivityForResult(activity);
                break;
            case R.id.img_global_toolbar_2:
                ActivityIntents.goSearchActForResult(activity);
                break;
        }
    }


    public SelectionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected SelectionView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void setupRecyclers() {
        setDynamicRecyclersSpan(1);
    }

    protected void setDynamicRecyclersSpan(int spanCount) {
        int previousPosition = 0;
        if (custom_recycle_selection_act.getLayoutManager() != null) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager)
                    custom_recycle_selection_act.getLayoutManager();
            previousPosition = gridLayoutManager.findLastVisibleItemPosition();
        }
        custom_recycle_selection_act.setLayoutManager(new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false));
        custom_recycle_selection_act.setHasFixedSize(true);
    }

    @SafeVarargs
    @Override
    public final void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        custom_recycle_selection_act.setAdapter(recycleAdaptor[0]);
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
        LayoutInflater.from(activity).inflate(R.layout.activity_selection, this, true);
        ButterKnife.bind(this);
        setupToolbar();
    }

    @Override
    protected void setupToolbar() {
        activity.setSupportActionBar(global_toolbar_2);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        img_global_toolbar_2.setOnClickListener(view -> ActivityIntents.goSearchActForResult(activity));
    }

    public void setupTextToolbar(String text) {
        txt_global_toolbar_2.setText(text);
    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_selection_act.setVisibility(showHide ? VISIBLE : GONE);
    }

    @Override
    protected void workforOnBackPressed() {
        finish();
    }

    @Override
    protected void workforOnCreateOptionsMenu(Menu menu) {
        activity.getMenuInflater().inflate(R.menu.selection_menu, menu);
    }

    @Override
    protected boolean workforOnOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               if(iSelectionActivityClickListener!=null){
                   iSelectionActivityClickListener.clickBack();
               }
                return true;
            case R.id.select_all_selection_menu:
                if (iSelectionActivityClickListener != null) {
                    iSelectionActivityClickListener.clickSelectAll();
                }
                return true;
            case R.id.deselect_all_selection_menu:
                if (iSelectionActivityClickListener != null) {
                    iSelectionActivityClickListener.clickDeSelectAll();
                }
                return true;
            default:
                return true;
        }
    }

    //todo use this metod
    private void spanFromMenu() {
        if (spanCount == 1) {
            spanCount++;
        } else {
            spanCount = 1;
        }
        setDynamicRecyclersSpan(spanCount);
    }

    @Override
    protected void workForPermissionResults(int requestCode, @NonNull int[] grantResults) {

    }

    /**
     * no need to listen to albumActivity, because user can not add or delete attach from here in albumAttach activity
     */
    @Override
    public void workForActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case CommonRequestCodes.GET_SEARCH:
                    doSearch(data);
                    break;
                case CommonRequestCodes.GET_TIME_FILTER:
                    doTimeFilter(data);
                    break;
            }
        }
    }

    @Override
    protected void clearAllViewClickListeners() {

    }

    private void doSearch(Intent data) {
        if(data!=null){
            if(data.getExtras()!=null && iSelectionActivityClickListener!=null){
                iSelectionActivityClickListener.searchText(data);
                showHideSearchBanner(true);
            } else {
                showMessageSomeProblems(CommonsLogErrorNumber.error_68);
            }
        } else {
            showMessageSomeProblems(CommonsLogErrorNumber.error_69);
        }
    }

    private void doTimeFilter(Intent data) {

        if(data!=null){
            if(data.getExtras()!=null && iSelectionActivityClickListener!=null){
                iSelectionActivityClickListener.filterTime(data);
                showHideTimeFilterBanner(true);
            } else {
                showMessageSomeProblems(CommonsLogErrorNumber.error_86);
            }
        } else {
            showMessageSomeProblems(CommonsLogErrorNumber.error_87);
        }
    }

    private void showHideSearchBanner(boolean showHide){
        conslay_delete_global_search_result_layout.setVisibility(showHide?VISIBLE:GONE);
    }

    private void showHideTimeFilterBanner(boolean showHide){
        conslay_delete_global_filter_result_layout.setVisibility(showHide?VISIBLE:GONE);
    }

    public void recycleScrollHandler(int position) {
        if (position <= Objects.requireNonNull(custom_recycle_selection_act.getLayoutManager()).getItemCount()) {
            custom_recycle_selection_act.scrollToPosition(position);
            /*RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(activity) {
                @Override
                protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_END;
                }
            };
            smoothScroller.setTargetPosition(position);
            Objects.requireNonNull(custom_recycle_reports_act.getLayoutManager()).startSmoothScroll(smoothScroller);*/
        }
    }

}

