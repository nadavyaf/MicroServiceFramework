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

    }

    @org.junit.jupiter.api.Test
    void TestisDone() {
    }

    @org.junit.jupiter.api.Test
    void TestTimeGet() {
    }
}