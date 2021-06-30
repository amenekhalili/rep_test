package ir.fararayaneh.erp.data.data_providers.queries.recycle_list_view_queries.report_quries;


import androidx.annotation.NonNull;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.Completable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Single;
import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonActivityKind;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonChatroomTypeCode;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.CommonUtilCode;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.IDataProvider;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.BaseCodingTable;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.data.models.tables.GroupRelatedTable;
import ir.fararayaneh.erp.data.models.tables.LastMessageTable;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CartableTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListDetailTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListMasterTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.GoodTranceTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.MessageTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.SalableGoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TaskTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TimeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WeighingTable;

import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_CARTABLE_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_CHAT_LIST_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_FUEL_DETAIL_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_FUEL_MASTER_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_GOOD_TRANS_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_GOODS_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_MESSAGE_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_SELECT_PURCHASABLE_GOODS_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_SELECT_SALABLE_GOODS_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_TASK_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_TIME_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_WEIGHTING_TABLE_NUMBER;

/**
 * here are queries of report presenter and selection presenter
 * caution :
 * accessDenied fields should not be selected or edited
 * but accessDenied rows should be removed in adaptor not here
 * <p>
 * search only on  first table not on all table
 * به این دلیل که در صورت به روز شدن دیتا توسط سوکت
 * ممکن است که به آن دیتای کمکی ناشی از جدول های یک و یک به بعد
 * نیاز داشته باشیم که در زمان سرچ با خود نبرده ایم
 * لذا فیلتر فقط روی تیبل اصلی انجام میشود و تیبل های فرعی به طور کامل در اختیار قرار میگیرند
 * چه  در حال سرچ باشیم چه در حال سرچ نباشیم
 */

public class ReportQueries implements IDataProvider {

    @Override
    public Object data(IModels iModels) {
        return null;
    }

//------------------------------------------------------------------------------------------------------
    private ArrayList<String> searchInBaseCodingNameTable(Realm realm, String searchValue) {
        RealmResults<BaseCodingTable> tempBaseCodingTableRealmResults =
                realm.where(BaseCodingTable.class)
                        .contains(CommonColumnName.NAME, searchValue)
                        .findAll();

        ArrayList<String> searchedIdArrayList = new ArrayList<>();
        Stream.of(tempBaseCodingTableRealmResults).forEach(baseCodingTable ->
                searchedIdArrayList.add(baseCodingTable.getId()));

        return searchedIdArrayList;

    }
//------------------------------------------------------------------------------------------------------
    //توجه : we may have not id for filter
    //چون ممکن است چت روم ما فاقد آی دی باشد و یک چت روم جدید باشد
    public Single<OrderedRealmCollection[]> primitiveChatListQuery(@NonNull Realm myRealm) {
    //do not close realm and do not use an other thread
    OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_CHAT_LIST_TABLE_NUMBER];
    return Single.create(emitter -> {

                    orderedRealmCollections[0] = myRealm.where(ChatroomTable.class)
                            .beginGroup()
                            .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNCED)
                            .or()
                            .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                            .endGroup()
                            .beginGroup()
                            .notEqualTo(CommonColumnName.ACTIVITY_STATE, CommonActivityState.DELETE)
                            .endGroup()
                            .sort(CommonColumnName.SORT_DATE, Sort.DESCENDING)
                            .findAll();

                    orderedRealmCollections[1] = myRealm.where(LastMessageTable.class).findAll();

                    //برای تعیین تایپ چت روم
                    orderedRealmCollections[2] = myRealm.where(UtilCodeTable.class).
                                                  equalTo(CommonColumnName.PARENT_CODE, CommonUtilCode.CHT).findAll();
                    //***************************************************************************************************
                    ArrayList<String> privateChatRoomGUID = new ArrayList<>();
                    ArrayList<String> friendUserId = new ArrayList<>();

                    //find all private chatRoom member manner :
                     RealmResults<ChatroomMemberTable> memberTables = myRealm.where(ChatroomMemberTable.class)
                             .equalTo(CommonColumnName.USER_ID, SharedPreferenceProvider.getUserId(App.getAppContext()))
                             .findAll();

                     Stream.of(memberTables).forEach(new Consumer<ChatroomMemberTable>() {
                         @Override
                         public void accept(ChatroomMemberTable chatroomMemberTable) {
                             RealmResults<ChatroomMemberTable> members = myRealm.where(ChatroomMemberTable.class).
                                     equalTo(CommonColumnName.CHATROOM_GUID, chatroomMemberTable.getChatroomGUID())
                                     .findAll();
                             //if my chatRoom is private
                             if(members.size()==2){
                                 privateChatRoomGUID.add(chatroomMemberTable.getChatroomGUID());
                                 friendUserId.add(Objects.requireNonNull(members.where()
                                         .notEqualTo(CommonColumnName.USER_ID, SharedPreferenceProvider
                                                 .getUserId(App.getAppContext())).findFirst())
                                         .getUserId());
                             }
                         }
                     });

                     orderedRealmCollections[3] = myRealm.where(ChatroomMemberTable.class)
                          .beginGroup()
                              .beginGroup()
                              .in(CommonColumnName.USER_ID,(String[]) friendUserId.toArray(new String[0]))
                              .endGroup()
                              .beginGroup()
                              .in(CommonColumnName.CHATROOM_GUID,(String[]) privateChatRoomGUID.toArray(new String[0]))
                              .endGroup()
                          .endGroup()
                          .or()
                          .beginGroup()
                              .equalTo(CommonColumnName.USER_ID, SharedPreferenceProvider.getUserId(App.getAppContext()))
                          .endGroup()
                          .findAll();


                //***************************************************************************************************
                emitter.onSuccess(orderedRealmCollections);
            }
    );
}


    public Single<OrderedRealmCollection[]> searchChatListQuery(@NonNull Realm myRealm,String searchValue) {
        //do not close realm and do not use an other thread
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_CHAT_LIST_TABLE_NUMBER];
        return Single.create(emitter -> {

                    orderedRealmCollections[0] = myRealm.where(ChatroomTable.class)
                            .beginGroup()
                            .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNCED)
                            .or()
                            .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.BEFORE_SYNC)
                            .endGroup()
                            .beginGroup()
                            .notEqualTo(CommonColumnName.ACTIVITY_STATE, CommonActivityState.DELETE)
                            .endGroup()
                            .beginGroup()
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .or()
                            .contains(CommonColumnName.DESCRIPTION, searchValue,Case.INSENSITIVE)
                            .endGroup()
                            .sort(CommonColumnName.SORT_DATE, Sort.DESCENDING)
                            .findAll();

                    orderedRealmCollections[1] = myRealm.where(LastMessageTable.class).findAll();

                    //برای تعیین تایپ چت روم
                    orderedRealmCollections[2] = myRealm.where(UtilCodeTable.class).
                            equalTo(CommonColumnName.PARENT_CODE, CommonUtilCode.CHT).findAll();
                    //***************************************************************************************************
                    ArrayList<String> privateChatRoomGUID = new ArrayList<>();
                    ArrayList<String> friendUserId = new ArrayList<>();

                    //find all private chatRoom member manner :
                    RealmResults<ChatroomMemberTable> memberTables = myRealm.where(ChatroomMemberTable.class)
                            .equalTo(CommonColumnName.USER_ID, SharedPreferenceProvider.getUserId(App.getAppContext()))
                            .findAll();

                    Stream.of(memberTables).forEach(new Consumer<ChatroomMemberTable>() {
                        @Override
                        public void accept(ChatroomMemberTable chatroomMemberTable) {
                            RealmResults<ChatroomMemberTable> members = myRealm.where(ChatroomMemberTable.class).
                                    equalTo(CommonColumnName.CHATROOM_GUID, chatroomMemberTable.getChatroomGUID())
                                    .findAll();
                            //if my chatRoom is private
                            if(members.size()==2){
                                privateChatRoomGUID.add(chatroomMemberTable.getChatroomGUID());
                                friendUserId.add(Objects.requireNonNull(members.where()
                                        .notEqualTo(CommonColumnName.USER_ID, SharedPreferenceProvider
                                                .getUserId(App.getAppContext())).findFirst())
                                        .getUserId());
                            }
                        }
                    });

                    orderedRealmCollections[3] = myRealm.where(ChatroomMemberTable.class)
                            .beginGroup()
                            .beginGroup()
                            .in(CommonColumnName.USER_ID,(String[]) friendUserId.toArray(new String[0]))
                            .endGroup()
                            .beginGroup()
                            .in(CommonColumnName.CHATROOM_GUID,(String[]) privateChatRoomGUID.toArray(new String[0]))
                            .endGroup()
                            .endGroup()
                            .or()
                            .beginGroup()
                            .equalTo(CommonColumnName.USER_ID, SharedPreferenceProvider.getUserId(App.getAppContext()))
                            .endGroup()
                            .findAll();


                    //***************************************************************************************************
                    emitter.onSuccess(orderedRealmCollections);
                }
        );
    }

