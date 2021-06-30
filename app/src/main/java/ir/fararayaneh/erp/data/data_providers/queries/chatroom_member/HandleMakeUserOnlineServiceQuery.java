package ir.fararayaneh.erp.data.data_providers.queries.chatroom_member;

//todo eslahe hame queri haye class service va hazfe anha be samte queri haye asli

import android.util.Log;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.online.request.socket.UserIsOnlineSocketModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;

public class HandleMakeUserOnlineServiceQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        UserIsOnlineSocketModel userIsOnlineSocketModel = (UserIsOnlineSocketModel) iModels;

        return Single.create(emitter -> {


            realm.beginTransaction();

            RealmResults<ChatroomMemberTable> chatroomMemberTables =
                    realm.where(ChatroomMemberTable.class)
                            .equalTo(CommonColumnName.USER_ID,userIsOnlineSocketModel.getUserId())
                            .findAll();
            Stream.of(chatroomMemberTables).forEach(memberTable -> memberTable.setIsOnline(Commons.IS_USER_ONLINE));
            realm.commitTransaction();

            Log.i("arash", "HandleMakeUserOnlineServiceQuery: size");

            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}

