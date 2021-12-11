package bgu.spl.mics;

import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.GPU;
public class Callback_TickBroadcastGPU implements Callback<TickBroadcast>{
    private GPU gpu;
    public Callback_TickBroadcastGPU(GPU gpu){
        this.gpu=gpu;
    }
    public void call(TickBroadcast tick) {
        gpu.updateTime();
    }
}

