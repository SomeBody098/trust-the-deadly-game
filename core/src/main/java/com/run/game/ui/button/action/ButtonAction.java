package com.run.game.ui.button.action;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public abstract class ButtonAction extends ClickListener {

    public abstract void execute();

    @Override
    public void clicked(InputEvent event, float x, float y) {
        execute();
        super.clicked(event, x, y);
    }

}
