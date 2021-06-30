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

/**
 * ایت آداپتور برای ویویی است که کیبرد فقط دسیمال نامبر پر میکند
 * اما مقداری که در پرزنتر پر میشود استرینگ است
 *اگر مقداری که به اینجا میرسد -1 یا null
 * است تبدیل به SPACE میشود و برمیگردد و در
 * پرزنتر SPACE
 * چک شده و جلویش گرفته میشود
 شبیه ویو 14
 */
public class ComboFormRecHolderType21 extends BaseRecHolder {

    private ComboFormModel comboFormModel;

    public void setComboFormModel(ComboFormModel comboFormModel) {
        this.comboFormModel = comboFormModel;
    }

    private IClickRowComboFormRecHolderListener holderListener;
    @BindView(R.id.txt_title_row_form_type_21)
    AppCompatTextView txt_title_row_form_type_21;
    @BindView(R.id.edt_txt_value_row_form_type_21)
    AppCompatEditText edt_txt_value_row_form_type_21;
    @BindView(R.id.conslay_row_form_type_21)
    ConstraintLayout conslay_row_form_type_21;


    @OnTextChanged(R.id.edt_txt_value_row_form_type_21)
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
            conslay_row_form_type_21.setVisibility(View.GONE);
        } else {
            conslay_row_form_type_21.setVisibility(View.VISIBLE);
        }
    }

    ComboFormRecHolderType21(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String title, String value) {
        txt_title_row_form_type_21.setText(title);
        edt_txt_value_row_form_type_21.setText(value.toLowerCase().equals(Commons.NULL) || value.equals(Commons.NULL_INTEGER) ? Commons.SPACE : value);
    }

}

