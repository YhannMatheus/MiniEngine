package miniengine.Core;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.media.AudioClip;

public class AssetManager {
    // Cache de Sprites: Nome do Arquivo -> Imagem na Memória
    private static final Map<String, Image> sprites = new HashMap<>();
    private static final Map<String, AudioClip> sounds = new HashMap<>();

    public static Image getSprite(String fileName) {
        String path = fileName.startsWith("/") ? fileName : "/images/" + fileName;

        if (sprites.containsKey(path)) {
            return sprites.get(path);
        }

        try {
            InputStream stream = AssetManager.class.getResourceAsStream(path);
            if (stream == null) {
                System.err.println("ERRO [AssetManager] - Imagem não encontrada: " + path);
                return null;
            }

            Image img = new Image(stream);

            sprites.put(path, img);

            System.out.println("LOG [AssetManager] - Imagem carregada: " + path);
            return img;

        } catch (Exception e) {
            System.err.println("ERRO [AssetManager] - Falha ao ler imagem: " + e.getMessage());
            return null;
        }
    }

    public static AudioClip getAudio(String fileName) {
        String path = fileName.startsWith("/") ? fileName : "/sounds/" + fileName;

        if (sounds.containsKey(path)) {
            return sounds.get(path);
        }

        try {
            URL url = AssetManager.class.getResource(path);

            if (url == null) {
                System.err.println("ERRO [AssetManager] - Audio não encontrado: " + path);
                return null;
            }

            AudioClip clip = new AudioClip(url.toExternalForm());

            sounds.put(path, clip);
            System.out.println("LOG [AssetManager] - Audio carregado: " + path);
            return clip;

        } catch (Exception e) {
            System.err.println("ERRO [AssetManager] - Falha ao ler audio: " + e.getMessage());
            return null;
        }
    }

    public static void clear() {
        sprites.clear();

        System.gc();
        System.out.println("LOG [AssetManager] - Memória de Assets limpa.");
    }
}
