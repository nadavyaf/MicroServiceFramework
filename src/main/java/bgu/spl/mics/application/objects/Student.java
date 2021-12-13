package bgu.spl.mics.application.objects;

/**
 * Passive object representing single student.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Student {
    /**
     * Enum representing the Degree the student is studying for.
     */
    enum Degree {
        MSc, PhD
    }

    private String name;
    private String department;
    private Degree status;
    private int publications;
    private int papersRead;

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getStatus() {
        if (status==Degree.PhD)
        return "PhD";
        return "MSc";
    }

    public void addPublication(){
        this.publications++;
    }
    public void addPapersRead(){
        this.papersRead++;
    }


    public int getPublications() {
        return publications;
    }

    public int getPapersRead() {
        return papersRead;
    }
}
