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

    private LCRSTreeNode<E> root; // reference to the root
    private int size; // number of nodes
    private TreeIteratorFactory<E> iteratorFactory;
    
    public class LCRSTreeNode<T>  implements Position<T> {

        private T element;
        private LCRSTreeNode<T> parent;
        private LCRSTreeNode<T> sibling;
        private LCRSTreeNode<T> child;

        @Override
        public T getElement() {
            return element;
        }
        public LCRSTreeNode(T element, LCRSTreeNode<T> parent,
                            LCRSTreeNode<T> sibling, LCRSTreeNode<T> child) {
            setElement(element);
            setParent(parent);
            setSibling(sibling);
            setChild(child);
        }

        public LCRSTreeNode<T> getParent() {
            return parent;
        }

        public void setParent(LCRSTreeNode<T> parent) {
            this.parent = parent;
        }

        public LCRSTreeNode<T> getSibling() {
            return sibling;
        }

        public void setSibling(LCRSTreeNode<T> sibling) {
            this.sibling = sibling;
        }

        public LCRSTreeNode<T> getChild() {
            return child;
        }

        public void setChild(LCRSTreeNode<T> child) {
            this.child = child;
        }

        public void setElement(T element) {
            this.element = element;
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size==0);
    }

    @Override
    public boolean isInternal(Position<E> v) throws IllegalStateException {
        return !isLeaf(v);
    }

    @Override
    public boolean isLeaf(Position<E> p) throws IllegalStateException {
        LCRSTreeNode<E> node = checkPosition(p);
        return (node.getChild() == null);
    }

    @Override
    public boolean isRoot(Position<E> p) throws IllegalStateException {
        LCRSTreeNode<E> node = checkPosition(p);
        return (node == this.root);
    }

    @Override
    public Position<E> root() throws IllegalStateException {
        if(root == null){
            throw new IllegalStateException("The tree is empty");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> p) throws IndexOutOfBoundsException, IllegalStateException {
        LCRSTreeNode<E> node = checkPosition(p);
        Position<E> nParent = (Position<E>) node.getParent();
        if(nParent == null){
            throw new IndexOutOfBoundsException("No parent");
        }
        return nParent;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> p) {
        LCRSTreeNode<E> pos = checkPosition(p);
        List<Position<E>> children = new ArrayList<Position<E>>();

        if (isLeaf(p)) {
            throw new IllegalStateException("External nodes have no children");
        }

        LCRSTreeNode<E> posChild = pos.getChild();   //Accedo al hijo del puntero dado
        while (posChild != null) {
            children.add(posChild);
            posChild = posChild.getSibling();
        }
        return children;

    }

    public E replace(Position<E> p, E e) throws IllegalStateException {
        LCRSTreeNode<E> node = checkPosition(p);
        E temp = p.getElement();
        node.setElement(e);
        return temp;    }

    @Override
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) {
            throw new IllegalStateException("Tree already has a root");
        }
        size = 1;
        root = new LCRSTreeNode<E>(e, null, null, null);
        return root;    }

    
    public void swapElements(Position<E> p1, Position<E> p2) throws IllegalStateException {
        LCRSTreeNode<E> node1 = checkPosition(p1);
        LCRSTreeNode<E> node2 = checkPosition(p2);
        E elem = node1.getElement();
        node1.setElement(node2.getElement());
        node2.setElement(elem);
    }

    
    private LCRSTreeNode<E> checkPosition(Position<E> p) throws IllegalStateException {
        if (p == null || !(p instanceof LCRSTreeNode)) {
            throw new IllegalStateException("The position is invalid");
        }
        return (LCRSTreeNode<E>) p;
    }


    public Position<E> add(E element, Position<E> p) {
        LCRSTreeNode<E> parent = checkPosition(p);
        LCRSTreeNode<E> newNode = new LCRSTreeNode<E>(element, parent, null, null);
        LCRSTreeNode<E> pAux;

        if (parent.getChild() == null) { //Si el padre no tene hijos se añade sin dilación
            parent.setChild(newNode);
            newNode.setParent(parent);
        } else if (parent.getChild() != null) { //Si tiene hijos, se recorren los hijos y ponemos el nuevo como ultimo hermano
            pAux = parent.getChild();//Pongo el puntero auxiliar apuntando al hijo del padre dado
            if (pAux.getSibling() == null) {//Si no tiene mas hermanos, le añado como hermano de este
                pAux.setSibling(newNode);
            } else {//En caso contrario (tiene mas hermanos)
                while (pAux.getSibling() != null) {//Mientras tenga hermanos recorro hasta el ultimo
                    pAux = pAux.getSibling();//Avanzamos el puntero hermano a hermano
                }
                pAux.setSibling(newNode);//Cuando no tenga mas hermanos, lo añado
            }
        }
        size++;
        return (Position<E>) newNode;
    }

    public void remove(Position<E> p) throws IllegalStateException {
        LCRSTreeNode<E> node = checkPosition(p);
        if (node.getParent() != null) {  //Si el nodo tiene padre, recorro desde el nodo
            Iterator<Position<E>> it = this.iteratorFactory.createIterator(this, p);
            int cont = 0;  //En el contador guardo el numero de elementos que hay por debajo del nodo
            while (it.hasNext()) {
                it.next();
                cont++;
            }  //Al eliminar el nodo, se van a eliminar todos sus hijos y demas elementos por debajo
            size = size - cont;  //por lo que esta es la forma mas rapida de eliminar y actualizar el tamaño

            LCRSTreeNode<E> hijo = node.getChild();
            LCRSTreeNode<E> padre = node.getParent();
            LCRSTreeNode<E> hermano = node.getSibling();
            if ((hijo != null) && (hijo.getSibling() != null)) {  //Si nodo tiene hijos e hijo tiene hermano
                hijo.setParent(padre);  //Hijo de nodo a eliminar pasa a tener como padre al padre del nodo a eliminar
                hijo.getSibling().setSibling(hermano);
                //hermano del Hijo del nodo a eliminar pasa a tener como hermano al hermano nodo a eliminar

            } else if ((hijo == null) && (hermano != null)) {  //Si no tiene hijos pero sí hermanos
                hermano.setParent(padre);
            } else if ((hijo != null) && (hijo.getSibling() == null)) { //Si nodo tiene hijos e hijo no tiene hermano
                hijo.setParent(padre);
                hijo.setSibling(hermano); //Hermano del hijo va a ser hermano del nodo a eliminar
            }
            node.setParent(null);  //Poniendo todos sus punteros a null, se elimina el nodo automaticamente
            node.setChild(null);
            node.setSibling(null);
            node.setElement(null);

        } else {
            this.root = null;
            this.size = 0;
        }
    }

    public void setIterator(TreeIteratorFactory<E> iteratorFactory) {
        this.iteratorFactory = iteratorFactory;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return this.iteratorFactory.createIterator(this);
    }

    public Position<E> moveSubtree(Position<E> pOrig, Position<E> pDest) throws IllegalStateException {
        //Creado
        LCRSTreeNode<E> origen = checkPosition(pOrig); //puntero al nodo origen
        LCRSTreeNode<E> destino = checkPosition(pDest); //puntero al nodo destino
        LCRSTreeNode<E> pAux, pAux2;  //Puntero auxiliar
        LCRSTreeNode<E> pAuxHijo, pAuxHermano; //puntero auxiliar

        if (origen.getParent() == null) { //Si el nodo origen es la raíz... EXCEPCION
            throw new IllegalStateException("The position is root");
        } else {
            pAux2 = origen.getParent(); //Puntero al padre
            pAuxHijo = pAux2.getChild();  //Puntero al hijo del padre
            pAuxHermano = pAuxHijo.getSibling(); //Puntero al hermano de pAuxHijo

            if (pAuxHijo == origen) { //Si el nodo origen es el primer hijo
                pAux2.setChild(origen.getSibling()); //ahora el primer hijo es el hermano del origen
            } else {
                while (pAuxHermano != origen) { //busco para encontrar el nodo origen
                    pAuxHijo = pAuxHermano;  //puntero al hermano siguiente
                    pAuxHermano = pAuxHermano.getSibling();  //Sigo recorriendo hermanos
                }
                pAuxHijo.setSibling(origen.getSibling()); //coloco el hermano anterior al hermano siguiente del nodo origen
            }
            origen.setParent(destino); //cuelgo el nodo origen al destino
            origen.setSibling(null); //hago que no tenga hermanos

            if (destino.getChild() == null) {  //Si destino no tiene hijos, se añade sin mas
                destino.setChild(origen);
            } else {  //Caso contrario (tiene hijos). Lo añadimos como hijo de destino y los hijos que ya tenia, pasan como hermanos de origen
                pAux = destino.getChild();  //Puntero auxiliar al hijo de destino
                while (pAux.getSibling() != null) {  //Recorro los hermanos del hijo de destino hasta que no haya mas
                    pAux = pAux.getSibling();
                }
                pAux.setSibling(origen);  //Cuando no haya mas, el siguiente hermano sera el nodo origen

            }
        }
        return origen;   //Devuelvo el nodo origen
    }
}
