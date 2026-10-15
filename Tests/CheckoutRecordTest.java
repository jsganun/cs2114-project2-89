package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Classes.CheckoutRecord;
import Classes.LibraryItem;
import Classes.LinkedChain;

/**
 * CheckoutRecordTest
 * Test class for CheckoutRecord. Each public method has at least one
 * normal case and one bad-input case.
 *
 * @author Ajeet Bondugula
 * @version 10/14/2026
 *
 */
public class CheckoutRecordTest {

    /** An item that has a record on loan in most tests. */
    private LibraryItem book;
    /** A second item, used to make a record with a different serial. */
    private LibraryItem dvd;
    /** A record for book, borrowed by Sam. */
    private CheckoutRecord record;
    /** A record made with the no-argument constructor. */
    private CheckoutRecord blankRecord;
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
     * Creates fresh items and records before each test.
     */
    @BeforeEach
    public void setUp() {
        book = new LibraryItem("Dune", "Frank Herbert", "A desert planet.");
        dvd = new LibraryItem("Planet Earth", "BBC", "Nature documentary.");
        record = new CheckoutRecord("Sam", book);
        blankRecord = new CheckoutRecord();
        longName = "a".repeat(CheckoutRecord.MAX_BORROWER_LENGTH + 1);
    }

    // ----------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------

    /**
     * Tests that the no-argument constructor makes a blank record
     * with no borrower or item and a positive ID.
     */
    @Test
    public void testDefaultConstructor() {
        assertNull(blankRecord.getBorrower());
        assertNull(blankRecord.getItem());
        assertEquals(-1, blankRecord.getSerial());
        assertTrue(blankRecord.getId() > 0);
    }

    /**
     * Tests that the constructor stores a valid borrower and item,
     * including a name exactly at the 100-character limit.
     */
    @Test
    public void testConstructor() {
        assertEquals("Sam", record.getBorrower());
        assertSame(book, record.getItem());
        String exactlyMax = "b".repeat(CheckoutRecord.MAX_BORROWER_LENGTH);
        assertEquals(exactlyMax, new CheckoutRecord(exactlyMax, dvd).getBorrower());
    }

    /**
     * Tests that the constructor rejects a null, empty, blank, or
     * over-100-character borrower and a null item, with the spec's messages.
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
    }

    // ----------------------------------------------------------
    // Getters
    // ----------------------------------------------------------

    /**
     * Tests that getBorrower returns the stored name, keeps special
     * characters as plain text, and returns null for a blank record.
     */
    @Test
    public void testGetBorrower() {
        assertEquals("Sam", record.getBorrower());
        // Bad case: special characters are stored as ordinary text
        CheckoutRecord odd = new CheckoutRecord("%s %n \\ <script>", dvd);
        assertEquals("%s %n \\ <script>", odd.getBorrower());
        assertNull(blankRecord.getBorrower());
    }

    /**
     * Tests that getSerial returns the item's serial, and -1 for a
     * blank record with no item.
     */
    @Test
    public void testGetSerial() {
        assertEquals(book.getSerial(), record.getSerial());
        assertEquals(dvd.getSerial(), new CheckoutRecord("Lee", dvd).getSerial());
        // Bad case: a blank record has no item to report
        assertEquals(-1, blankRecord.getSerial());
    }

    /**
     * Tests that getItem returns the same item that was stored, and
     * null for a blank record.
     */
    @Test
    public void testGetItem() {
        assertSame(book, record.getItem());
        assertNull(blankRecord.getItem());
    }

    /**
     * Tests that IDs are assigned in increasing order, are positive,
     * and are never shared between records.
     */
    @Test
    public void testGetId() {
        CheckoutRecord first = new CheckoutRecord("Lee", dvd);
        CheckoutRecord second = new CheckoutRecord("Lee", dvd);
        assertEquals(first.getId() + 1, second.getId());
        // Bad case: IDs are never zero or negative and never reused
        assertTrue(record.getId() > 0);
        assertNotEquals(record.getId(), blankRecord.getId());
    }

