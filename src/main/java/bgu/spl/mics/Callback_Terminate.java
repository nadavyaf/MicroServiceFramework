package bgu.spl.mics;

import bgu.spl.mics.application.messages.TerminateBroadcast;

public class Callback_Terminate implements Callback<TerminateBroadcast> {
    private MicroService microService;
    public Callback_Terminate(MicroService microService){
        this.microService=microService;
    }
    public void call(TerminateBroadcast c) throws InterruptedException {
        MessageBusImpl.getInstance().unregister(this.microService);
    }
}
