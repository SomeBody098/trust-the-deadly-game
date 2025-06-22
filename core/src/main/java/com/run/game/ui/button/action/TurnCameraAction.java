package com.run.game.ui.button.action;

import com.run.game.map.MapRotator;
import com.run.game.utils.exception.UnexpectedBehaviorException;

public class TurnCameraAction implements ButtonAction {

    private final byte once;

    private MapRotator place;

    public TurnCameraAction(byte once, MapRotator place) {
        this.once = once;
        this.place = place;
    }

    @Override
    public void execute() {
        if (place == null) throw new UnexpectedBehaviorException("Unknown place for processing!");

        place.rotate(once);
    }

    public MapRotator getPlace() {
        return place;
    }

    public void setPlace(MapRotator place) {
        this.place = place;
    }
}
