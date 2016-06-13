package material.maps;

public class HashTableMapQP<K, V> extends AbstractHashTableMap<K, V> {

    public HashTableMapQP(int size) {
        super();
    }

    public HashTableMapQP() {
        super();
    }

    public HashTableMapQP(int p, int cap) {
        super();
    }

    @Override
    protected int offset(K key, int attempt) {
        return 1*attempt + 1*attempt*attempt;
    }
}
