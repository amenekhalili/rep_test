package ir.fararayaneh.erp.data.data_providers.queries.evacuate;

import android.util.Log;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.EvacuatePacketTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;

public class SendAllEvacuatePacketQuery extends BaseQueries {


    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        return Single.create(emitter -> realm.executeTransactionAsync(realm -> {
                    RealmResults<EvacuatePacketTable> realmResults = realm.where(EvacuatePacketTable.class).findAll();
                    Stream.of(realmResults).forEach(evacuatePacketTable -> SendPacket.sendEncryptionMessage(App.getAppContext(),evacuatePacketTable.getJsonPacket(),true));
                },
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(App.getNullRXModel());
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("SendAllEvacuatePacketQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}

