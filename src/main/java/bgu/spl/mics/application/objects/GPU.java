package bgu.spl.mics.application.objects;

import bgu.spl.mics.Event;

import java.util.LinkedList;

/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU {
    public GPU(Type type) {
        this.cluster = Cluster.getInstance();
        this.model = null;
        this.type = type;
        eventList = new LinkedList<Event>();
    }

    /**
     * Enum representing the type of the GPU.
     */
    enum Type {RTX3090, RTX2080, GTX1080}
    final private Cluster cluster;
    private Type type;
    private Model model;
    final private LinkedList<Event> eventList;

}
