package ir.fararayaneh.erp.data.data_providers.online;


import com.rx2androidnetworking.Rx2AndroidNetworking;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.fararayaneh.erp.data.data_providers.IDataProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.AttachmentDownloadModel;

/**
 * upload file : with service foreGround , one after an other (can be cancelled)
 * download file : with RX and all in an "one by one manner" (if user close activity, download will be stopped)
 * download apk : with service foreGround (DownLoadAPKForGroundService.class)

 * GlobalModel should have url, fileName, path and position as tag
 */

public class DownloadFileProvider implements IDataProvider {


    @Override
    public Completable data(IModels iModels) {

        AttachmentDownloadModel downloadModel = (AttachmentDownloadModel) iModels;
        return Rx2AndroidNetworking
                .download(downloadModel.getUrl(),
                        downloadModel.getDownloadPath(),
                        downloadModel.getFileName())
                .setTag(downloadModel.getDownloadTag())
                .build()
                .getDownloadCompletable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
