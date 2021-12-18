package bgu.spl.mics.application.objects;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {
    final private int cores;
    final private Cluster cluster=Cluster.getInstance();
    private DataBatch currDataBatch;
    //NEED TO DELETE
    private int numOfTicks;
    private int currDataBatchTick;
    private int currTime;

    public CPU(int numberOfCores) {
        this.cores = numberOfCores;
        currTime= 1;
        numOfTicks=0;
    }

    public int getTime() {
        return currTime;
    }

    public int getCores() {
        return cores;
    }

    public Cluster getCluster() {
        return cluster;
    }


    /** works on the Databatches, and processes them.
     *
     * @pre (!data.isEmpty)
     * @post if currTime-datapeek()>10 then data.length=@pre(data.length - 1)
     * @return
     */

    private void process() throws InterruptedException {
        Cluster.getInstance().getStatistics().incrementCPUTimeUnits();
        this.numOfTicks++;
            if (currTime - currDataBatch.getStartTime() >= currDataBatchTick) {
                currDataBatch.setProcessedCpu();
                Cluster.getInstance().sendToGPU(currDataBatch);
                Cluster.getInstance().getStatistics().incrementCPUProcessed();
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

    public int getNumOfTicks() {
        return numOfTicks;
    }

    public void setCurrDataBatch(DataBatch currDataBatch) {
        this.currDataBatch = currDataBatch;
    }

    public void setNumOfTicks(int numOfTicks) {
        this.numOfTicks = numOfTicks;
    }

    public void setCurrDataBatchTick(int currDataBatchTick) {
        this.currDataBatchTick = currDataBatchTick;
    }

    public void setCurrTime(int currTime) {
        this.currTime = currTime;
    }
}
