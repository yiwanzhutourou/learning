package com.youdushufang.dp.mvc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HeatrModel implements HearModelInterface, Runnable {

    private List<BeatObserver> beatObservers = new ArrayList<>();
    private List<BPMObserver> bpmObservers = new ArrayList<>();

    private Random random = new Random(System.currentTimeMillis());

    private int time = 1000;

    public HeatrModel() {
        new Thread(this).start();
    }

    @Override
    public int getHeartRate() {
        return 60000 / time;
    }

    @Override
    public void registerBeatObserver(BeatObserver observer) {
        Objects.requireNonNull(observer);
        beatObservers.add(observer);
    }

    @Override
    public void removeBeatObserver(BeatObserver observer) {
        Objects.requireNonNull(observer);
        beatObservers.remove(observer);
    }

    @Override
    public void registerBPMObserver(BPMObserver observer) {
        Objects.requireNonNull(observer);
        bpmObservers.add(observer);
    }

    @Override
    public void removeBPMObserver(BPMObserver observer) {
        Objects.requireNonNull(observer);
        bpmObservers.add(observer);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        int lastRate = -1;

        for (;;) {
            int change = random.nextInt(10);
            if (random.nextInt(2) == 0) {
                change = 0 - change;
            }
            int rate = 60000 / (time + change);
            if (rate < 120 && rate > 50) {
                time += change;
            }
            notifyBeatObservers();
            if (rate != lastRate) {
                lastRate = rate;
                notifyBPMObservers();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException ignored) {}
        }
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
}
