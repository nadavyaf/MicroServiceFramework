package bgu.spl.mics;

import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.objects.Cluster;
import bgu.spl.mics.application.services.GPUService;

import java.util.Random;

public class Callback_TestModelEvent implements Callback<TestModelEvent> {
    private final GPUService gpuService;

    public Callback_TestModelEvent(GPUService gpu) {
        this.gpuService = gpu;
    }

    public void call(TestModelEvent c) throws InterruptedException {
        Random rand = new Random();
        String result;
        int random = rand.nextInt(11);
        if (c.getModel().getStudent().getStatus().equals("PhD")) {
            if (random >= 2)//=80%
                result = "Good";
            else
                result = "Bad";
        } else {
            if (random >= 4)//=60%
                result = "Good";
            else
                result = "Bad";

        }
        Cluster.getInstance().getStatistics().addTrainedModel(c.getModel().getName());
        gpuService.complete(c,result);
    }
}
