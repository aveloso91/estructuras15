package material.tree.binarytree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import material.tree.*;
import material.tree.iterator.BFSIteratorFactory;
import material.tree.iterator.TreeIteratorFactory;

/**
 * *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro
 * @param <E> The type of the elements in the tree
 * @see BinaryTree
 */
public class ArrayBinaryTree<E> implements BinaryTree<E> {

    private class BTPos<T> implements Position<T> {

        private T element;
        private int index;
        private ArrayBinaryTree<T> myTree;

        public BTPos(ArrayBinaryTree<T> myTree, T element, int index) {
            this.myTree = myTree;
            this.element = element;
            this.index = index;
        }

        @Override
        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public ArrayBinaryTree<T> getMyTree() {
            return myTree;
        }

        public void setMyTree(ArrayBinaryTree<T> myTree) {
            this.myTree = myTree;
        }

        @Override
        public String toString() {
            return "BTPos{" +
                    "element=" + element +
                    ", index=" + index +
                    ", myTree=" + myTree +
                    '}';
        }
    }

//    private class ArrayBinaryTreeIterator<T> implements Iterator<Position<T>> {
//
//        @Override
//        public boolean hasNext() {
//            throw new UnsupportedOperationException("Not yet implemented!!");
//        }
//
//        @Override
//        public Position<T> next() {
//            throw new UnsupportedOperationException("Not yet implemented!!");
//        }
//    }

    private BTPos<E>[] tree;
    private int size;
    private TreeIteratorFactory<E> iteratorFactory;

    /**
     * Constructor of the class.
     */
    public ArrayBinaryTree(int capacity) {
        this.tree = (BTPos[]) new BTPos[capacity];
        this.size = 0;
        this.iteratorFactory = new BFSIteratorFactory<>();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size ==0;
    }

    @Override
    public boolean isInternal(Position<E> p) throws IllegalStateException {
        checkPosition(p);
        return (hasLeft(p) || hasRight(p));
    }

    @Override
    public boolean isLeaf(Position<E> p) throws IllegalStateException {
        return !isInternal(p);
    }

    @Override
    public boolean isRoot(Position<E> p) throws IllegalStateException {
        checkPosition(p);
        return (p == root());
    }

    @Override
    public boolean hasLeft(Position<E> p) throws IllegalStateException {
        BTPos<E> node = checkPosition(p);
        return (tree[node.getIndex()*2] != null);
    }

    @Override
    public boolean hasRight(Position<E> p) throws IllegalStateException {
        BTPos<E> node = checkPosition(p);
        return (tree[node.getIndex()*2+1] != null);
    }

    @Override
    public Position<E> root() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("The tree is empty");
        }
        return tree[1];
    }

    @Override
    public Position<E> left(Position<E> p) throws IllegalStateException, IndexOutOfBoundsException {
        BTPos<E> node = checkPosition(p);
        int left = node.getIndex()*2;
        if (tree[left] == null) {
            throw new IndexOutOfBoundsException("The node hasn't got left child");
        }
        return tree[left];
    }

    @Override
    public Position<E> right(Position<E> p) throws IllegalStateException, IndexOutOfBoundsException {
        BTPos<E> node = checkPosition(p);
        int right = node.getIndex()*2+1;
        if (tree[right] == null) {
            throw new IndexOutOfBoundsException("The node hasn't got right child");
        }
        return tree[right];    }

    @Override
    public Position<E> parent(Position<E> p) throws IllegalStateException, IndexOutOfBoundsException {
        BTPos<E> node = checkPosition(p);
        int indexNode = node.getIndex();
        int indexParent = 0;
        if(indexNode%2 != 0){
            indexParent = (indexNode-1)/2;
        }
        if(indexNode%2 == 0){
            indexParent = indexNode/2;
        }
        if (tree[indexParent] == null){
            throw new IndexOutOfBoundsException("The node hasn't got parent");
        }
        return tree[indexParent];
    }

    @Override
    public Iterable<Position<E>> children(Position<E> p) throws IllegalStateException {
        List<Position<E>> children = new ArrayList<Position<E>>();
        if (hasLeft(p)) {
            children.add(left(p));
        }
        if (hasRight(p)) {
            children.add(right(p));
        }
        return children;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return this.iteratorFactory.createIterator(this);
    }

    @Override
    public E replace(Position<E> p, E e) throws IllegalStateException {
        BTPos<E> node = checkPosition(p);
        E temp = p.getElement();
        node.setElement(e);
        return temp;    }

    // Additional Methods
    @Override
    public Position<E> sibling(Position<E> v) throws IllegalStateException, IndexOutOfBoundsException {
        Position<E> parent = parent(v);
        BTPos<E> node = checkPosition(v);
        if(parent != null){
            List<E> children = (List<E>) children(parent);
            if(children.size() < 2)
                throw new IndexOutOfBoundsException("The node hasn't got sibling");
            else{
                if(node == left(parent))
                    return right(parent);
                else
                    return left(parent);
            }

        }
        throw new IndexOutOfBoundsException("The node hasn't got sibling");
    }

    @Override
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) {
            throw new IllegalStateException("Tree already has a root");
        }
        BTPos<E> node = new BTPos<E>(this, e, 1);
        this.tree[1] = node;
        size++;
        return node;
    }

    @Override
    public Position<E> insertLeft(Position<E> p, E e) throws IllegalStateException {
        BTPos<E> node = checkPosition(p);
        int nodeIndex = node.getIndex();
        if (!hasLeft(node)) {
            int leftIndex = nodeIndex * 2;
            this.extendTree(leftIndex);
            BTPos<E> newNode = new BTPos<>(this, e, leftIndex);
            tree[leftIndex] = newNode;
            tree[leftIndex * 2] = null;
            tree[leftIndex * 2 + 1] = null;
            size++;
            return newNode;
        }
        throw new IllegalStateException("The node already has a left child");
    }

    @Override
    public Position<E> insertRight(Position<E> p, E e) throws IllegalStateException {
        BTPos<E> node = checkPosition(p);
        int nodeIndex = node.getIndex();
        if (!hasRight(node)) {
            int rightIndex = nodeIndex * 2 + 1;
            this.extendTree(rightIndex);
            BTPos<E> newNode = new BTPos<>(this, e, rightIndex);
            tree[rightIndex] = newNode;
            tree[rightIndex * 2] = null;
            tree[rightIndex * 2 + 1] = null;
            size++;
            return newNode;
        }
        throw new IllegalStateException("The node already has a right child");
    }

    @Override
    public E remove(Position<E> p) throws IllegalStateException {
        BTPos<E> node = checkPosition(p);
        this.size -= calculateSize(node);
        Iterator<Position<E>> it = this.iteratorFactory.createIterator(this, node);

        while (it.hasNext()) { //poner los myTree de cada nodo a null para que no pertenezcan al arbol
            BTPos<E> auxNode = (BTPos<E>) it.next();
            auxNode.setMyTree(null);
        }
        tree[node.getIndex()] = null;
        return p.getElement();
    }

