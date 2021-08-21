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

    public static final int END = 0;
    public static final int BYTE = 1;
    public static final int SHORT = 2;
    public static final int INT = 3;
    public static final int LONG = 4;
    public static final int FLOAT = 5;
    public static final int DOUBLE = 6;
    public static final int BYTE_ARRAY = 7;
    public static final int STRING = 8;
    public static final int LIST = 9;
    public static final int COMPOUND = 10;
    public static final int INT_ARRAY = 11;
    public static final int LONG_ARRAY = 12;
}
