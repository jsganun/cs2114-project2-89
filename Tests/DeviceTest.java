
package Tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    // ----------------------------------------------------------
    // Added for the prototype: constructor, bad input, type, text
    // ----------------------------------------------------------

    /**
     * Tests that the constructor stores the title, creator, description,
     * and every descriptor.
     */
    @Test
    public void testConstructor()
    {
        assertEquals("Mac", device.getTitle());
        assertEquals("Operating System", device.getOperatingSystem());
        assertEquals("Manufacturer", device.getManufacturer());
        assertEquals("Model", device.getModel());
        assertEquals("DeviceType", device.getDeviceType());
        assertEquals(0, device.getSerial());
    }

    /**
     * Tests that the constructor rejects a blank title, a blank or null
     * String descriptor, and a count that is not greater than 0.
     */
    @Test
    public void testConstructorBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> new Device("  ", "Creator", "D", "Operating System", "Manufacturer", "Model", "DeviceType"));
        assertThrows(IllegalArgumentException.class, () -> new Device("T", null, "D", "Operating System", "Manufacturer", "Model", "DeviceType"));
        assertThrows(IllegalArgumentException.class, () -> new Device("T", "C", null, "Operating System", "Manufacturer", "Model", "DeviceType"));
        assertThrows(IllegalArgumentException.class, () -> new Device("a".repeat(101), "C", "D", "Operating System", "Manufacturer", "Model", "DeviceType"));
        assertThrows(IllegalArgumentException.class, () -> new Device("T", "C", "D", null, "Manufacturer", "Model", "DeviceType"));
        assertThrows(IllegalArgumentException.class, () -> new Device("T", "C", "D", "   ", "Manufacturer", "Model", "DeviceType"));
        assertThrows(IllegalArgumentException.class, () -> new Device("T", "C", "D", "Operating System", null, "Model", "DeviceType"));
        assertThrows(IllegalArgumentException.class, () -> new Device("T", "C", "D", "Operating System", "   ", "Model", "DeviceType"));
        assertThrows(IllegalArgumentException.class, () -> new Device("T", "C", "D", "Operating System", "Manufacturer", null, "DeviceType"));
        assertThrows(IllegalArgumentException.class, () -> new Device("T", "C", "D", "Operating System", "Manufacturer", "   ", "DeviceType"));
        assertThrows(IllegalArgumentException.class, () -> new Device("T", "C", "D", "Operating System", "Manufacturer", "Model", null));
        assertThrows(IllegalArgumentException.class, () -> new Device("T", "C", "D", "Operating System", "Manufacturer", "Model", "   "));
    }

    /**
     * Tests that setOperatingSystem rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetOperatingSystemBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> device.setOperatingSystem(null));
        assertThrows(IllegalArgumentException.class, () -> device.setOperatingSystem("   "));
        assertEquals("Operating System", device.getOperatingSystem());
    }

    /**
     * Tests that setManufacturer rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetManufacturerBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> device.setManufacturer(null));
        assertThrows(IllegalArgumentException.class, () -> device.setManufacturer("   "));
        assertEquals("Manufacturer", device.getManufacturer());
    }

    /**
     * Tests that setModel rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetModelBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> device.setModel(null));
        assertThrows(IllegalArgumentException.class, () -> device.setModel("   "));
        assertEquals("Model", device.getModel());
    }

    /**
     * Tests that setDeviceType rejects null and blank text and keeps the old value.
     */
    @Test
    public void testSetDeviceTypeBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> device.setDeviceType(null));
        assertThrows(IllegalArgumentException.class, () -> device.setDeviceType("   "));
        assertEquals("DeviceType", device.getDeviceType());
    }

    /**
     * Tests getType.
     */
    @Test
    public void testGetType()
    {
        assertEquals("Device", device.getType());
    }

    /**
     * Tests that toString lists the common fields and every descriptor.
     */
    @Test
    public void testToString()
    {
        assertEquals("Device 0\nTitle: Mac\nCreator: Apple\nDescription: Computer"
            + "\nOperating system: Operating System\nManufacturer: Manufacturer"
            + "\nModel: Model\nDevice type: DeviceType",
            device.toString());
    }

    /**
     * Tests that matches finds a keyword in this class's own descriptors.
     */
    @Test
    public void testMatches()
    {
        assertTrue(device.matches("manufacturer"));
        assertTrue(device.matches("apple"));
        assertTrue(device.matches("devicetype"));
        assertFalse(device.matches("zebra"));
    }

    /**
     * Tests that matches rejects null and blank keywords.
     */
    @Test
    public void testMatchesBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> device.matches(null));
        assertThrows(IllegalArgumentException.class, () -> device.matches("  "));
    }
}
