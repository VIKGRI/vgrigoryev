package ru.grigoryev.synchronize;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Class for testing thread safe ParallelSearch class.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.10.2017
 */
public class ParallelSearchTest {
    /**
     * Testing result method.
     */
    @Test
    public void whenParallelSearchingThenMainThreadIsWaiting() {
        List<String> exts = new ArrayList<>();
        exts.add("txt");
        ParallelSearch p = new ParallelSearch();
        List<String> result = p.result("C:\\Users\\admin\\Desktop\\cpp\\java\\Курс\\Пакет Стандарт\\Часть 007. Multithreading",
                 "Hello", exts);
        if (result != null) {
            for (String path : result) {
                System.out.println(path);
            }
        } else {
            System.out.println("Files are not found");
        }
    }
}