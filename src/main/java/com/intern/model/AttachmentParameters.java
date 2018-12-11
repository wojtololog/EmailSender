package com.intern.model;

public class AttachmentParameters {
    private String path;
    private String fileName;

    public AttachmentParameters(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }
}
