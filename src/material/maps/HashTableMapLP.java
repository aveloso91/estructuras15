package material.maps;

public class HashTableMapLP<K, V> extends AbstractHashTableMap<K, V> {

    /**
     * Collision solved with linear probe
     *
     * @param key
     * @return
     */
    public HashTableMapLP(int size) {
        super();
    }

    public HashTableMapLP() {
        super();
    }

    public HashTableMapLP(int p, int cap) {
        super();
    }

    @Override
    protected int offset(K key, int attempt) {
        return 1;
    }

}
