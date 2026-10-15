package Tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertEquals("The Legend of Zelda: Breath of the Wild", game.getTitle());
        assertEquals("Action-Adventure", game.getGenre());
        assertEquals("Nintendo Switch", game.getPlatform());
        assertEquals("E10+", game.getRating());
        assertEquals(0, game.getSerial());
    }

    /**
     * Tests that the constructor rejects a blank title, a blank or null
     * String descriptor, and a count that is not greater than 0.
     */
    @Test
    public void testConstructorBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> new Game("  ", "Creator", "D", "Action-Adventure", "Nintendo Switch", "E10+"));
        assertThrows(IllegalArgumentException.class, () -> new Game("T", null, "D", "Action-Adventure", "Nintendo Switch", "E10+"));
        assertThrows(IllegalArgumentException.class, () -> new Game("T", "C", null, "Action-Adventure", "Nintendo Switch", "E10+"));
        assertThrows(IllegalArgumentException.class, () -> new Game("a".repeat(101), "C", "D", "Action-Adventure", "Nintendo Switch", "E10+"));
        assertThrows(IllegalArgumentException.class, () -> new Game("T", "C", "D", null, "Nintendo Switch", "E10+"));
        assertThrows(IllegalArgumentException.class, () -> new Game("T", "C", "D", "   ", "Nintendo Switch", "E10+"));
        assertThrows(IllegalArgumentException.class, () -> new Game("T", "C", "D", "Action-Adventure", null, "E10+"));
        assertThrows(IllegalArgumentException.class, () -> new Game("T", "C", "D", "Action-Adventure", "   ", "E10+"));
        assertThrows(IllegalArgumentException.class, () -> new Game("T", "C", "D", "Action-Adventure", "Nintendo Switch", null));
        assertThrows(IllegalArgumentException.class, () -> new Game("T", "C", "D", "Action-Adventure", "Nintendo Switch", "   "));
    }

    /**
     * Tests that setGenre rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetGenreBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> game.setGenre(null));
        assertThrows(IllegalArgumentException.class, () -> game.setGenre("   "));
        assertEquals("Action-Adventure", game.getGenre());
    }

    /**
     * Tests that setPlatform rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetPlatformBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> game.setPlatform(null));
        assertThrows(IllegalArgumentException.class, () -> game.setPlatform("   "));
        assertEquals("Nintendo Switch", game.getPlatform());
    }

    /**
     * Tests that setRating rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetRatingBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> game.setRating(null));
        assertThrows(IllegalArgumentException.class, () -> game.setRating("   "));
        assertEquals("E10+", game.getRating());
    }

    /**
     * Tests getType.
     */
    @Test
    public void testGetType()
    {
        assertEquals("Game", game.getType());
    }

    /**
     * Tests that toString lists the common fields and every descriptor.
     */
    @Test
    public void testToString()
    {
        assertEquals("Game 0\nTitle: The Legend of Zelda: Breath of the Wild\nCreator: Nintendo"
            + "\nDescription: An open-world action-adventure game"
            + "\nGenre: Action-Adventure\nPlatform: Nintendo Switch\nRating: E10+",
            game.toString());
    }

    /**
     * Tests that matches finds a keyword in this class's own descriptors.
     */
    @Test
    public void testMatches()
    {
        assertTrue(game.matches("switch"));
        assertTrue(game.matches("zelda"));
        assertTrue(game.matches("e10+"));
        assertFalse(game.matches("zebra"));
    }

    /**
     * Tests that matches rejects null and blank keywords.
     */
    @Test
    public void testMatchesBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> game.matches(null));
        assertThrows(IllegalArgumentException.class, () -> game.matches("  "));
    }
}
