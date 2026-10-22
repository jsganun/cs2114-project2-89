package Tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

import Classes.Book;

public class BookTest
{
    private Book harryPotter;
    /**
     * Sets up a Book object for testing before each test method is run.
     */
    @BeforeEach
    public void setUp()
    {
        harryPotter = new Book("HarryPotter", "J.K. Rowling", "British Kid", "Fiction", "English", "HardCover", "Publisher", 200 );
    }
    /**
     * Tests that the getter methods return the expected values for the Book object.
     */
    @Test
    public void testGetPublisher()
    {
        assertEquals("Publisher", harryPotter.getPublisher());

    }
    /**
     * Tests that the setter methods update the values for the Book object.
     */
    @Test
    public void testSetPublisher()
    {
        harryPotter.setPublisher("New Publisher");
        assertEquals("New Publisher", harryPotter.getPublisher());

    } 
    /**
     * Tests that the getter methods return the expected values for the Book object.
     */
    @Test
    public void testGetGenre()
    {
        assertEquals("Fiction", harryPotter.getGenre());

    }
    /**
     * Tests that the getter methods return the expected values for the Book object.
     */
    @Test
    public void testGetFormat()
    {
        assertEquals("HardCover", harryPotter.getFormat());

    }
    /**
     * Tests that the getter methods return the expected values for the Book object.
     */
    @Test
    public void testGetLanguage()
    {
        assertEquals("English", harryPotter.getLanguage());

    }
    /**
     * Tests that the getter methods return the expected values for the Book object.
     */
    @Test
    public void testGetPageCount()
    {
        assertEquals(200, harryPotter.getPageCount());

    }
    /**
     * Tests that the setter methods update the values for the Book object.
     */
    @Test
    public void testSetGenre()
    {
        harryPotter.setGenre("Non-Fiction");
        assertEquals("Non-Fiction", harryPotter.getGenre());

    }
    /**
     * Tests that the setter methods update the values for the Book object.
     */
    @Test
    public void testSetFormat()
    {
        harryPotter.setFormat("SoftCover");
        assertEquals("SoftCover", harryPotter.getFormat());

    }
    /**
     * Tests that the setter methods update the values for the Book object.
     */
    @Test
    public void testSetLanguage()
    {
        harryPotter.setLanguage("Spanish");
        assertEquals("Spanish", harryPotter.getLanguage());

    }
    /**
     * Tests that the setter methods update the values for the Book object.
     */
    @Test
    public void testSetPageCount()
    {
        harryPotter.setPageCount(300);
        assertEquals(300, harryPotter.getPageCount());

    }

    // ----------------------------------------------------------
    // Added for the prototype: constructor, bad input, type, text
    // ----------------------------------------------------------

    /**
     * Tests that the constructor stores the title, creator, description,
     * and every descriptor.
     */
    @Test
    public void testConstructor()
    {
        assertEquals("HarryPotter", harryPotter.getTitle());
        assertEquals("Fiction", harryPotter.getGenre());
        assertEquals("English", harryPotter.getLanguage());
        assertEquals("HardCover", harryPotter.getFormat());
        assertEquals("Publisher", harryPotter.getPublisher());
        assertEquals(200, harryPotter.getPageCount());
        assertEquals(0, harryPotter.getSerial());
    }

