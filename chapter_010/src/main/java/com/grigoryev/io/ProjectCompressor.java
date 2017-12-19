package com.grigoryev.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Provides services for
 * file conversion to zip-file.
 *
 * @author vgrigoryev
 * @version 1
 * @since 19.12.2017
 */
public class ProjectCompressor {
    /**
     * Parent folder which contains folder
     * which will be archived.
     */
    private String projectFolder;
    /**
     * Extensions available for files
     * to archive.
     */
    private Set<String> extensions;

    /**
     * Gets project's parent folder.
     * @return project's parent folder
     */
    public String getProjectFolder() {
        return projectFolder;
    }

    /**
     * Sets project's parent folder.
     * @param projectFolder project's parent folder
     */
    public void setProjectFolder(String projectFolder) {
        this.projectFolder = projectFolder;
    }

    /**
     * Gets extensions for files.
     * @return extensions for files
     */
    public Set<String> getExtensions() {
        return extensions;
    }

    /**
     * Sets extensions for files.
     * @param extensions extensions for files
     */
    public void setExtensions(Set<String> extensions) {
        this.extensions = extensions;
    }

    /**
     * main function.
     * @param args args got from cl
     * @throws IOException I/O exception
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 6 || !args[0].equals("-d") || !args[2].equals("-e") || !args[4].equals("-o")) {
            System.out.println("To zip folder type: java -jar pack.jar -d <YOUR FOLDER> -e <YOUR EXTENSIONS DIVIDED BY ,> -o <NAME OF ZIP FILE>");
        } else {
            ProjectCompressor pc = new ProjectCompressor();
            String sourceFileName = args[1];
            pc.setProjectFolder(new File(sourceFileName).getParentFile().getAbsolutePath());
            String[] exts = args[3].split(",");
            Set<String> extensions = new HashSet<>();
            extensions.addAll(Arrays.asList(exts));
            pc.setExtensions(extensions);

            String zipFileName = args[5];

            try (FileOutputStream fos = new FileOutputStream(String.format("%1$s\\%2$s",
                    pc.getProjectFolder(), zipFileName));
                 ZipOutputStream zos = new ZipOutputStream(fos)) {
                pc.zipFile(new File(sourceFileName).getAbsolutePath(), zos);
            }
            System.out.println("Zip file is created");
        }
    }

    /**
     * Checks whether this file has available extension
     * or not.
     *
     * @param fileName specified file name
     * @return true if file is available and false otherwise
     */
    private boolean doesExtensionMatch(String fileName) {
        boolean contains = false;
        for (String extension : this.extensions) {
            if (fileName.endsWith(String.format(".%s", extension))) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    /**
     * Converts file to zip.
     *
     * @param sourceFileName source file name
     * @param zout zip output stream
     * @throws IOException input/output exception
     */
    private void zipFile(String sourceFileName, ZipOutputStream zout)
            throws IOException {
        File[] files = new File(sourceFileName).listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getAbsolutePath();
                if (file.isDirectory()) {
                    zipFile(file.getAbsolutePath(), zout);
                } else {
                    if (this.doesExtensionMatch(fileName)) {
                        try (FileInputStream fis = new FileInputStream(fileName)) {
                            if (fileName.startsWith(this.projectFolder)) {
                                fileName = fileName.substring(this.projectFolder.length() + 1);
                            }
                            zout.putNextEntry(new ZipEntry(fileName));
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = fis.read(buffer)) > 0) {
                                zout.write(buffer, 0, length);
                            }
                            zout.closeEntry();
                        }
                    }
                }
            }
        }
    }
}
