package main;

import org.kohsuke.args4j.*;

import java.io.IOException;
import java.util.Objects;

public class Main {
    @Argument(required = true, usage = "Команда split")
    private String command;

    @Argument(required = true, index = 1, usage = "Имя входного файла")
    private String inputFileName;

    @Option(name = "-o", usage = "Базовое имя выходного файла")
    private String outputFileName;

    @Option(name = "-d", usage = "Выходные файлы следует называть ofile1, ofile2.." +
            " или же ofilea, ofileb.. при отсутствии флага ")
    private boolean fileFormat;

    @Option(name = "-l", usage = "Указывает размер выходных файлов в строчках(По умолчанию - 100)")
    private int sizeOfOutputFilesinLines;

    @Option(name = "-c", usage = "Указывает размер выходных файлов в символах")
    private int sizeOfOutputFilesinChars;

    @Option(name = "-n", usage = "Указывает количество выходных файлов")
    private int amountOfOutputFiles;

    public static void main(String[] args) {
        new Main().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("split [-d] [-l num|-c num|-n num] [-o ofile] file");
            parser.printUsage(System.err);
            return;
        }
        Split spl = null;
        try {
            spl = new Split(fileFormat, sizeOfOutputFilesinLines, sizeOfOutputFilesinChars,
                    amountOfOutputFiles, outputFileName, inputFileName);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        try {
            spl.createFiles();
        } catch (IOException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Main main = (Main) o;
        return fileFormat == main.fileFormat && sizeOfOutputFilesinLines == main.sizeOfOutputFilesinLines && sizeOfOutputFilesinChars == main.sizeOfOutputFilesinChars && amountOfOutputFiles == main.amountOfOutputFiles && Objects.equals(command, main.command) && Objects.equals(inputFileName, main.inputFileName) && Objects.equals(outputFileName, main.outputFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, inputFileName, outputFileName, fileFormat, sizeOfOutputFilesinLines, sizeOfOutputFilesinChars, amountOfOutputFiles);
    }
}
