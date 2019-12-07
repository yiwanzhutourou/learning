package com.youdushufang.dp.mediator;

import java.util.List;
import java.util.Objects;

public class ListBox extends Widget {

    private List<String> items;

    private String selected;

    public ListBox(List<String> items, DialogDirector dialogDirector) {
        super(dialogDirector);
        Objects.requireNonNull(items);
        this.items = items;
        this.selected = items.size() > 0 ? selected = items.get(0) : "";
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        if (items.contains(selected)) {
            this.selected = selected;
            changed();
        }
    }

    @Override
    public void show() {
        System.out.println("ListBox: " + selected);
    }
}
