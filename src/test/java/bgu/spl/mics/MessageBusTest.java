package bgu.spl.mics;

import bgu.spl.mics.application.services.GPUService;
import bgu.spl.mics.example.messages.ExampleEvent;
import junit.framework.TestCase;

public class MessageBusTest extends TestCase {
    private static MessageBusImpl mbs;

    public void setUp(){
        mbs=MessageBusImpl.getInstance();
    }

    public void testSubscribeEvent() {
     /**  MicroService m = new GPUService("GPU1");
       TestModelEvent TME = null;
        mbs.subscribeEvent(TME,m);
        assertTrue(m.isSubscribed); */
    }

    public void testSubscribeBroadcast() {
    }

    public void testComplete() {

    }

    public void testSendBroadcast() {
    }

    public void testSendEvent() {
    }

    public void testRegister() {
        GPUService m = new GPUService("gpu1");
        mbs.register(m);
        try {
            m.getGPUQueue().put(new TestModelEvent());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testUnregister() {
    }

    public void testAwaitMessage() {
    }
}