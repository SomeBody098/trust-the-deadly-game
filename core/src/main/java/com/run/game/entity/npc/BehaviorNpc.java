package com.run.game.entity.npc;

public class BehaviorNpc {

    private final String name;
    private final String backstory;

    public BehaviorNpc(String name, String backstory) {
        this.name = name;
        this.backstory = backstory;
    }

    public String getName() {
        return name;
    }

    public String getBackstory() {
        return backstory;
    }
}
