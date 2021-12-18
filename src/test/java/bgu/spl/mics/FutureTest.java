package bgu.spl.mics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class FutureTest {
    Future<String> future = new Future();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown(){
    }

    @Test
    void testGet() throws InterruptedException {
        future.resolve("resolved");
        assertNotNull(future.get(), "Should return a non-null type!");
    }

    @Test
    void testResolve() throws InterruptedException {
        future.resolve("resolved");
        assertNotNull(future.get());
    }

    @Test
    void testIsDone() {
        assertFalse(future.isDone(), "Should return false!");
        future.resolve("Done");
        assertTrue(future.isDone(), "Should return true!");
    }

    @Test
    void testGetTimeout() throws InterruptedException {
        assertNull(future.get(50, TimeUnit.MILLISECONDS), "Null type expected!");
        future.resolve("resolve");
        assertNotNull(future.get(10, TimeUnit.MILLISECONDS));
    }
}