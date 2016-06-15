package material.tree.binarysearchtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import material.tree.Position;

/**
 * AVLTree class - implements an AVL Tree by extending a binary search tree.
 *
 * @author A. Duarte, J. Vélez
 */
public class AVLTree<E> implements BinarySearchTree<E> {

    //Esta clase es necesaria para guardar el valor de la altura AVL en los nodos BTNodes
    private class AVLInfo<T> implements Comparable<AVLInfo<T>>, Position<T> {

        private int height;
        private T element;
        private Position<AVLInfo<T>> pos;
        private Comparator<T> comparator;

        AVLInfo(T element, Comparator<T> comparator) {
            this.element = element;
            this.pos = null;
            this.height = 1;
            this.comparator = comparator;
        }

        public void setTreePosition(Position<AVLInfo<T>> pos) {
            this.pos = pos;
        }

        public Position<AVLInfo<T>> getTreePosition() {
            return this.pos;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public T getElement() {
            return element;
        }

        @Override
        public int compareTo(AVLInfo<T> o) {
            return comparator.compare(this.element, o.element);
        }

        @Override
        public String toString() {
            return element.toString();
        }

        @Override
        public boolean equals(Object obj) {
            AVLInfo<T> info = (AVLInfo<T>) obj;
            return element.equals(info.getElement());
        }



    }

    private class AVLTreeIterator<T> implements Iterator<Position<T>> {

        private Iterator<Position<AVLInfo<T>>> it;

        public AVLTreeIterator(Iterator<Position<AVLInfo<T>>> iterator) {
            this.it = iterator;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Position<T> next() {
            Position<AVLInfo<T>> aux = it.next();
            return aux.getElement();
        }

        @Override
        public void remove() {
            it.remove();
        }
    }

    private LinkedBinarySearchTree<AVLInfo<E>> bst;
    private ReestructurableBinaryTree<AVLInfo<E>> resBT;
    private Comparator<E> comparator;

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> c) {
        if (c == null) {
            this.comparator = new DefaultComparator<E>();
        } else {
            this.comparator = c;
        }
        this.resBT = new ReestructurableBinaryTree<>();
        this.bst = new LinkedBinarySearchTree<>();
        bst.binTree = resBT;
    }

    @Override
    public Position<E> find(E value) {
        AVLInfo<E> searchedValue = new AVLInfo<>(value, comparator);
        Position<AVLInfo<E>> output = bst.find(searchedValue);
        return (output==null)?null:output.getElement();
    }

