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
        g = new GPU(GPU.Type.RTX3090, null);
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
    void TestgetLearnedQueue() {
    }

    @Test
    void TestgetType() {
    }

    @Test
    void TestgetModel() {
    }

    @Test
    void TestgetCluster() {
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
        g.addEvent(event);
        int size = g.getEventQueue().size();
        g.extractEvent();
        assertEquals(size - 1, g.getEventQueue().size(), "Event Queue size isn't correct.");
    }

    @Test
    void Testdivide1000(Data data) {
        assertNull(g.divide1000(null), "Expected to return a null type");
        int dataSize = data.getSize();
        DataBatch db = g.divide1000(data);
        assertEquals(1000, db.getSize(), "Data batch isnt of size 1000");
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
    void testInsertLearned(Event event) {
        int learnedSize = g.getLearnedQueue().size();
        g.insertLearned(event);
        assertEquals(learnedSize +1, g.getLearnedQueue(), "Expected the learned queue size to increase by one");
    }
}