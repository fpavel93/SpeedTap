package com.example.speedtap;

import android.content.Context;

public class ColorButton extends android.support.v7.widget.AppCompatButton {

    String color;
    int size;

    public ColorButton(Context context,String color) {
        super(context);

        setColor(color);
        switch (color)
        {
            case "red":{ this.setBackground(getResources().getDrawable(R.drawable.ic_red_bubble_image)); break;}
            case "blue":{ this.setBackground(getResources().getDrawable(R.drawable.ic_blue_bubble_image)); break;}
            case "green":{ this.setBackground(getResources().getDrawable(R.drawable.ic_green_bubble_image)); break;}
            case "yellow":{ this.setBackground(getResources().getDrawable(R.drawable.ic_yellow_bubble_image)); break;}
            case "purple":{ this.setBackground(getResources().getDrawable(R.drawable.ic_purple_bubble_image)); break;}
            case "Heart":{this.setBackground(getResources().getDrawable(R.drawable.ic_broken_heart_svgrepo_com)); break;}
        }
    }

    public String getColor() {
        return color;
    }

    private void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
