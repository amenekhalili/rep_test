package ir.fararayaneh.erp.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import android.os.Looper;
import android.util.Log;

import java.util.Arrays;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonKind;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsSocket;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
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
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
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
import ir.fararayaneh.erp.data.models.tables.sync_tables.AccidentTable;
import ir.fararayaneh.erp.utils.NotificationHelper;
import ir.fararayaneh.erp.utils.encription.AESHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.socket_helper.SocketDataBaseHelper;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonParser;

import static ir.fararayaneh.erp.commons.CommonNotification.NON_FOREGROUND_NOTIFICATIONS_ID;


/**
 * if we have not user id in shared preference, we do not come here
 */

public class SocketService extends Service {

    private Socket mSocket;
    private Emitter.Listener listenToNewMessage;
    private Emitter.Listener listenToDisconnect;
    private Emitter.Listener listenToConnect;
    private CompositeDisposable compositeDisposable;
    private static ISocketListener socketListener;
    private ServiceNotificationHandler serviceNotificationHandler;

    public static void setSocketlistener(ISocketListener socketListener) {
        SocketService.socketListener = socketListener;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("arash", "onCreate: SocketService ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("arash", "onStartCommand: SocketService");
        setupAll();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        socketDisConnection();
        clearDisposable();
        super.onDestroy();
    }

    private void setupAll() {
        if (App.getmSocket() == null) {
            Log.i("arash", "run: onStartCommand mSocket==null");
            /*
            support by call from work manager class from application class and fireBase
             */
        } else if (mSocket == null) {
            init();
            addDisposable(subscribeHandleMessage());
            threadConnection();

        }
    }

    private void init() {
        createSocket();
        createDisposable();
    }

    private void createSocket() {
        if (mSocket == null) {
            mSocket = App.getmSocket();
        }
    }

    private void createDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    private void threadConnection() {
        new Thread(this::socketConnection).start();
    }

    private void socketConnection() {
        //we are sure that socket is not null
        if (mSocket != null) {
            if (!mSocket.connected()) {
                mSocket.on(Socket.EVENT_CONNECT, listenToConnect);
                mSocket.on(CommonsSocket.SOCKET_EVENT_MESSAGE, listenToNewMessage);
                mSocket.on(Socket.EVENT_DISCONNECT, listenToDisconnect);
                mSocket.connect();
            }
        }
    }

    private void socketDisConnection() {
        if (mSocket != null) {
            mSocket.disconnect();
            mSocket.off(Socket.EVENT_CONNECT, listenToConnect);
            mSocket.off(CommonsSocket.SOCKET_EVENT_MESSAGE, listenToNewMessage);
            mSocket.off(Socket.EVENT_DISCONNECT, listenToDisconnect);
        }
    }

    private void addDisposable(Disposable disposable) {
        if (compositeDisposable != null)
            compositeDisposable.add(disposable);
    }

    private void clearDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    //--------------------------------------------------------------------------------
    @SuppressLint("CheckResult")
    private Disposable subscribeHandleMessage() {
        return handleMessage()
                .subscribe(SocketService.this::explorationJsonMessage,
                        throwable -> {
                            ThrowableLoggerHelper.logMyThrowable("socketMessage/subscribeHandleMessage/ " + throwable.getMessage());
                            addDisposable(subscribeHandleMessage());
                        });

    }

    private Flowable<ISocketModel> handleMessage() {

        return Flowable.create(SocketService.this::setupSocketListeners, BackpressureStrategy.BUFFER)
                .map(inputMessage -> AESHelper.decriptionAES(CommonsSocket.SOCKET_AES_KEY, inputMessage))
                .doAfterNext(jsonString -> {
                    Log.i("arash", "evacuate process 1");
                    addDisposable(SocketDataBaseHelper.workForUpdateEvacuateModel(jsonString));
                })
                .map(json -> {
                    //todo add other socket kind
                    switch (SocketJsonParser.getKind(json)) {
                        case CommonKind.SYNCTASK:
                            Log.i("arash", "handleMessage: task :" + json);
                            return App.getmGson().fromJson(json, TaskSocketModel.class);
                        case CommonKind.SYNCACCIDENT:
                            Log.i("arash", "handleMessage: accident :" + json);
                            return App.getmGson().fromJson(json, AccidentSocketModel.class);
                        case CommonKind.SYNCTIME:
                            Log.i("arash", "handleMessage: time: " + json);
                            return App.getmGson().fromJson(json, TimeSocketModel.class);
                        case CommonKind.SYNCCARTABLE:
                            Log.i("arash", "handleMessage: cartable: " + json);
                            return App.getmGson().fromJson(json, CartableSocketModel.class);
                        case CommonKind.SYNCWAREHOUSEHANDLING:
                            Log.i("arash", "handleMessage: warehouse: " + json);
                            return App.getmGson().fromJson(json, WarehouseSocketModel.class);
                        case CommonKind.SYNCCHATROOMMEMBER:
                            Log.i("arash", "handleMessage: SYNCCHATROOMMEMBER: " + json);
                            return App.getmGson().fromJson(json, ChatroomMemberSocketModel.class);
                        case CommonKind.SYNCCHATROOMMEMBERADD:
                            Log.i("arash", "handleMessage: SYNCCHATROOMMEMBERADD: " + json);
                            return App.getmGson().fromJson(json, ChatroomMemberAddSocketModel.class);
                        case CommonKind.SYNCCHATROOM:
                            Log.i("arash", "handleMessage: SYNCCHATROOM: " + json);
                            return App.getmGson().fromJson(json, ChatroomSocketModel.class);
                        case CommonKind.SYNCCHATROOMADD:
                            Log.i("arash", "handleMessage: SYNCCHATROOM: " + json);
                            return App.getmGson().fromJson(json, ChatroomAddSocketModel.class);
                        case CommonKind.SYNCMESSAGE:
                            Log.i("arash", "handleMessage: SYNCMESSAGE: " + json);
                            return App.getmGson().fromJson(json, MessageSocketModel.class);
                        case CommonKind.SYNCMESSAGEEDIT:
                            Log.i("arash", "handleMessage: SYNCMESSAGEEdit: " + json);
                            return App.getmGson().fromJson(json, MessageEditSocketModel.class);
                        case CommonKind.SYNCAGROFIELD:
                            Log.i("arash", "handleMessage: SYNCAGROFIELD: " + json);
                            return App.getmGson().fromJson(json, AgrofieldSocketModel.class);
                        case CommonKind.SYNCBASECODING:
                            Log.i("arash", "handleMessage: SYNCBASECODING: " + json);
                            return App.getmGson().fromJson(json, BaseCodingSocketModel.class);
                        case CommonKind.SYNCFORMREF:
                            Log.i("arash", "handleMessage: SYNCFORMREF: " + json);
                            return App.getmGson().fromJson(json, FormRefSocketModel.class);
                        case CommonKind.SYNCGOODS:
                            Log.i("arash", "handleMessage: SYNCGOODS: " + json);
                            return App.getmGson().fromJson(json, GoodsSocketModel.class);
                        case CommonKind.SYNCGROUPRELATED:
                            Log.i("arash", "handleMessage: SYNCGROUPRELATED: " + json);
                            return App.getmGson().fromJson(json, GroupRelatedSocketModel.class);
                        case CommonKind.SYNCKARTABLERECIEVER:
                            Log.i("arash", "handleMessage: SYNCKARTABLERECIEVER: " + json);
                            return App.getmGson().fromJson(json, KartableReceiverSocketModel.class);
                        case CommonKind.DEVICEDELETE:
                            Log.i("arash", "handleMessage: DEVICEDELETE: " + json);
                            return App.getmGson().fromJson(json, DeviceDeleteSocketModel.class);
                        case CommonKind.SYNCUTILCODE:
                            Log.i("arash", "handleMessage: SYNCUTILCODE: " + json);
                            return App.getmGson().fromJson(json, UtilCodeSocketModel.class);
                        case CommonKind.SYNCWEIGHING:
                            Log.i("arash", "handleMessage: SYNCWEIGHING: " + json);
                            return App.getmGson().fromJson(json, WeighingSocketModel.class);
                        case CommonKind.SYNCWAREHOUSEHANDLINGLISTOUT:
                            Log.i("arash", "handleMessage: SYNCWAREHOUSEHANDLINGLISTOUT: " + json);
                            return App.getmGson().fromJson(json, WarehouseOutSocketModel.class);
                        case CommonKind.SYNCGOODTRANCE:
                            Log.i("arash", "handleMessage: SYNCGOODTRANCE: " + json);
                            return App.getmGson().fromJson(json, GoodTrancSocketModel.class);
                        case CommonKind.SYNCPURCHASABLEGOODS:
                            Log.i("arash", "handleMessage: SYNCPURCHASABLEGOODS: " + json);
                            return App.getmGson().fromJson(json, PurchasableGoodsSocketModel.class);
                        case CommonKind.SYNCSALABLEGOODS:
                            Log.i("arash", "handleMessage: SYNCSALABLEGOODS: " + json);
                            return App.getmGson().fromJson(json, SalableGoodsSocketModel.class);
                        case CommonKind.SYNCCONTACTBOOK:
                            Log.i("arash", "handleMessage: SYNCCONTACTBOOK: " + json);
                            return App.getmGson().fromJson(json, ContactBookSocketModel.class);
                        case CommonKind.SYNCADDRESSBOOK:
                            Log.i("arash", "handleMessage: SYNCADDRESSBOOK: " + json);
                            return App.getmGson().fromJson(json, AddressBookSocketModel.class);
                        case CommonKind.SYNCCOUNTRYDIVISION:
                            Log.i("arash", "handleMessage: SYNCCOUNTRYDIVISION: " + json);
                            return App.getmGson().fromJson(json, CountryDivisionSocketModel.class);
                        case CommonKind.SYNCFUELLISTMASTER:
                            Log.i("arash", "handleMessage: FuelListMasterSocketModel: " + json);
                            return App.getmGson().fromJson(json, FuelListMasterSocketModel.class);
                        case CommonKind.SYNCFUELLISTDETAIL:
                            Log.i("arash", "handleMessage: FuelListDetailSocketModel: " + json);
                            return App.getmGson().fromJson(json, FuelListDetailSocketModel.class);
                        case CommonKind.SYNCONLINE:
                            Log.i("arash", "handleMessage: SYNCONLINE: " + json);
                            return App.getmGson().fromJson(json, UserIsOnlineSocketModel.class);
                        case CommonKind.SYNCOFFLINE:
                            Log.i("arash", "handleMessage: SYNCOFFLINE: " + json);
                            return App.getmGson().fromJson(json, UserIsOfflineSocketModel.class);
                    }
                    return null;
                });
    }

    private void setupSocketListeners(final FlowableEmitter<String> emitter) {
        listenToNewMessage = args -> {
            String inputMessage = (String) args[0];
            Log.i("arash", "from socket message "
                    + inputMessage
            );
            emitter.onNext(inputMessage);
        };
        listenToConnect = args -> {
            Log.i("arash", "from socket:Connect " + Arrays.toString(args));
            if (socketListener != null) {
                socketListener.SocketISConnect();
            }
            EvacuatePacketForeGroundService.intentToEvacuatePacketForeGroundService(App.getAppContext());
        };
        listenToDisconnect = args -> {
            Log.i("arash", "from socket:Disconnect " + Arrays.toString(args));
            if (socketListener != null) {
                socketListener.SocketIsDisConnect();
            }
        };
    }

    private void explorationJsonMessage(ISocketModel iSocketModel) {


        //todo add for other models
        if (iSocketModel instanceof TaskSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateTaskSocketModel(((TaskSocketModel) iSocketModel)));
            getInnerClassInstance().handelGlobalNotificationType1(getBaseContext().getString(R.string.task_word), ((TaskSocketModel) iSocketModel).getBody().getId());
            //--------------------------------------------
        } else if (iSocketModel instanceof TimeSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateTimeSocketModel(((TimeSocketModel) iSocketModel)));
            getInnerClassInstance().handelGlobalNotificationType1(getBaseContext().getString(R.string.time_sheet), ((TimeSocketModel) iSocketModel).getBody().getId());
            //--------------------------------------------
        } else if (iSocketModel instanceof AccidentSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateAccidentSocketModel(((AccidentSocketModel) iSocketModel)));
            getInnerClassInstance().handelGlobalNotificationType1(getBaseContext().getString(R.string.damage), ((AccidentSocketModel) iSocketModel).getBody().getSerial());
            //--------------------------------------------
        }else if (iSocketModel instanceof CartableSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateCartableSocketModel(((CartableSocketModel) iSocketModel)));
            getInnerClassInstance().handelGlobalNotificationType2(getBaseContext().getString(R.string.new_cartable), ((CartableSocketModel) iSocketModel).getBody().getSubject());
            //--------------------------------------------
        } else if (iSocketModel instanceof WarehouseSocketModel) {
            //no need to notification
            addDisposable(SocketDataBaseHelper.workForUpdateWareHouseSocketModel(((WarehouseSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof ChatroomMemberSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateChatroomMemberSocketModel(((ChatroomMemberSocketModel) iSocketModel)));
            getInnerClassInstance().handelMemberNotification((ChatroomMemberSocketModel) iSocketModel);
            //--------------------------------------------
        } else if (iSocketModel instanceof ChatroomMemberAddSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateChatroomMemberAddSocketModel(((ChatroomMemberAddSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof ChatroomSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateChatroomSocketModel(((ChatroomSocketModel) iSocketModel)));
            getInnerClassInstance().handelChatRoomNotification((ChatroomSocketModel) iSocketModel);
            //--------------------------------------------
        } else if (iSocketModel instanceof ChatroomAddSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateChatroomAddSocketModel(((ChatroomAddSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof MessageSocketModel) {
            //caution : maybe we have not chatRoomId in root(oldMessage)
            addDisposable(SocketDataBaseHelper.workForUpdateMessageSocketModel(((MessageSocketModel) iSocketModel)));
            getInnerClassInstance().handelMessageNotification((MessageSocketModel) iSocketModel);
            //--------------------------------------------
        } else if (iSocketModel instanceof MessageEditSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateMessageEditSocketModel(((MessageEditSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof AgrofieldSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateAgrofieldSocketModel(((AgrofieldSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof BaseCodingSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateBaseCodingSocketModel(((BaseCodingSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof FormRefSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateFormRefSocketModel(((FormRefSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof GoodsSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateGoodsSocketModel(((GoodsSocketModel) iSocketModel)));
            getInnerClassInstance().handelGlobalNotificationType2(getBaseContext().getString(R.string.new_goods), ((GoodsSocketModel) iSocketModel).getBody().getName());
            //--------------------------------------------
        } else if (iSocketModel instanceof GroupRelatedSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateGroupRelatedSocketModel(((GroupRelatedSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof KartableReceiverSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateKartableReceiverSocketModel(((KartableReceiverSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof DeviceDeleteSocketModel) {
            //for intent to splash, we need to ui thread
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> SocketDataBaseHelper.workForDeviceDeleteSocketModel(((DeviceDeleteSocketModel) iSocketModel), getApplicationContext()));
        } else if (iSocketModel instanceof UtilCodeSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateUtilCodeSocketModel(((UtilCodeSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof WeighingSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateWeighingSocketModel(((WeighingSocketModel) iSocketModel)));
            getInnerClassInstance().handelGlobalNotificationType2(getBaseContext().getString(R.string.weiging_report), getBaseContext().getString(R.string.new_message));
            //--------------------------------------------
        } else if (iSocketModel instanceof WarehouseOutSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateWareHouseOutSocketModel(((WarehouseOutSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof GoodTrancSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateGoodTrancSocketModel(((GoodTrancSocketModel) iSocketModel)));
            getInnerClassInstance().handelGlobalNotificationType1(getBaseContext().getString(R.string.invoice), ((GoodTrancSocketModel) iSocketModel).getBody().getId());
            //--------------------------------------------
        } else if (iSocketModel instanceof PurchasableGoodsSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdatePurchasableGoodsSocketModel(((PurchasableGoodsSocketModel) iSocketModel)));
            getInnerClassInstance().handelGlobalNotificationType2(getBaseContext().getString(R.string.new_purchas_able_goods), ((PurchasableGoodsSocketModel) iSocketModel).getBody().getGoodName());
            //--------------------------------------------
        } else if (iSocketModel instanceof SalableGoodsSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateSalableGoodsSocketModel(((SalableGoodsSocketModel) iSocketModel)));
            getInnerClassInstance().handelGlobalNotificationType2(getBaseContext().getString(R.string.new_sale_able_goods), ((SalableGoodsSocketModel) iSocketModel).getBody().getGoodName());
            //--------------------------------------------
        } else if (iSocketModel instanceof ContactBookSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateContactBookSocketModel(((ContactBookSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof AddressBookSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateAddressBookSocketModel(((AddressBookSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof CountryDivisionSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateCountryDivisionSocketModel(((CountryDivisionSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof FuelListMasterSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateFuelListMasterSocketModel(((FuelListMasterSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof FuelListDetailSocketModel) {
            addDisposable(SocketDataBaseHelper.workForUpdateFuelListDetailSocketModel(((FuelListDetailSocketModel) iSocketModel)));
            getInnerClassInstance().handelGlobalNotificationType2(getBaseContext().getString(R.string.new_fuel_details), getBaseContext().getString(R.string.track_code) + Commons.SPLITTER_OF_CONFIGS + ((FuelListDetailSocketModel) iSocketModel).getBody().getId());
            //--------------------------------------------
        } else if (iSocketModel instanceof UserIsOnlineSocketModel) {
            addDisposable(SocketDataBaseHelper.workForMakeUserOnline(((UserIsOnlineSocketModel) iSocketModel)));
            //--------------------------------------------
        } else if (iSocketModel instanceof UserIsOfflineSocketModel) {
            addDisposable(SocketDataBaseHelper.workForMakeUserOffline(((UserIsOfflineSocketModel) iSocketModel)));
            //--------------------------------------------
        }

        sendSocketForUiChange(iSocketModel);

    }

    //-----------------------------------------------------------------------------------

    /**
     * همه ‍پکت ها به یو آی هم فرستاده میشوند تا اگر نیاز است فعالیت خاصی انجام شود
     */
    private void sendSocketForUiChange(ISocketModel iSocketModel) {
        if (socketListener != null) {
            socketListener.listener(iSocketModel);
        }
    }
    //-----------------------------------------------------------------------------------

    /**
     * به دلیل محدودیت های Api 26
     * به بالا، فقط باید از فایربیس سرویس از طریق ورک منیجر یا زمانی که برنامه در فورگراند است این سرویس کال شود
     * یا به صورت فورگراند کال شود
     */
    public static void intentToSocketService(Context context) {
        context.startService(new Intent(context, SocketService.class));
    }

    //-----------------------------------------------------------------------------------
    private ServiceNotificationHandler getInnerClassInstance() {
        if (serviceNotificationHandler == null) {
            serviceNotificationHandler = new ServiceNotificationHandler();
        }
        return serviceNotificationHandler;
    }
    //-----------------------------------------------------------------------------------

    private class ServiceNotificationHandler {

        void handelGlobalNotificationType1(String subject, String id) {
            NotificationHelper.notif(getBaseContext(), subject + " " + getBaseContext().getString(R.string.were_sent), getBaseContext().getString(R.string.track_code) + Commons.SPLITTER_OF_CONFIGS + id, NON_FOREGROUND_NOTIFICATIONS_ID, true);
        }

        void handelGlobalNotificationType2(String title, String content) {
            NotificationHelper.notif(getBaseContext(), title, content, NON_FOREGROUND_NOTIFICATIONS_ID, true);
        }

        void handelMemberNotification(ChatroomMemberSocketModel iSocketModel) {
            if (iSocketModel.getBody().getActivityState().equals(CommonActivityState.DELETE) &&
                    iSocketModel.getBody().getUserId().equals(SharedPreferenceProvider.getUserId(getBaseContext()))) {
                getInnerClassInstance().handelGlobalNotificationType2(iSocketModel.getBody().getName(), getBaseContext().getString(R.string.delete_member_msg));
            }
        }

        void handelChatRoomNotification(ChatroomSocketModel iSocketModel) {
            if (iSocketModel.getBody().getActivityState().equals(CommonActivityState.DELETE)) {
                getInnerClassInstance().handelGlobalNotificationType2(iSocketModel.getBody().getName(), getBaseContext().getString(R.string.delete_chat_room_msg));
            }
        }

        void handelMessageNotification(MessageSocketModel iSocketModel) {
            if (iSocketModel.getBody().getIsOld().equals(Commons.IS_NOT_OLD_MESSAGE)) {
                if (!iSocketModel.getBody().getUserId().equals(SharedPreferenceProvider.getUserId(getBaseContext())) &&
                        !iSocketModel.getBody().getChatroomId().equals(SharedPreferenceProvider.getOnlineChatroom(getBaseContext()))
                ) {
                    getInnerClassInstance().handelGlobalNotificationType2(getBaseContext().getString(R.string.new_message), iSocketModel.getBody().getMessage());
                }
            }
        }
    }


}
