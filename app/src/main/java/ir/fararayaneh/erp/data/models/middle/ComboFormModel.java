package ir.fararayaneh.erp.data.models.middle;

import ir.fararayaneh.erp.data.models.IModels;

/**
 * در این مدل باید تمام اتربیوت هایی که
 * در تمام CommonComboFormType
 * و به عبارتی در تمام فایل های
 * xml
 *  مورد نیاز است قرار داده شود
 */
// TODO: 2/3/2019 اضافه کردن بقیه اتربیوت های مورد نیاز همه کومبو تایپ ها
public class ComboFormModel implements IModels {
    private String title,value,error;
    private boolean on;

    public ComboFormModel(String title, String value,String error,boolean on) {
        this.title = title;
        this.value = value;
        this.error = error;
        this.on = on;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
