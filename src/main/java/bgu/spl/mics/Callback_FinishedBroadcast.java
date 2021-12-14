package bgu.spl.mics;

import bgu.spl.mics.application.messages.FinishedBroadcast;
import bgu.spl.mics.application.messages.PublishResultsEvent;
import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.services.StudentService;

public class Callback_FinishedBroadcast implements Callback<FinishedBroadcast> {
    private StudentService sts;

    public Callback_FinishedBroadcast(StudentService sts) {
        this.sts = sts;
    }

    @Override
    public void call(FinishedBroadcast c) throws InterruptedException {
        Model model = this.sts.getModelList().get(sts.getCurrModel());
        Future<String> future = sts.getFuture();
        if(future.get().equals("Trained")){
            Future<String> newFuture = sts.sendEvent(new TestModelEvent(model));
            sts.setFuture(newFuture);
        }
        else if(future.get().equals("Good") || future.get().equals("Bad")){
            Future<String> newFuture = sts.sendEvent(new PublishResultsEvent(model));
            sts.setFuture(newFuture);
        }
        else if(future.get().equals("Published")){
            if(sts.getCurrModel() < sts.getModelList().size()-1){
                sts.incrementCurrModel();
                Future<String> newFuture = sts.sendEvent(new TrainModelEvent(sts.getModelList().get(sts.getCurrModel())));
                sts.setFuture(newFuture);
            }
        }
    }

}