//------------------------------------------------------------------------------------------------------
//توجه : we may have not id for filter
public Single<OrderedRealmCollection[]> primitiveMessageQuery(Realm realm,String chatroomId) {

    //do not close realm and do not use an other thread
    //لازم نیست اکتیویتی کایند یوزر چک شود اگر اکتیو نبود به اینجا نمیامد
    //و اگر در زمان حضور در چت روم از حالت اکتیو خارج شود چون در کویری مربوطه چت روم دیلیت فرستاده میشود بنابراین رفتار مناسب انجام میشود
    OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_MESSAGE_TABLE_NUMBER];
    return Single.create(emitter -> {
                orderedRealmCollections[0] =
                        realm.where(MessageTable.class)
                                .beginGroup()
                                .equalTo(CommonColumnName.CHATROOM_ID, chatroomId)
                                .endGroup()
                                /*.beginGroup()
                                .notEqualTo(CommonColumnName.SYNCSTATE, CommonSyncState.ACCESS_DENIED)
                                .endGroup()
                                .beginGroup()
                                .notEqualTo(CommonColumnName.ACTIVITY_STATE, CommonActivityState.DELETE)//نیازی به چک کردن deleteForMe نیست به بیزینس چت روم مراجعه شود
                                .endGroup()*/
                                .sort(CommonColumnName.SORT_DATE, Sort.ASCENDING)
                                .findAll();

        orderedRealmCollections[1] =
                realm.where(ChatroomTable.class)
                        .equalTo(CommonColumnName.ID, chatroomId)
                        .findAll();

        orderedRealmCollections[2] =
                realm.where(ChatroomMemberTable.class)
                        .beginGroup()
                        .equalTo(CommonColumnName.CHATROOM_ID, chatroomId)
                        .endGroup()
                        .findAll();

        //برای تعیین تایپ یوزر که ادمین است یاپاور یا ...
        //برای تعیین تایپ مسیج که که سیستمی است یا یوزری ...
        orderedRealmCollections[3] = realm.where(UtilCodeTable.class)
                .equalTo(CommonColumnName.PARENT_CODE, CommonUtilCode.SET)
                .or()
                .equalTo(CommonColumnName.PARENT_CODE, CommonUtilCode.MTY)
                .findAll();


                emitter.onSuccess(orderedRealmCollections);
            }
    );

}
//------------------------------------------------------------------------------------------------------

    //we sure have id for filter
    public Single<OrderedRealmCollection[]> primitiveCartableQuery(@NonNull Realm myRealm, ArrayList<String> filteredTimeId) {
        //do not close realm and do not use an other thread
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_CARTABLE_TABLE_NUMBER];
        return Single.create(emitter -> {
            if(filteredTimeId ==null){
                orderedRealmCollections[0] = myRealm.where(CartableTable.class).findAll()
                        .sort(CommonColumnName.ID,Sort.DESCENDING);
            } else {
                orderedRealmCollections[0] = myRealm.where(CartableTable.class)
                        .in(CommonColumnName.ID, (String[]) filteredTimeId.toArray(new String[0]))
                        .findAll()
                        .sort(CommonColumnName.ID,Sort.DESCENDING);
            }
                    emitter.onSuccess(orderedRealmCollections);
                }
        );
    }
    //we sure have id for filter
    public Single<OrderedRealmCollection[]> searchCartableQuery(@NonNull Realm myRealm, String searchText, ArrayList<String> filteredTimeId) {
        if (searchText.equals(Commons.SPACE)) {
            return primitiveCartableQuery(myRealm, filteredTimeId);
        } else {
            //do not close realm and do not use an other thread
            OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_CARTABLE_TABLE_NUMBER];
            return Single.create(emitter -> {
                if(filteredTimeId ==null){
                    orderedRealmCollections[0] = myRealm.where(CartableTable.class)
                            .contains(CommonColumnName.SENDER_NAME, searchText, Case.INSENSITIVE)
                            .or()
                            .contains(CommonColumnName.B_5_HC_PRIORITY_NAME, searchText, Case.INSENSITIVE)
                            .or()
                            .contains(CommonColumnName.SUBJECT, searchText, Case.INSENSITIVE)
                            .findAll()
                            .sort(CommonColumnName.ID,Sort.DESCENDING);
                } else {
                    orderedRealmCollections[0] = myRealm
                            .where(CartableTable.class)
                            .beginGroup()
                            .in(CommonColumnName.ID, (String[]) filteredTimeId.toArray(new String[0]))
                            .endGroup()
                            .beginGroup()
                            .contains(CommonColumnName.SENDER_NAME, searchText, Case.INSENSITIVE)
                            .or()
                            .contains(CommonColumnName.B_5_HC_PRIORITY_NAME, searchText, Case.INSENSITIVE)
                            .or()
                            .contains(CommonColumnName.SUBJECT, searchText, Case.INSENSITIVE)
                            .endGroup()
                            .findAll()
                            .sort(CommonColumnName.ID,Sort.DESCENDING);
                }

                        emitter.onSuccess(orderedRealmCollections);
                    }
            );
        }
    }

