package ru.grigoryev.synchronize;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class non-blocking cache.
 *
 * @author vgrigoryev
 * @version 1
 * @since 13.10.2017
 */
public class NonBlock {
    /**
     * Inner container for models.
     */
    private ConcurrentHashMap<Integer, Model> models = new ConcurrentHashMap<>();

    /**
     * Adds specified model to the cache if it hasn't been added.
     * @param model specified model
     */
    public void add(Model model) {
        this.models.putIfAbsent(model.getId(), model);
    }

    /**
     * Deletes the model with specified id.
     * @param id model's id
     */
    public void delete(int id) {
        if (this.models.containsKey(id)) {
            System.out.println("Deleted: ID " + this.models.remove(id).getId());
        }
    }

    /**
     * Updates the model if it hasn't been already updated.
     * @param key model's id
     * @param name model's name
     */
    public void update(int key, String name) {
        Random random = new Random();
        if (this.models.containsKey(key)) {
            Model model = this.models.get(key);
            try {
                Thread.sleep(random.nextInt(400));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            try {
                this.models.computeIfPresent(key, (k, v) -> {
                    if (v.getVersion() == model.getVersion()) {
                        System.out.println(Thread.currentThread() + ": Updated");
                        return new Model(v.getId(), name, v.getVersion() + 1);
                    } else {
                        throw new OptimisticException("Attempt to rewrite data");
                    }

                });
            } catch (OptimisticException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
