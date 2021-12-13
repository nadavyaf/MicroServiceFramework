package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.application.services.ConferenceService;

public class PublishConferenceBroadcast implements Broadcast {
private ConferenceService cfs;
public PublishConferenceBroadcast(ConferenceService cfs){
    this.cfs = cfs;
}

    public ConferenceService getCfs() {
        return cfs;
    }
}
