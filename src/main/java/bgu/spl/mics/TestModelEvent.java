package bgu.spl.mics;

public class TestModelEvent implements TestModel {
    Future<String> future= new Future();
    public Future<String> getFuture() {
        return future;
    }
    public Boolean isResolved(){
        return future.isDone();
    }


}
