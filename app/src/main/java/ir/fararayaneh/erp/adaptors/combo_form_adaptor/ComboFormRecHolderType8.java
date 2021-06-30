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

public class ComboFormRecHolderType8 extends BaseRecHolder {

    private IClickRowComboFormRecHolderListener holderListener;
    @BindView(R.id.txt_title_row_form_type_8)
    AppCompatTextView txt_title_row_form_type_8;
    @BindView(R.id.txt_value_row_form_type_8)
    AppCompatTextView txt_value_row_form_type_8;
    @BindView(R.id.txt_error_row_form_type_8)
    AppCompatTextView txt_error_row_form_type_8;
    @BindView(R.id.img_row_form_type_8)
    AppCompatImageView img_row_form_type_8;

    @OnClick({R.id.txt_title_row_form_type_8, R.id.txt_value_row_form_type_8, R.id.txt_error_row_form_type_8,R.id.img_row_form_type_8})
    public void clickTimeSheetPlanRecHolder8(View view) {
        if(holderListener!=null){
            holderListener.clickRow(getAdapterPosition(),"");
        }
    }

    ComboFormRecHolderType8(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener=holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String title , String value ,String error) {

        txt_title_row_form_type_8.setText(title);
        showHideValue(!value.equals(Commons.NULL));
        showHideError(!error.equals(Commons.NULL));
        showHideImage(value.equals(Commons.NULL) && error.equals(Commons.NULL));
        txt_value_row_form_type_8.setText(value);
        txt_error_row_form_type_8.setText(error);
    }



    private void showHideValue(boolean showHide) {
        txt_value_row_form_type_8.setVisibility(showHide?View.VISIBLE:View.GONE);
    }

    private void showHideError(boolean showHide) {
        txt_error_row_form_type_8.setVisibility(showHide?View.VISIBLE:View.GONE);
    }

    private void showHideImage(boolean showHide){
        img_row_form_type_8.setVisibility(showHide?View.VISIBLE:View.GONE);
    }
}
