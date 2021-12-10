package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Model;

public interface TrainModel extends Event<Model> {
    Model m = null; // Assiph added.

    void TrainingAmodel(TrainModel M);

    Model getModel();//Assiph added.
}
