package Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import Classes.Catalogue;
import Classes.LibraryItem;

/**
 * Test class for the Catalogue class.
 * @author Jacob Mapa (jmapa26)
 * @version October 15, 2026
 */
public class CatalogueTest {

	private Catalogue catalogue;
	private LibraryItem item;

	@BeforeEach
	public void setUp() {
		catalogue = new Catalogue();
	}

	@Test
	public void testGetItemsByTitle() {
		// TO DO
	}

	@Test
	public void testGetItemsByCreator() {
		// TO DO
	}

	@Test
	public void testGetItem() {
		// TO DO
	}

	@Test
	public void testGetCheckoutRecord() {
		// TO DO
	}

	@Test
	public void testGetCheckoutRecords() {
		// TO DO
	}

	@Test
	public void testBorrowItem() {
		// TO DO
	}

	@Test
	public void testReturnItem() {
		// TO DO
	}
}
