package net.minecraft.nbt;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class IntTag extends NumericTag {
    public static final TagType<IntTag> TYPE;
    private final int data;
    
    private IntTag(final int integer) {
        this.data = integer;
    }
    
    public static IntTag valueOf(final int integer) {
        if (integer >= -128 && integer <= 1024) {
            return Cache.cache[integer + 128];
        }
        return new IntTag(integer);
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data);
    }
    
    @Override
    public byte getId() {
        return 3;
    }
    
    @Override
    public TagType<IntTag> getType() {
        return IntTag.TYPE;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.data);
    }
    
    @Override
    public IntTag copy() {
        return this;
    }
    
    @Override
    public boolean equals(final Object object) {
        return this == object || (object instanceof IntTag && this.data == ((IntTag)object).data);
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
        return (short)(this.data & 0xFFFF);
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
        return (float)this.data;
    }
    
    @Override
    public Number getAsNumber() {
        return this.data;
    }
    
    static {
        TYPE = new TagType<IntTag>() {
            @Override
            public IntTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                kc.accountBits(96L);
                return IntTag.valueOf(dataInput.readInt());
            }
            
            @Override
            public String getName() {
                return "INT";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_Int";
            }
            
            @Override
            public boolean isValue() {
                return true;
            }
        };
    }
    
    static class Cache {
        static final IntTag[] cache;
        
        static {
            cache = new IntTag[1153];
            for (int integer1 = 0; integer1 < Cache.cache.length; ++integer1) {
                Cache.cache[integer1] = new IntTag(-128 + integer1);
            }
        }
    }
}
