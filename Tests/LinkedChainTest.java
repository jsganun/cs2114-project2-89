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

import Classes.LinkedChain;

/**
 * LinkedChainTest
 * Tests the generic LinkedChain, including the changes Group 89 made to
 * the provided class: a tail reference (so addToEnd is O(1)) and add(null)
 * returning false. Each public method has a normal case and a bad-input
 * case, and removing the first, last, and only entry are tested one by one
 * because they are the cases that can break the tail reference.
 *
 * @author Group 89
 * @version 10/15/2026
 */
public class LinkedChainTest {

    private LinkedChain<String> chain;

    /**
     * Creates an empty chain before each test.
     */
    @BeforeEach
    public void setUp() {
        chain = new LinkedChain<>();
    }

    /** Fills the chain with a, b, c in that order. */
    private void fillAbc() {
        chain.addToEnd("a");
        chain.addToEnd("b");
        chain.addToEnd("c");
    }

    // ----------------------------------------------------------
    // Constructor, size, isEmpty
    // ----------------------------------------------------------

    /** A new chain is empty. */
    @Test
    public void testConstructor() {
        assertEquals(0, chain.size());
        assertTrue(chain.isEmpty());
        assertEquals("[]", chain.toString());
    }

    /** size and isEmpty follow adds and removes. */
    @Test
    public void testSizeAndIsEmpty() {
        fillAbc();
        assertEquals(3, chain.size());
        assertFalse(chain.isEmpty());
        chain.clear();
        assertEquals(0, chain.size());
        assertTrue(chain.isEmpty());
    }

    // ----------------------------------------------------------
    // add and addToEnd
    // ----------------------------------------------------------

    /** add puts the entry at the front. */
    @Test
    public void testAdd() {
        assertTrue(chain.add("a"));
        assertTrue(chain.add("b"));
        assertArrayEquals(new String[] {"b", "a"},
            chain.toArray(new String[2]));
    }

    /** add(null) returns false and leaves the chain unchanged. */
    @Test
    public void testAddBadInput() {
        chain.add("a");
        assertFalse(chain.add(null));
        assertEquals(1, chain.size());
    }

    /** addToEnd keeps insertion order. */
    @Test
    public void testAddToEnd() {
        fillAbc();
        assertArrayEquals(new String[] {"a", "b", "c"},
            chain.toArray(new String[3]));
    }

    /** addToEnd(null) returns false and leaves the chain unchanged. */
    @Test
    public void testAddToEndBadInput() {
        assertFalse(chain.addToEnd(null));
        assertTrue(chain.isEmpty());
        chain.addToEnd("a");
        assertFalse(chain.addToEnd(null));
        assertEquals(1, chain.size());
    }

    /** add after addToEnd keeps both ends working. */
    @Test
    public void testMixAddAndAddToEnd() {
        chain.addToEnd("b");
        chain.add("a");
        chain.addToEnd("c");
        assertEquals("[a, b, c]", chain.toString());
    }

    // ----------------------------------------------------------
    // remove()
    // ----------------------------------------------------------

    /** remove() takes the first entry. */
    @Test
    public void testRemoveFirst() {
        fillAbc();
        assertEquals("a", chain.remove());
        assertEquals("[b, c]", chain.toString());
        assertEquals(2, chain.size());
    }

    /** remove() on an empty chain returns null. */
    @Test
    public void testRemoveFirstBadInput() {
        assertNull(chain.remove());
        assertEquals(0, chain.size());
    }

    /** After remove() empties the chain, addToEnd still works. */
    @Test
    public void testRemoveFirstUntilEmptyThenAddToEnd() {
        chain.addToEnd("a");
        assertEquals("a", chain.remove());
        assertTrue(chain.isEmpty());
        chain.addToEnd("b");
        chain.addToEnd("c");
        assertEquals("[b, c]", chain.toString());
    }

    // ----------------------------------------------------------
    // remove(T): first, last, only, middle, absent
    // ----------------------------------------------------------

    /** Removing the first entry leaves the rest in order. */
    @Test
    public void testRemoveEntryFirst() {
        fillAbc();
        assertTrue(chain.remove("a"));
        assertEquals("[b, c]", chain.toString());
        assertEquals(2, chain.size());
    }

    /** Removing the last entry moves the tail, so addToEnd still works. */
    @Test
    public void testRemoveEntryLast() {
        fillAbc();
        assertTrue(chain.remove("c"));
        assertEquals("[a, b]", chain.toString());
        chain.addToEnd("d");
        assertEquals("[a, b, d]", chain.toString());
        assertEquals(3, chain.size());
    }

    /** Removing the only entry leaves a chain that accepts new entries. */
    @Test
    public void testRemoveEntryOnly() {
        chain.addToEnd("a");
        assertTrue(chain.remove("a"));
        assertTrue(chain.isEmpty());
        assertEquals("[]", chain.toString());
        chain.addToEnd("b");
        assertEquals("[b]", chain.toString());
        assertEquals("b", chain.get(0));
    }

