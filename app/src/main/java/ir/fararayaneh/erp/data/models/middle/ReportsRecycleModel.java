package ir.fararayaneh.erp.data.models.middle;


import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * for show data in reportsActivity recycle for any report
 * firstAttachName : (GUID.suffix) first attach show by (PicassoHandler.setByPicasso) if is image or not
 *
 * canUserClickForEdit : can user click on edit this row or not
 * canUserSeeAttach : can user click on attach image for see all attach(for some formCode like cartable user can not add or remove attachment)
 *
 * viewType : from CommonViewTypeReport
 *
 *
 */
public class ReportsRecycleModel implements IModels {

    private String descriptions,firstAttachName;
    private boolean canUserClickForEdit,canUserSeeAttach;
    private int viewType; //--->0 is normal view type by default
    private String guid;

    public ReportsRecycleModel(String descriptions, String firstAttachName, boolean canUserClickForEdit, boolean canUserSeeAttach, int viewType, String guid) {
        this.descriptions = descriptions;
        this.firstAttachName = firstAttachName;
        this.canUserClickForEdit = canUserClickForEdit;
        this.canUserSeeAttach = canUserSeeAttach;
        this.viewType = viewType;
        this.guid = guid;
    }

    public String getDescriptions() {
        return descriptions==null? Commons.NULL:descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getFirstAttachName() {
        return firstAttachName;
    }

    public void setFirstAttachName(String firstAttachName) {
        this.firstAttachName = firstAttachName;
    }

    public boolean isCanUserClickForEdit() {
        return canUserClickForEdit;
    }

    public void setCanUserClickForEdit(boolean canUserClickForEdit) {
        this.canUserClickForEdit = canUserClickForEdit;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public boolean isCanUserSeeAttach() {
        return canUserSeeAttach;
    }

    public void setCanUserSeeAttach(boolean canUserSeeAttach) {
        this.canUserSeeAttach = canUserSeeAttach;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
