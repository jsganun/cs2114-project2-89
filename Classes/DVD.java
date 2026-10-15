package Classes;

/**
 * DVD
 * Represents a DVD that can be checked out. Inherits from LibraryItem.
 *
 * Every descriptor is required: String fields cannot be null or blank,
 * and counts must be greater than 0. A rejected setter keeps the old
 * value.
 */
public class DVD extends LibraryItem
{
    private String genre;
    private int numEpisodes;

    /**
     * Creates a new DVD.
     *
     * @param title the title of the DVD
     * @param creator the creator of the DVD
     * @param description the description of the DVD; may be empty
     * @param genre the genre
     * @param numEpisodes the number of episodes or distinct parts
     * @throws IllegalArgumentException if title or creator is null,
     *         blank, or over 100 characters, if description is null,
     *         if a String descriptor is null or blank, or if a count
     *         is not greater than 0
     */
    public DVD(
        String title,
        String creator,
        String description,
        String genre,
        int numEpisodes)
    {
        super(title, creator, description);
        this.genre = checkText(genre, "Genre");
        this.numEpisodes = checkPositive(numEpisodes, "Number of episodes");
    }

    /**
     * Returns "DVD".
     * @return the item type
     */
    @Override
    public String getType()
    {
        return "DVD";
    }

    /**
     * Returns the genre of the DVD.
     * @return the genre
     */
    public String getGenre()
    {
        return genre;
    }

    /**
     * Returns the number of episodes or distinct parts of the DVD.
     * @return the number of episodes or distinct parts
     */
    public int getNumEpisodes()
    {
        return numEpisodes;
    }

    /**
     * Sets the genre of the DVD.
     * @param genre the new genre
     * @throws IllegalArgumentException if genre is null or blank;
     *         the old value is kept
     */
    public void setGenre(String genre)
    {
        this.genre = checkText(genre, "Genre");
    }

    /**
     * Sets the number of episodes or distinct parts of the DVD.
     * @param numEpisodes the new number of episodes or distinct parts
     * @throws IllegalArgumentException if numEpisodes is not greater than 0;
     *         the old value is kept
     */
    public void setNumEpisodes(int numEpisodes)
    {
        this.numEpisodes = checkPositive(numEpisodes, "Number of episodes");
    }

    /**
     * Adds this class's descriptors to the text a keyword search looks at.
     * @return the searchable text of this item
     */
    @Override
    protected String searchText()
    {
        return super.searchText() + " " + genre + " " + numEpisodes;
    }

    /**
     * Describes this item with all of its descriptors.
     * @return a multi-line description
     */
    @Override
    public String toString()
    {
        return super.toString() +
        "\nGenre: " + genre +
        "\nNumber of episodes: " + numEpisodes;
    }
}
