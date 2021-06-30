package ir.fararayaneh.erp.data.data_providers.queries.chatroom_member;

import android.util.Log;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;


/**
 * اگر چت روم ممبر با اکتیویتی استیت دیلیت به دست ما رسیده  است که سینک استیت آن سینکد است
 * و یوزر ایدی موجود در تیبل هم خودم هستم یعنی من توسط فرد بالادست دیلیت شده ام و بنابراین
 * باید اکتیویتی استیت چت روم را  هم دیلیت کنم تا آن چت روم را نبینم
 */

//todo eslahe hame queri haye class service va hazfe anha be samte queri haye asli

public class InsertUpdateChatroomMemberTableServiceQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        ChatroomMemberTable chatroomMemberTable = (ChatroomMemberTable) iModels;

        return Single.create(emitter -> {
            realm.beginTransaction();

            realm.insertOrUpdate(chatroomMemberTable);

            //delete proper chatRoom
            if(chatroomMemberTable.getUserId().equals(SharedPreferenceProvider.getUserId(App.getAppContext()))){
                if(chatroomMemberTable.getSyncState().equals(CommonSyncState.SYNCED) &&
                chatroomMemberTable.getActivityState().equals(CommonActivityState.DELETE)){
                    RealmResults<ChatroomTable> chatroomTablesForDelete = realm.where(ChatroomTable.class)
                            .equalTo(CommonColumnName.ID,chatroomMemberTable.getChatroomId())
                            .findAll();
                    if(chatroomTablesForDelete.size()!=0){
                        Objects.requireNonNull(chatroomTablesForDelete.get(0)).setActivityState(CommonActivityState.DELETE);
                    }
                }
            }
            realm.commitTransaction();
            Log.i("arash", "InsertUpdateChatroomMemberTableServiceQuery: size" + realm.where(ChatroomMemberTable.class).findAll().size());
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}
