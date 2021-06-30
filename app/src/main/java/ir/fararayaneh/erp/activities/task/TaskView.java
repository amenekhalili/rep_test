package ir.fararayaneh.erp.activities.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amulyakhare.textdrawable.TextDrawable;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.form_base.BaseViewCollectionDataForm;
import ir.fararayaneh.erp.IBase.common_base.IGoNextPage;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;
import ir.fararayaneh.erp.utils.GridLayoutSpanHelper;

public class TaskView extends BaseViewCollectionDataForm {

    @BindView(R.id.conslay_prog_task_act)
    ConstraintLayout conslay_prog_task_act;
    @BindView(R.id.conslay_task_task_act)
    ConstraintLayout conslay_task_task_act;
    @BindView(R.id.include6)
    ConstraintLayout conslay_global_delete_btn_layout;
    @BindView(R.id.custom_recycle_task_task_act)
    CustomRecycleView custom_recycle_task_task_act;
    @BindView(R.id.btn_confirm_global_confirm_cancel_btns)
    AppCompatButton btn_confirm_global_confirm_cancel_btns;
    @BindView(R.id.btn_cancel_global_confirm_cancel_btns)
    AppCompatButton btn_cancel_global_confirm_cancel_btns;
    @BindView(R.id.btn_delete_global_delete_btn_layout)
    AppCompatButton btn_delete_global_delete_btn_layout;

    @OnClick({R.id.btn_confirm_global_confirm_cancel_btns, R.id.btn_cancel_global_confirm_cancel_btns, R.id.btn_delete_global_delete_btn_layout})
    public void clickTaskView(View view) {
        switch (view.getId()) {
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

    public TaskView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected TaskView(@NonNull BaseActivity activity) {
        super(activity);
    }


    @Override
    public void setupCustomLayoutManager(int maximumSpanInOneRow, int nonMachParentRowSpanCount, ArrayList<Integer> positionOfNonMachParentRow) {
        gridLayoutManager = new GridLayoutManager(activity, maximumSpanInOneRow);
        GridLayoutSpanHelper.setupCustomSpanPerEachRow(gridLayoutManager, maximumSpanInOneRow, nonMachParentRowSpanCount, positionOfNonMachParentRow);
        custom_recycle_task_task_act.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void setupStairCustomLayoutManager(ArrayList<Integer> positionOfQuarterRow, ArrayList<Integer> positionOfThirdRow, ArrayList<Integer> positionOfHalfRow) {
        //no need any work because setupCustomLayoutManager
    }

    @Override
    public void showHideBTNDelete(boolean showHide) {
        conslay_global_delete_btn_layout.setVisibility(showHide ? VISIBLE : GONE);
    }

    @SafeVarargs
    @Override
    public final void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        custom_recycle_task_task_act.setAdapter(recycleAdaptor[0]);
    }

    @Override
    public void loadAtEndList(IGoNextPage IGoNextPage) {
        custom_recycle_task_task_act.setListenEndScroll(() -> {
            //no need to done any work, data were loaded  before here in first time
        });
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_task, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupToolbar() {

    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_task_act.setVisibility(showHide ? VISIBLE : GONE);
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
                case CommonRequestCodes.GET_URI_LIST_FROM_ALBUM_ATTACHMENT:
                    sendAttachmentToPresenter(data,false);
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

    void showHideConslayTask(boolean showHide) {
        conslay_task_task_act.setVisibility(showHide ? VISIBLE : GONE);
    }

}
