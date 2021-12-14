package bgu.spl.mics.application.objects;

/**
 * Passive object representing a Deep Learning model.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Model {
    private String name;
    private Data data;
    private Student student;
    public enum Status {Pretrained, Training, Trained, Tested};
    public enum Results {None, Good, Bad};
    private Status currStatus;
    private Results currResults;

    public Model(String name, Data data, Student student) {
        this.name = name;
        this.data = data;
        this.student = student;
        this.currStatus = Status.Pretrained;
        this.currResults = Results.None;
    }

    public String getName() {
        return name;
    }

    public Data getData() {
        return data;
    }

    public Student getStudent() {
        return student;
    }

    public Status getCurrStatus() {
        return currStatus;
    }

    public Results getCurrResults() {
        return currResults;
    }

    public void setCurrResults(Results currResults) {
        this.currResults = currResults;
    }

    public String resultsToString(){
        if (this.getCurrResults() == Results.Good){
            return "Good";
        }
        if (this.getCurrResults() == Results.Bad){
            return "Bad";
        }
        else{
            return "Null";
        }
    }

    public void updateStatus(){
        if(this.getCurrStatus() == Status.Pretrained){
            this.currStatus = Status.Training;
        }
        else if(this.currStatus == Status.Training){
            this.currStatus = Status.Trained;
        }
        else if(this.currStatus == Status.Trained){
            this.currStatus = Status.Tested;
        }
    }

    public boolean isTrained(){
        return this.currStatus == Status.Trained;
    }
}
