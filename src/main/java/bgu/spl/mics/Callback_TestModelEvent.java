package bgu.spl.mics;

import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.application.objects.Student;
import bgu.spl.mics.application.services.GPUService;

import java.util.Random;

public class Callback_TestModelEvent implements Callback<TestModelEvent> {
    GPUService gpuService;

    public Callback_TestModelEvent(GPUService gpu) {
        this.gpuService = gpu;
    }

    public void call(TestModelEvent c) {
        Random rand = new Random();
        String result;
        int random = rand.nextInt(11);
        if (c.getStudent().getStatus().equals("PhD")) {
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
        gpuService.complete(c,result);
    }
}
