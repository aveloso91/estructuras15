package material.tree.binarysearchtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import material.tree.Position;

/**
 * Realization of a red-black Tree by extending a binary search tree.
 *
 * @author A. Duarte, J. Vélez
 */
/**
 * Realization of a dictionary by means of a red-black tree.
 */
public class RBTree<E> implements BinarySearchTree<E> {

    //Esta clase es necesaria para guardar el valor de la altura AVL en los nodos BTNodes
    private class RBInfo<T> implements Comparable<RBInfo<T>>, Position<T> {

        private boolean isRed; // we add a color field to a BTNode
        private T element;
        private Position<RBInfo<T>> pos;
        private Comparator<T> comparator;

        RBInfo(T element, Comparator<T> comparator) {
            this.element = element;
            this.comparator = comparator;
        }

        public void setTreePosition(Position<RBInfo<T>> pos) {
            this.pos = pos;
        }

        public Position<RBInfo<T>> getTreePosition() {
            return this.pos;
        }

        public boolean isRed() {
            return isRed;
        }

        public void setRed() {
            isRed = true;
        }

        public void setBlack() {
            isRed = false;
        }

        public void setColor(boolean color) {
            isRed = color;
        }

        public T getElement() {
            return element;
        }

        @Override
        public boolean equals(Object obj) {
            RBInfo<T> info = (RBInfo<T>) obj;
            return element.equals(info.getElement());
        }

        @Override
        public int compareTo(RBInfo<T> o) {
            return comparator.compare(this.element, o.element);
        }
    }

    private class RBTreeIterator<T> implements Iterator<Position<T>> {

        private Iterator<Position<RBInfo<T>>> it;

        public RBTreeIterator(Iterator<Position<RBInfo<T>>> iterator) {
            this.it = iterator;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Position<T> next() {
            Position<RBInfo<T>> aux = it.next();
            return aux.getElement();
        }

        @Override
        public void remove() {
            it.remove();
        }
    }

    private LinkedBinarySearchTree<RBInfo<E>> bst;
    private ReestructurableBinaryTree<RBInfo<E>> resBT;
    private Comparator<E> comparator;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> c) {
        if (c == null) {
            this.comparator = new DefaultComparator<E>();
        } else {
            this.comparator = c;
        }
        this.bst = new LinkedBinarySearchTree<RBInfo<E>>();
        this.resBT = new ReestructurableBinaryTree<RBInfo<E>>();
        bst.binTree = resBT;
    }

    @Override
    public Position<E> find(E value) {
        RBInfo<E> searchedValue = new RBInfo<>(value, comparator);
        Position<RBInfo<E>> output = bst.find(searchedValue);

        if (output == null) {
            return null;
        }
        return output.getElement();
    }

