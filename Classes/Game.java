package Classes;

/**
 * Game
 * Represents a game that can be checked out. Inherits from LibraryItem.
 *
 * Every descriptor is required: String fields cannot be null or blank,
 * and counts must be greater than 0. A rejected setter keeps the old
 * value.
 */
public class Game extends LibraryItem
{
    private String genre;
    private String platform;
    private String rating;

    /**
     * Creates a new Game.
     *
     * @param title the title of the game
     * @param creator the creator of the game
     * @param description the description of the game; may be empty
     * @param genre the genre
     * @param platform the platform
     * @param rating the rating or age restriction
     * @throws IllegalArgumentException if title or creator is null,
     *         blank, or over 100 characters, if description is null,
     *         if a String descriptor is null or blank, or if a count
     *         is not greater than 0
     */
    public Game(
        String title,
        String creator,
        String description,
        String genre,
        String platform,
        String rating)
    {
        super(title, creator, description);
        this.genre = checkText(genre, "Genre");
        this.platform = checkText(platform, "Platform");
        this.rating = checkText(rating, "Rating");
    }

    /**
     * Returns "Game".
     * @return the item type
     */
    @Override
    public String getType()
    {
        return "Game";
    }

    /**
     * Returns the genre of the game.
     * @return the genre
     */
    public String getGenre()
    {
        return genre;
    }

    /**
     * Returns the platform of the game.
     * @return the platform
     */
    public String getPlatform()
    {
        return platform;
    }

    /**
     * Returns the rating or age restriction of the game.
     * @return the rating or age restriction
     */
    public String getRating()
    {
        return rating;
    }

    /**
     * Sets the genre of the game.
     * @param genre the new genre
     * @throws IllegalArgumentException if genre is null or blank;
     *         the old value is kept
     */
    public void setGenre(String genre)
    {
        this.genre = checkText(genre, "Genre");
    }

    /**
     * Sets the platform of the game.
     * @param platform the new platform
     * @throws IllegalArgumentException if platform is null or blank;
     *         the old value is kept
     */
    public void setPlatform(String platform)
    {
        this.platform = checkText(platform, "Platform");
    }

    /**
     * Sets the rating or age restriction of the game.
     * @param rating the new rating or age restriction
     * @throws IllegalArgumentException if rating is null or blank;
     *         the old value is kept
     */
    public void setRating(String rating)
    {
        this.rating = checkText(rating, "Rating");
    }

    /**
     * Adds this class's descriptors to the text a keyword search looks at.
     * @return the searchable text of this item
     */
    @Override
    protected String searchText()
    {
        return super.searchText() + " " + genre + " " + platform + " " + rating;
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
        "\nPlatform: " + platform +
        "\nRating: " + rating;
    }
}
