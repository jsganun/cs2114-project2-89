package Tests;

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
    public void testGetAuthor()
    {
        assertEqual("Fiction", harryPotter.getGenre());

    }
    @Test
    public void testGetGenre()
    {
        assertEqual("Fiction", harryPotter.getGenre());

    }
}