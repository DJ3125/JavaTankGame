package com.example.finalproject_4_5_24.features.attack;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.features.health.HealthTypes;

final class NormalAttack extends Attack{
    NormalAttack(DynamicGameElement.FeatureInitializationInfo info, int attackAmount){super(info, attackAmount);}

    @Override public int attack(HealthTypes.HealthEncapsulator health) {

        return super.attack(health);
    }
}
