package com.run.game.entity;

public class Npc implements Entity { // TODO: 06.07.2025 сделать для Npc свой Param

    public static byte MAX_LOYALTY = 10;
    public static byte MAX_SADNESS = 10;
    public static byte MAX_ANGER = 10;

    private byte loyalty = 0;
    private byte sadness = 0;
    private byte anger = 0;
    private String response;




    public String getResponse(){
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public byte getLoyalty() {
        return loyalty;
    }

    public byte getSadness() {
        return sadness;
    }

    public byte getAnger() {
        return anger;
    }
}
