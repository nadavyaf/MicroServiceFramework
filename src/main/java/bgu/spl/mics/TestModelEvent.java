package bgu.spl.mics;

public class TestModelEvent implements Event<String> {
    Future<String> future= new Future();
    public Future<String> getFuture() {
        return future;
    }
    public Boolean isResolved(){
        return future.isDone();
    }


}
