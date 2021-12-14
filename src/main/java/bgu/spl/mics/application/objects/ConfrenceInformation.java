package bgu.spl.mics.application.objects;

/**
 * Passive object representing information on a conference.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class ConfrenceInformation {
    private int currTime;
    private String name;
    private int date;
    public ConfrenceInformation(String name, int date){
        this.name= name;
        this.date = date;
        this.currTime = 0;
    }

    public void updateTime() {
    currTime++;
    }

    public int getCurrTime() {
        return currTime;
    }

    public String getName() {
        return name;
    }

    public int getDate() {
        return date;
    }
}
