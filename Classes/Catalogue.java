package Classes;

/**
 * Holds all LibraryItem, CheckoutRecord, and WaitlistEntry objects via
 * LinkedChain, and enforces the library's rules: it assigns serial numbers,
 * refuses to check out an item that is already out, refuses to remove an
 * item that is on loan, and keeps a waitlist for checked-out items.
 *
 * The Catalogue works with its chains only through LinkedChain's public
 * methods. Searches copy a chain into an array once with toArray and scan
 * the array, because LinkedChain.get(i) is O(n) and calling it inside a
 * loop would make every scan O(n squared).
 *
 * Responses follow one convention: something not found returns false, null,
 * or an empty array; a broken rule throws IllegalStateException; an unusable
 * argument throws IllegalArgumentException; a serial that matches nothing
 * throws IndexOutOfBoundsException. Each message is written so the menu can
 * print it as is.
 *
 * @author Jacob Mapa (jmapa26)
 * @version October 15, 2026
 */
public class Catalogue
{
    /** Every item in the library, in the order they were added. */
    private final LinkedChain<LibraryItem> items;
    /** One record per item that is currently checked out. */
    private final LinkedChain<CheckoutRecord> records;
    /** People waiting for checked-out items, in the order they joined. */
    private final LinkedChain<WaitlistEntry> waitlist;
    /** The serial the next added item receives. Never reused. */
    private int nextSerial;

    /**
     * Constructor initializes items, records, and the waitlist.
     */
    public Catalogue()
    {
        items = new LinkedChain<>();
        records = new LinkedChain<>();
        waitlist = new LinkedChain<>();
        nextSerial = 1;
    }

    // ----------------------------------------------------------
    // Adding and removing items
    // ----------------------------------------------------------

