package ru.grigoryev.maps;

/**
 * Pair of key and value which is a associated with a key.
 *
 * @param <K> type of key
 * @param <V> type of value
 * @author vgrigoryev
 * @version 1
 * @since 28.09.2017
 */
public class Pair<K, V> {
    /**
     *Key by which value is associated.
     */
    private K key;
    /**
     *Value which is a associated with a key.
     */
    private V value;

    /**
     *Constructor.
     * @param key specified key
     * @param value specified value associated with this key
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Key's getter.
     * @return key of this pair
     */
    public K getKey() {
        return key;
    }

    /**
     * Value's getter.
     * @return value which is a associated with a key
     */
    public V getValue() {
        return value;
    }

    /**
     * Key's setter.
     * @param key key of this pair
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Value's setter.
     * @param value value which is a associated with a key
     */
    public void setValue(V value) {
        this.value = value;
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

        return key != null ? key.equals(pair.key) : pair.key == null;
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Pair{"
                + "key=" + key
                + ", value=" + value
                + '}';
    }
}
