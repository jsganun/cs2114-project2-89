package Classes;
/**
 * CheckoutRecord
 * Represents one active loan: which LibraryItem is out and who has it.
 *
 * A record owns the facts of a single loan and knows nothing about the
 * catalogue or the chains. Each copy of an item gets its own record, so
 * one person can hold two copies of the same title. Two records are
 * equal when the serials of their items match.
 *
 * Validation lives here as a backstop: no record can hold a null item
 * or a null, blank, or over-100-character borrower name, no matter who
 * calls the constructor or setters.
 *
 * @author Ajeet Bondugula
 * @version 10/14/2026
 *
 */
public class CheckoutRecord
{
    /** The longest borrower name a record will accept. */
    public static final int MAX_BORROWER_LENGTH = 100;

    /** The name of the person who has the item. */
    private String borrower;
    /** A unique, chronologically assigned ID number for this record. */
    private int id;
    /** The library item on loan. */
    private LibraryItem item;

    /** The ID number the next record created will receive. */
    private static int nextId = 1;

    /**
     * Creates a blank CheckoutRecord with no borrower or linked
     * LibraryItem. Assigns a unique ID number to the record.
     */
    public CheckoutRecord()
    {
        this.id = nextId;
        nextId++;
    }

    /**
     * Creates a new CheckoutRecord for the given borrower and item.
     * Assigns a unique ID number to the record.
     *
     * @param borrower
     *            the name of the person borrowing the item
     * @param item
     *            the library item being borrowed
     * @throws IllegalArgumentException if borrower is null, blank, or
     *         over 100 characters, or if item is null
     */
    public CheckoutRecord(String borrower, LibraryItem item)
    {
        checkBorrower(borrower);
        checkItem(item);
        this.borrower = borrower;
        this.item = item;
        this.id = nextId;
        nextId++;
    }

    /**
     * Gets the name of the borrower on this record.
     *
     * @return the borrower, or null for a blank record
     */
    public String getBorrower()
    {
        return borrower;
    }

    /**
     * Gets the serial number of the LibraryItem on loan.
     *
     * @return the item's serial, or -1 if no item is linked
     */
    public int getSerial()
    {
        if (item == null)
        {
            return -1;
        }
        return item.getSerial();
    }

    /**
     * Gets the LibraryItem on loan.
     *
     * @return the item, or null for a blank record
     */
    public LibraryItem getItem()
    {
        return item;
    }

    /**
     * Gets the unique ID number of this record.
     *
     * @return the ID number
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets the borrower on this record.
     *
     * @param borrower - the new borrower name
     * @throws IllegalArgumentException if borrower is null, blank, or
     *         over 100 characters; the old borrower is kept
     */
    public void setBorrower(String borrower)
    {
        checkBorrower(borrower);
        this.borrower = borrower;
    }

    /**
     * Sets the LibraryItem on this record.
     *
     * @param item - the new library item
     * @throws IllegalArgumentException if item is null; the old item
     *         is kept
     */
    public void setItem(LibraryItem item)
    {
        checkItem(item);
        this.item = item;
    }

    /**
     * Two records are equal when both are linked to an item and the
     * items' serials match. A blank record is only equal to itself.
     *
     * @param object the object to compare against
     * @return true if object is a CheckoutRecord for the same serial
     */
    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (object == null || !(object instanceof CheckoutRecord))
        {
            return false;
        }

        CheckoutRecord other = (CheckoutRecord) object;
        if (this.item == null || other.item == null)
        {
            return false;
        }
        return this.getSerial() == other.getSerial();
    }

    /**
     * Returns a hash code consistent with equals.
     *
     * @return the item's serial, or the record ID for a blank record
     */
    @Override
    public int hashCode()
    {
        if (item == null)
        {
            return id;
        }
        return getSerial();
    }

    /**
     * Returns a readable description of this record: its ID, the serial
     * of the item on loan, and the borrower. Missing values print as N/A.
     *
     * @return a string representation of this record
     */
    @Override
    public String toString()
    {
        // Replace nulls with N/A
        String formattedBorrower = (borrower != null) ? borrower : "N/A";
        String formattedSerial = (item != null)
            ? String.valueOf(getSerial())
            : "N/A";

        // Print result
        return
            "Record: " + id +
            "\nSerial: " + formattedSerial +
            "\nBorrower: " + formattedBorrower;
    }

    /**
     * Rejects a borrower name that is null, blank, or too long.
     *
     * @param name the borrower name to check
     * @throws IllegalArgumentException if name is null, blank, or over
     *         100 characters
     */
    private void checkBorrower(String name)
    {
        if (name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Borrower name cannot be blank");
        }
        if (name.length() > MAX_BORROWER_LENGTH)
        {
            throw new IllegalArgumentException(
                "Borrower name is over 100 characters");
        }
    }

    /**
     * Rejects a null library item.
     *
     * @param libraryItem the item to check
     * @throws IllegalArgumentException if libraryItem is null
     */
    private void checkItem(LibraryItem libraryItem)
    {
        if (libraryItem == null)
        {
            throw new IllegalArgumentException("Item cannot be null");
        }
    }
}
