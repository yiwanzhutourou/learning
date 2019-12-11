package com.youdushufang.dp.mvc.model;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BeatModel implements BeatModelInterface, MetaEventListener {

    private static final int END_OF_TRACK_MESSAGE = 47;

    private final int INIT_BPM = 90;

    private Sequencer sequencer;

    private List<BeatObserver> beatObservers = new ArrayList<>();
    private List<BPMObserver> bpmObservers = new ArrayList<>();

    private int bpm = INIT_BPM;

    private Sequence sequence;

    private Track track;

    @Override
    public void initialize() {
        setUpMidi();
        buildTracks();
    }

    @Override
    public void on() {
        sequencer.start();
        setBPM(INIT_BPM);
    }

    @Override
    public void off() {
        setBPM(0);
        sequencer.stop();
    }

    @Override
    public void setBPM(int bpm) {
        this.bpm = bpm;
        sequencer.setTempoInBPM(getBPM());
        notifyBPMObservers();
    }

    @Override
    public int getBPM() {
        return bpm;
    }

    @Override
    public void registerObserver(BeatObserver observer) {
        Objects.requireNonNull(observer);
        beatObservers.add(observer);
    }

    @Override
    public void unregisterObserver(BeatObserver observer) {
        Objects.requireNonNull(observer);
        beatObservers.remove(observer);
    }

    @Override
    public void registerObserver(BPMObserver observer) {
        Objects.requireNonNull(observer);
        bpmObservers.add(observer);
    }

    @Override
    public void unregisterObserver(BPMObserver observer) {
        Objects.requireNonNull(observer);
        bpmObservers.remove(observer);
    }

    @Override
    public void meta(MetaMessage meta) {
        if (meta.getType() == END_OF_TRACK_MESSAGE) {
            beatEvent();
            // loop here
            if (sequencer != null && sequencer.isOpen()) {
                sequencer.setTickPosition(0);
                setBPM(getBPM());
                sequencer.start();
            }
        }
    }

    private void beatEvent() {
        notifyBeatObservers();
    }

    private void notifyBeatObservers() {
        for (BeatObserver observer : beatObservers) {
            observer.updateBeat();
        }
    }

    private void notifyBPMObservers() {
        for (BPMObserver observer : bpmObservers) {
            observer.updateBPM();
        }
    }

    private void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);
            sequencer.setTempoInBPM(getBPM());
            sequence = new Sequence(Sequence.PPQ, 4);
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private void buildTracks() {
        int[] trackList = new int[] { 35, 0, 46, 0 };
        sequence.deleteTrack(track);
        track = sequence.createTrack();
        makeTracks(trackList);
        track.add(makeEvent(192, 1, 0, 4));
        try {
            sequencer.setSequence(sequence);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private void makeTracks(int[] trackList) {
        for (int i = 0; i < trackList.length; i++) {
            int key = trackList[i];
            if (key != 0) {
                track.add(makeEvent(144, key, 100, i));
                track.add(makeEvent(128, key, 100, i + 1));
            }
        }
    }

    private MidiEvent makeEvent(int comd, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, 9, one, two);
            event = new MidiEvent(a, tick);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return event;
    }
}
