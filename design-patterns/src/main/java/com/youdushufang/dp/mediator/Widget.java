package com.youdushufang.dp.mediator;

public abstract class Widget {

    private DialogDirector dialogDirector;

    public Widget(DialogDirector dialogDirector) {
        this.dialogDirector = dialogDirector;
    }

    protected void changed() {
        dialogDirector.widgetChanged(this);
    }

    public abstract void show();
}
