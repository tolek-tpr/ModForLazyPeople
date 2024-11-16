package me.tolek.files;

import me.tolek.modules.settings.AutoWelcomeBack;

public class TestWbModifier extends ConfigFieldModifier<AutoWelcomeBack> {

    public TestWbModifier() {
        super(AutoWelcomeBack.class);
    }

    @Override
    public AutoWelcomeBack accept(AutoWelcomeBack param) {
        return null;
    }
}
