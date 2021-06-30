package ir.fararayaneh.erp.IBase.common_base;

import android.content.Intent;
import android.net.Uri;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

/**
 * Uri is data.getData() in onActivityResult
 */
public interface ISendAttachmentToPersenter extends IListeners {

    void getAttachmentFromViewAttachProvider(Intent data);

    void getAttachmentFromViewAttachAlbum(Intent data);
}
