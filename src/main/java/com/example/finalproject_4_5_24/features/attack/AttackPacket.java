package com.example.finalproject_4_5_24.features.attack;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;

public final class AttackPacket {
    AttackPacket(GameElementEncapsulator<?> encapsulator, int dmg) {
        this.encapsulator = encapsulator;
        this.dmg = dmg;
    }

    public GameElementEncapsulator<?> source() {return this.encapsulator;}
    public int dmg() {return this.dmg;}

    @Override public String toString() {return "AttackPacket[encapsulator=" + encapsulator + ", dmg=" + dmg + ']';}
    private final GameElementEncapsulator<?> encapsulator;
    private final int dmg;

}
