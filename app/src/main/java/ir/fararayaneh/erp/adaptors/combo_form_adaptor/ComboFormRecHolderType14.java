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
 شبیه ویو 21
 */
public class ComboFormRecHolderType14 extends BaseRecHolder {

    private ComboFormModel comboFormModel;

    public void setComboFormModel(ComboFormModel comboFormModel) {
        this.comboFormModel = comboFormModel;
    }

    private IClickRowComboFormRecHolderListener holderListener;
    @BindView(R.id.txt_title_row_form_type_14)
    AppCompatTextView txt_title_row_form_type_14;
    @BindView(R.id.edt_txt_value_row_form_type_14)
    AppCompatEditText edt_txt_value_row_form_type_14;
    @BindView(R.id.conslay_row_form_type_14)
    ConstraintLayout conslay_row_form_type_14;


    @OnTextChanged(R.id.edt_txt_value_row_form_type_14)
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
            conslay_row_form_type_14.setVisibility(View.GONE);
        } else {
            conslay_row_form_type_14.setVisibility(View.VISIBLE);
        }
    }

    /*@OnFocusChange({R.id.edt_txt_value_row_form_type_4})
    void onFocusChange(View view, boolean hasFocus) {
        if(hasFocus){
            myPosition=getAdapterPosition();
        }
        else if (holderListener != null) {
            holderListener.clickRow(myPosition, myValue);
        }
        Log.i("arash", "onFocusChange: hasFocus:"+hasFocus+"***"+"myValue:"+myValue);
    }*/


    ComboFormRecHolderType14(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String title, String value) {
        txt_title_row_form_type_14.setText(title);
        edt_txt_value_row_form_type_14.setText(value.toLowerCase().equals(Commons.NULL) || value.equals(Commons.NULL_INTEGER) ? Commons.SPACE : value);
    }

}

