package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public final class Split {
    public final String outputFileName;
    public final String inputFileName;
    public final boolean fileFormat;
    public final int sizeOfOutputFilesinLines;
    public final int sizeOfOutputFilesinChars;
    public final int amountOfOutputFiles;
    public final int sizeOfOutFile;

    public Split(boolean fileFormat, int
            sizeOfOutputFilesinLines, int sizeOfOutputFilesinChars, int amountOfOutputFiles,
                 String outputFileName, String inputFileName) throws IOException {
        if (sizeOfOutputFilesinChars != 0 && sizeOfOutputFilesinLines != 0 ||
                sizeOfOutputFilesinChars != 0 && amountOfOutputFiles != 0 ||
                amountOfOutputFiles != 0 && sizeOfOutputFilesinLines != 0) {
            throw new IllegalArgumentException();
        } else {
            this.sizeOfOutputFilesinLines = sizeOfOutputFilesinLines;
            this.sizeOfOutputFilesinChars = sizeOfOutputFilesinChars;
        }

        this.inputFileName = inputFileName;
        this.fileFormat = fileFormat;

        if (outputFileName == null) {
            this.outputFileName = "x";
        } else {
            if (outputFileName.equals("-")) {
                this.outputFileName = inputFileName.substring(0, inputFileName.indexOf("."));
            } else this.outputFileName = outputFileName;
        }
        ArrayList<Integer> parametres = parametresOfOutputFiles(amountOfOutputFiles,
                sizeOfOutputFilesinLines, sizeOfOutputFilesinChars);
        this.sizeOfOutFile = parametres.get(0);
        this.amountOfOutputFiles = parametres.get(1);

    }

    public ArrayList<Integer> parametresOfOutputFiles(int amountOfOutputFiles,
                                                      int sizeOfOutputFilesinLines,
                                                      int sizeOfOutputFilesinChars) throws IOException {
        ArrayList<Integer> parametres = new ArrayList<>(); // первый в листе  - размер выходного файла, второй - количество выходных файлов
        if (amountOfOutputFiles != 0) {
            parametres.add(parametresCount(lengthOfFileInLines(), amountOfOutputFiles));
            parametres.add(amountOfOutputFiles);
        } else if (sizeOfOutputFilesinChars != 0) {
            parametres.add(sizeOfOutputFilesinChars);
            parametres.add(parametresCount(lengthOfFileInChars(), sizeOfOutputFilesinChars));
        } else if (sizeOfOutputFilesinLines != 0) {
            parametres.add(sizeOfOutputFilesinLines);
            parametres.add(parametresCount(lengthOfFileInLines(), sizeOfOutputFilesinLines));
        } else {
            parametres.add(100);
            parametres.add(parametresCount(lengthOfFileInLines(), 100));
        }
        return parametres;
    }

    private int parametresCount(int length, int size) {
        if (length % size > 0) return length / size + 1;
        else return length / (size);
    }

    private int lengthOfFileInChars() throws IOException {
        int length = 0;
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        while (reader.read() != -1) length += 1;
        return length;
    }

    private int lengthOfFileInLines() throws IOException {
        int length = 0;
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        while (null != reader.readLine()) length += 1;
        return length;
    }

    public String nameOfFile(int number) {
        if (fileFormat) {
            return outputFileName + String.format("%d" + ".txt", number);
        } else {
            StringBuilder sb = new StringBuilder();
            while (number > 26) {
                sb.append((char) (number % 26 + 96));
                number /= 26;
            }
            if (number == 26) sb.append("z");
            else sb.append((char) (number % 26 + 96));
            return outputFileName + sb.reverse() + ".txt";
        }
    }

    public void createFiles() throws IOException {
        BufferedReader br = new BufferedReader((new FileReader(inputFileName)));
        if (sizeOfOutputFilesinChars != 0) {
            int currentChar;
            for (int i = 1; i <= amountOfOutputFiles; i++) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(nameOfFile(i)));
                for (int j = 0; j < sizeOfOutFile; j++) {
                    currentChar = br.read();
                    if (currentChar != -1) bw.write(currentChar);
                    else break;
                }
                bw.close();
            }
        } else {
            String currentLine;
            for (int i = 1; i <= amountOfOutputFiles; i++) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(nameOfFile(i)));
                bw.write(br.readLine());
                for (int j = 1; j < sizeOfOutFile; j++) {
                    currentLine = br.readLine();
                    if (currentLine != null) {
                        bw.newLine();
                        bw.write(currentLine);
                    } else break;
                }
                bw.close();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Split split = (Split) o;
        return fileFormat == split.fileFormat && sizeOfOutputFilesinLines == split.sizeOfOutputFilesinLines && sizeOfOutputFilesinChars == split.sizeOfOutputFilesinChars && amountOfOutputFiles == split.amountOfOutputFiles && sizeOfOutFile == split.sizeOfOutFile && Objects.equals(outputFileName, split.outputFileName) && Objects.equals(inputFileName, split.inputFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputFileName, inputFileName, fileFormat, sizeOfOutputFilesinLines, sizeOfOutputFilesinChars, amountOfOutputFiles, sizeOfOutFile);
    }
}


