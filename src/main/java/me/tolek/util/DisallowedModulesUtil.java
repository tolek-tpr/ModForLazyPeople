package me.tolek.util;

import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.BooleanSetting;

import java.util.ArrayList;

public class DisallowedModulesUtil {

    private static final DisallowedModulesDefinition[] DISALLOWED_MODULES_DEFINITIONS = generateDisallowedModulesDefinitions();

    private static DisallowedModulesDefinition[] generateDisallowedModulesDefinitions() {
        ArrayList<DisallowedModulesDefinition> definitions = new ArrayList<>();
        MflpSettingsList list = MflpSettingsList.getInstance();

        definitions.add(new DisallowedModuleBuilder()
                        .withServerIP("synergyserver.net")
                        .addDisallowedModule(list.AUTO_WELCOME_BACK, false)
                        .addDisallowedModule(list.AUTO_WELCOME, false)
                        .build());

        return definitions.toArray(new DisallowedModulesDefinition[0]);
    }

    public static ArrayList<Tuple<BooleanSetting, Boolean>> getDisallowedModules(String serverAddress) {
        ArrayList<Tuple<BooleanSetting, Boolean>> result = new ArrayList<>();

        for (DisallowedModulesDefinition disallowedModulesDefinition : DISALLOWED_MODULES_DEFINITIONS) {
            if (serverAddress.endsWith(disallowedModulesDefinition.serverIP)) {
                for (Tuple<BooleanSetting, Boolean> state : disallowedModulesDefinition.states) {
                    if (state.value1.getState() != state.value2)
                        result.add(state);
                }
            }
        }

        return result;
    }

    public static boolean usingDisallowedModule(String serverAddress) {
        return !getDisallowedModules(serverAddress).isEmpty();
    }

    public static void fixDisallowedModules(String serverAddress) {
        for (Tuple<BooleanSetting, Boolean> state : getDisallowedModules(serverAddress))
            state.value1.setState(state.value2);
    }

    private static class DisallowedModuleBuilder {

        private final DisallowedModulesDefinition definition = new DisallowedModulesDefinition();

        public DisallowedModulesDefinition build() {
            return definition;
        }

        public DisallowedModuleBuilder withServerIP(String serverIP) {
            definition.serverIP = serverIP;
            return this;
        }

        public DisallowedModuleBuilder addDisallowedModule(BooleanSetting setting, boolean requiredState) {
            definition.states.add(new Tuple<>(setting, requiredState));
            return this;
        }

    }

    private static class DisallowedModulesDefinition {
        public String serverIP;
        public ArrayList<Tuple<BooleanSetting, Boolean>> states = new ArrayList<>();
    }

}
