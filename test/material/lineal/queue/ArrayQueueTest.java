package material.lineal.queue;

import org.junit.Assert;
import junit.framework.TestCase;

/**
 * Created by Alejandro on 8/4/16.
 */
public class ArrayQueueTest extends TestCase {

    public void testSize() throws Exception {
        ArrayQueue<Integer> q = new ArrayQueue<Integer>(5);
        Assert.assertSame(0,q.size());
        q.enqueue(7);
        Assert.assertSame(1, q.size());
        q.enqueue(7);
        Assert.assertSame(2, q.size());
        q.enqueue(7);
        Assert.assertSame(3, q.size());
        q.enqueue(7);
        Assert.assertSame(4, q.size());
        q.dequeue();
        Assert.assertSame(3, q.size());
        q.dequeue();
        q.dequeue();
        Assert.assertSame(1, q.size());
        q.dequeue();
        Assert.assertSame(0, q.size());
    }

    public void testIsEmpty() throws Exception {
        ArrayQueue<Integer> q = new ArrayQueue<Integer>(5);
        Assert.assertTrue(q.isEmpty());
        q.enqueue(1);
        Assert.assertFalse(q.isEmpty());
    }

    public void testFront() throws Exception {
        ArrayQueue<Integer> q = new ArrayQueue<Integer>(5);
        q.enqueue(9);
        Assert.assertEquals((Integer) 9, q.front());
        q.enqueue(3);
        q.enqueue(2);
        Assert.assertEquals((Integer) 9, q.front());
        q.dequeue();
        Assert.assertEquals((Integer) 3, q.front());
        q.dequeue();
        Assert.assertEquals((Integer) 2, q.front());
        q.enqueue(6);
        Assert.assertEquals((Integer) 2, q.front());
        q.enqueue(1);
        Assert.assertEquals((Integer) 2, q.front());
        q.dequeue();
        Assert.assertEquals((Integer) 6, q.front());
        q.dequeue();
        Assert.assertEquals((Integer) 1, q.front());
    }

    public void testEnqueue() throws Exception {
        ArrayQueue<Integer> q = new ArrayQueue<Integer>(5);
        q.enqueue(9);
        Assert.assertEquals((Integer) 9, q.front());
        Assert.assertFalse(q.isEmpty());
        Assert.assertEquals(1,q.size());
        q.enqueue(2);
        Assert.assertEquals((Integer) 9, q.front());
        Assert.assertFalse(q.isEmpty());
        Assert.assertEquals(2,q.size());
    }

    public void testDequeue() throws Exception {
        ArrayQueue<Integer> q = new ArrayQueue<Integer>(5);
        q.enqueue(9);
        q.enqueue(3);
        q.enqueue(2);
        Assert.assertEquals((Integer) 9, q.front());
        Assert.assertFalse(q.isEmpty());
        Assert.assertEquals(3, q.size());
        q.dequeue();
        Assert.assertEquals((Integer) 3, q.front());
        Assert.assertFalse(q.isEmpty());
        Assert.assertEquals(2, q.size());
        q.dequeue();
        Assert.assertEquals((Integer) 2, q.front());
        Assert.assertFalse(q.isEmpty());
        Assert.assertEquals(1, q.size());
        q.dequeue();
        Assert.assertTrue(q.isEmpty());
        Assert.assertEquals(0, q.size());
    }
}