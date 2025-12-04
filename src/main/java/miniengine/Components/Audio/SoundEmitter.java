package miniengine.Components.Audio;

import javafx.scene.media.AudioClip;
import miniengine.Core.Game;
import miniengine.Structure.GameComponent;
import miniengine.Structure.Transform;

import java.net.URL;

public class SoundEmitter extends GameComponent {
    private AudioClip clip;
    private String soundPath;

    public double maxDistance = 500;
    public double baseVolume = 1.0;
    public boolean loop = false;
    public boolean playOnStart = false;

    private boolean isPlaying = false;

    public SoundEmitter(String soundFile) {
        String path = soundFile.startsWith("/") ? soundFile : "/sounds/" + soundFile;
    }

    @Override
    public void start(){
        loadClip();

        if (clip != null) {
            if (loop) clip.setCycleCount(AudioClip.INDEFINITE);
            if (playOnStart) play();
        }
    }

    private void loadClip() {
        try {
            URL url = getClass().getResource(soundPath);

            if (url == null) {
                System.err.println("ERRO [" + gameObject.name + "] - Som não encontrado: " + soundPath);
            } else {
                this.clip = new AudioClip(url.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERRO CRÍTICO [" + gameObject.name + "] - Falha ao ler som: " + soundPath);
        }
    }

    @Override
    public void update() {
        if (clip == null || !isPlaying) return;

        SoundListener listener = Game.getInstance().getListener();
        if (listener == null) return;

        Transform myT = gameObject.transform;
        Transform listT = listener.gameObject.transform;

        double distance = myT.position.distance(listT.position);
        double volume = 0;

        if (distance < maxDistance) {
            volume = baseVolume * (1.0 - (distance / maxDistance));
        }
        if (volume < 0) volume = 0;

        clip.setVolume(volume);
    }

    public void play() {
        if (clip != null && !isPlaying) {
            clip.play(baseVolume);
            isPlaying = true;
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            isPlaying = false;
        }
    }

    @Override
    public void destroy() {
        stop();
    }
}
