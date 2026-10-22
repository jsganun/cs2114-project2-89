package Tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Classes.Book;
import Classes.Catalogue;
import Classes.CheckoutRecord;
import Classes.DVD;
import Classes.Device;
import Classes.Game;
import Classes.LibraryItem;
import Classes.Magazine;
import Classes.WaitlistEntry;

/**
 * Test class for the Catalogue class. Every public method has a normal
 * case and a bad-input case, plus tests for an empty catalogue, removing
 * an item that is not there, duplicate copies, and the waitlist. The
 * catalogue holds items of five different subclasses at once.
 * 
 * @author Jacob Mapa
 * @version October 15, 2026
 */
public class CatalogueTest {

    private Catalogue catalogue;
    private LibraryItem hobbit;      // serial 1, available
    private LibraryItem copyOne;     // serial 2, a copy of Dune, checked out to Jacob
    private LibraryItem copyTwo;     // serial 3, another copy of Dune, available
    private LibraryItem planet;      // serial 4, checked out to Jacob
    private LibraryItem zelda;       // serial 5, checked out to Jonathan
    private LibraryItem magazine;    // serial 6, available
    private LibraryItem laptop;      // serial 7, available

    /**
     * Builds a catalogue of five item types with some items checked out.
     */
    @BeforeEach
    public void setUp() {
        catalogue = new Catalogue();
        hobbit = new Book("The Hobbit", "J.R.R. Tolkien", "A fantasy adventure.",
            "Fantasy", "English", "Hardcover","wd", 310);
        copyOne = new Book("Dune", "Frank Herbert", "A desert planet.",
            "Science Fiction", "English", "Paperback", "wd", 412);
        copyTwo = new Book("Dune", "Frank Herbert", "A desert planet.",
            "Science Fiction", "English", "Paperback", "wd",  412);
        planet = new DVD("Planet Earth", "David Attenborough", "Nature.",
            "Documentary", 11);
        zelda = new Game("Zelda", "Nintendo", "Adventure.", "Adventure",
            "Nintendo Switch", "E10+");
        magazine = new Magazine("Time", "Time Inc.", "News weekly.", "News",
            "Vol. 1", "2023-01-01");
        laptop = new Device("Library Laptop", "Library Services", "A laptop.",
            "Windows", "Dell", "Latitude", "Laptop");

        catalogue.addItem(hobbit);
        catalogue.addItem(copyOne);
        catalogue.addItem(copyTwo);
        catalogue.addItem(planet);
        catalogue.addItem(zelda);
        catalogue.addItem(magazine);
        catalogue.addItem(laptop);

        catalogue.borrowItem(copyOne, "Jacob Mapa");
        catalogue.borrowItem(planet, "Jacob Mapa");
        catalogue.borrowItem(zelda, "Jonathan Ganun");
    }

    // ----------------------------------------------------------
    // Constructor and empty catalogue
    // ----------------------------------------------------------

    /**
     * Tests that a new catalogue is empty in every view.
     */
    @Test
    public void testConstructorEmptyCatalogue() {
        Catalogue empty = new Catalogue();
        assertEquals(0, empty.getAllItems().length);
        assertEquals(0, empty.getAllReturnedItems().length);
        assertEquals(0, empty.getAllBorrowedItems().length);
        assertEquals(0, empty.getAllCheckoutRecords().length);
        assertEquals(0, empty.getItemsByTitle("Anything").length);
        assertEquals(0, empty.getItemsByCreator("Anyone").length);
        assertEquals(0, empty.searchItems("x").length);
        assertEquals(0, empty.getCheckoutRecords("Sam").length);
    }

    /**
     * Tests the operations that make no sense on an empty catalogue.
     */
    @Test
    public void testEmptyCatalogueBadInput() {
        Catalogue empty = new Catalogue();
        assertThrows(IndexOutOfBoundsException.class, () -> empty.getItem(1));
        assertThrows(IndexOutOfBoundsException.class, () -> empty.getCheckoutRecord(1));
        LibraryItem stranger = new Book("X", "Y", "", "g", "l", "f","P", 1);
        assertFalse(empty.removeItem(stranger));
        assertFalse(empty.borrowItem(stranger, "Sam"));
        assertFalse(empty.returnItem(stranger));
    }

