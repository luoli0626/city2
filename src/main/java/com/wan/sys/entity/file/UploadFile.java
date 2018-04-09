package com.wan.sys.entity.file;

public class UploadFile {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UploadFile(String path) { this.path = path; }
}
