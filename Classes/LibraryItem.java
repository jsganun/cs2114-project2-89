package Classes;

/**
 * LibraryItem
 * Represents an item that can be stored in the library catalogue.
 *
 * Provides common information such as title, creator, description,
 * and serial number for a library item. Whether an item is checked out
 * is not stored here: an item is out if and only if the Catalogue holds
 * a CheckoutRecord with its serial.
 *
 * Validation lives here as a backstop: no item can hold a null, blank,
 * or over-100-character title or creator, or a null description, no
 * matter who calls the constructor or setters.
 *
 * @author Jonathan Ganun
 * @version 10/15/2026
 */
public abstract class LibraryItem
{
    /** The longest title or creator an item will accept. */
    public static final int MAX_TEXT_LENGTH = 100;

    private String title;
    private String creator;
    private int serial;
    private String description;

    /**
     * Creates a new LibraryItem with the specified title, creator,
     * and description. The serial is 0 until a Catalogue assigns one.
     *
     * @param title
     *            the title of the library item
     * @param creator
     *            the creator of the library item
     * @param description
     *            the description of the library item; may be empty
     * @throws IllegalArgumentException if title or creator is null,
     *         blank, or over 100 characters, or if description is null
     */
    public LibraryItem(String title, String creator, String description)
    {
        this.title = checkName(title, "Title");
        this.creator = checkName(creator, "Creator");
        this.description = checkDescription(description);
        this.serial = 0;
    }

    /**
     * Gets the kind of item this is, such as "Book" or "DVD".
     *
     * @return the item type
     */
    public abstract String getType();

    /**
     * Gets the title of this library item.
     *
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Gets the description of this library item.
     *
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Gets the creator of this library item.
     *
     * @return the creator
     */
    public String getCreator()
    {
        return creator;
    }

    /**
     * Gets the serial number assigned to this library item.
     *
     * @return the serial number, or 0 if no Catalogue has assigned one
     */
    public int getSerial()
    {
        return serial;
    }

    /**
     * Sets the title of this library item.
     *
     * @param title - the new title
     * @throws IllegalArgumentException if title is null, blank, or over
     *         100 characters; the old title is kept
     */
    public void setTitle(String title)
    {
        this.title = checkName(title, "Title");
    }

    /**
     * Sets the creator of this library item.
     *
     * @param creator - the new creator
     * @throws IllegalArgumentException if creator is null, blank, or
     *         over 100 characters; the old creator is kept
     */
    public void setCreator(String creator)
    {
        this.creator = checkName(creator, "Creator");
    }

    /**
     * Sets the description of this library item.
     *
     * @param description - the new description; may be empty
     * @throws IllegalArgumentException if description is null; the old
     *         description is kept
     */
    public void setDescription(String description)
    {
        this.description = checkDescription(description);
    }

    /**
     * Tells whether the keyword appears in any of this item's
     * attributes (title, creator, description, type, and the extra
     * descriptors of each subclass), ignoring case. Uses plain String
     * comparison, not a pattern, so special characters are ordinary text.
     *
     * @param keyword the text to look for
     * @return true if any attribute contains the keyword
     * @throws IllegalArgumentException if keyword is null or blank
     */
    public boolean matches(String keyword)
    {
        if (keyword == null || keyword.trim().isEmpty())
        {
            throw new IllegalArgumentException("Search text cannot be blank");
        }
        return searchText().toLowerCase().contains(keyword.trim().toLowerCase());
    }

    /**
     * Gets all the text that a keyword search looks through. Subclasses
     * override this to add their own descriptors.
     *
     * @return the searchable text of this item
     */
    protected String searchText()
    {
        return title + " " + creator + " " + description + " " + getType();
    }

    /**
     * Gets a one-line summary of this item for lists.
     *
     * @return the serial, type, title, and creator on one line
     */
    public String summary()
    {
        return "[" + serial + "] " + getType() + " \"" + title + "\" by "
            + creator;
    }

    /**
     * Assigns this item's serial. Only Catalogue calls this, once, when
     * the item is added.
     *
     * @param newSerial the serial to assign; must be positive
     * @throws IllegalArgumentException if newSerial is not positive
     * @throws IllegalStateException if a serial was already assigned
     */
    void assignSerial(int newSerial)
    {
        if (newSerial <= 0)
        {
            throw new IllegalArgumentException("Serial must be positive");
        }
        if (serial != 0)
        {
            throw new IllegalStateException(
                "Item " + serial + " already has a serial");
        }
        serial = newSerial;
    }

    /**
     * Two items are equal when both have been assigned a serial and the
     * serials match. An item without a serial is only equal to itself.
     *
     * @param object the object to compare against
     * @return true if object is a LibraryItem with the same serial
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LibraryItem)) {
            return false;
        }

        LibraryItem other = (LibraryItem) object;
        return this.serial != 0 && this.serial == other.serial;
    }

    /**
     * Returns a hash code consistent with equals.
     *
     * @return the serial
     */
    @Override
    public int hashCode() {
        return serial;
    }

    @Override
    public String toString() {
        String formattedDescription = description.isEmpty() ? "N/A" : description;

        // Print result
        return
            getType() + " " + serial +
            "\nTitle: " + title +
            "\nCreator: " + creator +
            "\nDescription: " + formattedDescription;
    }

    /**
     * Rejects a String field that is null or blank.
     *
     * @param value the text to check
     * @param field the field name used in the error message
     * @return value, unchanged
     * @throws IllegalArgumentException if value is null or blank
     */
    protected static String checkText(String value, String field)
    {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be blank");
        }
        return value;
    }

    /**
     * Rejects a count that is zero or negative.
     *
     * @param value the count to check
     * @param field the field name used in the error message
     * @return value, unchanged
     * @throws IllegalArgumentException if value is not positive
     */
    protected static int checkPositive(int value, String field)
    {
        if (value <= 0) {
            throw new IllegalArgumentException(field + " must be greater than 0");
        }
        return value;
    }

    /**
     * Rejects a title or creator that is null, blank, or too long.
     *
     * @param value the text to check
     * @param field "Title" or "Creator", used in the error message
     * @return value, unchanged
     * @throws IllegalArgumentException if value is null, blank, or over
     *         100 characters
     */
    private static String checkName(String value, String field)
    {
        checkText(value, field);
        if (value.length() > MAX_TEXT_LENGTH) {
            throw new IllegalArgumentException(field + " is over 100 characters");
        }
        return value;
    }

    /**
     * Rejects a null description. An empty description is allowed.
     *
     * @param value the description to check
     * @return value, unchanged
     * @throws IllegalArgumentException if value is null
     */
    private static String checkDescription(String value)
    {
        if (value == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        return value;
    }
}
