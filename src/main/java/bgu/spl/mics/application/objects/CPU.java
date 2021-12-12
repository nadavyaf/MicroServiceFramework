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
    final private LinkedBlockingQueue<DataBatch> CPUdata;
    final private Cluster cluster=Cluster.getInstance();
    private int currTime; // we will get from TimeService pulses,TickBrodcast, which will be caught in the GPUservice and CPUservice and update our time int.
    public int getTime() {
        return currTime;
    }
    public CPU(int numberOfCores) {
        this.cores = numberOfCores;
        this.CPUdata = new LinkedBlockingQueue<DataBatch>();
        currTime= 1;
    }

    public int getCores() {
        return cores;
    }

    public LinkedBlockingQueue getData() {
        return CPUdata;
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
        while (Flag.flag) {
            DataBatch process = CPUdata.take();
            int tick = 0;
            if (process.getType() == Data.Type.Images)
                tick = 4;
            if (process.getType() == Data.Type.Text)
                tick = 2;
            if (process.getType() == Data.Type.Tabular)
                tick = 1;
            int processTime = (32 / this.getCores()) * tick;
            process.setStartTime(currTime);
            while (currTime - process.getStartTime() < processTime)
                this.wait();
            process.setProcessedCpu();
            this.cluster.sendToGPU(process);
            this.cluster.getStatistics().IncrementnumberOfProcessedBatches();
            this.cluster.getStatistics().IncrementCPUTimeUnitsBy(processTime);
        }
    }
    /** Updates the time of the cpu.
     *
     * @pre none
     * @post @pre(time)<time
     */
    public void updateTime(){
    currTime++;
    this.notifyAll();
    }
}
