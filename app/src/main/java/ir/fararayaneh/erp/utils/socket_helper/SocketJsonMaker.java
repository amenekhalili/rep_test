package ir.fararayaneh.erp.utils.socket_helper;


import java.util.ArrayList;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonKind;
import ir.fararayaneh.erp.commons.CommonNetRequest;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.models.middle.ChatroomAddModel;
import ir.fararayaneh.erp.data.models.middle.ChatroomMemberAddModel;
import ir.fararayaneh.erp.data.models.middle.LoadMoreMessageModel;
import ir.fararayaneh.erp.data.models.middle.MessageEditModel;
import ir.fararayaneh.erp.data.models.online.request.socket.AccidentSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.AddressBookSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.AgrofieldSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.BaseCodingSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.CartableSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ChatroomAddSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ChatroomMemberAddSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ChatroomMemberSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ChatroomSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ContactBookSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.CountryDivisionSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.DeviceDeleteSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.FormRefSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.FuelListDetailSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.GoodTrancSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.GoodsSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.GroupRelatedSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.KartableReceiverSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.MessageEditSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.MessageMoreSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.MessageSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.PurchasableGoodsSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.SalableGoodsSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.TaskSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.TimeSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.UtilCodeSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.WarehouseOutSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.WarehouseSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.WeighingSocketModel;
import ir.fararayaneh.erp.data.models.middle.DeviceDeleteModel;
import ir.fararayaneh.erp.data.models.tables.AgroFieldTable;
import ir.fararayaneh.erp.data.models.tables.BaseCodingTable;
import ir.fararayaneh.erp.data.models.tables.FormRefTable;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.data.models.tables.GroupRelatedTable;
import ir.fararayaneh.erp.data.models.tables.KartableRecieverTable;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AccidentTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AddressBookTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CartableTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ContactBookTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CountryDivisionTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListDetailTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.GoodTranceTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.MessageTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.SalableGoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TaskTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TimeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingListOutTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WeighingTable;

public class SocketJsonMaker {

    public static String taskSocket(String organization, String userId, TaskTable taskTable) {
        return App.getmGson().toJson(new TaskSocketModel(CommonKind.SYNCTASK, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, taskTable));
    }

    public static String timeSocket(String organization, String userId, TimeTable timeTable) {
        return App.getmGson().toJson(new TimeSocketModel(CommonKind.SYNCTIME, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, timeTable));
    }

    public static String accidentSocket(String organization, String userId, AccidentTable accidentTable) {
        return App.getmGson().toJson(new AccidentSocketModel(CommonKind.SYNCACCIDENT, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, accidentTable));
    }



    public static String wareHouseSocket(String organization, String userId, WarehouseHandlingTable warehouseHandlingTable) {
        return App.getmGson().toJson(new WarehouseSocketModel(CommonKind.SYNCWAREHOUSEHANDLING, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, warehouseHandlingTable));
    }

    public static String cartableSocket(String organization, String userId, CartableTable cartableTable) {
        return App.getmGson().toJson(new CartableSocketModel(CommonKind.SYNCCARTABLE, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, cartableTable));
    }

    public static String chatroomMemberSocket(String organization, String userId, ChatroomMemberTable chatroomMemberTable) {
        return App.getmGson().toJson(new ChatroomMemberSocketModel(CommonKind.SYNCCHATROOMMEMBER, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, chatroomMemberTable));
    }

    public static String chatroomMemberAddSocket(String organization, String userId, ChatroomMemberTable chatroomMemberTable, ArrayList<ChatroomTable> chatroomTableList) {
        return App.getmGson().toJson(new ChatroomMemberAddSocketModel(CommonKind.SYNCCHATROOMMEMBERADD, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, new ChatroomMemberAddModel(chatroomMemberTable.getActivityKind(),chatroomMemberTable.getId(),chatroomMemberTable.getGuid(),chatroomMemberTable.getUserId(),chatroomMemberTable.getName(),chatroomMemberTable.getB5HCUserTypeId(),chatroomMemberTable.getChatroomId(),chatroomMemberTable.getChatroomGUID(),chatroomMemberTable.getSyncState(),chatroomMemberTable.getActivityState(),chatroomMemberTable.getAttach(),chatroomMemberTable.getIsMute(),chatroomMemberTable.getIsOnline(),chatroomMemberTable.getIsTyping(),chatroomMemberTable.getUnSeenCount(),chatroomTableList)));
    }

    public static String chatroomSocket(String organization, String userId, ChatroomTable chatroomTable) {
        return App.getmGson().toJson(new ChatroomSocketModel(CommonKind.SYNCCHATROOM, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, chatroomTable));
    }

    public static String chatroomAddSocket(String organization, String userId, ChatroomTable chatroomTable, ArrayList<ChatroomMemberTable> memberList) {
        return App.getmGson().toJson(new ChatroomAddSocketModel(CommonKind.SYNCCHATROOMADD, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, new ChatroomAddModel(chatroomTable.getId(),chatroomTable.getB5HCChatTypeId(),chatroomTable.getName(),chatroomTable.getDescription(),chatroomTable.getSyncState(),chatroomTable.getActivityState(),chatroomTable.getOldValue(),chatroomTable.getAttach(),chatroomTable.getGuid(),chatroomTable.getInsertDate(),memberList)));
    }

    public static String messageSocket(String organization, String userId, MessageTable messageTable) {
        return App.getmGson().toJson(new MessageSocketModel(CommonKind.SYNCMESSAGE, organization, userId, CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, messageTable));
    }

