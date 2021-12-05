package bgu.spl.mics;

import bgu.spl.mics.application.objects.Model;

public interface TrainModel extends Event<Model> {
    Model m = null; // Assiph added.

    void TrainingAmodel(TrainModel M);

    Model getModel();//Assiph added.
}
