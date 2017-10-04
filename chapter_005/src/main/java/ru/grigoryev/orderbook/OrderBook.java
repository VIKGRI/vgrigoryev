package ru.grigoryev.orderbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Represents class which is used for processing bid and ask orders for books.
 *
 * @author vgrigoryev
 * @version 1
 * @since 03.10.2017
 */
public class OrderBook {
    /**
     * This method parses input xml-document.
     * @return all entries with names of books as keys - its orders as values
     */
    public Map<Integer, Order> parse() {

        Map<Integer, Order> orders = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("test1.xml"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                this.processLine(line, orders);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    /**
     * This method parses the input line, constructs instances of Order class and put it into orders.
     * @param line input line
     * @param orders collection where orders are stored
     */
    private void processLine(String line, Map<Integer, Order> orders) {
        String[] tokens = line.split("\"");
        if (tokens[0].startsWith("<AddOrder")) {
            String bookId = tokens[1].trim();

            String operation = tokens[3].trim();
            OperationType operationType = Enum.valueOf(OperationType.class, operation);

            String priceStr = tokens[5].trim();
            double price = Double.parseDouble(priceStr);

            String volumeStr = tokens[7].trim();
            int volume = Integer.parseInt(volumeStr);

            String orderIdStr = tokens[9].trim();
            int orderId = Integer.parseInt(orderIdStr);

            orders.put(orderId, new Order(bookId, operationType, price, volume, orderId));
        } else if (tokens[0].startsWith("<DeleteOrder")) {
            String orderIdStr = tokens[3].trim();
            int orderId = Integer.parseInt(orderIdStr);
            orders.remove(orderId);
        }
    }

    /**
     * This method prints the result in the special format.
     */
    public void print() {
        Map<Integer, Order> orders = this.parse();
        Map<String, Set<Order>> result = this.distributeBooks(orders);
        ArrayList<ParticularBookInfo> books = new ArrayList<>();
        for (Map.Entry<String, Set<Order>> book : result.entrySet()) {
           ParticularBookInfo item = this.new ParticularBookInfo(book.getValue(), book.getKey());
           books.add(item);
           Thread t = new Thread(item);
           t.start();
        }
        boolean goOn = false;
        while (!goOn) {
            if (Thread.activeCount() < 3) {
                goOn = true;
            }
        }
        for (ParticularBookInfo p : books) {
            System.out.println(p);
        }
    }

    /**
     * This metod rearranges specified collection and returns it as a set of collections each of which is related to special book.
     * @param orders all orders
     * @return Map of entries with book names as keys and set of orders for these books as values
     */
    private Map<String, Set<Order>> distributeBooks(Map<Integer, Order> orders) {
        Map<String, Set<Order>> orderBooks = new TreeMap<>();
        for (Map.Entry<Integer, Order> order : orders.entrySet()) {
            Set<Order> value = orderBooks.get(order.getValue().getBookId());
            if (value == null) {
                Set<Order> newBook = new LinkedHashSet<>();
                newBook.add(order.getValue());
                orderBooks.put(order.getValue().getBookId(), newBook);
            } else {
                value.add(order.getValue());
            }
        }
        return orderBooks;
    }

    /**
     * Class implements Runnable. It's used for performing calculations over one book concurrently.
     */
    class ParticularBookInfo implements Runnable {
        /**
         * Bid orders for this book.
         */
        private TreeMap<Double, Integer> bids;
        /**
         * Ask orders for this book.
         */
        private TreeMap<Double, Integer> asks;
        /**
         * All orders for this book.
         */
        private Set<Order> common;
        /**
         * Book's id.
         */
        private String bookId;

        /**
         * Constructor.
         * @param common All orders for this book
         * @param bookId Book's id
         */
        ParticularBookInfo(Set<Order> common, String bookId) {
            this.common = common;
            this.bookId = bookId;
            this.bids = new TreeMap<>(new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    return -Double.compare(o1, o2);
                }
            });
            this.asks = new TreeMap<>();
        }

        @Override
        public void run() {
            for (Order order : this.common) {
                if (order.getOperationType() == OperationType.BUY) {
                    Iterator<Map.Entry<Double, Integer>> it = this.asks.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<Double, Integer> ask = it.next();
                        if (order.getPrice() >= ask.getKey()) {
                            int min = Math.min(order.getVolume(), ask.getValue());
                            if (order.getVolume() > ask.getValue()) {
                                it.remove();
                                order.setVolume(order.getVolume() - min);
                            } else if (order.getVolume() < ask.getValue()) {
                                order.setVolume(0);
                                ask.setValue(ask.getValue() - min);
                                break;
                            } else {
                                it.remove();
                                order.setVolume(0);
                                break;
                            }
                        }
                    }
                    if (order.getVolume() != 0) {
                        Integer value = this.bids.get(order.getPrice());
                        this.bids.put(order.getPrice(), (value == null ? 0 : value) + order.getVolume());
                    }
                } else {
                    Iterator<Map.Entry<Double, Integer>> it = this.bids.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<Double, Integer> bid = it.next();
                        if (order.getPrice() <= bid.getKey()) {
                            int min = Math.min(order.getVolume(), bid.getValue());
                            if (order.getVolume() > bid.getValue()) {
                                it.remove();
                                order.setVolume(order.getVolume() - min);
                            } else if (order.getVolume() < bid.getValue()) {
                                order.setVolume(0);
                                bid.setValue(bid.getValue() - min);
                                break;
                            } else {
                                it.remove();
                                order.setVolume(0);
                                break;
                            }
                        }
                    }
                    if (order.getVolume() != 0) {
                        Integer value = this.asks.get(order.getPrice());
                        this.asks.put(order.getPrice(), (value == null ? 0 : value) + order.getVolume());
                    }
                }
            }
        }

        @Override
        public String toString() {
            Iterator<Map.Entry<Double, Integer>> bid = this.bids.entrySet().iterator();
            Iterator<Map.Entry<Double, Integer>> ask = this.asks.entrySet().iterator();

            StringBuilder result = new StringBuilder();
            Map.Entry<Double, Integer> currentBid;
            Map.Entry<Double, Integer> currentAsk;
            String bidStr;
            String askStr;
            result.append("Order book: " + this.bookId + "\n");
            result.append("Volume@Price" + " - " + " Volume@Price\n");
            result.append("BID" + "             " + "ASK\n");
            while (bid.hasNext() || ask.hasNext()) {
                currentBid = bid.hasNext() ?  bid.next() : null;
                currentAsk = ask.hasNext() ? ask.next() : null;
                bidStr = currentBid == null ? "--------" : "" + currentBid.getValue() + "@" + currentBid.getKey();
                askStr = currentAsk == null ? "--------\n" : "" + currentAsk.getValue() + "@" + currentAsk.getKey() + "\n";
                result.append(bidStr);
                int i = bidStr.length();
                while (i < 10) {
                    result.append(" ");
                    i++;
                }
                result.append("-");
                result.append("    ");
                result.append(askStr);
            }
            return result.toString();
        }
    }
}

