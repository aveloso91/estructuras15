package material.tree;

import material.tree.iterator.BFSIteratorFactory;
import java.util.*;
import material.tree.iterator.TreeIteratorFactory;

/**
 * A linked class for a tree where nodes have an arbitrary number of children.
 *
 * @author Raul Cabido, Abraham Duarte, Jose Velez, Jesús Sánchez-Oro
 * @param <E> the elements stored in the tree
 */
public class LCRSTree<E> implements Tree<E> {

    /**
     * Inner class which represents a node of the tree
     *
     * @param <T> the type of the elements stored in a node
     */
    public class TreeNode<T>  implements Position<T> {

        private T element; //The element stored in the position
        private TreeNode<T> parent; //The parent of the node
        private TreeNode<T> child; //Reference of the first child of the node
        private TreeNode<T> sibling; //Reference of the first sibling of the node
        private LCRSTree<T> myTree; //A reference to the tree where the node belongs

        /**
         * Constructor of the class
         *
         * @param element the element to store in the node
         * @param parent the parent of the node
         * @param child the child of the node
         * @param sibling the sibling of the node
         * @param myTree the tree where the node is stored
         */
        public TreeNode(T element, TreeNode<T> parent, TreeNode<T> child, TreeNode<T> sibling, LCRSTree<T> myTree) {
            this.element = element;
            this.parent = parent;
            this.child = child;
            this.sibling = sibling;
            this.myTree = myTree;
        }

        /**
         * Accesses to the element stored in this position
         *
         * @return the element of the node
         */
        @Override
        public T getElement() {
            return element;
        }

        /**
         * Set the element stored in this position
         *
         * @param element
         */
        public void setElement(T element) {
            this.element = element;
        }

        /**
         * Accesses to the child of this node
         *
         * @return the child of the node
         */
        public TreeNode<T> getChild() {
            return child;
        }

        /**
         * Set the child of the node
         *
         * @param child
         */
        public void setChild(TreeNode<T> child) {
            this.child = child;
        }

        /**
         * Accesses to the parent of this node
         *
         * @return the parent of this node
         */
        public TreeNode<T> getParent() {
            return parent;
        }

        /**
         * Set the parent of this node
         *
         * @param parent
         */
        public void setParent(TreeNode<T> parent) {
            this.parent = parent;
        }

        /**
         * Accesses to the sibling of this node
         *
         * @return the sibling of this node
         */
        public TreeNode<T> getSibling() {
            return sibling;
        }

        /**
         * Set the sibling of this node
         *
         * @param sibling
         */
        public void setSibling(TreeNode<T> sibling) {
            this.sibling = sibling;
        }

        /**
         * Accesses to the tree of the node
         *
         * @return the tree of the node
         */
        public LCRSTree<T> getMyTree() {
            return myTree;
        }

        /**
         * Set the tree of the node
         *
         * @param myTree
         */
        public void setMyTree(LCRSTree<T> myTree) {
            this.myTree = myTree;
        }
    }

    private TreeNode<E> root; //The root of the tree
    private int size; //The size of the tree
    private TreeIteratorFactory<E> iteratorFactory; // The factory of iterators

