package ir.fararayaneh.erp.data.models.middle;

import ir.fararayaneh.erp.data.models.IModels;

/**
 * برای دریافت مسیج های بیشتر در یک چت روم
 * هی نشان دهنده اولین ای دی موجود در جدول است
 */
public class LoadMoreMessageModel implements IModels {

    private String id;

    public LoadMoreMessageModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