    @Override
    public Iterable<Position<E>> findAll(E value) {
        RBInfo<E> searchedValue = new RBInfo<>(value, comparator);
        List<Position<E>> aux = new ArrayList<>();
        for (Position<RBInfo<E>> n : bst.findAll(searchedValue)) {
            aux.add(n.getElement());
        }
        return aux;
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    @Override
    public int size() {
        return bst.size();
    }

    /**
     * Inserts an element into the RBTree and returns the newly created postion.
     */
    public Position<E> insert(E e) {
        RBInfo<E> aux = new RBInfo<>(e, comparator);

        Position<RBInfo<E>> posZ = bst.insert(aux);
        aux.setTreePosition(posZ);
        aux.setRed();

        if (this.bst.binTree.isRoot(posZ)) {
            aux.setBlack();
        } else {
            remedyDoubleRed(aux); // fix a double-red color violation
        }
        return aux;
    }

    /**
     * Remedies a double red violation at a given node caused by insertion.
     */
    protected void remedyDoubleRed(RBInfo<E> nodeZ) {
        Position<RBInfo<E>> posV = this.bst.binTree.parent(nodeZ.getTreePosition());
        RBInfo<E> nodeV = posV.getElement();

        if (!nodeV.isRed()) {
            return;
        }
        // we have a double red: posZ and posV
        Position<RBInfo<E>> grandParent = this.bst.binTree.parent(posV);
        boolean hasSibling = this.bst.binTree.hasLeft(grandParent) && this.bst.binTree.hasRight(grandParent);
        boolean blackUncle = true;
        if (hasSibling) {
            Position<RBInfo<E>> uncleZ = this.bst.binTree.sibling(posV);
            blackUncle = !uncleZ.getElement().isRed;
        }
        if (blackUncle) { // Case 1: trinode restructuring
            posV = this.resBT.restructure(nodeZ.getTreePosition(), this.bst);
            posV.getElement().setBlack();
            this.bst.binTree.left(posV).getElement().setRed();
            this.bst.binTree.right(posV).getElement().setRed();
        } else { // Case 2: recoloring
            nodeV.setBlack();
            if (hasSibling) {
                this.bst.binTree.sibling(posV).getElement().setBlack();
            }
            Position<RBInfo<E>> posU = this.bst.binTree.parent(posV);
            if (this.bst.binTree.isRoot(posU)) {
                return;
            }
            RBInfo<E> nodeU = posU.getElement();
            nodeU.setRed();
            remedyDoubleRed(nodeU);
        }
    }

    @Override
    public E remove(Position<E> posV) throws IllegalStateException {
        RBInfo<E> nodeV = (RBInfo<E>) posV;
        E toReturn = posV.getElement(); // entry to be returned

        Position<RBInfo<E>> pos = nodeV.getTreePosition();
        boolean beforeIsRed = nodeV.isRed();
        RBInfo<E> rem = this.bst.remove(pos);
        
        /*
        ¿¿¿rem.getElement().equals(nodeV.getElement())????
        Para comprobar si no ha habido reemplazo
        */
        if (rem.getTreePosition() != nodeV.getTreePosition()) {
            pos.getElement().setColor(beforeIsRed);
            Position<RBInfo<E>> auxNode = nodeV.getTreePosition();
            nodeV.setTreePosition(rem.getTreePosition());
            rem.setTreePosition(auxNode);
        }

        if (!rem.isRed()) {
            remedyDoubleBlack(rem.getTreePosition());
        }
        return toReturn;
    }

    /**
     * Remedies a double black violation at a given node caused by removal.
     */
    protected void remedyDoubleBlack(Position<RBInfo<E>> posR) {
        boolean oldColor;
        Position<RBInfo<E>> posZ;
        RBInfo<E> nodeZ;
        Position<RBInfo<E>> posX = this.bst.binTree.parent(posR);
        RBInfo<E> nodeX = posX.getElement();
        Position<RBInfo<E>> posY = (this.bst.binTree.hasLeft(posX))?this.bst.binTree.left(posX):this.bst.binTree.right(posX);
        RBInfo<E> nodeY = posY.getElement();
        final boolean blackNodeY = !nodeY.isRed();

        if (blackNodeY) {
            posZ = hasRedChild(posY); //posZ != null measn that it at least one red childpren
            if (posZ != null) { // Case 1: trinode restructuring
                oldColor = nodeX.isRed();
                posZ = this.resBT.restructure(posZ, this.bst); //After restrusturing posZ gets the value of midKey
                posZ.getElement().setColor(oldColor);
                if (this.bst.binTree.hasLeft(posX) && this.bst.binTree.hasRight(posX)) {
                    posR.getElement().setBlack();
                }
                this.bst.binTree.left(posZ).getElement().setBlack();
                this.bst.binTree.right(posZ).getElement().setBlack();
                return;
            }
            nodeY.setRed();
            if (!nodeX.isRed()) { // Case 2: recoloring
                if (!this.bst.binTree.isRoot(posX)) {
                    remedyDoubleBlack(posX);
                }
                return;
            }
            nodeX.setBlack();
            return;
        } // Case 3: adjustment
        if (this.bst.binTree.hasRight(posY)) {
            posZ = this.bst.binTree.right(posY);
        } else {
            posZ = this.bst.binTree.left(posY);
        }
//        if (this.bst.binTree.hasRight(posX) && (this.bst.binTree.right(posX) == posY)) {
//            posZ = this.bst.binTree.right(posY);
//        } else {
//            posZ = this.bst.binTree.left(posY);
//        }
        this.resBT.restructure(posZ, this.bst);
        nodeY.setBlack();
        nodeX.setRed();
        remedyDoubleBlack(posR);
    }

    /**
     * Returns a red child of a node.
     */
    protected Position<RBInfo<E>> hasRedChild(Position<RBInfo<E>> pos) {
        Position<RBInfo<E>> child;
        if (this.bst.binTree.hasLeft(pos)) {
            child = this.bst.binTree.left(pos);
            final boolean redLeftChild = child.getElement() != null && child.getElement().isRed();
            if (redLeftChild) {
                return child;
            }
        }

        if (this.bst.binTree.hasRight(pos)) {
            child = this.bst.binTree.right(pos);
            final boolean redRightChild = child.getElement() != null && child.getElement().isRed();
            if (redRightChild) {
                return child;
            }
        }
        return null;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        Iterator<Position<RBInfo<E>>> bstIt = bst.iterator();
        RBTreeIterator<E> it = new RBTreeIterator<>(bstIt);
        return it;
    }

    /**
     * Find range in binary search trees.
     * Llamo al findRange de LinkedBinarySearch
     * Recorro los valores entre el maximo y el minimo
     * Los voy añadiendo
     * @param minimum
     * @param maximum
     */
    @Override
    public Iterable<Position<E>> findRange(E minimum, E maximum) {
        RBInfo<E> min = new RBInfo<E>(minimum, this.comparator);
        RBInfo<E> max = new RBInfo<E>(maximum, this.comparator);

        Iterable<Position<RBInfo<E>>> output = bst.findRange(min, max);

        List<Position<E>> listPos = new ArrayList<Position<E>>();
        for (Position<RBInfo<E>> aux : output) {
            listPos.add(aux.getElement());
        }

        return listPos;
    }

    /**
     * Returns the position with the smallest value in the tree.
     */
    @Override
    public Position<E> first() {
        Position<RBInfo<E>> p = bst.first();
        return p.getElement();
    }

    /**
     * Returns the position with the largest value in the tree.
     */
    @Override
    public Position<E> last() {
        Position<RBInfo<E>> p = bst.last();
        return p.getElement();
    }

    /**
     * Returns an iterable collection with all values smaller than pos
     * @param pos
     * @return
     */
    public Iterable<Position<E>> successors(Position<E> pos) {
        RBInfo<E> aux = (RBInfo<E>) pos;
        Iterable<Position<RBInfo<E>>> iterable = this.bst.successors(aux.getTreePosition());
        Iterator<Position<RBInfo<E>>> it = iterable.iterator();
        ArrayList<Position<E>> res = new ArrayList<>();
        while(it.hasNext())
            res.add(it.next().getElement()); //De un Position<AVLInfo<E>> le hacemos el getElement y tenemos el AVLInfo<E> que es un compatible con Position de <E>
        return res;
    }

    /**
     * Returns an iterable collection with all values larger than pos
     * @param pos
     * @return
     */
    public Iterable<Position<E>> predecessors(Position<E> pos) {
        RBInfo<E> aux = (RBInfo<E>) pos;
        Iterable<Position<RBInfo<E>>> iterable = this.bst.predecessors(aux.getTreePosition());
        Iterator<Position<RBInfo<E>>> it = iterable.iterator();
        ArrayList<Position<E>> res = new ArrayList<>();
        while(it.hasNext())
            res.add(it.next().getElement()); //De un Position<AVLInfo<E>> le hacemos el getElement y tenemos el AVLInfo<E> que es un compatible con Position de <E>
        return res;
    }

}