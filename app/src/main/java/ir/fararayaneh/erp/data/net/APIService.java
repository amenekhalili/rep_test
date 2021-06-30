package ir.fararayaneh.erp.data.net;


import io.reactivex.Single;
import ir.fararayaneh.erp.commons.CommonUrls;
import ir.fararayaneh.erp.data.models.online.response.RestResponseModel;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    /**
     *
     * @param kind is same kind in json of getReqModel

     */
    @GET(CommonUrls.KIND_AC)
    Single<RestResponseModel> getData
            (
                    @Path(value = CommonUrls.KIND, encoded = true) String kind,
                    @Query(CommonUrls.PAGE) int pageNumber,
                    @Query(CommonUrls.getReqModel) String getRequest
            );

    @POST(CommonUrls.KIND_AC)
    @Multipart
    Single<ResponseBody> postData
            (
                    @Path(value = CommonUrls.KIND, encoded = true) String kind,
                    @Part(CommonUrls.REQUEST_JSON_FIELD_NAME) RequestBody requestBody
            );


}
