package ir.fararayaneh.erp.data.models.online.request.socket;

import ir.fararayaneh.erp.data.models.online.request.RootModel;
import ir.fararayaneh.erp.data.models.middle.DeviceDeleteModel;

/**
 * این مدل نیازی به سینک و غیره ندارد و به محض دریافت در دیوایس فرآیند حذف
 * و پاکسازی دیتای یوزر در موبایل انجام میشود بنابراین فقط به شرطی یوزر این
 * پکت را باید از سوی سرور دریافت نماید که ارتباط بین دیویایس و یوزر در دیتابیس قطع شده باشد
 */

public class DeviceDeleteSocketModel extends RootModel implements ISocketModel {

    private DeviceDeleteModel body; //we set null in send socket, because here no need to body data

    public DeviceDeleteSocketModel(String kind, String organization, String userId, String appName, String hasFile, String lang, String pageNo, String pageItem, String chatRoomId, String nodeReceivedDate, String errorNumber, String receivers, DeviceDeleteModel body) {
        super(kind, organization, userId, appName, hasFile, lang, pageNo, pageItem, chatRoomId, nodeReceivedDate, errorNumber, receivers);
        this.body=body;
    }

    public DeviceDeleteModel getBody() {
        return body;
    }

    public void setBody(DeviceDeleteModel body) {
        this.body = body;
    }
}
