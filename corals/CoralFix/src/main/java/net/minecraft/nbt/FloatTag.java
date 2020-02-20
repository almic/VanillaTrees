package net.minecraft.nbt;

import java.io.DataInput;
import net.minecraft.util.Mth;
import java.io.IOException;
import java.io.DataOutput;

public class FloatTag extends NumericTag {
    public static final FloatTag ZERO;
    public static final TagType<FloatTag> TYPE;
    private final float data;
    
    private FloatTag(final float float1) {
        this.data = float1;
    }
    
    public static FloatTag valueOf(final float float1) {
        if (float1 == 0.0f) {
            return FloatTag.ZERO;
        }
        return new FloatTag(float1);
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(this.data);
    }
    
    @Override
    public byte getId() {
        return 5;
    }
    
    @Override
    public TagType<FloatTag> getType() {
        return FloatTag.TYPE;
    }
    
    @Override
    public String toString() {
        return this.data + "f";
    }
    
    @Override
    public FloatTag copy() {
        return this;
    }
    
    @Override
    public boolean equals(final Object object) {
        return this == object || (object instanceof FloatTag && this.data == ((FloatTag)object).data);
    }
    
    @Override
    public int hashCode() {
        return Float.floatToIntBits(this.data);
    }
    
    @Override
    public long getAsLong() {
        return (long)this.data;
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
        return this.data;
    }
    
    @Override
    public Number getAsNumber() {
        return this.data;
    }
    
    static {
        ZERO = new FloatTag(0.0f);
        TYPE = new TagType<FloatTag>() {
            @Override
            public FloatTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                kc.accountBits(96L);
                return FloatTag.valueOf(dataInput.readFloat());
            }
            
            @Override
            public String getName() {
                return "FLOAT";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_Float";
            }
            
            @Override
            public boolean isValue() {
                return true;
            }
        };
    }
}
