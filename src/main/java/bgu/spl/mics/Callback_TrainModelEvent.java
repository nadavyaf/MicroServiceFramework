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
    gpu.divideAll();
    gpu.getModel().updateStatus();
    for (int i =0;i<gpu.getCapacity()&&!gpu.getDataList().isEmpty();i++){
        Cluster.getInstance().sendToCPU(gpu.getDataList().pollFirst());
    }
    }
}
