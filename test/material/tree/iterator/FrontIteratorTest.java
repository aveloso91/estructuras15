package material.tree.iterator;

import material.tree.LCRSTree;
import material.tree.LinkedTree;
import material.tree.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by Alejandro on 27/5/16.
 */
public class FrontIteratorTest {

    LCRSTree<String> t = new LCRSTree<String>();

    @Before
    public void setUp() throws Exception {
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C",a);
        Position<String> d = t.add("D", c);
        Position<String> e = t.add("E",c);
        t.setIterator(new FrontIteratorFactory<>());
    }

    @Test
    public void testHasNext() throws Exception {
        Iterator<Position<String>> it = t.iterator();
        while(it.hasNext()){
            assertTrue(it.hasNext());
            it.next();
        }
        assertFalse(it.hasNext());
    }

    @Test (expected = Exception.class)
    public void testNext() throws Exception {
        Iterator<Position<String>> it = t.iterator();
        assertEquals(it.next().getElement(), "B");
        while(it.hasNext()) {
            it.next();
        }
        it.next();
    }

    @Test
    public void testFrontOrder() throws Exception {
        String test = "";
        String solution = "BDE";
        Iterator<Position<String>> it = t.iterator();
        while(it.hasNext()){
            test = test+it.next().getElement();
        }
        assertEquals(test,solution);
    }
}
