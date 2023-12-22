import java.util.Comparator;

/**
 * custom comparator to compare two bankRecord objects
 */
public class BankRecordComparator implements Comparator<BankRecord> {
    private int index;

    /**
     * constructor
     * @param index
     */
    public BankRecordComparator(int index){
        this.index = index;
    }

    /**
     * compares the two objects, o1 and o2, for use in sort and finding methods
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return
     */
    @Override
    public int compare(BankRecord o1, BankRecord o2) {
        switch (index){
            case 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 21: return o1.get(index).compareTo(o2.get(index));
            case 1, 11, 12, 13, 14, 16, 17, 18, 19, 20: return Double.compare(Double.parseDouble(o1.get(index)), Double.parseDouble(o2.get(index)));
            default: return o1.getRecordID().compareTo(o2.getRecordID());
        }
    }
}
