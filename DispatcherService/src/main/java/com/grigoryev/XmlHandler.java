package com.grigoryev;

/**
 * Provides methods for building
 * and parsing xml file.
 *
 * @author vgrigoryev
 * @version 1
 * @since 08.02.2018
 */
public interface XmlHandler {
    /**
     * Constructs XML document.
     *
     * @param fileName specified name for XML document
     */
    String buildXml(String fileName, int id);

    /**
     * Parses XML document.
     *
     * @param filePath specified file path
     */
    Integer parseXml(String filePath);

    /**
     * Assign id to the xml file.
     *
     * @param filePath specified file path
     * @param id specified id
     */
    void assignId(String filePath, int id);
}
