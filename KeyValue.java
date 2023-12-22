

import java.util.Objects;

/**
 * this class stores getters and setters for the key values
 * @param <K>
 * @param <V>
 */
public class KeyValue<K, V> extends BankRecord implements Comparable<KeyValue<K, V>> {

    private K key;
    private V value;

    public KeyValue(K key, V value) {
        // TODO Auto-generated constructor stub
        this.key = key;
        this.value = value;
    }


    public K getKey() {
        return key;
    }


    public void setKey(K key) {
        this.key = key;
    }


    public V getValue() {
        return value;
    }


    public void setValue(V value) {
        this.value = value;
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return key + " " + value;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof KeyValue) {
            KeyValue<K,V> p = (KeyValue<K, V>) obj;
            if (Objects.equals(key, p.key)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int compareTo(KeyValue<K, V> o) {
        return this.key.toString().compareTo(o.key.toString());
    }


}
