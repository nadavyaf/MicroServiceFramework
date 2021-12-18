package bgu.spl.mics.application.objects;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU { /** Assiph's comments: I think we should add another queue - timeTickQueue that will be updating the time. I also think
 we need to delete the clusterQueue.*/
    /**
     * Enum representing the type of the GPU.
     */
   public enum Type {RTX3090, RTX2080, GTX1080}
    private Type type;
    private Model model;
    private Cluster cluster;
    private DataBatch currBatch;
    final private ArrayBlockingQueue<DataBatch> processedCPUQueue;
    int learnedBatches;
    private int capacity;
    private int numOfTicks;
    private int currTime;
    private int ticks;
    private LinkedList <DataBatch> dataList;

    public GPU(Type type) {
        numOfTicks=0;
        this.type = type;
        this.currBatch=null;
        this.model = null;
        this.learnedBatches = 0;
        dataList = new LinkedList<DataBatch>();
        cluster = Cluster.getInstance();
        currTime = 1;//need to think.
        if(this.type == Type.RTX3090){
            this.capacity = 32;
            this.ticks=1;
        }
        else if(this.type == Type.RTX2080){
            this.capacity = 16;
            this.ticks=2;
        }
        else if(this.type == Type.GTX1080){
            this.capacity = 8;
            this.ticks=4;
        }
        this.processedCPUQueue = new ArrayBlockingQueue<DataBatch>(capacity);
    }


    public Type getEnum(int type){
        if (type==1)
        return Type.GTX1080;
        if (type == 2)
            return Type.RTX2080;
        return Type.RTX3090;

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
     * Divide all the data recieved into data batches, add each batch to clusterQueue.
     * @pre: data != null
     * @inv: data.size >= 0
     * @post: data.size == 0
     *        clusterSize = @pre clusterQueue.size
     *        clusterQueue.size == clusterSize + numberofDataBatches(The number of data batches created)
     */
    public void divideAll(){
            Data data = this.getModel().getData();
            for (int i =0;i<data.getNumOfBatches();i++){
                this.dataList.add(new DataBatch(data.getType(),this));
            }
    }

    public LinkedList<DataBatch> getDataList() {
        return dataList;
    }

    /**
     * Insert the processed and learned event into the learned queue for the message bus to take.
     * @param data
     * @pre: none
     * @post: this.getprocessedCPUQueue.contains(data) == true
     */
    public void insertProcessedCPU(DataBatch data){
        processedCPUQueue.add(data);
    }

    /**
     *
     * @param
     * @pre: !dataBatch.isLearnedGPU()
     * @post:
     *  numOfTicks == @prenumOfTicks + 1
     *  learnedBatches == learnedSize + 1
     *
     */
    public void GPULearn() throws InterruptedException {
        numOfTicks++;
        Cluster.getInstance().getStatistics().incrementGPUTimeUnits();
        if(currTime - this.currBatch.getStartTime() >= ticks) {
            currBatch.setLearnedGpu();
            this.learnedBatches++;
            if (!dataList.isEmpty())
                Cluster.getInstance().sendToCPU(dataList.pollFirst());
            currBatch = null;
            if(learnedBatches == model.getData().getNumOfBatches()){
                learnedBatches = 0;
                this.model.updateStatus();
                Cluster.getInstance().getStatistics().addTrainedModel(this.model.getName());
            }
        }
    }

    /**
     *
     * @pre !processedCPUQueue.isEmpty()
     * @inv none
     * @post none
     *
     */

    /**
     *
     * @pre !processedCPUQueue.isEmpty()
     * @inv none
     * @post none
     *
     */
    public void updateTime() throws InterruptedException {
        if(currBatch == null) {
            if (!this.processedCPUQueue.isEmpty()) {
                currBatch = this.processedCPUQueue.take();
                currBatch.setStartTime(currTime);
            }
        }
        currTime++;
        if(currBatch != null){
            this.GPULearn();
        }
    }
    public void setModel(Model m){
        this.model=m;
    }

    public int getNumOfTicks() {
        return numOfTicks;
    }

    public void setCurrBatch(DataBatch db){
        this.currBatch = db;
    }

    public void setCurrTime(int time){
        this.currTime = time;
    }

    public void setLearnedBatches(int learnedBatches) {
        this.learnedBatches = learnedBatches;
    }


}
