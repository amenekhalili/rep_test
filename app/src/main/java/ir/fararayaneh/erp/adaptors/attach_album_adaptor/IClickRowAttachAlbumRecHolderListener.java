package ir.fararayaneh.erp.adaptors.attach_album_adaptor;

public interface IClickRowAttachAlbumRecHolderListener {

    void clickRowToShowByIntent(int position);

    void clickRowToStartDownload(int position);

    void clickRowToCancelDownload(int position);

    void clickRowToDeleteAttach(int position);
}
