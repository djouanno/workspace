package com.esir.sr.sweetsnake.sound;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.enumeration.SoundEffect;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class SoundPlayer
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger     log          = LoggerFactory.getLogger(SoundPlayer.class);

    /** The playing clips list */
    private static final List<Clip> playingClips = new LinkedList<Clip>();

    /** Is the player muted */
    private static boolean          isMuted      = false;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Empty constructor to prevent instantiation of the sound player
     */
    private SoundPlayer() {
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC STATIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param soundEffect
     */
    public static void play(final SoundEffect soundEffect) {
        if (!isMuted) {
            try {
                log.debug("Playing sound {}", soundEffect);
                final AudioInputStream stream = AudioSystem.getAudioInputStream(new BufferedInputStream(SoundPlayer.class.getResourceAsStream(soundEffect.getPath())));
                final Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, stream.getFormat()));
                clip.open(stream);
                clip.loop(soundEffect.getLooping());
                clip.start();
                playingClips.add(clip);
                clip.addLineListener(new LineListener() {
                    @Override
                    public void update(final LineEvent e) {
                        if (e.getType().equals(LineEvent.Type.STOP)) {
                            playingClips.remove(clip);
                            log.debug("Removing sound from the playing clips list {}", soundEffect);
                        }
                    }
                });
                stream.close();
            } catch (final LineUnavailableException e) {
                log.warn(e.getMessage(), e);
            } catch (UnsupportedAudioFileException | IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 
     */
    public static void mute() {
        log.debug("Muting the sound player");
        isMuted = true;
        for (final Clip clip : playingClips) {
            stop(clip);
        }
    }

    /**
     * 
     */
    public static void unmute() {
        log.debug("Unmuting the sound player");
        isMuted = false;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE STATIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param clip
     */
    private static void stop(final Clip clip) {
        clip.stop();
        playingClips.remove(clip);
    }

}
