
package Tests;
public class DeviceTest
{
    @BeforeEach
    public void setUp()
    {
        Device device = new Device("Mac","Apple","Computer","Operating System", "Manufacturer", "Model", "DeviceType");
    }
    /**
     * Tests the getManufacturer method.
     */
    @Test
    public void testGetManufacturer()
    {
        assertEqual("Manufacturer", device.getBrand());

    }
    /**
     * Tests the getOperatingSystem method.
     */
    @Test
    public void testGetOperatingSystem()
    {
        assertEqual("Operating System", device.getOperatingSystem());

    }
    /**
     * Tests the getDeviceType method.
     */
    @Test
    public void testGetDeviceType()
    {
        assertEqual("DeviceType", device.getDeviceType());

    }
    /**
     * Tests the getModel method.
     */
    @Test
    public void testGetModel()
    {
        assertEqual("Model", device.getModel());

    }
    /**
     * Tests the setManufacturer method.
     */
    @Test
    public void testSetManufacturer()
    {
        device.setBrand("NewManufacturer");
        assertEqual("NewManufacturer", device.getBrand());

    }
    /**
     * Tests the setOperatingSystem method.
     */
    @Test
    public void testSetOperatingSystem()
    {
        device.setOperatingSystem("NewOperatingSystem");
        assertEqual("NewOperatingSystem", device.getOperatingSystem());

    }
    /**
     * Tests the setDeviceType method.
     */ 
    @Test
    public void testSetDeviceType()
    {
        device.setDeviceType("NewDeviceType");
        assertEqual("NewDeviceType", device.getDeviceType());

    }
    /**
     * Tests the setModel method.
     */
    @Test
    public void testSetModel()
    {
        device.setModel("NewModel");
        assertEqual("NewModel", device.getModel());

    }

}