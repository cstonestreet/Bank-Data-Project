import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankRecordArrayTest {

    @Test
    void addRecord() {
        BankRecordArray bra = new BankRecordArray();

        BankRecord record1 = new BankRecord();
        BankRecord record2 = new BankRecord();

        bra.addRecord(record1);
        bra.addRecord(record2);

        assertEquals(2, bra.getSize(), "Expected size of BankRecordArray");
    }

    @Test
    void getRecord() {
        BankRecordArray bra = new BankRecordArray();

        BankRecord record1 = new BankRecord();
        bra.addRecord(record1);

        assertEquals(record1, bra.getRecord(0), "Expected the same record");
    }

    @Test
    void getRecordOutOfBounds() {
        BankRecordArray bra = new BankRecordArray();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            bra.getRecord(0); // This should throw an IndexOutOfBoundsException
        });
    }

    @Test
    void setRecord() {
        BankRecordArray bra = new BankRecordArray();

        BankRecord record1 = new BankRecord();
        BankRecord record2 = new BankRecord();

        bra.addRecord(record1);
        bra.setRecord(0, record2);

        assertEquals(record2, bra.getRecord(0), "Expected updated record");
    }

    @Test
    void testToString() {
        BankRecordArray bra = new BankRecordArray();

        BankRecord record1 = new BankRecord();
        BankRecord record2 = new BankRecord();

        bra.addRecord(record1);
        bra.addRecord(record2);

        String expected = record2 + "\n" + record1 + "\n";

        assertEquals(expected, bra.toString(), "Expected formatted string");
    }
}