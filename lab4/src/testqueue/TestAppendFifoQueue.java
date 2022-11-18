package testqueue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import queue_singlelinkedlist.FifoQueue;

import static org.junit.jupiter.api.Assertions.*;

class TestAppendFifoQueue {
    private FifoQueue<Integer> queueOne;
    private FifoQueue<Integer> queueTwo;

    @BeforeEach
    void setUp() {
        queueOne = new FifoQueue<Integer>();
        queueTwo = new FifoQueue<Integer>();
    }

    @AfterEach
    void tearDown(){
        queueOne = null;
        queueTwo = null;
    }

    /**
     * Test if appending two empty queues yields two empty queues
     */
    @Test
    void testAppendTwoEmpty() {
        queueOne.append(queueTwo);
        assertTrue(queueOne.isEmpty(), "Wrong result from empty queue");
        assertTrue(queueTwo.isEmpty(), "Wrong result from empty queue");
    }

    /**
     * Test if appending an empty queue to a non-empty queue works
     */
    @Test
    void testAppendEmpty() {
        queueOne.offer(5);
        queueOne.append(queueTwo);

        assertEquals(5, queueOne.poll(), "Wrong value from top of queue");
        assertTrue(queueTwo.isEmpty(), "Wrong result from empty queue");
    }

    /**
     * Test if appending a non-empty queue to an empty queue works
     */
    @Test
    void testAppendNonEmpty() {
        queueTwo.offer(5);
        queueOne.append(queueTwo);

        assertEquals(5, queueOne.peek(), "Wrong value from top of queue");
        assertEquals(1, queueOne.size(), "Wrong size from queue");
        assertTrue(queueTwo.isEmpty(), "Wrong result from empty queue");
    }

    /**
     * Test if appending two non-empty queues work
     */
    @Test
    void testAppendTwoNonEmpty() {
        for(int i = 0; i < 5; ++i) {
            queueOne.offer(i);
            queueTwo.offer(i + 5);
        }

        queueOne.append(queueTwo);

        assertTrue(queueTwo.isEmpty(), "Wrong result from empty queue");

        assertEquals(0, queueOne.peek(), "Wrong value from top of queue");
        assertEquals(10, queueOne.size(), "Wrong size from queue");

        for(int i = 0; i < 5; ++i) queueOne.poll();
        assertEquals(5, queueOne.peek(), "Wrong value in middle of queue");

        for(int i = 0; i < 4; ++i) queueOne.poll();
        assertEquals(9, queueOne.peek(), "Wrong value at end of queue");
    }

    /**
     * Test if appending a non-empty queue to an empty queue works
     */
    @Test
    void testAppendWithSelf() {
        assertThrows(IllegalArgumentException.class, () -> queueOne.append(queueOne),
                "Does not throw when appending queue to itself");

        for(int i = 0; i < 5; ++i) {
            queueOne.offer(i);
        }

        assertThrows(IllegalArgumentException.class, () -> queueOne.append(queueOne),
                "Does not throw when appending queue to itself");
    }
}
