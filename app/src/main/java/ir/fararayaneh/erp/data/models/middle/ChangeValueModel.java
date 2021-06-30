package ir.fararayaneh.erp.data.models.middle;

import android.content.Context;

import java.io.Serializable;

import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * for save values in SelectionDBRecAdaptor
 *
 */
public class ChangeValueModel implements IModels , Serializable {

    public ChangeValueModel(Context context,String id) {
        this.id = id;
        taxValue = String.valueOf(SharedPreferenceProvider.getTaxPercent(context));
    }

    private String id,
            amount = Commons.NULL_INTEGER,//use for check user set proper amount or not
            currency = Commons.ZERO_STRING,//default value if user not set
            discountValue = Commons.ZERO_STRING,//default value if user not set
            taxValue ;//have default value in constructor

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public void setTaxValue(String taxValue) {
        this.taxValue = taxValue;
    }

    public String getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public String getTaxValue() {
        return taxValue;
    }
}
