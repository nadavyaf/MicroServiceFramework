package bgu.spl.mics.application.objects;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {
    final private int cores;
    final private Cluster cluster;
    private int currTime;
    private DataBatch currDataBatch;
    private int currDataBatchTick = 0;

    public int getTime() {
        return currTime;
    }

    public CPU(int numberOfCores) {
        this.cores = numberOfCores;
        this.cluster = Cluster.getInstance();
        this.currDataBatch = null;
        currTime= 1;
    }

    public int getCores() {
        return cores;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void process() throws InterruptedException {
        this.cluster.getStatistics().incrementCPUTimeUnits();
        if(currTime - currDataBatch.getStartTime() >= currDataBatchTick) {
            currDataBatch.setProcessedCpu();
            this.cluster.sendGPU(currDataBatch);
            this.cluster.getStatistics().incrementCPUProcessed();
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
