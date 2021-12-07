package net.minecraft.nbt;

import java.io.IOException;
import java.io.DataOutput;

public interface Tag {
    
    void write(final DataOutput dataOutput) throws IOException;
    
    String toString();
    
    byte getId();
    
    TagType<?> getType();
    
    Tag copy();
    
    default String getAsString() {
        return this.toString();
    }

}
