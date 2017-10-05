package ru.grigoryev.threads;

/**
 * Pair of key and value which is a associated with a key.
 *
 * @param <K> type of first value
 * @param <V> type of second value
 * @author vgrigoryev
 * @version 1
 * @since 05.10.2017
 */
public class Pair<K, V> {
    /**
     * First value.
     */
    private K first;
    /**
     * Second value.
     */
    private V second;

    /**
     *Constructor.
     * @param first specified first value
     * @param second specified second value
     */
    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    /**
     * First value's getter.
     * @return first value
     */
    public K getFirst() {
        return this.first;
    }

    /**
     * Second value's getter.
     * @return second value
     */
    public V getSecond() {
        return this.second;
    }

    /**
     * First value's setter.
     * @param first first value
     */
    public void seFirst(K first) {
        this.first = first;
    }

    /**
     * Second value's setter.
     * @param second second value
     */
    public void setSecond(V second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (first != null ? !first.equals(pair.first) : pair.first != null) {
            return false;
        }
        return second != null ? second.equals(pair.second) : pair.second == null;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
