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

    public String getGenre()
    {
        return genre;
    }

    public String getLanguage()
    {
        return language;
    }

    public String getFormat()
    {
        return format;
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }
}