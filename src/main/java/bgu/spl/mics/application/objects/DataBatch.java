package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class DataBatch {
    private boolean processedCpu;
    private boolean learnedGpu;
    private int StartTime;
    private Data.Type type;
    private GPU createdGpu;
    private int startIndex;
    //Assiph's comment: maybe add a field of which gpu? so we know from which one it came.

    public DataBatch(Data.Type type, GPU gpu){
        this.processedCpu = false;
        this.learnedGpu = false;
        this.type = type;
        this.createdGpu = gpu;
        this.startIndex = 0;
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

    public void setStartTime(int startTime) {
        StartTime = startTime;
    }

    public Data.Type getType() {
        return type;
    }

    public void setProcessedCpu() {
        this.processedCpu = true;
    }

    public void setLearnedGpu() {
        this.learnedGpu = true;
    }

    public GPU gotCreatedGpu() {
        return createdGpu;
    }
}
