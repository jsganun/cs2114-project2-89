package Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import Classes.LibraryItem;
import Classes.CheckoutRecord;

/**
 * LibraryItemTest
 * Test class for LibraryItem
 * 
 * @author Jonathan Ganun
 * @version 10/15/2026
 */
public class LibraryItemTest {
    
    private LibraryItem item;

    /**
     * Sets up the test LibraryItem for each test
     */
    @BeforeEach 
    public void setUp() {
        item = new LibraryItem("How to Larp", "Larp Master", "A guide on how to larp your way through larping.");
    }

    /**
     * Tests the getTitle() method
     */
    @Test 
    public void testGetTitle() {
        assertEquals("How to Larp", item.getTitle());
    }

    /**
     * Tests the getDescription() method
     */
    @Test 
    public void testGetDescription() {
        assertEquals("A guide on how to larp your way through larping.", item.getDescription());
    }

    /**
     * Tests the getCreator() method
     */
    @Test 
    public void testGetCreator() {
        assertEquals("Larp Master", item.getCreator());
    }

    /**
     * Tests the getRecord() method
     */
    @Test 
    public void testGetRecord() {
        assertNull(item.getRecord());
    }

    /**
     * Tests the getSerial() method
     */
    @Test
    public void testGetSerial() {
        LibraryItem item1 = new LibraryItem();
        LibraryItem item2 = new LibraryItem();

        assertEquals(item1.getSerial() + 1, item2.getSerial());
    }

    /**
     * Tests the setTitle() method
     */
    @Test 
    public void testSetTitle() {
        Exception exception = null;
        try {
            item.setTitle(null);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertTrue(exception instanceof IllegalArgumentException);

        item.setTitle("How to not larp");
        assertEquals("How to not larp", item.getTitle());
    }

    /**
     * Tests the setCreator() method
     */
    @Test 
    public void testSetCreator() {
        Exception exception = null;
        try {
            item.setCreator(null);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertTrue(exception instanceof IllegalArgumentException);

        item.setCreator("Not Larp Master");
        assertEquals("Not Larp Master", item.getCreator());
    }

    /**
     * Tests the setDescription() method
     */
    @Test 
    public void testSetDescription() {
        Exception exception = null;
        try {
            item.setDescription(null);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertTrue(exception instanceof IllegalArgumentException);

        item.setDescription("A guide on how to not larp");
        assertEquals("A guide on how to not larp", item.getDescription());
    }

    /**
     * Tests the setRecord() method
     */
    @Test 
    public void testSetRecord() {
        CheckoutRecord record = new CheckoutRecord("John", item);

        item.setRecord(record);
        assertEquals(record, item.getRecord());

        item.setRecord(null);
        assertNull(item.getRecord());
    }

    /**
     * Tests the equals() method
     */
    @SuppressWarnings("unlikely-arg-type")
    @Test 
    public void testEquals() {
        LibraryItem anotherItem = new LibraryItem("How to Larp", "Larp Master", "A guide on how to larp your way through larping.");

        assertFalse(item.equals(null));
        assertFalse(item.equals("Yogurt"));
        assertFalse(item.equals(anotherItem));
        assertTrue(item.equals(item));
    }

    /**
     * Tests the toString() method
     */
    @Test 
    public void testToString() {
        LibraryItem blankItem = new LibraryItem();
        String expectedBlank = "Title: N/A" +
            "\nCreator: N/A" +
            "\nSerial: " + blankItem.getSerial() + 
            "\nDescription: N/A";
        String expectedFilled = "Title: How to Larp" +
            "\nCreator: Larp Master" +
            "\nSerial: " + item.getSerial() + 
            "\nDescription: A guide on how to larp your way through larping.";
        
        assertEquals(expectedBlank, blankItem.toString());    
        assertEquals(expectedFilled, item.toString());
    }
}
