package bgu.spl.mics;

import bgu.spl.mics.application.objects.Model;

public class TrainModelEvent implements TrainModel{
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
