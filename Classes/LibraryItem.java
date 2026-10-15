package Classes;

/**
 * LibraryItem
 * Represents an item that can be stored in the library catalogue.
 * 
 * Provides common information such as title, creator, description,
 * serial number, and checkout status for a library item.
 * 
 * @author Jonathan Ganun
 * @version 10/15/2026
 */
public class LibraryItem
{
    private String title;
    private String creator;
    private int serial;
    private String description;
    private CheckoutRecord record;

    private static int nextSerial = 0;

    /**
     * Creates a new LibraryItem with empty title, creator, and description values.
     * Assigns a unique serial number to the item.
     */
    public LibraryItem()
    {
        this.serial = nextSerial;
        nextSerial++;
    }

    /**
     * Creates a new LibraryItem with the specified title, creator,
     * and description.
     *
     * @param title
     *            the title of the library item
     * @param creator
     *            the creator of the library item
     * @param description
     *            the description of the library item
     */
    public LibraryItem(String title, String creator, String description)
    {
        this();
        this.title = title;
        this.creator = creator;
        this.description = description;
    }

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
     * Gets the checkout record associated with this library item.
     *
     * @return the checkout record, or null if the item is not checked out
     */
    public CheckoutRecord getRecord()
    {
        return record;
    }

    /**
     * Gets the serial number assigned to this library item.
     *
     * @return the serial number
     */
    public int getSerial()
    {
        return serial;
    }

    /**
     * Sets the title of this library item.
     *
     * @param title - the new title
     * @throws IllegalArgumentException if title is null
     */
    public void setTitle(String title)
    {
        checkForNull(title, "Title cannot be null.");
        this.title = title;
    }

    /**
     * Sets the creator of this library item.
     *
     * @param creator - the new creator
     * @throws IllegalArgumentException if creator is null
     */
    public void setCreator(String creator)
    {
        checkForNull(creator, "Creator cannot be null.");
        this.creator = creator;
    }

    /**
     * Sets the description of this library item.
     *
     * @param description - the new description
     * @throws IllegalArgumentException if description is null
     */
    public void setDescription(String description)
    {
        checkForNull(description, "Description cannot be null.");
        this.description = description;
    }

    /**
     * Sets the checkout record associated with this library item.
     *
     * @param record - the checkout record for this item
     */
    public void setRecord(CheckoutRecord record)
    {
        this.record = record;
    }

    @Override 
    public boolean equals(Object object) {
        if (object == null || !(object instanceof LibraryItem)) {
            return false;
        }

        LibraryItem other = (LibraryItem) object;
        return this.serial == other.serial;
    }

    @Override
    public String toString() {

        // Replace nulls with N/A
        String formattedTile = (title != null) ? title : "N/A";
        String formattedCreator = (creator != null) ? creator : "N/A";
        String formattedDescription = (description != null) ? description : "N/A";

        // Print result
        return 
            "Title: " + formattedTile + 
            "\nCreator: " + formattedCreator +
            "\nSerial: " + serial + 
            "\nDescription: " + formattedDescription;
    }

    private void checkForNull(Object obj, String reason)
    {
        if (obj == null) {
            throw new IllegalArgumentException(reason);
        }
    }
}