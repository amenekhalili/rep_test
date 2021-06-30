package ir.fararayaneh.erp.activities.chat_list;

import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.IBase.common_base.BasePresenterWithRecycle;
import ir.fararayaneh.erp.adaptors.chat_list_adaptor.ChatListRecAdaptor;
import ir.fararayaneh.erp.adaptors.chat_list_adaptor.IClickChatListRecAdaptor;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.message.RefineLastMessageQuery;
import ir.fararayaneh.erp.data.data_providers.queries.recycle_list_view_queries.report_quries.ReportQueries;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.services.EvacuatePacketForeGroundService;
import ir.fararayaneh.erp.utils.data_handler.KindListForSyncActHelper;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.intent_handler.IntentReceiverHandler;

public class ChatListPresenter extends BasePresenterWithRecycle<ChatListView> {

    private ChatListRecAdaptor chatListRecAdaptor;
    private IClickChatListRecAdaptor clickChatListRecAdaptor;
    private IChatListActivityListener iChatListActivityListener;
    private Realm realm;
    private ReportQueries reportQueries;

    public ChatListPresenter(WeakReference<ChatListView> weekView, Context context, boolean shouldHaveIntent) {
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

    }

    @Override
    protected void workForSocketListener(ISocketModel iSocketModel) {
    }

    @Override
    protected void ClearAllPresenterListener() {
        clickChatListRecAdaptor = null;
        iChatListActivityListener = null;
        //should be last
        closeRealm();
    }


    @Override
    public void click() {
        clickChatListRecAdaptor = new IClickChatListRecAdaptor() {
            @Override
            public void clickAttachRow(ChatroomTable chatroomTable) {
                goAttachAlbumActivity(false, false, chatroomTable.getAttach());
            }
            @Override
            public void clickGoChatRoomRow(ChatroomTable chatroomTable,String chatTypeCode) {
                goChatRoomHandler(chatroomTable,chatTypeCode);
            }
        };




        iChatListActivityListener = new IChatListActivityListener() {
            @Override
            public void goSyncAct() {
                if(checkNull()){
                    closeRealm();//we no need listener when sync table
                    ActivityIntents.goSyncActForResult(getView().getActivity(), KindListForSyncActHelper.getChatKindsList());
                }
            }

            @Override
            public void doSearch(Intent data) {
                if (checkNull()) {
                    getView().showHideProgress(true);
                }
                if(data!=null){
                    addDisposable(getSearchedChatrooms(IntentReceiverHandler.
                            getIntentData(Objects.requireNonNull(data.getExtras())).getSomeString()));
                } else {
                    addDisposable(getSearchedChatrooms(Commons.SPACE));
                }

            }
        };


        if(checkNull()){
            getView().setiChatListActivityListener(iChatListActivityListener);
        }
        chatListRecAdaptor.setClickChatListRecAdaptor(clickChatListRecAdaptor);
    }



    @Override
    public void onResume() {
        super.onResume();
        SharedPreferenceProvider.setOnlineChatList(context,true);
        //we set offline chatRoom in onResume chatList and mainActivity
        SharedPreferenceProvider.setOnlineChatroom(context, Commons.NULL_INTEGER,false);
    }

    @Override
    public void onStop() {
        SharedPreferenceProvider.setOnlineChatList(context,false);
        super.onStop();
    }

    private void goChatRoomHandler(ChatroomTable chatroomTable,String chatTypeCode) {

        /*
         *اگر چت رومی را میبینیم
         * یعنی مجاز به کلیک روی ان هستیم و فقط باید
         * مانع کلیک روی چت روم های beForSync باشیم (به بیزینس جدول چت روم و primitiveChatListQuery مراجعه شود)
         * عمیات ان لاین کردن چت روم مزبور و صفر کردن نخوانده ها و سین شدن پیام ها و ... در خود چت روم هندل میشود
         */
        if(!chatroomTable.getSyncState().equals(CommonSyncState.BEFORE_SYNC)){
            ActivityIntents.goMessageActivity(context,chatroomTable.getId(),chatTypeCode,chatroomTable.getGuid());
        } else {
            if(checkNull()){
                getView().showMessageNoPermission();
            }
        }

    }

    @Override
    public void onCreate(IntentModel intentModel) {
        if (checkNull()) {
            getView().showHideProgress(true);
        }
        chatListRecAdaptor = new ChatListRecAdaptor(true,context,null);
        super.onCreate(intentModel);
    }

    @Override
    protected void setRecyclAdaptor() {
        if (checkNull()) {
            getView().setRecycleAdaptor(chatListRecAdaptor);
        }
    }

    @Override
    protected void workForLoadPage() {
        addDisposable(fillAllLastMessage());
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
        if (checkNull()) {
            getView().workforOnBackPressed();
        }

    }
//----------------------------------------------------------------------------------------------

    private void closeRealm() {
        if (realm != null) {
            if(!realm.isClosed()){
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

    private Disposable fillAllLastMessage() {

        RefineLastMessageQuery refineLastMessageQuery=(RefineLastMessageQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.REFINE_LAST_MESSAGE_QUERY);

        assert refineLastMessageQuery != null;
        return refineLastMessageQuery
                .data(App.getNullRXModel())
                .subscribe(iModels -> addDisposable(getPrimitiveChatListResult()), throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_121);
                    }
                });


    }

    private ReportQueries getReportQueriesInstance() {
        if (reportQueries == null) {
            reportQueries = (ReportQueries) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.REPORT_QUERIES);
        }
        return reportQueries;
    }

    private Disposable getPrimitiveChatListResult() {
        return getReportQueriesInstance()
                .primitiveChatListQuery(getRealm())
                .subscribe(orderedRealmCollections -> {
                    if (chatListRecAdaptor != null) {
                        chatListRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }

                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_114);
                    }
                });
    }

    private Disposable getSearchedChatrooms(String searchValue) {

        return getReportQueriesInstance()
                .searchChatListQuery(getRealm(),searchValue)
                .subscribe(orderedRealmCollections -> {
                    if (chatListRecAdaptor != null) {
                        chatListRecAdaptor.injectNewDataCollection(orderedRealmCollections);
                        if (checkNull()) {
                            getView().showHideProgress(false);
                        }
                    }

                }, throwable -> {
                    if (checkNull()) {
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_124);
                    }
                });

    }
}
