package miniengine.Components.Audio;

import javafx.scene.media.AudioClip;
import miniengine.Core.AssetManager;
import miniengine.Structure.GameComponent;

public class AudioSource extends GameComponent {

    private AudioClip clip;

    public double volume = 1.0;
    public double pitch = 1.0;
    public boolean loop = false;

    public boolean playOnAwake = false;

    public AudioSource(String fileName) {
        this.clip = AssetManager.getAudio(fileName);
    }

    @Override
    public void start() {
        if (playOnAwake && clip != null) {
            play();
        }
    }

    public void play() {
        if (clip == null) return;

        clip.setVolume(volume);
        clip.setRate(pitch);

        if (loop) {
            if (!clip.isPlaying()) clip.play();
        } else {
            clip.play();
        }
    }

    public void stop() {
        if (clip != null) clip.stop();
    }

    public void playOneShot() {
        if (clip == null) return;
        clip.setVolume(volume);
        clip.setRate(pitch);
        clip.play();
    }
}