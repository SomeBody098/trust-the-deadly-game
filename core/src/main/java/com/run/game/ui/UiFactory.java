package com.run.game.ui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.run.game.Main;
import com.run.game.map.MapRotator;
import com.run.game.ui.button.CustomButton;
import com.run.game.ui.button.action.ButtonAction;
import com.run.game.ui.button.action.ScreenSwitchAction;
import com.run.game.ui.button.action.TurnCameraAction;
import com.run.game.utils.exception.NotInitializedObjectException;
import com.run.game.utils.param.ParamFactory;
import com.run.game.utils.param.UiParam;

public class UiFactory {

    private static OrthographicCamera uiCamera;
    private static Viewport viewport;
    private static Batch batch;

    public static void init(OrthographicCamera uiCamera, Viewport viewport, Batch batch){
        UiFactory.uiCamera = uiCamera;
        UiFactory.viewport = viewport;
        UiFactory.batch = batch;
    }

    public static Stage createMainMenuStage(Main game, Screen targetScreen){
        Stage mainMenu = new Stage(viewport, batch);

        mainMenu.addActor(createStartButton(game, targetScreen));

        return mainMenu;
    }

    public static Stage createGameUiStage(MapRotator place){
        Stage gameUi = new Stage(viewport, batch);

        gameUi.addActor(createLeftButton(place));
        gameUi.addActor(createRightButton(place));

        return gameUi;
    }

    private static CustomButton createStartButton(Main game, Screen targetScreen){
        isUiCameraInitialized();
        UiParam param = ParamFactory.getUiParam("start_button");

        Animation<Texture> animation = UiGraphic.getIdelAnimationStartButton(param.duration_animation);
        Texture pressed = UiGraphic.getPressedTextureStartButton();

        Rectangle bounds = createBoundsForUi(param, pressed.getWidth(), pressed.getHeight());
        ButtonAction action = new ScreenSwitchAction(game, targetScreen);

        return new CustomButton(
            "start_button",
            bounds,
            action,
            animation,
            pressed
        );
    }

    private static CustomButton createRightButton(MapRotator place){
        isUiCameraInitialized();
        UiParam param = ParamFactory.getUiParam("right_button");

        Animation<Texture> animation = UiGraphic.getIdelAnimationRightButton(param.duration_animation);
        Texture pressed = UiGraphic.getPressedTextureRightButton();

        Rectangle bounds = createBoundsForUi(param, pressed.getWidth(), pressed.getHeight());
        bounds.x += pressed.getWidth() * 2;

        ButtonAction action = new TurnCameraAction((byte) 1, place);

        return new CustomButton(
            "right_button",
            bounds,
            action,
            animation,
            pressed
        );
    }

    private static CustomButton createLeftButton(MapRotator place){
        isUiCameraInitialized();
        UiParam param = ParamFactory.getUiParam("left_button");

        Animation<Texture> animation = UiGraphic.getIdelAnimationLeftButton(param.duration_animation);
        Texture pressed = UiGraphic.getPressedTextureLeftButton();

        Rectangle bounds = createBoundsForUi(param, pressed.getWidth(), pressed.getHeight());
        bounds.x -= pressed.getWidth() * 2;

        ButtonAction action = new TurnCameraAction((byte) -1, place);

        return new CustomButton(
            "left_button",
            bounds,
            action,
            animation,
            pressed
        );
    }

    private static Rectangle createBoundsForUi(UiParam param, float wight, float height){
        float resultWight = wight * param.wight_percent;
        float resultHeight = height * param.height_percent;

        return new Rectangle(
            (uiCamera.viewportWidth * param.position_x_percent) - resultWight / 2,
            (uiCamera.viewportHeight * param.position_y_percent) - resultHeight / 2,
            resultWight,
            resultHeight
        );
    }

    private static void isUiCameraInitialized(){
        if (uiCamera == null){
            throw new NotInitializedObjectException("uiCamera is not initialized!");
        }
    }
}