    @Override
    public Iterable<Position<E>> findAll(E value) {
        List<Position<E>> aux = new ArrayList<>();
        AVLInfo<E> searchedValue = new AVLInfo<>(value, comparator);
        for (Position<AVLInfo<E>> n : bst.findAll(searchedValue)) {
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
     * Returns whether a node has balance factor between -1 and 1.
     */
    private boolean isBalanced(Position<AVLInfo<E>> p) {
        int leftHeight = (bst.binTree.hasLeft(p))?bst.binTree.left(p).getElement().getHeight():0;
        int rightHeight = (bst.binTree.hasRight(p))?bst.binTree.right(p).getElement().getHeight():0;
        final int bf = leftHeight - rightHeight;
        return ((-1 <= bf) && (bf <= 1));
    }

    /**
     * Return a child of p with height no smaller than that of the other child.
     */
    private Position<AVLInfo<E>> tallerChild(Position<AVLInfo<E>> p) {

        int leftHeight = (bst.binTree.hasLeft(p))?bst.binTree.left(p).getElement().getHeight():0;
        int rightHeight = (bst.binTree.hasRight(p))?bst.binTree.right(p).getElement().getHeight():0;

        if (leftHeight > rightHeight) {
            return bst.binTree.left(p);
        } else if (leftHeight < rightHeight) {
            return bst.binTree.right(p);
        }

        // equal height children - break tie using parent's type
        if (bst.binTree.isRoot(p)) {
            return bst.binTree.left(p);
        }

        if (p == bst.binTree.left(bst.binTree.parent(p))) {
            return bst.binTree.left(p);
        } else {
            return bst.binTree.right(p);
        }
    }

    private void calculateHeight(Position<AVLInfo<E>> p) {
        int leftHeight = (bst.binTree.hasLeft(p))?bst.binTree.left(p).getElement().getHeight():0;
        int rightHeight = (bst.binTree.hasRight(p))?bst.binTree.right(p).getElement().getHeight():0;
        p.getElement().setHeight(1 + Math.max(leftHeight, rightHeight));
    }

    /**
     * Rebalance method called by insert and remove. Traverses the path from p
     * to the root. For each node encountered, we recompute its height and
     * perform a trinode restructuring if it's unbalanced.
     */
    private void rebalance(Position<AVLInfo<E>> zPos) {
        if (bst.binTree.isInternal(zPos)) {
            calculateHeight(zPos);
        }
        while (!bst.binTree.isRoot(zPos)) { // traverse up the tree towards the
            // root
            zPos = bst.binTree.parent(zPos);
            calculateHeight(zPos);
            if (!isBalanced(zPos)) {
                // perform a trinode restructuring at zPos's tallest grandchild
                Position<AVLInfo<E>> xPos = tallerChild(tallerChild(zPos));
                zPos = this.resBT.restructure(xPos, bst);
                calculateHeight(bst.binTree.left(zPos));
                calculateHeight(bst.binTree.right(zPos));
                calculateHeight(zPos);
            }
        }
    }


    @Override
    public Position<E> insert(E e) {
//        AVLInfo<E> aux = new AVLInfo<>(e);
        AVLInfo<E> aux = new AVLInfo<>(e, comparator);
        Position<AVLInfo<E>> internalNode = bst.insert(aux);
        aux.setTreePosition(internalNode);
        rebalance(internalNode);
        return aux;
    }

    @Override
    public E remove(Position<E> pos) throws IllegalStateException {
        AVLInfo<E> aux = (AVLInfo<E>) pos;
        AVLInfo<E> rem = bst.remove(aux.getTreePosition());
        if (rem.getTreePosition() != aux.getTreePosition()) {
            Position<AVLInfo<E>> auxNode = aux.getTreePosition();
            aux.setTreePosition(rem.getTreePosition());
            rem.setTreePosition(auxNode);
        }

        rebalance(aux.getTreePosition());
        return pos.getElement();
    }

    @Override
    public Iterator<Position<E>> iterator() {
        Iterator<Position<AVLInfo<E>>> bstIt = bst.iterator();
        AVLTreeIterator<E> it = new AVLTreeIterator<>(bstIt);
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
        AVLInfo<E> min = new AVLInfo<E>(minimum, this.comparator);
        AVLInfo<E> max = new AVLInfo<E>(maximum, this.comparator);

        Iterable<Position<AVLInfo<E>>> output = bst.findRange(min, max);

        List<Position<E>> listPos = new ArrayList<Position<E>>();
        for (Position<AVLInfo<E>> aux : output) {
            listPos.add(aux.getElement());
        }
        return listPos;
    }

    /**
     * Returns the position with the largest value in the tree.
     */
    @Override
    public Position<E> last() {
        Position<AVLInfo<E>> p = bst.last();
        return p.getElement();
    }

    /**
     * Returns the position with the smallest value in the tree.
     */
    @Override
    public Position<E> first() {
        Position<AVLInfo<E>> p = bst.first();
        return p.getElement();
    }

    /**
     * Returns an iterable collection with all values smaller than pos
     * @param pos
     * @return
     */
    public Iterable<Position<E>> successors(Position<E> pos) {
        AVLInfo<E> aux = (AVLInfo<E>) pos;
        Iterable<Position<AVLInfo<E>>> iterable = this.bst.successors(aux.getTreePosition());
        Iterator<Position<AVLInfo<E>>> it = iterable.iterator();
        ArrayList<Position<E>> res = new ArrayList<>();
        while(it.hasNext())
            res.add(it.next().getElement());
        return res;
    }

    /**
     * Returns an iterable collection with all values larger than pos
     * @param pos
     * @return
     */
    public Iterable<Position<E>> predecessors(Position<E> pos) {
        AVLInfo<E> aux = (AVLInfo<E>) pos;
        Iterable<Position<AVLInfo<E>>> iterable = this.bst.predecessors(aux.getTreePosition());
        Iterator<Position<AVLInfo<E>>> it = iterable.iterator();
        ArrayList<Position<E>> res = new ArrayList<>();
        while(it.hasNext())
            res.add(it.next().getElement());
        return res;
    }
}