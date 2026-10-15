package Tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import Classes.DVD;
public class DVDTest

    
    {
        private DVD dvd;
        @BeforeEach
        public void setUp()
        {
            DVD dvd = new DVD("Inception", "Christopher Nolan", "A mind-bending thriller", "Science Fiction", 148);
        }
        /**
        * Tests the getGenre method.
        */
        @Test
        public void testGetGenre()
        {
            assertEqual("Science Fiction", dvd.getGenre());
    
        }
        /*
        * Tests the getNumEpisodes method.
         */
        @Test
        public void testGetNumEpisodes()
        {
            assertEqual(148, dvd.getNumEpisodes());
    
        }
        /**
         * Tests the setGenre method.
         */
        @Test
        public void testSetGenre()
        {
            dvd.setGenre("Action");
            assertEqual("Action", dvd.getGenre());
    
        }
        /**
         * Tests the setNumEpisodes method.
         */
        @Test
        public void testSetNumEpisodes()
        {
            dvd.setNumEpisodes(5);
            assertEqual(5, dvd.getNumEpisodes());
        }
    }