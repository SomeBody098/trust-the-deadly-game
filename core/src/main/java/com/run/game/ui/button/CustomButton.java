package com.run.game.ui.button;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.run.game.ui.button.action.ButtonAction;
import com.run.game.utils.exception.UnexpectedBehaviorException;

public class CustomButton extends Actor {

    private final Rectangle bounds;

    private final ButtonAction action;

    private final Animation<Texture> idelAnimation;
    private final Texture pressedTexture;
    private Texture currentFrame;

    private boolean isTouched;
    private boolean isPressed;

    private float stateTime;

    public CustomButton(String name, Rectangle bounds, ButtonAction action, Animation<Texture> idelAnimation, Texture pressedTexture) {
        super.setName(name);
        this.bounds = bounds;
        this.action = action;
        this.idelAnimation = idelAnimation;
        this.pressedTexture = pressedTexture;

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isPressed && bounds.contains(x, y)) {
                    executionTask();
                }
                isPressed = false;
                currentFrame = idelAnimation.getKeyFrame(0);
            }
        });
    }

    public void executionTask(){
        action.execute();
    }

    @Override
    public void act(float delta) {
        if (!isTouched) {
            currentFrame = idelAnimation.getKeyFrame(stateTime, true);
            stateTime += delta;
        }

        super.act(delta);
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        boolean touched = locatedOnBounds(x, y);

        if (touched) {
            currentFrame = pressedTexture;
            isTouched = true;
        } else {
            isTouched = false;
        }

        return touched ? this : null;
    }

    private boolean locatedOnBounds(float screenX, float screenY){
        return bounds.contains(screenX, screenY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        if (currentFrame == null) throw new UnexpectedBehaviorException("Unknown current frame fot draw.");

        Color color = getColor();
        color.set(color.r, color.g, color.b, color.a * parentAlpha);

        batch.setColor(color);
        batch.draw(currentFrame, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public void setPosition(float x, float y){
        bounds.setPosition(x, y);
        super.setPosition(x, y);
    }

    @Override
    public void setSize(float wight, float height){
        bounds.setSize(wight, height);
        super.setSize(wight, height);
    }

    @Override
    public void setBounds(float x, float y, float width, float height){
        bounds.set(x, y, width, height);
        super.setBounds(x, y, width, height);
    }
}
