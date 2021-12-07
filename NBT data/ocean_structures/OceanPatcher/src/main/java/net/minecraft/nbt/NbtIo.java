package net.minecraft.nbt;

import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.util.zip.GZIPInputStream;
import java.io.InputStream;

public class NbtIo {
    public static CompoundTag readCompressed(final InputStream inputStream) throws IOException {
        try (final DataInputStream dataInputStream2 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)))) {
            return read(dataInputStream2, NbtAccounter.UNLIMITED);
        }
    }
    
    public static void writeCompressed(final CompoundTag jt, final OutputStream outputStream) throws IOException {
        try (final DataOutputStream dataOutputStream3 = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)))) {
            write(jt, dataOutputStream3);
        }
    }
    
    public static void safeWrite(final CompoundTag jt, final File file) throws IOException {
        final File file2 = new File(file.getAbsolutePath() + "_tmp");
        if (file2.exists()) {
            file2.delete();
        }
        write(jt, file2);
        if (file.exists()) {
            file.delete();
        }
        if (file.exists()) {
            throw new IOException("Failed to delete " + file);
        }
        file2.renameTo(file);
    }
    
    public static void write(final CompoundTag jt, final File file) throws IOException {
        final DataOutputStream dataOutputStream3 = new DataOutputStream(new FileOutputStream(file));
        try {
            write(jt, dataOutputStream3);
        }
        finally {
            dataOutputStream3.close();
        }
    }
    
    @Nullable
    public static CompoundTag read(final File file) throws IOException {
        if (!file.exists()) {
            return null;
        }
        final DataInputStream dataInputStream2 = new DataInputStream(new FileInputStream(file));
        try {
            return read(dataInputStream2, NbtAccounter.UNLIMITED);
        }
        finally {
            dataInputStream2.close();
        }
    }
    
    public static CompoundTag read(final DataInputStream dataInputStream) throws IOException {
        return read(dataInputStream, NbtAccounter.UNLIMITED);
    }
    
    public static CompoundTag read(final DataInput dataInput, final NbtAccounter kc) throws IOException {
        final Tag kj3 = readUnnamedTag(dataInput, 0, kc);
        if (kj3 instanceof CompoundTag) {
            return (CompoundTag)kj3;
        }
        throw new IOException("Root tag must be a named compound tag");
    }
    
    public static void write(final CompoundTag jt, final DataOutput dataOutput) throws IOException {
        writeUnnamedTag(jt, dataOutput);
    }
    
    private static void writeUnnamedTag(final Tag kj, final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(kj.getId());
        if (kj.getId() == 0) {
            return;
        }
        dataOutput.writeUTF("");
        kj.write(dataOutput);
    }
    
    private static Tag readUnnamedTag(final DataInput dataInput, final int integer, final NbtAccounter kc) throws IOException {
        final byte byte4 = dataInput.readByte();
        if (byte4 == 0) {
            return EndTag.INSTANCE;
        }
        dataInput.readUTF();
        try {
            return TagTypes.getType(byte4).load(dataInput, integer, kc);
        }
        catch (IOException iOException5) {
            System.err.println("Failed loading NBT:");
            System.err.println("  Tag type: " + String.valueOf(byte4));
            return null;
        }
    }
}