//------------------------------------------------------------------------------------------------------

    //we sure have guid for filter
    public Single<OrderedRealmCollection[]> primitiveGoodTransQuery(@NonNull Realm myRealm, String formId, ArrayList<String> filteredTimeGUID) {

        //do not close realm and do not use an other thread
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_GOOD_TRANS_TABLE_NUMBER];
        return Single.create(emitter -> {

            if(filteredTimeGUID ==null){
                orderedRealmCollections[0] = myRealm.where(GoodTranceTable.class)
                        .equalTo(CommonColumnName.B5FORMREFID, formId)
                        .findAll();
            } else {
                orderedRealmCollections[0] = myRealm.where(GoodTranceTable.class)
                        .beginGroup()
                        .in(CommonColumnName.GUID, (String[]) filteredTimeGUID.toArray(new String[0]))
                        .endGroup()
                        .beginGroup()
                        .equalTo(CommonColumnName.B5FORMREFID, formId)
                        .endGroup()
                        .findAll();
            }



                    //we need all rows , because my report is dynamic by socket and we do not now witch base codding is needed
                    orderedRealmCollections[1] = myRealm.where(BaseCodingTable.class).findAll();

                    emitter.onSuccess(orderedRealmCollections);
                }
        );
    }

    //we sure have guid for filter
    public Single<OrderedRealmCollection[]> searchGoodTransQuery(@NonNull Realm myRealm, String searchText, String formId, ArrayList<String> filteredTimeGUID) {
        if (searchText.equals(Commons.SPACE)) {
            return primitiveGoodTransQuery(myRealm, formId, filteredTimeGUID);
        } else {
            //do not close realm and do not use an other thread
            OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_GOOD_TRANS_TABLE_NUMBER];
            return Single.create(emitter -> {

                        ArrayList<String> searchedIdArrayList = searchInBaseCodingNameTable(myRealm, searchText);
                if(filteredTimeGUID ==null){
                    orderedRealmCollections[0] = myRealm.where(GoodTranceTable.class)
                            .beginGroup()
                            .equalTo(CommonColumnName.B5FORMREFID, formId)
                            .endGroup()
                            .beginGroup()
                            .contains(CommonColumnName.FORM_NUMBER, searchText, Case.INSENSITIVE)
                            .or()
                            .contains(CommonColumnName.DESCRIPTION, searchText, Case.INSENSITIVE)
                            .or()
                            .in(CommonColumnName.B5IDREFID1, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .or()
                            .in(CommonColumnName.B5IDREFID2, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .or()
                            .in(CommonColumnName.B5IDREFID3, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .or()
                            .in(CommonColumnName.B5IDREFID4, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .or()
                            .in(CommonColumnName.B5IDREFID5, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .endGroup()
                            .findAll();
                } else {
                    orderedRealmCollections[0] = myRealm.where(GoodTranceTable.class)
                                .beginGroup()
                            .in(CommonColumnName.GUID, (String[]) filteredTimeGUID.toArray(new String[0]))
                                .endGroup()
                                .beginGroup()
                            .equalTo(CommonColumnName.B5FORMREFID, formId)
                            .endGroup()
                            .beginGroup()
                            .contains(CommonColumnName.FORM_NUMBER, searchText, Case.INSENSITIVE)
                            .or()
                            .contains(CommonColumnName.DESCRIPTION, searchText, Case.INSENSITIVE)
                            .or()
                            .in(CommonColumnName.B5IDREFID1, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .or()
                            .in(CommonColumnName.B5IDREFID2, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .or()
                            .in(CommonColumnName.B5IDREFID3, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .or()
                            .in(CommonColumnName.B5IDREFID4, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .or()
                            .in(CommonColumnName.B5IDREFID5, (String[]) searchedIdArrayList.toArray(new String[0]))
                                .endGroup()
                            .findAll();
                }


                        //we need all rows , because my report is dynamic by socket and we do not now witch base codding is needed
                        orderedRealmCollections[1] = myRealm.where(BaseCodingTable.class).findAll();

                        //--------------------------------------------------------------------
                        emitter.onSuccess(orderedRealmCollections);
                    }
            );
        }

    }

//------------------------------------------------------------------------------------------------------
    //we sure have id for filter
    public Single<OrderedRealmCollection[]> primitiveSalableGoodsQuery(@NonNull Realm myRealm, ArrayList<String> filteredTimeId,ArrayList<String> filteredGoodsByWarehouse) {
    //do not close realm and do not use an other thread
    OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_SELECT_SALABLE_GOODS_TABLE_NUMBER];
    return Single.create(emitter -> {
                if(filteredTimeId ==null){
                    orderedRealmCollections[0] = myRealm.where(SalableGoodsTable.class)
                            .beginGroup()
                            .in(CommonColumnName.GOOD_ID, (String[]) filteredGoodsByWarehouse.toArray(new String[0]))
                            .endGroup()
                            .findAll();
                } else {
                    orderedRealmCollections[0] = myRealm.where(SalableGoodsTable.class)
                            .beginGroup()
                            .in(CommonColumnName.GOOD_ID, (String[]) filteredGoodsByWarehouse.toArray(new String[0]))
                            .endGroup()
                            .beginGroup()
                            .in(CommonColumnName.ID, (String[]) filteredTimeId.toArray(new String[0]))
                            .endGroup()
                            .findAll();
                }

                emitter.onSuccess(orderedRealmCollections);
            }
    );
}


    //we sure have id for filter
    public Single<OrderedRealmCollection[]> searchSalableGoodsQuery(@NonNull Realm myRealm, String searchText, ArrayList<String> filteredTimeId,ArrayList<String> filteredGoodsByWarehouse) {
        if (searchText.equals(Commons.SPACE)) {
            return primitiveSalableGoodsQuery(myRealm, filteredTimeId,filteredGoodsByWarehouse);
        } else {
            //do not close realm and do not use an other thread
            OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_SELECT_SALABLE_GOODS_TABLE_NUMBER];
            return Single.create(emitter -> {
                        if(filteredTimeId ==null){
                            orderedRealmCollections[0] = myRealm.where(SalableGoodsTable.class)
                                    .beginGroup()
                                    .in(CommonColumnName.GOOD_ID, (String[]) filteredGoodsByWarehouse.toArray(new String[0]))
                                    .endGroup()
                                    .contains(CommonColumnName.CODE,searchText , Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.GOOD_CODE, searchText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.TECH_INFO, searchText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.GOOD_NAME, searchText, Case.INSENSITIVE)
                                    .findAll();
                        } else {
                            orderedRealmCollections[0] = myRealm.where(SalableGoodsTable.class)
                                    .beginGroup()
                                    .in(CommonColumnName.GOOD_ID, (String[]) filteredGoodsByWarehouse.toArray(new String[0]))
                                    .endGroup()
                                    .beginGroup()
                                    .in(CommonColumnName.ID, (String[]) filteredTimeId.toArray(new String[0]))
                                    .endGroup()
                                    .beginGroup()
                                    .contains(CommonColumnName.CODE,searchText , Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.GOOD_CODE, searchText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.TECH_INFO, searchText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.GOOD_NAME, searchText, Case.INSENSITIVE)
                                    .endGroup()
                                    .findAll();
                        }
                        emitter.onSuccess(orderedRealmCollections);
                    }
            );
        }
    }
//------------------------------------------------------------------------------------------------------
    //we sure have id for filter
    public Single<OrderedRealmCollection[]> primitivePurchasableGoodsQuery(@NonNull Realm myRealm, ArrayList<String> filteredTimeId,ArrayList<String> filteredGoodsByWarehouse) {
        //do not close realm and do not use an other thread
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_SELECT_PURCHASABLE_GOODS_TABLE_NUMBER];
        return Single.create(emitter -> {
                    if(filteredTimeId ==null){
                        orderedRealmCollections[0] = myRealm.where(PurchasableGoodsTable.class)
                                .in(CommonColumnName.GOOD_ID, (String[]) filteredGoodsByWarehouse.toArray(new String[0]))
                                .findAll();
                    } else {
                        orderedRealmCollections[0] = myRealm.where(PurchasableGoodsTable.class)
                                .beginGroup()
                                .in(CommonColumnName.GOOD_ID, (String[]) filteredGoodsByWarehouse.toArray(new String[0]))
                                .endGroup()
                                .beginGroup()
                                .in(CommonColumnName.ID, (String[]) filteredTimeId.toArray(new String[0]))
                                .endGroup()
                                .findAll();
                    }

            emitter.onSuccess(orderedRealmCollections);
                }
        );
    }
    //we sure have id for filter
    public Single<OrderedRealmCollection[]> searchPurchasableGoodsQuery(@NonNull Realm myRealm, String searchText, ArrayList<String> filteredTimeId,ArrayList<String> filteredGoodsByWarehouse) {
    if (searchText.equals(Commons.SPACE)) {
        return primitivePurchasableGoodsQuery(myRealm, filteredTimeId, filteredGoodsByWarehouse);
    } else {
        //do not close realm and do not use an other thread
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_SELECT_PURCHASABLE_GOODS_TABLE_NUMBER];
        return Single.create(emitter -> {
                    if(filteredTimeId ==null){
                        orderedRealmCollections[0] = myRealm.where(PurchasableGoodsTable.class)
                                .beginGroup()
                                .in(CommonColumnName.GOOD_ID, (String[]) filteredGoodsByWarehouse.toArray(new String[0]))
                                .endGroup()
                                .contains(CommonColumnName.CODE,searchText , Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.GOOD_CODE, searchText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.TECH_INFO, searchText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.GOOD_NAME, searchText, Case.INSENSITIVE)
                                .findAll();
                    } else {
                        orderedRealmCollections[0] = myRealm.where(PurchasableGoodsTable.class)
                                .beginGroup()
                                .in(CommonColumnName.GOOD_ID, (String[]) filteredGoodsByWarehouse.toArray(new String[0]))
                                .endGroup()
                                .beginGroup()
                                .in(CommonColumnName.ID, (String[]) filteredTimeId.toArray(new String[0]))
                                .endGroup()
                                .beginGroup()
                                .contains(CommonColumnName.CODE,searchText , Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.GOOD_CODE, searchText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.TECH_INFO, searchText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.GOOD_NAME, searchText, Case.INSENSITIVE)
                                .endGroup()
                                .findAll();
                    }

            emitter.onSuccess(orderedRealmCollections);
                }
        );
    }
}

//------------------------------------------------------------------------------------------------------
    /**
     * all goods show to user, but only non accessDenied row can be selected
     *
     *     //we sure have id for filter
     *
     *  caution : goods not filtered for warehouse
     */
    public Single<OrderedRealmCollection[]> primitiveGoodsQuery(@NonNull Realm myRealm) {
        //do not close realm and do not use an other thread
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_GOODS_TABLE_NUMBER];
        return Single.create(emitter -> {

                    orderedRealmCollections[0] = myRealm.where(GoodsTable.class).findAll();
                    emitter.onSuccess(orderedRealmCollections);
                }
        );
    }

    /**
     * all goods show to user, but only non accessDenied row can be selected
     *
     *     //we sure have id for filter
     *
     *     caution : goods not filtered for warehouse
     */
    public Single<OrderedRealmCollection[]> searchGoodsQuery(@NonNull Realm myRealm, String filterText) {
        if (filterText.equals(Commons.SPACE)) {
            return primitiveGoodsQuery(myRealm);
        } else {
            //do not close realm and do not use an other thread
            OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_GOODS_TABLE_NUMBER];
            return Single.create(emitter -> {


                        orderedRealmCollections[0] = myRealm.where(GoodsTable.class)
                                .contains(CommonColumnName.NAME, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.BRAND_NAME, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.SERIAL, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.CODE, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.UNIT_NAME, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.BRAND_NAME, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.LATIN_NAME, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.TECH_INFO, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.NATIONALITY_CODE, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.WIDTH, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.HEIGHT, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.LENGTH, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.WEIGHT, filterText, Case.INSENSITIVE)
                                .or()
                                .contains(CommonColumnName.SERIAL, filterText, Case.INSENSITIVE)
                                .findAll();

                        emitter.onSuccess(orderedRealmCollections);

                    }

            );
        }
    }
//------------------------------------------------------------------------------------------------------

    /**
     * all goods show to user, but only non accessDenied row can be selected
     *
     *     //we sure have guid for filter
     */
    public Single<OrderedRealmCollection[]> primitiveTaskQuery(@NonNull Realm myRealm, ArrayList<String> filteredTimeGUID) {
        //do not close realm and do not use an other thread
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_TASK_TABLE_NUMBER];
        return Single.create(emitter -> {
            if(filteredTimeGUID ==null){
                orderedRealmCollections[0] = myRealm.where(TaskTable.class).findAll();
            } else {
                orderedRealmCollections[0] = myRealm.where(TaskTable.class)
                        .in(CommonColumnName.GUID, (String[]) filteredTimeGUID.toArray(new String[0]))
                        .findAll();

            }
                    //we need all rows , because my report is dynamic by socket and we do not now witch base codding is needed
                    orderedRealmCollections[1] = myRealm.where(BaseCodingTable.class).findAll();
                    emitter.onSuccess(orderedRealmCollections);
                }
        );
    }

    /**
     * all goods show to user, but only non accessDenied row can be selected
     *
     *     //we sure have guid for filter
     */
    public Single<OrderedRealmCollection[]> searchTaskQuery(@NonNull Realm myRealm, String filterText, ArrayList<String> filteredTimeGUID) {
        if (filterText.equals(Commons.SPACE)) {
            return primitiveTaskQuery(myRealm, filteredTimeGUID);
        } else {
            //do not close realm and do not use an other thread
            OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_TASK_TABLE_NUMBER];
            return Single.create(emitter -> {

                        ArrayList<String> searchedIdArrayList = searchInBaseCodingNameTable(myRealm, filterText);

                if(filteredTimeGUID ==null){
                    orderedRealmCollections[0] = myRealm.where(TaskTable.class)
                            .contains(CommonColumnName.SUBJECT, filterText, Case.INSENSITIVE)
                            .or()
                            .contains(CommonColumnName.COMMENTS, filterText, Case.INSENSITIVE)
                            .or()
                            .in(CommonColumnName.B5IDREFID, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .or()
                            .in(CommonColumnName.B5IDREFID3, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .findAll();
                } else {
                    orderedRealmCollections[0] = myRealm.where(TaskTable.class)
                            .beginGroup()
                            .in(CommonColumnName.GUID, (String[]) filteredTimeGUID.toArray(new String[0]))
                            .endGroup()
                            .beginGroup()
                            .contains(CommonColumnName.SUBJECT, filterText, Case.INSENSITIVE)
                            .or()
                            .contains(CommonColumnName.COMMENTS, filterText, Case.INSENSITIVE)
                            .or()
                            .in(CommonColumnName.B5IDREFID, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .or()
                            .in(CommonColumnName.B5IDREFID3, (String[]) searchedIdArrayList.toArray(new String[0]))
                            .endGroup()
                            .findAll();
                }



                        //we need all rows , because my report is dynamic by socket and we do not now witch base codding is needed
                        orderedRealmCollections[1] = myRealm.where(BaseCodingTable.class).findAll();

                        //--------------------------------------------------------------------
                        emitter.onSuccess(orderedRealmCollections);

                    }
            );
        }
    }
//------------------------------------------------------------------------------------------------------
    /*
    *
            *     //we sure have guid for filter
            */
    public Single<OrderedRealmCollection[]> searchFuelMasterQuery(@NonNull Realm myRealm, String filterText, ArrayList<String> filteredTimeGUID) {
        if (filterText.equals(Commons.SPACE)) {
            return primitiveFuelMasterQuery(myRealm, filteredTimeGUID);
        } else {
            //do not close realm and do not use an other thread
            OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_FUEL_MASTER_TABLE_NUMBER];
            return Single.create(emitter -> {
                        if (filteredTimeGUID == null) {
                            orderedRealmCollections[0] = myRealm.where(FuelListMasterTable.class)
                                    .beginGroup()
                                    .contains(CommonColumnName.BRANCH_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.UNIT_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.WAREHOUSE_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.DEPARTMENT_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.FUEL_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.FORM_NUMBER, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.DESCRIPTION, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.DRIVER_NAME, filterText, Case.INSENSITIVE)
                                    .endGroup()
                                    .beginGroup()
                                    .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.SYNCED)
                                    .or()
                                    .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.ACCESS_DENIED)
                                    .endGroup()
                                    .beginGroup()
                                    .notEqualTo(CommonColumnName.ACTIVITY_STATE,CommonActivityState.DELETE)
                                    .endGroup()
                                    .findAll();
                        } else {
                            orderedRealmCollections[0] = myRealm.where(FuelListMasterTable.class)
                                    .beginGroup()
                                    .in(CommonColumnName.ID, (String[]) filteredTimeGUID.toArray(new String[0]))
                                    .endGroup()
                                    .beginGroup()
                                    .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.SYNCED)
                                    .or()
                                    .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.ACCESS_DENIED)
                                    .endGroup()
                                    .beginGroup()
                                    .contains(CommonColumnName.BRANCH_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.UNIT_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.WAREHOUSE_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.DEPARTMENT_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.FUEL_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.FORM_NUMBER, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.DESCRIPTION, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.DRIVER_NAME, filterText, Case.INSENSITIVE)
                                    .endGroup()
                                    .beginGroup()
                                    .notEqualTo(CommonColumnName.ACTIVITY_STATE,CommonActivityState.DELETE)
                                    .endGroup()
                                    .findAll();
                        }

                        //--------------------------------------------------------------------
                        emitter.onSuccess(orderedRealmCollections);
                    }
            );
        }
    }

    public Single<OrderedRealmCollection[]> primitiveFuelMasterQuery(@NonNull Realm myRealm, ArrayList<String> filteredTimeGUID) {
        //do not close realm and do not use an other thread
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_FUEL_MASTER_TABLE_NUMBER];
        return Single.create(emitter -> {
                    if(filteredTimeGUID ==null){
                        orderedRealmCollections[0] = myRealm.where(FuelListMasterTable.class)
                                .beginGroup()
                                .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.SYNCED)
                                .or()
                                .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.ACCESS_DENIED)
                                .endGroup()
                                .beginGroup()
                                .notEqualTo(CommonColumnName.ACTIVITY_STATE,CommonActivityState.DELETE)
                                .endGroup()
                                .findAll();
                    } else {
                        orderedRealmCollections[0] = myRealm.where(FuelListMasterTable.class)
                                .beginGroup()
                                .in(CommonColumnName.ID, (String[]) filteredTimeGUID.toArray(new String[0]))
                                .endGroup()
                                .beginGroup()
                                .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.SYNCED)
                                .or()
                                .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.ACCESS_DENIED)
                                .endGroup()
                                .beginGroup()
                                .notEqualTo(CommonColumnName.ACTIVITY_STATE,CommonActivityState.DELETE)
                                .endGroup()
                                .findAll();
                    }
                    emitter.onSuccess(orderedRealmCollections);
                }
        );
    }
