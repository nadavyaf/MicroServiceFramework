package bgu.spl.mics.application.objects;

/**
 * Passive object representing a Deep Learning model.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Model {
    private Data data;
    private String name;
    private Student student;
    private Status status=Status.PreTrained;
    private results result=results.None;
    private Boolean published;
    public enum Status{PreTrained,Training,Trained,Tested};
    public enum results {None,Good,Bad}

    public Model(Data data, String name,Student student){
        this.data = data;
        this.name = name;
        this.student = student;
        this.published = false;
    }

    public Data getData() {
        return data;
    }

    public String getName() {
        return name;
    }
    public Status getCurrStatus(){
        return status;
    }
    public String getResult(){
        if (result==results.Good)
            return "Good";
        if (result==results.Bad)
            return "Bad";
        return "None";
    }

    public Student getStudent() {
        return student;
    }


    public void updateStatus(){
        if(this.getCurrStatus() == Status.PreTrained){
            this.status = Status.Training;
        }
        else if(this.status == Status.Training){
            this.status = Status.Trained;
        }
        else if(this.status == Status.Trained){
            this.status = Status.Tested;
        }
    }

    public boolean isTrained(){
        return this.status == Status.Trained;
    }

    public void setResult(results result) {
        this.result = result;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
}
