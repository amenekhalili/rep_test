package ir.fararayaneh.erp.activities.time_filter;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseView;
import ir.fararayaneh.erp.R;

public class TimeFilterView extends BaseView {

    @BindView(R.id.btn_start_time_filter)
    AppCompatButton btn_start_time_filter;
    @BindView(R.id.btn_end_time_filter)
    AppCompatButton btn_end_time_filter;
    @BindView(R.id.btn_confirm_time_filter)
    AppCompatButton btn_confirm_time_filter;
    @BindView(R.id.conslay_prog_time_filter)
    ConstraintLayout conslay_prog_time_filter;
    @BindView(R.id.txt_start_date)
    AppCompatTextView txt_start_date;
    @BindView(R.id.txt_end_date)
    AppCompatTextView txt_end_date;

    @OnClick({R.id.btn_start_time_filter,R.id.btn_end_time_filter,R.id.btn_confirm_time_filter})
    public void clickTimeFilterView(View view) {
            if(timeFilterClickListener!=null){
                switch (view.getId()){
                case R.id.btn_start_time_filter:
                    timeFilterClickListener.clickStartDate();
                    break;
                case R.id.btn_end_time_filter:
                    timeFilterClickListener.clickEndDate();
                    break;
                case R.id.btn_confirm_time_filter:
                    timeFilterClickListener.clickConfirm();
                    break;
            }
        }

    }

    private ITimeFilterClickListener timeFilterClickListener;

    public void setTimeFilterClickListener(ITimeFilterClickListener timeFilterClickListener) {
        this.timeFilterClickListener = timeFilterClickListener;
    }

    public TimeFilterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeFilterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected TimeFilterView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_time_filter, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupToolbar() {

    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_time_filter.setVisibility(showHide ? VISIBLE : GONE);
    }

    @Override
    protected void workforOnBackPressed() {

    }

    @Override
    protected void workforOnCreateOptionsMenu(Menu menu) {

    }

    @Override
    protected boolean workforOnOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    protected void workForPermissionResults(int requestCode, @NonNull int[] grantResults) {

    }

    @Override
    public void workForActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clearAllViewClickListeners() {

    }

//------------------------
    public void setStartDate(String startDate){
        txt_start_date.setText(startDate);
    }

    public void setEndDate(String endDate){
        txt_end_date.setText(endDate);
    }
}
