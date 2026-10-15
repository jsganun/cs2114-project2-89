package Tests;
public class GameTest
{
    @BeforeEach
    public void setUp()
    {
        Game game = new Game("The Legend of Zelda: Breath of the Wild", "Nintendo", "An open-world action-adventure game", "Action-Adventure", "Nintendo Switch", "E10+");
    }
    /**
     * Tests the getGenre method.
     */
    @Test
    public void testGetGenre()
    {
        assertEqual("Action-Adventure", game.getGenre());

    }
    /**
     * Tests the getPlatform method.
     */
    @Test
    public void testGetPlatform()
    {
        assertEqual("Nintendo Switch", game.getPlatform());

    }
    /**
     * Tests the getRating method.
     */
    @Test
    public void testGetRating()
    {
        assertEqual("E10+", game.getRating());

    }
    /**
     * Tests the setGenre method.
     */
    @Test
    public void testSetGenre()
    {
        game.setGenre("RPG");
        assertEqual("RPG", game.getGenre());

    }
    /**
     * Tests the setPlatform method.
     */
    @Test
    public void testSetPlatform()
    {
        game.setPlatform("PlayStation 5");
        assertEqual("PlayStation 5", game.getPlatform());
    }
    /**
     * Tests the setRating method.
     */
    @Test
    public void testSetRating() 
    {
        game.setRating("T");
        assertEqual("T", game.getRating());
    }
}