package bgu.spl.mics.application.objects;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CPUTest {
    private CPU cpu = new CPU(32);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void testUpdateTime() throws InterruptedException {
        GPU gpu = new GPU(GPU.Type.RTX3090);
        DataBatch db = new DataBatch(Data.Type.Images, gpu);
        cpu.setCurrDataBatch(db);
        cpu.setCurrTime(100);
        int time = cpu.getTime();
        cpu.updateTime();
        Assert.assertEquals(cpu.getTime(), time+1);
    }

}