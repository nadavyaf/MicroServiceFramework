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
    final Cluster cluster;


    public CPU(int cores, LinkedList<DataBatch> data, Cluster cluster) {
        this.cores = cores;
        this.data = data;
        this.cluster = cluster;
    }

    /**
     * Returns the number of cores designated in the field.
     *
     */
    public int getCores() {
        return cores;
    }

    /**
     * Returns a LinkedList of the data batch.
     *
     */
    public LinkedList<DataBatch> getData() {
        return data;
    }

    /**
     * Returns the cluster which the CPU is connected to.
     *
     */
    public Cluster getCluster() {
        return cluster;
    }
}
