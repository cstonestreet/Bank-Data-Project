/**
 * This is the generic contract of Set Structure
 * @param <T>
 */
public interface LinkedSetADT<T> {

    /**
     * Add element to the set if it does not exist in the set
     * @param element
     */
    void add(T element); // automatically public and abstract

    /**
     * Add element to specific index of the set if it does not exist in the set
     * @param index
     * @param element
     */
    void add(int index, T element);

    /**
     * Remove the target from the set if it exists in the set
     * @param target
     * @return
     */
    public T remove(T target);

    /**
     * Remvoe the target in the index from the set if it exists in the set
     * @param index
     * @return
     */
    public T remove(int index);

    void clear();

    /**
     * check whether the target exists in the set
     * @param target
     * @return
     */
    boolean contains(T target);

    /**
     * replaces element in the set
     * @param element
     * @param index
     */
    void replaceAt(T element, int index);


    /**
     * return how many elements in the set
     * @return
     */
    int size();

    /**
     * checks whether the set is empty
     * @return
     */
    boolean isEmpty();

    /**
     * Union two set and return a new set that includes this and another set
     * @param anotherSet
     * @return
     */
    LinkedSetADT<T> union(LinkedSetADT<T> anotherSet);

    /**
     * Union two set and return a new set that intersects this and another set
     * @param anotherSet
     * @return
     */
    LinkedSetADT<T> intersection(LinkedSetADT<T> anotherSet);

    /**
     * return the element in the index from the set
     * @param index
     * @return
     */
    T get(int index);


}