
package Tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import Classes.Device;
public class DeviceTest
{   
    private Device device;

    @BeforeEach
    public void setUp()
    {
        device = new Device("Mac","Apple","Computer","Operating System", "Manufacturer", "Model", "DeviceType");
    }
    /**
     * Tests the getManufacturer method.
     */
    @Test
    public void testGetManufacturer()
    {
        assertEquals("Manufacturer", device.getManufacturer());

    }
    /**
     * Tests the getOperatingSystem method.
     */
    @Test
    public void testGetOperatingSystem()
    {
        assertEquals("Operating System", device.getOperatingSystem());

    }
    /**
     * Tests the getDeviceType method.
     */
    @Test
    public void testGetDeviceType()
    {
        assertEquals("DeviceType", device.getDeviceType());

    }
    /**
     * Tests the getModel method.
     */
    @Test
    public void testGetModel()
    {
        assertEquals("Model", device.getModel());

    }
    /**
     * Tests the setManufacturer method.
     */
    @Test
    public void testSetManufacturer()
    {
        device.setManufacturer
        ("NewManufacturer");
        assertEquals("NewManufacturer", device.getManufacturer());

    }
    /**
     * Tests the setOperatingSystem method.
     */
    @Test
    public void testSetOperatingSystem()
    {
        device.setOperatingSystem("NewOperatingSystem");
        assertEquals("NewOperatingSystem", device.getOperatingSystem());

    }
    /**
     * Tests the setDeviceType method.
     */ 
    @Test
    public void testSetDeviceType()
    {
        device.setDeviceType("NewDeviceType");
        assertEquals("NewDeviceType", device.getDeviceType());

    }
    /**
     * Tests the setModel method.
     */
    @Test
    public void testSetModel()
    {
        device.setModel("NewModel");
        assertEquals("NewModel", device.getModel());

    }

}