package Tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;

import Classes.LibraryItem;


public class LibraryItemTest {
    
    private LibraryItem item;

    @BeforeEach 
    public void setUp() {
        item = new LibraryItem("How to Larp", "Larp Master", "A guide on how to larp your way through larping.");
    }

    @Test 
    public void testGetTitle() {
        assertEquals("How to Larp", item.getTitle());
    }

    @Test 
    public void testGetDescription() {
        assertEquals("A guide on how to larp your way through larping.", item.getDescription());
    }

    @Test 
    public void testGetCreator() {
        assertEquals("Larp Master", item.getCreator());
    }

    @Test 
    public void testGetRecord() {
        
    }

    @Test
    public void testGetSerial() {
        LibraryItem item1 = new LibraryItem();
        LibraryItem item2 = new LibraryItem();

        assertEquals(item1.getSerial() + 1, item2.getSerial());
    }

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

    @Test 
    public void testSetRecord() {

    }

    @Test 
    public void testEquals() {
        LibraryItem anotherItem = new LibraryItem("How to Larp", "Larp Master", "A guide on how to larp your way through larping.");

        assertFalse(item.equals(null));
        assertFalse(item.equals("Yogurt"));
        assertFalse(item.equals(anotherItem));
        assertTrue(item.equals(item));
    }

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
