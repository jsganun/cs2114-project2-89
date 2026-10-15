package Classes;

import java.util.Scanner;

/**
 * Main
 * Builds a sample Catalogue, hands it to a ConsoleMenu, and runs it.
 * All reading and printing happens in ConsoleMenu.
 *
 * To run: java Classes.Main (see the README for the full commands).
 *
 * @author Jonathan Ganun (sample catalogue); menu hand-off revised with
 *         Claude Code
 * @version 10/15/2026
 */
public class Main {

    /**
     * Starts LibCatS with the sample catalogue and the keyboard and
     * screen as input and output.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        Catalogue catalogue = createSampleCatalogue();
        ConsoleMenu menu = new ConsoleMenu(catalogue, new Scanner(System.in),
            System.out);
        menu.run();
    }

    /**
     * Creates a sample catalogue for prototyping purposes
     * @return a sample catalogue
     */
    public static Catalogue createSampleCatalogue() {
        // Create the catalogue object
        Catalogue catalogue = new Catalogue();

        // Load sample items into the catalogue
        Book hobbit = new Book("The Hobbit", "J.R.R. Tolkien",
            "A fantasy adventure in Middle-earth.", "Fantasy", "English",
            "Hardcover", 310);
        hobbit.setIsbn("978-0-547-92822-7");
        catalogue.addItem(hobbit);
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

        Magazine natGeo = new Magazine("National Geographic", "Susan Goldberg",
            "A magazine covering science, exploration, and culture.",
            "Science", "Vol. 245, No. 3", "March 2024");
        natGeo.setDateOfIssue("2024-03-01");
        catalogue.addItem(natGeo);
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
}
