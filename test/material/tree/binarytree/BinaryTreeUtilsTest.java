package material.tree.binarytree;

import material.tree.Position;
import org.junit.Test;

import java.util.Iterator;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Alejandro on 3/6/16.
 */
public class BinaryTreeUtilsTest {



    /**
     * Test of level method, of class BinaryTreeUtils.
     */
    @Test
    public void testLevel() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        Position<String> n3 = t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        BinaryTreeUtils<String> u = new BinaryTreeUtils<>(t);
        int level = u.level(n5);
        assertEquals(level,2);
    }

    /**
     * Test of contains method, of class BinaryTreeUtils.
     */
    @Test
    public void testContains() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        BinaryTreeUtils<String> u = new BinaryTreeUtils<>(t);
        boolean find = u.contains("+");
        assertFalse(find);
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        Position<String> n3 = t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        find = u.contains("+");
        assertTrue(find);
        find = u.contains("3");
        assertTrue(find);
        find = u.contains("9");
        assertFalse(find);
    }

    @Test
    public void testMirror() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        BinaryTreeUtils<String> u = new BinaryTreeUtils<>(t);
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        Position<String> n3 = t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        ArrayBinaryTree<String> t2 = (ArrayBinaryTree<String>) u.mirror();
        Iterator<Position<String>> it = t2.iterator();
        String result = "";
        while(it.hasNext()){
            result = result + it.next().getElement();
        }
        assertEquals(result,"+*253");
    }

}