    /** Removing a middle entry joins its neighbors. */
    @Test
    public void testRemoveEntryMiddle() {
        fillAbc();
        assertTrue(chain.remove("b"));
        assertEquals("[a, c]", chain.toString());
    }

    /** Removing an absent entry, null, or from an empty chain is false. */
    @Test
    public void testRemoveEntryBadInput() {
        assertFalse(chain.remove("a"));
        fillAbc();
        assertFalse(chain.remove("z"));
        assertFalse(chain.remove(null));
        assertEquals("[a, b, c]", chain.toString());
        assertEquals(3, chain.size());
    }

    /** With duplicates, remove(T) takes only the first one. */
    @Test
    public void testRemoveEntryDuplicates() {
        chain.addToEnd("a");
        chain.addToEnd("x");
        chain.addToEnd("a");
        assertTrue(chain.remove("a"));
        assertEquals("[x, a]", chain.toString());
    }

    // ----------------------------------------------------------
    // clear
    // ----------------------------------------------------------

    /** clear empties the chain and the chain can be refilled. */
    @Test
    public void testClear() {
        fillAbc();
        chain.clear();
        assertTrue(chain.isEmpty());
        chain.addToEnd("x");
        assertEquals("[x]", chain.toString());
    }

    /** clear on an empty chain does nothing. */
    @Test
    public void testClearBadInput() {
        chain.clear();
        assertEquals(0, chain.size());
    }

    // ----------------------------------------------------------
    // get
    // ----------------------------------------------------------

    /** get returns the same object that was added. */
    @Test
    public void testGet() {
        String first = new String("a");
        chain.addToEnd(first);
        chain.addToEnd("b");
        assertSame(first, chain.get(0));
        assertEquals("b", chain.get(1));
    }

    /** get outside 0..size-1 throws IndexOutOfBoundsException. */
    @Test
    public void testGetBadInput() {
        assertThrows(IndexOutOfBoundsException.class, () -> chain.get(0));
        fillAbc();
        assertThrows(IndexOutOfBoundsException.class, () -> chain.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> chain.get(3));
    }

    // ----------------------------------------------------------
    // contains and count
    // ----------------------------------------------------------

    /** contains finds present entries and rejects absent ones. */
    @Test
    public void testContains() {
        fillAbc();
        assertTrue(chain.contains("b"));
        assertFalse(chain.contains("z"));
    }

    /** contains(null) and contains on an empty chain are false. */
    @Test
    public void testContainsBadInput() {
        assertFalse(chain.contains("a"));
        assertFalse(chain.contains(null));
    }

    /** count counts duplicates. */
    @Test
    public void testCount() {
        chain.addToEnd("a");
        chain.addToEnd("b");
        chain.addToEnd("a");
        assertEquals(2, chain.count("a"));
        assertEquals(1, chain.count("b"));
        assertEquals(0, chain.count("z"));
    }

    /** count(null) is 0. */
    @Test
    public void testCountBadInput() {
        assertEquals(0, chain.count(null));
        chain.addToEnd("a");
        assertEquals(0, chain.count(null));
    }

    // ----------------------------------------------------------
    // toArray and toString
    // ----------------------------------------------------------

    /** toArray fills the given array, first entry first. */
    @Test
    public void testToArray() {
        fillAbc();
        String[] given = new String[4];
        String[] result = chain.toArray(given);
        assertSame(given, result);
        assertEquals("a", result[0]);
        assertEquals("c", result[2]);
    }

    /** toArray throws for null or a too-small array; an empty chain
     *  fills a length-0 array. */
    @Test
    public void testToArrayBadInput() {
        assertEquals(0, chain.toArray(new String[0]).length);
        fillAbc();
        assertThrows(IllegalArgumentException.class, () -> chain.toArray(null));
        assertThrows(IllegalArgumentException.class,
            () -> chain.toArray(new String[2]));
    }

    /** toString lists entries in order. */
    @Test
    public void testToString() {
        fillAbc();
        assertEquals("[a, b, c]", chain.toString());
        assertEquals("[]", new LinkedChain<Integer>().toString());
    }

    // ----------------------------------------------------------
    // One generic class, many element types
    // ----------------------------------------------------------

    /** The same class stores unrelated element types. */
    @Test
    public void testManyElementTypes() {
        LinkedChain<Integer> numbers = new LinkedChain<>();
        LinkedChain<Object> things = new LinkedChain<>();
        numbers.addToEnd(7);
        things.addToEnd("text");
        things.addToEnd(3.5);
        assertEquals(7, numbers.get(0));
        assertEquals(3.5, things.get(1));
    }
}
