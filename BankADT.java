/**
 * interface for the methods for MUArray
 * @param <T>
 */
public interface BankADT <T>{

    /**
     * Add element to specific index of the set if it does not exist in the set
     * @param index
     * @param element
     */
    void addAt(T element, int index);

    /**
     * replaces an element at a given index
     * @param element
     * @param index
     */
    void replaceAt(T element, int index);

    /**
     * Remove the target from the set if it exists in the set
     * @param target
     * @return
     */
    T remove(T target);
    /**
     * Remove the target in the index from the set if it exists in the set
     * @param index
     * @return
     */
    T remove(int index);

    /**
     * checks to see if the array contains a target element
     * @param target
     * @return
     */
    boolean contains(T target);

    /**
     * doubles the size of the array when it is full
     */
    void doubleSize();

    /**
     * shrinks the size of the array if over half of the array is empty, and the array will be larger than the initial
     * size after shrinking
     */
    void shrinkArray();

    /**
     * returns if the array is empty
     * @return
     */
    boolean isEmpty();

    /**
     * returns the array
     * @return
     */
    T[] getArray();

    /**
     * returns number of elements in the array
     * @return
     */
    int getCount();

    /**
     * returns the element in an array at a given index
     * @param index
     * @return
     */
    T getElement(int index);

    /**
     * returns the index value of a given element
     * @param element
     * @return
     */
    int indexOf(T element);

    /**
     * returns length of the array
     * @return
     */
    int getLength();
}