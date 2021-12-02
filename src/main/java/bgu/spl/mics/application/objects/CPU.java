package bgu.spl.mics.application.objects;

import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.Timer;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {
    final private int cores;
    final private LinkedList<DataBatch> data;
    final private Cluster cluster;
    private Timer Time;
    public Timer getTime() {
        return Time;
    }
    public CPU(int numberOfCores) {
        this.cores = numberOfCores;
        this.data = new LinkedList<DataBatch>();
        this.cluster = Cluster.getInstance();
        Time= new Timer();
    }

    public int getCores() {
        return cores;
    }

    public LinkedList<DataBatch> getData() {
        return data;
    }

    public Cluster getCluster() {
        return cluster;
    }

    /**Gets data from the Cluster and add it to the DataBatch of a cpu.
     *
     * @param d
     * @pre none
     * @post data.length=@pre(data.length + 1)
     *
     */
    public void addData(DataBatch d){
        data.addLast(d);
    }

    /** works on the Databatches, and processes them.
     *
     * @pre (!data.isEmpty)
     * @post data.length=@pre(data.length - 1)
     * @return
     */
    public void Proccessed(){//Processes the first element in the data LinkedList, and then pops it.

    }

    /** Updates the time of the cpu.
     *
     * @pre none
     * @post @pre(time)<time
     */
    public void updateTime(){

    }

}
