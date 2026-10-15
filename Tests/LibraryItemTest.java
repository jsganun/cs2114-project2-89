package Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Classes.Catalogue;
import Classes.LibraryItem;

/**
 * LibraryItemTest
 * Test class for LibraryItem. LibraryItem is abstract, so the tests use a
 * small stub subclass. An item has serial 0 until a Catalogue adds it,
 * so tests that need a serial add the item to a Catalogue.
 *
 * @author Jonathan Ganun
 * @version 10/15/2026
 */
public class LibraryItemTest {

    /** The smallest possible concrete item. */
    private static class StubItem extends LibraryItem {
        StubItem(String title, String creator, String description) {
            super(title, creator, description);
        }

        @Override
        public String getType() {
            return "Stub";
        }
    }

    private static final String LARP_DESCRIPTION =
        "A guide on how to larp your way through larping.";

    private LibraryItem item;
    private Catalogue catalogue;

    /**
     * Sets up the test LibraryItem for each test
     */
    @BeforeEach
    public void setUp() {
        item = new StubItem("How to Larp", "Larp Master", LARP_DESCRIPTION);
        catalogue = new Catalogue();
    }

    // ----------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------

    /**
     * Tests that the constructor stores title, creator, and description,
     * and accepts an empty description and a 100-character title.
     */
    @Test
    public void testConstructor() {
        assertEquals("How to Larp", item.getTitle());
        assertEquals("Larp Master", item.getCreator());
        assertEquals(LARP_DESCRIPTION, item.getDescription());
        assertEquals("", new StubItem("T", "C", "").getDescription());
        String exactly100 = "a".repeat(100);
        assertEquals(exactly100, new StubItem(exactly100, "C", "").getTitle());
    }

    /**
     * Tests that the constructor rejects a null, blank, or over-100-
     * character title or creator, and a null description.
     */
    @Test
    public void testConstructorBadInput() {
        IllegalArgumentException blank = assertThrows(
            IllegalArgumentException.class, () -> new StubItem("  ", "C", "D"));
        assertEquals("Title cannot be blank", blank.getMessage());
        assertThrows(IllegalArgumentException.class,
            () -> new StubItem(null, "C", "D"));
        assertThrows(IllegalArgumentException.class,
            () -> new StubItem("T", "", "D"));
        assertThrows(IllegalArgumentException.class,
            () -> new StubItem("T", null, "D"));
        IllegalArgumentException tooLong = assertThrows(
            IllegalArgumentException.class,
            () -> new StubItem("a".repeat(101), "C", "D"));
        assertEquals("Title is over 100 characters", tooLong.getMessage());
        IllegalArgumentException longCreator = assertThrows(
            IllegalArgumentException.class,
            () -> new StubItem("T", "a".repeat(101), "D"));
        assertEquals("Creator is over 100 characters",
            longCreator.getMessage());
        assertThrows(IllegalArgumentException.class,
            () -> new StubItem("T", "C", null));
    }

