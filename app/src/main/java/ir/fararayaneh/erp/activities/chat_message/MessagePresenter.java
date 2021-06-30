package ir.fararayaneh.erp.activities.chat_message;

import android.content.Context;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.lang.ref.WeakReference;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.IBase.common_base.BasePresenterWithRecycle;
import ir.fararayaneh.erp.adaptors.message_adaptor.IClickMessageRecAdaptor;
import ir.fararayaneh.erp.adaptors.message_adaptor.MessageRecAdaptor;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonChatRoomToolbarState;
import ir.fararayaneh.erp.commons.CommonChatroomTypeCode;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonMessageTypeCode;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.CommonTimeZone;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom_member.HandleAddUnreadMessageChatMembersQuery;
import ir.fararayaneh.erp.data.data_providers.queries.message.InsertUpdateMessageTableQuery;
import ir.fararayaneh.erp.data.data_providers.queries.recycle_list_view_queries.report_quries.ReportQueries;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.middle.LoadMoreMessageModel;
import ir.fararayaneh.erp.data.models.middle.MessageEditModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.MessageTable;
import ir.fararayaneh.erp.utils.CalculationHelper;
import ir.fararayaneh.erp.utils.NotificationHelper;
import ir.fararayaneh.erp.utils.UUIDHelper;
import ir.fararayaneh.erp.utils.data_handler.AttachJsonCreator;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;


public class MessagePresenter extends BasePresenterWithRecycle<MessageView> {

    private MessageRecAdaptor messageRecAdaptor;
    private IClickMessageRecAdaptor clickMessageRecAdaptor;
    private IChatMessageProvider iChatMessageProvider;
    private Realm realm;
    private ReportQueries reportQueries;
    private String chatRoomId = Commons.NULL_INTEGER;
    private String chatRoomType = CommonChatroomTypeCode.NULL_CHATROOM_TYPE;
    private String userId = Commons.NULL_INTEGER;
    private String userB5HCMessageTypeId = Commons.NULL_INTEGER, systemB5HCMessageTypeId = Commons.NULL_INTEGER;
    private String chatRoomAttachJson = Commons.NULL_INTEGER;
    private String chatRoomGUID = Commons.NULL_INTEGER;
    private boolean canGetOldMessage = true;

    public MessagePresenter(WeakReference<MessageView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }


    @Override
    protected UserMessageModel makeProperShowDialogueData(int dialogueType, int commonComboClickType, String someValue) {
        return null;
    }

    @Override
    protected void workForListenToMSGOrDialoge(int whichDialog, int whichClick, IModels iModels) {

    }

    @Override
    protected void updateUIWhenReceivedAttachment() {
        /*
         * ذخیره یک پیام برای  اتچمنتهای دریافت شده
         * ارسال یک پیام برای اتچمنتهای دریافت شده
         * شروع روند آپلود اتچمنت ها
         *
         */
        addDisposable(workForSendMessage(Commons.SPACE, userB5HCMessageTypeId));
    }


