package me.tolek.files;

import com.google.gson.*;
import me.tolek.modules.settings.base.*;

import java.lang.reflect.Type;

public class MflpSettingTypeAdapter implements JsonDeserializer<MflpSetting> {

    @Override
    public MflpSetting deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        Gson gson = new Gson();
        JsonElement jsonSettingType = obj.get("type");
        String settingType = jsonSettingType != null ? jsonSettingType.getAsString() : "";

        if (settingType.equals("boolean")) {
            return gson.fromJson(obj, BooleanSetting.class);
        } else if (settingType.equals("button")) {
            return gson.fromJson(obj, ButtonSetting.class);
        } else if (settingType.equals("color")) {
            return gson.fromJson(obj, ColorSetting.class);
        } else if (settingType.equals("float")) {
            return gson.fromJson(obj, FloatSetting.class);
        } else if (settingType.equals("int")) {
            return gson.fromJson(obj, IntegerSetting.class);
        } else if (settingType.equals("list")) {
            return gson.fromJson(obj, ListSetting.class);
        } else if (settingType.equals("string")) {
            return gson.fromJson(obj, StringSetting.class);
        }

        return null;
    }

}
