package me.tolek.files;

public abstract class ConfigFieldModifier<T> {

    private final Class<T> type;

    public ConfigFieldModifier(Class<T> type) {
        this.type = type;
    }

    public abstract T accept(T param);

    public Class<T> getType() { return type; }

}
