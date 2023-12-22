import java.util.Iterator;

/**
 * this class stores methods to implement the custom AVL tree implementation
 * @param <K>
 * @param <V>
 */
public class MUAVLTree<K extends Comparable<K>, V> implements AVLTreeADT<K, V> {
    private AVLNode<KeyValue<K, V>> root;

    public MUAVLTree() {
        this.root = null;
    }

    @Override
    public void insert(KeyValue<K, V> data) {
        root = insert(root, data);
    }

    /**
     * get a key in the tree if it exists
     * @param key
     * @return
     */
    @Override
    public V get(K key) {
        AVLNode<KeyValue<K, V>> resultNode = getNode(root, key);
        return (resultNode != null) ? resultNode.getData().getValue() : null;
    }
    @Override
    public AVLNode<KeyValue<K, V>> getNode(AVLNode<KeyValue<K, V>> node, K key) {
        if (node == null) {
            // Key not found
            return null;
        }

        int compareResult = key.compareTo(node.getData().getKey());

        if (compareResult == 0) {
            // Key found
            return node;
        } else if (compareResult < 0) {
            // Search in the left subtree
            return getNode(node.getLeft(), key);
        } else {
            // Search in the right subtree
            return getNode(node.getRight(), key);
        }
    }

    /**
     * updates height and balance factor of the avl tree
     * @param node
     */
    @Override
    public void updateHeightAndBalance(AVLNode node) {
        if (node != null) {
            int leftHeight = getHeight(node.getLeft());
            int rightHeight = getHeight(node.getRight());
            node.setHeight(1 + Math.max(leftHeight, rightHeight));
            node.setBalanceFactor(leftHeight - rightHeight);
        }
    }

    /**
     * returns the balance factor of the avl tree
     * @param node
     * @return
     */
    @Override
    public int getBalanceFactor(AVLNode node) {
        return (node == null) ? 0 : getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    /**
     * returns the height of the avl tree
     * @param node
     * @return
     */
    @Override
    public int getHeight(AVLNode node) {
        return (node == null) ? -1 : node.getHeight();
    }

    /**
     * rotate right rebalancing method
     * @param x
     * @return
     */
    @Override
    public AVLNode<KeyValue<K, V>> rotateRight(AVLNode<KeyValue<K, V>> x) {
        AVLNode<KeyValue<K, V>> y = x.getLeft();
        AVLNode<KeyValue<K, V>> T2 = y.getRight();

        // Perform rotation
        y.setRight(x);
        x.setLeft(T2);

        // Update heights
        updateHeightAndBalance(x);
        updateHeightAndBalance(y);

        return y;
    }

    /**
     * rotate left rebalancing method
     * @param y
     * @return
     */
    @Override
    public AVLNode<KeyValue<K, V>> rotateLeft(AVLNode<KeyValue<K, V>> y) {
        AVLNode<KeyValue<K, V>> x = y.getRight();
        AVLNode<KeyValue<K, V>> T2 = x.getLeft();

        // Perform rotation
        x.setLeft(y);
        y.setRight(T2);

        // Update heights
        updateHeightAndBalance(y);
        updateHeightAndBalance(x);

        return x;
    }

    /**
     * balances the avl tree
     * @param node
     * @return
     */
    @Override
    public AVLNode<KeyValue<K, V>> balance(AVLNode<KeyValue<K, V>> node) {
        updateHeightAndBalance(node);

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            // Left heavy
            if (getBalanceFactor(node.getLeft()) < 0) {
                // Left-Right Case
                node.setLeft(rotateLeft(node.getLeft()));
            }
            // Left-Left Case
            return rotateRight(node);
        }

        if (balanceFactor < -1) {
            // Right heavy
            if (getBalanceFactor(node.getRight()) > 0) {
                // Right-Left Case
                node.setRight(rotateRight(node.getRight()));
            }
            // Right-Right Case
            return rotateLeft(node);
        }

        return node; // Tree is balanced
    }

