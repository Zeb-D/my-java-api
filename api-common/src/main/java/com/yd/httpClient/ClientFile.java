package com.yd.httpClient;

import com.yd.common.util.Checker;
import com.yd.common.util.stream.HttpTyper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClientFile {
    private String name;
    private String contentType;
    private InputStream inputStream;

    public ClientFile(String filepath) throws FileNotFoundException {
        this(new File(filepath));
    }

    public ClientFile(File file) throws FileNotFoundException {
        this.name = file.getName();
        this.contentType = HttpTyper.getContentTypeFromFileName(this.name);
        this.inputStream = new FileInputStream(file);
    }

    public ClientFile(String name, String contentType, InputStream inputStream) {
        this.name = name;
        this.contentType = contentType;
        this.inputStream = (InputStream) Checker.checkNotNull(inputStream);
    }

    public String getName() {
        return this.name;
    }

    public String getContentType() {
        return this.contentType;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }
}
