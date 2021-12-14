package bgu.spl.mics;

import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.Cluster;
import bgu.spl.mics.application.objects.DataBatch;
import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.application.services.GPUService;

import java.util.LinkedList;

public class Callback_TrainModelEvent implements Callback<TrainModelEvent>{
    private GPUService gpus;
    public Callback_TrainModelEvent(GPUService gpus){
        this.gpus=gpus;
    }
    public void call(TrainModelEvent c) throws InterruptedException {
    gpus.setEvent(c);
    GPU gpu = gpus.getGpu();
    gpu.setModel(c.getModel());
    LinkedList<DataBatch> divide= gpu.divideAll();
    gpu.getModel().updateStatus();
    for (int i =0;i<gpu.getCapacity()&&!divide.isEmpty();i++){
        Cluster.getInstance().sendToCPU(divide.pollFirst());
    }
    }
}
