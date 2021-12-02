package bgu.spl.mics;

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
        assertNotNull(f.get());
    }

    @org.junit.jupiter.api.Test
    void TestResolve() {
        assertThrows(Exception.class,() -> f.resolve(null), "Expected an error");
        f.resolve("result");
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
    }
}