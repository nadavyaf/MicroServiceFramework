package bgu.spl.mics.application.objects;

import bgu.spl.mics.Event;
import bgu.spl.mics.Message;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


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
    final private LinkedBlockingQueue<Message> eventQueue;
    final private LinkedList<DataBatch> clusterQueue;
    final private ArrayBlockingQueue<DataBatch> processedCPUQueue;
    int learnedBatches;
    private int capacity;
    private int currTime;

    public GPU(Type type) {
        this.type = type;
        this.model = null;
        this.eventQueue = new LinkedBlockingQueue<Message>();
        this.learnedBatches = 0;
        this.clusterQueue = new LinkedList<DataBatch>();
        currTime = 1;//need to think.
        if(this.type == Type.RTX3090){
            this.capacity = 32;
        }
        else if(this.type == Type.RTX2080){
            this.capacity = 16;
        }
        else if(this.type == Type.GTX1080){
            this.capacity = 8;
        }
        this.processedCPUQueue = new ArrayBlockingQueue<DataBatch>(capacity);
    }

    /**
     *
     * Return the eventQueue, which holds events that the messageBus allocated to the GPU.
     * @pre: none
     * @post: none
     */
    public LinkedBlockingQueue<Message> getEventQueue() {
        return eventQueue;
    }
    public Type getEnum(int type){
        if (type==1)
        return Type.GTX1080;
        if (type == 2)
            return Type.RTX2080;
        return Type.RTX3090;

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
     * Return the processedQueue, which holds the processed data batches.
     * @pre: none
     * @post: none
     */
    public ArrayBlockingQueue<DataBatch> getProcessedCPUQueue() {
        return processedCPUQueue;
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
     * Return the amount of data batches which were processed by the GPU.
     * @pre: none
     * @post: none
     */
    public int getLearnedBatches(){
        return this.learnedBatches;
    }

    /**
     *
     * returns the currTime ticks in this GPU.
     * @return
     */

    public int getCurrTime() {
        return currTime;
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
     *       this.model == null
     * @post: size = @pre eventQueue.size
     *        eventQueue.size == size -1
     *        this.model != null
     *        learnedBatches == 0
     */
    public Event extractEvent(){
        return null;
    }

    /**
     * Helper which creates a dataBatch and add 1000 sample from data to dataBatch. If data is empty, throw
     * an exception.
     * @param data
     * @pre: data != null
     * @post: size == @pre data.size
     *        data.size == size - 1000
     */
    public DataBatch divide1000(Data data){
        return null;
    }

    /**
     * Divide all the data recieved into data batches, add each batch to clusterQueue.
     * @param data
     * @pre: data != null
     * @inv: data.size >= 0
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
     * @param data
     * @pre: data.isprocessedCPU()
     *       processedCPUQueue.size < capacity
     * @post: processedSize = @pre processedCPUQueue.size
     *        processedCPUQueue.size == processedSize + 1
     */
    public void insertProcessedCPU(DataBatch data){
    }

    /**
     *
     * @param dataBatch
     * @pre: !dataBatch.isLearnedGPU()
     * @post: dataBatch.isLearnedGPU()
     *        learnedSize = @pre learnedBatches
     *        learnedBatches == learnedSize + 1
     */
    public void GPULearn(DataBatch dataBatch){
    }

    /**
     *
     * @pre: none
     * @post: none
     */
    public boolean isDone(){
        return false;
    }

    /**
     *
     * @pre !processedCPUQueue.isEmpty()
     * @inv none
     * @post none
     *
     */
    public void updateTick(){//we will need something like this also in GPU.
        if (currTime-processedCPUQueue.peek().getStartTime()>10)// should be ticks instead of 10 instead, it is known in the json file we get{
            System.out.println("Need to implement here!");
            //implement

        else{
            //We just wait until the number of ticks is passed, we block the CPU so just let the loop run.
        }
    }

    /** Updates the time of the cpu.
     *
     * @pre none
     * @post @pre(time)<time
     */
    public void updateTime(){

    }
}
