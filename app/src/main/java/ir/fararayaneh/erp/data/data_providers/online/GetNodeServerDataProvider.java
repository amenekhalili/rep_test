package ir.fararayaneh.erp.data.data_providers.online;


import com.androidnetworking.common.Priority;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.fararayaneh.erp.data.data_providers.IDataProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.online.response.RestResponseModel;

/**
 * for get app version from node
 */
public class GetNodeServerDataProvider implements IDataProvider {

    /**
     *
     * @param iModels : globalModel.getDownloadURL() ==>> address for get
   */

    @Override
    public Single<RestResponseModel> data(IModels iModels) {

        GlobalModel globalModel =(GlobalModel)iModels;

        return Rx2AndroidNetworking.
                get(globalModel.getDownloadURL())
                .setPriority(Priority.HIGH)
                .build()
                .getObjectSingle(RestResponseModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
