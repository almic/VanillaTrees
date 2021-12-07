package net.minecraft.nbt;

public class NbtAccounter {
    public static final NbtAccounter UNLIMITED;
    private final long quota;
    private long usage;
    
    public NbtAccounter(final long long1) {
        this.quota = long1;
    }
    
    public void accountBits(final long long1) {
        this.usage += long1 / 8L;
        if (this.usage > this.quota) {
            throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.usage + "bytes where max allowed: " + this.quota);
        }
    }
    
    static {
        UNLIMITED = new NbtAccounter(0L) {
            @Override
            public void accountBits(final long long1) {
            }
        };
    }
}
