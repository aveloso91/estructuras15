package material.tree.iterator;

import material.tree.Position;
import material.tree.Tree;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/**
 * Created by Alejandro on 29/4/16.
 */
public class FrontIterator<E> implements Iterator<Position<E>> {
    private final Queue<Position<E>> nodeQueue;
    private final Tree<E> tree;

    public FrontIterator(Tree<E> tree, Position<E> start) {
        nodeQueue = new ArrayDeque<>();
        this.tree = tree;
        front(start, nodeQueue);
    }

    public FrontIterator(Tree<E> tree) {
        nodeQueue = new ArrayDeque<>();
        this.tree = tree;
        front(tree.root(), nodeQueue);
    }

    private void front(Position<E> p, Queue<Position<E>> pos) {
        for (Position<E> w : tree.children(p)) {
            if (tree.isLeaf(w)) {
                nodeQueue.add(w);
            } else {
                front(w, nodeQueue);
            }
        }
    }

    @Override
    public boolean hasNext() {
        return (nodeQueue.size() != 0);
    }

    @Override
    public Position<E> next() {
        Position<E> aux = nodeQueue.remove();
        return aux;
    }

}
