package Classes;
public class Game extends LibraryItem
{
    private String genre;
    private String platform;
    private String rating;

    public Game()
    {
        super();
    }

    public Game(
        String title,
        String creator,
        String description,
        String genre,
        String platform,
        String rating)
    {
        super(title, creator, description);
        this.genre = genre;
        this.platform = platform;
        this.rating = rating;
    }

    public String getGenre()
    {
        return genre;
    }

    public String getPlatform()
    {
        return platform;
    }

    public String getRating()
    {
        return rating;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }
}