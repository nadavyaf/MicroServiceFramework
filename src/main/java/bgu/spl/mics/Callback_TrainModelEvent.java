package bgu.spl.mics;

import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.Cluster;
import bgu.spl.mics.application.objects.DataBatch;
import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.application.services.GPUService;

import java.util.LinkedList;

public class Callback_TrainModelEvent implements Callback<TrainModelEvent> {
    private final GPUService gpus;

    public Callback_TrainModelEvent(GPUService gpus) {
        this.gpus = gpus;
    }

    @Override
    public void call(TrainModelEvent c) {
        gpus.setEvent(c);
        GPU gpu = gpus.getGpu();
        gpu.setModel(c.getModel());
        LinkedList<DataBatch> dataList = gpu.divideAll();
        gpu.getModel().updateStatus();
        for(int i=0; i<gpu.getCapacity() && !dataList.isEmpty(); i++){
            Cluster.getInstance().sendCPU(dataList.pollFirst());
        }
    }
}
