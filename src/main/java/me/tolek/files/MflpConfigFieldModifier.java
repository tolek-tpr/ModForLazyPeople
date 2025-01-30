package me.tolek.files;

public abstract class MflpConfigFieldModifier<T extends ISerializable> {

    public abstract T accept(ISerializable modifiable);

    public abstract Class<T> getFieldType();

}