    // ----------------------------------------------------------
    // addItem
    // ----------------------------------------------------------

    /**
     * Tests that addItem assigns serials 1, 2, 3, ... in order and that
     * several item types live in the same catalogue.
     */
    @Test
    public void testAddItem() {
        assertEquals(1, hobbit.getSerial());
        assertEquals(7, laptop.getSerial());
        LibraryItem extra = new DVD("Extra", "Someone", "", "Drama", 3);
        assertEquals(8, catalogue.addItem(extra));
        assertSame(extra, catalogue.getItem(8));
        assertEquals(8, catalogue.getAllItems().length);
    }

    /**
     * Tests that two copies with the same title and creator both go in
     * with different serials (duplicates are allowed).
     */
    @Test
    public void testAddItemDuplicateCopies() {
        assertEquals(2, catalogue.getItemsByTitle("Dune").length);
        assertTrue(copyOne.getSerial() != copyTwo.getSerial());
    }

    /**
     * Tests that adding null is rejected and that adding the same
     * object twice is refused.
     */
    @Test
    public void testAddItemBadInput() {
        IllegalArgumentException nullItem = assertThrows(
            IllegalArgumentException.class, () -> catalogue.addItem(null));
        assertEquals("Item cannot be null", nullItem.getMessage());
        IllegalStateException twice = assertThrows(
            IllegalStateException.class, () -> catalogue.addItem(hobbit));
        assertEquals("Item 1 is already in the catalogue.", twice.getMessage());
        assertEquals(7, catalogue.getAllItems().length);
    }

    /**
     * Tests that a serial is never reused after its item is removed.
     */
    @Test
    public void testSerialNeverReused() {
        catalogue.removeItem(laptop);
        LibraryItem next = new DVD("Next", "Someone", "", "Drama", 3);
        assertEquals(8, catalogue.addItem(next));
    }

    // ----------------------------------------------------------
    // removeItem
    // ----------------------------------------------------------

    /**
     * Tests that removeItem removes an available item and keeps the
     * others in order.
     */
    @Test
    public void testRemoveItem() {
        assertTrue(catalogue.removeItem(hobbit));
        assertEquals(6, catalogue.getAllItems().length);
        assertSame(copyOne, catalogue.getAllItems()[0]);
        assertEquals(0, catalogue.getItemsByTitle("The Hobbit").length);
    }

    /**
     * Tests removing the first, middle, and last item one by one.
     */
    @Test
    public void testRemoveItemFirstMiddleLast() {
        assertTrue(catalogue.removeItem(hobbit));
        assertTrue(catalogue.removeItem(copyTwo));
        assertTrue(catalogue.removeItem(laptop));
        assertArrayEquals(new LibraryItem[] {copyOne, planet, zelda, magazine},
            catalogue.getAllItems());
    }

    /**
     * Tests removing an item that is not in the catalogue, twice, and
     * with null.
     */
    @Test
    public void testRemoveItemNotThere() {
        LibraryItem stranger = new DVD("Stranger", "Nobody", "", "Drama", 1);
        assertFalse(catalogue.removeItem(stranger));
        assertTrue(catalogue.removeItem(hobbit));
        assertFalse(catalogue.removeItem(hobbit));
        assertEquals(6, catalogue.getAllItems().length);
    }

