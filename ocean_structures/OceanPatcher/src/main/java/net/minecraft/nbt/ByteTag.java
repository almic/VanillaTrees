package net.minecraft.nbt;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class ByteTag extends NumericTag {
    public static final TagType<ByteTag> TYPE;
    public static final ByteTag ZERO;
    public static final ByteTag ONE;
    private final byte data;
    
    private ByteTag(final byte byte1) {
        this.data = byte1;
    }
    
    public static ByteTag valueOf(final byte byte1) {
        return Cache.cache[128 + byte1];
    }
    
    public static ByteTag valueOf(final boolean boolean1) {
        return boolean1 ? ByteTag.ONE : ByteTag.ZERO;
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(this.data);
    }
    
    @Override
    public byte getId() {
        return 1;
    }
    
    @Override
    public TagType<ByteTag> getType() {
        return ByteTag.TYPE;
    }
    
    @Override
    public String toString() {
        return this.data + "b";
    }
    
    @Override
    public ByteTag copy() {
        return this;
    }
    
    @Override
    public boolean equals(final Object object) {
        return this == object || (object instanceof ByteTag && this.data == ((ByteTag)object).data);
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
        return this.data;
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
        TYPE = new TagType<ByteTag>() {
            @Override
            public ByteTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                kc.accountBits(72L);
                return ByteTag.valueOf(dataInput.readByte());
            }
            
            @Override
            public String getName() {
                return "BYTE";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_Byte";
            }
            
            @Override
            public boolean isValue() {
                return true;
            }
        };
        ZERO = valueOf((byte)0);
        ONE = valueOf((byte)1);
    }
    
    static class Cache {
        private static final ByteTag[] cache;
        
        static {
            cache = new ByteTag[256];
            for (int integer1 = 0; integer1 < Cache.cache.length; ++integer1) {
                Cache.cache[integer1] = new ByteTag((byte)(integer1 - 128));
            }
        }
    }
}
