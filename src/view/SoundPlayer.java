package view;


import javax.sound.sampled.*;
import java.io.File;

public class SoundPlayer {

	// Data Members
    private final String filePath;
    private Clip clip;
    private Boolean isMuted = true;
    
    // Constructor
    public SoundPlayer(String filePath) {
        this.filePath = filePath;
        loadClip();
    }

    private void loadClip() {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void playOrMute() {
    	
    	if(clip != null) {
    		
    		if(isMuted == true) {
    			this.play();
    			isMuted = false;
    		}
    		else {
    			this.mute();
    			isMuted = true;
    		}
    		
    	}
    }

    public void play() {
        if (clip != null) {
            if (clip.isRunning())
                clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            if (clip.isRunning())
                clip.stop();
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            isMuted = true;
        }
    }

    public void mute() {
    	
        if (clip != null) {

            if (!isMuted) {
                clip.stop();
                isMuted = true;
            } else {
                clip.start();
                isMuted = false;
            }

        }
    }
}
