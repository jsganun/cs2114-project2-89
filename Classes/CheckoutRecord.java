package Classes;
public class CheckoutRecord
{
    private String borrower;
    private int id;
    private LibraryItem item;

    public CheckoutRecord()
    {
        // TODO
    }

    public CheckoutRecord(String borrower, LibraryItem item)
    {
        this.borrower = borrower;
        this.item = item;
    }

    public String getBorrower()
    {
        return borrower;
    }

    public void setBorrower(String borrower)
    {
        this.borrower = borrower;
    }

    public LibraryItem getItem()
    {
        return item;
    }

    public void setItem(LibraryItem item)
    {
        this.item = item;
    }

    public int getId()
    {
        return id;
    }
}