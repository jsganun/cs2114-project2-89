public abstract class LibraryItem
{
    private String title;
    private String creator;
    private int serial;
    private String description;
    private CheckoutRecord record;

    public LibraryItem()
    {
        // TODO
    }

    public LibraryItem(String title, String creator)
    {
        this.title = title;
        this.creator = creator;
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

    public int getSerial()
    {
        return serial;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}