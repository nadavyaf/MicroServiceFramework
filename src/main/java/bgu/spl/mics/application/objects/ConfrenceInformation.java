package bgu.spl.mics.application.objects;

/**
 * Passive object representing information on a conference.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class ConfrenceInformation {
    private String name;
    private int date;
    private int currTime;

    public ConfrenceInformation(String name, int date) {
        this.name = name;
        this.date = date;
        this.currTime=0;
    }

    public String getName() {
        return name;
    }

    public int getDate() {
        return date;
    }

    public int getCurrTime() {
        return currTime;
    }

    public void updateTime(){
        currTime++;
    }
}
