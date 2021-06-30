package ir.fararayaneh.erp.data.data_providers.queries.chatroom;

import android.util.Log;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
//todo eslahe hame queri haye class service va hazfe anha be samte queri haye asli

public class InsertUpdateChatroomTableServiceQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        ChatroomTable chatroomTable = (ChatroomTable) iModels;

        return Single.create(emitter -> {
            realm.beginTransaction();
            realm.insertOrUpdate(chatroomTable);
            realm.commitTransaction();
            Log.i("arash", "InsertUpdateChatroomTableServiceQuery: size" + realm.where(ChatroomTable.class).findAll().size());
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}
