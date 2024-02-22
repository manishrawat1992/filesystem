package org.example;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class InmemoryFileSystemImplTest {


    @Test
    public void fopenTest(){
        FileSystem fs= new InmemoryFileSystemImpl();

        long handle = fs.fopen("1.txt");
        byte[] bytes = new byte[]{1, 2, 3};

        fs.fwrite(handle, 2, bytes);

        byte[] bytes1 = fs.fread(handle, 2, 3);

        assertEquals(bytes, bytes1);
        
        fs.fclose(handle);

        System.out.println(bytes);
    }

    @Test
    public void renameTest(){
        FileSystem fs= new InmemoryFileSystemImpl();

        long handle = fs.fopen("1.txt");

        fs.rename("1.txt" ,"2.txt");

        fs.fopen("1.txt");
    }
}