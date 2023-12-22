import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankRecordTest {

    @Test
    void buildRecordID() {
        BankRecord record = new BankRecord();

        record.addDoubleValues(1);
        record.addDoubleValues(2);

        assertEquals("1200000000", record.buildRecordID());
    }

    @Test
    void getNumericValues() {
        BankRecord record = new BankRecord();
        record.addDoubleValues(10.5);
        record.addDoubleValues(20.0);

        assertEquals(10.5, record.getNumericValues(0), 0.01);
        assertEquals(20.0, record.getNumericValues(1), 0.01);
    }

    @Test
    void addStringValues() {
        BankRecord record = new BankRecord();
        record.addStringValues("Value1");
        record.addStringValues("Value2");

        assertEquals("Value1", record.getStringValues(0));
        assertEquals("Value2", record.getStringValues(1));
    }

    @Test
    void addDoubleValues() {
        BankRecord record = new BankRecord();
        record.addDoubleValues(10.5);
        record.addDoubleValues(20.0);

        assertEquals(10.5, record.getNumericValues(0), 0.01);
        assertEquals(20.0, record.getNumericValues(1), 0.01);
    }
}