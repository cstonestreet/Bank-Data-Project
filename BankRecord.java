/**
 * this class stores string and numeric values from the inputLineElement in the main, along with a method to build the recordID
 * from the numeric values, and methods that get the elements from inputLineElement and put them in the correct arrays.
 *
 */
public class BankRecord {
    int currentIncrement;
    String recordID;
    String [] stringValues = new String[11];
    double [] numericValues = new double[10];

    public MUArray<String> attributes = new MUArray<>();

    int doubleCount = 0;
    int stringCount = 0;

    public BankRecord(){

    }


    public BankRecord(int index, String value){
        switch (index){
            case 0: this.recordID = value;break;
            case 1: numericValues[0] = Double.parseDouble(value);break;
            case 2: stringValues[0] = value;break;
            case 3: stringValues[1]=value;break;
            case 4: stringValues[2]=value;break;
            case 5: stringValues[3]=value;break;
            case 6: stringValues[4]=value;break;
            case 7: stringValues[5]=value;break;
            case 8: stringValues[6]=value;break;
            case 9: stringValues[7]=value;break;
            case 10: stringValues[8]=value;break;
            case 11: numericValues[1] = Double.parseDouble(value);break;
            case 12: numericValues[2] = Double.parseDouble(value);break;
            case 13: numericValues[3] = Double.parseDouble(value);break;
            case 14: numericValues[4] = Double.parseDouble(value);break;
            case 15: stringValues[9]=value;break;
            case 16: numericValues[5] = Double.parseDouble(value);break;
            case 17: numericValues[6] = Double.parseDouble(value);break;
            case 18: numericValues[7] = Double.parseDouble(value);break;
            case 19: numericValues[8] = Double.parseDouble(value);break;
            case 20: numericValues[9] = Double.parseDouble(value);break;
            case 21: stringValues[10]=value;break;
            default: recordID = value;break;
        }
    }
    public String get(int index){
        switch (index){
            case 0: return recordID;
            case 1: return numericValues[0] + "";
            case 2: return stringValues[0];
            case 3: return stringValues[1];
            case 4: return stringValues[2];
            case 5: return stringValues[3];
            case 6: return stringValues[4];
            case 7: return stringValues[5];
            case 8: return stringValues[6];
            case 9: return stringValues[7];
            case 10: return stringValues[8];
            case 11: return numericValues[1] + "";
            case 12: return numericValues[2] + "";
            case 13: return numericValues[3] + "";
            case 14: return numericValues[4] + "";
            case 15: return stringValues[9];
            case 16: return numericValues[5] + "";
            case 17: return numericValues[6] + "";
            case 18: return numericValues[7] + "";
            case 19: return numericValues[8] + "";
            case 20: return numericValues[9] + "";
            case 21: return stringValues[10];


            default: return recordID;
        }
    }
    /**
     * this method creates a new string and creates a recordID with the values from numericValues, then returns the recordID
     * @return
     */
    public String buildRecordID() {
        StringBuilder recordIDBuild = new StringBuilder();

        for (int i = 0; i < numericValues.length; i++) {
            // Convert the double value to a string
            String numericValueAsString = Double.toString(numericValues[i]);

            // Remove any trailing ".0" characters
            if (numericValueAsString.endsWith(".0")) {
                numericValueAsString = numericValueAsString.substring(0, numericValueAsString.length() - 2);
            }

            recordIDBuild.append(numericValueAsString);
        }

        recordID = String.valueOf(recordIDBuild);
        return recordID;
    }


    /**
     * this method returns the value of numericValues at index i
     * @param i
     * @return
     */
    public double getNumericValues(int i) {
        return (numericValues[i]);
    }
    public String getStringValues(int i){
        return (stringValues[i]);
    }

    /**
     * this method adds the string elements from inputLineElement to an array called stringValues
     * @param inputLineElement
     */
    public void addStringValues(String inputLineElement) {
        stringValues[stringCount] = inputLineElement;
        stringCount++;

    }

    /**
     * this method adds the numeric elements from inputLineElement to an array called numericValues
     * @param temp
     */
    public void addDoubleValues(double temp) {
        numericValues[doubleCount] = temp;
        doubleCount++;
    }

    /**
     * this toString method
     * @return
     */
    @Override
    public String toString() {
        StringBuilder currentLineBuilder = new StringBuilder();
        for (int i = 0; i < attributes.getCount(); i++) {
            currentLineBuilder.append(attributes.getElement(i));
            if (i < attributes.getCount() - 1) {
                currentLineBuilder.append(",");
            }
        }
        if(currentIncrement == 0){
            return getRecordID() + "," + currentLineBuilder.toString().replaceAll(";", ",");
        }else{
            return getRecordID() + currentIncrement + "," + " " + currentLineBuilder.toString().replaceAll(";", ",");
        }
    }

    /**
     * returns the recordID
     * @return
     */
    public String getRecordID(){
        return recordID;
    }
    public boolean bankEquals(BankRecord obj, int index) {
        // Assuming get(index) returns a Comparable type, adjust this accordingly
        Comparable<String> getIndex = get(index);
        Comparable<String> objIndex = obj.get(index);

        // Check for null values
        if (getIndex == null && objIndex == null) {
            return true; // Both are null, consider them equal
        } else if (getIndex == null || objIndex == null) {
            return false; // One is null while the other is not, consider them not equal
        } else {
            return getIndex.compareTo(objIndex.toString()) == 0;
        }
    }
}
