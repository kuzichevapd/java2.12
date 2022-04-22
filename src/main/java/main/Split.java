package main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class Split {
    private final String outputFileName;
    private final String inputFileName;
    private final boolean fileFormat;
    private final int sizeOfOutputFilesInLines;
    private final int sizeOfOutputFilesInChars;
    private final int amountOfOutputFiles;
    private final int sizeOfOutFile;

    public Split(Main main) throws IOException {
        if (main.getSizeOfOutputFilesInLines() < 0 || main.getSizeOfOutputFilesInChars() < 0
                || main.getAmountOfOutputFiles() < 0) throw new IllegalArgumentException();
        this.sizeOfOutputFilesInLines = main.getSizeOfOutputFilesInLines();
        this.sizeOfOutputFilesInChars = main.getSizeOfOutputFilesInChars();
        this.inputFileName = getNameOfInputFile(main.getInputFile());
        this.fileFormat = main.isFileFormat();
        if (checkInputFile(main.getInputFile())) {
            if (main.getOutputFileName() == null) {
                this.outputFileName = "x";
            } else {
                if (main.getOutputFileName().equals("-")) {
                    this.outputFileName = inputFileName.substring(0, inputFileName.indexOf("."));
                } else this.outputFileName = main.getOutputFileName();
            }
        } else throw new IllegalArgumentException();
        Pair sizeAndAmountOfOutputFiles = countSizeAndAmountOfOutputFiles(main.getAmountOfOutputFiles(),
                sizeOfOutputFilesInLines, sizeOfOutputFilesInChars);
        this.sizeOfOutFile = sizeAndAmountOfOutputFiles.sizeOfOutFile;
        this.amountOfOutputFiles = sizeAndAmountOfOutputFiles.amountOfOutputFiles;
    }

    private String getNameOfInputFile(File inputFile) {
        if (checkInputFile(inputFile)) return new File(String.valueOf(inputFile)).getName();
        else return "";
    }

    private boolean checkInputFile(File inputFile) {
        return Files.isRegularFile(Path.of(inputFile.getPath()));
    }


    public Pair countSizeAndAmountOfOutputFiles(int amountOfOutputFiles,
                                                int sizeOfOutputFilesInLines,
                                                int sizeOfOutputFilesInChars) throws IOException {
        Pair pair;
        int sizeOfOutFileByDefault = 100;
        if (amountOfOutputFiles != 0) {
            pair = new Pair(sizeAndAmountCount(lengthOfFileInLines(), amountOfOutputFiles), amountOfOutputFiles);
        } else if (sizeOfOutputFilesInChars != 0) {
            pair = new Pair(sizeOfOutputFilesInChars, sizeAndAmountCount(lengthOfFileInChars(), sizeOfOutputFilesInChars));
        } else if (sizeOfOutputFilesInLines != 0) {
            pair = new Pair(sizeOfOutputFilesInLines, sizeAndAmountCount(lengthOfFileInLines(), sizeOfOutputFilesInLines));
        } else {
            pair = new Pair(sizeOfOutFileByDefault, sizeAndAmountCount(lengthOfFileInLines(), sizeOfOutFileByDefault));
        }
        return pair;
    }

    private int sizeAndAmountCount(int length, int size) {
        if (length % size > 0) return length / size + 1;
        else return length / (size);
    }

    private int lengthOfFileInChars() throws IOException {
        int length = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            while (reader.read() != -1) length += 1;
        }
        return length;
    }

    private int lengthOfFileInLines() throws IOException {
        int length = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            while (null != reader.readLine()) length += 1;
        }
        return length;
    }

    public String nameOfFile(int number) {
        if (fileFormat) {
            return String.format(outputFileName + "%d" + ".txt", number);
        } else {
            int alphabetSize = 26;
            int numberOfChar = 96;
            StringBuilder sb = new StringBuilder();
            while (number > alphabetSize) {
                sb.append((char) (number % alphabetSize + numberOfChar));
                number /= alphabetSize;
            }
            if (number == alphabetSize) sb.append("z");
            else sb.append((char) (number % alphabetSize + numberOfChar));
            return outputFileName + sb.reverse() + ".txt";
        }
    }

    public void createFiles() throws IOException {
        try (BufferedReader br = new BufferedReader((new FileReader(inputFileName)))) {
            if (sizeOfOutputFilesInChars != 0) {
                int currentChar;
                for (int i = 1; i <= amountOfOutputFiles; i++) {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(nameOfFile(i)))) {
                        for (int j = 0; j < sizeOfOutFile; j++) {
                            currentChar = br.read();
                            if (currentChar != -1) bw.write(currentChar);
                            else break;
                        }
                    }
                }
            } else {
                String currentLine;
                for (int i = 1; i <= amountOfOutputFiles; i++) {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(nameOfFile(i)))) {
                        bw.write(br.readLine());
                        for (int j = 1; j < sizeOfOutFile; j++) {
                            currentLine = br.readLine();
                            if (currentLine != null) {
                                bw.newLine();
                                bw.write(currentLine);
                            } else break;
                        }
                    }
                }
            }
        }
    }

    private static class Pair {
        int sizeOfOutFile;
        int amountOfOutputFiles;

        public Pair(int sizeOfOutFile, int amountOfOutputFiles) {
            this.sizeOfOutFile = sizeOfOutFile;
            this.amountOfOutputFiles = amountOfOutputFiles;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Split split = (Split) o;
        return fileFormat == split.fileFormat && sizeOfOutputFilesInLines == split.sizeOfOutputFilesInLines
                && sizeOfOutputFilesInChars == split.sizeOfOutputFilesInChars &&
                amountOfOutputFiles == split.amountOfOutputFiles &&
                sizeOfOutFile == split.sizeOfOutFile &&
                Objects.equals(outputFileName, split.outputFileName)
                && Objects.equals(inputFileName, split.inputFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputFileName, inputFileName,
                fileFormat, sizeOfOutputFilesInLines, sizeOfOutputFilesInChars, amountOfOutputFiles, sizeOfOutFile);
    }
}


