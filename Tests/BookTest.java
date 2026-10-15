package Tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

import Classes.Book;

public class BookTest
{
    @BeforeEach
    public void setUp()
    {
        Book harryPotter = new Book("HarryPotter", "J.K. Rowling", "Fiction", "English", "HardCover", 200 );
    }
    @Test
    public void testGetGenre()
    {
        assertEqual("Fiction", harryPotter.getGenre());

    }
    @Test
    public void testGetFormat()
    {
        assertEqual("HardCover", harryPotter.getFormat());

    }
    @Test
    public void testGetLanguage()
    {
        assertEqual("English", harryPotter.getLanguage());

    }
    @Test
    public void testGetPageCount()
    {
        assertEqual(200, harryPotter.getPageCount());

    }
    @Test
    public void testSetGenre()
    {
        harryPotter.setGenre("Non-Fiction");
        assertEqual("Non-Fiction", harryPotter.getGenre());

    }
    @Test
    public void testSetFormat()
    {
        harryPotter.setFormat("SoftCover");
        assertEqual("SoftCover", harryPotter.getFormat());

    }
    @Test
    public void testSetLanguage()
    {
        harryPotter.setLanguage("Spanish");
        assertEqual("Spanish", harryPotter.getLanguage());

    }
    @Test
    public void testSetPageCount()
    {
        harryPotter.setPageCount(300);
        assertEqual(300, harryPotter.getPageCount());

    }   
    
}