package bgu.spl.mics;

import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.objects.Cluster;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.services.GPUService;

import java.util.Random;

public class Callback_TestModelEvent implements Callback<TestModelEvent> {
    private final GPUService gpuService;

    public Callback_TestModelEvent(GPUService gpu) {
        this.gpuService = gpu;
    }

    public void call(TestModelEvent c) throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(11);
        if (c.getModel().getStudent().getStatus().equals("PhD")) {
            if (random >= 2) {//=80%
                c.getModel().setResult(Model.results.Good);
            } else {
                c.getModel().setResult(Model.results.Bad);
            }
        }
        else{
                if (random >= 4) {//=60%
                    c.getModel().setResult(Model.results.Good);
                }
                else {
                    c.getModel().setResult(Model.results.Bad);
                }

            }
            c.getModel().updateStatus();
            Cluster.getInstance().getStatistics().addTrainedModel(c.getModel().getName());
            gpuService.complete(c, c.getModel());
        }
    }