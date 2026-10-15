package Classes;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Magazine
 * Represents a magazine that can be checked out. Inherits from LibraryItem.
 *
 * Every descriptor is required: String fields cannot be null or blank,
 * and counts must be greater than 0. A rejected setter keeps the old
 * value.
 *
 * The exact date of issue is optional (stretch goal): it can be set later
 * and must be a real calendar date.
 */
public class Magazine extends LibraryItem
{
    private String genre;
    private String volume;
    private String publicationDate;
    /** The exact date of issue, or null if none was recorded. */
    private LocalDate dateOfIssue;

    /**
     * Creates a new Magazine.
     *
     * @param title the title of the magazine
     * @param creator the creator of the magazine
     * @param description the description of the magazine; may be empty
     * @param genre the genre
     * @param volume the volume or issue number
     * @param publicationDate the publication date as printed on the cover
     * @throws IllegalArgumentException if title or creator is null,
     *         blank, or over 100 characters, if description is null,
     *         if a String descriptor is null or blank, or if a count
     *         is not greater than 0
     */
    public Magazine(
        String title,
        String creator,
        String description,
        String genre,
        String volume,
        String publicationDate)
    {
        super(title, creator, description);
        this.genre = checkText(genre, "Genre");
        this.volume = checkText(volume, "Volume");
        this.publicationDate = checkText(publicationDate, "Publication date");
    }

    /**
     * Returns "Magazine".
     * @return the item type
     */
    @Override
    public String getType()
    {
        return "Magazine";
    }

    /**
     * Returns the genre of the magazine.
     * @return the genre
     */
    public String getGenre()
    {
        return genre;
    }

    /**
     * Returns the volume or issue number of the magazine.
     * @return the volume or issue number
     */
    public String getVolume()
    {
        return volume;
    }

    /**
     * Returns the publication date as printed on the cover of the magazine.
     * @return the publication date as printed on the cover
     */
    public String getPublicationDate()
    {
        return publicationDate;
    }

    /**
     * Sets the genre of the magazine.
     * @param genre the new genre
     * @throws IllegalArgumentException if genre is null or blank;
     *         the old value is kept
     */
    public void setGenre(String genre)
    {
        this.genre = checkText(genre, "Genre");
    }

    /**
     * Sets the volume or issue number of the magazine.
     * @param volume the new volume or issue number
     * @throws IllegalArgumentException if volume is null or blank;
     *         the old value is kept
     */
    public void setVolume(String volume)
    {
        this.volume = checkText(volume, "Volume");
    }

    /**
     * Sets the publication date as printed on the cover of the magazine.
     * @param publicationDate the new publication date as printed on the cover
     * @throws IllegalArgumentException if publicationDate is null or blank;
     *         the old value is kept
     */
    public void setPublicationDate(String publicationDate)
    {
        this.publicationDate = checkText(publicationDate, "Publication date");
    }

    /**
     * Returns the exact date of issue of the magazine.
     * @return the date of issue, or null if none was recorded
     */
    public LocalDate getDateOfIssue()
    {
        return dateOfIssue;
    }

    /**
     * Sets the exact date of issue after checking that it is a real
     * calendar date.
     * @param text the date in yyyy-MM-dd form, such as 2024-03-01
     * @throws IllegalArgumentException if text is null or is not a real
     *         date (for example 2023-02-30); the old date is kept
     */
    public void setDateOfIssue(String text)
    {
        if (!isValidDate(text))
        {
            throw new IllegalArgumentException(
                "Date of issue is not a valid date (use yyyy-MM-dd)");
        }
        this.dateOfIssue = LocalDate.parse(text.trim());
    }

    /**
     * Checks that text is a real calendar date in yyyy-MM-dd form.
     * @param text the text to check
     * @return true if text parses to a real date
     */
    public static boolean isValidDate(String text)
    {
        if (text == null)
        {
            return false;
        }
        try
        {
            LocalDate.parse(text.trim());
            return true;
        }
        catch (DateTimeParseException e)
        {
            return false;
        }
    }

    /**
     * Adds this class's descriptors to the text a keyword search looks at.
     * @return the searchable text of this item
     */
    @Override
    protected String searchText()
    {
        return super.searchText() + " " + genre + " " + volume + " " + publicationDate + " " + (dateOfIssue == null ? "" : dateOfIssue);
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
        "\nVolume: " + volume +
        "\nPublication date: " + publicationDate +
        "\nDate of issue: " + (dateOfIssue == null ? "N/A" : dateOfIssue);
    }
}
