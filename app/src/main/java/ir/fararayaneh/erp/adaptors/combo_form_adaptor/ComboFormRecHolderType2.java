package ir.fararayaneh.erp.adaptors.combo_form_adaptor;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.graphics.Color;
import android.view.View;

import com.amulyakhare.textdrawable.TextDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;

public class ComboFormRecHolderType2 extends BaseRecHolder {

    private IClickRowComboFormRecHolderListener holderListener;
    @BindView(R.id.txt_title_row_form_type_2)
    AppCompatTextView txt_title_row_form_type_2;
    @BindView(R.id.txt_value_row_form_type_2)
    AppCompatTextView txt_value_row_form_type_2;
    @BindView(R.id.txt_error_row_form_type_2)
    AppCompatTextView txt_error_row_form_type_2;
    @BindView(R.id.img_row_form_type_2)
    AppCompatImageView img_row_form_type_2;

    @OnClick({R.id.txt_title_row_form_type_2, R.id.txt_value_row_form_type_2, R.id.txt_error_row_form_type_2,R.id.img_row_form_type_2})
    public void clickTimeSheetPlanRecHolder2(View view) {
        if(holderListener!=null){
            holderListener.clickRow(getAdapterPosition(),"");
        }
    }

    ComboFormRecHolderType2(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener=holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String title , String value ,String error) {

            txt_title_row_form_type_2.setText(title);
            showHideValue(!value.equals(Commons.NULL));
            showHideError(!error.equals(Commons.NULL));
            showHideImage(value.equals(Commons.NULL) && error.equals(Commons.NULL));
            txt_value_row_form_type_2.setText(value);
            txt_error_row_form_type_2.setText(error);
    }



    private void showHideValue(boolean showHide) {
        txt_value_row_form_type_2.setVisibility(showHide?View.VISIBLE:View.GONE);
    }

    private void showHideError(boolean showHide) {
        txt_error_row_form_type_2.setVisibility(showHide?View.VISIBLE:View.GONE);
    }

    private void showHideImage(boolean showHide){
        img_row_form_type_2.setVisibility(showHide?View.VISIBLE:View.GONE);
    }
}
