package bgu.spl.mics;

import bgu.spl.mics.application.messages.PublishConferenceBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.services.ConferenceService;

public class Callback_TickBroadcastConference implements Callback<TickBroadcast> {
    private ConferenceService cfs;
    public Callback_TickBroadcastConference(ConferenceService cfs){
        this.cfs = cfs;
    }
    public void call(TickBroadcast c) throws InterruptedException {
        this.cfs.getCfi().updateTime();
        if (this.cfs.getCfi().getCurrTime()>=this.cfs.getCfi().getDate()){
            this.cfs.sendBroadcast(new PublishConferenceBroadcast(this.cfs));
            synchronized (this) { // so it doesn't get a broadcast when it dies.
                MessageBusImpl.getInstance().unregister(this.cfs);
            }
        }


    }
}
