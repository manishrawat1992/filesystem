package org.example;

import com.google.common.collect.Maps;
import org.example.file.FileDescriptor;
import org.example.metadata.Inode;
import org.example.metadata.MetadataManager;
import org.example.storage.Storage;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class InmemoryFileSystemImpl implements FileSystem {

    private Storage storage;

    private MetadataManager metadata;

    private Map<Long, FileHandle> openedFiles = Maps.newConcurrentMap();

    public InmemoryFileSystemImpl(){
        this.storage = new Storage(1000);
        this.metadata = new MetadataManager();
    }

    @Override
    public long fopen(String path) {
        long uuid = UUID.randomUUID().getLeastSignificantBits();

        FileDescriptor desc = metadata.findFileDescriptor(path);

        // Create new file if file not found
        if(desc ==null) {
            desc = new FileDescriptor(storage);
            metadata.createFile(path, desc);
        }

        openedFiles.put(uuid, new FileHandle(desc));

        return uuid;
    }

    @Override
    public byte[] fread(long fileHandle, long offset, long size) {
        if(!openedFiles.containsKey(fileHandle)){
            throw new RuntimeException("File not open");
        }

        FileHandle handle = openedFiles.get(fileHandle);

        byte[] result = handle.read(offset, size);

        return result;
    }

    @Override
    public void fwrite(long fileHandle, long offset, byte[] data) {
        if(!openedFiles.containsKey(fileHandle)){
            throw new RuntimeException("File not open");
        }

        FileHandle handle = openedFiles.get(fileHandle);

        handle.write(offset, data);
    }

    @Override
    public void fclose(long fileHandle) {
        if(!openedFiles.containsKey(fileHandle)){
            throw new RuntimeException("File not open");
        }

        openedFiles.remove(fileHandle);
    }

    @Override
    public void rename(String filePath, String fileName) {
        metadata.rename(filePath, fileName);
    }

    @Override
    public void remove(String filePath) {

        FileDescriptor desc = metadata.findFileDescriptor(filePath);
        desc.deallocate();

        metadata.removeFile(filePath);
    }
}
