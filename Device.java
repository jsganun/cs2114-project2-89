public class Device extends LibraryItem
{
    private String operatingSystem;
    private String manufacturer;
    private String model;
    private String deviceType;

    public Device()
    {
        super();
    }

    public Device(
        String title,
        String creator,
        String operatingSystem,
        String manufacturer,
        String model,
        String deviceType)
    {
        super(title, creator);
        this.operatingSystem = operatingSystem;
        this.manufacturer = manufacturer;
        this.model = model;
        this.deviceType = deviceType;
    }

    public String getOperatingSystem()
    {
        return operatingSystem;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    public String getModel()
    {
        return model;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public void setOperatingSystem(String operatingSystem)
    {
        this.operatingSystem = operatingSystem;
    }

    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }
}