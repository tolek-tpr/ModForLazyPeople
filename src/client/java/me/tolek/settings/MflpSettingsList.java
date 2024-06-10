package me.tolek.settings;

import me.tolek.settings.base.BooleanSetting;
import me.tolek.settings.base.MflpSetting;

import java.util.ArrayList;

public class MflpSettingsList {

    private static MflpSettingsList instance;


    public AutoWelcomeBack AUTO_WELCOME_BACK = new AutoWelcomeBack();

    private MflpSettingsList() {

    }

    public static MflpSettingsList getInstance() {
        if (instance == null) MflpSettingsList.instance = new MflpSettingsList();
        return instance;
    }

    public ArrayList<MflpSetting> getSettings() {
        ArrayList<MflpSetting> settings = new ArrayList<>();
        settings.add(AUTO_WELCOME_BACK);
        return settings;
    }

}
