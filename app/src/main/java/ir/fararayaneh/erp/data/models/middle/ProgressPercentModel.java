package ir.fararayaneh.erp.data.models.middle;

import ir.fararayaneh.erp.data.models.IModels;

public class ProgressPercentModel implements IModels {
    private int currentPercentage;
    private String name;
    private byte downloadState; //from CommonUpdateTableState.class

    public int getCurrentPercentage() {
        return currentPercentage;
    }

    public void setCurrentPercentage(int currentPercentage) {
        this.currentPercentage = currentPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(byte downloadState) {
        this.downloadState = downloadState;
    }
}
