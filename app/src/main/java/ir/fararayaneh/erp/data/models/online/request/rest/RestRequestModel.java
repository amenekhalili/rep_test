package ir.fararayaneh.erp.data.models.online.request.rest;




import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.online.request.RootModel;

/**
 * this model is used for rest request
 */


  public class RestRequestModel extends RootModel implements IModels  {

    private Body body;

    public RestRequestModel(String kind,  String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber,String receivers,Body body) {
        super(kind,  organization, userId, appName,  hasFile, lang, pageNo, pageItem, chatRoomId,  nodeReceivedDate, errorNumber,receivers);
        this.body=body;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
