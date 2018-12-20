package com.intern.model;

/**
 * It holds e-mail message attachment parameters
 */
public class AttachmentParameters {
    /**
     * this is a absolute path to attached file
     */
    private String path;
    /**
     * this is a filename of attached file
     */
    private String fileName;

    /**
     * Initialize class fields
     * @param path absolute path to attached file
     * @param fileName a filename of attached file
     */
    public AttachmentParameters(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    /**
     *
     * @return absolute path to attached file
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @return a filename of attached file
     */
    public String getFileName() {
        return fileName;
    }
}
