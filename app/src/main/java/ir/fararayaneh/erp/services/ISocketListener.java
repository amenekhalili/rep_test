package ir.fararayaneh.erp.services;

import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;

public interface ISocketListener {

    void listener(ISocketModel iSocketModel);
    void SocketISConnect();
    void SocketIsDisConnect();

}
