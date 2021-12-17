package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Model;

public class PublishResultsEvent implements Event<Model> {/** Assiph's comments: the reason this is an event is because when this event
 finishes - when the results for a student are published - we need to update the students publications (not that it states -
 "increases when the conference
 publishes the student results, not when the student sends the event".*/
private final Model model;
public PublishResultsEvent(Model model){
    this.model=model;
}

    public Model getModel() {
        return model;
    }
}
