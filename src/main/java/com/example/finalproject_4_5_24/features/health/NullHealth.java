package com.example.finalproject_4_5_24.features.health;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

final class NullHealth extends Health{
    NullHealth(DynamicGameElement.FeatureInitializationInfo info){super(info, 0);}
    @Override protected int heal(int amount) {return 0;}
    @Override protected int takeDamage(int amount) {return 0;}
}
