package net.minecraft.nbt;

import java.io.DataInput;
import java.util.Objects;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import java.util.Set;
import java.io.IOException;
import java.io.DataOutput;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.regex.Pattern;

public class CompoundTag implements Tag {
    private static final Pattern SIMPLE_VALUE;
    public static final TagType<CompoundTag> TYPE;
    private final Map<String, Tag> tags;
    
    private CompoundTag(final Map<String, Tag> map) {
        this.tags = map;
    }
    
    public CompoundTag() {
        this(Maps.newHashMap());
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        for (final String string4 : this.tags.keySet()) {
            final Tag kj5 = this.tags.get(string4);
            writeNamedTag(string4, kj5, dataOutput);
        }
        dataOutput.writeByte(0);
    }
    
    public Set<String> getAllKeys() {
        return this.tags.keySet();
    }
    
    @Override
    public byte getId() {
        return 10;
    }
    
    @Override
    public TagType<CompoundTag> getType() {
        return CompoundTag.TYPE;
    }
    
    public int size() {
        return this.tags.size();
    }
    
    @Nullable
    public Tag put(final String string, final Tag kj) {
        return this.tags.put(string, kj);
    }
    
    public void putByte(final String string, final byte byte2) {
        this.tags.put(string, ByteTag.valueOf(byte2));
    }
    
    public void putShort(final String string, final short short2) {
        this.tags.put(string, ShortTag.valueOf(short2));
    }
    
    public void putInt(final String string, final int integer) {
        this.tags.put(string, IntTag.valueOf(integer));
    }
    
    public void putLong(final String string, final long long2) {
        this.tags.put(string, LongTag.valueOf(long2));
    }
    
    public void putUUID(final String string, final UUID uUID) {
        this.putLong(string + "Most", uUID.getMostSignificantBits());
        this.putLong(string + "Least", uUID.getLeastSignificantBits());
    }
    
    public UUID getUUID(final String string) {
        return new UUID(this.getLong(string + "Most"), this.getLong(string + "Least"));
    }
    
    public boolean hasUUID(final String string) {
        return this.contains(string + "Most", 99) && this.contains(string + "Least", 99);
    }
    
    public void removeUUID(final String string) {
        this.remove(string + "Most");
        this.remove(string + "Least");
    }
    
    public void putFloat(final String string, final float float2) {
        this.tags.put(string, FloatTag.valueOf(float2));
    }
    
    public void putDouble(final String string, final double double2) {
        this.tags.put(string, DoubleTag.valueOf(double2));
    }
    
    public void putString(final String string1, final String string2) {
        this.tags.put(string1, StringTag.valueOf(string2));
    }
    
    public void putByteArray(final String string, final byte[] arr) {
        this.tags.put(string, new ByteArrayTag(arr));
    }
    
    public void putIntArray(final String string, final int[] arr) {
        this.tags.put(string, new IntArrayTag(arr));
    }
    
    public void putIntArray(final String string, final List<Integer> list) {
        this.tags.put(string, new IntArrayTag(list));
    }
    
    public void putLongArray(final String string, final long[] arr) {
        this.tags.put(string, new LongArrayTag(arr));
    }
    
    public void putLongArray(final String string, final List<Long> list) {
        this.tags.put(string, new LongArrayTag(list));
    }
    
    public void putBoolean(final String string, final boolean boolean2) {
        this.tags.put(string, ByteTag.valueOf(boolean2));
    }
    
    @Nullable
    public Tag get(final String string) {
        return this.tags.get(string);
    }
    
    public byte getTagType(final String string) {
        final Tag kj3 = this.tags.get(string);
        if (kj3 == null) {
            return 0;
        }
        return kj3.getId();
    }
    
    public boolean contains(final String string) {
        return this.tags.containsKey(string);
    }
    
    public boolean contains(final String string, final int integer) {
        final int integer2 = this.getTagType(string);
        return integer2 == integer || (integer == 99 && (integer2 == 1 || integer2 == 2 || integer2 == 3 || integer2 == 4 || integer2 == 5 || integer2 == 6));
    }
    
    public byte getByte(final String string) {
        try {
            if (this.contains(string, 99)) {
                return ((NumericTag)this.tags.get(string)).getAsByte();
            }
        }
        catch (ClassCastException ex) {}
        return 0;
    }
    
    public short getShort(final String string) {
        try {
            if (this.contains(string, 99)) {
                return ((NumericTag)this.tags.get(string)).getAsShort();
            }
        }
        catch (ClassCastException ex) {}
        return 0;
    }
    
    public int getInt(final String string) {
        try {
            if (this.contains(string, 99)) {
                return ((NumericTag)this.tags.get(string)).getAsInt();
            }
        }
        catch (ClassCastException ex) {}
        return 0;
    }
    
    public long getLong(final String string) {
        try {
            if (this.contains(string, 99)) {
                return ((NumericTag)this.tags.get(string)).getAsLong();
            }
        }
        catch (ClassCastException ex) {}
        return 0L;
    }
    
    public float getFloat(final String string) {
        try {
            if (this.contains(string, 99)) {
                return ((NumericTag)this.tags.get(string)).getAsFloat();
            }
        }
        catch (ClassCastException ex) {}
        return 0.0f;
    }
    
    public double getDouble(final String string) {
        try {
            if (this.contains(string, 99)) {
                return ((NumericTag)this.tags.get(string)).getAsDouble();
            }
        }
        catch (ClassCastException ex) {}
        return 0.0;
    }
    
