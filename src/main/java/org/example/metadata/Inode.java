package org.example.metadata;

import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.file.FileDescriptor;

import java.util.Map;

@Getter
@EqualsAndHashCode(of={"name"})
public class Inode {

    @Setter
    private String name;

    private InodeType type;

    private Map<String, Inode> children;

    // This is null in case of a folder.
    private FileDescriptor desc;

    // Create directory
    public Inode(String name) {
        this.name = name;
        this.type = InodeType.FOLDER;
        this.children = Maps.newConcurrentMap();
    }

    // Create file
    public Inode(String name, FileDescriptor descriptor) {
        this.name = name;
        this.type = InodeType.FILE;
        this.desc = descriptor;
    }

}
