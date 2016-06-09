package material.tree.binarytree;

import java.util.Iterator;
import material.tree.Position;

public class BinaryTreeUtils<E> {

    private BinaryTree<E> binTree;
    
    public BinaryTreeUtils(BinaryTree<E> binTree) {
        this.binTree = binTree;
    }

    /**
     * Given a tree the method returns a new tree where all left children become
     * right children and vice versa
     * @return the mirror of the tree
     */
    public BinaryTree<E> mirror() {
        BinaryTree<E> result = null;
        if (binTree instanceof LinkedBinaryTree) {
            result = new LinkedBinaryTree();
            if (binTree.isEmpty()) {
                return result;
            } else {
                Position<E> root = binTree.root();//Obtenemos raiz de binTree y se la añadimos a la result
                result.addRoot(root.getElement());
                Position<E> rootS = result.root();
                mirrorLBT(root, (LinkedBinaryTree) result, rootS);//calculamos espejo
                return result;
            }
        }
        else if (binTree instanceof ArrayBinaryTree) {
            result = new ArrayBinaryTree(this.binTree.size());
            if (binTree.isEmpty()) {
                return result;
            } else {
                Position<E> root = binTree.root();
                result.addRoot(root.getElement());
                Position<E> rootS = result.root();
                mirrorABT(root, (ArrayBinaryTree) result, rootS);
                return result;
            }
        }
        return result;
    }

    private void mirrorLBT(Position<E> root, LinkedBinaryTree<E> result, Position<E> rootResult){
        if(!this.binTree.isLeaf(root)){
            if(this.binTree.hasLeft(root)){
                result.insertRight(rootResult, this.binTree.left(root).getElement());//si no es hoja y tiene hijo por un lado
                mirrorLBT(this.binTree.left(root), result, result.right(rootResult));//lo insertamos por el otro y volvemos a llamar
            }
            if(this.binTree.hasRight(root)){
                result.insertLeft(rootResult, this.binTree.right(root).getElement());
                mirrorLBT(this.binTree.right(root), result, result.left(rootResult));
            }
        }

    }

    private void mirrorABT(Position<E> root, ArrayBinaryTree<E> result, Position<E> rootResult){
        if(!this.binTree.isLeaf(root)){
            if(this.binTree.hasLeft(root)){
                result.insertRight(rootResult, this.binTree.left(root).getElement());
                mirrorABT(this.binTree.left(root), result, result.right(rootResult));
            }
            if(this.binTree.hasRight(root)){
                result.insertLeft(rootResult, this.binTree.right(root).getElement());
                mirrorABT(this.binTree.right(root), result, result.left(rootResult));
            }
        }
    }

    /**
     * Determines whether the element e is the tree or not
     * @param e the element to check
     * @return TRUE if e is in the tree, FALSE otherwise
     */
    public boolean contains(E e) {
        if(binTree.isEmpty()){
            return false;
        }
        else{
            if(binTree.root().getElement().equals(e)){
                return true;
            }
            else{
                boolean find = false;
                Iterator<Position<E>> it = binTree.iterator();
                while(it.hasNext() && !find){
                    Position<E> nodo = (Position<E>) it.next();
                    if(nodo.getElement().equals(e)){
                        find = true;
                    }
                }
                return find;
            }
        }
    }

    /**
     * Determines the level of a node in the tree
     * @param p the position to check
     * @return the level of the position in the tree
     */
    public int level(Position<E> p) {
        int cont = 0;
        while(!binTree.isRoot(p)){
            p=binTree.parent(p);
            cont++;
        }
        return cont;
    }
}
