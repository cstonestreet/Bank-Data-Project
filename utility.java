import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

/**
 * this class stores methods to sort, merge, purge, and search
 */
public class utility {

    /**
     * @param newFile
     * @param avlTree
     * @param bankRecordsLinked
     * @param countsArray
     */
    public static void mergeFiles(String newFile, MUAVLTree<String, BankRecord> avlTree, OrderedMULinkedList<BankRecord> bankRecordsLinked, MUArray<Integer> countsArray) {
        try {
            Scanner input = new Scanner(new File(newFile));

            String header = input.nextLine();

            while (input.hasNextLine()) {
                String inputLine = input.nextLine();
                countsArray.replaceAt(countsArray.getElement(0) + 1, 0);

                BankRecord record = parseBankRecord(inputLine);

                if (record != null) {
                    if (!avlTree.contains(record.getRecordID())) {
                        avlTree.insert(new KeyValue<>(record.getRecordID(), record));
                        bankRecordsLinked.insertSorted(record);
                    } else {
                        countsArray.replaceAt(countsArray.getElement(2) + 1, 2);
                    }
                } else {
                    countsArray.replaceAt(countsArray.getElement(1) + 1, 1);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File is not available.");
        }
    }

    /**
     * method that purges records from the linked list if they are contained in the linked list
     * @param newFile
     * @param avlTree
     * @param bankRecordsLinked
     * @param countsArray
     */
    public static void purgeFiles(String newFile, MUAVLTree<String, BankRecord> avlTree, OrderedMULinkedList<BankRecord> bankRecordsLinked, MUArray<Integer> countsArray) {
        try {
            Scanner input = new Scanner(new File(newFile));
            String header = input.nextLine();

            while (input.hasNextLine()) {
                String inputLine = input.nextLine();
                countsArray.replaceAt(countsArray.getElement(0) + 1, 0);

                BankRecord record = parseBankRecord(inputLine);

                if (record != null) {
                    if (avlTree.contains(record.getRecordID())) {
                        avlTree.remove(record.getRecordID());

                        bankRecordsLinked.remove(record);
                    } else {
                        countsArray.replaceAt(countsArray.getElement(2) + 1, 2);
                    }
                } else {
                    countsArray.replaceAt(countsArray.getElement(1) + 1, 1);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File is not available.");
        }
    }

    /**
     * method used to parse each line of a file, and returns the record object
     * @param inputLine
     * @return
     */
    private static BankRecord parseBankRecord(String inputLine) {
        // creating array for elements in each line
        String[] inputLineElements = inputLine.replaceAll("\"", "").split(";");
        boolean invalidData = false;
        // checking each element for ? or unknown
        for (String inputLineElement : inputLineElements) {
            if (inputLineElement.equals("?") || inputLineElement.contains("unknown")) {
                invalidData = true;
                return null;
            }
        }
        // if an element is ? or unknown, print invalid message
        // if the line is valid and not duplicate, add line to bankFileLine array
        BankRecord record = new BankRecord(); // object of BankRecord class
        record.attributes.add(inputLine); // adds attribute line to attributes array in BankRecord

        // try catch statement that adds each numeric attribute to the numericValues array using addDoubleValues, and each
        // string attribute to the stringValues array using addStringValues
        for (String inputLineElement : inputLineElements) {
            try {
                double temp = Double.parseDouble(inputLineElement);
                //System.out.println(inputLineElement);
                record.addDoubleValues(temp);
            } catch (NumberFormatException e) {
                //System.out.println(inputLineElement);
                record.addStringValues(inputLineElement);
            }
        }

        // uses the buildRecordID method in BankRecord to create record ID
        record.recordID = record.buildRecordID();
        return record;
    }


    /**
     * takes the linkedList and the custom comparator to use selection sort algorithm to sort based on column
     * @param array
     * @param comp
     */
    public static void selectionSort(MUArray<BankRecord> array, Comparator<BankRecord> comp) {
        if (array == null || array.getLength() == 0) {
            return;
        }
        int n = array.getCount();
        for (int i = n - 1; i >= 0; i--) {
            int iMax = i;
            for (int j = i - 1; j >= 0; j--) {
                if (comp.compare(array.getElement(j), array.getElement(iMax)) > 0) {
                    iMax = j;
                }
            }
            if (iMax != i) {
                BankRecord temp = array.getElement(iMax);
                array.replaceAt(array.getElement(i), iMax);
                array.replaceAt(temp, i);
            }
        }
    }

    /**
     * linear search algorithm to search for specific parameter
     * @param dummy
     * @param bankRecordComparator
     * @param data
     * @return
     */
    public static MUArray<BankRecord> linearSearch(BankRecord dummy, BankRecordComparator bankRecordComparator, MUArray<BankRecord> data) {
        MUArray<BankRecord> finds = new MUArray<>();
        for (int i = 0; i < data.getCount(); i++) {
            if(bankRecordComparator.compare(data.getElement(i), dummy) == 0){
                finds.add(data.getElement(i));
            }
        }
        return finds;
    }

    /**
     * binary search algorithm to search for specific parameter, only implemented when column is sorted
     * @param dummy
     * @param bankRecordComparator
     * @param data
     * @return
     */
    public static int binarySearch(BankRecord dummy, BankRecordComparator bankRecordComparator, MUArray<BankRecord> data) {
        int left = 0;
        int right =  data.getCount() - 1;
        int middle = 0;
        while(left <= right) {
            middle = (right + left) / 2;
            if (bankRecordComparator.compare(data.getElement(middle), dummy) == 0) {
                return middle;
            }else if (bankRecordComparator.compare(data.getElement(middle), dummy) < 0) {
                left = middle + 1;
            }else {
                right = middle - 1;
            }
        }
        return -1*left - 1;
    }
}

