package ru.grigoryev.orderbook;

/**
 *Class represents book's order.
 * @author grigoryeev
 * @since 02.10.2017
 * @version 1
 */
public class Order {
    /**
     * book's id.
     */
    private String bookId;
    /**
     * operation's type.
     */
    private OperationType operationType;
    /**
     * book's price.
     */
    private double price;
    /**
     * amount of books in the order.
     */
    private int volume;
    /**
     * order's id.
     */
    private int orderId;

    /**
     * Constructor.
     * @param bookId book's id.
     * @param operationType operation's type.
     * @param price book's price.
     * @param volume amount of books in the order
     * @param orderId order's id
     */
    public Order(String bookId, OperationType operationType, double price, int volume, int orderId) {
        if (bookId != null) {
            this.bookId = bookId;
        }
        this.operationType = operationType;
        if (price >= 0) {
            this.price = price;
        }
        if (volume > 0) {
            this.volume = volume;
        }
        if (orderId > 0) {
            this.orderId = orderId;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        return this.orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return this.orderId;
    }

    /**
     * Book id's getter.
     * @return book's id
     */
    public String getBookId() {
        return this.bookId;
    }

    /**
     * Operation type's getter.
     * @return operation's type
     */
    public OperationType getOperationType() {
        return this.operationType;
    }

    /**
     * Book price's getter.
     * @return book's price in the order
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Volume's getter.
     * @return amount of books in the order
     */
    public int getVolume() {
        return this.volume;
    }

    /**
     * Order's id.
     * @return order's id
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Book id's setter.
     * @param bookId book's id
     */
    public void setBookId(String bookId) {
        if (bookId != null) {
            this.bookId = bookId;
        }
    }

    /**
     * Operation type's setter.
     * @param operationType operation's type
     */
    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    /**
     * Book price's setter.
     * @param price book's price in the order
     */
    public void setPrice(double price) {
        if (price > 0) {
            this.price = price;
        }
    }

    /**
     * Volume's setter.
     * @param volume amount of books in the order
     */
    public void setVolume(int volume) {
        if (volume >= 0) {
            this.volume = volume;
        }
    }

    /**
     * Order id's setter.
     * @param orderId irder's id
     */
    public void setOrderId(int orderId) {
        if (orderId > 0) {
            this.orderId = orderId;
        }
    }

    @Override
    public String toString() {
        return "Order{"
                + "bookId='" + this.bookId + '\''
                + ", operationType=" + this.operationType
                + ", price=" + this.price
                + ", volume=" + this.volume
                + ", orderId=" + this.orderId + '}';
    }
}
/**
 * Enumiration represents type of operation.
 */
enum OperationType {
    /**
     * Sell and buy operations.
     */
    SELL, BUY
}
