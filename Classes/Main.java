package Classes;

import java.util.Scanner;

public class Main {
    
    private static Catalogue catalogue;
    private static Scanner input;
    private static boolean running;

    public static void main(String[] args) {

        // Create sample catalogue
        catalogue = createSampleCatalogue();
        
        // Instantiate input
        input = new Scanner(System.in);

        // Begin prototype program
        run();
    }

    /**
     * Creates a sample catalogue for prototyping purposes
     * @return a sample catalogue
     */
    private static Catalogue createSampleCatalogue() {
        // Create the catalogue object
        Catalogue catalogue = new Catalogue();

        // Load sample items into the catalogue
        catalogue.addItem(new Book("The Hobbit", "J.R.R. Tolkien",
            "A fantasy adventure in Middle-earth.", "Fantasy", "English",
            "Hardcover", 310));
        catalogue.addItem(new Book("Kindred", "Octavia E. Butler",
            "A science-fiction novel about time travel and family.",
            "Science Fiction", "English", "Paperback", 264));
        catalogue.addItem(new Book("The Left Hand of Darkness", "Ursula K. Le Guin",
            "An envoy visits a world with an unusual society.",
            "Science Fiction", "English", "Paperback", 304));

        catalogue.addItem(new DVD("Planet Earth", "David Attenborough",
            "A documentary series exploring Earth's ecosystems.",
            "Documentary", 11));
        catalogue.addItem(new DVD("Blue Planet II", "David Attenborough",
            "A nature series about life in the oceans.", "Documentary", 7));
        catalogue.addItem(new DVD("The Good Place", "Michael Schur",
            "A comedy series about the afterlife.", "Comedy", 53));

        catalogue.addItem(new Game("Stardew Valley", "ConcernedApe",
            "A farming and community simulation game.", "Simulation",
            "PC", "E"));
        catalogue.addItem(new Game("Celeste", "Maddy Makes Games",
            "A platforming adventure about climbing a mountain.",
            "Platformer", "Nintendo Switch", "E"));
        catalogue.addItem(new Game("Hades", "Supergiant Games",
            "A mythological action game about escaping the underworld.",
            "Roguelike", "PlayStation 5", "T"));

        catalogue.addItem(new Magazine("National Geographic", "Susan Goldberg",
            "A magazine covering science, exploration, and culture.",
            "Science", "Vol. 245, No. 3", "March 2024"));
        catalogue.addItem(new Magazine("Smithsonian", "Kevin Gover",
            "A magazine about history, science, and the arts.",
            "Culture", "Vol. 54, No. 8", "November 2023"));
        catalogue.addItem(new Magazine("The Atlantic", "Jeffrey Goldberg",
            "A magazine featuring reporting and commentary.",
            "News", "Vol. 332, No. 4", "October 2023"));

        catalogue.addItem(new Device("Library Laptop 01", "Library Services",
            "A laptop available for in-library use.", "Windows 11", "Dell",
            "Latitude 5440", "Laptop"));
        catalogue.addItem(new Device("Library Tablet 01", "Library Services",
            "A tablet available for reading and research.", "iPadOS", "Apple",
            "iPad Air", "Tablet"));
        catalogue.addItem(new Device("Library E-reader 01", "Library Services",
            "An e-reader available for borrowing.", "Linux", "Kobo",
            "Clara 2E", "E-reader"));

        // Add checkout records for some of the items
        catalogue.borrowItem(catalogue.getItemsByTitle("The Hobbit")[0],
            "Alex Morgan");
        catalogue.borrowItem(catalogue.getItemsByTitle("Planet Earth")[0],
            "Jamie Lee");
        catalogue.borrowItem(catalogue.getItemsByTitle("Stardew Valley")[0],
            "Taylor Reed");
        catalogue.borrowItem(catalogue.getItemsByTitle("National Geographic")[0],
            "Jordan Kim");
        catalogue.borrowItem(catalogue.getItemsByTitle("Library Laptop 01")[0],
            "Casey Patel");
        
        // Return the sample catalogue
        return catalogue;
    }

