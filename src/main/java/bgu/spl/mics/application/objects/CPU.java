package bgu.spl.mics.application.objects;

import java.util.LinkedList;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {
    final private int cores;
    final private LinkedList<DataBatch> data;
    final private Cluster cluster;

    public CPU(int numberOfCores, LinkedList<DataBatch> data, Cluster cluster) {
        this.cores = numberOfCores;
        this.data = data;
        this.cluster = cluster;
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

}
