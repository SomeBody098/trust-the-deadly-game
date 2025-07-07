package com.run.game.utils.param;

public class NpcParam implements Param{ // TODO: 07.07.2025 следует еще расширить спектр эмоций и чувств

    public final String name;
    public final String backstory;

    public byte MAX_LOYALTY;
    public byte MAX_SADNESS;
    public byte MAX_ANGER;

    public byte initial_loyalty;
    public byte initial_sadness;
    public byte initial_anger;

    public NpcParam(String name, String backstory, byte MAX_LOYALTY, byte MAX_SADNESS, byte MAX_ANGER, byte initial_loyalty, byte initial_sadness, byte initial_anger) {
        this.name = name;
        this.backstory = backstory;
        this.MAX_LOYALTY = MAX_LOYALTY;
        this.MAX_SADNESS = MAX_SADNESS;
        this.MAX_ANGER = MAX_ANGER;
        this.initial_loyalty = initial_loyalty;
        this.initial_sadness = initial_sadness;
        this.initial_anger = initial_anger;
    }
}