//------------------------------------------------------------------------------------------------------
    public Single<OrderedRealmCollection[]> searchFuelDetailQuery(Realm myRealm,String masterId, String filterText, ArrayList<String> filteredTimeGUID) {
        if (filterText.equals(Commons.SPACE)) {
            return primitiveFuelDetailQuery(myRealm,masterId, filteredTimeGUID);
        } else {
            //do not close realm and do not use an other thread
            OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_FUEL_DETAIL_TABLE_NUMBER];
            return Single.create(emitter -> {
                        if (filteredTimeGUID == null) {
                            orderedRealmCollections[0] = myRealm.where(FuelListDetailTable.class)
                                    .beginGroup()
                                    .contains(CommonColumnName.DEVICE_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.PLACE_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.DESCRIPTION, filterText, Case.INSENSITIVE)
                                    .endGroup()
                                    .beginGroup()
                                    .equalTo(CommonColumnName.MASTER_ID,masterId)
                                    .endGroup()
                                    .beginGroup()
                                    .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.SYNCED)//we can see synced only
                                    .or()
                                    .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.ACCESS_DENIED)
                                    .endGroup()
                                    .beginGroup()
                                    .notEqualTo(CommonColumnName.ACTIVITY_STATE,CommonActivityState.DELETE)
                                    .endGroup()
                                    .findAll();
                        } else {
                            orderedRealmCollections[0] = myRealm.where(FuelListDetailTable.class)
                                    .beginGroup()
                                    .in(CommonColumnName.ID, (String[]) filteredTimeGUID.toArray(new String[0]))
                                    .endGroup()
                                    .beginGroup()
                                    .contains(CommonColumnName.DEVICE_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.PLACE_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.DESCRIPTION, filterText, Case.INSENSITIVE)
                                    .endGroup()
                                    .beginGroup()
                                    .equalTo(CommonColumnName.MASTER_ID,masterId)
                                    .endGroup()
                                    .beginGroup()
                                    .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.SYNCED)//we can see synced only
                                    .or()
                                    .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.ACCESS_DENIED)
                                    .endGroup()
                                    .beginGroup()
                                    .notEqualTo(CommonColumnName.ACTIVITY_STATE,CommonActivityState.DELETE)
                                    .endGroup()
                                    .findAll();
                        }

                        orderedRealmCollections[1] = myRealm.where(UtilCodeTable.class).
                                equalTo(CommonColumnName.PARENT_CODE, CommonUtilCode.DCF).findAll();

                        //--------------------------------------------------------------------
                        emitter.onSuccess(orderedRealmCollections);
                    }
            );
        }
    }

    public Single<OrderedRealmCollection[]> primitiveFuelDetailQuery(@NonNull Realm myRealm,String masterId, ArrayList<String> filteredTimeGUID) {
        //do not close realm and do not use an other thread
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_FUEL_DETAIL_TABLE_NUMBER];
        return Single.create(emitter -> {
                    if(filteredTimeGUID ==null){
                        orderedRealmCollections[0] = myRealm.where(FuelListDetailTable.class)
                                .equalTo(CommonColumnName.MASTER_ID,masterId)
                                .beginGroup()
                                .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.SYNCED)
                                .or()
                                .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.ACCESS_DENIED)
                                .endGroup()
                                .beginGroup()
                                .notEqualTo(CommonColumnName.ACTIVITY_STATE,CommonActivityState.DELETE)
                                .endGroup()
                                .findAll();
                    } else {
                        orderedRealmCollections[0] = myRealm.where(FuelListDetailTable.class)
                                .beginGroup()
                                .in(CommonColumnName.ID, (String[]) filteredTimeGUID.toArray(new String[0]))
                                .endGroup()
                                .equalTo(CommonColumnName.MASTER_ID,masterId)
                                .beginGroup()
                                .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.SYNCED)
                                .or()
                                .equalTo(CommonColumnName.SYNCSTATE,CommonSyncState.ACCESS_DENIED)
                                .endGroup()
                                .beginGroup()
                                .notEqualTo(CommonColumnName.ACTIVITY_STATE,CommonActivityState.DELETE)
                                .endGroup()
                                .findAll();
                    }

                    orderedRealmCollections[1] = myRealm.where(UtilCodeTable.class).
                    equalTo(CommonColumnName.PARENT_CODE, CommonUtilCode.DCF).findAll();
                    emitter.onSuccess(orderedRealmCollections);
                }
        );
    }

