package com.example.finalproject_4_5_24.features.attack;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.Features;
import com.example.finalproject_4_5_24.features.health.HealthTypes;

public sealed abstract class Attack extends Features implements AttackProperties permits NormalAttack, NullAttack {
    protected Attack(DynamicGameElement.FeatureInitializationInfo info, int attackAmount){
        super(info);
        this.normalAttack = this.attackAmount = attackAmount;
    }

    @Override public final int getAttack() {return this.attackAmount;}
    @Override public final int getNormalAttack() {return this.normalAttack;}
    public int attack(HealthTypes.HealthEncapsulator health){return health.changeHealth(new AttackPacket(this.getEncapsulator(), -this.attackAmount));}
    @Override protected final void updateOnTurn() {}

    @Override
    protected void updateOnTick() {

    }

    @Override
    protected void updateOnChange() {

    }

    private final int attackAmount;
    private final int normalAttack;

}
