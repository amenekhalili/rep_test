package ir.fararayaneh.erp.adaptors.combo_form_adaptor;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;

public class ComboFormRecHolderType20 extends BaseRecHolder {

    private IClickRowComboFormRecHolderListener holderListener;
    @BindView(R.id.txt_title_row_form_type_20)
    AppCompatTextView txt_title_row_form_type_20;
    @BindView(R.id.txt_value_row_form_type_20)
    AppCompatTextView txt_value_row_form_type_20;
    @BindView(R.id.txt_error_row_form_type_20)
    AppCompatTextView txt_error_row_form_type_20;

    @OnClick({R.id.txt_title_row_form_type_20, R.id.txt_value_row_form_type_20, R.id.txt_error_row_form_type_20})
    public void clickComboFormRecHolderType20(View view) {
        if (holderListener != null) {
            holderListener.clickRow(getAdapterPosition(), "");
        }
    }

    ComboFormRecHolderType20(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String title, String value, String error) {

        showHideTitle(!title.equals(Commons.NULL));
        showHideValue(!value.equals(Commons.NULL));
        showHideError(!error.equals(Commons.NULL));
        txt_title_row_form_type_20.setText(title);
        txt_value_row_form_type_20.setText(value);
        txt_error_row_form_type_20.setText(error);
    }

    private void showHideTitle(boolean showHide) {
        txt_title_row_form_type_20.setVisibility(showHide ? View.VISIBLE : View.GONE);
    }


    private void showHideValue(boolean showHide) {
        txt_value_row_form_type_20.setVisibility(showHide ? View.VISIBLE : View.GONE);
    }

    private void showHideError(boolean showHide) {
        txt_error_row_form_type_20.setVisibility(showHide ? View.VISIBLE : View.GONE);
    }
}
