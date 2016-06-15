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
public class AVLTreeTest {
    @Test
    public void testSize() {
        AVLTree <Integer> b = new AVLTree <Integer>();
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
        AVLTree <Integer> b = new AVLTree <Integer>();

        for (int cont = 0; cont < 25; cont+=2)
            b.insert(cont);

        b.insert(17);

        Position<Integer> p = b.find(17);
        assertEquals(p.getElement().intValue(), 17);
        p = b.find(2);
        assertEquals(p.getElement().intValue(), 2);
        p = b.find(13);
        assertEquals(p,null);
    }

    @Test
    public void testInsert() {
        AVLTree <Integer> b = new AVLTree <Integer>();
        b.insert(5);
        b.insert(3);
        b.insert(1);
        b.insert(7);
        b.insert(6);

        Iterator<Position <Integer>> i = b.iterator();

        String salida = "";
        while(i.hasNext()) {
            salida += i.next().getElement().toString();
        }
        assertEquals(salida, "13567");  //Sii

        b = new AVLTree <Integer>();
        b.insert(4);
        b.insert(5);
        b.insert(7);
        b.insert(2);
        b.insert(1);
        b.insert(3);
        b.insert(6);

        i = b.iterator();
        salida = "";
        while(i.hasNext()) {
            salida += i.next().getElement().toString();
        }
        assertEquals(salida, "1234567");


    }

    @Test
    public void testRemove() {
        AVLTree <Integer> b = new AVLTree <Integer>();
        Position <Integer> p2 = b.insert(5);
        b.insert(3);
        Position <Integer> p = b.insert(1);
        Position <Integer> p3 = b.insert(7);
        Position <Integer> p1 = b.insert(6);

        b.remove(p1);

        Iterator <Position <Integer>> i = b.iterator();

        String salida = "";
        while(i.hasNext()) {  //Recorro el árbol en anchura
            salida += i.next().getElement().toString();
        }

        assertEquals(salida, "1357");
    }

    @Test
    public void testFindRange() {
        AVLTree<Integer> b = new AVLTree<>();
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
        assertEquals(salida, "534");
    }

    @Test
    public void testFirst() {
        AVLTree<String> b = new AVLTree<>();
        b.insert("5");
        b.insert("3");
        b.insert("6");
        b.insert("7");
        Position<String> a = b.insert("1");
        b.insert("4");
        String salida = "1";
        assertEquals(b.first().getElement(), a.getElement());

    }

    @Test
    public void testLast() {
        AVLTree<Integer> b = new AVLTree<>();
        b.insert(5);
        b.insert(3);
        b.insert(6);
        Position<Integer> a = b.insert(7);
        b.insert(1);
        b.insert(4);

        assertEquals(b.last().getElement(), a.getElement());
    }

    @Test
    public void testPredecessors() {  //Mas pequeños
        AVLTree<Integer> b = new AVLTree<>();
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
    public void testSuccessors() {
        AVLTree<Integer> b = new AVLTree<>();
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
    public void testAVL() {
        //nos creamos un árbol binario de búsqueda
        AVLTree<Integer> arbol = new AVLTree<>();
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
        assertTrue(salida.equals("10 12 11 10 18 14 "));
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
        System.out.println (lista);
        assertTrue(salida.equals("10 5 6 5 12 11 10 "));
        assertTrue(lista.size()==7);
    }

    @Test
    public void testAVLFL() {
        //nos creamos un árbol binario de búsqueda
        AVLTree<Integer> arbol = new AVLTree<>();
        arbol.insert(5);
        arbol.insert(11);
        Position<Integer> pos1 = arbol.insert(10);
        arbol.insert(4);
        arbol.insert(18);
        arbol.insert(12);
        arbol.insert(19);
        arbol.insert(14);
        Integer last = arbol.last().getElement();
        Integer first = arbol.first().getElement();
        System.out.println ("Last: "+last);
        System.out.println ("First: "+first);

        assertTrue(last==19);
        assertTrue(first==4);


        Iterable<Position<Integer>> suc = arbol.successors(pos1);
        String cadena = "";
        for (Position<Integer> p: suc) {
            System.out.println (p.getElement());
            cadena += p.getElement()+" ";
        }
        assertTrue(cadena.equals("12 11 18 14 19 "));

        cadena = "";
        Iterable<Position<Integer>> pred = arbol.predecessors(pos1);
        for (Position<Integer> p: pred) {
            System.out.println (p.getElement());
            cadena += p.getElement()+" ";
        }
        assertTrue(cadena.equals("5 4 "));

    }
}

