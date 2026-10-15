package Classes;
/**
 * CheckoutRecord
 * Represents one active loan: the serial of the LibraryItem that is out
 * and who has it.
 *
 * A record owns the facts of a single loan and knows nothing about the
 * catalogue or the chains. Each copy of an item has its own serial, so
 * one person can hold two copies of the same title. Two records are
 * equal when their serials match.
 *
 * Validation lives here as a backstop: no record can hold a serial that
 * is not positive or a null, blank, or over-100-character borrower
 * name, no matter who calls the constructor or setter.
 *
 * @author Ajeet Bondugula
 * @version 10/15/2026
 *
 */
public class CheckoutRecord
{
    /** The longest borrower name a record will accept. */
    public static final int MAX_BORROWER_LENGTH = 100;

    /** The serial of the item on loan. Positive and never changes. */
    private final int serial;
    /** The name of the person who has the item. */
    private String borrower;

    /**
     * Creates a new CheckoutRecord for the given borrower and item.
     * The record keeps the item's serial, not the item itself.
     *
     * @param borrower
     *            the name of the person borrowing the item
     * @param item
     *            the library item being borrowed; must already have a
     *            serial from a Catalogue
     * @throws IllegalArgumentException if borrower is null, blank, or
     *         over 100 characters, if item is null, or if item has no
     *         serial yet
     */
    public CheckoutRecord(String borrower, LibraryItem item)
    {
        validateBorrower(borrower);
        if (item == null)
        {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (item.getSerial() <= 0)
        {
            throw new IllegalArgumentException(
                "Item must be added to the catalogue before it is borrowed");
        }
        this.borrower = borrower;
        this.serial = item.getSerial();
    }

    /**
     * Gets the name of the borrower on this record.
     *
     * @return the borrower
     */
    public String getBorrower()
    {
        return borrower;
    }

    /**
     * Gets the serial number of the LibraryItem on loan.
     *
     * @return the item's serial, always positive
     */
    public int getSerial()
    {
        return serial;
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
        validateBorrower(borrower);
        this.borrower = borrower;
    }

    /**
     * Two records are equal when their serials match, whoever the
     * borrower is.
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
        if (!(object instanceof CheckoutRecord))
        {
            return false;
        }
        return this.serial == ((CheckoutRecord) object).serial;
    }

    /**
     * Returns a hash code consistent with equals.
     *
     * @return the serial
     */
    @Override
    public int hashCode()
    {
        return serial;
    }

    /**
     * Returns a readable description of this record: the serial of the
     * item on loan and the borrower.
     *
     * @return a string representation of this record
     */
    @Override
    public String toString()
    {
        return "Item " + serial + " is checked out to " + borrower + ".";
    }

    /**
     * Rejects a borrower name that is null, blank, or too long. Shared
     * with Catalogue and WaitlistEntry so every class applies the same rule.
     *
     * @param name the borrower name to check
     * @throws IllegalArgumentException if name is null, blank, or over
     *         100 characters
     */
    static void validateBorrower(String name)
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
}
