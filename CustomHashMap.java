

import java.util.Iterator;

/**
 * this class stores methods for the custom hash map implementation
 * @param <K>
 * @param <V>
 */
public class CustomHashMap <K extends Comparable<K>, V> implements HashMapADT<K, V> {

    private final static int DEFAULT_SIZE = 17;
    MUArray<OrderedMULinkedList<KeyValue<K, V>>> data;
    private int tableSize;
    private double loadFactor = 0.90;
    private int count;
    public CustomHashMap() {
        // TODO Auto-generated constructor stub
        data = new MUArray<OrderedMULinkedList<KeyValue<K, V>>>(DEFAULT_SIZE);
        tableSize = DEFAULT_SIZE;
        for (int i = 0; i < tableSize; i++) {
            data.add(new OrderedMULinkedList<KeyValue<K, V>>());
        }
        count = 0;

    }

    /**
     * method to get key from hash map
     * @param key
     * @return
     */
    @Override
    public V get(K key) {
        int index = hash(key);
        OrderedMULinkedList<KeyValue<K, V>> list = data.getElement(index);
        Iterator<KeyValue<K, V>> iter = list.iterator();
        while(iter.hasNext()){
            KeyValue<K, V> kv = iter.next();
            if(kv.getKey().equals(key)){
                return kv.getValue();
            }
        }
        return null; // Key not found
    }

    /**
     * puts the element in the hash map
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            return;
        }

        if (loadFactor < (double) count / tableSize) {
            rehashing();
        }
        KeyValue<K, V> kv = new KeyValue<K, V>(key, value);
        int index = hash(key);

        OrderedMULinkedList<KeyValue<K, V>> list = data.getElement(index);

        // Check if the key already exists in the list
        if (list.contains(kv)) {
            list.remove(kv);  // Remove the existing key-value pair
            list.add(kv);     // Add it back to the end of the list (for ordering)
        } else {
            list.add(kv);     // Add the new key-value pair to the list
            count++;
        }
    }

    /**
     * updates the value at a certain key
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean update(K key, V value) {
        int index = hash(key);
        OrderedMULinkedList<KeyValue<K, V>> list = data.getElement(index);
        Iterator<KeyValue<K, V>> iter = list.iterator();

        while(iter.hasNext()){
            KeyValue<K,V> kv = iter.next();
            if(kv.getKey().equals(key)){
                kv.setValue(value);
                return true;
            }
        }
        return false;
    }

    /**
     * see if the hash map contains a key
     * @param key
     * @return
     */
    @Override
    public boolean containsKey(K key) {
        int index = hash(key);
        OrderedMULinkedList<KeyValue<K, V>> list = data.getElement(index);
        Iterator<KeyValue<K, V>> iter = list.iterator();
        while(iter.hasNext()){
            KeyValue<K,V> kv = iter.next();
            if(kv.getKey().equals(key)){
                return true;
            }
        }
        return false;
    }

    /**
     * rehashing function to increase table size
     */
    @Override
    public void rehashing() {
        // TODO Auto-generated method stub
        MUArray<OrderedMULinkedList<KeyValue<K, V>>> temp = new MUArray<OrderedMULinkedList<KeyValue<K, V>>>(2 * tableSize +  1);
        tableSize = 2*tableSize + 1;
        for (int i = 0; i < tableSize; i++) {
            temp.add(new OrderedMULinkedList<KeyValue<K, V>>());
        }

        for (int i = 0; i < data.getCount(); i++) {
            Iterator<KeyValue<K, V>> iter = data.getElement(i).iterator();
            while(iter.hasNext()) {
                KeyValue<K, V> kv = iter.next();
                int index = hash(kv.getKey());
                temp.getElement(index).add(kv);
            }
        }
        data = temp;
    }

    /**
     * calculating hash value
     * @param key
     * @return
     */
    @Override
    public int hash(K key) {
        return Math.abs(key.hashCode()) % tableSize;
    }

    /**
     * printing out each bucket of the hash map, including overflows, followed by stats about the hash map
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int baseCount = tableSize;
        int overflowCount = 0;
        for (int i = 0; i < data.getCount(); i++) {
            if (!data.getElement(i).isEmpty()) {
                sb.append("Bucket number: ").append(i).append(":");
                Iterator<KeyValue<K, V>> iter = data.getElement(i).iterator();
                int j = 0;

                while (iter.hasNext()) {
                    if (j >= 1) {
                        sb.append(" Overflow: ");
                    }
                    sb.append(" ").append(iter.next().toString()).append(", ");
                    overflowCount++;
                    j++;
                }
                sb.append("\n\n");
            }
        }
        int totalCount = baseCount + overflowCount;
        sb.append("Base capacity: " + baseCount + "\n" + "Total Capacity: " + totalCount + "\n" + "Load Factor: " + ((double) count / tableSize) + "\n");
        return sb.toString();
    }

    /**
     * gets the count
     * @return
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * checks if hash map is empty
     * @return
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * remove element from hash map
     * @param key
     * @return
     */
    @Override
    public boolean remove(K key) {
        int index = hash(key);
        OrderedMULinkedList<KeyValue<K, V>> list = data.getElement(index);
        Iterator<KeyValue<K, V>> iter = list.iterator();
        KeyValue<K, V> toRemove = null;
        while(iter.hasNext()){
            KeyValue<K,V> kv = iter.next();
            if (kv.getKey().equals(key)) {
                toRemove = kv;
                break;
            }
        }

        if (toRemove != null) {
            list.remove(toRemove);
            count--;
            return true;  // Key successfully removed
        } else {
            return false;  // Key not found
        }
    }

}