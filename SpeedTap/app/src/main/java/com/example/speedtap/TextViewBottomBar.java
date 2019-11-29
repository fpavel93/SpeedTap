package com.example.speedtap;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextViewBottomBar extends android.support.v7.widget.AppCompatTextView {

    String color;

    public TextViewBottomBar(Context context, String color) {
        super(context);

        setColor(color);

        switch (color)
        {
            case "red":{ this.setBackground(getResources().getDrawable(R.drawable.text_view_red)); break;}
            case "blue":{ this.setBackground(getResources().getDrawable(R.drawable.text_view_blue)); break;}
            case "green":{ this.setBackground(getResources().getDrawable(R.drawable.text_view_green)); break;}
            case "yellow":{ this.setBackground(getResources().getDrawable(R.drawable.text_view_yellow)); break;}
            case "purple":{ this.setBackground(getResources().getDrawable(R.drawable.text_view_purple)); break;}
        }
    }

    public String getColor() {
        return color;
    }

    private void setColor(String color) {
        this.color = color;
    }

}