    public static String messageMoreSocket(String organization, String userId, LoadMoreMessageModel loadMoreMessageModel) {
        return App.getmGson().toJson(new MessageMoreSocketModel(CommonKind.SYNCMESSAGEMORE, organization, userId, CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NUMBER_MORE_MESSAGE, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP,loadMoreMessageModel ));
    }

    public static String messageEditSocket(String organization, String userId,String chatRoomId, MessageEditModel messageEditModel) {
        return App.getmGson().toJson(new MessageEditSocketModel(CommonKind.SYNCMESSAGEEDIT, organization, userId, CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, chatRoomId, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, messageEditModel));
    }

    public static String agroFieldSocket(String organization, String userId, AgroFieldTable agroFieldTable) {
        return App.getmGson().toJson(new AgrofieldSocketModel(CommonKind.SYNCAGROFIELD, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, agroFieldTable));
    }

    public static String baseCoddingSocket(String organization, String userId, BaseCodingTable baseCodingTable) {
        return App.getmGson().toJson(new BaseCodingSocketModel(CommonKind.SYNCBASECODING, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, baseCodingTable));
    }

    public static String formRefSocket(String organization, String userId, FormRefTable formRefTable) {
        return App.getmGson().toJson(new FormRefSocketModel(CommonKind.SYNCFORMREF, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, formRefTable));
    }

    public static String goodsSocket(String organization, String userId, GoodsTable goodsTable) {
        return App.getmGson().toJson(new GoodsSocketModel(CommonKind.SYNCGOODS, organization, userId, CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, goodsTable));
    }

    public static String groupRelatedSocket(String organization, String userId, GroupRelatedTable groupRelatedTable) {
        return App.getmGson().toJson(new GroupRelatedSocketModel(CommonKind.SYNCGROUPRELATED, organization, userId, CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, groupRelatedTable));
    }

    public static String kartableReceiverSocket(String organization, String userId, KartableRecieverTable kartableRecieverTable) {
        return App.getmGson().toJson(new KartableReceiverSocketModel(CommonKind.SYNCKARTABLERECIEVER, organization, userId, CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, kartableRecieverTable));
    }

    public static String deviceDeleteSocket(String organization, String userId, DeviceDeleteModel deviceDeleteModel) {
        return App.getmGson().toJson(new DeviceDeleteSocketModel(CommonKind.DEVICEDELETE, organization, userId, CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, deviceDeleteModel));
    }

    public static String utileCodeSocket(String organization, String userId, UtilCodeTable utilCodeTable) {
        return App.getmGson().toJson(new UtilCodeSocketModel(CommonKind.SYNCUTILCODE, organization, userId, CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, utilCodeTable));
    }

    public static String weighingSocket(String organization, String userId, WeighingTable weighingTable) {
        return App.getmGson().toJson(new WeighingSocketModel(CommonKind.SYNCWEIGHING, organization, userId, CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, weighingTable));
    }

    public static String warehouseOutSocket(String organization, String userId, WarehouseHandlingListOutTable warehouseHandlingListOutTable) {
        return App.getmGson().toJson(new WarehouseOutSocketModel(CommonKind.SYNCWAREHOUSEHANDLINGLISTOUT, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, warehouseHandlingListOutTable));
    }

    public static String goodTransSocket(String organization, String userId, GoodTranceTable goodTranceTable) {
        return App.getmGson().toJson(new GoodTrancSocketModel(CommonKind.SYNCGOODTRANCE, organization, userId, CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, goodTranceTable));
    }

    public static String purchasableGoodsSocket(String organization, String userId, PurchasableGoodsTable purchasableGoodsTable) {
        return App.getmGson().toJson(new PurchasableGoodsSocketModel(CommonKind.SYNCPURCHASABLEGOODS, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, purchasableGoodsTable));
    }

    public static String salableGoodsSocket(String organization, String userId, SalableGoodsTable salableGoodsTable) {
        return App.getmGson().toJson(new SalableGoodsSocketModel(CommonKind.SYNCSALABLEGOODS, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, salableGoodsTable));
    }

    public static String contactBookSocket(String organization, String userId, ContactBookTable contactBookTable) {
        return App.getmGson().toJson(new ContactBookSocketModel(CommonKind.SYNCCONTACTBOOK, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, contactBookTable));
    }

    public static String addressBookSocket(String organization, String userId, AddressBookTable addressBookTable) {
        return App.getmGson().toJson(new AddressBookSocketModel(CommonKind.SYNCADDRESSBOOK, organization, userId, CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, addressBookTable));
    }

    public static String countryDivisionSocket(String organization, String userId, CountryDivisionTable countryDivisionTable) {
        return App.getmGson().toJson(new CountryDivisionSocketModel(CommonKind.SYNCCOUNTRYDIVISION, organization, userId, CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, countryDivisionTable));
    }

    public static String fuelListSocket(String organization, String userId, FuelListDetailTable fuelListDetailTable) {
        return App.getmGson().toJson(new FuelListDetailSocketModel(CommonKind.SYNCFUELLISTDETAIL, organization, userId,CommonNetRequest.APP_NAME, CommonsDownloadFile.HAVE_NO_FILE, Commons.NULL_CAP, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.NULL_INTEGER, Commons.MINIMUM_TIME, Commons.NULL_INTEGER, Commons.NULL_CAP, fuelListDetailTable));
    }

}
