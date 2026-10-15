package Classes;

/**
 * WaitlistEntry
 * Represents one person waiting for a checked-out item (stretch goal:
 * waitlist). Like a CheckoutRecord it keeps only the item's serial and a
 * borrower name, and knows nothing about the catalogue or the chains.
 *
 * Two entries are equal when both the serial and the borrower match, so
 * one person cannot wait twice for the same item but can wait for two
 * different items.
 *
 * @author Group 89 (drafted with Claude Code)
 * @version 10/15/2026
 */
public class WaitlistEntry
{
    /** The serial of the item being waited for. */
    private final int serial;
    /** The name of the person waiting. */
    private final String borrower;

    /**
     * Creates a waitlist entry.
     *
     * @param serial the serial of the item being waited for; must be
     *        positive
     * @param borrower the name of the person waiting
     * @throws IllegalArgumentException if serial is not positive, or if
     *         borrower is null, blank, or over 100 characters
     */
    public WaitlistEntry(int serial, String borrower)
    {
        if (serial <= 0)
        {
            throw new IllegalArgumentException("Serial must be positive");
        }
        CheckoutRecord.validateBorrower(borrower);
        this.serial = serial;
        this.borrower = borrower;
    }

    /**
     * Gets the serial of the item being waited for.
     *
     * @return the serial, always positive
     */
    public int getSerial()
    {
        return serial;
    }

    /**
     * Gets the name of the person waiting.
     *
     * @return the borrower
     */
    public String getBorrower()
    {
        return borrower;
    }

    /**
     * Two entries are equal when the serial and borrower both match.
     *
     * @param object the object to compare against
     * @return true if object is a WaitlistEntry for the same serial and
     *         borrower
     */
    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof WaitlistEntry))
        {
            return false;
        }
        WaitlistEntry other = (WaitlistEntry) object;
        return serial == other.serial && borrower.equals(other.borrower);
    }

    /**
     * Returns a hash code consistent with equals.
     *
     * @return a hash of the serial and borrower
     */
    @Override
    public int hashCode()
    {
        return 31 * serial + borrower.hashCode();
    }

    /**
     * Describes this entry.
     *
     * @return a sentence naming the borrower and the item
     */
    @Override
    public String toString()
    {
        return borrower + " is waiting for item " + serial + ".";
    }
}
