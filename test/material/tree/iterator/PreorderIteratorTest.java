package material.tree.iterator;

import material.tree.LCRSTree;
import material.tree.LinkedTree;
import material.tree.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alejandro on 27/5/16.
 */
public class PreorderIteratorTest {

    LCRSTree<String> t = new LCRSTree<String>();

    @Before
    public void setUp() throws Exception {
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C",a);
        Position<String> d = t.add("D", c);
        Position<String> e = t.add("E",c);
        t.setIterator(new PreorderIteratorFactory<>());
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
        assertEquals(it.next().getElement(), "A");
        while(it.hasNext()) {
            it.next();
        }
        it.next();
    }

    @Test
    public void testPreOrder() throws Exception {
        String test = "";
        String solution = "ABCDE";
        Iterator<Position<String>> it = t.iterator();
        while(it.hasNext()){
            test = test+it.next().getElement();
        }
        assertEquals(test,solution);
    }
}
