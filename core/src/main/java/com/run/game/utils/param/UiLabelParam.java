package com.run.game.utils.param;

public class UiLabelParam extends UiParam{

    public String text;

    public UiLabelParam(float position_x_percent, float position_y_percent, float wight_percent, float height_percent, String text) {
        super(position_x_percent, position_y_percent, wight_percent, height_percent);

        this.text = text;
    }
}
