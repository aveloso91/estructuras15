package material.tree.binarysearchtree;

import material.tree.Position;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Alejandro on 14/6/16.
 */
public class LinkedBinarySearchTreeTest {
    @Test
    public void testSize() {
        LinkedBinarySearchTree<Integer> b = new LinkedBinarySearchTree<Integer>();
        assertEquals(b.size(), 0);
        b.insert(5);
        assertEquals(b.size(), 1);
        b.insert(10);
        assertEquals(b.size(), 2);

        for (int cont = 0; cont < 25; cont++) {
            b.insert(cont);
        }

        assertEquals(b.size(), 27);
    }

    @Test
    public void testFind() {
        BinarySearchTree<Integer> b = new LinkedBinarySearchTree<Integer>();

        for (int cont = 0; cont < 25; cont += 2) {
            b.insert(cont);
        }

        b.insert(17);

        Position<Integer> p = b.find(17);
        assertEquals(p.getElement().intValue(), 17);
        p = b.find(2);
        assertEquals(p.getElement().intValue(), 2);
        p = b.find(13);
        assertEquals(p, null);
    }

    @Test
    public void testInsert() {
        LinkedBinarySearchTree<Integer> b = new LinkedBinarySearchTree<Integer>();
        b.insert(5);
        b.insert(3);
        b.insert(6);
        b.insert(7);
        b.insert(1);
        b.insert(2);

        Iterator<Position<Integer>> i = b.iterator();

        String salida = "";
        while (i.hasNext()) {
            salida += i.next().getElement().toString();
        }
        assertEquals(salida, "123567");
    }

    @Test
    public void testRemove() {
        LinkedBinarySearchTree<Integer> b = new LinkedBinarySearchTree<Integer>();
        b.insert(5);
        Position<Integer> p = b.insert(3);
        b.insert(7);
        b.insert(1);
        b.insert(6);
        b.remove(p);

        Iterator<Position<Integer>> i = b.iterator();

        String salida = "";
        while (i.hasNext()) {
            salida += i.next().getElement().toString();
        }
        assertEquals(salida, "1567");

    }

    @Test
    public void testFindRange() {
        LinkedBinarySearchTree<Integer> b = new LinkedBinarySearchTree<>();
        b.insert(5);
        b.insert(3);
        b.insert(6);
        b.insert(7);
        b.insert(1);
        b.insert(4);
        String salida = "";
        Iterable<Position<Integer>> i = b.findRange(2, 5);
        for (Position<Integer> e : i) {
            salida += e.getElement().toString();
        }
        //recorrido en anchura 536147
        assertEquals(salida, "534");
    }

    @Test
    public void testFirst() {
        LinkedBinarySearchTree<Integer> b = new LinkedBinarySearchTree<>();
        b.insert(5);
        b.insert(3);
        b.insert(6);
        b.insert(7);
        b.insert(1);
        b.insert(4);
        String salida = "1";
        assertEquals(b.first().getElement().toString(), salida);

    }

    @Test
    public void testLast() {
        LinkedBinarySearchTree<Integer> b = new LinkedBinarySearchTree<>();
        b.insert(5);
        b.insert(3);
        b.insert(6);
        b.insert(7);
        b.insert(1);
        b.insert(4);
        String salida = "7";
        assertEquals(b.last().getElement().toString(), salida);
    }

    @Test
    public void testSuccessors() {
        LinkedBinarySearchTree<Integer> b = new LinkedBinarySearchTree<>();
        Position<Integer> p = b.insert(5);
        b.insert(3);
        b.insert(6);
        b.insert(7);
        b.insert(1);
        b.insert(4);
        String salida = "";
        Iterable<Position<Integer>> i = b.successors(p);
        for (Position<Integer> e : i) {
            salida += e.getElement().toString();
        }
        assertEquals(salida, "67");
    }

    @Test
    public void testPredecessors() {
        LinkedBinarySearchTree<Integer> b = new LinkedBinarySearchTree<>();
        Position<Integer> p = b.insert(5);
        b.insert(3);
        b.insert(6);
        b.insert(7);
        b.insert(1);
        b.insert(4);
        String salida = "";
        Iterable<Position<Integer>> i = b.predecessors(p);
        for (Position<Integer> e : i) {
            salida += e.getElement().toString();
        }
        assertEquals(salida, "314");
    }

    @Test
    public void testABB() {
        //nos creamos un árbol binario de búsqueda
        LinkedBinarySearchTree<Integer> arbol = new LinkedBinarySearchTree<>();
        arbol.insert(10);
        arbol.insert(5);
        arbol.insert(11);
        arbol.insert(10);
        arbol.insert(4);
        arbol.insert(18);
        arbol.insert(12);
        arbol.insert(19);
        arbol.insert(14);

        Iterable<Position<Integer>> itera = arbol.findRange(10, 18);
        ArrayList<Position<Integer>> lista = new ArrayList<>();
        Iterator<Position<Integer>> it = itera.iterator();
        String salida = "";
        while(it.hasNext()){
            Position<Integer> pos = it.next();
            salida+=pos.getElement()+" ";
            lista.add(pos);
        }
        System.out.println (salida);
        assertTrue(salida.equals("10 11 10 18 12 14 "));
        assertTrue(lista.size()==6);

        arbol.insert(6);
        arbol.insert(5);
        itera = arbol.findRange(5, 12);
        lista = new ArrayList<>();
        it = itera.iterator();
        salida = "";
        while(it.hasNext()){
            Position<Integer> pos = it.next();
            salida+=pos.getElement()+" ";
            lista.add(pos);
        }

        assertTrue(salida.equals("10 5 6 5 11 10 12 "));
        assertTrue(lista.size()==7);
    }
}
