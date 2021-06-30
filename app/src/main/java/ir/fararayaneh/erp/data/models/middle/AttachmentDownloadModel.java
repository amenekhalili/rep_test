package ir.fararayaneh.erp.data.models.middle;

import ir.fararayaneh.erp.data.models.IModels;

/**
 * fileName : abcd.mp3
 * downloadPath : folder name of download
 * downloadTag : use for cancel download
 */
public class AttachmentDownloadModel implements IModels {

        private String url,downloadPath,fileName,downloadTag;

    public AttachmentDownloadModel(String url, String downloadPath, String fileName, String downloadTag) {
        this.url = url;
        this.downloadPath = downloadPath;
        this.fileName = fileName;
        this.downloadTag = downloadTag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadTag() {
        return downloadTag;
    }

    public void setDownloadTag(String downloadTag) {
        this.downloadTag = downloadTag;
    }
}
