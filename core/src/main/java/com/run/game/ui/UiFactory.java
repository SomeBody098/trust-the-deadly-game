package com.run.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.run.game.Main;
import com.run.game.entity.npc.Npc;
import com.run.game.map.MapRotator;
import com.run.game.ui.action.ScreenSwitchAction;
import com.run.game.ui.action.TurnCameraAction;
import com.run.game.utils.exception.NotInitializedObjectException;
import com.run.game.utils.net.NetManager;
import com.run.game.utils.param.ParamFactory;
import com.run.game.utils.param.UiLabelParam;
import com.run.game.utils.param.UiParam;

import java.text.ParseException;

public class UiFactory {

    private static final float GLOBAL_WIGHT = Gdx.graphics.getWidth();
    private static final float GLOBAL_HEIGHT = Gdx.graphics.getHeight();

    private static OrthographicCamera uiCamera;
    private static Viewport viewport;
    private static Batch batch;

    private static Skin skin;

    public static void init(OrthographicCamera uiCamera, Viewport viewport, Batch batch){
        UiFactory.uiCamera = uiCamera;
        UiFactory.viewport = viewport;
        UiFactory.batch = batch;

        skin = new Skin();
        skin.addRegions(new TextureAtlas("ui/uiskin.atlas"));
        skin.load(Gdx.files.internal("ui/uiskin.json"));
    }

    public static Stage createMainMenuStage(Main game, Screen targetScreen){
        Stage mainMenu = new Stage(viewport, batch);

        mainMenu.addActor(createStartButton(game, targetScreen));
        mainMenu.addActor(createLabel());

        return mainMenu;
    }

    public static Stage createGameUiStage(MapRotator place, Npc npc){ // FIXME: 07.07.2025 тут должен быть DTO а не целый npc
        Stage gameUi = new Stage(viewport, batch);

        gameUi.addActor(createLeftButton(place));
        gameUi.addActor(createRightButton(place));
        gameUi.addActor(createTextField(npc));

        return gameUi;
    }

    private static Button createStartButton(Main game, Screen targetScreen){
        isUiCameraInitialized();
        UiParam param = ParamFactory.getUiParam("start-button");

        Button button = new Button(skin, "start-button");
        button.addListener(new ScreenSwitchAction(game, targetScreen));

        setStandardBoundsForUiObject(button, param);

        return button;
    }

    private static Button createRightButton(MapRotator place){
        isUiCameraInitialized();
        UiParam param = ParamFactory.getUiParam("right-button");

        Button button = new Button(skin, "right-button");
        button.addListener(new TurnCameraAction((byte) 1, place));

        float wight = param.wight_percent * button.getWidth();
        float height = param.height_percent * button.getHeight();

        button.setBounds(
            param.position_x_percent * GLOBAL_WIGHT,
            param.position_y_percent * GLOBAL_HEIGHT - height / 2,
            wight, height
        );

        return button;
    }

    private static Button createLeftButton(MapRotator place){
        isUiCameraInitialized();
        UiParam param = ParamFactory.getUiParam("left-button");

        Button button = new Button(skin, "left-button");
        button.addListener(new TurnCameraAction((byte) -1, place));

        float wight = param.wight_percent * button.getWidth();
        float height = param.height_percent * button.getHeight();

        button.setBounds(
            param.position_x_percent * GLOBAL_WIGHT - wight,
            param.position_y_percent * GLOBAL_HEIGHT - height / 2,
            wight, height
        );

        return button;
    }

    private static TextField createTextField(Npc npc){ // FIXME: 07.07.2025 сделай через шину событий (Event bus) или Dto
        UiParam param = ParamFactory.getUiParam("text-field");
        TextField field = new TextField("", skin, "dialog");

        field.setFocusTraversal(false);
        field.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')){
                    String args = textField.getText();
                    textField.setText("");

                    try {
                        NetManager.giveRequestNpc(npc.getPrompt(args));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        setStandardBoundsForUiObject(field, param);

        return field;
    }

    private static Label createLabel(){
        UiLabelParam param = ParamFactory.getUiLabelParam("name-game-label");
        Label label = new Label(param.text, skin, "name-game-label");

        label.setAlignment(Align.center);
        setStandardBoundsForUiObject(label, param);

        return label;
    }

    private static void setStandardBoundsForUiObject(Actor uiObject, UiParam param){
        float wight = param.wight_percent * uiObject.getWidth();
        float height = param.height_percent * uiObject.getHeight();

        uiObject.setBounds(
            param.position_x_percent * GLOBAL_WIGHT - wight / 2,
            param.position_y_percent * GLOBAL_HEIGHT - height / 2,
            wight, height
        );
    }

    private static void isUiCameraInitialized(){
        if (uiCamera == null){
            throw new NotInitializedObjectException("uiCamera is not initialized!");
        }
    }
}
