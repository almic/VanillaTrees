package net.minecraft.nbt;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;
import java.util.Objects;

public class StringTag implements Tag {
    public static final TagType<StringTag> TYPE;
    private static final StringTag EMPTY;
    private final String data;
    
    private StringTag(final String string) {
        Objects.<String>requireNonNull(string, "Null string not allowed");
        this.data = string;
    }
    
    public static StringTag valueOf(final String string) {
        if (string.isEmpty()) {
            return StringTag.EMPTY;
        }
        return new StringTag(string);
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.data);
    }
    
    @Override
    public byte getId() {
        return 8;
    }
    
    @Override
    public TagType<StringTag> getType() {
        return StringTag.TYPE;
    }
    
    @Override
    public String toString() {
        return quoteAndEscape(this.data);
    }
    
    @Override
    public StringTag copy() {
        return this;
    }
    
    @Override
    public boolean equals(final Object object) {
        return this == object || (object instanceof StringTag && Objects.equals(this.data, ((StringTag)object).data));
    }
    
    @Override
    public int hashCode() {
        return this.data.hashCode();
    }
    
    @Override
    public String getAsString() {
        return this.data;
    }
    
    public static String quoteAndEscape(final String string) {
        final StringBuilder stringBuilder2 = new StringBuilder(" ");
        char character3 = '\0';
        for (int integer4 = 0; integer4 < string.length(); ++integer4) {
            final char character4 = string.charAt(integer4);
            if (character4 == '\\') {
                stringBuilder2.append('\\');
            }
            else if (character4 == '\"' || character4 == '\'') {
                if (character3 == '\0') {
                    character3 = ((character4 == '\"') ? '\'' : '\"');
                }
                if (character3 == character4) {
                    stringBuilder2.append('\\');
                }
            }
            stringBuilder2.append(character4);
        }
        if (character3 == '\0') {
            character3 = '\"';
        }
        stringBuilder2.setCharAt(0, character3);
        stringBuilder2.append(character3);
        return stringBuilder2.toString();
    }
    
    static {
        TYPE = new TagType<StringTag>() {
            @Override
            public StringTag load(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
                kc.accountBits(288L);
                final String string5 = dataInput.readUTF();
                kc.accountBits(16 * string5.length());
                return StringTag.valueOf(string5);
            }
            
            @Override
            public String getName() {
                return "STRING";
            }
            
            @Override
            public String getPrettyName() {
                return "TAG_String";
            }
            
            @Override
            public boolean isValue() {
                return true;
            }
        };
        EMPTY = new StringTag("");
    }
}
