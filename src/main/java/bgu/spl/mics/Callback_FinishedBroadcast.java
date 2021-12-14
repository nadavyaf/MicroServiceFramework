package bgu.spl.mics;

import bgu.spl.mics.application.messages.FinishedBroadcast;
import bgu.spl.mics.application.messages.PublishResultsEvent;
import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.services.StudentService;
public class Callback_FinishedBroadcast implements Callback<FinishedBroadcast>{
    StudentService sts;
    public Callback_FinishedBroadcast(StudentService sts){
        this.sts=sts;
    }
    public void call(FinishedBroadcast c) throws InterruptedException {
        Model model = sts.getModels().get(sts.getCurrModel());
        String result = sts.getFuture().get();
        if (result.equals("Trained"))
            sts.setFuture(sts.sendEvent(new TestModelEvent(model)));
        else if (result.equals("Good"))
            sts.setFuture(sts.sendEvent(new PublishResultsEvent(model)));
        else if (result.equals("Published")||result.equals("Bad")){
            if (sts.getCurrModel()+1<sts.getModels().size()){
                sts.incrementcurrModel();
                sts.setFuture(sts.sendEvent(new TrainModelEvent(sts.getModels().get(sts.getCurrModel()))));
            }
        }


    }
}
