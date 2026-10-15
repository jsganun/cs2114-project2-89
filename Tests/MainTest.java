package Tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import Classes.Catalogue;
import Classes.LibraryItem;
import Classes.Main;

/**
 * MainTest
 * Tests Main: the sample catalogue it builds, and that the program starts,
 * shows the menu, and exits cleanly on typed input or on none.
 *
 * @author Group 89
 * @version 10/15/2026
 */
public class MainTest {

    /**
     * Runs Main.main with the given typed input, with System.in and
     * System.out replaced for the duration.
     *
     * @param typed what the user types
     * @return everything the program printed
     */
    private String runMain(String typed) {
        InputStream realIn = System.in;
        PrintStream realOut = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(
                typed.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(bytes, true, StandardCharsets.UTF_8));
            Main.main(new String[0]);
        }
        finally {
            System.setIn(realIn);
            System.setOut(realOut);
        }
        return bytes.toString(StandardCharsets.UTF_8);
    }

    /**
     * Tests that the sample catalogue holds items of all five subclasses
     * in one LinkedChain, with some checked out.
     */
    @Test
    public void testCreateSampleCatalogue() {
        Catalogue sample = Main.createSampleCatalogue();
        assertNotNull(sample);
        LibraryItem[] all = sample.getAllItems();
        assertEquals(15, all.length);
        Set<String> types = new HashSet<>();
        for (LibraryItem item : all) {
            types.add(item.getClass().getSimpleName());
        }
        assertEquals(5, types.size());
        assertEquals(5, sample.getAllBorrowedItems().length);
        assertEquals(10, sample.getAllReturnedItems().length);
    }

    /**
     * Tests that the program starts, shows the menu, and exits on 0.
     */
    @Test
    public void testMain() {
        String shown = runMain("1\n0\n");
        assertTrue(shown.contains("=== LibCatS Library Catalogue ==="));
        assertTrue(shown.contains("The Hobbit"));
        assertTrue(shown.contains("Goodbye."));
    }

    /**
     * Tests that the program exits normally when there is no input at all.
     */
    @Test
    public void testMainEmptyInput() {
        String[] shown = new String[1];
        assertDoesNotThrow(() -> shown[0] = runMain(""));
        assertTrue(shown[0].contains("Goodbye."));
    }
}
