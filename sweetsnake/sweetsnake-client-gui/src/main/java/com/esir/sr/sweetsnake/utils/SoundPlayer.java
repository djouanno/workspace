package com.esir.sr.sweetsnake.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
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
    private static final Logger                                log = LoggerFactory.getLogger(SoundPlayer.class);

    /** The sounds mapping */
    private static final Map<SoundEffect, BufferedInputStream> sounds;

    /**********************************************************************************************
     * [BLOCK] STATIC INITIALIZATION
     **********************************************************************************************/

    static {
        sounds = new LinkedHashMap<SoundEffect, BufferedInputStream>();
        sounds.put(SoundEffect.MOVE, new UnclosableBufferedInputStream(SoundPlayer.class.getResourceAsStream(SoundEffect.MOVE.toString())));
        sounds.put(SoundEffect.EAT, new UnclosableBufferedInputStream(SoundPlayer.class.getResourceAsStream(SoundEffect.EAT.toString())));
    }

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
        try {
            final AudioInputStream stream = AudioSystem.getAudioInputStream(sounds.get(soundEffect));
            final Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, stream.getFormat()));
            clip.open(stream);
            clip.start();
            stream.close();
        } catch (final LineUnavailableException e) {
            log.warn(e.getMessage(), e);
        } catch (UnsupportedAudioFileException | IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
