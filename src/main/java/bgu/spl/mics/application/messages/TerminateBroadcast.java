package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.MicroService;

public class TerminateBroadcast implements Broadcast {
    private MicroService microService;
    public TerminateBroadcast(MicroService m){
        this.microService=m;
    }

    public MicroService getMicroService() {
        return microService;
    }
}
