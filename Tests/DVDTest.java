package Tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import Classes.DVD;
public class DVDTest
{
    private DVD dvd;

    @BeforeEach
    public void setUp()
    {
        dvd = new DVD("Inception", "Christopher Nolan", "A mind-bending thriller", "Science Fiction", 148);
    }

    /**
    * Tests the getGenre method.
    */
    @Test
    public void testGetGenre()
    {
        assertEquals("Science Fiction", dvd.getGenre());

    }

    /*
    * Tests the getNumEpisodes method.
        */
    @Test
    public void testGetNumEpisodes()
    {
        assertEquals(148, dvd.getNumEpisodes());

    }

    /**
     * Tests the setGenre method.
     */
    @Test
    public void testSetGenre()
    {
        dvd.setGenre("Action");
        assertEquals("Action", dvd.getGenre());

    }
    
    /**
     * Tests the setNumEpisodes method.
     */
    @Test
    public void testSetNumEpisodes()
    {
        dvd.setNumEpisodes(5);
        assertEquals(5, dvd.getNumEpisodes());
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
        assertEquals("Inception", dvd.getTitle());
        assertEquals("Science Fiction", dvd.getGenre());
        assertEquals(148, dvd.getNumEpisodes());
        assertEquals(0, dvd.getSerial());
    }

    /**
     * Tests that the constructor rejects a blank title, a blank or null
     * String descriptor, and a count that is not greater than 0.
     */
    @Test
    public void testConstructorBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> new DVD("  ", "Creator", "D", "Science Fiction", 148));
        assertThrows(IllegalArgumentException.class, () -> new DVD("T", null, "D", "Science Fiction", 148));
        assertThrows(IllegalArgumentException.class, () -> new DVD("T", "C", null, "Science Fiction", 148));
        assertThrows(IllegalArgumentException.class, () -> new DVD("a".repeat(101), "C", "D", "Science Fiction", 148));
        assertThrows(IllegalArgumentException.class, () -> new DVD("T", "C", "D", null, 148));
        assertThrows(IllegalArgumentException.class, () -> new DVD("T", "C", "D", "   ", 148));
        assertThrows(IllegalArgumentException.class, () -> new DVD("T", "C", "D", "Science Fiction", 0));
        assertThrows(IllegalArgumentException.class, () -> new DVD("T", "C", "D", "Science Fiction", -5));
    }

    /**
     * Tests that setGenre rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetGenreBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> dvd.setGenre(null));
        assertThrows(IllegalArgumentException.class, () -> dvd.setGenre("   "));
        assertEquals("Science Fiction", dvd.getGenre());
    }

    /**
     * Tests that setNumEpisodes rejects 0 and negative numbers and keeps the old value.
     */
    @Test
    public void testSetNumEpisodesBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> dvd.setNumEpisodes(0));
        assertThrows(IllegalArgumentException.class, () -> dvd.setNumEpisodes(-5));
        assertEquals(148, dvd.getNumEpisodes());
    }

    /**
     * Tests getType.
     */
    @Test
    public void testGetType()
    {
        assertEquals("DVD", dvd.getType());
    }

    /**
     * Tests that toString lists the common fields and every descriptor.
     */
    @Test
    public void testToString()
    {
        assertEquals("DVD 0\nTitle: Inception\nCreator: Christopher Nolan"
            + "\nDescription: A mind-bending thriller\nGenre: Science Fiction"
            + "\nNumber of episodes: 148",
            dvd.toString());
    }

    /**
     * Tests that matches finds a keyword in this class's own descriptors.
     */
    @Test
    public void testMatches()
    {
        assertTrue(dvd.matches("science fiction"));
        assertTrue(dvd.matches("nolan"));
        assertTrue(dvd.matches("mind-bending"));
        assertFalse(dvd.matches("zebra"));
    }

    /**
     * Tests that matches rejects null and blank keywords.
     */
    @Test
    public void testMatchesBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> dvd.matches(null));
        assertThrows(IllegalArgumentException.class, () -> dvd.matches("  "));
    }
}
