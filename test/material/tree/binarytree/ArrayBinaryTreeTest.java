package material.tree.binarytree;

import junit.framework.Assert;
import material.tree.Position;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/**
 * Created by Alejandro on 3/6/16.
 */
public class ArrayBinaryTreeTest {
    /**
     * Test of size method, of class LinkedBinaryTree.
     */
    @Test
    public void testSize() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        assertEquals(t.size(), 1);
    }

    /**
     * Test of isEmpty method, of class LinkedBinaryTree.
     */
    @Test
    public void testIsEmpty() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        assertTrue(t.isEmpty());
    }

    /**
     * Test of isInternal method, of class LinkedBinaryTree.
     */
    @Test
    public void testIsInternal() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        assertTrue(t.isInternal(p));
    }

    /**
     * Test of isLeaf method, of class LinkedBinaryTree.
     */
    @Test
    public void testIsLeaf() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        assertTrue(t.isLeaf(p));
    }

    /**
     * Test of isRoot method, of class LinkedBinaryTree.
     */
    @Test
    public void testIsRoot() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        assertTrue(t.isRoot(p));
    }

    /**
     * Test of hasLeft method, of class LinkedBinaryTree.
     */
    @Test
    public void testHasLeft() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        assertTrue(t.hasLeft(p));
    }

    /**
     * Test of hasRight method, of class LinkedBinaryTree.
     */
    @Test
    public void testHasRight() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> h = t.insertRight(p, "*");
        assertTrue(t.hasRight(p));
    }

    /**
     * Test of root method, of class LinkedBinaryTree.
     */
    @Test
    public void testRoot() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        assertEquals(t.root(), p);
        assertNotSame(t.root(), h);
    }

    /**
     * Test of left method, of class LinkedBinaryTree.
     */
    @Test
    public void testLeft() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        assertEquals(t.left(p), n2);
    }

    /**
     * Test of right method, of class LinkedBinaryTree.
     */
    @Test
    public void testRight() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> h = t.insertRight(p, "*");
        assertEquals(t.right(p), h);
    }

    /**
     * Test of parent method, of class LinkedBinaryTree.
     */
    @Test
    public void testParent() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        assertEquals(t.parent(n2), p);
    }

    /**
     * Test of children method, of class LinkedBinaryTree.
     */
    @Test
    public void testChildren() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        List<Position<String>> myChildren = new ArrayList<>();
        myChildren.add(n2);
        myChildren.add(h);
        Iterator<Position<String>> myIt = myChildren.iterator();
        for (Position<String> child : t.children(p)) {
            Position<String> next = myIt.next();
            assertEquals(child, next);
        }
    }

    /**
     * Test of iterator method, of class LinkedBinaryTree.
     */
    @Test
    public void testIterator() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        t.insertRight(p, "3");
        String salida = "";
        for (Position<String> e : t) {
            salida += e.getElement();
        }
        assertEquals(salida, "+23");
    }

    /**
     * Test of replace method, of class LinkedBinaryTree.
     */
    @Test
    public void testReplace() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        t.replace(p, "-");
        assertEquals(p.getElement(), "-");
    }

    /**
     * Test of sibling method, of class LinkedBinaryTree.
     */
    @Test
    public void testSibling() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        assertEquals(t.sibling(n2),h);
    }

    /**
     * Test of addRoot method, of class LinkedBinaryTree.
     */
    @Test
    public void testAddRoot() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        assertEquals(t.root(), p);
    }

    /**
     * Test of insertLeft method, of class LinkedBinaryTree.
     */
    @Test
    public void testInsertLeft() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        assertEquals(t.left(p), n2);
    }

    /**
     * Test of insertRight method, of class LinkedBinaryTree.
     */
    @Test
    public void testInsertRight() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> h = t.insertRight(p, "*");
        assertEquals(t.right(p), h);
    }

    /**
     * Test of remove method, of class LinkedBinaryTree.
     */
    @Test
    public void testRemove() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> q = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.remove(q);
        assertEquals(t.size(), 2);
    }

    /**
     * Test of swapElements method, of class LinkedBinaryTree.
     */
    @Test
    public void testSwapElements() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> p1 = t.insertLeft(p, "2");
        Position<String> p2 = t.insertRight(p, "3");
        t.swapElements(p1, p2);
        String salida = "";
        for (Position<String> e : t) {
            salida += e.getElement();
        }
        assertEquals(salida, "+32");
    }

    /**
     * Test of subTree method, of class LinkedBinaryTree.
     */
    @Test
    public void testSubTree() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        Position<String> n3 = t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        t.subTree(h);
        assertEquals(t.root().getElement(), "*");
        assertEquals(t.right(t.root()).getElement(),"5");

    }

}