    /**
     * Tests that the constructor rejects a blank title, a blank or null
     * String descriptor, and a count that is not greater than 0.
     */
    @Test
    public void testConstructorBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> new Book("  ", "Creator", "D", "Fiction", "English", "HardCover","yur", 200));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", null, "D", "Fiction", "English", "HardCover","yur", 200));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "C", null, "Fiction", "English", "HardCover","yur", 200));
        assertThrows(IllegalArgumentException.class, () -> new Book("a".repeat(101), "C", "D", "Fiction", "English", "HardCover","yur", 200));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "C", "D", null, "English", "HardCover","yur", 200));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "C", "D", "   ", "English", "HardCover","yur", 200));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "C", "D", "Fiction", null, "HardCover","yur", 200));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "C", "D", "Fiction", "   ", "HardCover","yur", 200));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "C", "D", "Fiction", "English", null,"yur", 200));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "C", "D", "Fiction", "English", "   ","yur", 200));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "C", "D", "Fiction", "English", "HardCover","yur", 0));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "C", "D", "Fiction", "English", "HardCover","yur", -5));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "C", "D", "Fiction", "English", "HardCover",null, 200));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "C", "D", "Fiction", "English", "HardCover"," ", 200));
    }

    /**
     * Tests that setGenre rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetGenreBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setGenre(null));
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setGenre("   "));
        assertEquals("Fiction", harryPotter.getGenre());
    }

    /**
     * Tests that setLanguage rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetLanguageBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setLanguage(null));
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setLanguage("   "));
        assertEquals("English", harryPotter.getLanguage());
    }

    /**
     * Tests that setPublisher rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetPublisherBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setPublisher(null));
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setPublisher("   "));
        assertEquals("Publisher", harryPotter.getPublisher());
    }

    /**
     * Tests that setFormat rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetFormatBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setFormat(null));
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setFormat("   "));
        assertEquals("HardCover", harryPotter.getFormat());
    }

    /**
     * Tests that setPageCount rejects 0 and negative numbers and keeps the old value.
     */
    @Test
    public void testSetPageCountBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setPageCount(0));
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setPageCount(-5));
        assertEquals(200, harryPotter.getPageCount());
    }

    /**
     * Tests getType.
     */
    @Test
    public void testGetType()
    {
        assertEquals("Book", harryPotter.getType());
    }

    /**
     * Tests that toString lists the common fields and every descriptor.
     */
    @Test
    public void testToString()
    {
        assertEquals("Book 0\nTitle: HarryPotter\nCreator: J.K. Rowling\nDescription: British Kid"
            + "\nGenre: Fiction\nLanguage: English\nFormat: HardCover"
            + "\nPublisher: Publisher\nPage count: 200\nISBN: N/A",
            harryPotter.toString());
    }

    /**
     * Tests that matches finds a keyword in this class's own descriptors.
     */
    @Test
    public void testMatches()
    {
        assertTrue(harryPotter.matches("hardcover"));
        assertTrue(harryPotter.matches("rowling"));
        assertTrue(harryPotter.matches("english"));
        assertTrue(harryPotter.matches("publisher"));
        assertFalse(harryPotter.matches("zebra"));
    }

    /**
     * Tests that matches rejects null and blank keywords.
     */
    @Test
    public void testMatchesBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> harryPotter.matches(null));
        assertThrows(IllegalArgumentException.class, () -> harryPotter.matches("  "));
    }

    // ----------------------------------------------------------
    // ISBN (stretch goal: extra descriptors with a check-digit rule)
    // ----------------------------------------------------------

    /**
     * Tests that a valid ISBN-13, ISBN-10, and an ISBN-10 ending in X are
     * accepted, with or without hyphens, and are stored without hyphens.
     */
    @Test
    public void testSetIsbn()
    {
        assertFalse(harryPotter.hasIsbn());
        assertEquals("", harryPotter.getIsbn());
        harryPotter.setIsbn("978-0-306-40615-7");
        assertTrue(harryPotter.hasIsbn());
        assertEquals("9780306406157", harryPotter.getIsbn());
        harryPotter.setIsbn("0-306-40615-2");
        assertEquals("0306406152", harryPotter.getIsbn());
        harryPotter.setIsbn("0 8044 2957 x");
        assertEquals("080442957X", harryPotter.getIsbn());
    }

    /**
     * Tests that a wrong check digit, wrong length, letters, empty text,
     * and null are rejected, and that the old ISBN is kept.
     */
    @Test
    public void testSetIsbnBadInput()
    {
        harryPotter.setIsbn("9780306406157");
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setIsbn("978-0-306-40615-6"));
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setIsbn("0306406153"));
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setIsbn("12345"));
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setIsbn("97803064061AB"));
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setIsbn(""));
        assertThrows(IllegalArgumentException.class, () -> harryPotter.setIsbn(null));
        assertEquals("9780306406157", harryPotter.getIsbn());
    }

    /**
     * Tests isValidIsbn on good and bad text, including an X anywhere but
     * the last place of an ISBN-10.
     */
    @Test
    public void testIsValidIsbn()
    {
        assertTrue(Book.isValidIsbn("9780547928227"));
        assertTrue(Book.isValidIsbn("080442957X"));
        assertFalse(Book.isValidIsbn("X306406152"));
        assertFalse(Book.isValidIsbn("0306406152X"));
        assertFalse(Book.isValidIsbn(null));
        assertFalse(Book.isValidIsbn("   "));
    }

    /**
     * Tests that a recorded ISBN shows in toString and in searches.
     */
    @Test
    public void testIsbnInToStringAndMatches()
    {
        harryPotter.setIsbn("9780306406157");
        assertTrue(harryPotter.toString().endsWith("\nISBN: 9780306406157"));
        assertTrue(harryPotter.matches("9780306406157"));
    }
}
