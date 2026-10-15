package Tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

import Classes.Game;

public class GameTest
{
    private Game game;

    @BeforeEach
    public void setUp()
    {
        game = new Game("The Legend of Zelda: Breath of the Wild", "Nintendo", "An open-world action-adventure game", "Action-Adventure", "Nintendo Switch", "E10+");
    }
    /**
     * Tests the getGenre method.
     */
    @Test
    public void testGetGenre()
    {
        assertEquals("Action-Adventure", game.getGenre());

    }
    /**
     * Tests the getPlatform method.
     */
    @Test
    public void testGetPlatform()
    {
        assertEquals("Nintendo Switch", game.getPlatform());

    }
    /**
     * Tests the getRating method.
     */
    @Test
    public void testGetRating()
    {
        assertEquals("E10+", game.getRating());

    }
    /**
     * Tests the setGenre method.
     */
    @Test
    public void testSetGenre()
    {
        game.setGenre("RPG");
        assertEquals("RPG", game.getGenre());

    }
    /**
     * Tests the setPlatform method.
     */
    @Test
    public void testSetPlatform()
    {
        game.setPlatform("PlayStation 5");
        assertEquals("PlayStation 5", game.getPlatform());
    }
    /**
     * Tests the setRating method.
     */
    @Test
    public void testSetRating() 
    {
        game.setRating("T");
        assertEquals("T", game.getRating());
    }
}