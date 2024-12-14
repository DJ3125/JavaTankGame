package com.example.finalproject_4_5_24.features.attack;

sealed interface AttackProperties permits Attack, AttackTypes.AttackEncapsulator {
    int getAttack();
    int getNormalAttack();
//    int attack(HealthTypes.HealthEncapsulator health);
}
