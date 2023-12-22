
public interface HashMapADT<K extends Comparable<K>, V> {

    V get(K key);

    void put(K key, V value);
    boolean update(K key, V value);

    boolean containsKey(K key);

    void rehashing();

    int hash(K key);

    int size();

    boolean isEmpty();

    boolean remove(K key);

}
