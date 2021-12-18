package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Model;

public class TrainModelEvent implements Event<Model> {/** Assiph's Comment: Just remember that after this event finishes, you need
 to send another event called TestModelEvent.*/
   private Model m;
    public TrainModelEvent(Model M) {
    this.m=M;
    }
    public Model getModel() {
        return this.m;
    }
}
