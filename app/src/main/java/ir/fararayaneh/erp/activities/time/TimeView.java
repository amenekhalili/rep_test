package ir.fararayaneh.erp.activities.time;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.form_base.BaseViewCollectionDataForm;
import ir.fararayaneh.erp.IBase.common_base.IGoNextPage;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;
import ir.fararayaneh.erp.utils.GridLayoutSpanHelper;

public class TimeView extends BaseViewCollectionDataForm {



    @BindView(R.id.conslay_prog_time_act)
    ConstraintLayout conslay_prog_time_act;
    @BindView(R.id.conslay_sheet_plan_time_act)
    ConstraintLayout conslay_sheet_plan_time_act;
    @BindView(R.id.include6)
    ConstraintLayout conslay_global_delete_btn_layout;
    @BindView(R.id.custom_recycle_sheet_plan_time_act)
    CustomRecycleView custom_recycle_sheet_plan_time_act;
    @BindView(R.id.btn_confirm_global_confirm_cancel_btns)
    AppCompatButton btn_confirm_global_confirm_cancel_btns;
    @BindView(R.id.btn_cancel_global_confirm_cancel_btns)
    AppCompatButton btn_cancel_global_confirm_cancel_btns;
    @BindView(R.id.btn_delete_global_delete_btn_layout)
    AppCompatButton btn_delete_global_delete_btn_layout;

    @OnClick({R.id.btn_confirm_global_confirm_cancel_btns,R.id.btn_cancel_global_confirm_cancel_btns,R.id.btn_delete_global_delete_btn_layout})
    public void clickTimeView(View view) {
        switch (view.getId()){
            case R.id.btn_confirm_global_confirm_cancel_btns:
                clickConfirmBTN();
                break;
            case R.id.btn_cancel_global_confirm_cancel_btns:
                clickCancelBTN();
                break;
            case R.id.btn_delete_global_delete_btn_layout:
                clickDeleteBTN();
                break;
        }

    }



    public TimeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected TimeView(@NonNull BaseActivity activity) {
        super(activity);
    }


    @Override
    public void setupCustomLayoutManager(int maximumSpanInOneRow,int nonMachParentRowSpanCount,ArrayList<Integer> positionOfNonMachParentRow){
        gridLayoutManager=new GridLayoutManager(activity,maximumSpanInOneRow);
        GridLayoutSpanHelper.setupCustomSpanPerEachRow(gridLayoutManager,maximumSpanInOneRow,nonMachParentRowSpanCount,positionOfNonMachParentRow);
        custom_recycle_sheet_plan_time_act.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void setupStairCustomLayoutManager(ArrayList<Integer> positionOfQuarterRow, ArrayList<Integer> positionOfThirdRow, ArrayList<Integer> positionOfHalfRow) {
        //no need to any work because setupCustomLayoutManager
    }

    @Override
    public void showHideBTNDelete(boolean showHide) {
        conslay_global_delete_btn_layout.setVisibility(showHide?VISIBLE:GONE);
    }

    @SafeVarargs
    @Override
    public final void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        custom_recycle_sheet_plan_time_act.setAdapter(recycleAdaptor[0]);
    }

    @Override
    public void loadAtEndList(IGoNextPage IGoNextPage) {
        custom_recycle_sheet_plan_time_act.setListenEndScroll(() -> {
            //no need to done any work, data were loaded  before here in first time
        });
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_time, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupToolbar() {

    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_time_act.setVisibility(showHide?VISIBLE:GONE);
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

    //--------------------------------------------------------------------------------------->>

    private void clickConfirmBTN() {
        if(collectionDataListener!=null){
            collectionDataListener.confirmClick();
        }
    }

    private void clickCancelBTN() {
        if(collectionDataListener!=null){
            collectionDataListener.cancelClick();
        }
    }
    private void clickDeleteBTN() {
        if(collectionDataListener!=null){
            collectionDataListener.deleteClick();
        }
    }
    public void showHideConslaySheetPlanTime(boolean showHide) {
        conslay_sheet_plan_time_act.setVisibility(showHide?VISIBLE:GONE);
    }

}
