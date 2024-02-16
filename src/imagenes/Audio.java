package imagenes;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author compi
 */
public class Audio {

    public Audio() {
        
    }

    public File filePointAudio() {
        return new File("point_audio.wav");
    }

    public File fileJumpAudio() {
        return new File("jump_audio.wav");
    }

    public File fileCrashAudio() {
        return new File("crash_audio.wav");
    }

    public AudioInputStream audioStream(File file) {
        try {
            return AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException | IOException ex) {
            return null;
        }
    }
    
    public void playAudio(File file) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream(file));
            clip.start();
        } catch (LineUnavailableException | IOException ex) {
            
        }
    }

}