    // ----------------------------------------------------------
    // Setters
    // ----------------------------------------------------------

    /**
     * Tests that setBorrower changes the borrower on a filled record
     * and on a blank record.
     */
    @Test
    public void testSetBorrower() {
        record.setBorrower("Alex");
        assertEquals("Alex", record.getBorrower());
        blankRecord.setBorrower("Jordan");
        assertEquals("Jordan", blankRecord.getBorrower());
    }

    /**
     * Tests that setBorrower rejects a null, empty, blank, or
     * over-100-character name and keeps the old borrower.
     */
    @Test
    public void testSetBorrowerBadInput() {
        assertThrows(IllegalArgumentException.class, () -> record.setBorrower(null));
        assertThrows(IllegalArgumentException.class, () -> record.setBorrower(""));
        assertThrows(IllegalArgumentException.class, () -> record.setBorrower(" \t "));
        IllegalArgumentException tooLong = assertThrows(IllegalArgumentException.class,
            () -> record.setBorrower(longName));
        assertEquals("Borrower name is over 100 characters", tooLong.getMessage());
        // The old value survives every rejected change
        assertEquals("Sam", record.getBorrower());
    }

    /**
     * Tests that setItem changes the item and the serial it reports.
     */
    @Test
    public void testSetItem() {
        record.setItem(dvd);
        assertSame(dvd, record.getItem());
        assertEquals(dvd.getSerial(), record.getSerial());
    }

    /**
     * Tests that setItem rejects a null item and keeps the old item.
     */
    @Test
    public void testSetItemBadInput() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> record.setItem(null));
        assertEquals("Item cannot be null", e.getMessage());
        assertSame(book, record.getItem());
    }

    // ----------------------------------------------------------
    // equals, hashCode, toString
    // ----------------------------------------------------------

    /**
     * Tests that records for the same item serial are equal, even with
     * different borrowers, and that a record equals itself.
     */
    @Test
    public void testEquals() {
        CheckoutRecord sameSerial = new CheckoutRecord("Different Person", book);
        assertTrue(record.equals(record));
        assertTrue(record.equals(sameSerial));
        assertTrue(blankRecord.equals(blankRecord));
    }

    /**
     * Tests that a record is not equal to null, another type, a record
     * for a different serial, or a blank record.
     */
    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testEqualsBadInput() {
        assertFalse(record.equals(null));
        assertFalse(record.equals("Sam"));
        assertFalse(record.equals(book));
        assertFalse(record.equals(new CheckoutRecord("Sam", dvd)));
        assertFalse(record.equals(blankRecord));
        assertFalse(blankRecord.equals(record));
        assertFalse(blankRecord.equals(new CheckoutRecord()));
    }

    /**
     * Tests that equal records share a hash code and that a blank
     * record hashes to its ID without throwing.
     */
    @Test
    public void testHashCode() {
        CheckoutRecord sameSerial = new CheckoutRecord("Different Person", book);
        assertEquals(record.hashCode(), sameSerial.hashCode());
        // Bad case: a blank record still hashes without throwing
        assertEquals(blankRecord.getId(), blankRecord.hashCode());
    }

    /**
     * Tests the toString output for a filled record and for a blank
     * record, where missing values print as N/A.
     */
    @Test
    public void testToString() {
        String expected = "Record: " + record.getId() +
            "\nSerial: " + book.getSerial() +
            "\nBorrower: Sam";
        assertEquals(expected, record.toString());

        String expectedBlank = "Record: " + blankRecord.getId() +
            "\nSerial: N/A" +
            "\nBorrower: N/A";
        assertEquals(expectedBlank, blankRecord.toString());
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
        assertThrows(IllegalArgumentException.class, () -> records.addToEnd(null));
        assertEquals(0, records.toArray(new CheckoutRecord[0]).length);
    }
}
