package bgu.spl.mics;

import bgu.spl.mics.application.messages.PublishConferenceBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.services.ConferenceService;

public class Callback_TickBroadcastConference implements Callback<TickBroadcast> {
    private ConferenceService cfs;

    public Callback_TickBroadcastConference(ConferenceService cfs) {
        this.cfs=cfs;
    }

    @Override
    public void call(TickBroadcast c) throws InterruptedException {
        cfs.getCfi().updateTime();
        if(cfs.getCfi().getCurrTime() >= cfs.getCfi().getDate()){
            this.cfs.sendBroadcast(new PublishConferenceBroadcast(cfs));
            MessageBusImpl.getInstance().unregister(this.cfs);
        }
    }
}
