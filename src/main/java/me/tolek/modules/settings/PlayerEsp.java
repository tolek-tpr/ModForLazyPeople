package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;
import me.tolek.modules.settings.executor.PlayerEspImpl;

public class PlayerEsp extends BooleanSetting {

    public PlayerEsp() {
        super("Player ESP", false, "Draws boxes on players near you.");
        PlayerEspImpl playerEspImpl = new PlayerEspImpl();
        playerEspImpl.setEnabled(true);
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
