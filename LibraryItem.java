public abstract class LibraryItem
{
    private String title;
    private String creator;
    private int serial;
    private String description;
    private CheckoutRecord record;

    private static int nextSerial = 0;

    
    public LibraryItem()
    {
        this.title = "";
        this.creator = "";
        this.description = "";
        this.serial = nextSerial;
        nextSerial++;
    }

    public LibraryItem(String title, String creator, String description)
    {
        this();
        this.title = title;
        this.creator = creator;
        this.description = description;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public String getCreator()
    {
        return creator;
    }

    public CheckoutRecord getRecord()
    {
        return record;
    }

    public int getSerial()
    {
        return serial;
    }

    public void setTitle(String title)
    {
        checkForNull(title, "Title cannot be null.");
        this.title = title;
    }

    public void setCreator(String creator)
    {
        checkForNull(creator, "Creator cannot be null.");
        this.creator = creator;
    }

    public void setDescription(String description)
    {
        checkForNull(description, "Description cannot be null.");
        this.description = description;
    }

    public void setRecord(CheckoutRecord record)
    {
        this.record = record;
    }

    private void checkForNull(Object obj, String reason)
    {
        if (obj == null) {
            throw new IllegalArgumentException(reason);
        }
    }
}