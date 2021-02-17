package client.audio;

import javax.sound.sampled.*;
import java.io.File;

public class Audio {
    private static Clip myClip;
    private static float soundVolume = 1;

    public Audio(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                myClip = AudioSystem.getClip();
                AudioInputStream ais = AudioSystem.getAudioInputStream(file.toURI().toURL());
                myClip.open(ais);
            } else {
                throw new RuntimeException("Sound: file not found: " + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
    }

    public void setFile(String fileName) {
        myClip.stop();
        try {
            File file = new File(fileName);
            if (file.exists()) {
                myClip = AudioSystem.getClip();
                AudioInputStream ais = AudioSystem.getAudioInputStream(file.toURI().toURL());
                myClip.open(ais);
            } else {
                throw new RuntimeException("Sound: file not found: " + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
    }

    public static void play() {
        FloatControl floatControl = (FloatControl) myClip.getControl(FloatControl.Type.MASTER_GAIN);
        floatControl.setValue(20f * (float) Math.log10(soundVolume));
        myClip.loop(Clip.LOOP_CONTINUOUSLY);
        myClip.setFramePosition(0);
        myClip.start();
    }

    public static void stop() {
        myClip.stop();
        myClip.close();
    }

    public static void decreaseSound() {
        System.out.println(soundVolume);
        soundVolume -= 0.1;

        FloatControl floatControl = (FloatControl) myClip.getControl(FloatControl.Type.MASTER_GAIN);
        floatControl.setValue(20f * (float) Math.log10(soundVolume));

    }

    public static void increaseSound() {
        System.out.println(soundVolume);
        if (soundVolume < 1) {
            soundVolume += 0.1;

            FloatControl floatControl = (FloatControl) myClip.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(20f * (float) Math.log10(soundVolume));
        }
    }
}
