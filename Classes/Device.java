package Classes;

/**
 * Device
 * Represents a device that can be checked out. Inherits from LibraryItem.
 *
 * Every descriptor is required: String fields cannot be null or blank,
 * and counts must be greater than 0. A rejected setter keeps the old
 * value.
 */
public class Device extends LibraryItem
{
    private String operatingSystem;
    private String manufacturer;
    private String model;
    private String deviceType;

    /**
     * Creates a new Device.
     *
     * @param title the title of the device
     * @param creator the creator of the device
     * @param description the description of the device; may be empty
     * @param operatingSystem the operating system
     * @param manufacturer the manufacturer
     * @param model the model
     * @param deviceType the device type (for example tablet or laptop)
     * @throws IllegalArgumentException if title or creator is null,
     *         blank, or over 100 characters, if description is null,
     *         if a String descriptor is null or blank, or if a count
     *         is not greater than 0
     */
    public Device(
        String title,
        String creator,
        String description,
        String operatingSystem,
        String manufacturer,
        String model,
        String deviceType)
    {
        super(title, creator, description);
        this.operatingSystem = checkText(operatingSystem, "Operating system");
        this.manufacturer = checkText(manufacturer, "Manufacturer");
        this.model = checkText(model, "Model");
        this.deviceType = checkText(deviceType, "Device type");
    }

    /**
     * Returns "Device".
     * @return the item type
     */
    @Override
    public String getType()
    {
        return "Device";
    }

    /**
     * Returns the operating system of the device.
     * @return the operating system
     */
    public String getOperatingSystem()
    {
        return operatingSystem;
    }

    /**
     * Returns the manufacturer of the device.
     * @return the manufacturer
     */
    public String getManufacturer()
    {
        return manufacturer;
    }

    /**
     * Returns the model of the device.
     * @return the model
     */
    public String getModel()
    {
        return model;
    }

    /**
     * Returns the device type (for example tablet or laptop) of the device.
     * @return the device type (for example tablet or laptop)
     */
    public String getDeviceType()
    {
        return deviceType;
    }

    /**
     * Sets the operating system of the device.
     * @param operatingSystem the new operating system
     * @throws IllegalArgumentException if operatingSystem is null or blank;
     *         the old value is kept
     */
    public void setOperatingSystem(String operatingSystem)
    {
        this.operatingSystem = checkText(operatingSystem, "Operating system");
    }

    /**
     * Sets the manufacturer of the device.
     * @param manufacturer the new manufacturer
     * @throws IllegalArgumentException if manufacturer is null or blank;
     *         the old value is kept
     */
    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = checkText(manufacturer, "Manufacturer");
    }

    /**
     * Sets the model of the device.
     * @param model the new model
     * @throws IllegalArgumentException if model is null or blank;
     *         the old value is kept
     */
    public void setModel(String model)
    {
        this.model = checkText(model, "Model");
    }

    /**
     * Sets the device type (for example tablet or laptop) of the device.
     * @param deviceType the new device type (for example tablet or laptop)
     * @throws IllegalArgumentException if deviceType is null or blank;
     *         the old value is kept
     */
    public void setDeviceType(String deviceType)
    {
        this.deviceType = checkText(deviceType, "Device type");
    }

    /**
     * Adds this class's descriptors to the text a keyword search looks at.
     * @return the searchable text of this item
     */
    @Override
    protected String searchText()
    {
        return super.searchText() + " " + operatingSystem + " " + manufacturer + " " + model + " " + deviceType;
    }

    /**
     * Describes this item with all of its descriptors.
     * @return a multi-line description
     */
    @Override
    public String toString()
    {
        return super.toString() +
        "\nOperating system: " + operatingSystem +
        "\nManufacturer: " + manufacturer +
        "\nModel: " + model +
        "\nDevice type: " + deviceType;
    }
}
