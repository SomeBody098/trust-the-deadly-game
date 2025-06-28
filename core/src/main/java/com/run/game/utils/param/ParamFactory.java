package com.run.game.utils.param;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

public class ParamFactory {

    private static final ObjectMap<String, JsonValue> DATA_STORAGE;
    private static final ObjectMap<String, Param> LATE_PARAM;

    static {
        DATA_STORAGE = new ObjectMap<>();
        LATE_PARAM = new ObjectMap<>();
        JsonReader reader = new JsonReader();

        DATA_STORAGE.put("ui", reader.parse(Gdx.files.internal("parameters/ui_property.json")));
        DATA_STORAGE.put("entity", reader.parse(Gdx.files.internal("parameters/entity_property.json")));
    }

    public static UiParam getUiParam(String uiName) {
        if (LATE_PARAM.containsKey(uiName)) return (UiParam) LATE_PARAM.get(uiName);

        JsonValue uiValue = getJsonValue(uiName, "ui");

        UiParam param = new UiParam(
            uiValue.getFloat("position_x_percent"),
            uiValue.getFloat("position_y_percent"),
            uiValue.getFloat("wight_percent"),
            uiValue.getFloat("height_percent")
        );

        LATE_PARAM.put(uiName, param);

        return param;
    }

    public static UiLabelParam getUiLabelParam(String uiName) {
        if (LATE_PARAM.containsKey(uiName)) return (UiLabelParam) LATE_PARAM.get(uiName);

        JsonValue uiValue = getJsonValue(uiName, "ui");

        UiLabelParam param = new UiLabelParam(
            uiValue.getFloat("position_x_percent"),
            uiValue.getFloat("position_y_percent"),
            uiValue.getFloat("wight_percent"),
            uiValue.getFloat("height_percent"),
            uiValue.getString("text")
        );

        LATE_PARAM.put(uiName, param);

        return param;
    }


    public static EntityParam getEntityParam(String entityName){
        if (LATE_PARAM.containsKey(entityName)) return (EntityParam) LATE_PARAM.get(entityName);

        JsonValue uiValue = getJsonValue(entityName, "entity");

        // TODO: 08.06.2025 в будущем реализуй...

        return null;
    }

    private static JsonValue getJsonValue(String nameObject, String nameJson){
        if (!DATA_STORAGE.containsKey(nameJson)) throw new IllegalArgumentException("Unknown name Json file: " + nameJson);
        JsonValue root = DATA_STORAGE.get(nameJson);

        if (!root.has(nameObject)) throw new IllegalArgumentException("Unknown name object in Json file: " + nameObject);

        return root.get(nameObject);
    }
}
