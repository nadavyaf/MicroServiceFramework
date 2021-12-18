package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.services.GPUService;
import junit.framework.TestCase;

public class GPUTest extends TestCase {

    public void testDivideAll() {
    }

    public void testInsertProcessedCPU() {
        GPU gpu = new GPU(GPU.Type.RTX2080);
        DataBatch data = new DataBatch(Data.Type.Images,gpu);
        gpu.insertProcessedCPU(data);
        assertTrue("Didn't manage to add data!",gpu.getProcessedCPUQueue().contains(data));
    }

    public void testGPULearn() {

    }

    public void testUpdateTime() {
    }

    public void testSetModel() {
    }
}