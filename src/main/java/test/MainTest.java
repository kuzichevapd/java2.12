package test;


import main.Split;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {


    @Test
    public void test1() throws IOException {
        Split spl = new Split(false, 0, 0, 3,
                "FirstTest", "FileTestOne.txt");
        spl.createFiles();
        assertEquals(17,
                spl.sizeOfOutFile);
        assertTrue(new File("FirstTesta.txt").exists());
        assertTrue(new File("FirstTestc.txt").exists());
        assertTrue(new File("FirstTestb.txt").exists());

        new File("FirstTesta.txt").delete();
        new File("FirstTestb.txt").delete();
        new File("FirstTestc.txt").delete();
    }

    @Test
    public void test2() throws IOException {
        Split spl = new Split(true, 11, 0, 0,
                null, "FileTestOne.txt");
        spl.createFiles();
        assertEquals(5, spl.amountOfOutputFiles);
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
    public void test3() throws IOException {
        Split spl = new Split(true, 0, 1000, 0,
                "-", "FileTestOne.txt");
        spl.createFiles();
        assertEquals(5, spl.amountOfOutputFiles);
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
    public void test4() throws IOException {
        Split spl = new Split(true, 0, 0, 0,
                "-", "FileTestTwo.txt");
        spl.createFiles();
        assertEquals(4, spl.amountOfOutputFiles);
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
    public void test5() throws IOException {
        try {
            Split spl = new Split(true, 0, 1000, 8,
                    "-", "FileTestOne.txt");
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }


}
