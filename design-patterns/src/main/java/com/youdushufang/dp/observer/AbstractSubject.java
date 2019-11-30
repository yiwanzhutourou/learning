package com.youdushufang.dp.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractSubject implements Subject {

    private List<Observer> observers;

    public AbstractSubject() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void attach(Observer observer) {
        Objects.requireNonNull(observer);
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        Objects.requireNonNull(observer);
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
