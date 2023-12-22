import java.io.*;
import java.util.Iterator;
import java.util.Scanner;


public class Bank4 {

    public static void main(String[] args) throws IOException {
        // object of class incrementStoring that allows for adding to the MUArrays in incrementStoring
        incrementStoring inc = new incrementStoring();
        OrderedMULinkedList<BankRecord> bankRecordsLinked = new OrderedMULinkedList<>();
        CustomHashMap<String, BankRecord> recordHashMap = new CustomHashMap<>();

        MUAVLTree<String, BankRecord> avlTree = new MUAVLTree<>();

        MUArray<Integer> countsArray = new MUArray<>();
        countsArray.add(0); // Initialize lineCount [0]
        countsArray.add(0); // Initialize invalidLineCount [1]
        countsArray.add(0); // Initialize duplicateLineCount [2]
        countsArray.add(0); // Initialize non-duplicate recordID count [3]

        // scanner to get the file name
        Scanner userInput = new Scanner(System.in);

        MUArray<BankRecord> bankRecords = new MUArray<>();
        // array to store the valid lines of the file
        MUArray<String> bankFileLine = new MUArray<>();
        MUArray<String> fileNames = new MUArray<>();

        String fileName;
        String header = "";
        int sortAttribute = -1;
        int findAttribute;
        String searchText;

        System.out.println("Welcome to Bank 4.0");
        boolean dataInputDone = false;

        while (!dataInputDone) {
            System.out.println("Enter file name, or press enter when finished");
            String dataFileName = userInput.nextLine();

            if (dataFileName.isEmpty()) {
                System.out.println("Thanks for using Bank 3.0");
                System.exit(0);
            } else {
                File file = new File(dataFileName);
                if (file.isFile() && !file.isDirectory()) {
                    fileNames.add(dataFileName);
                    dataInputDone = true;
                } else {
                    System.out.println("File does not exist.");
                }
            }
        }

        for (int i = 0; i < fileNames.getCount(); i++) {
            fileName = fileNames.getElement(i);
            File file = new File(fileName);
            Scanner input = new Scanner(file);

            // storing the header
            header = "header: " + "\"RecordID\"," + input.nextLine().replaceAll(";", ",");

            while (input.hasNextLine()) {
                String inputLine = input.nextLine();
                countsArray.replaceAt(countsArray.getElement(0) + 1, 0);
                // checking for duplicate line
                if (bankFileLine.contains(inputLine)) {
                    //System.out.println("Error: Duplicate Line " + lineCount);
                    countsArray.replaceAt(countsArray.getElement(2) + 1, 2);
                } else {
                    // creating array for elements in each line
                    String[] inputLineElements = inputLine.replaceAll("\"", "").split(";");
                    //System.out.println("inputLineElements after splitting:" + Arrays.toString(inputLineElements));
                    boolean invalidData = false;
                    // checking each element for ? or unknown
                    for (String inputLineElement : inputLineElements) {
                        if (inputLineElement.equals("?") || inputLineElement.contains("unknown")) {
                            invalidData = true;
                            //System.out.println("Line is invalid");
                            countsArray.replaceAt(countsArray.getElement(1) + 1, 1);
                            //System.out.println(countsArray.getElement(1));
                            break;
                        }
                    }
                    // if an element is ? or unknown, print invalid message
                    if (invalidData) {
                        //System.out.println("Invalid data at line " + lineCount);
                    } else {
                        // if the line is valid and not duplicate, add line to bankFileLine array
                        BankRecord record = new BankRecord(); // object of BankRecord class
                        bankFileLine.add(inputLine);
                        record.attributes.add(inputLine); // adds attribute line to attributes array in BankRecord

                        // System.out.println("Each inputLineElement that is being sent through try catch: ");
                        // try catch statement that adds each numeric attribute to the numericValues array using addDoubleValues, and each
                        // string attribute to the stringValues array using addStringValues
                        for (String inputLineElement : inputLineElements) {
                            try {
                                double temp = Double.parseDouble(inputLineElement);
                                // System.out.println(inputLineElement);
                                record.addDoubleValues(temp);
                            } catch (NumberFormatException e) {
                                //System.out.println(inputLineElement);
                                record.addStringValues(inputLineElement);
                            }
                        }

                        // uses the buildRecordID method in BankRecord to create record ID and then add it to the recordIDS array
                        String currentRecordID = record.buildRecordID();
                        record.recordID = currentRecordID;
                        int recordIndexOf = inc.recordIDS.indexOf(currentRecordID);

                        // if recordIDS does not contain the currentRecordID, then it is added to the array,
                        // along with a corresponding increment value in the increment array
                        if (recordIndexOf == -1) {
                            inc.recordIDS.add(currentRecordID);
                            inc.increment.add(0);
                            countsArray.replaceAt(countsArray.getElement(3) + 1, 3);
                        } else {
                            // If the currentRecordID is already in the recordIDS array,
                            // get its corresponding increment value and increment it by 1
                            int incrementValue = inc.increment.getElement(recordIndexOf);
                            incrementValue++; // Increment the value by 1
                            inc.increment.replaceAt(incrementValue, recordIndexOf);

                            // Assign the increment value to the current BankRecord
                            record.currentIncrement = incrementValue;
                        }
                        // add the record
                        avlTree.insert(new KeyValue<>(currentRecordID, record));
                        bankRecordsLinked.insertSorted(record);
                    }
                }
            }
            System.out.println("Data file: " + fileName + " was successfully read.");
            // file name
            // C:\Users\cmsto\Downloads\bank(1).csv
            // C:\Users\cmsto\Downloads\Connor.txt
            // C:\Users\cmsto\Downloads\smallTest.csv
        }

        Iterator<BankRecord> arrayIter = bankRecordsLinked.iterator();

        while (arrayIter.hasNext()) {
            BankRecord record = arrayIter.next();
            bankRecords.addAt(record, 0);
            recordHashMap.put(record.getRecordID(), record);
        }

        String testID = "24380199901.193.994-36.44.8575191";

        String manipLoop;
        String outputFile;
        boolean quit = false;
        while (!quit) {
            System.out.println("Enter (o)utput, (s)ort, (f)ind, (m)erge, (p)urge, (h)ash table, pre, in, post, or (q)uit:");
            manipLoop = userInput.nextLine();
            switch (manipLoop) {
                case "o":
                    System.out.println("Enter output file name: ");
                    outputFile = userInput.nextLine();

                    File outputFileCheck = new File(outputFile);

                    if (outputFile.isEmpty()) {
                        System.out.println(header);
                        System.out.println(bankRecords);

                        int recordsInMemory = avlTree.size();
                        System.out.println("Data lines read: " + countsArray.getElement(0) + ";" + " Records in memory: " + recordsInMemory + ";"
                                + " Invalid records: " + countsArray.getElement(1) + "," + " Duplicate records: " + countsArray.getElement(2) + "," +
                                " Non-Incremental RecordIDs: " + countsArray.getElement(3));
                        break;
                    } else if (outputFileCheck.exists()) {
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                            // write the header
                            writer.write(String.valueOf(bankRecords));
                            // append the lines stats
                            int recordsInMemory = avlTree.size();
                            writer.append(System.lineSeparator());
                            writer.append("Data lines read: " + countsArray.getElement(0) + ";" + " Records in memory: " + recordsInMemory + ";"
                                    + " Invalid records: " + countsArray.getElement(1) + "," + " Duplicate records: " + countsArray.getElement(2) + "," +
                                    " Non-Incremental RecordIDs: " + countsArray.getElement(3));

                            System.out.println("Output written to file: " + outputFile);
                        } catch (IOException e) {
                            System.out.println("Error writing to the output file: " + e.getMessage());
                        }
                        break;
                    } else {
                        System.out.println("Error: Output file not found.");
                        break;
                    }
                case "s":
                    System.out.println("Enter sort attribute (column number): ");
                    int sortIndex = userInput.nextInt();
                    userInput.nextLine();
                    if (sortIndex < 0 || sortIndex > 21) {
                        System.out.println("Invalid sort attribute value.");
                        break;
                    } else {
                        sortAttribute = sortIndex;
                    }
                    utility.selectionSort(bankRecords, new BankRecordComparator(sortAttribute));

                    System.out.println("Sorting complete");
                    break;
                case "f":
                    System.out.println("Enter search column: ");
                    findAttribute = userInput.nextInt();
                    if (findAttribute < 0 || findAttribute > 21) {
                        System.out.println("Invalid search column value.");
                        break;
                    } else if (findAttribute == 0) {
                        System.out.println("Enter record ID to search for: ");
                        userInput.nextLine();
                        searchText = userInput.nextLine();
                        BankRecord foundRecord = avlTree.get(searchText);
                        if (foundRecord != null) {
                            System.out.println("Record ID was found: ");
                            System.out.println(foundRecord);
                        } else {
                            System.out.println("record ID not found");
                        }
                    } else {
                        System.out.println("Enter exact text to search for in the column: ");
                        userInput.nextLine();
                        searchText = userInput.nextLine();
                        BankRecord dummy = new BankRecord(findAttribute, searchText);
                        BankRecordComparator newComp = new BankRecordComparator(findAttribute);
                        MUArray<BankRecord> finds = new MUArray<>();
                        if (sortAttribute == findAttribute) {
                            // call binary search
                            int result = utility.binarySearch(dummy, newComp, bankRecords);
                            if (result >= 0) {
                                int i = result;
                                while (i >= 0 && newComp.compare(dummy, bankRecords.getElement(i)) == 0) {
                                    finds.add(bankRecords.getElement(i));
                                    i--;
                                }
                                i = result + 1;
                                while (i < bankRecords.getCount() && (newComp.compare(dummy, bankRecords.getElement(i)) == 0)) {
                                    finds.add(bankRecords.getElement(i));
                                    i++;
                                }
                            } else {
                                System.out.println(result);
                            }
                        } else {
                            // linear search
                            finds = utility.linearSearch(dummy, newComp, bankRecords);
                        }
                        System.out.println(finds);
                        System.out.println("The specified search element was found " + finds.getCount() + " times.");

                    }
                    break;
                case "q":
                    System.out.println("Thanks for using Bank 4.0");
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice: Please try again");
                    break;
                case "m":
                    System.out.println("Enter data file name: ");
                    String mergeFile = userInput.nextLine();
                    if(mergeFile.isEmpty()){
                        System.out.println("File does not exist.");
                        break;
                    }
                    utility.mergeFiles(mergeFile, avlTree, bankRecordsLinked, countsArray);
                    utility.selectionSort(bankRecords, new BankRecordComparator(sortAttribute));

                    bankRecords = new MUArray<>();
                    avlTree = new MUAVLTree<>();
                    recordHashMap = new CustomHashMap<>();

                    Iterator<BankRecord> mergeIter = bankRecordsLinked.iterator();

                    while (mergeIter.hasNext()) {
                        BankRecord record = mergeIter.next();
                        bankRecords.add(record);
                        recordHashMap.put(record.getRecordID(), record);
                        avlTree.insert(new KeyValue<>(record.getRecordID(), record));
                    }


                    System.out.println("Data files merged");
                    break;
                case "p":
                    System.out.println("Enter data file name: ");
                    String purgeFile = userInput.nextLine();
                    if(purgeFile.isEmpty()){
                        System.out.println("File not does exist.");
                        break;
                    }
                    utility.purgeFiles(purgeFile, avlTree, bankRecordsLinked, countsArray);

                    bankRecords = new MUArray<>();
                    recordHashMap = new CustomHashMap<>();
                    avlTree = new MUAVLTree<>();
                    Iterator<BankRecord> newIter = bankRecordsLinked.iterator();

                    while (newIter.hasNext()) {
                        BankRecord record = newIter.next();
                        bankRecords.add(record);
                        recordHashMap.put(record.getRecordID(), record);
                        avlTree.insert(new KeyValue<>(record.getRecordID(), record));
                    }
                    System.out.println("Data file purged");
                    break;
                case "h":
                    System.out.println("Enter output file name: ");
                    String hashDisplayFile = userInput.nextLine();

                    Iterator<BankRecord> hashIter = bankRecordsLinked.iterator();

                    if (hashDisplayFile.isEmpty()) {
                        System.out.println(recordHashMap);
                        int recordsInMemory = avlTree.size();
                        System.out.println("Data lines read: " + countsArray.getElement(0) + ";" + " Records in memory: " + recordsInMemory + ";"
                                + " Invalid records: " + countsArray.getElement(1) + "," + " Duplicate records: " + countsArray.getElement(2) + "," +
                                " Non-Incremental RecordIDs: " + countsArray.getElement(3));
                    } else {
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(hashDisplayFile))) {
                            writer.write(String.valueOf(recordHashMap));

                            // append the lines stats
                            int recordsInMemory = avlTree.size();
                            writer.append(System.lineSeparator());
                            writer.append("Data lines read: " + countsArray.getElement(0) + ";" + " Records in memory: " + recordsInMemory + ";"
                                    + " Invalid records: " + countsArray.getElement(1) + "," + " Duplicate records: " + countsArray.getElement(2) + "," +
                                    " Non-Incremental RecordIDs: " + countsArray.getElement(3));

                            System.out.println("Output written to file: " + hashDisplayFile);
                        }
                    }
                    break;
                case "pre":
                    System.out.println("Pre-Order Traversal: ");
                    avlTree.preOrderTraversal();
                    System.out.println();
                    break;
                case "in":
                    System.out.println("In-order Traversal: ");
                    avlTree.inOrderTraversal();
                    System.out.println();
                    break;
                case "post":
                    System.out.println("Post-order Traversal: ");
                    avlTree.postOrderTraversal();
                    System.out.println();
                    break;
            }
        }
    }
}