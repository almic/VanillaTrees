package net.minecraft.nbt;

import java.io.DataInput;
import java.util.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.io.DataOutput;
import com.google.common.collect.Lists;
import java.util.List;

public class ListTag extends CollectionTag<Tag> {
    public static final TagType<ListTag> TYPE;
    private final List<Tag> list;
    private byte type;
    
    private ListTag(final List<Tag> list, final byte byte2) {
        this.list = list;
        this.type = byte2;
    }
    
    public ListTag() {
        this(Lists.newArrayList(), (byte)0);
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        if (this.list.isEmpty()) {
            this.type = 0;
        }
        else {
            this.type = this.list.get(0).getId();
        }
        dataOutput.writeByte(this.type);
        dataOutput.writeInt(this.list.size());
        for (final Tag kj4 : this.list) {
            kj4.write(dataOutput);
        }
    }
    
    @Override
    public byte getId() {
        return 9;
    }
    
    @Override
    public TagType<ListTag> getType() {
        return ListTag.TYPE;
    }
    
    @Override
    public String toString() {
        final StringBuilder stringBuilder2 = new StringBuilder("[");
        for (int integer3 = 0; integer3 < this.list.size(); ++integer3) {
            if (integer3 != 0) {
                stringBuilder2.append(',');
            }
            stringBuilder2.append(this.list.get(integer3));
        }
        return stringBuilder2.append(']').toString();
    }
    
    private void updateTypeAfterRemove() {
        if (this.list.isEmpty()) {
            this.type = 0;
        }
    }
    
    @Override
    public Tag remove(final int integer) {
        final Tag kj3 = this.list.remove(integer);
        this.updateTypeAfterRemove();
        return kj3;
    }
    
    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    public CompoundTag getCompound(final int integer) {
        if (integer >= 0 && integer < this.list.size()) {
            final Tag kj3 = this.list.get(integer);
            if (kj3.getId() == 10) {
                return (CompoundTag)kj3;
            }
        }
        return new CompoundTag();
    }
    
    public ListTag getList(final int integer) {
        if (integer >= 0 && integer < this.list.size()) {
            final Tag kj3 = this.list.get(integer);
            if (kj3.getId() == 9) {
                return (ListTag)kj3;
            }
        }
        return new ListTag();
    }
    
    public short getShort(final int integer) {
        if (integer >= 0 && integer < this.list.size()) {
            final Tag kj3 = this.list.get(integer);
            if (kj3.getId() == 2) {
                return ((ShortTag)kj3).getAsShort();
            }
        }
        return 0;
    }
    
    public int getInt(final int integer) {
        if (integer >= 0 && integer < this.list.size()) {
            final Tag kj3 = this.list.get(integer);
            if (kj3.getId() == 3) {
                return ((IntTag)kj3).getAsInt();
            }
        }
        return 0;
    }
    
    public int[] getIntArray(final int integer) {
        if (integer >= 0 && integer < this.list.size()) {
            final Tag kj3 = this.list.get(integer);
            if (kj3.getId() == 11) {
                return ((IntArrayTag)kj3).getAsIntArray();
            }
        }
        return new int[0];
    }
    
    public double getDouble(final int integer) {
        if (integer >= 0 && integer < this.list.size()) {
            final Tag kj3 = this.list.get(integer);
            if (kj3.getId() == 6) {
                return ((DoubleTag)kj3).getAsDouble();
            }
        }
        return 0.0;
    }
    
    public float getFloat(final int integer) {
        if (integer >= 0 && integer < this.list.size()) {
            final Tag kj3 = this.list.get(integer);
            if (kj3.getId() == 5) {
                return ((FloatTag)kj3).getAsFloat();
            }
        }
        return 0.0f;
    }
    
    public String getString(final int integer) {
        if (integer < 0 || integer >= this.list.size()) {
            return "";
        }
        final Tag kj3 = this.list.get(integer);
        if (kj3.getId() == 8) {
            return kj3.getAsString();
        }
        return kj3.toString();
    }
    
    @Override
    public int size() {
        return this.list.size();
    }
    
    @Override
    public Tag get(final int integer) {
        return this.list.get(integer);
    }
    
    @Override
    public Tag set(final int integer, final Tag kj) {
        final Tag kj2 = this.get(integer);
        if (!this.setTag(integer, kj)) {
            throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", kj.getId(), this.type));
        }
        return kj2;
    }
    
    @Override
    public void add(final int integer, final Tag kj) {
        if (!this.addTag(integer, kj)) {
            throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", kj.getId(), this.type));
        }
    }
    
    @Override
    public boolean setTag(final int integer, final Tag kj) {
        if (this.updateType(kj)) {
            this.list.set(integer, kj);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean addTag(final int integer, final Tag kj) {
        if (this.updateType(kj)) {
            this.list.add(integer, kj);
            return true;
        }
        return false;
    }
    
    private boolean updateType(final Tag kj) {
        if (kj.getId() == 0) {
            return false;
        }
        if (this.type == 0) {
            this.type = kj.getId();
            return true;
        }
        return this.type == kj.getId();
    }
    
    @Override
    public ListTag copy() {
        final Iterable<Tag> iterable2 = TagTypes.getType(this.type).isValue() ? this.list : Iterables.<Tag, Tag>transform((Iterable<Tag>)this.list, Tag::copy);
        final List<Tag> list3 = Lists.newArrayList(iterable2);
        return new ListTag(list3, this.type);
    }
    
    @Override
    public boolean equals(final Object object) {
        return this == object || (object instanceof ListTag && Objects.equals(this.list, ((ListTag)object).list));
    }
    
    @Override
    public int hashCode() {
        return this.list.hashCode();
    }
    
    public int getElementType() {
        return this.type;
    }
    
    @Override
    public void clear() {
        this.list.clear();
        this.type = 0;
    }
    
    static {
        TYPE = new TagType<ListTag>() {
            @Override
            public ListTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                kc.accountBits(296L);
                if (integer > 512) {
                    throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
                }
                final byte byte5 = dataInput.readByte();
                final int integer2 = dataInput.readInt();
                if (byte5 == 0 && integer2 > 0) {
                    throw new RuntimeException("Missing type on ListTag");
                }
                kc.accountBits(32L * integer2);
                final TagType<?> kl7 = TagTypes.getType(byte5);
                final List<Tag> list8 = Lists.newArrayListWithCapacity(integer2);
                for (int integer3 = 0; integer3 < integer2; ++integer3) {
                    list8.add((Tag)kl7.load(dataInput, integer + 1, kc));
                }
                return new ListTag(list8, byte5);
            }
            
            @Override
            public String getName() {
                return "LIST";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_List";
            }
        };
    }
}
