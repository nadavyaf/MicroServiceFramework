package bgu.spl.mics;

import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.services.GPUService;

public class Callback_TickBroadcastGPU implements Callback<TickBroadcast>{
    private final GPUService gpus;
    public Callback_TickBroadcastGPU(GPUService gpus){
        this.gpus=gpus;
    }
    public void call(TickBroadcast tick) throws InterruptedException {
        gpus.getGpu().updateTime();
        if(gpus.getGpu().getModel() != null){
            if(this.gpus.getGpu().getModel().isTrained()){
            this.gpus.complete(gpus.getEvent(),gpus.getGpu().getModel());
            this.gpus.setEvent(null);
            this.gpus.getGpu().setModel(null);
            }
        }
    }
}

