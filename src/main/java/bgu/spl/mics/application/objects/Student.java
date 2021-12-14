package bgu.spl.mics.application.objects;

import bgu.spl.mics.Future;

/**
 * Passive object representing single student.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Student {
    /**
     * Enum representing the Degree the student is studying for.
     */
    public enum Degree {
        MSc, PhD
    }

    private String name;
    private String department;
    private Degree status;
    private int publications;
    private int papersRead;

    public Student(String name,String department,Degree status){
        this.name=name;
        this.department=department;
        this.status=status;
        this.publications=0;
        this.papersRead=0;
    }

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
