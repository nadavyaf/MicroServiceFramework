package bgu.spl.mics;

import bgu.spl.mics.application.objects.CPU;
import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.application.services.CPUService;
import bgu.spl.mics.application.services.GPUService;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MessageBusTest {

    private static MessageBusImpl mb = MessageBusImpl.getInstance();
    private static ExampleBroadcast broadcast = new ExampleBroadcast("m");
    private static ExampleEvent event = new ExampleEvent("n");

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSubscribeEvent() {
        GPU gpu = new GPU(GPU.Type.RTX3090);
        GPUService gpus = new GPUService("GPU Service", gpu);
        mb.register(gpus);
        assertFalse(mb.isMicroServiceEventRegistered(gpus, event),"Microservice shouldn't be registered yet!");
        assertTrue(mb.isMicroServiceRegistered(gpus), "Microservice should be registered");
        mb.subscribeEvent(event.getClass(), gpus);
        assertTrue(mb.isMicroServiceEventRegistered(gpus, event), "Should be registered!");
        mb.unregister(gpus);
    }

    @Test
    void testSubscribeBroadcast() {
        CPU cpu = new CPU(16);
        CPUService cpus = new CPUService("CPU Service", cpu);
        mb.register(cpus);
        assertTrue(mb.isMicroServiceRegistered(cpus), "Microservice should be registered");
        mb.subscribeBroadcast(broadcast.getClass(), cpus);
        mb.unregister(cpus);
    }

    @Test
    void testComplete() throws InterruptedException {
        GPU gpu = new GPU(GPU.Type.RTX3090);
        GPUService gpus = new GPUService("GPU Service", gpu);
        mb.register(gpus);
        mb.subscribeEvent(event.getClass(), gpus);
        assertTrue(mb.isMicroServiceEventRegistered(gpus, event), "Event should be registered!");
        mb.sendEvent(event);
        mb.complete(event, "result");
        mb.unregister(gpus);
    }

    @Test
    void testSendBroadcast() throws InterruptedException {
        CPU cpu = new CPU(16);
        CPUService cpus = new CPUService("CPU Service", cpu);
        mb.register(cpus);
        assertTrue(mb.isMicroServiceRegistered(cpus), "CPU should be registered!");
        mb.subscribeBroadcast(broadcast.getClass(), cpus);
        mb.sendBroadcast(broadcast);
        mb.unregister(cpus);
    }

    @Test
    void testSendEvent() throws InterruptedException {
        GPU gpu = new GPU(GPU.Type.RTX3090);
        GPUService gpus = new GPUService("GPU Service", gpu);
        mb.register(gpus);
        assertTrue(mb.isMicroServiceRegistered(gpus),"GPU should be registered!");
        mb.subscribeEvent(event.getClass(), gpus);
        assertTrue(mb.isMicroServiceEventRegistered(gpus, event));
        mb.sendEvent(event);
    }

    @Test
    void testRegister() {
        GPU gpu = new GPU(GPU.Type.RTX3090);
        GPUService gpus = new GPUService("GPU Service", gpu);
        assertFalse(mb.isMicroServiceRegistered(gpus), "GPU shouldnt be registered!");
        mb.register(gpus);
        assertTrue(mb.isMicroServiceRegistered(gpus), "GPU should be registered!");
        mb.unregister(gpus);
    }

    @Test
    void unregister() {
        GPU gpu = new GPU(GPU.Type.RTX3090);
        GPUService gpus = new GPUService("GPU Service", gpu);
        mb.register(gpus);
        assertTrue(mb.isMicroServiceRegistered(gpus));
        mb.unregister(gpus);
        assertFalse(mb.isMicroServiceRegistered(gpus));
    }

    @Test
    void testAwaitMessage() throws InterruptedException {
        CPU cpu = new CPU(16);
        CPUService cpus = new CPUService("CPU Service", cpu);
        mb.register(cpus);
        mb.subscribeEvent(event.getClass(), cpus);
        mb.sendEvent(event);
        mb.unregister(cpus);
    }


}