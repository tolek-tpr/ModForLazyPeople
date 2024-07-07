package me.tolek.event;

import me.tolek.interfaces.Listener;
import me.tolek.interfaces.RenderListener;
import me.tolek.interfaces.UpdateListener;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.PlayerEsp;
import me.tolek.modules.settings.executor.PlayerEspImpl;

import java.util.ArrayList;
import java.util.List;

public class Events {

    public List<RenderListener> renderListeners = new ArrayList<>();
    public List<UpdateListener> updateListeners = new ArrayList<>();

    private static Events instance;
    private MflpSettingsList settingsList = MflpSettingsList.getInstance();

    private Events() {
        renderListeners.add(new PlayerEspImpl());
        updateListeners.add(new PlayerEspImpl());
    }

    public static Events getInstance() {
        if (instance == null) instance = new Events();
        return instance;
    }

}
