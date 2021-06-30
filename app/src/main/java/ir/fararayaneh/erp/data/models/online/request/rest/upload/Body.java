package ir.fararayaneh.erp.data.models.online.request.rest.upload;

import ir.fararayaneh.erp.data.models.IModels;

public class Body implements IModels {

    private String attachmentGUID, mimeType, tag,description;

    public Body(String attachmentGUID, String mimeType, String tag, String description) {
        this.attachmentGUID = attachmentGUID;//guid+suffix
        this.mimeType = mimeType;
        this.tag = tag;
        this.description = description;
    }

    public String getAttachmentGUID() {
        return attachmentGUID;
    }

    public void setAttachmentGUID(String attachmentGUID) {
        this.attachmentGUID = attachmentGUID;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
