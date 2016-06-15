package material.tree.binarysearchtree;

import material.tree.Position;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Alejandro on 14/6/16.
 */
public class RBTreeTest {
    @Test
    public void testSize() {
        RBTree <Integer> b = new RBTree <Integer>();
        assertEquals(b.size(), 0);
        b.insert(5);
        assertEquals(b.size(), 1);
        b.insert(10);
        assertEquals(b.size(), 2);

        for (int cont = 0; cont < 25; cont++)
            b.insert(cont);

        assertEquals(b.size(), 27);
    }

    @Test
    public void testFind() {
        RBTree <Integer> b = new RBTree <Integer>();

        for (int cont = 0; cont < 25; cont+=2)
            b.insert(cont);

        b.insert(17);

        Position<Integer> p = b.find(17);
        assertEquals(p.getElement().intValue(),17);
        p = b.find(2);
        assertEquals(p.getElement().intValue(),2);
        p = b.find(13);
        assertEquals(p,null);
    }

    @Test
    public void testInsert() {
        RBTree <Integer> b = new RBTree <Integer>();
        b.insert(5);
        b.insert(3);
        b.insert(1);
        b.insert(7);
        b.insert(6);

        Iterator<Position <Integer>> i = b.iterator();

        String salida = "";
        while(i.hasNext()) {  //Recorro el árbol en anchura
            salida += i.next().getElement().toString();
        }
        assertEquals(salida, "13567");


        b = new RBTree <Integer>();
        b.insert(4);
        b.insert(7);
        b.insert(12);
        b.insert(15);
        b.insert(3);
        b.insert(5);
        b.insert(14);
        b.insert(18);
        b.insert(16);
        b.insert(17);

        i = b.iterator();

        salida = "";

        while(i.hasNext()) {  //Recorro el árbol en anchura
            salida += i.next().getElement().toString();
        }
        assertEquals(salida, "3457121415161718");

    }

    @Test
    public void testRemove() {
        RBTree <Integer> b = new RBTree <Integer>();
        b.insert(5);
        b.insert(3);
        Position <Integer> p = b.insert(1);
        b.insert(7);
        b.insert(6);

        b.remove(p);

        Iterator <Position <Integer>> i = b.iterator();

        String salida = "";
        while(i.hasNext()) {  //Recorro el árbol en anchura
            salida += i.next().getElement().toString();
        }
        assertEquals(salida, "3567");
    }

    @Test
    public void testRBTreeFindRange() {
        RBTree <Integer> b = new RBTree <Integer>();
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
        RBTree<String> b = new RBTree<>();
        b.insert("5");
        b.insert("3");
        b.insert("6");
        b.insert("7");
        Position<String> a = b.insert("1");
        b.insert("4");

        assertEquals(b.first().getElement(), a);
    }

    @Test
    public void testLast() {
        RBTree<Integer> b = new RBTree<>();
        b.insert(5);
        b.insert(3);
        b.insert(6);
        Position<Integer> a = b.insert(7);
        b.insert(1);
        b.insert(4);

        assertEquals(b.last().getElement(), a);
    }

    @Test
    public void testPredecessors() {  //Mas pequeños
        RBTree<String> b = new RBTree<>();
        Position<String> p = b.insert("5");
        b.insert("3");
        b.insert("6");
        b.insert("7");
        b.insert("1");
        b.insert("4");
        String salida = "";
        Iterable<Position<String>> i = b.predecessors(p);
        for (Position<String> e : i) {
            salida += e.getElement();
        }
        assertEquals(salida, "314");
    }

    @Test
    public void testSuccessors() {
        RBTree<Integer> b = new RBTree<>();
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
    public void testARN() {
        //nos creamos un árbol binario de búsqueda
        RBTree<Integer> arbol = new RBTree<>();
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
        System.out.println ("ARN: "+salida);
        assertTrue(salida.equals("11 10 10 18 12 14 "));
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
        System.out.println ("ARN: "+salida);
        assertTrue(salida.equals("11 10 5 6 5 10 12 "));
        assertTrue(lista.size()==7);
    }
}

