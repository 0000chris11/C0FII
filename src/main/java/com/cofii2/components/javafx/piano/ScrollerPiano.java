package com.cofii2.components.javafx.piano;

import java.util.PriorityQueue;
import java.util.Queue;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A Piano node inside a ScrollPane.
 * 1. Sound Works
 * 2. Constructor can be null or use the KeyAction interface
 * 
 * @author COFII
 */
public final class ScrollerPiano extends ScrollPane {

    public static final int WK_AMOUNT = 52;
    public static final int BK_AMOUNT = 36;

    private Rectangle[] wks = new Rectangle[WK_AMOUNT];
    private Rectangle[] bks = new Rectangle[BK_AMOUNT];

    private int[] bkIds = new int[] { 0, 2, 3, 5, 6, 7, 9, 10, 12, 13, 14, 16, 17, 19, 20, 21, 23, 24, 26, 27, 28, 30,
            31, 33, 34, 35, 37, 38, 40, 41, 42, 44, 45, 47, 48, 49 };

    public static final int WK_WIDTH = 34;
    public static final int WK_HEIGHT = 148;

    public static final int BK_WIDTH = 22;
    public static final int BK_HEIGHT = 84;

    public static final Color WK_FILL = Color.WHITE;
    public static final Color BK_FILL = Color.BLACK;

    public static final Color WK_FILL_PRESSED = Color.rgb(153, 204, 255);
    public static final Color BK_FILL_PRESSED = Color.rgb(0, 51, 102);

    public static final Color STROKE = Color.BLACK;

    private Note[] whiteNotes = new Note[WK_AMOUNT];
    private Note[] blackNotes = new Note[BK_AMOUNT];
    private MidiChannel currentChannel;

    private int velocity = 120;

    private void loadingNotes() {
        int wNotesCounter = 0;
        int bNotesCounter = 0;

        int letterCounter = 0;
        int nScale = 0;
        int noteIndex = 33;
        for (int a = 0; a < 88; a++) {

            if (letterCounter == Note.NOTE_SCALE.length) {
                letterCounter = 0;
            }
            String lnote = Note.NOTE_SCALE[letterCounter++];

            if (lnote.equals("C")) {
                nScale++;
            }

            String name = lnote + nScale;
            if (!lnote.contains("#")) {
                whiteNotes[wNotesCounter++] = new Note(a, name, noteIndex++);
            } else {
                blackNotes[bNotesCounter++] = new Note(a, name, noteIndex++);
            }

        }
    }

    private void synthInit() {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();

            synth.open();

            Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();
            MidiChannel[] channels = synth.getChannels();

            synth.loadInstrument(instruments[1]);
            currentChannel = channels[0];

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public ScrollerPiano(KeyAction ka) {
        synthInit();
        loadingNotes();

        Queue<Integer> queue = new PriorityQueue<>();
        for (int a = 0; a < bkIds.length; a++) {
            queue.offer(bkIds[a]);
        }

        int wx = 0;
        int bkCount = 0;
        for (int a = 0; a < WK_AMOUNT; a++) {
            wks[a] = new Rectangle(wx, 0, WK_WIDTH, WK_HEIGHT);
            wks[a].setFill(WK_FILL);
            wks[a].setStroke(STROKE);

            // WK LISTENERS
            final int aa = a;
            wks[a].setOnMousePressed(e -> {
                wks[aa].setFill(WK_FILL_PRESSED);
                currentChannel.noteOn(whiteNotes[aa].number, velocity);
                if (ka != null) {
                    ka.whiteKeyPressedAction(whiteNotes[aa].name);
                }
            });

            wks[a].setOnMouseReleased(e -> {
                wks[aa].setFill(WK_FILL);
                currentChannel.noteOff(whiteNotes[aa].number);
                if (ka != null) {
                    ka.whiteKeyReleasedAction(whiteNotes[aa].name);
                }
            });

            wx += WK_WIDTH;

            if (!queue.isEmpty()) {
                if (a == queue.peek()) {
                    bks[bkCount] = new Rectangle((wx - 12), 0, BK_WIDTH, BK_HEIGHT);
                    bks[bkCount].setFill(BK_FILL);
                    bks[bkCount].setStroke(STROKE);

                    // BK LISTENERS
                    final int bb = bkCount;
                    bks[bkCount].setOnMousePressed(e -> {
                        bks[bb].setFill(BK_FILL_PRESSED);
                        currentChannel.noteOn(blackNotes[bb].number, velocity);
                        if (ka != null) {
                            ka.blackKeyPressedAction(blackNotes[bb].name);
                        }
                    });

                    bks[bkCount].setOnMouseReleased(e -> {
                        bks[bb].setFill(BK_FILL);
                        currentChannel.noteOff(blackNotes[bb].number);
                        if (ka != null) {
                            ka.blackKeyReleasedAction(blackNotes[bb].name);
                        }
                    });

                    bkCount++;
                    queue.poll();
                }
            }
        }

        Group root = new Group();
        root.getChildren().addAll(wks);
        root.getChildren().addAll(bks);

        this.setContent(root);
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}
