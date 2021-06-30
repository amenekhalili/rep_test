package ir.fararayaneh.erp.utils.socket_helper;

import android.content.Context;
import android.util.Log;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.accident.InsertUpdateAccidentTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.address_book.InsertUpdateAddressBookServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.agrofield.InsertUpdateAgrofieldTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.base_codding.InsertUpdateBaseCoddingTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.cartable_queris.InsertUpdateCartableTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom.InsertUpdateChatroomChatRoomMemberTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom.InsertUpdateChatroomTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom_member.HandleMakeUserOfflineServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom_member.HandleMakeUserOnlineServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom_member.InsertUpdateChatroomMemberAddTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom_member.InsertUpdateChatroomMemberTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.contact_book.InsertUpdateContactBookServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.country_division.InsertUpdateCountryDivisionServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.evacuate.InsertUpdateEvacuateTableInSendProcessQuery;
import ir.fararayaneh.erp.data.data_providers.queries.evacuate.InsertUpdateEvacuateTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.form_ref.InsertUpdateFormRefTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.fuel_list_detail.InsertUpdateFuelListDetailTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.fuel_list_master.InsertUpdateFuelListMasterTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.good_trance.InsertUpdateGoodTrancTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.goods.InsertUpdateGoodsTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.group_related.InsertUpdateGroupRelatedTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.kartable_reciever.InsertUpdateKartableReceiverTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.message.InsertUpdateMessageEditTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.message.InsertUpdateMessageTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.purchasable_goods.InsertUpdatePurchasableGoodsServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.salable_goods.InsertUpdateSalableGoodsServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.task_queris.InsertUpdateTaskTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_queris.InsertUpdateTimeTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.util_code.InsertUpdateUtilCodeTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warehouse_out_query.InsertUpdateWarehouseOutTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.InsertUpdateWarehouseTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.weighing.InsertUpdateWeighingTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
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
import ir.fararayaneh.erp.data.models.online.request.socket.FuelListMasterSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.GoodTrancSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.GoodsSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.GroupRelatedSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.KartableReceiverSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.MessageEditSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.MessageSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.PurchasableGoodsSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.SalableGoodsSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.TaskSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.TimeSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.UserIsOfflineSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.UserIsOnlineSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.UtilCodeSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.WarehouseOutSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.WarehouseSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.WeighingSocketModel;
import ir.fararayaneh.erp.utils.data_handler.AllTBLListHelper;
import ir.fararayaneh.erp.utils.data_handler.ClearAllTablesHandler;
import ir.fararayaneh.erp.utils.data_handler.IClearAllTablesListener;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;

public class SocketDataBaseHelper {

    /**
     *insert in database even we get error from server in field error or in field syncState
     */


