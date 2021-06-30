package ir.fararayaneh.erp.activities.good_trance;

import android.app.Activity;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.IGoNextPage;
import ir.fararayaneh.erp.IBase.common_base.form_base.BaseViewCollectionDataForm;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;
import ir.fararayaneh.erp.utils.GridLayoutSpanHelper;

/**
 * this view have two recycler , one for master, an other for details,
 */

public class GoodTranceView extends BaseViewCollectionDataForm {

    @BindView(R.id.conslay_prog_good_trance_act)
    ConstraintLayout conslay_prog_good_trance_act;
    @BindView(R.id.conslay_good_trance_master_detail)
    ConstraintLayout conslay_good_trance_master_detail;
    @BindView(R.id.custom_recycle_good_trance_master)
    CustomRecycleView custom_recycle_good_trance_master;
    @BindView(R.id.custom_recycle_good_trance_detail)
    CustomRecycleView custom_recycle_good_trance_detail;
    @BindView(R.id.btn_confirm_global_confirm_cancel_delete_btns)
    AppCompatButton btn_confirm_global_confirm_cancel_delete_btns;
    @BindView(R.id.btn_cancel_global_confirm_cancel_delete_btns)
    AppCompatButton btn_cancel_global_confirm_cancel_delete_btns;
    @BindView(R.id.btn_delete_global_confirm_cancel_delete_btns)
    AppCompatButton btn_delete_global_confirm_cancel_delete_btns;


    private IGoodTranceListener iGoodTranceListener;

    public void setiGoodTranceListener(IGoodTranceListener iGoodTranceListener) {
        this.iGoodTranceListener = iGoodTranceListener;
    }

    @OnClick({R.id.btn_confirm_global_confirm_cancel_delete_btns, R.id.btn_cancel_global_confirm_cancel_delete_btns, R.id.btn_delete_global_confirm_cancel_delete_btns})
    public void clickGoodTranceView(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_global_confirm_cancel_delete_btns:
                clickConfirmBTN();
                break;
            case R.id.btn_cancel_global_confirm_cancel_delete_btns:
                clickCancelBTN();
                break;
            case R.id.btn_delete_global_confirm_cancel_delete_btns:
                clickDeleteBTN();
                break;
        }
    }

    public GoodTranceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GoodTranceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected GoodTranceView(@NonNull BaseActivity activity) {
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
        custom_recycle_good_trance_master.setLayoutManager(gridLayoutManager);

        //--setup layout manager for details:
        LinearLayoutManager detailLayoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);
        custom_recycle_good_trance_detail.setLayoutManager(detailLayoutManager);
    }

    @Override
    public void showHideBTNDelete(boolean showHide) {
        btn_delete_global_confirm_cancel_delete_btns.setVisibility(showHide ? VISIBLE : GONE);
    }

    @SafeVarargs
    @Override
    public final void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        custom_recycle_good_trance_master.setAdapter(recycleAdaptor[0]);
    }

    @Override
    public void loadAtEndList(IGoNextPage IGoNextPage) {
        //no need to done any work, data were loaded  before here in first time
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_good_trance, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupToolbar() {

    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_good_trance_act.setVisibility(showHide ? VISIBLE : GONE);
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
        } else {
            switch (requestCode) {
                case CommonRequestCodes.SELECTION_REPORT:
                    workForSelectionActResult(data);
                    break;
                case CommonRequestCodes.ONE_GOOD_FOR_GOOD_TRANS:
                    workForGoodForGoodTranceActResult(data);
                    break;
            }
        }
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

    private void clickDeleteBTN() {
        if (collectionDataListener != null) {
            collectionDataListener.deleteClick();
        }
    }

    void showHideConslayGoodTrance(boolean showHide) {
        conslay_good_trance_master_detail.setVisibility(showHide ? VISIBLE : GONE);
    }

    public final void setRecycleDetailAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder> recycleAdaptor) {
        custom_recycle_good_trance_detail.setAdapter(recycleAdaptor);
    }

    private void workForGoodForGoodTranceActResult(Intent data) {
        if (data != null) {
            if (data.getExtras() != null && iGoodTranceListener != null) {
                iGoodTranceListener.dataFromGoodForGoodTransAct(data);
            } else {
                showMessageSomeProblems(CommonsLogErrorNumber.error_63);
            }
        } else {
            showMessageSomeProblems(CommonsLogErrorNumber.error_64);
        }

    }


    private void workForSelectionActResult(Intent data) {
        if (data != null) {
            if (data.getExtras() != null && iGoodTranceListener != null) {
                iGoodTranceListener.dataFromSelectionAct(data);
            } else {
                showMessageSomeProblems(CommonsLogErrorNumber.error_21);
            }
        } else {
            showMessageSomeProblems(CommonsLogErrorNumber.error_22);
        }
    }

}
