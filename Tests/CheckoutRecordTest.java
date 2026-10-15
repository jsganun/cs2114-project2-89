package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Classes.Book;
import Classes.Catalogue;
import Classes.CheckoutRecord;
import Classes.DVD;
import Classes.LibraryItem;
import Classes.LinkedChain;

/**
 * CheckoutRecordTest
 * Test class for CheckoutRecord. Each public method has at least one
 * normal case and one bad-input case. A record keeps only an item's
 * serial, and an item has no serial until a Catalogue adds it, so the
 * fixtures add their items to a Catalogue first.
 *
 * @author Ajeet Bondugula (original tests); revised with Claude Code for
 *         the serial-based record
 * @version 10/15/2026
 *
 */
public class CheckoutRecordTest {

    /** An item that has a record on loan in most tests. */
    private LibraryItem book;
    /** A second item, used to make a record with a different serial. */
    private LibraryItem dvd;
    /** A record for book, borrowed by Sam. */
    private CheckoutRecord record;
    /** A borrower name one character over the 100-character limit. */
    private String longName;

    /**
     * Creates a new CheckoutRecordTest. JUnit calls this once per test;
     * the fixtures are built in setUp.
     */
    public CheckoutRecordTest() {
        // Fixtures are initialized in setUp
    }

    /**
     * Creates fresh items and a record before each test.
     */
    @BeforeEach
    public void setUp() {
        Catalogue catalogue = new Catalogue();
        book = new Book("Dune", "Frank Herbert", "A desert planet.",
            "Science Fiction", "English", "Paperback", 412);
        dvd = new DVD("Planet Earth", "BBC", "Nature documentary.",
            "Documentary", 11);
        catalogue.addItem(book);
        catalogue.addItem(dvd);
        record = new CheckoutRecord("Sam", book);
        longName = "a".repeat(CheckoutRecord.MAX_BORROWER_LENGTH + 1);
    }

    // ----------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------

    /**
     * Tests that the constructor stores the borrower and the item's
     * serial, and accepts a name of exactly 100 characters.
     */
    @Test
    public void testConstructor() {
        assertEquals("Sam", record.getBorrower());
        assertEquals(book.getSerial(), record.getSerial());
        String exactlyMax = "a".repeat(CheckoutRecord.MAX_BORROWER_LENGTH);
        assertEquals(exactlyMax, new CheckoutRecord(exactlyMax, dvd).getBorrower());
    }

    /**
     * Tests that the constructor rejects a null, blank, or over-100-
     * character borrower, a null item, and an item that has no serial.
     */
    @Test
    public void testConstructorBadInput() {
        IllegalArgumentException blank = assertThrows(IllegalArgumentException.class,
            () -> new CheckoutRecord("   ", book));
        assertEquals("Borrower name cannot be blank", blank.getMessage());

        IllegalArgumentException tooLong = assertThrows(IllegalArgumentException.class,
            () -> new CheckoutRecord(longName, book));
        assertEquals("Borrower name is over 100 characters", tooLong.getMessage());

        assertThrows(IllegalArgumentException.class, () -> new CheckoutRecord(null, book));
        assertThrows(IllegalArgumentException.class, () -> new CheckoutRecord("", book));
        IllegalArgumentException noItem = assertThrows(IllegalArgumentException.class,
            () -> new CheckoutRecord("Sam", null));
        assertEquals("Item cannot be null", noItem.getMessage());

        LibraryItem notAdded = new DVD("Loose", "Nobody", "", "Drama", 1);
        assertThrows(IllegalArgumentException.class,
            () -> new CheckoutRecord("Sam", notAdded));
    }

    // ----------------------------------------------------------
    // Getters
    // ----------------------------------------------------------

    /**
     * Tests that getBorrower returns the name, including one with
     * special characters.
     */
    @Test
    public void testGetBorrower() {
        assertEquals("Sam", record.getBorrower());
        CheckoutRecord odd = new CheckoutRecord("%s %n \\ <script>", dvd);
        assertEquals("%s %n \\ <script>", odd.getBorrower());
    }

