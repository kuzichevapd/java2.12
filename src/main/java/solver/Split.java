package solver;

import java.io.*;


public class Split {
    private final int amountOfOutputFiles;
    private final int sizeOfOutFile;
    private final Launcher main;

    public Split(Launcher main) throws IOException {
        this.main = main;
        int sizeOfOutFileByDefault = 100;
        if (main.getAmountOfOutputFiles() != 0) {
            this.sizeOfOutFile = getSizeByAmount(main.getAmountOfOutputFiles());
            this.amountOfOutputFiles = main.getAmountOfOutputFiles();
        } else if (main.getSizeOfOutputFilesInLines() != 0) {
            this.sizeOfOutFile = main.getSizeOfOutputFilesInLines();
            this.amountOfOutputFiles = getAmountByLines(main.getSizeOfOutputFilesInLines());
        } else if (main.getSizeOfOutputFilesInChars() != 0) {
            this.sizeOfOutFile = main.getSizeOfOutputFilesInChars();
            this.amountOfOutputFiles = getAmountByChars(main.getSizeOfOutputFilesInChars());
        } else {
            this.sizeOfOutFile = sizeOfOutFileByDefault;
            this.amountOfOutputFiles = getAmountByDefault();
        }
    }


    private int getSizeByAmount(int amountOfOutputFiles) throws IOException {
        return getSizeOrAmount(lengthOfFileInLines(), amountOfOutputFiles);
    }

    private int getAmountByChars(int SizeOfOutputFilesInChars) throws IOException {
        return getSizeOrAmount(lengthOfFileInChars(), SizeOfOutputFilesInChars);
    }

    private int getAmountByLines(int sizeOfOutputFilesInLines) throws IOException {
        return getSizeOrAmount(lengthOfFileInLines(), sizeOfOutputFilesInLines);
    }

    private int getAmountByDefault() throws IOException {
        int sizeOfOutFileByDefault = 100;
        return getSizeOrAmount(lengthOfFileInLines(), sizeOfOutFileByDefault);
    }


    private int getSizeOrAmount(int length, int size) {
        if (length % size > 0) return length / size + 1;
        else return length / (size);
    }

    private int lengthOfFileInChars() throws IOException {
        int length = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(main.getInputFileName()))) {
            while (reader.read() != -1) length += 1;
        }
        return length;
    }

    private int lengthOfFileInLines() throws IOException {
        int length = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(main.getInputFileName()))) {
            while (null != reader.readLine()) length += 1;
        }
        return length;
    }

    private String nameOfFile(int number) {
        if (main.isFileFormat()) {
            return String.format(main.getOutputFileName() + "%d" + ".txt", number);
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
            return main.getOutputFileName() + sb.reverse() + ".txt";
        }
    }

    public void createFiles() throws IOException {
        try (BufferedReader br = new BufferedReader((new FileReader(main.getInputFileName())))) {
            if (main.getSizeOfOutputFilesInChars() != 0) {
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

}


