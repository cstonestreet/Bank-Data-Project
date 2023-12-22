import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for the methods in the MUArray class
 */
class MUArrayTest {
    MUArray<String> testArray = new MUArray<>();

    @BeforeEach
    void setUp() {
        testArray.add("John");
        testArray.add("Steve");
        testArray.add("Bob");
    }

    @AfterEach
    void tearDown() {
        testArray = null;
    }

    @Test
    void add() {
        // Test adding an element
        testArray.add("Alice");
        assertTrue(testArray.contains("Alice"));
    }

    @Test
    void addAt() {
        // Test adding an element at a specific index
        testArray.addAt("Alice", 1);
        assertEquals("Alice", testArray.getElement(1));
    }

    @Test
    void replaceAt() {
        // Test replacing an element at a specific index
        testArray.replaceAt("Alice", 1);
        assertEquals("Alice", testArray.getElement(1));
    }

    @Test
    void remove() {
        // Test removing an element by value
        assertNotNull(testArray.remove("Steve"));
        assertFalse(testArray.contains("Steve"));
    }

    @Test
    void testRemove() {
        // Test removing an element by index
        assertNotNull(testArray.remove(1));
        assertFalse(testArray.contains("Steve"));
    }

    @Test
    void contains() {
        // Test if an element is contained in the array
        assertTrue(testArray.contains("Steve"));
        assertFalse(testArray.contains("Alice"));
    }

    @Test
    void doubleSize() {
        // Test doubling the size of the array
        for (int i = 0; i < 15; i++) {
            testArray.add("Element " + i);
        }
        assertEquals(20, testArray.getLength());
    }

    @Test
    void shrinkArray() {
        // Test shrinking the size of the array
        for (int i = 0; i < 38; i++) { // adds 38 more elements for a total of 41
            testArray.add("Element " + i);
        }
        // array size at this point should have doubled and currently be 80
        assertEquals(80, testArray.getLength());
        // remove 20 values, makes count equal to 21
        for (int i = 0; i < 20; i++) {
            testArray.remove(0);
        }
        // shrinkArray makes array size equal to 2 * count, which should be 42
        assertEquals(42, testArray.getLength());
    }

    @Test
    void isEmpty() {
        // Test checking if the array is empty
        assertFalse(testArray.isEmpty());
        testArray.remove(0);
        testArray.remove(0);
        testArray.remove(0);
        assertTrue(testArray.isEmpty());
    }

    @Test
    void getArray() {
        // Test getting the array
        assertNotNull(testArray.getArray());
    }

    @Test
    void getCount() {
        // Test getting the count of elements
        assertEquals(3, testArray.getCount());
    }

    @Test
    void getElement() {
        // Test getting an element by index
        assertEquals("John", testArray.getElement(0));
    }

    @Test
    void indexOf() {
        // Test getting the index of an element
        assertEquals(1, testArray.indexOf("Steve"));
    }

    @Test
    void testToString() {
        // Test converting the array to a string
        assertNotNull(testArray.toString());
    }
}