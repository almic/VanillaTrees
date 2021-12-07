package net.minecraft.nbt;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class LongTag extends NumericTag {
    public static final TagType<LongTag> TYPE;
    private final long data;
    
    private LongTag(final long long1) {
        this.data = long1;
    }
    
    public static LongTag valueOf(final long long1) {
        if (long1 >= -128L && long1 <= 1024L) {
            return Cache.cache[(int)long1 + 128];
        }
        return new LongTag(long1);
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.data);
    }
    
    @Override
    public byte getId() {
        return 4;
    }
    
    @Override
    public TagType<LongTag> getType() {
        return LongTag.TYPE;
    }
    
    @Override
    public String toString() {
        return this.data + "L";
    }
    
    @Override
    public LongTag copy() {
        return this;
    }
    
    @Override
    public boolean equals(final Object object) {
        return this == object || (object instanceof LongTag && this.data == ((LongTag)object).data);
    }
    
    @Override
    public int hashCode() {
        return (int)(this.data ^ this.data >>> 32);
    }
    
    @Override
    public long getAsLong() {
        return this.data;
    }
    
    @Override
    public int getAsInt() {
        return (int)(this.data);
    }
    
    @Override
    public short getAsShort() {
        return (short)(this.data & 0xFFFFL);
    }
    
    @Override
    public byte getAsByte() {
        return (byte)(this.data & 0xFFL);
    }
    
    @Override
    public double getAsDouble() {
        return (double)this.data;
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
        TYPE = new TagType<LongTag>() {
            @Override
            public LongTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                kc.accountBits(128L);
                return LongTag.valueOf(dataInput.readLong());
            }
            
            @Override
            public String getName() {
                return "LONG";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_Long";
            }
            
            @Override
            public boolean isValue() {
                return true;
            }
        };
    }
    
    static class Cache {
        static final LongTag[] cache;
        
        static {
            cache = new LongTag[1153];
            for (int integer1 = 0; integer1 < Cache.cache.length; ++integer1) {
                Cache.cache[integer1] = new LongTag(-128 + integer1);
            }
        }
    }
}
