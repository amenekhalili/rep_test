package ir.fararayaneh.erp.data.models.middle;

import ir.fararayaneh.erp.data.models.IModels;

/**
 * fileNme -->> abscdmn.mp3
 */
public class AttachAlbumRecycleModel implements IModels {

    private String fileNme;

    private DownloadState downloadState;

    private boolean canDeleteRow;

    public AttachAlbumRecycleModel(String fileNme, DownloadState downloadState, boolean canDeleteRow) {
        this.fileNme = fileNme;
        this.downloadState = downloadState;
        this.canDeleteRow = canDeleteRow;
    }

    public String getFileNme() {
        return fileNme;
    }

    public void setFileNme(String fileNme) {
        this.fileNme = fileNme;
    }

    public DownloadState getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(DownloadState downloadState) {
        this.downloadState = downloadState;
    }

    public boolean isCanDeleteRow() {
        return canDeleteRow;
    }

    public void setCanDeleteRow(boolean canDeleteRow) {
        this.canDeleteRow = canDeleteRow;
    }
}
