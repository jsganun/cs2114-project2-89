public class DVD extends LibraryItem
{
    private String genre;
    private int numEpisodes;

    public DVD()
    {
        super();
    }

    public DVD(
        String title,
        String creator,
        String description,
        String genre,
        int numEpisodes)
    {
        super(title, creator, description);
        this.genre = genre;
        this.numEpisodes = numEpisodes;
    }

    public String getGenre()
    {
        return genre;
    }

    public int getNumEpisodes()
    {
        return numEpisodes;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public void setNumEpisodes(int numEpisodes)
    {
        this.numEpisodes = numEpisodes;
    }
}