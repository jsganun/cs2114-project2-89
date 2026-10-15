package Classes;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * ConsoleMenu
 * The only class that reads or prints. It turns typed commands into
 * Catalogue calls and turns results and exceptions into messages.
 *
 * Validation here is the friendly first layer: text that is blank or over
 * 100 characters, and numbers that are not numbers, are rejected with a
 * message and the user is asked again, so nobody has to retype a whole
 * command. The domain classes still enforce the same rules as a backstop.
 *
 * Every command runs inside a try block. The message of an
 * IllegalArgumentException, IllegalStateException, or
 * IndexOutOfBoundsException is printed as written; any other
 * RuntimeException prints "Something went wrong. Returning to the menu."
 * and the menu continues. A closed or empty input stream (Ctrl+D) prints
 * "Goodbye." and returns normally.
 *
 * Besides the Catalogue's chains, the menu keeps its own
 * LinkedChain&lt;String&gt; of recent activity. add() puts the newest
 * entry at the front, so toArray returns the newest first.
 *
 * @author Group 89 (drafted with Claude Code; list layouts adapted from
 *         Jonathan Ganun's Main)
 * @version 10/15/2026
 */
public class ConsoleMenu
{
    /** The longest title, creator, or name the menu accepts. */
    private static final int MAX_TEXT = 100;
    /** How many recent actions the activity list shows. */
    private static final int HISTORY_SHOWN = 10;
    /** The rule line used in list headers. */
    private static final String RULE =
        "--------------------------------------------------";

    private final Catalogue catalogue;
    private final Scanner in;
    private final PrintStream out;
    /** Successful actions, newest first. */
    private final LinkedChain<String> history;

    /**
     * Signals that the input stream is closed or empty. Private because
     * only run() needs to know about it.
     */
    private static class EndOfInput extends RuntimeException
    {
        private static final long serialVersionUID = 1L;
    }

    /**
     * Creates a menu over a catalogue.
     *
     * @param catalogue the catalogue the menu works on
     * @param in the source of typed commands
     * @param out where messages are printed
     * @throws IllegalArgumentException if any argument is null
     */
    public ConsoleMenu(Catalogue catalogue, Scanner in, PrintStream out)
    {
        if (catalogue == null || in == null || out == null)
        {
            throw new IllegalArgumentException(
                "Catalogue, input, and output cannot be null");
        }
        this.catalogue = catalogue;
        this.in = in;
        this.out = out;
        this.history = new LinkedChain<>();
    }

    /**
     * Shows the menu and carries out commands until the user chooses
     * Exit or the input ends. Never throws: a bad command prints a message
     * and returns to the menu.
     */
    public void run()
    {
        try
        {
            boolean running = true;
            while (running)
            {
                printMenu();
                int choice = readInt("Choose an option: ");
                try
                {
                    running = carryOut(choice);
                }
                catch (EndOfInput e)
                {
                    throw e;
                }
                catch (IllegalArgumentException | IllegalStateException
                    | IndexOutOfBoundsException e)
                {
                    out.println(e.getMessage());
                }
                catch (RuntimeException e)
                {
                    out.println("Something went wrong. Returning to the menu.");
                }
            }
        }
        catch (EndOfInput e)
        {
            // The stream closed mid-command: fall through to the goodbye
        }
        out.println("Goodbye.");
    }

    // ----------------------------------------------------------
    // Commands
    // ----------------------------------------------------------

    /**
     * Prints the list of commands.
     */
    private void printMenu()
    {
        out.println();
        out.println("=== LibCatS Library Catalogue ===");
        out.println("[1]  Show all items");
        out.println("[2]  Show available items");
        out.println("[3]  Show checked-out items");
        out.println("[4]  Show checkout records");
        out.println("[5]  View details of one item");
        out.println("[6]  Search");
        out.println("[7]  Add an item");
        out.println("[8]  Remove an item");
        out.println("[9]  Check out an item");
        out.println("[10] Return an item");
        out.println("[11] Join the waitlist for a checked-out item");
        out.println("[12] Show recent activity");
        out.println("[0]  Exit");
    }

    /**
     * Carries out one command.
     *
     * @param choice the number the user typed
     * @return false if the user chose Exit, true otherwise
     */
    private boolean carryOut(int choice)
    {
        switch (choice)
        {
            case 0:
                return false;
            case 1:
                showItems("All Catalogue Items", "There are no items in the catalogue!",
                    catalogue.getAllItems());
                break;
            case 2:
                showItems("All Available Items", "There are no available items!",
                    catalogue.getAllReturnedItems());
                break;
            case 3:
                showItems("All Checked-Out Items", "There are no checked-out items!",
                    catalogue.getAllBorrowedItems());
                break;
            case 4:
                showCheckoutRecords();
                break;
            case 5:
                showDetails();
                break;
            case 6:
                search();
                break;
            case 7:
                addItem();
                break;
            case 8:
                removeItem();
                break;
            case 9:
                borrowItem();
                break;
            case 10:
                returnItem();
                break;
            case 11:
                joinWaitlist();
                break;
            case 12:
                showHistory();
                break;
            default:
                out.println("Please choose a number from the menu.");
        }
        return true;
    }

    /**
     * Prints a titled list of one-line item summaries, each with whether
     * the item is available or who has it.
     *
     * @param heading the list's title
     * @param emptyMessage what to say when the list is empty
     * @param list the items to show
     */
    private void showItems(String heading, String emptyMessage,
        LibraryItem[] list)
    {
        if (list.length == 0)
        {
            out.println(RULE + "\n" + emptyMessage + "\n" + RULE);
            return;
        }
        printHeader(heading);
        for (LibraryItem item : list)
        {
            out.println(item.summary() + " - " + status(item));
        }
        out.println(RULE);
    }

    /**
     * Prints every checkout record with the title of the item on loan.
     */
    private void showCheckoutRecords()
    {
        CheckoutRecord[] all = catalogue.getAllCheckoutRecords();
        if (all.length == 0)
        {
            out.println(RULE + "\nThere are no checkout records!\n" + RULE);
            return;
        }
        printHeader("Checkout Records");
        for (CheckoutRecord record : all)
        {
            LibraryItem item = catalogue.getItem(record.getSerial());
            out.println(record + " (" + item.getTitle() + ")");
        }
        out.println(RULE);
    }

    /**
     * Prints everything known about one item, found by serial.
     */
    private void showDetails()
    {
        LibraryItem item = catalogue.getItem(readInt("Serial number: "));
        out.println(RULE);
        out.println(item);
        out.println("Status: " + status(item));
        WaitlistEntry[] waiting = catalogue.getWaitlist(item);
        if (waiting.length > 0)
        {
            out.println("Waitlist:");
            for (int i = 0; i < waiting.length; i++)
            {
                out.println("  " + (i + 1) + ". " + waiting[i].getBorrower());
            }
        }
        out.println(RULE);
    }

    /**
     * Searches by title, creator, serial number, or any attribute.
     */
    private void search()
    {
        out.println("Search by: [1] title  [2] creator  [3] serial number"
            + "  [4] any attribute");
        int how = readInt("Choose a search: ");
        LibraryItem[] found;
        if (how == 1)
        {
            found = catalogue.getItemsByTitle(readNonBlank("Title: "));
        }
        else if (how == 2)
        {
            found = catalogue.getItemsByCreator(readNonBlank("Creator: "));
        }
        else if (how == 3)
        {
            found = new LibraryItem[] {
                catalogue.getItem(readInt("Serial number: "))};
        }
        else if (how == 4)
        {
            found = catalogue.searchItems(readNonBlank("Keyword: "));
        }
        else
        {
            out.println("Please choose a number from the menu.");
            return;
        }
        if (found.length == 0)
        {
            out.println("Nothing found.");
        }
        else
        {
            showItems("Search Results", "Nothing found.", found);
        }
    }

    /**
     * Asks for the details of a new item and adds it.
     */
    private void addItem()
    {
        out.println("Type: [1] Book  [2] Magazine  [3] DVD  [4] Device  [5] Game");
        int type = readInt("Choose a type: ");
        if (type < 1 || type > 5)
        {
            out.println("No such type. Returning to the menu.");
            return;
        }
        String title = readNonBlank("Title: ");
        String creator = readNonBlank("Creator: ");
        String description = readLine("Description (can be empty): ").trim();
        LibraryItem item;
        if (type == 1)
        {
            Book book = new Book(title, creator, description,
                readNonBlank("Genre: "), readNonBlank("Language: "),
                readNonBlank("Format (for example Paperback): "),
                readPositiveInt("Page count: "));
            askIsbn(book);
            item = book;
        }
        else if (type == 2)
        {
            Magazine magazine = new Magazine(title, creator, description,
                readNonBlank("Genre: "), readNonBlank("Volume: "),
                readNonBlank("Publication date as printed: "));
            askDateOfIssue(magazine);
            item = magazine;
        }
        else if (type == 3)
        {
            item = new DVD(title, creator, description,
                readNonBlank("Genre: "), readPositiveInt("Number of episodes: "));
        }
        else if (type == 4)
        {
            item = new Device(title, creator, description,
                readNonBlank("Operating system: "),
                readNonBlank("Manufacturer: "), readNonBlank("Model: "),
                readNonBlank("Device type: "));
        }
        else
        {
            item = new Game(title, creator, description,
                readNonBlank("Genre: "), readNonBlank("Platform: "),
                readNonBlank("Rating: "));
        }
        int serial = catalogue.addItem(item);
        out.println("Added item " + serial + ": " + title);
        record("Added item " + serial + " (" + title + ")");
    }

    /**
     * Asks for an optional ISBN until it is empty or valid.
     *
     * @param book the book to give the ISBN to
     */
    private void askIsbn(Book book)
    {
        while (true)
        {
            String text = readLine("ISBN (press Enter to skip): ").trim();
            if (text.isEmpty())
            {
                return;
            }
            try
            {
                book.setIsbn(text);
                return;
            }
            catch (IllegalArgumentException e)
            {
                out.println(e.getMessage() + ". Please try again.");
            }
        }
    }

    /**
     * Asks for an optional date of issue until it is empty or valid.
     *
     * @param magazine the magazine to give the date to
     */
    private void askDateOfIssue(Magazine magazine)
    {
        while (true)
        {
            String text =
                readLine("Date of issue, yyyy-MM-dd (press Enter to skip): ").trim();
            if (text.isEmpty())
            {
                return;
            }
            try
            {
                magazine.setDateOfIssue(text);
                return;
            }
            catch (IllegalArgumentException e)
            {
                out.println(e.getMessage() + ". Please try again.");
            }
        }
    }

    /**
     * Removes an item chosen by serial.
     */
    private void removeItem()
    {
        LibraryItem item = catalogue.getItem(readInt("Serial number: "));
        if (catalogue.removeItem(item))
        {
            out.println("Removed item " + item.getSerial() + ": " + item.getTitle());
            record("Removed item " + item.getSerial() + " (" + item.getTitle() + ")");
        }
        else
        {
            out.println("No such item: " + item.getSerial());
        }
    }

    /**
     * Checks out an item chosen by serial to a named borrower.
     */
    private void borrowItem()
    {
        LibraryItem item = catalogue.getItem(readInt("Serial number: "));
        String borrower = readNonBlank("Borrower name: ");
        if (catalogue.borrowItem(item, borrower))
        {
            out.println("Item " + item.getSerial() + " (" + item.getTitle()
                + ") is now checked out to " + borrower + ".");
            record("Checked out item " + item.getSerial() + " to " + borrower);
        }
        else
        {
            out.println("No such item: " + item.getSerial());
        }
    }

    /**
     * Returns an item chosen by serial and notifies the next person in
     * line, if anyone is waiting.
     */
    private void returnItem()
    {
        LibraryItem item = catalogue.getItem(readInt("Serial number: "));
        String borrower = catalogue.getBorrower(item);
        if (catalogue.returnItem(item))
        {
            out.println("Item " + item.getSerial() + " (" + item.getTitle()
                + ") was returned by " + borrower + ".");
            record("Returned item " + item.getSerial() + " from " + borrower);
            String next = catalogue.getNextInLine(item);
            if (next != null)
            {
                out.println("Notice for " + next + ": item " + item.getSerial()
                    + " (" + item.getTitle() + ") is available and on hold for you.");
            }
        }
        else
        {
            out.println("No such item: " + item.getSerial());
        }
    }

    /**
     * Puts a named person on the waitlist for a checked-out item.
     */
    private void joinWaitlist()
    {
        LibraryItem item = catalogue.getItem(readInt("Serial number: "));
        String borrower = readNonBlank("Name to put on the waitlist: ");
        if (catalogue.joinWaitlist(item, borrower))
        {
            int place = catalogue.getWaitlist(item).length;
            out.println(borrower + " is number " + place
                + " in line for item " + item.getSerial() + ".");
            record(borrower + " joined the waitlist for item " + item.getSerial());
        }
        else
        {
            out.println("No such item: " + item.getSerial());
        }
    }

    /**
     * Prints the most recent successful actions, newest first.
     */
    private void showHistory()
    {
        String[] all = history.toArray(new String[history.size()]);
        if (all.length == 0)
        {
            out.println(RULE + "\nNothing has happened yet.\n" + RULE);
            return;
        }
        printHeader("Recent Activity");
        for (int i = 0; i < all.length && i < HISTORY_SHOWN; i++)
        {
            out.println(all[i]);
        }
        out.println(RULE);
    }

    // ----------------------------------------------------------
    // Reading input (the only place that touches the Scanner)
    // ----------------------------------------------------------

    /**
     * Prints a prompt and reads one whole line. Lines are only ever read
     * with nextLine and printed with println, never used as a format or
     * pattern, so any characters are treated as plain text.
     *
     * @param prompt the prompt to print
     * @return the line the user typed
     * @throws EndOfInput if the stream is closed or has no more lines
     */
    private String readLine(String prompt)
    {
        out.print(prompt);
        try
        {
            if (!in.hasNextLine())
            {
                throw new EndOfInput();
            }
            return in.nextLine();
        }
        catch (NoSuchElementException | IllegalStateException e)
        {
            throw new EndOfInput();
        }
    }

    /**
     * Asks until the user types text that is not blank and not over 100
     * characters.
     *
     * @param prompt the prompt to print
     * @return the text, trimmed
     */
    private String readNonBlank(String prompt)
    {
        while (true)
        {
            String text = readLine(prompt).trim();
            if (text.isEmpty())
            {
                out.println("Text cannot be blank. Please try again.");
            }
            else if (text.length() > MAX_TEXT)
            {
                out.println("Text is over 100 characters. Please try again.");
            }
            else
            {
                return text;
            }
        }
    }

    /**
     * Asks until the user types a whole number. Zero and negative numbers
     * are allowed here; the Catalogue reports "No such item" for them.
     *
     * @param prompt the prompt to print
     * @return the number typed
     */
    private int readInt(String prompt)
    {
        while (true)
        {
            String text = readLine(prompt).trim();
            try
            {
                return Integer.parseInt(text);
            }
            catch (NumberFormatException e)
            {
                out.println("That is not a number. Please try again.");
            }
        }
    }

    /**
     * Asks until the user types a whole number greater than 0.
     *
     * @param prompt the prompt to print
     * @return the number typed
     */
    private int readPositiveInt(String prompt)
    {
        while (true)
        {
            int number = readInt(prompt);
            if (number > 0)
            {
                return number;
            }
            out.println("The number must be greater than 0. Please try again.");
        }
    }

    // ----------------------------------------------------------
    // Output helpers
    // ----------------------------------------------------------

    /**
     * Describes whether an item is available or who has it.
     *
     * @param item the item to describe
     * @return "Available" or "Checked out to NAME"
     */
    private String status(LibraryItem item)
    {
        String borrower = catalogue.getBorrower(item);
        return borrower == null ? "Available" : "Checked out to " + borrower;
    }

    /**
     * Prints a boxed list title.
     *
     * @param heading the title
     */
    private void printHeader(String heading)
    {
        out.println(RULE);
        out.println("| " + heading + " |");
        out.println(RULE);
    }

    /**
     * Remembers a successful action at the front of the activity chain.
     *
     * @param action what happened
     */
    private void record(String action)
    {
        history.add(action);
    }
}