    public LCRSTree() {
        root = null;
        size = 0;
        iteratorFactory = new BFSIteratorFactory<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean isInternal(Position<E> v) throws IllegalStateException {
        return (!isLeaf(v));
    }

    @Override
    public boolean isLeaf(Position<E> p) throws IllegalStateException {
        TreeNode<E> node = checkPosition(p);
        return (node.getChild() == null);
    }

    @Override
    public boolean isRoot(Position<E> p) throws IllegalStateException {
        TreeNode<E> node = checkPosition(p);
        return (node == this.root());
    }

    @Override
    public Position<E> root() throws IllegalStateException {
        if (root == null) {
            throw new IllegalStateException("The tree is empty");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> p) throws IndexOutOfBoundsException,
            IllegalStateException {
        TreeNode<E> node = checkPosition(p);
        Position<E> parentPos = (Position<E>) node.getParent();
        if (parentPos == null) {
            throw new IndexOutOfBoundsException("The node has not parent");
        }
        return parentPos;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> p) {
        TreeNode<E> node = checkPosition(p);
        List<Position<E>> children = new ArrayList<Position<E>>();
        TreeNode<E> childNode = node.getChild();
        while(childNode != null){
            children.add(childNode);
            childNode = childNode.getSibling();
        }
        return children;
    }

    /**
     * Modifies the element stored in a given position
     * @param p the position to be modified
     * @param e the new element to be stored
     * @return the previous element stored in the position
     * @throws IllegalStateException if the position is not valid
     */
    public E replace(Position<E> p, E e) throws IllegalStateException {
        TreeNode<E> node = checkPosition(p);
        E temp = e;
        node.setElement(e);
        return temp;
    }

    @Override
    public Position<E> addRoot(E e) throws IllegalStateException {
        if(!isEmpty()){
            throw new IllegalStateException("Tree already has a root");
        }
        size = 1;
        root = new TreeNode<E>(e,null,null,null,this);
        return root;
    }

    /**
     * Swap the elements stored in two given positions
     * @param p1 the first node to swap
     * @param p2 the second node to swap
     * @throws IllegalStateException if the position of any node is not valid
     */
    public void swapElements(Position<E> p1, Position<E> p2)
            throws IllegalStateException {
        TreeNode<E> node1 = checkPosition(p1);
        TreeNode<E> node2 = checkPosition(p2);
        E temp = p2.getElement();
        node2.setElement(p1.getElement());
        node1.setElement(temp);
    }

    /**
     * Validates the given position, casting it to TreeNode if valid
     * @param p the position to be converted
     * @return the position casted to TreeNode
     * @throws IllegalStateException if the position is not valid
     */
    private TreeNode<E> checkPosition(Position<E> p)
            throws IllegalStateException {
        if (p == null || !(p instanceof TreeNode)) {
            throw new IllegalStateException("The position is invalid");
        }
        TreeNode<E> aux = (TreeNode<E>) p;

        if (aux.getMyTree() != this) {
            throw new IllegalStateException("The node is not from this tree");
        }
        return aux;
    }

    /**
     * Adds a new node whose parent is pointed by a given position.
     *
     * @param element the element to be added
     * @param p the position of the parent
     * @return the position of the new node created
     * @throws IllegalStateException if the position is not valid
     */
    public Position<E> add(E element, Position<E> p) {
        TreeNode<E> nParent = checkPosition(p);
        TreeNode<E> nNew = new TreeNode<E>(element,nParent,null,null,nParent.getMyTree());
        if(nParent.getChild() == null){
            nParent.setChild(nNew);
        }else{
            List<Position<E>> lc = (List<Position<E>>) children(p);
            TreeNode<E> nLastS = checkPosition(lc.get(lc.size()-1));
            nLastS.setSibling(nNew);
        }
        size++;
        return nNew;
    }

    /**
     * Removes a node and its corresponding subtree rooted at node.
     *
     * @param p the position of the node to be removed.
     * @throws IllegalStateException if the position is not valid
     */
    public void remove(Position<E> p) throws IllegalStateException {
        TreeNode<E> node = checkPosition(p);
        if(node.getParent() == null){
            this.root = null;
            this.size = 0;
        }else{
            Iterator<Position<E>> it = this.iteratorFactory.createIterator(this,p);
            int counter = 0;
            while(it.hasNext()){
                it.next();
                counter++;
            }
            TreeNode<E> nodeParent = node.getParent();
            if(nodeParent.getChild() == node){
                if(node.getSibling() != null){
                    nodeParent.setChild(node.getSibling());
                }else{
                    nodeParent.setChild(null);
                }
            }else{
                List<Position<E>> lc = (List<Position<E>>) children(nodeParent);
                for(Position<E> c : lc){
                    TreeNode<E> cNode = checkPosition(c);
                    if(cNode.getSibling() == node){
                        cNode.setSibling(node.getSibling());
                        break;
                    }
                }
            }
            node.setSibling(null);
            node.setParent(null);
            node.setChild(null);
            node.setElement(null);
            node.setMyTree(null);
            size = size - counter;
        }
    }

    public void setIterator(TreeIteratorFactory<E> iteratorFactory) {
        this.iteratorFactory = iteratorFactory;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return this.iteratorFactory.createIterator(this);
    }

    /** Moves a node and its corresponding subtree (rooted at pOrig) to make it as a new children of pDest
     *
     * @param pOrig position of nodeOrig
     * @param pDest position of nodeDest
     * @return the position of destination node
     * @throws IllegalStateException
     */
    public Position<E> moveSubtree(Position<E> pOrig, Position<E> pDest) throws IllegalStateException{
        TreeNode<E> nOrig = checkPosition(pOrig);
        TreeNode<E> nDest = checkPosition(pDest);
        boolean descendent = false;
        Iterator<Position<E>> it = this.iteratorFactory.createIterator(this,pOrig);
        while(it.hasNext() && descendent==false){
            if(it.next() == pDest){
                descendent = true;
            }
        }
        if(descendent == true){            //Si el nodo destino es descendiente del origen
            throw new IllegalStateException("Destination Node can't be a descendent of Origin Node.");
        }else{
            removeMove(nOrig);
            List<Position<E>> lc = (List<Position<E>>) children(nDest);
            if(lc.size()>0) {
                TreeNode<E> nLastS = checkPosition(lc.get(lc.size() - 1));
                nLastS.setSibling(nOrig);
                nOrig.setParent(nDest);
            }else{
                nDest.setChild(nOrig);
                nOrig.setParent(nDest);
            }
        }

        return (Position) nDest;
    }

    private void removeMove(Position<E> p) throws IllegalStateException {
        TreeNode<E> node = checkPosition(p);
        TreeNode<E> nodeParent = node.getParent();
        if(nodeParent.getChild() == node){
            if(node.getSibling() != null){
                nodeParent.setChild(node.getSibling());
            }else{
                nodeParent.setChild(null);
            }
        }else{
            List<Position<E>> lc = (List<Position<E>>) children(nodeParent);
            for(Position<E> c : lc){
                TreeNode<E> cNode = checkPosition(c);
                if(cNode.getSibling() == node){
                    cNode.setSibling(node.getSibling());
                    break;
                }
            }
        }
    }
}
