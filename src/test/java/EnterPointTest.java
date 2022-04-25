import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import solver.EnterPoint;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnterPointTest {
    @Test
    public void amountOfOutputFilesTest() throws IOException {
        EnterPoint.main(new String[]{"-n", "3", "-o", "FirstTest", "FileTestOne.txt"});
        assertTrue(new File("FirstTesta.txt").exists());
        assertTrue(new File("FirstTestc.txt").exists());
        assertTrue(new File("FirstTestb.txt").exists());

        new File("FirstTesta.txt").delete();
        new File("FirstTestb.txt").delete();
        new File("FirstTestc.txt").delete();
    }

    @Test
    public void lengthInLinesTest() throws IOException {
        EnterPoint.main(new String[]{"-d", "-l", "11", "FileTestOne.txt"});
        assertTrue(new File("x1.txt").exists());
        assertTrue(new File("x2.txt").exists());
        assertTrue(new File("x3.txt").exists());
        assertTrue(new File("x4.txt").exists());
        assertTrue(new File("x5.txt").exists());

        new File("x1.txt").delete();
        new File("x2.txt").delete();
        new File("x3.txt").delete();
        new File("x4.txt").delete();
        new File("x5.txt").delete();
    }

    @Test
    public void lengthInCharsTest() throws IOException {
        EnterPoint.main(new String[]{"-d", "-c", "1000", "-o", "-", "C:\\Users\\kuuzi\\IdeaProjects\\java2.123\\FileTestOne.txt"});
        assertTrue(new File("FileTestOne1.txt").exists());
        assertTrue(new File("FileTestOne2.txt").exists());
        assertTrue(new File("FileTestOne3.txt").exists());
        assertTrue(new File("FileTestOne4.txt").exists());
        assertTrue(new File("FileTestOne5.txt").exists());

        new File("FileTestOne1.txt").delete();
        new File("FileTestOne2.txt").delete();
        new File("FileTestOne3.txt").delete();
        new File("FileTestOne4.txt").delete();
        new File("FileTestOne5.txt").delete();
    }

    @Test
    public void noOptionsTest() throws IOException {
        EnterPoint.main(new String[]{"-d", "-o", "-", "FileTestTwo.txt"});
        assertTrue(new File("FileTestTwo1.txt").exists());
        assertTrue(new File("FileTestTwo2.txt").exists());
        assertTrue(new File("FileTestTwo3.txt").exists());
        assertTrue(new File("FileTestTwo4.txt").exists());

        new File("FileTestTwo1.txt").delete();
        new File("FileTestTwo2.txt").delete();
        new File("FileTestTwo3.txt").delete();
        new File("FileTestTwo4.txt").delete();
    }

    @Test
    public void negativeNumbersTest() {
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> EnterPoint.main(new String[]{"-d", "-c", "-1000", "-o",
                "-", "C:\\Users\\kuuzi\\IdeaProjects\\java2.123\\FileTestOne.txt"}));
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> EnterPoint.main(new String[]{"-d", "-l", "-10", "-o", "-", "FileTestOne.txt"}));
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> EnterPoint.main(new String[]{"-d", "-n", "-5", "-o", "-", "FileTestOne.txt"}));
    }

    @Test
    public void nonDirectoryTest() {
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> EnterPoint.main(new String[]{"-d", "-c", "-1000", "-o", "-",
                "C:\\Users\\kuuzi\\IdeaProjects\\java2.123\\DirectoryTest"}));
    }

}
