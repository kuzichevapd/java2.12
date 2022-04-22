package main;

import org.kohsuke.args4j.*;

import java.io.File;
import java.io.IOException;


public class Main {
    @Argument(usage = "Команда split")
    private String command;

    @Argument(required = true, index = 1, usage = "Имя входного файла")
    private File inputFile;

    @Option(name = "-o", usage = "Базовое имя выходного файла")
    private String outputFileName;

    @Option(name = "-d", usage = "Выходные файлы следует называть ofile1, ofile2.." +
            " или же ofilea, ofileb.. при отсутствии флага ")
    private boolean fileFormat;

    @Option(name = "-l", forbids = {"-n", "-c"}, usage = "Указывает размер выходных файлов в строчках(По умолчанию - 100)")
    private int sizeOfOutputFilesInLines;

    @Option(name = "-c", forbids = {"-l", "-n"}, usage = "Указывает размер выходных файлов в символах")
    private int sizeOfOutputFilesInChars;

    @Option(name = "-n", forbids = {"-l", "-c"}, usage = "Указывает количество выходных файлов")
    private int amountOfOutputFiles;


    public static void main(String[] args) throws IOException {
        new Main().launch(args);
    }

    private void launch(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        Main main = new Main();
        try {
            Split spl = new Split(main);
            spl.createFiles();
        } catch (IOException e) {
            throw new IOException();
        }
        try {
            Split spl = new Split(main);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    public int getSizeOfOutputFilesInChars() {
        return sizeOfOutputFilesInChars;
    }

    public File getInputFile() {
        return inputFile;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public boolean isFileFormat() {
        return fileFormat;
    }

    public int getSizeOfOutputFilesInLines() {
        return sizeOfOutputFilesInLines;
    }

    public int getAmountOfOutputFiles() {
        return amountOfOutputFiles;
    }
}
