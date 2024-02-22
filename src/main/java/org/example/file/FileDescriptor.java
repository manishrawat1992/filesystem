package org.example.file;

import com.google.common.collect.Lists;
import lombok.Data;
import org.example.storage.Block;
import org.example.storage.Storage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Data
public class FileDescriptor {

    private Storage storage;

    // Block size if 128
    private List<Block> blocks = Lists.newArrayList();

    private boolean invalid= false;

    public FileDescriptor(Storage storage) {
        this.storage = storage;
    }

    public synchronized byte[] read(long offset, long size){
        long blockNo = offset / 128;
        long blockOffset = offset%128;

        long endBlockNo = (offset+size)/128;

        // TODO read size data and return;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        for(long i = blockNo; i<=endBlockNo; i++) {
            Block block = blocks.get((int) i);

            try {
                buffer.write(block.getData());
            } catch (IOException e) {}
        }


        byte[] ret = Arrays.copyOfRange(buffer.toByteArray(), (int) blockOffset, (int) blockOffset+ (int) size);

        return ret;
    }

    public synchronized void write(long offset, byte[] data) {
        allocate(offset, data.length);

        for(long i=offset, j=0; i< offset+data.length; i++,j++) {
            long blockNo = i / 128;
            long blockOffset = i%128;

            Block block = blocks.get((int) blockNo);

            block.getData()[(int) blockOffset] = data[(int) j];

        }

    }

    private void allocate(long offset, int length) {
        int currentSize = blocks.size() * 128;

        while(offset+length> currentSize) {
            Block block = storage.allocate();
            blocks.add(block);
            currentSize +=128;
        }
    }

    public synchronized void deallocate() {
        invalid=true;
        for(Block block: blocks) {
            storage.deallocate(block);
        }
    }
}
