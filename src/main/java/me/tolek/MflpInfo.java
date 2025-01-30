package me.tolek;

import me.tolek.files.ISerializable;

public class MflpInfo implements ISerializable {

    private static MflpInfo instance;

    private MflpInfo() {}

    public static MflpInfo getInstance() {
        if (instance == null) instance = new MflpInfo();
        return instance;
    }

    public boolean shownWelcomeScreen = false;
    public String loadedFileVersion = "";

}
