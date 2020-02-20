package net.minecraft.nbt;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class EndTag implements Tag {
    public static final TagType<EndTag> TYPE;
    public static final EndTag INSTANCE;
    
    private EndTag() {
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
    }
    
    @Override
    public byte getId() {
        return 0;
    }
    
    @Override
    public TagType<EndTag> getType() {
        return EndTag.TYPE;
    }
    
    @Override
    public String toString() {
        return "END";
    }
    
    @Override
    public EndTag copy() {
        return this;
    }
    
    static {
        TYPE = new TagType<EndTag>() {
            @Override
            public EndTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) {
                kc.accountBits(64L);
                return EndTag.INSTANCE;
            }
            
            @Override
            public String getName() {
                return "END";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_End";
            }
            
            @Override
            public boolean isValue() {
                return true;
            }
        };
        INSTANCE = new EndTag();
    }
}
