package bgu.spl.mics.application.objects;

import bgu.spl.mics.Event;

import java.util.LinkedList;


/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU {
    /**
     * Enum representing the type of the GPU.
     */
    enum Type {RTX3090, RTX2080, GTX1080}

    private Type type;
    private Model model;
    private Cluster cluster;
    private LinkedList<Event> eventQueue;
    private LinkedList<DataBatch> clusterQueue;
    private LinkedList<Event> learnedQueue;
    private int capacity;


    public GPU(Type type, Model model) {
        this.type = type;
        this.model = model;
        this.eventQueue = new LinkedList<Event>();
        this.clusterQueue = new LinkedList<DataBatch>();
        this.learnedQueue = new LinkedList<Event>();
        if(this.type == Type.RTX3090){
            this.capacity = 32;
        }
        else if(this.type == Type.RTX2080){
            this.capacity = 16;
        }
        else if(this.type == Type.GTX1080){
            this.capacity = 8;
        }
    }

    /**
     *
     * Return the eventQueue, which holds events that the messageBus allocated to the GPU.
     * @pre: none
     * @post: none
     */
    public LinkedList<Event> getEventQueue() {
        return eventQueue;
    }

    /**
     *
     * Return the clusterQueue, which holds databatches that are sent to the cluster.
     * @pre: none
     * @post: none
     */
    public LinkedList<DataBatch> getClusterQueue() {
        return clusterQueue;
    }

    /**
     *
     * Return the learnedQueue, which holds events which have been learned by the computer,
     * and will be sent to the MessageBus.
     * @pre: none
     * @post: none
     */
    public LinkedList<Event> getLearnedQueue() {
        return learnedQueue;
    }




    /**
     *
     * Return the type of compiler for the GPU
     * @pre: none
     * @post: none
     */
    public Type getType() {
        return type;
    }

    /**
     *
     * Return the model the GPU is working on. If none, return null.
     * @pre: none
     * @post: none
     */
    public Model getModel() {
        return model;
    }

    /**
     *
     * Return the cluster which the GPU is connected to.
     * @pre: none
     * @post: none
     */
    public Cluster getCluster() {
        return cluster;
    }

    /**
     *
     * Return the current capacity of the GPU
     * @pre: none
     * @post: none
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * Take an event which the message bus has allocated to the GPU and add to its eventQueue.
     * @param event
     * @pre: none
     * @post: size == @pre eventQueue.size
     *        eventQueue.size == size + 1
     */
    public void addEvent(Event event){
        this.eventQueue.add(event);
    }

    /**
     * Take event from eventQueue (remember to change the return when implementing), if null, throw
     * an exception.
     * @pre: !eventQueue.isEmpty()
     * @post: size == @pre eventQueue.size
     *        eventQueue.size == size -1
     */
    public Event extractEvent(){
        return null;
    }

    /**
     * Helper which creates a dataBatch and add 1000 sample from data to dataBatch. If data is empty, throw
     * an exception.
     * @param data
     * @pre: data != null
     * @post: dataBatch.size == 1000
     *        size == @pre data.size
     *        data.size == size - 1000
     */
    public DataBatch divide1000(Data data){
        return null;
    }

    /**
     * Divide all the data recieved into data batches, add each batch to clusterQueue.
     * @param data
     * @pre: data != null
     * @post: data.size == 0
     *        clusterSize = @pre clusterQueue.size
     *        clusterQueue.size == clusterSize + numberofDataBatches(The number of data batches created)
     */
    public void divideAll(Data data){
    }

    /**
     * Release data batches for the cluster to allocate. If the clusterQueue is empty or the capacity
     * of the GPU is 0, throw an error.
     * @pre: clusterQueue != null, capacity > 0
     * @inv: capacity >=0
     * @post: clusterSize = @pre clusterQueue.size
     *        clusterQueue.size == clusterSize - 1
     *        capacitySize = @pre capacity
     *        capacity == capacitySize - 1
     */
    public void clusterSend(){
    }


    /**
     * Insert the processed and learned event into the learned queue for the message bus to take.
     * @param event
     * @pre: event.future.isDone() == true
     *       event.data.getProcessed() == event.data.getSize()
     * @post: learnedSize = @pre learnedQueue.size
     *        learnedQueue.size == learnedSize + 1
     */
    public void insertLearned(Event event){

    }

}
