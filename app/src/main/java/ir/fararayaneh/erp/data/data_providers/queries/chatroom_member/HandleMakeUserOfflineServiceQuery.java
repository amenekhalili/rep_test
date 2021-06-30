package ir.fararayaneh.erp.data.data_providers.queries.chatroom_member;

import android.util.Log;

import com.annimon.stream.Stream;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.online.request.socket.UserIsOfflineSocketModel;
import ir.fararayaneh.erp.data.models.online.request.socket.UserIsOnlineSocketModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;

public class HandleMakeUserOfflineServiceQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        UserIsOfflineSocketModel userIsOfflineSocketModel = (UserIsOfflineSocketModel) iModels;

        return Single.create(emitter -> {


            realm.beginTransaction();

            RealmResults<ChatroomMemberTable> chatroomMemberTables =
                    realm.where(ChatroomMemberTable.class)
                            .equalTo(CommonColumnName.USER_ID,userIsOfflineSocketModel.getUserId())
                            .findAll();
            Stream.of(chatroomMemberTables).forEach(memberTable -> memberTable.setIsOnline(Commons.IS_USER_OFFLINE));
            realm.commitTransaction();

            Log.i("arash", "HandleMakeUserOfflineServiceQuery: size");

            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}