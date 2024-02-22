package org.example.metadata;

import lombok.Data;
import org.example.file.FileDescriptor;


@Data
public class MetadataManager {

    // At this time has depth one only
    private Inode inode;

    public MetadataManager(){
        inode = new Inode("/");
    }

    public void createFile(String fileName, FileDescriptor fileDescriptor) {
        // Create file in tree
        this.inode.getChildren().put(fileName, new Inode(fileName, fileDescriptor));
    }

    public FileDescriptor findFileDescriptor(String fileName) {
        // TODO Tree search to find file
        Inode found = inode.getChildren().get(fileName);
        if (found == null) {
            return null;
        }
        return found.getDesc();
    }

    public void rename(String previous, String fileName) {
        Inode file = this.inode.getChildren().remove(previous);
        file.setName(fileName);

        this.inode.getChildren().put(fileName, file);
    }

    public void removeFile(String filePath) {
        this.inode.getChildren().remove(filePath);
    }
}
