package net.minecraft.nbt;

public class TagTypes {
    private static final TagType<?>[] TYPES;
    
    public static TagType<?> getType(final int integer) {
        if (integer < 0 || integer >= TagTypes.TYPES.length) {
            return TagType.createInvalid(integer);
        }
        return TagTypes.TYPES[integer];
    }
    
    static {
        TYPES = new TagType[] { EndTag.TYPE, ByteTag.TYPE, ShortTag.TYPE, IntTag.TYPE, LongTag.TYPE, FloatTag.TYPE, DoubleTag.TYPE, ByteArrayTag.TYPE, StringTag.TYPE, ListTag.TYPE, CompoundTag.TYPE, IntArrayTag.TYPE, LongArrayTag.TYPE };
    }
}
