package ir.fararayaneh.erp.adaptors.selection_adaptor;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonViewTypeReport;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.custom_views.CustomCircleImageView;
import ir.fararayaneh.erp.utils.CalculationHelper;
import ir.fararayaneh.erp.utils.GetATTResources;
import ir.fararayaneh.erp.utils.PicassoHandler;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;

public class SelectionRecHolder extends BaseRecHolder {

    private IClickRowSelectionRecHolderListener holderListener;

    @BindView(R.id.img_attach_row_select)
    CustomCircleImageView img_attach_row_select;
    @BindView(R.id.txt_description_row_select)
    AppCompatTextView txt_description_row_select;
    @BindView(R.id.frameState)
    FrameLayout frameState;
    @BindView(R.id.img_plus_global_plus_minus)
    AppCompatImageView img_plus_global_plus_minus;
    @BindView(R.id.img_minus_global_plus_minus)
    AppCompatImageView img_minus_global_plus_minus;
    @BindView(R.id.edt_text_global_plus_minus)
    AppCompatEditText edt_text_global_plus_minus;
    @BindView(R.id.global_check_box)
    AppCompatCheckBox global_check_box;
    @BindView(R.id.include2)
    ConstraintLayout conslay_global_plus_minus;
    @BindView(R.id.parent_edt_curency_select)
    com.google.android.material.textfield.TextInputLayout parent_edt_curency_select;

    @BindView(R.id.parent_edt_discount_2_select)
    com.google.android.material.textfield.TextInputLayout parent_edt_discount_2_select;


    @BindView(R.id.parent_edt_discount_select)
    com.google.android.material.textfield.TextInputLayout parent_edt_discount_select;
    @BindView(R.id.parent_edt_tax_select)
    com.google.android.material.textfield.TextInputLayout parent_edt_tax_select;
    @BindView(R.id.edt_curency_select)
    com.google.android.material.textfield.TextInputEditText edt_curency_select;
    @BindView(R.id.edt_discount_select)
    com.google.android.material.textfield.TextInputEditText edt_discount_select;
    @BindView(R.id.edt_tax_select)
    com.google.android.material.textfield.TextInputEditText edt_tax_select;
    @BindView(R.id.edt_discount_2_select)
    com.google.android.material.textfield.TextInputEditText edt_discount_2_select;

    @BindView(R.id.conslay4)
    ConstraintLayout conslayEditableLay;


    private String idOfRow;


    @OnClick({R.id.img_plus_global_plus_minus, R.id.img_minus_global_plus_minus,R.id.txt_description_row_select,R.id.img_attach_row_select})
    public void clickSelectRecHolder(View view) {
        switch (view.getId()) {
            case R.id.img_plus_global_plus_minus:
                workForPlusValues();
                break;
            case R.id.img_minus_global_plus_minus:
                workForMinusValues();
                break;
            case R.id.txt_description_row_select:
                workForDialog();
                break;
            case R.id.img_attach_row_select:
                workForClickAttach();
                break;
        }
    }


    @OnCheckedChanged(R.id.global_check_box)
    void checkBoxSelectRecHolder(CompoundButton button, boolean checked) {
        if (holderListener != null) {
            holderListener.clickCheckBox(idOfRow,checked);
        }
    }

    private void workForDialog() {
        ArrayList<String> arrayList=new ArrayList<>(1);
        arrayList.add(txt_description_row_select.getText().toString());
        if (holderListener != null) {
            holderListener.showDialog(arrayList);
        }
    }

    private void workForMinusValues() {
        edt_text_global_plus_minus.setText(CalculationHelper.minusAnyAndRoundNonMoneyValueMinZero(Objects.requireNonNull(edt_text_global_plus_minus.getText()).toString(),Commons.PLUS_STRING,itemView.getContext()));
    }

    private void workForPlusValues() {
        edt_text_global_plus_minus.setText(CalculationHelper.plusAnyAndRoundNonMoneyValue(Objects.requireNonNull(edt_text_global_plus_minus.getText()).toString(),Commons.PLUS_STRING,itemView.getContext()));
    }

    private void workForClickAttach() {
        //if canUserSeeAttach=false, user can not click on it
        if (holderListener != null) {
            holderListener.seeAttach(getAdapterPosition());
        }
    }

    public SelectionRecHolder(View itemView, IClickRowSelectionRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);

