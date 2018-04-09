package com.wan.sys.entity.file;

import java.io.Serializable;

public class UploadFile implements Serializable{

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UploadFile(String path) { this.path = path; }
}
