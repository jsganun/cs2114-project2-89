package Tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Classes.Catalogue;
import Classes.LibraryItem;

/**
 * Test class for the Catalogue class.
 * @author Jacob Mapa (jmapa26)
 * @version October 15, 2026
 */
public class CatalogueTest {

    private Catalogue catalogue;
    private LibraryItem checkoutRecordItem;
    private LibraryItem catalogueItemOne;
    private LibraryItem catalogueItemTwo;
    private LibraryItem libraryItem;
    private LibraryItem bookItem;
    private LibraryItem deviceItem;
    private LibraryItem dvdItem;
    private LibraryItem gameItem;
    private LibraryItem magazineItem;

    @BeforeEach
    public void setUp() {
        catalogue = new Catalogue();
        checkoutRecordItem = new LibraryItem("CheckoutRecord Class", "Ajeet Bondugula", "A class for checkout records.");
        catalogueItemOne = new LibraryItem("Catalogue Class", "Jacob Mapa", "A class for catalogues.");
        catalogueItemTwo = new LibraryItem("Catalogue Class", "Jonathan Ganun", "A class for catalogues.");
        libraryItem = new LibraryItem("LibraryItem Class", "Jonathan Ganun", "A class for library items.");
        bookItem = new LibraryItem("Book Class", "Zayd Pathan", "A book item.");
        deviceItem = new LibraryItem("Device Class", "Zayd Pathan", "A device item.");
        dvdItem = new LibraryItem("DVD Class", "Zayd Pathan", "A DVD item.");
        gameItem = new LibraryItem("Game Class", "Zayd Pathan", "A video game item.");
        magazineItem = new LibraryItem("Magazine Class", "Zayd Pathan", "A magazine item.");

        catalogue.addItem(checkoutRecordItem);
        catalogue.addItem(catalogueItemOne);
        catalogue.addItem(catalogueItemTwo);
        catalogue.addItem(libraryItem);
        catalogue.addItem(bookItem);
        catalogue.addItem(deviceItem);
        catalogue.addItem(dvdItem);
        catalogue.addItem(gameItem);
        catalogue.addItem(magazineItem);

        catalogue.borrowItem(bookItem, "Jacob Mapa");
        catalogue.borrowItem(deviceItem, "Jacob Mapa");
        catalogue.borrowItem(dvdItem, "Jacob Mapa");
        catalogue.borrowItem(gameItem, "Jonathan Ganun");

        catalogue.returnItem(bookItem);
    }

    @Test
    public void testGetItemsByTitle() {
        assertArrayEquals(new LibraryItem[] {
            catalogueItemOne,
            catalogueItemTwo
        }, catalogue.getItemsByTitle("Catalogue Class"));
        assertArrayEquals(new LibraryItem[0], catalogue.getItemsByTitle("No Match"));
        assertThrows(IllegalArgumentException.class, () -> catalogue.getItemsByTitle(null));
    }

    @Test
    public void testGetItemsByCreator() {
        assertArrayEquals(new LibraryItem[] {
            bookItem,
            deviceItem,
            dvdItem,
            gameItem,
            magazineItem
        }, catalogue.getItemsByCreator("Zayd Pathan"));
        assertArrayEquals(new LibraryItem[0], catalogue.getItemsByCreator("No Such Creator"));
        assertThrows(IllegalArgumentException.class, () -> catalogue.getItemsByCreator(null));
    }

    @Test
    public void testGetItem() {
        assertEquals(catalogueItemOne, catalogue.getItem(catalogueItemOne.getSerial()));
        assertEquals(libraryItem, catalogue.getItem(libraryItem.getSerial()));
        assertThrows(IndexOutOfBoundsException.class, () -> catalogue.getItem(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> catalogue.getItem(Integer.MAX_VALUE));
    }

    @Test
    public void testGetCheckoutRecord() {
        assertEquals("Jacob Mapa", catalogue.getCheckoutRecord(deviceItem.getRecord().getId()).getBorrower());
        assertThrows(IndexOutOfBoundsException.class, () -> catalogue.getCheckoutRecord(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> catalogue.getCheckoutRecord(Integer.MAX_VALUE));
    }

    @Test
    public void testGetCheckoutRecords() {
        assertEquals(2, catalogue.getCheckoutRecords("Jacob Mapa").length);
        assertEquals(1, catalogue.getCheckoutRecords("Jonathan Ganun").length);
        assertEquals(0, catalogue.getCheckoutRecords("Nobody").length);
        assertThrows(IllegalArgumentException.class, () -> catalogue.getCheckoutRecords(null));
    }

    @Test
    public void testBorrowItem() {
        assertTrue(catalogue.borrowItem(checkoutRecordItem, "Jacob Mapa"));
        assertFalse(catalogue.borrowItem(deviceItem, "Ajeet Bondugula"));
        assertFalse(catalogue.borrowItem(new LibraryItem(), "Jacob Mapa"));
        assertFalse(catalogue.borrowItem(gameItem, "Ajeet Bondugula"));
        assertThrows(IllegalArgumentException.class, () -> catalogue.borrowItem(null, "Jacob Mapa"));
        assertThrows(IllegalArgumentException.class, () -> catalogue.borrowItem(deviceItem, null));
    }

    @Test
    public void testReturnItem() {
        assertTrue(catalogue.borrowItem(checkoutRecordItem, "Jacob Mapa"));
        assertTrue(catalogue.returnItem(checkoutRecordItem));
        assertFalse(catalogue.returnItem(checkoutRecordItem));
        assertFalse(catalogue.returnItem(magazineItem));
        assertThrows(IllegalArgumentException.class, () -> catalogue.returnItem(null));
    }

    @Test
    public void testAddItem() {
        LibraryItem newItem = new LibraryItem("Test Item", "Test Creator", "A test item.");
        catalogue.addItem(newItem);
        assertEquals(newItem, catalogue.getItem(newItem.getSerial()));
        assertThrows(IllegalArgumentException.class, () -> catalogue.addItem(null));
    }

    @Test
    public void testRemoveItem() {
        LibraryItem removed = checkoutRecordItem;
        catalogue.removeItem(removed);
        assertEquals(0, catalogue.getItemsByTitle("CheckoutRecord Class").length);
        assertThrows(IllegalArgumentException.class, () -> catalogue.removeItem(null));
    }
}
