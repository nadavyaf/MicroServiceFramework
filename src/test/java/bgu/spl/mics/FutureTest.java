package bgu.spl.mics;

import static org.junit.jupiter.api.Assertions.*;

class FutureTest {

    private static Future<String> a;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        a = new Future<String>();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void TestGet() {
    }

    @org.junit.jupiter.api.Test
    void TestResolve() {
        assertThrows(Exception.class,() -> a.resolve(null), "Expected an error");
        a.resolve("result");
        assertEquals("result", a.get(), "Expected: result, got: " +a.get());
        assertThrows(Exception.class,() -> a.resolve("something"), "Expected an error");
    }

    @org.junit.jupiter.api.Test
    void TestisDone() {
    }

    @org.junit.jupiter.api.Test
    void TestTimeGet() {
    }
}