package bgu.spl.mics;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class FutureTest {
    private static Future<String> f;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        f = new Future();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void TestGet() {
        f.resolve("somevalue");
        assertNotNull(f.get(),"Got null from get.");
        assertEquals("somevalue", f.get(), "Expected: result, got: " +f.get());
    }

    @org.junit.jupiter.api.Test
    void TestResolve() {
        assertThrows(Exception.class,() -> f.resolve(null), "Expected an error");
        f.resolve("result");
        assertTrue(f.isDone(),"Already resolved but didn't show");
        assertEquals("result", f.get(), "Expected: result, got: " +f.get());
        assertThrows(Exception.class,() -> f.resolve("something"), "Expected an error");
    }

    @org.junit.jupiter.api.Test
    void TestisDone() {
        assertEquals(false,f.isDone(),"expected isDone() to be false, because it didn't run before.");
        f.resolve("work");
        assertEquals(true,f.isDone(),"expected isDone() to be true, because it ran before.");
    }

    @org.junit.jupiter.api.Test
    void TestTimeGet() {
        assertThrows(Exception.class,()-> f.get(-10,TimeUnit.MILLISECONDS),"Worked for a negative time.");
        long currentTime = System.currentTimeMillis();
        assertNull(f.get(500,TimeUnit.MILLISECONDS),"Expected null, got a result instead.");
        assertTrue(System.currentTimeMillis()-currentTime<=500);
        f.resolve("somevalue");
        assertNotNull(f.get(10,TimeUnit.MILLISECONDS),"Got null from get.");
        String ans=f.get(10,TimeUnit.MILLISECONDS);
        assertEquals("somevalue", ans, "Expected: result, got: " +ans);
    }
}