    private Disposable workForSendMessage(String message, String b5hcMsgtypeId) {

        MessageTable messageTable = new MessageTable(Commons.NULL_INTEGER, Commons.NULL_INTEGER, chatRoomId, b5hcMsgtypeId
                , userId,
                CustomTimeHelper.longLocalDateToOtherLocalConverter(CommonTimeZone.SERVER_TIME_ZONE, CustomTimeHelper.getCurrentDate().getTime(), CommonsFormat.DATE_SHARE_FORMAT_FROM_STRING)
                , Commons.MINIMUM_TIME, Commons.MINIMUM_TIME, Commons.MINIMUM_TIME, CommonSyncState.BEFORE_SYNC,
                CommonActivityState.ADD, message, AttachJsonCreator.buildJson(attachNameList, attachColumnIdList, attachB5HCTypeIdList)
                , UUIDHelper.getProperUUID(), Commons.IS_NOT_DELETE_FOR_ME, Commons.NULL_INTEGER, Commons.NULL_INTEGER,Commons.IS_NOT_OLD_MESSAGE,CustomTimeHelper.dateToLongConvertor(null));//todo use CustomTimeHelper.getLocalGreenwichLongTime()

        InsertUpdateMessageTableQuery insertUpdateMessageTableQuery = (InsertUpdateMessageTableQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_MESSAGE_TABLE_QUERY);
        assert insertUpdateMessageTableQuery != null;
        return insertUpdateMessageTableQuery.data(messageTable).subscribe(iModels -> {
            if (checkNull()) {
                uploadAttachAndSendSocket(messageTable);
                clearAttachmentList();
            }
        }, throwable -> {
            if (checkNull()) {
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_120);
                clearAttachmentList();
            }
        });

    }

    private void uploadAttachAndSendSocket(MessageTable messageTable) {
        if (attachNameList.size() != 0) {
            addDisposable(startUploadAttachmentProcess());
        }
        SendPacket.sendEncryptionMessage(context, SocketJsonMaker.messageSocket(SharedPreferenceProvider.getOrganization(context), userId, messageTable), false);
    }

    private void clearAttachmentList() {

        if (attachB5HCTypeIdList != null && attachColumnIdList != null && attachNameList != null) {
            attachB5HCTypeIdList.clear();
            attachColumnIdList.clear();
            attachNameList.clear();
        }
    }

    @Override
    protected void workForSocketListener(ISocketModel iSocketModel) {

    }

    @Override
    protected void ClearAllPresenterListener() {
        clickMessageRecAdaptor = null;
        iChatMessageProvider = null;
        //should be last
        closeRealm();
    }


    @Override
    public void click() {

        iChatMessageProvider = new IChatMessageProvider() {
            @Override
            public void sendMessage(String message) {
                addDisposable(workForSendMessage(message, userB5HCMessageTypeId));
            }

            @Override
            public void toolbarClick() {
                goAttachAlbumActivity(false, false, chatRoomAttachJson);
            }

            @Override
            public void loadMoreMessage() {
                handelLoadMoreMessage(true);
            }
        };

        clickMessageRecAdaptor = new IClickMessageRecAdaptor() {
            @Override
            public void clickAttachRow(MessageTable messageTable) {
                if (checkNull()) {
                    ActivityIntents.goAttachAlbumActForResult(getView().getActivity(), messageTable.getAttach(), false, false);
                }
            }

            @Override
            public void clickAvatarRow(ChatroomMemberTable memberTable) {
                if (checkNull()) {
                    ActivityIntents.goAttachAlbumActForResult(getView().getActivity(), memberTable.getAttach(), false, false);
                }
            }

            @Override
            public void receivedNewMessage(MessageTable messageTable) {
                handelLoadMoreMessage(false);
               if(messageTable.getIsOld().equals(Commons.IS_NOT_OLD_MESSAGE)) {
                    if (checkNull()) {
                        getView().handleNewMSGForBadge(messageTable.getUserId().equals(userId));
                    }
                    if (!messageTable.getUserId().equals(userId) && !NotificationHelper.isDeviceSilence(context)) {
                        if(messageTable.getChatroomId().equals(SharedPreferenceProvider.getOnlineChatroom(context))){
                            NotificationHelper.playSoundMessageReceived(context);
                        }
                    }
                }

            }

            @Override
            public void workForchatroomDelete() {
                onBackPress();
            }

            @Override
            public void workForScroolToPosition(int position) {
                if(checkNull()){
                    getView().scroolRecyceleToPosition(position);
                }
            }

            @Override
            public void workForEditChatRoomNameAndImage(String name, String fileName) {
                if (checkNull()) {
                    getView().setupTextImageToolbar(name, fileName);
                }
            }

            @Override
            public void workForChangesIsOnlineIsTypingToolbar(boolean isOnline, boolean isTyping) {
                if (checkNull()) {
                    getView().handleOnlineOfflineTypingToolbar(isTyping ? CommonChatRoomToolbarState.TYPING : isOnline ? CommonChatRoomToolbarState.ONLINE : CommonChatRoomToolbarState
                            .OFFLINE);
                }
            }
        };
        if (checkNull()) {
            getView().setiChatMessageProvider(iChatMessageProvider);
        }
        messageRecAdaptor.setClickMessageRecAdaptor(clickMessageRecAdaptor);
    }

    @Override
    public void onCreate(IntentModel intentModel) {
        if (checkNull()) {
            getView().showHideProgress(false);
        }
        setPrimitiveValue(intentModel);
        //should be after setPrimitiveValue
        messageRecAdaptor = new MessageRecAdaptor(true, context, null, chatRoomId, chatRoomType);
        createSoftKeyListener();
        super.onCreate(intentModel);
    }

    private void handelLoadMoreMessage(boolean goForMoreMessage){

        if(goForMoreMessage && canGetOldMessage){
            canGetOldMessage=false;
            sendLoadMoreMessagePacket();
        } else {
            canGetOldMessage =true;
        }

        if(checkNull()){
            getView().showHideLoadMoreProgress(!canGetOldMessage);
        }


    }

    private void sendLoadMoreMessagePacket() {
        SendPacket.sendEncryptionMessage(context, SocketJsonMaker.messageMoreSocket(SharedPreferenceProvider.getOrganization(context), userId, new LoadMoreMessageModel(messageRecAdaptor.getFirstMessageId())), false);
    }

    private void createSoftKeyListener() {
        //no need to unregister, were be done automatically
        if (checkNull()) {
            KeyboardVisibilityEvent.setEventListener(
                    getView().getActivity(),
                    isOpen -> {
                        if (chatRoomType.equals(CommonChatroomTypeCode.PRV)) {
                            if (isOpen) {
                                sendKeyboardOpenMessage();
                                if(checkNull()){
                                    getView().showHideFabButton(false);
                                }
                            } else {
                                sendKeyboardCloseMessage();
                            }
                        }
                    });
        }

    }


    private void sendKeyboardOpenMessage() {

        if (messageRecAdaptor != null) {
            messageRecAdaptor.sendUserIsTypingPacket();
        }
    }

    /**
     * وقتی یوزر کیبورد را میبندد باید انلاین بودن وی نمایش داده شود
     */
    private void sendKeyboardCloseMessage() {
        if (messageRecAdaptor != null) {
            messageRecAdaptor.sendUserIsOnlinePacket();
        }
    }

    private void setPrimitiveValue(IntentModel intentModel) {
        chatRoomId = intentModel.getSomeString();
        chatRoomType = intentModel.getSomeString2();
        chatRoomGUID = intentModel.getSomeString3();
        userId = SharedPreferenceProvider.getUserId(context);
    }

    @Override
    protected void setRecyclAdaptor() {
        if (checkNull()) {
            getView().setRecycleAdaptor(messageRecAdaptor);
        }
    }

    @Override
    protected void workForLoadPage() {
        addDisposable(getPrimitiveMessageResult());
    }


    @Override
    protected void setLazyLoad() {
        if (checkNull()) {
            getView().loadAtEndList(() -> {
                //no need to any work
            });
        }
    }

    @Override
    public void onPause() {
    }



    @Override
    public void onBackPress() {
        allWorkForOutGoingChatRoom();
        if (checkNull()) {
            getView().workforOnBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        allWorkForOutGoingChatRoom();
        super.onDestroy();
    }

    //-----------------------------------------------------------------------------------
    private void closeRealm() {
        if (realm != null) {
            if (!realm.isClosed()) {
                realm.removeAllChangeListeners();
                RealmCloseHelper.closeRealm(realm);
            }
        }
    }

    private Realm getRealm() {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        return realm;
    }

    private ReportQueries getReportQueriesInstance() {
        if (reportQueries == null) {
            reportQueries = (ReportQueries) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.REPORT_QUERIES);
        }
        return reportQueries;
    }

    private Disposable getPrimitiveMessageResult() {
        return getReportQueriesInstance()
                .primitiveMessageQuery(getRealm(), chatRoomId)
                .subscribe(orderedRealmCollections -> {
                    if (messageRecAdaptor != null) {
                        addDisposable(handleUnreadMsgToZero(orderedRealmCollections));
                    }
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_116);
                    }
                });
    }
