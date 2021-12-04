package bgu.spl.mics;
import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.application.services.GPUService;
import bgu.spl.mics.application.services.StudentService;
import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageBusTest extends TestCase {
    private static MessageBusImpl mbs;
    private static GPUService m;
    private static GPU g;

    public void setUp(){
        g = new GPU(g.getEnum(2));
        m = new GPUService("gpu1 service",g);
        mbs=MessageBusImpl.getInstance();
    }

    public void testSubscribeEvent() {
       TestModelEvent TME = new TestModelEvent();
        assertThrows(Exception.class,()->mbs.subscribeEvent(TestModelEvent.class,m),"Managed to subscribe to event without registering first!");
        mbs.register(m);
        mbs.subscribeEvent(TestModelEvent.class,m);
        assertTrue("Didn't manage to subscribe to event even though should have managed to!",m.isEventSubscribed(TME));
    }

    public void testSubscribeBroadcast() {
        StudentService st = new StudentService("Student 1");
        PublishConferenceBroadcast b = new PublishConferenceBroadcast();
        assertThrows(Exception.class,()->mbs.subscribeBroadcast(PublishConferenceBroadcast.class,st),"Managed to subscribe to broadcast without registering first!");
        mbs.register(st);
        mbs.subscribeBroadcast(PublishConferenceBroadcast.class,st);
        assertTrue("Didn't manage to subscribe to broadcast even though should have managed to!",st.isBroadcastSubscribed(b));

    }

    public void testComplete() {
        TestModelEvent TEM= new TestModelEvent();
        assertThrows(Exception.class,()->mbs.complete(TEM,null),"Managed to work, even though the result is null");
        String result = "works";
        assertThrows(Exception.class,()->mbs.complete(null,result),"Managed to work, even though the event is null");
        mbs.complete(TEM,result);
        assertTrue("Didn't resolve the Future object.",TEM.getFuture().isDone());
    }

    public void testSendBroadcast() {
        assertThrows(Exception.class,()->mbs.sendBroadcast(null),"Managed to send a null broadcast.");
        StudentService st1 = new StudentService("Student 1");
        StudentService st2 = new StudentService("Student 2");
        PublishConferenceBroadcast b = new PublishConferenceBroadcast();
        mbs.register(st1);
        mbs.register(st2);
        mbs.subscribeBroadcast(PublishConferenceBroadcast.class,st1);
        mbs.subscribeBroadcast(PublishConferenceBroadcast.class,st2);
        mbs.sendBroadcast(b);
        assertFalse("sent a broadcast to a subscribed service, but it didn't add it to it's messageQueue",st1.getMessageQueue().isEmpty());
        assertFalse("sent a broadcast to a subscribed service, but it didn't add it to it's messageQueue (was the second service to get the broadcast)",st2.getMessageQueue().isEmpty());
    }

    public void testSendEvent() { //still need to check.
        assertThrows(Exception.class,()->mbs.sendEvent(null),"Managed to send a null event.");
        TestModelEvent TME = new TestModelEvent();
        GPU gpu = new GPU(g.getEnum(2));
        GPUService n = new GPUService("gpu2 service", gpu);
        assertNull("Sent an event when there were no subscribed services, and still got a result different than null, expected null.",mbs.sendEvent(TME));
        mbs.register(m);
        mbs.subscribeEvent(TestModelEvent.class,m);
        Future result = mbs.sendEvent(TME);
        assertTrue("Should have resolved the future, but it didn't",result.isDone()); //checks if we got a future with result, should cover if the event resolved.
    }

    public void testRegister() {
        mbs.register(m);
        assertTrue(mbs.isMicroServiceRegistered(m));
    }

    public void testUnregister() {
    mbs.unregister(m);
    assertFalse("Still registered even though it unregistered.",mbs.isMicroServiceRegistered(m));
    mbs.register(m);
    mbs.subscribeEvent(TestModelEvent.class,m);
    mbs.sendEvent(new TestModelEvent());
    mbs.sendEvent(new TestModelEvent());
    mbs.sendEvent(new TestModelEvent());
    mbs.sendEvent(new TestModelEvent());
    mbs.sendEvent(new TestModelEvent());
    mbs.unregister(m);
    assertNull("Should empty the MessageQueue when unregisters, but didn't empty it.",m.getMessageQueue());
    assertFalse("Still registered even though it unregistered.",mbs.isMicroServiceRegistered(m));
    }

    public void testAwaitMessage() {
        assertThrows(Exception.class,()->mbs.awaitMessage(m),"Should have threw an exception since the service is not registered.");
        mbs.register(m);
        mbs.subscribeEvent(TestModelEvent.class,m);
        mbs.sendEvent(new TestModelEvent());
        Message b = m.getMessageQueue().peek();
        Message a= new TestModelEvent();
        try {
            a = mbs.awaitMessage(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue("The MessageQueue didn't delete the message from it",m.getMessageQueue().isEmpty());
        assertEquals("The message that came out of awaitMessage, is not the same as the message that went in.",a,b);
    }
}