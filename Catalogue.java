public class Catalogue
{
    private LinkedChain<LibraryItem> items;
    private LinkedChain<CheckoutRecord> records;

    public Catalogue()
    {
        items = new LinkedChain<>();
        records = new LinkedChain<>();
    }

    public LibraryItem[] getItemsByTitle(String title)
    {
        return null;
    }

    public LibraryItem[] getItemsByCreator(String creator)
    {
        return null;
    }

    public LibraryItem getItem(int serial)
    {
        return null;
    }

    public CheckoutRecord getCheckoutRecord(int id)
    {
        return null;
    }

    public CheckoutRecord[] getCheckoutRecords(String borrower)
    {
        return null;
    }

    public boolean borrow(LibraryItem item, String borrower)
    {
        return false;
    }

    public boolean returnItem(LibraryItem item)
    {
        return false;
    }
}