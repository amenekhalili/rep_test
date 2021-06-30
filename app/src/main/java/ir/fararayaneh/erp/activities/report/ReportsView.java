package ir.fararayaneh.erp.activities.report;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ReportsView extends BaseViewWithRecycle {

    private IReportClickListener iReportClickListener;

    private int spanCount = 1;

    public void setiReportClickListener(IReportClickListener iReportClickListener) {
        this.iReportClickListener = iReportClickListener;
    }

    @BindView(R.id.global_toolbar_2)
    Toolbar global_toolbar_2;
    @BindView(R.id.img_global_toolbar_2)
    AppCompatImageView img_global_toolbar_2;
    @BindView(R.id.img_filter_toolbar_2)
    AppCompatImageView img_filter_toolbar_2;
    @BindView(R.id.txt_global_toolbar_2)
    AppCompatTextView txt_global_toolbar_2;
    @BindView(R.id.custom_recycle_reports_act)
    CustomRecycleView custom_recycle_reports_act;
    @BindView(R.id.conslay_prog_reports_act)
    ConstraintLayout conslay_prog_reports_act;
    @BindView(R.id.global_float_btn)
    FloatingActionButton global_float_btn;
    @BindView(R.id.global_float_btn_up)
    FloatingActionButton global_float_btn_up;
    @BindView(R.id.chip_cancel_search)
    com.robertlevonyan.views.chip.Chip chip_cancel_search;
    @BindView(R.id.chip_cancel_filter)
    com.robertlevonyan.views.chip.Chip chip_cancel_filter;
    @BindView(R.id.include9)
    ConstraintLayout conslay_delete_global_search_result_layout;
    @BindView(R.id.include10)
    ConstraintLayout conslay_delete_global_filter_result_layout;


    @OnClick({R.id.global_float_btn, R.id.chip_cancel_search, R.id.chip_cancel_filter, R.id.img_filter_toolbar_2, R.id.img_global_toolbar_2, R.id.global_float_btn_up})
    public void clickReportsView(View view) {
        switch (view.getId()) {
            case R.id.global_float_btn:
                recycleScrollHandler(Objects.requireNonNull(custom_recycle_reports_act.getLayoutManager()).getItemCount() - 1);
                showHideFabButton(false);
                break;
            case R.id.global_float_btn_up:
                recycleScrollHandler(0);
                showHideFabButtonUp(false);
                break;
            case R.id.chip_cancel_search:
                if (iReportClickListener != null) {
                    iReportClickListener.filterText(null);
                    showHideSearchBanner(false);
                }
                break;
            case R.id.chip_cancel_filter:
                if (iReportClickListener != null) {
                    iReportClickListener.filterTime(null);
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

    public ReportsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReportsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected ReportsView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void setupRecyclers() {
        setDynamicRecyclersSpan(spanCount);
    }

    protected void setDynamicRecyclersSpan(int spanCount) {
        /*int previousPosition = 0;
        if (custom_recycle_reports_act.getLayoutManager() != null) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager)
                    custom_recycle_reports_act.getLayoutManager();
            previousPosition = gridLayoutManager.findLastVisibleItemPosition();
        }*/
        // custom_recycle_reports_act.setLayoutManager(new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false));
        custom_recycle_reports_act.setLayoutManager(new CustomLayoutManager(activity, RecyclerView.VERTICAL, false));
        custom_recycle_reports_act.setHasFixedSize(true);//you told RecyclerView to don’t calculate items size every time they added and removed from RecyclerView
        //recycleScrollHandler(previousPosition);
    }

    @SafeVarargs
    @Override
    public final void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        custom_recycle_reports_act.setAdapter(recycleAdaptor[0]);
        setRecycleScrollListener();
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
        LayoutInflater.from(activity).inflate(R.layout.activity_reports, this, true);
        ButterKnife.bind(this);
        setupToolbar();
    }

    @Override
    protected void setupToolbar() {

        activity.setSupportActionBar(global_toolbar_2);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setupTextToolbar(String text) {
        txt_global_toolbar_2.setText(text);
    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_reports_act.setVisibility(showHide ? VISIBLE : GONE);
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

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
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

    public void recycleScrollHandler(int position) {
        if (position <= Objects.requireNonNull(custom_recycle_reports_act.getLayoutManager()).getItemCount()) {
            custom_recycle_reports_act.scrollToPosition(position);
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

    private void setRecycleScrollListener() {
        custom_recycle_reports_act.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    showHideFabButton(true);
                    showHideFabButtonUp(false);
                } else if (dy == 0) {
                    showHideFabButton(false);
                    showHideFabButtonUp(false);
                } else {
                    showHideFabButton(false);
                    showHideFabButtonUp(true);
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
    private void showHideFabButtonUp(boolean showHide) {
        if (showHide) {
            global_float_btn_up.show();
        } else {
            global_float_btn_up.hide();
        }
    }

    private void doSearch(Intent data) {
        if (data != null) {
            if (data.getExtras() != null && iReportClickListener != null) {
                iReportClickListener.filterText(data);
                showHideSearchBanner(true);
            } else {
                showMessageSomeProblems(CommonsLogErrorNumber.error_70);
            }
        } else {
            showMessageSomeProblems(CommonsLogErrorNumber.error_71);
        }
    }

    private void doTimeFilter(Intent data) {

        if (data != null) {
            if (data.getExtras() != null && iReportClickListener != null) {
                iReportClickListener.filterTime(data);
                showHideTimeFilterBanner(true);
            } else {
                showMessageSomeProblems(CommonsLogErrorNumber.error_86);
            }
        } else {
            showMessageSomeProblems(CommonsLogErrorNumber.error_87);
        }
    }

    private void showHideSearchBanner(boolean showHide) {
        conslay_delete_global_search_result_layout.setVisibility(showHide ? VISIBLE : GONE);
    }

    private void showHideTimeFilterBanner(boolean showHide) {
        conslay_delete_global_filter_result_layout.setVisibility(showHide ? VISIBLE : GONE);
    }
}