        setupTextChangeListener();
    }

    private void setupTextChangeListener() {

        edt_text_global_plus_minus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (holderListener != null) {
                    holderListener.PlusMinusValue(idOfRow, editable.toString().trim());
                }
            }
        });

        edt_tax_select.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (holderListener != null) {
                    holderListener.PlusMinusTaxValue(idOfRow, editable.toString().trim());
                }
            }
        });

        edt_discount_select.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //in 274 we have only discount
                //in 284 we have only discount2
                if (holderListener != null) {
                    holderListener.PlusMinusDiscountValue(idOfRow, editable.toString().trim());
                }
            }
        });

        edt_discount_2_select.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //in 274 we have only discount
                //in 284 we have only discount2
                if (holderListener != null) {
                    holderListener.PlusMinusDiscountValue(idOfRow, editable.toString().trim());
                }
            }
        });

        edt_curency_select.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (holderListener != null) {
                    holderListener.PlusMinusCurrency(idOfRow, editable.toString().trim());
                }
            }
        });


    }


    /**
     * @param fileName: GUID.Suffix
     * @param viewType  : from CommonViewTypeReport
     */
    void setRowData(String idOfRow,String description, boolean canUserSeeAttach,String fileName,boolean canUserChangeValue,String values, int viewType,boolean isAccessDenied,boolean isChecked,boolean showCurrency,String currencyValue,boolean showDiscount,String discountValue,boolean showTax,String taxValue,boolean showDiscount2,String discount2Value) {

        this.idOfRow = idOfRow;
        setupDescription(description);
        setupState(viewType);
        showHideAttachImage(canUserSeeAttach,fileName);
        showHideCurrency(showCurrency,currencyValue);
        showHideDiscount(showDiscount,discountValue);
        showHideDiscount2(showDiscount2,discount2Value);
        showHideEditAbleLay(showTax,showDiscount,showCurrency);
        showHideTax(showTax,taxValue);
        showHideChangeNumber(canUserChangeValue,values);
        setupCheckedState(!isAccessDenied && isChecked);//if object were be access denied, remove its check box
        showHideCheckBox(isAccessDenied);
    }



    private void showHideEditAbleLay(boolean showTax, boolean showDiscount, boolean showCurrency) {
        conslayEditableLay.setVisibility(showTax || showDiscount || showCurrency?View.VISIBLE:View.GONE);
    }

    private void showHideTax(boolean showTax, String taxValue) {
        if(showTax){
            parent_edt_tax_select.setVisibility(View.VISIBLE);
            edt_tax_select.setText(taxValue);
        } else {
            parent_edt_tax_select.setVisibility(View.GONE);
        }
    }

    private void showHideDiscount(boolean showDiscount, String discountValue) {
        if(showDiscount){
            parent_edt_discount_select.setVisibility(View.VISIBLE);
            edt_discount_select.setText(discountValue);
        } else {
           parent_edt_discount_select.setVisibility(View.GONE);
        }
    }

    private void showHideDiscount2(boolean showDiscount2, String discount2Value) {
        if(showDiscount2){
            parent_edt_discount_2_select.setVisibility(View.VISIBLE);
            edt_discount_2_select.setText(discount2Value);
        } else {
            parent_edt_discount_2_select.setVisibility(View.GONE);
        }
    }

    private void showHideCurrency(boolean showCurrency, String currencyValue) {
        if(showCurrency){
            parent_edt_curency_select.setVisibility(View.VISIBLE);
            edt_curency_select.setText(currencyValue);
        } else {
            parent_edt_curency_select.setVisibility(View.GONE);
        }
    }

    private void showHideCheckBox(boolean isAccessDenied) {
        global_check_box.setVisibility(isAccessDenied?View.GONE:View.VISIBLE);
    }

    private void setupCheckedState(boolean isChecked) {
        global_check_box.setChecked(isChecked);
    }

    private void setupDescription(String description) {
        txt_description_row_select.setText(description);
    }

    private void showHideAttachImage(boolean canUserSeeAttach,String fileName) {
        img_attach_row_select.setVisibility(canUserSeeAttach?View.VISIBLE:View.GONE);
        if(canUserSeeAttach){
            PicassoHandler.setByPicasso(picasso, MimeTypeHandler.getFilePathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, fileName), MimeTypeHandler.getURLFromName(fileName), img_attach_row_select,false,Commons.SPACE , true, false);
        }
    }

    private void showHideChangeNumber(boolean canUserChangeValue,String values) {
        conslay_global_plus_minus.setVisibility(canUserChangeValue?View.VISIBLE:View.GONE);
        if(canUserChangeValue){
            edt_text_global_plus_minus.setText(values);
        }
    }

    //todo add other view type
    private void setupState(int viewType) {
        switch (viewType) {
            case CommonViewTypeReport.NORMAL_VIEW_TYPE:
                setupStateValues(itemView.getResources().getString(R.string.seen), itemView.getResources().getColor(GetATTResources.resId(itemView.getContext(), new int[]{R.attr.colorGrayAttr})));
                break;
            case CommonViewTypeReport.INSERTED_VIEW_TYPE:
                setupStateValues(itemView.getResources().getString(R.string.New), itemView.getResources().getColor(GetATTResources.resId(itemView.getContext(), new int[]{R.attr.colorGreenAttr})));
                break;
            case CommonViewTypeReport.DELETED_VIEW_TYPE:
                setupStateValues(itemView.getResources().getString(R.string.deleted), itemView.getResources().getColor(GetATTResources.resId(itemView.getContext(), new int[]{R.attr.colorBlueAttr})));
                break;
            case CommonViewTypeReport.ACCESS_DENIED_VIEW_TYPE:
                setupStateValues(itemView.getResources().getString(R.string.access_denied), itemView.getResources().getColor(GetATTResources.resId(itemView.getContext(), new int[]{R.attr.colorRedAttr})));
                break;
            case CommonViewTypeReport.EDITED_VIEW_TYPE:
                setupStateValues(itemView.getResources().getString(R.string.edited), itemView.getResources().getColor(GetATTResources.resId(itemView.getContext(), new int[]{R.attr.colorOrangeAttr})));
                break;
        }
    }

    private void setupStateValues(String state,int color) {
        //txt_state_row_select.setTextColor(color);
        //txt_state_row_select.setText(state);
        frameState.setBackgroundColor(color);

    }
}
