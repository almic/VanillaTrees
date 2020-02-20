package net.minecraft.nbt;

import java.io.DataInput;
import net.minecraft.util.Mth;
import java.io.IOException;
import java.io.DataOutput;

public class DoubleTag extends NumericTag {
    public static final DoubleTag ZERO;
    public static final TagType<DoubleTag> TYPE;
    private final double data;
    
    private DoubleTag(final double double1) {
        this.data = double1;
    }
    
    public static DoubleTag valueOf(final double double1) {
        if (double1 == 0.0) {
            return DoubleTag.ZERO;
        }
        return new DoubleTag(double1);
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(this.data);
    }
    
    @Override
    public byte getId() {
        return 6;
    }
    
    @Override
    public TagType<DoubleTag> getType() {
        return DoubleTag.TYPE;
    }
    
    @Override
    public String toString() {
        return this.data + "d";
    }
    
    @Override
    public DoubleTag copy() {
        return this;
    }
    
    @Override
    public boolean equals(final Object object) {
        return this == object || (object instanceof DoubleTag && this.data == ((DoubleTag)object).data);
    }
    
    @Override
    public int hashCode() {
        final long long2 = Double.doubleToLongBits(this.data);
        return (int)(long2 ^ long2 >>> 32);
    }
    
    @Override
    public long getAsLong() {
        return (long)Math.floor(this.data);
    }
    
    @Override
    public int getAsInt() {
        return Mth.floor(this.data);
    }
    
    @Override
    public short getAsShort() {
        return (short)(Mth.floor(this.data) & 0xFFFF);
    }
    
    @Override
    public byte getAsByte() {
        return (byte)(Mth.floor(this.data) & 0xFF);
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
        ZERO = new DoubleTag(0.0);
        TYPE = new TagType<DoubleTag>() {
            @Override
            public DoubleTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                kc.accountBits(128L);
                return DoubleTag.valueOf(dataInput.readDouble());
            }
            
            @Override
            public String getName() {
                return "DOUBLE";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_Double";
            }
            
            @Override
            public boolean isValue() {
                return true;
            }
        };
    }
}
