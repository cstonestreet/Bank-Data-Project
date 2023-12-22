public class AVLNode<T extends Comparable<T>> extends BankRecord {

    private T data;
    private AVLNode<T> left;
    private AVLNode<T> right;
    private int height;
    private int balanceFactor;

    public AVLNode(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 0;
        this.balanceFactor = 0;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public AVLNode<T> getLeft() {
        return left;
    }

    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    public AVLNode<T> getRight() {
        return right;
    }

    public void setRight(AVLNode<T> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBalanceFactor() {
        return balanceFactor;
    }

    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }
}
