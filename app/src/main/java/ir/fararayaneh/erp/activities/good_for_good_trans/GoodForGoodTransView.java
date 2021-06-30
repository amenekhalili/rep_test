package ir.fararayaneh.erp.activities.good_for_good_trans;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.IGoNextPage;
import ir.fararayaneh.erp.IBase.common_base.form_base.BaseViewCollectionDataForm;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;
import ir.fararayaneh.erp.utils.GridLayoutSpanHelper;

public class GoodForGoodTransView  extends BaseViewCollectionDataForm {


    @BindView(R.id.conslay_prog_for_good_trans_act)
    ConstraintLayout conslay_prog_for_good_trans_act;
    @BindView(R.id.conslay_good_for_good_trance)
    ConstraintLayout conslay_good_for_good_trance;
    @BindView(R.id.custom_recycle_good_for_good_trance_master)
    CustomRecycleView custom_recycle_good_for_good_trance_master;
    @BindView(R.id.btn_confirm_global_confirm_cancel_btns)
    AppCompatButton btn_confirm_global_confirm_cancel_btns;
    @BindView(R.id.btn_cancel_global_confirm_cancel_btns)
    AppCompatButton btn_cancel_global_confirm_cancel_btns;


    @OnClick({R.id.btn_confirm_global_confirm_cancel_btns, R.id.btn_cancel_global_confirm_cancel_btns})
    public void clickGoodForGoodTransView(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_global_confirm_cancel_btns:
                clickConfirmBTN();
                break;
            case R.id.btn_cancel_global_confirm_cancel_btns:
                clickCancelBTN();
                break;
        }
    }


    public GoodForGoodTransView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GoodForGoodTransView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected GoodForGoodTransView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    public void setupCustomLayoutManager(int maximumSpanInOneRow, int nonMachParentRowSpanCount, ArrayList<Integer> positionOfNonMachParentRow) {
        //no need to any work because setupStairCustomLayoutManager
    }

    @Override
    public void setupStairCustomLayoutManager(ArrayList<Integer> positionOfQuarterRow, ArrayList<Integer> positionOfThirdRow, ArrayList<Integer> positionOfHalfRow) {
        gridLayoutManager = new GridLayoutManager(activity, GridLayoutSpanHelper.SPAN_ROM_STAIRS);
        GridLayoutSpanHelper.setupStairsCustomSpanPerEachRow(gridLayoutManager, positionOfQuarterRow, positionOfThirdRow, positionOfHalfRow);
        custom_recycle_good_for_good_trance_master.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void showHideBTNDelete(boolean showHide) {
        //no need here
    }

    @SafeVarargs
    @Override
    public final void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        custom_recycle_good_for_good_trance_master.setAdapter(recycleAdaptor[0]);
    }

    @Override
    public void loadAtEndList(IGoNextPage IGoNextPage) {
        //no need to done any work, data were loaded  before here in first time
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_good_for_good_trans, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupToolbar() {

    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_for_good_trans_act.setVisibility(showHide ? VISIBLE : GONE);
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
    //------------------------------------------------------------------------>>
    private void clickConfirmBTN() {
        if (collectionDataListener != null) {
            collectionDataListener.confirmClick();
        }
    }

    private void clickCancelBTN() {
        if (collectionDataListener != null) {
            collectionDataListener.cancelClick();
        }
    }


    void showHideConslayGoodForGoodTrance(boolean showHide) {
        conslay_good_for_good_trance.setVisibility(showHide ? VISIBLE : GONE);
    }

}