    // ----------------------------------------------------------
    // Getters
    // ----------------------------------------------------------

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
        assertEquals(LARP_DESCRIPTION, item.getDescription());
    }

    /**
     * Tests the getCreator() method
     */
    @Test
    public void testGetCreator() {
        assertEquals("Larp Master", item.getCreator());
    }

    /**
     * Tests the getType() method
     */
    @Test
    public void testGetType() {
        assertEquals("Stub", item.getType());
    }

    /**
     * Tests the getSerial() method: 0 before a Catalogue adds the item,
     * then positive, and each added item gets the next serial.
     */
    @Test
    public void testGetSerial() {
        LibraryItem item2 = new StubItem("Second", "Creator", "");
        assertEquals(0, item.getSerial());
        catalogue.addItem(item);
        catalogue.addItem(item2);
        assertEquals(1, item.getSerial());
        assertEquals(item.getSerial() + 1, item2.getSerial());
    }

    // ----------------------------------------------------------
    // Setters
    // ----------------------------------------------------------

    /**
     * Tests setTitle(): a good title replaces the old one, and bad input
     * keeps the old title.
     */
    @Test
    public void testSetTitle() {
        item.setTitle("How to not larp");
        assertEquals("How to not larp", item.getTitle());
    }

    /**
     * Tests that setTitle() rejects null, blank, and over-100-character
     * titles and keeps the old title.
     */
    @Test
    public void testSetTitleBadInput() {
        assertThrows(IllegalArgumentException.class, () -> item.setTitle(null));
        assertThrows(IllegalArgumentException.class, () -> item.setTitle(" \t "));
        assertThrows(IllegalArgumentException.class,
            () -> item.setTitle("a".repeat(101)));
        assertEquals("How to Larp", item.getTitle());
    }

    /**
     * Tests setCreator(): a good creator replaces the old one.
     */
    @Test
    public void testSetCreator() {
        item.setCreator("Not Larp Master");
        assertEquals("Not Larp Master", item.getCreator());
    }

    /**
     * Tests that setCreator() rejects null, blank, and over-100-character
     * creators and keeps the old creator.
     */
    @Test
    public void testSetCreatorBadInput() {
        assertThrows(IllegalArgumentException.class, () -> item.setCreator(null));
        assertThrows(IllegalArgumentException.class, () -> item.setCreator(""));
        assertThrows(IllegalArgumentException.class,
            () -> item.setCreator("a".repeat(101)));
        assertEquals("Larp Master", item.getCreator());
    }

    /**
     * Tests setDescription(): a good description, including an empty one,
     * replaces the old one.
     */
    @Test
    public void testSetDescription() {
        item.setDescription("A guide on how to not larp");
        assertEquals("A guide on how to not larp", item.getDescription());
        item.setDescription("");
        assertEquals("", item.getDescription());
    }

    /**
     * Tests that setDescription() rejects null and keeps the old value.
     */
    @Test
    public void testSetDescriptionBadInput() {
        assertThrows(IllegalArgumentException.class,
            () -> item.setDescription(null));
        assertEquals(LARP_DESCRIPTION, item.getDescription());
    }

    // ----------------------------------------------------------
    // matches and summary
    // ----------------------------------------------------------

    /**
     * Tests that matches() finds a keyword in the title, creator,
     * description, or type, ignoring case.
     */
    @Test
    public void testMatches() {
        assertTrue(item.matches("larp"));
        assertTrue(item.matches("MASTER"));
        assertTrue(item.matches("through larping"));
        assertTrue(item.matches("stub"));
        assertFalse(item.matches("zebra"));
    }

    /**
     * Tests that matches() rejects null and blank keywords, and treats
     * special characters as plain text instead of a pattern.
     */
    @Test
    public void testMatchesBadInput() {
        assertThrows(IllegalArgumentException.class, () -> item.matches(null));
        assertThrows(IllegalArgumentException.class, () -> item.matches("   "));
        assertFalse(item.matches(".*"));
        assertFalse(item.matches("(["));
    }

    /**
     * Tests the one-line summary.
     */
    @Test
    public void testSummary() {
        catalogue.addItem(item);
        assertEquals("[1] Stub \"How to Larp\" by Larp Master", item.summary());
    }

    // ----------------------------------------------------------
    // equals, hashCode, toString
    // ----------------------------------------------------------

    /**
     * Tests the equals() method: an item equals itself, and two copies
     * with the same title and creator are different because their serials
     * differ.
     */
    @Test
    public void testEquals() {
        LibraryItem copy = new StubItem("How to Larp", "Larp Master", LARP_DESCRIPTION);
        catalogue.addItem(item);
        catalogue.addItem(copy);

        assertTrue(item.equals(item));
        assertFalse(item.equals(copy));
        assertEquals(item.hashCode(), item.getSerial());
    }

    /**
     * Tests that equals() is false for null, another type, and for items
     * that have no serial yet.
     */
    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testEqualsBadInput() {
        LibraryItem unassigned = new StubItem("A", "B", "");
        assertFalse(item.equals(null));
        assertFalse(item.equals("Yogurt"));
        assertFalse(item.equals(unassigned));
        assertFalse(unassigned.equals(item));
        assertTrue(unassigned.equals(unassigned));
    }

    /**
     * Tests the toString() method
     */
    @Test
    public void testToString() {
        LibraryItem blankDescription = new StubItem("Blank", "Someone", "");
        catalogue.addItem(item);
        catalogue.addItem(blankDescription);
        String expectedFilled = "Stub 1" +
            "\nTitle: How to Larp" +
            "\nCreator: Larp Master" +
            "\nDescription: " + LARP_DESCRIPTION;
        String expectedBlank = "Stub 2" +
            "\nTitle: Blank" +
            "\nCreator: Someone" +
            "\nDescription: N/A";

        assertEquals(expectedFilled, item.toString());
        assertEquals(expectedBlank, blankDescription.toString());
    }
}
