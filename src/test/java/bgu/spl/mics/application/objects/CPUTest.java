package bgu.spl.mics.application.objects;
import org.junit.jupiter.api.Test;

import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

public class CPUTest{
    private static CPU cpu;
    void setUp() {
        cpu = new CPU(4);
    }
    @Test
    public void testAddData() {
        long size = cpu.getData().size();
        cpu.addData(new DataBatch());
        assertEquals(size+1,cpu.getData().size(),"the size didn't increment.");
    }
    @Test
    public void testProcessed() {
    assertThrows(Exception.class,()-> cpu.Proccessed(),"Data is empty, but still managed to run.");
    long size = cpu.getData().size();
    DataBatch a = new DataBatch();
    cpu.addData(a);
    cpu.Proccessed();
    assertEquals(size,cpu.getData().size());
    assertTrue(a.isProcessedCpu());
    }
    @Test
    public void testUpdateTime() {
    int currTime = cpu.getTime();
    cpu.updateTime();
    assertTrue(cpu.getTime()>currTime);
    }
}