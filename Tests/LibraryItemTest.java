package Tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import Classes.LibraryItem;


public class LibraryItemTest {
    
    private LibraryItem item;

    @BeforeEach 
    public void setUp() {
        item = new LibraryItem("How to Larp", "Larp Master", "A guide on how to larp your way through larping.");
    }

    @Test 
    public void testGetTitle() {
        assertEquals("How to Larp", item.getTitle());
    }

    @Test 
    public void testGetDescription() {
        assertEquals("A guide on how to larp your way through larping.", item.getDescription());
    }

    @Test 
    public void testGetCreator() {
        assertEquals("Larp Master", item.getCreator());
    }

    @Test 
    public void testGetRecord() {
        
    }

    @Test 
    public void testGetSerial() {

    }

    @Test 
    public void testSetTitle() {

    }

    @Test 
    public void testSetCreator() {

    }

    @Test 
    public void testSetDescription() {

    }

    @Test 
    public void testSetRecord() {

    }

    @Test 
    public void testEquals() {

    }

    @Test 
    public void testToString() {

    }
}
