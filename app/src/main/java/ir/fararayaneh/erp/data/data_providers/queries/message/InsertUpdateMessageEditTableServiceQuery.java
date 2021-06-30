package ir.fararayaneh.erp.data.data_providers.queries.message;


import android.util.Log;


import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;
import com.google.android.gms.common.internal.service.Common;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonTimeZone;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.online.request.socket.MessageEditSocketModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.MessageTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;

/**
 * وقتی این پکت دریافت میشود
 * اگر یوزر آی دی آن خودم هستم باید تعداد unseenCount
 * یوزر خودم را برای آن چت روم اصلاح میکنم و همچنین مقدار بج ها را هم اصلاح کنم
 * و چت روم را هم نوتیف کنم تا یو آی اصلاح شود
 * اگر یوزر آی دی خودم نیستم
 * و اگر چت روم پرایوت است باید مقدار سین دیت همه مسیج های یوزر مقابل را اصلاح کنم(برای نمایش دو تا تیک آبی که مسیج دیده شده است) تغییرات در UI
 * اتوماتیک اعمال میشود و لازم نیست
 * به آنلاین بودن آن چت روم دقت شود
 *
 * در دریافت این پکت اگر خودمان فرستنده ان نیستیم تمام استیت های آن یوزر در همه چت روم ها آنلاین شود
 */
public class InsertUpdateMessageEditTableServiceQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        MessageEditSocketModel messageEditSocketModel = (MessageEditSocketModel) iModels;

        String myUserId = SharedPreferenceProvider.getUserId(App.getAppContext());
        //مقدار این تاریخ مهم نیست فقط باید با مقدار نال دیت مخالف باشد چون برای نمایش دو تیک آبی استفاده میشود
        //String properStringDate = CustomTimeHelper.longLocalDateToOtherLocalConverter(CommonTimeZone.SERVER_TIME_ZONE, CustomTimeHelper.getCurrentDate().getTime(), CommonsFormat.DATE_SHARE_FORMAT_FROM_STRING);
        String properStringDate = "01-Jan-90 03:30:00";
        return Single.create(emitter -> {
            //-------------------------------------------//
            realm.beginTransaction();
            if (myUserId.equals(messageEditSocketModel.getUserId())) {
                RealmResults<ChatroomMemberTable> memberResult = realm.where(ChatroomMemberTable.class)
                        .equalTo(CommonColumnName.USER_ID, myUserId)
                        .equalTo(CommonColumnName.CHATROOM_ID, messageEditSocketModel.getChatRoomId())
                        .findAll();
                if (memberResult.size() != 0) {
                    SharedPreferenceProvider.setBadgeNumber(App.getAppContext(), -Integer.parseInt(Objects.requireNonNull(memberResult.get(0)).getUnSeenCount()), 0);//should be first call
                    Objects.requireNonNull(memberResult.get(0)).setUnSeenCount(Commons.ZERO_STRING);
                    //be in dalil ke ba taghire nakhnde ha, chatlist ham dar UI eslah shavad,
                    //ama nabayad mahale radif taghir konad, chatlist ra ba sortDate mojod por mikonim
                    //dar vaghe faghat ui ra notif mikonim
                    RealmResults<ChatroomTable> chatroomTables = realm.where(ChatroomTable.class)
                            .equalTo(CommonColumnName.ID, messageEditSocketModel.getChatRoomId())
                            .findAll();
                    if (chatroomTables.size() != 0) {
                        Objects.requireNonNull(chatroomTables.get(0)).setSortDate(Objects.requireNonNull(chatroomTables.get(0)).getSortDate());
                    }
                }
            } else {
                //check we have that chatRoom and is active
                RealmResults<MessageTable> messageTables =
                        realm.where(MessageTable.class)
                                .equalTo(CommonColumnName.CHATROOM_ID, messageEditSocketModel.getChatRoomId())
                                .notEqualTo(CommonColumnName.USER_ID, myUserId)
                                .findAll();

                Stream.of(messageTables).forEach(new Consumer<MessageTable>() {
                    @Override
                    public void accept(MessageTable messageTable) {
                        messageTable.setSeenDate(properStringDate);//for old manner of seen
                        messageTable.setSeenCount(messageTable.getSeenCount()+1);//for new manner of seen
                    }
                });

                    //make user online
                    RealmResults<ChatroomMemberTable> chatroomMemberTables = realm.where(ChatroomMemberTable.class)
                            .equalTo(CommonColumnName.USER_ID, myUserId).findAll();
                    for (ChatroomMemberTable chatroomMemberTable : chatroomMemberTables) {
                        chatroomMemberTable.setIsOnline(Commons.IS_USER_ONLINE);

                }
            }
            Log.i("arash", "InsertUpdateMessageEditTableServiceQuery: size" + realm.where(MessageTable.class).findAll().size());
            realm.commitTransaction();
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}
