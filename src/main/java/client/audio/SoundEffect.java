package client.audio;

import javax.sound.sampled.*;
import java.io.File;

public class SoundEffect {
    private static Clip myClip;
    private static AudioInputStream ais;

    public static void setFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.exists()) {
                myClip = AudioSystem.getClip();
                ais = AudioSystem.getAudioInputStream(file.toURI().toURL());
                myClip.open(ais);
                myClip.addLineListener(new LineListener() {
                    public void update(LineEvent event) {
                        if (event.getType() == LineEvent.Type.STOP) {
                            effectStop();
                        }
                    }
                });
            } else {
                System.out.println("Sound: file not found: " + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
    }

    public static void effectPlay() {
        myClip.start();
    }

    public static void effectStop() {
        myClip.close();
    }

    public static Clip getMyClip() {
        return myClip;
    }
}