    public String getString(final String string) {
        try {
            if (this.contains(string, 8)) {
                return this.tags.get(string).getAsString();
            }
        }
        catch (ClassCastException ex) {}
        return "";
    }
    
    public byte[] getByteArray(final String string) {
        try {
            if (this.contains(string, 7)) {
                return ((ByteArrayTag)this.tags.get(string)).getAsByteArray();
            }
        }
        catch (ClassCastException ex) {}
        return new byte[0];
    }
    
    public int[] getIntArray(final String string) {
        try {
            if (this.contains(string, 11)) {
                return ((IntArrayTag)this.tags.get(string)).getAsIntArray();
            }
        }
        catch (ClassCastException ex) {}
        return new int[0];
    }
    
    public long[] getLongArray(final String string) {
        try {
            if (this.contains(string, 12)) {
                return ((LongArrayTag)this.tags.get(string)).getAsLongArray();
            }
        }
        catch (ClassCastException ex) {}
        return new long[0];
    }
    
    public CompoundTag getCompound(final String string) {
        try {
            if (this.contains(string, 10)) {
                return ((CompoundTag)this.tags.get(string));
            }
        }
        catch (ClassCastException ex) {}
        return new CompoundTag();
    }
    
    public ListTag getList(final String string, final int integer) {
        try {
            if (this.getTagType(string) == 9) {
                final ListTag jz4 = (ListTag)this.tags.get(string);
                if (jz4.isEmpty() || jz4.getElementType() == integer) {
                    return jz4;
                }
                return new ListTag();
            }
        }
        catch (ClassCastException ex) {}
        return new ListTag();
    }
    
    public boolean getBoolean(final String string) {
        return this.getByte(string) != 0;
    }
    
    public void remove(final String string) {
        this.tags.remove(string);
    }
    
    @Override
    public String toString() {
        final StringBuilder stringBuilder2 = new StringBuilder("{");
        Collection<String> collection3 = this.tags.keySet();
        /*if (CompoundTag.LOGGER.isDebugEnabled()) {
            final List<String> list4 = Lists.newArrayList(this.tags.keySet());
            Collections.<String>sort(list4);
            collection3 = list4;
        }*/
        for (final String string5 : collection3) {
            if (stringBuilder2.length() != 1) {
                stringBuilder2.append(',');
            }
            stringBuilder2.append(handleEscape(string5)).append(':').append(this.tags.get(string5));
        }
        return stringBuilder2.append('}').toString();
    }
    
    public boolean isEmpty() {
        return this.tags.isEmpty();
    }
    
    @Override
    public CompoundTag copy() {
        final Map<String, Tag> map2 = Maps.newHashMap(Maps.transformValues(this.tags, Tag::copy));
        return new CompoundTag(map2);
    }
    
    @Override
    public boolean equals(final Object object) {
        return this == object || (object instanceof CompoundTag && Objects.equals(this.tags, ((CompoundTag)object).tags));
    }
    
    @Override
    public int hashCode() {
        return this.tags.hashCode();
    }
    
    private static void writeNamedTag(final String string, final Tag kj, final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(kj.getId());
        if (kj.getId() == 0) {
            return;
        }
        dataOutput.writeUTF(string);
        kj.write(dataOutput);
    }
    
    private static byte readNamedTagType(final DataInput dataInput, final NbtAccounter kc) throws IOException {
        return dataInput.readByte();
    }
    
    private static String readNamedTagName(final DataInput dataInput, final NbtAccounter kc) throws IOException {
        return dataInput.readUTF();
    }
    
    private static Tag readNamedTagData(final TagType<?> kl, final String string, final DataInput dataInput, final int integer, final NbtAccounter kc) {
        try {
            return kl.load(dataInput, integer, kc);
        }
        catch (IOException iOException6) {
            System.err.println("Failed loading NBT:");
            System.err.println("  Tag name: " + string);
            System.err.println("  Tag type: " + kl.getName());
            return null;
        }
    }
    
    public CompoundTag merge(final CompoundTag jt) {
        for (final String string4 : jt.tags.keySet()) {
            final Tag kj5 = jt.tags.get(string4);
            if (kj5.getId() == 10) {
                if (this.contains(string4, 10)) {
                    final CompoundTag jt2 = this.getCompound(string4);
                    jt2.merge((CompoundTag)kj5);
                }
                else {
                    this.put(string4, kj5.copy());
                }
            }
            else {
                this.put(string4, kj5.copy());
            }
        }
        return this;
    }
    
    protected static String handleEscape(final String string) {
        if (CompoundTag.SIMPLE_VALUE.matcher(string).matches()) {
            return string;
        }
        return StringTag.quoteAndEscape(string);
    }
    
    static {
        SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
        TYPE = new TagType<CompoundTag>() {
            @Override
            public CompoundTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                kc.accountBits(384L);
                if (integer > 512) {
                    throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
                }
                final Map<String, Tag> map5 = Maps.newHashMap();
                byte byte6;
                while ((byte6 = readNamedTagType(dataInput, kc)) != 0) {
                    final String string7 = readNamedTagName(dataInput, kc);
                    kc.accountBits(224 + 16 * string7.length());
                    final Tag kj8 = readNamedTagData(TagTypes.getType(byte6), string7, dataInput, integer + 1, kc);
                    if (map5.put(string7, kj8) != null) {
                        kc.accountBits(288L);
                    }
                }
                return new CompoundTag(map5);
            }
            
            @Override
            public String getName() {
                return "COMPOUND";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_Compound";
            }
        };
    }
}
