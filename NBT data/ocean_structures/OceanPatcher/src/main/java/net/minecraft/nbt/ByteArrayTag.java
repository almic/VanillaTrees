package net.minecraft.nbt;

import java.io.DataInput;
import org.apache.commons.lang3.ArrayUtils;
import java.util.Arrays;
import java.io.IOException;
import java.io.DataOutput;
import java.util.List;

public class ByteArrayTag extends CollectionTag<ByteTag> {
    public static final TagType<ByteArrayTag> TYPE;
    private byte[] data;
    
    public ByteArrayTag(final byte[] arr) {
        this.data = arr;
    }
    
    public ByteArrayTag(final List<Byte> list) {
        this(toArray(list));
    }
    
    private static byte[] toArray(final List<Byte> list) {
        final byte[] arr2 = new byte[list.size()];
        for (int integer3 = 0; integer3 < list.size(); ++integer3) {
            final Byte byte4 = list.get(integer3);
            arr2[integer3] = (byte)((byte4 == null) ? 0 : ((byte)byte4));
        }
        return arr2;
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data.length);
        dataOutput.write(this.data);
    }
    
    @Override
    public byte getId() {
        return 7;
    }
    
    @Override
    public TagType<ByteArrayTag> getType() {
        return ByteArrayTag.TYPE;
    }
    
    @Override
    public String toString() {
        final StringBuilder stringBuilder2 = new StringBuilder("[B;");
        for (int integer3 = 0; integer3 < this.data.length; ++integer3) {
            if (integer3 != 0) {
                stringBuilder2.append(',');
            }
            stringBuilder2.append(this.data[integer3]).append('B');
        }
        return stringBuilder2.append(']').toString();
    }
    
    @Override
    public Tag copy() {
        final byte[] arr2 = new byte[this.data.length];
        System.arraycopy(this.data, 0, arr2, 0, this.data.length);
        return new ByteArrayTag(arr2);
    }
    
    @Override
    public boolean equals(final Object object) {
        return this == object || (object instanceof ByteArrayTag && Arrays.equals(this.data, ((ByteArrayTag)object).data));
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.data);
    }
    
    public byte[] getAsByteArray() {
        return this.data;
    }
    
    @Override
    public int size() {
        return this.data.length;
    }
    
    @Override
    public ByteTag get(final int integer) {
        return ByteTag.valueOf(this.data[integer]);
    }
    
    @Override
    public ByteTag set(final int integer, final ByteTag jr) {
        final byte byte4 = this.data[integer];
        this.data[integer] = jr.getAsByte();
        return ByteTag.valueOf(byte4);
    }
    
    @Override
    public void add(final int integer, final ByteTag jr) {
        this.data = ArrayUtils.add(this.data, integer, jr.getAsByte());
    }
    
    @Override
    public boolean setTag(final int integer, final Tag kj) {
        if (kj instanceof NumericTag) {
            this.data[integer] = ((NumericTag)kj).getAsByte();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean addTag(final int integer, final Tag kj) {
        if (kj instanceof NumericTag) {
            this.data = ArrayUtils.add(this.data, integer, ((NumericTag)kj).getAsByte());
            return true;
        }
        return false;
    }
    
    @Override
    public ByteTag remove(final int integer) {
        final byte byte3 = this.data[integer];
        this.data = ArrayUtils.remove(this.data, integer);
        return ByteTag.valueOf(byte3);
    }
    
    @Override
    public void clear() {
        this.data = new byte[0];
    }
    
    static {
        TYPE = new TagType<ByteArrayTag>() {
            @Override
            public ByteArrayTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                kc.accountBits(192L);
                final int integer2 = dataInput.readInt();
                kc.accountBits(8L * integer2);
                final byte[] arr6 = new byte[integer2];
                dataInput.readFully(arr6);
                return new ByteArrayTag(arr6);
            }
            
            @Override
            public String getName() {
                return "BYTE[]";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_Byte_Array";
            }
        };
    }
}
