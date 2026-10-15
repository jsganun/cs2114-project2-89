package Tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import Classes.Magazine;
public class MagazineTest
{
    private Magazine magazine;

    @BeforeEach
    public void setUp()
    {
        magazine = new Magazine("Time", "Time Inc.", "A weekly news magazine", "News", "Vol. 1", "2023-01-01");
    }
    /**
     * Tests the getGenre method.
     */
    @Test
    public void testGetGenre()
    {
        assertEquals("News", magazine.getGenre());

    }
    /**
     * Tests the getVolume method.
     */
    @Test
    public void testGetVolume()
    {
        assertEquals("Vol. 1", magazine.getVolume());

    }
    /**
     * Tests the getPublicationDate method.
     */
    @Test
    public void testGetPublicationDate()
    {
        assertEquals("2023-01-01", magazine.getPublicationDate());

    }
    /**
     * Tests the setGenre method.
     */
    @Test
    public void testSetGenre()
    {
        magazine.setGenre("Lifestyle");
        assertEquals("Lifestyle", magazine.getGenre());

    }
    /**
     * Tests the setVolume method.
     */
    @Test
    public void testSetVolume()
    {
        magazine.setVolume("Vol. 2");
        assertEquals("Vol. 2", magazine.getVolume());
    }
    /**
     * Tests the setPublicationDate method.
     */
    @Test
    public void testSetPublicationDate()
    {
        magazine.setPublicationDate("2023-01-08");
        assertEquals("2023-01-08", magazine.getPublicationDate());
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
        assertEquals("Time", magazine.getTitle());
        assertEquals("News", magazine.getGenre());
        assertEquals("Vol. 1", magazine.getVolume());
        assertEquals("2023-01-01", magazine.getPublicationDate());
        assertEquals(0, magazine.getSerial());
    }

    /**
     * Tests that the constructor rejects a blank title, a blank or null
     * String descriptor, and a count that is not greater than 0.
     */
    @Test
    public void testConstructorBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> new Magazine("  ", "Creator", "D", "News", "Vol. 1", "2023-01-01"));
        assertThrows(IllegalArgumentException.class, () -> new Magazine("T", null, "D", "News", "Vol. 1", "2023-01-01"));
        assertThrows(IllegalArgumentException.class, () -> new Magazine("T", "C", null, "News", "Vol. 1", "2023-01-01"));
        assertThrows(IllegalArgumentException.class, () -> new Magazine("a".repeat(101), "C", "D", "News", "Vol. 1", "2023-01-01"));
        assertThrows(IllegalArgumentException.class, () -> new Magazine("T", "C", "D", null, "Vol. 1", "2023-01-01"));
        assertThrows(IllegalArgumentException.class, () -> new Magazine("T", "C", "D", "   ", "Vol. 1", "2023-01-01"));
        assertThrows(IllegalArgumentException.class, () -> new Magazine("T", "C", "D", "News", null, "2023-01-01"));
        assertThrows(IllegalArgumentException.class, () -> new Magazine("T", "C", "D", "News", "   ", "2023-01-01"));
        assertThrows(IllegalArgumentException.class, () -> new Magazine("T", "C", "D", "News", "Vol. 1", null));
        assertThrows(IllegalArgumentException.class, () -> new Magazine("T", "C", "D", "News", "Vol. 1", "   "));
    }

    /**
     * Tests that setGenre rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetGenreBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> magazine.setGenre(null));
        assertThrows(IllegalArgumentException.class, () -> magazine.setGenre("   "));
        assertEquals("News", magazine.getGenre());
    }

    /**
     * Tests that setVolume rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetVolumeBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> magazine.setVolume(null));
        assertThrows(IllegalArgumentException.class, () -> magazine.setVolume("   "));
        assertEquals("Vol. 1", magazine.getVolume());
    }

    /**
     * Tests that setPublicationDate rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetPublicationDateBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> magazine.setPublicationDate(null));
        assertThrows(IllegalArgumentException.class, () -> magazine.setPublicationDate("   "));
        assertEquals("2023-01-01", magazine.getPublicationDate());
    }

    /**
     * Tests getType.
     */
    @Test
    public void testGetType()
    {
        assertEquals("Magazine", magazine.getType());
    }

    /**
     * Tests that toString lists the common fields and every descriptor.
     */
    @Test
    public void testToString()
    {
        assertEquals("Magazine 0\nTitle: Time\nCreator: Time Inc.\nDescription: A weekly news magazine"
            + "\nGenre: News\nVolume: Vol. 1\nPublication date: 2023-01-01"
            + "\nDate of issue: N/A",
            magazine.toString());
    }

    /**
     * Tests that matches finds a keyword in this class's own descriptors.
     */
    @Test
    public void testMatches()
    {
        assertTrue(magazine.matches("news"));
        assertTrue(magazine.matches("time inc"));
        assertTrue(magazine.matches("vol. 1"));
        assertFalse(magazine.matches("zebra"));
    }

    /**
     * Tests that matches rejects null and blank keywords.
     */
    @Test
    public void testMatchesBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> magazine.matches(null));
        assertThrows(IllegalArgumentException.class, () -> magazine.matches("  "));
    }

    // ----------------------------------------------------------
    // Date of issue (stretch goal: validity of dates)
    // ----------------------------------------------------------

    /**
     * Tests that a real date is accepted, including a leap day, and that
     * it shows in toString and in searches.
     */
    @Test
    public void testSetDateOfIssue()
    {
        assertNull(magazine.getDateOfIssue());
        magazine.setDateOfIssue("2024-03-01");
        assertEquals(java.time.LocalDate.of(2024, 3, 1), magazine.getDateOfIssue());
        magazine.setDateOfIssue("2024-02-29");
        assertEquals(java.time.LocalDate.of(2024, 2, 29), magazine.getDateOfIssue());
        assertTrue(magazine.toString().endsWith("\nDate of issue: 2024-02-29"));
        assertTrue(magazine.matches("2024-02-29"));
    }

    /**
     * Tests that impossible dates, wrong formats, empty text, and null are
     * rejected, and that the old date is kept.
     */
    @Test
    public void testSetDateOfIssueBadInput()
    {
        magazine.setDateOfIssue("2024-03-01");
        assertThrows(IllegalArgumentException.class, () -> magazine.setDateOfIssue("2023-02-29"));
        assertThrows(IllegalArgumentException.class, () -> magazine.setDateOfIssue("2024-13-01"));
        assertThrows(IllegalArgumentException.class, () -> magazine.setDateOfIssue("2024-04-31"));
        assertThrows(IllegalArgumentException.class, () -> magazine.setDateOfIssue("March 2024"));
        assertThrows(IllegalArgumentException.class, () -> magazine.setDateOfIssue("2024-3-1"));
        assertThrows(IllegalArgumentException.class, () -> magazine.setDateOfIssue(""));
        assertThrows(IllegalArgumentException.class, () -> magazine.setDateOfIssue(null));
        assertEquals(java.time.LocalDate.of(2024, 3, 1), magazine.getDateOfIssue());
    }

    /**
     * Tests isValidDate on real and impossible dates.
     */
    @Test
    public void testIsValidDate()
    {
        assertTrue(Magazine.isValidDate("2000-02-29"));
        assertFalse(Magazine.isValidDate("1900-02-29"));
        assertFalse(Magazine.isValidDate(null));
    }
}
