import java.util.Arrays;

/**
 * this class contains methods to add to and mutate the data array
 */
public class BankRecordArray {
    private static final int DEFAULT_SIZE = 10;
    private BankRecord[] data;
    private int count;

    /**
     * Constructor with no arguments
     */
    public BankRecordArray() {
        data = new BankRecord[DEFAULT_SIZE];
    }


    /**
     * Constructor with a specified starting size
     * @param initialSize
     */
    public BankRecordArray(int initialSize) {
        if (initialSize <= 0) {
            throw new IllegalArgumentException("Initial size must be greater than zero");
        }
        data = new BankRecord[initialSize];
        this.count = 0;
    }

    /**
     * method returns size
     * @return
     */
    public int getSize() {
        return count;
    }

    /**
     * Mutator method to add a BankRecord to the array
     * @param record
     */
    public void addRecord(BankRecord record) {
        if (count == data.length) {
            // If the array is full, double its size
            data = Arrays.copyOf(data, data.length * 2);
        }
        data[count] = record;
        count++;
    }


    /**
     * returns record at a given index
     * @param index
     * @return
     */
    public BankRecord getRecord(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return data[index];
    }

    /**
     * sets the record at a given index to the given record
     * @param index
     * @param record
     */
    public void setRecord(int index, BankRecord record) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        data[index] = record;
    }

    /**
     * toString to print the completed lines of the file in reverse
     * @return
     */
    @Override
    public String toString() {
        StringBuilder total = new StringBuilder();
        for (int i = count - 1; i >= 0; i--) {
            total.append(data[i].toString()).append("\n");
        }
        return total.toString();
    }

}