//    private int calculateSize(BTPos<E> n) {
//        BTPos<E> i = (BTPos<E>) tree[n.getIndex()*2];
//        BTPos<E> d = (BTPos<E>) tree[n.getIndex()*2+1];
//        if (n != null) {
//            return 1 + calculateSize(tree[i.getIndex()]) + calculateSize(tree[d.getIndex()]);
//        } else {
//            return 0;
//        }
//    }

    private int calculateSize(BTPos<E> n) {
        BTPos<E> i = (BTPos<E>) tree[n.getIndex()*2];
        BTPos<E> d = (BTPos<E>) tree[n.getIndex()*2+1];
        int sumaI = 0;
        int sumaD = 0;
        if(i != null){
            sumaI = calculateSize(tree[i.getIndex()]);
        }
        if(d != null){
            sumaD = calculateSize(tree[d.getIndex()]);
        }
        if (n != null) {
            return 1 + sumaI + sumaD;
        } else {
            return 0;
        }
    }


    @Override
    public void swapElements(Position<E> p1, Position<E> p2) throws IllegalStateException {
        BTPos<E> n1 = checkPosition(p1);
        BTPos<E> n2 = checkPosition(p2);
        E aux = n1.getElement();
        n1.setElement(n2.getElement());
        n2.setElement(aux);
    }

    public void extendTree (int posToCheck){
        if (tree.length < (posToCheck*2+1)) { //si el elemento excede el tamaño del array lo ampliamos
            BTPos<E>[] aux = (BTPos[]) new BTPos[tree.length*2];
            aux = this.tree;
            this.tree = (BTPos[]) new BTPos[tree.length*2];
            for (int i=0 ; i<aux.length ; i++){
                tree[i] = aux[i];
            }
        }
    }

    public void subTree(Position<E> v) {
        BTPos<E> node = checkPosition(v);
        ArrayBinaryTree<E> treeCopy = new ArrayBinaryTree<>(10);
        int index = node.getIndex();

        treeCopy.addRoot(node.getElement());
        subTreeAux(treeCopy, index, 1);

        for (int i = 0; i < treeCopy.tree.length; i++) {
            this.tree[i] = treeCopy.tree[i];
        }
        for(BTPos<E> n: this.tree){
            if(n!=null)
                n.setMyTree(this);
        }

        this.size = calculateSize(treeCopy.tree[1]);
    }

    private ArrayBinaryTree<E> subTreeAux(ArrayBinaryTree<E> t, int index1, int index2){
        if(hasLeft(this.tree[index1])){
            t.insertLeft(t.tree[index2], this.tree[index1 * 2].getElement());
            subTreeAux(t, index1*2, index2*2);
        }
        if(hasRight(this.tree[index1])){
            t.insertRight(t.tree[index2], this.tree[index1 * 2 + 1].getElement());
            subTreeAux(t, index1*2+1, index2*2+1);
        }

        return t;
    }

    /**
     * Determines whether the given position is a valid node.
     *
     * @param v the position to be checked
     * @return the position casted to BTPos
     */
    private BTPos<E> checkPosition(Position<E> v) throws IllegalStateException {
        if (v == null || !(v instanceof BTPos)) {
            throw new IllegalStateException("The position is invalid");
        }
        BTPos<E> btpos = (BTPos<E>) v;
        if (btpos.getMyTree() != this) {
            throw new IllegalStateException("The position does not belong to this tree");
        }
        return (BTPos<E>) v;    }

//    @Override
//    public String toString() {
//        return "ArrayBinaryTree{" +
//                "tree=" + Arrays.toString(tree) +
//                ", size=" + size +
//                '}';
//    }
}
