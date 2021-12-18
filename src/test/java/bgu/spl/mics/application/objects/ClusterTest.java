package bgu.spl.mics.application.objects;

import junit.framework.TestCase;

public class ClusterTest extends TestCase {

    public void testGetInstance() {
        Cluster a = Cluster.getInstance();
        assertNotNull("Cluster getInstance is null",a);
        Cluster b = Cluster.getInstance();
        assertEquals("Cluster instance creating new cluster",a,b);
    }

    public void testSendToGPU() {
        GPU gpu1 = new GPU(GPU.Type.RTX3090);
        GPU gpu2 = new GPU(GPU.Type.RTX2080);
        Cluster cluster = Cluster.getInstance();

    }

    public void testSendToCPU() {
    }

    public void testAddGPU() {
        for (int i =0;i<50;i++){
            Cluster.getInstance().addGPU(new GPU(GPU.Type.RTX3090));
        }
        for (int i =0;i<50;i++){
            Cluster.getInstance().addGPU(new GPU(GPU.Type.RTX2080));
        }
        for (int i =0;i<50;i++){
            Cluster.getInstance().addGPU(new GPU(GPU.Type.GTX1080));
        }
        assertEquals("Not all of the GPUs entered",Cluster.getInstance().getGPUS().size(),150);
    }

    public void testAddCPU() {
    }
}