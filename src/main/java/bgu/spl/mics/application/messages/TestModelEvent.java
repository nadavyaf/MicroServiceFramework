package bgu.spl.mics.application.messages;

import bgu.spl.mics.Future;
import bgu.spl.mics.application.messages.TestModel;
import bgu.spl.mics.application.objects.Student;

public class TestModelEvent implements TestModel {
private String result=null;
private Student student;
public TestModelEvent() {}

    public String getResult() {
        return result;
    }

    public Student getStudent() {
        return student;
    }
}
/**Future<String> future= new Future();
 public Future<String> getFuture() {
 return future;
 }
 public Boolean isResolved(){
 return future.isDone();
 }*/