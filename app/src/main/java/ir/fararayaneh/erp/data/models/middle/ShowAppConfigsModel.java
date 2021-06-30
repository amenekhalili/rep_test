package ir.fararayaneh.erp.data.models.middle;

import ir.fararayaneh.erp.data.models.IModels;

/**
 * for showing list of enabled configs in config activity
 */
public class ShowAppConfigsModel implements IModels {
    private int title,descriptions,srcImageDrawable;

    public ShowAppConfigsModel(int title, int descriptions, int srcImageDrawable) {
        this.title = title;
        this.descriptions = descriptions;
        this.srcImageDrawable = srcImageDrawable;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(int descriptions) {
        this.descriptions = descriptions;
    }

    public int getSrcImageDrawable() {
        return srcImageDrawable;
    }

    public void setSrcImageDrawable(int srcImageDrawable) {
        this.srcImageDrawable = srcImageDrawable;
    }
}
