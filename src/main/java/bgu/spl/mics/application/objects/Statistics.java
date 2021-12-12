package bgu.spl.mics.application.objects;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {
    private LinkedBlockingQueue<String> modelTrained;
    AtomicInteger numberOfProcessedBatches;
    AtomicInteger CPUTimeUnits;
    AtomicInteger GPUTimeUnits;

    public void addTrainedModel(String model){
        modelTrained.add(model);
    }
    public void IncrementnumberOfProcessedBatches(){
        numberOfProcessedBatches.getAndIncrement();
    }
    public void IncrementCPUTimeUnitsBy(int by){
        CPUTimeUnits.getAndAdd(by);
    }
    public void IncrementGPUTimeUnitsBy(int by){
        GPUTimeUnits.getAndAdd(by);
    }

    public LinkedBlockingQueue<String> getModelTrained() {
        return modelTrained;
    }

    public int getNumberOfProcessedBatches() {
        return numberOfProcessedBatches.get();
    }

    public int getCPUTimeUnits() {
        return CPUTimeUnits.get();
    }

    public int getGPUTimeUnits() {
        return GPUTimeUnits.get();
    }
}
