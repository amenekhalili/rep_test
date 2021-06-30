package ir.fararayaneh.erp.adaptors.combo_form_adaptor;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.middle.ComboFormModel;

public class ComboFormRecHolderType19  extends BaseRecHolder {

    private ComboFormModel comboFormModel;

    public void setComboFormModel(ComboFormModel comboFormModel) {
        this.comboFormModel = comboFormModel;
    }

    private IClickRowComboFormRecHolderListener holderListener;
    @BindView(R.id.txt_title_row_form_type_19)
    AppCompatTextView txt_title_row_form_type_19;
    @BindView(R.id.edt_txt_value_row_form_type_19)
    AppCompatEditText edt_txt_value_row_form_type_19;
    @BindView(R.id.conslay_row_form_type_19)
    ConstraintLayout conslay_row_form_type_19;


    @OnTextChanged(R.id.edt_txt_value_row_form_type_19)
    protected void onTextChanged(CharSequence text) {
        Log.i("arash", "onTextChanged2: " + text);
        String myValue = text.toString();
        showHideViewManager(myValue);
        comboFormModel.setValue(myValue);
        if (holderListener != null) {
            holderListener.clickRow(getAdapterPosition(), myValue);
        }
    }

    private void showHideViewManager(String myValue) {

        if (myValue.equals(Commons.HIDE_VIEW_PLEASE)) {
            conslay_row_form_type_19.setVisibility(View.GONE);
        } else {
            conslay_row_form_type_19.setVisibility(View.VISIBLE);
        }
    }



    ComboFormRecHolderType19(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String title, String value) {
        txt_title_row_form_type_19.setText(title);
        edt_txt_value_row_form_type_19.setText(value.toLowerCase().equals(Commons.NULL) || value.equals(Commons.NULL_INTEGER) ? Commons.SPACE : value);
    }

}


