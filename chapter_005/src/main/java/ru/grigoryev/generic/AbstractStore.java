package ru.grigoryev.generic;

/**
 * Abstract class which represents the Storage of elements.
 * @author vgrigoryev
 * @since 22.09.2017
 * @version  1
 * @param <T> Type of elements stored.
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {
    /**
     * Inner array which is used to store elements.
     */
    private SimpleArray<T> objects;

    /**
     *Constructor.
     * @param size size of array.
     */
    public AbstractStore(int size) {
        this.objects = new SimpleArray<>(size);
    }
    @Override
    public boolean add(T value) {
       return objects.add(value);
    }

    @Override
    public boolean update(T value) {
        return objects.update(value);
    }

    @Override
    public boolean delete(T value) {
        return objects.delete(value);
    }

    @Override
    public boolean contains(T value) {
        return objects.contains(value);
    }
}
