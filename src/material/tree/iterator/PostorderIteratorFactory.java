package material.tree.iterator;

import material.tree.Position;
import material.tree.Tree;

import java.util.Iterator;

/**
 * Created by Alejandro on 20/5/16.
 */
public class PostorderIteratorFactory<E> implements TreeIteratorFactory<E> {
    /**
     * Creates an iterator from the root of the tree
     *
     * @param tree the tree to be iterated
     * @return the iterator of the tree
     */
    @Override
    public Iterator<Position<E>> createIterator(Tree<E> tree) {
        return new PostorderIterator<>(tree);
    }
    /**
     * Creates an iterator from the root of the tree
     *
     * @param tree the tree to be iterated
     * @param pos  the initial position of the iterator
     * @return the iterator of the tree
     */
    @Override
    public Iterator<Position<E>> createIterator(Tree<E> tree, Position<E> pos) {
        return new PostorderIterator<>(tree,pos);
    }
}
