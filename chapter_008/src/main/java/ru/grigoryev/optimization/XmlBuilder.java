package ru.grigoryev.optimization;

import java.util.List;

/**
 * Provides methods for building
 * and parsing xml file.
 *
 * @author vgrigoryev
 * @version 1
 * @since 29.10.2017
 */
public interface XmlBuilder {
    /**
     * Constructs XML document with specified name based on
     * list of results.
     *
     * @param results data list used to build xml file
     * @param fileName specified name for XML document
     */
    void buildXml(List<String> results, String fileName);

    /**
     * Parses XML document and returns list
     * of integers stored in the xml file.
     *
     * @param filePath Specified file path to parse and compute
     * @return list of integers stored in the xml file
     */
    List<Integer> parseXml(String filePath);

}