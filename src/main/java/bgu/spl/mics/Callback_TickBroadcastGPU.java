package bgu.spl.mics;

import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.services.GPUService;

public class Callback_TickBroadcastGPU implements Callback<TickBroadcast>{
    private GPUService gpus;
    public Callback_TickBroadcastGPU(GPUService gpus){
        this.gpus=gpus;
    }
    public void call(TickBroadcast tick) throws InterruptedException {
        gpus.getGpu().updateTime();
        if(gpus.getGpu().getModel() != null) {
            if (gpus.getGpu().getModel().isTrained()) {
                this.gpus.complete(gpus.getEvent(), "Trained");
                this.gpus.setEvent(null);
                this.gpus.getGpu().setModel(null);
            }
        }
    }
}

