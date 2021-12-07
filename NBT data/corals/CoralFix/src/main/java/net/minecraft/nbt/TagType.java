package net.minecraft.nbt;

import java.io.IOException;
import java.io.DataInput;

public interface TagType<T extends Tag> {
    T load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException;
    
    default boolean isValue() {
        return false;
    }
    
    String getName();
    
    String getPrettyName();
    
    static TagType<EndTag> createInvalid(final int integer) {
        return new TagType<EndTag>() {
            @Override
            public EndTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                throw new IllegalArgumentException("Invalid tag id: " + integer);
            }
            
            @Override
            public String getName() {
                return "INVALID[" + integer + "]";
            }
            
            @Override
            public String getPrettyName() {
                return "UNKNOWN_" + integer;
            }
        };
    }
}
