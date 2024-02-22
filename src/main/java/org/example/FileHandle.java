package org.example;

import lombok.Data;
import org.example.file.FileDescriptor;


@Data
public class FileHandle {

    private FileDescriptor fileDescriptor;

    private long offset;

    public FileHandle(FileDescriptor desc) {
        this.fileDescriptor = desc;
    }

    public byte[] read(long offset, long size) {
        return fileDescriptor.read(offset, size);
    }

    public void write(long offset, byte[] data) {
        fileDescriptor.write(offset, data);
    }

}
