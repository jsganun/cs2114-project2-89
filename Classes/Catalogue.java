package Classes;

/**
 * Holds all LibraryItem and CheckoutRecord objects via LinkedChain.
 * Generates CheckoutRecord objects for available LibraryItem objects.
 * 
 * @author Jacob Mapa (jmapa26)
 * @version October 15, 2026
 */
public class Catalogue
{
    private LinkedChain<LibraryItem> items;
    private LinkedChain<CheckoutRecord> records;

    public static void main(String[] args) {
        // Code for prototype goes here.
    }

    /**
     * Constructor initializes items and records.
     */
    public Catalogue()
    {
        items = new LinkedChain<>();
        records = new LinkedChain<>();
    }

    /**
     * Returns an array of LibraryItem objects with an
     * equivalent title as the value of the String argument.
     * @param title The title being searched for.
     * @return An array of LibraryItem objects with the given title.
     * @throws if title is null
     */
    public LibraryItem[] getItemsByTitle(String title)
    {
        if (title == null) {
            throw new IllegalArgumentException(
                "The String argument passed to getItemsByTitle is null.");
        }
        LinkedChain<LibraryItem> itemsByTitle = new LinkedChain<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getTitle().equals(title)) {
                itemsByTitle.addToEnd(items.get(i));
            }
        }
        return itemsByTitle.toArray(new LibraryItem[itemsByTitle.size()]);
    }

    /**
     * Returns an array of LibraryItem objects with an
     * equivalent creator as the value of the String argument.
     * @param creator The creator being searched for.
     * @return An array of LibraryItem objects with the given creator.
     * @throws IllegalArgumentException if creator is null
     */
    public LibraryItem[] getItemsByCreator(String creator)
    {
        if (creator == null) {
            throw new IllegalArgumentException(
                "The String argument passed to getItemsByCreator is null.");
        }
        LinkedChain<LibraryItem> itemsByCreator = new LinkedChain<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCreator().equals(creator)) {
                itemsByCreator.addToEnd(items.get(i));
            }
        }
        return itemsByCreator.toArray(new LibraryItem[itemsByCreator.size()]);
    }

    /**
     * Returns a LibraryItem object whose serial is
     * the value of the int argument.
     * @param serial The serial of the LibraryItem being searched for.
     * @return The LibraryItem with the serial.
     * @throws IndexOutOfBoundsException if the serial doesn't exist
     */
    public LibraryItem getItem(int serial)
    {
        if (!isValidSerial(serial)) {
            throw new IndexOutOfBoundsException(serial +
                " is not a valid serial number.");
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getSerial() == serial) {
                return items.get(i);
            }
        }
        return null;
        // This line is added to avoid compilation error, but logically
        // it should never be reached if the serial is valid.
    }

    /**
     * Returns a CheckoutRecord object given its ID number.
     * @param id The CheckoutRecord object's ID number.
     * @return The CheckoutRecord object with the serial.
     * @throws IndexOutOfBoundsException if the ID doesn't exist.
     */
    public CheckoutRecord getCheckoutRecord(int id)
    {
        if (!isValidID(id)) {
            throw new IndexOutOfBoundsException(id +
                " is not a valid CheckoutRecord ID number.");
        }
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getId() == id) {
                return records.get(i);
            }
        }
        return null;
        // This line is added to avoid compilation error, but logically
        // it should never be reached if the ID is valid.
    }

    /**
     * Returns an array of CheckoutRecord objects
     * given a String argument representing the borrower.
     * @param borrower The name of the borrower of the LibraryItem.
     * @return An array of CheckoutRecord objects with the given borrower.
     * @throws IllegalArgumentException if borrower is null
     */
    public CheckoutRecord[] getCheckoutRecords(String borrower)
    {
        if (borrower == null) {
            throw new IllegalArgumentException(
                "The String argument passed to getCheckoutRecords is null.");
        }
        LinkedChain<CheckoutRecord> recordsForBorrower = new LinkedChain<>();
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getBorrower().equals(borrower)) {
                recordsForBorrower.add(records.get(i));
            }
        }
        return recordsForBorrower.toArray(new CheckoutRecord[recordsForBorrower.size()]);
    }

    /**
     * Creates a CheckoutRecord object given item and borrower arguments
     * and updates the corresponding LibraryItem object accordingly.
     * @param item The LibraryItem being borrowed.
     * @param borrower The person borrowing the LibraryItem.
     * @return false if the LibraryItem object already has an associated
     *         CheckoutRecord object, and true otherwise.
     * @throws IllegalArgumentException if item or borrower is null
     */
    public boolean borrowItem(LibraryItem item, String borrower)
    {
        if (item == null || borrower == null) {
            throw new IllegalArgumentException(
                "At least one of the arguments passed to borrowItem is null.");
        }
        if (!items.contains(item) ||
            item.getRecord() != null &&
            records.contains(item.getRecord())) {
            return false;
        }
        CheckoutRecord borrowRecord = new CheckoutRecord(borrower, item);
        records.add(borrowRecord);
        item.setRecord(borrowRecord);
        return true;
    }

    /**
     * Removes the associated CheckoutRecord reference in the LibraryItem.
     * @param item The LibraryItem being returned.
     * @return false if the LibraryItem object does not already have an
     *         associated CheckoutRecord object, and true otherwise.
     * @throws IllegalArgumentException if item is null
     */
    public boolean returnItem(LibraryItem item)
    {
        if (item == null) {
            throw new IllegalArgumentException(
                "The LibraryItem argument passed to returnItem is null.");
        }
        if (!items.contains(item) ||
            item.getRecord() == null ||
            !records.contains(item.getRecord())) {
            return false;
        }
        CheckoutRecord returnRecord = item.getRecord();
        records.remove(returnRecord);
        item.setRecord(null);
        return true;
    }
    
    /**
     * Adds a LibraryItem object to the LinkedChain items.
     * @param item The LibraryItem to add.
     * @throws IllegalArgumentException if item is null
     */
    public void addItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "The LibraryItem argument passed to addItem is null.");
        }
        items.addToEnd(item);
    }

    /**
     * Removes a LibraryItem object from the LinkedChain items.
     * @param item The LibraryItem to remove.
     * @throws IllegalArgumentException if item is null
     */
    public void removeItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "The LibraryItem argument passed to removeItem is null.");
        }
        items.remove(item);
    }

    /**
     * Sees whether the serial is associated with
     * a LibraryItem object in the LinkedChain items.
     * @param serial The serial of the potential LibraryItem
     * @return false if serial is negative or is not associated
     *         with a LibraryItem object, true otherwise.
     */
    private boolean isValidSerial(int serial) {
        if (serial < 0) {
            return false;
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getSerial() == serial) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sees whether the ID is associated with
     * a CheckoutRecord object in the LinkedChain records.
     * @param id The ID number of the potential CheckoutRecord
     * @return false if id is negative or is not associated
     *         with a CheckoutRecord object, true otherwise.
     */
    private boolean isValidID(int id) {
        if (id < 0) {
            return false;
        }
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }
}