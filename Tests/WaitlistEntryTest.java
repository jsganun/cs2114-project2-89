package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Classes.WaitlistEntry;

/**
 * WaitlistEntryTest
 * Test class for WaitlistEntry (stretch goal: waitlist). Each public
 * method has a normal case and a bad-input case.
 *
 * @author Group 89
 * @version 10/15/2026
 */
public class WaitlistEntryTest {

    private WaitlistEntry entry;

    /**
     * Creates a fresh entry before each test.
     */
    @BeforeEach
    public void setUp() {
        entry = new WaitlistEntry(4, "Sam");
    }

    /** The constructor stores the serial and borrower. */
    @Test
    public void testConstructor() {
        assertEquals(4, entry.getSerial());
        assertEquals("Sam", entry.getBorrower());
        assertEquals("a".repeat(100), new WaitlistEntry(1, "a".repeat(100)).getBorrower());
    }

    /** The constructor rejects a non-positive serial and a bad borrower. */
    @Test
    public void testConstructorBadInput() {
        assertThrows(IllegalArgumentException.class, () -> new WaitlistEntry(0, "Sam"));
        assertThrows(IllegalArgumentException.class, () -> new WaitlistEntry(-3, "Sam"));
        assertThrows(IllegalArgumentException.class, () -> new WaitlistEntry(1, null));
        assertThrows(IllegalArgumentException.class, () -> new WaitlistEntry(1, "  "));
        assertThrows(IllegalArgumentException.class,
            () -> new WaitlistEntry(1, "a".repeat(101)));
    }

    /** getSerial returns the serial. */
    @Test
    public void testGetSerial() {
        assertEquals(4, entry.getSerial());
    }

    /** getBorrower returns the name, special characters included. */
    @Test
    public void testGetBorrower() {
        assertEquals("%s %n \\ <script>", new WaitlistEntry(2, "%s %n \\ <script>").getBorrower());
    }

    /** Entries are equal when serial and borrower both match. */
    @Test
    public void testEquals() {
        assertTrue(entry.equals(entry));
        assertTrue(entry.equals(new WaitlistEntry(4, "Sam")));
        assertEquals(entry.hashCode(), new WaitlistEntry(4, "Sam").hashCode());
    }

    /** Entries differ for another serial, another borrower, null, or type. */
    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testEqualsBadInput() {
        assertFalse(entry.equals(new WaitlistEntry(5, "Sam")));
        assertFalse(entry.equals(new WaitlistEntry(4, "Lee")));
        assertFalse(entry.equals(null));
        assertFalse(entry.equals("Sam"));
    }

    /** hashCode is consistent with equals. */
    @Test
    public void testHashCode() {
        assertEquals(entry.hashCode(), new WaitlistEntry(4, "Sam").hashCode());
    }

    /** toString names the borrower and the item. */
    @Test
    public void testToString() {
        assertEquals("Sam is waiting for item 4.", entry.toString());
    }
}
