package org.example.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Storage {

    // File allocation table
    private Block[] data;

    // TODO Maintain a heap datastructure to provide unused space

    public Storage(long bytes) {
        data = new Block[(int) (bytes/128) + 1];
        for(int i=0;i<data.length;i++) {
            data[i] = new Block();

        }
    }

    // File System Allocator
    // naive allocation. Best allocation heap
    public Block allocate(){
        for(Block block : data) {
            if(block.isFree())
                return block;
        }

        throw new RuntimeException("No free disk space");
    }

    public void deallocate(Block block) {
        block.setFree(true);
    }
}
