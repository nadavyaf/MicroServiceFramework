package bgu.spl.mics;

import bgu.spl.mics.application.messages.FinishedBroadcast;
import bgu.spl.mics.application.messages.PublishResultsEvent;
import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.services.StudentService;

import java.util.concurrent.TimeUnit;

public class Callback_FinishedBroadcast implements Callback<FinishedBroadcast>{
    StudentService sts;
    public Callback_FinishedBroadcast(StudentService sts){
        this.sts=sts;
    }
    public void call(FinishedBroadcast c) throws InterruptedException {
        Model model = sts.getModels().get(sts.getCurrModel());
        if (sts.getFuture() == null) { //if there are no conferences anymore, and our future is null meaning we sent it and in the sentevent we got null back, we want to proceed to the next model, as there are no conferences left.
            if (sts.getCurrModel() + 1 < sts.getModels().size()) {
                sts.incrementcurrModel();
                sts.setFuture(sts.sendEvent(new TrainModelEvent(sts.getModels().get(sts.getCurrModel()))));
            }
        }
           else if (sts.getFuture().get(1, TimeUnit.MILLISECONDS) != null) {
                Model result = sts.getFuture().get();
                if (result.getCurrStatus()== Model.Status.Trained){
                    sts.setFuture(sts.sendEvent(new TestModelEvent(model)));
                }
                else if (!result.getPublished() && result.getResult().equals("Good")) {
                sts.setFuture(sts.sendEvent(new PublishResultsEvent(model)));
            }
                else if (result.getPublished() || result.getResult().equals("Bad")) {
                    if (sts.getCurrModel() + 1 < sts.getModels().size()) {
                        sts.incrementcurrModel();
                        sts.setFuture(sts.sendEvent(new TrainModelEvent(sts.getModels().get(sts.getCurrModel()))));
                    }

                }
            }
        }
}
