package ir.fararayaneh.erp.utils.socket_helper;

import android.content.Context;
import android.util.Log;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonsSocket;
import ir.fararayaneh.erp.utils.encription.AESHelper;

public class SendPacket {

    public static boolean sendEncryptionMessage(Context context, String msg, boolean fromSyncProcess) {
        Log.i("arash", "sendEncryptionMessage:  msg "+msg);
        //if fromSyncProcess is true, no need to save those packets
        if(!fromSyncProcess){
            SocketDataBaseHelper.workForUpdateEvacuateInSendProcess(msg);
        }
        if (App.getmSocket() != null) {
            if (App.getmSocket().connected()) {
                App.getmSocket().emit(CommonsSocket.SOCKET_EVENT_MESSAGE, AESHelper.encriptionAES(CommonsSocket.SOCKET_AES_KEY,msg));
                Log.i("arash", "sendEncryptionMessage: encripted "+AESHelper.encriptionAES(CommonsSocket.SOCKET_AES_KEY,msg));
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
