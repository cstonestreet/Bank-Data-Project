import java.util.NoSuchElementException;

/**
 * this class implements the methods defined in the BankADT interface
 * @param <T>
 */
public class MUArray<T> implements BankADT<T>{
    private T[] array;
    private int count;
    private static final int DEFAULT_SIZE = 10;
    @SuppressWarnings("unchecked")
    public MUArray(){
        array = (T[]) new Object[DEFAULT_SIZE];
        count = 0;
    }

    public MUArray(int initialSize){
        array = (T[]) new Object[initialSize];
        count = 0;
    }


    public void add(T element){
        if(element == null){
            return;
        }


        if(count == array.length){
            doubleSize();
        }
        array[count] = element;
        count++;
    }
    @Override
    public void addAt(T element, int index) {
        if(index < 0 || index > count){
            throw new IndexOutOfBoundsException("Index value is out of range");
        }
        if(element == null){
            return;
        }
        if(contains(element)){
            return;
        }
        count++;

        if(count == array.length){
            doubleSize();
        }

        for (int i = count; i > index ; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
    }

    @Override
    public void replaceAt(T element, int index) {
        if(isEmpty()){
            throw new NoSuchElementException("Array is empty");
        }
        if(index < 0 || index > count){
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        array[index] = element;
    }

    @Override
    public T remove(T target) {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty set");
        }
        if(target == null){
            return null;
        }
        T toReturn = null;
        for (int i = 0; i < count; i++) {
            if (array[i].equals(target)) {
                toReturn = array[i];
                count--;

                for (int j = i; j < count; j++) {
                    array[j] = array[j+1];
                }
                array[count] = null;

                if (2*count < array.length && count > DEFAULT_SIZE) {
                    shrinkArray();
                }

                return toReturn;
            }
        }
        return null;
    }

    @Override
    public T remove(int index) {
        if(isEmpty()){
            throw new NoSuchElementException("It is empty");
        }
        if(index < 0 || index > count){
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        T toReturn = array[index];
        count--;
        for (int i = index; i < count; i++) { // n
            array[i] = array[i + 1];
        }
        array[count] = null;

        if(array.length > 2*count && count > DEFAULT_SIZE){
            shrinkArray();
        }
        return toReturn;
    }
    @Override
    public boolean contains(T target) { // O(n)
        for (int i = 0; i < count; i++) {  // using count because you don't want to consider null values
            if (array[i].equals(target)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void doubleSize() {
        int growSize = 2 * array.length;
        if (growSize < array.length) {
            throw new OutOfMemoryError(); // in case enough values are added that it reaches maximum integer value
        }
        T[] temp = (T[]) new Object[growSize];
        // puts all the values in values array into the temp array
        System.arraycopy(array, 0, temp, 0, array.length);
        array = temp; // reassigns the temp array to the values array, the values array is now double sized
    }
    @Override
    public void shrinkArray(){
        int newSize = 2 * count;
        T[] temp = (T[]) new Object[newSize];
        // puts all the values in values array into the temp array
        if (count >= 0) System.arraycopy(array, 0, temp, 0, count);
        array = temp;
    }

    @Override
    public boolean isEmpty() {
        return count == 0; // if count = 0, then returns true, else, returns false
    }
    @Override
    public T[] getArray() {
        return array;
    }
    @Override
    public int getCount() {
        return count;
    }
    @Override
    public T getElement(int index){
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return array[index];
    }
    @Override
    public int indexOf(T element){
        int value = -1; // initial value that gets returned if item is not found
        if(element == null){
            return value;
        }
        for (int i = 0; i < count; i++) {  // using count because you don't want to consider null values
            if (array[i].equals(element)) {
                value = i;
                return value;
            }
        }
        return value;
    }
    @Override
    public int getLength(){
        return array.length;
    }


    /**
     * toString to print the completed lines of the file in reverse
     * @return
     */
    @Override
    public String toString() {
        StringBuilder total = new StringBuilder();
        for (int i = count - 1; i >= 0; i--) {
            total.append(array[i].toString()).append("\n");
        }
        return total.toString();
    }
}
