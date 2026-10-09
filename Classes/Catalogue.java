package Classes;

/**
 * Holds all LibraryItem and CheckoutRecord objects via LinkedChain.
 * Generates CheckoutRecord objects for available LibraryItem objects.
 * 
 * @author Jacob Mapa (jmapa26)
 * @version October 8, 2026
 */

public class Catalogue
{
    private LinkedChain<LibraryItem> items;
    private LinkedChain<CheckoutRecord> records;

    public static void main(String[] args) {
        // No idea of what to put in here.
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
     * @return An array of LibraryItem objects with the same title.
     */
    public LibraryItem[] getItemsByTitle(String title)
    {
        if (title == null) {
            throw new IllegalArgumentException("The String argument passed to getItemsByTitle is null.");
        }
        LinkedChain<LibraryItem> itemsByTitle = new LinkedChain<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getTitle().equals(title)) {
                itemsByTitle.add(items.get(i));
            }
        }

        LibraryItem[] arr = new LibraryItem[itemsByTitle.size()];
        return itemsByTitle.toArray(arr);
    }

    public LibraryItem[] getItemsByCreator(String creator)
    {
        if (creator == null) {
            throw new IllegalArgumentException("The String argument passed to getItemsByCreator is null.");
        }
        LinkedChain<LibraryItem> itemsByCreator = new LinkedChain<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCreator().equals(creator)) {
                itemsByCreator.add(items.get(i));
            }
        }

        LibraryItem[] arr = new LibraryItem[itemsByCreator.size()];
        return itemsByCreator.toArray(arr);
    }

    public LibraryItem getItem(int serial)
    {
        // This method's code has problems that require consideration.
        
        if (serial < 0 || serial > items.size()) {
            throw new IndexOutOfBoundsException(serial + " is not a valid serial number.");
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getSerial() == serial) {
                return items.get(i);
            }
        }

        // TODO: what would this do if the object with this serial number was removed?
        return null;
    }

    public CheckoutRecord getCheckoutRecord(int id)
    {
        // This method's code has problems that require consideration.
        
        if (id < 0 || id > records.size()) {
            throw new IndexOutOfBoundsException(id + " is not a valid CheckoutRecord ID number.");
        }
        //for (int i = 0; i < records.size()

        return null;
    }

    public CheckoutRecord[] getCheckoutRecords(String borrower)
    {
        LinkedChain<CheckoutRecord> recordsForBorrower = new LinkedChain<>();
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getBorrower().equals(borrower)) {
                recordsForBorrower.add(records.get(i));
            }
        }

        CheckoutRecord[] arr = new CheckoutRecord[recordsForBorrower.size()];
        return recordsForBorrower.toArray(arr);
    }

    public boolean borrow(LibraryItem item, String borrower)
    {
        // This method's code has problems that require consideration.
        
        if (item == null || !items.contains(item) || !recordsHaveItem(item)) {
            // What to do here?
            return false;
        }
        CheckoutRecord borrowRecord = new CheckoutRecord(borrower, item);
        item.setRecord(borrowRecord);
        records.add(borrowRecord);
        return true;
    }

    public boolean returnItem(LibraryItem item)
    {
        // This method's code has problems that require consideration.
        
        if (item == null || items.contains(item) || recordsHaveItem(item)) {
            // What to do here?
            return false;
        }
        item.setRecord(null);
        // Do I remove the CheckoutRecord object from the LinkedChain records?
        return true;
    }

    private boolean recordsHaveItem(LibraryItem item) {
        CheckoutRecord[] arr = new CheckoutRecord[records.size()];
        arr = records.toArray(arr);

        for (int i = 0; i < arr.length; i++) {
            CheckoutRecord record = arr[i];
            if (record.getItem() != null && record.getItem().equals(item)) {
                return true;
            }
        }

        return false;
    }
}