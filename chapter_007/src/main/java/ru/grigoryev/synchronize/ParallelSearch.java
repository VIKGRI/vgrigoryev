package ru.grigoryev.synchronize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of thread safe parallel text searching .
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.10.2017
 */
@ThreadSafe
public class ParallelSearch {
    /**
     * List of matched files.
     */
    @GuardedBy("lock")
    private List<String> result = new ArrayList<>();
    /**
     * List of created threads.
     */
    @GuardedBy("lock")
    private List<Thread> threads = new ArrayList<>();
    /**
     * lock for shared data.
     */
    private Lock lock = new ReentrantLock();
    /**
     * Condition for getting information about alive threads.
     */
    private Condition allDirectoriesExplored = lock.newCondition();

    /**
     * Constructor.
     */
    public ParallelSearch() {
        this.result = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.lock = new ReentrantLock();
        this.allDirectoriesExplored = this.lock.newCondition();
    }

    /**
     * Searches the specified text in the directory tree
     * starting with root in the files with extensions specified in the exts.
     *
     * @param root root directory where to find matches
     * @param text template text
     * @param exts available extensions
     */
    private void search(String root, String text, List<String> exts) {
        String searchText = text.trim();
        File[] files = new File(root).listFiles();
        if (files != null) {
            for (File file : files) {
                File threadFile = file;
                boolean isExtensionSuitable = false;
                if (threadFile.isFile()) {
                    for (String ext : exts) {
                        if (threadFile.getName().endsWith(ext)) {
                            isExtensionSuitable = true;
                            break;
                        }
                    }
                }
                if (isExtensionSuitable && threadFile.canRead()) {
                    Runnable fileSearcher = new Runnable() {
                        @Override
                        public void run() {
                            try (BufferedReader reader = new BufferedReader(new FileReader(threadFile))) {
                                String line;
                                StringBuilder builder = new StringBuilder();
                                builder.append(" ");
                                while ((line = reader.readLine()) != null) {
                                    builder.append(line + " ");
                                }
                                String[] parse = builder.toString().split(searchText);
                                if (parse.length >= 2) {
                                    add(threadFile.getAbsolutePath());
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            lock.lock();
                            try {
                                allDirectoriesExplored.signalAll();
                            } finally {
                                lock.unlock();
                            }
                        }
                    };
                    Thread t = new Thread(fileSearcher);
                    t.start();
                    lock.lock();
                    try {
                        this.threads.add(t);
                    } finally {
                        lock.unlock();
                    }
                } else if (threadFile.isDirectory()) {
                    search(threadFile.getAbsolutePath(), searchText, exts);
                    lock.lock();
                    try {
                        allDirectoriesExplored.signalAll();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
    }

    /**
     * Searches the specified text in the directory tree
     * starting with root in the files with extensions specified in the exts.
     *
     * @param root root directory where to find matches
     * @param text template text
     * @param exts available extensions
     * @return list of file paths where the text is found
     */
    public List<String> result(String root, String text, List<String> exts) {
        lock.lock();
        try {
            this.search(root, text, exts);
            while (this.aliveThreads() > 0) {
                try {
                    allDirectoriesExplored.await();
                    if (aliveThreads() == 1) {
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            return this.result.size() == 0 ? null : this.result;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Adds file path in the result list.
     *
     * @param filePath specified file path.
     */
    private void add(String filePath) {
        lock.lock();
        try {
            this.result.add(filePath);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the amount of alive threads.
     *
     * @return the number of alive threads
     */
    private int aliveThreads() {
        Iterator<Thread> it = this.threads.iterator();
        Thread current;
        lock.lock();
        try {
            while (it.hasNext()) {
                current = it.next();
                if (!current.isAlive()) {
                    it.remove();
                }
            }
            return this.threads.size();
        } finally {
            lock.unlock();
        }
    }
}
