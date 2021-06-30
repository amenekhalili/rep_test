package ir.fararayaneh.erp.adaptors.attach_album_adaptor;

public interface IClickAttachAlbumRecAdaptor {

    void clickRowToShowByIntent(int position);

    void clickRowToStartDownload(int position);

    void clickRowToCancelDownload(int position);

    void clickRowToDeleteAttach(int position);

}
