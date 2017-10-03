package ru.grigoryev.orderbook;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Class for testing OrderBook methods.
 *
 * @author vgrigoryev
 * @since 03.10.2017
 */
public class OrderBookTest {
    /**
     * test parses small xml document and checks correctness of method's work.
     */
    @Test
    public void whenProcessDocumentThenResultMatch() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        OrderBook ob = new OrderBook();
        ob.print();
        assertThat(out.toString(), is("Order book: book-1\nVolume@Price -  Volume@Price\nBID             "
                + "ASK\n96@100.0  -    42@100.0\n14@99.9   -    42@100.01\n126@99.8  -    71@100.1\n85@99.7   "
                + "-    63@100.2\n55@99.2   -    87@100.4\n--------  -    122@100.5\n--------  -    95@109.5\n\r\n"));
    }
}