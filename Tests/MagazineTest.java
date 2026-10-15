package Tests;

import org.junit.jupiter.api.Test;
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

}