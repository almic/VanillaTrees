package net.minecraft.nbt;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class ShortTag extends NumericTag {
    public static final TagType<ShortTag> TYPE;
    private final short data;
    
    private ShortTag(final short short1) {
        this.data = short1;
    }
    
    public static ShortTag valueOf(final short short1) {
        if (short1 >= -128 && short1 <= 1024) {
            return Cache.cache[short1 + 128];
        }
        return new ShortTag(short1);
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeShort(this.data);
    }
    
    @Override
    public byte getId() {
        return 2;
    }
    
    @Override
    public TagType<ShortTag> getType() {
        return ShortTag.TYPE;
    }
    
    @Override
    public String toString() {
        return this.data + "s";
    }
    
    @Override
    public ShortTag copy() {
        return this;
    }
    
    @Override
    public boolean equals(final Object object) {
        return this == object || (object instanceof ShortTag && this.data == ((ShortTag)object).data);
    }
    
    @Override
    public int hashCode() {
        return this.data;
    }
    
    @Override
    public long getAsLong() {
        return this.data;
    }
    
    @Override
    public int getAsInt() {
        return this.data;
    }
    
    @Override
    public short getAsShort() {
        return this.data;
    }
    
    @Override
    public byte getAsByte() {
        return (byte)(this.data & 0xFF);
    }
    
    @Override
    public double getAsDouble() {
        return this.data;
    }
    
    @Override
    public float getAsFloat() {
        return this.data;
    }
    
    @Override
    public Number getAsNumber() {
        return this.data;
    }
    
    static {
        TYPE = new TagType<ShortTag>() {
            @Override
            public ShortTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                kc.accountBits(80L);
                return ShortTag.valueOf(dataInput.readShort());
            }
            
            @Override
            public String getName() {
                return "SHORT";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_Short";
            }
            
            @Override
            public boolean isValue() {
                return true;
            }
        };
    }
    
    static class Cache {
        static final ShortTag[] cache;
        
        static {
            cache = new ShortTag[1153];
            for (int integer1 = 0; integer1 < Cache.cache.length; ++integer1) {
                Cache.cache[integer1] = new ShortTag((short)(-128 + integer1));
            }
        }
    }
}
