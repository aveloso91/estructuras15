package material.maps;

public class HashTableMapDH<K, V> extends AbstractHashTableMap<K, V> {

    public HashTableMapDH(int size) {
        super();
    }

    public HashTableMapDH() {
        super();
    }

    public HashTableMapDH(int p, int cap) {
        super();
    }

    @Override
    protected int offset(K key, int attempt) {
        int cap = this.capacity / 2 ;
        int p = cap - (key.hashCode() % cap);
        return p * attempt;
    }

}