//------------------------------------------------------------------------------------------------------

    /**
     * all goods show to user, but only non accessDenied row can be selected
     *
     *     //we sure have guid for filter
     */
    public Single<OrderedRealmCollection[]> primitiveTimeQuery(@NonNull Realm myRealm, ArrayList<String> filteredTimeGUID) {
        //do not close realm and do not use an other thread
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_TIME_TABLE_NUMBER];
        return Single.create(emitter -> {
                    if(filteredTimeGUID ==null){
                        orderedRealmCollections[0] = myRealm.where(TimeTable.class)
                                .findAll();
                    } else {
                        orderedRealmCollections[0] = myRealm.where(TimeTable.class)
                                .in(CommonColumnName.GUID, (String[]) filteredTimeGUID.toArray(new String[0]))
                                .findAll();
                    }
                    emitter.onSuccess(orderedRealmCollections);
                }
        );
    }

    /**
     * all goods show to user, but only non accessDenied row can be selected
     *
     *     //we sure have guid for filter
     */
    public Single<OrderedRealmCollection[]> searchTimeQuery(@NonNull Realm myRealm, String filterText, ArrayList<String> filteredTimeGUID) {
        if (filterText.equals(Commons.SPACE)) {
            return primitiveTimeQuery(myRealm, filteredTimeGUID);
        } else {
            //do not close realm and do not use an other thread
            OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_TIME_TABLE_NUMBER];
            return Single.create(emitter -> {
                        if (filteredTimeGUID == null) {
                            orderedRealmCollections[0] = myRealm.where(TimeTable.class)
                                    .contains(CommonColumnName.DESCRIPTION, filterText, Case.INSENSITIVE)
                                    .findAll();
                        } else {
                            orderedRealmCollections[0] = myRealm.where(TimeTable.class)

                                    .beginGroup()
                                    .in(CommonColumnName.GUID, (String[]) filteredTimeGUID.toArray(new String[0]))
                                    .endGroup()
                                    .beginGroup()
                                    .contains(CommonColumnName.DESCRIPTION, filterText, Case.INSENSITIVE)
                                    .endGroup()
                                    .findAll();
                        }

                        //--------------------------------------------------------------------
                        emitter.onSuccess(orderedRealmCollections);
                    }
            );
        }
    }