    /**
     * Adds a LibraryItem to the end of the catalogue and gives it the next
     * serial number. A serial is never reused, even after its item is
     * removed. Two items with the same title and creator are allowed; each
     * copy has its own serial.
     * @param item The LibraryItem to add.
     * @return the serial number assigned to the item
     * @throws IllegalArgumentException if item is null
     * @throws IllegalStateException if item already has a serial, which
     *         means it was already added
     */
    public int addItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (item.getSerial() != 0) {
            throw new IllegalStateException("Item " + item.getSerial()
                + " is already in the catalogue.");
        }
        item.assignSerial(nextSerial);
        nextSerial++;
        items.addToEnd(item);
        return item.getSerial();
    }

    /**
     * Removes a LibraryItem from the catalogue, along with anyone waiting
     * for it. An item that is checked out cannot be removed until it is
     * returned.
     * @param item The LibraryItem to remove.
     * @return true if the item was removed, or false if it is not in the
     *         catalogue
     * @throws IllegalArgumentException if item is null
     * @throws IllegalStateException if the item is currently checked out
     */
    public boolean removeItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (!items.contains(item)) {
            return false;
        }
        CheckoutRecord record = findRecord(item.getSerial());
        if (record != null) {
            throw new IllegalStateException("Item " + item.getSerial()
                + " is checked out to " + record.getBorrower()
                + " and cannot be removed until it is returned.");
        }
        WaitlistEntry[] waiting = waitlistArray();
        for (WaitlistEntry entry : waiting) {
            if (entry.getSerial() == item.getSerial()) {
                waitlist.remove(entry);
            }
        }
        return items.remove(item);
    }

    // ----------------------------------------------------------
    // Browsing and searching
    // ----------------------------------------------------------

    /**
     * Returns every LibraryItem in the catalogue, in the order added.
     * @return an array containing every LibraryItem; empty if there are none
     */
    public LibraryItem[] getAllItems() {
        return itemArray();
    }

    /**
     * Returns every LibraryItem that is available, meaning it has no
     * checkout record.
     * @return an array of every available LibraryItem; empty if there are
     *         none
     */
    public LibraryItem[] getAllReturnedItems() {
        return itemsByStatus(false);
    }

    /**
     * Returns every LibraryItem that is currently checked out.
     * @return an array of every checked-out LibraryItem; empty if there
     *         are none
     */
    public LibraryItem[] getAllBorrowedItems() {
        return itemsByStatus(true);
    }

    /**
     * Returns an array of LibraryItem objects with an
     * equivalent title as the value of the String argument. The comparison
     * ignores case but otherwise needs the whole title to match.
     * @param title The title being searched for.
     * @return An array of LibraryItem objects with the given title; empty
     *         if none match.
     * @throws IllegalArgumentException if title is null or blank
     */
    public LibraryItem[] getItemsByTitle(String title)
    {
        requireSearchText(title);
        LinkedChain<LibraryItem> found = new LinkedChain<>();
        for (LibraryItem item : itemArray()) {
            if (item.getTitle().equalsIgnoreCase(title.trim())) {
                found.addToEnd(item);
            }
        }
        return found.toArray(new LibraryItem[found.size()]);
    }

    /**
     * Returns an array of LibraryItem objects with an
     * equivalent creator as the value of the String argument. The
     * comparison ignores case but otherwise needs the whole name to match.
     * @param creator The creator being searched for.
     * @return An array of LibraryItem objects with the given creator;
     *         empty if none match.
     * @throws IllegalArgumentException if creator is null or blank
     */
    public LibraryItem[] getItemsByCreator(String creator)
    {
        requireSearchText(creator);
        LinkedChain<LibraryItem> found = new LinkedChain<>();
        for (LibraryItem item : itemArray()) {
            if (item.getCreator().equalsIgnoreCase(creator.trim())) {
                found.addToEnd(item);
            }
        }
        return found.toArray(new LibraryItem[found.size()]);
    }

    /**
     * Returns every item with any attribute (title, creator, description,
     * type, or a subclass descriptor such as genre or platform) that
     * contains the keyword, ignoring case.
     * @param keyword The text to look for.
     * @return An array of matching LibraryItem objects; empty if none match.
     * @throws IllegalArgumentException if keyword is null or blank
     */
    public LibraryItem[] searchItems(String keyword)
    {
        requireSearchText(keyword);
        LinkedChain<LibraryItem> found = new LinkedChain<>();
        for (LibraryItem item : itemArray()) {
            if (item.matches(keyword)) {
                found.addToEnd(item);
            }
        }
        return found.toArray(new LibraryItem[found.size()]);
    }

    /**
     * Returns a LibraryItem object whose serial is
     * the value of the int argument.
     * @param serial The serial of the LibraryItem being searched for.
     * @return The LibraryItem with the serial.
     * @throws IndexOutOfBoundsException if no item has the serial,
     *         including zero and negative numbers
     */
    public LibraryItem getItem(int serial)
    {
        for (LibraryItem item : itemArray()) {
            if (item.getSerial() == serial) {
                return item;
            }
        }
        throw new IndexOutOfBoundsException("No such item: " + serial);
    }

    // ----------------------------------------------------------
    // Checkout records
    // ----------------------------------------------------------

    /**
     * Returns every CheckoutRecord, in the order the loans were made.
     * @return an array containing every checkout record; empty if none
     */
    public CheckoutRecord[] getAllCheckoutRecords() {
        return recordArray();
    }

    /**
     * Returns the CheckoutRecord of the checked-out item with this serial.
     * @param serial The serial of the checked-out item.
     * @return The CheckoutRecord for that serial.
     * @throws IndexOutOfBoundsException if no record has the serial
     */
    public CheckoutRecord getCheckoutRecord(int serial)
    {
        CheckoutRecord record = findRecord(serial);
        if (record == null) {
            throw new IndexOutOfBoundsException(
                "No checkout record for item " + serial);
        }
        return record;
    }

    /**
     * Returns an array of CheckoutRecord objects
     * given a String argument representing the borrower.
     * @param borrower The name of the borrower of the LibraryItem.
     * @return An array of CheckoutRecord objects with the given borrower,
     *         in the order the loans were made; empty if none.
     * @throws IllegalArgumentException if borrower is null or blank
     */
    public CheckoutRecord[] getCheckoutRecords(String borrower)
    {
        requireSearchText(borrower);
        LinkedChain<CheckoutRecord> found = new LinkedChain<>();
        for (CheckoutRecord record : recordArray()) {
            if (record.getBorrower().equals(borrower)) {
                found.addToEnd(record);
            }
        }
        return found.toArray(new CheckoutRecord[found.size()]);
    }

    /**
     * Tells whether an item is currently checked out.
     * @param item The LibraryItem to look up.
     * @return true if the item has a checkout record
     * @throws IllegalArgumentException if item is null
     */
    public boolean isCheckedOut(LibraryItem item)
    {
        requireItem(item);
        return findRecord(item.getSerial()) != null;
    }

    /**
     * Tells who has an item.
     * @param item The LibraryItem to look up.
     * @return the borrower's name, or null if the item is not checked out
     * @throws IllegalArgumentException if item is null
     */
    public String getBorrower(LibraryItem item)
    {
        requireItem(item);
        CheckoutRecord record = findRecord(item.getSerial());
        return record == null ? null : record.getBorrower();
    }

    /**
     * Creates a CheckoutRecord object given item and borrower arguments.
     * If other people are waiting for the item, only the first person in
     * line may borrow it, and that person leaves the waitlist.
     * @param item The LibraryItem being borrowed.
     * @param borrower The person borrowing the LibraryItem.
     * @return true if the item was checked out, or false if the item is
     *         not in this catalogue
     * @throws IllegalArgumentException if item is null, or borrower is
     *         null, blank, or over 100 characters
     * @throws IllegalStateException if the item is already checked out, or
     *         is on hold for someone else on the waitlist
     */
    public boolean borrowItem(LibraryItem item, String borrower)
    {
        requireItem(item);
        CheckoutRecord.validateBorrower(borrower);
        if (!items.contains(item)) {
            return false;
        }
        CheckoutRecord current = findRecord(item.getSerial());
        if (current != null) {
            throw new IllegalStateException("Item " + item.getSerial()
                + " is already checked out to " + current.getBorrower() + ".");
        }
        WaitlistEntry next = firstWaiting(item.getSerial());
        if (next != null && !next.getBorrower().equals(borrower)) {
            throw new IllegalStateException("Item " + item.getSerial()
                + " is on hold for " + next.getBorrower()
                + ", who is first in line.");
        }
        if (next != null) {
            waitlist.remove(next);
        }
        records.addToEnd(new CheckoutRecord(borrower, item));
        return true;
    }

    /**
     * Removes the CheckoutRecord of an item, which makes it available.
     * @param item The LibraryItem being returned.
     * @return true if the item was returned, or false if the item is not
     *         in this catalogue
     * @throws IllegalArgumentException if item is null
     * @throws IllegalStateException if the item is not checked out; both
     *         chains stay unchanged
     */
    public boolean returnItem(LibraryItem item)
    {
        requireItem(item);
        if (!items.contains(item)) {
            return false;
        }
        CheckoutRecord record = findRecord(item.getSerial());
        if (record == null) {
            throw new IllegalStateException("Item " + item.getSerial()
                + " is not checked out.");
        }
        return records.remove(record);
    }

    // ----------------------------------------------------------
    // Waitlist (stretch goal)
    // ----------------------------------------------------------

    /**
     * Puts a person at the end of the waitlist for a checked-out item.
     * @param item The checked-out LibraryItem to wait for.
     * @param borrower The person waiting.
     * @return true if the person joined the waitlist, or false if the item
     *         is not in this catalogue
     * @throws IllegalArgumentException if item is null, or borrower is
     *         null, blank, or over 100 characters
     * @throws IllegalStateException if the item is not checked out, if
     *         the borrower already has it, or if the borrower is already
     *         waiting for it
     */
    public boolean joinWaitlist(LibraryItem item, String borrower)
    {
        requireItem(item);
        CheckoutRecord.validateBorrower(borrower);
        if (!items.contains(item)) {
            return false;
        }
        CheckoutRecord current = findRecord(item.getSerial());
        if (current == null) {
            throw new IllegalStateException("Item " + item.getSerial()
                + " is available, so it can be checked out now.");
        }
        if (current.getBorrower().equals(borrower)) {
            throw new IllegalStateException(borrower + " already has item "
                + item.getSerial() + ".");
        }
        WaitlistEntry entry = new WaitlistEntry(item.getSerial(), borrower);
        if (waitlist.contains(entry)) {
            throw new IllegalStateException(borrower
                + " is already waiting for item " + item.getSerial() + ".");
        }
        return waitlist.addToEnd(entry);
    }

    /**
     * Returns the people waiting for an item, first in line first.
     * @param item The LibraryItem to look up.
     * @return the waitlist entries for the item; empty if nobody is waiting
     * @throws IllegalArgumentException if item is null
     */
    public WaitlistEntry[] getWaitlist(LibraryItem item)
    {
        requireItem(item);
        LinkedChain<WaitlistEntry> found = new LinkedChain<>();
        for (WaitlistEntry entry : waitlistArray()) {
            if (entry.getSerial() == item.getSerial()) {
                found.addToEnd(entry);
            }
        }
        return found.toArray(new WaitlistEntry[found.size()]);
    }

    /**
     * Tells who is first in line for an item. The menu uses this to notify
     * that person when the item is returned.
     * @param item The LibraryItem to look up.
     * @return the name of the first person waiting, or null if nobody is
     * @throws IllegalArgumentException if item is null
     */
    public String getNextInLine(LibraryItem item)
    {
        requireItem(item);
        WaitlistEntry next = firstWaiting(item.getSerial());
        return next == null ? null : next.getBorrower();
    }

    // ----------------------------------------------------------
    // Private helpers
    // ----------------------------------------------------------

    /**
     * Copies the items chain into an array, first item first. O(n).
     * @return a snapshot of the items
     */
    private LibraryItem[] itemArray() {
        return items.toArray(new LibraryItem[items.size()]);
    }

    /**
     * Copies the records chain into an array. O(r).
     * @return a snapshot of the records
     */
    private CheckoutRecord[] recordArray() {
        return records.toArray(new CheckoutRecord[records.size()]);
    }

    /**
     * Copies the waitlist chain into an array. O(w).
     * @return a snapshot of the waitlist
     */
    private WaitlistEntry[] waitlistArray() {
        return waitlist.toArray(new WaitlistEntry[waitlist.size()]);
    }

    /**
     * Finds the checkout record with a serial.
     * @param serial the serial to look for
     * @return the record, or null if that item is not checked out
     */
    private CheckoutRecord findRecord(int serial) {
        for (CheckoutRecord record : recordArray()) {
            if (record.getSerial() == serial) {
                return record;
            }
        }
        return null;
    }

    /**
     * Finds the first person waiting for an item.
     * @param serial the serial of the item
     * @return the first waitlist entry for the serial, or null if none
     */
    private WaitlistEntry firstWaiting(int serial) {
        for (WaitlistEntry entry : waitlistArray()) {
            if (entry.getSerial() == serial) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Collects the items that are checked out, or those that are not.
     * Takes one snapshot of the records and scans it for each item.
     * @param checkedOut true for checked-out items, false for available ones
     * @return the matching items in catalogue order
     */
    private LibraryItem[] itemsByStatus(boolean checkedOut) {
        CheckoutRecord[] loans = recordArray();
        LinkedChain<LibraryItem> found = new LinkedChain<>();
        for (LibraryItem item : itemArray()) {
            boolean isOut = false;
            for (CheckoutRecord loan : loans) {
                if (loan.getSerial() == item.getSerial()) {
                    isOut = true;
                }
            }
            if (isOut == checkedOut) {
                found.addToEnd(item);
            }
        }
        return found.toArray(new LibraryItem[found.size()]);
    }

    /**
     * Rejects a null item.
     * @param item the item to check
     * @throws IllegalArgumentException if item is null
     */
    private void requireItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
    }

    /**
     * Rejects search text that is null or blank.
     * @param text the text to check
     * @throws IllegalArgumentException if text is null or blank
     */
    private void requireSearchText(String text) {
        if (text == null || text.trim(  ).isEmpty()) {
            throw new IllegalArgumentException("Text cannot be blank");
        }
    }
}
