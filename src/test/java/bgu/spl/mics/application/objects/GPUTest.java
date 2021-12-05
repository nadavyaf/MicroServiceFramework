package bgu.spl.mics.application.objects;

import bgu.spl.mics.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GPUTest {
    private static GPU g;

    @BeforeEach
    void setUp() {
        g = new GPU(GPU.Type.RTX3090);
    }

    @AfterEach
    void TesttearDown() {
    }

    @Test
    void TestgetEventQueue() {
    }

    @Test
    void TestgetClusterQueue() {
    }

    @Test
    void TestGetProcessedCPUQueue(){

    }

    @Test
    void TestGetType() {
    }

    @Test
    void TestGetModel() {
    }

    @Test
    void TestGetCluster() {
    }

    @Test
    void TestGetNumberOfBatches(){

    }

    @Test
    void TestaddEvent(Event event) {
        int size = g.getEventQueue().size();
        g.addEvent(event);
        assertEquals(size + 1, g.getEventQueue().size(), "Queue size is not correct.");
    }

    @Test
    void TestextractEvent() {
        Event event = new Event() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public String toString() {
                return super.toString();
            }
        };
        assertTrue(g.getModel() == null, "Cannot overwrite a model!");
        g.addEvent(event);
        int size = g.getEventQueue().size();
        g.extractEvent();
        assertFalse(g.getModel() == null, "Model shouldn't be null!");
        assertEquals(size - 1, g.getEventQueue().size(), "Event Queue size isn't correct.");
        assertEquals(0, g.getLearnedBatches(), "Shouldn't have any learned batches from this event!");
    }

    @Test
    void Testdivide1000(Data data) {
        assertNull(g.divide1000(null), "Expected to return a null type");
        int dataSize = data.getSize();
        DataBatch db = g.divide1000(data);
        assertEquals(dataSize-1000, data.getSize(), "data's size has not decreased by 1000");
    }

    @Test
    void testDivideAll(Data data) {
//        assertThrows(IllegalArgumentException, g.divideAll(data), "Expected to throw an exception");
        int queueSize = g.getClusterQueue().size();
        g.divideAll(data);
        int numberOfBatches = data.getSize() / 1000;
        assertEquals(0, data.getSize(),"Data should be empty");
        assertEquals(queueSize + numberOfBatches, g.getClusterQueue().size(), "clusterQueue size is wrong!");
    }

    @Test
    void testClusterSend() {
        assertNotNull(g.getClusterQueue(), "The cluster queue cannot be empty!");
        assertNotEquals(0, g.getCapacity(),"The GPU capacity is 0.");
        Data data = new Data(Data.Type.Images, 0, 5000);
        g.divideAll(data);
        int queueSize = g.getClusterQueue().size();
        int capacitySize =g.getCapacity();
        g.clusterSend();
        assertEquals(queueSize-1, g.getClusterQueue().size(), "Expected the cluster queue size to decrease by one.");
        assertEquals(capacitySize-1, g.getCapacity(),"Expected the capacity to decrease by one.");
    }

    @Test
    void testInsertProcessedGPU(DataBatch data){
        assertTrue(data.isProcessedCpu(), "Data hasn't been processed by CPU");
        assertTrue(g.getProcessedCPUQueue().size() < g.getCapacity(), "Processed CPU Queue is full!");
        int processedSize = g.getProcessedCPUQueue().size();
        g.insertProcessedCPU(data);
        assertEquals(processedSize + 1, g.getProcessedCPUQueue().size());
    }

    @Test
    void testGPULearn(){
        g.getProcessedCPUQueue().add(new DataBatch());
        assertFalse(g.getProcessedCPUQueue().peek().isLearnedGpu(),"Cannot teach data that has been taught!");
        int numberLearned = g.getLearnedBatches();
        g.GPULearn();
        assertTrue(g.getProcessedCPUQueue().peek().isLearnedGpu(), "Data should've been learned");
        assertEquals(numberLearned + 1, g.getLearnedBatches());
    }

    public void testUpdateTime() {
        int currTime = g.getCurrTime();
        g.updateTime();
        assertTrue(g.getCurrTime()>currTime);
    }

    @Test
    void isDone(){

    }
}