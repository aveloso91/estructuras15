package material.lineal.queue;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Alejandro on 8/4/16.
 */
public class LinkedQueueTest extends TestCase {

    public void testSize() throws Exception {
        LinkedQueue<Integer> q = new LinkedQueue<Integer>();
        Assert.assertSame(0, q.size());
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
    @Test
    public void testIsEmpty() throws Exception {
        LinkedQueue<Integer> q = new LinkedQueue<Integer>();
        Assert.assertTrue(q.isEmpty());
        q.enqueue(1);
        Assert.assertFalse(q.isEmpty());
        q.enqueue(3);
        Assert.assertFalse(q.isEmpty());
        q.dequeue();
        Assert.assertFalse(q.isEmpty());
        q.dequeue();
        Assert.assertTrue(q.isEmpty());
    }
    @Test
    public void testFront() throws Exception {
        LinkedQueue<Integer> q = new LinkedQueue<Integer>();
        q.enqueue(9);
        Assert.assertEquals((Integer) 9, q.front());
        q.enqueue(3);
        q.enqueue(2);
        Assert.assertEquals((Integer) 9, q.front());
        q.dequeue();
        Assert.assertEquals((Integer) 3, q.front());
        q.dequeue();
        Assert.assertEquals((Integer) 2, q.front());

    }
    @Test
    public void testEnqueue() throws Exception {
        LinkedQueue<Integer> q = new LinkedQueue<Integer>();
        q.enqueue(9);
        Assert.assertEquals((Integer) 9, q.front());
        Assert.assertFalse(q.isEmpty());
        Assert.assertEquals(1,q.size());
        q.enqueue(2);
        Assert.assertEquals((Integer) 9, q.front());
        Assert.assertFalse(q.isEmpty());
        Assert.assertEquals(2,q.size());
    }
    @Test
    public void testDequeue() throws Exception {
        LinkedQueue<Integer> q = new LinkedQueue<Integer>();
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