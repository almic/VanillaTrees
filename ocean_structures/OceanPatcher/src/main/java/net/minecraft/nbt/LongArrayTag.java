package net.minecraft.nbt;

import java.io.DataInput;
import org.apache.commons.lang3.ArrayUtils;
import java.util.Arrays;
import java.io.IOException;
import java.io.DataOutput;
import java.util.List;

public class LongArrayTag extends CollectionTag<LongTag> {
    public static final TagType<LongArrayTag> TYPE;
    private long[] data;
    
    public LongArrayTag(final long[] arr) {
        this.data = arr;
    }
    
    public LongArrayTag(final List<Long> list) {
        this(toArray(list));
    }
    
    private static long[] toArray(final List<Long> list) {
        final long[] arr2 = new long[list.size()];
        for (int integer3 = 0; integer3 < list.size(); ++integer3) {
            final Long long4 = list.get(integer3);
            arr2[integer3] = ((long4 == null) ? 0L : long4);
        }
        return arr2;
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data.length);
        for (final long long6 : this.data) {
            dataOutput.writeLong(long6);
        }
    }
    
    @Override
    public byte getId() {
        return 12;
    }
    
    @Override
    public TagType<LongArrayTag> getType() {
        return LongArrayTag.TYPE;
    }
    
    @Override
    public String toString() {
        final StringBuilder stringBuilder2 = new StringBuilder("[L;");
        for (int integer3 = 0; integer3 < this.data.length; ++integer3) {
            if (integer3 != 0) {
                stringBuilder2.append(',');
            }
            stringBuilder2.append(this.data[integer3]).append('L');
        }
        return stringBuilder2.append(']').toString();
    }
    
    @Override
    public LongArrayTag copy() {
        final long[] arr2 = new long[this.data.length];
        System.arraycopy(this.data, 0, arr2, 0, this.data.length);
        return new LongArrayTag(arr2);
    }
    
    @Override
    public boolean equals(final Object object) {
        return this == object || (object instanceof LongArrayTag && Arrays.equals(this.data, ((LongArrayTag)object).data));
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.data);
    }
    
    public long[] getAsLongArray() {
        return this.data;
    }
    
    @Override
    public int size() {
        return this.data.length;
    }
    
    @Override
    public LongTag get(final int integer) {
        return LongTag.valueOf(this.data[integer]);
    }
    
    @Override
    public LongTag set(final int integer, final LongTag kb) {
        final long long4 = this.data[integer];
        this.data[integer] = kb.getAsLong();
        return LongTag.valueOf(long4);
    }
    
    @Override
    public void add(final int integer, final LongTag kb) {
        this.data = ArrayUtils.add(this.data, integer, kb.getAsLong());
    }
    
    @Override
    public boolean setTag(final int integer, final Tag kj) {
        if (kj instanceof NumericTag) {
            this.data[integer] = ((NumericTag)kj).getAsLong();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean addTag(final int integer, final Tag kj) {
        if (kj instanceof NumericTag) {
            this.data = ArrayUtils.add(this.data, integer, ((NumericTag)kj).getAsLong());
            return true;
        }
        return false;
    }
    
    @Override
    public LongTag remove(final int integer) {
        final long long3 = this.data[integer];
        this.data = ArrayUtils.remove(this.data, integer);
        return LongTag.valueOf(long3);
    }
    
    @Override
    public void clear() {
        this.data = new long[0];
    }
    
    static {
        TYPE = new TagType<LongArrayTag>() {
            @Override
            public LongArrayTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                kc.accountBits(192L);
                final int integer2 = dataInput.readInt();
                kc.accountBits(64L * integer2);
                final long[] arr6 = new long[integer2];
                for (int integer3 = 0; integer3 < integer2; ++integer3) {
                    arr6[integer3] = dataInput.readLong();
                }
                return new LongArrayTag(arr6);
            }
            
            @Override
            public String getName() {
                return "LONG[]";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_Long_Array";
            }
        };
    }
}
