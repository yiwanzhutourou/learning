package com.youdushufang.dp.mediator;

public class SomeDialogDirector implements DialogDirector {

    private Button button;

    private ListBox listBox;

    private TextView textView;

    public void setButton(Button button) {
        this.button = button;
    }

    public void setListBox(ListBox listBox) {
        this.listBox = listBox;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void widgetChanged(Widget widget) {
        if (widget == button) {
            listBox.setSelected(button.getText());
        } else if (widget == listBox) {
            textView.setText(listBox.getSelected());
        }
    }
}
