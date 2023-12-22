import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedMULinkedList <T extends BankRecord> implements LinkedSetADT<T>{


    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int count; // keep track of num of elements in the linked list

    public OrderedMULinkedList(){
        head = null;
        tail = null;
        count = 0;
    }

    /**
     * Add element to the set if it does not exist in the set
     *
     * @param element
     */
    @Override
    public void add(T element) {
        if (element == null) {
            return;
        }

        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(element);

        if (head == null) { // if there is no head yet
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }

        count++;
    }


    /**
     * Add element to specific index of the set if it does not exist in the set
     *
     * @param index
     * @param element
     */
    @Override
    public void add(int index, T element) { // O(n)
        if(index < 0 || index > count){
            throw new IndexOutOfBoundsException();
        }
        if(element == null){
            return;
        }
        if(contains(element)){
            return;
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(element);
        if(head == null) { // if there is a head yet
            head = newNode;
            tail = newNode;
            count++;
            return;
        }

        int i = 0;
        DoublyLinkedListNode<T> current = head;
        DoublyLinkedListNode<T> prev = null;
        while(i < index){
            prev = current; // set the prev to the current
            current = current.getNext(); // set the current equal to the next one
            i++;
        }
        if(current == null){
            tail.setNext(newNode);
            tail = newNode;
        } else if (prev == null) {
            newNode.setNext(head);
            head = newNode;
        }else{
            prev.setNext(newNode);
            newNode.setNext(current);
        }
        count++;
    }
    public void insertSorted(T data) {
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);

        if (head == null) {
            // If the list is empty, insert at the beginning
            head = newNode;
            tail = newNode;
        } else {
            DoublyLinkedListNode<T> current = head;
            DoublyLinkedListNode<T> previous = null;

            // Create a comparator instance based on the recordID
            BankRecordComparator comparator = new BankRecordComparator(0); // Assuming index 0 corresponds to recordID

            // Create an iterator
            Iterator<T> iterator = iterator();

            // Find the correct position for insertion
            while (iterator.hasNext()) {
                T element = iterator.next();
                if (comparator.compare(data, element) > 0) {
                    previous = current;
                    current = current.getNext();
                } else {
                    break; // Stop when we find the correct position
                }
            }

            // Insert the new node
            newNode.setNext(current);
            newNode.setPrev(previous);

            if (previous != null) {
                previous.setNext(newNode);
            } else {
                // newNode is the new head
                head = newNode;
            }

            if (current != null) {
                current.setPrev(newNode);
            } else {
                // newNode is the new tail
                tail = newNode;
            }
        }
        count++;
    }





    /**
     * Remove the target from the set if it exists in the set
     *
     * @param target
     * @return
     */
    @Override
    public T remove(T target) { // O(n)
        if(target == null){
            return null;
        }
        if(isEmpty()){
            throw new NoSuchElementException("There is no such element to remove");
        }

        DoublyLinkedListNode<T> current = head;
        DoublyLinkedListNode<T> prev = null;

        while(current != null && !current.getData().bankEquals(target, 0)){
            prev = current;
            current = current.getNext();
        }
        if(current == null){
            return null;
        }
        T toReturn = current.getData();
        if(prev != null){
            prev.setNext(current.getNext());
            if(tail == current){
                tail = prev;
            }
        }else{
            head = head.getNext();
            if(count == 1){
                tail = null;
            }
        }

        count--;
        return toReturn;
    }

    /**
     * Remvoe the target in the index from the set if it exists in the set
     *
     * @param index
     * @return
     */

    @Override
    public T remove(int index) { //O(n)
        if(index < 0 || index >= count){
            throw new IndexOutOfBoundsException();
        }
        int i = 0;
        DoublyLinkedListNode<T> current = head;
        while(i < index){
            current = current.getNext();
            i++;
        }
        return remove(current.getData());
    }
    @Override
    public void clear() {
        head = null;
        tail = null;
        count = 0;
    }


    /**
     * check whether the target exists in the set
     *
     * @param target
     * @return
     */
    @Override
    public boolean contains(T target) { // O(n)
        DoublyLinkedListNode<T> current = head;
        while(current != null){
            if(current.getData().bankEquals(target, 0)){
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public void replaceAt(T element, int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        DoublyLinkedListNode<T> current = head;

        // Traverse to the node at the specified index
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        // Update the data of the node at the specified index
        current.setData(element);
    }

    /**
     * return how many elements in the set
     *
     * @return
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * checks whether the set is empty
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public LinkedSetADT<T> union(LinkedSetADT<T> anotherSet) {
        return null;
    }

    @Override
    public LinkedSetADT<T> intersection(LinkedSetADT<T> anotherSet) {
        return null;
    }


    /**
     * return the element in the index from the set
     *
     * @param index
     * @return
     */
    @Override
    public T get(int index) { // O(n)
        if(index < 0 || index >= count){
            throw new IndexOutOfBoundsException();
        }
        DoublyLinkedListNode<T> current = head;
        int i = 0;
        while(current != null && i < index){
            current = current.getNext();
            i++;
        }
        return current.getData();
    }

    public Iterator<T> iterator(){
        return new LinkedSetIterator<>(head);
    }
}