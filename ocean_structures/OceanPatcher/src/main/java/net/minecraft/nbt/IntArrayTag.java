package net.minecraft.nbt;

import java.io.DataInput;
import org.apache.commons.lang3.ArrayUtils;
import java.util.Arrays;
import java.io.IOException;
import java.io.DataOutput;
import java.util.List;

public class IntArrayTag extends CollectionTag<IntTag> {
    public static final TagType<IntArrayTag> TYPE;
    private int[] data;
    
    public IntArrayTag(final int[] arr) {
        this.data = arr;
    }
    
    public IntArrayTag(final List<Integer> list) {
        this(toArray(list));
    }
    
    private static int[] toArray(final List<Integer> list) {
        final int[] arr2 = new int[list.size()];
        for (int integer3 = 0; integer3 < list.size(); ++integer3) {
            final Integer integer4 = list.get(integer3);
            arr2[integer3] = ((integer4 == null) ? 0 : integer4);
        }
        return arr2;
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data.length);
        for (final int integer6 : this.data) {
            dataOutput.writeInt(integer6);
        }
    }
    
    @Override
    public byte getId() {
        return 11;
    }
    
    @Override
    public TagType<IntArrayTag> getType() {
        return IntArrayTag.TYPE;
    }
    
    @Override
    public String toString() {
        final StringBuilder stringBuilder2 = new StringBuilder("[I;");
        for (int integer3 = 0; integer3 < this.data.length; ++integer3) {
            if (integer3 != 0) {
                stringBuilder2.append(',');
            }
            stringBuilder2.append(this.data[integer3]);
        }
        return stringBuilder2.append(']').toString();
    }
    
    @Override
    public IntArrayTag copy() {
        final int[] arr2 = new int[this.data.length];
        System.arraycopy(this.data, 0, arr2, 0, this.data.length);
        return new IntArrayTag(arr2);
    }
    
    @Override
    public boolean equals(final Object object) {
        return this == object || (object instanceof IntArrayTag && Arrays.equals(this.data, ((IntArrayTag)object).data));
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.data);
    }
    
    public int[] getAsIntArray() {
        return this.data;
    }
    
    @Override
    public int size() {
        return this.data.length;
    }
    
    @Override
    public IntTag get(final int integer) {
        return IntTag.valueOf(this.data[integer]);
    }
    
    @Override
    public IntTag set(final int integer, final IntTag jy) {
        final int integer2 = this.data[integer];
        this.data[integer] = jy.getAsInt();
        return IntTag.valueOf(integer2);
    }
    
    @Override
    public void add(final int integer, final IntTag jy) {
        this.data = ArrayUtils.add(this.data, integer, jy.getAsInt());
    }
    
    @Override
    public boolean setTag(final int integer, final Tag kj) {
        if (kj instanceof NumericTag) {
            this.data[integer] = ((NumericTag)kj).getAsInt();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean addTag(final int integer, final Tag kj) {
        if (kj instanceof NumericTag) {
            this.data = ArrayUtils.add(this.data, integer, ((NumericTag)kj).getAsInt());
            return true;
        }
        return false;
    }
    
    @Override
    public IntTag remove(final int integer) {
        final int integer2 = this.data[integer];
        this.data = ArrayUtils.remove(this.data, integer);
        return IntTag.valueOf(integer2);
    }
    
    @Override
    public void clear() {
        this.data = new int[0];
    }
    
    static {
        TYPE = new TagType<IntArrayTag>() {
            @Override
            public IntArrayTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                kc.accountBits(192L);
                final int integer2 = dataInput.readInt();
                kc.accountBits(32L * integer2);
                final int[] arr6 = new int[integer2];
                for (int integer3 = 0; integer3 < integer2; ++integer3) {
                    arr6[integer3] = dataInput.readInt();
                }
                return new IntArrayTag(arr6);
            }
            
            @Override
            public String getName() {
                return "INT[]";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_Int_Array";
            }
        };
    }
}
