package bgu.spl.mics.application.messages;

import bgu.spl.mics.Future;
import bgu.spl.mics.application.messages.TestModel;

public class TestModelEvent implements TestModel {
    Future<String> future= new Future();
    public Future<String> getFuture() {
        return future;
    }
    public Boolean isResolved(){
        return future.isDone();
    }


}
