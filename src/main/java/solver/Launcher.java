package solver;

import org.kohsuke.args4j.*;

import java.io.File;
import java.io.IOException;


public class Launcher {
    @Argument(required = true, usage = "Имя входного файла")
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
        new Launcher().launch(args);
    }

    private void launch(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        if (sizeOfOutputFilesInLines < 0 || sizeOfOutputFilesInChars < 0
                || amountOfOutputFiles < 0)
            throw new IllegalArgumentException("Числовые значения не могут быть меньше нуля");
        if (inputFile.isDirectory()) {
            throw new IllegalArgumentException("Команда не может выполняться над директорией");
        }
        try {
            Split spl = new Split(this);
            spl.createFiles();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public int getSizeOfOutputFilesInChars() {
        return sizeOfOutputFilesInChars;
    }

    public String getInputFileName() {
        return new File(String.valueOf(inputFile)).getName();
    }

    public String getOutputFileName() {
        String inputFileName = new File(String.valueOf(inputFile)).getName();
        String outputName;
        if (outputFileName == null) {
            outputName = "x";
        } else if (outputFileName.equals("-")) {
            if ((inputFileName.contains("."))) {
                outputName = inputFileName.substring(0,
                        inputFileName.indexOf("."));
            } else outputName = inputFileName;
        } else outputName = outputFileName;
        return outputName;
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
