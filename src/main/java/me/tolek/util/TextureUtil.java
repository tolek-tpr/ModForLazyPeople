package me.tolek.util;

import me.tolek.ModForLazyPeople;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TextureUtil {

    // Cache to store textures for player faces
    private static final Map<String, Identifier> textureCache = new HashMap<>();

    public static Identifier getPlayerFace(String username) {
        // Check the cache for the player face.
        if (textureCache.containsKey(username))
            return textureCache.get(username);

        String url = "https://api.mcheads.org/head/" + username;
        try {
            /*// Fetch image from the URL as BufferedImage
            BufferedImage bufferedImage = ImageIO.read(new URL(url));

            // ARGB - ABGR conversion
            NativeImage nativeImage = new NativeImage(bufferedImage.getWidth(), bufferedImage.getHeight(), true);
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                for (int y = 0; y < bufferedImage.getHeight(); y++) {
                    int argbColor = bufferedImage.getRGB(x, y);

                    int a = argbColor >> 24 | 0xFF;
                    int r = argbColor >> 16 | 0xFF;
                    int g = argbColor >> 8  | 0xFF;
                    int b = argbColor >> 0  | 0xFF;

                    int rgbaColor = a << 24 | b << 16 | g << 8 | r << 0;

                    nativeImage.setColor(x, y, rgbaColor);
                }
            }*/

            NativeImage nativeImage = NativeImage.read(new URL(url).openStream());

            // Generate a unique Identifier based on the username
            Identifier textureId = Identifier.of(ModForLazyPeople.MOD_ID, "player_faces/" + username.toLowerCase());

            // Register the texture with Minecraft's TextureManager
            TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
            textureManager.registerTexture(textureId, new NativeImageBackedTexture(nativeImage));

            // Add it to the cache
            textureCache.put(username, textureId);

            return textureId;

        } catch (IOException e) {
            ModForLazyPeople.LOGGER.error("Failed to fetch player face: ", e);
        }

        return null; // Return null if there was an error
    }

}
