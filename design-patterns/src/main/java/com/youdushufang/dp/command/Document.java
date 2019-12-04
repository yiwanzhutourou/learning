package com.youdushufang.dp.command;

import lombok.Data;

import java.nio.file.Files;
import java.nio.file.Paths;

@Data
public class Document {

    private final String fileName;

    public Document(String fileName) {
        this.fileName = fileName;
    }

    public void open() throws FileNotFoundException {
        checkFileExists();
        System.out.println("Open: " + this);
    }

    public void close() throws FileNotFoundException {
        checkFileExists();
        System.out.println("Close: " + this);
    }

    private void checkFileExists() throws FileNotFoundException {
        if (!Files.exists(Paths.get(fileName))) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }
}
