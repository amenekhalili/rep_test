package ir.fararayaneh.erp.utils.waiting;



public class WaitingHandler implements Runnable  {

    private  IWaitingHandler iWaitingHandler;


    public void setiWaitingHandler(IWaitingHandler iWaitingHandler) {
        this.iWaitingHandler = iWaitingHandler;
    }

    public WaitingHandler() {
    }


    @Override
    public void run() {
        if(iWaitingHandler!=null){
            iWaitingHandler.afterHandler();
        }

    }


}


