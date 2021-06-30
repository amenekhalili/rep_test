package ir.fararayaneh.erp.adaptors.combo_form_adaptor;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;

public class ComboFormRecHolderType1 extends BaseRecHolder {

    private IClickRowComboFormRecHolderListener holderListener;
    @BindView(R.id.txt_title_row_form_type_1)
    AppCompatTextView txt_title_row_form_type_1;
    @BindView(R.id.txt_value_row_form_type_1)
    AppCompatTextView txt_value_row_form_type_1;
    @BindView(R.id.txt_error_row_form_type_1)
    AppCompatTextView txt_error_row_form_type_1;
    @BindView(R.id.img_row_form_type_1)
    AppCompatImageView img_row_form_type_1;

    @OnClick({R.id.txt_title_row_form_type_1,R.id.txt_error_row_form_type_1, R.id.txt_value_row_form_type_1,R.id.img_row_form_type_1})
    public void clickComboFormRecHolderType1(View view) {
        if(holderListener!=null){
            holderListener.clickRow(getAdapterPosition(),"");
        }
    }

    ComboFormRecHolderType1(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener=holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String title,String value,String error) {

        txt_title_row_form_type_1.setText(title);
        showHideValue(error.equals(Commons.NULL));
        showHideError(!error.equals(Commons.NULL));
        if(error.equals(Commons.NULL)){
            if(!value.equals(Commons.NULL)){
                txt_value_row_form_type_1.setText(value);
            } else {
                txt_value_row_form_type_1.setText(itemView.getResources().getString(R.string.please_select));
            }
        } else {
            txt_error_row_form_type_1.setText(error);
        }
    }

    private void showHideValue(boolean showHide) {
        txt_value_row_form_type_1.setVisibility(showHide?View.VISIBLE:View.GONE);
    }
    private void showHideError(boolean showHide) {
        txt_error_row_form_type_1.setVisibility(showHide?View.VISIBLE:View.GONE);
    }
}