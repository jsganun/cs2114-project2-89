public class Magazine extends LibraryItem
{
    private String genre;
    private String volume;
    private String publicationDate;

    public Magazine()
    {
        super();
    }

    public Magazine(
        String title,
        String creator,
        String genre,
        String volume,
        String publicationDate)
    {
        super(title, creator);
        this.genre = genre;
        this.volume = volume;
        this.publicationDate = publicationDate;
    }

    public String getGenre()
    {
        return genre;
    }

    public String getVolume()
    {
        return volume;
    }

    public String getPublicationDate()
    {
        return publicationDate;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public void setVolume(String volume)
    {
        this.volume = volume;
    }

    public void setPublicationDate(String publicationDate)
    {
        this.publicationDate = publicationDate;
    }
}