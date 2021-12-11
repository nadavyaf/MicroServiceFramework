package bgu.spl.mics.application.messages;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.objects.Model;

public class TrainModelEvent implements TrainModel{/** Assiph's Comment: Just remember that after this event finishes, you need
 to send another event called TestModelEvent.*/
    Model m = null;
    @Override
    public void TrainingAmodel(TrainModel M) { //Assiph added.
        Model m = M.getModel();
        /**
         *Need to implement
         *
         *
         */
        MessageBusImpl.getInstance().complete(M,m);
    }

    @Override
    public Model getModel() {//Assiph added.
        return this.m;
    }
}
