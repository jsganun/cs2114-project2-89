package Tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Classes.Book;
import Classes.Catalogue;
import Classes.ConsoleMenu;
import Classes.DVD;
import Classes.Game;
import Classes.LibraryItem;
import Classes.Magazine;

/**
 * ConsoleMenuTest
 * Tests the ConsoleMenu with a fake input stream, so no keyboard is
 * needed. Each test types a script of lines and checks what the menu
 * printed. Together they cover every bad-input case in the scope: blank
 * or too-long text (re-prompt), non-numeric choices and serials
 * (re-prompt), a serial that matches nothing, checking out an item that
 * is out, returning one that is not, removing one that is out, a search
 * with no matches, garbage text, repeated adds, and a closed input
 * stream.
 *
 * @author Group 89
 * @version 10/15/2026
 */
public class ConsoleMenuTest {

    private Catalogue catalogue;
    private LibraryItem dune;   // serial 1, available
    private LibraryItem planet; // serial 2, available
    private LibraryItem zelda;  // serial 3, checked out to Sam

    /**
     * Builds a small catalogue before each test.
     */
    @BeforeEach
    public void setUp() {
        catalogue = new Catalogue();
        dune = new Book("Dune", "Frank Herbert", "A desert planet.",
            "Science Fiction", "English", "Paperback", 412);
        planet = new DVD("Planet Earth", "David Attenborough", "Nature.",
            "Documentary", 11);
        zelda = new Game("Zelda", "Nintendo", "Adventure.", "Adventure",
            "Nintendo Switch", "E10+");
        catalogue.addItem(dune);
        catalogue.addItem(planet);
        catalogue.addItem(zelda);
        catalogue.borrowItem(zelda, "Sam");
    }

