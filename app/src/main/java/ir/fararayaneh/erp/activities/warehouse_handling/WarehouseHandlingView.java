package ir.fararayaneh.erp.activities.warehouse_handling;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

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
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;
import ir.fararayaneh.erp.utils.EditTextCheckHelper;
import ir.fararayaneh.erp.utils.GridLayoutSpanHelper;

public class WarehouseHandlingView extends BaseViewCollectionDataForm {

    private IClickWareHoseListener iClickWareHoseListener;

    public void setiClickWareHoseListener(IClickWareHoseListener iClickWareHoseListener) {
        this.iClickWareHoseListener = iClickWareHoseListener;
    }

    @BindView(R.id.conslay_prog_warhouse_handle_act)
    ConstraintLayout conslay_prog_warhouse_handle_act;

    @BindView(R.id.conslay_warhouse_handle_act)
    ConstraintLayout conslay_warhouse_handle_act;

    @BindView(R.id.include6)
    ConstraintLayout conslay_global_delete_btn_layout;

    @BindView(R.id.custom_recycle_warhouse_handle_act)
    CustomRecycleView custom_recycle_warhouse_handle_act;

    @BindView(R.id.btn_finish_global_confirm_cancel_btns_type_2_)
    AppCompatButton btn_finish_global_confirm_cancel_btns_type_2_;

    @BindView(R.id.btn_next_global_confirm_type_2_cancel_btns_type_2_)
    AppCompatButton btn_next_global_confirm_type_2_cancel_btns_type_2_;

    @BindView(R.id.btn_delete_global_delete_btn_layout)
    AppCompatButton btn_delete_global_delete_btn_layout;

    //--------------------------for delete soon--------------------------------------------------------------------
    @BindView(R.id.global_float_btn_2)
    FloatingActionButton global_float_btn_2;
    @BindView(R.id.conslay_add_Goods_warehouse)
    ConstraintLayout conslay_add_Goods_warehouse;
    @BindView(R.id.edt_warehouse_code)
    TextInputEditText edt_warehouse_code;
    @BindView(R.id.edt_good_code)
    TextInputEditText edt_good_code;
    @BindView(R.id.edt_amount)
    TextInputEditText edt_amount;
    @BindView(R.id.edt_description_code)
    TextInputEditText edt_description_code;
    @BindView(R.id.btn_confirm_add_good_layout)
    AppCompatButton btn_confirm_add_good_layout;
    //--------------------------for delete soon--------------------------------------------------------------------

    @OnClick({R.id.btn_finish_global_confirm_cancel_btns_type_2_,R.id.btn_next_global_confirm_type_2_cancel_btns_type_2_,R.id.btn_delete_global_delete_btn_layout,R.id.global_float_btn_2,R.id.btn_confirm_add_good_layout})
    public void clickWerhoseHandleView(View view) {
        switch (view.getId()){
            case R.id.btn_finish_global_confirm_cancel_btns_type_2_:
                clickFinishBTN();
                break;
            case R.id.btn_next_global_confirm_type_2_cancel_btns_type_2_:
                clickNextBTN();
                break;
            case R.id.btn_delete_global_delete_btn_layout:
                clickDeleteBTN();
                break;
        //--------------------------for delete soon--------------------------------------------------------------------
            case R.id.global_float_btn_2:
                showHideConslayAddGoods(true);
                break;
            case R.id.btn_confirm_add_good_layout:
                clickConfirmAddGoodsBTN();
                break;
        //--------------------------for delete soon--------------------------------------------------------------------

        }
    }




    public WarehouseHandlingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WarehouseHandlingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected WarehouseHandlingView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    public void setupCustomLayoutManager(int maximumSpanInOneRow, int nonMachParentRowSpanCount, ArrayList<Integer> positionOfNonMachParentRow) {
        gridLayoutManager=new GridLayoutManager(activity,maximumSpanInOneRow);
        GridLayoutSpanHelper.setupCustomSpanPerEachRow(gridLayoutManager,maximumSpanInOneRow,nonMachParentRowSpanCount,positionOfNonMachParentRow);
        custom_recycle_warhouse_handle_act.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void setupStairCustomLayoutManager(ArrayList<Integer> positionOfQuarterRow, ArrayList<Integer> positionOfThirdRow, ArrayList<Integer> positionOfHalfRow) {
        //no need to any work because setupCustomLayoutManager
    }


    /**
     * in warehouse handling we can not see delete button
     */
    @Override
    public void showHideBTNDelete(boolean showHide) {
        conslay_global_delete_btn_layout.setVisibility(GONE);
    }

    @SafeVarargs
    @Override
    public final void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        custom_recycle_warhouse_handle_act.setAdapter(recycleAdaptor[0]);
    }

    @Override
    public void loadAtEndList(IGoNextPage IGoNextPage) {
        custom_recycle_warhouse_handle_act.setListenEndScroll(() -> {
            //no need to done any work, data were loaded  before here in first time
        });
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_warehouse_handling, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupToolbar() {

    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_warhouse_handle_act.setVisibility(showHide?VISIBLE:GONE);
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
                case CommonRequestCodes.GET_BARCODE:
                    sendBarcodeToPresenter(data);
                    break;
                case CommonRequestCodes.Do_SYNC:
                    showMessageSyncWereDoneSuccessfully();
                    break;
            }
        }

    }

    @Override
    protected void clearAllViewClickListeners() {

    }


    //---------------------------------------------------------------------
    private void clickFinishBTN() {
        if(collectionDataListener!=null){
            collectionDataListener.confirmClick();
        }
    }

    private void clickNextBTN() {
        if(collectionDataListener!=null){
            collectionDataListener.nextClick();
        }
    }

    /**
     * in warehouse handling we can not see delete button
     */
    private void clickDeleteBTN() {
        /*if(collectionDataListener!=null){
            collectionDataListener.deleteClick();
        }*/
    }

    void showHideConslayWareHouse(boolean showHide) {
        conslay_warhouse_handle_act.setVisibility(showHide?VISIBLE:GONE);
    }

    //--------------------------for delete soon--------------------------------------------------------------------
    private void clickConfirmAddGoodsBTN() {
        if(EditTextCheckHelper.checkWithRaiseError(edt_warehouse_code,getActivity())){
            if(EditTextCheckHelper.checkWithRaiseError(edt_good_code,getActivity())){
                if(EditTextCheckHelper.checkWithRaiseError(edt_amount,getActivity())){
                    if(iClickWareHoseListener!=null){
                        if(EditTextCheckHelper.checkWithoutRaiseError(edt_description_code)){
                            iClickWareHoseListener.clickWarehouseAddButton(Objects.requireNonNull(edt_warehouse_code.getText()).toString(),edt_good_code.getText().toString(),edt_amount.getText().toString(),edt_description_code.getText().toString());
                        } else {
                            iClickWareHoseListener.clickWarehouseAddButton(Objects.requireNonNull(edt_warehouse_code.getText()).toString(),edt_good_code.getText().toString(),edt_amount.getText().toString(), Commons.NULL);
                        }
                        showHideConslayAddGoods(false);
                    }
                }
            }
        }
    }
    public void showHideConslayAddGoods(boolean showHide) {
        conslay_add_Goods_warehouse.setVisibility(showHide?VISIBLE:GONE);
        if(!showHide){
            edt_amount.setText("");
            edt_description_code.setText("");
            edt_good_code.setText("");
            edt_warehouse_code.setText("");
        }
    }
//--------------------------for delete soon--------------------------------------------------------------------



}
