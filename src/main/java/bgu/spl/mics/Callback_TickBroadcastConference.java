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
            System.out.println("Sent a broadcast");
            MessageBusImpl.getInstance().unregister(this.cfs);
            System.out.println("Message map contained: " + MessageBusImpl.getInstance().getMessageMap().containsValue(this.cfs));
            System.out.println("Micro map contained " + MessageBusImpl.getInstance().getMicroMap().containsKey(this.cfs));
        }


    }
}