    private static void run() {
        running = false; // Turn to true after, this is to prevent while true loop for now.

        while (running) {
            System.out.println("Select an option:");
            System.out.println("[1] Show all catalogue entries");
            System.out.println("[2] Show all returned catalogue entries");
            System.out.println("[3] Show all borrowed catalogue entries");
            System.out.println("[4] Show all checkout records");
            System.out.println("[5] Add a new item to the catalogue");
            System.out.println("[6] Remove an item from the catalogue");
            System.out.println("[7] Show All Catalogue Entries");
            System.out.println("[8] Show All Catalogue Entries");

        }
    }

    private static void showWholeCatalogue() {
        LibraryItem[] allItems = catalogue.getAllItems();

        if (allItems.length == 0) {
            System.out.println(
                "--------------------------------------------------\n" +
                "There are no items in the catalogue!\n" +
                "--------------------------------------------------"
            );
        }
        else {
            System.out.println("--------------------------------------------------");
            System.out.println("|               All Catalogue Items              |");
            System.out.println("--------------------------------------------------");
            for (int i = 0; i < allItems.length; i++) {
                LibraryItem item = allItems[i];
                System.out.println(item);
                if (i + 1 != allItems.length) {
                    System.out.println("\n");
                }
            }
            System.out.println("--------------------------------------------------");
        }
    }

    private static void showReturnedCatalogue() {
        LibraryItem[] returnedItems = catalogue.getAllReturnedItems();

        if (returnedItems.length == 0) {
            System.out.println(
                "--------------------------------------------------\n" +
                "There are no returned items in the catalogue!\n" +
                "--------------------------------------------------"
            );
        }
        else {
            System.out.println("--------------------------------------------------");
            System.out.println("|               All Returned Items               |");
            System.out.println("--------------------------------------------------");
            for (int i = 0; i < returnedItems.length; i++) {
                LibraryItem item = returnedItems[i];
                System.out.println(item);
                if (i + 1 != returnedItems.length) {
                    System.out.println("\n");
                }
            }
            System.out.println("--------------------------------------------------");
        }
    }

    private static void showBorrowedCatalogue() {
        LibraryItem[] borrowedItems = catalogue.getAllBorrowedItems();

        if (borrowedItems.length == 0) {
            System.out.println(
                "--------------------------------------------------\n" +
                "There are no borrowed items in the catalogue!\n" +
                "--------------------------------------------------"
            );
        }
        else {
            System.out.println("--------------------------------------------------");
            System.out.println("|               All Borrowed Items               |");
            System.out.println("--------------------------------------------------");
            for (int i = 0; i < borrowedItems.length; i++) {
                LibraryItem item = borrowedItems[i];
                System.out.println(item);
                if (i + 1 != borrowedItems.length) {
                    System.out.println("\n");
                }
            }
            System.out.println("--------------------------------------------------");
        }
    }

    private static void showCheckoutRecords() {
        CheckoutRecord[] records = catalogue.getAllCheckoutRecords();

        if (records.length == 0) {
            System.out.println(
                "--------------------------------------------------\n" +
                "There are no checkout records!\n" +
                "--------------------------------------------------"
            );
        }
        else {
            System.out.println("--------------------------------------------------");
            System.out.println("|                Checkout Records                |");
            System.out.println("--------------------------------------------------");
            for (int i = 0; i < records.length; i++) {
                CheckoutRecord record = records[i];
                System.out.println(record);
                if (i + 1 != records.length) {
                    System.out.println("\n");
                }
            }
            System.out.println("--------------------------------------------------");
        }
    }

    private static void addItem() {

    }

    private static void removeItem() {

    }

    private static void borrowItem() {

    }

    private static void returnItem() {

    }

    private static void exit() {
        System.exit(0);
    }
}
