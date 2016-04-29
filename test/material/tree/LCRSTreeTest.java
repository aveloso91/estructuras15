package material.tree;

import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Alejandro on 15/4/16.
 */
public class LCRSTreeTest {

    LCRSTree<String> t = new LCRSTree<String>();
    LCRSTree<String> t2 = new LCRSTree<String>();

    @Before
    public void setUp() throws Exception {
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C",a);
        Position<String> d = t.add("D", c);
        Position<String> e = t.add("E",c);
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(5, t.size());
        assertEquals(0, t2.size());
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertFalse(t.isEmpty());
        assertTrue(t2.isEmpty());
    }

    @Test
    public void testIsInternal() throws Exception {
        Position<String> a = t2.addRoot("A");
        Position<String> b = t2.add("B", a);
        Position<String> c = t2.add("C",a);
        Position<String> d = t2.add("D", c);
        Position<String> e = t2.add("E", c);
        assertTrue(t2.isInternal(a));
        assertTrue(t2.isInternal(c));
        assertFalse(t2.isInternal(e));
        assertFalse(t2.isInternal(d));
    }

    @Test
    public void testIsRoot() throws Exception {
        Position<String> a = t2.addRoot("A");
        Position<String> b = t2.add("B", a);
        Position<String> c = t2.add("C",a);
        Position<String> d = t2.add("D", c);
        Position<String> e = t2.add("E", c);
        assertTrue(t2.isRoot(a));
        assertFalse(t2.isRoot(b));
    }

    @Test
    public void testRoot() throws Exception {
        assertEquals("A", t.root().getElement());
    }

    @Test (expected = IllegalStateException.class)
    public void testRoot2() throws Exception {
        assertEquals("A",t2.root().getElement());
    }

    @Test
    public void testParent() throws Exception {
        Position<String> a = t2.addRoot("A");
        Position<String> b = t2.add("B", a);
        Position<String> c = t2.add("C",a);
        Position<String> d = t2.add("D", c);
        Position<String> e = t2.add("E", c);
        assertEquals(t2.parent(d).getElement(),"C");
        assertEquals(t2.parent(c).getElement(),"A");
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testParent2() throws Exception {
        Position<String> a = t2.addRoot("A");
        Position<String> b = t2.add("B", a);
        Position<String> c = t2.add("C",a);
        Position<String> d = t2.add("D", c);
        Position<String> e = t2.add("E", c);
        t2.parent(a);
    }

    @Test
    public void testChildren() throws Exception {
        List<Position<String>> children = (List<Position<String>>) t.children(t.root());
        assertEquals("B",children.get(0).getElement());
        assertEquals("C", children.get(1).getElement());
        t2.addRoot("A");
        children = (List<Position<String>>) t2.children(t2.root());
        assertEquals(0, children.size());
    }

    @Test
    public void testReplace() throws Exception {

    }

    @Test
    public void testAddRoot() throws Exception {
        Position<String> a = t2.addRoot("A");
        assertTrue(t2.isRoot(a));
    }

    @Test
    public void testSwapElements() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {
        Position<String> a = t2.addRoot("A");
        Position<String> b = t2.add("B", a);
        Position<String> c = t2.add("C",a);
        Position<String> d = t2.add("D", c);
        Position<String> e = t2.add("E", c);
        String out = "";
        for(Position<String> s : t2){
            out += s.getElement();
        }
        assertEquals("ABCDE",out);
    }

    @Test (expected = IllegalStateException.class)
    public void testRemove() throws Exception {
        Position<String> a = t2.addRoot("A");
        Position<String> b = t2.add("B", a);
        Position<String> c = t2.add("C",a);
        Position<String> d = t2.add("D", c);
        Position<String> e = t2.add("E", c);
        t2.remove(d);
        String out = "";
        for(Position<String> s : t2){
            out += s.getElement();
        }
        assertEquals("ABCE", out);
        t2.remove(b);
        out = "";
        for(Position<String> s : t2){
            out += s.getElement();
        }
        assertEquals("ACE",out);
        t2.remove(a);
        assertEquals(null,t2.root());
    }

    @Test
    public void testRemove2() throws Exception {
        Position<String> a = t2.addRoot("A");
        Position<String> b = t2.add("B", a);
        Position<String> c = t2.add("C",a);
        Position<String> d = t2.add("D", c);
        Position<String> e = t2.add("E", c);
        t2.remove(c);
        String out = "";
        for(Position<String> s : t2){
            out += s.getElement();
        }
        assertEquals("AB", out);
    }

    @Test
    public void testMoveSubtree() throws Exception {
        Position<String> a = t2.addRoot("A");
        Position<String> b = t2.add("B", a);
        Position<String> c = t2.add("C", a);
        Position<String> d = t2.add("D", a);
        Position<String> e = t2.add("E", b);
        Position<String> f = t2.add("F", b);
        Position<String> g = t2.add("G", f);
        Position<String> h = t2.add("H", f);
        Position<String> i = t2.add("I", h);
        Position<String> j = t2.add("J", i);
        t2.moveSubtree(f, c);
        assertEquals(t2.parent(f), c);
        assertEquals(t2.parent(h), f);
    }

    @Test (expected = IllegalStateException.class)
    public void testMoveSubtree2() {
        Position<String> a = t2.addRoot("A");
        Position<String> b = t2.add("B", a);
        Position<String> c = t2.add("C", a);
        Position<String> d = t2.add("D", a);
        Position<String> e = t2.add("E", b);
        Position<String> f = t2.add("F", b);
        Position<String> g = t2.add("G", f);
        Position<String> h = t2.add("H", f);
        Position<String> i = t2.add("I", h);
        Position<String> j = t2.add("J", i);
        t2.moveSubtree(f, c);
        assertEquals(t2.parent(f), c);
        assertEquals(t2.parent(h), f);
        t2.moveSubtree(f, i);
        assertEquals(t2.parent(f), c);
        assertEquals(t2.parent(h), f);
    }
}