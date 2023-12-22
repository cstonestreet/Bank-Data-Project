import java.util.Iterator;

public interface AVLTreeADT<K extends Comparable<K>, V> {
    void insert(KeyValue<K, V> data);
    AVLNode<KeyValue<K, V>> insert(AVLNode<KeyValue<K, V>> node, KeyValue<K, V> data);
    AVLNode<KeyValue<K, V>> balance(AVLNode<KeyValue<K, V>> node);
    AVLNode<KeyValue<K, V>> rotateLeft(AVLNode<KeyValue<K, V>> y);
    AVLNode<KeyValue<K, V>> rotateRight(AVLNode<KeyValue<K, V>> x);
    int getHeight(AVLNode<KeyValue<K, V>> node);
    int getBalanceFactor(AVLNode<KeyValue<K, V>> node);

    V get(K key);

    AVLNode<KeyValue<K, V>> getNode(AVLNode<KeyValue<K, V>> node, K key);

    void updateHeightAndBalance(AVLNode<KeyValue<K, V>> node);

    boolean contains(K key);

    boolean contains(AVLNode<KeyValue<K, V>> node, K key);

    void remove(K key);

    AVLNode<KeyValue<K, V>> remove(AVLNode<KeyValue<K, V>> node, K key);

    AVLNode<KeyValue<K, V>> findMin(AVLNode<KeyValue<K, V>> node);

    void preOrderTraversal();

    void preOrderTraversal(AVLNode<KeyValue<K, V>> node);

    void inOrderTraversal();

    void inOrderTraversal(AVLNode<KeyValue<K, V>> node);

    void postOrderTraversal();

    void postOrderTraversal(AVLNode<KeyValue<K, V>> node);

    int size();

    int size(AVLNode<KeyValue<K, V>> node);
}





