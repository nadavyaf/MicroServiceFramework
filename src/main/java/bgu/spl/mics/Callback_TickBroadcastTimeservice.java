package bgu.spl.mics;

import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.services.TimeService;

public class Callback_TickBroadcastTimeservice implements Callback<TickBroadcast> {
   private TimeService time;
    public Callback_TickBroadcastTimeservice(TimeService time){
        this.time = time;
    }
    public void call(TickBroadcast c) throws InterruptedException {
    time.updateTime();
    }
}