    /**
     * Tests that an item that is checked out cannot be removed and that
     * nothing changes.
     */
    @Test
    public void testRemoveItemCheckedOut() {
        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> catalogue.removeItem(copyOne));
        assertEquals("Item 2 is checked out to Jacob Mapa and cannot be "
            + "removed until it is returned.", e.getMessage());
        assertEquals(7, catalogue.getAllItems().length);
        assertEquals(3, catalogue.getAllCheckoutRecords().length);
        catalogue.returnItem(copyOne);
        assertTrue(catalogue.removeItem(copyOne));
    }

    /**
     * Tests that removing one copy leaves the other copy with the same
     * title in place (equals compares serials, not titles).
     */
    @Test
    public void testRemoveItemOneOfTwoCopies() {
        assertTrue(catalogue.removeItem(copyTwo));
        LibraryItem[] dunes = catalogue.getItemsByTitle("Dune");
        assertEquals(1, dunes.length);
        assertSame(copyOne, dunes[0]);
    }

    /**
     * Tests that removeItem rejects null.
     */
    @Test
    public void testRemoveItemBadInput() {
        assertThrows(IllegalArgumentException.class, () -> catalogue.removeItem(null));
    }

    // ----------------------------------------------------------
    // Browsing: all, available, checked out
    // ----------------------------------------------------------

    /**
     * Tests getAllItems returns every item in the order added.
     */
    @Test
    public void testGetAllItems() {
        assertArrayEquals(new LibraryItem[] {hobbit, copyOne, copyTwo, planet,
            zelda, magazine, laptop}, catalogue.getAllItems());
    }

    /**
     * Tests that the array getAllItems returns is a copy: changing it does
     * not change the catalogue.
     */
    @Test
    public void testGetAllItemsIsACopy() {
        LibraryItem[] all = catalogue.getAllItems();
        all[0] = null;
        assertSame(hobbit, catalogue.getAllItems()[0]);
    }

    /**
     * Tests the available and checked-out categories, which together hold
     * every item exactly once.
     */
    @Test
    public void testGetAllReturnedAndBorrowedItems() {
        assertArrayEquals(new LibraryItem[] {hobbit, copyTwo, magazine, laptop},
            catalogue.getAllReturnedItems());
        assertArrayEquals(new LibraryItem[] {copyOne, planet, zelda},
            catalogue.getAllBorrowedItems());
    }

    /**
     * Tests the categories at the extremes: nothing checked out, and
     * everything checked out.
     */
    @Test
    public void testCategoriesAtExtremes() {
        Catalogue c = new Catalogue();
        LibraryItem only = new DVD("Only", "Someone", "", "Drama", 1);
        c.addItem(only);
        assertEquals(1, c.getAllReturnedItems().length);
        assertEquals(0, c.getAllBorrowedItems().length);
        c.borrowItem(only, "Sam");
        assertEquals(0, c.getAllReturnedItems().length);
        assertEquals(1, c.getAllBorrowedItems().length);
    }

    // ----------------------------------------------------------
    // Searching
    // ----------------------------------------------------------

    /**
     * Tests getItemsByTitle: exact title (ignoring case), several copies,
     * and no match.
     */
    @Test
    public void testGetItemsByTitle() {
        assertArrayEquals(new LibraryItem[] {copyOne, copyTwo},
            catalogue.getItemsByTitle("Dune"));
        assertArrayEquals(new LibraryItem[] {hobbit},
            catalogue.getItemsByTitle("the hobbit"));
        assertArrayEquals(new LibraryItem[0], catalogue.getItemsByTitle("No Match"));
        assertArrayEquals(new LibraryItem[0], catalogue.getItemsByTitle("Dun"));
    }

    /**
     * Tests that getItemsByTitle rejects null and blank text.
     */
    @Test
    public void testGetItemsByTitleBadInput() {
        assertThrows(IllegalArgumentException.class, () -> catalogue.getItemsByTitle(null));
        assertThrows(IllegalArgumentException.class, () -> catalogue.getItemsByTitle("  "));
    }

    /**
     * Tests getItemsByCreator: exact name, several items, and no match.
     */
    @Test
    public void testGetItemsByCreator() {
        assertArrayEquals(new LibraryItem[] {copyOne, copyTwo},
            catalogue.getItemsByCreator("Frank Herbert"));
        assertArrayEquals(new LibraryItem[] {zelda},
            catalogue.getItemsByCreator("NINTENDO"));
        assertArrayEquals(new LibraryItem[0],
            catalogue.getItemsByCreator("No Such Creator"));
    }

    /**
     * Tests that getItemsByCreator rejects null and blank text.
     */
    @Test
    public void testGetItemsByCreatorBadInput() {
        assertThrows(IllegalArgumentException.class, () -> catalogue.getItemsByCreator(null));
        assertThrows(IllegalArgumentException.class, () -> catalogue.getItemsByCreator(""));
    }

    /**
     * Tests searchItems finds a keyword in any attribute: title, creator,
     * description, type, and subclass descriptors such as platform.
     */
    @Test
    public void testSearchItems() {
        assertArrayEquals(new LibraryItem[] {copyOne, copyTwo},
            catalogue.searchItems("herbert"));
        assertArrayEquals(new LibraryItem[] {zelda},
            catalogue.searchItems("switch"));
        assertArrayEquals(new LibraryItem[] {planet},
            catalogue.searchItems("documentary"));
        assertArrayEquals(new LibraryItem[] {laptop},
            catalogue.searchItems("dell"));
        assertEquals(1, catalogue.searchItems("dvd").length);
        assertEquals(0, catalogue.searchItems("zebra").length);
    }

    /**
     * Tests that searchItems rejects null and blank keywords and treats
     * special characters as plain text.
     */
    @Test
    public void testSearchItemsBadInput() {
        assertThrows(IllegalArgumentException.class, () -> catalogue.searchItems(null));
        assertThrows(IllegalArgumentException.class, () -> catalogue.searchItems("   "));
        assertEquals(0, catalogue.searchItems(".*").length);
        assertEquals(0, catalogue.searchItems("%s%n([").length);
    }

    /**
     * Tests getItem finds an item by serial.
     */
    @Test
    public void testGetItem() {
        assertSame(hobbit, catalogue.getItem(1));
        assertSame(laptop, catalogue.getItem(laptop.getSerial()));
    }

    /**
     * Tests that getItem throws for a serial that matches nothing,
     * including zero and negative numbers.
     */
    @Test
    public void testGetItemBadInput() {
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class,
            () -> catalogue.getItem(99));
        assertEquals("No such item: 99", e.getMessage());
        assertThrows(IndexOutOfBoundsException.class, () -> catalogue.getItem(0));
        assertThrows(IndexOutOfBoundsException.class, () -> catalogue.getItem(-1));
        assertThrows(IndexOutOfBoundsException.class,
            () -> catalogue.getItem(Integer.MAX_VALUE));
    }

    // ----------------------------------------------------------
    // Checkout records
    // ----------------------------------------------------------

    /**
     * Tests getAllCheckoutRecords returns the records in loan order.
     */
    @Test
    public void testGetAllCheckoutRecords() {
        CheckoutRecord[] all = catalogue.getAllCheckoutRecords();
        assertEquals(3, all.length);
        assertEquals(copyOne.getSerial(), all[0].getSerial());
        assertEquals(planet.getSerial(), all[1].getSerial());
        assertEquals(zelda.getSerial(), all[2].getSerial());
    }

    /**
     * Tests getCheckoutRecord finds a record by serial.
     */
    @Test
    public void testGetCheckoutRecord() {
        assertEquals("Jacob Mapa",
            catalogue.getCheckoutRecord(planet.getSerial()).getBorrower());
    }

    /**
     * Tests that getCheckoutRecord throws when the item is not checked
     * out or the serial matches nothing.
     */
    @Test
    public void testGetCheckoutRecordBadInput() {
        assertThrows(IndexOutOfBoundsException.class,
            () -> catalogue.getCheckoutRecord(hobbit.getSerial()));
        assertThrows(IndexOutOfBoundsException.class, () -> catalogue.getCheckoutRecord(-1));
        assertThrows(IndexOutOfBoundsException.class,
            () -> catalogue.getCheckoutRecord(Integer.MAX_VALUE));
    }

    /**
     * Tests getCheckoutRecords by borrower, including a person who has
     * two items, and a borrower with none.
     */
    @Test
    public void testGetCheckoutRecords() {
        assertEquals(2, catalogue.getCheckoutRecords("Jacob Mapa").length);
        assertEquals(1, catalogue.getCheckoutRecords("Jonathan Ganun").length);
        assertEquals(0, catalogue.getCheckoutRecords("Nobody").length);
        assertEquals(copyOne.getSerial(),
            catalogue.getCheckoutRecords("Jacob Mapa")[0].getSerial());
    }

    /**
     * Tests that getCheckoutRecords rejects null and blank names.
     */
    @Test
    public void testGetCheckoutRecordsBadInput() {
        assertThrows(IllegalArgumentException.class, () -> catalogue.getCheckoutRecords(null));
        assertThrows(IllegalArgumentException.class, () -> catalogue.getCheckoutRecords(" "));
    }

    /**
     * Tests isCheckedOut and getBorrower.
     */
    @Test
    public void testIsCheckedOutAndGetBorrower() {
        assertTrue(catalogue.isCheckedOut(copyOne));
        assertFalse(catalogue.isCheckedOut(copyTwo));
        assertEquals("Jacob Mapa", catalogue.getBorrower(copyOne));
        assertNull(catalogue.getBorrower(copyTwo));
    }

    /**
     * Tests that isCheckedOut and getBorrower reject null.
     */
    @Test
    public void testIsCheckedOutAndGetBorrowerBadInput() {
        assertThrows(IllegalArgumentException.class, () -> catalogue.isCheckedOut(null));
        assertThrows(IllegalArgumentException.class, () -> catalogue.getBorrower(null));
    }

    // ----------------------------------------------------------
    // borrowItem and returnItem
    // ----------------------------------------------------------

    /**
     * Tests that borrowItem checks out an available item, and that one
     * person can borrow two copies of the same title.
     */
    @Test
    public void testBorrowItem() {
        assertTrue(catalogue.borrowItem(hobbit, "Sam"));
        assertEquals("Sam", catalogue.getBorrower(hobbit));
        assertTrue(catalogue.borrowItem(copyTwo, "Jacob Mapa"));
        // Jacob now holds three items, two of them copies of Dune
        assertEquals(3, catalogue.getCheckoutRecords("Jacob Mapa").length);
        assertEquals(5, catalogue.getAllCheckoutRecords().length);
    }

    /**
     * Tests that borrowing an item that is already out is refused with a
     * message that names who has it, and that no record is added.
     */
    @Test
    public void testBorrowItemAlreadyOut() {
        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> catalogue.borrowItem(copyOne, "Sam"));
        assertEquals("Item 2 is already checked out to Jacob Mapa.", e.getMessage());
        assertEquals(3, catalogue.getAllCheckoutRecords().length);
        assertEquals("Jacob Mapa", catalogue.getBorrower(copyOne));
    }

    /**
     * Tests borrowItem's other bad inputs: null item, null, blank, or
     * over-100-character borrower, and an item that is not in the
     * catalogue.
     */
    @Test
    public void testBorrowItemBadInput() {
        assertThrows(IllegalArgumentException.class, () -> catalogue.borrowItem(null, "Sam"));
        assertThrows(IllegalArgumentException.class, () -> catalogue.borrowItem(hobbit, null));
        assertThrows(IllegalArgumentException.class, () -> catalogue.borrowItem(hobbit, "   "));
        assertThrows(IllegalArgumentException.class,
            () -> catalogue.borrowItem(hobbit, "a".repeat(101)));
        assertFalse(catalogue.borrowItem(new DVD("Loose", "Nobody", "", "Drama", 1), "Sam"));
        assertEquals(3, catalogue.getAllCheckoutRecords().length);
    }

    /**
     * Tests that returnItem checks an item back in.
     */
    @Test
    public void testReturnItem() {
        assertTrue(catalogue.returnItem(copyOne));
        assertFalse(catalogue.isCheckedOut(copyOne));
        assertEquals(2, catalogue.getAllCheckoutRecords().length);
        assertTrue(catalogue.borrowItem(copyOne, "Sam"));
    }

    /**
     * Tests that returning an item that is not checked out is refused and
     * leaves both chains unchanged.
     */
    @Test
    public void testReturnItemNotOut() {
        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> catalogue.returnItem(hobbit));
        assertEquals("Item 1 is not checked out.", e.getMessage());
        assertEquals(3, catalogue.getAllCheckoutRecords().length);
        assertEquals(7, catalogue.getAllItems().length);
        catalogue.returnItem(copyOne);
        assertThrows(IllegalStateException.class, () -> catalogue.returnItem(copyOne));
    }

    /**
     * Tests returnItem's other bad inputs: null, and an item that is not in
     * the catalogue.
     */
    @Test
    public void testReturnItemBadInput() {
        assertThrows(IllegalArgumentException.class, () -> catalogue.returnItem(null));
        assertFalse(catalogue.returnItem(new DVD("Loose", "Nobody", "", "Drama", 1)));
    }

    // ----------------------------------------------------------
    // Waitlist (stretch goal)
    // ----------------------------------------------------------

    /**
     * Tests that people join the waitlist for a checked-out item and are
     * listed first in line first.
     */
    @Test
    public void testJoinWaitlist() {
        assertTrue(catalogue.joinWaitlist(copyOne, "Sam"));
        assertTrue(catalogue.joinWaitlist(copyOne, "Lee"));
        WaitlistEntry[] waiting = catalogue.getWaitlist(copyOne);
        assertEquals(2, waiting.length);
        assertEquals("Sam", waiting[0].getBorrower());
        assertEquals("Lee", waiting[1].getBorrower());
        assertEquals("Sam", catalogue.getNextInLine(copyOne));
    }

    /**
     * Tests the refusals: an available item, the person who already has
     * it, someone already waiting, a bad name, null, and a stranger item.
     */
    @Test
    public void testJoinWaitlistBadInput() {
        IllegalStateException available = assertThrows(IllegalStateException.class,
            () -> catalogue.joinWaitlist(hobbit, "Sam"));
        assertEquals("Item 1 is available, so it can be checked out now.",
            available.getMessage());
        assertThrows(IllegalStateException.class,
            () -> catalogue.joinWaitlist(copyOne, "Jacob Mapa"));
        catalogue.joinWaitlist(copyOne, "Sam");
        IllegalStateException twice = assertThrows(IllegalStateException.class,
            () -> catalogue.joinWaitlist(copyOne, "Sam"));
        assertEquals("Sam is already waiting for item 2.", twice.getMessage());
        assertThrows(IllegalArgumentException.class, () -> catalogue.joinWaitlist(copyOne, " "));
        assertThrows(IllegalArgumentException.class, () -> catalogue.joinWaitlist(null, "Sam"));
        assertFalse(catalogue.joinWaitlist(new DVD("Loose", "Nobody", "", "Drama", 1), "Sam"));
        assertEquals(1, catalogue.getWaitlist(copyOne).length);
    }

    /**
     * Tests getWaitlist and getNextInLine with nobody waiting, and with
     * null.
     */
    @Test
    public void testGetWaitlistAndNextInLine() {
        assertEquals(0, catalogue.getWaitlist(copyOne).length);
        assertNull(catalogue.getNextInLine(copyOne));
        assertThrows(IllegalArgumentException.class, () -> catalogue.getWaitlist(null));
        assertThrows(IllegalArgumentException.class, () -> catalogue.getNextInLine(null));
    }

    /**
     * Tests that after a return the item is held for the first person in
     * line: someone else is refused, the first person may borrow it and
     * then leaves the waitlist.
     */
    @Test
    public void testWaitlistHoldAfterReturn() {
        catalogue.joinWaitlist(copyOne, "Sam");
        catalogue.joinWaitlist(copyOne, "Lee");
        catalogue.returnItem(copyOne);
        assertEquals("Sam", catalogue.getNextInLine(copyOne));

        IllegalStateException hold = assertThrows(IllegalStateException.class,
            () -> catalogue.borrowItem(copyOne, "Pat"));
        assertEquals("Item 2 is on hold for Sam, who is first in line.",
            hold.getMessage());
        assertTrue(catalogue.borrowItem(copyOne, "Sam"));
        assertEquals("Lee", catalogue.getNextInLine(copyOne));
        assertEquals(1, catalogue.getWaitlist(copyOne).length);
    }

    /**
     * Tests that removing an item also clears its waitlist.
     */
    @Test
    public void testRemoveItemClearsWaitlist() {
        catalogue.joinWaitlist(copyOne, "Sam");
        catalogue.returnItem(copyOne);
        assertTrue(catalogue.removeItem(copyOne));
        assertEquals(0, catalogue.getWaitlist(copyOne).length);
    }
}
