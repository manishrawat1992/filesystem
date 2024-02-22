package org.example;

public interface FileSystem {

    long fopen(String path);

    byte[] fread(long fileHandle, long offset, long size);

    void fwrite(long fileHandle, long offset, byte[] data);

    void fclose(long fileHandle);

    void rename(String filePath, String fileName);

    void remove(String filePath);
}