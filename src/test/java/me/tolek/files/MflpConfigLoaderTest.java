package me.tolek.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.settings.AutoWbFilter;
import me.tolek.modules.settings.AutoWelcomeBack;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.MflpSetting;
import me.tolek.util.MflpUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MflpConfigLoaderTest {

    @Test
    void loadSettings() {
        Gson gson;
        gson = new GsonBuilder()/*.registerTypeAdapter(MflpSetting.class, new MflpSettingTypeAdapter())*/.setPrettyPrinting().create();

        String path = System.getProperty("user.dir") + "/run/config/mflp/MflpSettings.json";

        try (FileReader fr = new FileReader(path, StandardCharsets.UTF_8)) {
            Logger logger = MflpUtil.getConfigLogger();
            MflpConfigLoader loader = new MflpConfigLoader();

            ArrayList<MflpConfigFieldModifier<? extends ISerializable>> modifiers = new ArrayList<>();

            modifiers.add(new MflpConfigFieldModifier<AutoWbFilter>() {
                @Override
                public AutoWbFilter accept(ISerializable modifiable) {
                    AutoWbFilter out = (AutoWbFilter) modifiable;
                    out.setState(2);

                    return out;
                }

                @Override
                public Class<AutoWbFilter> getFieldType() {
                    return AutoWbFilter.class;
                }
            });

            loader.loadSettings(gson.fromJson(fr, ModData.SettingsData.class), logger, new ArrayList<>());

            assertTrue(MflpSettingsList.getInstance().AUTO_WELCOME_BACK.getState());
            assertEquals(2, MflpSettingsList.getInstance().WB_FILTER.getState());

            assertTrue(((AutoWelcomeBack) MflpSettingsList.getInstance().getSettings().get(0)).getState());
            assertEquals(2, ((AutoWbFilter) MflpSettingsList.getInstance().getSettings().get(2)).getState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void loadAutoReplies() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String path = System.getProperty("user.dir") + "/run/config/mflp/MflpAutoReplies.json";

        try (FileReader fr = new FileReader(path, StandardCharsets.UTF_8)) {
            Logger logger = MflpUtil.getConfigLogger();
            MflpConfigLoader loader = new MflpConfigLoader();
            loader.loadAutoReplies(gson.fromJson(fr, ModData.AutoReplyData.class), logger, new ArrayList<>());
            assertFalse(AutoRepliesList.getInstance().getAutoReplies().get(0).isTurnedOn());
        } catch (Exception e) {

        }
    }
}