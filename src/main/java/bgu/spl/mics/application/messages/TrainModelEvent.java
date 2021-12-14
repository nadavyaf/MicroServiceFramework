package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Model;

public class TrainModelEvent implements Event<String> {/** Assiph's Comment: Just remember that after this event finishes, you need
 to send another event called TestModelEvent.*/
    Model m = null;

    public TrainModelEvent(Model M) {
    this.m=m;
    }
    public Model getModel() {//Assiph added.
        return this.m;
    }
}
