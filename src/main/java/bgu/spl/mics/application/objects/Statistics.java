package bgu.spl.mics.application.objects;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {
    private AtomicInteger CPUProcessed;
    private AtomicInteger CPUTimeUnits;
    private AtomicInteger GPUTimeUnits;
    private LinkedBlockingQueue<String> modelTrained;

    public Statistics() {
        this.CPUTimeUnits = new AtomicInteger();
        this.CPUProcessed = new AtomicInteger();
        this.GPUTimeUnits = new AtomicInteger();
        this.modelTrained = new LinkedBlockingQueue<>();
    }

    public void incrementCPUProcessed(){
        CPUProcessed.incrementAndGet();
    }

    public void incrementCPUTimeUnits(){
        CPUTimeUnits.incrementAndGet();
    }

    public void incrementGPUTimeUnits(){
        GPUTimeUnits.incrementAndGet();
    }

    public int getCPUProcessed() {
        return CPUProcessed.get();
    }

    public int getCPUTimeUnits() {
        return CPUTimeUnits.get();
    }

    public int getGPUTimeUnits() {
        return GPUTimeUnits.get();
    }

    public LinkedBlockingQueue<String> getModelTrained() {
        return modelTrained;
    }

    public void addTrainedModel(String name){
        this.getModelTrained().add(name);
    }
}