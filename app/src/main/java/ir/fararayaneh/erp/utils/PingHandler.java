package ir.fararayaneh.erp.utils;

import com.stealthcopter.networktools.Ping;
import com.stealthcopter.networktools.ping.PingResult;
import com.stealthcopter.networktools.ping.PingStats;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class PingHandler {

    private static int PING_TIME_OUT = 1000;
    private static int PING_TIMES = 5;

    public static Single<IModels> getPing(final String pingUrl) {

        return Single.create(emitter -> {


            GlobalModel globalModel = new GlobalModel();

            //todo remove leak of listener


            Ping.onAddress(pingUrl)
                    .setTimeOutMillis(PING_TIME_OUT)
                    .setTimes(PING_TIMES)
                    .doPing(new Ping.PingListener() {
                        @Override
                        public void onResult(PingResult pingResult) {

                        }

                        @Override
                        public void onFinished(PingStats pingStats) {
                            //globalModel.setMyBoolean(pingStats.getPacketsLost()==0);
                            globalModel.setMyBoolean(true);
                            if (!emitter.isDisposed()) {
                                    emitter.onSuccess(globalModel);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            ThrowableLoggerHelper.logMyThrowable("**PingHandler/getPing " + e.getMessage());
                            globalModel.setMyBoolean(false);
                            if (!emitter.isDisposed()) {
                                emitter.onError(new Exception());
                            }
                        }
            });
        });
    }

    public static boolean checkPingNode() {
        if (App.getmSocket() != null && !SharedPreferenceProvider.getNodeIp(App.getAppContext()).toLowerCase().equals(Commons.NULL)) {
            return App.getmSocket().connected();
        }
        return false;
    }

}
