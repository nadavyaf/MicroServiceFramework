package bgu.spl.mics;

import bgu.spl.mics.application.messages.PublishResultsEvent;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.services.ConferenceService;

public class Callback_PublishResultsEvent implements Callback<PublishResultsEvent> {
private ConferenceService cfs;
    public Callback_PublishResultsEvent(ConferenceService cfs){
        this.cfs = cfs;
    }
    public void call(PublishResultsEvent c) throws InterruptedException {
        Model model = c.getModel();
        if (model.getResult().equals("Good")){
            cfs.addToConference(model);
            model.setPublished(true);
        }

        cfs.complete(c,model);
    }
}
