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
        f.resolve("something");
        assertNotNull(f.get(), "Expected a non-null type");
        assertEquals("something", f.get(), "Expected: something, got: " +f.get());
    }

    @org.junit.jupiter.api.Test
    void TestResolve() {
        assertThrows(Exception.class,() -> f.resolve(null), "Expected an error");
        f.resolve("result");
        assertEquals("result", f.get(), "Expected: result, got: " +f.get());
        assertTrue(f.isDone(), "Expected: true, Got: false");
        assertThrows(Exception.class,() -> f.resolve("something"), "Expected an error");
    }

    @org.junit.jupiter.api.Test
    void TestisDone() {
        assertEquals(false, f.isDone(), "Expected: false, Got: True");
        f.resolve("work");
        assertEquals(true, f.isDone(), "Expected: True, Got: False");
    }

    @org.junit.jupiter.api.Test
    void TestTimeGet() {
        assertThrows(Exception.class,() -> f.get(-10, TimeUnit.MILLISECONDS), "Illegal time");
        assertNull(f.get(10,TimeUnit.MILLISECONDS),"Must return a null type");
        f.resolve("something");
        assertNotNull(f.get(5, TimeUnit.MILLISECONDS), "Expected a non-null type");
        String ans = f.get(5, TimeUnit.MILLISECONDS);
        assertEquals("something", ans, "Expected: something, got: " + ans);
    }
}