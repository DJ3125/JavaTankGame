package com.example.finalproject_4_5_24.features.health;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

final class NormalHealth extends Health{
    NormalHealth(DynamicGameElement.FeatureInitializationInfo info, int healthAmount) {super(info, healthAmount);}
    @Override protected int heal(int amount) {return this.changeCurrentHealth(amount);}
    @Override protected int takeDamage(int amount) {return this.changeCurrentHealth(amount);}
}