//------------------------------------------------------------------------------------------------------

    /**
     * all goods show to user, but only non accessDenied row can be selected
     *
     * //we sure have id for filter
     */
    public Single<OrderedRealmCollection[]> primitiveWeightingQuery(@NonNull Realm myRealm, ArrayList<String> filteredTimeId) {
        //do not close realm and do not use an other thread
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_WEIGHTING_TABLE_NUMBER];
        return Single.create(emitter -> {
                    if (filteredTimeId == null) {
                        orderedRealmCollections[0] = myRealm.where(WeighingTable.class).findAll();
                    } else {
                        orderedRealmCollections[0] = myRealm
                                .where(WeighingTable.class)
                                .in(CommonColumnName.ID, (String[]) filteredTimeId.toArray(new String[0]))
                                .findAll();
                    }
                    emitter.onSuccess(orderedRealmCollections);
                }
        );
    }

    /**
     * all goods show to user, but only non accessDenied row can be selected
     *
     * //we sure have id for filter
     */
    public Single<OrderedRealmCollection[]> searchWeightingQuery(@NonNull Realm myRealm, String filterText, ArrayList<String> filteredTimeId) {
        if (filterText.equals(Commons.SPACE)) {
            return primitiveWeightingQuery(myRealm, filteredTimeId);
        } else {
            //do not close realm and do not use an other thread
            OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_WEIGHTING_TABLE_NUMBER];
            return Single.create(emitter -> {
                        if (filteredTimeId == null) {
                            orderedRealmCollections[0] = myRealm.where(WeighingTable.class)

                                    .contains(CommonColumnName.CARPLAQUENUMBER, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.DRIVER_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.BUYER_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.GOOD_TRANSE_AMOUNT, filterText, Case.INSENSITIVE)
                                    /*
                                    .or()
                                    .contains(CommonColumnName.GOODTRANSENUMBER, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.EMPTY_WEIGHT, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.GOOD_TRANSE_WEIGHT, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.SUM_WEIGHT, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.BALANCE, filterText, Case.INSENSITIVE)*/
                                    .findAll();
                        } else {
                            orderedRealmCollections[0] = myRealm.where(WeighingTable.class)
                                    .beginGroup()
                                    .in(CommonColumnName.ID, (String[]) filteredTimeId.toArray(new String[0]))
                                    .endGroup()
                                    .beginGroup()
                                    .contains(CommonColumnName.CARPLAQUENUMBER, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.DRIVER_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.BUYER_NAME, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.GOOD_TRANSE_AMOUNT, filterText, Case.INSENSITIVE)
                                    /*
                                    .or()
                                    .contains(CommonColumnName.GOODTRANSENUMBER, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.EMPTY_WEIGHT, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.GOOD_TRANSE_WEIGHT, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.SUM_WEIGHT, filterText, Case.INSENSITIVE)
                                    .or()
                                    .contains(CommonColumnName.BALANCE, filterText, Case.INSENSITIVE)*/
                                    .endGroup()
                                    .findAll();
                        }

                        //--------------------------------------------------------------------
                        emitter.onSuccess(orderedRealmCollections);
                    }
            );
        }
    }



}
