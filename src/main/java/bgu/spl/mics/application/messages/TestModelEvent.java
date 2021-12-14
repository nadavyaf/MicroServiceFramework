package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Model;

public class TestModelEvent implements Event<String> {
private Model model;
    public TestModelEvent(Model model) {
        this.model = model;
    }

    public  Model getModel() {
        return this.model;
    }
}
/**Future<String> future= new Future();
 public Future<String> getFuture() {
 return future;
 }
 public Boolean isResolved(){
 return future.isDone();
 }*/