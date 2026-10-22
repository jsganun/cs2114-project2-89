package Classes;

/**
 * Book
 * Represents a book that can be checked out. Inherits from LibraryItem.
 *
 * Every descriptor is required: String fields cannot be null or blank,
 * and counts must be greater than 0. A rejected setter keeps the old
 * value.
 *
 * The ISBN is optional (stretch goal): it can be set later and is checked
 * by the ISBN check-digit rule.
 */
public class Book extends LibraryItem
{
    private String genre;
    private String language;
    private String format;
    private int pageCount;
    /** The ISBN with hyphens removed, or empty if none was recorded. */
    private String isbn = "";
    private String publisher;

    /**
     * Creates a new Book.
     *
     * @param title the title of the book
     * @param creator the creator of the book
     * @param description the description of the book; may be empty
     * @param genre the genre
     * @param language the language
     * @param format the format (for example Paperback or eBook)
     * @param pageCount the page count
     * @throws IllegalArgumentException if title or creator is null,
     *         blank, or over 100 characters, if description is null,
     *         if a String descriptor is null or blank, or if a count
     *         is not greater than 0
     */
    public Book(
        String title,
        String creator,
        String description,
        String genre,
        String language,
        String format,
        String publisher,
        int pageCount)
    {
        super(title, creator, description);
        this.genre = checkText(genre, "Genre");
        this.language = checkText(language, "Language");
        this.format = checkText(format, "Format");

        this.pageCount = checkPositive(pageCount, "Page count");
        this.publisher = checkText(publisher, "Publisher");
    }
    /**    (non-Javadoc)
     * @return the publisher of the book
     */
    public String getPublisher() {
        return publisher;
    }
    /**    (non-Javadoc)
     * @param publisher the new publisher of the book
     * @throws IllegalArgumentException if publisher is null or blank;
     *         the old value is kept
     * @see Classes.LibraryItem#getType()
     */
    public void setPublisher(String publisher)
    {
        this.publisher = checkText(publisher, "Publisher");
    }

    /**
     * Returns "Book".
     * @return the item type
     */
    @Override
    public String getType()
    {
        return "Book";
    }

    /**
     * Returns the genre of the book.
     * @return the genre
     */
    public String getGenre()
    {
        return genre;
    }

    /**
     * Returns the language of the book.
     * @return the language
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * Returns the format (for example Paperback or eBook) of the book.
     * @return the format (for example Paperback or eBook)
     */
    public String getFormat()
    {
        return format;
    }

    /**
     * Returns the page count of the book.
     * @return the page count
     */
    public int getPageCount()
    {
        return pageCount;
    }

    /**
     * Sets the genre of the book.
     * @param genre the new genre
     * @throws IllegalArgumentException if genre is null or blank;
     *         the old value is kept
     */
    public void setGenre(String genre)
    {
        this.genre = checkText(genre, "Genre");
    }

    /**
     * Sets the language of the book.
     * @param language the new language
     * @throws IllegalArgumentException if language is null or blank;
     *         the old value is kept
     */
    public void setLanguage(String language)
    {
        this.language = checkText(language, "Language");
    }

    /**
     * Sets the format (for example Paperback or eBook) of the book.
     * @param format the new format (for example Paperback or eBook)
     * @throws IllegalArgumentException if format is null or blank;
     *         the old value is kept
     */
    public void setFormat(String format)
    {
        this.format = checkText(format, "Format");
    }

    /**
     * Sets the page count of the book.
     * @param pageCount the new page count
     * @throws IllegalArgumentException if pageCount is not greater than 0;
     *         the old value is kept
     */
    public void setPageCount(int pageCount)
    {
        this.pageCount = checkPositive(pageCount, "Page count");
    }

    /**
     * Returns the ISBN of the book.
     * @return the ISBN without hyphens, or an empty String if none was
     *         recorded
     */
    public String getIsbn()
    {
        return isbn;
    }

    /**
     * Tells whether an ISBN was recorded for this book.
     * @return true if an ISBN is set
     */
    public boolean hasIsbn()
    {
        return !isbn.isEmpty();
    }

    /**
     * Sets the ISBN of the book after checking its check digit.
     * Hyphens and spaces are ignored.
     * @param isbn the new ISBN, in ISBN-10 or ISBN-13 form
     * @throws IllegalArgumentException if isbn is null or fails the
     *         check-digit rule; the old ISBN is kept
     */
    public void setIsbn(String isbn)
    {
        if (!isValidIsbn(isbn))
        {
            throw new IllegalArgumentException("ISBN is not valid");
        }
        this.isbn = strip(isbn);
    }

    /**
     * Checks an ISBN by its check-digit rule. An ISBN-10 is valid when
     * the digits weighted 10 down to 1 add up to a multiple of 11 (the
     * last character may be X for 10). An ISBN-13 is valid when the
     * digits weighted 1, 3, 1, 3, ... add up to a multiple of 10.
     *
     * @param candidate the text to check; hyphens and spaces are ignored
     * @return true if candidate is a valid ISBN-10 or ISBN-13
     */
    public static boolean isValidIsbn(String candidate)
    {
        if (candidate == null)
        {
            return false;
        }
        String digits = strip(candidate);
        if (digits.length() == 10)
        {
            int sum = 0;
            for (int i = 0; i < 10; i++)
            {
                char c = digits.charAt(i);
                int value;
                if (Character.isDigit(c))
                {
                    value = c - '0';
                }
                else if (i == 9 && c == 'X')
                {
                    value = 10;
                }
                else
                {
                    return false;
                }
                sum += value * (10 - i);
            }
            return sum % 11 == 0;
        }
        if (digits.length() == 13)
        {
            int sum = 0;
            for (int i = 0; i < 13; i++)
            {
                char c = digits.charAt(i);
                if (!Character.isDigit(c))
                {
                    return false;
                }
                sum += (c - '0') * (i % 2 == 0 ? 1 : 3);
            }
            return sum % 10 == 0;
        }
        return false;
    }

    /**
     * Removes hyphens and spaces and upper-cases an ISBN.
     * @param text the text to clean
     * @return the cleaned text
     */
    private static String strip(String text)
    {
        return text.replace("-", "").replace(" ", "").toUpperCase();
    }

    /**
     * Adds this class's descriptors to the text a keyword search looks at.
     * @return the searchable text of this item
     */
    @Override
    protected String searchText()
    {
        return super.searchText() + " " + genre + " " + language + " " + format + " " + pageCount + " " + isbn;
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
        "\nLanguage: " + language +
        "\nFormat: " + format +
        "\nPage count: " + pageCount +
        "\nISBN: " + (hasIsbn() ? isbn : "N/A");
    }
}
