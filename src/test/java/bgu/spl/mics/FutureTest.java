package bgu.spl.mics;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class FutureTest {
    private static Future<String> f;

    @Test
    void setUp() {
        f = new Future();
    }

    @Test
    void tearDown() {
    }

    @Test
    void TestGet() {
        assertNotNull(f.get(),"Got null from get."); /** checks if it can get null */
        Thread t1 = new Thread(()->{
            try {
                Thread.sleep(5000); /** sleeps on purpose, so the get can try and get null. */
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            f.resolve("somevalue"); /** adds some value to the f, should wake up the get and make it get "somevalue" */
        });
        t1.start();
        assertEquals("somevalue", f.get(), "Expected: result, got: " +f.get()); // check that it worked.
    }

    @Test
    void TestResolve() {
        assertThrows(Exception.class,() -> f.resolve(null), "Expected an error");
        f.resolve("result");
        assertTrue(f.isDone(),"Already resolved but didn't show");
        assertEquals("result", f.get(), "Expected: result, got: " +f.get());
        assertThrows(Exception.class,() -> f.resolve("something"), "Expected an error");
    }

    @Test
    void TestisDone() {
        assertEquals(false,f.isDone(),"expected isDone() to be false, because it didn't run before.");
        f.resolve("work");
        assertEquals(true,f.isDone(),"expected isDone() to be true, because it ran before.");
    }

    @Test
    void TestTimeGet() {
        Thread t1 = new Thread(()->{
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            f.resolve("somevalue");
        });
        assertThrows(Exception.class,()-> f.get(-10,TimeUnit.MILLISECONDS),"Worked for a negative time.");
        assertNull(f.get(5000,TimeUnit.MILLISECONDS),"Expected null, got a result instead.");
        t1.start();
        long currentTime = System.currentTimeMillis();
        assertNotNull(f.get(10000,TimeUnit.MILLISECONDS),"Got null from get.");
        assertTrue(System.currentTimeMillis()-currentTime<=1000,"didn't managed to break out of the wait.");
        String ans=f.get(10,TimeUnit.MILLISECONDS);
        assertEquals("somevalue", ans, "Expected: result, got: " +ans);
    }
}