    /**
     * Runs a menu over the catalogue with the given typed lines.
     *
     * @param cat the catalogue to use
     * @param typed the lines the user types, each ending in a newline
     * @return everything the menu printed
     */
    private String run(Catalogue cat, String typed) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true, StandardCharsets.UTF_8);
        Scanner in = new Scanner(new ByteArrayInputStream(
            typed.getBytes(StandardCharsets.UTF_8)));
        new ConsoleMenu(cat, in, out).run();
        return bytes.toString(StandardCharsets.UTF_8);
    }

    /** Runs the menu over the fixture catalogue. */
    private String run(String typed) {
        return run(catalogue, typed);
    }

    // ----------------------------------------------------------
    // Constructor and exit
    // ----------------------------------------------------------

    /** The constructor accepts real arguments. */
    @Test
    public void testConstructor() {
        assertNotNull(new ConsoleMenu(catalogue, new Scanner("0\n"), System.out));
    }

    /** The constructor rejects a null catalogue, input, or output. */
    @Test
    public void testConstructorBadInput() {
        assertThrows(IllegalArgumentException.class,
            () -> new ConsoleMenu(null, new Scanner("0\n"), System.out));
        assertThrows(IllegalArgumentException.class,
            () -> new ConsoleMenu(catalogue, null, System.out));
        assertThrows(IllegalArgumentException.class,
            () -> new ConsoleMenu(catalogue, new Scanner("0\n"), null));
    }

    /** Choosing 0 shows the menu once and says goodbye. */
    @Test
    public void testRunExit() {
        String shown = run("0\n");
        assertTrue(shown.contains("=== LibCatS Library Catalogue ==="));
        assertTrue(shown.contains("Goodbye."));
    }

    /** An empty input stream (Ctrl+D right away) exits cleanly. */
    @Test
    public void testRunEmptyInputStream() {
        String[] shown = new String[1];
        assertDoesNotThrow(() -> shown[0] = run(""));
        assertTrue(shown[0].contains("Goodbye."));
    }

    /** The stream ending in the middle of a command exits cleanly. */
    @Test
    public void testRunInputEndsMidCommand() {
        String[] shown = new String[1];
        assertDoesNotThrow(() -> shown[0] = run("7\n1\nHalf a title\n"));
        assertTrue(shown[0].contains("Goodbye."));
        assertEquals(3, catalogue.getAllItems().length);
    }

    /** A closed Scanner exits cleanly too. */
    @Test
    public void testRunClosedScanner() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Scanner in = new Scanner("1\n");
        in.close();
        assertDoesNotThrow(() ->
            new ConsoleMenu(catalogue, in, new PrintStream(bytes)).run());
        assertTrue(bytes.toString().contains("Goodbye."));
    }

    // ----------------------------------------------------------
    // Menu choices that are not usable
    // ----------------------------------------------------------

    /** A menu choice that is not a number is re-prompted. */
    @Test
    public void testNonNumericChoice() {
        String shown = run("abc\n\n1.5\n0\n");
        assertEquals(3, count(shown, "That is not a number. Please try again."));
        assertTrue(shown.contains("Goodbye."));
    }

    /** A number that is not on the menu is told so and the menu returns. */
    @Test
    public void testUnknownChoice() {
        String shown = run("99\n-1\n0\n");
        assertEquals(2, count(shown, "Please choose a number from the menu."));
    }

    /** Very long garbage and special characters are plain text. */
    @Test
    public void testGarbageInput() {
        String garbage = "x".repeat(20000);
        String shown = run(garbage + "\n%s%n${jndi}\\[(\n6\n4\n%s %d .* (\n0\n");
        assertTrue(shown.contains("Nothing found."));
        assertTrue(shown.contains("Goodbye."));
    }

    // ----------------------------------------------------------
    // Browsing
    // ----------------------------------------------------------

    /** Option 1 lists every item with whether it is available. */
    @Test
    public void testShowAllItems() {
        String shown = run("1\n0\n");
        assertTrue(shown.contains("[1] Book \"Dune\" by Frank Herbert - Available"));
        assertTrue(shown.contains("[3] Game \"Zelda\" by Nintendo - Checked out to Sam"));
    }

    /** Options 1 to 4 on an empty catalogue say there is nothing. */
    @Test
    public void testShowEmptyCatalogue() {
        String shown = run(new Catalogue(), "1\n2\n3\n4\n0\n");
        assertTrue(shown.contains("There are no items in the catalogue!"));
        assertTrue(shown.contains("There are no available items!"));
        assertTrue(shown.contains("There are no checked-out items!"));
        assertTrue(shown.contains("There are no checkout records!"));
    }

    /** Options 2 and 3 split the items into separate categories. */
    @Test
    public void testShowAvailableAndCheckedOut() {
        String available = run("2\n0\n");
        assertTrue(available.contains("\"Dune\""));
        assertTrue(available.contains("\"Planet Earth\""));
        assertFalse(available.contains("\"Zelda\""));
        String out = run("3\n0\n");
        assertTrue(out.contains("\"Zelda\""));
        assertFalse(out.contains("\"Dune\""));
    }

    /** Option 4 lists the checkout records with the item's title. */
    @Test
    public void testShowCheckoutRecords() {
        String shown = run("4\n0\n");
        assertTrue(shown.contains("Item 3 is checked out to Sam. (Zelda)"));
    }

    /** Option 5 shows the full details and the status of one item. */
    @Test
    public void testShowDetails() {
        String shown = run("5\n1\n0\n");
        assertTrue(shown.contains("Language: English"));
        assertTrue(shown.contains("Status: Available"));
        String out = run("5\n3\n0\n");
        assertTrue(out.contains("Status: Checked out to Sam"));
    }

    /** Details re-prompt for a non-number, and say "no such item" for a
     *  serial that matches nothing, including zero and negative. */
    @Test
    public void testShowDetailsBadInput() {
        String shown = run("5\nabc\n99\n5\n0\n5\n-4\n0\n");
        assertTrue(shown.contains("That is not a number. Please try again."));
        assertTrue(shown.contains("No such item: 99"));
        assertTrue(shown.contains("No such item: 0"));
        assertTrue(shown.contains("No such item: -4"));
        assertTrue(shown.contains("Goodbye."));
    }

    // ----------------------------------------------------------
    // Searching
    // ----------------------------------------------------------

    /** Search by title, creator, serial number, and any attribute. */
    @Test
    public void testSearch() {
        assertTrue(run("6\n1\nDune\n0\n").contains("[1] Book \"Dune\""));
        assertTrue(run("6\n2\nNintendo\n0\n").contains("\"Zelda\""));
        assertTrue(run("6\n3\n2\n0\n").contains("\"Planet Earth\""));
        assertTrue(run("6\n4\ndocumentary\n0\n").contains("\"Planet Earth\""));
    }

    /** A search with no matches says "Nothing found." */
    @Test
    public void testSearchNothingFound() {
        assertTrue(run("6\n1\nNo Such Title\n0\n").contains("Nothing found."));
        assertTrue(run("6\n4\nzebra\n0\n").contains("Nothing found."));
        assertTrue(run(new Catalogue(), "6\n4\nanything\n0\n").contains("Nothing found."));
    }

    /** Search text is re-prompted when blank, and a serial search says
     *  "no such item"; an unknown kind of search returns to the menu. */
    @Test
    public void testSearchBadInput() {
        String shown = run("6\n1\n   \nDune\n0\n");
        assertTrue(shown.contains("Text cannot be blank. Please try again."));
        assertTrue(shown.contains("\"Dune\""));
        assertTrue(run("6\n3\n77\n0\n").contains("No such item: 77"));
        assertTrue(run("6\n9\n0\n").contains("Please choose a number from the menu."));
    }

    // ----------------------------------------------------------
    // Adding
    // ----------------------------------------------------------

    /** Adding a book asks for each field and the item appears. */
    @Test
    public void testAddBook() {
        String shown = run("7\n1\nHobbit\nTolkien\nA hobbit.\nFantasy\nEnglish\n"
            + "Hardcover\n310\n\n0\n");
        assertTrue(shown.contains("Added item 4: Hobbit"));
        Book added = (Book) catalogue.getItem(4);
        assertEquals("Fantasy", added.getGenre());
        assertEquals(310, added.getPageCount());
        assertFalse(added.hasIsbn());
    }

    /** A blank or over-100-character title and creator, and a page count
     *  that is not a positive number, are each re-prompted. */
    @Test
    public void testAddBadInputIsReprompted() {
        String tooLong = "a".repeat(101);
        String shown = run("7\n1\n   \n" + tooLong + "\nHobbit\n\nTolkien\n"
            + "\nFantasy\nEnglish\nHardcover\nlots\n0\n-3\n310\n\n0\n");
        // blank title, blank creator (the empty line after is the optional
        // description, which is allowed to be empty)
        assertEquals(2, count(shown, "Text cannot be blank. Please try again."));
        assertEquals(1, count(shown, "Text is over 100 characters. Please try again."));
        assertTrue(shown.contains("That is not a number. Please try again."));
        assertEquals(2, count(shown, "The number must be greater than 0. Please try again."));
        assertEquals(4, catalogue.getAllItems().length);
        assertEquals("Hobbit", catalogue.getItem(4).getTitle());
    }

    /** A creator over 100 characters is rejected like a title. */
    @Test
    public void testAddOverlongCreator() {
        String shown = run("7\n3\nFilm\n" + "b".repeat(150) + "\nA Director\n\n"
            + "Drama\n5\n0\n");
        assertTrue(shown.contains("Text is over 100 characters. Please try again."));
        assertEquals("A Director", catalogue.getItem(4).getCreator());
    }

    /** An ISBN is optional; a bad one is re-prompted, a good one kept. */
    @Test
    public void testAddBookWithIsbn() {
        String shown = run("7\n1\nHobbit\nTolkien\n\nFantasy\nEnglish\nHardcover\n"
            + "310\n123\n9780547928227\n0\n");
        assertTrue(shown.contains("ISBN is not valid. Please try again."));
        assertEquals("9780547928227", ((Book) catalogue.getItem(4)).getIsbn());
    }

    /** A magazine's date of issue is optional; an impossible date is
     *  re-prompted, a real one kept. */
    @Test
    public void testAddMagazineWithDate() {
        String shown = run("7\n2\nTime\nTime Inc.\n\nNews\nVol. 1\n2024\n"
            + "2023-02-30\n2024-02-29\n0\n");
        assertTrue(shown.contains("Date of issue is not a valid date"));
        Magazine added = (Magazine) catalogue.getItem(4);
        assertEquals("2024-02-29", added.getDateOfIssue().toString());
    }

    /** All five item types can be added through the menu. */
    @Test
    public void testAddEveryType() {
        run("7\n1\nB\nC\n\nG\nL\nF\n5\n\n"
            + "7\n2\nM\nC\n\nG\nV\nD\n\n"
            + "7\n3\nD\nC\n\nG\n3\n"
            + "7\n4\nV\nC\n\nOS\nMaker\nModel\nType\n"
            + "7\n5\nGa\nC\n\nG\nP\nR\n0\n");
        LibraryItem[] all = catalogue.getAllItems();
        assertEquals(8, all.length);
        assertEquals("Book", all[3].getType());
        assertEquals("Magazine", all[4].getType());
        assertEquals("DVD", all[5].getType());
        assertEquals("Device", all[6].getType());
        assertEquals("Game", all[7].getType());
    }

    /** An unknown item type returns to the menu without adding. */
    @Test
    public void testAddUnknownType() {
        String shown = run("7\n9\n7\nabc\n0\n0\n");
        assertTrue(shown.contains("No such type. Returning to the menu."));
        assertEquals(3, catalogue.getAllItems().length);
    }

    /** Adding the same details twice is allowed; each copy gets its own
     *  serial. */
    @Test
    public void testRapidRepeatedAdds() {
        String one = "7\n3\nDVD Title\nCreator\n\nDrama\n2\n";
        run(one + one + one + "0\n");
        assertEquals(6, catalogue.getAllItems().length);
        assertEquals(3, catalogue.getItemsByTitle("DVD Title").length);
        assertEquals(4, catalogue.getItemsByTitle("DVD Title")[0].getSerial());
        assertEquals(6, catalogue.getItemsByTitle("DVD Title")[2].getSerial());
    }

    // ----------------------------------------------------------
    // Removing
    // ----------------------------------------------------------

    /** An available item is removed. */
    @Test
    public void testRemoveItem() {
        String shown = run("8\n1\n0\n");
        assertTrue(shown.contains("Removed item 1: Dune"));
        assertEquals(2, catalogue.getAllItems().length);
    }

    /** An item that is checked out is refused until it is returned. */
    @Test
    public void testRemoveItemCheckedOut() {
        String shown = run("8\n3\n0\n");
        assertTrue(shown.contains("Item 3 is checked out to Sam and cannot be "
            + "removed until it is returned."));
        assertEquals(3, catalogue.getAllItems().length);
        run("10\n3\n8\n3\n0\n");
        assertEquals(2, catalogue.getAllItems().length);
    }

    /** A serial that matches nothing, or is not a number, is handled. */
    @Test
    public void testRemoveItemBadInput() {
        String shown = run("8\n42\n8\nxyz\n2\n0\n");
        assertTrue(shown.contains("No such item: 42"));
        assertTrue(shown.contains("That is not a number. Please try again."));
        assertEquals(2, catalogue.getAllItems().length);
    }

    // ----------------------------------------------------------
    // Checking out and returning
    // ----------------------------------------------------------

    /** An available item is checked out to a named borrower. */
    @Test
    public void testBorrowItem() {
        String shown = run("9\n1\nAlex\n0\n");
        assertTrue(shown.contains("is now checked out to Alex."));
        assertEquals("Alex", catalogue.getBorrower(dune));
    }

    /** Checking out an item that is out is refused and shows who has it. */
    @Test
    public void testBorrowItemAlreadyOut() {
        String shown = run("9\n3\nAlex\n0\n");
        assertTrue(shown.contains("Item 3 is already checked out to Sam."));
        assertEquals("Sam", catalogue.getBorrower(zelda));
        assertEquals(1, catalogue.getAllCheckoutRecords().length);
    }

    /** A blank or over-100-character borrower is re-prompted, and a bad
     *  serial says "no such item". */
    @Test
    public void testBorrowItemBadInput() {
        String shown = run("9\n1\n  \n" + "n".repeat(101) + "\nAlex\n9\n0\n0\n");
        assertTrue(shown.contains("Text cannot be blank. Please try again."));
        assertTrue(shown.contains("Text is over 100 characters. Please try again."));
        assertTrue(shown.contains("No such item: 0"));
        assertEquals("Alex", catalogue.getBorrower(dune));
    }

    /** A checked-out item is returned. */
    @Test
    public void testReturnItem() {
        String shown = run("10\n3\n0\n");
        assertTrue(shown.contains("was returned by Sam."));
        assertFalse(catalogue.isCheckedOut(zelda));
    }

    /** Returning an item that is not out is refused and nothing changes. */
    @Test
    public void testReturnItemNotOut() {
        String shown = run("10\n1\n10\n77\n0\n");
        assertTrue(shown.contains("Item 1 is not checked out."));
        assertTrue(shown.contains("No such item: 77"));
        assertEquals(1, catalogue.getAllCheckoutRecords().length);
    }

    // ----------------------------------------------------------
    // Waitlist
    // ----------------------------------------------------------

    /** Someone joins the waitlist, and on return the next person in line
     *  gets a notice. */
    @Test
    public void testWaitlistNoticeOnReturn() {
        String shown = run("11\n3\nLee\n11\n3\nPat\n5\n3\n10\n3\n0\n");
        assertTrue(shown.contains("Lee is number 1 in line for item 3."));
        assertTrue(shown.contains("Pat is number 2 in line for item 3."));
        assertTrue(shown.contains("2. Pat"));
        assertTrue(shown.contains("Notice for Lee: item 3 (Zelda) is available "
            + "and on hold for you."));
    }

    /** Joining the waitlist for an available item, twice, or with a bad
     *  name is refused with a message. */
    @Test
    public void testWaitlistBadInput() {
        String shown = run("11\n1\nLee\n11\n3\nSam\n11\n3\nLee\n11\n3\nLee\n11\n3\n \nPat\n0\n");
        assertTrue(shown.contains("Item 1 is available, so it can be checked out now."));
        assertTrue(shown.contains("Sam already has item 3."));
        assertTrue(shown.contains("Lee is already waiting for item 3."));
        assertTrue(shown.contains("Text cannot be blank. Please try again."));
    }

    // ----------------------------------------------------------
    // Recent activity
    // ----------------------------------------------------------

    /** With nothing done yet, recent activity says so. */
    @Test
    public void testShowHistoryEmpty() {
        assertTrue(run("12\n0\n").contains("Nothing has happened yet."));
    }

    /** Recent activity lists successful actions, newest first, and does
     *  not list refused ones. */
    @Test
    public void testShowHistory() {
        String shown = run("9\n1\nAlex\n9\n1\nBo\n10\n1\n12\n0\n");
        int returned = shown.indexOf("Returned item 1 from Alex");
        int checkedOut = shown.indexOf("Checked out item 1 to Alex");
        assertTrue(returned > 0 && checkedOut > returned);
        assertFalse(shown.contains("Checked out item 1 to Bo"));
    }

    // ----------------------------------------------------------
    // The program never crashes
    // ----------------------------------------------------------

    /** A catalogue that fails unexpectedly: the menu prints a safe
     *  message, keeps running, and the next command still works. */
    @Test
    public void testUnexpectedExceptionKeepsRunning() {
        Catalogue broken = new Catalogue() {
            @Override
            public LibraryItem[] getAllItems() {
                throw new RuntimeException("boom");
            }
        };
        broken.addItem(new DVD("Film", "Someone", "", "Drama", 1));
        String shown = run(broken, "1\n2\n0\n");
        assertTrue(shown.contains("Something went wrong. Returning to the menu."));
        assertTrue(shown.contains("\"Film\""));
        assertFalse(shown.contains("boom"));
        assertTrue(shown.contains("Goodbye."));
    }

    /** Counts how many times a phrase appears in the output. */
    private static int count(String text, String phrase) {
        int found = 0;
        int at = text.indexOf(phrase);
        while (at >= 0) {
            found++;
            at = text.indexOf(phrase, at + phrase.length());
        }
        return found;
    }
}
