package solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class SplitTest {

    private boolean assertContentEqualsForLines(int amount, int currentLine,
                                        String firstFileName, String SecondFileName) throws IOException {
        boolean result = true;
        try (BufferedReader br1 = new BufferedReader((new FileReader(firstFileName)))) {
            for (int j = 1; j <= currentLine; j++ ) {br1.readLine(); }
            try (BufferedReader br2 = new BufferedReader((new FileReader(SecondFileName)))) {
                for (int i = 1; i <= amount; i++) {
                    result = result && (br1.readLine().contentEquals(br2.readLine()));
                }
            }
        }
        return result;
    }

    private boolean assertContentEqualsForChars(int amount, int currentChar,
                                                 String firstFileName, String SecondFileName) throws IOException {
        boolean result = true;
        try (BufferedReader br1 = new BufferedReader((new FileReader(firstFileName)))) {
            for (int j = 1; j <= currentChar; j++ ) {br1.read();
            }
            try (BufferedReader br2 = new BufferedReader((new FileReader(SecondFileName)))) {
                for (int i = 0; i < amount; i++) {
                    if (br1.read() != -1 && br2.read() != -1) {
                        result = result && (br1.read() == br2.read());
                    }
                    else break;
                }
            }
        }
        return result;
    }
   /* String testFilePath = getClass().getClassLoader().getResource("FileTestOne.txt").getPath();*/



    @Test
    public void amountOfOutputFilesTest() throws IOException {
        Launcher.main(new String[]{"-n", "3", "-o", "FirstTest", "FileTestOne.txt"});
        assertTrue(new File("FirstTesta.txt").exists());
        assertTrue(new File("FirstTestc.txt").exists());
        assertTrue(new File("FirstTestb.txt").exists());
        assertTrue(assertContentEqualsForLines(17,0,
                "FileTestOne.txt", "FirstTesta.txt"));
        assertTrue(assertContentEqualsForLines(17,17,
                "FileTestOne.txt", "FirstTestb.txt"));
        assertTrue(assertContentEqualsForLines(17,34,
                "FileTestOne.txt", "FirstTestc.txt"));

        new File("FirstTesta.txt").delete();
        new File("FirstTestb.txt").delete();
        new File("FirstTestc.txt").delete();
    }

    @Test
    public void lengthInLinesTest() throws IOException {
        Launcher.main(new String[]{"-d", "-l", "11", "FileTestOne.txt"});
        assertTrue(new File("x1.txt").exists());
        assertTrue(new File("x2.txt").exists());
        assertTrue(new File("x3.txt").exists());
        assertTrue(new File("x4.txt").exists());
        assertTrue(new File("x5.txt").exists());

        assertTrue(assertContentEqualsForLines(11,0,
                "FileTestOne.txt", "x1.txt"));
        assertTrue(assertContentEqualsForLines(11,11,
                "FileTestOne.txt", "x2.txt"));
        assertTrue(assertContentEqualsForLines(11,22,
                "FileTestOne.txt", "x3.txt"));
        assertTrue(assertContentEqualsForLines(11,33,
                "FileTestOne.txt", "x4.txt"));
        assertTrue(assertContentEqualsForLines(7,44,
                "FileTestOne.txt", "x5.txt"));


        new File("x1.txt").delete();
        new File("x2.txt").delete();
        new File("x3.txt").delete();
        new File("x4.txt").delete();
        new File("x5.txt").delete();
    }

    @Test
    public void lengthInCharsTest1() throws IOException {
        Launcher.main(new String[]{"-d", "-c", "1000", "-o", "-", "FileTestOne.txt"});
        assertTrue(new File("FileTestOne1.txt").exists());
        assertTrue(new File("FileTestOne2.txt").exists());
        assertTrue(new File("FileTestOne3.txt").exists());
        assertTrue(new File("FileTestOne4.txt").exists());
        assertTrue(new File("FileTestOne5.txt").exists());

        assertTrue(assertContentEqualsForChars(1000,0,
                "FileTestOne.txt", "FileTestOne1.txt"));
        assertTrue(assertContentEqualsForChars(1000,1000,
                "FileTestOne.txt", "FileTestOne2.txt"));
        assertTrue(assertContentEqualsForChars(1000,2000,
                "FileTestOne.txt", "FileTestOne3.txt"));
        assertTrue(assertContentEqualsForChars(1000,3000,
                "FileTestOne.txt", "FileTestOne4.txt"));
        assertTrue(assertContentEqualsForChars(1000,4000,
                "FileTestOne.txt", "FileTestOne5.txt"));

        new File("FileTestOne1.txt").delete();
        new File("FileTestOne2.txt").delete();
        new File("FileTestOne3.txt").delete();
        new File("FileTestOne4.txt").delete();
        new File("FileTestOne5.txt").delete();
    }

    @Test
    public void lengthInCharsTest2() throws IOException {
        Launcher.main(new String[]{ "-c", "4000", "-o", "EugeniiOnegin", "FileTestTwo.txt"});
        assertTrue(new File("EugeniiOnegina.txt").exists());
        assertTrue(new File("EugeniiOneginb.txt").exists());
        assertTrue(new File("EugeniiOneginc.txt").exists());

        assertTrue(assertContentEqualsForChars(4000,0,
                "FileTestTwo.txt", "EugeniiOnegina.txt"));
        assertTrue(assertContentEqualsForChars(4000,4000,
                "FileTestTwo.txt", "EugeniiOneginb.txt"));
        assertTrue(assertContentEqualsForChars(4000,8000,
                "FileTestTwo.txt", "EugeniiOneginc.txt"));

        new File("EugeniiOnegina.txt").delete();
        new File("EugeniiOneginb.txt").delete();
        new File("EugeniiOneginc.txt").delete();

    }

    @Test
    public void noOptionsTest() throws IOException {
        Launcher.main(new String[]{"-d", "-o", "-", "FileTestTwo.txt"});
        assertTrue(new File("FileTestTwo1.txt").exists());
        assertTrue(new File("FileTestTwo2.txt").exists());
        assertTrue(new File("FileTestTwo3.txt").exists());
        assertTrue(new File("FileTestTwo4.txt").exists());

        assertTrue(assertContentEqualsForLines(100,0,
                "FileTestTwo.txt", "FileTestTwo1.txt"));
        assertTrue(assertContentEqualsForLines(100,100,
                "FileTestTwo.txt", "FileTestTwo2.txt"));
        assertTrue(assertContentEqualsForLines(100,200,
                "FileTestTwo.txt", "FileTestTwo3.txt"));
        assertTrue(assertContentEqualsForLines(75,300,
                "FileTestTwo.txt", "FileTestTwo4.txt"));

        new File("FileTestTwo1.txt").delete();
        new File("FileTestTwo2.txt").delete();
        new File("FileTestTwo3.txt").delete();
        new File("FileTestTwo4.txt").delete();
    }

    @Test
    public void negativeNumbersTest() {
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> Launcher.main(new String[]{"-d", "-c", "-1000", "-o",
                "-", "FileTestOne.txt"}));
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> Launcher.main(new String[]{"-d", "-l", "-10", "-o", "-", "FileTestOne.txt"}));
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> Launcher.main(new String[]{"-d", "-n", "-5", "-o", "-", "FileTestOne.txt"}));
    }

    @Test
    public void nonDirectoryTest() {
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> Launcher.main(new String[]{"-d", "-c", "1000", "-o", "-",
                "DirectoryTest"}));
    }

}
