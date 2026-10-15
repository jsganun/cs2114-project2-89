package Tests;
public class MagazineTest
{
    @BeforeEach
    public void setUp()
    {
        Magazine magazine = new Magazine("Time", "Time Inc.", "A weekly news magazine", "News", "Vol. 1", "2023-01-01");
    }
    /**
     * Tests the getGenre method.
     */
    @Test
    public void testGetGenre()
    {
        assertEqual("News", magazine.getGenre());

    }
    /**
     * Tests the getVolume method.
     */
    @Test
    public void testGetVolume()
    {
        assertEqual("Vol. 1", magazine.getVolume());

    }
    /**
     * Tests the getPublicationDate method.
     */
    @Test
    public void testGetPublicationDate()
    {
        assertEqual("2023-01-01", magazine.getPublicationDate());

    }
    /**
     * Tests the setGenre method.
     */
    @Test
    public void testSetGenre()
    {
        magazine.setGenre("Lifestyle");
        assertEqual("Lifestyle", magazine.getGenre());

    }
    /**
     * Tests the setVolume method.
     */
    @Test
    public void testSetVolume()
    {
        magazine.setVolume("Vol. 2");
        assertEqual("Vol. 2", magazine.getVolume());
    }
    /**
     * Tests the setPublicationDate method.
     */
    @Test
    public void testSetPublicationDate()
    {
        magazine.setPublicationDate("2023-01-08");
        assertEqual("2023-01-08", magazine.getPublicationDate());
    }
    
}