package ir.fararayaneh.erp.activities.attach_provider;

import android.net.Uri;

import java.util.ArrayList;

import io.reactivex.SingleEmitter;
import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface IAttachProviderListener extends IListeners {

    void cameraListener();

    void goToCompress(SingleEmitter<Uri> emitter, Uri uri);

    void URIListener(ArrayList<Uri> uriArrayList, int witchRequestFile);

    void voiceListener();

    void confirmListener(String tag,String description);

    void noResultListener();
}
