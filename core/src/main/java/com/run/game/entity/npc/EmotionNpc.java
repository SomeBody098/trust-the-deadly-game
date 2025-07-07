package com.run.game.entity.npc;

import com.run.game.entity.Entity;

public class EmotionNpc implements Entity { // TODO: 06.07.2025 сделать для Npc свой Param

    public final byte MAX_LOYALTY;
    public final byte MAX_SADNESS;
    public final byte MAX_ANGER;

    private byte loyalty;
    private byte sadness;
    private byte anger;

    public EmotionNpc(byte MAX_LOYALTY, byte MAX_SADNESS, byte MAX_ANGER, byte loyalty, byte sadness, byte anger) {
        this.MAX_LOYALTY = MAX_LOYALTY;
        this.MAX_SADNESS = MAX_SADNESS;
        this.MAX_ANGER = MAX_ANGER;
        this.loyalty = loyalty;
        this.sadness = sadness;
        this.anger = anger;
    }

    public byte getLoyalty() {
        return loyalty;
    }

    public void increaseLoyalty(byte num) {
        loyalty = increase(loyalty, num, MAX_LOYALTY);
    }

    public void decreaseLoyalty(byte num) {
        loyalty = decrease(loyalty, num);
    }

    public byte getSadness() {
        return sadness;
    }

    public void increaseSadness(byte num) {
        sadness = increase(sadness, num, MAX_SADNESS);
    }

    public void decreaseSadness(byte num) {
        sadness = decrease(sadness, num);
    }


    public byte getAnger() {
        return anger;
    }

    public void increaseAnger(byte num) {
        anger = increase(anger, num, MAX_ANGER);
    }

    public void decreaseAnger(byte num) {
        anger = decrease(anger, num);
    }

    /**
     * This method increase num1 on num2.
     * If in result num1 gonna more max then num1 will be equal to max
     * @param num1
     * @param num2
     * @param max
     * @return mutated num1
     */
    private byte increase(byte num1, byte num2, byte max){
        num1 += num2;
        if (num1 > max) num1 = max;

        return num1;
    }

    /**
     * This method decrease num1 on num2.
     * If in result num1 gonna less 0 then num1 will be equal to 0
     * @param num1
     * @param num2
     * @return mutated num1
     */
    private byte decrease(byte num1, byte num2){
        num1 -= num2;
        if (num1 < 0) num1 = 0;

        return num1;
    }
}
