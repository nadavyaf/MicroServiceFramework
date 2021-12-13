package bgu.spl.mics.application.objects;

import bgu.spl.mics.MessageBusImpl;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {
    final private int cores;
    final private Cluster cluster=Cluster.getInstance();
    private DataBatch currDataBatch;
    private int currDataBatchTick;
    private int currTime; // we will get from TimeService pulses,TickBrodcast, which will be caught in the GPUservice and CPUservice and update our time int.
    public int getTime() {
        return currTime;
    }
    public CPU(int numberOfCores) {
        this.cores = numberOfCores;
        currTime= 1;
    }

    public int getCores() {
        return cores;
    }

    public Cluster getCluster() {
        return cluster;
    }

//    /**Gets data from the Cluster and add it to the DataBatch of a cpu.
//     *
//     * @param d
//     * @pre none
//     * @post data.length=@pre(data.length + 1)
//     *
//     */
//    public void addData(DataBatch d){
//        try {
//            CPUdata.put(d);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    /** works on the Databatches, and processes them.
     *
     * @pre (!data.isEmpty)
     * @post if currTime-datapeek()>10 then data.length=@pre(data.length - 1)
     * @return
     */
//    public DataBatch Proccessed(){//Processes the first element in the data LinkedList, and then pops it.
//        updateTime();
//        if (currTime - CPUdata.peek().getStartTime() > 10)// should be ticks instead of 10 instead, it is known in the json file we get{
//        {
//            System.out.println("Need to implement here!");
//            //implement
//            try {
//                return CPUdata.take();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        else{
//
//            //We just wait until the number of ticks is passed, we block the CPU so just let the loop run.
//        }
//        return null;
//    }
    public void process() throws InterruptedException {
        this.cluster.getStatistics().incrementCPUTimeUnits();
            if (currTime - currDataBatch.getStartTime() < currDataBatchTick) {
                currDataBatch.setProcessedCpu();
                this.cluster.sendToGPU(currDataBatch);
                this.cluster.getStatistics().incrementCPUProcessed();
                currDataBatch = null;
            }
    }
    /** Updates the time of the cpu.
     *
     * @pre none
     * @post @pre(time)<time
     */
    public void updateTime() throws InterruptedException {
        boolean updated=false;
        if(currDataBatch == null){
            synchronized (Cluster.getInstance().getCpuQueue()) {
                if (!Cluster.getInstance().getCpuQueue().isEmpty()) {
                    currDataBatch = Cluster.getInstance().getCpuQueue().take();
                    updated = true;
                }
            }
            if (updated) {
                int ticks = 0;
                if (currDataBatch.getType() == Data.Type.Images) {
                    ticks = 4;
                }
                if (currDataBatch.getType() == Data.Type.Text) {
                    ticks = 2;
                }
                if (currDataBatch.getType() == Data.Type.Tabular) {
                    ticks = 1;
                }
                currDataBatchTick = (32 / this.getCores()) * ticks;
                currDataBatch.setStartTime(currTime);
            }
        }
        currTime++;
        if(currDataBatch != null){
            this.process();
        }
    }
}