//----------------------------------------------------------------------------------

    /**
     * به دلیل نیاز به ترنزکشن باید در کویری جداگانه انجام شود
     */
    private Disposable handleUnreadMsgToZero(OrderedRealmCollection[] orderedRealmCollections) {

        handleUserB5HCMessageTypeId((OrderedRealmCollection<UtilCodeTable>) orderedRealmCollections[3]);
        handleSystemB5HCMessageTypeId((OrderedRealmCollection<UtilCodeTable>) orderedRealmCollections[3]);
        handleChatroomJsonAttach((OrderedRealmCollection<ChatroomTable>) orderedRealmCollections[1]);

        String beforeUnreadCount = getPrimitiveUnreadMSGCount((OrderedRealmCollection<ChatroomMemberTable>) orderedRealmCollections[2]);
        int allMsgCount = getAllMessageCount((OrderedRealmCollection<MessageTable>) orderedRealmCollections[0]);

        GlobalModel globalModel = new GlobalModel();
        globalModel.setUserId(SharedPreferenceProvider.getUserId(context));
        globalModel.setChatroomId(chatRoomId);
        globalModel.setMyString(String.valueOf(0-Integer.valueOf(beforeUnreadCount)));

        HandleAddUnreadMessageChatMembersQuery handleAddUnreadMessageChatMembersQuery =
                (HandleAddUnreadMessageChatMembersQuery) FactoryDataProvider
                        .getDataProvider(CommonsDataProviderFactory.HANDLE_ADD_UNREAD_MESSAGE_CHAT_MEMBER_QUERY);

        assert handleAddUnreadMessageChatMembersQuery != null;
        return handleAddUnreadMessageChatMembersQuery
                .data(globalModel)
                .subscribe(iModels -> {
                    messageRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                    allWorkForIncomingChatRoom(beforeUnreadCount, allMsgCount, getChatroomName((OrderedRealmCollection<ChatroomTable>) orderedRealmCollections[1]), getChatroomImage((OrderedRealmCollection<ChatroomTable>) orderedRealmCollections[1]), handleToolbarState((OrderedRealmCollection<ChatroomMemberTable>) orderedRealmCollections[2]));
                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_119);
                    }
                });
    }


    /**
     * should be after inject data
     */
    private void allWorkForIncomingChatRoom(String beforeUnreadCount, int allMessageCount, String chatroomName, String chatRoomAvatarFile, byte toolbarState) {

        int unReedCount =CalculationHelper.numberValidation(beforeUnreadCount)?Integer.valueOf(beforeUnreadCount):Commons.ZERO_INT;

        if (checkNull()) {
            //در صورت صفر شدن مقادیر نخوانده متد زیر مناسب نیست  بنابراین این شرط حتما چک شود
            if(allMessageCount- unReedCount >=0 && unReedCount !=0){
                getView().scroolRecyceleToPosition(allMessageCount- unReedCount);
                getView().setFabBadgeCount(unReedCount);
                getView().showHideFabButton(true);
            }

            getView().showHideProgress(false);
            getView().setupTextImageToolbar(chatroomName, chatRoomAvatarFile);
            if (chatRoomType.equals(CommonChatroomTypeCode.PRV)) {
                getView().handleOnlineOfflineTypingToolbar(toolbarState);
            }
        }

        SharedPreferenceProvider.setBadgeNumber(context, - unReedCount, 0);
        //we set online chatRoom, in onResume chatList and mainActivity not in onStop of here
        SharedPreferenceProvider.setOnlineChatroom(context, chatRoomId, chatRoomType.equals(CommonChatroomTypeCode.PRV));
        //should be last step, for find chatRoom type
        sendMessageEditPacket();
    }


    private void allWorkForOutGoingChatRoom() {
        SharedPreferenceProvider.setOnlineChatroom(context, Commons.NULL_INTEGER, false);
        sendMessageEditPacket();
    }

    private int getAllMessageCount(OrderedRealmCollection<MessageTable> orderedRealmCollection) {
        return orderedRealmCollection.where().findAll().size();
    }

    private void handleChatroomJsonAttach(OrderedRealmCollection<ChatroomTable> orderedRealmCollection) {
        chatRoomAttachJson = orderedRealmCollection.get(0).getAttach();
    }

    private void handleUserB5HCMessageTypeId(OrderedRealmCollection<UtilCodeTable> orderedRealmCollection) {
        userB5HCMessageTypeId = Objects.requireNonNull(orderedRealmCollection.where().equalTo(CommonColumnName.CODE, CommonMessageTypeCode.UMSG).findFirst()).getId();
    }

    private void handleSystemB5HCMessageTypeId(OrderedRealmCollection<UtilCodeTable> orderedRealmCollection) {
        systemB5HCMessageTypeId = Objects.requireNonNull(orderedRealmCollection.where().equalTo(CommonColumnName.CODE, CommonMessageTypeCode.SMSG).findFirst()).getId();

    }

    private String getChatroomName(OrderedRealmCollection<ChatroomTable> orderedRealmCollection) {
        return orderedRealmCollection.get(0).getName();
    }

    private byte handleToolbarState(OrderedRealmCollection<ChatroomMemberTable> orderedRealmCollection) {
        if (chatRoomType.equals(CommonChatroomTypeCode.PRV)) {
            ChatroomMemberTable chatroomMemberTable = orderedRealmCollection.where().notEqualTo(CommonColumnName.USER_ID, userId).findFirst();
            if (Objects.requireNonNull(chatroomMemberTable).getIsOnline().equals(Commons.IS_USER_ONLINE)) {
                return chatroomMemberTable.getIsTyping().equals(Commons.IS_USER_TYPING) ? CommonChatRoomToolbarState.TYPING : CommonChatRoomToolbarState.ONLINE;
            } else {
                return CommonChatRoomToolbarState.OFFLINE;
            }
        }
        return CommonChatRoomToolbarState.OFFLINE;
    }

    private String getChatroomImage(OrderedRealmCollection<ChatroomTable> orderedRealmCollection) {
        return AttachJsonCreator.getFirstAttachName(orderedRealmCollection.get(0).getAttach());
    }

    private String getPrimitiveUnreadMSGCount(OrderedRealmCollection<ChatroomMemberTable> orderedRealmCollection) {
        RealmResults<ChatroomMemberTable> results = orderedRealmCollection.where()
                .equalTo(CommonColumnName.CHATROOM_ID, chatRoomId)
                .equalTo(CommonColumnName.USER_ID, SharedPreferenceProvider.getUserId(context))
                .findAll();

        if (results.size() != 0) {
            return Objects.requireNonNull(results.get(0)).getUnSeenCount();
        } else {
            return Commons.ZERO_STRING;
        }
    }


    /**
     call in enter and exit activity of all type of chatroom
     for refine of seenDate and unSeenCount
     */
    private void sendMessageEditPacket() {
     SendPacket.sendEncryptionMessage(context, SocketJsonMaker.messageEditSocket(SharedPreferenceProvider.getOrganization(context), SharedPreferenceProvider.getUserId(context), chatRoomId, new MessageEditModel(chatRoomGUID)), true);
    }
}