    /**
     * insert data into the avl tree
     * @param node
     * @param data
     * @return
     */
    @Override
    public AVLNode<KeyValue<K, V>> insert(AVLNode<KeyValue<K, V>> node, KeyValue<K, V> data) {
        if (node == null) {
            return new AVLNode<>(data);
        }

        if (data.getKey().compareTo(node.getData().getKey()) < 0) {
            node.setLeft(insert(node.getLeft(), data));
        } else if (data.getKey().compareTo(node.getData().getKey()) > 0) {
            node.setRight(insert(node.getRight(), data));
        } else {
            // Duplicate key, do not insert
            return node;
        }

        return balance(node);
    }
    @Override
    public boolean contains(K key) {
        return contains(root, key);
    }

    /**
     * returns if the certain key is contained in the avl tree
     * @param node
     * @param key
     * @return
     */
    @Override
    public boolean contains(AVLNode<KeyValue<K, V>> node, K key) {
        if (node == null) {
            return false;
        }

        int compareResult = key.compareTo(node.getData().getKey());

        if (compareResult < 0) {
            return contains(node.getLeft(), key);
        } else if (compareResult > 0) {
            return contains(node.getRight(), key);
        } else {
            // Key found in the current node
            return true;
        }
    }

    @Override
    public void remove(K key) {
        root = remove(root, key);
    }

    /**
     * removes a node from the avl tree based on its key
     * @param node
     * @param key
     * @return
     */
    @Override
    public AVLNode<KeyValue<K, V>> remove(AVLNode<KeyValue<K, V>> node, K key) {
        if (node == null) {
            return null; // Key not found
        }

        int compareResult = key.compareTo(node.getData().getKey());

        if (compareResult < 0) {
            // Key is in the left subtree
            node.setLeft(remove(node.getLeft(), key));
        } else if (compareResult > 0) {
            // Key is in the right subtree
            node.setRight(remove(node.getRight(), key));
        } else {
            // Key found, perform removal
            if (node.getLeft() == null || node.getRight() == null) {
                // Node with only one child or no child
                node = (node.getLeft() != null) ? node.getLeft() : node.getRight();
            } else {
                // Node with two children
                AVLNode<KeyValue<K, V>> successor = findMin(node.getRight());
                node.setData(successor.getData());
                node.setRight(remove(node.getRight(), successor.getData().getKey()));
            }
        }

        // If the tree had only one node, then return
        if (node == null) {
            return null;
        }

        // Update height and balance factor
        updateHeightAndBalance(node);

        // Balance the tree
        return balance(node);
    }

    /**
     * returns the minimum value in the avl tree
     * @param node
     * @return
     */
    @Override
    public AVLNode<KeyValue<K, V>> findMin(AVLNode<KeyValue<K, V>> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    @Override
    public void preOrderTraversal() {
        preOrderTraversal(root);
    }

    /**
     * preforms pre-order traversal
     * @param node
     */
    @Override
    public void preOrderTraversal(AVLNode<KeyValue<K, V>> node) {
        if (node != null) {
            System.out.print(node.getData() + " " + "\n");
            preOrderTraversal(node.getLeft());
            preOrderTraversal(node.getRight());
        }
    }
    @Override
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    /**
     * preforms in-order traversal
     * @param node
     */
    @Override
    public void inOrderTraversal(AVLNode<KeyValue<K, V>> node) {
        if (node != null) {
            inOrderTraversal(node.getLeft());
            System.out.print(node.getData() + " " + "\n");
            inOrderTraversal(node.getRight());
        }
    }
    @Override
    public void postOrderTraversal() {
        postOrderTraversal(root);
    }

    /**
     * preforms post-order traversal
     * @param node
     */
    @Override
    public void postOrderTraversal(AVLNode<KeyValue<K, V>> node) {
        if (node != null) {
            postOrderTraversal(node.getLeft());
            postOrderTraversal(node.getRight());
            System.out.print(node.getData() + " " + "\n");
        }
    }
    @Override
    public int size() {
        return size(root);
    }

    /**
     * returns size of the avl tree
     * @param node
     * @return
     */
    @Override
    public int size(AVLNode<KeyValue<K, V>> node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.getLeft()) + size(node.getRight());
    }
}

