package com.youdushufang.dp.command;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class OpenCommand implements Command {

    private final Application application;

    private Document lastOpenedDocument;

    public OpenCommand(Application application) {
        this.application = application;
    }

    @Override
    public void execute() throws CommandException {
        String fileName = askUser();
        if (fileName != null && !"".equals(fileName)) {
            Document document = new Document(fileName);
            try {
                document.open();
            } catch (FileNotFoundException e) {
                throw new CommandException(e, e.getMessage());
            }
            application.add(document);
            lastOpenedDocument = document;
        }
    }

    @Override
    public void undo() throws CommandException {
        if (lastOpenedDocument != null) {
            try {
                lastOpenedDocument.close();
            } catch (FileNotFoundException e) {
                throw new CommandException(e, e.getMessage());
            }
            application.remove(lastOpenedDocument);
            lastOpenedDocument = null;
        }
    }

    private String askUser() {
        String fileName = null;
        System.out.format("Enter file name...%n");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            fileName = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
