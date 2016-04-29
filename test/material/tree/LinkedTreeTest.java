/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree;

import material.tree.iterator.BFSIterator;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Basic tests for LinkedTree class
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro
 */
public class LinkedTreeTest {

    /**
     * Test of size method, of class LinkedTree.
     */
    @Test
    public void testSize() {
        LinkedTree<String> t = new LinkedTree<String>();
        assertEquals(t.size(), 0);
        Position<String> a = t.addRoot("A");
        assertEquals(t.size(), 1);
    }

    /**
     * Test of isEmpty method, of class LinkedTree.
     */
    @Test
    public void testIsEmpty() {
        LinkedTree<String> t = new LinkedTree<String>();
        assertTrue(t.isEmpty());
    }

    /**
     * Test of isInternal method, of class LinkedTree.
     */
    @Test
    public void testIsInternal() {
        LinkedTree<String> t = new LinkedTree<String>();
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C", a);
        Position<String> d = t.add("D", b);
        assertTrue(t.isInternal(b));
    }

    /**
     * Test of isLeaf method, of class LinkedTree.
     */
    @Test
    public void testIsLeaf() {
        LinkedTree<String> t = new LinkedTree<String>();
        Position<String> a = t.addRoot("A");
        assertTrue(t.isLeaf(a));
    }

    /**
     * Test of isRoot method, of class LinkedTree.
     */
    @Test
    public void testIsRoot() {
        LinkedTree<String> t = new LinkedTree<String>();
        Position<String> a = t.addRoot("A");
        assertTrue(t.isRoot(a));
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C", a);
        Position<String> d = t.add("D", b);
        assertTrue(t.isRoot(a));
    }

    /**
     * Test of root method, of class LinkedTree.
     */
    @Test
    public void testRoot() {
        LinkedTree<String> t = new LinkedTree<String>();
        Position<String> a = t.addRoot("A");
        assertEquals(t.root(), a);
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C", a);
        Position<String> d = t.add("D", b);
        assertEquals(t.root(), a);
    }
    
    /**
     * Test of parent method, of class LinkedTree.
     */
    @Test
    public void testParent() {
        LinkedTree<String> t = new LinkedTree<String>();
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C", a);
        Position<String> d = t.add("D", b);
        assertEquals(t.parent(b), a);
    }

    /**
     * Test of children method, of class LinkedTree.
     */
    @Test
    public void testChildren() {
        LinkedTree<String> t = new LinkedTree<String>();
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C", a);
        Position<String> d = t.add("D", b);
        Set<Position<String>> myChildren = new HashSet<Position<String>>();
        myChildren.add(b);
        myChildren.add(c);
        for (Position<String> node : t.children(a)) {
            assertTrue(myChildren.contains(node));
        }
    }

    /**
     * Test of replace method, of class LinkedTree.
     */
    @Test
    public void testReplace() {
        LinkedTree<String> t = new LinkedTree<String>();
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C", a);
        Position<String> d = t.add("D", b);
        assertEquals(d.getElement(), "D");
        t.replace(d, "nuevo");
        assertEquals(d.getElement(), "nuevo");
    }

    
    /**
     * Test of addRoot method, of class LinkedTree.
     */
    @Test
    public void testAddRoot() {
        LinkedTree<String> t = new LinkedTree<String>();
        Position<String> a = t.addRoot("A");
        assertEquals(a, t.root());
    }

    /**
     * Test of swapElements method, of class LinkedTree.
     */
    @Test
    public void testSwapElements() {
        LinkedTree<String> t = new LinkedTree<String>();
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        t.swapElements(a, b);
        assertEquals(b.getElement(), "A");
        assertEquals(a.getElement(), "B");
    }

    /**
     * Test of remove method, of class LinkedTree.
     */
    @Test
    public void testRemove() {
        LinkedTree<String> t = new LinkedTree<String>();
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C", a);
        Position<String> d = t.add("D", b);
        t.remove(d);
        Iterator<Position<String>> it = new BFSIterator<String>(t,t.root());
        while (it.hasNext()) {
            Position<String> node = it.next();
            assertNotSame(node.getElement(),d);
        }
    }

    /**
     * Test of MoveSubtree remove method, of class LinkedTree.
     */
    @Test
    public void testMoveSubtree() {
        LinkedTree<String> t = new LinkedTree<String>();
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C", a);
        Position<String> d = t.add("D", a);
        Position<String> e = t.add("E", b);
        Position<String> f = t.add("F", b);
        Position<String> g = t.add("G", f);
        Position<String> h = t.add("H", f);
        Position<String> i = t.add("I", h);
        Position<String> j = t.add("J", i);
        t.moveSubtree(f, c);
        assertEquals(t.parent(f), c);
        assertEquals(t.parent(h), f);
    }

    /**
     * Test of MoveSubtree remove method, of class LinkedTree.
     */
    @Test (expected = IllegalStateException.class)
    public void testMoveSubtree2() {
        LinkedTree<String> t = new LinkedTree<String>();
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C", a);
        Position<String> d = t.add("D", a);
        Position<String> e = t.add("E", b);
        Position<String> f = t.add("F", b);
        Position<String> g = t.add("G", f);
        Position<String> h = t.add("H", f);
        Position<String> i = t.add("I", h);
        Position<String> j = t.add("J", i);
        t.moveSubtree(f, c);
        assertEquals(t.parent(f), c);
        assertEquals(t.parent(h), f);
        t.moveSubtree(f, i);
        assertEquals(t.parent(f), c);
        assertEquals(t.parent(h), f);
    }

}
