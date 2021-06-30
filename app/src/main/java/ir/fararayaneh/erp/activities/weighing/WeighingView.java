package ir.fararayaneh.erp.activities.weighing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.IGoNextPage;
import ir.fararayaneh.erp.IBase.common_base.form_base.BaseViewCollectionDataForm;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;
import ir.fararayaneh.erp.utils.GridLayoutSpanHelper;

public class WeighingView extends BaseViewCollectionDataForm {

    @BindView(R.id.conslay_prog_weighing_act)
    ConstraintLayout conslay_prog_weighing_act;

    @BindView(R.id.conslay_weighing_act)
    ConstraintLayout conslay_weighing_act;

    @BindView(R.id.include6)
    ConstraintLayout conslay_global_delete_btn_layout;

    @BindView(R.id.custom_recycle_weighing_act)
    CustomRecycleView custom_recycle_weighing_act;

    @BindView(R.id.btn_cancel_global_confirm_cancel_btns_type_3)
    AppCompatButton btn_cancel_global_confirm_cancel_btns_type_3;

    @BindView(R.id.btn_next_global_confirm_cancel_btns_type_3)
    AppCompatButton btn_next_global_confirm_cancel_btns_type_3;

    @BindView(R.id.btn_delete_global_delete_btn_layout)
    AppCompatButton btn_delete_global_delete_btn_layout;


    @OnClick({R.id.btn_cancel_global_confirm_cancel_btns_type_3,R.id.btn_next_global_confirm_cancel_btns_type_3,R.id.btn_delete_global_delete_btn_layout})
    public void clickWeighingView(View view) {
        switch (view.getId()){
            case R.id.btn_cancel_global_confirm_cancel_btns_type_3:
                clickCancelBTN();
                break;
            case R.id.btn_next_global_confirm_cancel_btns_type_3:
                clickNextBTN();
                break;
            case R.id.btn_delete_global_delete_btn_layout:
                clickDeleteBTN();
                break;
        }
    }



    public WeighingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeighingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected WeighingView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    public void setupCustomLayoutManager(int maximumSpanInOneRow, int nonMachParentRowSpanCount, ArrayList<Integer> positionOfNonMachParentRow) {
        gridLayoutManager=new GridLayoutManager(activity,maximumSpanInOneRow);
        GridLayoutSpanHelper.setupCustomSpanPerEachRow(gridLayoutManager,maximumSpanInOneRow,nonMachParentRowSpanCount,positionOfNonMachParentRow);
        custom_recycle_weighing_act.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void setupStairCustomLayoutManager(ArrayList<Integer> positionOfQuarterRow, ArrayList<Integer> positionOfThirdRow, ArrayList<Integer> positionOfHalfRow) {
        //no need to any work because setupCustomLayoutManager
    }

    /**
     * in weighing we can not see delete button
     */
    @Override
    public void showHideBTNDelete(boolean showHide) {
        conslay_global_delete_btn_layout.setVisibility(GONE);
    }

    @SafeVarargs
    @Override
    public final void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        custom_recycle_weighing_act.setAdapter(recycleAdaptor[0]);
    }

    @Override
    public void loadAtEndList(IGoNextPage IGoNextPage) {
        custom_recycle_weighing_act.setListenEndScroll(() -> {
            //no need to done any work, data were loaded  before here in first time
        });
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_weighing, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupToolbar() {

    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_weighing_act.setVisibility(showHide?VISIBLE:GONE);
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
        if (resultCode == Activity.RESULT_CANCELED) {
            showMessageNothingsSelected();
        }
    }

    @Override
    protected void clearAllViewClickListeners() {

    }

    //----------------------------------------------------------
    /**
     * in weighing we can not see delete button
     */
    private void clickDeleteBTN() {
        //do nothing
    }

    private void clickNextBTN() {
        if(collectionDataListener!=null){
            collectionDataListener.nextClick();
        }
    }

    private void clickCancelBTN() {
        if(collectionDataListener!=null){
            collectionDataListener.cancelClick();
        }
    }

    void showHideConslayWeighing(boolean showHide) {
        conslay_weighing_act.setVisibility(showHide?VISIBLE:GONE);
    }
}
