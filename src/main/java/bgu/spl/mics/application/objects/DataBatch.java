package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class DataBatch {
    private boolean processedCpu;
    private boolean learnedGpu;
    private int StartTime;

    public DataBatch(){
        this.processedCpu = false;
        this.learnedGpu = false;
    }

    public boolean isProcessedCpu() {
        return processedCpu;
    }

    public boolean isLearnedGpu() {
        return learnedGpu;
    }

    public int getStartTime() {
        return StartTime;
    }
}
