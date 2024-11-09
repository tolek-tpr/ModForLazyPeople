package me.tolek.event;

public abstract class EventImpl {

    private boolean enabled;
    public void onEnable() {}
    public void onDisable() {}

    public final void setEnabled(boolean enabled)
    {
        if(this.enabled == enabled)
            return;

        this.enabled = enabled;

        if(enabled)
            onEnable();
        else
            onDisable();
    }

}
