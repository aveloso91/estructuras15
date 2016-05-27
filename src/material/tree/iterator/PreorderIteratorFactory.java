package material.tree.iterator;

import material.tree.Position;
import material.tree.Tree;

import java.util.Iterator;

/**
 * Created by Alejandro on 20/5/16.
 */
public class PreorderIteratorFactory<E> implements TreeIteratorFactory<E> {
    @Override
    public Iterator<Position<E>> createIterator(Tree<E> tree) {
        return new PreorderIterator<>(tree);
    }

    @Override
    public Iterator<Position<E>> createIterator(Tree<E> tree, Position<E> pos) {
        return new PreorderIterator<>(tree,pos);
    }
}
