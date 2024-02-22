package org.example.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Block {

    byte[] data = new byte[128];

    boolean free = true;

}
