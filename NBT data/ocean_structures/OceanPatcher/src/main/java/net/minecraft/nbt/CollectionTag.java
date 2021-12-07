package net.minecraft.nbt;

import java.util.AbstractList;

public abstract class CollectionTag<T extends Tag> extends AbstractList<T> implements Tag {
    @Override
    public abstract T set(final int integer, final T kj);
    
    @Override
    public abstract void add(final int integer, final T kj);
    
    @Override
    public abstract T remove(final int integer);
    
    public abstract boolean setTag(final int integer, final Tag kj);
    
    public abstract boolean addTag(final int integer, final Tag kj);
}
