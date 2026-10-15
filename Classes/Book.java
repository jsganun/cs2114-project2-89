package Classes;
public class Book extends LibraryItem
{
    private String genre;
    private String language;
    private String format;
    private int pageCount;

    public Book()
    {
        super();
    }
    /**
     * 
     */
    public Book(
        String title,
        String creator,
        String description,
        String genre,
        String language,
        String format,
        int pageCount)
    {
        super(title, creator, description);
        this.genre = genre;
        this.language = language;
        this.format = format;
        this.pageCount = pageCount;
    }
    /**
     * Returns the genre of the book.
     * @return the genre of the book
     */
    public String getGenre()
    {
        return genre;
    }

    /**
     * Returns the language of the book.
     * @return the language of the book
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * Returns the format of the book.
     * @return the format of the book
     */
    public String getFormat()
    {
        return format;
    }

    /**
     * Returns the page count of the book.
     * @return the page count of the book
     */
    public int getPageCount()
    {
        return pageCount;
    }

    /**
     * Sets the genre of the book.
     * @param genre the genre of the book
     */
    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    /**
     * Sets the language of the book.
     * @param language the language of the book
     */
    public void setLanguage(String language)
    {
        this.language = language;
    }

    /**
     * Sets the format of the book.
     * @param format the format of the book
     */
    public void setFormat(String format)
    {
        this.format = format;
    }

    /**
     * Sets the page count of the book.
     * @param pageCount the page count of the book
     */
    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }
}