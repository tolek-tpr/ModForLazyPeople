package me.tolek.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import me.tolek.Macro.Macro;
import net.minecraft.client.option.KeyBinding;

import java.io.*;
import java.util.ArrayList;
//import java.util.function.Supplier;

public class MflpConfigManager {

    private static final String CONFIG_FILE = "MflpConfig.json";
    private Gson gson;

    public MflpConfigManager() {
        GsonBuilder builder = new GsonBuilder();
        //builder.registerTypeAdapter(Supplier.class, new SupplierTypeAdapter());
        builder.setPrettyPrinting();
        gson = builder.setPrettyPrinting().create();
    }

    public void save(ArrayList<Macro> macros, boolean shownWelcomeScreen) {
        File configFileObject = new File(CONFIG_FILE);
        if (configFileObject.exists()) {
            configFileObject.delete();
        }

        ModData modData = new ModData(macros, shownWelcomeScreen);
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(modData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ModData load() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            return gson.fromJson(reader, ModData.class);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            System.out.println("File not found!");
            return null;
        }
    }

    public class ShortMacro {

        public String name;
        public ArrayList<String> commands;
        public int key;
        public int repeatAmt = 1;
        public boolean isUneditable = false;
        public boolean isOn = true;

        public ShortMacro(String name, ArrayList<String> commands, int key, int repeatAmt, boolean isUneditable, boolean isOn) {
            this.name = name;
            this.commands = commands;
            this.key = key;
            this.repeatAmt = repeatAmt;
            this.isUneditable = isUneditable;
            this.isOn = isOn;
        }

    }

    public class ModData {
        private ArrayList<ShortMacro> macros = new ArrayList<>();
        private boolean shownWelcomeScreen;

        public ModData(ArrayList<Macro> macros, boolean shownWelcomeScreen) {
            for (Macro m : macros) {
                this.macros.add(new ShortMacro(m.getName(), m.getCommands(), m.getKey(), m.getRepeatAmount(), m.getUneditable(), m.getTurnedOn()));
            }

            //this.macros = macros;
            this.shownWelcomeScreen = shownWelcomeScreen;
        }

        public ArrayList<Macro> getMacros() {
            ArrayList<Macro> macros = new ArrayList<>();
            for (ShortMacro sm : this.macros) {
                KeyBinding kb = new KeyBinding("mflp.keybinding.undefined",
                        sm.key,
                        "mflp.keybindCategory.MFLP");
                macros.add(new Macro(kb, sm.commands, sm.name, sm.repeatAmt));
            }

            return macros;
        }

        public ArrayList<ShortMacro> getShortMacros() {
            return this.macros;
        }


        public boolean isShownWelcomeScreen() {
            return shownWelcomeScreen;
        }
    }


}