    public static Disposable workForUpdateTaskSocketModel(TaskSocketModel taskSocketModel) {

        InsertUpdateTaskTableServiceQuery insertUpdateTaskTableServiceQuery =(InsertUpdateTaskTableServiceQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_TASK_TABLE_SERVICE_QUERY);
        assert insertUpdateTaskTableServiceQuery != null;
        return insertUpdateTaskTableServiceQuery
                .data(taskSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error
                            if(!taskSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){

                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForSyncTaskSocketModel :error code from server: "+taskSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateTaskSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateTimeSocketModel(TimeSocketModel timeSocketModel) {

        InsertUpdateTimeTableServiceQuery insertUpdateTimeTableServiceQuery = (InsertUpdateTimeTableServiceQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_TIME_TABLE_SERVICE_QUERY);
        assert insertUpdateTimeTableServiceQuery != null;
        return insertUpdateTimeTableServiceQuery
                .data(timeSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!timeSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForSyncTimeSocketModel :error code from server: "+timeSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateTimeSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateAccidentSocketModel(AccidentSocketModel accidentSocketModel) {

        InsertUpdateAccidentTableServiceQuery InsertUpdateAccidentTableServiceQuery = (InsertUpdateAccidentTableServiceQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_ACCIDENT_TABLE_SERVICE_QUERY);
        assert InsertUpdateAccidentTableServiceQuery != null;
        return InsertUpdateAccidentTableServiceQuery
                .data(accidentSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!accidentSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForSyncAccidentSocketModel :error code from server: "+accidentSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateAccidentSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateCartableSocketModel(CartableSocketModel cartableSocketModel) {

        InsertUpdateCartableTableServiceQuery insertUpdateCartableTableServiceQuery = (InsertUpdateCartableTableServiceQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_CARTABLE_TABLE_SERVICE_QUERY);
        assert insertUpdateCartableTableServiceQuery != null;
        return insertUpdateCartableTableServiceQuery
                .data(cartableSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!cartableSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateCartableSocketModel :error code from server: "+cartableSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateCartableSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateWareHouseSocketModel(WarehouseSocketModel warehouseSocketModel) {
        InsertUpdateWarehouseTableServiceQuery insertUpdateWarehouseTableServiceQuery = (InsertUpdateWarehouseTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.Insert_Update_Warehouse_Table_Service_Query);
        assert insertUpdateWarehouseTableServiceQuery != null;
        return insertUpdateWarehouseTableServiceQuery
                .data(warehouseSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!warehouseSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForSyncCartableSocketModel :error code from server: "+warehouseSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateCartableSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateChatroomMemberSocketModel(ChatroomMemberSocketModel chatroomMemberSocketModel) {
        InsertUpdateChatroomMemberTableServiceQuery insertUpdateChatroomMemberTableServiceQuery = (InsertUpdateChatroomMemberTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_CHATROOM_MEMBER_TABLE_SERVICE_QUERY);
        assert insertUpdateChatroomMemberTableServiceQuery != null;
        return insertUpdateChatroomMemberTableServiceQuery
                .data(chatroomMemberSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!chatroomMemberSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateChatroomMemberSocketModel :error code from server: "+chatroomMemberSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateChatroomMemberSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateChatroomMemberAddSocketModel(ChatroomMemberAddSocketModel chatroomMemberAddSocketModel) {

        InsertUpdateChatroomMemberAddTableServiceQuery insertUpdateChatroomMemberAddTableServiceQuery = (InsertUpdateChatroomMemberAddTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_CHATROOMMEMBER_ADD_TABLE_SERVICE_QUERY);
        assert insertUpdateChatroomMemberAddTableServiceQuery != null;
        return insertUpdateChatroomMemberAddTableServiceQuery
                .data(chatroomMemberAddSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!chatroomMemberAddSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateChatroomMemberAddSocketModel :error code from server: "+chatroomMemberAddSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateChatroomMemberAddSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateChatroomSocketModel(ChatroomSocketModel chatroomSocketModel) {
        InsertUpdateChatroomTableServiceQuery insertUpdateChatroomTableServiceQuery = (InsertUpdateChatroomTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_CHATROOM_TABLE_SERVICE_QUERY);
        assert insertUpdateChatroomTableServiceQuery != null;
        return insertUpdateChatroomTableServiceQuery
                .data(chatroomSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!chatroomSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateChatroomSocketModel :error code from server: "+chatroomSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateChatroomSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateChatroomAddSocketModel(ChatroomAddSocketModel chatroomAddSocketModel) {

     InsertUpdateChatroomChatRoomMemberTableServiceQuery  insertUpdateChatroomChatRoomMemberTableServiceQuery=(InsertUpdateChatroomChatRoomMemberTableServiceQuery)
             FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_CHATROOM_CHATROOMMEMBER_TABLE_SERVICE_QUERY);
        assert insertUpdateChatroomChatRoomMemberTableServiceQuery != null;
        return insertUpdateChatroomChatRoomMemberTableServiceQuery
                .data(chatroomAddSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!chatroomAddSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateChatroomAddSocketModel :error code from server: "+chatroomAddSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateChatroomAddSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateMessageSocketModel(MessageSocketModel messageSocketModel) {
        InsertUpdateMessageTableServiceQuery insertUpdateMessageTableServiceQuery = (InsertUpdateMessageTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_MESSAGE_TABLE_SERVICE_QUERY);
        assert insertUpdateMessageTableServiceQuery != null;
        return insertUpdateMessageTableServiceQuery
                .data(messageSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!messageSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateMessageSocketModel :error code from server: "+messageSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateMessageSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateAgrofieldSocketModel(AgrofieldSocketModel agrofieldSocketModel) {
        InsertUpdateAgrofieldTableServiceQuery insertUpdateAgrofieldTableServiceQuery = (InsertUpdateAgrofieldTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_AGROFIELD_TABLE_SERVICE_QUERY);
        assert insertUpdateAgrofieldTableServiceQuery != null;
        return insertUpdateAgrofieldTableServiceQuery
                .data(agrofieldSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!agrofieldSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateAgrofieldSocketModel :error code from server: "+agrofieldSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateAgrofieldSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateBaseCodingSocketModel(BaseCodingSocketModel baseCodingSocketModel) {
        InsertUpdateBaseCoddingTableServiceQuery insertUpdateBaseCoddingTableServiceQuery = (InsertUpdateBaseCoddingTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_BASE_CODDING_TABLE_SERVICE_QUERY);
        assert insertUpdateBaseCoddingTableServiceQuery != null;
        return insertUpdateBaseCoddingTableServiceQuery
                .data(baseCodingSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!baseCodingSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateBaseCodingSocketModel :error code from server: "+baseCodingSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateBaseCodingSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateFormRefSocketModel(FormRefSocketModel formRefSocketModel) {
        InsertUpdateFormRefTableServiceQuery insertUpdateFormRefTableServiceQuery = (InsertUpdateFormRefTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_FORM_REF_TABLE_SERVICE_QUERY);
        assert insertUpdateFormRefTableServiceQuery != null;
        return insertUpdateFormRefTableServiceQuery
                .data(formRefSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!formRefSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateFormRefSocketModel :error code from server: "+formRefSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateFormRefSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateGoodsSocketModel(GoodsSocketModel goodsSocketModel) {
        InsertUpdateGoodsTableServiceQuery insertUpdateGoodsTableServiceQuery = (InsertUpdateGoodsTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_GOODS_TABLE_SERVICE_QUERY);
        assert insertUpdateGoodsTableServiceQuery != null;
        return insertUpdateGoodsTableServiceQuery
                .data(goodsSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!goodsSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateGoodsSocketModel :error code from server: "+goodsSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateGoodsSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateGroupRelatedSocketModel(GroupRelatedSocketModel groupRelatedSocketModel) {
        InsertUpdateGroupRelatedTableServiceQuery insertUpdateGroupRelatedTableServiceQuery = (InsertUpdateGroupRelatedTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_GROUP_RELATED_TABLE_SERVICE_QUERY);
        assert insertUpdateGroupRelatedTableServiceQuery != null;
        return insertUpdateGroupRelatedTableServiceQuery
                .data(groupRelatedSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!groupRelatedSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateGroupRelatedSocketModel :error code from server: "+groupRelatedSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateGroupRelatedSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateKartableReceiverSocketModel(KartableReceiverSocketModel kartableReceiverSocketModel) {
        InsertUpdateKartableReceiverTableServiceQuery insertUpdateKartableReceiverTableServiceQuery = (InsertUpdateKartableReceiverTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_KARTABLE_RECEIVER_TABLE_SERVICE_QUERY);
        assert insertUpdateKartableReceiverTableServiceQuery != null;
        return insertUpdateKartableReceiverTableServiceQuery
                .data(kartableReceiverSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!kartableReceiverSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateKartableReceiverSocketModel :error code from server: "+kartableReceiverSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateKartableReceiverSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateUtilCodeSocketModel(UtilCodeSocketModel utilCodeSocketModel) {
        InsertUpdateUtilCodeTableServiceQuery insertUpdateUtilCodeTableServiceQuery = (InsertUpdateUtilCodeTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_UTIL_CODE_TABLE_SERVICE_QUERY);
        assert insertUpdateUtilCodeTableServiceQuery != null;
        return insertUpdateUtilCodeTableServiceQuery
                .data(utilCodeSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!utilCodeSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateUtilCodeSocketModel :error code from server: "+utilCodeSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateUtilCodeSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateWeighingSocketModel(WeighingSocketModel weighingSocketModel) {
        InsertUpdateWeighingTableServiceQuery insertUpdateWeighingTableServiceQuery = (InsertUpdateWeighingTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_WEIGHING_TABLE_SERVICE_QUERY);
        assert insertUpdateWeighingTableServiceQuery != null;
        return insertUpdateWeighingTableServiceQuery
                .data(weighingSocketModel.getBody())
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!weighingSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateWeighingSocketModel :error code from server: "+weighingSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateWeighingSocketModel :"+throwable.getMessage()));

    }

    public static void workForDeviceDeleteSocketModel(DeviceDeleteSocketModel deviceDeleteSocketModel, Context context) {
        if(deviceDeleteSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
            if(deviceDeleteSocketModel.getBody().getDeviceId().equals(SharedPreferenceProvider.getDeviceId(context))&&
                    deviceDeleteSocketModel.getBody().getUserId().equals(SharedPreferenceProvider.getUserId(context))) {
                IClearAllTablesListener iClearAllTablesListener = new IClearAllTablesListener() {
                    @Override
                    public void done() {
                        SharedPreferenceProvider.clearAllPreferences(context);
                        if(App.getmSocket()!=null){
                            App.getmSocket().disconnect();
                            App.setmSocket(null);
                        }
                        ActivityIntents.goSplashActivity(context);
                    }

                    @Override
                    public void error() {
                        ToastMessage.nonUiToast(context,CommonsLogErrorNumber.error_112);
                        Log.i("arash", "error: **************************");
                    }
                };
                //todo add disposable here
                ClearAllTablesHandler.clear(iClearAllTablesListener, AllTBLListHelper.getListOfAllTables());
            }

        }
    }

    public static Disposable workForUpdateWareHouseOutSocketModel(WarehouseOutSocketModel iSocketModel) {
        InsertUpdateWarehouseOutTableServiceQuery serviceQuery =(InsertUpdateWarehouseOutTableServiceQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_WAREHOUSE_OUT_TABLE_SERVICE_QUERY) ;
        assert serviceQuery != null;
        return serviceQuery.data(iSocketModel.getBody()).subscribe(iModels -> {
            //log error that is in packet
            if(!iSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateWareHouseOutSocketModel :error code from server: "+iSocketModel.getErrorNumber());
            }
        },
                throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateWareHouseOutSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateGoodTrancSocketModel(GoodTrancSocketModel iSocketModel) {

        InsertUpdateGoodTrancTableServiceQuery serviceQuery =(InsertUpdateGoodTrancTableServiceQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_GOOD_TRANC_TABLE_SERVICE_QUERY) ;
        assert serviceQuery != null;
        return serviceQuery.data(iSocketModel.getBody()).subscribe(iModels -> {
                    //log error that is in packet
                    if(!iSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                        ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateGoodTrancSocketModel :error code from server: "+iSocketModel.getErrorNumber());
                    }
                },
                throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateGoodTrancSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdatePurchasableGoodsSocketModel(PurchasableGoodsSocketModel iSocketModel) {
        InsertUpdatePurchasableGoodsServiceQuery serviceQuery =(InsertUpdatePurchasableGoodsServiceQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_PURCHASABLE_GOODS_SERVICE_QUERY) ;
        assert serviceQuery != null;
        return serviceQuery.data(iSocketModel.getBody()).subscribe(iModels -> {
                    //log error that is in packet
                    if(!iSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                        ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdatePurchasableGoodsSocketModel :error code from server: "+iSocketModel.getErrorNumber());
                    }
                },
                throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdatePurchasableGoodsSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateSalableGoodsSocketModel(SalableGoodsSocketModel iSocketModel) {
        InsertUpdateSalableGoodsServiceQuery serviceQuery =(InsertUpdateSalableGoodsServiceQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_SALABLE_GOODS_SERVICE_QUERY) ;
        assert serviceQuery != null;
        return serviceQuery.data(iSocketModel.getBody()).subscribe(iModels -> {
                    //log error that is in packet
                    if(!iSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                        ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateSalableGoodsSocketModel :error code from server: "+iSocketModel.getErrorNumber());
                    }
                },
                throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateSalableGoodsSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateContactBookSocketModel(ContactBookSocketModel iSocketModel) {
        InsertUpdateContactBookServiceQuery serviceQuery =(InsertUpdateContactBookServiceQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_CONTACT_BOOK_SERVICE_QUERY) ;
        assert serviceQuery != null;
        return serviceQuery.data(iSocketModel.getBody()).subscribe(iModels -> {
                    //log error that is in packet
                    if(!iSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                        ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateContactBookSocketModel :error code from server: "+iSocketModel.getErrorNumber());
                    }
                },
                throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateContactBookSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateAddressBookSocketModel(AddressBookSocketModel iSocketModel) {
        InsertUpdateAddressBookServiceQuery serviceQuery =(InsertUpdateAddressBookServiceQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_ADDRESS_BOOK_SERVICE_QUERY) ;
        assert serviceQuery != null;
        return serviceQuery.data(iSocketModel.getBody()).subscribe(iModels -> {
                    //log error that is in packet
                    if(!iSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                        ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateAddressBookSocketModel :error code from server: "+iSocketModel.getErrorNumber());
                    }
                },
                throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateAddressBookSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateCountryDivisionSocketModel(CountryDivisionSocketModel iSocketModel) {
        InsertUpdateCountryDivisionServiceQuery serviceQuery =(InsertUpdateCountryDivisionServiceQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_COUNTRY_DIVISION_SERVICE_QUERY) ;
        assert serviceQuery != null;
        return serviceQuery.data(iSocketModel.getBody()).subscribe(iModels -> {
                    //log error that is in packet
                    if(!iSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                        ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateCountryDivisionSocketModel :error code from server: "+iSocketModel.getErrorNumber());
                    }
                },
                throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateCountryDivisionSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateMessageEditSocketModel(MessageEditSocketModel iSocketModel) {

        InsertUpdateMessageEditTableServiceQuery insertUpdateMessageEditTableServiceQuery = (InsertUpdateMessageEditTableServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_MESSAGE_EDIT_TABLE_SERVICE_QUERY);
        assert insertUpdateMessageEditTableServiceQuery != null;
        return insertUpdateMessageEditTableServiceQuery
                .data(iSocketModel)
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!iSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateMessageEditSocketModel :error code from server: "+iSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateMessageEditSocketModel :"+throwable.getMessage()));

    }

    public static Disposable workForMakeUserOnline(UserIsOnlineSocketModel iSocketModel) {

        HandleMakeUserOnlineServiceQuery handleMakeUserOnlineServiceQuery = (HandleMakeUserOnlineServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.HANDLE_MAKE_USER_ONLINE_SERVICE_QUERY);
        assert handleMakeUserOnlineServiceQuery != null;
        return handleMakeUserOnlineServiceQuery
                .data(iSocketModel)
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!iSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForMakeUserOnline :error code from server: "+iSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForMakeUserOnline :"+throwable.getMessage()));

    }

    public static Disposable workForMakeUserOffline(UserIsOfflineSocketModel iSocketModel) {

        HandleMakeUserOfflineServiceQuery handleMakeUserOfflineServiceQuery = (HandleMakeUserOfflineServiceQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.HANDLE_MAKE_USER_OFFLINE_SERVICE_QUERY);
        assert handleMakeUserOfflineServiceQuery != null;
        return handleMakeUserOfflineServiceQuery
                .data(iSocketModel)
                .subscribe(
                        iModels -> {
                            //log error that is in packet
                            if(!iSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForMakeUserOffline :error code from server: "+iSocketModel.getErrorNumber());
                            }
                        },
                        throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForMakeUserOffline :"+throwable.getMessage()));

    }

    public static Disposable workForUpdateFuelListMasterSocketModel(FuelListMasterSocketModel iSocketModel) {

        InsertUpdateFuelListMasterTableServiceQuery serviceQuery =(InsertUpdateFuelListMasterTableServiceQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_FUEL_LIST_MASTER_TABLE_SERVICE_QUERY) ;
        assert serviceQuery != null;
        return serviceQuery.data(iSocketModel.getBody()).subscribe(iModels -> {
                    //log error that is in packet
                    if(!iSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                        ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateFuelListMasterSocketModel :error code from server: "+iSocketModel.getErrorNumber());
                    }
                },
                throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateFuelListMasterSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateFuelListDetailSocketModel(FuelListDetailSocketModel iSocketModel) {

        InsertUpdateFuelListDetailTableServiceQuery serviceQuery =(InsertUpdateFuelListDetailTableServiceQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_FUEL_LIST_DETAIL_TABLE_SERVICE_QUERY) ;
        assert serviceQuery != null;
        return serviceQuery.data(iSocketModel.getBody()).subscribe(iModels -> {
                    //log error that is in packet
                    if(!iSocketModel.getErrorNumber().toLowerCase().equals(Commons.NULL_INTEGER)){
                        ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateFuelListDetailSocketModel :error code from server: "+iSocketModel.getErrorNumber());
                    }
                },
                throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateFuelListDetailSocketModel :"+throwable.getMessage()));
    }

    public static Disposable workForUpdateEvacuateModel(String jsonString) {
        /*
        چه سینک استیت ما سینکد یا اکسس دیناید هست
         از جدول Evacuate
        حذف میشود
        */
      InsertUpdateEvacuateTableServiceQuery serviceQuery=(InsertUpdateEvacuateTableServiceQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_EVACUATE_TABLE_SERVICE_QUERY);
      assert serviceQuery != null;
      GlobalModel globalModel = new GlobalModel();
      globalModel.setMyString(jsonString);
      return serviceQuery.data(globalModel).subscribe(iModels -> Log.i("arash", "evacuate process 2: "+jsonString), throwable -> ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateEvacuateModel :"+throwable.getMessage()));

    }

    static void workForUpdateEvacuateInSendProcess(String jsonPacket) {
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(jsonPacket);
        InsertUpdateEvacuateTableInSendProcessQuery insertUpdateEvacuateTableInSendProcessQuery =
                (InsertUpdateEvacuateTableInSendProcessQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_EVACUATE_TABLE_IN_SEND_PROCESS);
        assert insertUpdateEvacuateTableInSendProcessQuery != null;
        insertUpdateEvacuateTableInSendProcessQuery.data(globalModel).subscribe(new Consumer<IModels>() {
            @Override
            public void accept(IModels iModels) throws Exception {
                Log.i("arash", "SocketDataBaseHelper/workForUpdateEvacuateInSendProcess :were done");

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ThrowableLoggerHelper.logMyThrowable("SocketDataBaseHelper/workForUpdateEvacuateInSendProcess error:"+throwable.getMessage());
            }
        });
    }
}
