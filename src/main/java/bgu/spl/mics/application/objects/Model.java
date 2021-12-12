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
    private status currStatus=status.PreTrained;
    private results result=results.None;
    private enum status{PreTrained,Training,Trained,Tested};
    private enum results {None,Good,Bad}

    public Model(Data data, String name,Student student){
        this.data = data;
        this.name = name;
        this.student = student;
    }

    public Data getData() {
        return data;
    }

    public String getName() {
        return name;
    }
    public status getCurrStatus(){
        return currStatus;
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

}