    /**
     * Tests that getSerial returns the serial of the item on loan and is
     * always positive.
     */
    @Test
    public void testGetSerial() {
        assertEquals(book.getSerial(), record.getSerial());
        assertEquals(dvd.getSerial(), new CheckoutRecord("Lee", dvd).getSerial());
        assertTrue(record.getSerial() > 0);
    }

    // ----------------------------------------------------------
    // Setter
    // ----------------------------------------------------------

    /**
     * Tests that setBorrower changes the name.
     */
    @Test
    public void testSetBorrower() {
        record.setBorrower("Alex");
        assertEquals("Alex", record.getBorrower());
    }

    /**
     * Tests that setBorrower rejects null, blank, and over-100-character
     * names and keeps the old borrower.
     */
    @Test
    public void testSetBorrowerBadInput() {
        assertThrows(IllegalArgumentException.class, () -> record.setBorrower(null));
        assertThrows(IllegalArgumentException.class, () -> record.setBorrower(""));
        assertThrows(IllegalArgumentException.class, () -> record.setBorrower(" \t "));
        IllegalArgumentException tooLong = assertThrows(IllegalArgumentException.class,
            () -> record.setBorrower(longName));
        assertEquals("Borrower name is over 100 characters", tooLong.getMessage());
        assertEquals("Sam", record.getBorrower());
    }

    // ----------------------------------------------------------
    // equals, hashCode, toString
    // ----------------------------------------------------------

    /**
     * Tests that records for the same serial are equal, even with
     * different borrowers, and that a record equals itself.
     */
    @Test
    public void testEquals() {
        CheckoutRecord sameSerial = new CheckoutRecord("Different Person", book);
        assertTrue(record.equals(record));
        assertTrue(record.equals(sameSerial));
    }

    /**
     * Tests that a record is not equal to null, another type, or a record
     * for a different serial.
     */
    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testEqualsBadInput() {
        assertFalse(record.equals(null));
        assertFalse(record.equals("Sam"));
        assertFalse(record.equals(book));
        assertFalse(record.equals(new CheckoutRecord("Sam", dvd)));
    }

    /**
     * Tests that equal records share a hash code.
     */
    @Test
    public void testHashCode() {
        CheckoutRecord sameSerial = new CheckoutRecord("Different Person", book);
        assertEquals(record.hashCode(), sameSerial.hashCode());
        assertEquals(book.getSerial(), record.hashCode());
    }

    /**
     * Tests the toString output.
     */
    @Test
    public void testToString() {
        assertEquals("Item " + book.getSerial() + " is checked out to Sam.",
            record.toString());
    }

    // ----------------------------------------------------------
    // Stored in the generic LinkedChain
    // ----------------------------------------------------------

    /**
     * Tests that records can be stored in the generic LinkedChain and
     * found, counted, and removed through its public API.
     */
    @Test
    public void testStoredInLinkedChain() {
        LinkedChain<CheckoutRecord> records = new LinkedChain<>();
        CheckoutRecord dvdRecord = new CheckoutRecord("Lee", dvd);
        records.addToEnd(record);
        records.addToEnd(dvdRecord);

        assertEquals(2, records.size());
        assertSame(record, records.get(0));
        // contains and remove find a record through equals (by serial)
        assertTrue(records.contains(new CheckoutRecord("Anyone", book)));
        assertEquals(1, records.count(dvdRecord));
        assertTrue(records.remove(new CheckoutRecord("Anyone", book)));
        assertFalse(records.contains(record));
        assertEquals(1, records.size());
    }

    /**
     * Tests that a LinkedChain of records handles removing from an
     * empty chain, a null lookup, a null add, and an empty toArray.
     */
    @Test
    public void testStoredInLinkedChainBadInput() {
        LinkedChain<CheckoutRecord> records = new LinkedChain<>();
        assertFalse(records.remove(record));
        assertFalse(records.contains(null));
        assertFalse(records.addToEnd(null));
        assertEquals(0, records.toArray(new CheckoutRecord[0]).length);
    }
}
