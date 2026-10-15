package Tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

import Classes.Book;

public class BookTest
{
    private Book harryPotter;

    @BeforeEach
    public void setUp()
    {
        harryPotter = new Book("HarryPotter", "J.K. Rowling", "British Kid", "Fiction", "English", "HardCover", 200 );
    }
    @Test
    public void testGetGenre()
    {
        assertEquals("Fiction", harryPotter.getGenre());

    }
    @Test
    public void testGetFormat()
    {
        assertEquals("HardCover", harryPotter.getFormat());

    }
    @Test
    public void testGetLanguage()
    {
        assertEquals("English", harryPotter.getLanguage());

    }
    @Test
    public void testGetPageCount()
    {
        assertEquals(200, harryPotter.getPageCount());

    }
    @Test
    public void testSetGenre()
    {
        harryPotter.setGenre("Non-Fiction");
        assertEquals("Non-Fiction", harryPotter.getGenre());

    }
    @Test
    public void testSetFormat()
    {
        harryPotter.setFormat("SoftCover");
        assertEquals("SoftCover", harryPotter.getFormat());

    }
    @Test
    public void testSetLanguage()
    {
        harryPotter.setLanguage("Spanish");
        assertEquals("Spanish", harryPotter.getLanguage());

    }
    @Test
    public void testSetPageCount()
    {
        harryPotter.setPageCount(300);
        assertEquals(300, harryPotter.getPageCount());

    }   
    
}