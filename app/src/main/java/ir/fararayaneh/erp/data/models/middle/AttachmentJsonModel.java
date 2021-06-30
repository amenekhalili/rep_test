package ir.fararayaneh.erp.data.models.middle;

import ir.fararayaneh.erp.data.models.IModels;

/**
 * attachmentGUID :full name of attachment : attachment+suffix
*/
public class AttachmentJsonModel implements IModels {

    private String attachmentGUID,columnNumber,B5HCTypeId;

    public AttachmentJsonModel(String attachmentGUIDSuffix, String columnNumber, String b5HCTypeId) {
        this.attachmentGUID = attachmentGUIDSuffix;
        this.columnNumber = columnNumber;
        this.B5HCTypeId = b5HCTypeId;
    }

    public String getAttachmentGUIDSuffix() {
        return attachmentGUID;
    }

    public void setAttachmentGUIDSuffix(String attachmentGUIDSuffix) {
        this.attachmentGUID = attachmentGUIDSuffix;
    }

    public String getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(String columnNumber) {
        this.columnNumber = columnNumber;
    }

    public String getB5HCTypeId() {
        return B5HCTypeId;
    }

    public void setB5HCTypeId(String B5HCTypeId) {
        this.B5HCTypeId = B5HCTypeId;
    }
}
