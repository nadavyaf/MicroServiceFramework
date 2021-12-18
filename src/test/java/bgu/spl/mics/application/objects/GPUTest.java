package bgu.spl.mics.application.objects;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class GPUTest {
    GPU gpu = new GPU(GPU.Type.RTX3090);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void divideAll() {
        Data data = new Data(Data.Type.Images, 20000);
        Student student = new Student("Nadav", "Natural Science", Student.Degree.MSc);
        Model model = new Model(data, "Shabalala", student);
        Assert.assertNull(gpu.getModel());
        gpu.setModel(model);
        Assert.assertNotNull(gpu.getModel());
        gpu.divideAll();
    }

    @Test
    void insertProcessedCPU() {
        DataBatch db = new DataBatch(Data.Type.Images, gpu);
        Assert.assertFalse("Data batch was not added!", gpu.getProcessedCPUQueue().contains(db));
        gpu.insertProcessedCPU(db);
        Assert.assertTrue("Data batch was added!", gpu.getProcessedCPUQueue().contains(db));
    }

    @Test
    void GPULearn() throws InterruptedException {
        DataBatch db = new DataBatch(Data.Type.Images, gpu);
        gpu.setLearnedBatches(10);;
        int learnt = gpu.getLearnedBatches();
        gpu.setCurrBatch(db);
        Data data = new Data(Data.Type.Images, 20000);
        Student student = new Student("Nadav", "Natural Science", Student.Degree.MSc);
        Model model = new Model(data, "Shabalala", student);
        gpu.setModel(model);
        gpu.GPULearn();
        Assert.assertTrue("Learned batches should have increased by one", gpu.getLearnedBatches() == learnt + 1);
    }

    @Test
    void updateTime() throws InterruptedException {
        gpu.setCurrTime(10);
        int time = gpu.getCurrTime();
        gpu.updateTime();
        Assert.assertTrue("Time should've increased by one", gpu.getCurrTime() == time + 1);
    }
}