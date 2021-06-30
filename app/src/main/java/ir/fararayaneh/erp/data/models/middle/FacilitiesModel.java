package ir.fararayaneh.erp.data.models.middle;

import java.io.Serializable;

import ir.fararayaneh.erp.data.models.IModels;

/**
 * for showing proper facilities detail items in main activity
 */
public class FacilitiesModel implements IModels, Serializable {
    private String title,imageUrl;
    private String formCode;
    private boolean fake;

    public FacilitiesModel(String title, String formCode, String imageUrl, boolean fake) {
        this.title = title;
        this.formCode = formCode;
        this.imageUrl = imageUrl;
        this.fake = fake;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFake() {
        return fake;
    }

    public void setFake(boolean fake) {
        this.fake = fake;
    }
}
